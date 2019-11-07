package com.idi.finance.form;

import java.util.Date;

import com.idi.finance.bean.bctc.KyKeToanCon;
import com.idi.finance.bean.kyketoan.KyKeToan;

public class ChungTuForm {
	private int loaiKy = KyKeToanCon.NAN;
	private KyKeToan kyKeToan;
	private Date dau;
	private Date cuoi;

	public int getLoaiKy() {
		return loaiKy;
	}

	public void setLoaiKy(int loaiKy) {
		this.loaiKy = loaiKy;
	}

	public KyKeToan getKyKeToan() {
		return kyKeToan;
	}

	public void setKyKeToan(KyKeToan kyKeToan) {
		this.kyKeToan = kyKeToan;
	}

	public Date getDau() {
		return dau;
	}

	public void setDau(Date dau) {
		this.dau = dau;
	}

	public Date getCuoi() {
		return cuoi;
	}

	public void setCuoi(Date cuoi) {
		this.cuoi = cuoi;
	}
}
