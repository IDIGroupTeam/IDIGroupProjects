package com.idi.hr.bean;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class Timekeeping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8997072752578722467L;

	private int employeeId;
	private String employeeName;
	private String department;
	private String title;
	private String timeIn;
	private String timeOut;
	private String workedTime;
	private Date date;
	private String comment;

	private String comeLateM;// den muon sang
	private String leaveSoonM;// ve som sang
	private String comeLateA;// den muon chieu
	private String leaveSoonA;// ve som chieu

	//
	private MultipartFile timeKeepingFile;

	public Timekeeping() {
	}

	public Timekeeping(int employeeId, String employeeName, Date date, String timeIn, String timeOut, String workedTime,
			String comment, String department, String title, String comeLateM, String leaveSoonM, String comeLateA,
			String leaveSoonA) {

		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.date = date;
		this.timeIn = timeIn;
		this.timeOut = timeOut;
		this.workedTime = workedTime;
		this.comment = comment;
		this.department = department;
		this.title = title;
		this.comeLateM = comeLateM;
		this.leaveSoonM = leaveSoonM;
		this.comeLateA = comeLateA;
		this.leaveSoonA = leaveSoonA;

	}
	
	public Timekeeping(int employeeId, String employeeName, Date date, String department, String title, String timeIn, String timeOut, String comment) {

		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.date = date;
		this.department= department;
		this.title = title;
		this.timeIn = timeIn;		
		this.timeOut = timeOut;
		this.comment = comment;	

	}

	public String getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(String timeIn) {
		this.timeIn = timeIn;
	}

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	public String getWorkedTime() {
		return workedTime;
	}

	public void setWorkedTime(String workedTime) {
		this.workedTime = workedTime;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

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

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public MultipartFile getTimeKeepingFile() {
		return timeKeepingFile;
	}

	public void setTimeKeepingFile(MultipartFile timeKeepingFile) {
		this.timeKeepingFile = timeKeepingFile;
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
