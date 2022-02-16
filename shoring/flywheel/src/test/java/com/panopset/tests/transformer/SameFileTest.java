package com.panopset.tests.transformer;

import java.io.File;

import com.panopset.compat.Fileop;

public abstract class SameFileTest extends StandardTransformerTest {
	
	private final String expectedFileRezPath;
	protected final File refreshFile;
	protected final File tempFile;
	protected final File expectedFile;
	
	protected abstract String xform();
	
	public SameFileTest(String packageName, String refreshFileRezPath, String tempFile, String expectedFileRezPath) {
		super(packageName);
		this.expectedFileRezPath = expectedFileRezPath;
		this.refreshFile = SameFileTest.this.getPackagePath(refreshFileRezPath);
		this.tempFile = new File(Fileop.combinePaths(TEST_DIRECTORY, tempFile));
		this.expectedFile = SameFileTest.this.getPackagePath(expectedFileRezPath);
	}
	
	@Override
	protected ResultsDataSupplier createResultsDataSupplier() {
      return new ResultsDataSupplier() {
			
			@Override
			public String getExpectedResults() {
				return SameFileTest.this.readTextFromStandardResource(expectedFileRezPath);
			}
			
			@Override
			public String getActualResults() {
				Fileop.delete(tempFile);
				Fileop.copyFile(refreshFile, tempFile);
				return xform();
			}
		};
	}
	
//	new ResultsDataSupplier() {
//		
//		@Override
//		public String getExpectedResults() {
//			return readTextFromStandardResourxce(expectedFileRezPath);
//		}
//		
//		@Override
//		public String getActualResults() {
//			Fileop.delete(getToFile());
//			Fileop.copyFile(getRefreshFile(), getFromFile());
//			return null;
//		}
//		
//		private File refreshFile;
//		
//		File getRefreshFile() {
//			if (refreshFile == null) {
//				refreshFile = new File(Fileop.combinePaths(packagePath, refreshFileRezPath));
//			}
//			return refreshFile;
//		}
//
//	}
}
