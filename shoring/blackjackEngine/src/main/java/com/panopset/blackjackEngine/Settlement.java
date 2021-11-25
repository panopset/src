package com.panopset.blackjackEngine;

class Settlement {

  private final double blackjackPayoff;
  private final BlackjackMessages msg;
  private final Player player;
  private final HandDealer handDealer;
  private final BlackjackConfiguration blackjackConfiguration;
  private final BlackjackGameEngine bge;

  Settlement(Player player, BlackjackGameEngine bge, BlackjackMessages msg) {
    this.player = player;
    this.msg = msg;
    this.bge = bge;
    this.blackjackConfiguration = bge.getConfig();
    this.handDealer = bge.getCycle().getDealer();
    this.blackjackPayoff = bge.getConfig().isBlackjack6to5() ? 1.2 : 1.5;
  }

  void settlePlayer() {
    for (HandPlayer handPlayer : player.getHands()) {
      settleHand(handPlayer);
    }
  }

  private void settleHand(HandPlayer handPlayer) {
    if (handPlayer.isSelectedEvenMoney()) {
      settleForEvenMoney(handPlayer);
    } else {
      settleNormalHand(handPlayer);
    }
  }

  private void settleForEvenMoney(HandPlayer handPlayer) {
    bge.incrementStreak();
    handPlayer.setMessage(msg.getEvenMsg());
    handPlayer.getWager().setInitialPayoff(handPlayer.getWager().getInitialBet());
  }

  private void settleNormalHand(HandPlayer handPlayer) {
    if (handPlayer.isBusted()) {
      bge.setPriorHandBustedFlag();
    } else {
      settleNonBustedHand(handPlayer);
    }
  }

  private void settleNonBustedHand(HandPlayer handPlayer) {
    if (handDealer.isNatural21() && blackjackConfiguration.isEuropeanStyle()) {
      handPlayer.setMessage(msg.getLostMsg());
      handPlayer.getWager().lost();
    } else {
      settleNotEuropeanStyleBlackjackHand(handPlayer);
    }
  }

  private void settleNotEuropeanStyleBlackjackHand(HandPlayer handPlayer) {
    if (handPlayer.isSurrendered()) {
      handPlayer.getWager().lost();
      handPlayer.getWager().setInitialPayoff(handPlayer.getWager().getInitialBet() / 2);
    } else {
      settleNotSurrenderedHand(handPlayer);
    }
  }

  private void settleNotSurrenderedHand(HandPlayer handPlayer) {
    if (handPlayer.getValue() == handDealer.getValue()) {
      if (handPlayer.isNatural21()) {
        bge.incrementStreak();
        if (handDealer.isNatural21()) {
          handPlayer.setMessage(msg.getPushMsg());
        } else {
          handPlayer.setMessage(msg.getBlackjackMsg());
          handPlayer.getWager().setInitialPayoff((int) (blackjackPayoff * handPlayer.getWager().getInitialBet()));
        }
      } else {
        handPlayer.setMessage(msg.getPushMsg());
        bge.incrementStreak();
      }
    } else if (handDealer.getValue() > 21 || handPlayer.getValue() > handDealer.getValue()) {
      bge.incrementStreak();
      if (handPlayer.isNatural21()) {
        handPlayer.setMessage(msg.getBlackjackMsg());
        handPlayer.getWager().setInitialPayoff((int) (blackjackPayoff * handPlayer.getWager().getInitialBet()));
      } else {
        handPlayer.setMessage(msg.getWonMsg());
        handPlayer.getWager().setInitialPayoff(handPlayer.getWager().getInitialBet());
        handPlayer.getWager().setDoubledPayoff(handPlayer.getWager().getDoubledBet());
      }
    } else {
      handPlayer.setMessage(msg.getLostMsg());
      handPlayer.getWager().lost();
      bge.resetStreak();
    }
  }
}
