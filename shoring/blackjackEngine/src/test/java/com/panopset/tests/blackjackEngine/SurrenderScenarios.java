package com.panopset.tests.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_HIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_SURRENDER;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.BlackjackGameEngine;

public class SurrenderScenarios extends AbstractBlackjackTest {

  @Test
  void testSurrender16vs9() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 6;
      }

      @Override
      public boolean isLateSurrenderAllowed() {
        return true;
      }
    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_SURRENDER}, surrender16vs9());
    assertFalse(bge.getShoe().isTheDeckStacked());
    
    bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 6;
      }
    });
    bge.getShoe().stackTheDeckFromList(surrender16vs9());
    Assertions.assertFalse(bge.ct.isActive());
    Assertions.assertFalse(bge.ct.isDealt());
    bge.exec(CMD_DEAL);
    Assertions.assertTrue(bge.ct.isActive());
    Assertions.assertTrue(bge.ct.isDealt());
    bge.exec(CMD_SURRENDER);
    Assertions.assertEquals("Surrender not allowed in this casino", bge.getDealerMessage());
    bge.exec(CMD_HIT);
    assertFalse(bge.getShoe().isTheDeckStacked());
  }

  @Test
  void testSurrenderFailHitTo16vs10() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 6;
      }

      @Override
      public boolean isLateSurrenderAllowed() {
        return true;
      }
    });

    bge.getShoe().stackTheDeckFromList(surrenderFailHitTo16vs10());
    Assertions.assertFalse(bge.ct.isActive());
    Assertions.assertFalse(bge.ct.isDealt());
    bge.exec(CMD_DEAL);
    Assertions.assertTrue(bge.ct.isActive());
    Assertions.assertTrue(bge.ct.isDealt());
    bge.exec(CMD_HIT);
    bge.exec(CMD_SURRENDER);
    Assertions.assertEquals("Surrender not possible here", bge.getDealerMessage());
    bge.exec(CMD_HIT);
    assertFalse(bge.getShoe().isTheDeckStacked());
    bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 6;
      }
    });

    bge.getShoe().stackTheDeckFromList(surrenderFailHitTo16vs10());
    Assertions.assertFalse(bge.ct.isActive());
    Assertions.assertFalse(bge.ct.isDealt());
    bge.exec(CMD_DEAL);
    Assertions.assertTrue(bge.ct.isActive());
    Assertions.assertTrue(bge.ct.isDealt());
    bge.exec(CMD_HIT);
    bge.exec(CMD_SURRENDER);
    Assertions.assertEquals("Surrender not allowed in this casino", bge.getDealerMessage());
    bge.exec(CMD_HIT);
    assertFalse(bge.getShoe().isTheDeckStacked());
  }

}
