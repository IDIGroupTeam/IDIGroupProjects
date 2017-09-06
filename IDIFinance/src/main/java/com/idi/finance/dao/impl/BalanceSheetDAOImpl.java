package com.idi.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.idi.finance.bean.BalanceSheet;
import com.idi.finance.dao.BalanceSheetDAO;

public class BalanceSheetDAOImpl implements BalanceSheetDAO {
	private static final Logger logger = Logger.getLogger(BalanceSheetDAOImpl.class);

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public void insertOrUpdateBSs(List<BalanceSheet> bss) {
		if (bss == null)
			return;

		String updateQry = "UPDATE BALANCE_SHEET SET END_VALUE=?, START_VALUE=?, CHANGED_RATIO=? WHERE ASSETS_CODE=? AND ASSETS_PERIOD=?";
		String insertQry = "INSERT INTO BALANCE_SHEET(ASSETS_NAME, RULE, ASSETS_CODE, NOTE, END_VALUE, START_VALUE, CHANGED_RATIO, ASSETS_PERIOD) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

		Iterator<BalanceSheet> iter = bss.iterator();
		while (iter.hasNext()) {
			BalanceSheet bs = iter.next();
			logger.info(bs);

			int count = 0;
			try {
				// update firstly, if now row is updated, we will be insert data
				Timestamp assetsPeriod = new Timestamp(bs.getAssetsPeriod().getTime());
				count = jdbcTmpl.update(updateQry, bs.getEndValue(), bs.getStartValue(), bs.getChangedRatio(),
						bs.getAssetsCode(), assetsPeriod);

				// This is new data, so insert it.
				if (count == 0) {
					// logger.info("Insert new data " + bs);
					if (bs.getAssetsCode() != null) {
						count = jdbcTmpl.update(insertQry, bs.getAssetsName(), bs.getRule(), bs.getAssetsCode(),
								bs.getNote(), bs.getEndValue(), bs.getStartValue(), bs.getChangedRatio(), assetsPeriod);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<BalanceSheet> listBSsByAssetsCodeAndYear(String assetsCode, Date year) {
		if (assetsCode == null || year == null)
			return null;

		String query = "SELECT * FROM BALANCE_SHEET WHERE ASSETS_CODE = ? AND YEAR(ASSETS_PERIOD)=?";

		Calendar cal = Calendar.getInstance();
		cal.setTime(year);

		logger.info("Get list of balance sheet by asserts_code and year ...");
		logger.info(query);
		logger.info("assetsCode " + assetsCode + ". Year " + cal.get(Calendar.YEAR));

		Object[] params = { assetsCode, cal.get(Calendar.YEAR) };
		List<BalanceSheet> bss = jdbcTmpl.query(query, params, new BalanceSheetMapper());

		return bss;
	}

	@Override
	public List<BalanceSheet> listBSsByDate(Date date) {
		if (date == null)
			return null;

		String query = "SELECT * FROM BALANCE_SHEET WHERE YEAR(ASSETS_PERIOD)=?";

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		logger.info("Get list of balance sheet by year ...");
		logger.info(query);
		logger.info("Year " + cal.get(Calendar.YEAR));

		Object[] params = { cal.get(Calendar.YEAR) };
		List<BalanceSheet> bss = jdbcTmpl.query(query, params, new BalanceSheetMapper());

		return bss;
	}

	@Override
	public List<String> listBSAssetsCodes() {
		String query = "SELECT DISTINCT ASSETS_CODE FROM BALANCE_SHEET ORDER BY ASSETS_CODE";

		logger.info("Get list of ASSETS_CODE from balance sheet table ...");
		logger.info(query);

		List<String> assetsCodes = jdbcTmpl.query(query, new AssetsCodeMapper());

		return assetsCodes;
	}

	@Override
	public List<Date> listBSAssetsPeriods() {
		String query = "SELECT DISTINCT ASSETS_PERIOD FROM BALANCE_SHEET ORDER BY ASSETS_PERIOD";

		logger.info("Get list of ASSETS_PERIOD from balance sheet table ...");
		logger.info(query);

		List<Date> assetsPeriods = jdbcTmpl.query(query, new AssetsPeriodMapper());

		return assetsPeriods;
	}

	public class BalanceSheetMapper implements RowMapper<BalanceSheet> {
		public BalanceSheet mapRow(ResultSet rs, int rowNum) throws SQLException {

			BalanceSheet bs = new BalanceSheet();

			bs.setAssetsId(rs.getInt("ASSETS_ID"));
			bs.setAssetsName(rs.getString("ASSETS_NAME"));
			bs.setRule(rs.getString("RULE"));
			bs.setAssetsCode(rs.getString("ASSETS_CODE"));
			bs.setNote(rs.getString("NOTE"));
			bs.setEndValue(rs.getDouble("END_VALUE"));
			bs.setStartValue(rs.getDouble("START_VALUE"));
			bs.setChangedRatio(rs.getDouble("CHANGED_RATIO"));
			Timestamp assetPeriod = rs.getTimestamp("ASSETS_PERIOD");
			Date date = new Date(assetPeriod.getTime());
			bs.setAssetsPeriod(date);
			bs.setDescription(rs.getString("DESCRIPTION"));

			return bs;
		}
	}

	public class AssetsCodeMapper implements RowMapper<String> {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {

			return rs.getString("ASSETS_CODE");
		}
	}

	public class AssetsPeriodMapper implements RowMapper<Date> {
		public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
			Timestamp assetPeriod = rs.getTimestamp("ASSETS_PERIOD");
			Date date = new Date(assetPeriod.getTime());
			return date;
		}
	}

	@Override
	public void insertOrUpdateSR(List<BalanceSheet> srs) {
		if (srs == null)
			return;

		String updateQry = "UPDATE SALE_RESULT SET END_VALUE=?, START_VALUE=?, CHANGED_RATIO=? WHERE ASSETS_CODE=? AND ASSETS_PERIOD=?";
		String insertQry = "INSERT INTO SALE_RESULT(ASSETS_NAME, RULE, ASSETS_CODE, NOTE, END_VALUE, START_VALUE, CHANGED_RATIO, ASSETS_PERIOD) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

		Iterator<BalanceSheet> iter = srs.iterator();
		while (iter.hasNext()) {
			BalanceSheet bs = iter.next();
			logger.info(bs);

			int count = 0;
			try {
				// update firstly, if now row is updated, we will be insert data
				Timestamp assetsPeriod = new Timestamp(bs.getAssetsPeriod().getTime());
				count = jdbcTmpl.update(updateQry, bs.getEndValue(), bs.getStartValue(), bs.getChangedRatio(),
						bs.getAssetsCode(), assetsPeriod);

				// This is new data, so insert it.
				if (count == 0) {
					// logger.info("Insert new data " + bs);
					if (bs.getAssetsCode() != null) {
						count = jdbcTmpl.update(insertQry, bs.getAssetsName(), bs.getRule(), bs.getAssetsCode(),
								bs.getNote(), bs.getEndValue(), bs.getStartValue(), bs.getChangedRatio(), assetsPeriod);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<BalanceSheet> listSRsByAssetsCodeAndYear(String assetsCode, Date year) {
		if (assetsCode == null || year == null)
			return null;

		String query = "SELECT * FROM SALE_RESULT WHERE ASSETS_CODE = ? AND YEAR(ASSETS_PERIOD)=?";

		Calendar cal = Calendar.getInstance();
		cal.setTime(year);

		logger.info("Get list of sale result by asserts_code and year ...");
		logger.info(query);
		logger.info("assetsCode " + assetsCode + ". Year " + cal.get(Calendar.YEAR));

		Object[] params = { assetsCode, cal.get(Calendar.YEAR) };
		List<BalanceSheet> srs = jdbcTmpl.query(query, params, new BalanceSheetMapper());

		return srs;
	}

	@Override
	public List<BalanceSheet> listSRsByDate(Date date) {
		if (date == null)
			return null;

		String query = "SELECT * FROM SALE_RESULT WHERE YEAR(ASSETS_PERIOD)=?";

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		logger.info("Get list of sale result by year ...");
		logger.info(query);
		logger.info("Year " + cal.get(Calendar.YEAR));

		Object[] params = { cal.get(Calendar.YEAR) };
		List<BalanceSheet> srs = jdbcTmpl.query(query, params, new BalanceSheetMapper());

		return srs;
	}

	@Override
	public List<String> listSRAssetsCodes() {

		return null;
	}

	@Override
	public List<Date> listSRAssetsPeriods() {
		// TODO Auto-generated method stub
		return null;
	}
}
