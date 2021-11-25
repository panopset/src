package com.panopset.blackjackEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import com.panopset.blackjackEngine.Strategy.StratCat;

public class StrategyLine {

  private final BlackjackConfiguration config;
  private final String sourceLine;
  private final StratCat stratCat;
  private final String key;

  public StratCat getStratCat() {
    return stratCat;
  }

  public StrategyLine(StratCat stratCat, String sourceLine, BlackjackConfiguration config) {
    this.stratCat = stratCat;
    this.sourceLine = sourceLine;
    this.config = config;
    key = getStrategyLineElements().get(0);
  }

  public String getSource() {
    return sourceLine;
  }

  public String getAction(final Situation situation) {
    return new StrategyAction(situation,
        new CasinoRules(config.isLateSurrenderAllowed(), config.isDealerHitSoft17(),
            config.getDecks()))
                .getRecommendedAction(getTextForDealerUpCard(situation.dealerUpCard));
  }

  private String getTextForDealerUpCard(BlackjackCard card) {
    return getStrategyLineElements().get(card.getSoftValue() - 1);
  }

  private List<String> strategyLineElements;

  private List<String> getStrategyLineElements() {
    if (strategyLineElements == null) {
      StringTokenizer st = new StringTokenizer(sourceLine, " ");
      strategyLineElements = new ArrayList<>();
      while (st.hasMoreElements()) {
        strategyLineElements.add(st.nextToken());
      }
    }
    return strategyLineElements;
  }

  public String getKey() {
    return key;
  }

  @Override
  public String toString() {
    return getSource();
  }
}
