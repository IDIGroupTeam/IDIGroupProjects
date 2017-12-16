package com.idi.finance.bean.cdkt;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BalanceAssetData {
	private BalanceAssetItem asset;
	private int periodType = 1;
	private Date period;
	private double startValue;
	private double endValue;
	private double changedRatio;
	private String description;

	private List<BalanceAssetData> childs;

	public BalanceAssetItem getAsset() {
		return asset;
	}

	public void setAsset(BalanceAssetItem asset) {
		this.asset = asset;
	}

	public int getPeriodType() {
		return periodType;
	}

	public void setPeriodType(int periodType) {
		this.periodType = periodType;
	}

	public Date getPeriod() {
		return period;
	}

	public void setPeriod(Date period) {
		this.period = period;
	}

	public double getStartValue() {
		return startValue;
	}

	public void setStartValue(double startValue) {
		this.startValue = startValue;
	}

	public double getEndValue() {
		return endValue;
	}

	public void setEndValue(double endValue) {
		this.endValue = endValue;
	}

	public double getChangedRatio() {
		return changedRatio;
	}

	public void setChangedRatio(double changedRatio) {
		this.changedRatio = changedRatio;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<BalanceAssetData> getChilds() {
		return childs;
	}

	public void addChild(BalanceAssetData bad) {
		if (bad == null)
			return;

		if (childs == null)
			childs = new ArrayList<>();

		if (!childs.contains(bad))
			childs.add(bad);
	}

	public void addChild(List<BalanceAssetData> bads) {
		if (bads == null)
			return;

		Iterator<BalanceAssetData> iter = bads.iterator();
		while (iter.hasNext()) {
			addChild(iter.next());
		}
	}

	public void setChilds(List<BalanceAssetData> childs) {
		this.childs = childs;
	}

	@Override
	public String toString() {
		String out = asset + "  " + periodType + " " + period + " " + endValue;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof BalanceAssetData)) {
			return false;
		}

		BalanceAssetData item = (BalanceAssetData) obj;
		try {
			if (asset == null) {
				if (item.getAsset() != null)
					return false;
			} else if (item.getAsset() == null) {
				return false;
			} else if (!asset.equals(item.getAsset())) {
				return false;
			}

			if (periodType != item.getPeriodType()) {
				return false;
			}

			if (period == null) {
				if (item.getPeriod() != null)
					return false;
			} else if (item.getPeriod() == null) {
				return false;
			} else if (!period.equals(item.getPeriod())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
