package com.panopset.fxapp;

import java.util.ArrayList;
import java.util.List;

public enum AnchorFactory {

  INSTANCE;
  
  public static void add(Anchor anchor) {
    INSTANCE.addAnchor(anchor);
  }

  public static Anchor findAnchor() {
    return INSTANCE.getTopAnchor();
  }
  
  public static List<Anchor> getAnchors() {
    return INSTANCE.getCopyOfAnchors();
  }

  public static void remove(Anchor anchor) {
    INSTANCE.drop(anchor);
  }
 
  private synchronized void addAnchor(Anchor anchor) {
    if (anchors.contains(anchor)) {
      return;
    }
    anchors.add(anchor);
  }

  private synchronized Anchor getTopAnchor() {
    if (anchors.isEmpty()) {
      return null;
    }
    return anchors.get(0);
  }

  private synchronized List<Anchor> getCopyOfAnchors() {
    List<Anchor> rtn = new ArrayList<>();
    for (Anchor anchor : anchors) {
      rtn.add(anchor);
    }
    return rtn;
  }

  private synchronized void drop(Anchor anchor) {
    if (anchors.contains(anchor)) {
      if (anchors.size() == 1) {
        JavaFXapp.panExit();
      } else {
        anchors.remove(anchor);
      }
    }
  }

  private final List<Anchor> anchors = new ArrayList<>();
}
