package com.idi.finance.kpi;

public class ReceivableTurnover extends KpiProperties {
	private double totalOperatingRevenue;
	private double startReceivable;
	private double endReceivable;

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

	@Override
	public String toString() {
		String result = getPeriod() + " " + getValue() + " " + totalOperatingRevenue + " " + startReceivable + " "
				+ endReceivable + " " + getEvaluate() + " " + getThreshold();
		return result;
	}
}
