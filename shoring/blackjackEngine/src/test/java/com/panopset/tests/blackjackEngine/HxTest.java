package com.panopset.tests.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DOUBLE;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_HIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_STAND;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.BlackjackGameEngine;

public class HxTest extends AbstractBlackjackTest {

  @Test
  void testSingleDeck() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 1;
      }
    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_DOUBLE}, soft18v2());
    performDeviantActions(bge, new String[] {CMD_DEAL, CMD_HIT, CMD_HIT}, soft18v2());
    Assertions.assertEquals("Busted",
        bge.getCycle().getPlayers().get(0).getHands().get(0).getMessage());
  }

  @Test
  void test() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 6;
      }
    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_HIT, CMD_STAND}, soft18v2());
    performDeviantActions(bge, new String[] {CMD_DEAL, CMD_HIT, CMD_HIT}, soft18v2());
    Assertions.assertEquals("Busted",
        bge.getCycle().getPlayers().get(0).getHands().get(0).getMessage());
  }
}
