package com.idi.finance.bean.hanghoa;

import com.idi.finance.bean.chungtu.Tien;

public class HangHoa {
	private int maHh;
	private String tenHh;
	private DonVi donVi;
	private NhomHangHoa nhomHh;
	private Tien giaBan;
	private Tien giaMua;

	public int getMaHh() {
		return maHh;
	}

	public void setMaHh(int maHh) {
		this.maHh = maHh;
	}

	public String getTenHh() {
		return tenHh;
	}

	public void setTenHh(String tenHh) {
		this.tenHh = tenHh;
	}

	public DonVi getDonVi() {
		return donVi;
	}

	public void setDonVi(DonVi donVi) {
		this.donVi = donVi;
	}

	public NhomHangHoa getNhomHh() {
		return nhomHh;
	}

	public void setNhomHh(NhomHangHoa nhomHh) {
		this.nhomHh = nhomHh;
	}

	public Tien getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(Tien giaBan) {
		this.giaBan = giaBan;
	}

	public Tien getGiaMua() {
		return giaMua;
	}

	public void setGiaMua(Tien giaMua) {
		this.giaMua = giaMua;
	}

	@Override
	public String toString() {
		String out = maHh + "  " + tenHh;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof HangHoa)) {
			return false;
		}

		HangHoa item = (HangHoa) obj;
		try {
			if (maHh != item.getMaHh()) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
