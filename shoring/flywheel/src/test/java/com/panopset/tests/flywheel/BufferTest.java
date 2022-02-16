package com.panopset.tests.flywheel;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.panopset.compat.Stringop;
import com.panopset.tests.transformer.FlywheelTemplateToFileTest;
import com.panopset.tests.transformer.FlywheelTemplateToTextTransformerTest;
import com.panopset.tests.transformer.StandardPackagePath;

public final class BufferTest {

	public static final String SIMPLEONECHAR = "simpleOneChar.txt";
	public static final String SIMPLETWOLINES = "simpleTwoLines.txt";

	@Test
	void testSimpleOneChar() throws IOException {
		String expected = String.format("x%s", Stringop.getEol());
		new FlywheelTemplateToTextTransformerTest(this.getClass().getPackageName(), SIMPLEONECHAR, expected).test();
	}

	@Test
	void testTwoLines() throws IOException {
		String expected = "x" + Stringop.getEol() + "y" + Stringop.getEol();
		new FlywheelTemplateToTextTransformerTest(this.getClass().getPackageName(), SIMPLETWOLINES, expected).test();
	}

	@Test
	void testSimpleBuffer() throws IOException {
		new FlywheelTemplateToFileTest(this.getClass().getPackageName(), SimpleTest.SIMPLETEST, SimpleTest.SIMPLEOUT, SimpleTest.EXPECTED).test();
		String expected = new StandardPackagePath(this.getClass().getPackageName()).getRezStr(SimpleTest.EXPECTED);
		new FlywheelTemplateToTextTransformerTest(this.getClass().getPackageName(), SimpleTest.SIMPLETEST, expected).test();
		new FlywheelTemplateToFileTest(this.getClass().getPackageName(), SimpleTest.SIMPLETEST, SimpleTest.SIMPLEOUT, SimpleTest.EXPECTED).test();
	}

	@Test
	void testComplexBuffer() throws IOException {
		String expected = new StandardPackagePath(this.getClass().getPackageName()).getRezStr(ComplexTest.EXPECTED);
		new FlywheelTemplateToTextTransformerTest(this.getClass().getPackageName(), ComplexTest.TEMPLATE, expected).test();
		new FlywheelTemplateToFileTest(this.getClass().getPackageName(), ComplexTest.TEMPLATE, ComplexTest.OUTPUT, ComplexTest.EXPECTED).test();
	}
}
