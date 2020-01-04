package com.idi.finance.service;

import java.util.Date;
import java.util.List;

import com.idi.finance.bean.bctc.DuLieuKeToan;
import com.idi.finance.bean.bctc.KyKeToanCon;
import com.idi.finance.bean.kyketoan.KyKeToan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public interface BaoCaoTaiChinhService {
	public DuLieuKeToan taoBangCdps(Date batDau, Date ketThuc, List<LoaiTaiKhoan> loaiTaiKhoanDs, KyKeToan kyKeToan);

	public DuLieuKeToan taoBangCdps(KyKeToanCon kktCon, List<LoaiTaiKhoan> loaiTaiKhoanDs, KyKeToan kyKeToan);

	public DuLieuKeToan taoBangCdps(KyKeToanCon kktCon, LoaiTaiKhoan loaiTaiKhoan, KyKeToan kyKeToan);
}
