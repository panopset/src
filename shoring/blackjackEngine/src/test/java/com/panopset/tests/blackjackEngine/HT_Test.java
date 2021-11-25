package com.panopset.tests.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_HIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_STAND;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.BlackjackGameEngine;

public class HT_Test extends AbstractBlackjackTest {

  @Test
  void testFlop12() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {});
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_HIT, CMD_STAND},
        stack12comp10_2_v_4());
    bge = new BlackjackGameEngine(new BlackjackConfigDefault() {});
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_STAND}, stack12comp8_4_v_4());
  }

  @Test
  void testHitTo12() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {});
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_HIT, CMD_STAND}, stack12comp3_2_7_v_4());
  }

}
