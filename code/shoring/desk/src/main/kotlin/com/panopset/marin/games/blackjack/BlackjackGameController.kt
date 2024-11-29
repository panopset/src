package com.panopset.marin.games.blackjack

import com.panopset.blackjackEngine.BlackjackGameEngine
import com.panopset.blackjackEngine.CMD_AUTO
import com.panopset.blackjackEngine.CMD_RESET
import com.panopset.blackjackEngine.BlackjackGameState
import com.panopset.compat.Zombie
import com.panopset.desk.games.bj.BlackjackFxControls
import com.panopset.fxapp.FontManagerFX
import com.panopset.fxapp.FxDoc
import javafx.animation.AnimationTimer
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.input.KeyEvent
import javafx.scene.paint.Color
import java.util.*
import kotlin.concurrent.thread

class BlackjackGameController(ctls: BlackjackFxControls, val bge: BlackjackGameEngine) {
    val fxDoc: FxDoc = ctls.fxDoc
    var felt = ctls.felt
    private var dirty = false
    private var firstTime = true

    init {
        Zombie.addStopAction { timer.stop() }
        if (bge.bankroll.reloadAmount == 0) {
            bge.frontEndPreInitCheck()
        }
        startPaintCycle()
    }

    private fun handleKey(keyEvent: KeyEvent) {
        val action = keyEvent.text.lowercase(Locale.getDefault())
        if (bge.isAutomaticRunning()) {
            if (action != CMD_AUTO) {
                bge.exec(CMD_AUTO)
                return
            }
        }
        bge.exec(action)
    }

    private var fontSize = 0
    private var paintedSnapshot: BlackjackGameState? = null
    private var timer: AnimationTimer = object : AnimationTimer() {
                    override fun handle(now: Long) {
                        if (now - lastUpdate > 5000000L) {
                            paintFelt()
                            lastUpdate = now
                        }
                    }
                }

    var lastUpdate: Long = 0
    private var oneSecond: Long = 0

    private fun startPaintCycle() {
        Platform.runLater { timer.start() }
    }

    private fun isDirty(): Boolean {
        if (fontSize != FontManagerFX.getSize()) {
            fontSize = FontManagerFX.getSize()
            dirty = true
        }
        if (!dirty) {
            val currentTime = Date().time
            if (currentTime - oneSecond > 1000) {
                oneSecond = currentTime
                dirty = true
            }
        }
        return dirty
    }

    fun update() {
        dirty = true
    }

    private var binding = false
    //var dbgcount = 0L

    private fun paintFelt() {
        if (binding) {
            return
        }
        if (felt.parent == null) {
            return
        }
        val layoutHeight = felt.parent.layoutBounds.height.toInt()
        val layoutWidth = felt.parent.layoutBounds.width.toInt()
        if (layoutHeight < 10 || layoutWidth < 10) {
            return
        }
        felt.width = layoutWidth.toDouble()
        felt.height = layoutHeight.toDouble()
        val g = felt.graphicsContext2D

//            g.fill = Color.BLANCHEDALMOND
//            g.fillRect(0.0, 0.0, layoutWidth.toDouble(), layoutHeight.toDouble())
//            g.fill = Color.DARKGREEN
//            g.fillText("diags ${dbgcount++}", 100.0, 100.0)

        if (!Zombie.isActive) {
            g.fill = Color.DARKRED
            g.fillRect(0.0, 0.0, layoutWidth.toDouble(), layoutHeight.toDouble())
            return
        }
        if (isDirty()) {
            if (firstTime) {
                firstTime = false
                felt.scene.onKeyPressed = EventHandler {
                        keyEvent: KeyEvent -> handleKey(keyEvent)
                }
                thread {
                    bge.exec(CMD_RESET)
                }
            }
        } else {
            return
        }
        FeltPainter().draw(fxDoc, bge.getLatestSnapshot(), g, layoutWidth, layoutHeight)
        dirty = false
        return
    }
}
