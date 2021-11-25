package com.panopset.flywheel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filter {
  
  public static String findLine(String value) {
    CommandExecute.flywheel.setFindLine(value);
    return "";
  }
  
  public static String findLines(String v0, String v1) {
    CommandExecute.flywheel.setFindLines(v0, v1);
    return "";
  }

  public static String combine(String value) {
    CommandExecute.flywheel.setCombine(value);
    return "";
  }
  
  public static String regex(String regex, String line) {
    resolveRegex(regex, line);
    return "";
  }

  private static void resolveRegex(String regex, String data) {
    if (data == null) {
      CommandExecute.flywheel.stop("data is null");
      return;
    }
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(data);
    int i = 0;
    while (matcher.find()) {
      CommandExecute.flywheel.put(String.format("r%d",  i++), matcher.group(1));
    }
  }
}
