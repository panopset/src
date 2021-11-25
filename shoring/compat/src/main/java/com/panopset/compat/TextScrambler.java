package com.panopset.compat;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p>
 * If you do not want to use the defaults, the values must be set prior to calling encrypt or
 * decrypt. For example, to change the key obtention iterations:
 * </p>
 * 
 * <pre>
 * Stringop.setEol(&quot;&#92;n&quot;); // If you don't want the operating system to decide
 *                        // what return character to use for encrypted text linewrapping.
 * new TextScrambler().withKeyObtentionIterations(5000).encrypt(pwd, msg);
 * </pre>
 */
public final class TextScrambler {
  
  static {
    Security.setProperty("crypto.policy", "unlimited");
  }

  public static final int DEFAULT_WRAP_WIDTH = 80;
  private int wrapWidth = DEFAULT_WRAP_WIDTH;
  public static final String DEFAULT_KEYGEN_ALGORITHM = "AES";
  private String keygenAlgorithm = DEFAULT_KEYGEN_ALGORITHM;
  public static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5PADDING";
  private String cipherAlgorithm = DEFAULT_CIPHER_ALGORITHM;
  public static final String DEFAULT_KEY_ALGORITHM = "PBKDF2WithHmacSHA512";
  private String keyAlgorithm = DEFAULT_KEY_ALGORITHM;
  public static final int DEFAULT_KEY_OBTENTION_ITERATIONS = 10000;
  private int iters = DEFAULT_KEY_OBTENTION_ITERATIONS;

  private Cipher createCipher() {
    try {
      return Cipher.getInstance(cipherAlgorithm);
    } catch (Exception e) {
      Logop.error(e);
      throw new RuntimeException(e);
    }
  }

  private SecretKeyFactory secretKeyFactory;

  private SecretKeyFactory getSecretKeyFactory() {
    if (secretKeyFactory == null) {
      try {
        secretKeyFactory = SecretKeyFactory.getInstance(keyAlgorithm);
      } catch (NoSuchAlgorithmException e) {
        Logop.error(e);
        throw new RuntimeException(e);
      }
    }
    return secretKeyFactory;
  }

  /**
   * Encrypt a message.
   * 
   * @param password User password, pass phrase.
   * @param msg Message to be encrypted.
   * @return Base64 encoded encrypted message.
   * @throws Exception Exception.
   */
  public String encrypt(final String password, final String msg) throws Exception {
    Logop.clear();
    if (Stringop.isPopulated(password)) {
      String pkgStr = new String(Base64.getUrlEncoder().encode(encrypt2bytes(password, msg)));
      return Stringop.wrapFixedWidth(pkgStr, wrapWidth);
    } else {
      throw new Exception(Nls.xlate("Please specify a password."));
    }
  }

  /**
   * Use this method if just want the bytes.
   * 
   * @param password Password or passphrase.
   * @param msg Message to encrypt.
   * @return byte array for easy transmission and storage.
   */
  public byte[] encrypt2bytes(final String password, final String msg) throws Exception {
    byte[] salt = new byte[8];
    Randomop.nextBytes(salt);
    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iters, 128);
    SecretKeySpec secretKey =
        new SecretKeySpec(getSecretKeyFactory().generateSecret(spec).getEncoded(), keygenAlgorithm);
    Cipher cipher = createCipher();
    byte[] iv = new byte[16];
    Randomop.nextBytes(iv);
    IvParameterSpec ivspec = new IvParameterSpec(iv);
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
    byte[] encryptedData = cipher.doFinal(msg.getBytes(Stringop.UTF_8));
    byte[] rtn = new byte[salt.length + iv.length + encryptedData.length];
    System.arraycopy(salt, 0, rtn, 0, salt.length);
    System.arraycopy(iv, 0, rtn, salt.length, iv.length);
    System.arraycopy(encryptedData, 0, rtn, salt.length + iv.length, encryptedData.length);
    return rtn;
  }

  /**
   * Decrypt a message.
   * 
   * @param password User password, pass phrase.
   * @param msg Message to be decrypted.
   * @return Decrypted message.
   * @throws Exception Exception.
   */
  public String decrypt(final String password, final String msg) throws Exception {
    Logop.clear();
    try {
      String msgTrim =
          msg.trim().replace(Stringop.CARRIAGE_RETURN, "").replace(Stringop.LINE_FEED, "");
      byte[] pkgBytes = Base64.getUrlDecoder().decode(msgTrim);
      return decryptFromBytes(password, pkgBytes);
    } catch (IllegalArgumentException ex) {
      throw new Exception(Nls.xlate("Likely wrong password, or not an encrypted message, because")
          + ": " + ex.getMessage());
    }
  }

  /**
   * Decrypt a message that was encrypted with encrypt2bytes.
   * 
   * @param password User password, pass phrase.
   * @param bytes Message to be decrypted.
   * @return Decrypted message.
   * @throws Exception Exception.
   */
  public String decryptFromBytes(final String password, final byte[] bytes) throws Exception {
    if (bytes.length < 25) {
      throw new Exception(Nls.xlate("Not an encrypted message."));
    }
    ByteBuffer bb = ByteBuffer.wrap(bytes);
    int msgLength = bytes.length - 24;
    byte[] salt = new byte[8];
    byte[] iv = new byte[16];
    byte[] encr = new byte[msgLength];
    bb.get(salt, 0, 8);
    bb.get(iv, 0, 16);
    bb.get(encr, 0, msgLength);
    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iters, 128);
    SecretKeySpec secretKey =
        new SecretKeySpec(getSecretKeyFactory().generateSecret(spec).getEncoded(), keygenAlgorithm);
    Cipher cipher = createCipher();
    cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
    byte[] data = cipher.doFinal(encr);
    return new String(data);
  }

  /**
   * @param value Default is <b>80</b>. Set to 0 or -1 for no wrapping.
   * @return this.
   */
  public TextScrambler withWrapWidth(final int value) {
    wrapWidth = value;
    return this;
  }

  /**
   * 
   * @param value Default is <b>10000</b>.
   * @return this.
   */
  public TextScrambler withKeyObtentionIters(final int value) {
    iters = value;
    return this;
  }


  /**
   * 
   * @param value Default is <b>AES</b>.
   * @return this.
   */
  public TextScrambler withKeygenAlgorithm(final String value) {
    keygenAlgorithm = value;
    return this;
  }

  /**
   * 
   * @param value Default is <b>AES/CBC/PKCS5PADDING</b>.
   * @return this.
   */
  public TextScrambler withCipherAlgorithm(final String value) {
    cipherAlgorithm = value;
    return this;
  }

  /**
   * 
   * @param value Default is <b>PBKDF2WithHmacSHA512</b>.
   * @return this.
   */
  public TextScrambler withKeyAlgorithm(final String value) {
    keyAlgorithm = value;
    return this;
  }
}
