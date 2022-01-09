package com.drippz.testing;

import java.util.Objects;

import com.drippz.annotations.Column;
import com.drippz.annotations.Entity;
import com.drippz.annotations.Id;

@Entity(entityName="employees")
public class TestModel {
	@Id(columnName="id")
	private int id;
	@Column(columnName="first_name")
	private String firstName;
	@Column(columnName="last_name")
	private String lastName;
	@Override
	public String toString() {
		return "TestModel [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	public TestModel(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public TestModel(int id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public TestModel() {
		super();
	}
	@Override
	public int hashCode() {
		return Objects.hash(firstName, id, lastName);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestModel other = (TestModel) obj;
		return Objects.equals(firstName, other.firstName) && id == other.id && Objects.equals(lastName, other.lastName);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
