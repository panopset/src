package com.panopset.fxapp;

import java.lang.reflect.Field;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import com.panopset.compat.Logop;
import com.panopset.compat.Stringop;
import javafx.scene.control.Labeled;
import javafx.scene.control.Tab;

public class ReflectorFX {

  public static void bindBundle(Object controller, ResourceBundle resources) {
    if (resources == null) {
      return;
    }
    for (Field fld : controller.getClass().getFields()) {
      try {
        Object obj = fld.get(controller);
        if (obj instanceof Labeled) {
          Labeled ctl = (Labeled) obj;
          String key = fld.getName();
          if (Stringop.isEmpty(key)) {
            continue;
          }
          String txt = "";
          try {
            txt = resources.getString(key);
          } catch (MissingResourceException mre) {
            continue;
          }
          if (!Stringop.isEmpty(txt)) {
            ctl.setText(txt);
          }
        } else if (obj instanceof Tab) {
          Tab ctl = (Tab) obj;
          String key = fld.getName();
          String txt = resources.getString(key);
          if (!Stringop.isEmpty(txt)) {
            ctl.setText(txt);
          }
        }
      } catch (Exception e) {
        Logop.error(e);
      }
    }
  }
}
