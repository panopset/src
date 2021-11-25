package com.panopset.blackjackEngine;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class Player {

  @Override
  public String toString() {
    StringWriter sw = new StringWriter();
    int i = 0;
    for (HandPlayer h : hands) {
      sw.append(String.format("Player #%d: cards:%s value: %d ", i, h.cards, h.getValue()));
      i++;
    }
    return sw.toString();
  }

  public List<HandPlayer> getHands() {
    return hands;
  }

  private final List<HandPlayer> hands = new ArrayList<>();

  public Player(final BlackjackGameEngine bge) {
    hands.add(new HandPlayer(bge));
  }

  public Player(Player player) {
    for (HandPlayer hand : player.getHands()) {
      hands.add(new HandPlayer(hand));
    }
  }

  public synchronized HandPlayer getActiveHand() {
    for (HandPlayer h : hands) {
      if (!h.isFinal()) {
        return h;
      }
    }
    return null;
  }

  public synchronized boolean isFinal() {
    return getActiveHand() == null;
  }

}
