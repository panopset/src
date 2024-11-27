package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SurrenderScenarios {
    @Test
    fun testSurrender16vs9() {
        var bge = BlackjackGameEngine(BlackjackConfigSixDeckLateSurrenderTest)
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_SURRENDER), surrender16vs9())
        Assertions.assertFalse(bge.getShoe().isTheDeckStacked())
        bge = BlackjackGameEngine(BlackjackConfigSixDeckTest)
        bge.getShoe().stackTheDeckFromList(surrender16vs9())
        Assertions.assertFalse(bge.ct.isActive)
        Assertions.assertFalse(bge.ct.isDealt)
        bge.exec(CMD_DEAL)
        Assertions.assertTrue(bge.ct.isActive)
        Assertions.assertTrue(bge.ct.isDealt)
        bge.exec(CMD_SURRENDER)
        Assertions.assertEquals("Surrender not allowed in this casino", bge.dealerMessage)
        bge.exec(CMD_HIT)
        Assertions.assertFalse(bge.getShoe().isTheDeckStacked())
    }

    @Test
    fun testSurrenderFailHitTo16vs10() {
        var bge = BlackjackGameEngine(BlackjackConfigSixDeckLateSurrenderTest)
        bge.getShoe().stackTheDeckFromList(surrenderFailHitTo16vs10())
        Assertions.assertFalse(bge.ct.isActive)
        Assertions.assertFalse(bge.ct.isDealt)
        bge.exec(CMD_DEAL)
        Assertions.assertTrue(bge.ct.isActive)
        Assertions.assertTrue(bge.ct.isDealt)
        bge.exec(CMD_HIT)
        bge.exec(CMD_SURRENDER)
        Assertions.assertEquals("Surrender not possible here", bge.dealerMessage)
        bge.exec(CMD_HIT)
        Assertions.assertFalse(bge.getShoe().isTheDeckStacked())
        bge = BlackjackGameEngine(BlackjackConfigSixDeckTest)
        bge.getShoe().stackTheDeckFromList(surrenderFailHitTo16vs10())
        Assertions.assertFalse(bge.ct.isActive)
        Assertions.assertFalse(bge.ct.isDealt)
        bge.exec(CMD_DEAL)
        Assertions.assertTrue(bge.ct.isActive)
        Assertions.assertTrue(bge.ct.isDealt)
        bge.exec(CMD_HIT)
        bge.exec(CMD_SURRENDER)
        Assertions.assertEquals("Surrender not allowed in this casino", bge.dealerMessage)
        bge.exec(CMD_HIT)
        Assertions.assertFalse(bge.getShoe().isTheDeckStacked())
    }
}

object BlackjackConfigSixDeckLateSurrenderTest: BlackjackConfigBaseTest() {
    override fun getDecks(): Int {
        return 6
    }
    override fun isLateSurrenderAllowed(): Boolean {
        return true
    }
}