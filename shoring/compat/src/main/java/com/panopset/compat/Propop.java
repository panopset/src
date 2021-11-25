package com.panopset.compat;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Propop {
  
  private Propop() {}

  public static Map<String, String> loadPropsFromFile(final File file) throws IOException {
    Properties props = new Properties();
    load(props, file);
    return loadMapFromProperties(props);
  }

  public static Map<String, String> loadMapFromProperties(final Properties properties) {
    final Map<String, String> rtn = new HashMap<>();
    for (Object key : properties.keySet()) {
      rtn.put(key.toString(), properties.getProperty(key.toString()));
    }
    return rtn;
  }

  /**
   * If file can not be read, an empty Properties object is returned.
   * 
   * @param props Properties to load.
   * @param file File to load properties from.
   * 
   * @throws IOException exception.
   */
  public static Properties load(Properties props, File file) throws IOException {
    Properties rtn;
    if (props == null) {
      rtn = new Properties();
    } else {
      rtn = props;
    }
    if (file == null) {
      return rtn;
    }
    if (!file.exists()) {
    	Fileop.touch(file);
    }
    try (FileReader fr = new FileReader(file)) {
      rtn.load(fr);
    }
    return rtn;
  }

  /**
   * If file can not be read, an empty Properties object is returned.
   * 
   * @param file File to load properties from.
   */
  public static Properties load(File file) throws IOException {
    return load(new Properties(), file);
  }

  /**
   * Save properties in a file.
   * 
   * @param props Properties to save.
   * @param file File to save properties to.
   */
  public static void save(Properties props, File file) throws IOException {
    if (props == null || file == null) {
      return;
    }
    try (FileWriter fw = new FileWriter(file)) {
      props.store(fw, new SimpleDateFormat().format(new Date()));
    }
  }
}
