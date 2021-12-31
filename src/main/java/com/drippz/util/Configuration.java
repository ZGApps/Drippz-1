package com.drippz.util;

import java.sql.Connection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
	public Connection getConnection(String dbURL, String dbUsername, String dbPassword) {

		// TODO Connection Pool link HERE
		// TODO Figure out how to get the INPUT for loging into the DB from the props
		// file in the implementing project?
		// you can access the instance variables of the config object
		// this is up to your creativity -- connect to DB somehow
		return null;
	}
}
