package com.idi.finance.bean.chungtu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.idi.finance.bean.taikhoan.TaiKhoan;

public class NhomDinhKhoan {
	private int nhomDk;
	private List<TaiKhoan> taiKhoanDs;

	public int getNhomDk() {
		return nhomDk;
	}

	public void setNhomDk(int nhomDk) {
		this.nhomDk = nhomDk;
	}

	public List<TaiKhoan> getTaiKhoanDs() {
		return taiKhoanDs;
	}

	public void setTaiKhoanDs(List<TaiKhoan> taiKhoanDs) {
		this.taiKhoanDs = taiKhoanDs;
	}

	public void themTaiKhoan(TaiKhoan taiKhoan) {
		if (taiKhoan == null) {
			return;
		}

		if (taiKhoanDs == null)
			taiKhoanDs = new ArrayList<>();

		if (!taiKhoanDs.contains(taiKhoan)) {
			taiKhoanDs.add(taiKhoan);
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
