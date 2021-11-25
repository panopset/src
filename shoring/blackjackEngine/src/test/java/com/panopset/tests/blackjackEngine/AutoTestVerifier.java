package com.panopset.tests.blackjackEngine;

import org.junit.jupiter.api.Assertions;

class AutoTestVerifier {

  int priorHandCount = 0;
  
  void verifyInitialState(int handCount) {
    Assertions.assertEquals(0, handCount);
    Assertions.assertFalse(isHandCountOver1000(handCount));
  }

  void verifyPostAutoRun(int handCount) {
    Assertions.assertTrue(isHandCountOver1000(handCount));
    Assertions.assertEquals(priorHandCount, handCount);
  }

  private boolean isHandCountOver1000(int handCount) {
    return handCount > 1000;
  }
}
