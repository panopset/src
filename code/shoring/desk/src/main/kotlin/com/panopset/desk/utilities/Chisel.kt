package com.panopset.desk.utilities

import com.panopset.desk.utilities.chisel.ChiselPane
import com.panopset.fxapp.FxDoc
import com.panopset.marin.fx.PanopsetBrandedAppTran
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane

class Chisel: PanopsetBrandedAppTran() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Chisel().go()
        }
    }

    override fun createDynapane(fxDoc: FxDoc): Pane {
        val b: BorderPane = createStandardMenubarBorderPane(fxDoc)
        b.center = ChiselPane(fxDoc).createPane()
        return b
    }

    override fun getApplicationDisplayName(): String {
        return "Chisel"
    }

    override fun getDescription(): String {
        return "Panopset site deployment manager."
    }
}
