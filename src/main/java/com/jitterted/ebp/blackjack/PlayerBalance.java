package com.jitterted.ebp.blackjack;

public class PlayerBalance {
    private int playerBalance = 0;

    public PlayerBalance(int playerBalance) {
        this.playerBalance = playerBalance;
    }

    public int getPlayerBalance() {
        return this.playerBalance;
    }

    public void deposit(int depositAmount) {
        this.playerBalance += depositAmount;
    }

    public void withdraw(int withDrawAmount) {
        this.playerBalance -= withDrawAmount;
    }

//    public void addAmount(int amount) {
//        this.playerBalance += amount;
//    }
}
