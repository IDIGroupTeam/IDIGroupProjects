package com.idi.finance.form;

import java.util.List;

public class KpiChartForm {
	private int chartId;
	private boolean homeFlag;
	private double threshold;
	private List<KpiMeasureForm> kpiMeasures;

	public int getChartId() {
		return chartId;
	}

	public void setChartId(int chartId) {
		this.chartId = chartId;
	}

	public boolean isHomeFlag() {
		return homeFlag;
	}

	public void setHomeFlag(boolean homeFlag) {
		this.homeFlag = homeFlag;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public List<KpiMeasureForm> getKpiMeasures() {
		return kpiMeasures;
	}

	public void setKpiMeasures(List<KpiMeasureForm> kpiMeasures) {
		this.kpiMeasures = kpiMeasures;
	}
}
