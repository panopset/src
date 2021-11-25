package com.panopset.tests.flywheel;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import org.junit.jupiter.api.Test;

import com.panopset.flywheel.Flywheel;
import com.panopset.flywheel.FlywheelBuilder;
import com.panopset.flywheel.SourceFile;

public final class SourceFileTest {

  /**
   * Test source file constructor in Flywheel.Builder.
   *
   * @throws Exception
   *           Exception.
   */
  @Test
  void testSourceFileConstructor() throws Exception {
    File file = new File(SimpleTest.TEST_FILE_PATH + SimpleTest.SIMPLETEST);
    Flywheel flywheel = new FlywheelBuilder()
        .targetDirectory(SimpleTest.TEST_DIRECTORY).file(file).construct();
    SourceFile sf00 = new SourceFile(flywheel, SimpleTest.SIMPLETEST);
    SourceFile sf01 = new SourceFile(file, flywheel.getBaseDirectoryPath());
    assertTrue(sf00.equals(sf01));
    assertTrue(sf00.isValid());
    flywheel.exec();
  }
}
