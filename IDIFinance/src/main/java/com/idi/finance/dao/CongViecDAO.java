package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.congviec.LinhVuc;
import com.idi.finance.bean.congviec.NghiepVu;

public interface CongViecDAO {
	public List<LinhVuc> danhSachLinhVuc();

	public List<NghiepVu> danhSachNghiepVu(int maLv);

	public List<NghiepVu> danhSachNghiepVu();

	public NghiepVu layNghiepVu(String maNv);

	public void capNhatNghiepVu(NghiepVu nghiepVu);

	public void themNghiepVu(NghiepVu nghiepVu);

	public void xoaNghiepVu(String maNv);
}
