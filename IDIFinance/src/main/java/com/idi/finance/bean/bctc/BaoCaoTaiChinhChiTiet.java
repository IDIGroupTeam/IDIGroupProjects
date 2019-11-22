package com.idi.finance.bean.bctc;

import java.util.ArrayList;
import java.util.List;

import com.idi.finance.bean.Tien;

public class BaoCaoTaiChinhChiTiet {
	private BaoCaoTaiChinhCon bctc;
	private BalanceAssetItem asset;
	private Tien giaTri = new Tien();
	private Tien giaTriKyTruoc = new Tien();

	private BaoCaoTaiChinhChiTiet chiTiet;
	private List<BaoCaoTaiChinhChiTiet> chiTietDs;

	public BaoCaoTaiChinhCon getBctc() {
		if (bctc == null) {
			bctc = new BaoCaoTaiChinhCon();
		}
		return bctc;
	}

	public void setBctc(BaoCaoTaiChinhCon bctc) {
		this.bctc = bctc;
	}

	public BalanceAssetItem getAsset() {
		if (asset == null) {
			asset = new BalanceAssetItem();
		}
		return asset;
	}

	public void setAsset(BalanceAssetItem asset) {
		this.asset = asset;
	}

	public Tien getGiaTri() {
		if (giaTri == null) {
			giaTri = new Tien();
		}
		return giaTri;
	}

	public void setGiaTri(Tien giaTri) {
		this.giaTri = giaTri;
	}

	public Tien getGiaTriKyTruoc() {
		if (giaTriKyTruoc == null) {
			giaTriKyTruoc = new Tien();
		}
		return giaTriKyTruoc;
	}

	public void setGiaTriKyTruoc(Tien giaTriKyTruoc) {
		this.giaTriKyTruoc = giaTriKyTruoc;
	}

	public BaoCaoTaiChinhChiTiet getChiTiet() {
		return chiTiet;
	}

	public void setChiTiet(BaoCaoTaiChinhChiTiet chiTiet) {
		this.chiTiet = chiTiet;
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
		String out = bctc + "  " + asset + " " + giaTri + " " + giaTriKyTruoc;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof BaoCaoTaiChinhChiTiet)) {
			return false;
		}

		BaoCaoTaiChinhChiTiet item = (BaoCaoTaiChinhChiTiet) obj;
		try {
			if (bctc == null) {
				if (item.getBctc() != null)
					return false;
			} else if (item.getBctc() == null) {
				return false;
			} else if (!bctc.equals(item.getBctc())) {
				return false;
			}

			if (asset == null) {
				if (item.getAsset() != null)
					return false;
			} else if (item.getAsset() == null) {
				return false;
			} else if (!asset.equals(item.getAsset())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
