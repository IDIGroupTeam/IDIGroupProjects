package com.idi.finance.bean.soketoan;

import com.idi.finance.bean.KyKeToan;
import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.TaiKhoan;

public class NghiepVuKeToan {
	private KyKeToan ky;
	private ChungTu chungTu;
	private TaiKhoan taiKhoanNo;
	private TaiKhoan taiKhoanCo;

	public KyKeToan getKy() {
		return ky;
	}

	public void setKy(KyKeToan ky) {
		this.ky = ky;
	}

	public ChungTu getChungTu() {
		return chungTu;
	}

	public void setChungTu(ChungTu chungTu) {
		this.chungTu = chungTu;
	}

	public TaiKhoan getTaiKhoanNo() {
		return taiKhoanNo;
	}

	public void setTaiKhoanNo(TaiKhoan taiKhoanNo) {
		this.taiKhoanNo = taiKhoanNo;
	}

	public TaiKhoan getTaiKhoanCo() {
		return taiKhoanCo;
	}

	public void setTaiKhoanCo(TaiKhoan taiKhoanCo) {
		this.taiKhoanCo = taiKhoanCo;
	}

	@Override
	public String toString() {
		String out = chungTu + "  " + taiKhoanNo + " " + taiKhoanCo;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof NghiepVuKeToan)) {
			return false;
		}

		NghiepVuKeToan item = (NghiepVuKeToan) obj;
		try {
			if (chungTu == null) {
				if (item.getChungTu() != null)
					return false;
			} else if (item.getChungTu() == null) {
				return false;
			} else if (!chungTu.equals(item.getChungTu())) {
				return false;
			}

			if (taiKhoanNo == null) {
				if (item.getTaiKhoanNo() != null)
					return false;
			} else if (item.getTaiKhoanNo() == null) {
				return false;
			} else if (!taiKhoanNo.equals(item.getTaiKhoanNo())) {
				return false;
			}

			if (taiKhoanCo == null) {
				if (item.getTaiKhoanCo() != null)
					return false;
			} else if (item.getTaiKhoanCo() == null) {
				return false;
			} else if (!taiKhoanCo.equals(item.getTaiKhoanCo())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
