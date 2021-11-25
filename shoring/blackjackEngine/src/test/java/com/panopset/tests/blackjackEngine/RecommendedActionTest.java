package com.panopset.tests.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DOUBLE;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_HIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_SPLIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_STAND;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.BlackjackGameEngine;

public class RecommendedActionTest extends SessionTest implements PlayerScenarios {

  @Test
  void test() {
    String ra = CMD_HIT;
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 1;
      }
    });
    bge.getShoe().stackTheDeckFromList(soft13vs4());
    bge.exec(CMD_DEAL);
    ra = bge.getCycle().getRecommendedAction();
    Assertions.assertEquals(CMD_DOUBLE, ra);
    bge.exec(CMD_SPLIT);
    Assertions.assertEquals("Can't split cards that don't have the same face",
        bge.getDealerMessage());
    bge.exec(CMD_STAND);
    Assertions.assertEquals("soft 2  3  4  5  6  7  8  9  T  A ", bge.getMistakeHeader());
    Assertions.assertEquals(" 13  H  H  H* Dh Dh H  H  H  H  H ", bge.getMistakeMessage());
    Assertions.assertEquals(CMD_STAND, bge.getCurrentSnapshot().getAction());
  }
}
