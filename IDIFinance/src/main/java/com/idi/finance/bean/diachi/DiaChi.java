package com.idi.finance.bean.diachi;

public class DiaChi {
	private String diaChi;
	private VungDiaChi vung;

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public VungDiaChi getVung() {
		return vung;
	}

	public void setVung(VungDiaChi vung) {
		this.vung = vung;
	}

	@Override
	public String toString() {
		String out = diaChi + "  " + vung;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof DiaChi)) {
			return false;
		}

		DiaChi item = (DiaChi) obj;
		try {
			if (vung == null) {
				if (item.getVung() != null)
					return false;
			} else if (item.getVung() == null) {
				return false;
			} else if (!vung.equals(item.getVung())) {
				return false;
			}

			if (diaChi == null) {
				if (item.getDiaChi() != null)
					return false;
			} else if (item.getDiaChi() == null) {
				return false;
			} else if (!diaChi.trim().equals(item.getDiaChi().trim())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
