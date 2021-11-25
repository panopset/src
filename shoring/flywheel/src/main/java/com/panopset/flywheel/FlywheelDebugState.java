package com.panopset.flywheel;

public interface FlywheelDebugState {

  void doStep(FlywheelDebugContext context);

  void doRun(FlywheelDebugContext context);

}
