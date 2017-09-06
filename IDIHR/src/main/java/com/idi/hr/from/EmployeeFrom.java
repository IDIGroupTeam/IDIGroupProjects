package com.idi.hr.from;

import java.io.Serializable;

public class EmployeeFrom implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -824617476961929602L;
	
	private String email;
	private String fullName;
	private String gender;
	

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	
}
