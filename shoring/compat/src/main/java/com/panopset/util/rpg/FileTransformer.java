package com.panopset.util.rpg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.panopset.compat.Logop;
import com.panopset.compat.Stringop;

public abstract class FileTransformer {

  protected abstract String filter(String inputLine);

  public FileTransformer(File input, File output) {
    inp = input;
    outp = output;
  }

  public void exec() {
    try (FileReader fr = new FileReader(inp);
      BufferedReader br = new BufferedReader(fr);
      FileWriter fw = new FileWriter(outp);
      BufferedWriter bw = new BufferedWriter(fw)
    ) {
      String line = br.readLine();
      while (line != null) {
        String fl = filter(line);
        if (fl != null) {
          bw.write(fl);
          bw.write(Stringop.EOL);
        }
        line = br.readLine();
      }
    } catch (IOException e) {
      Logop.error(e);
    }
  }

  private final File inp;

  private final File outp;
}
