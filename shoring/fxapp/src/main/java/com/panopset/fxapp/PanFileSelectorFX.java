package com.panopset.fxapp;

import java.io.File;
import com.panopset.compat.Fileop;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

public class PanFileSelectorFX {
  final FileChooser fileChooser = new FileChooser();

  @FXML
  private Button selectfilbtn;

  @FXML
  public TextField inpfile;

  @FXML
  public void initialize() {
    FontManagerFX.register(selectfilbtn);
    selectfilbtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {

        File file = fileChooser.showOpenDialog(JavaFXapp.findStage());
        inpfile.setText(Fileop.getCanonicalPath(file));

      }
    });

    inpfile.setOnDragOver(new EventHandler<DragEvent>() {

      @Override
      public void handle(DragEvent event) {
        if (event.getGestureSource() != inpfile && event.getDragboard().hasFiles()) {
          event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
      }
    });
    inpfile.setOnDragDropped(new EventHandler<DragEvent>() {

      @Override
      public void handle(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles() && db.getFiles().size() > 0) {
          db.getFiles().get(0);
          inpfile.setText(Fileop.getCanonicalPath(db.getFiles().get(0)));
          success = true;
        }
        event.setDropCompleted(success);
        event.consume();
      }
    });

    FontManagerFX.register(inpfile);

  }

}
