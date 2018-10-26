package com.idi.task.login.bean;

public class RoleBean {
	
	private int roleID;
	private String roleName;
	
	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public RoleBean () {
		
	}
	
	public RoleBean (int roleID, String roleName) {
		this.roleID = roleID;
		this.roleName=roleName;
	}
	
}
