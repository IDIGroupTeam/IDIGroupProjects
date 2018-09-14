package com.idi.finance.bean;

public class CauHinh {
	public static final String CHU_TICH = "CHU_TICH";
	public static final String DIA_CHI = "DIA_CHI";
	public static final String GIAM_DOC = "GIAM_DOC";
	public static final String KE_TOAN_TRUONG = "KE_TOAN_TRUONG";
	public static final String TEN_CONG_TY = "TEN_CONG_TY";
	public static final String THU_KHO = "THU_KHO";
	public static final String THU_QUY = "THU_QUY";

	private String ma;
	private String ten;
	private String giaTri;

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
