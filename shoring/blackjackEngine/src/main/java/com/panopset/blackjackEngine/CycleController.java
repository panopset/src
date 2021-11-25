package com.panopset.blackjackEngine;

import java.io.StringWriter;

public class CycleController {

  private Cycle cycle;

  @Override
  public String toString() {
    StringWriter sw = new StringWriter();
    sw.append("cycle:" + cycle);
    return sw.toString();
  }

  public Cycle getCycle(BlackjackGameEngine g, Shoe s, Strategy strategy) {
    if (cycle == null) {
      cycle = new Cycle(g, s, strategy);
    }
    return cycle;
  }

  public synchronized void reset() {
    cycle = null;
  }

  public boolean isDealt() {
    if (cycle == null) {
      return false;
    }
    return cycle.isDealt();
  }

  public boolean isActive() {
    if (cycle == null) {
      return false;
    }
    return cycle.isActive();
  }
}
