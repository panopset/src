package com.panopset.flywheel;

public class FlywheelDebugStateStepping implements FlywheelDebugState {

  @Override
  public void doStep(FlywheelDebugContext context) {
    context.getFlywheel().releasePause();
  }

  @Override
  public void doRun(FlywheelDebugContext context) {
    context.setState(new FlywheelDebugStateRunning());
    context.getFlywheel().releasePause();
  }

}
