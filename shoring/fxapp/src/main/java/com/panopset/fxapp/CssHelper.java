package com.panopset.fxapp;

import java.net.URL;
import com.panopset.compat.Logop;
import javafx.scene.Parent;

public enum CssHelper {
  
  INSTANCE;

  public static void setStyleFromURL(Parent parent, String styleURL) {
      URL cssURL = INSTANCE.getClass().getResource(styleURL);
      if (cssURL == null) {
        Logop.error("Unable to load css " +  styleURL);
      } else {
        parent.getStylesheets()
            .add(cssURL.toExternalForm());
      }
  }

  public static void setStylesFromURLs(Parent parent, String... styles) {
    parent.getStylesheets().clear();
    for (String cssURLstr : styles) {
      setStyleFromURL(parent, cssURLstr);
    }
  }
}
