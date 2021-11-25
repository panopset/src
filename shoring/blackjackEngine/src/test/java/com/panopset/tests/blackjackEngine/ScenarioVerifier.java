package com.panopset.tests.blackjackEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import com.panopset.compat.Stringop;
import com.panopset.blackjackEngine.BlackjackGameEngine;
import com.panopset.blackjackEngine.Card;

public class ScenarioVerifier {

  public void performDeviantActions(BlackjackGameEngine bge, String[] expectedActions,
      List<Card> stackedDeckForTesting) {
    bge.getShoe().stackTheDeckFromList(stackedDeckForTesting);
    int i = 0;
    for (String action : expectedActions) {
      doTheNextAction(bge, action, i++, false);
    }
    Assertions.assertFalse(bge.getCycle().isActive());
    Assertions.assertFalse(bge.getShoe().isTheDeckStacked());
  }

  void verifyRecommendedActions(BlackjackGameEngine bge, String[] expectedActions,
      List<Card> stackedDeckForTesting) {
    bge.getShoe().stackTheDeckFromList(stackedDeckForTesting);
    int i = 0;
    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    System.out.println("***************Verify and execute:  " + Stringop.arrayToList(expectedActions));
    for (String action : expectedActions) {
      System.out.println("***>" + action);
      verifyAndDoRecommendedAction(bge, action, i++);
      System.out.println("<***" + action);
    }
    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    Assertions.assertFalse(bge.getCycle().isActive());
  }

  public static void verifyAndDoRecommendedAction(BlackjackGameEngine bge, String action, int i) {
    doTheNextAction(bge, action, i, true);
  }


  public static void doTheNextAction(BlackjackGameEngine bge, String action, int i, boolean verify) {
    String ra = bge.getCycle().getRecommendedAction();
    System.out.println(showTheCycleSituation(bge, action, i, "before action"));
    if (verify) {
      assertEquals(ra, action);
    }
    bge.exec(action);
    System.out.println(showTheCycleSituation(bge, action, i, "after action"));
    System.out.println(
        String.format("Strategy line for next action is %s.", bge.getCycle().getStrategyLine()));
  }

  public static String showTheCycleSituation(BlackjackGameEngine bge, String action, int i, String msg) {
    // if (!bge.getCycle().isDealt() || bge.getCycle().getActivePlayer() == null) {
    return String.format("%s %d %s: %s\n stacked deck remaining: %s \nactive situation:%s", msg, i, action,
        bge.getCycle().toString(), bge.getShoe().dumpStack(), bge.getCycle().getActiveSituation());
  }

  @SafeVarargs
  public final void verifyRecommendedActionsFastDeal(BlackjackGameEngine bge, String[] expectedActions,
      List<Card>... stackedDecksForTesting) {
    for (List<Card> stackedDeckForTesting : stackedDecksForTesting) {
      bge.getShoe().stackTheDeckFromList(stackedDeckForTesting);
    }
    int i = 0;
    for (String action : expectedActions) {
      verifyAndDoRecommendedAction(bge, action, i++);
    }    
  }

}
