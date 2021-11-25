package com.panopset.tests.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_AUTO;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_SHUFFLE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.BlackjackGameEngine;

public class AutoZombieTest {
  
  private AutoTestVerifier atv = new AutoTestVerifier();

  @Test
  void testZombieStop() throws Exception {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 6;
      }
    });
    atv.verifyInitialState(bge.getMetrics().getHandCount());
    bge.exec(CMD_SHUFFLE);
    bge.exec(CMD_AUTO);
    synchronized (bge) {
      bge.wait(1000);
    }
    bge.stop();
    synchronized (bge) {
      bge.wait(100);
    }
    atv.priorHandCount = bge.getMetrics().getHandCount();
    synchronized (bge) {
      bge.wait(100);
    }
    atv.verifyPostAutoRun(bge.getMetrics().getHandCount());
    bge.resume();
    Assertions.assertTrue(bge.isActive());
  }

}
