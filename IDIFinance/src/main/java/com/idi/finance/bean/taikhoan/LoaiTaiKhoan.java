package com.idi.finance.bean.taikhoan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.idi.finance.bean.doituong.NganHangTaiKhoan;

public class LoaiTaiKhoan implements Comparable<LoaiTaiKhoan> {
	private static final Logger logger = Logger.getLogger(LoaiTaiKhoan.class);
	public static final int NO = -1;
	public static final String NO_XAU = "NO";
	public static final int CO = 1;
	public static final String CO_XAU = "CO";

	// Bộ tài khoản thông tư 200
	public static final String TIEN_MAT = "111";
	public static final String TIEN_MAT_VN = "1111";
	public static final String TIEN_MAT_NT = "1112";
	public static final String TIEN_MAT_VANG = "1113";
	public static final String TIEN_GUI_NGAN_HANG = "112";
	public static final String TIEN_GUI_NGAN_HANG_VN = "1121";
	public static final String TIEN_GUI_NGAN_HANG_NT = "1122";
	public static final String TIEN_GUI_NGAN_HANG_VANG = "1123";
	public static final String TIEN_DANG_CHUYEN_NT = "1132";

	public static final String HANG_HOA = "156";

	public static final String LOI_NHUAN_CHUA_PHAN_PHOI = "421";
	public static final String LOI_NHUAN_CHUA_PHAN_PHOI_KY_TRUOC = "4211";
	public static final String LOI_NHUAN_CHUA_PHAN_PHOI_KY_NAY = "4212";

	private String maTk;
	private String tenTk;
	private String maTenTk;
	private String maTkCha;
	private int soDu;
	private int soDuGiaTri;
	private boolean luongTinh = false;
	private LoaiTaiKhoan loaiTaiKhoan;
	private List<LoaiTaiKhoan> loaiTaiKhoanDs;
	private LoaiTaiKhoan doiUng;
	private boolean isNew = false;
	private String maTkGoc;
	private NganHangTaiKhoan nganHangTaiKhoan;
	private boolean isPhatSinh = false;
	private boolean isCha = false;

	public LoaiTaiKhoan() {

	}

	public LoaiTaiKhoan(String maTk) {
		this.maTk = maTk;
	}

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

	public boolean isLuongTinh() {
		return luongTinh;
	}

	public void setLuongTinh(boolean luongTinh) {
		this.luongTinh = luongTinh;
	}

	public LoaiTaiKhoan getLoaiTaiKhoan() {
		return loaiTaiKhoan;
	}

	public void setLoaiTaiKhoan(LoaiTaiKhoan loaiTaiKhoan) {
		this.loaiTaiKhoan = loaiTaiKhoan;
	}

	public List<LoaiTaiKhoan> getLoaiTaiKhoanDs() {
		return loaiTaiKhoanDs;
	}

	public void setLoaiTaiKhoanDs(List<LoaiTaiKhoan> loaiTaiKhoanDs) {
		this.loaiTaiKhoanDs = loaiTaiKhoanDs;
	}

	public void themLoaiTaiKhoan(LoaiTaiKhoan loaiTaiKhoan) {
		if (loaiTaiKhoan == null) {
			return;
		}

		if (loaiTaiKhoanDs == null) {
			loaiTaiKhoanDs = new ArrayList<>();
		}

		if (!loaiTaiKhoanDs.contains(loaiTaiKhoan)) {
			loaiTaiKhoanDs.add(loaiTaiKhoan);
		}
	}

	public void themLoaiTaiKhoan(List<LoaiTaiKhoan> loaiTaiKhoanDs) {
		if (loaiTaiKhoanDs == null) {
			return;
		}

		Iterator<LoaiTaiKhoan> iter = loaiTaiKhoanDs.iterator();
		while (iter.hasNext()) {
			themLoaiTaiKhoan(iter.next());
		}
	}

	public LoaiTaiKhoan getDoiUng() {
		return doiUng;
	}

	public void setDoiUng(LoaiTaiKhoan doiUng) {
		this.doiUng = doiUng;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public NganHangTaiKhoan getNganHangTaiKhoan() {
		return nganHangTaiKhoan;
	}

	public boolean isPhatSinh() {
		return isPhatSinh;
	}

	public void setPhatSinh(boolean isPhatSinh) {
		this.isPhatSinh = isPhatSinh;
	}

	public boolean isCha() {
		return isCha;
	}

	public void setCha(boolean isCha) {
		this.isCha = isCha;
	}

	public String getMaTkGoc() {
		return maTkGoc;
	}

	public void setMaTkGoc(String maTkGoc) {
		this.maTkGoc = maTkGoc;
	}

	public void setNganHangTaiKhoan(NganHangTaiKhoan nganHangTaiKhoan) {
		this.nganHangTaiKhoan = nganHangTaiKhoan;
	}

	public boolean isTrucHe(LoaiTaiKhoan loaiTaiKhoan) {
		if (loaiTaiKhoan == null) {
			return false;
		}

		boolean res = false;
		if (maTk != null && loaiTaiKhoan.getMaTk() != null) {
			try {
				String maTkCha = maTk.trim();
				String maTkCon = loaiTaiKhoan.getMaTk().trim();

				if (maTkCha.length() > maTkCon.length()) {
					res = maTkCha.substring(0, maTkCon.length()).equals(maTkCon);
				} else {
					res = maTkCon.substring(0, maTkCha.length()).equals(maTkCha);
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}

		return res;
	}

	@Override
	public String toString() {
		String out = maTk + " - " + tenTk + " - " + soDu;
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
		if (maTk == null) {
			if (item.getMaTk() != null)
				return false;
		} else if (item.getMaTk() == null) {
			return false;
		} else if (!maTk.trim().equals(item.getMaTk().trim())) {
			return false;
		}

		// Cái này chỉ tạm thời cho chức năng lưu chuyển tiền tệ, sau phải sửa
		if (doiUng == null) {
			if (item.getDoiUng() != null)
				return false;
		} else if (item.getDoiUng() == null) {
			return false;
		} else if (!doiUng.equals(item.getDoiUng())) {
			return false;
		} else if (!(soDuGiaTri == item.getSoDuGiaTri())) {
			// Cách cài đặt này là tạm thời do thiết kế ban đầu không chuẩn
			return false;
		}

		return true;
	}

	@Override
	public int compareTo(LoaiTaiKhoan loaiTaiKhoan) {
		if (loaiTaiKhoan == null) {
			return 1;
		}

		int rs = 0;

		// So sanh maTk
		if (maTk == null) {
			if (loaiTaiKhoan.getMaTk() != null) {
				return -1;
			}
		} else {
			if (loaiTaiKhoan.getMaTk() == null) {
				return 1;
			} else {
				rs = maTk.compareTo(loaiTaiKhoan.getMaTk());
				if (rs != 0) {
					return rs;
				}
			}
		}

		return rs;
	}
}
