package com.idi.finance.bean.hanghoa;

public class DonVi {
	private int maDv;
	private String tenDv;
	private String moTa;

	public int getMaDv() {
		return maDv;
	}

	public void setMaDv(int maDv) {
		this.maDv = maDv;
	}

	public String getTenDv() {
		return tenDv;
	}

	public void setTenDv(String tenDv) {
		this.tenDv = tenDv;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	@Override
	public String toString() {
		String out = maDv + "  " + tenDv;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof DonVi)) {
			return false;
		}

		DonVi item = (DonVi) obj;
		try {
			if (maDv != item.getMaDv()) {
				return false;
			}

			if (tenDv == null) {
				if (item.getTenDv() != null)
					return false;
			} else if (item.getTenDv() == null) {
				return false;
			} else if (!tenDv.trim().equals(item.getTenDv().trim())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
