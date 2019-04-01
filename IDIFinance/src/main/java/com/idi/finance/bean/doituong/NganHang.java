package com.idi.finance.bean.doituong;

public class NganHang {
	private int maNh;
	private String tenVt;
	private String tenDd;
	private String tenTa;
	private String diaChi;
	private String bieuTuong;
	private String moTa;

	public int getMaNh() {
		return maNh;
	}

	public void setMaNh(int maNh) {
		this.maNh = maNh;
	}

	public String getTenVt() {
		return tenVt;
	}

	public void setTenVt(String tenVt) {
		this.tenVt = tenVt;
	}

	public String getTenDd() {
		return tenDd;
	}

	public void setTenDd(String tenDd) {
		this.tenDd = tenDd;
	}

	public String getTenTa() {
		return tenTa;
	}

	public void setTenTa(String tenTa) {
		this.tenTa = tenTa;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getBieuTuong() {
		return bieuTuong;
	}

	public void setBieuTuong(String bieuTuong) {
		this.bieuTuong = bieuTuong;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	@Override
	public String toString() {
		String out = maNh + "  " + tenVt;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof NganHang)) {
			return false;
		}

		NganHang item = (NganHang) obj;
		try {
			if (maNh != item.getMaNh()) {
				return false;
			}

			if (tenVt == null) {
				if (item.getTenVt() != null)
					return false;
			} else if (item.getTenVt() == null) {
				return false;
			} else if (!tenVt.trim().equals(item.getTenVt().trim())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
