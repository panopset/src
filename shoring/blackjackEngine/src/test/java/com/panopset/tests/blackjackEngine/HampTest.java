package com.panopset.tests.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_HIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_SPLIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_STAND;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.BlackjackGameEngine;

public class HampTest extends AbstractBlackjackTest {

  @Test
  void testSingleDeck() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 1;
      }
    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_SPLIT, CMD_STAND, CMD_STAND}, stackSplit6_v_2());
  }

  @Test
  void testDoubleDeck() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 2;
      }
    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_HIT}, stackSplit6_v_2_doubleDeck());
  }

}
