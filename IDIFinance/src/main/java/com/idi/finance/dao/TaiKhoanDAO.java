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

	public boolean isPhatSinh(String maTk);

	public boolean isCha(String maTk);

	public List<LoaiTaiKhoan> danhSachTaiKhoan();

	public List<LoaiTaiKhoan> danhSachTaiKhoanPhatSinh();

	public List<LoaiTaiKhoan> danhSachTaiKhoanCha();

	public List<LoaiTaiKhoan> danhSachTaiKhoanTheoCap1(String maTkCap1);

	public List<LoaiTaiKhoan> danhSachTaiKhoan(List<String> maTkDs);

	public List<LoaiTaiKhoan> danhSachTaiKhoanCon(List<String> maTkDs);

	public List<LoaiTaiKhoan> danhSachTaiKhoanCon();

	public List<LoaiTaiKhoan> danhSachTaiKhoan(String maTkCon);

	public List<LoaiTaiKhoan> cayTaiKhoan();

	public LoaiTaiKhoan capNhatTaiKhoanNganHang(LoaiTaiKhoan loaiTaiKhoan);
}
