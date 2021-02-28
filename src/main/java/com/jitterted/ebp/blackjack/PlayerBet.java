package com.jitterted.ebp.blackjack;

class PlayerBet {
    private int betAmount = 0;
    private int totalBetAmount = 0;

    public PlayerBet() {}

    public void placeBet(int amount) {
        this.betAmount = amount;
        this.totalBetAmount += amount;
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

    public int totalBetAmount () {
        return this.totalBetAmount;
    }
}
