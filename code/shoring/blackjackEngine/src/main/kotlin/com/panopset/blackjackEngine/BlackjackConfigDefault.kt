package com.panopset.blackjackEngine

import com.panopset.compat.Stringop

class BlackjackConfigDefault: BlackjackConfiguration {
    var defaultResources = DefaultResources()
    private var sc = false
    override fun getLargeBetInWholeDollars(): Int {
     return 20
    }
    override fun getTargetStakeInWholeDollars(): Int {
        return 10000
    }
    override fun getMinimumBetInWholeDollars(): Int {
        return 5
    }
    override fun getBetIncrementInWholeDollars(): Int {
        return 5
    }
    override fun getReloadAmountInWholeDollars(): Int {
        return 300
    }
    override fun getSeats(): Int {
        return DEFAULT_NBR_SEATS
    }
    override fun getDecks(): Int {
        return DEFAULT_NBR_DECKS
    }
    override fun isFastDeal(): Boolean {
        return false
    }
    override fun isBasicStrategyVariationsOnly(): Boolean {
        return false
    }
    override fun isDoubleAfterSplitAllowed(): Boolean {
        return true
    }
    override fun isResplitAcesAllowed(): Boolean {
        return false
    }
    override fun isSplitAcePlayable(): Boolean {
        return false
    }
    override fun isDealerHitSoft17(): Boolean {
        return true
    }
    override fun isEuropeanStyle(): Boolean {
        return false
    }
    override fun isLateSurrenderAllowed(): Boolean {
        return false
    }
    override fun isBlackjack6to5(): Boolean {
        return false
    }
    override fun isEvenMoneyOnBlackjackVace(): Boolean {
        return false
    }
    override fun getStrategyData(): ArrayList<String> {
        return Stringop.stringToList(defaultResources.defaultBasicStrategy)
    }
    override fun getCountingSystems(): CountingSystems {
        return CountingSystems(Stringop.stringToList(defaultResources.defaultCountingSystems))
    }
    override fun getStrategicVeryPositiveCount(): Int {
        return 10
    }
    override fun isShowCount(): Boolean {
        return sc
    }

    override fun toggleShowCount() {
        sc = !sc
    }

    override fun getMessages(): BlackjackMessages {
        return BlackjackMessagesDft()
    }
}

const val DEFAULT_NBR_DECKS = 8
const val DEFAULT_NBR_SEATS = 1
