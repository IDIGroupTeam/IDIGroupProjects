package com.idi.task.login.bean;

public class UserBean {
	private int userID ;
	private String username;
	private int enabled;
	private String password;
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public UserBean () {
		
	}
	public UserBean (int id, String userName, String password, int userID, int enabled) {
		this.id=id;
		this.username=userName;
		this.password=password;
		this.userID=userID;
		this.enabled=enabled;
	}
	
}
