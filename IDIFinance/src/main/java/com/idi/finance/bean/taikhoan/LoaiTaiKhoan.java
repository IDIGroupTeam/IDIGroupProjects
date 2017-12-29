package com.idi.finance.bean.taikhoan;

public class LoaiTaiKhoan {
	public static final int NO = -1;
	public static final int CO = 1;

	public static final String TIEN_MAT = "111";
	public static final String TIEN_MAT_VN = "1111";
	public static final String TIEN_MAT_NT = "1112";
	public static final String TIEN_MAT_VANG = "1113";
	public static final String TIEN_GUI_NGAN_HANG = "112";
	public static final String TIEN_GUI_NGAN_HANG_VN = "1121";
	public static final String TIEN_GUI_NGAN_HANG_NT = "1122";
	public static final String TIEN_GUI_NGAN_HANG_VANG = "1123";
	public static final String TIEN_DANG_CHUYEN_NT = "1132";

	private String maTk;
	private String tenTk;
	private String maTenTk;
	private String maTkCha;
	private int soDu;
	private int soDuGiaTri;
	private LoaiTaiKhoan taiKhoanCha;
	private boolean isNew = false;

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

	public int getSoDuGiaTri() {
		return soDuGiaTri;
	}

	public void setSoDuGiaTri(int soDuGiaTri) {
		this.soDuGiaTri = soDuGiaTri;
	}

	public LoaiTaiKhoan getTaiKhoanCha() {
		return taiKhoanCha;
	}

	public void setTaiKhoanCha(LoaiTaiKhoan taiKhoanCha) {
		this.taiKhoanCha = taiKhoanCha;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	@Override
	public String toString() {
		String out = maTk + " " + tenTk + " " + maTkCha + " " + soDu;
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
