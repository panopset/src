package com.panopset.compat.test;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.compat.Randomop;

public class RandomopTest {

  @Test
  void test() {
    int found = 0;
    while (found != 7) {
      int result = Randomop.random(1, 3);
      found = verifyInRange1to3(result, found);
    }
  }

  @Test
  void testMinGreaterThanMax() {
    int found = 0;
    while (found != 7) {
      int result = Randomop.random(3, 1);
      found = verifyInRange1to3(result, found);
    }
  }

  @Test
  void testSingle() {
    int result = Randomop.random(0, 0);
    Assertions.assertEquals(Integer.valueOf(0), result);
  }

  @Test
  void testBytes() {
    byte[] ba = new byte[2];
    Randomop.nextBytes(ba);
    Assertions.assertNotNull(ba);
  }

  @Test
  void testNextInt() {
    int x = Randomop.nextInt();
    int lastOne = x;
    x = Randomop.nextInt();
    Assertions.assertNotEquals(lastOne, x);
  }

  @Test
  void testNextLong() {
    long x = Randomop.nextLong();
    long lastOne = x;
    x = Randomop.nextLong();
    Assertions.assertNotEquals(lastOne, x);
  }

  private static int verifyInRange1to3(int result, int found) {
    int rtn = found;
    MatcherAssert.assertThat(4, Matchers.greaterThan(result));
    MatcherAssert.assertThat(0, Matchers.lessThan(result));
    switch (result) {
      case 1:
        rtn |= 1;
        break;
      case 2:
        rtn |= 2;
        break;
      case 3:
        rtn |= 4;
        break;
    }
    return rtn;
  }
}
