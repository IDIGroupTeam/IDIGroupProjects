package com.idi.finance.bean.hanghoa;

import java.util.List;

import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public class KhoHang {
	private int maKho;
	private String kyHieuKho;
	private String tenKho;
	private String diaChi;
	private LoaiTaiKhoan taiKhoan;
	private String moTa;

	public int getMaKho() {
		return maKho;
	}

	public void setMaKho(int maKho) {
		this.maKho = maKho;
	}

	public String getKyHieuKho() {
		return kyHieuKho;
	}

	public void setKyHieuKho(String kyHieuKho) {
		this.kyHieuKho = kyHieuKho;
	}

	public String getTenKho() {
		return tenKho;
	}

	public void setTenKho(String tenKho) {
		this.tenKho = tenKho;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public LoaiTaiKhoan getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(LoaiTaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	@Override
	public String toString() {
		String out = maKho + "  " + tenKho;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof HangHoa)) {
			return false;
		}

		KhoHang item = (KhoHang) obj;
		try {
			if (maKho != item.getMaKho()) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
