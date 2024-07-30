package com.panopset.fxapp

import com.panopset.compat.Fileop
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.stage.DirectoryChooser
import java.io.File

class PanDirSelectorPanel(fxDoc: FxDoc, fxId: String) {
    fun createDir(): File {
        return File(inputFile.text)
    }

    fun clear() {
        inputFile.text = ""
    }

    val inputFile = createPanInputTextFieldHGrow(fxDoc, fxId, "Please select a directory.",
        "Selected directory full path.")
    val pane = HBox()
    init {
        pane.children.add(createPanButton(fxDoc, {
            val file = DirectoryChooser().showDialog(JavaFXapp.findStage())
            inputFile.text = Fileop.getCanonicalPath(file)
        },"Directory", false, "Select directory"))
        HBox.setHgrow(pane, Priority.ALWAYS)
        pane.children.add(inputFile)
        decorateFileInputTextForMouse(inputFile)
    }
}
