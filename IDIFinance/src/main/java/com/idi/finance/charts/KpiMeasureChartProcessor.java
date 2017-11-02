package com.idi.finance.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.labels.BubbleXYItemLabelGenerator;
import org.jfree.chart.labels.HighLowItemLabelGenerator;
import org.jfree.chart.labels.IntervalXYItemLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.SymbolicXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

import de.laures.cewolf.ChartPostProcessor;

public class KpiMeasureChartProcessor implements ChartPostProcessor, Serializable {
	private static final Logger logger = Logger.getLogger(KpiMeasureChartProcessor.class);
	private double threshold = 0;

	public KpiMeasureChartProcessor() {

	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	@Override
	public void processChart(JFreeChart chart, Map<String, String> params) {
		try {
			JFreeChart jChart = (JFreeChart) chart;

			XYPlot xyPlot = jChart.getXYPlot();

			DateAxis domainAxis = (DateAxis) xyPlot.getDomainAxis();
			domainAxis.setTickUnit(new DateTickUnit(DateTickUnitType.MONTH, 1));

			Locale vn = new Locale("vi", "VN");
			Locale.setDefault(vn);
			SimpleDateFormat sdf = new SimpleDateFormat("MMM", Locale.getDefault());

			domainAxis.setDateFormatOverride(sdf);
			domainAxis.setAutoRange(true);

			Font font = xyPlot.getRenderer(1).getBaseItemLabelFont();
			Font newFont = new Font(font.getFontName(), Font.BOLD, font.getSize() + 1);

			NumberFormat format = new DecimalFormat("0.000");
			StandardXYItemLabelGenerator barLabel = new StandardXYItemLabelGenerator(
					StandardXYItemLabelGenerator.DEFAULT_ITEM_LABEL_FORMAT, sdf, format);
			xyPlot.getRenderer(1).setBaseItemLabelGenerator(barLabel);
			xyPlot.getRenderer(1).setBaseItemLabelFont(newFont);
			// xyPlot.getRenderer(1).setBaseItemLabelPaint(Color.RED);
			xyPlot.getRenderer(1).setBaseItemLabelsVisible(true);

			if (threshold > 0) {
				Marker target = new ValueMarker(threshold);
				Paint trans = new Color(255, 255, 255, 0);
				target.setPaint(trans);
				RectangleInsets offset = new RectangleInsets(10, 0, 0, 0);
				target.setLabelOffset(offset);
				target.setLabelFont(newFont);
				// target.setLabelPaint(Color.BLUE);
				target.setLabel("y = " + threshold);
				target.setLabelAnchor(RectangleAnchor.CENTER);
				target.setLabelTextAnchor(TextAnchor.BOTTOM_CENTER);
				xyPlot.addRangeMarker(target);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
