package com.idi.finance.dao;

import java.util.Date;
import java.util.List;

import com.idi.finance.bean.cdkt.DuLieuKeToan;
import com.idi.finance.bean.chungtu.TaiKhoan;
import com.idi.finance.bean.soketoan.NghiepVuKeToan;

public interface SoKeToanDAO {
	public List<NghiepVuKeToan> danhSachNghiepVuKeToanTheoLoaiTaiKhoan(String maTk);

	public List<NghiepVuKeToan> danhSachNghiepVuKeToanTheoLoaiTaiKhoan(String maTk, Date dau, Date cuoi);

	public List<NghiepVuKeToan> danhSachNghiepVuKeToanKhoTheoLoaiTaiKhoan(String maTk, Date dau, Date cuoi);

	public List<NghiepVuKeToan> danhSachNghiepVuKeToanKcTheoLoaiTaiKhoan(String maTk, Date dau, Date cuoi);

	public double tongPhatSinh(String maTk, int soDu, Date dau, Date cuoi);

	public List<TaiKhoan> tongPhatSinh(Date batDau, Date ketThuc);

	public List<DuLieuKeToan> danhSachTongPhatSinhDoiTuong(String maTk, Date dau, Date cuoi);

	public List<DuLieuKeToan> danhSachTongHopCongNo(String maTk, Date dau, Date cuoi);
}
