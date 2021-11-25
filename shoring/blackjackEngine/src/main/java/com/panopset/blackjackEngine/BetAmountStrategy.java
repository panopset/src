package com.panopset.blackjackEngine;

public class BetAmountStrategy {

  private final BlackjackGameEngine bge;
  private final BlackjackConfiguration config;

  public BetAmountStrategy(BlackjackGameEngine bge) {
    this.bge = bge;
    this.config = bge.getConfig();
  }

  public int adjust() {
    int betAmount = bge.getNextBet();
    if (bge.isAutomatic()) {
      if (config.isCountVeryPositive()) {
        betAmount = config.getLargeBetInWholeDollars() * 100;
      }
      if (config.isBetIdeaDoubleAfterBust() && bge.isBustedPriorHand()) {
        betAmount = betAmount * 2;
      }
      if (config.isBetIdeaLetItRideAfterTwoWins() && bge.getStreak() > 1) {
        betAmount = betAmount * 2;
      }
    }
    return betAmount;
  }
}
