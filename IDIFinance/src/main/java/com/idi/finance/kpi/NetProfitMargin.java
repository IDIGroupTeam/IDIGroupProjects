package com.idi.finance.kpi;

public class NetProfitMargin extends KpiProperties {
	private double grossProfit;
	private double netIncome;
	private double financeNetIncome;

	public double getGrossProfit() {
		return grossProfit;
	}

	public void setGrossProfit(double grossProfit) {
		this.grossProfit = grossProfit;
	}

	public double getNetIncome() {
		return netIncome;
	}

	public void setNetIncome(double netIncome) {
		this.netIncome = netIncome;
	}

	public double getFinanceNetIncome() {
		return financeNetIncome;
	}

	public void setFinanceNetIncome(double financeNetIncome) {
		this.financeNetIncome = financeNetIncome;
	}

	@Override
	public String toString() {
		String result = getPeriod() + " " + getValue() + " " + grossProfit + " " + netIncome + " " + " "
				+ financeNetIncome + " " + getEvaluate() + " " + getThreshold();
		return result;
	}
}
