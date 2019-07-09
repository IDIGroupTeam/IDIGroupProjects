package com.idi.finance.bean.chungtu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.bean.taikhoan.TaiKhoan;

public class NhomDinhKhoan {
	private int nhomDk;
	private List<TaiKhoan> taiKhoanNoDs;
	private List<TaiKhoan> taiKhoanCoDs;

	public int getNhomDk() {
		return nhomDk;
	}

	public void setNhomDk(int nhomDk) {
		this.nhomDk = nhomDk;
	}

	public void themTaiKhoan(TaiKhoan taiKhoan) {
		if (taiKhoan == null) {
			return;
		}

		if (taiKhoanNoDs == null)
			taiKhoanNoDs = new ArrayList<>();

		if (taiKhoanCoDs == null)
			taiKhoanCoDs = new ArrayList<>();

		if (taiKhoan.getSoDu() == LoaiTaiKhoan.NO) {
			if (!taiKhoanNoDs.contains(taiKhoan)) {
				taiKhoanNoDs.add(taiKhoan);
			}
		} else {
			if (!taiKhoanCoDs.contains(taiKhoan)) {
				taiKhoanCoDs.add(taiKhoan);
			}
		}
	}

	public void themTaiKhoan(List<TaiKhoan> taiKhoanDs) {
		if (taiKhoanDs == null) {
			return;
		}

		Iterator<TaiKhoan> iter = taiKhoanDs.iterator();
		while (iter.hasNext()) {
			TaiKhoan taiKhoan = iter.next();
			themTaiKhoan(taiKhoan);
		}
	}

	public List<TaiKhoan> getTaiKhoanNoDs() {
		return taiKhoanNoDs;
	}

	public void setTaiKhoanNoDs(List<TaiKhoan> taiKhoanNoDs) {
		this.taiKhoanNoDs = taiKhoanNoDs;
	}

	public List<TaiKhoan> getTaiKhoanCoDs() {
		return taiKhoanCoDs;
	}

	public void setTaiKhoanCoDs(List<TaiKhoan> taiKhoanCoDs) {
		this.taiKhoanCoDs = taiKhoanCoDs;
	}

	@Override
	public String toString() {
		String out = "nhomDk: " + nhomDk;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof NhomDinhKhoan)) {
			return false;
		}

		NhomDinhKhoan item = (NhomDinhKhoan) obj;
		try {
			if (nhomDk != item.getNhomDk())
				return false;
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
