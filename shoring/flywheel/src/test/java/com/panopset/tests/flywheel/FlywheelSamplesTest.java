package com.panopset.tests.flywheel;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.panopset.flywheel.samples.FlywheelSample;
import com.panopset.flywheel.samples.FlywheelSamples;

public class FlywheelSamplesTest {

  @Test
  void testGetSamples() {
    List<FlywheelSample> flywheelSamples = FlywheelSamples.all();
    Assertions.assertTrue(flywheelSamples.size() > 1);
    FlywheelSample fs0 = flywheelSamples.get(1);
    Assertions.assertEquals("${@p replaceme}textToReplaceWithCommandPrefix${@q}${@p pfx}${@${@q}${@r replaceme}${pfx}${@q}${l}\n", fs0.getTemplateText());
    Assertions.assertTrue(fs0.getLineBreaks());
    Assertions.assertFalse(fs0.getListBreaks());
    FlywheelSample fs1 = flywheelSamples.get(2);
    Assertions.assertFalse(fs1.getLineBreaks());
    Assertions.assertFalse(fs1.getListBreaks());
  }

}
