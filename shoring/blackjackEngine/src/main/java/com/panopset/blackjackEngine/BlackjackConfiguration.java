package com.panopset.blackjackEngine;

import java.util.List;

public interface BlackjackConfiguration extends DeckStacker {
  boolean isDoubleAfterSplitAllowed();
  boolean isResplitAcesAllowed();
  int getSeats();
  int getDecks();
  boolean isDealerHitSoft17();
  boolean isBlackjack6to5();
  boolean isEvenMoneyOnBlackjackVace();
  boolean isLateSurrenderAllowed();
  boolean isEuropeanStyle();
  boolean isFastDeal();
  boolean isBasicStrategyVariationsOnly();
  boolean isShowCount();
  void toggleShowCount();
  List<String> getStrategyData();
  List<String> getCountingSystemData();
  int getLargeBetInWholeDollars();
  int getTargetStakeInWholeDollars();
  int getMinimumBetInWholeDollars();
  int getBetIncrementInWholeDollars();
  int getReloadAmountInWholeDollars();
  boolean isCountVeryPositive();
  boolean isCountVeryNegative();
  boolean isBetIdeaDoubleAfterBust();
  boolean isBetIdeaLetItRideAfterTwoWins();
  BlackjackMessages getMessages();
}
