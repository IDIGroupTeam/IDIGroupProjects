package com.idi.finance.bean.chungtu;

public class DoiTuong {
	private int maDt;
	private int loaiDt;
	private String tenDt;
	private String diaChi;
	private String sdt;
	private String email;
	private String maThue;
	private String nguoiNop;

	public int getMaDt() {
		return maDt;
	}

	public void setMaDt(int maDt) {
		this.maDt = maDt;
	}

	public int getLoaiDt() {
		return loaiDt;
	}

	public void setLoaiDt(int loaiDt) {
		this.loaiDt = loaiDt;
	}

	public String getTenDt() {
		return tenDt;
	}

	public void setTenDt(String tenDt) {
		this.tenDt = tenDt;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMaThue() {
		return maThue;
	}

	public void setMaThue(String maThue) {
		this.maThue = maThue;
	}

	@Override
	public String toString() {
		String out = maDt + "  " + tenDt;
		return out;
	}

	public String getNguoiNop() {
		return nguoiNop;
	}

	public void setNguoiNop(String nguoiNop) {
		this.nguoiNop = nguoiNop;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof DoiTuong)) {
			return false;
		}

		DoiTuong item = (DoiTuong) obj;
		try {
			if (maDt != item.getMaDt())
				return false;

			if (loaiDt != item.getLoaiDt())
				return false;

			if (tenDt == null) {
				if (item.getTenDt() != null)
					return false;
			} else if (item.getTenDt() == null) {
				return false;
			} else if (!tenDt.trim().equals(item.getTenDt().trim())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
