package com.panopset.desk.utilities;

import java.net.URL;
import com.panopset.marin.fx.PanopsetBrandedApp;

public class LowerClass extends PanopsetBrandedApp {

  public static void main(String... args) {
    new LowerClass().go();
  }
  
  @Override
  public String getApplicationDisplayName() {
    return "Lower Class";
  }

  @Override
  public String getDescription() {
    return "Generate a report on minimum JDKs for the class files found in a jar, or a repository directory.";
  }

  @Override
  protected final URL createPaneFXMLresourceURL() {
    return this.getClass().getResource("/com/panopset/marin/utilities/AppLcScene.fxml");
  }
}
