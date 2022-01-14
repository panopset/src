package com.panopset.ophoneypot;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HoneypotTest {
	AttackRecorderMysql arm;
	
	@BeforeEach
	void init() {
		arm = new AttackRecorderMysql();
	}

	@Test
	void testIO() {
		arm.record(new Attack("5.5.5.5", "6,6,6,6", "JUnit Test", "key", "value"));
		DdsAttackStats stats = arm.read();
		assertTrue(stats.getCount() > 0);
	}

}
