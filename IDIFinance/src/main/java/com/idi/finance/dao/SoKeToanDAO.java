package com.idi.finance.dao;

import java.util.Date;
import java.util.List;

import com.idi.finance.bean.bctc.DuLieuKeToan;
import com.idi.finance.bean.soketoan.NghiepVuKeToan;
import com.idi.finance.bean.taikhoan.TaiKhoan;

public interface SoKeToanDAO {
	public List<NghiepVuKeToan> danhSachNghiepVuKeToanTheoLoaiTaiKhoan(String maTk);

	public List<NghiepVuKeToan> danhSachNghiepVuKeToanTheoLoaiTaiKhoan(String maTk, Date dau, Date cuoi);

	public List<NghiepVuKeToan> danhSachNghiepVuKeToanKtthTheoLoaiTaiKhoan(String maTk, Date dau, Date cuoi);

	public List<NghiepVuKeToan> danhSachNghiepVuKeToanKhoTheoLoaiTaiKhoan(String maTk, Date dau, Date cuoi);

	public List<NghiepVuKeToan> danhSachNghiepVuKeToanKhoTheoLoaiTaiKhoan(String maTk, int maHh, Date dau, Date cuoi);

	public List<NghiepVuKeToan> danhSachNghiepVuKeToanKhoTheoLoaiTaiKhoan(String maTk, int maHh, List<Integer> maKhoDs,
			Date dau, Date cuoi);

	public List<NghiepVuKeToan> danhSachNghiepVuKeToanKcTheoLoaiTaiKhoan(String maTk, Date dau, Date cuoi);

	public double tongPhatSinh(String maTk, int soDu, Date dau, Date cuoi);

	public List<TaiKhoan> tongPhatSinh(Date batDau, Date ketThuc);

	public List<DuLieuKeToan> danhSachTongPhatSinhDoiTuong(String maTk, Date dau, Date cuoi);

	public List<DuLieuKeToan> danhSachTongPhatSinhDoiTuongKtth(String maTk, Date dau, Date cuoi);

	public List<DuLieuKeToan> danhSachPhatSinhCongNo(String maTk, Date dau, Date cuoi);

	public List<DuLieuKeToan> danhSachPhatSinhCongNoKtth(String maTk, Date dau, Date cuoi);

	public List<DuLieuKeToan> danhSachPhatSinhNxt(String maTk, List<Integer> maKhoDs, Date dau, Date cuoi);

	public DuLieuKeToan tongPhatSinhNxt(String maTk, int maHh, List<Integer> maKhoDs, Date dau, Date cuoi);
}
