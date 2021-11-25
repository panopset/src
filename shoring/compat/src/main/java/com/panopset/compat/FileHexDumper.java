package com.panopset.compat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileHexDumper {
  private final File file;

  public FileHexDumper(File file) {
    this.file = file;
  }

  private File getFile() {
    return file;
  }

  public String[] dump(int start, Integer max, int width, boolean isSpaces,
      boolean isCharsetIncluded) throws IOException {
    if (getFile() == null) {
      Logop.error("No file was selected.", getFile());
      return new String[] {""};
    }
    if (!getFile().exists()) {
      Logop.error("File does not exist.", getFile());
      return new String[] {""};
    }
    try (FileInputStream fis = new FileInputStream(getFile())) {
      return Hexop.dump(fis, start, max, width, isSpaces, isCharsetIncluded, file.length());
    } catch (IOException e) {
      Logop.error(e);
      return new String[] {e.getMessage(), ""};
    }
  }

  public String[] dump(int bytes) throws IOException {
    return dump(0, bytes, bytes, false, false);
  }

}
