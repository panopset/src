package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.BlackjackConfigDefault
import com.panopset.blackjackEngine.BlackjackConfiguration
import com.panopset.blackjackEngine.BlackjackMessages
import com.panopset.blackjackEngine.CountingSystems
import java.util.ArrayList

open class BlackjackConfigBaseTest: BlackjackConfiguration {

    override fun isDoubleAfterSplitAllowed(): Boolean {
        return BlackjackConfigDefault.isDoubleAfterSplitAllowed()
    }

    override fun isResplitAcesAllowed(): Boolean {
        return BlackjackConfigDefault.isResplitAcesAllowed()
    }

    override fun isSplitAcePlayable(): Boolean {
        return BlackjackConfigDefault.isSplitAcePlayable()
    }

    override fun getSeats(): Int {
        return BlackjackConfigDefault.getDecks()
    }

    override fun getDecks(): Int {
        return BlackjackConfigDefault.getDecks()
    }

    override fun isDealerHitSoft17(): Boolean {
        return BlackjackConfigDefault.isDealerHitSoft17()
    }

    override fun isBlackjack6to5(): Boolean {
        return BlackjackConfigDefault.isBlackjack6to5()
    }

    override fun isEvenMoneyOnBlackjackVace(): Boolean {
        return BlackjackConfigDefault.isEvenMoneyOnBlackjackVace()
    }

    override fun isLateSurrenderAllowed(): Boolean {
        return BlackjackConfigDefault.isLateSurrenderAllowed()
    }

    override fun isEuropeanStyle(): Boolean {
        return BlackjackConfigDefault.isEuropeanStyle()
    }

    override fun isFastDeal(): Boolean {
        return BlackjackConfigDefault.isFastDeal()
    }

    override fun isBasicStrategyVariationsOnly(): Boolean {
        return BlackjackConfigDefault.isBasicStrategyVariationsOnly()
    }

    override fun isShowCount(): Boolean {
        return BlackjackConfigDefault.isShowCount()
    }

    override fun getStrategyData(): ArrayList<String> {
        return BlackjackConfigDefault.getStrategyData()
    }

    override fun getCountingSystems(): CountingSystems {
        return BlackjackConfigDefault.getCountingSystems()
    }

    override fun getLargeBetInWholeDollars(): Int {
        return BlackjackConfigDefault.getLargeBetInWholeDollars()
    }

    override fun getTargetStakeInWholeDollars(): Int {
        return BlackjackConfigDefault.getTargetStakeInWholeDollars()
    }

    override fun getMinimumBetInWholeDollars(): Int {
        return BlackjackConfigDefault.getMinimumBetInWholeDollars()
    }

    override fun getBetIncrementInWholeDollars(): Int {
        return BlackjackConfigDefault.getBetIncrementInWholeDollars()
    }

    override fun getReloadAmountInWholeDollars(): Int {
        return BlackjackConfigDefault.getReloadAmountInWholeDollars()
    }

    override fun getStrategicVeryPositiveCount(): Int {
        return BlackjackConfigDefault.getStrategicVeryPositiveCount()
    }

    override fun getMessages(): BlackjackMessages {
        return BlackjackConfigDefault.getMessages()
    }
}