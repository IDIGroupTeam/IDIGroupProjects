package com.idi.finance.charts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Week;
import org.jfree.data.xy.XYDataset;

import com.idi.finance.bean.bieudo.KpiMeasure;

import de.laures.cewolf.DatasetProduceException;

public class KpiMeasureBarChart extends KpiMeasureChart {
	private static final Logger logger = Logger.getLogger(KpiMeasureBarChart.class);
	private KpiMeasure kpiMeasure;

	public KpiMeasureBarChart(KpiMeasure kpiMeasure) {
		this.kpiMeasure = kpiMeasure;
	}

	@Override
	public Object produceDataset(Map<String, Object> map) throws DatasetProduceException {
		String title = "Số liệu";
		if (kpiMeasure != null && kpiMeasure.getMeasureName() != null
				&& !kpiMeasure.getMeasureName().trim().equals("")) {
			title = kpiMeasure.getMeasureName();
		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		TimeSeries series = new TimeSeries(title);
		dataset.addSeries(series);

		if (kpiMeasure != null) {
			if (kpiMeasure.getValues() != null && kpiMeasure.getValues().size() > 0) {
				List<Date> periods = new ArrayList<>(kpiMeasure.getValues().keySet());
				Collections.sort(periods);

				Iterator<Date> iter = periods.iterator();
				while (iter.hasNext()) {
					Date period = iter.next();
					Week week = new Week(period);

					Double value = kpiMeasure.getValues().get(period);
					if (value != null && value > 0) {
						series.add(week, value);
					} else {
						series.add(week, null);
					}
				}
			}
		}

		return dataset;
	}

	@Override
	public String generateToolTip(XYDataset dataset, int series, int point) {
		SimpleDateFormat format = new SimpleDateFormat("MMM-y");
		Date date = new Date(dataset.getX(series, point).longValue());

		String toolTip = "Tháng: " + format.format(date) + "<br>Giá trị: " + dataset.getYValue(series, point);
		return toolTip;
	}

	@Override
	public String getProducerId() {
		return "KpiMeasureBarChart";
	}

	@Override
	public boolean hasExpired(Map map, Date date) {
		return true;
	}
}
