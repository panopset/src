package com.panopset.tests.flywheel;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public final class DepthTest {

  @Test
  void testDepth() throws IOException {
    new SimpleTest().comparisonTest("depthTest.txt", "depthTest01.txt",
        "depthTest01Expected.txt");
    new SimpleTest().comparisonTest("depthTest.txt", "depthTest02.txt",
        "depthTest02Expected.txt");
  }

  @Test
  void testDeeper() throws IOException {
    new SimpleTest().comparisonTest("deepTest.txt", "deepTest.txt",
        "deepTestExpected.txt");
  }
}
