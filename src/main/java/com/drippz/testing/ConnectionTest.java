package com.drippz.testing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.drippz.Dripp;

public class ConnectionTest {

	public static void main(String[] args) {
		Dripp dripp = null;
		try {
			 dripp = new Dripp();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TestModel testObject = new TestModel("Agent", "Smith");
		TestModel otherTestObject = new TestModel("John", "West");
		List<String> targetFields = new ArrayList<String>();
		targetFields.add("first_name");
		targetFields.add("last_name");
		LinkedHashMap<String, String> constraints = new LinkedHashMap<String, String>();
		constraints.put("id", "2");
		
		
		try {
			createTableTest(TestModel.class, dripp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			insertToTableTest(testObject, dripp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			insertToTableTest(otherTestObject, dripp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			insertToTableTest(testObject, dripp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			displayEmployeeById(TestModel.class, targetFields, constraints, dripp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			displayEmployees(TestModel.class, dripp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testObject.setId(2);
		try {
			updateEmployee(testObject, targetFields, dripp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			displayEmployeeById(TestModel.class, targetFields, constraints, dripp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			displayEmployees(TestModel.class, dripp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void updateEmployee(TestModel testObject, List<String> targetFields, Dripp dripp) throws SQLException {
		
		try {
			dripp.modify(testObject, targetFields);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dripp.runTxUpdate();


		
	}

	private static void displayEmployeeById(Class<?> annotatedClass, List<String> targets, LinkedHashMap<String, String> constraints,
			Dripp dripp) throws SQLException {

		ResultSet rs = null;

		dripp.get(annotatedClass, targets, constraints);

		rs = dripp.runTxQuery();

		while (rs.next()) {

			System.out.println("PK " + constraints.get("id") + ": " + rs.getString(1) + " " + rs.getString(2));
			System.out.println();
		}

	}

	private static void displayEmployees(Class<?> annotatedClass, Dripp dripp) throws SQLException {

		ResultSet rs = null;
		List<String> all = new ArrayList<String>();
		all.add("*");
		LinkedHashMap<String, String> any = new LinkedHashMap<String, String>();
		dripp.get(annotatedClass, all, any);

		rs = dripp.runTxQuery();
		while (rs.next()) {

			System.out.println(rs.getInt(1) + ": " + rs.getString(2) + " " + rs.getString(3));
			System.out.println();

		}
	}

	private static void createTableTest(Class<?> annotatedClass, Dripp dripp) throws SQLException {

		dripp.beginTx();

		dripp.create(TestModel.class);

		dripp.commit();
		dripp.runTxUpdate();

	}

	private static void insertToTableTest(Object testobject, Dripp dripp) throws SQLException {
		
		ResultSet rs = null;
		
		try {
			 dripp.insert(testobject);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		rs = dripp.runTxQuery();
		while (rs.next()) {

			System.out.println("Returned PK: " + rs.getInt(1));
			System.out.println();

		}
		
	}

//	LinkedHashMap<String, String> columnsAndValues;
//	try {
//		columnsAndValues = Reader.getColumnsAndValues(testObject);
//	} catch (IllegalArgumentException | IllegalAccessException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	for(String k : columnsAndValues.keySet()) {
//		
//	}

}