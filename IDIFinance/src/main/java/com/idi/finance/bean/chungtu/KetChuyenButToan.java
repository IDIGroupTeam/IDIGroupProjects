package com.idi.finance.bean.chungtu;

import org.apache.log4j.Logger;

import com.idi.finance.bean.taikhoan.TaiKhoan;

public class KetChuyenButToan {
	private static final Logger logger = Logger.getLogger(KetChuyenButToan.class);

	public static final int KCBT_DAU_KY = 0;
	public static final int KCBT_CUOI_KY = 1;

	private int maKc;
	private String tenKc;
	private String moTa;
	private TaiKhoan taiKhoanNo;
	private TaiKhoan taiKhoanCo;
	private String congThuc;
	private int thuTu;
	private int loaiKc = KCBT_CUOI_KY;
	private boolean chon = true;

	public int getMaKc() {
		return maKc;
	}

	public void setMaKc(int maKc) {
		this.maKc = maKc;
	}

	public String getTenKc() {
		return tenKc;
	}

	public void setTenKc(String tenKc) {
		this.tenKc = tenKc;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public TaiKhoan getTaiKhoanNo() {
		return taiKhoanNo;
	}

	public void setTaiKhoanNo(TaiKhoan taiKhoanNo) {
		this.taiKhoanNo = taiKhoanNo;
	}

	public TaiKhoan getTaiKhoanCo() {
		return taiKhoanCo;
	}

	public void setTaiKhoanCo(TaiKhoan taiKhoanCo) {
		this.taiKhoanCo = taiKhoanCo;
	}

	public String getCongThuc() {
		return congThuc;
	}

	public void setCongThuc(String congThuc) {
		this.congThuc = congThuc;
	}

	public int getThuTu() {
		return thuTu;
	}

	public void setThuTu(int thuTu) {
		this.thuTu = thuTu;
	}

	public int getLoaiKc() {
		return loaiKc;
	}

	public void setLoaiKc(int loaiKc) {
		this.loaiKc = loaiKc;
	}

	public boolean isChon() {
		return chon;
	}

	public void setChon(boolean chon) {
		this.chon = chon;
	}

	public void tron(KetChuyenButToan ketChuyenButToan) {
		if (ketChuyenButToan == null) {
			return;
		}

		if (this.equals(ketChuyenButToan)) {
			if (this.getTaiKhoanNo() == null) {
				this.setTaiKhoanNo(ketChuyenButToan.getTaiKhoanNo());
			}
			if (this.getTaiKhoanCo() == null) {
				this.setTaiKhoanCo(ketChuyenButToan.getTaiKhoanCo());
			}
		}
	}

	@Override
	public String toString() {
		String out = maKc + " - " + tenKc + " " + taiKhoanNo + " " + taiKhoanCo;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof KetChuyenButToan)) {
			return false;
		}

		KetChuyenButToan item = (KetChuyenButToan) obj;
		if (maKc != item.getMaKc()) {
			return false;
		}

		return true;
	}
}
