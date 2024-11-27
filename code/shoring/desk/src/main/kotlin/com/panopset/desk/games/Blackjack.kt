package com.panopset.desk.games

import com.panopset.PanopsetBranding
import com.panopset.blackjackEngine.BlackjackConfigDefault
import com.panopset.desk.games.bj.BlackjackFxControls
import com.panopset.desk.games.bj.TabConfig
import com.panopset.desk.games.bj.TabGame
import com.panopset.fxapp.ApplicationBranding
import com.panopset.fxapp.ApplicationInfo
import com.panopset.fxapp.BrandedApp
import com.panopset.fxapp.FxDoc
import com.panopset.fxapp.PanComponentFactory.createPanTabPane
import com.panopset.marin.games.blackjack.BlackjackConfigurationFactory
import com.panopset.marin.games.blackjack.BlackjackGameController
import com.panopset.marin.games.blackjack.BlackjackGameEngineFactory
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
        val bp: BorderPane = createStandardMenubarBorderPane(fxDoc)





        ctls = BlackjackFxControls(fxDoc)
        bp.center = createTabPane(ctls)






        val bgeConfig = BlackjackConfigurationFactory.create(fxDoc)

        val bge = BlackjackGameEngineFactory.create(bgeConfig)
        //val countingSystems = bge.countingSystems





        val bgc = BlackjackGameController(ctls, bge)

//        ctls.chCountingSystems.selectionModel.selectedIndexProperty()
//            .addListener { _, _, newIndex ->
//                thread {
//                    val selectedIndex = newIndex.toInt()
//                    countingSystems.setSystemByKeyNamePosition(selectedIndex)
//                    Logz.info(String.format("Counting systems updated to %s.", countingSystems.findSelected().name))
//                }
//            }


// TODO: Verify we don't need this by confirming that Hi-Low is selected on default.
//        ctls.chCountingSystems.selectionModel.select(3)


       // bge.isReady = true

        return bp
    }

    private fun createTabPane(ctls: BlackjackFxControls): TabPane {
        val rtn = createPanTabPane(ctls.fxDoc, "bjMainTabSelected")
        rtn.tabs.add(TabConfig().createTab(ctls))
        rtn.tabs.add(TabGame().createTab(ctls.fxDoc, ctls.felt))
        return rtn
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Blackjack().go()
        }
    }
}
