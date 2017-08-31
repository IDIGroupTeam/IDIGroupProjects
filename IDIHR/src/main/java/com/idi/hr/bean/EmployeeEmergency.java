package com.idi.hr.bean;

import java.io.Serializable;

public class EmployeeEmergency implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String employeeId;
	private String name;
	private String relationship;
	private String phone;
	private String address;

	/**
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return
	 */
	public String getemployeeId() {
		return employeeId;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @return
	 */
	public String getRelationship() {
		return relationship;
	}

	/**
	 * @param string
	 */
	public void setAddress(String string) {
		address = string;
	}

	/**
	 * @param string
	 */
	public void setemployeeId(String string) {
		employeeId = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param string
	 */
	public void setPhone(String string) {
		phone = string;
	}

	/**
	 * @param string
	 */
	public void setRelationship(String string) {
		relationship = string;
	}

}