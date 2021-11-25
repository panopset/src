package com.panopset.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_AUTO;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_COUNT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DECREASE;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DOUBLE;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_HIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_INCREASE;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_RESET;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_SHUFFLE;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_SPLIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_STAND;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_SURRENDER;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import com.panopset.compat.Logop;
import com.panopset.compat.Stringop;
import com.panopset.compat.Zombie;

public class BlackjackGameEngine {

  private final BlackjackConfiguration blackjackConfiguration;
  private final BlackjackMessages msg;
  private CycleSnapshot currentSnapshot;
  private Zombie zombie;

  public BlackjackGameEngine() {
    this(BlackjackConfigDefault.INSTANCE);
  }

  public BlackjackGameEngine(final BlackjackConfiguration config) {
    this.blackjackConfiguration = config;
    msg = config.getMessages();
    CardCounterImpl.setConfig(config);
    reset();
    config.getStrategyData();
    config.getCountingSystemData();
  }

  public synchronized void exec(String action) {
    performAction(action);
    currentSnapshot = new CycleSnapshot(this, action);
  }

  BlackjackCard deal() {
    return new BlackjackCard(getShoe().deal());
  }

  public BlackjackConfiguration getConfig() {
    return blackjackConfiguration;
  }

  private Strategy strategy;

  private Strategy getStrategy() {
    if (strategy == null) {
      strategy = new Strategy();
      strategy.setConfig(blackjackConfiguration);
    }
    return strategy;
  }

  private Bankroll bankroll;

  public Bankroll getBankroll() {
    if (bankroll == null) {
      bankroll = new Bankroll(getConfig().getReloadAmountInWholeDollars() * 100L);
    }
    return bankroll;
  }

  private void reset() {
    getMetrics().reset();
    ct.reset();
    getBankroll().reset();
    CardCounterImpl.reset();
    shoe = null;
    strategy = null;
    resetNextBet();
    getShoe().shuffle();
    Logop.clear();
  }

  public Zombie getZombie() {
    if (zombie == null) {
      zombie = new Zombie();
    }
    return zombie;
  }

  private synchronized void performAction(String action) {
    if (performAdminAction(action)) {
      return;
    }
    String ra = getCycle().getRecommendedAction();
    if (!isPossibleAction(action, ra)) {
      return;
    }
    String recommendedActionHeader = "";
    String recommendedActionStrategyLine = "";
    if (!action.equals(ra)) {
      StrategyLine sl = getCycle().getStrategyLine();
      recommendedActionStrategyLine = sl.getSource();
      recommendedActionHeader = getStrategy().getHeaderFor(sl.getStratCat());
    }
    if (CMD_DEAL.equals(action)) {
      dealNextHand();
    }
    Player player = checkForFinish();
    if (player != null) {
      HandPlayer handPlayer = player.getActiveHand();
      if (performAction(player, handPlayer, action)) {
        handPlayer.setAction(action);
        player = checkForFinish();
        updateMessages(recommendedActionHeader, recommendedActionStrategyLine);
      }
    }
    reasonThisGameExists(player);
  }
  
  private void dealNextHand() {
    if (isShuffleNeeded()) {
      shuffle();
      CardCounterImpl.resetCount();
    }
    if (getCycle().isDealt()) {
      bankroll.settle();
      ct.reset();
    }
    getCycle().deal();
  }

  /**
   * If you are practicing the basic strategy, you don't care about the outcome of the hand, just
   * stop the game and flag the player if there is a mistake, otherwise just keep going on to the
   * next hand, no need to click deal.
   *
   * @param player Player.
   */
  private void reasonThisGameExists(Player player) {
    if (player == null && getConfig().isFastDeal() && automatic == null
        && "".equals(getMistakeMessage())) {
      exec(CMD_DEAL);
    }
  }

  private Player checkForFinish() {
    Player player = getCycle().getActivePlayer();
    if (player == null) {
      getCycle().finish();
    }
    return player;
  }

