package com.idi.finance.form;

import org.springframework.web.multipart.MultipartFile;

public class BalanceAssetForm {
	private String[] assetCodes;
	private String[] assetPeriods;

	private String[] changedAssetCodes;
	private String[] changedAssetPeriods;
	private String[] startValues;
	private String[] endValues;

	private boolean first;
	private int numberRecordsOfPage;
	private int pageIndex;
	private int totalPages;
	private int totalRecords;

	private MultipartFile balanceAssetFile;
	private MultipartFile taiKhoanFile;

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

	public String[] getChangedAssetCodes() {
		return changedAssetCodes;
	}

	public void setChangedAssetCodes(String[] changedAssetCodes) {
		this.changedAssetCodes = changedAssetCodes;
	}

	public String[] getChangedAssetPeriods() {
		return changedAssetPeriods;
	}

	public void setChangedAssetPeriods(String[] changedAssetPeriods) {
		this.changedAssetPeriods = changedAssetPeriods;
	}

	public String[] getStartValues() {
		return startValues;
	}

	public void setStartValues(String[] startValues) {
		this.startValues = startValues;
	}

	public String[] getEndValues() {
		return endValues;
	}

	public void setEndValues(String[] endValues) {
		this.endValues = endValues;
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

	public MultipartFile getBalanceAssetFile() {
		return balanceAssetFile;
	}

	public void setBalanceAssetFile(MultipartFile balanceAssetFile) {
		this.balanceAssetFile = balanceAssetFile;
	}

	public MultipartFile getTaiKhoanFile() {
		return taiKhoanFile;
	}

	public void setTaiKhoanFile(MultipartFile taiKhoanFile) {
		this.taiKhoanFile = taiKhoanFile;
	}
}
