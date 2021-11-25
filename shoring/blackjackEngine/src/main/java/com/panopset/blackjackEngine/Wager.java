package com.panopset.blackjackEngine;

public class Wager {

  public Wager(Wager handWager) {
    initialBet = handWager.getInitialBet();
    initialPayoff = handWager.getInitialPayoff();
    doubledBet = handWager.getDoubledBet();
    doubledPayoff = handWager.getDoubledPayoff();
    isDoubleDowned = handWager.isDoubleDowned;
  }

  public Wager(Bankroll bankroll) {
    b = bankroll;
    b.addHandWager(this);
  }

  private int initialBet;
  private int initialPayoff;
  private int doubledBet;
  private int doubledPayoff;
  private boolean isDoubleDowned;
  
  public int getInitialBet() {
    return initialBet;
  }
  
  public void setInitialBet(int value) {
    if (initialBet > 0) {
      b.add(initialBet);
    }
    b.subtract(value);
    initialBet = value;
  }

  public boolean isDoubledDown() {
    return isDoubleDowned;
  }

  public void lost() {
    initialBet = 0;
    doubledBet = 0;
  }
  
  public int getInitialPayoff() {
    return initialPayoff;
  }
  
  public void setInitialPayoff(int value) {
    initialPayoff = value;
  }
  
  public int getDoubledBet() {
    return doubledBet;
  }
  
  public void doubleDown() {
    b.subtract(initialBet);
    doubledBet = initialBet;
    isDoubleDowned = true;
  }
  
  public int getDoubledPayoff() {
    return doubledPayoff;
  }
  
  public void setDoubledPayoff(int value) {
    doubledPayoff = value;
  }

  private Bankroll b;

  public long getLiveValue() {
    return initialBet + initialPayoff + doubledBet + doubledPayoff;
  }
}
