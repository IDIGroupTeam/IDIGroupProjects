package com.idi.hr.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class WorkingDay implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -3871012330726286155L;
	
	private String month;
	private String workDayOfMonth;
	private String forCompany; 
	private String comment;
	private String updateId;
	private Timestamp updateTs;
	
	public WorkingDay() {
		
	}

	public WorkingDay(String month, String workDayOfMonth, String forCompany, String updateId, Timestamp updateTs, String comment) {
		this.month = month;
		this.workDayOfMonth = workDayOfMonth;
		this.forCompany = forCompany;	
		this.updateId = updateId;
		this.updateTs = updateTs;
		this.comment = comment;	
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getWorkDayOfMonth() {
		return workDayOfMonth;
	}

	public void setWorkDayOfMonth(String workDayOfMonth) {
		this.workDayOfMonth = workDayOfMonth;
	}

	public String getForCompany() {
		return forCompany;
	}

	public void setForCompany(String forCompany) {
		this.forCompany = forCompany;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public Timestamp getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(Timestamp updateTs) {
		this.updateTs = updateTs;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}