package com.panopset.ophoneypot;

import java.util.List;

import com.panopset.opmysql.Dds;
import com.panopset.opmysql.FieldDefinition;
import com.panopset.opmysql.FieldDefinitionString;
import com.panopset.opmysql.FieldDefinitionTimestamp;

public class AttackDatabase extends Dds {

	public void write(Attack attack) {
		ts.setValue(attack.getTimestamp());
		callingIP.setValue(attack.getCallingIP());
		targetIP.setValue(attack.getTargetIP());
		category.setValue(attack.getCategory());
		attackKey.setValue(attack.getKey());
		attackValue.setValue(attack.getValue());
		write();
	}

	@Override
	protected void populateFieldDefinitionList(List<FieldDefinition<?>> list) {
		list.add(ts);
		list.add(callingIP);
		list.add(targetIP);
		list.add(category);
		list.add(attackKey);
		list.add(attackValue);
	}

	private final FieldDefinitionTimestamp ts = new FieldDefinitionTimestamp("date"); 
	private final FieldDefinitionString callingIP = new FieldDefinitionString("calling_ip"); 
	private final FieldDefinitionString targetIP = new FieldDefinitionString("target_ip"); 
	private final FieldDefinitionString category = new FieldDefinitionString("category"); 
	private final FieldDefinitionString attackKey = new FieldDefinitionString("attack_key"); 
	private final FieldDefinitionString attackValue = new FieldDefinitionString("attack_value"); 

	@Override
	protected String getCatalogName() {
		return "attack";
	}

	@Override
	protected String getTableName() {
		return "attacks";
	}
}