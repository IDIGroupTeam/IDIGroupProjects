package com.idi.finance.kpi;

public class CurrentRatio extends KpiProperties {
	private double currentAsset;
	private double currentLiability;

	public double getCurrentAsset() {
		return currentAsset;
	}

	public void setCurrentAsset(double currentAsset) {
		this.currentAsset = currentAsset;
	}

	public double getCurrentLiability() {
		return currentLiability;
	}

	public void setCurrentLiability(double currentLiability) {
		this.currentLiability = currentLiability;
	}

	@Override
	public String toString() {
		String result = getPeriod() + " " + getValue() + " " + currentAsset + " " + currentLiability + " "
				+ getEvaluate() + " " + getThresold();
		return result;
	}
}
