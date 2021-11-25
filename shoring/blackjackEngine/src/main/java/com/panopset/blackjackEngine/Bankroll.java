package com.panopset.blackjackEngine;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Your bankroll consists of how many chips you have, and what your stake is.
 * 
 * All numbers are in pennies.
 *
 * Adding chips, and cashing them out, just like real life, does nothing to change your stake,
 * because chips are like cash.
 * 
 * Your stake only changes as you win our lose.
 *
 */
public class Bankroll {

  @Override
  public String toString() {
    StringWriter sw = new StringWriter();
    sw.append("Total Chips: ");
    sw.append("" + getChips());
    sw.append(" stake: ");
    sw.append("" + getStakeIncludingHands());
    sw.append(" tray: ");
    sw.append("" + chips);
    sw.append(" bankroll: ");
    sw.append("" + getStakeOutOfPlay());
    return sw.toString();
  }

  private long reloadAmount;
  private long reloadCount;
  private long chips;

  public Bankroll(long initialChips) {
    reloadAmount = initialChips;
  }

  public Bankroll(Bankroll bankroll) {
    this.chips = bankroll.chips;
    this.reloadCount = bankroll.reloadCount;
    this.reloadAmount = bankroll.reloadAmount;
  }

  private void reload() {
    chips += reloadAmount;
    reloadCount++;
  }

  public long getStakeOutOfPlay() {
    return chips - (reloadAmount * reloadCount);
  }

  public long getTableChips() {
    return chips + getLiveValue();
  }
  
  public long getLiveValue() {
    long liveValue = 0;
    for (Wager handWager: handWagers) {
      liveValue += handWager.getLiveValue();
    }
    return liveValue;
  }
  
  public long getStakeIncludingHands() {
    return chips + getLiveValue() - (reloadAmount * reloadCount);
  }

  public void subtract(int value) {
    chips -= value;
  }

  public void add(int value) {
    chips += value;
  }
  
  public void settle() {
    for (Wager handWager : handWagers) {
      chips += handWager.getLiveValue();
    }
    handWagers.clear();
  }

  public long getChips() {
    if (chips < 1) {
      reload();
    }
    return chips;
  }

  public long getReloadCount() {
    return reloadCount;
  }

  public void reset() {
    chips = 0;
    reloadCount = 0;
    handWagers.clear();
    reload();
  }
  
  private List<Wager> handWagers = new ArrayList<Wager>();
 
  public List<Wager> getHandWagers() {
    return handWagers;
  }

  void addHandWager(Wager handWager) {
    handWagers.add(handWager);
  }
 
}
