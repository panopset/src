package com.panopset.fxapp;

import java.io.File;
import javafx.stage.Stage;

public class FxDoc extends Anchor {

  private final Stage stage;

  public FxDoc(PanApplication anchorManager, Stage stage, File file) {
    super(anchorManager, file);
    this.stage = stage;
    //setTitle(Fileop.getCanonicalPath(file));
  }

  public FxDoc(PanApplication anchorManager, Stage stage) {
    super(anchorManager);
    this.stage = stage;
  }

  public Stage getStage() {
    return stage;
  }

  protected final void updateTitle() {
    getStage().setTitle(createWindowTitle());
  }

  public void closeWindow() {
    saveWindow();
    stage.close();
  }

  public void saveWindow() {
    getPersistentMapFile().put(KEY_WINDOW_XLOC, "" + stage.getX());
    getPersistentMapFile().put(KEY_WINDOW_YLOC, "" + stage.getY());
    getPersistentMapFile().put(KEY_WINDOW_W, "" + stage.getWidth());
    getPersistentMapFile().put(KEY_WINDOW_H, "" + stage.getHeight());
    saveDataToFile();
  }
}
