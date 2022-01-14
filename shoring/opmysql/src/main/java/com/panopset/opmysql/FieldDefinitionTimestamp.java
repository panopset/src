package com.panopset.opmysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.panopset.compat.Logop;

public class FieldDefinitionTimestamp extends FieldDefinition<Long> {

	public FieldDefinitionTimestamp(String name) {
		super(name, "timestamp default now()");
	}

	@Override
	protected void prepareWrite(PreparedStatement preparedStmt, int i) {
		try {
			preparedStmt.setTimestamp(i, new Timestamp(getValue()));
		} catch (SQLException e) {
			Logop.error(e);
		}
	}

}
