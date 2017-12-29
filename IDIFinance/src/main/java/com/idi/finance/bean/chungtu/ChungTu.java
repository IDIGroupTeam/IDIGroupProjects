package com.idi.finance.bean.chungtu;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.idi.finance.bean.LoaiTien;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public class ChungTu {
	private static final Logger logger = Logger.getLogger(ChungTu.class);
	// Hằng số cho phần chứng từ: Phiếu thu, chi, báo có, báo nợ
	public static final String TAT_CA = "TAT_CA";
	public static final String CHUNG_TU_PHIEU_THU = "PT";
	public static final String CHUNG_TU_PHIEU_CHI = "PC";
	public static final String CHUNG_TU_BAO_NO = "BN";
	public static final String CHUNG_TU_BAO_CO = "BC";
	public static final String CHUNG_TU_KT_TH = "KTTH";

	private int maCt;
	private int soCt;
	private String loaiCt;
	private Date ngayLap;
	private Date ngayHt;
	private LoaiTien loaiTien = new LoaiTien();
	private Tien soTien = new Tien();
	private String lyDo;
	private int kemTheo;
	private DoiTuong doiTuong;
	private List<TaiKhoan> taiKhoanNoDs;
	private List<TaiKhoan> taiKhoanCoDs;

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

	public Date getNgayHt() {
		return ngayHt;
	}

	public void setNgayHt(Date ngayHt) {
		this.ngayHt = ngayHt;
	}

	public LoaiTien getLoaiTien() {
		return loaiTien;
	}

	public void setLoaiTien(LoaiTien loaiTien) {
		this.loaiTien = loaiTien;
	}

	public Tien getSoTien() {
		return soTien;
	}

	public void setSoTien(Tien soTien) {
		this.soTien = soTien;
	}

	public String getLyDo() {
		return lyDo;
	}

	public void setLyDo(String lyDo) {
		this.lyDo = lyDo;
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

	public void themTaiKhoan(TaiKhoan taiKhoan) {
		if (taiKhoan == null) {
			return;
		}

		if (taiKhoan.getSoDu() == LoaiTaiKhoan.NO) {
			if (taiKhoanNoDs == null)
				taiKhoanNoDs = new ArrayList<>();

			if (!taiKhoanNoDs.contains(taiKhoan)) {
				taiKhoanNoDs.add(taiKhoan);
				soTien.setGiaTri(soTien.getGiaTri()
						+ taiKhoan.getSoTien().getSoTien() * taiKhoan.getSoTien().getLoaiTien().getBanRa());
			}
		} else if (taiKhoan.getSoDu() == LoaiTaiKhoan.CO) {
			if (taiKhoanCoDs == null)
				taiKhoanCoDs = new ArrayList<>();

			if (!taiKhoanCoDs.contains(taiKhoan)) {
				taiKhoanCoDs.add(taiKhoan);
			}
		}

	}

	public void themTaiKhoan(List<TaiKhoan> taiKhoanDs) {
		if (taiKhoanDs == null) {
			return;
		}

		Iterator<TaiKhoan> iter = taiKhoanDs.iterator();
		while (iter.hasNext()) {
			TaiKhoan taiKhoan = iter.next();
			themTaiKhoan(taiKhoan);
		}
	}

	public List<TaiKhoan> getTaiKhoanDs() {
		List<TaiKhoan> taiKhoanDs = new ArrayList<>();
		if (taiKhoanNoDs != null) {
			taiKhoanDs.addAll(taiKhoanNoDs);
		}
		if (taiKhoanCoDs != null) {
			taiKhoanDs.addAll(taiKhoanCoDs);
		}

		return taiKhoanDs;
	}

	public List<TaiKhoan> getTaiKhoanNoDs() {
		return taiKhoanNoDs;
	}

	public void setTaiKhoanNoDs(List<TaiKhoan> taiKhoanNoDs) {
		this.taiKhoanNoDs = taiKhoanNoDs;
	}

	public List<TaiKhoan> getTaiKhoanCoDs() {
		return taiKhoanCoDs;
	}

	public void setTaiKhoanCoDs(List<TaiKhoan> taiKhoanCoDs) {
		this.taiKhoanCoDs = taiKhoanCoDs;
	}

	public int getSoTkLonNhat() {
		int soTkLonNhat = 0;

		if (taiKhoanNoDs == null) {
			if (taiKhoanCoDs != null) {
				soTkLonNhat = taiKhoanCoDs.size();
			}
		} else {
			if (taiKhoanCoDs == null) {
				soTkLonNhat = taiKhoanNoDs.size();
			} else {
				soTkLonNhat = taiKhoanNoDs.size() > taiKhoanCoDs.size() ? taiKhoanNoDs.size() : taiKhoanCoDs.size();
			}
		}

		return soTkLonNhat;
	}

	public int getSoDongNkc() {
		int soDongNkc = 1;

		if (loaiCt.equals(CHUNG_TU_KT_TH)) {
			if (taiKhoanNoDs == null) {
				if (taiKhoanCoDs != null) {
					soDongNkc += taiKhoanCoDs.size();
				}
			} else {
				if (taiKhoanCoDs == null) {
					soDongNkc += taiKhoanNoDs.size();
				} else {
					soDongNkc += taiKhoanNoDs.size() + taiKhoanCoDs.size();
				}
			}
		} else {
			soDongNkc += getSoTkLonNhat();
		}

		return soDongNkc;
	}

	@Override
	public String toString() {
		String out = maCt + " " + loaiCt + " " + soCt;
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
