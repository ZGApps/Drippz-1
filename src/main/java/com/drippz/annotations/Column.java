package com.drippz.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

	String columnName() default "";

	boolean unique() default false;

	boolean nullable() default true;

	int length() default 255;

	int precision() default 0;

	int scale() default 0;

}