package com.idi.finance.form;

public class TkKpiChartForm {
	private int periodType = 1;
	private Integer[] kipCharts;

	public int getPeriodType() {
		return periodType;
	}

	public void setPeriodType(int periodType) {
		this.periodType = periodType;
	}

	public Integer[] getKipCharts() {
		return kipCharts;
	}

	public void setKipCharts(Integer[] kipCharts) {
		this.kipCharts = kipCharts;
	}
}
