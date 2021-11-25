package com.panopset.marin.bootstrap;

import java.io.File;
import java.io.StringWriter;
import com.panopset.compat.Fileop;
import com.panopset.compat.Jsonop;
import com.panopset.compat.Logop;

public class CreatePanopsetInfoFile {

  public static void main(String... args) {
    if (args == null) {
      printUsage();
      throw new NullPointerException();
    }
    if (args.length != 3) {
      printUsage();
      return;
    }
    new CreatePanopsetInfoFile().go(args[0], args[1], args[2]);
  }

  private void go(String installerFileName, String installerFilePath, String trg) {
    File targetFile = new File(trg);
    Fileop.checkParent(targetFile);
    String json = new Jsonop().toJson(ChecksumInformationFactory.createList(installerFileName, new File(installerFilePath)));
    Fileop.write(json, targetFile);
  }

  private static void printUsage() {
    Logop.dspmsg(generateUseText());
  }

  private static String generateUseText() {
    StringWriter sw = new StringWriter();
    sw.append("Usage:\n");
    sw.append("CreatePanopsetInfoFile <file-name> <path-to-file> <target-json>");
    return sw.toString();
  }
}
