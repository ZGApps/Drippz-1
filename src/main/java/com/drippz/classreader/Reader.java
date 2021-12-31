package com.drippz.classreader;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;

public class Reader {

	private static Logger logger = Logger.getLogger(Reader.class);
	
	public static Field getPK(Class<?> c) {
		Field[] fields = c.getDeclaredFields();
		
		Field pkField = null;
		return pkField;
	}
	
}
