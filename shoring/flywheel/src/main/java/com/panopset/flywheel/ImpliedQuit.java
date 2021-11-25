package com.panopset.flywheel;

public class ImpliedQuit extends CommandQuit {

  public ImpliedQuit(final Template template) {
    super(new TemplateLine(Syntax.getOpenDirective() + "q" + Syntax.getCloseDirective()),
        template);
  }

}
