package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.hanghoa.DonGia;
import com.idi.finance.bean.hanghoa.HangHoa;

public interface KhoHangDAO {
	public List<DonGia> layDonGiaDs(int maHh, int maKho);

	public List<DonGia> layDonGiaDs(HangHoa hangHoa);

	public double laySoLuong(HangHoa hangHoa);

	public void themNhapKho(HangHoa hangHoa);

	public void suaNhapKho(HangHoa hangHoa);

	public void xoaKho(HangHoa hangHoa);
}
