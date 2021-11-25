package com.panopset.tests.blackjackEngine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.BlackjackCard;
import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.BlackjackGameEngine;
import com.panopset.blackjackEngine.CardDefinition;
import com.panopset.blackjackEngine.Player;

public class PlayerTest {
  
  @Test
  void test() {
    Player player = new Player(new BlackjackGameEngine(BlackjackConfigDefault.INSTANCE));
    player.getActiveHand().dealCard(new BlackjackCard(CardDefinition.ACE_OF_CLUBS));
    player.getActiveHand().dealCard(new BlackjackCard(CardDefinition.TEN_OF_SPADES));
    Assertions.assertEquals("Player #0: cards:[ACE_OF_CLUBS, TEN_OF_SPADES] value: 21 ", player.toString());
    Assertions.assertEquals(1, player.getHands().size());
  }
}
