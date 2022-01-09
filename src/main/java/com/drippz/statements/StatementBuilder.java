package com.drippz.statements;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import com.drippz.classreader.Reader;
import com.drippz.util.ForeignKeyField;
import com.drippz.util.MetaModel;

public class StatementBuilder {

	private static Logger log = Logger.getLogger(StatementBuilder.class);
			/**
	 * inpput into create Statment must be:
	 * 
	 * @param opperation = String for the operation (Create, Find, Insert, Update,
	 *                   Delete)[case insensitive]
	 * @param targets    = A List containing the desired fields from the target
	 * @param options    = A HashMap with the key as the field to be checked
	 *                   followed by a space and the Logical operator <>= etc.
	 * @param meta       = A MetaModel of a Class that represents the target table
	 */
	public static String createGetStatement(List<String> targets, LinkedHashMap<String, String> options,
			MetaModel<?> meta) {
		StringBuilder stmt = new StringBuilder();

		stmt.append("SELECT ");
		stmt.append(createTargets(targets));
		stmt.append(" FROM ");
		stmt.append(meta.getEntityName());
		if (!options.isEmpty()) {
			stmt.append(" WHERE ");
			stmt.append(createOptions(options));

		}
		stmt.append(";");
		log.info("Created Get Statement: " + stmt.toString());
		return stmt.toString();

	}

