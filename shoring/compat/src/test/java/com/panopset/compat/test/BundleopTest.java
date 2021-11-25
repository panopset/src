package com.panopset.compat.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.panopset.compat.Bundleop;

public class BundleopTest {

	@Test
	void test() {
		assertEquals("bar",
		new Bundleop("com/panopset/test").get("foo"));
	}

}
