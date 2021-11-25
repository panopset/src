package com.panopset.flywheel;

import java.util.Map;
import java.util.StringTokenizer;
import com.panopset.compat.Stringop;

public class TokenVariableFactory {

  public void addTokensToMap(Map<String, String> map, String line, String tokens) {
    if (Stringop.isPopulated(tokens)) {
      int i = 0;
      StringTokenizer st = new StringTokenizer(line, tokens);
      while (st.hasMoreTokens()) {
        map.put(String.format("t%d", i++), st.nextToken());
      }
    }
  }
}
