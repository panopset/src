package com.panopset.compat;

public class Envop {

  public static final String FX_ARCH = "FX_ARCH";
  public static final String PAN_TARGET = "PAN_TARGET";
  public static final String PAN_URL = "PAN_URL";

  public static String getPanTarget() {
    return new Envop().get(PAN_TARGET);
  }

  public static String getPanURL() {
    return new Envop().getEnvPanURL();
  }
  
  private String envPanURL;
  
  private String getEnvPanURL() {
    if (envPanURL == null) {
      envPanURL = get(PAN_URL);
      if (Stringop.isBlank(envPanURL)) {
        envPanURL = "https://panopset.com";
      }
    }
    return envPanURL;
  }
  
  private String get(String key) {
    String rtn = System.getenv().get(key);
    return rtn == null ? "" : rtn;
  }
}
