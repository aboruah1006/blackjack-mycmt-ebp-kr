package com.jitterted.ebp.blackjack;

public class PlayerBet {
    private int betAmount = 0;

    public PlayerBet(int betAmount) {
        this.betAmount = betAmount;
    }

    public void placeBet(int amount) {
        this.betAmount = amount;
    }
    
    public int winAmount() {
        return this.betAmount * 2;
    }
    
    public int loseAmount() {
        return 0;
    }
    
    public int tieAmount() {
        return this.betAmount;
    }
}
