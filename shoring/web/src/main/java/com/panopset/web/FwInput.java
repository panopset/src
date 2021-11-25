package com.panopset.web;

public class FwInput {
  private Boolean lineBreakStr;
  private Boolean listBreakStr;
  private String listStr;
  private String template;
  private String tokens;
  private String splitz;

  public Boolean getLineBreakStr() {
    if (lineBreakStr == null) {
      lineBreakStr = Boolean.FALSE;
    }
    return lineBreakStr;
  }

  public void setLineBreakStr(Boolean lineBreakStr) {
    this.lineBreakStr = lineBreakStr;
  }

  public Boolean getListBreakStr() {
    if (listBreakStr == null) {
      listBreakStr = Boolean.FALSE;
    }
    return listBreakStr;
  }

  public void setListBreakStr(Boolean listBreakStr) {
    this.listBreakStr = listBreakStr;
  }

  public String getListStr() {
    if (listStr == null) {
      listStr = "";
    }
    return listStr;
  }

  public void setListStr(String listStr) {
    this.listStr = listStr;
  }

  public String getTemplate() {
    if (template == null) {
      template = "";
    }
    return template;
  }

  public void setTemplate(String template) {
    this.template = template;
  }

  public String getTokens() {
    if (tokens == null) {
      tokens = "";
    }
    return tokens;
  }

  public void setTokens(String tokens) {
    this.tokens = tokens;
  }

  public String getSplitz() {
    if (splitz == null) {
      splitz = "";
    }
    return splitz;
  }

  public void setSplitz(String splitz) {
    this.splitz = splitz;
  }

}
