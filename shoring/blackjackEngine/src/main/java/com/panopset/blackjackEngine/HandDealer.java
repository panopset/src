package com.panopset.blackjackEngine;

public class HandDealer extends Hand {

  public HandDealer(HandDealer dealer) {
    this();
    for (BlackjackCard card : dealer.cards) {
      this.cards.add(card);
    }
  }

  public HandDealer() {
    super();
  }

  public synchronized BlackjackCard getUpCard() {
    return cards.get(1);
  }

  public synchronized BlackjackCard getDownCard() {
    return cards.get(0);
  }
}
