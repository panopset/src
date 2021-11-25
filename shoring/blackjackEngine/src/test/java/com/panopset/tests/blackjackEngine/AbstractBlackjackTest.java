package com.panopset.tests.blackjackEngine;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import com.panopset.blackjackEngine.BlackjackCard;
import com.panopset.blackjackEngine.BlackjackGameEngine;
import com.panopset.blackjackEngine.Card;
import com.panopset.blackjackEngine.CardDefinition;

public abstract class AbstractBlackjackTest implements PlayerScenarios  {

  public void verifyRecommendedActions(BlackjackGameEngine bge, String[] expectedActions,
      List<Card> stackedDeckForTesting) {
    new ScenarioVerifier().verifyRecommendedActions(bge, expectedActions, stackedDeckForTesting);
  }
  
  public void performDeviantActions(BlackjackGameEngine bge, String[] expectedActions,
      List<Card> stackedDeckForTesting) {
    new ScenarioVerifier().performDeviantActions(bge, expectedActions, stackedDeckForTesting);
  }

  @SafeVarargs
  public final void verifyRecommendedActionsFastDeal(BlackjackGameEngine bge, String[] expectedActions,
      List<Card>... stackedDeckForTesting) {
    new ScenarioVerifier().verifyRecommendedActionsFastDeal(bge, expectedActions, stackedDeckForTesting);
  }
  
  public void verifyPlayerCards(CardDefinition[] expected, List<BlackjackCard> cards) {
    if (expected == null || cards == null) {
      Assertions.fail();
    }
    Assertions.assertEquals(expected.length, cards.size());
    int i = 0;
    for (BlackjackCard blackjackCard : cards) {
      Assertions.assertEquals(expected[i++], blackjackCard.getCard().getCardDefinition());
    }
  }
}
