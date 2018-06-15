package com.idi.finance.bean.chungtu;

import com.idi.finance.bean.cdkt.BalanceAssetItem;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public class TaiKhoan {
	private ChungTu chungTu;
	private LoaiTaiKhoan loaiTaiKhoan;
	private Tien soTien = new Tien();
	private int soDu = -1;
	private String lyDo;
	private BalanceAssetItem bai;
	private int maNvkt;
	private int nhomDk;

	public ChungTu getChungTu() {
		return chungTu;
	}

	public void setChungTu(ChungTu chungTu) {
		this.chungTu = chungTu;
	}

	public LoaiTaiKhoan getLoaiTaiKhoan() {
		return loaiTaiKhoan;
	}

	public void setLoaiTaiKhoan(LoaiTaiKhoan loaiTaiKhoan) {
		this.loaiTaiKhoan = loaiTaiKhoan;
	}

	public Tien getSoTien() {
		return soTien;
	}

	public void setSoTien(Tien soTien) {
		this.soTien = soTien;
	}

	public int getSoDu() {
		return soDu;
	}

	public void setSoDu(int soDu) {
		this.soDu = soDu;
	}

	public String getLyDo() {
		return lyDo;
	}

	public void setLyDo(String lyDo) {
		this.lyDo = lyDo;
	}

	public BalanceAssetItem getBai() {
		return bai;
	}

	public void setBai(BalanceAssetItem bai) {
		this.bai = bai;
	}

	public int getMaNvkt() {
		return maNvkt;
	}

	public void setMaNvkt(int maNvkt) {
		this.maNvkt = maNvkt;
	}

	public int getNhomDk() {
		return nhomDk;
	}

	public void setNhomDk(int nhomDk) {
		this.nhomDk = nhomDk;
	}

	@Override
	public String toString() {
		String out = loaiTaiKhoan + " - " + soDu;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof TaiKhoan)) {
			return false;
		}

		TaiKhoan item = (TaiKhoan) obj;
		try {
			if (chungTu == null) {
				if (item.getChungTu() != null)
					return false;
			} else if (item.getChungTu() == null) {
				return false;
			} else if (!chungTu.equals(item.getChungTu())) {
				return false;
			}

			if (loaiTaiKhoan == null) {
				if (item.getLoaiTaiKhoan() != null)
					return false;
			} else if (item.getLoaiTaiKhoan() == null) {
				return false;
			} else if (!loaiTaiKhoan.equals(item.getLoaiTaiKhoan())) {
				return false;
			}

			if (soDu != item.getSoDu())
				return false;
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
