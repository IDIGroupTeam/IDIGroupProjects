package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.doituong.DoiTuong;
import com.idi.finance.bean.doituong.KhachHang;

public interface KhachHangDAO {
	public List<DoiTuong> danhSachDoiTuong();

	public List<DoiTuong> danhSachDoiTuong(String maHoacTen);

	public List<KhachHang> danhSachKhachHang();
	
	public List<KhachHang> danhSachKhachHangPhatSinh();

	public KhachHang layKhachHang(Integer maKh);

	public void xoaKhachHang(Integer maKh);

	public void luuCapNhatKhachHang(KhachHang khachHang);

	public List<KhachHang> layKhachHangTheoMaHoacTen(String maHoacTen);

	public boolean kiemTraKhKhachHang(String khKh);
}
