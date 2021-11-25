package com.panopset.blackjackEngine;

import java.util.ArrayList;
import java.util.List;
import com.panopset.compat.Randomop;

public final class Deck {

  private final boolean isBlue;

  private List<Card> shuffled = new ArrayList<>();

  public Deck(boolean isBlue) {
    this.isBlue = isBlue;
    shuffle();
  }

  private void shuffle() {
    List<Card> cards = new ArrayList<>();
    for (CardDefinition cd : CardDefinition.values()) {
      cards.add(new Card(cd, isBlue));
    }
    while (!cards.isEmpty()) {
      shuffled.add(cards.remove(Randomop.random(0, cards.size() - 1)));
    }
  }

  public int remaining() {
    return shuffled.size();
  }

  public Card deal() {
    return shuffled.remove(0);
  }
}
