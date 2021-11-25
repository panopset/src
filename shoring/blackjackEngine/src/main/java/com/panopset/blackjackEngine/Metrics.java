package com.panopset.blackjackEngine;

/**
 * For the droid game, we're only interested in displaying the number of hands
 * since the last mistake, to save screen real-estate.
 */
public class Metrics {
  private int mistakeCount = 0;
  private int handCount = 0;
  private int handsSinceLastMistake = 0;
  private int handsSinceLastMistakeRecord = 0;
  private boolean userDriven = true;

  public Metrics() {
    
  }

  public Metrics(Metrics metrics) {
    this();
    mistakeCount = metrics.mistakeCount;
    handCount = metrics.handCount;
    handsSinceLastMistake = metrics.handsSinceLastMistake;
    handsSinceLastMistakeRecord = metrics.handsSinceLastMistakeRecord;
    userDriven = metrics.userDriven;
  }

  public void reset() {
    mistakeCount = 0;
    handCount = 0;
    setHandsSinceLastMistake(0);
    userDriven = true;
  }

  public void incrementMistakeCount() {
    mistakeCount++;
    setHandsSinceLastMistake(0);
    userDriven = true;
  }

  public int getMistakeCount() {
    return mistakeCount;
  }

  public int getHandsSinceLastMistake() {
    return handsSinceLastMistake;
  }

  public void setHandsSinceLastMistake(final int value) {
    handsSinceLastMistake = value;
  }

  public void setHandsSinceLastMistakeRecord(final int value) {
    if (!userDriven) {
      return;
    }
    if (handsSinceLastMistakeRecord < value) {
      handsSinceLastMistakeRecord = value;
    }
  }

  public int getHandsSinceLastMistakeRecord() {
    return handsSinceLastMistakeRecord;
  }

  public int getHandCount() {
    return handCount;
  }

  public void reportNewHand() {
    handCount++;
    handsSinceLastMistake++;
    setHandsSinceLastMistakeRecord(handsSinceLastMistake);
  }

  public void reportNewHandAutomatic() {
    userDriven = false;
    reportNewHand();
  }
}
