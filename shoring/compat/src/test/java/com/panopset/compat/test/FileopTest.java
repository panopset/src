package com.panopset.compat.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.panopset.compat.Fileop;
import com.panopset.compat.Logop;
import com.panopset.compat.Stringop;

public final class FileopTest {

  public static final File tempFile = new File("./temp.txt");
  public static final File tempDir = new File("./temp");
  public static final File deepFile = new File("./temp/temp.txt");
  public static final File badFile = new File("\u0000");
  final String fooWithReturnChar = String.format("%s%s", Stringop.FOO, Stringop.getEol());

  @BeforeEach
  public void beforeEach() throws IOException {
    cleanup();
  }

  @Test
  void test() throws IOException {
    Assertions.assertEquals("", Fileop.getCanonicalPath(null));
    Assertions.assertEquals("", Fileop.getCanonicalPath(badFile));
    Fileop.delete(null);
    Fileop.write(Stringop.FOO, null);
    Assertions.assertEquals(Stringop.USER_HOME, Fileop.getCanonicalPath(Fileop.getUserHome()));
    Fileop.delete(tempFile);
    Assertions.assertFalse(tempFile.exists());
    Fileop.touch(tempFile);
    Assertions.assertTrue(tempFile.exists());
    Fileop.touch(null);
    Fileop.touch(deepFile);
    Assertions.assertTrue(tempFile.exists());
    Assertions.assertEquals("txt", Fileop.getExtension(tempFile));
    Assertions.assertEquals("", Fileop.getExtension(new File(Stringop.FOO)));
    String parentDir = Fileop.getParentDirectory(deepFile);
    Assertions.assertEquals("temp", parentDir.substring(parentDir.lastIndexOf(Stringop.FILE_SEP) + 1));
    parentDir = Fileop.getParentDirectory(new File("foo/temp.txt"));
    Assertions.assertEquals("foo", parentDir.substring(parentDir.lastIndexOf(Stringop.FILE_SEP) + 1));
    Assertions.assertFalse(Fileop.fileExists(null));
    Assertions.assertFalse(Fileop.fileExists(new File("foo.txt")));
    Assertions.assertEquals("temp", Fileop.removeExtension("temp.txt"));
    Assertions.assertEquals("", Fileop.removeExtension(""));
    Assertions.assertTrue(Fileop.isFileOneOfExtensions(tempFile, "txt"));
    Assertions.assertFalse(Fileop.isFileOneOfExtensions(tempFile, "java,xml"));
    Assertions.assertTrue(Fileop.isFileOneOfExtensions(tempFile, "java,txt"));
    Assertions.assertFalse(Fileop.isFileOneOfExtensions(tempFile, null));
    Assertions.assertFalse(Fileop.isFileOneOfExtensions(null, "txt"));
    Assertions.assertFalse(Fileop.isFileOneOfExtensions(null, null));
  }

  @Test
  void combinePathsTest() throws IOException {
    File file = new File(Fileop.combinePaths("./temp", "temp.txt"));
    Assertions.assertEquals("", Fileop.readTextFile(file));
    Assertions.assertEquals(Fileop.getCanonicalPath(deepFile), Fileop.getCanonicalPath(file));
    Fileop.write(Stringop.FOO, file);
    Assertions.assertEquals(fooWithReturnChar, Fileop.readTextFile(file));
    Assertions.assertEquals(fooWithReturnChar, Fileop.readTextFile("./temp/temp.txt"));
  }

  @Test
  void checkParentTest() throws IOException {
    Assertions.assertFalse(tempDir.exists());
    Assertions.assertTrue(Fileop.checkParent(deepFile));
    Assertions.assertTrue(tempDir.exists());
    cleanup();
    Assertions.assertFalse(tempDir.exists());
    Fileop.touch(deepFile);
    Assertions.assertTrue(tempDir.exists());
    Assertions.assertTrue(Fileop.checkParent(deepFile));
    cleanup();
    Assertions.assertFalse(tempDir.exists());
    Fileop.mkdirs(tempDir);
    Assertions.assertTrue(tempDir.exists());
    Fileop.delete(tempDir);
    Assertions.assertFalse(tempDir.exists());
    Fileop.touch(tempDir);
    Logop.clear();
    Fileop.mkdirs(tempDir);
    Assertions.assertEquals(1, Logop.getStack().size());
  }

