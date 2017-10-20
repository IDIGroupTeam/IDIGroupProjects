package com.idi.finance.bean.chungtu;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ChungTu {
	private int maCt;
	private int soCt;
	private String loaiCt;
	private Date ngayLap;
	private String lyDo;
	private Tien soTien;
	private int kemTheo;
	private DoiTuong doiTuong;
	private List<TaiKhoan> taiKhoanDs;

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

	public List<TaiKhoan> getTaiKhoanDs() {
		return taiKhoanDs;
	}

	public void setTaiKhoanDs(List<TaiKhoan> taiKhoanDs) {
		this.taiKhoanDs = taiKhoanDs;
	}

	public void themTaiKhoan(TaiKhoan taiKhoan) {
		if (taiKhoan == null) {
			return;
		}

		if (taiKhoanDs == null)
			taiKhoanDs = new ArrayList<>();

		if (!taiKhoanDs.contains(taiKhoan)) {
			taiKhoanDs.add(taiKhoan);
		}
	}

	public void themTaiKhoan(List<TaiKhoan> taiKhoanDs) {
		if (taiKhoanDs == null) {
			return;
		}

		if (this.taiKhoanDs == null)
			this.taiKhoanDs = new ArrayList<>();

		Iterator<TaiKhoan> iter = taiKhoanDs.iterator();
		while (iter.hasNext()) {
			TaiKhoan taiKhoan = iter.next();
			if (!this.taiKhoanDs.contains(taiKhoan)) {
				this.taiKhoanDs.add(taiKhoan);
			}
		}
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

		if (!(obj instanceof ChungTu)) {
			return false;
		}

		ChungTu item = (ChungTu) obj;
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
