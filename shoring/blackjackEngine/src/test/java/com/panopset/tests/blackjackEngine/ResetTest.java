package com.panopset.tests.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_RESET;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.BlackjackGameEngine;
import com.panopset.blackjackEngine.CardDefinition;

public class ResetTest extends AbstractBlackjackTest {

  @Test
  void test() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 6;
      }
    });
    Assertions.assertEquals(0, bge.getBankroll().getStakeIncludingHands());
    Assertions.assertEquals(30000, bge.getBankroll().getChips());
    Assertions.assertFalse(bge.getCycle().isActive());
    bge.getShoe().stackTheDeckFromList(eightVersusFive());
    bge.exec(CMD_DEAL);
    verifyPlayerCards(
        new CardDefinition[] {CardDefinition.FIVE_OF_CLUBS, CardDefinition.THREE_OF_CLUBS},
        bge.getCurrentSnapshot().getPlayers().get(0).getHands().get(0).getCards());
    Assertions.assertTrue(bge.getCycle().isActive());
    Assertions.assertEquals(0, bge.getBankroll().getStakeIncludingHands());
    Assertions.assertEquals(500, bge.getBankroll().getLiveValue());
    Assertions.assertEquals(29500, bge.getBankroll().getChips());
    bge.exec(CMD_RESET);
    Assertions.assertEquals(0, bge.getBankroll().getStakeIncludingHands());
    Assertions.assertEquals(500, bge.getBankroll().getLiveValue());
    Assertions.assertEquals(29500, bge.getBankroll().getChips());
    Assertions.assertFalse(bge.getCycle().isActive());
    bge.getShoe().stackTheDeckFromList(dealerBlackjack());
    bge.exec(CMD_DEAL);
    Assertions.assertEquals(-500, bge.getBankroll().getStakeIncludingHands());
    Assertions.assertEquals(29500, bge.getBankroll().getChips());
    bge.exec(CMD_RESET);
    Assertions.assertFalse(bge.getCycle().isActive());
    Assertions.assertEquals(0, bge.getBankroll().getStakeIncludingHands());
    Assertions.assertEquals(29500, bge.getBankroll().getChips());
    bge.exec(CMD_RESET);
    bge.exec(CMD_DEAL);
    if (bge.getCycle().getDealer().getValue() == 21
        || bge.getCycle().getPlayers().get(0).getHands().get(0).isNatural21()) {
      Assertions.assertFalse(bge.getCycle().isActive());
    } else {
      Assertions.assertTrue(bge.getCycle().isActive());
    }
  }
}
