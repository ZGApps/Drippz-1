package com.drippz.util;

import java.lang.reflect.Field;

import com.drippz.annotations.JoinColumn;

public class ForeignKeyField {


	private Field field;
	
	public ForeignKeyField(Field field) {
		
		
		if (field.getAnnotation(JoinColumn.class) == null) {
			throw new IllegalStateException("Column Creation failed. no Column annotation found in field " + getName() +".");
		}
		
		this.field = field;
		
	}
	
	public String getName() {
		return field.getName();
	}

	public String getColumnName() {
		return field.getAnnotation(JoinColumn.class).columnName();
	}
	
	public String getTargetEntity() {
		return field.getAnnotation(JoinColumn.class).targetEntity();
	}
	public String getTargetColumn() {
		return field.getAnnotation(JoinColumn.class).targetColumn();
	}
}
