package com.panopset.tests.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DOUBLE;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_HIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_SPLIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_STAND;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.BlackjackCard;
import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.BlackjackConfiguration;
import com.panopset.blackjackEngine.BlackjackGameEngine;
import com.panopset.blackjackEngine.CardDefinition;
import com.panopset.blackjackEngine.HandPlayer;
import com.panopset.blackjackEngine.Situation;
import com.panopset.blackjackEngine.Strategy;

public class StrategyLinesTest extends AbstractBlackjackTest {

  @Test
  void test() {
    BlackjackConfiguration config = BlackjackConfigDefault.INSTANCE;
    Strategy s = new Strategy();
    HandPlayer handPlayer = new HandPlayer(new BlackjackGameEngine(config));
    handPlayer.getWager().setInitialBet(500);
    Assertions.assertEquals(handPlayer.getWager().getLiveValue(), 500);
    handPlayer.dealCard(new BlackjackCard(CardDefinition.TEN_OF_DIAMONDS));
    handPlayer.dealCard(new BlackjackCard(CardDefinition.SIX_OF_CLUBS));
    String ra = s.getRecommendation(
        new Situation(new BlackjackCard(CardDefinition.ACE_OF_CLUBS), handPlayer));
    Assertions.assertEquals(CMD_HIT, ra);
    Assertions.assertEquals("soft 2  3  4  5  6  7  8  9  T  A ",
        s.getHeaderFor(Strategy.StratCat.SOFT));
    Assertions.assertEquals("split2  3  4  5  6  7  8  9  T  A ",
        s.getHeaderFor(Strategy.StratCat.SPLIT));
    Assertions.assertEquals("hard 2  3  4  5  6  7  8  9  T  A ",
        s.getHeaderFor(Strategy.StratCat.HARD));
  }

  @Test
  void testSoft13v4() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 6;
      }
    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_HIT, CMD_STAND}, soft13vs4());
    bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 1;
      }
    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_DOUBLE}, soft13vs4());
    bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 1;
      }
    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_DOUBLE}, soft13vs4());
  }
  
  @Test
  void testSoft13vs4afterSplitAces() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 6;
      }
    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_SPLIT}, soft13vs4afterSplitAces());
  }
}
