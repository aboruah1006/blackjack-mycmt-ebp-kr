package com.jitterted.ebp.blackjack;

public class Player {
    //private final Game game;
    private PlayerBalance playerBalance = new PlayerBalance(0);
    private PlayerBet playerBet = new PlayerBet(0);

    public Player() { }

    public void playerDeposits(int amount) {
        playerBalance.deposit(amount);
    }

    public void playerBets(int betAmount) {
        playerBet.placeBet(betAmount);
        playerBalance.withdraw(betAmount);
    }

    public int playerBalance() {
        return playerBalance.getPlayerBalance();
    }

    public void playerWins() {
        playerBalance.deposit(playerBet.winAmount());
    }

    public void playerLoses() {
        playerBalance.deposit(playerBet.loseAmount());
    }

    public void playerTies() {
        playerBalance.deposit(playerBet.tieAmount());
    }
}