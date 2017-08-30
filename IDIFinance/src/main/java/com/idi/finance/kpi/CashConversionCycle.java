package com.idi.finance.kpi;

public class CashConversionCycle extends KpiProperties {
	private double operatingCycle;
	private double avgPaymentPeriod;

	public double getOperatingCycle() {
		return operatingCycle;
	}

	public void setOperatingCycle(double operatingCycle) {
		this.operatingCycle = operatingCycle;
	}

	public double getAvgPaymentPeriod() {
		return avgPaymentPeriod;
	}

	public void setAvgPaymentPeriod(double avgPaymentPeriod) {
		this.avgPaymentPeriod = avgPaymentPeriod;
	}

	// Cần thêm giá trị gì thì sau này thêm sau
	@Override
	public String toString() {
		String result = getPeriod() + " " + getValue() + " " + operatingCycle + " " + avgPaymentPeriod + " "
				+ getEvaluate() + " " + getThreshold();
		return result;
	}
}
