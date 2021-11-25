package com.panopset.fxapp;

import java.io.File;
import com.panopset.compat.Fileop;
import com.panopset.compat.Stringop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.DirectoryChooser;

public class PanDirSelectorFX {

  final DirectoryChooser dirChooser = new DirectoryChooser();

  public File getDirectory() {
    String str = inpfile.getText();
    if (str.length() > 1 && str.charAt(0) == '$') {
      str = str.substring(1);
      String rpl = System.getProperty(str);
      if (Stringop.isBlank(rpl)) {
        rpl = System.getenv().get(str);
      }
      if (Stringop.isPopulated(rpl)) {
        str = rpl;
        inpfile.setText(str);
      }
    }
    return new File(str);
  }

  @FXML
  private Button selectdirbtn;

  @FXML
  public TextField inpfile;

  @FXML
  public void initialize() {
    FontManagerFX.register(selectdirbtn);

    selectdirbtn.setOnAction((ActionEvent e) -> {
      if (Fileop.fileExists(getDirectory())) {
        dirChooser.setInitialDirectory(getDirectory());
      }
      File file = dirChooser.showDialog(JavaFXapp.findStage());
      inpfile.setText(Fileop.getCanonicalPath(file));
    });

    inpfile.setOnDragOver((DragEvent event) -> {
      if (event.getGestureSource() != inpfile && event.getDragboard().hasFiles()) {
        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
      }
      event.consume();
    });
    inpfile.setOnDragDropped((DragEvent event) -> {
      Dragboard db = event.getDragboard();
      boolean success = false;
      if (db.hasFiles() && !db.getFiles().isEmpty()) {
        db.getFiles().get(0);
        inpfile.setText(Fileop.getCanonicalPath(db.getFiles().get(0)));
        success = true;
      }
      event.setDropCompleted(success);
      event.consume();
    });
    FontManagerFX.register(inpfile);
  }
}
