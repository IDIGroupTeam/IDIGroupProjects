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
	private String leaveType;
	private String timeValue;
	private String comment;

	private String employeeName;
	private String department;
	private String title;

	public LeaveInfo() {
	}

	public LeaveInfo(int employeeId, Date date, String leaveType, String timeValue, String comment, String employeeName,
			String department, String title) {

		this.employeeId = employeeId;
		this.date = date;
		this.leaveType = leaveType;
		this.timeValue = timeValue;
		this.comment = comment;
		this.employeeName = employeeName;
		this.department = department;
		this.title = title;
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

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getTimeValue() {
		return timeValue;
	}

	public void setTimeValue(String timeValue) {
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
}
