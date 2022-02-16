package com.panopset.tests.flywheel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import com.panopset.flywheel.Flywheel;
import com.panopset.flywheel.FlywheelBuilder;

public class ComplexTest {

	public static final String TEMPLATE = "complexTest.txt";
	public static final String OUTPUT = "outdir/complexOut.html";
	public static final String EXPECTED = "complexTestExpected.html";
	private static final String FOO = "foo";
	private static final String BAR = "bar";

	@Test
	void testVariableDefinition() throws IOException {
		Flywheel script = new FlywheelBuilder().construct();
		script.put(FOO, BAR);
		script.exec();
		assertEquals(BAR, script.get(FOO));
	}

	void testScript() throws IOException {
		new SimpleTest().comparisonTest(TEMPLATE, "outdir/complexOut.html", EXPECTED);
	}

}
