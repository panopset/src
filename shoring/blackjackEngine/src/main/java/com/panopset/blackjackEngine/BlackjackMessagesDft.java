package com.panopset.blackjackEngine;

public class BlackjackMessagesDft implements BlackjackMessages {

  @Override
  public String getWonMsg() {
    return "Won";
  }

  @Override
  public String getLostMsg() {
    return "Lost";
  }

  @Override
  public String getPushMsg() {
    return "Push";
  }

  @Override
  public String getEvenMsg() {
    return "Even money";
  }

  @Override
  public String getBlackjackMsg() {
    return "Blackjack";
  }

  @Override
  public String getBustedMsg() {
    return "Busted";
  }

  @Override
  public String getDoubleImpossibleMsg() {
    return "Double not possible here";
  }

  @Override
  public String getSurrenderNotAllowedMsg() {
    return "Surrender not allowed in this casino";
  }

  @Override
  public String getSurrenderImpossibleMsg() {
    return "Surrender not possible here";
  }

  @Override
  public String getHandActiveMsg() {
    return "Hand is still active";
  }

  @Override
  public String getShuffledMsg() {
    return "Shuffled";
  }

  @Override
  public String getPleaseSelectMsg() {
    return "Please select";
  }

  @Override
  public String getDealMsg() {
    return "Deal";
  }

  @Override
  public String getResetMsg() {
    return "Reset";
  }
}
