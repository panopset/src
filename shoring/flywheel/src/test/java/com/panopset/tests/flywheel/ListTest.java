package com.panopset.tests.flywheel;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.panopset.compat.Stringop;
import com.panopset.flywheel.FlywheelListDriver;
import com.panopset.flywheel.LineFeedRules;

public final class ListTest {

  @Test
  void testListCommand() throws IOException {
    new SimpleTest().comparisonTest("listTest01.txt", "listTest.txt", "listTestExpected.html");
  }

  @Test
  void testSimpleList() throws IOException {
    Stringop.setEol("\n");
    FlywheelListDriver fwd =
        new FlywheelListDriver.Builder(new String[] {"x", "y"}, "ab\nc").build();
    String result = fwd.getOutput();
    Assertions.assertEquals("ab\nc\n\nab\nc\n\n", result);
    Stringop.setEol(Stringop.DOS_RTN);
  }

  @Test
  void testSimpleListWithReturnsSuppressed() throws IOException {
    Stringop.setEol("\n");
    FlywheelListDriver fwd = new FlywheelListDriver.Builder(new String[] {"x", "y"}, "ab\nc")
        .withLineFeedRules(LineFeedRules.FLATTEN).build();
    String result = fwd.getOutput();
    Assertions.assertEquals("abcabc", result);
    Stringop.setEol(Stringop.DOS_RTN);
  }

  @Test
  void testSimpleListWithListBreaksSuppressed() throws IOException {
    Stringop.setEol("\n");
    FlywheelListDriver fwd = new FlywheelListDriver.Builder(new String[] {"x", "y"}, "ab\nc")
        .withLineFeedRules(LineFeedRules.LINE_BREAKS).build();
    String result = fwd.getOutput();
    Assertions.assertEquals("ab\nc\nab\nc\n", result);
    Stringop.setEol(Stringop.DOS_RTN);
  }
}
