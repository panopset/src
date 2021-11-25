package com.panopset.tests.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DOUBLE;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_SPLIT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.BlackjackGameEngine;
import com.panopset.blackjackEngine.CardDefinition;
import com.panopset.blackjackEngine.CycleSnapshot;

public class CycleSnapshotTest extends AbstractBlackjackTest {

  @Test
  void test() {
    BlackjackGameEngine bge = new BlackjackGameEngine(new BlackjackConfigDefault() {});
    CycleSnapshot cs = bge.getCurrentSnapshot();
    Assertions.assertEquals(30000, cs.getBankroll().getChips());
    bge.getShoe().stackTheDeckFromList(eightVersusFive());
    bge.exec(CMD_DEAL);
    cs = bge.getCurrentSnapshot();
    Assertions.assertEquals(29500, cs.getBankroll().getChips());
    Assertions.assertEquals(CardDefinition.FIVE_OF_HEARTS.getSuit(), cs.getDealer().getUpCard().getCard().getSuit());
    Assertions.assertEquals(CardDefinition.FIVE_OF_HEARTS.getFace(), cs.getDealer().getUpCard().getCard().getFace());
    Assertions.assertEquals(1, cs.getPlayers().size());
    cs = bge.getCurrentSnapshot();
    Assertions.assertEquals(1, cs.getPlayers().size());
    bge.exec(CMD_DOUBLE);
    cs = bge.getCurrentSnapshot();
    Assertions.assertEquals("hard 2  3  4  5  6  7  8  9  T  A ", cs.getMistakeHeader());
    Assertions.assertEquals("  8  H  H  H  H* H* H  H  H  H  H ", cs.getMistakeMessage());
    bge.exec(CMD_SPLIT);
    cs = bge.getCurrentSnapshot();
    Assertions.assertEquals("Please select L=Deal", cs.getDealerMessage());
    Assertions.assertEquals("Stake: $10.00", cs.getGameStatusVertical().get(0));
    Assertions.assertEquals("Table: $310.00", cs.getGameStatusVertical().get(1));
    Assertions.assertEquals(" Stake: $10.00 Table: $310.00  Tray: $290.00  Score: 0 (1) Hi-Lo: 1", cs.getGameStatusHorizontal());
    Assertions.assertEquals("reloads: 1", cs.getStatusChipsVertical().get(0));
    Assertions.assertEquals("  reloads: 1  Chips: $290.00  Next bet: $5.00", cs.getStatusChipsHorizontal());
    Assertions.assertFalse(cs.isPainted());
    cs.setPainted();
    Assertions.assertTrue(cs.isPainted());
  }

}
