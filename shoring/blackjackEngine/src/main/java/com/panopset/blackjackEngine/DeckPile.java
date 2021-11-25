package com.panopset.blackjackEngine;

import com.panopset.compat.FlagSwitch;

public enum DeckPile {

  INSTANCE;

  private FlagSwitch flag = new FlagSwitch();

  
  public static void reset() {
    INSTANCE.flag.reset();
  }

  public static boolean pull() {
    return INSTANCE.flag.pull();
  }
}
