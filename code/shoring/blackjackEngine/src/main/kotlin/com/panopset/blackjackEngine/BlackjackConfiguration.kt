package com.panopset.blackjackEngine

import java.util.ArrayList

interface BlackjackConfiguration {
    fun isDoubleAfterSplitAllowed(): Boolean
    fun isResplitAcesAllowed(): Boolean
    fun isSplitAcePlayable(): Boolean
    fun getSeats(): Int
    fun getDecks(): Int
    fun isDealerHitSoft17(): Boolean
    fun isBlackjack6to5(): Boolean
    fun isEvenMoneyOnBlackjackVace(): Boolean
    fun isLateSurrenderAllowed(): Boolean
    fun isEuropeanStyle(): Boolean
    fun isFastDeal(): Boolean
    fun isBasicStrategyVariationsOnly(): Boolean
    fun isShowCount(): Boolean
    fun toggleShowCount()
    fun getStrategyData(): ArrayList<String>
    fun getCountingSystemData(): ArrayList<String>
    fun getLargeBetInWholeDollars(): Int
    fun getTargetStakeInWholeDollars(): Int
    fun getMinimumBetInWholeDollars(): Int
    fun getBetIncrementInWholeDollars(): Int
    fun getReloadAmountInWholeDollars(): Int
    fun getStrategicVeryPositiveCount(): Int
    fun isBetIdeaDoubleAfterBust(): Boolean
    fun isBetIdeaLetItRideAfterTwoWins(): Boolean
    fun getMessages(): BlackjackMessages
}
