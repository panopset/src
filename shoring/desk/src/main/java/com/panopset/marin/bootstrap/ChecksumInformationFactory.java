package com.panopset.marin.bootstrap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.panopset.compat.AppVersion;
import com.panopset.compat.ChecksumGenerator;
import com.panopset.compat.NameValuePair;
import com.panopset.marin.secure.checksums.ChecksumType;

public class ChecksumInformationFactory {

  public static List<NameValuePair> createList(String installerFileName, File installerFile) {
    List<NameValuePair> list = new ArrayList<>();
    list.add(new NameValuePair("version", AppVersion.getVersion()));
    list.add(new NameValuePair("bytes", ChecksumGenerator.byteCount(installerFile)));
    list.add(new NameValuePair(String.format("%s %s",installerFileName, ChecksumType.SHA512.getKey()), ChecksumGenerator.sha512(installerFile)));
    list.add(new NameValuePair("ifn", installerFileName));
    return list;
  }

}
