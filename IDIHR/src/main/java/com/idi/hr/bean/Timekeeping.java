package com.idi.hr.bean;

import java.io.Serializable;
import java.sql.Date;

public class Timekeeping implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8997072752578722467L;

	private int employeeId;
	private String employeeName;
	private String joinDate;
	
	private Date date;
	//private String comeLeave;
	private String leaveType;
	private String overtimeType;
	private int notTimekeeping;//ko cham cong
	private String overTimeValue;
	//private String onBussiness;//di cong tac
	//private String study;//di hoc
	private String comment;
	
	//----- for report -----// 
	//count for month
	private String seniority;//tham nien
	private String leaveAppoved; //nghi co phep//
	private String leaveUnAppoved;// nghi khong phep//
	private String leaveWithoutPay;// nghi khong luong//
	//private String notTimekeepingCount;// so lan khong cham cong
	private String comeLateM;// den muon sang
	private String leaveSoonM;//ve som sang
	private String comeLateA;// den muon chieu
	private String leaveSoonA;//ve som chieu
	//count for year
	private String quataLeave;//tong so ngay phep cua nam -> TINH THEO CONG THUC
	private String restQuata;// tong so ngay phep con tu nam truoc
	private String leaveUsed;// so ngay phep da su dung trong nam
	private String leaveRemain;//so ngay phep con lai trong nam
	//
		
	public Timekeeping() {}

	public Timekeeping(int employeeId, String employeeName, String joinDate, Date date, String leaveType,
			String overtimeType, int notTimekeeping, String overTimeValue, String comment,// String seniority,
			String comeLateM, String leaveSoonM, String comeLateA, String leaveSoonA, String quataLeave,
			String restQuata, String leaveUsed, String leaveRemain) {

		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.joinDate = joinDate;
		this.date = date;
		this.leaveType = leaveType;
		this.overtimeType = overtimeType;
		this.notTimekeeping = notTimekeeping;
		this.overTimeValue = overTimeValue;
		this.comment = comment;
		
		this.seniority = seniority;
		this.comeLateM = comeLateM;
		this.leaveSoonM = leaveSoonM;
		this.comeLateA = comeLateA;
		this.leaveSoonA = leaveSoonA;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
/*	public String getComeLeave() {
		return comeLeave;
	}
	public void setComeLeave(String comeLeave) {
		this.comeLeave = comeLeave;
	}*/
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public String getOvertimeType() {
		return overtimeType;
	}
	public void setOvertimeType(String overtimeType) {
		this.overtimeType = overtimeType;
	}
	public int getNotTimekeeping() {
		return notTimekeeping;
	}
	public void setNotTimekeeping(int notTimekeeping) {
		this.notTimekeeping = notTimekeeping;
	}
	public String getOverTimeValue() {
		return overTimeValue;
	}
	public void setOverTimeValue(String overTimeValue) {
		this.overTimeValue = overTimeValue;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getSeniority() {
		return seniority;
	}
	public void setSeniority(String seniority) {
		this.seniority = seniority;
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
/*	public String getNotTimekeepingCount() {
		return notTimekeepingCount;
	}
	public void setNotTimekeepingCount(String notTimekeepingCount) {
		this.notTimekeepingCount = notTimekeepingCount;
	}*/
	public String getComeLateM() {
		return comeLateM;
	}
	public void setComeLateM(String comeLateM) {
		this.comeLateM = comeLateM;
	}
	public String getLeaveSoonM() {
		return leaveSoonM;
	}
	public void setLeaveSoonM(String leaveSoonM) {
		this.leaveSoonM = leaveSoonM;
	}
	public String getComeLateA() {
		return comeLateA;
	}
	public void setComeLateA(String comeLateA) {
		this.comeLateA = comeLateA;
	}
	public String getLeaveSoonA() {
		return leaveSoonA;
	}
	public void setLeaveSoonA(String leaveSoonA) {
		this.leaveSoonA = leaveSoonA;
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
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	
}
