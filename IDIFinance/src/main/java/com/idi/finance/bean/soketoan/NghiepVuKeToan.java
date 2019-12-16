package com.idi.finance.bean.soketoan;

import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.KetChuyenButToan;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.taikhoan.TaiKhoan;

public class NghiepVuKeToan implements Comparable<NghiepVuKeToan> {
	private ChungTu chungTu;
	private HangHoa hangHoa;
	private KetChuyenButToan ketChuyenButToan;
	private TaiKhoan taiKhoanNo;
	private TaiKhoan taiKhoanCo;
	private double ton;
	private double slTon;

	public ChungTu getChungTu() {
		return chungTu;
	}

	public void setChungTu(ChungTu chungTu) {
		this.chungTu = chungTu;
	}

	public HangHoa getHangHoa() {
		return hangHoa;
	}

	public void setHangHoa(HangHoa hangHoa) {
		this.hangHoa = hangHoa;
	}

	public KetChuyenButToan getKetChuyenButToan() {
		return ketChuyenButToan;
	}

	public void setKetChuyenButToan(KetChuyenButToan ketChuyenButToan) {
		this.ketChuyenButToan = ketChuyenButToan;
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

	public double getTon() {
		return ton;
	}

	public void setTon(double ton) {
		this.ton = ton;
	}

	public double getSlTon() {
		return slTon;
	}

	public void setSlTon(double slTon) {
		this.slTon = slTon;
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
		String out = chungTu + " : " + hangHoa + " : " + taiKhoanNo + " : " + taiKhoanCo;
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

			if (hangHoa == null) {
				if (item.getHangHoa() != null)
					return false;
			} else if (item.getHangHoa() == null) {
				return false;
			} else if (!hangHoa.equals(item.getHangHoa())) {
				return false;
			}

			if (ketChuyenButToan == null) {
				if (item.getKetChuyenButToan() != null)
					return false;
			} else if (item.getKetChuyenButToan() == null) {
				return false;
			} else if (!ketChuyenButToan.equals(item.getKetChuyenButToan())) {
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
