package com.panopset.desk.games.bj

import com.panopset.blackjackEngine.DefaultResources
import com.panopset.fxapp.*
import com.panopset.fxapp.PanComponentFactory.createPanButton
import com.panopset.fxapp.PanComponentFactory.createPanFlowPane
import com.panopset.fxapp.PanComponentFactory.createPanLabel
import com.panopset.fxapp.PanComponentFactory.createPanScrollPane
import javafx.application.Platform
import javafx.scene.control.Tab
import javafx.scene.layout.BorderPane

class TabConfigBasicStrategy(private val ctls: BlackjackFxControls) {

    fun createTab(): Tab {
        val rtn = FontManagerFX.registerTab(ctls.fxDoc, Tab("Basic Strategy"))
        val bp = BorderPane()
        bp.top = createPanFlowPane(
            createPanButton(ctls.fxDoc, {userReset()}, "Reset", false, "Reset to default values."),
            createPanLabel(ctls.fxDoc, "  Basic Strategy.  Game must be reset (R key on the game) or restarted to pick up changes:")
        )
        bp.center = createPanScrollPane(ctls.taBasicStrategy)
        rtn.content = bp
        userReset()
        return rtn
    }

    private fun userReset() {
        Platform.runLater {
            val dft = DefaultResources().defaultBasicStrategy
            ctls.taBasicStrategy.text = dft
        }
    }
}
