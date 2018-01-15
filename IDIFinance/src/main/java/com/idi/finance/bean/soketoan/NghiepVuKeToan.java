package com.idi.finance.bean.soketoan;

import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.TaiKhoan;

public class NghiepVuKeToan implements Comparable<NghiepVuKeToan> {
	private ChungTu chungTu;
	private TaiKhoan taiKhoanNo;
	private TaiKhoan taiKhoanCo;

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
	public int compareTo(NghiepVuKeToan nvkt) {
		if (nvkt == null) {
			return 1;
		}

		if (nvkt.getChungTu() == null) {
			if (chungTu == null)
				return 0;

			return 1;
		}

		if (nvkt.getChungTu().getNgayHt() == null) {
			if (chungTu == null)
				return -1;

			if (chungTu.getNgayHt() == null)
				return 0;

			return 1;
		}

		if (chungTu == null || chungTu.getNgayHt() == null)
			return -1;

		int ngayHtRs = chungTu.getNgayHt().compareTo(nvkt.getChungTu().getNgayHt());

		if (ngayHtRs == 0) {
			if (chungTu.getMaCt() > nvkt.getChungTu().getMaCt()) {
				return 1;
			} else if (chungTu.getMaCt() == nvkt.getChungTu().getMaCt()) {
				return 0;
			} else {
				return -1;
			}
		} else {
			return ngayHtRs;
		}
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
