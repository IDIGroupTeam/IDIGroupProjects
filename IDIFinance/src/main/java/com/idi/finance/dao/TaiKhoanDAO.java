package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public interface TaiKhoanDAO {
	public LoaiTaiKhoan layTaiKhoan(String maTk);

	public void themTaiKhoan(LoaiTaiKhoan loaiTaiKhoan);

	public void capNhatTaiKhoan(LoaiTaiKhoan loaiTaiKhoan);

	public void insertOrUpdateTaiKhoanDs(LoaiTaiKhoan loaiTaiKhoan);

	public void insertOrUpdateTaiKhoanDs(List<LoaiTaiKhoan> taiKhoanDs);

	public void xoaTaiKhoan(String maTk);

	public List<LoaiTaiKhoan> danhSachTaiKhoan();

	public List<LoaiTaiKhoan> danhSachTaiKhoanTheoCap1(String maTkCap1);
}
