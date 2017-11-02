package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.KhachHang;

public interface KhachHangDAO {
	public List<KhachHang> danhSachKhachHang();

	public KhachHang layKhachHang(Integer maKh);

	public void xoaKhachHang(Integer maKh);

	public void luuCapNhatKhachHang(KhachHang khachHang);

	public List<KhachHang> layKhachHangTheoMaHoacTen(String maHoacTen);
}
