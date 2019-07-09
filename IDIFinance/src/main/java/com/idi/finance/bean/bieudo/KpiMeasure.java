package com.idi.finance.bean.bieudo;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

import com.idi.finance.bean.bctc.BalanceAssetItem;

public class KpiMeasure {
	private int measureId;
	private String measureName;
	private String expression;
	private KpiChart chart;
	private int typeChart;

	private SortedMap<Date, Double> values;
	private SortedMap<Date, Double> evaluates;
	private HashMap<KpiMeasure, SortedMap<Date, Double>> kpis;
	private HashMap<BalanceAssetItem, SortedMap<Date, Double>> operands;

	public int getMeasureId() {
		return measureId;
	}

	public void setMeasureId(int measureId) {
		this.measureId = measureId;
	}

	public String getMeasureName() {
		return measureName;
	}

	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public KpiChart getChart() {
		return chart;
	}

	public void setChart(KpiChart chart) {
		this.chart = chart;
	}

	public int getTypeChart() {
		return typeChart;
	}

	public void setTypeChart(int typeChart) {
		this.typeChart = typeChart;
	}

	public SortedMap<Date, Double> getValues() {
		return values;
	}

	public void setValues(SortedMap<Date, Double> values) {
		this.values = values;
	}

	/*
	 * 0: has not data. 1: is good. -1: is bad
	 */
	public SortedMap<Date, Double> getEvaluates() {
		return evaluates;
	}

	public void setEvaluates(SortedMap<Date, Double> evaluates) {
		this.evaluates = evaluates;
	}

	public void addEvaluate(Date key, double value) {
		if (evaluates == null)
			evaluates = new TreeMap<>(new Comparator<Date>() {
				@Override
				public int compare(Date date1, Date date2) {
					return date1.compareTo(date2);
				}
			});

		evaluates.put(key, value);
	}

	public HashMap<KpiMeasure, SortedMap<Date, Double>> getKpis() {
		return kpis;
	}

	public void setKpis(HashMap<KpiMeasure, SortedMap<Date, Double>> kpis) {
		this.kpis = kpis;
	}

	public void addKpis(KpiMeasure key, SortedMap<Date, Double> value) {
		if (key == null || value == null)
			return;

		if (this.kpis == null)
			this.kpis = new HashMap<>();

		this.kpis.put(key, value);
	}

	public HashMap<BalanceAssetItem, SortedMap<Date, Double>> getOperands() {
		return operands;
	}

	public void setOperands(HashMap<BalanceAssetItem, SortedMap<Date, Double>> operands) {
		this.operands = operands;
	}

	@Override
	public String toString() {
		String out = measureId + "  " + measureName + " " + expression + " " + typeChart;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof KpiMeasure)) {
			return false;
		}

		KpiMeasure kpiMeasure = (KpiMeasure) obj;
		try {
			if (measureId != kpiMeasure.getMeasureId()) {
				return false;
			}

			if (measureName == null) {
				if (kpiMeasure.getMeasureName() != null)
					return false;
			} else if (kpiMeasure.getMeasureName() == null) {
				return false;
			} else {
				return measureName.trim().equalsIgnoreCase(kpiMeasure.getMeasureName().trim());
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
