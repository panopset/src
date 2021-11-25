package com.panopset.flywheel;

/**
 * 
 * Used by Flywheel clients to step through commands.
 *
 */
public abstract class FlywheelDebugContext implements FlywheelDebugger {
  FlywheelDebugState state;
  private Flywheel flywheel;

  public void doStep() {
    getState().doStep(this);
  }

  public void doRun() {
    getState().doRun(this);
  }
  
  public void setFlywheel(Flywheel flywheel) {
    this.flywheel = flywheel;
  }
  
  public Flywheel getFlywheel() {
    return flywheel;
  }

  public FlywheelDebugState getState() {
    if (state == null) {
      reset();
    }
    return state;
  }

  public void setState(FlywheelDebugState state) {
    this.state = state;
  }
  
  public void reset() {
    cleanup();
    setState(new FlywheelDebugStateIdle());
  }
  
  void doBreak() {
    setState(new FlywheelDebugStateStepping());
  }
}
