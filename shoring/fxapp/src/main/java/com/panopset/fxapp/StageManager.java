package com.panopset.fxapp;

import com.panopset.compat.MathDefaults;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class StageManager implements MathDefaults, StageIcon {

  public void assembleAndShow(PanApplication anchorManager, AppDDSFX dds, FxDoc fxDoc) {
    Stage stage = fxDoc.getStage();
    setFavIcon(stage, dds);
    final Double xloc = notNull(fxDoc.getDouble(FxDoc.KEY_WINDOW_XLOC), 20.0);
    final Double yloc = notNull(fxDoc.getDouble(FxDoc.KEY_WINDOW_YLOC), 20.0);
    final Point2D loc = new Point2D(xloc, yloc);
    Double w = notNull(fxDoc.getDouble(FxDoc.KEY_WINDOW_W), Double.valueOf(PanApplication.DEFAULT_WIDTH));
    Double h = notNull(fxDoc.getDouble(FxDoc.KEY_WINDOW_H), Double.valueOf(PanApplication.DEFAULT_HEIGHT));
    stage.setWidth(w);
    stage.setHeight(h);
    Scene scene = new Scene(dds.createPane(fxDoc), w, h);
    stage.setScene(scene);
    
    boolean stillVisible = false;
    for (Screen screen: Screen.getScreens()) {
      if (screen.getBounds().contains(loc)) {
        stillVisible = true;
        break;
      }
    }
    
    
    if (!stillVisible || xloc == null || yloc == null) {
      stage.centerOnScreen();
    } else {
      stage.setX(xloc);
      stage.setY(yloc);
    }
    fxDoc.loadDataFromFile();

    stage.setOnHiding(event -> {
      fxDoc.saveWindow();
    });

    stage.setOnCloseRequest((event) -> {
      AnchorFactory.remove(fxDoc);
    });

    stage.show();
    
  }
}
