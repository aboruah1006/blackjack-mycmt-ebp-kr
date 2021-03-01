package com.jitterted.ebp.blackjack;

import java.util.Scanner;

public class Player {
    //private final Game game;
    private PlayerBalance playerBalance = new PlayerBalance(0);
    private PlayerBet playerBet = new PlayerBet();

    public Player() { }

    public void playerDeposits(int amount) {
        if(depositAmountLessThanOrEqualsZero(amount))
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
        playerBalance.deposit(amount);
    }

    public void playerBets(int betAmount) {
        if(betAmountIsGreaterThanPlayerBalance(betAmount))
            throw new IllegalArgumentException("Bet amount must be less than or equal to balance amount");
        playerBet.placeBet(betAmount);
        playerBalance.withdraw(betAmount);
        int bonusForCurrentBet = bonusCalculator(betAmount);
        playerBalance.deposit(bonusForCurrentBet);
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

    public int totalAmountBet() {
        return playerBet.totalBetAmount();
    }

    private boolean betAmountIsGreaterThanPlayerBalance(int betAmount) {
        return betAmount > playerBalance.getPlayerBalance();
    }

    private int bonusCalculator(int currentBetAmount) {
        if(betAmountGreaterThanEqualHundred(currentBetAmount))
            return 10;
        return 0;
    }

    private boolean betAmountGreaterThanEqualHundred(int currentBetAmount) {
        return currentBetAmount >= 100;
    }

    private boolean depositAmountLessThanOrEqualsZero(int amount) {
        return amount <= 0;
    }
}