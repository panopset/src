package com.panopset.tests.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_AUTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.panopset.compat.Stringop;
import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.BlackjackGameEngine;
import com.panopset.blackjackEngine.CardCounterImpl;
import com.panopset.blackjackEngine.CycleSnapshot;

public class AutoTest {
  
  private AutoTestVerifier atv = new AutoTestVerifier();

  @Test
  void test() throws Exception {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 6;
      }
    });
    testAutoRun(bge);
  }

  @Test
  void testFromVariations() throws Exception {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 6;
      }

      @Override
      public boolean isBasicStrategyVariationsOnly() {
        return true;
      }
    });
    bge.exec(CMD_AUTO);
    Assertions.assertEquals(
        "Please turn off \"Variations\" in the Configuration->Rules tab before running automatically.",
        bge.getDealerMessage());
  }

  @Test
  void targetStakeTest() throws Exception {
    final BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 1;
      }

      @Override
      public int getTargetStakeInWholeDollars() {
        return 1000;
      }

      @Override
      public boolean isCountVeryPositive() {
        return CardCounterImpl.getCount() > 4;
      }

      @Override
      public boolean isCountVeryNegative() {
        return CardCounterImpl.getCount() < 4;
      }

      @Override
      public int getLargeBetInWholeDollars() {
        return 200;
      }
    });
    bge.exec(CMD_AUTO);
    synchronized (bge) {
      CycleSnapshot paintedSnapshot = null;
      while (bge.isAutomatic() && bge.isActive()) {
        bge.wait(200);
        CycleSnapshot cycleSnapshot = bge.getCurrentSnapshot();
        if (cycleSnapshot != paintedSnapshot) {
          paintedSnapshot = cycleSnapshot;
          System.out.println(String.format("Stake: %s, Chips: %s Hands: %d ",
              Stringop.getDollarString(cycleSnapshot.getBankroll().getStakeIncludingHands()),
              Stringop.getDollarString(cycleSnapshot.getBankroll().getChips()), cycleSnapshot.getMetrics().getHandCount()));
          Assertions.assertEquals(500, cycleSnapshot.getNextBet());
        }
      }
    }
    bge.getBankroll().settle();
    Assertions.assertTrue(bge.getBankroll().getChips() > 99999);
    Assertions.assertTrue(bge.getBankroll().getHandWagers().isEmpty());
  }

  private void testAutoRun(BlackjackGameEngine bge) throws Exception {
    atv.verifyInitialState(bge.getMetrics().getHandCount());
    bge.exec(CMD_AUTO);
    synchronized (bge) {
      bge.wait(3000);
    }
    bge.exec(CMD_AUTO);
    synchronized (bge) {
      bge.wait(100);
    }
    atv.priorHandCount = bge.getMetrics().getHandCount();
    synchronized (bge) {
      bge.wait(100);
    }
    atv.verifyPostAutoRun(bge.getMetrics().getHandCount());
  }
}
