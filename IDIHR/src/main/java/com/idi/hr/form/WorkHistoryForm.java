package com.idi.hr.form;

import java.io.Serializable;

public class WorkHistoryForm implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -387100333072628695L;
	
	//Paging
	private int pageIndex;
	private int totalRecords;
	private int numberRecordsOfPage;
	private int totalPages;
	
	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getNumberRecordsOfPage() {
		return numberRecordsOfPage;
	}

	public void setNumberRecordsOfPage(int numberRecordsOfPage) {
		this.numberRecordsOfPage = numberRecordsOfPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}	

}
