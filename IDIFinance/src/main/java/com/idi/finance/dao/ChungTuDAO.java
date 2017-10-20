package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.LoaiTien;
import com.idi.finance.bean.chungtu.ChungTu;

public interface ChungTuDAO {
	public List<ChungTu> danhSachChungTuTheoLoaiCt(String loaiCt);

	public ChungTu layChungTuTheoMaCt(int maCt);

	public List<LoaiTien> danhSachLoaiTien();

	public void themChungTu(ChungTu chungTu);
}
