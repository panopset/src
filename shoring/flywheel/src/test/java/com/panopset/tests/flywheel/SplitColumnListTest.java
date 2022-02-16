package com.panopset.tests.flywheel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import com.panopset.compat.Stringop;
import com.panopset.flywheel.FlywheelBuilder;
import com.panopset.flywheel.LineFeedRules;
import com.panopset.flywheel.ReservedWords;

public final class SplitColumnListTest {

  /**
   * Test split columns in a list command. See <b>splitList.txt</b>.
   */
  @Test
  void testSplitColumns() throws IOException {
    Stringop.setEol("\n");
    String expected = "6#Zonk7#Bonk\n";
    String[] inp = new String[] { "${@l splitList.txt}${2}#${3}${@q}" };
    String results = new FlywheelBuilder().withLineFeedRules(LineFeedRules.LINE_BREAKS)
    		// TODO use testing framework.
        .baseDirectoryPath("src/test/resources/com/panopset/tests/flywheel")
        .map(ReservedWords.SPLITS, "13,14").input(inp).construct().exec();
    assertEquals(expected, results);
  }

}
