package com.panopset.marin.bootstrap;

import com.panopset.compat.AppVersion;
import com.panopset.compat.Envop;
import com.panopset.compat.Stringop;
import com.panopset.compat.Trinary;
import com.panopset.compat.UrlHelper;

public enum VersionHelper {

  INSTANCE;

  public static String getAvailableVersion() {
    return INSTANCE.getAvailable();
  }

  public static Trinary isReadyToUpdate() {
    return INSTANCE.isReadyToUpdateI();
  }

  private String availVers;

  private String getAvailable() {
    if (availVers == null) {
      availVers = "";
      String panURL = Envop.getPanURL();
      String versJson =
          UrlHelper.getTextFromURL(String.format("%s/gen/json/version.json", panURL));
      if (Stringop.isBlank(versJson)) {
        availVers = OFFLINE;
      } else {
        availVers = versJson.replaceAll("\"", "").replaceAll("\n", "").replaceAll("\r", "");
      }
    }
    return availVers;
  }

  private Trinary isReadyToUpdateI() {
    String thisVersion = AppVersion.getVersion();
    String availVersion = getAvailable();
    if (OFFLINE.equals(availVersion)) {
      return Trinary.ERROR;
    }
    return availVersion.equals(thisVersion) ? Trinary.FALSE : Trinary.TRUE;
  }
  
  private static final String OFFLINE = "Offline";
}
