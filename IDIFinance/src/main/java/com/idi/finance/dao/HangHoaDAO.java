package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.hanghoa.DonVi;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.hanghoa.KhoHang;
import com.idi.finance.bean.hanghoa.NhomHang;

public interface HangHoaDAO {
	public List<DonVi> danhSachDonViHangHoa();

	public List<DonVi> danhSachDonViHangHoaPhatSinh();

	public List<DonVi> danhSachDonViHangHoaSoDuKy();

	public DonVi layDonVi(int maDv);

	public void capNhatDonVi(DonVi donVi);

	public void themDonVi(DonVi donVi);

	public void xoaDonVi(int maDv);

	public NhomHang danhSachNhomHangHoa(NhomHang nhomHangHoa);

	public List<NhomHang> danhSachNhomHangHoa();

	public List<NhomHang> danhSachNhomHangHoaPhatSinh();

	public List<NhomHang> danhSachNhomHangHoaCha();

	public NhomHang layNhomHangHoa(int maNhomHn);

	public void capNhatNhomHangHoa(NhomHang nhomHangHoa);

	public void themNhomHangHoa(NhomHang nhomHangHoa);

	public void xoaNhomHangHoa(int maNhomHh);

	public List<HangHoa> danhSachHangHoa();
	
	public List<HangHoa> danhSachHangHoaPhatSinh();

	public List<HangHoa> danhSachKhHangHoa();

	public List<HangHoa> danhSachHangHoa(String tuKhoa);

	public HangHoa layHangHoa(int maHh);

	public boolean kiemTraDuyNhat(int maHh, String kyHieuHh);

	public void capNhatHangHoa(HangHoa hangHoa);

	public void themHangHoa(HangHoa hangHoa);

	public void xoaHangHoa(int maHh);

	public List<KhoHang> danhSachKhoBai();

	public List<KhoHang> danhSachKhoBaiPhatSinh();

	public List<KhoHang> danhSachKhoBaiMacDinh();

	public KhoHang layKhoBai(int maKho);

	public void capNhatKhoBai(KhoHang khoBai);

	public void themKhoBai(KhoHang khoBai);

	public void xoaKhoBai(int maKho);
}
