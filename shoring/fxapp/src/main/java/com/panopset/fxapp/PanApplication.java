package com.panopset.fxapp;

public interface PanApplication {

  int DEFAULT_WIDTH = 1000;
  int DEFAULT_HEIGHT = 900;

  default int getDefaultWidth() {
    return DEFAULT_WIDTH;
  }

  default int getDefaultHeight() {
    return DEFAULT_HEIGHT;
  }
  
  String getCompanyName();

  String getApplicationDisplayName();

  String getDescription();
  
  default String getApplicationShortName() {
    return this.getClass().getName().toLowerCase();
  }

  default String getFilesKey() {
    return getApplicationShortName() + "_files";
  }
}
