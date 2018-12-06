package com.idi.hr.form;

import java.io.Serializable;

public class SalaryForm implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -347300333072628695L;

	//salary report per employee
	private int employeeId; 
	private int monthReport;
	private int yearReport;	

	//Paging
	private int pageIndex;
	private int totalRecords;
	private int numberRecordsOfPage;
	private int totalPages;
	
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getMonthReport() {
		return monthReport;
	}

	public void setMonthReport(int monthReport) {
		this.monthReport = monthReport;
	}

	public int getYearReport() {
		return yearReport;
	}

	public void setYearReport(int yearReport) {
		this.yearReport = yearReport;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getNumberRecordsOfPage() {
		return numberRecordsOfPage;
	}

	public void setNumberRecordsOfPage(int numberRecordsOfPage) {
		this.numberRecordsOfPage = numberRecordsOfPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}	

}
