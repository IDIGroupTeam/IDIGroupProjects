package com.idi.finance.bean.chungtu;

import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public class TaiKhoan {
	private LoaiTaiKhoan taiKhoan;
	private double soTien;
	private int ghiNo;

	public LoaiTaiKhoan getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(LoaiTaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public double getSoTien() {
		return soTien;
	}

	public void setSoTien(double soTien) {
		this.soTien = soTien;
	}

	public int getGhiNo() {
		return ghiNo;
	}

	public void setGhiNo(int ghiNo) {
		this.ghiNo = ghiNo;
	}

	@Override
	public String toString() {
		String out = taiKhoan + " - " + soTien + " - " + ghiNo;
		return out;
	}
}
