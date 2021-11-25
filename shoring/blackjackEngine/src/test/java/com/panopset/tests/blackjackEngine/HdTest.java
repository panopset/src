package com.panopset.tests.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DOUBLE;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_HIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_STAND;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.BlackjackGameEngine;

public class HdTest extends AbstractBlackjackTest {

  @Test
  void testSingleDeck() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 1;
      }
    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_DOUBLE}, stackHard11_v_A());
  }

  @Test
  void testDoubleDeck() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 2;
      }
    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_DOUBLE}, stackHard11_v_A());
  }

  @Test
  void testTripleDeck() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 3;
      }
    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_DOUBLE}, stackHard11_v_A());
  }

  @Test
  void testDoubleDeckDealerStandsSoft17() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 2;
      }

      @Override
      public boolean isDealerHitSoft17() {
        return false;
      }

    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_DOUBLE}, stackHard11_v_A());
  }

  @Test
  void testTripleDeckDealerStandsSoft17() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 3;
      }

      @Override
      public boolean isDealerHitSoft17() {
        return false;
      }

    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_HIT, CMD_STAND}, stackHard11_v_A());
  }
  
  @Test
  void testSingleDeckHardHitTo11() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 1;
      }
    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_HIT, CMD_HIT, CMD_STAND}, stackHardHitTo11_v_A());
  }

}
