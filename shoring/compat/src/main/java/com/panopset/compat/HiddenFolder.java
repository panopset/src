package com.panopset.compat;

public enum HiddenFolder {

  INSTANCE;

  public static String getFullPathRelativeTo(String relativePathToAppend) {
    return INSTANCE.getBasePath() + Stringop.FILE_SEP +  relativePathToAppend;
  }

  private String getBasePath() {
    return Stringop.USER_HOME + Stringop.FILE_SEP + getHiddenFolderName();
  }

  private String rootLogFileName;

  public static String getRootLogFileName() {
    return INSTANCE.getRlfn();
  }

  private String getRlfn() {
    if (rootLogFileName == null) {
      rootLogFileName = "root";
    }
    return rootLogFileName;
  }

  public static void setRootLogFileName(String value) {
    INSTANCE.rootLogFileName = value;
  }

  private String hiddenFolderName;

  public static String getHiddenFolderName() {
    return INSTANCE.getHfn();
  }

  private String getHfn() {
    if (hiddenFolderName == null) {
      hiddenFolderName = "temp_but_should_replace";
    }
    return hiddenFolderName;
  }

  public static void setHiddenFolderName(String value) {
    INSTANCE.hiddenFolderName = value;
  }
}
