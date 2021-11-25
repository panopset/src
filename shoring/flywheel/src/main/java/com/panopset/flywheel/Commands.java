package com.panopset.flywheel;

/**
 * Enumeration of all commands.
 */
public enum Commands {

  /**
   * Replace command.
   */
  REPLACE('r', "r:Replace", "${@r ?}"),

  /**
   * File command.
   */
  FILE('f', "f:File", "${@f ?}"),

  /**
   * Push command.
   */
  PUSH('p', "p:Push", "${@p ?}?${@q}"),

  /**
   * List command.
   */
  LIST('l', "l:List", "${@l ?}?${@q}"),

  /**
   * Quit command.
   */
  QUIT('q', "q:Quit", "${@q}"),

  /**
   * Template command.
   */
  TEMPLATE('t', "t:Template", "${@t ?}"),

  /**
   * Execute command.
   */
  EXECUTE('e', "e:Execute", "${@e ?}"),

  /**
   * Break command.
   */
  BREAK('b', "Break", "${@b}");

  /**
   * Script character code associated with a Flywheel command.
   */
  private final char charCode;

  /**
   * Command name.
   */
  private final String name;

  /**
   * Command prototype.
   */
  private final String prototype;

  /**
   * @return Command char code.
   */
  public char getCharCode() {
    return charCode;
  }

  /**
   * @return Command name.
   */
  public String getName() {
    return name;
  }

  /**
   * @return Command prototype.
   */
  public String getPrototype() {
    return prototype;
  }

  /**
   * Constructor.
   * 
   * @param newCharCode
   *          Char code.
   * @param newName
   *          Name.
   * @param newPrototype
   *          Prototype.
   */
  Commands(final char newCharCode, final String newName,
      final String newPrototype) {
    this.charCode = newCharCode;
    this.name = newName;
    this.prototype = newPrototype;
  }
}
