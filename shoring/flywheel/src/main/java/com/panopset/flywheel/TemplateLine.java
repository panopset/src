package com.panopset.flywheel;

public class TemplateLine {
  private final String line;
  private final Integer templateLineNumber;
  private final Integer templateCharIndex;
  
  public TemplateLine(String line, int templateCharIndex, int templateLineNumber) {
    this.line = line;
    this.templateCharIndex = templateCharIndex;
    this.templateLineNumber = templateLineNumber;
  }

  public TemplateLine(String line) {
    this(line, 0, 0);
  }

  public String getLine() {
    return line;
  }

  public Integer getTemplateLineNumber() {
    return templateLineNumber;
  }

  public Integer getTemplateCharIndex() {
    return templateCharIndex;
  }
  
  @Override
  public String toString() {
    return String.format(" line#: %5d, char#: 5%d line: %s", getTemplateLineNumber(), getTemplateCharIndex(), getLine()); 
  }
}
