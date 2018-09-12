package com.idi.finance.bean.taikhoan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.idi.finance.bean.doitac.NganHangTaiKhoan;

public class LoaiTaiKhoan {
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

	public static final String PHAI_THU_KHACH_HANG = "131";
	public static final String PHAI_TRA_NGUOI_BAN = "331";

	public static final String TAM_UNG = "141";

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
	private boolean isNew = false;
	private String maTkGoc;
	private NganHangTaiKhoan nganHangTaiKhoan;

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

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public NganHangTaiKhoan getNganHangTaiKhoan() {
		return nganHangTaiKhoan;
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

	@Override
	public String toString() {
		String out = maTk + " " + tenTk;
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

		return true;
	}
}
