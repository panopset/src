package com.panopset.compat.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.compat.FlagSwitch;

public class FlagSwitchTest {

  @Test
  void test() {
    FlagSwitch flag = new FlagSwitch();
    Assertions.assertTrue(flag.pull());
    Assertions.assertFalse(flag.pull());
    Assertions.assertTrue(flag.pull());
    flag.reset();
    Assertions.assertTrue(flag.pull());
  }
}
