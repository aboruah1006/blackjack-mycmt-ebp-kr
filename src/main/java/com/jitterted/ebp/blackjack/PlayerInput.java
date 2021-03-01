package com.jitterted.ebp.blackjack;

import java.util.Scanner;

public class PlayerInput {

    public static int getBetAmount() {
        try {
            return getIntegerInput("Enter Bet amount:");
        } catch (Exception e) {
            throw new IllegalArgumentException("Error Parsing bet amount." + e.getMessage());
        }
    }

    public static int getDepositAmount() {
        try {
            return getIntegerInput("Enter Deposit amount:");
        } catch (Exception e) {
            throw new IllegalArgumentException("Error Parsing deposit amount." + e.getMessage());
        }
    }

    public static String getOption() {
        String playerOption = inputFromPlayer("[H]it or [S]tand?");
        return playerOption;
    }

    private static int getIntegerInput(String message) throws Exception{
        String amountString = inputFromPlayer(message);
        return Integer.parseInt(amountString);
    }

    private static String inputFromPlayer(String messageToDisplay) {
        System.out.println(messageToDisplay);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
