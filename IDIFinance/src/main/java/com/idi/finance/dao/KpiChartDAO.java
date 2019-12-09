package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.bieudo.KpiChart;
import com.idi.finance.bean.bieudo.KpiGroup;
import com.idi.finance.bean.bieudo.KpiKyBctc;
import com.idi.finance.bean.bieudo.KpiMeasure;

public interface KpiChartDAO {
	public void insertOrUpdateKpiGroups(List<KpiGroup> kpiGroups);

	public void insertOrUpdateKpiCharts(List<KpiChart> kpiCharts);

	public void insertOrUpdateKpiMeasures(List<KpiMeasure> kpiMeasures);

	public int getActiveKpiGroupId();

	public List<KpiGroup> listKpiGroups();

	public List<KpiChart> listKpiCharts();

	public KpiChart listKpiCharts(int chartId);

	public List<KpiChart> listKpiChartByKpiGroup(KpiGroup kpiGroup);

	public List<KpiChart> listSelectedKpiChartInDetails(Integer[] selectedKpiChartIds);

	public KpiMeasure listKpiMeasureById(String kpiMeasureId);

	public List<KpiKyBctc> listKpiKyBctcs();
}
