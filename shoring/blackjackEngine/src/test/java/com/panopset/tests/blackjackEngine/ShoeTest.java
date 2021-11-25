package com.panopset.tests.blackjackEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.Card;
import com.panopset.blackjackEngine.DeckPile;
import com.panopset.blackjackEngine.Shoe;

public class ShoeTest {

  @Test
  void testShuffle() {
    DeckPile.reset();
    Shoe shoe = new Shoe(5, 1);
    assertFalse(shoe.isTheDeckStacked());
    assertEquals(5, shoe.getNumberOfDecks());
    Card card = shoe.deal();
    assertTrue(card.isBlue());
    assertNotNull(card);
    assertTrue(shoe.isBlue());
    assertTrue(shoe.deal().isBlue());
    shoe.shuffle();
    assertFalse(shoe.isBlue());
    assertFalse(shoe.deal().isBlue());
  }
  
  @Test
  void testDeal() {
    DeckPile.reset();
    Shoe shoe = new Shoe(1, 1);
    assertFalse(shoe.isTheDeckStacked());
    assertTrue(shoe.isBlue());
    shoe.deal();
    for (int i = 0; i < 53; i++) {
      shoe.deal();
    }
    assertFalse(shoe.isBlue());
  }
}
