package com.panopset.ophoneypot;

import java.util.Date;

public class DdsAttackRecord {
	private Long timestamp;
	private String callingIP;
	private String category;
	private String key;
	private String value;

	public DdsAttackRecord(String callingIP, String category, String key, String value) {
		setTimestamp(new Date().getTime());
		setCallingIP(callingIP);
		setCategory(category);
		setKey(key);
		setValue(value);
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getCallingIP() {
		if (callingIP == null) {
			callingIP = "";
		}
		return callingIP;
	}

	public void setCallingIP(String callingIP) {
		this.callingIP = callingIP;
	}

	public String getCategory() {
		if (category == null) {
			category = "";
		}
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getKey() {
		if (key == null) {
			key = "";
		}
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		if (value == null) {
			value = "";
		}
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
