package com.idi.finance.form;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.idi.finance.bean.bctc.KyKeToanCon;
import com.idi.finance.bean.chungtu.TaiKhoan;
import com.idi.finance.bean.kyketoan.KyKeToan;

public class BalanceAssetForm {
	private KyKeToan kyKeToan;

	private String[] assetCodes;
	private String[] assetPeriods;
	private int periodType = KyKeToanCon.YEAR;

	private boolean first;
	private int numberRecordsOfPage;
	private int pageIndex;
	private int totalPages;
	private int totalRecords;

	private MultipartFile balanceAssetFile;
	private MultipartFile taiKhoanFile;

	private int ky = 1;
	private Date dau;
	private Date cuoi;

	private List<TaiKhoan> taiKhoanDs;

	public KyKeToan getKyKeToan() {
		return kyKeToan;
	}

	public void setKyKeToan(KyKeToan kyKeToan) {
		this.kyKeToan = kyKeToan;
	}

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

	public int getPeriodType() {
		return periodType;
	}

	public void setPeriodType(int periodType) {
		this.periodType = periodType;
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

	public int getKy() {
		return ky;
	}

	public void setKy(int ky) {
		this.ky = ky;
	}

	public Date getDau() {
		return dau;
	}

	public void setDau(Date dau) {
		this.dau = dau;
	}

	public Date getCuoi() {
		return cuoi;
	}

	public void setCuoi(Date cuoi) {
		this.cuoi = cuoi;
	}

	public List<TaiKhoan> getTaiKhoanDs() {
		return taiKhoanDs;
	}

	public void setTaiKhoanDs(List<TaiKhoan> taiKhoanDs) {
		this.taiKhoanDs = taiKhoanDs;
	}
}
