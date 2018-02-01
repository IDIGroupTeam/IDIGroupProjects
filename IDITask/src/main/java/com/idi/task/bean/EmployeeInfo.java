package com.idi.task.bean;

import java.io.Serializable;

public class EmployeeInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6492149113628672629L;
	private int employeeId;
	private String loginAccount;
	private String fullName;
	private String jobTitle;
	private String department;
	private String departmentName;
	private String phoneNo;	
	private String email;
	private String workStatus;
	
	public EmployeeInfo() {

	}

	public EmployeeInfo(int employeeId, String loginAccount, String fullName, 
			String jobTitle, String department, String departmentName, String phoneNo,
			String email, String workStatus) {

		this.employeeId = employeeId;
		this.loginAccount = loginAccount;
		this.fullName = fullName;
		this.jobTitle = jobTitle;
		this.department = department;
		this.departmentName = departmentName;
		this.phoneNo = phoneNo;
		this.email = email;
		this.workStatus = workStatus;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

}
