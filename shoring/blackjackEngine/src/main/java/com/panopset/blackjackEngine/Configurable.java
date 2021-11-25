package com.panopset.blackjackEngine;

public class Configurable {

  private BlackjackConfiguration config;
  
  protected BlackjackConfiguration getConfig() {
    if (config == null) {
      config = BlackjackConfigDefault.INSTANCE;
    }
    return config;
  }
  
  public void setConfig(BlackjackConfiguration config) {
    this.config = config;
  }
}
