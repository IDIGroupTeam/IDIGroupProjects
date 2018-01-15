package com.idi.finance.bean.hanghoa;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NhomHangHoa {
	private int maNhomHh;
	private String tenNhomHh;
	private int doSau = 1;
	private int muc;
	private NhomHangHoa nhomHh;
	private List<NhomHangHoa> nhomHhDs;

	public int getMaNhomHh() {
		return maNhomHh;
	}

	public void setMaNhomHh(int maNhomHh) {
		this.maNhomHh = maNhomHh;
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

	public NhomHangHoa getNhomHh() {
		return nhomHh;
	}

	public void setNhomHh(NhomHangHoa nhomHh) {
		this.nhomHh = nhomHh;
	}

	public List<NhomHangHoa> getNhomHhDs() {
		return nhomHhDs;
	}

	public void setNhomHhDs(List<NhomHangHoa> nhomHhDs) {
		this.nhomHhDs = nhomHhDs;
	}

	public void themNhomHh(NhomHangHoa nhomHangHoa) {
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

	public void themNhomHh(List<NhomHangHoa> nhomHangHoaDs) {
		if (nhomHangHoaDs == null) {
			return;
		}

		Iterator<NhomHangHoa> iter = nhomHangHoaDs.iterator();
		while (iter.hasNext()) {
			themNhomHh(iter.next());
		}
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

		if (!(obj instanceof NhomHangHoa)) {
			return false;
		}

		NhomHangHoa item = (NhomHangHoa) obj;
		try {
			if (maNhomHh != item.getMaNhomHh()) {
				return false;
			}

			if (tenNhomHh == null) {
				if (item.getTenNhomHh() != null)
					return false;
			} else if (item.getTenNhomHh() == null) {
				return false;
			} else if (!tenNhomHh.trim().equals(item.getTenNhomHh().trim())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
