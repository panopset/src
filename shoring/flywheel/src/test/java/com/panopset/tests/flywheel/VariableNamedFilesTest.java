package com.panopset.tests.flywheel;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public final class VariableNamedFilesTest {

  /**
   * Test variable named template. See <b>variableNamedTemplateTest.txt</b>.
   */
  @Test
  void testVariableNamedTemplate() throws IOException {
    new SimpleTest().comparisonTest("variableNamedTemplateTest.txt", "variableNamedTemplateResult.txt",
        "variableNamedTemplateExpected.txt");
  }

  /**
   * Test variable named list. See <b>variableNamedListTest.txt</b>
   */
  @Test
  void testVariableNamedList() throws IOException {
    new SimpleTest().comparisonTest("variableNamedListTest.txt", "variableNamedListResult.txt",
        "variableNamedListExpected.txt");
  }

}
