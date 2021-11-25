package com.panopset.blackjackEngine;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.panopset.compat.Stringop;
import java.util.Set;
import java.util.TreeSet;

public class CountingSystems extends Configurable {

  public List<String> getKeyNames() {
    return Stringop.arrayToList(getKeys());
  }

  public void setSystemByKeyNamePosition(final int position) {
    setCountingSystem(position);
  }

  public int getKeyNamePosition(final String keyName) {
    return findSelectionNbr(keyName);
  }

  private void loadCountingSystems(final List<String> systemsData) {
    int index = 0;
    for (String s : systemsData) {
      if (s.length() <= (NAME_POS + 1) || s.indexOf("#") == 0) {
        continue;
      }
      String key = s.substring(NAME_POS).trim();
      String dta = s.substring(0, NAME_POS);
      getSystemCatalog().put(key, new CountingSystem(key, dta, index++));
    }
  }

  private static final String DEFAULT_COUNTING_SYSTEM_KEY = "Hi-Lo";
  private static final int NAME_POS = 30;

  private int findSelectionNbr(final String key) {
    for (Entry<String, CountingSystem> e : getSystemCatalog().entrySet()) {
      if (e.getValue().getName().equals(key)) {
        return e.getValue().getSelection();
      }
    }
    return findSelectionNbr(DEFAULT_COUNTING_SYSTEM_KEY);
  }

  public String findSelectionKey(final int selection) {
    for (Entry<String, CountingSystem> e : getSystemCatalog().entrySet()) {
      if (e.getValue().getSelection() == selection) {
        return e.getValue().getName();
      }
    }
    return DEFAULT_COUNTING_SYSTEM_KEY;
  }

  private String[] keys;

  public String[] getKeys() {
    if (keys == null) {
      getSelected();
      Set<CountingSystem> s = Collections.synchronizedSortedSet(new TreeSet<CountingSystem>());
      for (Entry<String, CountingSystem> e : getSystemCatalog().entrySet()) {
        s.add(e.getValue());
      }
      keys = new String[s.size()];
      int i = 0;
      for (CountingSystem cs : s) {
        keys[i++] = cs.getName();
      }
    }
    return keys;
  }

  private CountingSystem selected;

  public CountingSystem getSelected() {
    if (selected == null) {
      selected = getSystemCatalog().get(DEFAULT_COUNTING_SYSTEM_KEY);
    }
    return selected;
  }

  public void setCountingSystem(int position) {
    selected = getSystemCatalog().get(findSelectionKey(position));
  }

  private Map<String, CountingSystem> csc;

  private Map<String, CountingSystem> getSystemCatalog() {
    if (csc == null) {
      csc = new HashMap<>();
      loadCountingSystems(getConfig().getCountingSystemData());
    }
    return csc;
  }

  void resetCount() {
    getSelected().resetCount();
  }

  public void reset() {
    csc = null;
    selected = null;
    resetCount();
  }
}
