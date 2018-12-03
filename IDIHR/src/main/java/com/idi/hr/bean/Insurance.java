package com.idi.hr.bean;

import java.io.Serializable;

public class Insurance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4987687152953100642L;

	private int employeeId;
	private String employeeName;
	private String socicalInsuNo;
	private String salarySocicalInsu;	
	private String percentSInsuC;
	private String percentSInsuE;
	private String payType;
	private String salaryZone;
	private String place; 
	private String status;
	private String hInsuNo;
	private String hInsuPlace;	
	private String comment;

	public Insurance() {

	}

	public Insurance(int employeeId, String employeeName, String socicalInsuNo, String salarySocicalInsu, String percentSInsuC, String percentSInsuE,
			String payType, String salaryZone, String place, String status, String hInsuNo, String hInsuPlace, String comment) {

		this.employeeId = employeeId;		
		this.employeeName = employeeName;
		this.socicalInsuNo = socicalInsuNo;
		this.salarySocicalInsu = salarySocicalInsu;
		this.percentSInsuC = percentSInsuC;
		this.percentSInsuE = percentSInsuE;
		this.payType = payType;
		this.salaryZone = salaryZone;
		this.place = place;
		
		this.status = status;
		this.hInsuNo = hInsuNo;
		this.hInsuPlace = hInsuPlace;	
		this.comment = comment;
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

	public String getSalarySocicalInsu() {
		return salarySocicalInsu;
	}

	public void setSalarySocicalInsu(String salarySocicalInsu) {
		this.salarySocicalInsu = salarySocicalInsu;
	}

	public String getSocicalInsuNo() {
		return socicalInsuNo;
	}

	public void setSocicalInsuNo(String socicalInsuNo) {
		this.socicalInsuNo = socicalInsuNo;
	}

	public String getPercentSInsuC() {
		return percentSInsuC;
	}

	public void setPercentSInsuC(String percentSInsuC) {
		this.percentSInsuC = percentSInsuC;
	}

	public String getPercentSInsuE() {
		return percentSInsuE;
	}

	public void setPercentSInsuE(String percentSInsuE) {
		this.percentSInsuE = percentSInsuE;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}	

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getSalaryZone() {
		return salaryZone;
	}

	public void setSalaryZone(String salaryZone) {
		this.salaryZone = salaryZone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String gethInsuNo() {
		return hInsuNo;
	}

	public void sethInsuNo(String hInsuNo) {
		this.hInsuNo = hInsuNo;
	}

	public String gethInsuPlace() {
		return hInsuPlace;
	}

	public void sethInsuPlace(String hInsuPlace) {
		this.hInsuPlace = hInsuPlace;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
