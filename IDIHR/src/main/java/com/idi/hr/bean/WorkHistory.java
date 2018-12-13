package com.idi.hr.bean;

import java.io.Serializable;

public class WorkHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8373133429401796296L;

	private int employeeId;
	private String employeeName;
	private String fromDate;
	private String toDate;
	private String title;
	private String department;
	private String company;
	private String salary;
	private String achievement;
	private String appraise; // danh gia, nhan xet

	public WorkHistory() {

	}

	public WorkHistory(int employeeId, String employeeName, String fromDate, String toDate, String title,
			String department, String company, String salary, String achievement, String appraise) {

		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.title = title;
		this.department = department;
		this.company = company;
		this.salary = salary;
		this.achievement = achievement;
		this.appraise = appraise;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getAchievement() {
		return achievement;
	}

	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}

	public String getAppraise() {
		return appraise;
	}

	public void setAppraise(String appraise) {
		this.appraise = appraise;
	}

}
