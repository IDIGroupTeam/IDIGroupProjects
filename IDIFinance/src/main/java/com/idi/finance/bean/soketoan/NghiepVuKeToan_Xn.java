package com.idi.finance.bean.soketoan;

import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.TaiKhoan_Xn;
import com.idi.finance.bean.chungtu.Tien;
import com.idi.finance.bean.hanghoa.HangHoa;

public class NghiepVuKeToan_Xn implements Comparable<NghiepVuKeToan_Xn> {
	private ChungTu chungTu;
	private HangHoa hangHoa;
	private double soLuongCt;
	private double soLuongTn;
	private Tien donGia;
	private TaiKhoan_Xn taiKhoanNo;
	private TaiKhoan_Xn taiKhoanCo;

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

	public double getSoLuongCt() {
		return soLuongCt;
	}

	public void setSoLuongCt(double soLuongCt) {
		this.soLuongCt = soLuongCt;
	}

	public double getSoLuongTn() {
		return soLuongTn;
	}

	public void setSoLuongTn(double soLuongTn) {
		this.soLuongTn = soLuongTn;
	}

	public Tien getDonGia() {
		return donGia;
	}

	public void setDonGia(Tien donGia) {
		this.donGia = donGia;
	}

	public TaiKhoan_Xn getTaiKhoanNo() {
		return taiKhoanNo;
	}

	public void setTaiKhoanNo(TaiKhoan_Xn taiKhoanNo) {
		this.taiKhoanNo = taiKhoanNo;
	}

	public TaiKhoan_Xn getTaiKhoanCo() {
		return taiKhoanCo;
	}

	public void setTaiKhoanCo(TaiKhoan_Xn taiKhoanCo) {
		this.taiKhoanCo = taiKhoanCo;
	}

	@Override
	public int compareTo(NghiepVuKeToan_Xn nvkt) {
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

		if (!(obj instanceof NghiepVuKeToan_Xn)) {
			return false;
		}

		NghiepVuKeToan_Xn item = (NghiepVuKeToan_Xn) obj;
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
