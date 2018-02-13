package com.idi.finance.bean.chungtu;

import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public class TaiKhoan_Xn {
	private int maNvkt;
	private LoaiTaiKhoan loaiTaiKhoan;
	private Tien soTien = new Tien();
	private int soDu = -1;
	private String lyDo;

	public int getMaNvkt() {
		return maNvkt;
	}

	public void setMaNvkt(int maNvkt) {
		this.maNvkt = maNvkt;
	}

	public LoaiTaiKhoan getLoaiTaiKhoan() {
		return loaiTaiKhoan;
	}

	public void setLoaiTaiKhoan(LoaiTaiKhoan loaiTaiKhoan) {
		this.loaiTaiKhoan = loaiTaiKhoan;
	}

	public Tien getSoTien() {
		return soTien;
	}

	public void setSoTien(Tien soTien) {
		this.soTien = soTien;
	}

	public int getSoDu() {
		return soDu;
	}

	public void setSoDu(int soDu) {
		this.soDu = soDu;
	}

	public String getLyDo() {
		return lyDo;
	}

	public void setLyDo(String lyDo) {
		this.lyDo = lyDo;
	}

	@Override
	public String toString() {
		String out = loaiTaiKhoan + " - " + soDu + " " + lyDo;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof TaiKhoan_Xn)) {
			return false;
		}

		TaiKhoan_Xn item = (TaiKhoan_Xn) obj;
		try {
			if (maNvkt != item.getMaNvkt())
				return false;

			if (loaiTaiKhoan == null) {
				if (item.getLoaiTaiKhoan() != null)
					return false;
			} else if (item.getLoaiTaiKhoan() == null) {
				return false;
			} else if (!loaiTaiKhoan.equals(item.getLoaiTaiKhoan())) {
				return false;
			}

			if (soDu != item.getSoDu())
				return false;
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
