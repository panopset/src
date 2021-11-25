package com.panopset.compat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

public class Rezop {
  
  private Rezop() {}

  public static List<String> textStreamToList(InputStream is) {
    String lines = Streamop.getTextFromStream(is);
    return Stringop.stringToList(lines);
  }

  public static String getPackageResourcePath(Package pkg) {
    StringWriter sw = new StringWriter();
    sw.append("/");
    sw.append(pkg2path(pkg.getName()));
    return sw.toString();
  }

  public static String pkg2path(String dotName) {
    return dotName.replace(".", "/");
  }

  public static void copyTextResourceToFile(Class<?> clazz, String resourcePath, File file)
      throws IOException {
    Fileop.copyInputStreamToFile(clazz.getResourceAsStream(resourcePath), file);
  }

  public static void copyTextResourceToFile(Class<?> clazz, String resourcePath, String fileName)
      throws IOException {
    Fileop.copyInputStreamToFile(clazz.getResourceAsStream(resourcePath), fileName);
  }

  public static String pkg2resourcePath(Class<?> clazz) {
    StringWriter sw = new StringWriter();
    sw.append("/");
    sw.append(clazz.getPackage().getName().replace(".", "/"));
    sw.append("/");
    return sw.toString();
  }
}
