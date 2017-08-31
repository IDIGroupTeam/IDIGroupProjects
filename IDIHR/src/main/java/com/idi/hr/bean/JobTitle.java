package com.idi.hr.bean;

import java.io.Serializable;

public class JobTitle implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5619522829808151745L;
	
	private String titleId;
	private String titleName;
	private String description;
	
	public JobTitle() {
		
	}

	public JobTitle(String titleId, String titleName, String description) {
		this.titleId = titleId;
		this.titleName = titleName;
		this.description = description;		
	}

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
