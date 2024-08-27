package com.panopset.desk.utilities.chisel

import com.panopset.fxapp.FxDoc
import com.panopset.fxapp.createPanButton
import com.panopset.fxapp.createPanHBox
import com.panopset.fxapp.createPanScrollPane
import javafx.application.Platform
import javafx.scene.control.TextArea
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox

class ChiselPane(private val fxDoc: FxDoc) {
    private val outputTA = TextArea()

    fun createPane(): BorderPane {
        val bp = BorderPane()
        bp.top = createControlPane()
        bp.center = createPanScrollPane(outputTA)
        return bp
    }

    private fun createControlPane(): HBox {
        val cp = createPanHBox(createPanButton(fxDoc, { run { refresh() } } , "refresh", true, ""))
        return cp
    }

    private fun refresh() {
        Platform.runLater {
            //http://www.jcraft.com/jsch/examples/ScpFrom.java.html
            outputTA.text = ""
        }
    }
}
