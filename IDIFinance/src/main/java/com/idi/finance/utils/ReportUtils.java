package com.idi.finance.utils;

import java.util.Iterator;
import java.util.List;

import com.idi.finance.bean.taikhoan.TaiKhoan;

public class ReportUtils {
	public static String layDanhSachTaiKhoan(List<TaiKhoan> taiKhoanDs) {
		if (taiKhoanDs == null || taiKhoanDs.size() == 0) {
			return "";
		}

		StringBuilder sb = new StringBuilder("");
		Iterator<TaiKhoan> iter = taiKhoanDs.iterator();
		while (iter.hasNext()) {
			TaiKhoan taiKhoan = iter.next();

			if (taiKhoan.getLoaiTaiKhoan() != null && taiKhoan.getLoaiTaiKhoan().getMaTk() != null
					&& !taiKhoan.getLoaiTaiKhoan().getMaTk().trim().equals("")) {
				sb.append(taiKhoan.getLoaiTaiKhoan().getMaTk());
				sb.append(",");
			}
		}

		if (sb.length() > 0) {
			return sb.substring(0, sb.length() - 1);
		}

		return "";
	}
}