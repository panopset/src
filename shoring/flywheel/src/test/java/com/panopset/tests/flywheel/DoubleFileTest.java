package com.panopset.tests.flywheel;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class DoubleFileTest {

  /**
   * Test generating more than one files using Command File.
   */
  @Test
  void testDoubleFileGeneration() throws IOException {
    SimpleTest
        .comparisonTest("doubleFileTest.txt", new String[] { "dft0.txt",
            "dft1.txt" }, new String[] { 
              "dft_expected0.txt",
              "dft_expected1.txt" });
  }
}
