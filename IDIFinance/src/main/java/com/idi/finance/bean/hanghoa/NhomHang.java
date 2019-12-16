package com.idi.finance.bean.hanghoa;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NhomHang {
	private int maNhomHh;
	private String kyHieuNhomHh;
	private String tenNhomHh;
	private int doSau = 1;
	private int muc;
	private NhomHang nhomHh;
	private List<NhomHang> nhomHhDs;

	private boolean xoa = true;

	public int getMaNhomHh() {
		return maNhomHh;
	}

	public void setMaNhomHh(int maNhomHh) {
		this.maNhomHh = maNhomHh;
	}

	public String getKyHieuNhomHh() {
		return kyHieuNhomHh;
	}

	public void setKyHieuNhomHh(String kyHieuNhomHh) {
		this.kyHieuNhomHh = kyHieuNhomHh;
	}

	public String getTenNhomHh() {
		return tenNhomHh;
	}

	public void setTenNhomHh(String tenNhomHh) {
		this.tenNhomHh = tenNhomHh;
	}

	public int getDoSau() {
		return doSau;
	}

	public void setDoSau(int doSau) {
		this.doSau = doSau;
	}

	public int getMuc() {
		return muc;
	}

	public void setMuc(int muc) {
		this.muc = muc;
	}

	public NhomHang getNhomHh() {
		return nhomHh;
	}

	public void setNhomHh(NhomHang nhomHh) {
		this.nhomHh = nhomHh;
	}

	public List<NhomHang> getNhomHhDs() {
		return nhomHhDs;
	}

	public void setNhomHhDs(List<NhomHang> nhomHhDs) {
		this.nhomHhDs = nhomHhDs;
	}

	public void themNhomHh(NhomHang nhomHangHoa) {
		if (nhomHangHoa == null) {
			return;
		}

		if (nhomHhDs == null)
			nhomHhDs = new ArrayList<>();

		if (!nhomHhDs.contains(nhomHangHoa)) {
			nhomHangHoa.setNhomHh(this);
			nhomHhDs.add(nhomHangHoa);
		}
	}

	public void themNhomHh(List<NhomHang> nhomHangHoaDs) {
		if (nhomHangHoaDs == null) {
			return;
		}

		Iterator<NhomHang> iter = nhomHangHoaDs.iterator();
		while (iter.hasNext()) {
			themNhomHh(iter.next());
		}
	}

	public boolean isXoa() {
		return xoa;
	}

	public void setXoa(boolean xoa) {
		this.xoa = xoa;
	}

	@Override
	public String toString() {
		String out = maNhomHh + "  " + tenNhomHh + " " + doSau + " " + muc;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof NhomHang)) {
			return false;
		}

		NhomHang item = (NhomHang) obj;
		try {
			if (maNhomHh != item.getMaNhomHh()) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
