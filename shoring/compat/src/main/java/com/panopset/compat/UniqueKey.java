package com.panopset.compat;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unique key that is the same between launches.
 *
 * @author Karl Dinwiddie
 *
 */
public final class UniqueKey {

  /**
   * Keys.
   */
  private static List<String> keys = new ArrayList<String>();

  /**
   * Map.
   */
  private static Map<String, Integer> map = new HashMap<String, Integer>();

  /**
   * Generate a unique key independent of run time, based on stack trace.
   *
   * @return String "key_" + sha1sum(getStackTrace)
   */
  public static String generateStackHashKey() throws IOException {
    Exception exp = null;
    try {
      throw new Exception();
    } catch (Exception ex) {
      exp = ex;
    }
    MessageDigest digest;
    try {
      digest = java.security.MessageDigest.getInstance("SHA1");
      digest.update(Logop.getStackTrace(exp).getBytes());
      byte[] hash = digest.digest();
      String rtn = Base64.getEncoder().encodeToString(hash);
      if (keys.contains(rtn)) {
        return generatedIterStackHashKey(rtn);
      }
      keys.add(rtn);
      return rtn;
    } catch (NoSuchAlgorithmException ex) {
      throw new RuntimeException(ex);
    }
  }

  /**
   * @param key
   *          Key.
   * @return Unique iteration for duplicate.
   */
  private static String generatedIterStackHashKey(final String key) {
    Integer next = map.get(key);
    if (next == null) {
      next = 0;
    }
    String rtn = key + next;
    next++;
    map.put(key, next);
    return rtn;
  }

  /**
   * Prevent instantiation.
   */
  private UniqueKey() {
  }
}
