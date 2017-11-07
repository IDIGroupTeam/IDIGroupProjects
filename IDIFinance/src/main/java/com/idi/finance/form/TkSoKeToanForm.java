package com.idi.finance.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TkSoKeToanForm {
	private String taiKhoan;
	private Date dau;
	private Date cuoi;
	private List<String> loaiCts;

	public String getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(String taiKhoan) {
		this.taiKhoan = taiKhoan;
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

	public List<String> getLoaiCts() {
		return loaiCts;
	}

	public void setLoaiCts(List<String> loaiCts) {
		this.loaiCts = loaiCts;
	}

	public void themLoaiCt(String loaiCt) {
		if (loaiCt == null)
			return;

		loaiCt = loaiCt.trim();
		if (loaiCts == null)
			loaiCts = new ArrayList<String>();

		if (!loaiCts.contains(loaiCt)) {
			loaiCts.add(loaiCt);
		}
	}
}
