package com.idi.hr.bean;

import java.io.Serializable;

public class Department implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -3871003330726286155L;
	
	private String departmentId;
	private String departmentName;
	private int numberOfMember; 
	private String desc;
	
	public Department() {
		
	}

	public Department(String departmentId, String departmentName, String description) {
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.desc = description;		
	}
	
	
	public int getNumberOfMember() {
		return numberOfMember;
	}

	public void setNumberOfMember(int numberOfMember) {
		this.numberOfMember = numberOfMember;
	}

	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	

}