package com.panopset.flywheel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;
import com.panopset.compat.Fileop;
import com.panopset.compat.Streamop;
import com.panopset.compat.Stringop;

public class Rezop {
  
  private Rezop() {}
  
  public static String loadFromRez(Class<?> clazz, String rezPath) {
    return Streamop.getTextFromStream(clazz.getResourceAsStream(rezPath));
  }

  public static List<String> loadListFromTextResource(Class<?> clazz, String rezPath) {
    InputStream is = clazz.getResourceAsStream(rezPath);
    return textStreamToList(is);
  }

  public static List<String> loadListFromTextResource(String rezPath) {
    InputStream is = Rezop.class.getResourceAsStream(rezPath);
    return textStreamToList(is);
  }

  public static List<String> textStreamToList(InputStream is) {
    String lines = Streamop.getTextFromStream(is);
    return Stringop.stringToList(lines);
  }

  public static InputStream getResourceStream(Class<?> clazz, String resourcePath) {
    return clazz.getResourceAsStream(resourcePath);
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
    Fileop.copyInputStreamToFile(getResourceStream(clazz, resourcePath), file);
  }

  public static void copyTextResourceToFile(Class<?> clazz, String resourcePath, String fileName)
      throws IOException {
    Fileop.copyInputStreamToFile(getResourceStream(clazz, resourcePath), fileName);
  }

  public static String pkg2resourcePath(Class<?> clazz) {
    StringWriter sw = new StringWriter();
    sw.append("/");
    sw.append(clazz.getPackage().getName().replace(".", "/"));
    sw.append("/");
    return sw.toString();
  }
}
