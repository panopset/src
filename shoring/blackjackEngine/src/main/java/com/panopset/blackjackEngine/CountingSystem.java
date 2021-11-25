package com.panopset.blackjackEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import com.panopset.compat.Stringop;

public class CountingSystem implements Comparable<CountingSystem> {

  private int count = 0;
  private final Integer sel;
  private final String nm;
  private final Map<String, Integer> vals = new HashMap<>();

  public CountingSystem(final String name, final String dta, final int selection) {
    sel = selection;
    nm = name;
    StringTokenizer st = new StringTokenizer(dta, " ");
    vals.put("2", Stringop.parseInt(st.nextToken().trim()));
    vals.put("3", Stringop.parseInt(st.nextToken().trim()));
    vals.put("4", Stringop.parseInt(st.nextToken().trim()));
    vals.put("5", Stringop.parseInt(st.nextToken().trim()));
    vals.put("6", Stringop.parseInt(st.nextToken().trim()));
    vals.put("7", Stringop.parseInt(st.nextToken().trim()));
    vals.put("8", Stringop.parseInt(st.nextToken().trim()));
    vals.put("9", Stringop.parseInt(st.nextToken().trim()));
    vals.put("T", Stringop.parseInt(st.nextToken().trim()));
    vals.put("A", Stringop.parseInt(st.nextToken().trim()));
  }

  public String getName() {
    return nm;
  }

  public int getCount() {
    return count;
  }

  public void count(BlackjackCard blackjackCard) {
    if (blackjackCard.isAce()) {
      count = count + vals.get("A");
    } else {
      String valRep = String.format("%d", blackjackCard.getSoftValue());
      if ("10".equals(valRep)) {
        valRep = "T";
      }
      count = count + vals.get(valRep);
    }
  }

  public Integer getSelection() {
    return sel;
  }

  @Override
  public int compareTo(final CountingSystem o) {
    return getSelection().compareTo(o.getSelection());
  }

  @Override
  public boolean equals(final Object o) {
    return (o instanceof CountingSystem)
        && getSelection().equals(((CountingSystem) o).getSelection());
  }

  @Override
  public int hashCode() {
    return getSelection().hashCode();
  }

  public void resetCount() {
    count = 0;
  }
}
