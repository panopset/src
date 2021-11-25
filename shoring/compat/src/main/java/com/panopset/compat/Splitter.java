package com.panopset.compat;

import java.util.ArrayList;
import java.util.List;

public class Splitter {
  private int i = 0;
  private final int[] w;

  private Splitter(List<Integer> widths) {
    int[] ints = new int[widths.size()];
    for (Integer x : widths) {
      ints[i++] = x;
    }
    w = ints;
  }

  private Splitter(int... widths) {
    w = widths;
  }

  public static Splitter fixedLengths(String commaSeparatedLineSplitWidths) {
    if (commaSeparatedLineSplitWidths.contains(",")) {
      List<Integer> splitWidths = new ArrayList<>();
      for (String splitWidthStr : commaSeparatedLineSplitWidths.split(",")) {
        splitWidths.add(Stringop.parseInt(splitWidthStr));
      }
      return new Splitter(splitWidths);
    }
    return new Splitter(Stringop.parseInt(commaSeparatedLineSplitWidths));
  }

  public static Splitter fixedLength(int lineSplitWidth) {
    return new Splitter(lineSplitWidth);
  }

  public static Splitter fixedLengths(int... lineSplitWidths) {
    return new Splitter(lineSplitWidths);
  }

  public List<String> split(String s) {
    List<String> rtn = new ArrayList<>();
    while (!recursivelyAccumulate(rtn, s));
    return rtn;
  }

  private boolean recursivelyAccumulate(List<String> list, String s) {
    if (s == null) {
      return true;
    }
    int nextWidth = getNextWidth();
    if (nextWidth == 0) {
      return true;
    }
    if (s.length() <= nextWidth) {
      list.add(s);
      return true;
    }
    list.add(s.substring(0, nextWidth));
    return recursivelyAccumulate(list, s.substring(nextWidth));
  }

  private int getNextWidth() {
    if (i > w.length - 1) {
      i = 0;
    }
    return w[i++];
  }
}
