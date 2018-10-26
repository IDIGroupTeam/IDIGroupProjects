package com.idi.task.login.bean;

public class UserRoleBean {
	private int roleID;
	private int secondRoleID;
	private int thirdRoleID;
	private String userRole;
	private int employeeID;
	private int idUser;
	
	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	public int getSecondRoleID() {
		return secondRoleID;
	}
	public void setSecondRoleID(int secondRoleID) {
		this.secondRoleID = secondRoleID;
	}
	public int getThirdRoleID() {
		return thirdRoleID;
	}
	public void setThirdRoleID(int thirdRoleID) {
		this.thirdRoleID = thirdRoleID;
	}
		
	public UserRoleBean() {
		
	}
	
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public UserRoleBean (int idUser, int employeeID, int roleID, int secondRoleID, int thirdRoleID, String userRole) {
		this.idUser =idUser;
		this.roleID = roleID;
		this.secondRoleID= secondRoleID;
		this.thirdRoleID=thirdRoleID;
		this.userRole=userRole;
		this.employeeID=employeeID;
	}
	
}
