package com.panopset.fxapp;

import com.panopset.compat.Stringop;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;


public interface TextFormatters {

  /**
   * 0, and positive numbers only.
   */
  default TextFormatter<String> createNumberFilter() {
    return new TextFormatter<>(change -> {
      String text = change.getText();
      if (text == null || "".equalsIgnoreCase(text.trim())) {
        return change;
      }
      if (Stringop.isNumber(text)) {
        return change;
      }
      return null;
    });
  }

  /**
   * Allow negative numbers.
   * https://stackoverflow.com/questions/40472668
   */
  default TextFormatter<Integer> createIntegerFilter() {


    final StringConverter<Integer> converter = new IntegerStringConverter() {
      @Override
      public Integer fromString(String s) {
        if (s.isEmpty()) return 0 ;
        return super.fromString(s);
      }
    };


    return new TextFormatter<Integer>(converter, 0, change -> {
      String newText = change.getControlNewText();
      if (newText.matches("-?([0-9]*)?")) {
        return change;
      } else if ("-".equals(change.getText())) {
        if (change.getControlText().startsWith("-")) {
          change.setText("");
          change.setRange(0, 1);
          change.setCaretPosition(change.getCaretPosition() - 2);
          change.setAnchor(change.getAnchor() - 2);
          return change;
        } else {
          change.setRange(0, 0);
          return change;
        }
      }
      return null;
    });
  }

  default TextFormatter<String> createHexFilter() {
    return new TextFormatter<>(change -> {
      String text = change.getText();
      if (text == null || "".equalsIgnoreCase(text.trim())) {
        return change;
      }
      if (text.matches("[0-9,A-F,a-f]*")) {
        return change;
      }
      return null;
    });
  }
}
