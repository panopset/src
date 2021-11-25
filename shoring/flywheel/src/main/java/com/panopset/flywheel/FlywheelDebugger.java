package com.panopset.flywheel;

public interface FlywheelDebugger {
  default void cleanup() {}
  default void initiateRun() {}
  default void showTemplate(Template t) {}
  default void showTopCommand(Template t, Command c) {}
  default void showResult(String result) {}
}
