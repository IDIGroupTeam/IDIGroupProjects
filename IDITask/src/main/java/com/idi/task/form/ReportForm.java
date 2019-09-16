package com.idi.task.form;

import java.io.Serializable;

public class ReportForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3276917600422061093L;
	
	private String fromDate;
	private String toDate;
	private int employeeId;
	private String employeeName;
	private String department;
	private String sender;
	private String comment;
	private String summary;
	
	private String idCheck;
	private String estimateCheck;
	private String updateTimeCheck;
	private String dueDateCheck;
	private String desCheck;
	private String unSelect;
	private String unSelected;
	
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
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getIdCheck() {
		return idCheck;
	}
	public void setIdCheck(String idCheck) {
		this.idCheck = idCheck;
	}
	public String getEstimateCheck() {
		return estimateCheck;
	}
	public void setEstimateCheck(String estimateCheck) {
		this.estimateCheck = estimateCheck;
	}
	public String getUpdateTimeCheck() {
		return updateTimeCheck;
	}
	public void setUpdateTimeCheck(String updateTimeCheck) {
		this.updateTimeCheck = updateTimeCheck;
	}
	public String getDueDateCheck() {
		return dueDateCheck;
	}
	public void setDueDateCheck(String dueDateCheck) {
		this.dueDateCheck = dueDateCheck;
	}	
	public String getDesCheck() {
		return desCheck;
	}
	public void setDesCheck(String desCheck) {
		this.desCheck = desCheck;
	}
	public String getUnSelect() {
		return unSelect;
	}
	public void setUnSelect(String unSelect) {
		this.unSelect = unSelect;
	}
	public String getUnSelected() {
		return unSelected;
	}
	public void setUnSelected(String unSelected) {
		this.unSelected = unSelected;
	}		
	
}
