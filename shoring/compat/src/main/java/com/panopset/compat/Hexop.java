package com.panopset.compat;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class Hexop {

  private Hexop() {}

  public static String dumpToString(String text, int start, Integer max, int width,
      boolean isSpaces, boolean isCharsetIncluded) {
    String[] d = dump(text, start, max, width, isSpaces, isCharsetIncluded);
    StringWriter sw = new StringWriter();
    for (String s : d) {
      sw.append(s);
      sw.append(Stringop.EOL);
    }
    return sw.toString();
  }

  public static String[] dump(String text, int start, Integer max, int width, boolean isSpaces,
      boolean isCharsetIncluded) {
    ByteArrayInputStream bais = new ByteArrayInputStream(text.getBytes());
    return dump(bais, start, max, width, isSpaces, isCharsetIncluded, text.length());
  }

  public static String[] dump(InputStream inpStrm, int start, Integer max, int width,
      boolean isSpaces, boolean isCharsetIncluded, long streamLength) {

    StringWriter inpWriter = new StringWriter();
    StringWriter rtnWriter = new StringWriter();
    StringWriter hexWriter = new StringWriter();
    StringWriter repWriter = new StringWriter();

    try (BufferedInputStream bis =
        inpStrm instanceof BufferedInputStream ? (BufferedInputStream) inpStrm
            : new BufferedInputStream(inpStrm)) {

      int i = 0;
      int lineByteCount = 0;
      if (start > 0) {
        if ((long) start > streamLength) {
          rtnWriter.append(
              String.format("File length of %s is smaller than the start position.", streamLength));
        }

        bis.skip((long) start);
      }

      int c = bis.read();

      while (true) {
        do {
          if (!hasMore(i++, max, c)) {
            return new String[] {inpWriter.toString(), rtnWriter.toString()};
          }

          if (lineByteCount > 0 && isSpaces) {
            hexWriter.append(" ");
            repWriter.append(" ");
          }

          String hexRep = String.format("%02X", c);
          hexWriter.append(hexRep);
          inpWriter.append((char) c);
          String backSlasher = backSlashOf(c, hexRep);
          if (backSlasher.length() > 0) {
            repWriter.append(backSlasher);
          } else {
            repWriter.append(" ");
            Character ch = (char) c;
            if (Character.isWhitespace(ch)) {
              repWriter.append(" ");
            } else {
              repWriter.append(Character.toString((char) c));
            }
          }
          c = bis.read();
          ++lineByteCount;
        } while (lineByteCount < width && c != -1);

        if (isCharsetIncluded) {
          rtnWriter.append(repWriter.toString());
          if (width > 0) {
            rtnWriter.append("\n");
          }
        }

        rtnWriter.append(hexWriter.toString());
        if (width > 0) {
          rtnWriter.append("\n");
        }

        hexWriter = new StringWriter();
        repWriter = new StringWriter();
        lineByteCount = 0;
      }
    } catch (Exception ex) {
      Logop.error(ex);
      return new String[] {ex.getMessage(), ""};
    }
  }

  private static String backSlashOf(int c, String hexRep) {
    if (!isInBackSlashRange(c)) {
      return "";
    } else if (hexRep == null) {
      return "";
    } else if ("09".equals(hexRep)) {
      return "\\t";
    } else if ("08".equals(hexRep)) {
      return "\\b";
    } else if ("0A".equals(hexRep)) {
      return "\\n";
    } else if ("0D".equals(hexRep)) {
      return "\\r";
    } else {
      return "0C".equals(hexRep) ? "\\f" : "";
    }
  }

  private static boolean isInBackSlashRange(int c) {
    return c < 14;
  }

  private static boolean hasMore(int i, int max, int c) {
    if (c != -1 && max > 0 && i >= max) {
      return true;
    } else {
      return c != -1;
    }
  }

  public static String[] dump(File file, int bytes) {
    return dump(file, 0, bytes, bytes, false, false);
  }

  public static String[] dump(File file, int start, Integer max, int width, boolean isSpaces,
      boolean isCharsetIncluded) {
    if (file == null) {
      return new String[] {"No file was selected.", ""};
    }
    if (!file.exists()) {
      return new String[] {"File " + Fileop.getCanonicalPath(file) + " does not exist.", ""};
    }
    try (FileInputStream fis = new FileInputStream(file)) {
      return dump(fis, start, max, width, isSpaces, isCharsetIncluded, file.length());
    } catch (IOException e) {
      Logop.error(e);
      return new String[] {e.getMessage(), ""};
    }
  }

  public static String[] dump(String string) {
    return dump(new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8)), 0,
        string.length(), 0, false, false, string.length());
  }

}
