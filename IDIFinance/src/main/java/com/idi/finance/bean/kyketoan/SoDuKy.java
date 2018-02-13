package com.idi.finance.bean.kyketoan;

import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public class SoDuKy {
	private KyKeToan kyKeToan;
	private LoaiTaiKhoan loaiTaiKhoan;
	private double noDauKy;
	private double coDauKy;
	private double noCuoiKy;
	private double coCuoiKy;

	public KyKeToan getKyKeToan() {
		return kyKeToan;
	}

	public void setKyKeToan(KyKeToan kyKeToan) {
		this.kyKeToan = kyKeToan;
	}

	public LoaiTaiKhoan getLoaiTaiKhoan() {
		return loaiTaiKhoan;
	}

	public void setLoaiTaiKhoan(LoaiTaiKhoan loaiTaiKhoan) {
		this.loaiTaiKhoan = loaiTaiKhoan;
	}

	public double getNoDauKy() {
		return noDauKy;
	}

	public void setNoDauKy(double noDauKy) {
		this.noDauKy = noDauKy;
	}

	public double getCoDauKy() {
		return coDauKy;
	}

	public void setCoDauKy(double coDauKy) {
		this.coDauKy = coDauKy;
	}

	public double getNoCuoiKy() {
		return noCuoiKy;
	}

	public void setNoCuoiKy(double noCuoiKy) {
		this.noCuoiKy = noCuoiKy;
	}

	public double getCoCuoiKy() {
		return coCuoiKy;
	}

	public void setCoCuoiKy(double coCuoiKy) {
		this.coCuoiKy = coCuoiKy;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof SoDuKy)) {
			return false;
		}

		SoDuKy item = (SoDuKy) obj;
		try {
			if (kyKeToan == null) {
				if (item.getKyKeToan() != null)
					return false;
			} else if (item.getKyKeToan() == null) {
				return false;
			} else if (!kyKeToan.equals(item.getKyKeToan())) {
				return false;
			}

			if (loaiTaiKhoan == null) {
				if (item.getLoaiTaiKhoan() != null)
					return false;
			} else if (item.getLoaiTaiKhoan() == null) {
				return false;
			} else if (!loaiTaiKhoan.equals(item.getLoaiTaiKhoan())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		String out = kyKeToan + " " + loaiTaiKhoan;
		return out;
	}
}
