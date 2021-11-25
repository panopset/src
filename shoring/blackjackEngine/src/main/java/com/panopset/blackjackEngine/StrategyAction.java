package com.panopset.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DOUBLE;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_HIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_SPLIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_STAND;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_SURRENDER;

public class StrategyAction {

  private final HandPlayer hand;
  private final boolean canDouble;
  private final CasinoRules casinoRules;
  private final boolean singleDeck;

  public StrategyAction(Situation situation, CasinoRules casinoRules) {
    this.hand = situation.getHandPlayer();
    this.canDouble = hand.canDouble();
    this.casinoRules = casinoRules;
    this.singleDeck = casinoRules.decks == 1;
  }

  public String getRecommendedAction(String recommendedActionText) {
    switch (recommendedActionText) {
      case "H":
        return CMD_HIT;
      case "P":
        return CMD_SPLIT;
      case "H*":
        return getHstarRecommendation();
      case "Hx":
        return getHxRecommendation();
      case "Hd":
        return getHdRecommendation();
      case "H@":
        return getHatRecommenation();
      case "HT":
        return getHtRecommendation();
      case "Rs":
        return getRsRecommendation();
      case "Rh":
        return getRhRecommendation();
      case "Sh":
        return getShRecommendation();
      case "Dh":
        return getDhRecommendation();
      case "Ds":
        return getDsRecommendation();
      default:
        return CMD_STAND;
    }
  }

  private String getHstarRecommendation() {
    if (singleDeck) {
      return getDhRecommendation();
    }
    return CMD_HIT;
  }

  private String getHxRecommendation() {
    if (singleDeck) {
      return getDsRecommendation();
    }
    return CMD_HIT;
  }

  private String getHatRecommenation() {
    if (singleDeck) {
      return CMD_SPLIT;
    }
    return CMD_HIT;
  }

  private String getHtRecommendation() {
    if (hand.isInitialDeal()) {
      for (BlackjackCard card : hand.cards) {
        if (card.getHardValue() == 10) {
          return CMD_HIT;
        }
      }
    }
    return CMD_STAND;
  }

  private String getRsRecommendation() {
    if (casinoRules.isLateSurrenderAllowed) {
      return CMD_SURRENDER;
    }
    return CMD_STAND;
  }

  private String getRhRecommendation() {
    if (casinoRules.isLateSurrenderAllowed) {
      return CMD_SURRENDER;
    }
    return CMD_HIT;
  }

  private String getShRecommendation() {
    for (BlackjackCard card : hand.cards) {
      if (card.getHardValue() == 4 || card.getHardValue() == 5) {
        return CMD_STAND;
      }
    }
    return CMD_HIT;
  }

  private String getHdRecommendation() {
    if (canDouble && (casinoRules.dealerHitsDoft17 || casinoRules.decks < 3)) {
      return CMD_DOUBLE;
    }
    return CMD_HIT;
  }

  private String getDsRecommendation() {
    return canDouble ? CMD_DOUBLE : CMD_STAND;
  }

  private String getDhRecommendation() {
    return canDouble ? CMD_DOUBLE : CMD_HIT;
  }
}
