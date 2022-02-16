package com.panopset.tests.transformer;

import java.io.File;
import java.io.StringWriter;

import com.panopset.flywheel.Flywheel;
import com.panopset.flywheel.FlywheelBuilder;

public class FlywheelTemplateToTextTransformerTest extends StandardTransformerTest {
	
	private final String expected;
	private final String templateFileRezPath;

	public FlywheelTemplateToTextTransformerTest(String packageName, String templateFileRezPath, final String expected) {
		super(packageName);
		this.expected = expected;
		this.templateFileRezPath = templateFileRezPath;
	}

	@Override
	protected ResultsDataSupplier createResultsDataSupplier() {
		
		return new ResultsDataSupplier() {
			
			@Override
			public String getExpectedResults() {
				return expected;
			}
			
			@Override
			public String getActualResults() {
				StringWriter sw = new StringWriter();
				File template = new StandardPackagePath(getPackageName()).getFile(templateFileRezPath);
				Flywheel script = new FlywheelBuilder().withWriter(sw).file(template)
						.construct();
				script.exec();
				return sw.toString();
			}
		};
	}
}
