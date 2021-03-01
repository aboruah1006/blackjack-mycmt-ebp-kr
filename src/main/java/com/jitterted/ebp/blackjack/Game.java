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

    String input;
    do {
      game.initialDeal();
      game.play();
      System.out.println("Play again? (y/n):");
      Scanner scanner = new Scanner(System.in);
      input = scanner.nextLine();
    } while (input.equalsIgnoreCase("y"));
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
    if (playerBusted())
      return GameOutcome.PLAYER_BUSTED;
    if (dealerBusted())
      return GameOutcome.DEALER_BUSTED;
    if (playerBeatsDealer())
      return GameOutcome.PLAYER_BEATS_DEALER;
    if (playerPushes())
      return GameOutcome.PLAYER_PUSHES;
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

  private String inputFromPlayer() {
    System.out.println("[H]it or [S]tand?");
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
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
}
