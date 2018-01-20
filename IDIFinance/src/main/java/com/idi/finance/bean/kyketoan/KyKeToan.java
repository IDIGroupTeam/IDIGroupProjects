package com.idi.finance.bean.kyketoan;

import java.text.SimpleDateFormat;
import java.util.Date;

public class KyKeToan {
	private int maKykt;
	private String tenKykt;
	private Date batDau;
	private Date ketThuc;
	private int trangThai;

	public int getMaKykt() {
		return maKykt;
	}

	public void setMaKykt(int maKykt) {
		this.maKykt = maKykt;
	}

	public String getTenKykt() {
		return tenKykt;
	}

	public void setTenKykt(String tenKykt) {
		this.tenKykt = tenKykt;
	}

	public Date getBatDau() {
		return batDau;
	}

	public void setBatDau(Date batDau) {
		this.batDau = batDau;
	}

	public Date getKetThuc() {
		return ketThuc;
	}

	public void setKetThuc(Date ketThuc) {
		this.ketThuc = ketThuc;
	}

	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");

		String out = maKykt + " " + tenKykt + "  " + sdf.format(batDau) + " " + sdf.format(ketThuc) + " " + trangThai;
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
			if (maKykt != item.getMaKykt()) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
