package com.panopset.desk.utilities;

import java.net.URL;
import com.panopset.marin.fx.PanopsetBrandedApp;

public class Flywheel extends PanopsetBrandedApp {

  public static void main(String... args) {
    new Flywheel().go();
  }

  @Override
  public String getApplicationDisplayName() {
    return "Flywheel text utilities.";
  }

  @Override
  public String getDescription() {
    return "Text processing utilities (flywheel, hexdump, global replace, list audit.)";
  }

  @Override
  protected final URL createPaneFXMLresourceURL() {
    return this.getClass().getResource("/com/panopset/marin/utilities/AppFwScene.fxml");
  }
}
