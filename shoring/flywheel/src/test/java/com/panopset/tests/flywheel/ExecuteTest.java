package com.panopset.tests.flywheel;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class ExecuteTest  {

  @Test
  void testExecuteCommand() throws IOException {
    new SimpleTest().comparisonTest("executeTest.txt", "executeTest.txt",
        "executeTestExpected.txt");
  }
}
