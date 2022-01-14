package com.panopset.opmysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;
import com.panopset.compat.Logop;

public class PanDatabase {

	Driver driver;

	String url;

	public String getUrl() {
		if (url == null) {
			url = String.format("jdbc:mysql://%s", System.getenv().get("PDBURL"));
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

	public void exec(String sql) {
		if (getConn() == null) {
			Logop.error("null connection, can not update.");
			return;
		}
		doExec(sql);
	}
	
	private void doExec(String sql) {
		try {
			Statement stmt = getConn().createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			Logop.error(e);
		}
	}
	
	private Connection conn;

	Connection getConn() {
		if (conn == null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(getUrl(), getUser(), getPwd());
			} catch (Exception ex) {
				Logop.handle(ex);
				return null;
			}
		}
		return conn;
	}
}
