package com.panopset.blackjackEngine;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import com.panopset.compat.Logop;
import com.panopset.compat.Randomop;
import com.panopset.compat.Streamop;
import com.panopset.compat.Stringop;

public class Shoe {

  private final Integer numberOfDecks;
  private final int cut;
  private boolean isBlue;
  private final List<Card> cards = new ArrayList<>();

  public Shoe(int numberOfDecks, int numberOfPlayers) {
    this.numberOfDecks = numberOfDecks;
    cut = 5 * numberOfPlayers;
    shuffle();
  }

  public boolean isBlue() {
    return isBlue;
  }

  public int getNumberOfDecks() {
    return numberOfDecks;
  }

  public void shuffle() {
    cards.clear();
    this.isBlue = DeckPile.pull();
    List<Deck> decks = new ArrayList<>();
    for (int i = 0; i < numberOfDecks; i++) {
      decks.add(new Deck(isBlue));
    }
    while (!decks.isEmpty()) {
      int deckIndex = Randomop.random(0, decks.size() - 1);
      Deck randomDeck = decks.get(deckIndex);
      cards.add(randomDeck.deal());
      if (randomDeck.remaining() == 0) {
        decks.remove(deckIndex);
      }
    }
  }

  public Card deal() {
    if (!stackedDeckForTesting.isEmpty()) {
      Logop.warn("Stacked deck in use, for testing.");
      return stackedDeckForTesting.remove(0);
    }
    if (cards.size() < getCut()) {
      shuffle();
    }
    return cards.remove(0);
  }

  private List<Card> stackedDeckForTesting = new ArrayList<>();
  
  public List<Card> getStackedDeckForTesting() {
    return stackedDeckForTesting;
  }
  
  public boolean isTheDeckStacked() {
    return !stackedDeckForTesting.isEmpty();
  }
  
  public void stackTheDeckFromDeckStacker(DeckStacker deckStacker) {
    stackTheDeckFromEOLseparatedText(deckStacker.getStackedDeck());
  }

  public void stackTheDeckFromEOLseparatedText(String stackedDeck) {
    sddx(Streamop.getLinesFromReader(new StringReader(stackedDeck)));
  }

  public void stackTheDeckFromArray(String[] stackedDeck) {
    sddx(Stringop.arrayToList(stackedDeck));
  }

  public void stackTheDeckFromList(List<Card> stackedDeck) {
    stackedDeckForTesting.clear();
    stackedDeckForTesting.addAll(stackedDeck);
  }

  private void sddx(final List<String> stackedDeck) {
    List<Card> shoeCards = new ArrayList<>();
    for (String s : stackedDeck) {
      CardDefinition cd = CardDefinition.find(s);
      shoeCards.add(new Card(cd));
    }
    stackTheDeckFromList(shoeCards);
  }

  public int remaining() {
    return cards.size();
  }

  public int getCut() {
    return cut;
  }
  
  public String dumpStack() {
    StringWriter sw = new StringWriter();
    for (Card card : stackedDeckForTesting) {
      sw.append(String.format("<<%s", card));
    }
    return sw.toString();
  }
}
