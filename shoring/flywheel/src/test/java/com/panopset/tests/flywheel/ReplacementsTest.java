package com.panopset.tests.flywheel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import com.panopset.compat.Stringop;
import com.panopset.flywheel.FlywheelBuilder;
import com.panopset.flywheel.LineFeedRules;

public final class ReplacementsTest {

  private static final String SCRIPT = "${foo}";

  @Test
  void testReplacements() throws IOException {
    assertEquals(Stringop.BAR,
        new FlywheelBuilder().replacement(Stringop.FOO, Stringop.BAR)
            .withLineFeedRules(LineFeedRules.FLATTEN).input(new String[] {Stringop.FOO}).construct()
            .exec());
  }

  @Test
  void testReplacementsUsingProps() throws IOException {
    Properties props = new Properties();
    props.put(Stringop.FOO, Stringop.BAR);
    assertEquals(Stringop.BAT,
        new FlywheelBuilder().properties(props).withLineFeedRules(LineFeedRules.FLATTEN)
            .replacement(Stringop.BAR, Stringop.BAT).input(new String[] {SCRIPT}).construct()
            .exec());
  }

}
