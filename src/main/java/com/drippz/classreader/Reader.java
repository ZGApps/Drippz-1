package com.drippz.classreader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.drippz.annotations.Column;
import com.drippz.annotations.Id;
import com.drippz.util.ColumnField;
import com.drippz.util.ForeignKeyField;
import com.drippz.util.MetaModel;

public class Reader {

	private static Logger logger = Logger.getLogger(Reader.class);

	public static LinkedHashMap<String, String> getGenericColumnNamesAndTypes(MetaModel<?> m) {
		LinkedHashMap<String, String> results = new LinkedHashMap<String, String>();
		List<String> columns = new ArrayList<String>();
		List<String> types = new ArrayList<String>();
		List<ColumnField> cFields = m.getColumns();
		for (ColumnField c : cFields) {
			columns.add(c.getColumnName());
			types.add(c.getType());
		}
		for (int i = 0; i < columns.size(); i++) {
			results.put(columns.get(i), types.get(i));
		}
		return results;
	}

	public static String getPKColumnName(MetaModel<?> m) {
		String name = m.getPrimaryKey().getColumnName();
		if (name == null) {
			name = m.getPrimaryKey().getName();
		}
		return name;
	}

	public static LinkedList<String> getFKnames(MetaModel<?> m) {

		LinkedList<ForeignKeyField> fKeyFields = m.getForeignKeys();
		LinkedList<String> columns = new LinkedList<String>();

		for (ForeignKeyField f : fKeyFields) {
			columns.add(f.getColumnName());

		}
		return columns;
	}
	public static LinkedList<String> getFKTargetTables(MetaModel<?> m) {

		LinkedList<ForeignKeyField> fKeyFields = m.getForeignKeys();
		LinkedList<String> Tables = new LinkedList<String>();

		for (ForeignKeyField f : fKeyFields) {
			Tables.add(f.getTargetEntity());

		}
		return Tables;
	}
	public static LinkedList<String> getFKTargetColumns(MetaModel<?> m) {

		LinkedList<ForeignKeyField> fKeyFields = m.getForeignKeys();
		LinkedList<String> columns = new LinkedList<String>();

		for (ForeignKeyField f : fKeyFields) {
			columns.add(f.getTargetColumn());

		}
		return columns;
	}

	public static String getGenericColumnNames(MetaModel<?> m) {
		List<ColumnField> cFields = m.getColumns();
		StringBuilder result = new StringBuilder();
		for (ColumnField f : cFields) {
			if(result.length() == 0) {
				result.append(f.getName());
			} else {
				result.append(", ");
				result.append(f.getName());
			}
			
		}
		return result.toString();

	}

	public static String getFKColumnName(MetaModel<?> m) {
		LinkedList<ForeignKeyField> fkFields = m.getForeignKeys();
		StringBuilder result = new StringBuilder();
		for (ForeignKeyField f : fkFields) {
			if(result.length() == 0) {
				result.append(f.getName());
			} else {
				result.append(", ");
				result.append(f.getName());
			}
			
		}
		return result.toString();
	}

	public static String getPKVal(String pkFieldName, Object objOfClass) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Class<?> classOfObj = objOfClass.getClass();
		Field f = classOfObj.getDeclaredField(pkFieldName);
		StringBuilder fieldContents = new StringBuilder(); 
		f.setAccessible(true);
		fieldContents.append(f.get(objOfClass));
		return fieldContents.toString();
		}

	public static LinkedHashMap<String, String> getColumnsAndValues(Object objOfClass) throws IllegalArgumentException, IllegalAccessException {
		LinkedHashMap<String, String> results = new LinkedHashMap<String, String>();
		Class<?> classOfObj = objOfClass.getClass();
		MetaModel<?> meta = MetaModel.of(objOfClass.getClass());
		

		for (Field f : classOfObj.getDeclaredFields()) {
			
			Column col = f.getAnnotation(Column.class);
			if(col != null) {
				f.setAccessible(true);
				StringBuilder fieldContents = new StringBuilder(); 
				fieldContents.append(f.get(objOfClass));
				String columnName = f.getAnnotation(Column.class).columnName();
				results.put(columnName, fieldContents.toString());
			}
		}
		return results;
	}

}
