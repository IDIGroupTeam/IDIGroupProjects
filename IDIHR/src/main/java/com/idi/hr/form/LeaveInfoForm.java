package com.idi.hr.form;

import java.io.Serializable;

public class LeaveInfoForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -387100333072628335L;
	
	private String date;
	private String toDate;
	private String dept;
	private String eId;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String geteId() {
		return eId;
	}

	public void seteId(String eId) {
		this.eId = eId;
	}
		
}
