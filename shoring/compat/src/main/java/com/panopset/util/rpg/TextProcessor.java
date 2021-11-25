package com.panopset.util.rpg;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class TextProcessor {

  private StringWriter sw;

  private StringWriter getStringWriter() {
    if (sw == null) {
      sw = new StringWriter();
    }
    return sw;
  }

  public void setText(String value) {
    sw = new StringWriter();
    for (ProcessListener listener : listeners) {
      listener.setText(value);
    }
    getStringWriter().append(value);
  }

  private final List<ProcessListener> listeners = new ArrayList<>();

  public void addProcessListener(ProcessListener listener) {
    listeners.add(listener);
  }

  public void append(String value) {
    for (ProcessListener listener : listeners) {
      listener.append(value);
    }
    getStringWriter().append(value);
  }

  public String getText() {
    return sw.toString();
  }
}
