package com.idi.finance.kpi;

import java.util.Date;

public class KpiProperties {
	private Date period;
	private double value;
	private int evaluate;
	private double threshold;

	public Date getPeriod() {
		return period;
	}

	public void setPeriod(Date period) {
		this.period = period;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getEvaluate() {
		return evaluate;
	}

	/*
	 * 0: has not data. 1: is good. -1: is bad
	 */
	public void setEvaluate(int evaluate) {
		this.evaluate = evaluate;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
}
