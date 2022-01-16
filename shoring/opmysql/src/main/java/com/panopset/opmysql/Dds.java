package com.panopset.opmysql;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.panopset.compat.Logop;
import com.panopset.flywheel.FlywheelBuilder;

public abstract class Dds {
	
	protected final PanDatabase pdb = new PanDatabase();

	protected abstract void populateFieldDefinitionList(List<FieldDefinition<?>> list);

	protected abstract String getCatalogName();
	protected abstract String getTableName();

	private static final String TABLE_NAME = "TABLE_NAME";
	private static final String FIELD_DDS = "FIELD_DDS";

	private static final String CREATE_TABLE = 
			"create table if not exists ${TABLE_NAME} ( " +
			" id int unsigned auto_increment not null, " +
			"${FIELD_DDS} " +
			" primary key (id) " +
			") ";
			

	private static final String DROP_TABLE = 
			"drop table if exists ${TABLE_NAME}";

	private String createTableStatement;

	public String getCreateTableStatement() {
		if (createTableStatement == null) {
			createTableStatement = new FlywheelBuilder().input(CREATE_TABLE).map(TABLE_NAME, getTableName())
					.map(FIELD_DDS, assembleFieldDefinitions()).construct().exec();
		}
		return createTableStatement;
	}
	
	private String valuesMap;
	
	private String getValuesMap() {
		if (valuesMap == null) {
			StringWriter sw = new StringWriter();
			for (int i = 0; i < getFieldDefinitions().size(); i++) {
				sw.append("?");
				if (i < getFieldDefinitions().size() - 1) {
					sw.append(", ");
				}
			}
			valuesMap = sw.toString();
		}
		return valuesMap;
	}
	
	private String namesMap;
	
	private String getNamesMap() {
		if (namesMap == null) {
			StringWriter sw = new StringWriter();
			int i = 0;
			for (FieldDefinition<?> fd : getFieldDefinitions()) {
				sw.append(fd.getName());
				if (i++ < getFieldDefinitions().size() - 1) {
					sw.append(", ");
				}
			}
			namesMap = sw.toString();
		}
		return namesMap;
	}
	
	public void write() {
		if (getConn() == null) {
			Logop.error("null connection, can not write.");
			return;
		}

		checkDatabase();
		
		try {
			String query = String.format(" insert into %s (%s)"
					+ " values (%s)", getTableName(), getNamesMap(), getValuesMap());
			PreparedStatement preparedStmt = getPanDatabase().getConn().prepareStatement(query);
			int i = 0;
			for (FieldDefinition<?> fd : getFieldDefinitions()) {
				fd.prepareWrite(preparedStmt, ++i);
			}
			preparedStmt.execute();
		} catch (Exception ex) {
			Logop.handle(ex);
		}
	}

	private List<FieldDefinition<?>> fieldDefinitions;

	private List<FieldDefinition<?>> getFieldDefinitions() {
		if (fieldDefinitions == null) {
			fieldDefinitions = new ArrayList<FieldDefinition<?>>();
			populateFieldDefinitionList(fieldDefinitions);
		}
		return fieldDefinitions;
	}

	private String assembleFieldDefinitions() {
		StringWriter sw = new StringWriter();
		for (FieldDefinition<?> fd : getFieldDefinitions()) {
			sw.append(fd.getDeclaration());
			sw.append(",");
		}
		return sw.toString();
	}
	
	public void drop() {
		exec(getDropTableStatement());
	}

	private void exec(String sql) {
		checkDatabase();
		getPanDatabase().exec(sql);
	}

	private String dropTableStatement;

	public String getDropTableStatement() {
		if (dropTableStatement == null) {
			dropTableStatement = new FlywheelBuilder().input(DROP_TABLE).map(TABLE_NAME, getTableName()).construct()
					.exec();
		}
		return dropTableStatement;
	}
	

	private boolean isChecked = false;

	private void checkDatabase() {
		if (isChecked) {
			return;
		}
		isChecked = true;
		exec(String.format("create database if not exists %s", getCatalogName()));
		try {
			getPanDatabase().getConn().setCatalog(getCatalogName());
		} catch (Exception ex) {
			Logop.handle(ex);
		}
		exec(getCreateTableStatement());
	}
	
	private Connection getConn() {
		return getPanDatabase().getConn();
	}
	
	private PanDatabase getPanDatabase() {
		return pdb;
	}
}
