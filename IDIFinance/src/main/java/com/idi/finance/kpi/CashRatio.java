package com.idi.finance.kpi;

public class CashRatio extends KpiProperties {
	private double cashEquivalent;
	private double currentLiability;

	public double getCashEquivalent() {
		return cashEquivalent;
	}

	public void setCashEquivalent(double cashEquivalent) {
		this.cashEquivalent = cashEquivalent;
	}

	public double getCurrentLiability() {
		return currentLiability;
	}

	public void setCurrentLiability(double currentLiability) {
		this.currentLiability = currentLiability;
	}

	@Override
	public String toString() {
		String result = getPeriod() + " " + getValue() + " " + cashEquivalent + " " + currentLiability + " "
				+ getEvaluate() + " " + getThresold();
		return result;
	}
}
