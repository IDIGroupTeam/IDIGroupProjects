package com.idi.finance.bean.hanghoa;

import java.util.List;

import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public class KhoHang {
	public static final int MA_KHO_MAC_DINH = 0;

	private int maKho;
	private String kyHieuKho;
	private String tenKho;
	private String diaChi;
	private LoaiTaiKhoan taiKhoan;
	private String moTa;
	private List<HangHoa> hangHoaDs;

	private int chieu = ChungTu.MUA;

	private boolean xoa = true;

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

	public List<HangHoa> getHangHoaDs() {
		return hangHoaDs;
	}

	public void setHangHoaDs(List<HangHoa> hangHoaDs) {
		this.hangHoaDs = hangHoaDs;
	}

	public int getChieu() {
		return chieu;
	}

	public void setChieu(int chieu) {
		this.chieu = chieu;
	}

	public boolean isXoa() {
		return xoa;
	}

	public void setXoa(boolean xoa) {
		this.xoa = xoa;
	}

	@Override
	public String toString() {
		String out = maKho + "  " + kyHieuKho + " " + tenKho + " " + chieu;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof KhoHang)) {
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
