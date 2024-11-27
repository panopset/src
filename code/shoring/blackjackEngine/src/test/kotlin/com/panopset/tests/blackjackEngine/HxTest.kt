package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HxTest {
    @Test
    fun testSingleDeck() {
        val bge = BlackjackGameEngine(BlackjackConfigSingleDeckTest)
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_DOUBLE), soft18v2())
        performDeviantActions(bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_HIT), soft18v2())
        Assertions.assertEquals(
            "Busted",
            bge.getCycle().players[0].hands[0].message
        )
    }

    @Test
    fun test() {
        val bge = BlackjackGameEngine(object : BlackjackConfigBaseTest() {
            override fun getDecks(): Int {
                return 6
            }
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_STAND), soft18v2())
        performDeviantActions(bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_HIT), soft18v2())
        Assertions.assertEquals(
            "Busted",
            bge.getCycle().players[0].hands[0].message
        )
    }
}
