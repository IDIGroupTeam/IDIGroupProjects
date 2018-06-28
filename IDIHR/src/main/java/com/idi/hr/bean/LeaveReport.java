package com.idi.hr.bean;

import java.io.Serializable;
import java.util.Map;

public class LeaveReport implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4968828210574815761L;

	private int employeeId;
	private String name;
	private String joinDate;
	private String department;
	private String leaveAppoved; // nghi co phep//
	private String leaveUnAppoved;// nghi khong phep//
	private String leaveWithoutPay;// nghi khong luong//
	private String notTimekeepingCount;// so lan khong cham cong
	private String seniority;// tham nien = (current date - join date)
	private String quataLeave;// tong so ngay phep cua nam -> TINH THEO CONG THUC
	private String restQuata;// tong so ngay phep con tu nam truoc
	private String leaveUsed;// so ngay phep da su dung trong nam
	private String leaveRemain;// so ngay phep con lai trong nam = (quataLeave - leaveUsed) + restQuata
	private String monthReport;
	private String yearReport;
	
	//count come late, leave soon, 
	private int comeLate = 0; // tong so lan di muon
	private int leaveSoon = 0; // tong so lan ve som
	private int lateMValue = 0; // tong thoi gian di muon sang
	private int lateAValue = 0; // tong thoi gian di muon chieu
	private int leaveSoonMValue = 0; // tong thoi gian ve som sang
	private int leaveSoonAValue = 0; // tong thoi gian ve som chieu
	
	private String active;
	
	private String leaveTypeReport;	
	private Map<String, String> leaveTypes;
	
	public LeaveReport() {}

	public LeaveReport(int employeeId, String yearReport, String seniority, String quataLeave, String restQuata, String leaveUsed,
			String leaveRemain) {

		this.employeeId = employeeId;
		this.yearReport = yearReport;
		this.seniority = seniority;
		this.quataLeave = quataLeave;
		this.restQuata = restQuata;
		this.leaveUsed = leaveUsed;
		this.leaveRemain = leaveRemain;

	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	public Map<String, String> getLeaveTypes() {
		return leaveTypes;
	}

	public void setLeaveTypes(Map<String, String> leaveTypes) {
		this.leaveTypes = leaveTypes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getLeaveTypeReport() {
		return leaveTypeReport;
	}

	public void setLeaveTypeReport(String leaveTypeReport) {
		this.leaveTypeReport = leaveTypeReport;
	}

	public String getMonthReport() {
		return monthReport;
	}

	public void setMonthReport(String monthReport) {
		this.monthReport = monthReport;
	}

	public String getYearReport() {
		return yearReport;
	}

	public void setYearReport(String yearReport) {
		this.yearReport = yearReport;
	}

	public int getComeLate() {
		return comeLate;
	}

	public void setComeLate(int comeLate) {
		this.comeLate = comeLate;
	}

	public int getLeaveSoon() {
		return leaveSoon;
	}

	public void setLeaveSoon(int leaveSoon) {
		this.leaveSoon = leaveSoon;
	}

	public int getLateMValue() {
		return lateMValue;
	}

	public void setLateMValue(int lateMValue) {
		this.lateMValue = lateMValue;
	}

	public int getLateAValue() {
		return lateAValue;
	}

	public void setLateAValue(int lateAValue) {
		this.lateAValue = lateAValue;
	}

	public int getLeaveSoonMValue() {
		return leaveSoonMValue;
	}

	public void setLeaveSoonMValue(int leaveSoonMValue) {
		this.leaveSoonMValue = leaveSoonMValue;
	}

	public int getLeaveSoonAValue() {
		return leaveSoonAValue;
	}

	public void setLeaveSoonAValue(int leaveSoonAValue) {
		this.leaveSoonAValue = leaveSoonAValue;
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
