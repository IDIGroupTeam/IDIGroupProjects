package com.idi.finance.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.idi.finance.bean.cdkt.KyKeToanCon;

public class TkSoKeToanForm {
	private int loaiKy = KyKeToanCon.MONTH;
	private String taiKhoan;
	private int maDt;
	private Date dau;
	private Date cuoi;
	private List<String> loaiCts;
	private int doSau = 2;

	public int getLoaiKy() {
		return loaiKy;
	}

	public void setLoaiKy(int loaiKy) {
		this.loaiKy = loaiKy;
	}

	public String getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(String taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public int getMaDt() {
		return maDt;
	}

	public void setMaDt(int maDt) {
		this.maDt = maDt;
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

	public int getDoSau() {
		return doSau;
	}

	public void setDoSau(int doSau) {
		this.doSau = doSau;
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
