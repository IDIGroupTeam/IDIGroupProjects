package com.idi.finance.charts;

import java.util.Date;
import java.util.Map;

import org.jfree.data.xy.XYDataset;
import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.tooltips.XYToolTipGenerator;

public abstract class KpiMeasureChart implements DatasetProducer, XYToolTipGenerator {

	@Override
	public abstract String generateToolTip(XYDataset data, int series, int item);

	@Override
	public abstract Object produceDataset(Map<String, Object> params) throws DatasetProduceException;

	@Override
	public abstract boolean hasExpired(Map params, Date since);

	@Override
	public abstract String getProducerId();
}
