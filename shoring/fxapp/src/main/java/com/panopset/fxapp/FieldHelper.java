package com.panopset.fxapp;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextInputControl;

public enum FieldHelper {
  
  INSTANCE;
  
  public static boolean isPasswordField(TextInputControl field) {
    return INSTANCE.isPwd(field);
  }
  
  private boolean isIdAvailable(TextInputControl field) {
    return field.getId() != null;
  }

  private boolean isPwd(TextInputControl field) {
    if (isIdAvailable(field)) {
      return field instanceof PasswordField || field.getId().toLowerCase().contains("pwdshow");
    }
    return false;
  }

}
