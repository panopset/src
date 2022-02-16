package com.panopset.tests.transformer;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;

import com.panopset.compat.Fileop;
import com.panopset.gp.GlobalReplaceProcessor;

public class GlobalReplaceTransformerTest extends SameFileTest {
	private final String searchStr;
	private final String replacementStr;
	private final String priorLineMustContain;

	public GlobalReplaceTransformerTest(String searchStr, String replacmentStr, String priorLineMustContain,
			String packageName, String refreshFile, String fromToFile, String expectedFile) {
		super(packageName, refreshFile, fromToFile, expectedFile);
		this.searchStr = searchStr;
		this.replacementStr = replacmentStr;
		this.priorLineMustContain = priorLineMustContain;
	}

	public GlobalReplaceTransformerTest(String searchStr, String replacmentStr, String packageName, String refreshFile,
			String fromToFile, String expectedFile) {
		this(searchStr, replacmentStr, "", packageName, refreshFile, fromToFile, expectedFile);
	}

	protected String xform() {
		try {
			getGlobalReplaceProcessor().process();
			return Fileop.readTextFile(tempFile);
		} catch (IOException e) {
			Assertions.fail("performTransformation failure.", e);
		}
		return "";
	}

	private GlobalReplaceProcessor grp;

	protected GlobalReplaceProcessor getGlobalReplaceProcessor() {
		if (grp == null) {
			grp = new GlobalReplaceProcessor(tempFile, searchStr, replacementStr, "txt", "", false);
			grp.setPriorLineMustContain(priorLineMustContain);
		}
		return grp;
	}
}
