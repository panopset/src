package com.panopset.flywheel;

public interface TemplateSource {
  boolean isDone();
  String nextRow();
  default String next() {
    String rtn = nextRow();
    boolean lastBackslashIsReal = false;
    if (rtn.length() > 0) {
      if (rtn.length() > 1) {
        String last2 = rtn.substring(rtn.length() - 2);
        if ("\\\\".equals(last2)) {
          lastBackslashIsReal = true;
        }
      }
      rtn = rtn.replaceAll("\\\\\\\\", "\\\\");
      if (!lastBackslashIsReal) {
        String lastChar = rtn.substring(rtn.length() - 1);
        if (!lastBackslashIsReal && "\\".equals(lastChar) && !isDone()) {
          String s = rtn.substring(0, rtn.length() - 1);
          if (isDone()) {
            return s;
          } else {
            return String.format("%s%s", s, next());
          }
        }
      }
    }
    return rtn;
  }
  void reset();
  int getLine();
  String getName();
  String getRaw();
}
