package com.panopset.tests.blackjackEngine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.Bankroll;
import com.panopset.blackjackEngine.Wager;

public class HandWagerTest {

  @Test
  void test() {
    Wager hb = new Wager(new Bankroll(300));
    hb.setInitialBet(500);
    hb.setInitialPayoff(500);
    hb.doubleDown();
    hb.setDoubledPayoff(500);
    Assertions.assertEquals(2000, hb.getLiveValue());
    hb = new Wager(new Bankroll(300));
    hb.setInitialBet(500);
    Assertions.assertEquals(500, hb.getLiveValue());
    hb.lost();
    Assertions.assertEquals(0, hb.getLiveValue());
  }
}
