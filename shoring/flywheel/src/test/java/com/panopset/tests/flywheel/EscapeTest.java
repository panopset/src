package com.panopset.tests.flywheel;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.panopset.compat.Stringop;
import com.panopset.flywheel.Flywheel;
import com.panopset.flywheel.FlywheelBuilder;
import com.panopset.flywheel.LineFeedRules;

public class EscapeTest {

  @Test
  void testEscapeNewLine() throws IOException {
    Assertions.assertEquals("foobar\n",
        applyTemplate(Arrays.asList(new String[] {"foo\\", "bar"})));
    Assertions.assertEquals("\n", applyTemplate(Arrays.asList(new String[] {"\\", ""})));
    Assertions.assertEquals("\\\n\n", applyTemplate(Arrays.asList(new String[] {"\\\\", ""})));
    Assertions.assertEquals("\\\n", applyTemplate(Arrays.asList(new String[] {"\\\\"})));
    Assertions.assertEquals("foobar\n", applyTemplate(Arrays.asList(new String[] {"${@p x}foo\\", "bar${@q}${x}"})));
  }

  private String applyTemplate(List<String> input) {
    Stringop.setEol("\n");
    Flywheel fw =
        new FlywheelBuilder().input(input).withLineFeedRules(LineFeedRules.LINE_BREAKS).construct();
    String result = fw.exec();
    return result;
  }
}
