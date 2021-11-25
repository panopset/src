package com.panopset.tests.flywheel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import org.junit.jupiter.api.Test;
import com.panopset.compat.Fileop;
import com.panopset.compat.Stringop;
import com.panopset.flywheel.Flywheel;
import com.panopset.flywheel.FlywheelBuilder;

public final class BufferTest {
  public static final String SIMPLEONECHAR = "simpleOneChar.txt";
  public static final String SIMPLETWOLINES = "simpleTwoLines.txt";


  @Test
  void testSimpleOneChar() throws IOException {
    StringWriter sw = new StringWriter();
    Flywheel script = new FlywheelBuilder().withWriter(sw)
        .file(new File(SimpleTest.TEST_FILE_PATH + SIMPLEONECHAR)).construct();
    script.exec();
    assertEquals("x" + Stringop.getEol(), sw.toString());
  }

  @Test
  void testTwoLines() throws IOException {
    StringWriter sw = new StringWriter();
    Flywheel script = new FlywheelBuilder().withWriter(sw)
        .file(new File(SimpleTest.TEST_FILE_PATH + SIMPLETWOLINES)).construct();
    script.exec();
    assertEquals("x" + Stringop.getEol() + "y" + Stringop.getEol(), sw.toString());
  }


  @Test
  void testSimpleBuffer() throws IOException {
    StringWriter sw = new StringWriter();
    Flywheel script = new FlywheelBuilder().withWriter(sw)
        .file(new File(SimpleTest.TEST_FILE_PATH + SimpleTest.SIMPLETEST)).construct();
    script.exec();
    assertEquals(Fileop.readTextFile(SimpleTest.TEST_FILE_PATH + SimpleTest.EXPECTED),
        sw.toString());
  }

  @Test
  void testComplexBuffer() throws IOException {
    StringWriter sw = new StringWriter();
    Flywheel script = new FlywheelBuilder().withWriter(sw)
        .file(new File(SimpleTest.TEST_FILE_PATH + ComplexTest.TEMPLATE)).construct();
    script.exec();
    assertEquals(Fileop.readTextFile(SimpleTest.TEST_FILE_PATH + ComplexTest.EXPECTED),
        sw.toString());
  }
}