  private boolean performAdminAction(String action) {
    clearMessages();
    switch (action) {
      case CMD_RESET:
        reset();
        setDealerMessage(msg.getResetMsg());
        return true;
      case CMD_COUNT:
        blackjackConfiguration.toggleShowCount();
        return true;
      case CMD_SHUFFLE:
        if (ct.isActive()) {
          setDealerMessage(String.format("%s.", msg.getHandActiveMsg()));
          return true;
        }
        shuffle();
        return true;
      case CMD_INCREASE:
        increase();
        return true;
      case CMD_DECREASE:
        decrease();
        return true;
      case CMD_AUTO:
        auto();
        return true;
      default:
        return false;
    }
  }

  private void hit(HandPlayer handPlayer) {
    handPlayer.dealCard(new BlackjackCard(getShoe().deal()));
    if (handPlayer.isBusted()) {
      handPlayer.setMessage(msg.getBustedMsg());
      handPlayer.getWager().lost();
    }
  }

  private void stand(HandPlayer hand) {
    hand.stand();
  }

  private boolean surrender(HandPlayer handPlayer) {
    if (!getConfig().isLateSurrenderAllowed()) {
      setDealerMessage(msg.getSurrenderNotAllowedMsg());
      return false;
    }
    if (!handPlayer.isInitialDeal()) {
      setDealerMessage(msg.getSurrenderImpossibleMsg());
      return false;
    }
    handPlayer.surrender();
    return true;
  }

  private boolean split(Player player, HandPlayer handPlayer) {
    if (!handPlayer.isCardFacesSplittableIncludeMessage()) {
      setDealerMessage(Logop.getStack().getLast().getMessage());
      return false;
    }
    HandPlayer splitHand = new HandPlayer(this);
    splitHand.getWager().setInitialBet(handPlayer.getWager().getInitialBet());
    final BlackjackCard splitCard = handPlayer.remove(1);
    handPlayer.dealCard(deal());
    handPlayer.setSplit();
    splitHand.dealCard(splitCard);
    splitHand.dealCard(deal());
    splitHand.setSplit();
    splitHand.setAction(CMD_SPLIT);
    player.getHands().add(splitHand);
    if (splitCard.isAce() && !getConfig().isResplitAcesAllowed()) {
      handPlayer.stand();
      splitHand.stand();
    }
    return true;
  }

  private boolean dbl(HandPlayer handPlayer) {
    if (!handPlayer.canDouble()) {
      setDealerMessage(msg.getDoubleImpossibleMsg());
      return false;
    }
    handPlayer.dealCard(new BlackjackCard(getShoe().deal()));
    handPlayer.stand();
    handPlayer.getWager().doubleDown();
    return true;
  }

  private boolean performAction(Player player, HandPlayer handPlayer, String action) {
    switch (action) {
      case CMD_SURRENDER:
        return surrender(handPlayer);
      case CMD_HIT:
        hit(handPlayer);
        return true;
      case CMD_DOUBLE:
        return dbl(handPlayer);
      case CMD_SPLIT:
        return split(player, handPlayer);
      case CMD_STAND:
        stand(handPlayer);
        return true;
      default:
        return false;
    }
  }

  private boolean isPossibleAction(String action, String ra) {
    if (CMD_DEAL.equals(action)) {
      if (!CMD_DEAL.equals(ra)) {
        setDealerMessage(String.format("%s.", msg.getHandActiveMsg()));
        return false;
      }
    } else {
      if (CMD_DEAL.equals(ra)) {
        setDealerMessage(String.format("%s L=%s", msg.getPleaseSelectMsg(), msg.getDealMsg()));
        return false;
      }
    }
    return true;
  }

  private void updateMessages(String recommendedActionHeader,
      String recommendedActionStrategyLine) {
    if (Stringop.isPopulated(recommendedActionStrategyLine)) {
      setMistakeHeader(recommendedActionHeader);
      setMistakeMessage(recommendedActionStrategyLine);
      getMetrics().incrementMistakeCount();
    }
  }

