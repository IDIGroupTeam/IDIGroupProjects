package com.idi.finance.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.idi.finance.bean.KpiChart;
import com.idi.finance.bean.KpiGroup;
import com.idi.finance.bean.KpiMeasure;
import com.idi.finance.dao.KpiChartDAO;

public class KpiChartDAOImpl implements KpiChartDAO {
	private static final Logger logger = Logger.getLogger(KpiChartDAOImpl.class);

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public void insertOrUpdateKpiGroups(List<KpiGroup> kpiGroups) {
		if (kpiGroups == null)
			return;

		String selectKpiGroup = "SELECT GROUP_ID FROM KPI_GROUP WHERE GROUP_NAME=?";
		String insertKpiGroup = "INSERT INTO KPI_GROUP(GROUP_NAME) VALUES(?)";

		Iterator<KpiGroup> iter = kpiGroups.iterator();
		while (iter.hasNext()) {
			KpiGroup kpiGroup = iter.next();

			int count = 0;
			try {
				// update firstly, if now row is updated, we will be insert data
				Object[] objs = { kpiGroup.getGroupName() };
				count = jdbcTmpl.queryForObject(selectKpiGroup, objs, Integer.class);
				kpiGroup.setGroupId(count);
			} catch (Exception e) {
				// e.printStackTrace();
				count = 0;
			}

			try {
				// This is new data, so insert it.
				if (count == 0) {
					GeneratedKeyHolder holder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement statement = con.prepareStatement(insertKpiGroup,
									Statement.RETURN_GENERATED_KEYS);
							statement.setString(1, kpiGroup.getGroupName());
							return statement;
						}
					}, holder);

					kpiGroup.setGroupId(holder.getKey().intValue());
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}

			// logger.info(kpiGroup);

