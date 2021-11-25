package com.panopset.flywheel;

import java.io.StringWriter;
import java.util.List;

public class TemplateArray implements TemplateSource {

  private final String[] ia;
  private int index = 0;

  public TemplateArray(final List<String> data) {
    ia = data.toArray(new String[0]);
  }

  public TemplateArray(final String[] data) {
    ia = data;
  }

  @Override
  public boolean isDone() {
    return index >= ia.length;
  }

  @Override
  public String nextRow() {
    return ia[index++];
  }

  @Override
  public void reset() {
    index = 0;
  }

  @Override
  public int getLine() {
    return index;
  }

  @Override
  public String getName() {
    return "";
  }

  @Override
  public String getRaw() {
    StringWriter sw = new StringWriter();
    for (String s : ia) {
      sw.append(s);
      sw.append("\n");
    }
    return sw.toString();
  }
}
