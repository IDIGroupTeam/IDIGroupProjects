package com.idi.finance.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.idi.finance.bean.BalanceAssetData;
import com.idi.finance.bean.BalanceAssetItem;
import com.idi.finance.bean.KpiGroup;
import com.idi.finance.bean.TaiKhoan;
import com.idi.finance.dao.TaiKhoanDAO;
import com.idi.finance.dao.impl.BalanceSheetDAOImpl.BalanceAssetMapper;

public class TaiKhoanDAOImpl implements TaiKhoanDAO {
	private static final Logger logger = Logger.getLogger(KpiChartDAOImpl.class);

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public void insertOrUpdateTaiKhoanDm(List<TaiKhoan> taiKhoanDM) {
		if (taiKhoanDM == null)
			return;

		String updateTkQry = "UPDATE DANH_MUC_TAI_KHOAN SET TEN_TK=?, MA_TK_CHA=?, SO_DU=? WHERE MA_TK=?";
		String insertTkQry = "INSERT INTO DANH_MUC_TAI_KHOAN (MA_TK, TEN_TK, MA_TK_CHA, SO_DU) VALUES(?, ?, ?, ?)";

		Iterator<TaiKhoan> iter = taiKhoanDM.iterator();
		while (iter.hasNext()) {
			TaiKhoan taiKhoan = iter.next();

			// Update to DANH_MUC_TAI_KHOAN
			int count = 0;
			try {

				// update firstly, if now row is updated, we will be insert data
				count = jdbcTmpl.update(updateTkQry, taiKhoan.getTenTk(), taiKhoan.getMaTkCha(), taiKhoan.getSoDu(),
						taiKhoan.getMaTk());

				// This is new data, so insert it.
				if (count == 0) {
					if (taiKhoan.getMaTk() != null) {
						count = jdbcTmpl.update(insertTkQry, taiKhoan.getMaTk(), taiKhoan.getTenTk(),
								taiKhoan.getMaTkCha(), taiKhoan.getSoDu());
					}
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
	}

	@Override
	public List<TaiKhoan> listTaiKhoanDm() {
		String query = "SELECT * FROM DANH_MUC_TAI_KHOAN";

		logger.info("Lấy danh mục tài khoản kế toán từ bảng DANH_MUC_TAI_KHOAN ...");
		logger.info(query);

		List<TaiKhoan> taiKhoanDm = jdbcTmpl.query(query, new TaiKhoanMapper());

		return taiKhoanDm;
	}

	public class TaiKhoanMapper implements RowMapper<TaiKhoan> {
		public TaiKhoan mapRow(ResultSet rs, int rowNum) throws SQLException {

			TaiKhoan taiKhoan = new TaiKhoan();

			taiKhoan.setMaTk(rs.getString("MA_TK"));
			taiKhoan.setTenTk(rs.getString("TEN_TK"));
			taiKhoan.setMaTkCha(rs.getString("MA_TK_CHA"));
			taiKhoan.setSoDu(rs.getInt("SO_DU"));

			return taiKhoan;
		}
	}
}
