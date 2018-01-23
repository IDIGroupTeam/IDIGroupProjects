package com.idi.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.idi.finance.bean.cdkt.BalanceAssetData;
import com.idi.finance.bean.cdkt.BalanceAssetItem;
import com.idi.finance.dao.BalanceSheetDAO;

/**
 * @author haitd
 *
 */
public class BalanceSheetDAOImpl implements BalanceSheetDAO {
	private static final Logger logger = Logger.getLogger(BalanceSheetDAOImpl.class);

	@Value("${DANH_SACH_BCDKT}")
	private String DANH_SACH_BCDKT;

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public void insertOrUpdateBAs(List<BalanceAssetData> bas) {
		if (bas == null)
			return;

		String updateBaisQry = "UPDATE BALANCE_ASSET_ITEM SET ASSET_NAME=?, RULE=?, NOTE=? WHERE ASSET_CODE=?";
		String insertBaisQry = "INSERT INTO BALANCE_ASSET_ITEM (ASSET_CODE, ASSET_NAME, RULE,  NOTE) VALUES(?, ?, ?, ?)";

		String updateBadsQry = "UPDATE BALANCE_ASSET_DATA SET START_VALUE=?, END_VALUE=?, CHANGED_RATIO=?, DESCRIPTION=? WHERE ASSET_CODE=? AND PERIOD=?";
		String insertBadsQry = "INSERT INTO BALANCE_ASSET_DATA (ASSET_CODE, PERIOD, START_VALUE, END_VALUE, CHANGED_RATIO, DESCRIPTION) VALUES(?, ?, ?, ?, ?, ?)";

		Iterator<BalanceAssetData> iter = bas.iterator();
		while (iter.hasNext()) {
			BalanceAssetData bad = iter.next();
			logger.info(bad);

			// Update to BALANCE_ASSET_ITEM
			int count = 0;
			try {
				BalanceAssetItem bai = bad.getAsset();

				// update firstly, if now row is updated, we will be insert data
				count = jdbcTmpl.update(updateBaisQry, bai.getAssetName(), bai.getRule(), bai.getNote(),
						bai.getAssetCode());

				// This is new data, so insert it.
				if (count == 0) {
					if (bai.getAssetCode() != null && !bai.getAssetCode().trim().equals("")) {
						count = jdbcTmpl.update(insertBaisQry, bai.getAssetCode(), bai.getAssetName(), bai.getRule(),
								bai.getNote());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Update to BALANCE_ASSET_DATA
			count = 0;
			try {
				// update firstly, if now row is updated, we will be insert data
				count = jdbcTmpl.update(updateBadsQry, bad.getStartValue(), bad.getEndValue(), bad.getChangedRatio(),
						bad.getDescription(), bad.getAsset().getAssetCode(), bad.getPeriod());

				// This is new data, so insert it.
				if (count == 0) {
					if (bad.getAsset() != null && bad.getAsset().getAssetCode() != null && bad.getPeriod() != null) {
						count = jdbcTmpl.update(insertBadsQry, bad.getAsset().getAssetCode(), bad.getPeriod(),
								bad.getStartValue(), bad.getEndValue(), bad.getChangedRatio(), bad.getDescription());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<BalanceAssetData> listBAsByAssetCodesAndDates(List<String> assetCodes, List<Date> assetPeriods) {
		String query = "SELECT ITEM.ASSET_CODE, PERIOD, ITEM.ASSET_NAME, RULE, NOTE, START_VALUE, END_VALUE, CHANGED_RATIO, DESCRIPTION FROM BALANCE_ASSET_ITEM AS ITEM, BALANCE_ASSET_DATA AS DATA WHERE DATA.ASSET_CODE=ITEM.ASSET_CODE $assetCodesCondition$ $assetPeriodsCondition$";

		String assetCodesCondition = "";
		String assetPeriodsCondition = "";

		if (assetCodes != null && assetCodes.size() > 0) {
			assetCodesCondition = "AND DATA.ASSET_CODE IN (?)";
			String tmpl = "";
			Iterator<String> iter = assetCodes.iterator();
			while (iter.hasNext()) {
				tmpl += "'" + iter.next() + "',";
			}
			if (!tmpl.equals("")) {
				tmpl = tmpl.substring(0, tmpl.length() - 1);
			}
			assetCodesCondition = assetCodesCondition.replaceAll("\\?", tmpl);
		}

		if (assetPeriods != null && assetPeriods.size() > 0) {
			assetPeriodsCondition = "AND MONTH(PERIOD) IN (?) AND YEAR(PERIOD) IN (?)";
			String monthTmpl = "";
			String yearTmpl = "";
			Set<Integer> months = new LinkedHashSet<>();
			Set<Integer> years = new LinkedHashSet<>();

			Calendar cal = Calendar.getInstance();

			Iterator<Date> iter = assetPeriods.iterator();
			while (iter.hasNext()) {
				Date period = iter.next();
				cal.setTime(period);

				try {
					months.add(cal.get(Calendar.MONTH) + 1);
				} catch (Exception e) {
				}

				try {
					years.add(cal.get(Calendar.YEAR));
				} catch (Exception e) {
				}
			}

			Iterator<Integer> monthIter = months.iterator();
			while (monthIter.hasNext()) {
				monthTmpl += "'" + monthIter.next() + "',";
			}

			Iterator<Integer> yearIter = years.iterator();
			while (yearIter.hasNext()) {
				yearTmpl += "'" + yearIter.next() + "',";
			}

			if (!monthTmpl.equals("")) {
				monthTmpl = monthTmpl.substring(0, monthTmpl.length() - 1);
			}
			if (!yearTmpl.equals("")) {
				yearTmpl = yearTmpl.substring(0, yearTmpl.length() - 1);
			}

			assetPeriodsCondition = assetPeriodsCondition.replaceFirst("\\?", monthTmpl);
			assetPeriodsCondition = assetPeriodsCondition.replaceFirst("\\?", yearTmpl);
		}

		query = query.replaceAll("\\$assetCodesCondition\\$", assetCodesCondition);
		query = query.replaceAll("\\$assetPeriodsCondition\\$", assetPeriodsCondition);
		query = query.replaceAll("\\s+", " ");
		query += " ORDER BY PERIOD, ASSET_CODE";

		logger.info(query);
		List<BalanceAssetData> bads = jdbcTmpl.query(query, new BalanceAssetMapper());

		return bads;
	}

	@Override
	public List<BalanceAssetData> listBAsByAssetsCodeAndYear(String assetCode, Date year) {
		if (assetCode == null || year == null)
			return null;

		String query = "SELECT ITEM.ASSET_CODE, PERIOD, ITEM.ASSET_NAME, RULE, NOTE, START_VALUE, END_VALUE, CHANGED_RATIO, DESCRIPTION FROM BALANCE_ASSET_ITEM AS ITEM, BALANCE_ASSET_DATA AS DATA WHERE DATA.ASSET_CODE=ITEM.ASSET_CODE AND DATA.ASSET_CODE = ? AND YEAR(PERIOD)=?";

		Calendar cal = Calendar.getInstance();
		cal.setTime(year);

		logger.info("Get list of balance asset data by assert_code and year ...");
		logger.info(query);
		logger.info("assetCode " + assetCode + ". Year " + cal.get(Calendar.YEAR));

		Object[] params = { assetCode, cal.get(Calendar.YEAR) };
		List<BalanceAssetData> bads = jdbcTmpl.query(query, params, new BalanceAssetMapper());

		return bads;
	}

	public class BalanceAssetMapper implements RowMapper<BalanceAssetData> {
		public BalanceAssetData mapRow(ResultSet rs, int rowNum) throws SQLException {

			BalanceAssetItem bai = new BalanceAssetItem();
			bai.setAssetCode(rs.getString("ASSET_CODE"));
			bai.setAssetName(rs.getString("ASSET_NAME"));
			bai.setRule(rs.getString("RULE"));
			bai.setNote(rs.getString("NOTE"));

			BalanceAssetData bad = new BalanceAssetData();
			bad.setAsset(bai);
			Timestamp period = rs.getTimestamp("PERIOD");
			Date date = new Date(period.getTime());
			bad.setPeriod(date);
			bad.setStartValue(rs.getDouble("START_VALUE"));
			bad.setEndValue(rs.getDouble("END_VALUE"));
			bad.setChangedRatio(rs.getDouble("CHANGED_RATIO"));
			bad.setDescription(rs.getString("DESCRIPTION"));

			return bad;
		}
	}

	@Override
	public void insertOrUpdateSRs(List<BalanceAssetData> srs) {
		if (srs == null)
			return;

		String updateBaisQry = "UPDATE SALE_RESULT_ITEM SET ASSET_NAME=?, RULE=?, NOTE=? WHERE ASSET_CODE=?";
		String insertBaisQry = "INSERT INTO SALE_RESULT_ITEM (ASSET_CODE, ASSET_NAME, RULE,  NOTE) VALUES(?, ?, ?, ?)";

		String updateBadsQry = "UPDATE SALE_RESULT_DATA SET START_VALUE=?, END_VALUE=?, CHANGED_RATIO=?, DESCRIPTION=? WHERE ASSET_CODE=? AND PERIOD=?";
		String insertBadsQry = "INSERT INTO SALE_RESULT_DATA (ASSET_CODE, PERIOD, START_VALUE, END_VALUE, CHANGED_RATIO, DESCRIPTION) VALUES(?, ?, ?, ?, ?, ?)";

		Iterator<BalanceAssetData> iter = srs.iterator();
		while (iter.hasNext()) {
			BalanceAssetData bad = iter.next();
			logger.info(bad);

			// Update to SALE_RESULT_ITEM
			int count = 0;
			try {
				BalanceAssetItem bai = bad.getAsset();

				// update firstly, if now row is updated, we will be insert data
				count = jdbcTmpl.update(updateBaisQry, bai.getAssetName(), bai.getRule(), bai.getNote(),
						bai.getAssetCode());

				// This is new data, so insert it.
				if (count == 0) {
					if (bai.getAssetCode() != null && !bai.getAssetCode().trim().equals("")) {
						count = jdbcTmpl.update(insertBaisQry, bai.getAssetCode(), bai.getAssetName(), bai.getRule(),
								bai.getNote());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Update to SALE_RESULT_DATA
			count = 0;
			try {
				// update firstly, if now row is updated, we will be insert data
				count = jdbcTmpl.update(updateBadsQry, bad.getStartValue(), bad.getEndValue(), bad.getChangedRatio(),
						bad.getDescription(), bad.getAsset().getAssetCode(), bad.getPeriod());

				// This is new data, so insert it.
				if (count == 0) {
					if (bad.getAsset() != null && bad.getAsset().getAssetCode() != null && bad.getPeriod() != null) {
						count = jdbcTmpl.update(insertBadsQry, bad.getAsset().getAssetCode(), bad.getPeriod(),
								bad.getStartValue(), bad.getEndValue(), bad.getChangedRatio(), bad.getDescription());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<BalanceAssetData> listSRssByAssetsCodeAndYear(String assetCode, Date year) {
		if (assetCode == null || year == null)
			return null;

		String query = "SELECT ITEM.ASSET_CODE, PERIOD, ITEM.ASSET_NAME, RULE, NOTE, START_VALUE, END_VALUE, CHANGED_RATIO, DESCRIPTION FROM SALE_RESULT_ITEM AS ITEM, SALE_RESULT_DATA AS DATA WHERE DATA.ASSET_CODE=ITEM.ASSET_CODE AND DATA.ASSET_CODE = ? AND YEAR(PERIOD)=?";

		Calendar cal = Calendar.getInstance();
		cal.setTime(year);

		logger.info("Get list of sale result data by assert_code and year ...");
		logger.info(query);
		logger.info("assetCode " + assetCode + ". Year " + cal.get(Calendar.YEAR));

		Object[] params = { assetCode, cal.get(Calendar.YEAR) };
		List<BalanceAssetData> bads = jdbcTmpl.query(query, params, new BalanceAssetMapper());

		return bads;
	}

	@Override
	public void insertOrUpdateCFs(List<BalanceAssetData> cashFlows) {
		if (cashFlows == null)
			return;

		String updateBaisQry = "UPDATE CASH_FLOW_ITEM SET ASSET_NAME=?, RULE=?, NOTE=? WHERE ASSET_CODE=?";
		String insertBaisQry = "INSERT INTO CASH_FLOW_ITEM (ASSET_CODE, ASSET_NAME, RULE,  NOTE) VALUES(?, ?, ?, ?)";

		String updateBadsQry = "UPDATE CASH_FLOW_DATA SET START_VALUE=?, END_VALUE=?, CHANGED_RATIO=?, DESCRIPTION=? WHERE ASSET_CODE=? AND PERIOD=?";
		String insertBadsQry = "INSERT INTO CASH_FLOW_DATA (ASSET_CODE, PERIOD, START_VALUE, END_VALUE, CHANGED_RATIO, DESCRIPTION) VALUES(?, ?, ?, ?, ?, ?)";

		Iterator<BalanceAssetData> iter = cashFlows.iterator();
		while (iter.hasNext()) {
			BalanceAssetData bad = iter.next();
			logger.info(bad);

			// Update to CASH_FLOW_ITEM
			int count = 0;
			try {
				BalanceAssetItem bai = bad.getAsset();

				// update firstly, if now row is updated, we will be insert data
				count = jdbcTmpl.update(updateBaisQry, bai.getAssetName(), bai.getRule(), bai.getNote(),
						bai.getAssetCode());

				// This is new data, so insert it.
				if (count == 0) {
					if (bai.getAssetCode() != null && !bai.getAssetCode().trim().equals("")) {
						count = jdbcTmpl.update(insertBaisQry, bai.getAssetCode(), bai.getAssetName(), bai.getRule(),
								bai.getNote());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Update to CASH_FLOW_DATA
			count = 0;
			try {
				// update firstly, if now row is updated, we will be insert data
				count = jdbcTmpl.update(updateBadsQry, bad.getStartValue(), bad.getEndValue(), bad.getChangedRatio(),
						bad.getDescription(), bad.getAsset().getAssetCode(), bad.getPeriod());

				// This is new data, so insert it.
				if (count == 0) {
					if (bad.getAsset() != null && bad.getAsset().getAssetCode() != null
							&& !bad.getAsset().getAssetCode().trim().equals("") && bad.getPeriod() != null) {
						count = jdbcTmpl.update(insertBadsQry, bad.getAsset().getAssetCode(), bad.getPeriod(),
								bad.getStartValue(), bad.getEndValue(), bad.getChangedRatio(), bad.getDescription());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<BalanceAssetData> listCFssByAssetsCodeAndYear(String assetCode, Date year) {
		if (assetCode == null || year == null)
			return null;

		String query = "SELECT ITEM.ASSET_CODE, PERIOD, ITEM.ASSET_NAME, RULE, NOTE, START_VALUE, END_VALUE, CHANGED_RATIO, DESCRIPTION FROM CASH_FLOW_ITEM AS ITEM, CASH_FLOW_DATA AS DATA WHERE DATA.ASSET_CODE=ITEM.ASSET_CODE AND DATA.ASSET_CODE = ? AND YEAR(PERIOD)=?";

		Calendar cal = Calendar.getInstance();
		cal.setTime(year);

		logger.info("Get list of cash flows data by assert_code and year ...");
		logger.info(query);
		logger.info("assetCode " + assetCode + ". Year " + cal.get(Calendar.YEAR));

		Object[] params = { assetCode, cal.get(Calendar.YEAR) };
		List<BalanceAssetData> cashFlows = jdbcTmpl.query(query, params, new BalanceAssetMapper());

		return cashFlows;
	}

	@Override
	public List<String> listBSAssetsCodes() {
		String query = "SELECT DISTINCT ASSET_CODE FROM BALANCE_ASSET_ITEM ORDER BY ASSET_CODE";

		logger.info("Get list of ASSET_CODE from BALANCE_ASSET_ITEM table ...");
		logger.info(query);

		List<String> assetsCodes = jdbcTmpl.query(query, new AssetsCodeMapper());

		return assetsCodes;
	}

	@Override
	public List<Date> listBSAssetsPeriods() {
		String query = "SELECT DISTINCT PERIOD FROM BALANCE_ASSET_DATA ORDER BY PERIOD";

		logger.info("Get list of PERIOD from BALANCE_ASSET_DATA table ...");
		logger.info(query);

		List<Date> assetsPeriods = jdbcTmpl.query(query, new AssetsPeriodMapper());

		return assetsPeriods;
	}

	public class AssetsCodeMapper implements RowMapper<String> {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {

			return rs.getString("ASSET_CODE");
		}
	}

	public class AssetsPeriodMapper implements RowMapper<Date> {
		public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
			Timestamp assetPeriod = rs.getTimestamp("PERIOD");
			Date date = new Date(assetPeriod.getTime());
			return date;
		}
	}

	@Override
	public List<String> listSRAssetsCodes() {
		return null;
	}

	@Override
	public List<Date> listSRAssetsPeriods() {
		return null;
	}

	@Override
	public List<BalanceAssetItem> listBAIs() {
		String query = DANH_SACH_BCDKT;

		logger.info("Get list balance sheet item from BALANCE_ASSET_ITEM table ...");
		logger.info(query);

		List<BalanceAssetItem> bais = jdbcTmpl.query(query, new BalanceAssetItemMapper());

		return bais;
	}

	public class BalanceAssetItemMapper implements RowMapper<BalanceAssetItem> {
		public BalanceAssetItem mapRow(ResultSet rs, int rowNum) throws SQLException {
			BalanceAssetItem bai = new BalanceAssetItem();
			
			return bai;
		}
	}
}
