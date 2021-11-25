package com.panopset.marin.apps.gr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.panopset.compat.DirectoryProcessor;
import com.panopset.compat.DirectoryProcessor.Listener;
import com.panopset.compat.FileProcessor;
import com.panopset.compat.Fileop;
import com.panopset.compat.LineFilter;
import com.panopset.compat.Logop;
import com.panopset.compat.RegexValidator;
import com.panopset.compat.Stringop;

public class GlobalReplaceProcessor {
  private final List<LineFilter> filters = new ArrayList<>();
  private String priorLineMustContain;

  public void addLineFilter(LineFilter lineFilter) {
    filters.add(lineFilter);
  }

  public void process(File file, String searchStr, String replacementStr, String extensionsList, String regex,
      boolean recursive) throws IOException {
    final RegexValidator regexTester = regex == null ? null : new RegexValidator(regex);
    Listener listener = new Listener() {
      @Override
      public boolean processFile(File file) {
        if (file.isDirectory()) {
          Logop.warn(String.format("Ignoring directory: %s", Fileop.getCanonicalPath(file)));
          return false;
        }
        if (Stringop.isPopulated(extensionsList) && !Fileop.isFileOneOfExtensions(file, extensionsList)) {
          return false;
        }
        if (!Stringop.isPopulated(extensionsList) && regexTester != null) {
          if (!regexTester.matches(file.getName())) {
            return false;
          }
        }
        if (!Stringop.isPopulated(searchStr) && filters.isEmpty()) { // TODO: AND CRLF = do nothing.
          Logop.warn("No replacement specified, exiting.");
          return false;
        }
        FileProcessor fp = new FileProcessor(file).withLineFilter(new LineFilter() {

          @Override
          public String filter(String str) {
            String rtn = str;
            if (Stringop.isPopulated(getPriorLineMustContain())) {
              if (getPriorLine().contains(getPriorLineMustContain())) {
                rtn = str.replaceAll(searchStr, replacementStr);   
              }
            } else {
              rtn = str.replaceAll(searchStr, replacementStr);   
            }
            setPriorLine(str);
            return rtn; 
          }

          String priorLine;

          public String getPriorLine() {
            if (priorLine == null) {
              priorLine = "";
            }
            return priorLine;
          }

          public void setPriorLine(String priorLine) {
            this.priorLine = priorLine;
          }
          
        });
        if (!filters.isEmpty()) {
          for (LineFilter lineFilter : filters) {
            fp.withLineFilter(lineFilter);
          }
        }
        return fp.exec();
      }
    };
    new DirectoryProcessor(file, listener, recursive).exec();
    Logop.green("Done");
  }

  public void setPriorLineMustContain(String priorLineMustContain) {
    this.priorLineMustContain = priorLineMustContain;
  }

  public String getPriorLineMustContain() {
    if (priorLineMustContain == null) {
      priorLineMustContain = "";
    }
    return priorLineMustContain;
  }
}
