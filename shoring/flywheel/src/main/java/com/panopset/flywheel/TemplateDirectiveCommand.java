package com.panopset.flywheel;

import java.io.StringWriter;
import com.panopset.compat.Stringop;

/**
 * Command that is created from a directive.
 */
public class TemplateDirectiveCommand extends TemplateCommand {

  public TemplateDirectiveCommand(final TemplateLine templateLine, final String innerPiece,
      final Template template) {
    super(templateLine, innerPiece, template);
    String params = "";
    if (innerPiece.length() > 2) {
      params = innerPiece.substring(Syntax.getDirective().length() + 2);
    }
    
    String replacement = getTemplate().getFlywheel().get(params);
    
    if (Stringop.isPopulated(replacement)) {
      setParams(replacement);
    } else {
      setParams(params);
    }
  }

  @Override
  public void resolve(final StringWriter sw) throws FlywheelException {
    // does nothing here.
  }

}
