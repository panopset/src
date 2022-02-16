package com.panopset.tests.transformer;

import java.io.File;

import com.panopset.compat.Fileop;

public abstract class StandardTransformerTest extends TransformerTest {

	private final String packageName;
//	private final String fromFileRezPath;
//	private final String toFileRezPath;

//	public StandardTransformerTest(String packageName, String fromFileRezPath, String toFileRezPath,
//			ResultsDataSupplier expectedResults) {
//		super(expectedResults);
//		this.packageName = packageName;
//		this.fromFileRezPath = fromFileRezPath;
//		this.toFileRezPath = toFileRezPath;
	
	//		//new File(		Fileop.combinePaths(new StandardPackagePath(packageName).getPackagePath(), expectedFileRezPath))

//	}
	

	public StandardTransformerTest(String packageName) {
		super();
		this.packageName = packageName;
//		this.fromFileRezPath = fromFileRezPath;
//		this.toFileRezPath = toFileRezPath;
	}

	public File getPackagePath(String fileName) {
		return new StandardPackagePath(packageName).getFile(fileName);
	}

	public String readTextFromStandardResource(String fileName) {
		return Fileop.readTextFile(getPackagePath(fileName));
	}

	public String getPackageName() {
		return packageName;
	}
//	private File fromFile;
//
//	@Override
//	File getFromFile() {
//		if (fromFile == null) {
//			fromFile = new File(Fileop.combinePaths(getPackagePath(), fromFileRezPath));
//		}
//		return fromFile;
//	}

//	private File toFile;
//
//	@Override
//	File getToFile() {
//		if (toFile == null) {
//			toFile = new File(Fileop.combinePaths(getPackagePath(), toFileRezPath));
//		}
//		return toFile;
//	}
}
