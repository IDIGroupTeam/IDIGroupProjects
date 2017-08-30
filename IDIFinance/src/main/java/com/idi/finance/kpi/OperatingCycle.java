package com.idi.finance.kpi;

public class OperatingCycle extends KpiProperties {
	private double totalOperatingRevenue;
	private double startReceivable;
	private double endReceivable;

	private double costOfSold;
	private double startInventory;
	private double endInventory;

	public double getTotalOperatingRevenue() {
		return totalOperatingRevenue;
	}

	public void setTotalOperatingRevenue(double totalOperatingRevenue) {
		this.totalOperatingRevenue = totalOperatingRevenue;
	}

	public double getStartReceivable() {
		return startReceivable;
	}

	public void setStartReceivable(double startReceivable) {
		this.startReceivable = startReceivable;
	}

	public double getEndReceivable() {
		return endReceivable;
	}

	public void setEndReceivable(double endReceivable) {
		this.endReceivable = endReceivable;
	}

	public double getCostOfSold() {
		return costOfSold;
	}

	public void setCostOfSold(double costOfSold) {
		this.costOfSold = costOfSold;
	}

	public double getStartInventory() {
		return startInventory;
	}

	public void setStartInventory(double startInventory) {
		this.startInventory = startInventory;
	}

	public double getEndInventory() {
		return endInventory;
	}

	public void setEndInventory(double endInventory) {
		this.endInventory = endInventory;
	}

	@Override
	public String toString() {
		String result = getPeriod() + " " + getValue() + " " + totalOperatingRevenue + " " + startReceivable + " "
				+ endReceivable + " " + costOfSold + " " + startInventory + " " + endInventory + " " + getEvaluate()
				+ " " + getThreshold();
		return result;
	}
}
