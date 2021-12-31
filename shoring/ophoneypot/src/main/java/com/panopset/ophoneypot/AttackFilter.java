package com.panopset.ophoneypot;

public class AttackFilter {

	private final AttackRecorder attackRecorder;

	public AttackFilter(AttackRecorder attackRecorder) {
		this.attackRecorder = attackRecorder;
	}

	public void record(String callingIP, String category, String key, String value) {
		attackRecorder.record(new DdsAttackRecord(callingIP, category, key, value));
	}

}
