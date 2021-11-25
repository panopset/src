package com.panopset.flywheel;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import com.panopset.compat.Logop;


/**
 * Any command that is after burner processed with the QuitCommand must extend
 * this class.
 */
public abstract class MatchableCommand extends TemplateDirectiveCommand {

  /**
   * Commands.
   */
  private final List<Command> commands = new ArrayList<Command>();

  /**
   * @return List of commands.
   */
  public final List<Command> getCommands() {
    return commands;
  }

  /**
   * Command quit.
   */
  private CommandQuit commandQuit;

  /**
   * @return Command quit for this matchable command.
   */
  public final CommandQuit getCommandQuit() {
    return commandQuit;
  }

  /**
   * Set command quit for this matchable command.
   *
   * @param newCommandQuit
   *          New command quit to set.
   */
  public final void setCommandQuit(final CommandQuit newCommandQuit) {
    commandQuit = newCommandQuit;
  }

  MatchableCommand(final TemplateLine templateLine, final String innerPiece,
      final Template template) {
    super(templateLine, innerPiece, template);
  }

  /**
   * Resolve matched commands.
   *
   * @param sw
   *          StringWriter.
   */
  public final void resolveMatchedCommands(final StringWriter sw) {
    for (Command command : getCommands()) {
      if (getTemplate().getFlywheel().isStopped()) {
        return;
      }
      try {
        command.resolveCommand(sw);
      } catch (FlywheelException e) {
        Logop.error(e);
        getTemplate().getFlywheel().stop(e.getMessage());
        return;
      }
    }
  }

}
