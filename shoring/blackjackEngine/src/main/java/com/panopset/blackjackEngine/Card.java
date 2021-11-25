package com.panopset.blackjackEngine;

public class Card {

  private final CardDefinition cd;
  
  private final boolean isBlue;

  public Card(final CardDefinition cardDefinition) {
    this(cardDefinition, true);
  }

  public Card(final CardDefinition cardDefinition, boolean isBlue) {
    cd = cardDefinition;
    this.isBlue = isBlue;
  }

  private boolean isShowing = false;

  public boolean isShowing() {
    return isShowing;
  }

  public void show() {
    isShowing = true;
  }

  public Face getFace() {
    return cd.getFace();
  }

  public Suit getSuit() {
    return cd.getSuit();
  }
  
  public CardDefinition getCardDefinition() {
    return cd;
  }

  public String name() {
    return cd.name();
  }
  
  @Override
  public String toString() {
    return name();
  }

  public boolean isBlue() {
    return isBlue;
  }
}
