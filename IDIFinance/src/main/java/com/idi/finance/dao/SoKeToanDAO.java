package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.soketoan.NghiepVuKeToan;

public interface SoKeToanDAO {
	public List<ChungTu> danhSachChungTu();

	public List<ChungTu> danhSachChungTuTheoLoaiTaiKhoan(String maTk);

	public List<NghiepVuKeToan> danhSachNghiepVuKeToanTheoLoaiTaiKhoan(String maTk);
}
