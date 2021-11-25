
package com.panopset.compat;

import java.util.ResourceBundle;

/**
 * Encapsulates a ResourceBundle base path and provides the path in formats for several use cases.
 *
 */
public class Bundleop {

  /**
   * @param basePath Full dot separated path to a bundle resource base name. For example, for
   *        basePath <b>com.panopset.bundles.user</b>. Bundleop would expect to find user.properties
   *        in the <b>com.panopset.bundles</b> resource package.
   */
  public Bundleop(final String basePath) {
    base = basePath;
  }

  /**
   * @return Resource bundle.
   */
  private ResourceBundle getBundle() {
    if (bundle == null) {
      try {
        bundle = ResourceBundle.getBundle(getPathSlash());
      } catch (Exception th) {
        Logop.warn(String.join(":", "Failed to load", getPathSlash()));
        throw th;
      }
    }
    return bundle;
  }

  public String get(final String key) {
    try {
      return getBundle().getString(key);
    } catch (Exception th) {
      Logop.error(
          String.join(":", "Bundle base name", getBundle().getBaseBundleName(), "key", key), th);
      return "";
    }
  }

  /**
   * @return Base path with leading file separator.
   */
  public String getPathRez() {
    if (pathRez == null) {
      pathRez = String.format("/%s", getPathSlash());
    }
    return pathRez;
  }

  /**
   * @return Base path without leading file separator.
   */
  public String getPathSlash() {
    if (pathSlash == null) {
      pathSlash = getPathDot().replace(".", "/");
    }
    return pathSlash;
  }

  /**
   * Useful for passing to ResourceBundle.getBundle().
   * 
   * @return Package path.
   */
  public String getPathDot() {
    if (pathDot == null) {
      pathDot = base;
    }
    return pathDot;
  }

  /**
   * Useful for passing to a Spring ReloadableResourceBundleMessageSource.setBasenames method.
   * 
   * @return Array of basenames for this bundle.
   */
  public String getPathBaseNames() {
    if (pathBaseNames == null) {
      pathBaseNames = String.join(":", "classpath", getPathSlash());
    }
    return pathBaseNames;
  }

  private ResourceBundle bundle;
  private String pathSlash;
  private String pathRez;
  private String pathDot;
  private String pathBaseNames;
  /**
   * basePath from constructor.
   */
  private final String base;
}
