package com.panopset.ophoneypot;

public class AttackRecorderMysql implements AttackRecorder {
	
	
	

	@Override
	public void record(DdsAttackRecord ddsAttackRecord) {
		record(ddsAttackRecord.getTimestamp(), ddsAttackRecord.getCallingIP(), ddsAttackRecord.getCategory(),
				ddsAttackRecord.getKey(), ddsAttackRecord.getValue());
	}


	private void record(Long timestamp, String callingIP, String category, String key, String value) {

	}


	public DdsAttackStats read() {
		DdsAttackStats rtn = new DdsAttackStats();
		return rtn;
	}

}
