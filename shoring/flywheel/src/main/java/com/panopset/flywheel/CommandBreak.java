package com.panopset.flywheel;

import java.io.StringWriter;

public class CommandBreak extends TemplateDirectiveCommand {

  public CommandBreak(TemplateLine templateLine, String innerPiece, Template template) {
    super(templateLine, innerPiece, template);
  }


  @Override
  public void resolve(final StringWriter sw) throws FlywheelException {
    getTemplate().getFlywheel().getControl().getContext().doBreak();
  }
}
