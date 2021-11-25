package com.panopset.marin.games.blackjack;

public class FxmlGlue {
  public BlackjackController bcr;
  public BlackjackGameController bgc;
  public BlackjackConfigController bcf;
  public BlackjackRulesController brc;
  public BlackjackCountingController bcc;
  public BlackjackBasicController bbc;

  boolean isSet() {
    return bcr != null && bgc != null && bcf != null && brc != null && bcc != null && bbc != null;
  }

  @Override
  public String toString() {
    return String.format("%s bcr: bgc: %s bcf: %s brc: %s bcc: %s bbc: %s", status(bcr), status(bgc),
        status(bcf), status(brc), status(bcc), status(bbc));
  }

  private String status(Object o) {
    return o == null ? "null" : "good";
  }
}
