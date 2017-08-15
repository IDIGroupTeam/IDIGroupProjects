package com.idi.finance.charts.financialleverage;

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

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.tooltips.XYToolTipGenerator;

import com.idi.finance.kpi.FinancialLeverage;

public class FinancialLeverageBarChart implements DatasetProducer, XYToolTipGenerator {
	private static final Logger logger = Logger.getLogger(FinancialLeverageBarChart.class);
	private HashMap<Date, FinancialLeverage> financialLeverages;

	public FinancialLeverageBarChart(HashMap<Date, FinancialLeverage> financialLeverages) {
		this.financialLeverages = financialLeverages;
	}

	@Override
	public Object produceDataset(Map<String, Object> map) throws DatasetProduceException {
		TimeSeriesCollection dataset = new TimeSeriesCollection();

		TimeSeries series = new TimeSeries("Số liệu");
		dataset.addSeries(series);

		if (financialLeverages != null && financialLeverages.size() > 0) {
			List<Date> periods = new ArrayList<>(financialLeverages.keySet());
			Collections.sort(periods);

			Iterator<Date> iter = periods.iterator();
			while (iter.hasNext()) {
				Date period = iter.next();

				Week week = new Week(period);

				FinancialLeverage currentRatio = financialLeverages.get(period);
				if (currentRatio != null && currentRatio.getValue() > 0) {
					series.add(week, currentRatio.getValue());
				} else {
					series.add(week, null);
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
		logger.info(toolTip);
		return toolTip;
	}

	@Override
	public String getProducerId() {
		return "FinancialLeverageChart";
	}

	@Override
	public boolean hasExpired(Map map, Date date) {
		return true;
	}
}
