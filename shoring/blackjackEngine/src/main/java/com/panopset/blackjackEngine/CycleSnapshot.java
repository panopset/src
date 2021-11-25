package com.panopset.blackjackEngine;

import java.util.ArrayList;
import java.util.List;

public class CycleSnapshot {

  private final Bankroll bankroll;
  private final HandDealer dealer;
  private final List<Player> players = new ArrayList<>();
  private final Metrics metrics;
  private final int nextBet;
  private final String mistakeHeader;
  private final String mistakeMessage;
  private final String dealerMessage;
  private final List<String> gameStatusVertical = new ArrayList<>();
  private final String gameStatusHorizontal;
  private final List<String> statusChipsVertical = new ArrayList<>();
  private final String statusChipsHorizontal;
  private boolean isPainted = false;
  private final String action;

  CycleSnapshot(final BlackjackGameEngine bge, String action) {
    this.action = action;
    bankroll = new Bankroll(bge.getBankroll());
    dealer = new HandDealer(bge.getCycle().getDealer());
    for (Player player : bge.getCycle().getPlayers()) {
      players.add(new Player(player));
    }
    metrics = new Metrics(bge.getMetrics());
    this.nextBet = bge.getNextBet();
    this.mistakeHeader = bge.getMistakeHeader();
    this.mistakeMessage = bge.getMistakeMessage();
    this.dealerMessage = bge.getDealerMessage();
    for (String gsv : bge.getGameStatusVertical()) {
      gameStatusVertical.add(gsv);
    }
    this.gameStatusHorizontal = bge.getGameStatusHorizontal();
    for (String scv : bge.getStatusChipsVertical()) {
      this.statusChipsVertical.add(scv);
    }
    this.statusChipsHorizontal = bge.getStatusChipsHorizontal();
  }

  public Bankroll getBankroll() {
    return bankroll;
  }

  public HandDealer getDealer() {
    return dealer;
  }

  public List<Player> getPlayers() {
    return players;
  }

  public Metrics getMetrics() {
    return metrics;
  }

  public int getNextBet() {
    return nextBet;
  }

  public String getMistakeHeader() {
    return mistakeHeader;
  }

  public String getMistakeMessage() {
    return mistakeMessage;
  }

  public String getDealerMessage() {
    return dealerMessage;
  }

  public List<String> getGameStatusVertical() {
    return gameStatusVertical;
  }

  public String getGameStatusHorizontal() {
    return gameStatusHorizontal;
  }

  public List<String> getStatusChipsVertical() {
    return statusChipsVertical;
  }

  public String getStatusChipsHorizontal() {
    return statusChipsHorizontal;
  }

  public void setPainted() {
    isPainted = true;
  }
  
  public boolean isPainted() {
    return isPainted;
  }
  
  public String getAction() {
    return action;
  }
}
