package com.idi.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.idi.finance.bean.chungtu.TaiKhoan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.BalanceSheetDAO;
import com.idi.finance.utils.Utils;

/**
 * @author haitd
 *
 */
public class BalanceSheetDAOImpl implements BalanceSheetDAO {
	private static final Logger logger = Logger.getLogger(BalanceSheetDAOImpl.class);

	@Value("${DANH_SACH_GOC_BCDKT}")
	private String DANH_SACH_GOC_BCDKT;

	@Value("${DANH_SACH_CON_BCDKT}")
	private String DANH_SACH_CON_BCDKT;

	@Value("${DANH_SACH_CON_TKKT}")
	private String DANH_SACH_CON_TKKT;

	@Value("${DANH_SACH_TKKT_THUOC_NHIEU_CHI_TIEU}")
	private String DANH_SACH_TKKT_THUOC_NHIEU_CHI_TIEU;

	@Value("${DANH_SACH_CDKT_THEO_TKKT}")
	private String DANH_SACH_CDKT_THEO_TKKT;

	@Value("${CAP_NHAT_CAN_DOI_KE_TOAN}")
	private String CAP_NHAT_CAN_DOI_KE_TOAN;

	@Value("${LAY_CDKT_KY_TRUOC}")
	private String LAY_CDKT_KY_TRUOC;

