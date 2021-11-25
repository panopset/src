package com.panopset.fxapp;

import java.net.URL;
import java.util.ResourceBundle;
import com.panopset.compat.Logop;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

public class LogScene implements Initializable {
  public TextArea log_ta;

  @FXML
  Button clear_log;

  @FXML
  Button refresh_log;

  @FXML
  CheckBox debug_mode;

  @FXML
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    FontManagerFX.register(log_ta);
    FontManagerFX.register(clear_log);
    FontManagerFX.register(refresh_log);
    FontManagerFX.register(debug_mode);
    debug_mode.setSelected(Logop.isDebugging());
    debug_mode.setOnAction((event) -> {
      CheckBox cb = (CheckBox) event.getSource();
      if (cb.isSelected()) {
        Logop.turnOnDebugging();
      } else {
        Logop.turnOffDebugging();
      }
    });
    refresh_log.setOnAction((event -> {
      update();
    }));
    clear_log.setOnAction((event -> {
      Logop.clear();
      log_ta.setText("");
    }));
    update();
  }

  private void update() {
    log_ta.setText(Logop.getEntryStackAsText());
  }
}
