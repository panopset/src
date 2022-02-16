package com.panopset.tests.transformer;

import java.io.File;

import com.panopset.compat.Fileop;
import com.panopset.flywheel.Flywheel;
import com.panopset.flywheel.FlywheelBuilder;

public class FlywheelTemplateToFileTest extends StandardTransformerTest {

	private final String fromFileRezPath;
	private final String tempFileName;
	private final String expectedFileRezPath;

	public FlywheelTemplateToFileTest(String packageRelPath, String fromFileRezPath, String tempFileName,
			String expectedFileRezPath) {
		super(packageRelPath);
		this.tempFileName = tempFileName;
		this.fromFileRezPath = fromFileRezPath;
		this.expectedFileRezPath = expectedFileRezPath;
	}

	@Override
	protected ResultsDataSupplier createResultsDataSupplier() {

		return new ResultsDataSupplier() {

			@Override
			public String getExpectedResults() {
				return getExpected();
			}

			@Override
			public String getActualResults() {
				getFlywheel().exec();
//				File generatedFile = new File(String.format("temp/%s", tempFileName));
				File generatedFile = new File(Fileop.combinePaths(TEST_DIRECTORY, tempFileName));
				return Fileop.readTextFile(generatedFile);
			}
		};
	}

	private FlywheelBuilder flywheelBuilder;

	public FlywheelBuilder getFlywheelBuilder() {
		if (flywheel != null) {
			throw new RuntimeException("You can't configure the test after running it!");
		}
		if (flywheelBuilder == null) {
			flywheelBuilder = new FlywheelBuilder().targetDirectory(TEST_DIRECTORY)
					.file(new StandardPackagePath(getPackageName()).getFile(fromFileRezPath));
		}
		return flywheelBuilder;
	}

	private String expected;

	private String getExpected() {
		if (expected == null) {
			File expectedFile = new StandardPackagePath(getPackageName()).getFile(expectedFileRezPath);
			expected = Fileop.readTextFile(expectedFile);
		}
		return expected;
	}

	private Flywheel flywheel;
	public Flywheel getFlywheel() {
		if (flywheel == null) {
			flywheel = getFlywheelBuilder().construct();
		}
		return flywheel;
	}
}
