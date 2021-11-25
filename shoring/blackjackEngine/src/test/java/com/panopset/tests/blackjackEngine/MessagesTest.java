package com.panopset.tests.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DOUBLE;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_HIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_RESET;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_SPLIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_STAND;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.BlackjackGameEngine;
import com.panopset.blackjackEngine.HandPlayer;

public class MessagesTest extends AbstractBlackjackTest {
  static BlackjackGameEngine bge;
  
  @BeforeAll
  public static void beforeAll() {
    bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 6;
      }
    });
  }

  @Test
  void test() {
    bge.exec(CMD_RESET);
    Assertions.assertEquals("Reset", bge.getDealerMessage());
    bge.exec(CMD_HIT);
    Assertions.assertEquals("Please select L=Deal", bge.getDealerMessage());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL}, dealerBlackjack());
    bge.exec(CMD_HIT);
    Assertions.assertEquals("Please select L=Deal", bge.getDealerMessage());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_HIT, CMD_HIT, CMD_STAND},
        eightVersusFive());
    bge.exec(CMD_HIT);
    Assertions.assertEquals("Please select L=Deal", bge.getDealerMessage());
    bge.exec(CMD_DOUBLE);
    Assertions.assertEquals("Please select L=Deal", bge.getDealerMessage());
    bge.getShoe().stackTheDeckFromList(eightVersusFive());
    ScenarioVerifier.verifyAndDoRecommendedAction(bge, CMD_DEAL, 0);
    ScenarioVerifier.verifyAndDoRecommendedAction(bge, CMD_HIT, 1);
    Assertions.assertEquals(0, bge.getMetrics().getMistakeCount());
    Assertions.assertFalse(bge.getCycle().getPlayers().get(0).getHands().get(0).isDoubleDowned());
    bge.exec(CMD_DEAL);
    Assertions.assertEquals("Hand is still active.", bge.getDealerMessage());
    bge.exec(CMD_DOUBLE);
    Assertions.assertEquals("Double not possible here", bge.getDealerMessage());
    bge.exec(CMD_STAND);
    Assertions.assertEquals("Won",
        bge.getCycle().getPlayers().get(0).getHands().get(0).getMessage());
    Assertions.assertEquals(1, bge.getMetrics().getMistakeCount());
    bge.getShoe().stackTheDeckFromList(playerBlackjack());
    bge.exec(CMD_DEAL);
    Assertions.assertEquals("Blackjack",
        bge.getCycle().getPlayers().get(0).getHands().get(0).getMessage());
    bge.getShoe().stackTheDeckFromList(eightVersusFive());
    Assertions.assertEquals(1, bge.getMetrics().getMistakeCount());
    Assertions.assertEquals(1, bge.getMetrics().getHandsSinceLastMistake());
    bge.exec(CMD_DEAL);
    bge.exec(CMD_STAND);
    Assertions.assertEquals(2, bge.getMetrics().getMistakeCount());
    Assertions.assertEquals(0, bge.getMetrics().getHandsSinceLastMistake());
    Assertions.assertEquals(3, bge.getMetrics().getHandsSinceLastMistakeRecord());
    bge.getShoe().stackTheDeckFromList(eightVersusFive());
    bge.exec(CMD_DEAL);
    bge.exec(CMD_HIT);
    Assertions.assertEquals(2, bge.getMetrics().getMistakeCount());
    bge.exec(CMD_STAND);
    Assertions.assertEquals(3, bge.getMetrics().getMistakeCount());
  }
  
  @Test
  void testHandPlayer() {
    HandPlayer hp = new HandPlayer(bge);
    Assertions.assertEquals("Please select deal (L)",  hp.getMessage());
  }
  
  @Test
  void testDoubleDown() {
    bge.getShoe().stackTheDeckFromList(doubleDown());
    Assertions.assertTrue(bge.getShoe().isTheDeckStacked());
    Assertions.assertFalse(bge.getCycle().getPlayers().get(0).getHands().get(0).isInitialDeal());
    bge.exec(CMD_DEAL);
    Assertions.assertTrue(bge.getCycle().getActivePlayer().getActiveHand().isInitialDeal());
    Assertions.assertTrue(bge.getCycle().getActivePlayer().getActiveHand().canDouble());
    Assertions.assertTrue(bge.getCycle().getDealer().getUpCard().getCard().isShowing());
    Assertions.assertFalse(bge.getCycle().getDealer().getDownCard().getCard().isShowing());
    bge.exec(CMD_DOUBLE);
    Assertions.assertTrue(bge.getCycle().getDealer().getDownCard().getCard().isShowing());
  }
  
  @Test
  void testNoSplitAfterHit() {
    bge.getShoe().stackTheDeckFromList(eightVersusFive());
    bge.exec(CMD_DEAL);
    bge.exec(CMD_HIT);
    bge.exec(CMD_SPLIT);
    Assertions.assertEquals("Can't split a hit hand", bge.getDealerMessage());
  }

}
