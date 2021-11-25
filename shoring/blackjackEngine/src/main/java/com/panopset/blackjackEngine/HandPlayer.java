package com.panopset.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.*;
import com.panopset.compat.Logop;

public class HandPlayer extends Hand {

  private boolean isSplit = false;
  private final BlackjackConfiguration c;

  private Wager wager;
  private boolean surrendered = false;
  private boolean selectedEvenMoney = false;
  private String action;

  public HandPlayer(HandPlayer handPlayer) {
    super();
    c = handPlayer.c;
    isSplit = handPlayer.isSplit;
    finalized = handPlayer.isFinal();
    wager = new Wager(handPlayer.wager);
    surrendered = handPlayer.surrendered;
    selectedEvenMoney = handPlayer.selectedEvenMoney;
    for (BlackjackCard card : handPlayer.cards) {
      getCards().add(card);
    }
    setMessage(handPlayer.getMessage());
    setAction(handPlayer.getAction());
  }

  public HandPlayer(final BlackjackGameEngine bge) {
    super();
    c = bge.getConfig();
    wager = new Wager(bge.getBankroll());
  }

  @Override
  public boolean isNatural21() {
    if (isSplit()) {
      return false;
    }
    return super.isNatural21();
  }

  public boolean isSplit() {
    return isSplit;
  }

  public void setSplit() {
    isSplit = true;
  }

  public Wager getWager() {
    return wager;
  }

  private String msg;

  public void setMessage(String message) {
    msg = message;
  }
  

  @Override
  public void dealCard(final BlackjackCard card) {
    super.dealCard(card);
    setMessage("");
  }

  public String getMessage() {
    if (msg == null) {
      msg = "Please select deal (L)";
    }
    return msg;
  }

  public boolean canDouble() {
    if (!isInitialDeal()) {
      return false;
    }
    if (isSplit()) {
      return c.isDoubleAfterSplitAllowed();
    } else {
      return true;
    }
  }

  public boolean isSurrendered() {
    return surrendered;
  }

  public void surrender() {
    surrendered = true;
    stand();
  }

  public boolean isSelectedEvenMoney() {
    return selectedEvenMoney;
  }

  @Override
  public boolean isDone() {
    if (super.isDone()) {
      return true;
    }
    return isSurrendered();
  }

  public void standWithEvenMoney() {
    stand();
    selectedEvenMoney = true;
  }

  public boolean isCardFacesSplittable() {
    return isCardFacesSplittable(false);
  }

  public boolean isCardFacesSplittableIncludeMessage() {
    return isCardFacesSplittable(true);
  }

  private boolean isCardFacesSplittable(boolean includeMessage) {
    if (cards.size() != 2) {
      if (includeMessage) {
        Logop.warn("Can't split a hit hand");
      }
      return false;
    } else if (!isFaceMatch()) {
      if (includeMessage) {
        Logop.warn("Can't split cards that don't have the same face");
      }
      return false;
    }
    return true;
  }

  private boolean isFaceMatch() {
    return cards.get(0).getCard().getFace() == cards.get(1).getCard().getFace();
  }

  public boolean isDoubleDowned() {
    return getWager().isDoubledDown();
  }
  
  public void setAction(String value) {
    action = value;
  }
  
  public String getAction() {
    if (action == null) {
      action = CMD_DEAL;
    }
    return action;
  }
}
