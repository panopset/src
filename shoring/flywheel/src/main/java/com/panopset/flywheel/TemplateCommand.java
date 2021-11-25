package com.panopset.flywheel;

import java.io.StringWriter;

/**
 * A template command is specified in a script, and has a source.
 */
public abstract class TemplateCommand extends Command {

  private final String innerp;

  final String getInnerPiece() {
    return innerp;
  }

  private String parms;

  @Override
  public final String getDescription() {
    StringWriter sw = new StringWriter();
    sw.append(getTemplateLine().getLine());
    return sw.toString();
  }

  final void setParams(final String parameters) {
    parms = parameters;
  }

  TemplateCommand(final TemplateLine templateLine, final String innerPiece,
      final Template template) {
    super(template, templateLine);
    innerp = innerPiece;
  }

  public final String getParams() {
    if (parms == null) {
      return "";
    }
    if (parms.length() > Syntax.getDirective().length()
        && parms.indexOf(Syntax.getDirective()) == 0) {
      return getTemplate().getFlywheel().get(
          parms.substring(Syntax.getDirective().length()));
    }
    return parms;
  }
}
