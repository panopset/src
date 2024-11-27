package com.panopset.tests.blackjackEngine

object BlackjackConfigSingleDeckTest: BlackjackConfigBaseTest() {
    override fun getDecks(): Int {
        return 1
    }
}