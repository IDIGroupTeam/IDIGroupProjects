package com.idi.hr.bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

public class EmployeeInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6492149113228672629L;
	private int employeeId;
	private String emerName;
	private String emerPhoneNo;
	private String fullName;
	private String gender;
	private String jobTitle;
	private Date DOB;
	private String maritalStatus;
	private String loginAccount;
	private String personalId;
	private Date issueDate;
	private String department;
	private String phoneNo;
	private Date joinDate;
	private Date officalJoinDate;
	private String email;
	private Date terminationDate;
	private String reasonforLeave;
	private String currentAdress;
	private String permanentAdress;
	private String note;
	private String nation;
	private byte[] image;
	private String workStatus;
	private String bankNo;
	private String bankName;
	private String bankBranch;
	private String salary;
	private String salarySocicalInsu;
	private String socicalInsuNo;
	private String healthInsuNo;
	private String percentSocicalInsu;

	public EmployeeInfo() {

	}

	public EmployeeInfo(int employeeId, String loginAccount, String gender, String fullName, String maritalStatus,
			String jobTitle, String department, Date DOB, String personalId, Date issueDate, String phoneNo,
			Date joinDate, Date officalJoinDate, String email, Date terminationDate, String reasonforLeave,
			String currentAdress, String permanentAdress, String note, String emerName, String emerPhoneNo,
			String nation, byte[] image, String workStatus, String bankNo, String bankName, String bankBranch,
			String salary, String salarySocicalInsu, String socicalInsuNo, String healthInsuNo,
			String percentSocicalInsu) {

		this.employeeId = employeeId;
		this.loginAccount = loginAccount;
		this.gender = gender;
		this.fullName = fullName;
		this.jobTitle = jobTitle;
		this.department = department;
		this.DOB = DOB;
		this.personalId = personalId;
		this.issueDate = issueDate;
		this.phoneNo = phoneNo;
		this.joinDate = joinDate;
		this.officalJoinDate = officalJoinDate;
		this.email = email;
		this.terminationDate = terminationDate;
		this.reasonforLeave = reasonforLeave;
		this.currentAdress = currentAdress;
		this.permanentAdress = permanentAdress;
		this.note = note;
		this.emerName = emerName;
		this.emerPhoneNo = emerPhoneNo;
		this.nation = nation;
		this.image = image;
		this.workStatus = workStatus;
		this.bankNo = bankNo;
		this.bankName = bankName;
		this.bankBranch = bankBranch;
		this.salary = salary;
		this.salarySocicalInsu = salarySocicalInsu;
		this.socicalInsuNo = socicalInsuNo;
		this.healthInsuNo = healthInsuNo;
		this.percentSocicalInsu = percentSocicalInsu;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Date getDOB() {
		return DOB;
	}

	public void setDOB(Date dOB) {
		DOB = dOB;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Date getOfficalJoinDate() {
		return officalJoinDate;
	}

	public void setOfficalJoinDate(Date officalJoinDate) {
		this.officalJoinDate = officalJoinDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
	}

	public String getReasonforLeave() {
		return reasonforLeave;
	}

	public void setReasonforLeave(String reasonforLeave) {
		this.reasonforLeave = reasonforLeave;
	}

	public String getCurrentAdress() {
		return currentAdress;
	}

	public void setCurrentAdress(String currentAdress) {
		this.currentAdress = currentAdress;
	}

	public String getPermanentAdress() {
		return permanentAdress;
	}

	public void setPermanentAdress(String permanentAdress) {
		this.permanentAdress = permanentAdress;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public byte[] getImage() {
		return image;
	}

	public String getEmerName() {
		return emerName;
	}

	public void setEmerName(String emerName) {
		this.emerName = emerName;
	}

	public String getEmerPhoneNo() {
		return emerPhoneNo;
	}

	public void setEmerPhoneNo(String emerPhoneNo) {
		this.emerPhoneNo = emerPhoneNo;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getSalarySocicalInsu() {
		return salarySocicalInsu;
	}

	public void setSalarySocicalInsu(String salarySocicalInsu) {
		this.salarySocicalInsu = salarySocicalInsu;
	}

	public String getSocicalInsuNo() {
		return socicalInsuNo;
	}

	public void setSocicalInsuNo(String socicalInsuNo) {
		this.socicalInsuNo = socicalInsuNo;
	}

	public String getHealthInsuNo() {
		return healthInsuNo;
	}

	public void setHealthInsuNo(String healthInsuNo) {
		this.healthInsuNo = healthInsuNo;
	}

	public String getPercentSocicalInsu() {
		return percentSocicalInsu;
	}

	public void setPercentSocicalInsu(String percentSocicalInsu) {
		this.percentSocicalInsu = percentSocicalInsu;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public void setImage(InputStream input) {
		try {
			if (input != null) {
				ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
				byte[] dataBuffer = new byte[4096];
				int numberBytes = 0;
				while ((numberBytes = input.read(dataBuffer, 0, 4096)) != -1) {
					byteStream.write(dataBuffer, 0, numberBytes);
				}
				input.close();
				image = byteStream.toByteArray();
			}
		} catch (IOException e) {
		}
	}

}
