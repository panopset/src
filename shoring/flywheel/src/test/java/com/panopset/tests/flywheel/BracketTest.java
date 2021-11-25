package com.panopset.tests.flywheel;

import static com.panopset.compat.Stringop.BAR;
import static com.panopset.compat.Stringop.FOO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import com.panopset.compat.Logop;
import com.panopset.flywheel.FlywheelBuilder;
import com.panopset.flywheel.LineFeedRules;

/**
 * Tests the Flywheel template variable resolution.
 */
public final class BracketTest {

  /**
   * <b>$&#123;1} ${foo}</b>.
   */
  static final String INPUT = "$&#123;1} ${" + FOO + "}.";

  /**
   * <b>$&#123;1} bar</b>.
   */
  static final String EXPECTED = "$&#123;1} " + BAR + ".";

  @Test
  void testBracket() throws IOException {
    Logop.turnOnDebugging();
    final String s = new FlywheelBuilder().withLineFeedRules(LineFeedRules.FLATTEN).map(FOO, BAR)
        .input(new String[] {EXPECTED}).construct().exec();
    assertEquals(EXPECTED, s);
    Logop.turnOffDebugging();
  }

}
