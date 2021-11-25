package com.panopset.flywheel;

/**
 * Flywheel control flags.
 */
public final class Control {

  private boolean stopped = false;
  private String stopReason = "";
  private FlywheelDebugContext context;

  /**
   * @return true if replacements are suppressed.
   */
  public boolean isReplacementsSuppressed() {
    return replacementsSuppressed;
  }

  /**
   * Used to temporarily suppress replacements.
   *
   * @param value Replacements suppressed.
   */
  public void setReplacementsSuppressed(final boolean value) {
    this.replacementsSuppressed = value;
  }

  /**
   * @return Stop execution flag.
   */
  public boolean isStopped() {
    return stopped;
  }

  public String getStopReason() {
    return stopReason;
  }

  /**
   * Some error conditions require that Flywheel execution must stop.
   */
  public void stop(String reason) {
    stopReason = reason;
    stopped = true;
    getContext().reset();
  }

  private boolean replacementsSuppressed = false;

  public void setFlywheelDebugContext(FlywheelDebugContext flywheelDebugContext) {
    this.context = flywheelDebugContext;
  }

  FlywheelDebugContext getContext() {
    if (context == null) {
      context = new FlywheelDebugContext() {};
    }
    return context;
  }
}
