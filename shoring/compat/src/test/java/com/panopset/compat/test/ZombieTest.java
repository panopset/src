package com.panopset.compat.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.compat.Zombie;

public class ZombieTest {
  String foo = "bar";

  @Test
  void test() {
    Zombie zombie = new Zombie();
    Assertions.assertTrue(zombie.isActive());
    Assertions.assertEquals("bar", foo);
    zombie.addStopAction(new Runnable() {
      @Override
      public void run() {
        ZombieTest.this.foo = "bat";
      }
    });
    Assertions.assertEquals("bar", foo);
    Assertions.assertTrue(zombie.isActive());
    Assertions.assertEquals("bar", foo);
    Assertions.assertTrue(zombie.isActive());
    zombie.stop();
    Assertions.assertFalse(zombie.isActive());
    Assertions.assertEquals("bat", foo);
    zombie.stop();
    Assertions.assertFalse(zombie.isActive());
    Assertions.assertEquals("bat", foo);
    zombie.resume();
    Assertions.assertTrue(zombie.isActive());
  }
}
