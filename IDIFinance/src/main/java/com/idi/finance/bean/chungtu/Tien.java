package com.idi.finance.bean.chungtu;

import org.springframework.format.annotation.NumberFormat;

import com.idi.finance.bean.LoaiTien;

public class Tien {
	private LoaiTien tien;
	@NumberFormat(pattern = "#")
	private double soTien;
	@NumberFormat(pattern = "#")
	private double giaTri;

	public LoaiTien getTien() {
		return tien;
	}

	public void setTien(LoaiTien tien) {
		this.tien = tien;
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
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
}
