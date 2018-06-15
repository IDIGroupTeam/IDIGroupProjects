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

	public void macDinhKyKeToan(int maKyKt);

	public void dongMoKyKeToan(int maKyKt, int trangThai);

	public void xoaKyKeToan(int maKyKt);

	// Các hàm về số dư đầu kỳ
	public List<SoDuKy> danhSachSoDuKy(int maKkt);

	public List<SoDuKy> danhSachSoDuKy(String maTk, int maKkt);

	public SoDuKy laySoDuKy(String maTk, int maKkt);

	public void themSoDuDauKy(SoDuKy soDuKy);
}
