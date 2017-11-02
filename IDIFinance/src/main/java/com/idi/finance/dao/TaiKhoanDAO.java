package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public interface TaiKhoanDAO {
	public void insertOrUpdateTaiKhoanDs(List<LoaiTaiKhoan> taiKhoanDs);

	public List<LoaiTaiKhoan> danhSachTaiKhoan();
	
	public List<LoaiTaiKhoan> danhSachTaiKhoanTheoCap1(String maTkCap1);
}
