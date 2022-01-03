package com.drippz.testing;

import com.drippz.annotations.Column;
import com.drippz.annotations.Entity;
import com.drippz.annotations.Id;

@Entity(entityName="employees")
public class TestModel {
	@Id
	private int id;
	@Column(columnName="first_name")
	private String firstName;
	@Column(columnName="last_name")
	private String lastName;
	@Override
	public String toString() {
		return "TestModel [firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}
