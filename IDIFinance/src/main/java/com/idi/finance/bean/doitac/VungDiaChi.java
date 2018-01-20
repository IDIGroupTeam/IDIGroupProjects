package com.idi.finance.bean.doitac;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VungDiaChi {
	public static final String TC = "TC";

	private String maDc;
	private String tenDc;
	private VungDiaChi vungDiaChi;
	private List<VungDiaChi> vungDiaChiDs;
	private CapDiaChi cap;

	public String getMaDc() {
		return maDc;
	}

	public void setMaDc(String maDc) {
		this.maDc = maDc;
	}

	public String getTenDc() {
		return tenDc;
	}

	public void setTenDc(String tenDc) {
		this.tenDc = tenDc;
	}

	public VungDiaChi getVungDiaChi() {
		return vungDiaChi;
	}

	public void setVungDiaChi(VungDiaChi vungDiaChi) {
		this.vungDiaChi = vungDiaChi;
	}

	public List<VungDiaChi> getVungDiaChiDs() {
		if (vungDiaChiDs == null)
			vungDiaChiDs = new ArrayList<>();
		return vungDiaChiDs;
	}

	public void setVungDiaChiDs(List<VungDiaChi> vungDiaChiDs) {
		this.vungDiaChiDs = vungDiaChiDs;
	}

	public void themVungDiaChi(VungDiaChi vungDiaChi) {
		if (vungDiaChi == null) {
			return;
		}

		if (vungDiaChiDs == null)
			vungDiaChiDs = new ArrayList<>();

		if (!vungDiaChiDs.contains(vungDiaChi)) {
			vungDiaChi.setVungDiaChi(this);
			vungDiaChiDs.add(vungDiaChi);
		}
	}

	public void themVungDiaChi(List<VungDiaChi> vungDiaChiDs) {
		if (vungDiaChiDs == null) {
			return;
		}

		Iterator<VungDiaChi> iter = vungDiaChiDs.iterator();
		while (iter.hasNext()) {
			themVungDiaChi(iter.next());
		}
	}

	public CapDiaChi getCap() {
		return cap;
	}

	public void setCap(CapDiaChi cap) {
		this.cap = cap;
	}

	@Override
	public String toString() {
		// String out = maDc + " " + cap.getTenCapDc() + " " + tenDc;
		String out = cap.getTenCapDc() + " " + tenDc;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof VungDiaChi)) {
			return false;
		}

		VungDiaChi item = (VungDiaChi) obj;
		try {
			if (maDc == null) {
				if (item.getMaDc() != null)
					return false;
			} else if (item.getMaDc() == null) {
				return false;
			} else if (!maDc.trim().equals(item.getMaDc().trim())) {
				return false;
			}

			/*
			 * if (tenDc == null) { if (item.getTenDc() != null) return false; } else if
			 * (item.getTenDc() == null) { return false; } else if
			 * (!tenDc.trim().equals(item.getTenDc().trim())) { return false; }
			 */
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
