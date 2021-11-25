package com.panopset.desk.security;

import java.net.URL;
import com.panopset.marin.fx.PanopsetBrandedApp;

public class Checksum extends PanopsetBrandedApp {

  public static void main(String... args) {
    new Checksum().go();
  }

  @Override
  protected final URL createPaneFXMLresourceURL() {
    return this.getClass().getResource("/com/panopset/marin/security/AppChecksumScene.fxml");
  }

  @Override
  public String getApplicationDisplayName() {
    return "Checksum";
  }

  @Override
  public String getDescription() {
    return "Validate files with various checksums you might typically encounter.";
  }
}
