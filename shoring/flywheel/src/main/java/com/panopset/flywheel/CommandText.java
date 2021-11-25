package com.panopset.flywheel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import com.panopset.compat.Logop;

/**
 * Command to handle normal text entry.
 */
public class CommandText extends Command {

  private String text;

  public CommandText(final Template template, final TemplateLine templateLine, final String params) {
    super(template, templateLine);
    this.text = params;
  }

  @Override
  public final String getDescription() {
    StringReader sr = new StringReader(text);
    try (BufferedReader br = new BufferedReader(sr)) {
      String str = br.readLine();
      if (str == null) {
        return str = "";
      }
      return "CommandText:" + str;
    } catch (IOException ex) {
      Logop.error(ex);
      return ex.getMessage();
    }
  }

  @Override
  public final void resolve(final StringWriter sw) {
    String rtn = text;
    if (!getTemplate().getFlywheel().isReplacementsSuppressed()) {
      for (String[] s : getTemplate().getFlywheel().getReplacements()) {
        rtn = rtn.replace(s[0], s[1]);
      }
    }
    sw.append(rtn);
    Logop.debug(String.format("%s %4d: %s", getTemplate().getTemplateSource().getName(),
        getTemplate().getTemplateSource().getLine(), rtn));
  }
}
