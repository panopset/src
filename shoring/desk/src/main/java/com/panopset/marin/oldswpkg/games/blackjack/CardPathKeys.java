package com.panopset.marin.oldswpkg.games.blackjack;

import com.panopset.blackjackEngine.Card;
import com.panopset.marin.fx.ImageMapCache;
import javafx.scene.image.Image;

public class CardPathKeys {

  private final String basePath;
  private final String ext;
  
  public CardPathKeys(String basePath, String ext) {
    this.basePath = basePath;
    this.ext = ext;
  }

  public Image getImage(Card card) {
    return find(card);
  }

  Image find(Card card) {
    Image rtn = ImageMapCache.get(card.name());
    if (rtn == null) {
      rtn = ImageMapCache.get(card.name(), getPathFor(card));
    }
    return rtn;
  }

  Image find(String id) {
    Image rtn = ImageMapCache.get(id);
    if (rtn == null) {
      rtn = ImageMapCache.get(id, getPathFor(id));
    }
    return rtn;
  }

  String getPathFor(Card card) {
    return getPathFor(getCardSvgName(card));
  }

  String getPathFor(String id) {
    return String.format("%s%s.%s", basePath, id, ext);
  }

  public String getCardSvgName(Card card) {
    return card.getCardDefinition().getKey();
  }

  public Image getBackBlue() {
    return find("1B");
  }

  public Image getBackRed() {
    return find("2B");
  }

}
