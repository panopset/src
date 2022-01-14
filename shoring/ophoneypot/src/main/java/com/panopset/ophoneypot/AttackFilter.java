package com.panopset.ophoneypot;

public class AttackFilter {

	private final AttackRecorder attackRecorder;

	public AttackFilter(AttackRecorder attackRecorder) {
		this.attackRecorder = attackRecorder;
	}

	public void record(String callingIP, String targetIP, String category, String key, String value) {
		attackRecorder.record(new Attack(callingIP, targetIP, category, key, value));
	}

}
