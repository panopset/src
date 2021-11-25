package com.panopset.tests.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DECREASE;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DOUBLE;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_HIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_INCREASE;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_SPLIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_STAND;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.panopset.compat.Stringop;
import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.BlackjackGameEngine;

public class ScenarioTest extends AbstractBlackjackTest {

  @Test
  void testWithSixDecks() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 6;
      }

      @Override
      public boolean isResplitAcesAllowed() {
        return true;
      }
    });
    Assertions.assertEquals("", bge.getMistakeMessage());
    Assertions.assertEquals("", bge.getMistakeHeader());
    Assertions.assertEquals("", bge.getDealerMessage());
    Assertions.assertFalse(bge.ct.isActive());
    Assertions.assertEquals("Dealer: Player 0: Player #0: cards:[] value: 0 " + Stringop.LINE_SEPARATOR + 
        " Bankroll:Total Chips: 29500 stake: 0 tray: 29500 bankroll: -500", bge.getCycle().toString());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_DOUBLE}, doubleDown());
    Assertions.assertTrue(bge.getCycle().getPlayers().get(0).getHands().get(0).isDoubleDowned());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_HIT, CMD_HIT, CMD_STAND},
        eightVersusFive());
    verifyRecommendedActions(bge,
        new String[] {CMD_DEAL, CMD_SPLIT, CMD_HIT, CMD_HIT, CMD_STAND, CMD_SPLIT, CMD_STAND, CMD_STAND},
        resplitAces());
    verifyRecommendedActions(bge,
        new String[] {CMD_DEAL, CMD_SPLIT, CMD_DOUBLE, CMD_SPLIT, CMD_STAND, CMD_STAND},
        doubleAfterSplit());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_HIT, CMD_STAND}, soft18vT());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL}, dealerBlackjack());
    Assertions.assertFalse(bge.getShoe().isTheDeckStacked());
  }

  @Test
  void testWithSixDecksResplitAcesAllowed() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 6;
      }

      @Override
      public boolean isResplitAcesAllowed() {
        return true;
      }
    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_SPLIT, CMD_HIT, CMD_HIT, CMD_STAND,
        CMD_SPLIT, CMD_STAND, CMD_STAND}, resplitAces());
  }

  @Test
  void testWithSingleDeck() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 1;
      }
    });
    Assertions.assertEquals(30000, bge.getBankroll().getChips());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_DOUBLE}, doubleDown());
    Assertions.assertEquals(29000, bge.getBankroll().getChips());
    bge.setNextBet(0);
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_DOUBLE}, eightVersusFive());
    Assertions.assertEquals(28000, bge.getBankroll().getChips());
    Assertions.assertFalse(bge.getCycle().isActive());
    bge.getBankroll().settle();
    Assertions.assertEquals(30000, bge.getBankroll().getChips());
    bge.setNextBet(2000);
    verifyRecommendedActions(bge,
        new String[] {CMD_DEAL, CMD_SPLIT},
        resplitAces());
    Assertions.assertEquals(26000, bge.getBankroll().getChips());
    Assertions.assertFalse(bge.getCycle().isActive());
    bge.getBankroll().settle();
    Assertions.assertEquals(34000, bge.getBankroll().getChips());
    verifyRecommendedActions(bge,
        new String[] {CMD_DEAL, CMD_SPLIT, CMD_DOUBLE, CMD_SPLIT, CMD_STAND, CMD_STAND},
        doubleAfterSplit());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_HIT, CMD_STAND}, soft18vT());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL}, dealerBlackjack());
    Assertions.assertFalse(bge.getShoe().isTheDeckStacked());
  }

  @Test
  void testWithSingleDeckResplitAcesAllowed() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 1;
      }
      public boolean isResplitAcesAllowed() {
        return true;
      }
    });
    Assertions.assertEquals(30000, bge.getBankroll().getChips());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_DOUBLE}, doubleDown());
    Assertions.assertEquals(29000, bge.getBankroll().getChips());
    bge.setNextBet(0);
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_DOUBLE}, eightVersusFive());
    Assertions.assertEquals(28000, bge.getBankroll().getChips());
    Assertions.assertEquals(0, bge.getBankroll().getStakeIncludingHands());
    Assertions.assertFalse(bge.getCycle().isActive());
    bge.getBankroll().settle();
    Assertions.assertEquals(0, bge.getBankroll().getStakeIncludingHands());
    Assertions.assertEquals(30000, bge.getBankroll().getChips());
    bge.setNextBet(2000);
    verifyRecommendedActions(bge,
        new String[] {CMD_DEAL, CMD_SPLIT, CMD_HIT, CMD_HIT, CMD_STAND, CMD_SPLIT, CMD_STAND, CMD_STAND},
        resplitAces());
    Assertions.assertEquals(24000, bge.getBankroll().getChips());
    Assertions.assertEquals(6000, bge.getBankroll().getStakeIncludingHands());
    verifyRecommendedActions(bge,
        new String[] {CMD_DEAL, CMD_SPLIT, CMD_DOUBLE, CMD_SPLIT, CMD_STAND, CMD_STAND},
        doubleAfterSplit());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_HIT, CMD_STAND}, soft18vT());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL}, dealerBlackjack());
    Assertions.assertFalse(bge.getShoe().isTheDeckStacked());
  }

  @Test
  void testBlackjack() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public boolean isBlackjack6to5() {
        return false;
      }
    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL}, playerBlackjack());
    Assertions.assertEquals(29500, bge.getBankroll().getChips());
    Assertions.assertFalse(bge.getCycle().isActive());
    bge.getBankroll().settle();
    Assertions.assertEquals(30750, bge.getBankroll().getChips());
    bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public boolean isBlackjack6to5() {
        return true;
      }
    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL}, playerBlackjack());
    Assertions.assertEquals(29500, bge.getBankroll().getChips());
    Assertions.assertFalse(bge.getCycle().isActive());
    bge.getBankroll().settle();
    Assertions.assertEquals(30600, bge.getBankroll().getChips());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL}, playerAndDealerBlackjack());
    Assertions.assertEquals(30100, bge.getBankroll().getChips());
    Assertions.assertFalse(bge.getCycle().isActive());
    bge.getBankroll().settle();
    Assertions.assertEquals(30600, bge.getBankroll().getChips());
    bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public boolean isEuropeanStyle() {
        return true;
      }
    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL}, playerAndDealerBlackjack());
    Assertions.assertEquals(29500, bge.getBankroll().getChips());
    Assertions.assertEquals("Lost",
        bge.getCycle().getPlayers().get(0).getHands().get(0).getMessage());
    Assertions.assertFalse(bge.getShoe().isTheDeckStacked());
  }

  @Test
  void testBlackjackVs21total() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getSeats() {
        return 2;
      }
    });
    bge.exec(CMD_INCREASE);
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_STAND},
        playerBlackjackAnd20Vs21total2players());
    Assertions.assertEquals(28000, bge.getBankroll().getChips());
    Assertions.assertFalse(bge.getCycle().isActive());
    bge.getBankroll().settle();
    Assertions.assertEquals(30500, bge.getBankroll().getChips());
    bge.exec(CMD_DECREASE);
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_STAND},
        playerBlackjackAnd20Vs21total2players());
    Assertions.assertFalse(bge.getShoe().isTheDeckStacked());
    Assertions.assertEquals(29500, bge.getBankroll().getChips());
    Assertions.assertFalse(bge.getCycle().isActive());
    bge.getBankroll().settle();
    Assertions.assertEquals(30750, bge.getBankroll().getChips());
  }

  @Test
  void testDealerStandsOnSoft17() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public boolean isDealerHitSoft17() {
        return false;
      }

      @Override
      public int getDecks() {
        return 1;
      }
    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_DOUBLE}, dealerSoft17_0());
    Assertions.assertEquals("Won",
        bge.getCycle().getPlayers().get(0).getHands().get(0).getMessage());
    Assertions.assertEquals(29000, bge.getBankroll().getChips());
    Assertions.assertFalse(bge.getCycle().isActive());
    bge.getBankroll().settle();
    Assertions.assertEquals(31000, bge.getBankroll().getChips());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_HIT, CMD_STAND}, soft18vT());
    Assertions.assertEquals(30500, bge.getBankroll().getChips());
    Assertions.assertFalse(bge.getCycle().isActive());
    bge.getBankroll().settle();
    Assertions.assertEquals(31500, bge.getBankroll().getChips());
  }

  @Test
  void testDealerHitsOnSoft17() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {});
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_DOUBLE}, dealerSoft17_1());
    Assertions.assertEquals(29000, bge.getBankroll().getChips());
    Assertions.assertFalse(bge.getCycle().isActive());
    bge.getBankroll().settle();
    Assertions.assertEquals(30000, bge.getBankroll().getChips());
    Assertions.assertEquals("Push",
        bge.getCycle().getPlayers().get(0).getHands().get(0).getMessage());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_HIT, CMD_STAND}, soft18vT());
    Assertions.assertEquals(29500, bge.getBankroll().getChips());
    Assertions.assertFalse(bge.getCycle().isActive());
    bge.getBankroll().settle();
    Assertions.assertEquals(30500, bge.getBankroll().getChips());
  }

  @Test
  void testEvenMoney() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public boolean isEvenMoneyOnBlackjackVace() {
        return true;
      }
    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL}, playerAndDealerBlackjack());
    Assertions.assertEquals(500, bge.getBankroll().getStakeIncludingHands());
    Assertions.assertEquals("Even money",
        bge.getCycle().getPlayers().get(0).getHands().get(0).getMessage());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL}, dealerBlackjack());
    Assertions.assertEquals(0, bge.getBankroll().getStakeIncludingHands());
    Assertions.assertEquals("Lost",
        bge.getCycle().getPlayers().get(0).getHands().get(0).getMessage());
    Assertions.assertFalse(bge.getShoe().isTheDeckStacked());
  }

  @Test
  void testWithDoubleAfterSplitNotAllowed6decks() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public boolean isDoubleAfterSplitAllowed() {
        return false;
      }

      @Override
      public boolean isResplitAcesAllowed() {
        return true;
      }
    });
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_DOUBLE}, doubleDown());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_HIT, CMD_HIT, CMD_STAND},
        eightVersusFive());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_SPLIT, CMD_HIT, CMD_HIT, CMD_STAND,
        CMD_SPLIT, CMD_STAND, CMD_STAND}, resplitAces1());
    verifyRecommendedActions(bge,
        new String[] {CMD_DEAL, CMD_SPLIT, CMD_HIT, CMD_STAND, CMD_SPLIT, CMD_STAND, CMD_STAND},
        doubleAfterSplit());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_HIT, CMD_STAND}, soft18vT());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL}, dealerBlackjack());
  }


  @Test
  void testWithResplitAcesNotAllowed6decks() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {
      @Override
      public int getDecks() {
        return 6;
      }
    });
    bge.getShoe().stackTheDeckFromList(resplitAces());
    Assertions.assertFalse(bge.ct.isActive());
    Assertions.assertFalse(bge.ct.isDealt());
    bge.exec(CMD_DEAL);
    Assertions.assertTrue(bge.ct.isActive());
    Assertions.assertTrue(bge.ct.isDealt());
    Assertions.assertEquals("cycle:Dealer: [THREE_OF_CLUBS, KING_OF_CLUBS]" + Stringop.LINE_SEPARATOR + 
        "Player 0: Player #0: cards:[ACE_OF_DIAMONDS, ACE_OF_DIAMONDS] value: 12 " + Stringop.LINE_SEPARATOR + 
        " Bankroll:Total Chips: 29500 stake: 0 tray: 29500 bankroll: -500",
        bge.ct.toString());
    bge.exec(CMD_SPLIT);
    bge.exec(CMD_HIT);
    bge.exec(CMD_STAND);
    bge.exec(CMD_SPLIT);
    bge.exec(CMD_HIT);
    bge.exec(CMD_STAND);
    bge.exec(CMD_STAND);
    Assertions.assertEquals(CMD_DEAL, bge.getCycle().getRecommendedAction());
    Assertions.assertFalse(bge.getCycle().isActive());
    bge.exec(CMD_DEAL);
    System.out.println(bge.getShoe().dumpStack());
    Assertions.assertFalse(bge.getShoe().isTheDeckStacked());
  }

}
