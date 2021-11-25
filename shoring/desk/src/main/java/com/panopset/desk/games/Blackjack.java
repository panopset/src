package com.panopset.desk.games;

import java.net.URL;

import com.panopset.marin.fx.PanopsetBrandedApp;

public class Blackjack extends PanopsetBrandedApp {
  
  public static void main(String... args) {
    new Blackjack().go();
  }

  @Override
  protected final URL createPaneFXMLresourceURL() {
    return this.getClass().getResource("/com/panopset/marin/games/blackjack/Blackjack.fxml");
  }

  @Override
  public String getApplicationDisplayName() {
    return "Blackjack";
  }

  @Override
  public String getDescription() {
    return "Blackjack trainer.";
  }
}
