package com.panopset.tests.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_STAND;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_SURRENDER;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.BlackjackGameEngine;

public class RsTest extends AbstractBlackjackTest {
  
  @Test
  void testSurrenderAllowed() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public boolean isLateSurrenderAllowed() {
        return true;
      }
    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_SURRENDER}, stackHard17vA());
  }

  @Test
  void testSurrenderNotAllowed() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_STAND}, stackHard17vAwithDealerHit());
  }

}
