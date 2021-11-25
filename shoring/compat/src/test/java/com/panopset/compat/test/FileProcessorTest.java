package com.panopset.compat.test;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.compat.FileProcessor;
import com.panopset.compat.Fileop;
import com.panopset.compat.LineFilter;
import com.panopset.compat.Stringop;

public class FileProcessorTest {

  final File file = FileopTest.tempFile;

  @Test
  void test() throws IOException {
    Fileop.delete(file);
    Fileop.write(Stringop.FOO, file);


    FileProcessor fp = new FileProcessor(file).withLineFilter(new LineFilter() {

      @Override
      public String filter(String str) {
        return Stringop.BAR;
      }

    });
    Assertions.assertTrue(fp.exec());
    String result = Fileop.readTextFile(file);
    Assertions.assertEquals(Stringop.appendEol(Stringop.BAR), result);
    FileopTest.cleanup();
  }

  @Test
  void testNullResultFilter() throws IOException {
    Fileop.delete(file);
    Fileop.write(Stringop.FOO, file);
    FileProcessor fp = new FileProcessor(file).withLineFilter(new LineFilter() {

      @Override
      public String filter(String str) {
        return null;
      }

    });
    Assertions.assertTrue(fp.exec());
    FileopTest.cleanup();
  }
}
