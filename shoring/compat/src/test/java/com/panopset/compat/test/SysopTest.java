package com.panopset.compat.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.compat.Fileop;
import com.panopset.compat.Stringop;
import com.panopset.compat.Sysop;

public class SysopTest {

  @Test
  void test() throws Exception {
    Sysop.setSysProp(Stringop.FOO, Stringop.BAR);
    Sysop.setSysProp(Stringop.FOO, "");
    Sysop.setSysProp(Stringop.FOO, null);
    Assertions.assertEquals(Stringop.BAR, Sysop.getSysProp(Stringop.FOO));
    Fileop.touch(FileopTest.tempFile);
    Sysop.setSysProp(Stringop.FOO, Stringop.BAR);
    Assertions.assertEquals(Stringop.BAR, Sysop.getSysProp(Stringop.FOO));
    Sysop.setSysPropFromUrlFilePath(Stringop.FOO, FileopTest.tempFile.toURI().toURL());
    Assertions.assertEquals(FileopTest.tempFile.toURI().toURL().getPath(), Sysop.getSysProp(Stringop.FOO));
    Sysop.setSysPropFromUrlFilePath(Stringop.FOO, null);
    Assertions.assertEquals(FileopTest.tempFile.toURI().toURL().getPath(), Sysop.getSysProp(Stringop.FOO));
    Fileop.delete(FileopTest.tempFile);
  }
}
