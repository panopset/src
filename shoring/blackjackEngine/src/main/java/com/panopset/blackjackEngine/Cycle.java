package com.panopset.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import java.io.StringWriter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Cycle {

  Cycle(final BlackjackGameEngine game, final Shoe shoe, final Strategy strategy) {
    this.strategy = strategy;
    blackjackConfiguration = game.getConfig();
    msg = game.getConfig().getMessages();
    bge = game;
    this.shoe = shoe;
  }

  @Override
  public String toString() {
    StringWriter sw = new StringWriter();
    sw.append("Dealer: ");
    if (dealer != null) {
      sw.append(String.format("%s%n", dealer.cards));
    }
    int i = 0;
    for (Player p : getPlayers()) {
      sw.append(String.format("Player %d: %s%n", i++, p.toString()));
    }
    sw.append(" Bankroll:" + bge.getBankroll());
    return sw.toString();
  }

  private final BlackjackConfiguration blackjackConfiguration;
  private final BlackjackMessages msg;

  private final BlackjackGameEngine bge;

  private final Shoe shoe;

  private final Strategy strategy;

  private HandDealer dealer;

  private List<Player> players;

  public HandDealer getDealer() {
    if (dealer == null) {
      dealer = new HandDealer();
    }
    return dealer;
  }

  public List<Player> getPlayers() {
    if (players == null) {
      players = new CopyOnWriteArrayList<>();
      for (int i = 0; i < blackjackConfiguration.getSeats(); i++) {
        if (blackjackConfiguration.isCountVeryNegative()) {
          bge.triggerShuffleBeforeNextHand();
        }
        Player player = new Player(bge);
        int betAmount = new BetAmountStrategy(bge).adjust();
        player.getHands().get(0).getWager().setInitialBet(betAmount);
        players.add(player);
      }
    }
    return players;
  }

  public Player getActivePlayer() {
    for (Player p : getPlayers()) {
      if (!p.isFinal()) {
        return p;
      }
    }
    return null;
  }

  public String getRecommendedAction() {
    if (getDealer().isFinal() || !getDealer().hasCards()) {
      return CMD_DEAL;
    } else {
      return getStrategy().getRecommendation(getCurrentSituation(getActivePlayerHand()));
    }
  }

  public StrategyLine getStrategyLine() {
    if (getActivePlayer() == null) {
      return null;
    }
    return getStrategy().findStrategyLine(getCurrentSituation(getActivePlayer().getActiveHand()));
  }

  public Situation getActiveSituation() {
    return getCurrentSituation(getActivePlayerHand());
  }

  public Situation getCurrentSituation(HandPlayer hand) {
    if (isDealt()) {
      return new Situation(getDealer().getUpCard(), hand);
    }
    return new Situation(null, hand);
  }

  public Strategy getStrategy() {
    return strategy;
  }

  public void deal() {
    for (Player p : getPlayers()) {
      p.getActiveHand().dealCard(new BlackjackCard(shoe.deal()));
      bge.reportNewHand();
    }
    getDealer().dealCard(new BlackjackCard(shoe.deal(), false));
    for (Player p : getPlayers()) {
      p.getActiveHand().dealCard(new BlackjackCard(shoe.deal()));
    }
    getDealer().dealCard(new BlackjackCard(shoe.deal()));
    checkFor21();
    dealt = true;
    active = true;
  }

  private void checkFor21() {
    for (Player player : getPlayers()) {
      checkFor21(player);
    }
    if (getDealer().getValue() == 21) {
      finish();
    }
  }

  private void checkFor21(Player player) {
    if (getDealer().getUpCard().isAce() && bge.getConfig().isEvenMoneyOnBlackjackVace()) {
      for (HandPlayer playerHand : player.getHands()) {
        if (playerHand.isNatural21()) {
          playerHand.standWithEvenMoney();
        }
      }
    } else {
      for (HandPlayer playerHand : player.getHands()) {
        if (playerHand.getValue() == 21) {
          player.getActiveHand().stand();
        }
      }
    }
  }

  public HandPlayer getActivePlayerHand() {
    Player p = getActivePlayer();
    if (p == null) {
      return null;
    }
    return p.getActiveHand();
  }

  public synchronized void finish() {
    synchronized (bge) {
      completeCycle();
      active = false;
    }
  }

  private void completeCycle() {
    if (!getDealer().isFinal()) {
      prepareSettlement();
    }
  }

  private void prepareSettlement() {
    getDealer().setShowing(0);
    resolvePlayerBlackjacks();
    while (!getDealer().isFinal()) {
      if (getDealer().getValue() < 17) {
        getDealer().dealCard(bge.deal());
      } else if (getDealer().getValue() == 17) {
        if (blackjackConfiguration.isDealerHitSoft17() && getDealer().isSoft()) {
          getDealer().dealCard(bge.deal());
        } else {
          getDealer().stand();
        }
      } else {
        getDealer().stand();
      }
    }
    bge.clearPriorHandBustedFlag();
    collectAndPayChips();
  }

  private void resolvePlayerBlackjacks() {
    boolean allPlayersHaveBlackjack = true;
    for (Player handPlayer : getPlayers()) {
      if (!resolvePlayerHandsBlackjacks(handPlayer)) {
        allPlayersHaveBlackjack = false;
      }
    }
    if (allPlayersHaveBlackjack) {
      dealer.stand();
    }
  }

  private boolean resolvePlayerHandsBlackjacks(Player handPlayer) {
    boolean allHandsAreBlackjack = true;
    for (HandPlayer h : handPlayer.getHands()) {
      if (!h.isDone()) {
        if (getDealer().isNatural21() && !blackjackConfiguration.isEuropeanStyle()) {
          h.stand();
        } else {
          if (h.isNatural21()) {
            h.stand();
          } else {
            allHandsAreBlackjack = false;
          }
        }
      }
    }
    return allHandsAreBlackjack;
  }

  private void collectAndPayChips() {
    for (Player player : getPlayers()) {
      new Settlement(player, bge, msg).settlePlayer();
    }
  }

  private boolean dealt = false;

  private boolean active = false;

  public boolean isActive() {
    return active;
  }

  public boolean isDealt() {
    return dealt;
  }
}
