package com.panopset.ophoneypot;

public class AttackRecorderMysql implements AttackRecorder {
	
	

	@Override
	public void record(Attack attack) {
//		record(ddsAttackRecord.getTimestamp(), ddsAttackRecord.getCallingIP(), ddsAttackRecord.getCategory(),
//				ddsAttackRecord.getKey(), ddsAttackRecord.getValue());
		attackDatabase.write(attack);
		//hd.write(ddsAttackRecord);
	}


//	private void record(Long timestamp, String callingIP, String category, String key, String value) {
//		PanDatabase hd = new PanDatabase(new HoneypotAccess());
//		hd.write(timestamp, callingIP, category, key, value);
//	}


	public DdsAttackStats read() {
		DdsAttackStats rtn = new DdsAttackStats();
		return rtn;
	}
	
	
	private final AttackDatabase attackDatabase = new AttackDatabase();
	
	public AttackDatabase getAttackDatabase() {
		return attackDatabase;
	}

}
