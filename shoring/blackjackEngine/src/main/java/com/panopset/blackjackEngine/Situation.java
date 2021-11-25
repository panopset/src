package com.panopset.blackjackEngine;

/**
 * In Blackjack, there is a well defined set of situations. An instance of this class represents a
 * single situation. Each situation can be mapped to an action, based on a strategy.
 *
 */
public class Situation {

  final BlackjackCard dealerUpCard;
  final HandPlayer handPlayer;

  public Situation(final BlackjackCard dealerUpCard, final HandPlayer handPlayer) {
    this.dealerUpCard = dealerUpCard;
    this.handPlayer = handPlayer;
  }

  public HandPlayer getHandPlayer() {
    return handPlayer;
  }

  public BlackjackCard getDealerUpCard() {
    return dealerUpCard;
  }

  @Override
  public String toString() {
    return String.format("Dealer up: %s Hand: %s", dealerUpCard, handPlayer);
  }
}
