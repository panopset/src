package com.panopset.tests.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_RESET;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_SPLIT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.BlackjackGameEngine;
import com.panopset.blackjackEngine.CardDefinition;

public class SplitNotAllowedTest extends AbstractBlackjackTest {

  @Test
  void test() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault());
    bge.exec(CMD_RESET);
    bge.getShoe().stackTheDeckFromList(splitNotAllowedTest());
    bge.exec(CMD_DEAL);
    bge.exec(CMD_SPLIT);
    
    verifyPlayerCards(new CardDefinition[] {CardDefinition.TWO_OF_SPADES, CardDefinition.EIGHT_OF_DIAMONDS}, 
        bge.getCurrentSnapshot().getPlayers().get(0).getHands().get(0).getCards());
    
    Assertions.assertEquals(CardDefinition.TWO_OF_SPADES.getFace(), bge.getCurrentSnapshot().getPlayers().get(0).getActiveHand().getCards().get(0).getCard().getFace());
    Assertions.assertEquals(CardDefinition.TWO_OF_SPADES.getSuit(), bge.getCurrentSnapshot().getPlayers().get(0).getActiveHand().getCards().get(0).getCard().getSuit());
    Assertions.assertEquals(CardDefinition.EIGHT_OF_DIAMONDS.getFace(), bge.getCurrentSnapshot().getPlayers().get(0).getActiveHand().getCards().get(1).getCard().getFace());
    Assertions.assertEquals(CardDefinition.EIGHT_OF_DIAMONDS.getSuit(), bge.getCurrentSnapshot().getPlayers().get(0).getActiveHand().getCards().get(1).getCard().getSuit());
    Assertions.assertEquals("Can't split cards that don't have the same face",
        bge.getDealerMessage());
  }

}
