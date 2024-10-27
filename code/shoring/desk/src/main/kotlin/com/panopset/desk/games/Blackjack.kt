package com.panopset.desk.games

import com.panopset.PanopsetBranding
import com.panopset.desk.games.bj.BlackjackFxControls
import com.panopset.desk.games.bj.TabConfig
import com.panopset.desk.games.bj.TabGame
import com.panopset.fxapp.ApplicationBranding
import com.panopset.fxapp.ApplicationInfo
import com.panopset.fxapp.BrandedApp
import com.panopset.fxapp.FxDoc
import com.panopset.fxapp.PanComponentFactory.createPanTabPane
import javafx.scene.control.TabPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane

class Blackjack: BrandedApp(
    object: ApplicationInfo {
        override fun getApplicationBranding(): ApplicationBranding {
            return PanopsetBranding()
        }

        override fun getApplicationDisplayName(): String {
            return "Blackjack"
        }

        override fun getDescription(): String {
            return "Blackjack trainer."
        }
    }
) {

    private lateinit var ctls: BlackjackFxControls

    val BLACKJACK_STAKE_KEY = "blackjackStake"
    override fun createDynapane(fxDoc: FxDoc): Pane {
        ctls = BlackjackFxControls(fxDoc)
        val bp: BorderPane = createStandardMenubarBorderPane(fxDoc)
        bp.center = createTabPane(ctls)
        return bp
    }

    private fun createTabPane(ctls: BlackjackFxControls): TabPane {
        val rtn = createPanTabPane(ctls.fxDoc, "bjMainTabSelected")
        rtn.tabs.add(TabConfig().createTab(ctls))
        rtn.tabs.add(TabGame().createTab(ctls))
        return rtn
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Blackjack().go()
        }
    }
}
