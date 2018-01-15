package com.idi.finance.bean.hanghoa;

public class LoaiHangHoa {
	private int maHh;
	private String tenHh;
	private DonVi donVi;
	private NhomHangHoa nhomHh;

	public int getMaHh() {
		return maHh;
	}

	public void setMaHh(int maHh) {
		this.maHh = maHh;
	}

	public String getTenHh() {
		return tenHh;
	}

	public void setTenHh(String tenHh) {
		this.tenHh = tenHh;
	}

	public DonVi getDonVi() {
		return donVi;
	}

	public void setDonVi(DonVi donVi) {
		this.donVi = donVi;
	}

	public NhomHangHoa getNhomHh() {
		return nhomHh;
	}

	public void setNhomHh(NhomHangHoa nhomHh) {
		this.nhomHh = nhomHh;
	}

	@Override
	public String toString() {
		String out = maHh + "  " + tenHh;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof LoaiHangHoa)) {
			return false;
		}

		LoaiHangHoa item = (LoaiHangHoa) obj;
		try {
			if (maHh != item.getMaHh()) {
				return false;
			}

			if (tenHh == null) {
				if (item.getTenHh() != null)
					return false;
			} else if (item.getTenHh() == null) {
				return false;
			} else if (!tenHh.trim().equals(item.getTenHh().trim())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
