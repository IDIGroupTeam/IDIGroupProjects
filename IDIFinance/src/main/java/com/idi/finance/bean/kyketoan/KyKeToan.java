package com.idi.finance.bean.kyketoan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class KyKeToan {
	public static final int MO = 1;
	public static final int DONG = 0;
	public static final int MAC_DINH = 1;
	public static final int KHONG_MAC_DINH = 0;

	private int maKyKt;
	private String tenKyKt;
	private Date batDau;
	private Date ketThuc;
	private int trangThai;
	private int macDinh;
	private List<SoDuKy> soDuKyDs;
	private boolean dau;

	public int getMaKyKt() {
		return maKyKt;
	}

	public void setMaKyKt(int maKyKt) {
		this.maKyKt = maKyKt;
	}

	public String getTenKyKt() {
		return tenKyKt;
	}

	public void setTenKyKt(String tenKyKt) {
		this.tenKyKt = tenKyKt;
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

	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

	public int getMacDinh() {
		return macDinh;
	}

	public void setMacDinh(int macDinh) {
		this.macDinh = macDinh;
	}

	public List<SoDuKy> getSoDuKyDs() {
		return soDuKyDs;
	}

	public void setSoDuKyDs(List<SoDuKy> soDuKyDs) {
		this.soDuKyDs = soDuKyDs;
	}

	public void themSoDuKy(SoDuKy soDuKy) {
		if (soDuKy == null)
			return;

		if (soDuKyDs == null)
			soDuKyDs = new ArrayList<>();

		if (!soDuKyDs.contains(soDuKy)) {
			soDuKyDs.add(soDuKy);
		}
	}

	public void themSoDuKy(List<SoDuKy> soDuKyDs) {
		if (soDuKyDs == null)
			return;

		Iterator<SoDuKy> iter = soDuKyDs.iterator();
		while (iter.hasNext()) {
			SoDuKy soDuKy = iter.next();
			themSoDuKy(soDuKy);
		}
	}

	public boolean isDau() {
		return dau;
	}

	public void setDau(boolean dau) {
		this.dau = dau;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		String out = maKyKt + "";

		try {
			out = maKyKt + " " + sdf.format(batDau) + " " + sdf.format(ketThuc);
		} catch (Exception e) {
		}

		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof KyKeToan)) {
			return false;
		}

		KyKeToan item = (KyKeToan) obj;
		try {
			if (maKyKt != item.getMaKyKt()) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
