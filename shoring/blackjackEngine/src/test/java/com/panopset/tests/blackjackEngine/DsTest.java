package com.panopset.tests.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_COUNT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DOUBLE;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_HIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_STAND;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.panopset.compat.Stringop;
import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.BlackjackGameEngine;

public class DsTest extends AbstractBlackjackTest {

  @Test
  void test() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault());
    bge.getShoe().shuffle();
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_DOUBLE}, soft18v3());
    Assertions.assertEquals("| Stake: $10.00| Table: $310.00|  Tray: $290.00|  Score: 1 (1)| Hi-Lo: -1", bge.getRawGameStatus());
    Assertions.assertEquals(
        Stringop.arrayToList(new String[] {"Stake: $10.00", "Table: $310.00", "Tray: $290.00", "Score: 1 (1)", "Hi-Lo: -1"}),
        bge.getGameStatusVertical());
    Assertions.assertEquals(" Stake: $10.00 Table: $310.00  Tray: $290.00  Score: 1 (1) Hi-Lo: -1",
        bge.getGameStatusHorizontal());
    Assertions.assertEquals(
        Stringop.arrayToList(new String[] {"reloads: 1", "Chips: $290.00", "Next bet: $5.00"}),
        bge.getStatusChipsVertical());
    Assertions.assertEquals("  reloads: 1  Chips: $290.00  Next bet: $5.00",
        bge.getStatusChipsHorizontal());
    Assertions.assertEquals("  reloads: 1|  Chips: $290.00|  Next bet: $5.00",
        bge.getRawStatusChips());
    Assertions.assertEquals("  reloads: 1|  Chips: $290.00|  Next bet: $5.00",
        bge.getRawStatusChips());
    bge.shuffle();
    bge.getShoe().stackTheDeckFromList(soft18v3());
    bge.shuffle();
    bge.exec(CMD_COUNT);
    Assertions.assertEquals("| Stake: $10.00| Table: $310.00|  Tray: $290.00|  Score: 1 (1)", bge.getRawGameStatus());
  }

  @Test
  void testDoubleNotAllowed() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault());
    verifyRecommendedActions(bge, new String[] {CMD_DEAL, CMD_HIT, CMD_STAND}, softHitTo18v3());
  }

}
