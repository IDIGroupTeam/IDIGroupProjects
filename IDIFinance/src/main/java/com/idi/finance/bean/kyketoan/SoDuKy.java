package com.idi.finance.bean.kyketoan;

import com.idi.finance.bean.chungtu.DoiTuong;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public class SoDuKy {
	private KyKeToan kyKeToan;
	private LoaiTaiKhoan loaiTaiKhoan;
	private DoiTuong doiTuong = new DoiTuong();
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

	public DoiTuong getDoiTuong() {
		return doiTuong;
	}

	public void setDoiTuong(DoiTuong doiTuong) {
		this.doiTuong = doiTuong;
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

	public void tron(SoDuKy soDuKy) {
		if (soDuKy != null) {
			if (this.kyKeToan == null) {
				this.kyKeToan = soDuKy.getKyKeToan();
			}

			if (this.loaiTaiKhoan == null) {
				this.loaiTaiKhoan = soDuKy.getLoaiTaiKhoan();
			}

			if (this.doiTuong == null) {
				this.doiTuong = soDuKy.getDoiTuong();
			}

			if (this.noDauKy == 0) {
				this.noDauKy = soDuKy.getNoDauKy();
			}

			if (this.coDauKy == 0) {
				this.coDauKy = soDuKy.getCoDauKy();
			}

			if (this.noCuoiKy == 0) {
				this.noCuoiKy = soDuKy.getNoCuoiKy();
			}

			if (this.coCuoiKy == 0) {
				this.coCuoiKy = soDuKy.getCoCuoiKy();
			}
		}
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

			if (doiTuong == null) {
				if (item.getDoiTuong() != null)
					return false;
			} else if (item.getDoiTuong() == null) {
				return false;
			} else if (!doiTuong.equals(item.getDoiTuong())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		String out = kyKeToan + " " + doiTuong + " " + loaiTaiKhoan + " " + noDauKy + " " + coDauKy;
		return out;
	}
}
