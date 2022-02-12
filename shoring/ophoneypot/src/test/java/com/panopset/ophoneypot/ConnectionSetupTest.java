package com.panopset.ophoneypot;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.panopset.compat.Stringop;
import com.panopset.opmysql.PanDatabase;

public class ConnectionSetupTest {

	@Test
	void testEnv() {
		PanDatabase pdb = new PanDatabase();
//		assertTrue(Stringop.isPopulated(pdb.getUrl()));
//		assertTrue(Stringop.isPopulated(pdb.getUser()));
//		assertTrue(Stringop.isPopulated(pdb.getPwd()));
	}
}
