package com.panopset.compat;

public class ClassVersion {
  
  public ClassVersion() {
  }
  
  public ClassVersion(MajorVersion majorVersion, String minorVersion) {
    setMajorVersion(majorVersion);
    setMinorVersion(minorVersion);
  }

  private MajorVersion majorVersion;
  
  public MajorVersion getMajorVersion() {
    return majorVersion;
  }
  
  public void setMajorVersion(MajorVersion majorVersion) {
    this.majorVersion = majorVersion;
  }
  
  private String minorVersion;
  
  public String getMinorVersion() {
    return minorVersion;
  }
  
  public void setMinorVersion(String minorVersion) {
    this.minorVersion = minorVersion;
  }
 
  public boolean isValid() {
    return getMajorVersion() != null;
  }
}
