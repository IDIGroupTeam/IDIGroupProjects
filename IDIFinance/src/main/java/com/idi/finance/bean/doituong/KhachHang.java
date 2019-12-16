package com.idi.finance.bean.doituong;

public class KhachHang {
	private int maKh;
	private String khKh;
	private String tenKh;
	private String diaChi;
	private String maThue;
	private String email;
	private String sdt;
	private String webSite;

	private boolean xoa = true;

	public int getMaKh() {
		return maKh;
	}

	public void setMaKh(int maKh) {
		this.maKh = maKh;
	}

	public String getKhKh() {
		return khKh;
	}

	public void setKhKh(String khKh) {
		this.khKh = khKh;
	}

	public String getTenKh() {
		return tenKh;
	}

	public void setTenKh(String tenKh) {
		this.tenKh = tenKh;
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

	public boolean isXoa() {
		return xoa;
	}

	public void setXoa(boolean xoa) {
		this.xoa = xoa;
	}

	@Override
	public String toString() {
		String out = maKh + "  " + tenKh;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof KhachHang)) {
			return false;
		}

		KhachHang item = (KhachHang) obj;
		try {
			if (maKh != item.getMaKh()) {
				return false;
			}

		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
