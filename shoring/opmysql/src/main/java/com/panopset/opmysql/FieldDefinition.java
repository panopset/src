package com.panopset.opmysql;

import java.io.StringWriter;
import java.sql.PreparedStatement;

public abstract class FieldDefinition<T> {
	
	public FieldDefinition(String name, String specs) {
		setName(name);
		setSpecs(specs);
	}
	
	public String getSpecs() {
		return specs;
	}

	public void setSpecs(String specs) {
		this.specs = specs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String specs;
	public String name;
	
	public String getDeclaration() {
		StringWriter sw = new StringWriter();
		sw.append("\n ");
		sw.append(getName());
		sw.append(" ");
		sw.append(getSpecs());
		return sw.toString();
	}
	
	private T value;
	
	public T getValue() {
		return value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}

	protected abstract void prepareWrite(PreparedStatement preparedStmt, int i);
}
