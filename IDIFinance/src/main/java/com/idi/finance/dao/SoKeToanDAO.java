package com.idi.finance.dao;

import java.util.Date;
import java.util.List;

import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.soketoan.NghiepVuKeToan;

public interface SoKeToanDAO {
	public List<ChungTu> danhSachChungTu(Date dau, Date cuoi, List<String> loaiCts);

	public List<ChungTu> danhSachChungTuKho(Date dau, Date cuoi, List<String> loaiCts);

	public List<NghiepVuKeToan> danhSachNghiepVuKeToanTheoLoaiTaiKhoan(String maTk);

	public List<NghiepVuKeToan> danhSachNghiepVuKeToanTheoLoaiTaiKhoan(String maTk, Date dau, Date cuoi,
			List<String> loaiCts);

	public List<NghiepVuKeToan> danhSachNghiepVuKeToanTheoLoaiTaiKhoan(String maTk, Date dau, Date cuoi);

	public double tongPhatSinh(String maTk, int soDu, Date dau, Date cuoi);
}
