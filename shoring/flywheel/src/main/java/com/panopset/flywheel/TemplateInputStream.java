package com.panopset.flywheel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import com.panopset.compat.Logop;

public class TemplateInputStream extends TemplateArray {

  public TemplateInputStream(InputStream inputStream) {
    super(inputStream2list(inputStream));
  }

  private static final List<String> inputStream2list(InputStream inputStream) {
    List<String> rtn = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
      String line = br.readLine();
      while (line != null) {
        rtn.add(line);
        line = br.readLine();
      }
    } catch (IOException ex) {
      Logop.error(ex);
    }
    return rtn;
  }
}
