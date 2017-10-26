package com.idi.finance.bean.chungtu;

import org.springframework.format.annotation.NumberFormat;

import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public class TaiKhoan {
	private ChungTu chungTu;
	private LoaiTaiKhoan taiKhoan;
	@NumberFormat(pattern = "#")
	private double soTien;
	private int ghiNo;
	private String lyDo;

	public ChungTu getChungTu() {
		return chungTu;
	}

	public void setChungTu(ChungTu chungTu) {
		this.chungTu = chungTu;
	}

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

	public String getLyDo() {
		return lyDo;
	}

	public void setLyDo(String lyDo) {
		this.lyDo = lyDo;
	}

	@Override
	public String toString() {
		String out = chungTu + " - " + taiKhoan + " - " + ghiNo;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof TaiKhoan)) {
			return false;
		}

		TaiKhoan item = (TaiKhoan) obj;
		try {
			if (chungTu == null) {
				if (item.getChungTu() != null)
					return false;
			} else if (item.getChungTu() == null) {
				return false;
			} else if (!chungTu.equals(item.getChungTu())) {
				return false;
			}

			if (taiKhoan == null) {
				if (item.getTaiKhoan() != null)
					return false;
			} else if (item.getTaiKhoan() == null) {
				return false;
			} else if (!taiKhoan.equals(item.getTaiKhoan())) {
				return false;
			}

			if (ghiNo != item.getGhiNo())
				return false;
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
