package com.panopset.tests.flywheel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import com.panopset.compat.Fileop;
import com.panopset.flywheel.Flywheel;
import com.panopset.flywheel.FlywheelBuilder;
import com.panopset.gp.FileCompare;

public class SimpleTest {

  /**
   * Temp directory: $HOME/temp/com/panopset/test.
   */
  public static final File TEST_DIRECTORY =
      new File(Fileop.getCanonicalPath(Fileop.TEMP_DIRECTORY) + "/com/panopset/test");

  public static final String TEST_FILE_PATH = "src/" + "test/resources/com/panopset/flywheel/";

  /**
   * simpleTest.txt.
   */
  public static final String SIMPLETEST = "simpleTest.txt";

  /**
   * simpleOut.txt.
   */
  public static final String SIMPLEOUT = "simpleOut.txt";

  /**
   * simpleTestExpected.txt.
   */
  public static final String EXPECTED = "simpleTestExpected.txt";

  /**
   * Test simple script.
   */
  @Test
  void testSimpleScript() throws IOException {
    SimpleTest.comparisonTest(SIMPLETEST, SIMPLEOUT, EXPECTED);
  }

  /**
   * Comparison test.
   *
   * @param scriptName Script name.
   * @param generatedFileName Generated file name.
   * @param expected Expected results.
   */
  public static void comparisonTest(final String scriptName, final String generatedFileName,
      final String expected) throws IOException {
    comparisonTest(scriptName, new String[] {generatedFileName}, new String[] {expected});
  }

  /**
   *
   * @param scriptName Script name.
   * @param generatedFileNames Generated file names.
   * @param expecteds Expected results.
   */
  public static void comparisonTest(final String scriptName, final String[] generatedFileNames,
      final String[] expecteds) throws IOException {
    assertEquals(expecteds.length, generatedFileNames.length);
    int incr = 0;
    for (String generatedFileName : generatedFileNames) {
      File generatedFile = new File(TEST_DIRECTORY + "/" + generatedFileName);
      if (generatedFile.exists()) {
        Fileop.delete(generatedFile);
      }
      Flywheel script = new FlywheelBuilder().targetDirectory(TEST_DIRECTORY)
          .file(new File(TEST_FILE_PATH + scriptName)).construct();
      script.exec();
      assertFalse(script.isStopped());
      assertTrue(generatedFile.exists());
      assertTrue(
          FileCompare.filesAreSame(generatedFile, new File(TEST_FILE_PATH + expecteds[incr++])));
    }
  }

}
