package com.panopset.blackjackEngine;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class Hand {

  public static final int MAX = 21;
  private int value = 0;
  private boolean isSoft;

  public final List<BlackjackCard> cards = new ArrayList<>();

  public boolean isNatural21() {
    return isInitialDeal() && getValue() == MAX;
  }

  public List<BlackjackCard> getCards() {
    return cards;
  }

  public int getHardValueOf(int i) {
    return cards.get(i).getHardValue();
  }

  public boolean hasCards() {
    return !cards.isEmpty();
  }

  public boolean isInitialDeal() {
    return hasCards() && cards.size() == 2;
  }

  public BlackjackCard remove(int i) {
    return cards.remove(i);
  }

  public void setShowing(int i) {
    cards.get(i).show();
  }

  public void dealCard(final BlackjackCard card) {
    cards.add(card);
    if (cards.size() > 1) {
      setValue();
    }
  }

  private void setValue() {
    value = 0;
    int margin = 0;
    isSoft = false;
    for (BlackjackCard card : cards) {
      value += card.getHardValue();
      if (card.isAce()) {
        margin += 10;
      }
    }
    while (value < 12 && margin > 0) {
      value += 10;
      margin -= 10;
      isSoft = true;
    }
  }

  public boolean isDone() {
    return isBusted();
  }

  public boolean isBusted() {
    boolean rtn = value > MAX;
    if (rtn) {
      stand();
    }
    return rtn;
  }

  public boolean isSoft() {
    return isSoft;
  }

  public int getValue() {
    return value;
  }

  public void stand() {
    finalized = true;
  }

  protected boolean finalized = false;

  public boolean isFinal() {
    return finalized;
  }

  @Override
  public String toString() {
    StringWriter sw = new StringWriter();
    sw.append(String.format("*HAND>>* %d: ", value));
    boolean firstTime = true;
    for (BlackjackCard card : getCards()) {
      if (firstTime) {
        firstTime = false;
      } else {
        sw.append(" ");
      }
      sw.append(card.toString());
    }
    return sw.toString();
  }
}
