package com.panopset.compat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChecksumGenerator {

  private static String summit(FileInputStream fis, String algorithm) throws NoSuchAlgorithmException, IOException {
    MessageDigest md = MessageDigest.getInstance(algorithm);
    return digestFromStream(md, fis);
  }
  
  private static String digestFromStream(MessageDigest md, FileInputStream fis) throws IOException {
    byte[] dataBytes = new byte[1024];
    int nread;
    while ((nread = fis.read(dataBytes)) != -1) {
      md.update(dataBytes, 0, nread);
    }
    byte[] mdbytes = md.digest();
    return convertBytesToHex(mdbytes);
  }

  private static String convertBytesToHex(byte[] byts) {
    StringBuilder sb = new StringBuilder();
    for (byte byt : byts) {
      sb.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
    }
    return sb.toString();
  }

  private static String getDigestOf(final File file, final String algorithm) {
    if (!file.canRead()) {
      return Nls.xlate("Can not read") + " " +  Fileop.getCanonicalPath(file);
    }
    try {
      return summit(new FileInputStream(file), algorithm);
    } catch (Exception e) {
      return e.getMessage();
    }
  }

  public static String byteCount(File file) {
    return String.format("%,d", file.length());
  }

  public static String md5(File file) {
    return getDigestOf(file, "MD5");
  }

  public static String sha1(File file) {
    return getDigestOf(file, "SHA-1");
  }

  public static String sha256(File file) {
    return getDigestOf(file, "SHA-256");
  }

  public static String sha384(File file) {
    return getDigestOf(file, "SHA-384");
  }

  public static String sha512(File file) {
    return getDigestOf(file, "SHA-512");
  }
}
