package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.doituong.NganHang;
import com.idi.finance.bean.doituong.NganHangTaiKhoan;

public interface NganHangDAO {
	public List<NganHang> danhSachNganHang();

	public List<NganHangTaiKhoan> danhSachTaiKhoanNganHang();

	public NganHang layNganHang(int maNh);

	public void themNganHang(NganHang nganHang);

	public void capNhatNganHang(NganHang nganHang);

	public void xoaNganHang(int maNh);

	public NganHangTaiKhoan layTaiKhoanNganHang(int maTk);

	public void themTaiKhoanNganHang(NganHangTaiKhoan nganHangTaiKhoan);

	public void capNhatTaiKhoanNganHang(NganHangTaiKhoan nganHangTaiKhoan);

	public void xoaTaiKhoanNganHang(int maTk);
}
