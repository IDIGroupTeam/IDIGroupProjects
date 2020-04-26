package com.idi.finance.utils;

import java.util.List;
import java.util.Objects;

import com.idi.finance.bean.bieudo.KpiMeasure;

/**
 * 
 * @author HaiTD
 *
 *         Apr 26, 2020
 */
public class KpiMeasureUtils {
	/**
	 * Get kpi measure by measure Id from list of kpi measures
	 * 
	 * @param measureId
	 *            The measure Id
	 * @param kpiMeasures
	 *            The list of kpi measures
	 * @return A kpi measure
	 */
	public static KpiMeasure getKpiMeasureById(int measureId, List<KpiMeasure> kpiMeasures) {
		if (kpiMeasures == null) {
			return null;
		}

		KpiMeasure kpiMeasureRs = null;
		for (KpiMeasure kpiMeasure : kpiMeasures) {
			if (Objects.nonNull(kpiMeasure) && measureId == kpiMeasure.getMeasureId()) {
				kpiMeasureRs = kpiMeasure;
			}
		}

		return kpiMeasureRs;
	}
}