  private boolean isShuffleNeeded() {
    return getShoe().remaining() < getShoe().getCut();
  }

  public void setNextBet(final int value) {
    nextBet = value;
  }

  private int getWagerIncrement() {
    return blackjackConfiguration.getBetIncrementInWholeDollars() * 100;
  }

  private void increase() {
    setNextBet(getNextBet() + getWagerIncrement());
    updateInactiveHands();
  }

  private void decrease() {
    setNextBet(getNextBet() - getWagerIncrement());
    updateInactiveHands();
  }

  private void updateInactiveHands() {
    for (Player player : getCycle().getPlayers()) {
      for (HandPlayer handPlayer : player.getHands()) {
        if (!handPlayer.hasCards()) {
          handPlayer.getWager().setInitialBet(getNextBet());
        }
      }
    }
  }

  public final CycleController ct = new CycleController();

  public Cycle getCycle() {
    return ct.getCycle(this, getShoe(), getStrategy());
  }

  private Thread automatic;

  public boolean isAutomatic() {
    return automatic != null;
  }

  private synchronized void auto() {
    if (automatic == null) {
      if (isReadyToRunAutomatically()) {
        automatic = createAutomaticThread();
        automatic.start();
      }
    } else {
      automatic = null;
    }
  }

  private Thread createAutomaticThread() {
    return new Thread(new Runnable() {
      @Override
      public void run() {
        while (isAutomatic() && getZombie().isActive()) {
          String ra = getCycle().getRecommendedAction();
          if (CMD_DEAL.equals(ra)) {
            int targetStake = getConfig().getTargetStakeInWholeDollars() * 100;
            if ((targetStake > 0) && (getBankroll().getChips() >= targetStake)) {
              Logop.green("Target stake of " + Stringop.getDollarString(targetStake)
                  + " reached, automatic execution ended.");
              automatic = null;
              return;
            }
          }
          exec(ra);
        }
      }
    });
  }

  private boolean isReadyToRunAutomatically() {
    if (blackjackConfiguration.isBasicStrategyVariationsOnly()) {
      String message =
          "Please turn off \"Variations\" in the Configuration->Rules tab before running automatically.";
      Logop.warn(message);
      setDealerMessage(message);
      return false;
    }
    return true;
  }

  public void shuffle() {
    getShoe().shuffle();
    CardCounterImpl.resetCount();
    if (getShoe().isTheDeckStacked()) {
      setDealerMessage("Shuffled and stacked deck for debugging");
    } else {
      setDealerMessage(msg.getShuffledMsg());
    }
  }

  private int nextBet;

  private Integer getMinBet() {
    return 100 * blackjackConfiguration.getMinimumBetInWholeDollars();
  }

  Integer getNextBet() {
    if (nextBet == 0) {
      nextBet = getMinBet();
    }
    return nextBet;
  }

  public void resetNextBet() {
    nextBet = getMinBet();
  }

  private Shoe shoe;

  public Shoe getShoe() {
    if (shoe == null) {
      shoe = new Shoe(blackjackConfiguration.getDecks(), blackjackConfiguration.getSeats());
      if (Stringop.isPopulated(blackjackConfiguration.getStackedDeck())) {
        shoe.stackTheDeckFromDeckStacker(blackjackConfiguration);
      }
    }
    return shoe;
  }

  private Metrics metrics;

  public Metrics getMetrics() {
    if (metrics == null) {
      metrics = new Metrics();
    }
    return metrics;
  }

  private String mistakeMessage;

  public String getMistakeMessage() {
    if (mistakeMessage == null) {
      mistakeMessage = "";
    }
    return mistakeMessage;
  }

  private void setMistakeMessage(final String value) {
    mistakeMessage = value;
  }

  private String mistakeHeader;

  public String getMistakeHeader() {
    if (mistakeHeader == null) {
      mistakeHeader = "";
    }
    return mistakeHeader;
  }

