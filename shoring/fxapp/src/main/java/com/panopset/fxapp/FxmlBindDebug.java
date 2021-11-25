package com.panopset.fxapp;

import com.panopset.compat.Logop;

public enum FxmlBindDebug {

  INSTANCE;

  int depth = 0;
  String pad = "";

  public static void updatePad() {
    INSTANCE.doUpdate();
  }

  public static void padId(String id) {
    INSTANCE.doPadId(id);
  }

  public static void reduceDepth() {
    INSTANCE.depth--;
  }

  public static void logmsg(String msg) {
    Logop.debug(String.format("%s%s", INSTANCE.pad, msg));
  }

  void doUpdate() {
    for (int i = 0; i < depth; i++) {
      pad = new StringBuilder().append(pad).append(" ").toString();
    }
    depth++;
  }

  void doPadId(String id) {
    pad = new StringBuilder().append(pad).append(id).append(" ").toString();
  }

}
