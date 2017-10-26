package com.idi.finance.bean;

import java.text.DecimalFormat;

import org.springframework.format.annotation.NumberFormat;

public class LoaiTien {
	private String maLt;
	private String tenLt;
	@NumberFormat(pattern = "#")
	private double muaTM;
	@NumberFormat(pattern = "#")
	private double muaCk;
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
}
