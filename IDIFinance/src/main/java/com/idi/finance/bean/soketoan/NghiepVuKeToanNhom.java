package com.idi.finance.bean.soketoan;

import java.util.ArrayList;
import java.util.List;

import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.KetChuyenButToan;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.taikhoan.TaiKhoan;

public class NghiepVuKeToanNhom implements Comparable<NghiepVuKeToanNhom> {
	private ChungTu chungTu;
	private HangHoa hangHoa;
	private KetChuyenButToan ketChuyenButToan;
	private List<TaiKhoan> taiKhoanNoDs;
	private List<TaiKhoan> taiKhoanCoDs;
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

	public List<TaiKhoan> getTaiKhoanNoDs() {
		return taiKhoanNoDs;
	}

	public void setTaiKhoanNoDs(List<TaiKhoan> taiKhoanNoDs) {
		this.taiKhoanNoDs = taiKhoanNoDs;
	}

	public void themTaiKhoanNo(TaiKhoan taiKhoanNo) {
		if (taiKhoanNo == null)
			return;

		if (taiKhoanNoDs == null)
			taiKhoanNoDs = new ArrayList<>();

		if (!taiKhoanNoDs.contains(taiKhoanNo)) {
			taiKhoanNoDs.add(taiKhoanNo);
		}
	}

	public void themTaiKhoanNo(List<TaiKhoan> taiKhoanNoDs) {
		if (taiKhoanNoDs == null)
			return;

		for (TaiKhoan taiKhoanNo : taiKhoanNoDs) {
			themTaiKhoanNo(taiKhoanNo);
		}
	}

	public List<TaiKhoan> getTaiKhoanCoDs() {
		return taiKhoanCoDs;
	}

	public void setTaiKhoanCoDs(List<TaiKhoan> taiKhoanCoDs) {
		this.taiKhoanCoDs = taiKhoanCoDs;
	}

	public void themTaiKhoanCo(TaiKhoan taiKhoanCo) {
		if (taiKhoanCo == null)
			return;

		if (taiKhoanCoDs == null)
			taiKhoanCoDs = new ArrayList<>();

		if (!taiKhoanCoDs.contains(taiKhoanCo)) {
			taiKhoanCoDs.add(taiKhoanCo);
		}
	}

	public void themTaiKhoanCo(List<TaiKhoan> taiKhoanCoDs) {
		if (taiKhoanCoDs == null)
			return;

		for (TaiKhoan taiKhoanCo : taiKhoanCoDs) {
			themTaiKhoanCo(taiKhoanCo);
		}
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
	public int compareTo(NghiepVuKeToanNhom nvkt) {
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
		String out = chungTu + " : " + hangHoa;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof NghiepVuKeToanNhom)) {
			return false;
		}

		NghiepVuKeToanNhom item = (NghiepVuKeToanNhom) obj;
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

			if (taiKhoanNoDs == null) {
				if (item.getTaiKhoanNoDs() != null)
					return false;
			} else if (item.getTaiKhoanNoDs() == null) {
				return false;
			} else if (!taiKhoanNoDs.equals(item.getTaiKhoanNoDs())) {
				return false;
			}

			if (taiKhoanCoDs == null) {
				if (item.getTaiKhoanCoDs() != null)
					return false;
			} else if (item.getTaiKhoanCoDs() == null) {
				return false;
			} else if (!taiKhoanCoDs.equals(item.getTaiKhoanCoDs())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
