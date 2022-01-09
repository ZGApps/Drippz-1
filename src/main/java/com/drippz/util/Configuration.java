package com.drippz.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import com.drippz.connections.DScreator;

public class Configuration {


	private List<MetaModel<Class<?>>> metaModelList;
	private MetaModel<Class<?>> singleModel;

	public Configuration addAnnotatedClass(Class<?> annotatedClass) {

		if (metaModelList == null) {
			metaModelList = new LinkedList<>();
		}

		metaModelList.add(MetaModel.of(annotatedClass));
		return this;
	}

	public Configuration makeSingleModel(Class<?> annotatedClass) {

		singleModel = MetaModel.of(annotatedClass);

		return this;

	}

	public List<MetaModel<Class<?>>> getMetaModels() {

		return (metaModelList == null) ? Collections.emptyList() : metaModelList;
	}

	public MetaModel<Class<?>> getSingleModel() {
		return singleModel;
	}

	// return a connection object or call on a seperate class like connection Util
	public Connection getConnection() throws SQLException {
		DataSource ds = DScreator.getDataSource();
		Connection connection = null;
		connection = ds.getConnection();
		return connection;
	}

	public boolean closeConnection(Connection con) {

		if (con != null) {
			try {
				con.close();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	public String getMode() {
		return DScreator.getMode();
	}
}
