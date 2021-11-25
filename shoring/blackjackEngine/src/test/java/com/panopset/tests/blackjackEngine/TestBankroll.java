package com.panopset.tests.blackjackEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.panopset.compat.Stringop;
import com.panopset.blackjackEngine.Bankroll;

public class TestBankroll {

    Bankroll bankroll = new Bankroll(30000);

    @Test
    void test() {
      assertEquals("$5.95", Stringop.getDollarString(595));
        bankroll.subtract(1000);
        assertEquals(29000, bankroll.getChips());
        assertEquals(-1000, bankroll.getStakeOutOfPlay());
        bankroll.subtract(30000);
        assertEquals(29000, bankroll.getChips());
        assertEquals(-31000, bankroll.getStakeOutOfPlay());
        assertEquals(2, bankroll.getReloadCount());
        assertEquals(29000, bankroll.getChips());
        assertEquals(-31000, bankroll.getStakeOutOfPlay());
        bankroll.add(5000);
        assertEquals(34000, bankroll.getChips());
        assertEquals(-26000, bankroll.getStakeOutOfPlay());
        bankroll.add(500000);
        assertEquals(474000, bankroll.getStakeOutOfPlay());
        assertEquals(2, bankroll.getReloadCount());
        assertEquals(474000, bankroll.getStakeOutOfPlay());
        bankroll.subtract(474000);
        assertEquals(0, bankroll.getStakeOutOfPlay());
        assertEquals(60000, bankroll.getChips());
        bankroll.reset();
        assertEquals(0, bankroll.getStakeOutOfPlay());
        assertEquals(30000, bankroll.getChips());
    }

}
