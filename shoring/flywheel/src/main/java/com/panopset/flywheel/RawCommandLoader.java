package com.panopset.flywheel;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import com.panopset.compat.Logop;
import com.panopset.compat.Stringop;

class RawCommandLoader {

  private final Template tmplt;

  private final Deque<TemplateLine> queue = new ArrayDeque<>();

  private final List<Command> commands = new ArrayList<>();

  RawCommandLoader(final Template template) {
    assert template != null;
    tmplt = template;
  }

  List<Command> load() throws FlywheelException {
    tmplt.getTemplateSource().reset();
    int templateIndex = 0;
    int lineNumber = 0;
    while (!tmplt.getTemplateSource().isDone()) {
      if (tmplt.getFlywheel().isStopped()) {
        return new ArrayList<>();
      }
      flushQueue();
      if (tmplt.getFlywheel().isStopped()) {
        return new ArrayList<>();
      }
      String line = tmplt.getTemplateSource().next();
      if (tmplt.getTemplateRules().getLineBreaks().booleanValue()) {
        line = String.format("%s%s", line, Stringop.getEol());
      }
      process(new TemplateLine(line, templateIndex, lineNumber++));
      templateIndex += line.length();
    }
    flushQueue();
    return commands;
  }

  private void flushQueue() throws FlywheelException {
    while (!queue.isEmpty()) {
      process(queue.pop());
      if (tmplt.getFlywheel().isStopped()) {
        return;
      }
    }
  }

  private void loadCommand(final Command command) {
    if (tmplt.getFirstCommand() == null) {
      tmplt.setFirstCommand(command);
    }
    commands.add(command);
    Logop.debug("Loading command: " + command.toString());
  }

  private void process(final TemplateLine templateLine) throws FlywheelException {
    String line = templateLine.getLine();
    int openDirectiveLoc = line.indexOf(Syntax.getOpenDirective());
    int closeDirectiveLoc = line.indexOf(Syntax.getCloseDirective());
    if (closeDirectiveLoc == -1 || openDirectiveLoc == -1) {
      loadCommand(new CommandText(tmplt, templateLine, line));
      return;
    }
    if (closeDirectiveLoc < openDirectiveLoc) {
      skipTo(templateLine, openDirectiveLoc);
      return;
    }
    int endOfDirective = closeDirectiveLoc + Syntax.getCloseDirective().length();
    if (openDirectiveLoc == 0) {
      var command = new Command.Builder().template(tmplt).source(templateLine, closeDirectiveLoc).construct();
      if (command == null) {
        skipTo(templateLine, 1);
      } else {
        String remainder = line.substring(endOfDirective);
        loadCommand(command);
        if (remainder.length() > 0) {
          this.queue.push(new TemplateLine(remainder, templateLine.getTemplateCharIndex() + openDirectiveLoc, templateLine.getTemplateLineNumber()));
        }
      }
    } else {
      skipTo(templateLine, openDirectiveLoc);
    }
  }

  private void skipTo(final TemplateLine templateLine, final int pos) {
    String line = templateLine.getLine();
    int templateCharIndex = templateLine.getTemplateCharIndex();
    int templateLineNumber = templateLine.getTemplateLineNumber();
    String skippedTextLine = line.substring(0, pos);
    loadCommand(new CommandText(tmplt, new TemplateLine(skippedTextLine, templateCharIndex, templateLineNumber), skippedTextLine));
    this.queue.push(new TemplateLine(line.substring(pos), templateCharIndex + pos, templateLineNumber));
  }

  public static void addStructure(final List<Command> commands) {
    if (commands == null) {
      return;
    }
    Command prev = null;
    for (Command command : commands) {
      command.setPrev(prev);
      if (prev != null) {
        prev.setNext(command);
      }
      prev = command;
    }
  }
}
