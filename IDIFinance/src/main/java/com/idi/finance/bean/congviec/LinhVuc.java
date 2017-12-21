package com.idi.finance.bean.congviec;

public class LinhVuc {
	private int maLv;
	private String tenLv;
	private String moTa;
	private LinhVuc chaLv;

	public int getMaLv() {
		return maLv;
	}

	public void setMaLv(int maLv) {
		this.maLv = maLv;
	}

	public String getTenLv() {
		return tenLv;
	}

	public void setTenLv(String tenLv) {
		this.tenLv = tenLv;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public LinhVuc getChaLv() {
		return chaLv;
	}

	public void setChaLv(LinhVuc chaLv) {
		this.chaLv = chaLv;
	}

	@Override
	public String toString() {
		String out = maLv + " " + tenLv;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
}
