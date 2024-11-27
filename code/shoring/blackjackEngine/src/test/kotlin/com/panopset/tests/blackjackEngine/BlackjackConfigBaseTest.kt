package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.BlackjackConfigDefault
import com.panopset.blackjackEngine.BlackjackConfiguration
import com.panopset.blackjackEngine.BlackjackMessages
import com.panopset.blackjackEngine.CountingSystems
import java.util.ArrayList

open class BlackjackConfigBaseTest: BlackjackConfiguration {
    private var sc = false
    private val blackjackConfigDefault = BlackjackConfigDefault()

    override fun isDoubleAfterSplitAllowed(): Boolean {
        return blackjackConfigDefault.isDoubleAfterSplitAllowed()
    }

    override fun isResplitAcesAllowed(): Boolean {
        return blackjackConfigDefault.isResplitAcesAllowed()
    }

    override fun isSplitAcePlayable(): Boolean {
        return blackjackConfigDefault.isSplitAcePlayable()
    }

    override fun getSeats(): Int {
        return blackjackConfigDefault.getSeats()
    }

    override fun getDecks(): Int {
        return blackjackConfigDefault.getDecks()
    }

    override fun isDealerHitSoft17(): Boolean {
        return blackjackConfigDefault.isDealerHitSoft17()
    }

    override fun isBlackjack6to5(): Boolean {
        return blackjackConfigDefault.isBlackjack6to5()
    }

    override fun isEvenMoneyOnBlackjackVace(): Boolean {
        return blackjackConfigDefault.isEvenMoneyOnBlackjackVace()
    }

    override fun isLateSurrenderAllowed(): Boolean {
        return blackjackConfigDefault.isLateSurrenderAllowed()
    }

    override fun isEuropeanStyle(): Boolean {
        return blackjackConfigDefault.isEuropeanStyle()
    }

    override fun isFastDeal(): Boolean {
        return blackjackConfigDefault.isFastDeal()
    }

    override fun isBasicStrategyVariationsOnly(): Boolean {
        return blackjackConfigDefault.isBasicStrategyVariationsOnly()
    }

    override fun isShowCount(): Boolean {
        return sc
    }

    override fun toggleShowCount() {
        sc = !sc
    }

    override fun getStrategyData(): ArrayList<String> {
        return blackjackConfigDefault.getStrategyData()
    }

    override fun getCountingSystems(): CountingSystems {
        return blackjackConfigDefault.getCountingSystems()
    }

    override fun getLargeBetInWholeDollars(): Int {
        return blackjackConfigDefault.getLargeBetInWholeDollars()
    }

    override fun getTargetStakeInWholeDollars(): Int {
        return blackjackConfigDefault.getTargetStakeInWholeDollars()
    }

    override fun getMinimumBetInWholeDollars(): Int {
        return blackjackConfigDefault.getMinimumBetInWholeDollars()
    }

    override fun getBetIncrementInWholeDollars(): Int {
        return blackjackConfigDefault.getBetIncrementInWholeDollars()
    }

    override fun getReloadAmountInWholeDollars(): Int {
        return blackjackConfigDefault.getReloadAmountInWholeDollars()
    }

    override fun getStrategicVeryPositiveCount(): Int {
        return blackjackConfigDefault.getStrategicVeryPositiveCount()
    }

    override fun getMessages(): BlackjackMessages {
        return blackjackConfigDefault.getMessages()
    }
}