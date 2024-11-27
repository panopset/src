package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.BlackjackConfigDefault
import com.panopset.blackjackEngine.BlackjackGameEngine
import com.panopset.blackjackEngine.CardDefinition
import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CycleSnapshotTest {
    @Test
    fun test() {
        val bge = BlackjackGameEngine(BlackjackConfigDefault())
        var cs = bge.takeAnewSnapshot()
        Assertions.assertEquals(30000, cs.chips)
        bge.getShoe().stackTheDeckFromList(eightVersusFive())
        bge.exec(CMD_DEAL)
        cs = bge.takeAnewSnapshot()
        Assertions.assertEquals(29500, cs.chips)
        Assertions.assertEquals(CardDefinition.FIVE_OF_HEARTS.suit, cs.handDealer.upCard.card.suit)
        Assertions.assertEquals(CardDefinition.FIVE_OF_HEARTS.face, cs.handDealer.upCard.card.face)
        Assertions.assertEquals(1, cs.players.size)
        cs = bge.takeAnewSnapshot()
        Assertions.assertEquals(1, cs.players.size)
        bge.exec(CMD_DOUBLE)
        cs = bge.takeAnewSnapshot()
        Assertions.assertEquals("hard 2  3  4  5  6  7  8  9  T  A ", cs.mistakeHeader)
        Assertions.assertEquals("  8  H  H  H  H* H* H  H  H  H  H ", cs.mistakeMessage)
        bge.exec(CMD_SPLIT)
        bge.toggleShowCount()
        cs = bge.takeAnewSnapshot()
        Assertions.assertEquals("Please select L=Deal", cs.dealerMessage)
        Assertions.assertEquals("Stake: $10.00", cs.gameStatusVertical[0])
        Assertions.assertEquals("Chips: $310.00", cs.gameStatusVertical[1])
        Assertions.assertEquals(
            " Stake: $10.00 Chips: $310.00 Score: 0 (1) Hi-Lo: 1",
            cs.gameStatusHorizontal
        )
        Assertions.assertEquals("reloads: 1", cs.statusChipsVertical[0])
        Assertions.assertEquals("  reloads: 1  Chips: $310.00  Next bet: $5.00", cs.statusChipsHorizontal)
        Assertions.assertFalse(cs.isPainted)
        cs.isPainted = true
        Assertions.assertTrue(cs.isPainted)
    }
}
