package com.panopset.flywheel;

public class FlywheelDebugStateIdle implements FlywheelDebugState {

  @Override
  public void doStep(FlywheelDebugContext context) {
    context.setState(new FlywheelDebugStateStepping());
    context.initiateRun();
    
  }

  @Override
  public void doRun(FlywheelDebugContext context) {
    context.setState(new FlywheelDebugStateRunning());
    context.initiateRun();
  }

}
