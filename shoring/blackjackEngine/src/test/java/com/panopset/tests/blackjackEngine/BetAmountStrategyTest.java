package com.panopset.tests.blackjackEngine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.BetAmountStrategy;
import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.BlackjackGameEngine;

public class BetAmountStrategyTest {

  @Test
  void testDoubleAfterBust() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {

      @Override
      public boolean isBetIdeaDoubleAfterBust() {
        return true;
      }
    });
    bge.setAutomaticOnForTesting();
    BetAmountStrategy bas = new BetAmountStrategy(bge);
    bge.setPriorHandBustedFlag();
    Assertions.assertEquals(1000, bas.adjust());
    bge.clearPriorHandBustedFlag();
    Assertions.assertEquals(500, bas.adjust());
    bge.clearAutomaticForTesting();
    bge = new BlackjackGameEngine(new BlackjackConfigDefault() {

      @Override
      public boolean isBetIdeaLetItRideAfterTwoWins() {
        return true;
      }

      @Override
      public boolean isCountVeryPositive() {
        return true;
      }

      @Override
      public int getLargeBetInWholeDollars() {
        return 50;
      }
    });
    bge.setAutomaticOnForTesting();
    bas = new BetAmountStrategy(bge);
    Assertions.assertEquals(5000, bas.adjust());
    bge.incrementStreak();
    Assertions.assertEquals(5000, bas.adjust());
    bge.incrementStreak();
    Assertions.assertEquals(10000, bas.adjust());
    bge.clearAutomaticForTesting();
  }
}
