package com.panopset.desk.games.bj

import com.panopset.fxapp.FontManagerFX
import com.panopset.fxapp.FxDoc
import com.panopset.fxapp.PanComponentFactory.createPanFlowPane
import com.panopset.fxapp.PanComponentFactory.createPanHBox
import javafx.scene.canvas.Canvas
import javafx.scene.control.Tab
import javafx.scene.layout.BorderPane

class TabGame {

    fun createTab(fxDoc: FxDoc, felt: Canvas): Tab {
        val rtn = FontManagerFX.registerTab(fxDoc, Tab("Game"))
        val bp = BorderPane()
        bp.center = createPanHBox(felt)


        if (felt.parent == null) {
            throw RuntimeException()
        }

        bp.bottom = createPanFlowPane(
            // TODO create the buttons here.
        )

        rtn.content = bp
        return rtn
    }
}
