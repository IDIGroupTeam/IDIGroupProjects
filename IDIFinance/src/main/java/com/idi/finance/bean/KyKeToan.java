package com.idi.finance.bean;

import java.util.Date;
import java.util.List;

import com.idi.finance.bean.soketoan.NghiepVuKeToan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.utils.Utils;

public class KyKeToan {
	public static final int NAN = -1;
	public static final int WEEK = 0;
	public static final int MONTH = 1;
	public static final int QUARTER = 2;
	public static final int YEAR = 3;

	private int loai = MONTH;
	private Date dau = Utils.getStartPeriod(new Date(), MONTH);
	private Date cuoi = Utils.getEndPeriod(new Date(), MONTH);

	private LoaiTaiKhoan loaiTaiKhoan;
	private double soDuDauKy;
	private double soDuCuoiKy;
	private double tongNoPhatSinh;
	private double tongCoPhatSinh;

	private List<NghiepVuKeToan> nghiepVuKeToanDs;

	public KyKeToan() {
		loai = MONTH;
		dau = Utils.getStartPeriod(new Date(), loai);
		cuoi = Utils.getEndPeriod(new Date(), loai);
	}

	public KyKeToan(Date date) {
		dau = Utils.getStartPeriod(date, loai);
		cuoi = Utils.getEndPeriod(date, loai);
	}

	public KyKeToan(Date date, int loai) {
		dau = Utils.getStartPeriod(date, loai);
		cuoi = Utils.getEndPeriod(date, loai);
	}

	public LoaiTaiKhoan getLoaiTaiKhoan() {
		return loaiTaiKhoan;
	}

	public void setLoaiTaiKhoan(LoaiTaiKhoan loaiTaiKhoan) {
		this.loaiTaiKhoan = loaiTaiKhoan;
	}

	public int getLoai() {
		return loai;
	}

	public void setLoai(int loai) {
		this.loai = loai;
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

	public double getSoDuDauKy() {
		return soDuDauKy;
	}

	public void setSoDuDauKy(double soDuDauKy) {
		this.soDuDauKy = soDuDauKy;
	}

	public double getSoDuCuoiKy() {
		return soDuCuoiKy;
	}

	public void setSoDuCuoiKy(double soDuCuoiKy) {
		this.soDuCuoiKy = soDuCuoiKy;
	}

	public double getTongNoPhatSinh() {
		return tongNoPhatSinh;
	}

	public void setTongNoPhatSinh(double tongNoPhatSinh) {
		this.tongNoPhatSinh = tongNoPhatSinh;
	}

	public double getTongCoPhatSinh() {
		return tongCoPhatSinh;
	}

	public void setTongCoPhatSinh(double tongCoPhatSinh) {
		this.tongCoPhatSinh = tongCoPhatSinh;
	}

	public List<NghiepVuKeToan> getNghiepVuKeToanDs() {
		return nghiepVuKeToanDs;
	}

	public void setNghiepVuKeToanDs(List<NghiepVuKeToan> nghiepVuKeToanDs) {
		this.nghiepVuKeToanDs = nghiepVuKeToanDs;
	}

	@Override
	public String toString() {
		String out = loai + "  " + dau + " " + cuoi;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof KyKeToan)) {
			return false;
		}

		KyKeToan item = (KyKeToan) obj;
		try {
			if (loai != item.getLoai()) {
				return false;
			}

			if (dau == null) {
				if (item.getDau() != null)
					return false;
			} else if (item.getDau() == null) {
				return false;
			} else if (!dau.equals(item.getDau())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
