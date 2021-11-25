package com.panopset.tests.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_AUTO;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DOUBLE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.BlackjackGameEngine;

public class FastDealTest extends AbstractBlackjackTest {

  @Test
  void test() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public boolean isFastDeal() {
        return true;
      }
    });
    verifyRecommendedActionsFastDeal(bge, new String[] {CMD_DEAL, CMD_DOUBLE}, dealerBlackjack(),
        dealerSoft17_0(), doubleDown());
  }

  @Test
  void testMistake() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public boolean isFastDeal() {
        return true;
      }
    });
    bge.getShoe().stackTheDeckFromList(stackSplit6_v_2());
    bge.exec(CMD_DEAL);
    bge.exec(CMD_DOUBLE);
    Assertions.assertEquals(1, bge.getMetrics().getMistakeCount());
  }


  @Test
  void testAutomatic() throws Exception {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public boolean isFastDeal() {
        return true;
      }
    });
    bge.exec(CMD_AUTO);
    synchronized (bge) {
      bge.wait(100);
    }
    bge.exec(CMD_AUTO);
    Assertions.assertEquals(0, bge.getMetrics().getMistakeCount());
  }
}
