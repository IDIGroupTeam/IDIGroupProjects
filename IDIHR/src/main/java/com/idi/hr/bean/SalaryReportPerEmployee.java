package com.idi.hr.bean;

import java.io.Serializable;

public class SalaryReportPerEmployee implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -1871003330726282259L;
	
	private int employeeId; 
	private String fullName;
	private String salary;
	private String department;
	private String jobTitle;
	private String finalSalary;
	private String overTimeN;
	private String overTimeW;
	private String overTimeH;
	private String overTimeSalary;
	private String bounus;
	private String subsidize;
	private String advancePayed;
	private String taxPersonal;
	private int month;
	private int year;	
	private String payedInsurance;
	
	public SalaryReportPerEmployee() {
		
	}

	public SalaryReportPerEmployee(int employeeId, String fullName, String department, String jobTitle,
			String salary, String finalSalary, String overTimeN, String overTimeW, String overTimeH, String overTimeSalary,
		    String bounus, String subsidize, String advancePayed, String taxPersonal, String payedInsurance) {
		this.employeeId = employeeId;
		this.fullName = fullName;
		this.salary = salary;
		this.department = department;
		this.jobTitle = jobTitle;
		this.finalSalary = finalSalary;
		this.overTimeN = overTimeN;
		this.overTimeW = overTimeW;
		this.overTimeH = overTimeH;
		this.overTimeSalary = overTimeSalary;
		this.bounus = bounus;
		this.subsidize = subsidize;
		this.advancePayed = advancePayed;
		this.taxPersonal = taxPersonal;
				
/*		this.month = month;
		this.year = year;*/
		this.payedInsurance = payedInsurance;

	}	

	public String getFinalSalary() {
		return finalSalary;
	}

	public void setSalaryFinalSalary(String finalSalary) {
		this.finalSalary = finalSalary;
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

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getDepartment() {
		return department;
	}

	public void setPayedInsurance(String payedInsurance) {
		this.payedInsurance = payedInsurance;
	}

	public String getOverTimeN() {
		return overTimeN;
	}

	public void setOverTimeN(String overTimeN) {
		this.overTimeN = overTimeN;
	}

	public String getOverTimeW() {
		return overTimeW;
	}

	public void setOverTimeW(String overTimeW) {
		this.overTimeW = overTimeW;
	}

	public String getOverTimeH() {
		return overTimeH;
	}

	public void setOverTimeH(String overTimeH) {
		this.overTimeH = overTimeH;
	}

	public String getOverTimeSalary() {
		return overTimeSalary;
	}

	public void setOverTimeSalary(String overTimeSalary) {
		this.overTimeSalary = overTimeSalary;
	}

	public String getBounus() {
		return bounus;
	}

	public void setBounus(String bounus) {
		this.bounus = bounus;
	}

	public String getSubsidize() {
		return subsidize;
	}

	public void setSubsidize(String subsidize) {
		this.subsidize = subsidize;
	}

	public String getAdvancePayed() {
		return advancePayed;
	}

	public void setAdvancePayed(String advancePayed) {
		this.advancePayed = advancePayed;
	}

	public String getTaxPersonal() {
		return taxPersonal;
	}

	public void setTaxPersonal(String taxPersonal) {
		this.taxPersonal = taxPersonal;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setFinalSalary(String finalSalary) {
		this.finalSalary = finalSalary;
	}

	public String getPayedInsurance() {
		return payedInsurance;
	}

	public void setDepartment(String payedInsurance) {
		this.payedInsurance = payedInsurance;
	}
}