package com.panopset.opmysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.panopset.compat.Logop;

public class FieldDefinitionString extends FieldDefinition<String> {

	public static final int DEFAULT_FIELD_LENGTH = 32;

	public FieldDefinitionString(String name) {
		this(name, DEFAULT_FIELD_LENGTH);
	}

	public FieldDefinitionString(String name, int length) {
		super(name, String.format("varchar(%d) not null", length));
	}

	@Override
	protected void prepareWrite(PreparedStatement preparedStmt, int i) {
		try {
			preparedStmt.setString(i, getValue());
		} catch (SQLException e) {
			Logop.error(e);
		}
	}
}
