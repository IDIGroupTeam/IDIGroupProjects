package com.idi.finance.bean.soketoan;

import java.util.Date;

import com.idi.finance.bean.chungtu.DoiTuong;
import com.idi.finance.bean.chungtu.TaiKhoan;
import com.idi.finance.bean.chungtu.Tien;

public class NghiepVuKeToan {
	private int maCt;
	private int soCt;
	private String loaiCt;
	private Date ngayLap;
	private String lyDo;
	private Tien soTien;
	private int kemTheo;
	private DoiTuong doiTuong;
	private TaiKhoan taiKhoanCo;
	private TaiKhoan taiKhoanNo;

	public int getMaCt() {
		return maCt;
	}

	public void setMaCt(int maCt) {
		this.maCt = maCt;
	}

	public int getSoCt() {
		return soCt;
	}

	public void setSoCt(int soCt) {
		this.soCt = soCt;
	}

	public String getLoaiCt() {
		return loaiCt;
	}

	public void setLoaiCt(String loaiCt) {
		this.loaiCt = loaiCt;
	}

	public Date getNgayLap() {
		return ngayLap;
	}

	public void setNgayLap(Date ngayLap) {
		this.ngayLap = ngayLap;
	}

	public String getLyDo() {
		return lyDo;
	}

	public void setLyDo(String lyDo) {
		this.lyDo = lyDo;
	}

	public Tien getSoTien() {
		return soTien;
	}

	public void setSoTien(Tien soTien) {
		this.soTien = soTien;
	}

	public int getKemTheo() {
		return kemTheo;
	}

	public void setKemTheo(int kemTheo) {
		this.kemTheo = kemTheo;
	}

	public DoiTuong getDoiTuong() {
		return doiTuong;
	}

	public void setDoiTuong(DoiTuong doiTuong) {
		this.doiTuong = doiTuong;
	}

	public TaiKhoan getTaiKhoanCo() {
		return taiKhoanCo;
	}

	public void setTaiKhoanCo(TaiKhoan taiKhoanCo) {
		this.taiKhoanCo = taiKhoanCo;
	}

	public TaiKhoan getTaiKhoanNo() {
		return taiKhoanNo;
	}

	public void setTaiKhoanNo(TaiKhoan taiKhoanNo) {
		this.taiKhoanNo = taiKhoanNo;
	}

	@Override
	public String toString() {
		String out = maCt + "  " + loaiCt + " " + soCt + " " + ngayLap;
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
			if (maCt != item.getMaCt())
				return false;

			if (soCt != item.getSoCt()) {
				return false;
			}

			if (loaiCt == null) {
				if (item.getLoaiCt() != null)
					return false;
			} else if (item.getLoaiCt() == null) {
				return false;
			} else if (!loaiCt.trim().equals(item.getLoaiCt().trim())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
