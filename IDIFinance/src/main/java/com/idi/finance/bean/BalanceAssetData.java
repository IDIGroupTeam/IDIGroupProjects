package com.idi.finance.bean;

import java.util.Date;

public class BalanceAssetData {
	private BalanceAssetItem asset;
	private Date period;
	private double startValue;
	private double endValue;
	private double changedRatio;
	private String description;

	public BalanceAssetItem getAsset() {
		return asset;
	}

	public void setAsset(BalanceAssetItem asset) {
		this.asset = asset;
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

	@Override
	public String toString() {
		String out = asset + "  " + period + " " + startValue + " " + endValue + " " + changedRatio;
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
