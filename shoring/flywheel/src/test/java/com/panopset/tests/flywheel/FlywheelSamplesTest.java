package com.panopset.tests.flywheel;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.compat.Stringop;
import com.panopset.flywheel.samples.FlywheelSample;
import com.panopset.flywheel.samples.FlywheelSamples;

public class FlywheelSamplesTest {

	private static final String EXPECTED = "${@p replaceme}textToReplaceWithCommandPrefix${@q}${@p pfx}${@${@q}${@r replaceme}${pfx}${@q}${l}";

	@Test
	void testGetSamples() {
		Stringop.setEol("\n");
		List<FlywheelSample> flywheelSamples = FlywheelSamples.all();
		Assertions.assertTrue(flywheelSamples.size() > 1);
		FlywheelSample fs0 = flywheelSamples.get(1);
		String fs0text = fs0.getTemplateText().trim();
		String expected = EXPECTED;
		System.out.println(Stringop.firstHexDiff(expected, fs0text));
		Assertions.assertEquals(expected, fs0text);
		Assertions.assertTrue(fs0.getLineBreaks());
		Assertions.assertFalse(fs0.getListBreaks());
		FlywheelSample fs1 = flywheelSamples.get(2);
		Assertions.assertFalse(fs1.getLineBreaks());
		Assertions.assertFalse(fs1.getListBreaks());
	}

}
