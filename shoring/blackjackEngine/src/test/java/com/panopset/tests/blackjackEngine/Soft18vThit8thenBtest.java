package com.panopset.tests.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_HIT;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.BlackjackGameEngine;

public class Soft18vThit8thenBtest extends AbstractBlackjackTest {
  
  @Test
  void test() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_HIT, CMD_HIT}, soft18vThit8thenBtest());
  }
}
