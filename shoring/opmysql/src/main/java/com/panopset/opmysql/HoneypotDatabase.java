package com.panopset.opmysql;

public class HoneypotDatabase {

	String url;

	public String getUrl() {
		if (url == null) {
			url = System.getenv().get("PDBURL");
		}
		return url;
	}


	String user;

	public String getUser() {
		if (user == null) {
			user = System.getenv().get("PDBUSR");
		}
		return user;
	}


	String pwd;

	public String getPwd() {
		if (pwd == null) {
			pwd = System.getenv().get("PDBPWD");
		}
		return pwd;
	}

}
