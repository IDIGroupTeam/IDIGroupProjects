package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.hanghoa.DonVi;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.hanghoa.KhoBai;
import com.idi.finance.bean.hanghoa.NhomHangHoa;

public interface HangHoaDAO {
	public List<DonVi> danhSachDonViHangHoa();

	public DonVi layDonVi(int maDv);

	public void capNhatDonVi(DonVi donVi);

	public void themDonVi(DonVi donVi);

	public void xoaDonVi(int maDv);

	public NhomHangHoa danhSachNhomHangHoa(NhomHangHoa nhomHangHoa);

	public List<NhomHangHoa> danhSachNhomHangHoa();

	public NhomHangHoa layNhomHangHoa(int maNhomHn);

	public void capNhatNhomHangHoa(NhomHangHoa nhomHangHoa);

	public void themNhomHangHoa(NhomHangHoa nhomHangHoa);

	public void xoaNhomHangHoa(int maNhomHh);

	public List<HangHoa> danhSachHangHoa();

	public HangHoa layHangHoa(int maHh);

	public void capNhatHangHoa(HangHoa hangHoa);

	public void themHangHoa(HangHoa hangHoa);

	public void xoaHangHoa(int maHh);

	public List<KhoBai> danhSachKhoBai();

	public KhoBai layKhoBai(int maKho);

	public void capNhatKhoBai(KhoBai khoBai);

	public void themKhoBai(KhoBai khoBai);

	public void xoaKhoBai(int maKho);
}
