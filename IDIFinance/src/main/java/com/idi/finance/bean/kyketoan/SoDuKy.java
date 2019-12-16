package com.idi.finance.bean.kyketoan;

import org.apache.log4j.Logger;

import com.idi.finance.bean.LoaiTien;
import com.idi.finance.bean.doituong.DoiTuong;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.hanghoa.KhoHang;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public class SoDuKy {
	private static final Logger logger = Logger.getLogger(SoDuKy.class);

	private KyKeToan kyKeToan;
	private LoaiTaiKhoan loaiTaiKhoan;
	private DoiTuong doiTuong = new DoiTuong();
	private HangHoa hangHoa;
	private KhoHang khoHang;

	private double noDauKy;
	private double noDauKyNt;
	private double coDauKy;
	private double coDauKyNt;
	private double noCuoiKy;
	private double noCuoiKyNt;
	private double coCuoiKy;
	private double coCuoiKyNt;
	private LoaiTien loaiTien = new LoaiTien();

	private double soLuong;

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

	public HangHoa getHangHoa() {
		return hangHoa;
	}

	public void setHangHoa(HangHoa hangHoa) {
		this.hangHoa = hangHoa;
	}

	public KhoHang getKhoHang() {
		return khoHang;
	}

	public void setKhoHang(KhoHang khoHang) {
		this.khoHang = khoHang;
	}

	public double getNoDauKy() {
		return noDauKy;
	}

	public void setNoDauKy(double noDauKy) {
		this.noDauKy = noDauKy;
	}

	public double getNoDauKyNt() {
		return noDauKyNt;
	}

	public void setNoDauKyNt(double noDauKyNt) {
		this.noDauKyNt = noDauKyNt;
	}

	public double getCoDauKy() {
		return coDauKy;
	}

	public void setCoDauKy(double coDauKy) {
		this.coDauKy = coDauKy;
	}

	public double getCoDauKyNt() {
		return coDauKyNt;
	}

	public void setCoDauKyNt(double coDauKyNt) {
		this.coDauKyNt = coDauKyNt;
	}

	public double getNoCuoiKy() {
		return noCuoiKy;
	}

	public void setNoCuoiKy(double noCuoiKy) {
		this.noCuoiKy = noCuoiKy;
	}

	public double getNoCuoiKyNt() {
		return noCuoiKyNt;
	}

	public void setNoCuoiKyNt(double noCuoiKyNt) {
		this.noCuoiKyNt = noCuoiKyNt;
	}

	public double getCoCuoiKy() {
		return coCuoiKy;
	}

	public void setCoCuoiKy(double coCuoiKy) {
		this.coCuoiKy = coCuoiKy;
	}

	public double getCoCuoiKyNt() {
		return coCuoiKyNt;
	}

	public void setCoCuoiKyNt(double coCuoiKyNt) {
		this.coCuoiKyNt = coCuoiKyNt;
	}

	public LoaiTien getLoaiTien() {
		return loaiTien;
	}

	public void setLoaiTien(LoaiTien loaiTien) {
		this.loaiTien = loaiTien;
	}

	public double getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(double soLuong) {
		this.soLuong = soLuong;
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

			if (this.hangHoa == null) {
				this.hangHoa = soDuKy.getHangHoa();
			}

			if (this.khoHang == null) {
				this.khoHang = soDuKy.getKhoHang();
			}

			this.noDauKy += soDuKy.getNoDauKy();
			this.noDauKyNt += soDuKy.getNoDauKyNt();
			this.coDauKy += soDuKy.getCoDauKy();
			this.coDauKyNt += soDuKy.getCoDauKyNt();
			this.noCuoiKy += soDuKy.getNoCuoiKy();
			this.noCuoiKyNt += soDuKy.getNoCuoiKyNt();
			this.coCuoiKy += soDuKy.getCoCuoiKy();
			this.coCuoiKyNt += soDuKy.getCoCuoiKyNt();
			this.soLuong += soDuKy.getSoLuong();
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

			if (hangHoa == null) {
				if (item.getHangHoa() != null)
					return false;
			} else if (item.getHangHoa() == null) {
				return false;
			} else if (!hangHoa.equals(item.getHangHoa())) {
				return false;
			}

			if (khoHang == null) {
				if (item.getKhoHang() != null)
					return false;
			} else if (item.getKhoHang() == null) {
				return false;
			} else if (!khoHang.equals(item.getKhoHang())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		String out = kyKeToan + " " + doiTuong + " " + loaiTaiKhoan + " " + hangHoa + " " + khoHang + " " + noDauKy
				+ " " + noDauKyNt + " " + coDauKy + " " + coDauKyNt + " - " + loaiTien + " - " + soLuong;
		return out;
	}
}
