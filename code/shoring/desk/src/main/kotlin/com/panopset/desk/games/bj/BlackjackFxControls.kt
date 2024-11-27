package com.panopset.desk.games.bj

import com.panopset.blackjackEngine.BlackjackConfigDefault
import com.panopset.compat.Stringop
import com.panopset.fxapp.*
import com.panopset.fxapp.PanComponentFactory.createPanCheckBox
import com.panopset.fxapp.PanComponentFactory.createPanChoiceBox
import com.panopset.fxapp.PanComponentFactory.createPanInputTextFieldWithDefaultValue
import com.panopset.fxapp.PanComponentFactory.createPersistentPanTextArea
import javafx.scene.canvas.Canvas
import javafx.scene.control.TextField

class BlackjackFxControls(val fxDoc: FxDoc) {
    val defaultConfig = BlackjackConfigDefault()
    val felt = Canvas()
    val taBasicStrategy = createPersistentPanTextArea(fxDoc, KEY_BASIC_STRATEGY_DATA, "", "", Stringop.arrayToString(defaultConfig.getStrategyData()))
    val taCountingSystems = createPersistentPanTextArea(fxDoc, KEY_COUNTING_SYSTEMS_DATA, "", "Entry with the * in front of the name, is the default.", Stringop.arrayToString(defaultConfig.getCountingSystems().countingSystemsData))
    val chCountingSystems = createPanChoiceBox(fxDoc, KEY_COUNTING_SYSTEMS_CHOICE_BOX, defaultConfig.getCountingSystems().keyNames, defaultConfig.getCountingSystems().defaultKey)
    val countPositive = createPanInputTextFieldWithDefaultValue(fxDoc, KEY_VERY_POSITIVE_COUNT, defaultConfig.getStrategicVeryPositiveCount().toString(), "", "")
    val minimumBet = createPanInputTextFieldWithDefaultValue(fxDoc, KEY_MINIMUM_BET_IN_WHOLE_DOLLARS, defaultConfig.getMinimumBetInWholeDollars().toString(), "", "")
    val incrementBet = createPanInputTextFieldWithDefaultValue(fxDoc, KEY_INCREMENT_BET_IN_WHOLE_DOLLARS, defaultConfig.getBetIncrementInWholeDollars().toString(), "", "")
    val largeBet = createPanInputTextFieldWithDefaultValue(fxDoc, KEY_LARGE_BET_IN_WHOLE_DOLLARS, defaultConfig.getLargeBetInWholeDollars().toString(), "", "")
    val targetStake = createPanInputTextFieldWithDefaultValue(fxDoc, KEY_TARGET_STAKE_IN_WHOLE_DOLLARS, defaultConfig.getTargetStakeInWholeDollars().toString(), "", "")
    val chDecks = createPanChoiceBox(fxDoc, KEY_NUMBER_OF_DECKS, Stringop.listToArrayList(listOf("1", "2", "6", "8")), defaultConfig.getDecks().toString())
    val chSeats = createPanChoiceBox(fxDoc, KEY_NUMBER_OF_SEATS, Stringop.listToArrayList(listOf("1", "2", "3", "4", "5", "6", "7")), defaultConfig.getSeats().toString())
    val ruleEuropeanStyle = createPanCheckBox(fxDoc, KEY_EUROPEAN_STYLE, "European Style.",
        "Player always loses to dealer Blackjack, even in a push.", defaultConfig.isEuropeanStyle())
    val resplitAces = createPanCheckBox(fxDoc, KEY_RESPLIT_ACES, "Re-split Aces", "Re-split aces is very hard to find these days.", defaultConfig.isResplitAcesAllowed())
    val splitAcePlayable = createPanCheckBox(fxDoc, KEY_SPLIT_ACE_PLAYABLE, "Split Ace playable", "", defaultConfig.isSplitAcePlayable())
    val ruleVariations = createPanCheckBox(fxDoc, KEY_BASIC_STRATEGY_VARIATIONS_ONLY, "Variations.", "", defaultConfig.isBasicStrategyVariationsOnly())
    val dealerHitsSoft17 = createPanCheckBox(fxDoc, KEY_DEALER_HITS_SOFT_17, "Dealer hits soft 17.", "", defaultConfig.isDealerHitSoft17())
    val ruleShowCount = createPanCheckBox(fxDoc, KEY_IS_SHOW_COUNT, "Show count.", "", defaultConfig.isShowCount())
    val rule65 = createPanCheckBox(fxDoc, KEY_BLACKJACK_6_TO_5, "Blackjack pays 6 to 5.",
        "Blackjack pays 6 to 5 instead of 3 to 2", defaultConfig.isBlackjack6to5())
    val ruleEvenMoney = createPanCheckBox(fxDoc, "rule_even",
        "Player takes even money, blackjack versus ace.", "All the books say this is a bad idea.",
        defaultConfig.isEvenMoneyOnBlackjackVace())
    val doubleAfterSplit = createPanCheckBox(fxDoc, KEY_DOUBLE_AFTER_SPLIT, "Double after split", "",
        defaultConfig.isDoubleAfterSplitAllowed())
    val ruleLateSurrender = createPanCheckBox(fxDoc, KEY_IS_LATE_SURRENDER_ALLOWED,"Late surrender.", "",
        defaultConfig.isLateSurrenderAllowed())
    val ruleFastDeal = createPanCheckBox(fxDoc, KEY_FAST_DEAL, "Fast deal.",
        "Skip showing hand result and just go to the next deal.  Having this option, by the way, " +
                "is the whole reason I wrote this game.", defaultConfig.isFastDeal())
    val reloadAmount: TextField = createPanInputTextFieldWithDefaultValue(fxDoc, KEY_RELOAD_AMOUNT_IN_WHOLE_DOLLARS,
        defaultConfig.getReloadAmountInWholeDollars().toString(),
        "Enter your initial stake.", "Initial stake, and reload amount, in dollars.")
}


