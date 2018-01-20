package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.hanghoa.DonVi;
import com.idi.finance.bean.hanghoa.LoaiHangHoa;
import com.idi.finance.bean.hanghoa.NhomHangHoa;

public interface HangHoaDAO {
	public List<DonVi> danhSachDonViHangHoa();

	public NhomHangHoa danhSachNhomHangHoa(NhomHangHoa nhomHangHoa);

	public List<LoaiHangHoa> danhSachLoaiHangHoa();
}
