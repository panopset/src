package com.panopset.tests.flywheel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.panopset.compat.Fileop;
import com.panopset.flywheel.Flywheel;
import com.panopset.flywheel.FlywheelBuilder;
import com.panopset.gp.FileCompare;
import com.panopset.tests.TestDirectory;

public class SimpleTest implements TestDirectory {

	// TODO
	public static final String TEST_FILE_PATH = "src/test/resources/com/panopset/tests/flywheel/";


	public static final String SIMPLETEST = "simpleTest.txt";
	public static final String SIMPLEOUT = "simpleOut.txt";
	public static final String EXPECTED = "simpleTestExpected.txt";

	@Test
	void testSimpleScript() throws IOException {
		new SimpleTest().comparisonTest(SIMPLETEST, SIMPLEOUT, EXPECTED);
	}

	public void comparisonTest(final String scriptName, final String generatedFileName, final String expected)
			throws IOException {
		comparisonTest(scriptName, new String[] { generatedFileName }, new String[] { expected });
	}

	public void comparisonTest(final String scriptName, final String[] generatedFileNames, final String[] expecteds)
			throws IOException {
		assertEquals(expecteds.length, generatedFileNames.length);
		int incr = 0;
		for (String generatedFileName : generatedFileNames) {
			File generatedFile = new File(Fileop.combinePaths(TEST_DIRECTORY, generatedFileName));
			if (generatedFile.exists()) {
				Fileop.delete(generatedFile);
			}
			Flywheel script = new FlywheelBuilder().targetDirectory(TEST_DIRECTORY)
					.file(new File(String.format("%s%s", TEST_FILE_PATH, scriptName))).construct();
			script.exec();
			assertFalse(script.isStopped());
			assertTrue(generatedFile.exists());
			assertTrue(FileCompare.filesAreSame(generatedFile,
					new File(String.format("%s%s", TEST_FILE_PATH, expecteds[incr++]))));
		}
	}

}
