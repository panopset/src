package com.panopset.flywheel;

public class FlywheelDebugStateRunning implements FlywheelDebugState {

  @Override
  public void doStep(FlywheelDebugContext context) {
    context.setState(new FlywheelDebugStateStepping());
    context.getFlywheel().releasePause();
  }

  @Override
  public void doRun(FlywheelDebugContext context) {
    context.getFlywheel().releasePause();
  }

}
