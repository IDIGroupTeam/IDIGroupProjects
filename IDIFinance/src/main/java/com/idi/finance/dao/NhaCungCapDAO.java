package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.doituong.DoiTuong;
import com.idi.finance.bean.doituong.NhaCungCap;

public interface NhaCungCapDAO {
	public List<DoiTuong> danhSachDoiTuong();
	
	public List<DoiTuong> danhSachDoiTuong(String maHoacTen);

	public List<NhaCungCap> danhSachNhaCungCap();

	public NhaCungCap layNhaCungCap(Integer maNcc);

	public void xoaNhaCungCap(Integer maNcc);

	public void luuCapNhatNhaCungCap(NhaCungCap nhaCungCap);

	public List<NhaCungCap> layNhaCungCapTheoMaHoacTen(String maHoacTen);

	public boolean kiemTraKhNhaCungCap(String khNcc);
}
