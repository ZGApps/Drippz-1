package com.drippz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.drippz.statements.StatementBuilder;
import com.drippz.util.Configuration;
import com.drippz.util.MetaModel;

public class Dripp {

	private static Logger log = Logger.getLogger(Dripp.class);
	Configuration cfg = new Configuration();
	Connection conn;

	HashMap<String, ResultSet> drippCache = new HashMap<String, ResultSet>();

	StringBuilder sql;

	public Dripp() throws SQLException {
		this.conn = this.cfg.getConnection();
	}

	public void resetDripp() throws SQLException {
		drippCache = new HashMap<String, ResultSet>();
		cfg = new Configuration();
		newConnection();
	}

	public void beginTx() {
		sql = new StringBuilder();
		addToSQL(StatementBuilder.createBegin());

	}

	public void commit() {
		addToSQL(StatementBuilder.createSpace());
		addToSQL(StatementBuilder.createCommit());
	}

	public ResultSet runTxQuery() throws SQLException {
		log.info("Full send SQL: " + sql.toString());
		if (sql.toString().contains("SELECT")) {
			for (String key : drippCache.keySet()) {
				if (key.equals(sql.toString())) {
					log.info("Returned result from Cache");
					System.out.println("returning from Cache where Key = " + sql.toString());
					return drippCache.get(sql.toString());
				}
			}
		}

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql.toString());
//		if (sql.toString().contains("SELECT")) {
//			for (String key : drippCache.keySet()) {
//				if (key.equals(sql.toString())) {
//					rs.next();
//					store(sql.toString(), rs);
//					System.out.println("Stored in cache where Key = " + sql.toString());
//				}
//			}
//		}
		sql = new StringBuilder();
		return rs;
	}

	public void runTxUpdate() throws SQLException {

		log.info("Full send SQL: " + sql.toString());
		Statement stmt = conn.createStatement();

		stmt.executeUpdate(sql.toString());
		sql = new StringBuilder();
	}

	public void create(Class<?> annotatedClass) {

		addToSQL(StatementBuilder.createSpace());
		String dropStatement = StatementBuilder.createDropTableStatement(MetaModel.of(annotatedClass));
		addToSQL(dropStatement);

		addToSQL(StatementBuilder.createSpace());
		String createStatement = StatementBuilder.createCreateStatement(MetaModel.of(annotatedClass));
		addToSQL(createStatement);
	}

	public void get(Class<?> annotatedClass, List<String> targetFields, LinkedHashMap<String, String> constraints)
			throws SQLException {

		setSQL(StatementBuilder.createGetStatement(targetFields, constraints, MetaModel.of(annotatedClass)));

	}

	public void insert(Object objectOfAnnotatedClass) throws IllegalArgumentException, IllegalAccessException {
		sql = new StringBuilder();
		addToSQL(StatementBuilder.createInsertStatement(objectOfAnnotatedClass));
	}

	public void modify(Object objectOfAnnotatedClass, List<String> fieldsToUpdate)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		sql = new StringBuilder();
		addToSQL(StatementBuilder.createUpdateStatement(objectOfAnnotatedClass, fieldsToUpdate));
	}

	private void newConnection() throws SQLException {
		closeConnection();
		cfg.getConnection();
	}

	public Connection getConnection() {
		return conn;
	}

	public void closeConnection() {
		cfg.closeConnection(conn);
	}

	private String setSQL(String in) {
		sql = new StringBuilder();
		sql.append(in);
		return sql.toString();
	}

	private String addToSQL(String in) {
		sql.append(in);
		return sql.toString();
	}

	public String getSQL() {
		return sql.toString();
	}

	private void store(String sql, ResultSet rs) {
		drippCache.put(sql, rs);
	}

	private ResultSet retrieve(String sql) {
		if (checkCache(sql)) {
			return drippCache.get(sql);
		} else {
			return null;
		}
	}

	private boolean checkCache(String sql) {
		if (drippCache.containsKey(sql)) {
			return true;
		} else {
			return false;

		}
	}

}
