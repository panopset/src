package com.panopset.marin.apps.gr;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.panopset.compat.Logop;
import com.panopset.compat.Stringop;
import com.panopset.fxapp.PanDirSelectorFX;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

 public class GrScene implements Initializable {
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    if (!fxidReturnCharactorsDoNothing.isSelected() && !fxidWindowsToUnix.isSelected() && !fxidUnixToWindows.isSelected()) {
      fxidReturnCharactorsDoNothing.setSelected(true);
    }
  }

  @FXML
  private void handleReplaceAllAction(Event event)  {
    if (fxidReturnCharactorsDoNothing.isSelected()) {
      Stringop.setEol(Stringop.LINE_SEPARATOR);
    } else if (fxidUnixToWindows.isSelected()) {
      Stringop.setEol(Stringop.DOS_RTN);
    } else if (fxidWindowsToUnix.isSelected()) {
      Stringop.setEol(Stringop.LINE_FEED);
    }
    GlobalReplaceProcessor te = new GlobalReplaceProcessor();
    te.setPriorLineMustContain(fxidPriorLineMustContain.getText());
    te.setReplacementLineMustContain(fxidReplacementLineMustContain.getText());
    try {
      te.process(grdirselectController.getDirectory(), fxidFromText.getText(),

          fxidToText.getText(), fxidExtensions.getText(), fxidRegex.getText(), fxidRecursive.isSelected());
    } catch (IOException e) {
      Logop.error(e);
    }
  }

  @FXML
  ToggleGroup fxCrlfOptionsToggleGroup;

  @FXML
  RadioButton fxidReturnCharactorsDoNothing;
  
  @FXML
  RadioButton fxidWindowsToUnix;
  
  @FXML
  RadioButton fxidUnixToWindows;

  @FXML
  CheckBox fxidRecursive;
  
  @FXML
  TextField fxidFromText;

  @FXML
  TextField fxidToText;

  @FXML
  VBox mainbox;

  @FXML
  HBox grdirselect;
  
  @FXML
  PanDirSelectorFX grdirselectController;
  
  @FXML
  PanDirSelectorFX grdirslct;

  @FXML
  TextField fxidRegex;

  @FXML
  TextField fxidExtensions;

  @FXML
  TextField fxidPriorLineMustContain;

  @FXML
  TextField fxidReplacementLineMustContain;

}
