package com.idi.hr.bean;

import java.io.Serializable;

public class LeaveType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -561952282980815175L;
	
	private String leaveId;
	private String leaveName;
	private String description;
	
	public LeaveType() {
		
	}

	public LeaveType(String leaveId, String leaveName, String description) {
		this.leaveId = leaveId;
		this.leaveName = leaveName;
		this.description = description;		
	}

	public String getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}

	public String getLeaveName() {
		return leaveName;
	}

	public void setLeaveName(String leaveName) {
		this.leaveName = leaveName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
