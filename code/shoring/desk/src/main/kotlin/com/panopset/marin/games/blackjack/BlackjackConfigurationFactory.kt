package com.panopset.marin.games.blackjack

import com.panopset.blackjackEngine.BlackjackConfigDefault
import com.panopset.blackjackEngine.BlackjackConfiguration
import com.panopset.blackjackEngine.BlackjackMessages
import com.panopset.blackjackEngine.CountingSystems
import com.panopset.compat.Logz
import com.panopset.desk.games.bj.*
import com.panopset.fxapp.FxDoc

object BlackjackConfigurationFactory {
    fun create(fxDoc: FxDoc): BlackjackConfiguration {
        val blackjackConfigDefault = BlackjackConfigDefault()
        val rtn = object: BlackjackConfiguration {
            override fun getMessages(): BlackjackMessages {
                return BlackjackConfigDefault().getMessages()
            }
            override fun isDoubleAfterSplitAllowed(): Boolean {
                return fxDoc.getBooleanValue(KEY_DOUBLE_AFTER_SPLIT)
            }
            override fun isResplitAcesAllowed(): Boolean {
                return fxDoc.getBooleanValue(KEY_RESPLIT_ACES)
            }
            override fun isSplitAcePlayable(): Boolean {
                return fxDoc.getBooleanValue(KEY_RESPLIT_ACES)
            }
            override fun getStrategyData(): ArrayList<String> {
                var rtn = fxDoc.getArrayListValue(KEY_BASIC_STRATEGY_DATA)
                val dft = blackjackConfigDefault.getStrategyData()
                if (rtn.isEmpty())
                    rtn = dft
                if (rtn != dft) {
                    Logz.warn("Custom (or out of date) strategy data in use.  Go to config to reset.")
                }
                return rtn
            }
            override fun getCountingSystems(): CountingSystems {
                var countingSystems = CountingSystems(fxDoc.getArrayListValue(KEY_COUNTING_SYSTEMS_DATA))
                val dft = blackjackConfigDefault.getCountingSystems()
                if (countingSystems.countingSystemsData.isEmpty()) {
                    countingSystems = CountingSystems(dft.countingSystemsData)
                }
                if (countingSystems.countingSystemsData != dft.countingSystemsData) {
                    Logz.warn("Custom (or out of date) counting system data in use.  Go to config to reset.")
                }
                return countingSystems
            }
            override fun getSeats(): Int {
                return fxDoc.getIntValue(KEY_NUMBER_OF_SEATS)
            }
            override fun getDecks(): Int {
                return fxDoc.getIntValue(KEY_NUMBER_OF_DECKS)
            }
            override fun isDealerHitSoft17(): Boolean {
                return fxDoc.getBooleanValue(KEY_DEALER_HITS_SOFT_17)
            }
            override fun isBlackjack6to5(): Boolean {
                return fxDoc.getBooleanValue(KEY_BLACKJACK_6_TO_5)
            }
            override fun isEvenMoneyOnBlackjackVace(): Boolean {
                return fxDoc.getBooleanValue(KEY_EVEN_MONEY_BLACKJACK_VS_ACE)
            }
            override fun isLateSurrenderAllowed(): Boolean {
                return fxDoc.getBooleanValue(KEY_IS_LATE_SURRENDER_ALLOWED)
            }
            override fun isEuropeanStyle(): Boolean {
                return fxDoc.getBooleanValue(KEY_EUROPEAN_STYLE)
            }
            override fun isFastDeal(): Boolean {
                return fxDoc.getBooleanValue(KEY_FAST_DEAL)
            }
            override fun isBasicStrategyVariationsOnly(): Boolean {
                return fxDoc.getBooleanValue(KEY_BASIC_STRATEGY_VARIATIONS_ONLY)
            }
            override fun isShowCount(): Boolean {
                return fxDoc.getBooleanValue(KEY_IS_SHOW_COUNT)
            }

            override fun toggleShowCount() {
                fxDoc.toggle(KEY_IS_SHOW_COUNT)
            }

            override fun getLargeBetInWholeDollars(): Int {
                return fxDoc.getIntValue(KEY_LARGE_BET_IN_WHOLE_DOLLARS)
            }
            override fun getTargetStakeInWholeDollars(): Int {
                return fxDoc.getIntValue(KEY_TARGET_STAKE_IN_WHOLE_DOLLARS)
            }
            override fun getMinimumBetInWholeDollars(): Int {
                return fxDoc.getIntValue(KEY_MINIMUM_BET_IN_WHOLE_DOLLARS)
            }
            override fun getBetIncrementInWholeDollars(): Int {
                return fxDoc.getIntValue(KEY_INCREMENT_BET_IN_WHOLE_DOLLARS)
            }
            override fun getReloadAmountInWholeDollars(): Int {
                return fxDoc.getIntValue(KEY_RELOAD_AMOUNT_IN_WHOLE_DOLLARS)
            }
            override fun getStrategicVeryPositiveCount(): Int {
                return fxDoc.getIntValue(KEY_VERY_POSITIVE_COUNT)
            }
        }
        return rtn
    }
}
