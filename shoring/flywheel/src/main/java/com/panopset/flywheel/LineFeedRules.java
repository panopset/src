package com.panopset.flywheel;

import com.panopset.compat.Stringop;

public class LineFeedRules {

  private Boolean lineBreaks;
  private Boolean listBreaks;

  public static final LineFeedRules LINE_BREAKS = new LineFeedRules(true, false);
  public static final LineFeedRules LIST_BREAKS = new LineFeedRules(false, true);
  public static final LineFeedRules FULL_BREAKS = new LineFeedRules(true, true);
  public static final LineFeedRules FLATTEN = new LineFeedRules(false, false);
  
  public LineFeedRules() {}

  public LineFeedRules(String lineBreaks, String listBreaks) {
    this.lineBreaks = Stringop.parseBoolean(lineBreaks);
    this.listBreaks = Stringop.parseBoolean(listBreaks);
  }

  public LineFeedRules(Boolean lineBreaks, Boolean listBreaks) {
    this.lineBreaks = lineBreaks;
    this.listBreaks = listBreaks;
  }

  public Boolean getLineBreaks() {
   if (lineBreaks == null) {
    lineBreaks = true;
   }
   return lineBreaks;
  }

  public void setLineBreaks(Boolean lineBreaks) {
   this.lineBreaks = lineBreaks;
  }

  public Boolean getListBreaks() {
   if (listBreaks == null) {
    listBreaks = true;
   }
   return listBreaks;
  }

  public void setListBreaks(Boolean listBreaks) {
   this.listBreaks = listBreaks;
  }
  
  @Override
  public String toString() {
    return String.format("LineB:%s ListB:%s", getLineBreaks(), getListBreaks());
  }
}
