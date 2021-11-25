package com.panopset.compat;

public class FlagSwitch {
  
  private boolean value = false;

  public boolean pull() {
    value = !value;
    return value;
  }

  public void reset() {
    value = false;
  }

}
