package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.doituong.DoiTuong;
import com.idi.finance.bean.doituong.NhanVien;

public interface NhanVienDAO {
	public List<DoiTuong> danhSachDoiTuong();

	public List<DoiTuong> danhSachDoiTuong(String maHoacTen);

	public List<NhanVien> danhSachNhanVien();

	public List<NhanVien> layNhanVienTheoMaHoacTen(String maHoacTen);
}
