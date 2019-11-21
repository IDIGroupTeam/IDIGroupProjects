package com.idi.finance.bean.bctc;

import java.util.ArrayList;
import java.util.List;

public class BaoCaoTaiChinhCon {
	public final static int LOAI_CDKT = 1;
	public final static int LOAI_KQHDKD = 2;
	public final static int LOAI_LCTT = 3;
	public final static int LOAI_CDPS = 4;

	private int maBctcCon;
	private int loaiBctc;
	private BaoCaoTaiChinh bctc;
	List<BaoCaoTaiChinhChiTiet> chiTietDs;

	public int getMaBctcCon() {
		return maBctcCon;
	}

	public void setMaBctcCon(int maBctcCon) {
		this.maBctcCon = maBctcCon;
	}

	public int getLoaiBctc() {
		return loaiBctc;
	}

	public void setLoaiBctc(int loaiBctc) {
		this.loaiBctc = loaiBctc;
	}

	public BaoCaoTaiChinh getBctc() {
		return bctc;
	}

	public void setBctc(BaoCaoTaiChinh bctc) {
		this.bctc = bctc;
	}

	public List<BaoCaoTaiChinhChiTiet> getChiTietDs() {
		return chiTietDs;
	}

	public void setChiTietDs(List<BaoCaoTaiChinhChiTiet> chiTietDs) {
		this.chiTietDs = chiTietDs;
	}

	public void themBctcChiTiet(BaoCaoTaiChinhChiTiet bctcChiTiet) {
		if (bctcChiTiet == null) {
			return;
		}

		if (chiTietDs == null) {
			chiTietDs = new ArrayList<>();
		}

		if (!chiTietDs.contains(bctcChiTiet)) {
			chiTietDs.add(bctcChiTiet);
		}
	}

	public void themBctcChiTiet(List<BaoCaoTaiChinhChiTiet> bctcChiTietDs) {
		if (bctcChiTietDs == null) {
			return;
		}

		for (BaoCaoTaiChinhChiTiet bctcChiTiet : bctcChiTietDs) {
			themBctcChiTiet(bctcChiTiet);
		}
	}

	@Override
	public String toString() {
		String out = maBctcCon + "  " + bctc + " " + loaiBctc;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof BaoCaoTaiChinh)) {
			return false;
		}

		BaoCaoTaiChinhCon item = (BaoCaoTaiChinhCon) obj;
		try {
			if (maBctcCon != item.getMaBctcCon()) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
