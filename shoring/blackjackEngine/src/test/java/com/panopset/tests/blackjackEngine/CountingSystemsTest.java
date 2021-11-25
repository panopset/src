package com.panopset.tests.blackjackEngine;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.BlackjackCard;
import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.Card;
import com.panopset.blackjackEngine.CardCounterImpl;
import com.panopset.blackjackEngine.CardDefinition;
import com.panopset.blackjackEngine.CountingSystem;

public class CountingSystemsTest {

  @Test
  void test() throws Exception {
    CardCounterImpl.updateByIndex(3);
    testConfig();
    CardCounterImpl.setConfig(BlackjackConfigDefault.INSTANCE);
    CardCounterImpl.updateByIndex(3);
    testConfig();
  }
  
  private void testConfig() throws Exception {
    CountingSystem dft = CardCounterImpl.getSystem();
    CardCounterImpl.resetCount();
    assertEquals(8, CardCounterImpl.getKeyNames().size());    
    assertEquals(0, CardCounterImpl.getCount());
    new BlackjackCard(new Card(CardDefinition.FIVE_OF_CLUBS));
    assertEquals(1, CardCounterImpl.getCount());
    new BlackjackCard( new Card(CardDefinition.FOUR_OF_HEARTS));
    assertEquals(2, CardCounterImpl.getCount());
    new BlackjackCard(new Card(CardDefinition.EIGHT_OF_HEARTS));
    assertEquals(2, CardCounterImpl.getCount());
    new BlackjackCard(new Card(CardDefinition.ACE_OF_HEARTS));
    assertEquals(1, CardCounterImpl.getCount());
    new BlackjackCard(new Card(CardDefinition.TEN_OF_SPADES));
    assertEquals(0, CardCounterImpl.getCount());
    new BlackjackCard(new Card(CardDefinition.THREE_OF_SPADES));
    assertEquals(1, CardCounterImpl.getCount());
    new BlackjackCard(new Card(CardDefinition.TWO_OF_HEARTS));
    assertEquals(2, CardCounterImpl.getCount());
    CardCounterImpl.updateByIndex(0);
    assertEquals("None", CardCounterImpl.getSystem().getName());
    CardCounterImpl.updateByIndex(1);
    assertEquals("Wizard Ace/5", CardCounterImpl.getSystem().getName());
    CardCounterImpl.updateByIndex(2);
    CountingSystem ko = CardCounterImpl.getSystem();
    assertEquals("KO", ko.getName());
    CardCounterImpl.updateByIndex(3);
    CountingSystem hiLo = CardCounterImpl.getSystem();
    assertEquals(hiLo.hashCode(), dft.hashCode());
    assertEquals(hiLo, dft);
    assertNotEquals(hiLo, ko);
    assertEquals("Hi-Lo", hiLo.getName());
    CardCounterImpl.updateByIndex(4);
    assertEquals("Hi-Opt I", CardCounterImpl.getSystem().getName());
    CardCounterImpl.updateByIndex(5);
    assertEquals("Hi-Opt II", CardCounterImpl.getSystem().getName());
    CardCounterImpl.updateByIndex(6);
    assertEquals("Zen Count", CardCounterImpl.getSystem().getName());
    CardCounterImpl.updateByIndex(7);
    assertEquals("Omega II", CardCounterImpl.getSystem().getName());
    assertEquals(7, CardCounterImpl.INSTANCE.getCountingSystems().getKeyNamePosition("Omega II"));
    assertEquals(3, CardCounterImpl.INSTANCE.getCountingSystems().getKeyNamePosition("foo"));
    assertEquals("Hi-Lo", CardCounterImpl.INSTANCE.getCountingSystems().findSelectionKey(-1));
  }

}
