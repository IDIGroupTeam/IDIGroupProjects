package com.idi.finance.bean.chungtu;

import org.springframework.format.annotation.NumberFormat;

import com.idi.finance.bean.LoaiTien;

public class Tien {
	private LoaiTien loaiTien = new LoaiTien();
	@NumberFormat(pattern = "#")
	private double soTien;
	@NumberFormat(pattern = "#")
	private double giaTri;

	public LoaiTien getLoaiTien() {
		return loaiTien;
	}

	public void setLoaiTien(LoaiTien loaiTien) {
		this.loaiTien = loaiTien;
	}

	public double getSoTien() {
		return soTien;
	}

	public void setSoTien(double soTien) {
		this.soTien = soTien;
	}

	public double getGiaTri() {
		return giaTri;
	}

	public void setGiaTri(double giaTri) {
		this.giaTri = giaTri;
	}

	@Override
	public String toString() {
		return soTien * loaiTien.getBanRa() + " " + loaiTien.getMaLt();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof Tien)) {
			return false;
		}

		Tien item = (Tien) obj;
		try {
			if (loaiTien == null) {
				if (item.getLoaiTien() != null)
					return false;
			} else if (item.getLoaiTien() == null) {
				return false;
			} else if (!loaiTien.equals(item.getLoaiTien())) {
				return false;
			}

			if (soTien != item.getSoTien())
				return false;
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
