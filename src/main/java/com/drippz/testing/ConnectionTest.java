package com.drippz.testing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.drippz.connections.DScreator;

public class ConnectionTest {

	public static void main(String[] args) {
		ConnectionTest dsDemo = new ConnectionTest();
		dsDemo.displayEmployees();
		dsDemo.displayEmployeeById(1);
	}

	private void displayEmployeeById(int id) {
		Connection connection = null;
		String selectSQL = "SELECT * FROM employees WHERE id = ?";
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
		String sql = "SELECT * FROM employees";
		
		try {
			DataSource ds = DScreator.getDataSource();
			connection = ds.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
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
}