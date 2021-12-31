package com.drippz.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import com.drippz.connections.DScreator;

public class Configuration {

	private String dbURL;
	private String dbUsername;
	private String dbPassword;

	private List<MetaModel<Class<?>>> metaModelList;

	public Configuration addAnnotatedClass(Class<?> annotatedClass) {

		if (metaModelList == null) {
			metaModelList = new LinkedList<>();
		}

		metaModelList.add(MetaModel.of(annotatedClass));
		return this;
	}

	public List<MetaModel<Class<?>>> getMetaModels() {

		return (metaModelList == null) ? Collections.emptyList() : metaModelList;
	}

	// return a connection object or call on a seperate class like connection Util
	public Connection getConnection(String dbURL, String dbUsername, String dbPassword) throws SQLException {
		DataSource ds = DScreator.getDataSource();
		Connection connection = null;
		connection = ds.getConnection();
		return connection;
	}
}
