package com.panopset.desk.games.bj

import com.panopset.fxapp.FontManagerFX
import com.panopset.fxapp.PanComponentFactory.createPanVBox
import com.panopset.fxapp.PanComponentFactory.createPanHBox
import com.panopset.fxapp.PanComponentFactory.createPanLabel
import javafx.scene.control.Tab

class TabConfigRules {
    fun createTab(ctls: BlackjackFxControls): Tab {
        val rtn = FontManagerFX.registerTab(ctls.fxDoc, Tab("Rules"))
        rtn.content = createPanVBox(
            createPanHBox(
                createPanVBox(
                    ctls.dealerHitsSoft17, ctls.resplitAces, ctls.splitAcePlayable, ctls.doubleAfterSplit,
                    createPanHBox(createPanLabel(ctls.fxDoc, "Decks: "), ctls.chDecks)
                ),
                createPanVBox(
                    ctls.rule65, ctls.ruleLateSurrender, ctls.ruleVariations,
                    createPanHBox(createPanLabel(ctls.fxDoc, "Seats: "), ctls.chSeats)
                ),
                createPanVBox(
                    ctls.ruleShowCount, ctls.ruleEuropeanStyle, ctls.ruleFastDeal,
                    createPanHBox(createPanLabel(ctls.fxDoc, "Reload: "), ctls.reloadAmount)
                )
            ),
            createPanHBox(
                createPanVBox(
                    ctls.ruleEvenMoney,
                    createPanLabel(ctls.fxDoc, "* 'Bets' strategy simulations only work with one player.\"")
                )
            )
        )
        return rtn
    }
}
