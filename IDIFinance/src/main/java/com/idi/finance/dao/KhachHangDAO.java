package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.chungtu.DoiTuong;
import com.idi.finance.bean.doitac.KhachHang;

public interface KhachHangDAO {
	public List<DoiTuong> danhSachDoiTuong();

	public List<KhachHang> danhSachKhachHang();

	public KhachHang layKhachHang(Integer maKh);

	public void xoaKhachHang(Integer maKh);

	public void luuCapNhatKhachHang(KhachHang khachHang);

	public List<KhachHang> layKhachHangTheoMaHoacTen(String maHoacTen);
}
