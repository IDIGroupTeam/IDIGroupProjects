package com.idi.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.TaiKhoanDAO;

public class TaiKhoanDAOImpl implements TaiKhoanDAO {
	private static final Logger logger = Logger.getLogger(TaiKhoanDAOImpl.class);

	@Value("${DANH_SACH_TAI_KHOAN_THEO_CAP1}")
	private String DANH_SACH_TAI_KHOAN_THEO_CAP1;

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public void insertOrUpdateTaiKhoanDs(List<LoaiTaiKhoan> taiKhoanDs) {
		if (taiKhoanDs == null)
			return;

		String updateTkQry = "UPDATE TAI_KHOAN_DANH_MUC SET TEN_TK=?, MA_TK_CHA=?, SO_DU=? WHERE MA_TK=?";
		String insertTkQry = "INSERT INTO TAI_KHOAN_DANH_MUC (MA_TK, TEN_TK, MA_TK_CHA, SO_DU) VALUES(?, ?, ?, ?)";

		Iterator<LoaiTaiKhoan> iter = taiKhoanDs.iterator();
		while (iter.hasNext()) {
			LoaiTaiKhoan taiKhoan = iter.next();

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
	public List<LoaiTaiKhoan> danhSachTaiKhoan() {
		String query = "SELECT * FROM TAI_KHOAN_DANH_MUC";

		logger.info("Lấy danh mục tài khoản kế toán từ bảng TAI_KHOAN_DANH_MUC ...");
		logger.info(query);

		List<LoaiTaiKhoan> taiKhoanDs = jdbcTmpl.query(query, new TaiKhoanMapper());

		return taiKhoanDs;
	}

	public class TaiKhoanMapper implements RowMapper<LoaiTaiKhoan> {
		public LoaiTaiKhoan mapRow(ResultSet rs, int rowNum) throws SQLException {

			LoaiTaiKhoan taiKhoan = new LoaiTaiKhoan();

			taiKhoan.setMaTk(rs.getString("MA_TK"));
			taiKhoan.setTenTk(rs.getString("TEN_TK"));
			taiKhoan.setMaTenTk(rs.getString("MA_TK") + " - " + rs.getString("TEN_TK"));
			taiKhoan.setMaTkCha(rs.getString("MA_TK_CHA"));
			taiKhoan.setSoDu(rs.getInt("SO_DU"));

			return taiKhoan;
		}
	}

	@Override
	public List<LoaiTaiKhoan> danhSachTaiKhoanTheoCap1(String maTkCap1) {
		String query = DANH_SACH_TAI_KHOAN_THEO_CAP1;
		query = query.replaceAll("\\?", maTkCap1);

		logger.info("Lấy danh mục tài khoản kế toán từ bảng TAI_KHOAN_DANH_MUC theo cấp 1 ... " + maTkCap1);
		logger.info(query);

		List<LoaiTaiKhoan> taiKhoanDs = jdbcTmpl.query(query, new TaiKhoanMapper());

		return taiKhoanDs;
	}
}
