package com.idi.finance.kpi;

public class QuickRatio extends KpiProperties {
	private double currentAsset;
	private double inventory;
	private double currentLiability;

	public double getCurrentAsset() {
		return currentAsset;
	}

	public void setCurrentAsset(double currentAsset) {
		this.currentAsset = currentAsset;
	}

	public double getInventory() {
		return inventory;
	}

	public void setInventory(double inventory) {
		this.inventory = inventory;
	}

	public double getCurrentLiability() {
		return currentLiability;
	}

	public void setCurrentLiability(double currentLiability) {
		this.currentLiability = currentLiability;
	}

	@Override
	public String toString() {
		String result = getPeriod() + " " + getValue() + " " + currentAsset + " " + inventory + " " + currentLiability
				+ " " + getEvaluate() + " " + getThreshold();
		return result;
	}
}
