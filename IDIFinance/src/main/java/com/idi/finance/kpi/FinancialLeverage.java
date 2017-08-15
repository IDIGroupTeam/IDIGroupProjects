package com.idi.finance.kpi;

public class FinancialLeverage extends KpiProperties {
	private double totalAsset;
	private double totalEquity;

	public double getTotalAsset() {
		return totalAsset;
	}

	public void setTotalAsset(double totalAsset) {
		this.totalAsset = totalAsset;
	}

	public double getTotalEquity() {
		return totalEquity;
	}

	public void setTotalEquity(double totalEquity) {
		this.totalEquity = totalEquity;
	}

	@Override
	public String toString() {
		String result = getPeriod() + " " + getValue() + " " + totalAsset + " " + totalEquity + " " + getEvaluate()
				+ " " + getThresold();
		return result;
	}
}
