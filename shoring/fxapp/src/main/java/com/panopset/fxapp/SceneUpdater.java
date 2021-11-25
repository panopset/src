package com.panopset.fxapp;

import com.panopset.compat.Logop;

/**
 * 
 * Call triggerAnUpdate after user input has changed,
 * override doUpdate to do the update.
 *
 */
public abstract class SceneUpdater {

  protected abstract void doUpdate();

  protected void triggerAnUpdate() {
    needsUpdate = true;
    if (updater == null) {
      createUpdater();
    }
  }
  
  private boolean needsUpdate = false;
  private boolean updating = false;
  private Thread updater;

  private synchronized void synchronizedUpdate() {
    needsUpdate = false;
    updating = true;
    doUpdate();
    updating = false;
  }

  private void submitUpdate() {
    if (!updating) {
      synchronizedUpdate();
    }
  }
  
  private synchronized void createUpdater() {
    if (updater != null) {
      return;
    }
    updater = new Thread(() -> {
      while (JavaFXapp.isActive()) {
        try {
          if (needsUpdate) {
            submitUpdate();
          }
          Thread.sleep(getSleepMillis());
        } catch (InterruptedException e) {
          Logop.warn(e);
          Thread.currentThread().interrupt();
        }
      }
    });
    updater.start();
  }

  private static final int DEFAULT_SLEEP_MILLIS = 1000;
  
  private Integer sleepMillis;
  
  public int getSleepMillis() {
    if (sleepMillis == null) {
      sleepMillis = DEFAULT_SLEEP_MILLIS;
    }
    return sleepMillis;
  }

  public void setSleepMillis(int value) {
    sleepMillis = value;
  }
}
