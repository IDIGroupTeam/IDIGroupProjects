package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.chungtu.ChungTu;

public interface SoKeToanDAO {
	public List<ChungTu> danhSachChungTu();

	public List<ChungTu> danhSachChungTuTheoLoaiTaiKhoan(String maTk);
}
