package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.bieudo.KpiChart;
import com.idi.finance.bean.bieudo.KpiGroup;
import com.idi.finance.bean.bieudo.KpiKyBctc;
import com.idi.finance.bean.bieudo.KpiMeasure;

public interface KpiChartDAO {
	public int updateKpiChart(KpiChart kpiChart);

	public int updateKpiMeasure(KpiMeasure kpiMeasure);

	public void insertOrUpdateKpiGroups(List<KpiGroup> kpiGroups);

	public void insertOrUpdateKpiCharts(List<KpiChart> kpiCharts);

	public void insertOrUpdateKpiMeasures(List<KpiMeasure> kpiMeasures);

	public int getActiveKpiGroupId();

	public List<KpiGroup> listKpiGroups();

	public List<KpiChart> listKpiCharts();

	public KpiChart getKpiChart(int chartId);

	public List<KpiChart> listKpiChartByKpiGroup(KpiGroup kpiGroup);

	public List<KpiChart> listSelectedKpiChartInDetails(Integer[] selectedKpiChartIds);

	public KpiMeasure getKpiMeasureById(String kpiMeasureId);

	public List<KpiKyBctc> listKpiKyBctcsByKyKeToan(int maKyKt);

	public int saveKpiKyBctc(KpiKyBctc kpiKyBctcOld, KpiKyBctc kpiKyBctc);

	public int deleteKpiChart(int charId);

	public int deleteKpiMeasure(int measureId);
}
