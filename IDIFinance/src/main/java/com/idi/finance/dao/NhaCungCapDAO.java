package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.NhaCungCap;

public interface NhaCungCapDAO {
	public List<NhaCungCap> danhSachNhaCungCap();

	public NhaCungCap layNhaCungCap(Integer maNcc);

	public void xoaNhaCungCap(Integer maNcc);

	public void luuCapNhatNhaCungCap(NhaCungCap nhaCungCap);

	public List<NhaCungCap> layNhaCungCapTheoMaHoacTen(String maHoacTen);
}
