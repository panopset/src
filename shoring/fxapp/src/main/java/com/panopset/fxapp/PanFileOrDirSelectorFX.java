package com.panopset.fxapp;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import com.panopset.compat.Fileop;
import com.panopset.compat.Stringop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class PanFileOrDirSelectorFX implements Initializable {

  private final FileChooser fileChooser = new FileChooser();
  private final DirectoryChooser dirChooser = new DirectoryChooser();

  public File getFile() {
    return new File(fod_inpfile.getText());
  }

  public boolean canRead() {
    return Stringop.isPopulated(fod_inpfile.getText()) && getFile().exists() && getFile().canRead();
  }

  @FXML
  private Button fod_selectdirbtn;

  @FXML
  private Button fod_selectfilbtn;

  @FXML
  TextField fod_inpfile;

  @Override
  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    FontManagerFX.register(fod_selectdirbtn);
    FontManagerFX.register(fod_selectfilbtn);
    FontManagerFX.register(fod_inpfile);

    fod_selectdirbtn.setOnAction((ActionEvent e) -> {
        File file = dirChooser.showDialog(JavaFXapp.findStage());
        fod_inpfile.setText(Fileop.getCanonicalPath(file));
      }
    );

    fod_selectfilbtn.setOnAction((ActionEvent e) -> {
        File file = fileChooser.showOpenDialog(JavaFXapp.findStage());
        fod_inpfile.setText(Fileop.getCanonicalPath(file));
      }
    );

    fod_inpfile.setOnDragOver((DragEvent event) -> {
        if (event.getGestureSource() != fod_inpfile && event.getDragboard().hasFiles()) {
          event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
      }
    );
  }
}