  @Test
  void copyInputStreamToFileTest() throws IOException {
    Assertions.assertFalse(tempFile.exists());
    InputStream inp = new ByteArrayInputStream(Stringop.FOO.getBytes());
    Fileop.copyInputStreamToFile(inp, "./temp.txt");
    Assertions.assertEquals(fooWithReturnChar, Fileop.readTextFile(tempFile));
  }

  @Test
  void deleteLinesTest() throws IOException {
    Stringop.setEol(Stringop.DOS_RTN);
    Fileop.write("foo\r\nbar\r\nbat", tempFile);
    Fileop.deleteLines(tempFile, "bar");
    Assertions.assertEquals("foo\r\nbat\r\n", Fileop.readTextFile(tempFile));
    Fileop.write("foo\r\nbar\r\nbat", tempFile);
    Fileop.deleteLines(tempFile, null);
    Assertions.assertEquals("foo\r\nbar\r\nbat\r\n", Fileop.readTextFile(tempFile));
    Fileop.write("foo\r\nbar\r\nbat", deepFile);
    Fileop.deleteLines(tempDir, "bar");
    Assertions.assertEquals("foo\r\nbat\r\n", Fileop.readTextFile(deepFile));
    Fileop.write("foo\r\nbar\r\nbat", tempFile);
    Fileop.deleteLines(tempFile, "");
    Assertions.assertEquals("foo\r\nbar\r\nbat\r\n", Fileop.readTextFile(tempFile));
    Fileop.deleteLines(null, "");
    Assertions.assertEquals("file is null", Logop.getStack().getLast().getMessage());
    Stringop.setEol("\n");
  }

  @Test
  void testGetCanonicalPath() {
    assertTrue(Stringop.isPopulated(Fileop.getCanonicalPath(new File(Stringop.USER_HOME))));
    MatcherAssert.assertThat(Fileop.getCanonicalPath(tempFile).indexOf("temp.txt"),
        Matchers.greaterThan(0));
  }

  @Test
  void getStandardPathTest() {
    String path = Fileop.getStandardPath(new File("temp\\temp.txt"));
    Assertions.assertEquals("/temp.txt", path.substring(path.lastIndexOf("/")));
  }

  @Test
  void createTempFileTest() throws IOException {
    Assertions.assertEquals(0, Fileop.readLines(tempFile).size());
    File tempFoo = Fileop.createTempFile(Stringop.FOO);
    File tempBar = Fileop.createTempFile(Stringop.BAR);
    Fileop.touch(tempFoo);
    Assertions.assertTrue(tempFoo.exists());
    Fileop.write(new String[] {Stringop.FOO, Stringop.BAR}, null);
    Assertions.assertEquals("Can't write to a null file", Logop.getStack().getLast().getMessage());
    Fileop.write(new String[] {Stringop.FOO, Stringop.BAR}, tempFoo);
    checkIsFooBarFile(tempFoo);
    Fileop.moveFile(tempFoo, tempBar);
    checkIsFooBarFile(tempBar);
    Assertions.assertFalse(tempFoo.exists());
    Fileop.copyFile(tempBar, tempFoo);
    checkIsFooBarFile(tempFoo);
    checkIsFooBarFile(tempBar);
    Fileop.delete(tempFoo);
    Fileop.delete(tempBar);
    Assertions.assertFalse(tempFoo.exists());
    Assertions.assertFalse(tempBar.exists());
  }
  
  private void checkIsFooBarFile(File file) throws IOException {
    List<String> lines = Fileop.readLines(file);
    Assertions.assertEquals(2, lines.size());
    Assertions.assertEquals(Stringop.FOO, lines.get(0));
    Assertions.assertEquals(Stringop.BAR, lines.get(1));
  }

  static void cleanup() throws IOException {
    cleanup(tempFile);
    cleanup(tempDir);
  }

  static void cleanup(File f) throws IOException {
    Fileop.delete(f);
    Assertions.assertFalse(f.exists());
  }
}
