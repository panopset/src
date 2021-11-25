package com.panopset.tests.flywheel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import com.panopset.flywheel.Flywheel;
import com.panopset.flywheel.FlywheelBuilder;

public class ComplexTest {

  /**
   * <b>complexText.txt</b>.
   */
  public static final String TEMPLATE = "complexTest.txt";

  /**
   * <b>complexTextExpected.html</b>.
   */
  public static final String EXPECTED = "complexTestExpected.html";

  /**
   * <b>foo</b>.
   */
  private static final String FOO = "foo";

  /**
   * <b>bar</b>.
   */
  private static final String BAR = "bar";

  /**
   * Variable definition.
   */
  @Test
  void testApp() throws IOException {
    Flywheel script = new FlywheelBuilder().construct();
    script.put(FOO, BAR);
    script.exec();
    assertEquals(BAR, script.get(FOO));
  }

  /**
   * Test script.
   */
  @Test
  void testScript() throws IOException {
    SimpleTest.comparisonTest(TEMPLATE, "outdir/complexOut.html", EXPECTED);
  }

}
