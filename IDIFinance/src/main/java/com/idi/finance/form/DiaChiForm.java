package com.idi.finance.form;

import org.springframework.web.multipart.MultipartFile;

public class DiaChiForm {
	private String mien;
	private String thanhPho;
	private String quan;

	private boolean first;
	private int numberRecordsOfPage;
	private int pageIndex;
	private int totalPages;
	private int totalRecords;

	private MultipartFile vungFile;

	public String getMien() {
		return mien;
	}

	public void setMien(String mien) {
		this.mien = mien;
	}

	public String getThanhPho() {
		return thanhPho;
	}

	public void setThanhPho(String thanhPho) {
		this.thanhPho = thanhPho;
	}

	public String getQuan() {
		return quan;
	}

	public void setQuan(String quan) {
		this.quan = quan;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public int getNumberRecordsOfPage() {
		return numberRecordsOfPage;
	}

	public void setNumberRecordsOfPage(int numberRecordsOfPage) {
		this.numberRecordsOfPage = numberRecordsOfPage;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public MultipartFile getVungFile() {
		return vungFile;
	}

	public void setVungFile(MultipartFile vungFile) {
		this.vungFile = vungFile;
	}
}