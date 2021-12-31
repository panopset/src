package com.panopset.ophoneypot;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.panopset.opmysql.HoneypotDatabase;

public class HoneypotTest {
	HoneypotDatabase hpm;
	AttackRecorderMysql arm;
	
	@BeforeEach
	void init() {
		hpm = new HoneypotDatabase();
		arm = new AttackRecorderMysql();
	}
	
	@Test
	void testEnv() {
		checkPopulated(hpm.getUrl());
		checkPopulated(hpm.getUser());
		checkPopulated(hpm.getPwd());
				
	}

	@Test
	void testIO() {
		arm.record(new DdsAttackRecord("5.5.5.5", "JUnit Test", "key", "value"));
		DdsAttackStats stats = arm.read();
		assertTrue(stats.getCount() > 0);
	}

	private void checkPopulated(String value) {
		assertNotNull(value);
		assertTrue(value.length() > 0);
	}
}
