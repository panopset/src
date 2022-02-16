package com.panopset.tests.flywheel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.panopset.flywheel.Flywheel;
import com.panopset.flywheel.FlywheelBuilder;
import com.panopset.flywheel.LineFeedRules;
import com.panopset.tests.transformer.FlywheelTemplateToFileTest;

public final class ReplacementTest {

	private static final String TEST_FILE_NAME = "replacementsTest.txt";

	@Test
	void testReplacements() {
		FlywheelTemplateToFileTest fttft = new FlywheelTemplateToFileTest(getClass().getPackageName(), TEST_FILE_NAME, TEST_FILE_NAME, "replacementsTestExpected.txt");
		FlywheelBuilder fb = fttft.getFlywheelBuilder();
		fb.replacement("foo", "bar");
		fttft.test();
//		Flywheel flywheel = fttft.getFlywheel();
//		File generatedFile = new File(SimpleTest.TEST_DIRECTORY + "/" + TEST_FILE_NAME);
//		if (generatedFile.exists()) {
//			Fileop.delete(generatedFile);
//		}
//		Flywheel script = new FlywheelBuilder().targetDirectory(SimpleTest.TEST_DIRECTORY)
//				.file(new File(SimpleTest.TEST_FILE_PATH + TEST_FILE_NAME)).replacement("foo", "bar").construct();
//		script.exec();
//		assertFalse(flywheel.isStopped());
//		Logop.dspmsg(Fileop.getCanonicalPath(generatedFile));
//		assertTrue(generatedFile.exists());
//		assertTrue(generatedFile.length() > 0);
//		assertTrue(FileCompare.filesAreSame(generatedFile,
//				new File(SimpleTest.TEST_FILE_PATH + "replacementsTestExpected.txt")));
	}

	@Test
	void testAtReplacement() {
		Flywheel f = new FlywheelBuilder().input("${@p x}${@${@q}${x}q}")
				.withLineFeedRules(new LineFeedRules(false, false)).construct();
		assertEquals("${@q}", f.exec());
	}
}
