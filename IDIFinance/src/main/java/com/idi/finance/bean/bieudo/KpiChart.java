package com.idi.finance.bean.bieudo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;

import com.idi.finance.bean.bctc.BalanceAssetItem;

public class KpiChart {
	private int chartId;
	private String chartTitle;
	private String chartTitleEn;
	private boolean homeFlag;
	private double threshold;
	private KpiGroup kpiGroup;
	private List<KpiMeasure> kpiMeasures;

	private List<Date> dates;
	private HashMap<KpiMeasure, SortedMap<Date, Double>> kpis;
	private HashMap<BalanceAssetItem, SortedMap<Date, Double>> operands;

	public int getChartId() {
		return chartId;
	}

	public void setChartId(int chartId) {
		this.chartId = chartId;
	}

	public String getChartTitle() {
		return chartTitle;
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}

	public String getChartTitleEn() {
		return chartTitleEn;
	}

	public void setChartTitleEn(String chartTitleEn) {
		this.chartTitleEn = chartTitleEn;
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

	public KpiGroup getKpiGroup() {
		return kpiGroup;
	}

	public void setKpiGroup(KpiGroup kpiGroup) {
		this.kpiGroup = kpiGroup;
	}

	public List<KpiMeasure> getKpiMeasures() {
		return kpiMeasures;
	}

	public void setKpiMeasures(List<KpiMeasure> kpiMeasures) {
		this.kpiMeasures = kpiMeasures;
	}

	public void addKpiMeasure(KpiMeasure kpiMeasure) {
		if (kpiMeasure != null) {
			if (kpiMeasures == null)
				kpiMeasures = new ArrayList<>();

			if (!kpiMeasures.contains(kpiMeasure)) {
				kpiMeasures.add(kpiMeasure);
			}

			kpiMeasure.setChart(this);
		}
	}

	public void addKpiMeasures(List<KpiMeasure> kpiMeasures) {
		if (kpiMeasures != null) {
			Iterator<KpiMeasure> iter = kpiMeasures.iterator();
			while (iter.hasNext()) {
				addKpiMeasure(iter.next());
			}
		}
	}

	public List<Date> getDates() {
		return dates;
	}

	public void setDates(List<Date> dates) {
		this.dates = dates;
	}

	public HashMap<KpiMeasure, SortedMap<Date, Double>> getKpis() {
		return kpis;
	}

	public void setKpis(HashMap<KpiMeasure, SortedMap<Date, Double>> kpis) {
		this.kpis = kpis;
	}

	public void addKpis(HashMap<KpiMeasure, SortedMap<Date, Double>> kpis) {
		if (kpis == null)
			return;

		if (this.kpis == null)
			this.kpis = new HashMap<>();

		Iterator<KpiMeasure> iter = kpis.keySet().iterator();
		while (iter.hasNext()) {
			KpiMeasure kpiMeasure = iter.next();
			if (!this.kpis.keySet().contains(kpiMeasure)) {
				this.kpis.put(kpiMeasure, kpis.get(kpiMeasure));
			}
		}
	}

	public HashMap<BalanceAssetItem, SortedMap<Date, Double>> getOperands() {
		return operands;
	}

	public void setOperands(HashMap<BalanceAssetItem, SortedMap<Date, Double>> operands) {
		this.operands = operands;
	}

	public void addOperands(HashMap<BalanceAssetItem, SortedMap<Date, Double>> operands) {
		if (operands == null)
			return;

		if (this.operands == null)
			this.operands = new HashMap<>();

		Iterator<BalanceAssetItem> iter = operands.keySet().iterator();
		while (iter.hasNext()) {
			BalanceAssetItem bai = iter.next();
			if (!this.operands.keySet().contains(bai)) {
				this.operands.put(bai, operands.get(bai));
			}
		}
	}

	@Override
	public String toString() {
		String out = chartId + "  " + chartTitle + " " + homeFlag;
		if (kpiMeasures != null) {
			out += " " + kpiMeasures.size();
		} else {
			out += " 0";
		}
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof KpiChart)) {
			return false;
		}

		KpiChart kpiChart = (KpiChart) obj;
		try {
			if (chartId != kpiChart.getChartId()) {
				return false;
			}

			if (chartTitle == null) {
				if (kpiChart.getChartTitle() != null)
					return false;
			} else if (kpiChart.getChartTitle() == null) {
				return false;
			} else {
				return chartTitle.trim().equalsIgnoreCase(kpiChart.getChartTitle().trim());
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
