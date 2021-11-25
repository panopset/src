package com.panopset.tests.flywheel;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class DeepListTest {

  /**
   * Test using a list within a list.
   */
  @Test
  void testListCommand() throws IOException {
    SimpleTest.comparisonTest("deepListTest01.txt", "deepListTest.txt",
        "deepListTestExpected.txt");
  }

}
