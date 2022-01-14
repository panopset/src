package com.panopset.ophoneypot;

public class DdsAttackStats {

	private static final String REPORT_SQL = """
			select distinct calling_ip, attack_key, attack_value, count(*) as total
			from attacks group by calling_ip, attack_key, attack_value
			order by total desc limit 20;
						""";

	private Long count;

	public Long getCount() {
		if (count == null) {
			count = 0L;
		}
		return count;
	}

}
