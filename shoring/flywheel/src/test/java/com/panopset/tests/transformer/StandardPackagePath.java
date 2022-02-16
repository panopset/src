package com.panopset.tests.transformer;

import java.io.File;

import com.panopset.compat.Fileop;

public class StandardPackagePath {
	private final String packageName;
	private String packagePath;

	public StandardPackagePath(String packageName) {
		this.packageName = packageName;
	}

	public String getRezStr(String fileName) {
		return Fileop.readTextFile(getFile(fileName));
	}

	public File getFile(String fileName) {
		return new File(String.format("%s/%s", getPackagePath(), fileName));
	}

	protected String getPackagePath() {
		if (packagePath == null) {
			packagePath = String.format("src/test/resources/%s", packageName.replace('.', '/'));
		}
		return packagePath;
	}
}
