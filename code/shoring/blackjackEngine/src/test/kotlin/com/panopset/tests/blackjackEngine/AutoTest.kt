package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.*
import com.panopset.compat.Stringop
import com.panopset.compat.Zombie
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AutoTest {
    @Test
    fun test() {
        val bge = BlackjackGameEngine(object : BlackjackConfigBaseTest() {
            override fun getDecks(): Int {
                return 6
            }
        })
        testAutoRun(bge)
    }

    @Test
    fun testFromVariations() {
        val bge = BlackjackGameEngine(object : BlackjackConfigBaseTest() {
            override fun getDecks(): Int {
                return 6
            }
            override fun isBasicStrategyVariationsOnly(): Boolean {
                return true
            }
        })
        bge.exec(CMD_AUTO)
        Assertions.assertEquals(
            "Please turn off \"Variations\" in the Configuration->Rules tab before running automatically.",
            bge.dealerMessage
        )
    }

    @Test
    fun targetStakeTest() {
        val bge = BlackjackGameEngine(object : BlackjackConfigBaseTest() {
            override fun getDecks(): Int {
                return 1
            }
            override fun getTargetStakeInWholeDollars(): Int {
                return 10000
            }
            override fun getStrategicVeryPositiveCount(): Int {
                return 6
            }
            override fun getLargeBetInWholeDollars(): Int {
                return 200
            }
        })
        bge.exec(CMD_AUTO)
        synchronized(bge) {
            var paintedSnapshot = BlackjackGameState()
            while (bge.isAutomaticRunning() && Zombie.isActive) {
                bge.waitMillis(200)
                val cycleSnapshot = bge.takeAnewSnapshot()
                if (cycleSnapshot != paintedSnapshot) {
                    paintedSnapshot = cycleSnapshot
                    println(
                        String.format(
                            "Stake: %s, Chips: %s Hands: %d ",
                            Stringop.getDollarString(cycleSnapshot.stakeIncludingHands),
                            Stringop.getDollarString(cycleSnapshot.chips), cycleSnapshot.metrics.handCount
                        )
                    )
                    Assertions.assertEquals(500, cycleSnapshot.nextBet)
                }
            }
        }
        Assertions.assertTrue(bge.bankroll.getChips() > 99999)
    }

    private fun testAutoRun(bge: BlackjackGameEngine) {
        verifyInitialState(bge.metrics.handCount)
        bge.exec(CMD_AUTO)
        synchronized(bge) { bge.waitMillis(1000) }
        bge.exec(CMD_AUTO)
        synchronized(bge) { bge.waitMillis(100) }
        val priorHandCount: Int = bge.metrics.handCount
        synchronized(bge) { bge.waitMillis(100) }
        verifyPostAutoRun(priorHandCount, bge.metrics.handCount)
    }
}
