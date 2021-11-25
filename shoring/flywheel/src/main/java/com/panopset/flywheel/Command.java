package com.panopset.flywheel;

import java.io.StringWriter;
import com.panopset.compat.Nls;

public abstract class Command {

  private final Template template;
  private final TemplateLine templateLine;
  private Command nextCmd;
  private Command prevCmd;

  public Command(final Template template, TemplateLine templateLine) {
    this.template = template;
    this.templateLine = templateLine;
  }

  public final Command getNext() {
    return nextCmd;
  }

  public final void setNext(final Command next) {
    nextCmd = next;
  }

  public final Command getPrev() {
    return prevCmd;
  }

  public final void setPrev(final Command prev) {
    prevCmd = prev;
  }

  public final Template getTemplate() {
    return template;
  }

  public final TemplateLine getTemplateLine() {
    return templateLine;
  }

  public abstract void resolve(StringWriter sw) throws FlywheelException;

  public final void resolveCommand(final StringWriter sw) throws FlywheelException {
    resolve(sw);
  }

  public abstract String getDescription();

  @Override
  public final String toString() {
    StringWriter sw = new StringWriter();
    sw.append(this.getTemplate().getRelativePath());
    sw.append(" ");
    sw.append(getTemplateLine().toString());
    return sw.toString();
  }

  public static final class Builder {
    private Template template;
    private TemplateLine templateLine;
    private int closeDirectiveLoc;
    private Command command;

    public Builder template(final Template commandTemplate) {
      this.template = commandTemplate;
      return this;
    }

    public Builder source(final TemplateLine templateLine, final int newCloseDirectiveLoc) {
      this.templateLine = templateLine;
      closeDirectiveLoc = newCloseDirectiveLoc;
      return this;
    }

    public Builder command(final Command newCommand) {
      command = newCommand;
      return this;
    }

    public Command construct() throws FlywheelException {
      Command rtn = null;
      if (command == null) {
        if (templateLine == null) {
          throw new FlywheelException(
              Nls.xlate("Not enough information to create a command"));
        }

        String line = templateLine.getLine();
        int ln = templateLine.getTemplateLineNumber();
        int tci = templateLine.getTemplateCharIndex();
        
        final String innerPiece = line.substring(Syntax.getOpenDirective()
            .length(), closeDirectiveLoc);
        if (innerPiece.indexOf(Syntax.getDirective()) == 0
            && innerPiece.length() > 1) {
          char cmd = innerPiece.substring(1, 2).charAt(0);
          if (cmd == Commands.FILE.getCharCode()) {
            rtn = new CommandFile(new TemplateLine(line, tci, ln), innerPiece, template);
          } else if (cmd == Commands.PUSH.getCharCode()) {
            rtn = new CommandPush(templateLine, innerPiece, template);
          } else if (cmd == Commands.REPLACE.getCharCode()) {
            rtn = new CommandReplace(templateLine, innerPiece, template);
          } else if (cmd == Commands.LIST.getCharCode()) {
            rtn = new CommandList(templateLine, innerPiece, template);
          } else if (cmd == Commands.QUIT.getCharCode()) {
            rtn = new CommandQuit(templateLine, template);
          } else if (cmd == Commands.TEMPLATE.getCharCode()) {
            rtn = new CommandTemplate(templateLine, innerPiece, template);
          } else if (cmd == Commands.BREAK.getCharCode()) {
            rtn = new CommandBreak(templateLine, innerPiece, template);
          } else if (cmd == Commands.EXECUTE.getCharCode()) {
            rtn = new CommandExecute(templateLine, innerPiece, template);
          }
        } else {
          rtn = new CommandVariable(templateLine, innerPiece, template);
        }
      } else {
        rtn = command;
      }
      return rtn;
    }
  }
}
