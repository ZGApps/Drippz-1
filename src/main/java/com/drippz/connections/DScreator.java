package com.drippz.connections;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DScreator {

	private static Logger logger = Logger.getLogger(DScreator.class);
	
	private static HikariDataSource ds;
	static {
		


		String url = "";
		String username = "";
		String password = "";
	

			Properties prop = getPropsFile();
			url = prop.getProperty("DB_URL");
			System.out.println(url);
			username = prop.getProperty("username");
			password = prop.getProperty("password");

			HikariConfig config = new HikariConfig();
			config.setDriverClassName("org.postgresql.Driver");;
			config.setJdbcUrl(url);
			config.setUsername(username);
			config.setPassword(password);
			config.addDataSourceProperty("cachePrepStmts", "true");
			config.addDataSourceProperty("prepStmtCacheSize", "250");
			config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

			ds = new HikariDataSource(config);
 
	}
	public static DataSource getDataSource() {
		return ds;
	}
	
	private static Properties getPropsFile() {
		Properties p = new Properties();
		String loc = new File("").getAbsolutePath();
		String targetName = "drippz.props";
		java.nio.file.Path path = java.nio.file.Paths.get(loc, "src", "main", "resources", targetName);
		if(!java.nio.file.Files.exists(path)) {
			logger.error("No properties file found in " + path.toString());
			return p;
		}
		String pathString = path.toString();
		try(FileReader file = new FileReader(pathString)){
			p.load(file);
			return p;
		} catch (FileNotFoundException e) {
			logger.error("No properties file: drippz.props, found in " + path.toString());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Could not read Properties File");
			e.printStackTrace();
		}
		return p;

	}
}


 