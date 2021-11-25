package com.panopset.compat.test;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.compat.Fileop;

public class FileopMockTest {

  @Test
  void testTouch() throws IOException {
    Fileop.touch(null);
    Fileop.delete(FileopTest.tempFile);
    Assertions.assertFalse(FileopTest.tempFile.exists());
    Fileop.touch(FileopTest.tempFile);
    Assertions.assertTrue(FileopTest.tempFile.exists());
    Fileop.delete(FileopTest.deepFile);
    Assertions.assertFalse(FileopTest.deepFile.exists());
    Fileop.touch(FileopTest.deepFile);
    Assertions.assertTrue(FileopTest.deepFile.exists());
  }

}
