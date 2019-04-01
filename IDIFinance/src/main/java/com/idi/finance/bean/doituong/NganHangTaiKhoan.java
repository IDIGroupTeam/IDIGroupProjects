package com.idi.finance.bean.doituong;

public class NganHangTaiKhoan {
	private int maTk;
	private String soTk;
	private NganHang nganHang;
	private String chiNhanh;
	private String diaChiCn;
	private String chuTk;
	private String moTa;

	public int getMaTk() {
		return maTk;
	}

	public void setMaTk(int maTk) {
		this.maTk = maTk;
	}

	public String getSoTk() {
		return soTk;
	}

	public void setSoTk(String soTk) {
		this.soTk = soTk;
	}

	public NganHang getNganHang() {
		return nganHang;
	}

	public void setNganHang(NganHang nganHang) {
		this.nganHang = nganHang;
	}

	public String getChiNhanh() {
		return chiNhanh;
	}

	public void setChiNhanh(String chiNhanh) {
		this.chiNhanh = chiNhanh;
	}

	public String getDiaChiCn() {
		return diaChiCn;
	}

	public void setDiaChiCn(String diaChiCn) {
		this.diaChiCn = diaChiCn;
	}

	public String getChuTk() {
		return chuTk;
	}

	public void setChuTk(String chuTk) {
		this.chuTk = chuTk;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	@Override
	public String toString() {
		String out = maTk + "  " + soTk + " " + nganHang;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof NganHangTaiKhoan)) {
			return false;
		}

		NganHangTaiKhoan item = (NganHangTaiKhoan) obj;
		try {
			if (maTk != item.getMaTk()) {
				return false;
			}

			if (soTk == null) {
				if (item.getSoTk() != null)
					return false;
			} else if (item.getSoTk() == null) {
				return false;
			} else if (!soTk.trim().equals(item.getSoTk().trim())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
