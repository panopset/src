package com.panopset.fxapp

import com.panopset.compat.*
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.MenuBar
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.stage.Stage
import java.io.File
import java.util.logging.Level

class FxDoc : Anchor, LogDisplayer {
    var logEntry: LogEntry = LogEntry(AlertColor.GRN, Level.INFO, "")
    var mbs: MutableList<MenuBar> = ArrayList()
    var nodes: MutableList<Node> = ArrayList()
    var tabPanes: MutableList<TabPane> = ArrayList()
    var tabs: MutableList<Tab> = ArrayList()
    val stage: Stage
    val fxDocMessage: FxDocMessage = FxDocMessage(fxDoc = this)
    lateinit var scene: Scene
    private var closingSaveComplete = false

    constructor(panApplication: PanApplication, stage: Stage, file: File) : super(panApplication, file) {
        this.stage = stage
    }

    constructor(panApplication: PanApplication, stage: Stage) : super(panApplication) {
        this.stage = stage
    }

    public override fun updateTitle() {
        stage.title = createWindowTitle()
    }

    fun closeWindow() {
        saveWindow()
        closingSaveComplete = true
        try {
            stage.close()
        } catch (e: Exception) {
            Logz.errorEx(e)
        }
    }

    fun saveWindow() {
        if (closingSaveComplete) {
            return
        }
        pmf.put(
            KEY_WINDOW_DIMS,
            combineDelim("|", stage.x.toString(), stage.y.toString(), stage.width.toString(), stage.height.toString())
        )
        saveDataToFile()
    }

    override fun dspmsg(msg: String) {
        fxDocMessage.setMsg(msg)
    }

    override fun warn(msg: String) {
        dspmsg(msg)
    }

    override fun clear() {
        dspmsg("")
    }

    override fun getPriorMessage(): String {
        return fxDocMessage.getPriorMessage()
    }

    override fun green(msg: String) {
        dspmsg(msg)
    }

    override fun errorMsg(msg: String, throwable: Throwable) {
        fxDocMessage.setErrorMsg(msg)
    }

    override fun errorMsg(msg: String, file: File) {
        fxDocMessage.setErrorMsg(msg)
    }

    override fun errorMsg(msg: String) {
        fxDocMessage.setErrorMsg(msg)
    }

    override fun errorMsg(file: File, throwable: Throwable) {
        fxDocMessage.setErrorMsg("${throwable.message},\n${file.absolutePath}")
    }

    override fun errorEx(throwable: Throwable) {
        fxDocMessage.setErrorMsg("${throwable.message}")
    }

    override fun debug(msg: String) {
        dspmsg(msg)
    }
}
