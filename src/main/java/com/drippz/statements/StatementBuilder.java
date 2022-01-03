package com.drippz.statements;

import java.util.HashMap;
import java.util.List;

import com.drippz.classreader.Reader;
import com.drippz.util.MetaModel;

public class StatementBuilder {

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
	public static String createGetOrSelectOrUpdateOrDeleteStatement(String opperation, List<String> targets, HashMap<String, String> options,
			MetaModel<?> meta) {
		StringBuilder stmt = new StringBuilder();

		switch (opperation.toLowerCase()) {
		case "get":
			stmt.append("SELECT ");
			stmt.append(createTargets(targets));
			stmt.append(" FROM ");
			stmt.append(meta.getEntityName() + " ");
			if (!options.isEmpty()) {
				stmt.append("WHERE ");
				stmt.append(createOptions(options));
			}
			//TODO maybe limits?
			break;
		case "insert":
			//TODO insert statement
			stmt.append("INSERT INTO ");
			stmt.append(meta.getClassName() + " ");

			break;
		case "update":
			//TODO update statement
			break;
		case "delete":
			//TODO Delete Statement
			break;

		}

		return stmt.toString();

	}
	
	public static String createCreateStatement(MetaModel<?> meta) {
		
		StringBuilder stmt = new StringBuilder();
		
		stmt.append("CREATE Table [IF NOT EXISTS] ");
		stmt.append(meta.getEntityName() + " (");
		stmt.append(Reader.getPKColumnName(meta) + " PRIMARY KEY, ");
		HashMap<String, String> columns = Reader.getGenericColumnNamesAndTypes(meta);
		for (String key : columns.keySet()) {
			stmt.append(key + " " + columns.get(key) + ", ");
		}
		stmt.delete(stmt.length()-1, stmt.length());
		
		stmt.append(");");
		return stmt.toString();
		
	}

	private static Object createOptions(HashMap<String, String> input) {


		StringBuilder options = new StringBuilder();

		for (String key : input.keySet()) {
			if (options.isEmpty()) {
				options.append(key + " ?");
			} else {
				options.append(", ");
				options.append(key + " ?");
			}
		}

		return options.toString();
	}

	private static String createTargets(List<String> input) {

		StringBuilder targets = new StringBuilder();

		for (String i : input) {
			if (targets.isEmpty()) {
				targets.append(i);
			} else {
				targets.append(", ");
				targets.append(i);
			}

		}
		targets.append(" ");

		return targets.toString();
	}

}
