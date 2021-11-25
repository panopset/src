package com.panopset.blackjackEngine;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Strategy extends Configurable {

  public enum StratCat {
    NONE, SOFT, SPLIT, HARD
  }

  public String getRecommendation(final Situation s) {
    StrategyLine line = findStrategyLine(s);
    return line.getAction(s);
  }

  private Map<StratCat, Map<String, StrategyLine>> sls;
  private String hardHeader = "";
  private String softHeader = "";
  private String splitHeader = "";

  public String getHeaderFor(StratCat cat) {
    switch (cat) {
      case SOFT:
        return softHeader;
      case SPLIT:
        return splitHeader;
      case HARD:
      default:
        return hardHeader;
    }
  }

  public Map<StratCat, Map<String, StrategyLine>> getStrategyLines() {
    if (sls == null) {
      sls = createStrategyLineMap();
      populateStrategyLineMap();
    }
    return sls;
  }

  private void populateStrategyLineMap() {
    StratCat reading = StratCat.NONE;
    for (String s : getConfig().getStrategyData()) {
      if (s.length() < 1 || s.substring(0, 1).equals("#")) {
        continue;
      }
      if (StratCat.HARD.toString().equalsIgnoreCase(s.substring(0, 4))) {
        reading = StratCat.HARD;
        hardHeader = s;
      } else if (StratCat.SOFT.toString().equalsIgnoreCase(s.substring(0, 4))) {
        reading = StratCat.SOFT;
        softHeader = s;
      } else if (StratCat.SPLIT.toString().equalsIgnoreCase(s.substring(0, 5))) {
        reading = StratCat.SPLIT;
        splitHeader = s;
      } else {
        if (StratCat.HARD.equals(reading)) {
          StrategyLine sl = new StrategyLine(StratCat.HARD, s, getConfig());
          sls.get(StratCat.HARD).put(sl.getKey(), sl);
        } else if (StratCat.SOFT.equals(reading)) {
          StrategyLine sl = new StrategyLine(StratCat.SOFT, s, getConfig());
          sls.get(StratCat.SOFT).put(sl.getKey(), sl);
        } else {
          StrategyLine sl = new StrategyLine(StratCat.SPLIT, s, getConfig());
          sls.get(StratCat.SPLIT).put(sl.getKey(), sl);
        }
      }
    }
  }

  public StrategyLine findStrategyLine(Situation s) {
    StrategyLine rtn = null;
    if (shouldSplit(s)) {
      rtn = findSplitStrategyLine(s);
    }
    if (rtn != null) {
      return rtn;
    }
    if (s.getHandPlayer().isSoft()) {
      rtn = findSoftStrategyLine(s);
    }
    if (rtn != null) {
      return rtn;
    }
    return findHardStrategyLine(s);
  }

  private boolean shouldSplit(Situation s) {
    if (s.getHandPlayer().isInitialDeal() && s.getHandPlayer().isCardFacesSplittable()) {
      int v = s.getHandPlayer().getCards().get(0).getHardValue();
      return v != 5 && v != 10;
    }
    return false;
  }

  private StrategyLine findSplitStrategyLine(Situation s) {
    int cv = s.getHandPlayer().getHardValueOf(0);
    String key = "" + cv + "," + cv;
    if (key.equals("1,1")) {
      key = "A,A";
    }
    return getStrategyLines().get(StratCat.SPLIT).get(key);
  }

  private StrategyLine findSoftStrategyLine(Situation s) {
    int v = s.getHandPlayer().getValue();
    String key = (v > 19) ? "20+" : "" + v;
    return getStrategyLines().get(StratCat.SOFT).get(key);
  }


  private StrategyLine findHardStrategyLine(Situation s) {
    String key = "";
    int v = s.getHandPlayer().getValue();
    if (v > 17) {
      key = "18+";
    } else if (v < 8) {
      key = "7";
    } else {
      key = "" + v;
    }
    return getStrategyLines().get(StratCat.HARD).get(key);
  }

  private Map<StratCat, Map<String, StrategyLine>> createStrategyLineMap() {
    Map<StratCat, Map<String, StrategyLine>> rtn = new EnumMap<>(StratCat.class);
    rtn.put(StratCat.HARD, new HashMap<String, StrategyLine>());
    rtn.put(StratCat.SOFT, new HashMap<String, StrategyLine>());
    rtn.put(StratCat.SPLIT, new HashMap<String, StrategyLine>());
    return rtn;
  }
}
