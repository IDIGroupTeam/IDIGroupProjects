package com.idi.finance.bean.cdkt;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.idi.finance.utils.Utils;

public class KyKeToanCon {
	public static final int NAN = -1;
	public static final int WEEK = 0;
	public static final int MONTH = 1;
	public static final int QUARTER = 2;
	public static final int YEAR = 3;

	private int loai = MONTH;
	private Date dau = Utils.getStartPeriod(new Date(), MONTH);
	private Date cuoi = Utils.getEndPeriod(new Date(), MONTH);

	public KyKeToanCon() {
		loai = MONTH;
		dau = Utils.getStartPeriod(new Date(), loai);
		cuoi = Utils.getEndPeriod(new Date(), loai);
	}

	public KyKeToanCon(Date date) {
		loai = MONTH;
		dau = Utils.getStartPeriod(date, loai);
		cuoi = Utils.getEndPeriod(date, loai);
	}

	public KyKeToanCon(Date date, int loai) {
		this.loai = loai;
		dau = Utils.getStartPeriod(date, this.loai);
		cuoi = Utils.getEndPeriod(date, this.loai);
	}

	public int getLoai() {
		return loai;
	}

	public void setLoai(int loai) {
		this.loai = loai;
	}

	public Date getDau() {
		return dau;
	}

	public void setDau(Date dau) {
		this.dau = dau;
	}

	public Date getCuoi() {
		return cuoi;
	}

	public void setCuoi(Date cuoi) {
		this.cuoi = cuoi;
	}

	public KyKeToanCon kyTruoc() {
		return Utils.prevPeriod(this);
	}

	public KyKeToanCon kySau() {
		return Utils.nextPeriod(this);
	}

	public boolean truoc(KyKeToanCon kyKeToan) throws Exception {
		if (kyKeToan == null || kyKeToan.getDau() == null) {
			return false;
		}

		if (this.getLoai() != kyKeToan.getLoai())
			throw new Exception("Không so sánh được hai kỳ kế toán khác loại nhau.");

		return this.getDau().before(kyKeToan.getDau());
	}

	public boolean sau(KyKeToanCon kyKeToan) throws Exception {
		if (kyKeToan == null || kyKeToan.getDau() == null) {
			return true;
		}

		if (this.getLoai() != kyKeToan.getLoai())
			throw new Exception("Không so sánh được hai kỳ kế toán khác loại nhau.");

		return this.getDau().after(kyKeToan.getDau());
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		String batDau = sdf.format(dau);
		String ketThuc = sdf.format(cuoi);

		String out = loai + "  " + batDau + " " + ketThuc;
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

		KyKeToanCon item = (KyKeToanCon) obj;
		try {
			if (loai != item.getLoai()) {
				return false;
			}

			if (dau == null) {
				if (item.getDau() != null)
					return false;
			} else if (item.getDau() == null) {
				return false;
			} else if (!dau.equals(item.getDau())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
