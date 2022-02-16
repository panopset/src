package com.panopset.tests.gr;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.panopset.tests.transformer.GlobalReplaceTransformerTest;

public class PipeTest {

	private static final String REFRESH = "pipeTestRefresh.txt";
	private static final String TEMPLATE = "pipeTest.txt";
	private static final String EXPECTED = "pipeTestExpected.txt";

	@Test
	void test() throws IOException {
		new GlobalReplaceTransformerTest("red", "yellow", "blue", this.getClass().getPackageName(), REFRESH, TEMPLATE,
				EXPECTED).test();
	}
}
