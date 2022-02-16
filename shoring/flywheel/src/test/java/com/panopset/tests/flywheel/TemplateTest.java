package com.panopset.tests.flywheel;

import java.io.IOException;

public final class TemplateTest {

  /**
   * Test template command. See <b>templateTest01.txt</b>.
   */
  public void testTemplateCommand() throws IOException {
    new SimpleTest().comparisonTest("templateTest01.txt", "templateTest.txt",
        "templateTestExpected.txt");
  }

}
