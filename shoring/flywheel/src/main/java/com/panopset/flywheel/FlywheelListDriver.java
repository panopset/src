package com.panopset.flywheel;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import com.panopset.compat.Splitter;
import com.panopset.compat.Stringop;

public class FlywheelListDriver {

  private List<String> inputList;
  private final String templateStr;
  private String splitz;
  private String tokens;
  private LineFeedRules lineFeedRules;

  public LineFeedRules getLineFeedRules() {
    if (lineFeedRules == null) {
      lineFeedRules = new LineFeedRules();
    }
    return lineFeedRules;
  }

  public void setLineFeedRules(LineFeedRules lineFeedRules) {
    this.lineFeedRules = lineFeedRules;
  }

  public List<String> getInputList() {
    return inputList;
  }

  public void setInputList(List<String> value) {
    inputList = value;
  }

  public String getTemplateStr() {
    return templateStr;
  }

  public boolean hasSplitz() {
    return getSplitz().length() > 0;
  }

  public String getSplitz() {
    if (splitz == null) {
      splitz = "";
    }
    return splitz;
  }

  public void setSplitz(String value) {
    splitz = value;
  }

  public String getTokens() {
    if (tokens == null) {
      tokens = "";
    }
    return tokens;
  }

  public void setTokens(String value) {
    tokens = value;
  }
  
  public synchronized String getOutput() throws IOException {
    if (hasSplitz()) {
      StringWriter sw = new StringWriter();
      for (String s : getInputList()) {
        Iterable<String> chunks = Splitter.fixedLengths(getSplitz()).split(s);
        List<String> chunky = new ArrayList<>();
        for (String chunk : chunks) {
          chunky.add(chunk);
        }
        setInputList(chunky);
        sw.append(processInput());
        sw.append(Stringop.getEol());
      }
      return sw.toString();
    } else {
      return processInput();
    }
  }

  private String processInput() throws IOException {
    StringWriter sw = new StringWriter();
    for (String s : getInputList()) {
      if (s.isBlank()) {
        continue;
      }
      Flywheel flywheel = new FlywheelBuilder().map(createInputMapFrom(s))
          .input(Stringop.stringToList(getTemplateStr())).withLineFeedRules(getLineFeedRules())
          .withWriter(sw).construct();
      flywheel.exec();
      if (flywheel.isStopped()) {
        return String.format("Stopped: %s", flywheel.getControl().getStopReason());
      }
      if (flywheel.getTemplate().getTemplateRules().getListBreaks().booleanValue()) {
        sw.append(Stringop.getEol());
      }
    }
    return sw.toString();
  }

  private Map<String, String> createInputMapFrom(final String inputLine) {
    Map<String, String> rtn = new HashMap<>();
    rtn.put("l", inputLine);
    if (Stringop.isPopulated(inputLine)) {
      int i = 0;
      StringTokenizer st = new StringTokenizer(inputLine, " ");
      while (st.hasMoreTokens()) {
        rtn.put(String.format("w%d", i++), st.nextToken());
      }
      new TokenVariableFactory().addTokensToMap(rtn, inputLine, getTokens());
    }
    return rtn;
  }

  private FlywheelListDriver(List<String> inputList, String templateStr) {
    this.inputList = inputList;
    this.templateStr = templateStr;
  }

  public static class Builder {
    public Builder(List<String> inputList, String templateStr) {
      fp = new FlywheelListDriver(inputList, templateStr);
    }

    public Builder(String[] inputArray, String templateStr) {
      fp = new FlywheelListDriver(Arrays.asList(inputArray), templateStr);
    }

    final FlywheelListDriver fp;

    public FlywheelListDriver build() {
      if (fp.inputList.isEmpty()) {
        fp.inputList.add("");
      }
      return fp;
    }

    public Builder withLineFeedRules(LineFeedRules lineFeedRules) {
      fp.setLineFeedRules(lineFeedRules);
      return this;
    }

    public Builder withSplitz(String value) {
      fp.setSplitz(value);
      return this;
    }

    public Builder withTokens(String value) {
      fp.setTokens(value);
      return this;
    }
  }
}
