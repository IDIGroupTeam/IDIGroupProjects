package com.idi.finance.dao;

import java.util.Date;
import java.util.List;

import com.idi.finance.bean.LoaiTien;
import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.KetChuyenButToan;

public interface ChungTuDAO {
	public List<ChungTu> danhSachChungTuTheoLoaiCt(String loaiCt, Date batDau, Date ketThuc);

	public List<ChungTu> danhSachChungTuKhoTheoLoaiCt(String loaiCt, Date batDau, Date ketThuc);

	public ChungTu layChungTu(int maCt, String loaiCt);

	public ChungTu layChungTuKho(int maCt, String loaiCt);

	public List<LoaiTien> danhSachLoaiTien();

	public void themChungTu(ChungTu chungTu);

	public void capNhatChungTu(ChungTu chungTu);

	public void themChungTuKho(ChungTu chungTu);

	public void capNhatChungTuKho(ChungTu chungTu);

	public int demSoChungTuTheoLoaiCtVaKy(String loaiCt, Date batDau, Date ketThuc);

	public void xoaChungTu(int maCt, String loaiCt);

	public void xoaChungTuKho(ChungTu chungTu);

	// Dùng cho bút toán kết chuyển
	public List<KetChuyenButToan> danhSachKetChuyenButToan();

	public List<KetChuyenButToan> danhSachKetChuyenButToan(int loaiKc);

	public void capNhatKetChuyenButToan(KetChuyenButToan ketChuyenButToan);

	public void themKetChuyenButToan(KetChuyenButToan ketChuyenButToan);

	public KetChuyenButToan layKetChuyenButToan(int maKc);

	public void xoaKetChuyenButToan(int maKc);

	// Dùng cho kết chuyển
	public List<ChungTu> danhSachKetChuyen(Date batDau, Date ketThuc);

	public ChungTu layKetChuyen(int maCt);

	public Date layKetChuyenGanNhat(int loaiKc, Date batDau, Date ketThuc);

	public void themChungTuKetChuyen(ChungTu chungTu);
	
	public void xoaKetChuyen(ChungTu chungTu);
}
