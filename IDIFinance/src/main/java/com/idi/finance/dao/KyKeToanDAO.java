package com.idi.finance.dao;

import java.util.Date;
import java.util.List;

import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.kyketoan.KyKeToan;
import com.idi.finance.bean.kyketoan.SoDuKy;

public interface KyKeToanDAO {
	// Các hàm về kỳ kế toán
	public List<KyKeToan> danhSachKyKeToan();

	public KyKeToan layKyKeToan(int maKyKt);

	public KyKeToan layKyKeToan(ChungTu chungTu);

	public KyKeToan layKyKeToanMacDinh();

	public void capNhatKyKeToan(KyKeToan kyKeToan);

	public void themKyKeToan(KyKeToan kyKeToan);

	public Date layKetThucLonNhat();

	public Date layKetThucLonNhat(Date gioiHan);

	public KyKeToan layKyKeToanTruoc(KyKeToan kyKeToan);

	public List<SoDuKy> tinhSoDuKyTheoDoiTuong(KyKeToan kyKeToan, int loaiDt, String maTk);

	public void macDinhKyKeToan(int maKyKt);

	public void dongMoKyKeToan(int maKyKt, int trangThai);

	public void xoaKyKeToan(int maKyKt);

	// Các hàm về số dư đầu kỳ
	public List<SoDuKy> danhSachSoDuKy(int maKkt);

	public List<SoDuKy> danhSachSoDuKyTheoDoiTuong(int maKkt, int loaiDt);

	public List<SoDuKy> danhSachSoDuKyTheoDoiTuong(String maTk, int maKkt);

	public List<SoDuKy> danhSachSoDuKy(String maTk, int maKkt);

	public SoDuKy laySoDuKy(String maTk, int maKkt);

	public SoDuKy laySoDuKyTheoDoiTuong(String maTk, int maKkt, int loaiDt, int maDt);

	public void themSoDuDauKy(SoDuKy soDuKy);
}
