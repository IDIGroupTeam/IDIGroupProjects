package com.idi.finance.bean.cdkt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.idi.finance.bean.soketoan.NghiepVuKeToan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public class DuLieuKeToan {
	private KyKeToanCon kyKeToan;
	private LoaiTaiKhoan loaiTaiKhoan;

	private double soDuDauKy;
	private double soDuCuoiKy;
	private double tongNoPhatSinh;
	private double tongCoPhatSinh;

	private double noDauKy;
	private double coDauKy;
	private double noCuoiKy;
	private double coCuoiKy;

	private DuLieuKeToan duLieuKeToan;
	private List<DuLieuKeToan> duLieuKeToanDs;
	private List<NghiepVuKeToan> nghiepVuKeToanDs;

	public DuLieuKeToan() {

	}

	public DuLieuKeToan(KyKeToanCon kyKeToan) {
		this.kyKeToan = kyKeToan;
	}

	public DuLieuKeToan(KyKeToanCon kyKeToan, LoaiTaiKhoan loaiTaiKhoan) {
		this.kyKeToan = kyKeToan;
		this.loaiTaiKhoan = loaiTaiKhoan;
	}

	public KyKeToanCon getKyKeToan() {
		return kyKeToan;
	}

	public void setKyKeToan(KyKeToanCon kyKeToan) {
		this.kyKeToan = kyKeToan;
	}

	public LoaiTaiKhoan getLoaiTaiKhoan() {
		return loaiTaiKhoan;
	}

	public void setLoaiTaiKhoan(LoaiTaiKhoan loaiTaiKhoan) {
		this.loaiTaiKhoan = loaiTaiKhoan;
	}

	public double getSoDuDauKy() {
		return soDuDauKy;
	}

	public void setSoDuDauKy(double soDuDauKy) {
		this.soDuDauKy = soDuDauKy;
	}

	public double getSoDuCuoiKy() {
		return soDuCuoiKy;
	}

	public void setSoDuCuoiKy(double soDuCuoiKy) {
		this.soDuCuoiKy = soDuCuoiKy;
	}

	public double getTongNoPhatSinh() {
		return tongNoPhatSinh;
	}

	public void setTongNoPhatSinh(double tongNoPhatSinh) {
		this.tongNoPhatSinh = tongNoPhatSinh;
	}

	public double getTongCoPhatSinh() {
		return tongCoPhatSinh;
	}

	public void setTongCoPhatSinh(double tongCoPhatSinh) {
		this.tongCoPhatSinh = tongCoPhatSinh;
	}

	public double getNoDauKy() {
		return noDauKy;
	}

	public void setNoDauKy(double noDauKy) {
		this.noDauKy = noDauKy;
	}

	public double getCoDauKy() {
		return coDauKy;
	}

	public void setCoDauKy(double coDauKy) {
		this.coDauKy = coDauKy;
	}

	public double getNoCuoiKy() {
		return noCuoiKy;
	}

	public void setNoCuoiKy(double noCuoiKy) {
		this.noCuoiKy = noCuoiKy;
	}

	public double getCoCuoiKy() {
		return coCuoiKy;
	}

	public void setCoCuoiKy(double coCuoiKy) {
		this.coCuoiKy = coCuoiKy;
	}

	public List<NghiepVuKeToan> getNghiepVuKeToanDs() {
		return nghiepVuKeToanDs;
	}

	public void setNghiepVuKeToanDs(List<NghiepVuKeToan> nghiepVuKeToanDs) {
		this.nghiepVuKeToanDs = nghiepVuKeToanDs;
	}

	public void themNghiepVuKeToan(NghiepVuKeToan nghiepVuKeToan) {
		if (nghiepVuKeToan == null) {
			return;
		}

		if (nghiepVuKeToanDs == null) {
			nghiepVuKeToanDs = new ArrayList<>();
		}

		if (!nghiepVuKeToanDs.contains(nghiepVuKeToan)) {
			nghiepVuKeToanDs.add(nghiepVuKeToan);
			Collections.sort(nghiepVuKeToanDs);
		}
	}

	public void themNghiepVuKeToan(List<NghiepVuKeToan> nghiepVuKeToanDs) {
		if (nghiepVuKeToanDs == null) {
			return;
		}

		Iterator<NghiepVuKeToan> iter = nghiepVuKeToanDs.iterator();
		while (iter.hasNext()) {
			themNghiepVuKeToan(iter.next());
		}
	}

	public DuLieuKeToan getDuLieuKeToan() {
		return duLieuKeToan;
	}

	public void setDuLieuKeToan(DuLieuKeToan duLieuKeToan) {
		this.duLieuKeToan = duLieuKeToan;
	}

	public List<DuLieuKeToan> getDuLieuKeToanDs() {
		return duLieuKeToanDs;
	}

	public void setDuLieuKeToanDs(List<DuLieuKeToan> duLieuKeToanDs) {
		this.duLieuKeToanDs = duLieuKeToanDs;
	}

	public void themDuLieuKeToan(DuLieuKeToan duLieuKeToan) {
		if (duLieuKeToan == null) {
			return;
		}

		if (duLieuKeToanDs == null) {
			duLieuKeToanDs = new ArrayList<>();
		}

		if (!duLieuKeToanDs.contains(duLieuKeToan)) {
			duLieuKeToanDs.add(duLieuKeToan);
		}
	}

	public void themDuLieuKeToan(List<DuLieuKeToan> duLieuKeToanDs) {
		if (duLieuKeToanDs == null) {
			return;
		}

		Iterator<DuLieuKeToan> iter = duLieuKeToanDs.iterator();
		while (iter.hasNext()) {
			themDuLieuKeToan(iter.next());
		}
	}

	@Override
	public String toString() {
		String out = kyKeToan + ":  " + loaiTaiKhoan + ": " + soDuDauKy + " " + tongNoPhatSinh + " " + tongCoPhatSinh
				+ " " + soDuCuoiKy;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof KyKeToanCon)) {
			return false;
		}

		DuLieuKeToan item = (DuLieuKeToan) obj;
		try {
			if (kyKeToan == null) {
				if (item.getKyKeToan() != null)
					return false;
			} else if (item.getKyKeToan() == null) {
				return false;
			} else if (!kyKeToan.equals(item.getKyKeToan())) {
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
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
