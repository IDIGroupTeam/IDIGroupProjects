package com.idi.finance.form;

public class BalanceAssetForm {
	private String[] assetCodes;
	private String[] assetPeriods;

	private boolean first;
	private int numberRecordsOfPage;
	private int pageIndex;
	private int totalPages;
	private int totalRecords;

	public String[] getAssetCodes() {
		return assetCodes;
	}

	public void setAssetCodes(String[] assetCodes) {
		this.assetCodes = assetCodes;
	}

	public String[] getAssetPeriods() {
		return assetPeriods;
	}

	public void setAssetPeriods(String[] assetPeriods) {
		this.assetPeriods = assetPeriods;
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
}
