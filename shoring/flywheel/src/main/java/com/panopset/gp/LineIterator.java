package com.panopset.gp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import com.panopset.compat.Logop;

public class LineIterator {
  private String next;
  
  private final BufferedReader br;

  public LineIterator(Reader reader) {
    br = new BufferedReader(reader);
  }

  public void close() throws IOException {
    br.close();
  }

  public String next() {
    String rtn = next;
    try {
      next = br.readLine();
    } catch (IOException ex) {
      Logop.handle(ex);
    }
    return rtn;
  }

  public boolean hasNext() {
    return next != null;
  }
}
