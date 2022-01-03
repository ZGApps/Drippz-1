package com.drippz.classreader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.drippz.util.ColumnField;
import com.drippz.util.ForeignKeyField;
import com.drippz.util.MetaModel;

public class Reader {

	private static Logger logger = Logger.getLogger(Reader.class);

	public static HashMap<String, String> getGenericColumnNamesAndTypes(MetaModel<?> m) {
		HashMap<String, String> results = new HashMap<String, String>();
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
		if (name == "") {
			name = m.getPrimaryKey().getName();
		}
		return name;
	}

	public static HashMap<List<String>, HashMap<String, String>> getFKnamesAndTargets(MetaModel<?> m) {
		HashMap<List<String>, HashMap<String, String>> results = new HashMap<List<String>, HashMap<String, String>>();
		List<ForeignKeyField> fKeyFields = m.getForeignKeys();
		List<String> columns = new ArrayList<String>();
		HashMap<String, String> targets = new HashMap<String, String>();
		for (ForeignKeyField f : fKeyFields) {
			columns.add(f.getColumnName());
			targets.put(f.getTargetEntity(), f.getTargetColumn());
		}
		results.put(columns, targets);
		return results;
	}

}
