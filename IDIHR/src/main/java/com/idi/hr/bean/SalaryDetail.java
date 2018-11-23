package com.idi.hr.bean;

import java.io.Serializable;

public class SalaryDetail implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -1871003330726286159L;
	
	private int employeeId; 
	private String finalSalary;
	private String overTimeN;
	private String overTimeW;
	private String overTimeH;
	private String overTimeSalary;
	private float salaryPerHour;
	private String bounus;
	private String subsidize;
	private String advancePayed;
	private String taxPersonal;
	private int month;
	private int year;
	private String desc;
	private String payedInsurance;
	private String fullName;
	private String phoneNo;
	private String bankNo;
	private String bankName;
	private String bankBranch;
	private String salary;
	private String department;
	private String jobTitle;
	private String salaryInsurance;
	private String percentCompanyPay;
	private String percentEmployeePay;
	private int workComplete;
	
	public SalaryDetail() {
		
	}

	public SalaryDetail(int employeeId, String finalSalary, String overTimeN, String overTimeW, String overTimeH, String overTimeSalary,
		String bounus, String subsidize, String advancePayed, String taxPersonal, int month, int year,	String description,
		String payedInsurance, String fullName, String phoneNo, String bankNo, String bankName, String bankBranch, String salary, 
		String department, String jobTitle, String salaryInsurance, String percentCompanyPay, String percentEmployeePay, int workComplete) {
		
		this.employeeId = employeeId;
		this.finalSalary = finalSalary;
		this.overTimeN = overTimeN;
		this.overTimeW = overTimeW;
		this.overTimeH = overTimeH;
		this.overTimeSalary = overTimeSalary;
		this.bounus = bounus;
		this.subsidize = subsidize;
		this.advancePayed = advancePayed;
		this.taxPersonal = taxPersonal;
		this.month = month;
		this.year = year;
		this.desc = description;		
		this.payedInsurance = payedInsurance;
		
		this.fullName = fullName;
		this.phoneNo = phoneNo;
		this.bankNo = bankNo;
		this.bankName = bankName;
		this.bankBranch = bankBranch;
		this.salary = salary;
		this.department = department;
		this.jobTitle = jobTitle;
		this.salaryInsurance = salaryInsurance;
		this.percentCompanyPay = percentCompanyPay;
		this.percentEmployeePay = percentEmployeePay;
		this.workComplete = workComplete;
		
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

	public float getSalaryPerHour() {
		return salaryPerHour;
	}

	public void setSalaryPerHour(float salaryPerHour) {
		this.salaryPerHour = salaryPerHour;
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

	public String getDesc() {
		return desc;
	}
	
	public String getPayedInsurance() {
		return payedInsurance;
	}

	public void setPayedInsurance(String payedInsurance) {
		this.payedInsurance = payedInsurance;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getSalaryInsurance() {
		return salaryInsurance;
	}

	public void setSalaryInsurance(String salaryInsurance) {
		this.salaryInsurance = salaryInsurance;
	}

	public String getPercentCompanyPay() {
		return percentCompanyPay;
	}

	public void setPercentCompanyPay(String percentCompanyPay) {
		this.percentCompanyPay = percentCompanyPay;
	}

	public String getPercentEmployeePay() {
		return percentEmployeePay;
	}

	public void setPercentEmployeePay(String percentEmployeePay) {
		this.percentEmployeePay = percentEmployeePay;
	}

	public int getWorkComplete() {
		return workComplete;
	}

	public void setWorkComplete(int workComplete) {
		this.workComplete = workComplete;
	}

}