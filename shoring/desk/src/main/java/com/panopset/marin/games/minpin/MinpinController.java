package com.panopset.marin.games.minpin;

import java.net.URL;
import java.util.ResourceBundle;

import com.panopset.minpin.back.dna.SourceType;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class MinpinController implements Initializable {

    @FXML
    Label foo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            foo.setText("" + SourceType.INTERNAL.name());
        });
    }
}