  private void setMistakeHeader(final String value) {
    mistakeHeader = value;
  }

  private String dealerMessage;

  public String getDealerMessage() {
    if (dealerMessage == null) {
      dealerMessage = "";
    }
    return dealerMessage;
  }

  public void setDealerMessage(final String value) {
    dealerMessage = value;
  }

  private void clearMessages() {
    setMistakeHeader("");
    setMistakeMessage("");
    setDealerMessage("");
  }

  public List<String> getGameStatusVertical() {
    List<String> rtn = new ArrayList<>();
    StringTokenizer st = new StringTokenizer(getRawGameStatus(), "|");
    while (st.hasMoreElements()) {
      rtn.add(st.nextToken().trim());
    }
    return rtn;
  }

  public String getGameStatusHorizontal() {
    return getRawGameStatus().replace("|", "");
  }

  public List<String> getStatusChipsVertical() {
    List<String> rtn = new ArrayList<>();
    StringTokenizer st = new StringTokenizer(getRawStatusChips(), "|");
    while (st.hasMoreElements()) {
      rtn.add(st.nextToken().trim());
    }
    return rtn;
  }

  public String getStatusChipsHorizontal() {
    return getRawStatusChips().replace("|", "");
  }

  public String getRawGameStatus() {
    StringWriter sw = new StringWriter();
    sw.append("| Stake: " + Stringop.getDollarString(getBankroll().getStakeIncludingHands()));
    sw.append("| Table: " + Stringop.getDollarString(getBankroll().getTableChips()));
    sw.append("|  Tray: " + Stringop.getDollarString(getBankroll().getChips()));
    sw.append("|  Score: " + getMetrics().getHandsSinceLastMistake());
    sw.append(" (" + getMetrics().getHandsSinceLastMistakeRecord() + ")");
    if (getConfig().isShowCount()) {
      sw.append("| ");
      sw.append(CardCounterImpl.getSystem().getName());
      sw.append(": " + CardCounterImpl.getSystem().getCount());
    }
    return sw.toString();
  }

  public String getRawStatusChips() {
    StringWriter sw = new StringWriter();
    sw.append("  reloads: " + getBankroll().getReloadCount());
    sw.append("|  Chips: " + Stringop.getDollarString(getBankroll().getChips()));
    sw.append("|  Next bet: " + Stringop.getDollarString(getNextBet()));
    return sw.toString();
  }

  public void reportNewHand() {
    if (isAutomatic()) {
      getMetrics().reportNewHandAutomatic();
    } else {
      getMetrics().reportNewHand();
    }
  }

  private boolean shuffleFlag = false;

  public void triggerShuffleBeforeNextHand() {
    shuffleFlag = true;
  }

  public boolean isShuffleFlagOn() {
    return shuffleFlag;
  }

  private boolean bustedPriorHand = false;

  public boolean isBustedPriorHand() {
    return bustedPriorHand;
  }

  public void setPriorHandBustedFlag() {
    bustedPriorHand = true;
  }

  public void clearPriorHandBustedFlag() {
    bustedPriorHand = false;
  }

  private int streak = 0;

  public int getStreak() {
    return streak;
  }

  public void incrementStreak() {
    streak++;
  }

  public void resetStreak() {
    streak = 0;
  }

  public synchronized CycleSnapshot getCurrentSnapshot() {
    if (currentSnapshot == null) {
      currentSnapshot = new CycleSnapshot(this, "");
    }
    return currentSnapshot;
  }

  public void stop() {
    getZombie().stop();
  }

  public void resume() {
    getZombie().resume();
  }

  public boolean isActive() {
    return getZombie().isActive();
  }

  public Long getTotalValue() {
    return getBankroll().getStakeIncludingHands();
  }

  public void setAutomaticOnForTesting() {
    automatic = new Thread();
  }

  public void clearAutomaticForTesting() {
    automatic = null;
  }
}
