package com.jitterted.ebp.blackjack;

import org.fusesource.jansi.Ansi;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class Game {

  private final Deck deck;
  private final Player player = new Player();

  private Hand dealerHand = new Hand();
  private Hand playerHand = new Hand();
  //private PlayerBet playerBet = new PlayerBet(0);

  public static void main(String[] args) {
    displayWelcomeScreen();
    playGame();
    resetScreen();
  }

  private static void displayWelcomeScreen() {
    System.out.println(ansi()
                           .bgBright(Ansi.Color.WHITE)
                           .eraseScreen()
                           .cursor(1, 1)
                           .fgGreen().a("Welcome to")
                           .fgRed().a(" Jitterted's")
                           .fgBlack().a(" BlackJack"));
  }

  private static void playGame() {
    Game game = new Game();
    game.takePlayerDeposit(game);
    String input;
    do {
      game.initialDeal();
      game.play();
      if(game.playerBalanceNil()) {
        System.out.println("Player ran out of balance. Game over !!");
        break;
      }
      System.out.println("Play again? (y/n):");
      Scanner scanner = new Scanner(System.in);
      input = scanner.nextLine();
    } while (input.equalsIgnoreCase("y"));
  }

  private void takePlayerDeposit(Game game) {
    boolean invalidInput = true;
    do {
      invalidInput = !game.getPlayerDeposit();
    }while(invalidInput);
  }

  private boolean playerBalanceNil() {
    return player.playerBalance() == 0;
  }

  private static void resetScreen() {
    System.out.println(ansi().reset());
  }

  public Game() {
    deck = new Deck();
  }

  public void initialDeal() {
    dealerHand = new Hand();
    playerHand = new Hand();

    // deal first round of cards, players first
    dealHand();

    // deal next round of cards
    dealHand();
  }

  private void dealHand() {
    drawCardIntoPlayerHand();
    drawCardIntoDealerHand();
  }

  private void drawCardIntoDealerHand() {
    dealerHand.add(deck.draw());
  }

  private void drawCardIntoPlayerHand() {
    playerHand.add(deck.draw());
  }

  public void play() {
    takePlayerBet();
    // get Player's decision: hit until they stand, then they're done (or they go bust)
    boolean playerBusted = false;
    while (!playerBusted) {
      displayGameState();
      String playerChoice = inputFromPlayer().toLowerCase();
      if (playerStops(playerChoice)) {
        break;
      }
      if (playerHits(playerChoice)) {
        drawCardIntoPlayerHand();
        playerBusted = playerBusted();
      } else {
        System.out.println("You need to [H]it or [S]tand");
      }
    }

    // Dealer makes its choice automatically based on a simple heuristic (<=16, hit, 17>stand)
    if (!playerBusted) {
      dealerPlays();
    }

    displayFinalGameState();

    handleGameOutcome();
  }

  private void takePlayerBet() {
    System.out.println("Player balance - " + player.playerBalance());
    boolean betPlaced = false;
    do{
      betPlaced = getPlayerBet();
    } while(!betPlaced);
  }

  private boolean playerHits(String playerChoice) {
    return playerChoice.startsWith("h");
  }

  private boolean playerStops(String playerChoice) {
    return playerChoice.startsWith("s");
  }

  private void dealerPlays() {
    while (dealerHand.value() <= 16) {
      drawCardIntoDealerHand();
    }
  }

  private void handleGameOutcome() {
    GameOutcome gameOutcome = getGameOutcome();
    System.out.println(gameOutcome.getMessage());
  }

  private GameOutcome getGameOutcome() {
    if (playerBusted()) {
      playerLoses();
      return GameOutcome.PLAYER_BUSTED;
    }
    if (dealerBusted()) {
      playerWins();
      return GameOutcome.DEALER_BUSTED;
    }
    if (playerBeatsDealer()) {
      playerWins();
      return GameOutcome.PLAYER_BEATS_DEALER;
    }
    if (playerPushes()) {
      playerTies();
      return GameOutcome.PLAYER_PUSHES;
    }
    playerLoses();
    return GameOutcome.PLAYER_LOSES;
  }

  private boolean playerPushes() {
    return playerHand.pushesWith(dealerHand);
  }

  private boolean playerBeatsDealer() {
    return playerHand.beats(dealerHand);
  }

  private boolean dealerBusted() {
    return dealerHand.isBusted();
  }

  private boolean playerBusted() {
    return playerHand.isBusted();
  }

  private void displayGameState() {
    clearScreen();
    displayDealerHand();
    displayPlayerHand();
  }

  private void displayFinalGameState() {
    clearScreen();
    displayFinalDealerHand();
    displayPlayerHand();
  }

  private void displayDealerHand() {
    displayDealerUpCard();
    displayDealerHoleCard();
  }

  private void displayDealerUpCard() {
    System.out.println("Dealer has: ");
    System.out.println(dealerHand.displayFirstCard()); // first card is Face Up
  }

  private void clearScreen() {
    System.out.print(ansi().eraseScreen().cursor(1, 1));
  }
  // second card is the hole card, which is hidden

  private void displayDealerHoleCard() {
    System.out.print(
        ansi()
            .cursorUp(7)
            .cursorRight(12)
            .a("┌─────────┐").cursorDown(1).cursorLeft(11)
            .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
            .a("│░ J I T ░│").cursorDown(1).cursorLeft(11)
            .a("│░ T E R ░│").cursorDown(1).cursorLeft(11)
            .a("│░ T E D ░│").cursorDown(1).cursorLeft(11)
            .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
            .a("└─────────┘"));
  }

  private void displayFinalDealerHand() {
    System.out.println("Dealer has: ");
    dealerHand.displayHand();
    System.out.println(" (" + dealerHand.value() + ")");
  }

  private void displayPlayerHand() {
    System.out.println();
    System.out.println("Player has: ");
    playerHand.displayHand();
    System.out.println(" (" + playerHand.value() + ")");
  }

  public void playerDeposits(int amount) {
    player.playerDeposits(amount);
  }

  public void playerBets(int betAmount) {
    player.playerBets(betAmount);
  }

  public int playerBalance() {
    return player.playerBalance();
  }

  public void playerWins() {
    player.playerWins();
  }

  public void playerLoses() {
    player.playerLoses();
  }

  public void playerTies() {
    player.playerTies();
  }

  private String inputFromPlayer() {
    //return player.getPlayerOption();
    return PlayerInput.getOption();
  }

  private boolean getPlayerDeposit() {
    //int depositAmount = player.inputDepositAmount();
    int depositAmount = PlayerInput.getDepositAmount();
    try {
      playerDeposits(depositAmount);
    } catch (Exception e) {
      System.out.println("Error setting player deposit. " + e.getMessage());
      return false;
    }
    return true;
  }

  private boolean getPlayerBet() {
    //int betAmount = player.inputBetAmount();
    int betAmount = PlayerInput.getBetAmount();
    try {
      playerBets(betAmount);
    } catch (Exception e) {
      System.out.println("Error placing player bet. " + e.getMessage());
      return false;
    }
    return true;
  }
}
