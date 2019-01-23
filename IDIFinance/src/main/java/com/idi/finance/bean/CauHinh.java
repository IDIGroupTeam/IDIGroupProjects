package com.idi.finance.bean;

public class CauHinh {
	// File này quản lý nhóm cấu hình đọc từ csdl
	// Người dùng được phép thay đổi giá trị
	public static final int NHOM_MAC_DINH = 0;
	public static final int NHOM_CONG_TY = 1;
	public static final int NHOM_TK = 2;
	public static final int NHOM_KHAC = 3;

	private String ma;
	private String ten;
	private String giaTri;
	private int nhom = NHOM_MAC_DINH;

	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		if (ma != null) {
			ma = ma.trim();
		}
		this.ma = ma;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		if (ten != null) {
			ten = ten.trim();
		}
		this.ten = ten;
	}

	public String getGiaTri() {
		return giaTri;
	}

	public void setGiaTri(String giaTri) {
		if (giaTri != null) {
			giaTri = giaTri.trim();
		}
		this.giaTri = giaTri;
	}

	public int getNhom() {
		return nhom;
	}

	public void setNhom(int nhom) {
		this.nhom = nhom;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof CauHinh)) {
			return false;
		}

		CauHinh item = (CauHinh) obj;
		if (ma == null) {
			if (item.getMa() != null)
				return false;
		} else if (item.getMa() == null) {
			return false;
		} else if (!ma.trim().equals(item.getMa().trim())) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		String out = ma + "  " + ten + " " + giaTri;
		return out;
	}
}
