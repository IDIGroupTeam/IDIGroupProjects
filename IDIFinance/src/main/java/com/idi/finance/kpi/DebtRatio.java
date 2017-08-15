package com.idi.finance.kpi;

public class DebtRatio extends KpiProperties {
	private double totalDebt;
	private double totalAsset;

	public double getTotalDebt() {
		return totalDebt;
	}

	public void setTotalDebt(double totalDebt) {
		this.totalDebt = totalDebt;
	}

	public double getTotalAsset() {
		return totalAsset;
	}

	public void setTotalAsset(double totalAsset) {
		this.totalAsset = totalAsset;
	}

	@Override
	public String toString() {
		String result = getPeriod() + " " + getValue() + " " + totalDebt + " " + totalAsset + " " + getEvaluate() + " "
				+ getThresold();
		return result;
	}
}
