package com.idi.finance.bean.diachi;

public class CapDiaChi {
	public static final int MIEN = 1;
	public static final int THANH_PHO = 2;
	public static final int QUAN = 3;
	public static final int PHUONG = 4;

	private int maCapDc;
	private String tenCapDc;
	private String moTa;
	private int capDc;

	public int getMaCapDc() {
		return maCapDc;
	}

	public void setMaCapDc(int maCapDc) {
		this.maCapDc = maCapDc;
	}

	public String getTenCapDc() {
		return tenCapDc;
	}

	public void setTenCapDc(String tenCapDc) {
		this.tenCapDc = tenCapDc;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public int getCapDc() {
		return capDc;
	}

	public void setCapDc(int capDc) {
		this.capDc = capDc;
	}

	@Override
	public String toString() {
		String out = maCapDc + "  " + tenCapDc;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof CapDiaChi)) {
			return false;
		}

		CapDiaChi item = (CapDiaChi) obj;
		try {
			if (maCapDc != item.getCapDc()) {
				return false;
			}

			if (tenCapDc == null) {
				if (item.getTenCapDc() != null)
					return false;
			} else if (item.getTenCapDc() == null) {
				return false;
			} else if (!tenCapDc.trim().equals(item.getTenCapDc().trim())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
