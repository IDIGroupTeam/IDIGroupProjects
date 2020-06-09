package com.idi.task.bean;

import java.io.Serializable;

public class TaskCategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8373133429412312296L;

	private String categoryId;	
	private String categoryName;
	private String active;
	private String des;

	public TaskCategory() {
		
	}
	
	public TaskCategory(String categoryId, String categoryName, String active, String des) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.active = active;
		this.des = des;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
}
