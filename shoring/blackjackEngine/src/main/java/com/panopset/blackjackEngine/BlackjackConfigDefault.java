package com.panopset.blackjackEngine;

import java.util.List;
import com.panopset.compat.Stringop;

public class BlackjackConfigDefault implements BlackjackConfiguration {

  public static final Integer DEFAULT_INITIAL_STAKE_IWD = 300;
  public static final Integer DEFAULT_MIN_BET_IWD = 5;
  public static final Integer DEFAULT_BET_INCR_IWD = 5;
  public static final Integer DEFAULT_LARGE_BET_IWD = 20;
  public static final Integer DEFAULT_TARGET_STAKE_IWD = 10000;
  public static final Integer DEFAULT_NBR_DECKS = 8;
  public static final Integer DEFAULT_NBR_SEATS = 1;
  private int seats = DEFAULT_NBR_SEATS;
  private int decks = DEFAULT_NBR_DECKS;
  private boolean isFastDeal = false;
  private boolean isVariations = false;

  public static final BlackjackConfigDefault INSTANCE = new BlackjackConfigDefault();

  private boolean isDASallowed = true;
  private boolean isRSAallowed = false;
  private boolean isDLRhitSoft17 = true;
  private boolean isEuropeanStyle = false;
  private boolean isLateSurrenderAllowed = false;
  private boolean isBlackjack6to5 = false;
  private boolean isEvenMoneyOnBlackjackVace = false;

  @Override
  public int getSeats() {
    return seats;
  }

  @Override
  public int getDecks() {
    return decks;
  }

  @Override
  public boolean isFastDeal() {
    return isFastDeal;
  }

  @Override
  public boolean isBasicStrategyVariationsOnly() {
    return isVariations;
  }

  @Override
  public boolean isDoubleAfterSplitAllowed() {
    return isDASallowed;
  }

  @Override
  public boolean isResplitAcesAllowed() {
    return isRSAallowed;
  }

  private List<String> sd;

  @Override
  public List<String> getStrategyData() {
    if (sd == null) {
      sd = Stringop.stringToList(DefaultResources.getDefaultBasicStrategy());
    }
    return sd;
  }

  private List<String> csd;
  
  @Override
  public List<String> getCountingSystemData() {
    if (csd == null) {
      csd = Stringop.stringToList(DefaultResources.getDefaultCountingSystems());
    }
    return csd;
  }

  @Override
  public boolean isDealerHitSoft17() {
    return isDLRhitSoft17;
  }

  @Override
  public boolean isCountVeryPositive() {
    return false;
  }

  @Override
  public boolean isEuropeanStyle() {
    return isEuropeanStyle;
  }

  @Override
  public boolean isCountVeryNegative() {
    return false;
  }

  @Override
  public String getStackedDeck() {
    return "";
  }

  @Override
  public boolean isLateSurrenderAllowed() {
    return isLateSurrenderAllowed;
  }
  
  @Override
  public boolean isBlackjack6to5() {
    return isBlackjack6to5;
  }

  @Override
  public boolean isEvenMoneyOnBlackjackVace() {
    return isEvenMoneyOnBlackjackVace;
  }
  
  private boolean showCount = true;
  
  @Override
  public boolean isShowCount() {
    return showCount;
  }

  @Override
  public void toggleShowCount() {
    showCount = !showCount;
  }

  @Override
  public int getLargeBetInWholeDollars() {
    return DEFAULT_LARGE_BET_IWD;
  }

  @Override
  public int getTargetStakeInWholeDollars() {
    return DEFAULT_TARGET_STAKE_IWD;
  }

  @Override
  public int getMinimumBetInWholeDollars() {
    return DEFAULT_MIN_BET_IWD;
  }

  @Override
  public int getBetIncrementInWholeDollars() {
    return DEFAULT_BET_INCR_IWD;
  }

  @Override
  public int getReloadAmountInWholeDollars() {
    return DEFAULT_INITIAL_STAKE_IWD;
  }

  BlackjackMessages messages;
  
  @Override
  public BlackjackMessages getMessages() {
    if (messages == null) {
      messages = new BlackjackMessagesDft();
    }
    return messages;
  }

  @Override
  public boolean isBetIdeaDoubleAfterBust() {
    return false;
  }

  @Override
  public boolean isBetIdeaLetItRideAfterTwoWins() {
    return false;
  }
}
