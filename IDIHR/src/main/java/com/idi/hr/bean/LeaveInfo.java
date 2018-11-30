package com.idi.hr.bean;

import java.io.Serializable;
import java.sql.Date;

public class LeaveInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6056292862087841823L;

	private int employeeId;
	private Date date;
	private String toDate;
	private String leaveType;
	private float timeValue;
	private String comment;
	private String leaveName;
	private String employeeName;
	private String department;
	private String title;
	private String overLate;
	private String overLeave;
	private String duplicate;
	private String toDateInvalid;
	
	public LeaveInfo() {
	}

	public LeaveInfo(int employeeId, Date date, String leaveType, float timeValue, String comment, String employeeName,
			String department, String title, String leaveName) {

		this.employeeId = employeeId;
		this.date = date;
		this.leaveType = leaveType;
		this.timeValue = timeValue;
		this.comment = comment;
		this.employeeName = employeeName;
		this.department = department;
		this.title = title;
		this.leaveName = leaveName;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public Date getDate() {
		return date;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getLeaveName() {
		return leaveName;
	}

	public void setLeaveName(String leaveName) {
		this.leaveName = leaveName;
	}

	public float getTimeValue() {
		return timeValue;
	}

	public void setTimeValue(float timeValue) {
		this.timeValue = timeValue;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOverLate() {
		return overLate;
	}

	public void setOverLate(String overLate) {
		this.overLate = overLate;
	}

	public String getOverLeave() {
		return overLeave;
	}

	public void setOverLeave(String overLeave) {
		this.overLeave = overLeave;
	}

	public String getDuplicate() {
		return duplicate;
	}

	public void setDuplicate(String duplicate) {
		this.duplicate = duplicate;
	}

	public String getToDateInvalid() {
		return toDateInvalid;
	}

	public void setToDateInvalid(String toDateInvalid) {
		this.toDateInvalid = toDateInvalid;
	}	
	
}
