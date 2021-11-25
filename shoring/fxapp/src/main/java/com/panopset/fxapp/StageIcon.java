package com.panopset.fxapp;

import javafx.stage.Stage;

public interface StageIcon {

  default void setFavIcon(Stage stage, AppDDSFX dds) {
    if (dds.getFaviconImage() != null) {
      stage.getIcons().add(dds.getFaviconImage());
    }
  }

}
