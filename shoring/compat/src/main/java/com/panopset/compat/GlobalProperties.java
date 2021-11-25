package com.panopset.compat;

import java.io.File;
import java.io.IOException;

/**
 * 
 * Some things, like a font size, should persist for all applications.
 *
 */
public enum GlobalProperties {

  INSTANCE;
  
  public static void flush() {
    try {
      INSTANCE.saveToFile();
    } catch (IOException ex) {
      Logop.error(ex);
    }
  }
  
  public static void putGP(String key, String value) {
    try {
      INSTANCE.putValue(key, value);
    } catch (IOException ex) {
      Logop.error(ex);
    }
  }
  
  public static String getGP(String key) {
    try {
		return INSTANCE.getPmf().get(key);
	} catch (IOException e) {
		Logop.debug(String.format("%s not found, %s", key, e.getMessage()));
		return "";
	}
  }
  
  private void saveToFile() throws IOException {
    getPmf().flush();
  }
  
  private void putValue(String key, String value) throws IOException {
    getPmf().put(key, value);
  }
  
  private PersistentMapFile pmf;
  
  private PersistentMapFile getPmf() {
    if (pmf == null) {
      pmf = new PersistentMapFile(new File(HiddenFolder.getFullPathRelativeTo("global.properties")));
    }
    return pmf;
  }
}
