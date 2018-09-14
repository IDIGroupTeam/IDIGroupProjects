package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.CauHinh;

public interface CauHinhDAO {
	public List<CauHinh> danhSachCauHinh();

	public int capNhatCauHinh(CauHinh cauHinh);

	public CauHinh layCauHinh(String maCh);
}
