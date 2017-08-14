package com.idi.finance.charts.currentratio;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.plot.XYPlot;

import de.laures.cewolf.ChartPostProcessor;

public class CurrentRatioChartProcessor implements ChartPostProcessor, Serializable {
	private static final Logger logger = Logger.getLogger(CurrentRatioChartProcessor.class);

	@Override
	public void processChart(JFreeChart chart, Map<String, String> params) {
		try {
			JFreeChart jChart = (JFreeChart) chart;

			XYPlot xyPlot = jChart.getXYPlot();
			// xyPlot.setBackgroundPaint(new Color(0xC0C0C0));

			DateAxis domainAxis = (DateAxis) xyPlot.getDomainAxis();
			domainAxis.setTickUnit(new DateTickUnit(DateTickUnitType.MONTH, 1));

			Locale vn = new Locale("vi", "VN");
			Locale.setDefault(vn);
			SimpleDateFormat sdf = new SimpleDateFormat("MMM", Locale.getDefault());

			domainAxis.setDateFormatOverride(sdf);
			domainAxis.setAutoRange(true);
			// domainAxis.setVerticalTickLabels(true);

			// xyPlot.getRenderer(1).setSeriesPaint(0, new Color(0x9999FF));
			// xyPlot.getRenderer(1).setSeriesPaint(1, new Color(0x993366));
			// xyPlot.getRenderer(1).setSeriesPaint(2, new Color(0xFFFFCC));
			// xyPlot.getRenderer(0).setSeriesPaint(0, new Color(0x00FFFF));

			// xyPlot.setDomainGridlinesVisible(false);
			// xyPlot.setRangeGridlinePaint(new Color(0x3E3E3E));
			// jChart.getLegend().setBackgroundPaint(new Color(0xEEEEEE));
			// jChart.getLegend().setBackgroundPaint(Color.lightGray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
