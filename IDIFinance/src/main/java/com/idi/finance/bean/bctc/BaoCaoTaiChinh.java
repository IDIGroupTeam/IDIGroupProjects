package com.idi.finance.bean.bctc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ibm.icu.text.SimpleDateFormat;
import com.idi.finance.bean.kyketoan.KyKeToan;

public class BaoCaoTaiChinh {
	private int maBctc;
	private String tieuDe;
	private int loaiTg;
	private Date batDau;
	private Date ketThuc;
	private String nguoiLap;
	private String giamDoc;
	private Date ngayLap;

	private KyKeToan kyKeToan;
	List<BaoCaoTaiChinhCon> bctcDs;

	private BaoCaoTaiChinhCon bangCdkt;
	private BaoCaoTaiChinhCon bangKqhdkd;
	private BaoCaoTaiChinhCon bangLctt;
	private BaoCaoTaiChinhCon bangCdps;

	private int maBctcCu;

	public int getMaBctc() {
		return maBctc;
	}

	public void setMaBctc(int maBctc) {
		this.maBctc = maBctc;
	}

	public String getTieuDe() {
		return tieuDe;
	}

	public void setTieuDe(String tieuDe) {
		this.tieuDe = tieuDe;
	}

	public int getLoaiTg() {
		return loaiTg;
	}

	public void setLoaiTg(int loaiTg) {
		this.loaiTg = loaiTg;
	}

	public Date getBatDau() {
		return batDau;
	}

	public void setBatDau(Date batDau) {
		this.batDau = batDau;
	}

	public Date getKetThuc() {
		return ketThuc;
	}

	public void setKetThuc(Date ketThuc) {
		this.ketThuc = ketThuc;
	}

	public String getNguoiLap() {
		return nguoiLap;
	}

	public void setNguoiLap(String nguoiLap) {
		this.nguoiLap = nguoiLap;
	}

	public String getGiamDoc() {
		return giamDoc;
	}

	public void setGiamDoc(String giamDoc) {
		this.giamDoc = giamDoc;
	}

	public Date getNgayLap() {
		return ngayLap;
	}

	public void setNgayLap(Date ngayLap) {
		this.ngayLap = ngayLap;
	}

	public KyKeToan getKyKeToan() {
		return kyKeToan;
	}

	public void setKyKeToan(KyKeToan kyKeToan) {
		this.kyKeToan = kyKeToan;
	}

	public List<BaoCaoTaiChinhCon> getBctcDs() {
		return bctcDs;
	}

	public void setBctcDs(List<BaoCaoTaiChinhCon> bctcDs) {
		this.bctcDs = bctcDs;
	}

	public void themBctcCon(BaoCaoTaiChinhCon bctcCon) {
		if (bctcCon == null) {
			return;
		}

		if (bctcDs == null) {
			bctcDs = new ArrayList<>();
		}

		if (!bctcDs.contains(bctcCon)) {
			bctcDs.add(bctcCon);
		}
	}

	public void themBctcCon(List<BaoCaoTaiChinhCon> bctcConDs) {
		if (bctcConDs == null) {
			return;
		}

		for (BaoCaoTaiChinhCon bctcCon : bctcConDs) {
			themBctcCon(bctcCon);
		}
	}

	public BaoCaoTaiChinhCon getBangCdkt() {
		return bangCdkt;
	}

	public void setBangCdkt(BaoCaoTaiChinhCon bangCdkt) {
		this.bangCdkt = bangCdkt;
	}

	public BaoCaoTaiChinhCon getBangKqhdkd() {
		return bangKqhdkd;
	}

	public void setBangKqhdkd(BaoCaoTaiChinhCon bangKqhdkd) {
		this.bangKqhdkd = bangKqhdkd;
	}

	public BaoCaoTaiChinhCon getBangLctt() {
		return bangLctt;
	}

	public void setBangLctt(BaoCaoTaiChinhCon bangLctt) {
		this.bangLctt = bangLctt;
	}

	public BaoCaoTaiChinhCon getBangCdps() {
		return bangCdps;
	}

	public void setBangCdps(BaoCaoTaiChinhCon bangCdps) {
		this.bangCdps = bangCdps;
	}

	public int getMaBctcCu() {
		return maBctcCu;
	}

	public void setMaBctcCu(int maBctcCu) {
		this.maBctcCu = maBctcCu;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String out = maBctc + "  " + tieuDe + "  " + sdf.format(batDau) + " " + sdf.format(ketThuc);
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

		BaoCaoTaiChinh item = (BaoCaoTaiChinh) obj;
		return maBctc == item.getMaBctc();
	}
}
