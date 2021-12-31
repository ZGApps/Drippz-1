package com.drippz.statements;

import java.util.HashMap;
import java.util.List;

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
	public String createStatement(String opperation, List<String> targets, HashMap<String, String> options,
			MetaModel<?> meta) {
		StringBuilder stmt = new StringBuilder();

		switch (opperation.toLowerCase()) {
		case "create":

			break;
		case "get":
			stmt.append("SELECT ");
			stmt.append(createTargets(targets));
			stmt.append(meta.getClassName() + " ");
			if (!options.isEmpty()) {
				stmt.append("WHERE ");
				stmt.append(createOptions(options));
			}
			break;
		case "insert":

			break;
		case "update":

			break;
		case "delete":

			break;

		}

		return stmt.toString();

	}

	private Object createOptions(HashMap<String, String> input) {


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

	private String createTargets(List<String> input) {

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
