package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ResetTest {
    @Test
    fun test() {
        val bge = BlackjackGameEngine(BlackjackConfigSixDeckTest)
        Assertions.assertEquals(0, bge.bankroll.getStakeIncludingHands(bge.getCycle().players))
        Assertions.assertEquals(30000, bge.bankroll.getChips())
        Assertions.assertFalse(bge.getCycle().isActive)
        bge.getShoe().stackTheDeckFromList(eightVersusFive())
        bge.exec(CMD_DEAL)
        verifyPlayerCards(
            arrayOf(CardDefinition.FIVE_OF_CLUBS, CardDefinition.THREE_OF_CLUBS),
            bge.getLatestSnapshot().players[0].hands[0].getBlackjackCards()
        )
        Assertions.assertTrue(bge.getCycle().isActive)
        Assertions.assertEquals(0, bge.bankroll.getStakeIncludingHands(bge.getCycle().players))
        Assertions.assertEquals(500, bge.bankroll.getLiveValue(bge.getCycle().players))
        Assertions.assertEquals(29500, bge.bankroll.getChips())
        bge.exec(CMD_RESET)
        Assertions.assertEquals(0, bge.bankroll.getStakeIncludingHands(bge.getCycle().players))
        Assertions.assertEquals(0, bge.bankroll.getLiveValue(bge.getCycle().players))
        Assertions.assertEquals(30000, bge.bankroll.getChips())
        Assertions.assertFalse(bge.getCycle().isActive)
        bge.getShoe().stackTheDeckFromList(dealerBlackjack())
        bge.exec(CMD_DEAL)
        Assertions.assertEquals(-500, bge.bankroll.getStakeIncludingHands(bge.getCycle().players))
        Assertions.assertEquals(29500, bge.bankroll.getChips())
        bge.exec(CMD_RESET)
        Assertions.assertFalse(bge.getCycle().isActive)
        Assertions.assertEquals(0, bge.bankroll.getStakeIncludingHands(bge.getCycle().players))
        Assertions.assertEquals(30000, bge.bankroll.getChips())
        bge.exec(CMD_RESET)
        bge.exec(CMD_DEAL)
        if (bge.getCycle().dealer.getHandValue() == 21
            || bge.getCycle().players[0].hands[0].isNatural21()
        ) {
            Assertions.assertFalse(bge.getCycle().isActive)
        } else {
            Assertions.assertTrue(bge.getCycle().isActive)
        }
    }
}
