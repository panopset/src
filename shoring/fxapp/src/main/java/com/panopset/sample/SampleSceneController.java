package com.panopset.sample;

import java.net.URL;
import java.util.ResourceBundle;

import com.panopset.fxapp.FontManagerFX;
import com.panopset.fxapp.FontSize;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;

public class SampleSceneController implements Initializable {

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    CheckBox fooBox = new CheckBox("foo");

    //Dynamically created controls need an id, which is used as the FxDoc document key.
    fooBox.setId("foo");

    sampleBox.getChildren().add(fooBox);
  }

  @FXML
  HBox sampleBox;

  public void doSmall(ActionEvent actionEvent) {
    FontManagerFX.setFontSize(FontSize.SMALL);
  }

  public void doMedium(ActionEvent actionEvent) {
    FontManagerFX.setFontSize(FontSize.MEDIUM);
  }

  public void doLarge(ActionEvent actionEvent) {
    FontManagerFX.setFontSize(FontSize.LARGE);
  }

  public void doSuper(ActionEvent actionEvent) {
    FontManagerFX.setFontSize(FontSize.SUPER);
  }

  public void doCinema(ActionEvent actionEvent) {
    FontManagerFX.setFontSize(FontSize.CINEMA);
  }
}