	@Value("${TINH_CDKT_THEO_MATK}")
	private String TINH_CDKT_THEO_MATK;

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public void insertOrUpdateBA(BalanceAssetData bad) {
		if (bad == null)
			return;

		String updateBadsQry = "UPDATE BALANCE_ASSET_DATA SET START_VALUE=?, END_VALUE=?, CHANGED_RATIO=?, DESCRIPTION=? WHERE ASSET_CODE=? AND PERIOD=? AND PERIOD_TYPE=?";
		String insertBadsQry = "INSERT INTO BALANCE_ASSET_DATA (ASSET_CODE, PERIOD_TYPE, PERIOD, START_VALUE, END_VALUE, CHANGED_RATIO, DESCRIPTION) VALUES(?, ?, ?, ?, ?, ?, ?)";

		// Update to BALANCE_ASSET_DATA
		int count = 0;
		try {
			// update firstly, if now row is updated, we will be insert data
			count = jdbcTmpl.update(updateBadsQry, bad.getStartValue(), bad.getEndValue(), bad.getChangedRatio(),
					bad.getDescription(), bad.getAsset().getAssetCode(), bad.getPeriod(), bad.getPeriodType());

			// This is new data, so insert it.
			if (count == 0) {
				if (bad.getAsset() != null && bad.getAsset().getAssetCode() != null && bad.getPeriod() != null) {
					count = jdbcTmpl.update(insertBadsQry, bad.getAsset().getAssetCode(), bad.getPeriodType(),
							bad.getPeriod(), bad.getStartValue(), bad.getEndValue(), bad.getChangedRatio(),
							bad.getDescription());
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	@Override
	public void insertOrUpdateBAs(BalanceAssetData bad) {
		if (bad == null)
			return;

		String updateBaisQry = "UPDATE BALANCE_ASSET_ITEM SET ASSET_NAME=?, RULE=?, NOTE=?, SO_DU=? WHERE ASSET_CODE=?";
		String insertBaisQry = "INSERT INTO BALANCE_ASSET_ITEM (ASSET_CODE, ASSET_NAME, RULE,  NOTE, SO_DU) VALUES(?, ?, ?, ?, ?)";

		String updateBadsQry = "UPDATE BALANCE_ASSET_DATA SET START_VALUE=?, END_VALUE=?, CHANGED_RATIO=?, DESCRIPTION=? WHERE ASSET_CODE=? AND PERIOD=? AND PERIOD_TYPE=?";
		String insertBadsQry = "INSERT INTO BALANCE_ASSET_DATA (ASSET_CODE, PERIOD_TYPE, PERIOD, START_VALUE, END_VALUE, CHANGED_RATIO, DESCRIPTION) VALUES(?, ?, ?, ?, ?, ?, ?)";

		logger.info(bad);

		// In the furture, we should make transaction in this method
		// Update to BALANCE_ASSET_ITEM
		int count = 0;
		try {
			BalanceAssetItem bai = bad.getAsset();

			// update firstly, if now row is updated, we will be insert data
			count = jdbcTmpl.update(updateBaisQry, bai.getAssetName(), bai.getRule(), bai.getNote(), bai.getSoDu(),
					bai.getAssetCode());

			// This is new data, so insert it.
			if (count == 0) {
				if (bai.getAssetCode() != null && !bai.getAssetCode().trim().equals("")) {
					count = jdbcTmpl.update(insertBaisQry, bai.getAssetCode(), bai.getAssetName(), bai.getRule(),
							bai.getNote(), bai.getSoDu());
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
					bad.getDescription(), bad.getAsset().getAssetCode(), bad.getPeriod(), bad.getPeriodType());

			// This is new data, so insert it.
			if (count == 0) {
				if (bad.getAsset() != null && bad.getAsset().getAssetCode() != null && bad.getPeriod() != null) {
					count = jdbcTmpl.update(insertBadsQry, bad.getAsset().getAssetCode(), bad.getPeriodType(),
							bad.getPeriod(), bad.getStartValue(), bad.getEndValue(), bad.getChangedRatio(),
							bad.getDescription());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertOrUpdateBAs(List<BalanceAssetData> bas) {
		if (bas == null)
			return;

		Iterator<BalanceAssetData> iter = bas.iterator();
		while (iter.hasNext()) {
			BalanceAssetData bad = iter.next();
			insertOrUpdateBAs(bad);
		}
	}

	@Override
	public List<BalanceAssetData> listBAsByAssetCodesAndDates(List<String> assetCodes, List<Date> assetPeriods,
			int periodType) {
		String query = "SELECT ITEM.ASSET_CODE, PERIOD_TYPE, PERIOD, ITEM.ASSET_NAME, RULE, NOTE, START_VALUE, END_VALUE, CHANGED_RATIO, DESCRIPTION FROM BALANCE_ASSET_ITEM AS ITEM, BALANCE_ASSET_DATA AS DATA WHERE DATA.ASSET_CODE=ITEM.ASSET_CODE $assetCodesCondition$ $assetPeriodsCondition$ AND PERIOD_TYPE=?";

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
		query += " ORDER BY PERIOD_TYPE, PERIOD, ASSET_CODE";

		logger.info(query);
		Object[] params = { periodType };
		List<BalanceAssetData> bads = jdbcTmpl.query(query, params, new BalanceAssetMapper());

		return bads;
	}

	@Override
	public List<BalanceAssetData> listBAsByAssetsCodeAndYear(String assetCode, Date year, int periodType) {
		if (assetCode == null || year == null)
			return null;

		String query = "SELECT ITEM.ASSET_CODE, PERIOD_TYPE, PERIOD, ITEM.ASSET_NAME, RULE, NOTE, START_VALUE, END_VALUE, CHANGED_RATIO, DESCRIPTION FROM BALANCE_ASSET_ITEM AS ITEM, BALANCE_ASSET_DATA AS DATA WHERE DATA.ASSET_CODE=ITEM.ASSET_CODE AND DATA.ASSET_CODE = ? AND YEAR(PERIOD)=? AND PERIOD_TYPE=?";

		Calendar cal = Calendar.getInstance();
		cal.setTime(year);

		logger.info("Get list of balance asset data by assert_code and year ...");
		logger.info(query);
		logger.info("assetCode " + assetCode + ". Year " + cal.get(Calendar.YEAR) + ". Period type: " + periodType);

		Object[] params = { assetCode, cal.get(Calendar.YEAR), periodType };
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
			bad.setPeriod(rs.getDate("PERIOD"));
			bad.setStartValue(rs.getDouble("START_VALUE"));
			bad.setEndValue(rs.getDouble("END_VALUE"));
			bad.setChangedRatio(rs.getDouble("CHANGED_RATIO"));
			bad.setDescription(rs.getString("DESCRIPTION"));

			return bad;
		}
	}

	@Override
	public void insertOrUpdateSRs(BalanceAssetData sr) {
		if (sr == null)
			return;

		String updateBaisQry = "UPDATE SALE_RESULT_ITEM SET ASSET_NAME=?, RULE=?, NOTE=?, SO_DU=? WHERE ASSET_CODE=?";
		String insertBaisQry = "INSERT INTO SALE_RESULT_ITEM (ASSET_CODE, ASSET_NAME, RULE,  NOTE, SO_DU) VALUES(?, ?, ?, ?, ?)";

		String updateBadsQry = "UPDATE SALE_RESULT_DATA SET START_VALUE=?, END_VALUE=?, CHANGED_RATIO=?, DESCRIPTION=? WHERE ASSET_CODE=? AND PERIOD=? AND PERIOD_TYPE=?";
		String insertBadsQry = "INSERT INTO SALE_RESULT_DATA (ASSET_CODE, PERIOD_TYPE, PERIOD, START_VALUE, END_VALUE, CHANGED_RATIO, DESCRIPTION) VALUES(?, ?, ?, ?, ?, ?, ?)";

		// Update to SALE_RESULT_ITEM
		int count = 0;
		try {
			BalanceAssetItem bai = sr.getAsset();

			// update firstly, if now row is updated, we will be insert data
			count = jdbcTmpl.update(updateBaisQry, bai.getAssetName(), bai.getRule(), bai.getNote(), bai.getSoDu(),
					bai.getAssetCode());

			// This is new data, so insert it.
			if (count == 0) {
				if (bai.getAssetCode() != null && !bai.getAssetCode().trim().equals("")) {
					count = jdbcTmpl.update(insertBaisQry, bai.getAssetCode(), bai.getAssetName(), bai.getRule(),
							bai.getNote(), bai.getSoDu());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Update to SALE_RESULT_DATA
		count = 0;
		try {
			// update firstly, if now row is updated, we will be insert data
			count = jdbcTmpl.update(updateBadsQry, sr.getStartValue(), sr.getEndValue(), sr.getChangedRatio(),
					sr.getDescription(), sr.getAsset().getAssetCode(), sr.getPeriod(), sr.getPeriodType());

			// This is new data, so insert it.
			if (count == 0) {
				if (sr.getAsset() != null && sr.getAsset().getAssetCode() != null && sr.getPeriod() != null) {
					count = jdbcTmpl.update(insertBadsQry, sr.getAsset().getAssetCode(), sr.getPeriodType(),
							sr.getPeriod(), sr.getStartValue(), sr.getEndValue(), sr.getChangedRatio(),
							sr.getDescription());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertOrUpdateSRs(List<BalanceAssetData> srs) {
		if (srs == null)
			return;

		Iterator<BalanceAssetData> iter = srs.iterator();
		while (iter.hasNext()) {
			BalanceAssetData sr = iter.next();
			insertOrUpdateSRs(sr);
		}
	}

	@Override
	public List<BalanceAssetData> listSRssByAssetsCodeAndYear(String assetCode, Date year, int periodType) {
		if (assetCode == null || year == null)
			return null;

		String query = "SELECT ITEM.ASSET_CODE, PERIOD_TYPE, PERIOD, ITEM.ASSET_NAME, RULE, NOTE, START_VALUE, END_VALUE, CHANGED_RATIO, DESCRIPTION FROM SALE_RESULT_ITEM AS ITEM, SALE_RESULT_DATA AS DATA WHERE DATA.ASSET_CODE=ITEM.ASSET_CODE AND DATA.ASSET_CODE = ? AND YEAR(PERIOD)=? AND PERIOD_TYPE=?";

		Calendar cal = Calendar.getInstance();
		cal.setTime(year);

		logger.info("Get list of sale result data by assert_code and year ...");
		logger.info(query);
		logger.info("assetCode " + assetCode + ". Year " + cal.get(Calendar.YEAR) + ". Period type: " + periodType);

		Object[] params = { assetCode, cal.get(Calendar.YEAR), periodType };
		List<BalanceAssetData> bads = jdbcTmpl.query(query, params, new BalanceAssetMapper());

		return bads;
	}

	public void insertOrUpdateCFs(BalanceAssetData cf) {
		if (cf == null)
			return;

		String updateBaisQry = "UPDATE CASH_FLOW_ITEM SET ASSET_NAME=?, RULE=?, NOTE=?, SO_DU=? WHERE ASSET_CODE=?";
		String insertBaisQry = "INSERT INTO CASH_FLOW_ITEM (ASSET_CODE, ASSET_NAME, RULE,  NOTE, SO_DU) VALUES(?, ?, ?, ?, ?)";

		String updateBadsQry = "UPDATE CASH_FLOW_DATA SET START_VALUE=?, END_VALUE=?, CHANGED_RATIO=?, DESCRIPTION=? WHERE ASSET_CODE=? AND PERIOD=? AND PERIOD_TYPE=?";
		String insertBadsQry = "INSERT INTO CASH_FLOW_DATA (ASSET_CODE, PERIOD_TYPE, PERIOD, START_VALUE, END_VALUE, CHANGED_RATIO, DESCRIPTION) VALUES(?, ?, ?, ?, ?, ?, ?)";

		// Update to CASH_FLOW_ITEM
		int count = 0;
		try {
			BalanceAssetItem bai = cf.getAsset();

			// update firstly, if now row is updated, we will be insert data
			count = jdbcTmpl.update(updateBaisQry, bai.getAssetName(), bai.getRule(), bai.getNote(), bai.getSoDu(),
					bai.getAssetCode());

			// This is new data, so insert it.
			if (count == 0) {
				if (bai.getAssetCode() != null && !bai.getAssetCode().trim().equals("")) {
					count = jdbcTmpl.update(insertBaisQry, bai.getAssetCode(), bai.getAssetName(), bai.getRule(),
							bai.getNote(), bai.getSoDu());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Update to CASH_FLOW_DATA
		count = 0;
		try {
			// update firstly, if now row is updated, we will be insert data
			count = jdbcTmpl.update(updateBadsQry, cf.getStartValue(), cf.getEndValue(), cf.getChangedRatio(),
					cf.getDescription(), cf.getAsset().getAssetCode(), cf.getPeriod(), cf.getPeriodType());

			// This is new data, so insert it.
			if (count == 0) {
				if (cf.getAsset() != null && cf.getAsset().getAssetCode() != null
						&& !cf.getAsset().getAssetCode().trim().equals("") && cf.getPeriod() != null) {
					count = jdbcTmpl.update(insertBadsQry, cf.getAsset().getAssetCode(), cf.getPeriodType(),
							cf.getPeriod(), cf.getStartValue(), cf.getEndValue(), cf.getChangedRatio(),
							cf.getDescription());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertOrUpdateCFs(List<BalanceAssetData> cfs) {
		if (cfs == null)
			return;

		Iterator<BalanceAssetData> iter = cfs.iterator();
		while (iter.hasNext()) {
			BalanceAssetData cf = iter.next();
			insertOrUpdateCFs(cf);
		}
	}

	@Override
	public List<BalanceAssetData> listCFssByAssetsCodeAndYear(String assetCode, Date year, int periodType) {
		if (assetCode == null || year == null)
			return null;

		String query = "SELECT ITEM.ASSET_CODE, PERIOD_TYPE, PERIOD, ITEM.ASSET_NAME, RULE, NOTE, START_VALUE, END_VALUE, CHANGED_RATIO, DESCRIPTION FROM CASH_FLOW_ITEM AS ITEM, CASH_FLOW_DATA AS DATA WHERE DATA.ASSET_CODE=ITEM.ASSET_CODE AND DATA.ASSET_CODE = ? AND YEAR(PERIOD)=? AND PERIOD_TYPE=?";

		Calendar cal = Calendar.getInstance();
		cal.setTime(year);

		logger.info("Get list of cash flows data by assert_code and year ...");
		logger.info(query);
		logger.info("assetCode " + assetCode + ". Year " + cal.get(Calendar.YEAR) + ". Period type: " + periodType);

		Object[] params = { assetCode, cal.get(Calendar.YEAR), periodType };
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
	public List<Date> listBSAssetsPeriods(int periodType) {
		String query = "SELECT DISTINCT PERIOD FROM BALANCE_ASSET_DATA WHERE PERIOD_TYPE=? ORDER BY PERIOD";

		logger.info("Get list of PERIOD from BALANCE_ASSET_DATA table ...");
		logger.info(query);

		Object[] params = { periodType };
		List<Date> assetsPeriods = jdbcTmpl.query(query, params, new AssetsPeriodMapper());

		return assetsPeriods;
	}

	public class AssetsCodeMapper implements RowMapper<String> {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {

			return rs.getString("ASSET_CODE");
		}
	}

	public class AssetsPeriodMapper implements RowMapper<Date> {
		public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getDate("PERIOD");
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
	public List<BalanceAssetItem> listBais() {
		String query = DANH_SACH_GOC_BCDKT;

		logger.info("Get list balance sheet item from BALANCE_ASSET_ITEM table ...");
		logger.info(query);

		List<BalanceAssetItem> baisRs = new ArrayList<>();
		List<BalanceAssetItem> bais = jdbcTmpl.query(query, new BalanceAssetItemMapper());
		Iterator<BalanceAssetItem> iter = bais.iterator();
		while (iter.hasNext()) {
			BalanceAssetItem bai = iter.next();
			bai = updateChilds(bai);
			if (bai != null) {
				baisRs.add(bai);
			}
		}

		return baisRs;
	}

	private BalanceAssetItem updateChilds(BalanceAssetItem parent) {
		if (parent == null)
			return null;

		String query = DANH_SACH_CON_BCDKT;
		int row = 0;

		Object[] params = { parent.getAssetCode() };
		List<BalanceAssetItem> bais = jdbcTmpl.query(query, params, new BalanceAssetItemMapper());
		List<BalanceAssetItem> baisRs = new ArrayList<>();

		if (bais != null && bais.size() > 0) {
			Iterator<BalanceAssetItem> iter = bais.iterator();
			while (iter.hasNext()) {
				BalanceAssetItem bai = iter.next();
				bai.setParent(parent);

				bai = updateChilds(bai);

				if (bai != null) {
					row += bai.getRow();
					baisRs.add(bai);
				}
			}

			parent.addChild(baisRs);
		}

		query = DANH_SACH_CON_TKKT;

		List<LoaiTaiKhoan> taiKhoanDs = jdbcTmpl.query(query, params, new TaiKhoanMapper());
		if (taiKhoanDs != null && taiKhoanDs.size() > 0) {
			parent.themTaiKhoan(taiKhoanDs);
			row += taiKhoanDs.size();
		}

		parent.setRow(row);

		// Đoạn này nếu sau này xử lý tốt phần hiển thị trang jsp thì có thể bỏ đi
		if ((parent.getChilds() == null || parent.getChilds().size() == 0)
				&& (parent.getTaiKhoanDs() == null || parent.getTaiKhoanDs().size() == 0)) {
			parent = null;
		}

		return parent;
	}

	public class BalanceAssetItemMapper implements RowMapper<BalanceAssetItem> {
		public BalanceAssetItem mapRow(ResultSet rs, int rowNum) throws SQLException {

			BalanceAssetItem bai = new BalanceAssetItem();

			bai.setAssetCode(rs.getString("ASSET_CODE"));
			bai.setAssetName(rs.getString("ASSET_NAME"));
			bai.setAssetCodeName(bai.getAssetCode() + " - " + bai.getAssetName());
			bai.setRule(rs.getString("RULE"));
			bai.setNote(rs.getString("NOTE"));
			bai.setSoDu(rs.getInt("SO_DU"));

			String assetParent = rs.getString("ASSET_PARENT");
			if (assetParent != null && !assetParent.trim().equals("")) {
				BalanceAssetItem baiParent = new BalanceAssetItem();
				baiParent.setAssetCode(assetParent);

				bai.setParent(baiParent);
			}

			return bai;
		}
	}

	public class TaiKhoanMapper implements RowMapper<LoaiTaiKhoan> {
		public LoaiTaiKhoan mapRow(ResultSet rs, int rowNum) throws SQLException {

			LoaiTaiKhoan taiKhoan = new LoaiTaiKhoan();

			taiKhoan.setMaTk(rs.getString("MA_TK"));
			taiKhoan.setTenTk(rs.getString("TEN_TK"));
			taiKhoan.setMaTenTk(rs.getString("MA_TK") + " - " + rs.getString("TEN_TK"));
			taiKhoan.setMaTkCha(rs.getString("MA_TK_CHA"));
			taiKhoan.setSoDu(rs.getInt("SO_DU"));
			taiKhoan.setSoDuGiaTri(rs.getInt("SO_DU_GIA_TRI"));

			return taiKhoan;
		}
	}

	@Override
	public List<LoaiTaiKhoan> danhSachTkktThuocNhieuChiTieu() {
		String query = DANH_SACH_TKKT_THUOC_NHIEU_CHI_TIEU;

		logger.info("Danh sách tài khoản kế toán thuộc nhiều chi tiêu CDKT ...");
		logger.info(query);

		List<LoaiTaiKhoan> taiKhoanDs = jdbcTmpl.query(query, new TKMapper());

		return taiKhoanDs;
	}

	public class TKMapper implements RowMapper<LoaiTaiKhoan> {
		public LoaiTaiKhoan mapRow(ResultSet rs, int rowNum) throws SQLException {

			LoaiTaiKhoan taiKhoan = new LoaiTaiKhoan();

			taiKhoan.setMaTk(rs.getString("MA_TK"));
			taiKhoan.setSoDuGiaTri(rs.getInt("SO_DU_GIA_TRI"));

			return taiKhoan;
		}
	}

	@Override
	public List<BalanceAssetItem> danhSachCdktTheoTkkt(String maTk, int soDu) {
		String query = DANH_SACH_CDKT_THEO_TKKT;

		logger.info("Danh sách các chỉ tiêu cân đối kế toán theo tài khoản kết toán ...");
		logger.info(query);

		Object[] params = { maTk, soDu };
		List<BalanceAssetItem> cdktDs = jdbcTmpl.query(query, params, new BalanceAssetItemMapper());

		return cdktDs;
	}

	@Override
	public void capNhatCanDoiKeToan(List<TaiKhoan> taiKhoanDs) {
		if (taiKhoanDs != null) {
			String query = CAP_NHAT_CAN_DOI_KE_TOAN;
			Iterator<TaiKhoan> iter = taiKhoanDs.iterator();
			while (iter.hasNext()) {
				TaiKhoan taiKhoan = iter.next();
				logger.info("Cập nhật cân đối kế toán vào bảng CHUNG_TU_TAI_KHOAN: ASSET_CODE: "
						+ taiKhoan.getBai().getAssetCode() + ". MA_CT: " + taiKhoan.getChungTu().getMaCt() + ". MA_TK: "
						+ taiKhoan.getLoaiTaiKhoan().getMaTk() + ". SO_DU: " + taiKhoan.getSoDu());
				try {
					jdbcTmpl.update(query, taiKhoan.getBai().getAssetCode(), taiKhoan.getChungTu().getMaCt(),
							taiKhoan.getLoaiTaiKhoan().getMaTk(), taiKhoan.getSoDu());
				} catch (Exception e) {
					logger.info("LỖI CẬP NHẬT: " + e.getMessage());
				}
			}
		}
	}

	@Override
	public BalanceAssetData getPeriodEndValue(BalanceAssetData bad) {
		if (bad == null || bad.getPeriod() == null || bad.getAsset() == null || bad.getAsset().getAssetCode() == null)
			return bad;

		String query = LAY_CDKT_KY_TRUOC;

		try {
			Date prevPeriod = Utils.prevPeriod(bad.getPeriod(), bad.getPeriodType());
			Object[] params = { bad.getAsset().getAssetCode(), bad.getPeriodType(), prevPeriod };
			Double value = jdbcTmpl.queryForObject(query, params, Double.class);
			bad.setStartValue(value);
		} catch (Exception e) {
			bad.setStartValue(0);
		}

		return bad;
	}

	@Override
	public BalanceAssetData calculateBs(BalanceAssetData bad) {
		if (bad == null || bad.getPeriod() == null || bad.getAsset() == null || bad.getAsset().getTaiKhoanDs() == null)
			return bad;

		String query = TINH_CDKT_THEO_MATK;

		Date start = bad.getPeriod();
		Date end = Utils.getEndPeriod(bad.getPeriod(), bad.getPeriodType());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		String batDau = sdf.format(start);
		String ketThuc = sdf.format(end);

		// logger.info("Tính chi tiêu cân đối kế toán: " + bad.getAsset().getAssetCode()
		// + " từ " + batDau + " đến " + ketThuc);

		Iterator<LoaiTaiKhoan> iter = bad.getAsset().getTaiKhoanDs().iterator();
		while (iter.hasNext()) {
			try {
				LoaiTaiKhoan loaiTaiKhoan = iter.next();
				String tmplQuery = query.replaceAll("\\$MA_TK\\$", loaiTaiKhoan.getMaTk());

				Object[] params = { bad.getAsset().getAssetCode(), loaiTaiKhoan.getSoDuGiaTri(), batDau, ketThuc };
				Double value = jdbcTmpl.queryForObject(tmplQuery, params, Double.class);
				bad.setEndValue(bad.getEndValue() + bad.getAsset().getSoDu() * loaiTaiKhoan.getSoDuGiaTri() * value);
				// logger.info(tmplQuery);
				// logger.info(loaiTaiKhoan + " " + value);
			} catch (Exception e) {
				// logger.error("LỖI: " + e.getMessage());
			}
		}
		// logger.info(bad);

		return bad;
	}
}
