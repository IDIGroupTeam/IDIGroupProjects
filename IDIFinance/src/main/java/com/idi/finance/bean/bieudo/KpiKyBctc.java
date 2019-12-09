package com.idi.finance.bean.bieudo;

import com.idi.finance.bean.bctc.BaoCaoTaiChinh;
import com.idi.finance.hangso.KpiMonthCont;

public class KpiKyBctc {
	private KpiMonthCont kpiKy;
	private BaoCaoTaiChinh bctc;

	public KpiMonthCont getKpiKy() {
		return kpiKy;
	}

	public void setKpiKy(KpiMonthCont kpiKy) {
		this.kpiKy = kpiKy;
	}

	public BaoCaoTaiChinh getBctc() {
		return bctc;
	}

	public void setBctc(BaoCaoTaiChinh bctc) {
		this.bctc = bctc;
	}

	@Override
	public String toString() {
		String out = kpiKy + " - " + bctc;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof BaoCaoTaiChinh)) {
			return false;
		}

		KpiKyBctc item = (KpiKyBctc) obj;

		if (kpiKy == null) {
			if (item.getKpiKy() != null)
				return false;
		} else if (item.getKpiKy() == null) {
			return false;
		} else if (kpiKy.getKey() != item.getKpiKy().getKey()) {
			return false;
		}

		if (bctc == null) {
			if (item.getBctc() != null)
				return false;
		} else if (item.getBctc() == null) {
			return false;
		} else {
			return bctc.equals(item.getBctc());
		}

		return true;
	}
}
