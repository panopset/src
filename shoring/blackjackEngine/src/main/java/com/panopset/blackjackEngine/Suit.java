package com.panopset.blackjackEngine;

public enum Suit {
  HEART("H"), SPADE("S"), DIAMOND("D"), CLUB("C");

  Suit(String key) {
    this.key = key;
  }

  private final String key;

  public String getKey() {
    return key;
  }
}
