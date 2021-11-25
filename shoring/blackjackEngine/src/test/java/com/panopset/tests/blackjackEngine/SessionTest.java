package com.panopset.tests.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_SPLIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.BlackjackGameEngine;

public class SessionTest implements SessionData {

  BlackjackGameEngine bge = new BlackjackGameEngine();

  @Test
  void testBlackjack() {
    bge.getShoe().stackTheDeckFromArray(BLACKJACK_WWCD);
    bge.shuffle();
    Assertions.assertEquals("Shuffled and stacked deck for debugging", bge.getDealerMessage());
    doRecommendedAction(CMD_DEAL);
    assertFalse(bge.getCycle().isActive());
    assertEquals(750L, bge.getTotalValue());

    bge.getShoe().stackTheDeckFromArray(SPLITACES_DLRBLACKJACK);
    doRecommendedAction(CMD_DEAL);
    assertFalse(bge.getCycle().isActive());
    assertEquals(250L, bge.getTotalValue());
    bge.getShoe().stackTheDeckFromArray(SPLITACES_DLRHIT_TO_21);
    doRecommendedAction(CMD_DEAL);
    assertTrue(bge.getCycle().isActive());
    assertEquals("p", doRecommendedAction(CMD_SPLIT));
    assertFalse(bge.getCycle().isActive());
    assertEquals(250L, bge.getTotalValue());
    assertEquals("J",
        bge.getCycle().getActiveSituation().getDealerUpCard().getCard().getFace().getKey());
    String stackRawData = "six of clubs\njack of spades\nnine of hearts\nace of diamonds";
    bge.getShoe().stackTheDeckFromEOLseparatedText(stackRawData);
    doRecommendedAction(CMD_DEAL);
    assertFalse(bge.getCycle().isActive());
  }

  private String doRecommendedAction(String action) {
    String ra = bge.getCycle().getRecommendedAction(); 
    assertEquals(ra, action);
    bge.exec(action);
    return ra;
  }
}
