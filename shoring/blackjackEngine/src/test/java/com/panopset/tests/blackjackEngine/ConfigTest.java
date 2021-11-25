package com.panopset.tests.blackjackEngine;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_HIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_SHUFFLE;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_STAND;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.BlackjackConfigDefault;
import com.panopset.blackjackEngine.BlackjackConfiguration;
import com.panopset.blackjackEngine.BlackjackGameEngine;

public class ConfigTest extends AbstractBlackjackTest {

  @Test
  void test() {
    BlackjackConfiguration config = new BlackjackConfigDefault();
    Assertions.assertTrue(config.isShowCount());
    config.toggleShowCount();
    Assertions.assertFalse(config.isShowCount());
    config.toggleShowCount();
    Assertions.assertTrue(config.isShowCount());
    Assertions.assertEquals(5, config.getBetIncrementInWholeDollars());
    Assertions.assertEquals(20, config.getLargeBetInWholeDollars());
  }

  /**
   * We want to be able to stack the deck from the configuration, in case a front end wants to give
   * that option to the users.
   */
  @Test
  void deckStackerTest() {

    BlackjackConfiguration config = new BlackjackConfigDefault() {

      @Override
      public String getStackedDeck() {
        return "five_of_clubs\njack_of_hearts\nqueen of clubs\nten_of_hearts\nsix_of_clubs";
      }
    };
    BlackjackGameEngine bge = new BlackjackGameEngine(config);
    bge.exec(CMD_SHUFFLE);
    // Make sure the player knows there's a stacked deck.
    Assertions.assertEquals("Shuffled and stacked deck for debugging", bge.getDealerMessage());
    Assertions.assertTrue(bge.getShoe().isTheDeckStacked());
    bge.exec(CMD_DEAL);
    Assertions.assertTrue(bge.getShoe().isTheDeckStacked());
    bge.exec(CMD_SHUFFLE);
    Assertions.assertEquals("Hand is still active.", bge.getDealerMessage());
    bge.exec(CMD_HIT);
    Assertions.assertFalse(bge.getShoe().isTheDeckStacked());
    bge.exec(CMD_SHUFFLE);
    Assertions.assertEquals("Hand is still active.", bge.getDealerMessage());
    bge.exec(CMD_STAND);
    bge.exec(CMD_SHUFFLE);
    Assertions.assertEquals("Shuffled", bge.getDealerMessage());
  }
}
