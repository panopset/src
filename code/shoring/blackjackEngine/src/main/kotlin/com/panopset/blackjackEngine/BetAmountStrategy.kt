package com.panopset.blackjackEngine

class BetAmountStrategy(private val bge: BlackjackGameEngine) {
    private val config: BlackjackConfiguration = bge.config

    fun adjust(): Int {
        var betAmount = bge.getNextBet()
        if (bge.isAutomaticRunning()) {
            if (bge.isCountVeryPositive()) {
                betAmount = config.getLargeBetInWholeDollars() * 100
            }
        }
        return betAmount
    }
}