	public static String createUpdateStatement(Object objOfClass, List<String> fieldsToUpdate)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		StringBuilder stmt = new StringBuilder();
		MetaModel<?> meta = MetaModel.of(objOfClass.getClass());
		// TODO fix Update Statement Builder
		stmt.append("UPDATE ");
		stmt.append(meta.getEntityName() + " SET ");
		LinkedHashMap<String, String> changes = Reader.getColumnsAndValues(objOfClass);
		LinkedHashMap<String, String> columns = Reader.getGenericColumnNamesAndTypes(meta); //TODO second string is not returning type correctly?
		StringBuilder subStmt = new StringBuilder();
		for (String s : changes.keySet()) {
			if (!fieldsToUpdate.contains(s)) {
				continue;
			}
			String content = changes.get(s);
			if(content.isEmpty() || changes.get(s) == " " || content == "0") {
				System.out.println("content is empty, a space, or 0");
				break;
			}
			if (!(subStmt.length() == 0)) {
				subStmt.append(", ");
			}
			subStmt.append(s);
			subStmt.append(" = ");
			String type = columns.get(s);
			if (type.contains("String") || type.contains("char")) {
				subStmt.append("'");
			}
			subStmt.append(changes.get(s));
			if (type.contains("String") || type.contains("char")) {
				subStmt.append("'");
			}
		}
		stmt.append(subStmt);
		stmt.append(" WHERE ");
		String pkColumnName = Reader.getPKColumnName(meta);
		stmt.append(pkColumnName);
		stmt.append(" = ");
		stmt.append(Reader.getPKVal(pkColumnName, objOfClass));
		stmt.append(";");
		log.info("Created Update Statement: " + stmt.toString());
		return stmt.toString();
	}

	public static String createInsertStatement(Object objOfClass) throws IllegalArgumentException, IllegalAccessException {
		StringBuilder stmt = new StringBuilder();
		MetaModel<?> meta = MetaModel.of(objOfClass.getClass());
		LinkedHashMap<String, String> columnsAndValues = Reader.getColumnsAndValues(objOfClass);
		StringBuilder subStringColNames = new StringBuilder();
		StringBuilder subStringValues = new StringBuilder();
		for (String s : columnsAndValues.keySet()) {
			LinkedHashMap<String, String> types = Reader.getGenericColumnNamesAndTypes(meta);
			String type = types.get(s);
			if(!(subStringColNames.length() == 0)) {
				subStringColNames.append(", ");
			}
			if(!(subStringValues.length() == 0)) {
				subStringValues.append(", ");
			}
			subStringColNames.append(s);
			if (type.contains("String") || type.contains("char")) {
				subStringValues.append("'");
			}
			subStringValues.append(columnsAndValues.get(s));
			if (type.contains("String") || type.contains("char")) {
				subStringValues.append("'");
			}

		}
		stmt.append("INSERT INTO ");
		stmt.append(meta.getEntityName() + " (");

		stmt.append(subStringColNames);
		String fkNames = Reader.getFKColumnName(meta);
		if (fkNames != null) {
			stmt.append(fkNames);	
		}
		for (String s : columnsAndValues.keySet()) {
			
		}
		stmt.append(") VALUES (");
		stmt.append(subStringValues);		
		stmt.append(") RETURNING ");
		String pkName = Reader.getPKColumnName(meta);
		stmt.append(pkName);
		stmt.append(";");
		log.info("Created Insert Statement: " + stmt.toString());
		return stmt.toString();
	}

	public static String createCreateStatement(MetaModel<?> meta) {
		StringBuilder stmt = new StringBuilder();
	
		stmt.append("CREATE TABLE IF NOT EXISTS ");
		stmt.append(meta.getEntityName());
		stmt.append(" (");
		stmt.append(Reader.getPKColumnName(meta) + " SERIAL PRIMARY KEY, ");
		LinkedHashMap<String, String> columns = Reader.getGenericColumnNamesAndTypes(meta);
		for (String key : columns.keySet()) {
			stmt.append(key + " " + typeConvert(columns.get(key)) + ", ");
		}
		List<ForeignKeyField> fKeys = meta.getForeignKeys();
		if (!fKeys.isEmpty()) {
			LinkedList<String> fkNames = Reader.getFKnames(meta);
			LinkedList<String> fkTables = Reader.getFKTargetTables(meta);
			LinkedList<String> fkColumns = Reader.getFKTargetColumns(meta);
			for (int i = 0; i < fKeys.size(); i++) {
				stmt.append("FOREIGN KEY ");
				stmt.append(fkNames.get(i) + " ");
				stmt.append(" REFERENCES ");
				stmt.append(fkTables.get(i) + " ");
				stmt.append(fkColumns.get(i) + ", ");
			}
		}
		stmt.delete(stmt.length() - 2, stmt.length());
		stmt.append(");");
		log.info("Created Table Creation Statement: " + stmt.toString());
		
		return stmt.toString();

	}

	// TODO createDeleteStatement()

	public static String createDropTableStatement(MetaModel<?> meta) {

		StringBuilder stmt = new StringBuilder();

		stmt.append("DROP TABLE IF EXISTS ");
		stmt.append(meta.getEntityName());
		stmt.append(" CASCADE;");
		log.info("Created Drop Statement: " + stmt.toString());
		return stmt.toString();

	}
	
	public static String createBegin() {
		return "BEGIN;";
	}
	
	public static String createCommit() {
		return "COMMIT;";
	}
	
	public static String createSpace() {
		return " ";
	}


	private static Object createOptions(LinkedHashMap<String, String> input) {

		StringBuilder options = new StringBuilder();

		for (String key : input.keySet()) {
			if (options.length() == 0) {
				options.append(key);
				options.append(" = ");
				options.append(input.get(key));
			} else {
				options.append(", ");
				options.append(key);
				options.append(" = ");
				options.append(input.get(key));
			}
		}

		return options.toString();
	}

	private static String createTargets(List<String> input) {

		StringBuilder targets = new StringBuilder();

		for (String i : input) {
			if (targets.length() == 0) {
				targets.append(i);
			} else {
				targets.append(", ");
				targets.append(i);
			}

		}

		return targets.toString();
	}
	
	


	private static String typeConvert(String in) {

		switch (in) {
		case "class java.lang.String":
			return "VARCHAR";
		case "char":
			return "CHARACTER [1]";
		case "int":
		case "short":
		case "byte":
			return "INTEGER";
		case "long":
			return "BIGINT";
		case "double":
		case "float":
			return "FLOAT8";
		case "boolean":
			return "BOOLEAN";
		default:
			log.fatal("Type not handled yet: " + in);
			return "NEW TYPE NEEDS ADDING: " + in;}

	}

}
