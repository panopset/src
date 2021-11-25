package com.panopset.marin.secure.checksums;

import com.panopset.compat.Stringop;

public enum ChecksumType {
  BYTES("bytes"), MD5("md5"), SHA1("sha1"), SHA256("sha256"), SHA384(
      "sha384"), SHA512("sha512");
  private final String key;

  public static ChecksumType find(String name) {
    if (Stringop.isEmpty(name)) {
      return null;
    }
    for (ChecksumType cst : values()) {
      if (cst.name().equalsIgnoreCase(name)) {
        return cst;
      }
    }
    return null;
  }

  ChecksumType(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
