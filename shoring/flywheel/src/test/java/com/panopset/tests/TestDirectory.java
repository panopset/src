package com.panopset.tests;

import java.io.File;

import com.panopset.compat.Fileop;

public interface TestDirectory {

	File TEST_DIRECTORY = new File(
			Fileop.combinePaths(Fileop.getCanonicalPath(Fileop.TEMP_DIRECTORY), "/com/panopset/test"));

}
