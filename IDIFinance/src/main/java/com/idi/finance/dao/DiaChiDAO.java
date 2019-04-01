package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.diachi.CapDiaChi;
import com.idi.finance.bean.diachi.VungDiaChi;

public interface DiaChiDAO {
	public List<CapDiaChi> danhSachCapDiaChi(int capDc);

	public VungDiaChi danhSachDiaChi(VungDiaChi vungDiaChi);

	public int demTongSoVungDiaChi(VungDiaChi vungDiaChi);

	public List<VungDiaChi> danhSachDiaChi(VungDiaChi vungDiaChi, int batDau, int soDong);

	public List<VungDiaChi> danhSachDiaChi(int capDc);

	public List<VungDiaChi> danhSachDiaChi(String maVungDcCha);

	public VungDiaChi layVungDiaChi(String maVungDc);

	public void capNhatVung(List<VungDiaChi> vungDiaChiDs);
}
