package com.panopset.blackjackEngine;

import java.util.List;

public enum CardCounterImpl {
  
  INSTANCE;

  CountingSystems countingSystems;
  
  public CountingSystems getCountingSystems() {
    if (countingSystems == null) {
      countingSystems = new CountingSystems();
    }
    return countingSystems;
  }
  
  public static void updateByIndex(int index) {
    INSTANCE.getCountingSystems().setSystemByKeyNamePosition(index);
  }

  public static void count(BlackjackCard blackjackCard) {
    getSystem().count(blackjackCard);
  }

  public static int getCount() {
    return getSystem().getCount();
  }

  public static void reset() {
    INSTANCE.getCountingSystems().reset();
  }

  public static void resetCount() {
    getSystem().resetCount();
  }

  public static CountingSystem getSystem() {
    return INSTANCE.getCountingSystems().getSelected();
  }

  public static List<String> getKeyNames() {
    return INSTANCE.getCountingSystems().getKeyNames();
  }

  public static void setConfig(BlackjackConfiguration config) {
    INSTANCE.getCountingSystems().setConfig(config);
  }

}
