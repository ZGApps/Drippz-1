package com.drippz.testing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.drippz.connections.DScreator;
import com.drippz.util.Configuration;
import com.drippz.util.MetaModel;
import com.drippz.statements.StatementBuilder;
import com.drippz.testing.TestModel;

public class ConnectionTest {

	public static void main(String[] args) {
		ConnectionTest dsDemo = new ConnectionTest();
//		dsDemo.displayEmployees();
//		dsDemo.displayEmployeeById(1);
		MetaModel<?> meta = MetaModel.of(TestModel.class);
		StatementBuilder.createCreateStatement(meta);
	}

	private void displayEmployeeById(int id) {
		
		Configuration cfg = new Configuration();
		Connection connection = null;
		List<String> target = new ArrayList<String>();
		target.add("*");
		Map<String, String> options = new HashMap<String, String>();
		options.put("id =", "1");
		String selectSQL = StatementBuilder.createGetOrSelectOrUpdateOrDeleteStatement("get", target, (HashMap<String, String>) options, cfg.makeSingleModel(TestModel.class).getSingleModel());
		System.out.println(selectSQL);
		PreparedStatement prepStmt = null;
		try {
			DataSource ds = DScreator.getDataSource();
			connection = ds.getConnection();
			prepStmt = connection.prepareStatement(selectSQL);
			prepStmt.setInt(1, id);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				System.out.println("id: " + rs.getInt("id"));
				System.out.println("First Name: " + rs.getString("first_name"));
				System.out.println("Last Name: " + rs.getString("last_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void displayEmployees() {
		Connection connection = null;
		Configuration cfg = new Configuration();
		List<String> target = new ArrayList<String>();
		target.add("first_name");
		target.add("last_name");
		Map<String, String> options = new HashMap<String, String>();
		String sql = StatementBuilder.createGetOrSelectOrUpdateOrDeleteStatement("get", target, (HashMap<String, String>) options, cfg.makeSingleModel(TestModel.class).getSingleModel());
		
		try {
			DataSource ds = DScreator.getDataSource();
			connection = ds.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
//				System.out.println("id: " + rs.getInt("id"));
				System.out.println("First Name: " + rs.getString("first_name"));
				System.out.println("Last Name: " + rs.getString("last_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}