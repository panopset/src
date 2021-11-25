package com.panopset.compat;

import java.io.StringWriter;

public class JavaVersionChart {

  public static void main(String... args) {
    Logop.dspmsg(getChart());
  }

  public static String getChart() {
    return new JavaVersionChart().print();
  }

  private String print() {
    StringWriter sw = new StringWriter();
    for (MajorVersion mv : MajorVersion.values()) {
      sw.append(Stringop.EOL);
      sw.append(mv.toString());
    }
    return sw.toString();
  }
}
