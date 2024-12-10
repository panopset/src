package com.panopset.desk.games.bj

import com.panopset.blackjackEngine.DefaultResources
import com.panopset.compat.Stringop
import com.panopset.fxapp.*
import com.panopset.fxapp.PanComponentFactory.createPanButton
import com.panopset.fxapp.PanComponentFactory.createPanFlowPane
import com.panopset.fxapp.PanComponentFactory.createPanHBox
import com.panopset.fxapp.PanComponentFactory.createPanLabel
import com.panopset.fxapp.PanComponentFactory.createPanScrollPane
import com.panopset.fxapp.PanComponentFactory.createPanTitledPane
import com.panopset.fxapp.PanComponentFactory.createPanVBox
import javafx.scene.control.Tab
import javafx.scene.layout.BorderPane

class TabConfigCounting(val ctls: BlackjackFxControls) {

    fun createTab(): Tab {
        val rtn = FontManagerFX.registerTab(ctls.fxDoc, Tab("Counting"))
        val topHbox = createPanHBox(
            createPanVBox(
                createPanTitledPane(ctls.fxDoc, "Counting system", ctls.chCountingSystems.getChoiceBoxForDisplayOnly()),
                createPanTitledPane(ctls.fxDoc, "Positive count large bet trigger", ctls.countPositive),
                createPanLabel(ctls.fxDoc, "Setting positive count trigger to 0, or blank, will turn off card counting adjustments."),
                createPanLabel(ctls.fxDoc, "Counting system simulations work best with one player."),
                createPanFlowPane(
                    createPanButton(ctls.fxDoc, {userReset(ctls)}, "Reset to default.", false, ""),
                    createPanLabel(ctls.fxDoc, "  Counting systems:")
                )
            ),
            createPanVBox(
                createPanTitledPane(ctls.fxDoc, "Minimum Bet", ctls.minimumBet),
                createPanTitledPane(ctls.fxDoc, "Large Bet", ctls.largeBet),
                createPanTitledPane(ctls.fxDoc, "Target Stake", ctls.targetStake),
            )
        )
        topHbox.spacing = 5.0
        ctls.taCountingSystems.text = defaultCountingSystems
        val bp = BorderPane()
        bp.top = topHbox
        bp.center = createPanScrollPane(ctls.taCountingSystems)
        rtn.content = bp
        val countingSystemsText = ctls.taCountingSystems.text
        if (!Stringop.isPopulated(countingSystemsText)) {
            userReset(ctls)
        }
        return rtn
    }

    private fun userReset(ctls: BlackjackFxControls) {
        ctls.taCountingSystems.text = defaultCountingSystems
    }
    private val defaultCountingSystems = DefaultResources().defaultCountingSystems
}

