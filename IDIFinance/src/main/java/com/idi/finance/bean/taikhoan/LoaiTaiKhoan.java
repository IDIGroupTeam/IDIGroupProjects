package com.idi.finance.bean.taikhoan;

public class LoaiTaiKhoan {
	public static final int NO = 0;
	public static final int CO = 1;
	
	public static final String TIEN_MAT = "111";
	private String maTk;
	private String tenTk;
	private String maTenTk;
	private String maTkCha;
	private int soDu;

	public String getMaTk() {
		return maTk;
	}

	public void setMaTk(String maTk) {
		this.maTk = maTk;
	}

	public String getTenTk() {
		return tenTk;
	}

	public void setTenTk(String tenTk) {
		this.tenTk = tenTk;
	}

	public String getMaTenTk() {
		return maTenTk;
	}

	public void setMaTenTk(String maTenTk) {
		this.maTenTk = maTenTk;
	}

	public String getMaTkCha() {
		return maTkCha;
	}

	public void setMaTkCha(String maTkCha) {
		this.maTkCha = maTkCha;
	}

	public int getSoDu() {
		return soDu;
	}

	public void setSoDu(int soDu) {
		this.soDu = soDu;
	}

	@Override
	public String toString() {
		String out = maTk;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof LoaiTaiKhoan)) {
			return false;
		}

		LoaiTaiKhoan item = (LoaiTaiKhoan) obj;
		try {
			if (maTk == null) {
				if (item.getMaTk() != null)
					return false;
			} else if (item.getMaTk() == null) {
				return false;
			} else if (!maTk.trim().equals(item.getMaTk().trim())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
