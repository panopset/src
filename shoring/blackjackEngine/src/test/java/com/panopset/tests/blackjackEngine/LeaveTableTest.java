package com.panopset.tests.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.BlackjackGameEngine;
import com.panopset.blackjackEngine.CardCounterImpl;

public class LeaveTableTest extends AbstractBlackjackTest {

  @Test
  void test() {
    CardCounterImpl.reset();
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 6;
      }

      @Override
      public boolean isCountVeryNegative() {
        return CardCounterImpl.getCount() < -3;
      }
    });
    bge.shuffle();
    Assertions.assertFalse(bge.getConfig().isCountVeryNegative());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL}, lowCountTest());
    Assertions.assertTrue(bge.getConfig().isCountVeryNegative());
    Assertions.assertFalse(bge.isShuffleFlagOn());
    bge.exec(CMD_DEAL);
    Assertions.assertTrue(bge.isShuffleFlagOn());
  }
}
