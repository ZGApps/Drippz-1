package com.drippz.util;

import java.lang.reflect.Field;

import com.drippz.annotations.Column;

public class ForeignKeyField {


	private Field field;
	
	public ForeignKeyField(Field field) {
		
		
		if (field.getAnnotation(Column.class) == null) {
			throw new IllegalStateException("Column Creation failed. no Column annotation found in field " + getName() +".");
		}
		
		this.field = field;
		
	}
	
	public String getName() {
		return field.getName();
	}

	public Class<?> getType() {
		return field.getType();
	}
	
	public String getColumnName() {
		return field.getAnnotation(Column.class).columnName();
	}
}
