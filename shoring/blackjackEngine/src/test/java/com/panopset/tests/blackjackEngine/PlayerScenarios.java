package com.panopset.tests.blackjackEngine;

import java.util.ArrayList;
import java.util.List;

import com.panopset.blackjackEngine.Card;
import com.panopset.blackjackEngine.CardDefinition;

public interface PlayerScenarios {

  default List<Card> playerBlackjack() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.TEN_OF_SPADES));
    rtn.add(new Card(CardDefinition.FOUR_OF_SPADES));
    rtn.add(new Card(CardDefinition.ACE_OF_HEARTS));
    rtn.add(new Card(CardDefinition.THREE_OF_SPADES));
    return rtn;
  }

  default List<Card> playerBlackjackAnd20Vs21total2players() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.TEN_OF_SPADES));
    rtn.add(new Card(CardDefinition.TEN_OF_HEARTS));
    rtn.add(new Card(CardDefinition.THREE_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.ACE_OF_SPADES));
    rtn.add(new Card(CardDefinition.JACK_OF_HEARTS));
    rtn.add(new Card(CardDefinition.FOUR_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.NINE_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.FIVE_OF_DIAMONDS));
    return rtn;
  }

  default List<Card> dealerBlackjack() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.FOUR_OF_SPADES));
    rtn.add(new Card(CardDefinition.TEN_OF_SPADES));
    rtn.add(new Card(CardDefinition.THREE_OF_SPADES));
    rtn.add(new Card(CardDefinition.ACE_OF_HEARTS));
    return rtn;
  }

  default List<Card> playerAndDealerBlackjack() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.TEN_OF_SPADES));
    rtn.add(new Card(CardDefinition.TEN_OF_HEARTS));
    rtn.add(new Card(CardDefinition.ACE_OF_HEARTS));
    rtn.add(new Card(CardDefinition.ACE_OF_SPADES));
    return rtn;
  }

  default List<Card> dealerSoft17_0() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.EIGHT_OF_SPADES));
    rtn.add(new Card(CardDefinition.SIX_OF_SPADES));
    rtn.add(new Card(CardDefinition.THREE_OF_SPADES));
    rtn.add(new Card(CardDefinition.ACE_OF_HEARTS));
    rtn.add(new Card(CardDefinition.JACK_OF_HEARTS));
    return rtn;
  }

  default List<Card> dealerSoft17_1() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.EIGHT_OF_SPADES));
    rtn.add(new Card(CardDefinition.SIX_OF_SPADES));
    rtn.add(new Card(CardDefinition.THREE_OF_SPADES));
    rtn.add(new Card(CardDefinition.ACE_OF_HEARTS));
    rtn.add(new Card(CardDefinition.TEN_OF_HEARTS));
    rtn.add(new Card(CardDefinition.FOUR_OF_HEARTS));
    return rtn;
  }

  default List<Card> splitNotAllowedTest() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.TWO_OF_SPADES));
    rtn.add(new Card(CardDefinition.SIX_OF_CLUBS));
    rtn.add(new Card(CardDefinition.EIGHT_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.ACE_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.QUEEN_OF_HEARTS));
    return rtn;
  }


  default List<Card> soft18vThit8thenBtest() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.SEVEN_OF_HEARTS));
    rtn.add(new Card(CardDefinition.SEVEN_OF_CLUBS));
    rtn.add(new Card(CardDefinition.ACE_OF_CLUBS));
    rtn.add(new Card(CardDefinition.KING_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.EIGHT_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.KING_OF_CLUBS));
    return rtn;
  }
  
  default List<Card> doubleDown() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.EIGHT_OF_SPADES));
    rtn.add(new Card(CardDefinition.KING_OF_HEARTS));
    rtn.add(new Card(CardDefinition.TWO_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.SIX_OF_HEARTS));
    rtn.add(new Card(CardDefinition.THREE_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.ACE_OF_DIAMONDS));
    return rtn;
  }

  default List<Card> twentyV2hitToSoft18() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.KING_OF_CLUBS));
    rtn.add(new Card(CardDefinition.FIVE_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.KING_OF_HEARTS));
    rtn.add(new Card(CardDefinition.TWO_OF_HEARTS));
    rtn.add(new Card(CardDefinition.ACE_OF_DIAMONDS));
    return rtn;
  }

  // dealer busts if the player doubles.
  default List<Card> eightVersusFive() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.FIVE_OF_CLUBS));
    rtn.add(new Card(CardDefinition.KING_OF_HEARTS));
    rtn.add(new Card(CardDefinition.THREE_OF_CLUBS));
    rtn.add(new Card(CardDefinition.FIVE_OF_HEARTS));
    rtn.add(new Card(CardDefinition.THREE_OF_CLUBS));
    rtn.add(new Card(CardDefinition.ACE_OF_HEARTS));
    rtn.add(new Card(CardDefinition.TEN_OF_HEARTS));
    return rtn;
  }

  default List<Card> resplitAces() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.ACE_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.THREE_OF_CLUBS));
    rtn.add(new Card(CardDefinition.ACE_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.KING_OF_CLUBS));//D:(3)K
    rtn.add(new Card(CardDefinition.THREE_OF_DIAMONDS));//A|A3
    rtn.add(new Card(CardDefinition.ACE_OF_DIAMONDS));//AA|A3
    rtn.add(new Card(CardDefinition.QUEEN_OF_DIAMONDS));//AA|A3Q
    rtn.add(new Card(CardDefinition.SIX_OF_DIAMONDS));//AA|A3Q6
    rtn.add(new Card(CardDefinition.NINE_OF_HEARTS));//A|A9|A3Q6
    rtn.add(new Card(CardDefinition.JACK_OF_HEARTS));//AJ|A9|A3Q6
    rtn.add(new Card(CardDefinition.QUEEN_OF_HEARTS));//D:3QQ
    return rtn;
  }

  default List<Card> resplitAces1() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.ACE_OF_SPADES));
    rtn.add(new Card(CardDefinition.THREE_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.ACE_OF_HEARTS));
    rtn.add(new Card(CardDefinition.KING_OF_HEARTS));
    rtn.add(new Card(CardDefinition.THREE_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.ACE_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.QUEEN_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.SIX_OF_SPADES));
    rtn.add(new Card(CardDefinition.NINE_OF_HEARTS));
    rtn.add(new Card(CardDefinition.JACK_OF_HEARTS));
    rtn.add(new Card(CardDefinition.QUEEN_OF_HEARTS));
    //rtn.add(new Card(CardDefinition.KING_OF_HEARTS));
    return rtn;
  }

  default List<Card> doubleAfterSplit() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.SIX_OF_SPADES));
    rtn.add(new Card(CardDefinition.KING_OF_HEARTS));
    rtn.add(new Card(CardDefinition.SIX_OF_HEARTS));
    rtn.add(new Card(CardDefinition.THREE_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.THREE_OF_HEARTS));
    rtn.add(new Card(CardDefinition.SIX_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.TEN_OF_SPADES));
    rtn.add(new Card(CardDefinition.JACK_OF_HEARTS));
    rtn.add(new Card(CardDefinition.QUEEN_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.KING_OF_DIAMONDS));
    return rtn;
  }

  default List<Card> soft18v2() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.SEVEN_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.KING_OF_HEARTS));
    rtn.add(new Card(CardDefinition.ACE_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.TWO_OF_HEARTS));
    rtn.add(new Card(CardDefinition.JACK_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.QUEEN_OF_DIAMONDS));
    return rtn;
  }

  default List<Card> soft18v3() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.ACE_OF_SPADES));
    rtn.add(new Card(CardDefinition.KING_OF_HEARTS));
    rtn.add(new Card(CardDefinition.SEVEN_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.THREE_OF_HEARTS));
    rtn.add(new Card(CardDefinition.THREE_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.TEN_OF_DIAMONDS));
    return rtn;
  }

  default List<Card> softHitTo18v3() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.ACE_OF_SPADES));
    rtn.add(new Card(CardDefinition.KING_OF_HEARTS));
    rtn.add(new Card(CardDefinition.THREE_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.THREE_OF_HEARTS));
    rtn.add(new Card(CardDefinition.FOUR_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.TEN_OF_DIAMONDS));
    return rtn;
  }
  
  default List<Card> soft18vT() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.ACE_OF_SPADES));
    rtn.add(new Card(CardDefinition.KING_OF_HEARTS));
    rtn.add(new Card(CardDefinition.SEVEN_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.TEN_OF_HEARTS));
    rtn.add(new Card(CardDefinition.THREE_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.TEN_OF_DIAMONDS));
    return rtn;
  }

  default List<Card> lowCountTest() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.ACE_OF_HEARTS));
    rtn.add(new Card(CardDefinition.KING_OF_HEARTS));
    rtn.add(new Card(CardDefinition.TEN_OF_SPADES));
    rtn.add(new Card(CardDefinition.QUEEN_OF_DIAMONDS));
    return rtn;
  }

  default List<Card> bustedHstarSingle() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.TWO_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.KING_OF_HEARTS));
    rtn.add(new Card(CardDefinition.ACE_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.FOUR_OF_HEARTS));
    rtn.add(new Card(CardDefinition.JACK_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.QUEEN_OF_DIAMONDS));
    return rtn;
  }

  default List<Card> stackHard11_v_A() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.SEVEN_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.FOUR_OF_HEARTS));
    rtn.add(new Card(CardDefinition.FOUR_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.ACE_OF_HEARTS));
    rtn.add(new Card(CardDefinition.JACK_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.QUEEN_OF_DIAMONDS));
    return rtn;
  }

  default List<Card> stackHardHitTo11_v_A() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.TWO_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.FOUR_OF_HEARTS));
    rtn.add(new Card(CardDefinition.FOUR_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.ACE_OF_HEARTS));
    rtn.add(new Card(CardDefinition.FIVE_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.QUEEN_OF_DIAMONDS));
    return rtn;
  }
  
  default List<Card> stackSplit6_v_2() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.SIX_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.TEN_OF_HEARTS));
    rtn.add(new Card(CardDefinition.SIX_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.TWO_OF_HEARTS));
    rtn.add(new Card(CardDefinition.TEN_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.JACK_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.QUEEN_OF_HEARTS));
    return rtn;
  }
  
  default List<Card> stack12comp10_2_v_4() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.TEN_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.TEN_OF_HEARTS));
    rtn.add(new Card(CardDefinition.TWO_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.FOUR_OF_HEARTS));
    rtn.add(new Card(CardDefinition.EIGHT_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.JACK_OF_HEARTS));
    return rtn;
  }

  
  default List<Card> stack12comp8_4_v_4() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.EIGHT_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.TEN_OF_HEARTS));
    rtn.add(new Card(CardDefinition.FOUR_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.FOUR_OF_HEARTS));
    rtn.add(new Card(CardDefinition.EIGHT_OF_HEARTS));
    return rtn;
  }
  
  default List<Card> stackHard17vA() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.SEVEN_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.FOUR_OF_HEARTS));
    rtn.add(new Card(CardDefinition.TEN_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.ACE_OF_HEARTS));
    return rtn;
  }
  
  default List<Card> stackHard16vT() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.SIX_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.FOUR_OF_HEARTS));
    rtn.add(new Card(CardDefinition.TEN_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.TEN_OF_HEARTS));
    rtn.add(new Card(CardDefinition.QUEEN_OF_DIAMONDS));
    return rtn;
  }

  default List<Card> stackHard16_with_4_vT() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.FOUR_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.FOUR_OF_HEARTS));
    rtn.add(new Card(CardDefinition.TWO_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.TEN_OF_HEARTS));
    rtn.add(new Card(CardDefinition.TEN_OF_DIAMONDS));
    return rtn;
  }
  
  default List<Card> stackHard16_with_5_vT() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.TWO_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.FOUR_OF_HEARTS));
    rtn.add(new Card(CardDefinition.FIVE_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.TEN_OF_HEARTS));
    rtn.add(new Card(CardDefinition.NINE_OF_DIAMONDS));
    return rtn;
  }
  
  default List<Card> stackHard17vAwithDealerHit() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.SEVEN_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.FOUR_OF_HEARTS));
    rtn.add(new Card(CardDefinition.TEN_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.ACE_OF_HEARTS));
    rtn.add(new Card(CardDefinition.JACK_OF_HEARTS));
    rtn.add(new Card(CardDefinition.QUEEN_OF_HEARTS));
    return rtn;
  }

  default List<Card> stack12comp3_2_7_v_4() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.THREE_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.TEN_OF_HEARTS));
    rtn.add(new Card(CardDefinition.TWO_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.FOUR_OF_HEARTS));
    rtn.add(new Card(CardDefinition.SEVEN_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.EIGHT_OF_HEARTS));
    return rtn;
  }

  default List<Card> stackSplit6_v_2_doubleDeck() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.SIX_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.TEN_OF_HEARTS));
    rtn.add(new Card(CardDefinition.SIX_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.TWO_OF_HEARTS));
    rtn.add(new Card(CardDefinition.TEN_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.JACK_OF_DIAMONDS));
    return rtn;
  }

  default List<Card> soft13vs4() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.TWO_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.KING_OF_HEARTS));
    rtn.add(new Card(CardDefinition.ACE_OF_CLUBS));
    rtn.add(new Card(CardDefinition.FOUR_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.JACK_OF_DIAMONDS));
    return rtn;
  }

  default List<Card> soft13vs4afterSplitAces() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.ACE_OF_SPADES));
    rtn.add(new Card(CardDefinition.KING_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.ACE_OF_CLUBS));
    rtn.add(new Card(CardDefinition.FOUR_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.TWO_OF_SPADES));
    rtn.add(new Card(CardDefinition.JACK_OF_SPADES));
    rtn.add(new Card(CardDefinition.QUEEN_OF_CLUBS));
    rtn.add(new Card(CardDefinition.QUEEN_OF_SPADES));
    return rtn;
  }

  default List<Card> surrender16vs9() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.TEN_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.KING_OF_HEARTS));
    rtn.add(new Card(CardDefinition.SIX_OF_CLUBS));
    rtn.add(new Card(CardDefinition.NINE_OF_DIAMONDS));
    return rtn;
  }

  default List<Card> surrenderFailHitTo16vs10() {
    List<Card> rtn = new ArrayList<Card>();
    rtn.add(new Card(CardDefinition.TWO_OF_HEARTS));
    rtn.add(new Card(CardDefinition.KING_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.THREE_OF_HEARTS));
    rtn.add(new Card(CardDefinition.KING_OF_DIAMONDS));
    rtn.add(new Card(CardDefinition.FIVE_OF_HEARTS));
    rtn.add(new Card(CardDefinition.SIX_OF_HEARTS));
    return rtn;
  }
  
}
