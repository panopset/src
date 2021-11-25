package com.panopset.tests.blackjackEngine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.panopset.blackjackEngine.BlackjackCard;
import com.panopset.blackjackEngine.Card;
import com.panopset.blackjackEngine.CardDefinition;
import com.panopset.blackjackEngine.Face;
import com.panopset.blackjackEngine.Suit;

public class CardTest {

  @Test
  void testFaces() {
    Assertions.assertEquals("A", Face.ACE.getKey());
    Assertions.assertEquals("2", Face.TWO.getKey());
    Assertions.assertEquals("3", Face.THREE.getKey());
    Assertions.assertEquals("4", Face.FOUR.getKey());
    Assertions.assertEquals("5", Face.FIVE.getKey());
    Assertions.assertEquals("6", Face.SIX.getKey());
    Assertions.assertEquals("7", Face.SEVEN.getKey());
    Assertions.assertEquals("8", Face.EIGHT.getKey());
    Assertions.assertEquals("9", Face.NINE.getKey());
    Assertions.assertEquals("T", Face.TEN.getKey());
    Assertions.assertEquals("J", Face.JACK.getKey());
    Assertions.assertEquals("Q", Face.QUEEN.getKey());
    Assertions.assertEquals("K", Face.KING.getKey());
  }
  
  @Test
  void testSuits() {
    Assertions.assertEquals("H", new BlackjackCard(new Card(CardDefinition.ACE_OF_HEARTS)).getCard().getSuit().getKey());
    Assertions.assertEquals("S", new BlackjackCard(new Card(CardDefinition.ACE_OF_SPADES)).getCard().getSuit().getKey());
    Assertions.assertEquals("D", new BlackjackCard(new Card(CardDefinition.ACE_OF_DIAMONDS)).getCard().getSuit().getKey());
    Assertions.assertEquals("C", new BlackjackCard(new Card(CardDefinition.ACE_OF_CLUBS)).getCard().getSuit().getKey());
    Assertions.assertEquals("H", CardDefinition.ACE_OF_HEARTS.getSuit().getKey());
    Assertions.assertEquals("S", CardDefinition.ACE_OF_SPADES.getSuit().getKey());
    Assertions.assertEquals("D", CardDefinition.ACE_OF_DIAMONDS.getSuit().getKey());
    Assertions.assertEquals("C", CardDefinition.ACE_OF_CLUBS.getSuit().getKey());
    Assertions.assertEquals("H", Suit.HEART.getKey());
    Assertions.assertEquals("S", Suit.SPADE.getKey());
    Assertions.assertEquals("D", Suit.DIAMOND.getKey());
    Assertions.assertEquals("C", Suit.CLUB.getKey());
    Assertions.assertEquals("H", CardDefinition.ACE_OF_HEARTS.getSuit().getKey());
    Assertions.assertNull(CardDefinition.find("foo"));
    Assertions.assertEquals("A", CardDefinition.find("ACE_OF_HEARTS").getFace().getKey());
    Assertions.assertNull(CardDefinition.find(null, null));
    Assertions.assertNull(CardDefinition.find(Face.ACE, null));
    Assertions.assertNull(CardDefinition.find(null, Suit.HEART));
    Assertions.assertEquals("A", CardDefinition.find(Face.ACE, Suit.HEART).getFace().getKey());
  }
  
  @Test
  void testClone() {
    BlackjackCard tenOfHearts = new BlackjackCard(new Card(CardDefinition.TEN_OF_HEARTS), false);
    BlackjackCard clone = new BlackjackCard(tenOfHearts);
    Assertions.assertFalse(tenOfHearts.getCard().isShowing());
    Assertions.assertFalse(clone.getCard().isShowing());
    tenOfHearts.show();
    Assertions.assertTrue(tenOfHearts.getCard().isShowing());
    Assertions.assertTrue(clone.getCard().isShowing());
    BlackjackCard fiveOfDiamonds = new BlackjackCard(new Card(CardDefinition.FIVE_OF_DIAMONDS));
    clone = new BlackjackCard(fiveOfDiamonds);
    Assertions.assertEquals(clone.getCard().getSuit().getKey(), Suit.DIAMOND.getKey());
    Assertions.assertTrue(clone.getCard().isShowing());
  }
}
