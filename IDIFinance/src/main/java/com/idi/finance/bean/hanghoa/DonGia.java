package com.idi.finance.bean.hanghoa;

import java.util.Date;

import com.idi.finance.bean.chungtu.Tien;

public class DonGia {
	private int maGia;
	private Tien donGia;
	private Date ngayTao;

	public int getMaGia() {
		return maGia;
	}

	public void setMaGia(int maGia) {
		this.maGia = maGia;
	}

	public Tien getDonGia() {
		return donGia;
	}

	public void setDonGia(Tien donGia) {
		this.donGia = donGia;
	}

	public Date getNgayTao() {
		return ngayTao;
	}

	public void setNgayTao(Date ngayTao) {
		this.ngayTao = ngayTao;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof DonVi)) {
			return false;
		}

		DonGia item = (DonGia) obj;
		try {
			if (maGia != item.getMaGia()) {
				return false;
			}

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		String out = maGia + " - " + donGia;
		return out;
	}
}