const val KEY_DOUBLE_AFTER_SPLIT = "dblaftrsplit"
const val KEY_RESPLIT_ACES = "rspltaces"
const val KEY_SPLIT_ACE_PLAYABLE = "spltaceplays"
const val KEY_BASIC_STRATEGY_DATA = "taBasic"
const val KEY_COUNTING_SYSTEMS_DATA = "taCountingSystems"
const val KEY_COUNTING_SYSTEMS_CHOICE_BOX  = "cb_counting_systems"
const val KEY_NUMBER_OF_DECKS = "cb_decks"
const val KEY_NUMBER_OF_SEATS = "cb_seats"
const val KEY_DEALER_HITS_SOFT_17 = "dealer_hits_soft_17"
const val KEY_BLACKJACK_6_TO_5 = "rule_65"
const val KEY_EVEN_MONEY_BLACKJACK_VS_ACE = "rule_65"
const val KEY_IS_LATE_SURRENDER_ALLOWED = "rule_ls"
const val KEY_EUROPEAN_STYLE = "rule_ls"
const val KEY_FAST_DEAL = "rule_fd"
const val KEY_BASIC_STRATEGY_VARIATIONS_ONLY = "rule_vs"
const val KEY_IS_SHOW_COUNT = "rule_sc"
const val KEY_MINIMUM_BET_IN_WHOLE_DOLLARS = "minimum_bet"
const val KEY_INCREMENT_BET_IN_WHOLE_DOLLARS = "increment_bet"
const val KEY_LARGE_BET_IN_WHOLE_DOLLARS = "large_bet"
const val KEY_TARGET_STAKE_IN_WHOLE_DOLLARS = "target_stake"
const val KEY_RELOAD_AMOUNT_IN_WHOLE_DOLLARS = "reload_amount"
const val KEY_VERY_POSITIVE_COUNT = "count_positive"
