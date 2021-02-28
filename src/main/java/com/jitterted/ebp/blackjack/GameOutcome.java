package com.jitterted.ebp.blackjack;

public enum GameOutcome {
    PLAYER_BUSTED("You Busted, so you lose.  💸"),
    DEALER_BUSTED("Dealer went BUST, Player wins! Yay for you!! 💵"),
    PLAYER_BEATS_DEALER("You beat the Dealer! 💵"),
    PLAYER_PUSHES("Push: The house wins, you Lose. 💸"),
    PLAYER_LOSES("You lost to the Dealer. 💸");

    private final String message;

    GameOutcome(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
