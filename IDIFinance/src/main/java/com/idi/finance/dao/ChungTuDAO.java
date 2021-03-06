package com.idi.finance.dao;

import java.util.Date;
import java.util.List;

import com.idi.finance.bean.LoaiTien;
import com.idi.finance.bean.chungtu.ChungTu;

public interface ChungTuDAO {
	public List<ChungTu> danhSachChungTuTheoLoaiCt(String loaiCt);

	public ChungTu layChungTu(int maCt);

	public ChungTu layChungTu(int maCt, String loaiCt);

	public List<LoaiTien> danhSachLoaiTien();

	public void themChungTu(ChungTu chungTu);

	public void capNhatChungTu(ChungTu chungTu);

	public int demSoChungTuTheoLoaiCtVaNam(String loaiCt, Date nam);

	public void xoaChungTu(int maCt);

	public void xoaChungTu(int maCt, String loaiCt);
}
