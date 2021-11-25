package com.panopset.flywheel;

import java.util.ArrayList;
import java.util.List;

/**
 * Implied quit filter.
 * <p>
 * Adds implied Command Quits to the commands vector.
 * </p>
 */
class ImpliedQuitFilter {

  /**
   * Add implied quits.
   *
   * @param commands
   *          Commands to add implied quits to.
   * @return List of commands with implied Command Quits added.
   */
  List<Command> addImpliedQuits(final List<Command> commands) throws FlywheelException {
    List<Command> rtn = new ArrayList<Command>();
    if (commands == null) {
      return rtn;
    }
    Command first = null;
    boolean checkOnce = false;
    for (Command command : commands) {
      if (first == null) {
        first = command;
      }
      if (command instanceof CommandFile) {
        insertImpliedQuitBefore(command);
        command.getTemplate().setCommandFile((CommandFile) command);
      }
      if (command.getNext() == null
          && command.getTemplate().getCommandFile() != null) {
        if (checkOnce) {
          throw new FlywheelException("Structure error.");
        }
        ImpliedQuit iq = new ImpliedQuit(command.getTemplate());
        command.setNext(iq);
        iq.setPrev(command);
        checkOnce = true;
      }
    }
    Command command = first;
    while (command != null) {
      rtn.add(command);
      command = command.getNext();
    }
    return rtn;
  }

  /**
   * Insert implied quit.
   * 
   * @param command
   *          Command to insert before.
   */
  private void insertImpliedQuitBefore(final Command command) {
    if (command.getTemplate().getCommandFile() == null) {
      return;
    }
    ImpliedQuit iq = new ImpliedQuit(command.getTemplate());
    iq.setNext(command);
    if (command.getPrev() != null) {
      iq.setPrev(command.getPrev());
      command.getPrev().setNext(iq);
    }
  }

}
