package com.drippz;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.drippz.annotations.Column;
import com.drippz.statements.StatementBuilder;
import com.drippz.testing.TestModel;
import com.drippz.util.MetaModel;

public class StatementBuilderTests {
	
	private TestModel testModel;

	private MetaModel<?> meta;
	
	List<String> targets;
	
	LinkedHashMap<String, String> options;
	
	
	@Before
	public void setup() {
		testModel = new TestModel("John", "Smith");
		meta = MetaModel.of(TestModel.class);
		targets = new LinkedList<String>();
		options = new LinkedHashMap<String, String>();
	}
	
	@After
	public void tearDown() {
		testModel = null;
		meta = null;
		targets = null;
		options = null;
	}
	
	@Test
	public void aTestcreateGetStatementWorks() {
		// String opperation, List<String> targets, HashMap<String, String> options, MetaModel<?> meta
		targets.add("*");
		String expectedResult = "SELECT * FROM employees";
		String givenResult = StatementBuilder.createGetStatement(targets, options, meta);
		
		assertEquals(expectedResult, givenResult);
	}
	
	@Test
	public void aTestCreateGetStatementFields() {
		targets.add("first_name");
		targets.add("last_name");
		String expectedResult = "SELECT first_name, last_name FROM employees";
		String givenResult = StatementBuilder.createGetStatement(targets, options, meta);
		
		assertEquals(expectedResult, givenResult);
	}
	
	@Test
	public void aTestCreateGetStatementOptions() {
		targets.add("*");
		options.put("id", "1");
		String expectedResult = "SELECT * FROM employees WHERE id = 1";
		String givenResult = StatementBuilder.createGetStatement(targets, options, meta);
		assertEquals(expectedResult, givenResult);
	}
	
	@Test
	public void aTestCreateGetStatementFieldsAndOptions() {
		targets.add("first_name");
		targets.add("last_name");
		options.put("first_name", "John");
		options.put("last_name", "Smith");
		String expectedResult = "SELECT first_name, last_name FROM employees WHERE first_name = John, last_name = Smith";
		String givenResult = StatementBuilder.createGetStatement(targets, options, meta);
		assertEquals(expectedResult, givenResult);
	}
	
	
	
	@Test
	public void bTestCreateCreateStatementWorks() {
	
		String expectedResult = "CREATE TABLE IF NOT EXISTS employees (id SERIAL PRIMARY KEY, first_name VARCHAR, last_name VARCHAR);";
		String givenResult = StatementBuilder.createCreateStatement(meta);
		assertEquals(expectedResult, givenResult);
	}
	

	@Test
	public void cTestCreateUpdateStatementWorks() {
		String givenResult = new String();
		testModel.setId(1);
		List<String> fieldsToUpdate = new LinkedList<String>();
		fieldsToUpdate.add("first_name");
		fieldsToUpdate.add("last_name");
		String expectedResult = "UPDATE employees SET first_name = 'John', last_name = 'Smith' WHERE id = 1;";
		try {
			givenResult = StatementBuilder.createUpdateStatement(testModel, fieldsToUpdate);
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
		assertEquals(expectedResult, givenResult);
	}

	@Test
	public void dTestCreateInsertStatementWorks() {
		String givenResult = new String();
		String expectedResult = "INSERT INTO employees (first_name, last_name) VALUES ('John', 'Smith') RETURNING id;";
		try {
			givenResult = StatementBuilder.createInsertStatement(testModel);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(expectedResult, givenResult);
		
	}
	
	
	
	
}

