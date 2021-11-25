package com.panopset.fxapp;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public interface AppDDSFX extends PanApplication {
  Image getFaviconImage();
  Pane createPane(FxDoc fxDoc);
}
