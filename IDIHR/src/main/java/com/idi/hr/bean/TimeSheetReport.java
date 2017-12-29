package com.idi.hr.bean;

import java.io.Serializable;

public class TimeSheetReport implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4968828210574815761L;

	private int employeeId;
	private String leaveAppoved; // nghi co phep//
	private String leaveUnAppoved;// nghi khong phep//
	private String leaveWithoutPay;// nghi khong luong//
	private String notTimekeepingCount;// so lan khong cham cong
	private String seniority;// tham nien = (current date - join date)
	private String quataLeave;// tong so ngay phep cua nam -> TINH THEO CONG THUC
	private String restQuata;// tong so ngay phep con tu nam truoc
	private String leaveUsed;// so ngay phep da su dung trong nam
	private String leaveRemain;// so ngay phep con lai trong nam = (quataLeave - leaveUsed) + restQuata
	//count come late, leave soon, 

	public TimeSheetReport() {
	}

	public TimeSheetReport(int employeeId, String seniority, String quataLeave, String restQuata, String leaveUsed,
			String leaveRemain) {

		this.employeeId = employeeId;
		this.seniority = seniority;
		this.quataLeave = quataLeave;
		this.restQuata = restQuata;
		this.leaveUsed = leaveUsed;
		this.leaveRemain = leaveRemain;

	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getLeaveAppoved() {
		return leaveAppoved;
	}

	public void setLeaveAppoved(String leaveAppoved) {
		this.leaveAppoved = leaveAppoved;
	}

	public String getLeaveUnAppoved() {
		return leaveUnAppoved;
	}

	public void setLeaveUnAppoved(String leaveUnAppoved) {
		this.leaveUnAppoved = leaveUnAppoved;
	}

	public String getLeaveWithoutPay() {
		return leaveWithoutPay;
	}

	public void setLeaveWithoutPay(String leaveWithoutPay) {
		this.leaveWithoutPay = leaveWithoutPay;
	}

	public String getNotTimekeepingCount() {
		return notTimekeepingCount;
	}

	public void setNotTimekeepingCount(String notTimekeepingCount) {
		this.notTimekeepingCount = notTimekeepingCount;
	}

	public String getSeniority() {
		return seniority;
	}

	public void setSeniority(String seniority) {
		this.seniority = seniority;
	}

	public String getQuataLeave() {
		return quataLeave;
	}

	public void setQuataLeave(String quataLeave) {
		this.quataLeave = quataLeave;
	}

	public String getRestQuata() {
		return restQuata;
	}

	public void setRestQuata(String restQuata) {
		this.restQuata = restQuata;
	}

	public String getLeaveUsed() {
		return leaveUsed;
	}

	public void setLeaveUsed(String leaveUsed) {
		this.leaveUsed = leaveUsed;
	}

	public String getLeaveRemain() {
		return leaveRemain;
	}

	public void setLeaveRemain(String leaveRemain) {
		this.leaveRemain = leaveRemain;
	}
}
