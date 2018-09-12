package com.idi.finance.bean.doitac;

public class NhaCungCap {
	private int maNcc;
	private String khNcc;
	private String tenNcc;
	private String diaChi;
	private String maThue;
	private String email;
	private String sdt;
	private String webSite;

	public int getMaNcc() {
		return maNcc;
	}

	public void setMaNcc(int maNcc) {
		this.maNcc = maNcc;
	}

	public String getKhNcc() {
		return khNcc;
	}

	public void setKhNcc(String khNcc) {
		this.khNcc = khNcc;
	}

	public String getTenNcc() {
		return tenNcc;
	}

	public void setTenNcc(String tenNcc) {
		this.tenNcc = tenNcc;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getMaThue() {
		return maThue;
	}

	public void setMaThue(String maThue) {
		this.maThue = maThue;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	@Override
	public String toString() {
		String out = maNcc + "  " + tenNcc;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof NhaCungCap)) {
			return false;
		}

		NhaCungCap item = (NhaCungCap) obj;
		try {
			if (maNcc != item.getMaNcc()) {
				return false;
			}

			if (tenNcc == null) {
				if (item.getTenNcc() != null)
					return false;
			} else if (item.getTenNcc() == null) {
				return false;
			} else if (!tenNcc.trim().equals(item.getTenNcc().trim())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
