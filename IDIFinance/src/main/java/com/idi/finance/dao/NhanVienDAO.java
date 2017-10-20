package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.NhanVien;

public interface NhanVienDAO {
	public List<NhanVien> layNhanVienTheoMaHoacTen(String maHoacTen);
}
