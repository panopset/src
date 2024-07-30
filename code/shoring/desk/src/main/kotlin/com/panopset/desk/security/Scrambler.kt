package com.panopset.desk.security

import com.panopset.compat.Logz
import com.panopset.compat.Stringop
import com.panopset.compat.TextScrambler
import com.panopset.fxapp.*
import com.panopset.marin.fx.PanopsetBrandedAppTran
import javafx.scene.control.*
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane

class Scrambler : PanopsetBrandedAppTran() {
    private lateinit var unscrambleButton: Button
    private lateinit var scrambleButton: Button
    private lateinit var taInOutMessage: TextArea
    private lateinit var tfKoi: TextField
    private lateinit var showPwdCheckbox: CheckBox
    private lateinit var passwordField: PasswordField
    private lateinit var passwordShow: TextField

    override fun createDynapane(fxDoc: FxDoc): Pane {
        unscrambleButton = createPanButton(fxDoc, {doUnscramble()}, "_Unscramble", true, "Unscramble the given text, using the given password.")
        scrambleButton = createPanButton(fxDoc, {doScramble()}, "_Scramble", true, "Scramble the given text, using the given password.")
        showPwdCheckbox = createPanCheckBox(fxDoc, "showPwd", "Show")
        passwordField = createPanPwdField()
        passwordShow = createPanTransientField()
        passwordField.textProperty().bindBidirectional(passwordShow.textProperty())
        passwordField.visibleProperty().bind(showPwdCheckbox.selectedProperty().not())
        passwordShow.visibleProperty().bind(showPwdCheckbox.selectedProperty())
        taInOutMessage = createPanTextArea()
        tfKoi = createPanIntTextFieldWithDefaultValue(fxDoc,"koi", "10000",
            "Please enter a numeric value.", "Default is 10000, recommend you change that for added security.")
        val b: BorderPane = createStandardMenubarBorderPane(fxDoc)
        b.center = createCenter()
        return b
    }
    private fun createCenter(): BorderPane {
        val bp = BorderPane()
        bp.top = createPanHBox(unscrambleButton, scrambleButton,
            createPanLabel("  Passphrase:"), createPanStackPane(passwordField, passwordShow),
            showPwdCheckbox)
        bp.center = createPanScrollPane(taInOutMessage)
        bp.bottom = createPanHBox(createPanLabel("   Key obtention interations: "),
            tfKoi)
        return bp
    }

    override fun getApplicationDisplayName(): String {
        return "Scrambler"
    }

    override fun getDescription(): String {
        return "Text scrambler."
    }

    private fun doUnscramble() {
        val value = taInOutMessage.text
        try {
            taInOutMessage.text = createScrambler().decrypt(getTheCurrentPassphrase(), value)
        } catch (e: Exception) {
            Logz.errorEx(e)
        }
    }

    private fun doScramble() {
        val value = taInOutMessage.text
        try {
            taInOutMessage.text = createScrambler().encrypt(getTheCurrentPassphrase(), value)
        } catch (e: Exception) {
            Logz.errorEx(e)
        }
    }

    private fun getTheCurrentPassphrase(): String {
        return if (showPwdCheckbox.isSelected) {
            passwordShow.text
        } else {
            passwordField.text
        }
    }

    private fun createScrambler(): TextScrambler {
        return TextScrambler().withKeyObtentionIters(getKeyObIters())
    }

    private fun getKeyObIters(): Int {
        val strVal: String = tfKoi.text
        if (Stringop.isPopulated(strVal)) {
            val intVal = Stringop.parseInt(strVal)
            if (intVal > 0) {
                return intVal
            }
        }
        return TextScrambler.DEFAULT_KEY_OBTENTION_ITERATIONS
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Scrambler().go()
        }
    }
}
