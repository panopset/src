package com.panopset.web;

public class ScramblerInput {
  public String getText() {
    return text;
  }
  public void setText(String text) {
    this.text = text;
  }
  public String getKoi() {
    return koi;
  }
  public void setKoi(String koi) {
    this.koi = koi;
  }
  public String getPassphrase() {
    return passphrase;
  }
  public void setPassphrase(String passphrase) {
    this.passphrase = passphrase;
  }
  private String text;
  private String koi;
  private String passphrase;
}
