package com.panopset.compat.test;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.compat.Fileop;
import com.panopset.compat.LineFilter;
import com.panopset.compat.Report;
import com.panopset.compat.Stringop;

public class ReportTest {

  static final String FOOEOL = Stringop.appendEol(Stringop.FOO);
  static final String BAREOL = Stringop.appendEol(Stringop.BAR);
  static final File temp = FileopTest.tempFile;
  static final File temp0 = new File("./temp0.txt");
  static final LineFilter barFilter = str -> Stringop.BAR;
  static final LineFilter fooFilter = str -> Stringop.FOO;

  @Test
  void test() throws IOException {
    Fileop.write(Stringop.FOO, temp);
    String result = Fileop.readTextFile(temp);
    Assertions.assertEquals(FOOEOL, result);
    Report report = new Report(temp, temp0, barFilter);
    Assertions.assertTrue(report.exec());
    result = Fileop.readTextFile(temp);
    Assertions.assertEquals(FOOEOL, result);
    result = Fileop.readTextFile(temp0);
    Assertions.assertEquals(BAREOL, result);
    cleanup();
  }

  @Test
  void testSingleFilter() throws IOException {
    Fileop.write(Stringop.FOO, temp);
    String result = Fileop.readTextFile(temp);
    Assertions.assertEquals(Stringop.appendEol(Stringop.FOO), result);
    Report report = new Report(temp, barFilter);
    Assertions.assertTrue(report.exec());
    result = Fileop.readTextFile(temp);
    Assertions.assertEquals(Stringop.appendEol(Stringop.BAR), result);
    cleanup();
  }

  @Test
  void testSameFilter() throws IOException {
    Fileop.write(Stringop.FOO, temp);
    String result = Fileop.readTextFile(temp);
    Assertions.assertEquals(Stringop.appendEol(Stringop.FOO), result);
    Report report = new Report(temp, fooFilter);
    Assertions.assertFalse(report.exec());
    result = Fileop.readTextFile(temp);
    Assertions.assertEquals(Stringop.appendEol(Stringop.FOO), result);
    cleanup();
  }

  @Test
  void testNullAndBad() throws IOException {
    Fileop.write(Stringop.FOO, temp);
    Report report = new Report(temp, null, barFilter);
    Assertions.assertTrue(report.exec());
    report = new Report(temp, FileopTest.badFile, barFilter);
    Assertions.assertFalse(report.exec());
    report = new Report(FileopTest.badFile, temp, barFilter);
    Assertions.assertFalse(report.exec());
    report = new Report(FileopTest.badFile, null, barFilter);
    Assertions.assertFalse(report.exec());
    cleanup();
  }

  static void cleanup() throws IOException {
    FileopTest.cleanup();
    FileopTest.cleanup(temp0);
  }
}
