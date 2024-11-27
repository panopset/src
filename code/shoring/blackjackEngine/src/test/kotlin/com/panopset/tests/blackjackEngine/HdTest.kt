package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Test

class HdTest {
    @Test
    fun testSingleDeck() {
        val bge = BlackjackGameEngine(BlackjackConfigSingleDeckTest)
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_DOUBLE), stackHard11_v_A())
    }

    @Test
    fun testDoubleDeck() {
        val bge = BlackjackGameEngine(object : BlackjackConfigBaseTest() {
            override fun getDecks(): Int {
                return 2
            }
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_DOUBLE), stackHard11_v_A())
    }

    @Test
    fun testTripleDeck() {
        val bge = BlackjackGameEngine(object : BlackjackConfigBaseTest() {
            override fun getDecks(): Int {
                return 3
            }
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_DOUBLE), stackHard11_v_A())
    }

    @Test
    fun testDoubleDeckDealerStandsSoft17() {
        val bge = BlackjackGameEngine(object : BlackjackConfigBaseTest() {
            override fun getDecks(): Int {
                return 2
            }
            override fun isDealerHitSoft17(): Boolean {
                return false
            }
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_DOUBLE), stackHard11_v_A())
    }

    @Test
    fun testTripleDeckDealerStandsSoft17() {
        val bge = BlackjackGameEngine(object : BlackjackConfigBaseTest() {
            override fun getDecks(): Int {
                return 3
            }
            override fun isDealerHitSoft17(): Boolean {
                return false
            }
        })
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_STAND), stackHard11_v_A())
    }

    @Test
    fun testSingleDeckHardHitTo11() {
        val bge = BlackjackGameEngine(BlackjackConfigSingleDeckTest)
        verifyRecommendedActions(bge, arrayOf(CMD_DEAL, CMD_HIT, CMD_HIT, CMD_STAND), stackHardHitTo11_v_A())
    }
}
