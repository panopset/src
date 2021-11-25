package com.panopset.blackjackEngine;

public class BlackjackCard {

  private final Card c;
  
  public Card getCard() {
    return c;
  }

  public BlackjackCard(CardDefinition cardDefinition) {
    this(new Card(cardDefinition, true), true);
  }

  public BlackjackCard(Card card) {
    this(card, true);
  }

  public BlackjackCard(Card card, boolean isShowing) {
    c = card;
    if (isShowing) {
      c.show();
      count();
    }
  }

  public BlackjackCard(BlackjackCard card) {
    this(card.getCard(), card.getCard().isShowing());
  }

  public boolean isAce() {
    return c.getFace().getOffset() == 0;
  }

  public int getSoftValue() {
    if (isAce()) {
      return 11;
    }
    return getNonAceValue();
  }

  public int getHardValue() {
    if (isAce()) {
      return 1;
    }
    return getNonAceValue();
  }

  private int getNonAceValue() {
    if (c.getFace().getOffset() < 10) {
      return c.getFace().getOffset() + 1;
    } else {
      return 10;
    }
  }

  private void count() {
    CardCounterImpl.count(this);
  }

  public void show() {
    count();
    c.show();
  }

  @Override
  public String toString() {
    return c.name();
  }
}
