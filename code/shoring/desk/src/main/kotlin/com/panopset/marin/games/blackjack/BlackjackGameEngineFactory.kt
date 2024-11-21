package com.panopset.marin.games.blackjack

import com.panopset.blackjackEngine.BlackjackConfigDefault
import com.panopset.blackjackEngine.BlackjackGameEngine
import com.panopset.compat.Logz
import com.panopset.compat.Stringop
import com.panopset.desk.games.bj.BlackjackFxControls
import java.util.ArrayList

class BlackjackGameEngineFactory {
    fun create(ctls: BlackjackFxControls): BlackjackGameEngine {
        val rtn = object: BlackjackGameEngine(ctls.fxDoc, object : BlackjackConfigDefault() {
            val blackjackConfigDefault = BlackjackConfigDefault()
            override fun isDoubleAfterSplitAllowed(): Boolean {
                return ctls.doubleAfterSplit.isSelected
            }
            override fun isResplitAcesAllowed(): Boolean {
                return ctls.resplitAces.isSelected
            }
            override fun getStrategyData(): ArrayList<String> {
                val dft = super.getStrategyData()
                val ta = ctls.taBasicStrategy
                val text = ta.text
                if (Stringop.isBlank(text)) {
                    ctls.taBasicStrategy.text = Stringop.listToString(dft)
                    return dft
                }
                val rtn = Stringop.stringToList(text)
                if (rtn != dft) {
                    Logz.warn("Custom (or out of date) strategy data in use.  Go to config to reset.")
                }
                return rtn
            }
            override fun getCountingSystemData(): ArrayList<String> {
                val dft = super.getCountingSystemData()
                val ta = ctls.taCountingSystems
                val text = ta.text
                if (Stringop.isBlank(text)) {
                    return dft
                }
                val rtn = Stringop.stringToList( text)
                if (rtn != dft) {
                    Logz.warn("Custom (or out of date) counting system data in use.  Go to config to reset.")
                }
                return rtn
            }
            override fun getSeats(): Int {
                return Stringop.parseInt(ctls.cbSeats.value, super.getSeats())
            }
            override fun getDecks(): Int {
                return Stringop.parseInt(ctls.cbDecks.value, super.getDecks())
            }
            override fun isDealerHitSoft17(): Boolean {
                return ctls.dealerHitsSoft17.isSelected
            }
            override fun isBlackjack6to5(): Boolean {
                return ctls.rule65.isSelected
            }
            override fun isEvenMoneyOnBlackjackVace(): Boolean {
                return ctls.ruleEvenMoney.isSelected
            }
            override fun isLateSurrenderAllowed(): Boolean {
                return ctls.ruleLateSurrender.isSelected
            }
            override fun isEuropeanStyle(): Boolean {
                return ctls.ruleEuropeanStyle.isSelected
            }
            override fun isFastDeal(): Boolean {
                return ctls.ruleFastDeal.isSelected
            }
            override fun isBasicStrategyVariationsOnly(): Boolean {
                return ctls.ruleVariations.isSelected
            }
            override fun isShowCount(): Boolean {
                return ctls.ruleShowCount.isSelected
            }
            override fun toggleShowCount() {
                ctls.ruleShowCount.isSelected = !ctls.ruleShowCount.isSelected
            }
            override fun getLargeBetInWholeDollars(): Int {
                return Stringop.parseInt(
                    ctls.largeBet.text,
                    blackjackConfigDefault.getLargeBetInWholeDollars())
            }
            override fun getTargetStakeInWholeDollars(): Int {
                return Stringop.parseInt(
                    ctls.targetStake.text,
                    blackjackConfigDefault.getTargetStakeInWholeDollars()
                )
            }
            override fun getMinimumBetInWholeDollars(): Int {
                return Stringop.parseInt(
                    ctls.minimumBet.text,
                    blackjackConfigDefault.getMinimumBetInWholeDollars()
                )
            }
            override fun getBetIncrementInWholeDollars(): Int {
                return Stringop.parseInt(
                    ctls.minimumBet.text,
                    blackjackConfigDefault.getBetIncrementInWholeDollars()
                )
            }
            override fun getReloadAmountInWholeDollars(): Int {
                return Stringop.parseInt(
                    ctls.reloadAmount.text,
                    blackjackConfigDefault.getReloadAmountInWholeDollars()
                )
            }
            override fun getStrategicVeryPositiveCount(): Int {
                    val rtn = ctls.countPositive.text
                    if (rtn.isEmpty()) {
                        return super.getStrategicVeryPositiveCount()
                    }
                    return rtn.toInt()
                }
            override fun isBetIdeaDoubleAfterBust(): Boolean {
                return ctls.betIdeaDoubleAfterBust.isSelected
            }
            override fun isBetIdeaLetItRideAfterTwoWins(): Boolean {
                return ctls.betideaLetItRide.isSelected
            }
        }) {
            override fun exec(action: String) {
                super.exec(action)
                ctls.update()
            }
        }
        return rtn
    }
}
