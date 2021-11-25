package com.panopset.blackjackEngine;

public class CasinoRules {

  public final boolean isLateSurrenderAllowed;
  public final boolean dealerHitsDoft17;
  public final int decks;
  
  public CasinoRules(boolean isLateSurrenderAllowed, boolean dealerHitsSoft17, int decks) {
    this.isLateSurrenderAllowed = isLateSurrenderAllowed;
    this.dealerHitsDoft17 = dealerHitsSoft17;
    this.decks = decks;
  }
}
