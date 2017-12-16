package com.idi.finance.bean;

import java.text.DecimalFormat;

import org.springframework.format.annotation.NumberFormat;

public class LoaiTien {
	public static final String VND = "VND";
	public static final String VND_TEN = "Việt Nam Đồng";
	
	public static final String VANG = "VANG";
	public static final String VANG_TEN = "Vàng bạc, đá quý";

	private String maLt = VND;
	private String tenLt = VND_TEN;
	@NumberFormat(pattern = "#")
	private double muaTM = 1;
	@NumberFormat(pattern = "#")
	private double muaCk = 1;
	@NumberFormat(pattern = "#")
	private double banRa = 1;

	public String getMaLt() {
		return maLt;
	}

	public void setMaLt(String maLt) {
		this.maLt = maLt;
	}

	public String getTenLt() {
		return tenLt;
	}

	public void setTenLt(String tenLt) {
		this.tenLt = tenLt;
	}

	public double getMuaTM() {
		return muaTM;
	}

	public void setMuaTM(double muaTM) {
		this.muaTM = muaTM;
	}

	public double getMuaCk() {
		return muaCk;
	}

	public void setMuaCk(double muaCk) {
		this.muaCk = muaCk;
	}

	public double getBanRa() {
		return banRa;
	}

	public void setBanRa(double banRa) {
		this.banRa = banRa;
	}

	@Override
	public String toString() {
		DecimalFormat decimalFormat = new DecimalFormat("#");
		String out = maLt + " - " + tenLt + " - " + decimalFormat.format(banRa);
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof LoaiTien)) {
			return false;
		}

		LoaiTien item = (LoaiTien) obj;
		try {
			if (maLt == null) {
				if (item.getMaLt() != null)
					return false;
			} else if (item.getMaLt() == null) {
				return false;
			} else if (!maLt.equals(item.getMaLt())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
