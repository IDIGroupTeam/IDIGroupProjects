package com.idi.hr.form;

import java.io.Serializable;

public class EmployeeForm implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -387100333072628615L;
	
	private int quarter;
	private String searchValue;

	public int getQuarter() {
		return quarter;
	}

	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}	

}
