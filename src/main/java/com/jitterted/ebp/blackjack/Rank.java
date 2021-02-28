package com.jitterted.ebp.blackjack;

public class Rank {
  private final String rank;

  public Rank(String rank) {
    this.rank = rank;
  }

  public static Rank of(String rank) {
    return new Rank(rank);
  }

  public int rankValue() {
    if (isFaceCard()) {
      return 10;
    } else if (isAceCard()) {
      return 1;
    } else {
      return Integer.parseInt(rank);
    }
  }

  private boolean isAceCard() {
    return rank.equals("A");
  }

  private boolean isFaceCard() {
    return "JQK".contains(rank);
  }

  public String display() {
    return rank;
  }
}
