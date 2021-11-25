package com.panopset.fxapp;

import com.panopset.compat.Stringop;

public enum FontSize {
  SMALL(9, 1, 1.5), MEDIUM(12, 2, 2), LARGE(16, 3, 3), SUPER(24, 4, 4), CINEMA(48, 5, 5);
  private final int val;
  private final double imgRatio;
  private final double svgRatio;

  FontSize(int fontSize, double imgRatio, final double svgRatio) {
    val = fontSize;
    this.imgRatio = imgRatio;
    this.svgRatio = svgRatio;
  }

  public static FontSize findFromName(String fontName) {
    if (!Stringop.isPopulated(fontName)) {
      return DEFAULT_SIZE;
    }
    for (FontSize size : FontSize.values()) {
      if (size.name().equals(fontName)) {
        return size;
      }
    }
    return DEFAULT_SIZE;
  }

  public static FontSize findFromValue(String fontSzAsStr) {
    if (!Stringop.isPopulated(fontSzAsStr)) {
      return DEFAULT_SIZE;
    }
    for (FontSize size : FontSize.values()) {
      if (String.format("%d", size.val).equals(fontSzAsStr)) {
        return size;
      }
    }
    return DEFAULT_SIZE;
  }

  public static FontSize find(final int fontSize) {
    for (FontSize size : FontSize.values()) {
      if (size.getValue() == fontSize) {
        return size;
      }
    }
    for (FontSize size : FontSize.values()) {
      if (size.getValue() > fontSize) {
        return size;
      }
    }
    return DEFAULT_SIZE;
  }

  public int getValue() {
    return val;
  }

  public double getImgRatio() {
    return imgRatio;
  }

  public double getSvgRatio() {
    return svgRatio;
  }
  
  public static final FontSize DEFAULT_SIZE = FontSize.MEDIUM;
}
