package com.panopset.opmysql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.panopset.compat.Logop;

public class FieldDefinitionDate extends FieldDefinition<Long> {

	public FieldDefinitionDate(String name) {
		super(name, "timestamp default now()");
	}

	public void setValue(Long value) {
		this.value = value;
	}
	
	public Long getValue() {
		return value;
	}
	
	private Long value = 0L;

	@Override
	protected void prepareWrite(PreparedStatement preparedStmt, int i) {
		try {
			preparedStmt.setDate(i, new Date(getValue()));
		} catch (SQLException e) {
			Logop.error(e);
		}
	}

}
