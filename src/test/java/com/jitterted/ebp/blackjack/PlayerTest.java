package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.fusesource.jansi.Ansi.ansi;

public class PlayerTest {
    @Test
    public void newPlayerDepositAmountThenBalance() {
        Player player = new Player();
        assertThat(player.playerBalance()).isEqualTo(0);
        player.playerDeposits(10);
        assertThat(player.playerBalance()).isEqualTo(10);
    }

    @Test
    public void playerPlaceBetReducedBalance() {
        Player player = new Player();
        player.playerDeposits(100);
        player.playerBets(50);
        assertThat(player.playerBalance()).isEqualTo(50);
    }

    @Test
    public void playerWinsBetThenBalanceIncreases() {
        Player player = new Player();
        player.playerDeposits(100);
        player.playerBets(50);
        player.playerWins();
        assertThat(player.playerBalance()).isEqualTo(150);
    }

    @Test
    public void playerLosesBetThenBalanceDecreases() {
        Player player = new Player();
        player.playerDeposits(100);
        player.playerBets(50);
        player.playerLoses();
        assertThat(player.playerBalance()).isEqualTo(50);
    }

    @Test
    public void playerTiesBetThenBalanceStaysSame() {
        Player player = new Player();
        player.playerDeposits(100);
        player.playerBets(50);
        player.playerTies();
        assertThat(player.playerBalance()).isEqualTo(100);
    }
}
