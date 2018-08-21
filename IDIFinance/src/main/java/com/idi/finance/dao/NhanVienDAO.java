package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.NhanVien;
import com.idi.finance.bean.chungtu.DoiTuong;

public interface NhanVienDAO {
	public List<DoiTuong> danhSachDoiTuong();

	public List<NhanVien> danhSachNhanVien();

	public List<NhanVien> layNhanVienTheoMaHoacTen(String maHoacTen);
}
