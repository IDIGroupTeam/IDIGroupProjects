package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.chungtu.DoiTuong;
import com.idi.finance.bean.doitac.NhaCungCap;

public interface NhaCungCapDAO {
	public List<DoiTuong> danhSachDoiTuong();

	public List<NhaCungCap> danhSachNhaCungCap();

	public NhaCungCap layNhaCungCap(Integer maNcc);

	public void xoaNhaCungCap(Integer maNcc);

	public void luuCapNhatNhaCungCap(NhaCungCap nhaCungCap);

	public List<NhaCungCap> layNhaCungCapTheoMaHoacTen(String maHoacTen);
}