			// Update or Insert kpi charts
			insertOrUpdateKpiCharts(kpiGroup.getKpiCharts());
		}
	}

	@Override
	public void insertOrUpdateKpiCharts(List<KpiChart> kpiCharts) {
		if (kpiCharts == null)
			return;

		String selectKpiChart = "SELECT CHART_ID FROM KPI_CHART WHERE CHART_TITLE=?";
		String insertKpiChart = "INSERT INTO KPI_CHART(CHART_TITLE, CHART_TITLE_EN, GROUP_ID) VALUES(?, ?, ?)";

		Iterator<KpiChart> iter = kpiCharts.iterator();
		while (iter.hasNext()) {
			KpiChart kpiChart = iter.next();
			// logger.info(kpiChart);

			int count = 0;
			try {
				// update firstly, if now row is updated, we will be insert data
				Object[] objs = { kpiChart.getChartTitle() };
				count = jdbcTmpl.queryForObject(selectKpiChart, objs, Integer.class);
			} catch (Exception e) {
				// e.printStackTrace();
				count = 0;
			}

			try {
				// This is new data, so insert it.
				if (count == 0) {
					GeneratedKeyHolder holder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement statement = con.prepareStatement(insertKpiChart,
									Statement.RETURN_GENERATED_KEYS);
							statement.setString(1, kpiChart.getChartTitle());
							statement.setString(2, kpiChart.getChartTitleEn());
							statement.setInt(3, kpiChart.getKpiGroup().getGroupId());
							return statement;
						}
					}, holder);

					kpiChart.setChartId(holder.getKey().intValue());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Update or Insert kpi measures
			insertOrUpdateKpiMeasures(kpiChart.getKpiMeasures());
		}
	}

	@Override
	public void insertOrUpdateKpiMeasures(List<KpiMeasure> kpiMeasures) {
		if (kpiMeasures == null)
			return;

		String selectKpiMeasure = "SELECT MEASURE_ID FROM KPI_MEASURE WHERE MEASURE_NAME=?";
		String insertKpiMeasure = "INSERT INTO KPI_MEASURE(MEASURE_NAME, EXPRESSION, CHART_ID) VALUES(?, ?, ?)";

		Iterator<KpiMeasure> iter = kpiMeasures.iterator();
		while (iter.hasNext()) {
			KpiMeasure kpiMeasure = iter.next();
			logger.info(kpiMeasure);

			int count = 0;
			try {
				// update firstly, if now row is updated, we will be insert data
				Object[] objs = { kpiMeasure.getMeasureName() };
				count = jdbcTmpl.queryForObject(selectKpiMeasure, objs, Integer.class);
			} catch (Exception e) {
				e.printStackTrace();
				count = 0;
			}

			try {
				// This is new data, so insert it.
				if (count == 0) {
					GeneratedKeyHolder holder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement statement = con.prepareStatement(insertKpiMeasure,
									Statement.RETURN_GENERATED_KEYS);
							statement.setString(1, kpiMeasure.getMeasureName());
							statement.setString(2, kpiMeasure.getExpression());
							statement.setInt(3, kpiMeasure.getChart().getChartId());
							return statement;
						}
					}, holder);

					kpiMeasure.setMeasureId(holder.getKey().intValue());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int getActiveKpiGroupId() {
		String query = "SELECT MIN(GROUP_ID) FROM KPI_GROUP";

		// logger.info("Get min kpi group id");
		// logger.info(query);

		Integer groupId = jdbcTmpl.queryForObject(query, Integer.class);
		// logger.info(groupId);

		if (groupId != null)
			return groupId.intValue();

		return 0;
	}

	@Override
	public List<KpiGroup> listKpiGroups() {
		String query = "SELECT * FROM KPI_GROUP ORDER BY GROUP_ID";

		// logger.info("Get list of kpi group ...");
		// logger.info(query);

		List<KpiGroup> kipGroups = jdbcTmpl.query(query, new KpiGroupMapper());

		return kipGroups;
	}

	public class KpiGroupMapper implements RowMapper<KpiGroup> {
		public KpiGroup mapRow(ResultSet rs, int rowNum) throws SQLException {

			KpiGroup kpiGroup = new KpiGroup();
			kpiGroup.setGroupId(rs.getInt("GROUP_ID"));
			kpiGroup.setGroupName(rs.getString("GROUP_NAME"));

			// logger.info(kpiGroup);

			return kpiGroup;
		}
	}

	@Override
	public List<KpiChart> listKpiChartByKpiGroup(KpiGroup kpiGroup) {
		String query = "SELECT * FROM KPI_CHART WHERE GROUP_ID=? ORDER BY CHART_ID";

		logger.info("Get list of kpi charts of kpi group '" + kpiGroup.getGroupName() + "' ...");
		logger.info(query);

		Object[] objs = { kpiGroup.getGroupId() };
		List<KpiChart> kpiCharts = jdbcTmpl.query(query, objs, new KpiChartMapper());
		kpiGroup.setKpiCharts(kpiCharts);

		return kpiCharts;
	}

	public class KpiChartMapper implements RowMapper<KpiChart> {
		public KpiChart mapRow(ResultSet rs, int rowNum) throws SQLException {

			KpiChart kpiChart = new KpiChart();
			kpiChart.setChartId(rs.getInt("CHART_ID"));
			kpiChart.setHomeFlag(rs.getBoolean("HOME_FLAG"));
			kpiChart.setChartTitle(rs.getString("CHART_TITLE"));
			kpiChart.setThreshold(rs.getDouble("THRESHOLD"));
			
			// logger.info(kpiChart);

			return kpiChart;
		}
	}

	@Override
	public List<KpiChart> listSelectedKpiChartInDetails(Integer[] selectedKpiChartIds) {
		if (selectedKpiChartIds == null) {
			return null;
		}

		String kpiChartQuery = "SELECT * FROM KPI_CHART WHERE CHART_ID=? ORDER BY CHART_ID";
		String kpiMeasureQuery = "SELECT * FROM KPI_MEASURE WHERE CHART_ID=? ORDER BY MEASURE_ID";

		logger.info("Get list of selected kpi charts ...");
		logger.info(kpiChartQuery);
		List<KpiChart> kpiCharts = new ArrayList<>();
		for (Integer chartId : selectedKpiChartIds) {
			logger.info("Get list of selected kpi charts in detail, chartId " + chartId + "...");
			logger.info(kpiMeasureQuery);

			Object[] objs = { chartId };
			List<KpiChart> kpiChartTmpls = jdbcTmpl.query(kpiChartQuery, objs, new KpiChartMapper());

			if (kpiChartTmpls != null) {
				KpiChart kpiChart = kpiChartTmpls.get(0);

				List<KpiMeasure> kpiMeasures = jdbcTmpl.query(kpiMeasureQuery, objs, new KpiMeasureMapper());
				kpiChart.addKpiMeasures(kpiMeasures);

				logger.info(kpiChart);
				kpiCharts.add(kpiChart);
			}
		}

		return kpiCharts;
	}

	public class KpiMeasureMapper implements RowMapper<KpiMeasure> {
		public KpiMeasure mapRow(ResultSet rs, int rowNum) throws SQLException {
			KpiMeasure kpiMeasure = new KpiMeasure();
			kpiMeasure.setMeasureId(rs.getInt("MEASURE_ID"));
			kpiMeasure.setMeasureName(rs.getString("MEASURE_NAME"));
			kpiMeasure.setExpression(rs.getString("EXPRESSION"));
			kpiMeasure.setTypeChart(rs.getInt("TYPE_CHART"));

			// logger.info(kpiMeasure);

			return kpiMeasure;
		}
	}

	@Override
	public KpiMeasure listKpiMeasureById(String kpiMeasureId) {
		if (kpiMeasureId == null)
			return null;

		String query = "SELECT * FROM KPI_MEASURE WHERE MEASURE_ID=? ORDER BY MEASURE_ID";

		logger.info("Get kpi measure by measure_id ...");
		logger.info(query);
		logger.info("measure_id " + kpiMeasureId);

		Object[] params = { kpiMeasureId };
		List<KpiMeasure> bads = jdbcTmpl.query(query, params, new KpiMeasureMapper());

		if (bads != null && bads.size() > 0)
			return bads.get(0);

		return null;
	}
}
