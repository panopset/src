package com.panopset.blackjackEngine;

import com.panopset.compat.Streamop;

public enum DefaultResources {
  
  INSTANCE;

  private static final String DEFAULT_STRATEGY_RSRC

      = "/basic.txt";

  private static final String DEFAULT_COUNTING_SYSTEMS_RSRC

      = "/cs.txt";

  public static String getDefaultBasicStrategy() {
    return INSTANCE.getDefaultBasicStrategyPrivate();
  }
  
  private String getDefaultBasicStrategyPrivate() {
    return Streamop.getTextFromStream(this.getClass().getResourceAsStream(DEFAULT_STRATEGY_RSRC));
  }

  public static String getDefaultCountingSystems() {
    return INSTANCE.getDefaultCountingSystemsPrivate();
  }
  
  private String getDefaultCountingSystemsPrivate() {
    return Streamop.getTextFromStream(this.getClass().getResourceAsStream(DEFAULT_COUNTING_SYSTEMS_RSRC));
  }
}
