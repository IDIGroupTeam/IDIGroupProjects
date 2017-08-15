package com.idi.finance.charts.debtratio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Week;
import org.jfree.data.xy.XYDataset;

import com.idi.finance.kpi.DebtRatio;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.tooltips.XYToolTipGenerator;

public class DebtRatioLineChart implements DatasetProducer, XYToolTipGenerator {
	private static final Logger logger = Logger.getLogger(DebtRatioLineChart.class);
	private HashMap<Date, DebtRatio> debtRatios;
	private double threshold = 1.0;

	public DebtRatioLineChart(HashMap<Date, DebtRatio> debtRatios, double threshold) {
		this.debtRatios = debtRatios;
		this.threshold = threshold;
	}

	@Override
	public Object produceDataset(Map<String, Object> map) throws DatasetProduceException {
		TimeSeriesCollection dataset = new TimeSeriesCollection();

		TimeSeries series = new TimeSeries("Tiêu chuẩn");
		dataset.addSeries(series);

		if (debtRatios != null && debtRatios.size() > 0) {
			List<Date> periods = new ArrayList<>(debtRatios.keySet());
			Collections.sort(periods);

			Iterator<Date> iter = periods.iterator();
			while (iter.hasNext()) {
				Date period = iter.next();
				Week week = new Week(period);
				series.add(week, threshold);
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
		return "DebtRatioLineChart";
	}

	@Override
	public boolean hasExpired(Map arg0, Date arg1) {
		return true;
	}
}
