package com.panopset.compat.test;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.compat.Fileop;
import com.panopset.compat.Rezop;
import com.panopset.compat.Streamop;
import com.panopset.compat.Stringop;

public class RezopTest {

  @Test
  void test() throws IOException {
    Assertions.assertEquals("x",
        Rezop.textStreamToList(this.getClass().getResourceAsStream(REZPATH)).get(0));
    Assertions.assertEquals("/com/panopset/compat/test/", Rezop.pkg2resourcePath(this.getClass()));
    Rezop.copyTextResourceToFile(this.getClass(), REZPATH, Fileop.getCanonicalPath(FileopTest.tempFile));
    Assertions.assertEquals(String.format("x%s", Stringop.getEol()), Fileop.readTextFile(FileopTest.tempFile));
    Rezop.copyTextResourceToFile(this.getClass(), REZPATH, FileopTest.tempFile);
    Assertions.assertEquals(String.format("x%s", Stringop.getEol()), Fileop.readTextFile(FileopTest.tempFile));
    Assertions.assertEquals("com/panopset/foo", Rezop.pkg2path("com.panopset.foo"));
    Assertions.assertEquals("/com/panopset/compat/test", Rezop.getPackageResourcePath(this.getClass().getPackage()));
  }

  @Test
  void testLoadFromRez()  {
    String rslt = Streamop.getTextFromStream(this.getClass().getResourceAsStream(REZPATH)).replace("\r\n", "\n");
    Assertions.assertEquals(String.format("x%s", "\n"), rslt);
    Assertions.assertEquals("x", Rezop.textStreamToList(this.getClass().getResourceAsStream(REZPATH)).get(0));
    Assertions.assertEquals("x", Rezop.textStreamToList(this.getClass().getResourceAsStream(REZPATH)).get(0));
  }

  @Test
  void testNotFound() {
    String rslt = Streamop.getTextFromStream(this.getClass().getResourceAsStream("foo.txt"));
    Assertions.assertEquals("", rslt);
  }

  private static final String REZPATH = "/com/panopset/enchar.txt";
}
