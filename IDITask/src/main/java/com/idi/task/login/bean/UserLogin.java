package com.idi.task.login.bean;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class UserLogin implements Serializable  {
	
	private static final long serialVersionUID = -5701415677357601191L;
	private int userID ;
	private String username;
	private int enable;
	private String password;
	private int roleID;
	private int secondRoleID;
	private int thirdRoleID;
	private String user_Role;
	private String fullName;
	private int id;
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UserLogin(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities,int userID,int roleID
			) {
	
		this.userID=userID;
		this.roleID=roleID;
		this.password=password;
		
	}
	
	public UserLogin() {
		
	}
	/**
	 * 
	 */

	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getEnable() {
		return enable;
	}
	public void setEnable(int enable) {
		this.enable = enable;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getUser_Role() {
		return user_Role;
	}
	public void setUser_Role(String user_Role) {
		this.user_Role = user_Role;
	}

}
