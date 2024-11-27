package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Test

class HampTest {
    @Test
    fun testSingleDeck() {
        val bge = BlackjackGameEngine(BlackjackConfigSingleDeckTest)
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_SPLIT, CMD_STAND, CMD_STAND), stackSplit6_v_2())
    }

    @Test
    fun testDoubleDeck() {
        val bge = BlackjackGameEngine(object: BlackjackConfigBaseTest() {
            override fun getDecks(): Int {
                return 2
            }
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_HIT), stackSplit6_v_2_doubleDeck())
    }
}
