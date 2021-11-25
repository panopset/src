package com.panopset.flywheel;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import com.panopset.compat.Stringop;

public class ListAudit {
  
  Map<String, List<String>> map = Collections.synchronizedSortedMap(new TreeMap<> ());
  Set<String> names = Collections.synchronizedSortedSet(new TreeSet<>());

  public void add(String name, List<String> lines) {
    if (!names.contains(name)) {
      names.add(name);
    }
    for (String line : lines) {
      List<String> listNames = map.get(line);
      if (listNames == null) {
        listNames = new ArrayList<>();
        listNames.add(name);
        map.put(line, listNames);
      } else {
        if (!listNames.contains(name)) {
          listNames.add(name);
        }
      }
    }
  }

  public List<String> getReport() {
    List<String> rtn = new ArrayList<>();
    rtn.add(getHeaderLine());
    for (Entry<String, List<String>> e : map.entrySet()) {
      StringWriter sw = new StringWriter();
      sw.append(e.getKey());
      for (String name: names) {
        sw.append(",");
        if (e.getValue().contains(name)) {
          sw.append("*");
        }
      }
      rtn.add(sw.toString());
    }
    return rtn;
  }

  public String getReportText() {
    return Stringop.listToString(getReport());
  }
  
  private String getHeaderLine() {
    StringWriter sw = new StringWriter();
    for (String name : names) {
      sw.append(String.format(",%s", name));
    }
    return sw.toString();
  }
}
