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

	@Value("${LAY_LOAI_TAI_KHOAN_THEO_MA_TK}")
	private String LAY_LOAI_TAI_KHOAN_THEO_MA_TK;

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public LoaiTaiKhoan layTaiKhoan(String maTk) {
		String query = LAY_LOAI_TAI_KHOAN_THEO_MA_TK;

		Object[] objs = { maTk };
		List<LoaiTaiKhoan> loaiTaiKhoanDs = jdbcTmpl.query(query, objs, new LoaiTaiKhoanMapper());

		if (loaiTaiKhoanDs != null && loaiTaiKhoanDs.size() > 0) {
			return loaiTaiKhoanDs.get(0);
		}
		return null;
	}

	@Override
	public void themTaiKhoan(LoaiTaiKhoan loaiTaiKhoan) {
		if (loaiTaiKhoan == null)
			return;

		String insertTkQry = "INSERT INTO TAI_KHOAN_DANH_MUC (MA_TK, TEN_TK, MA_TK_CHA, SO_DU) VALUES(?, ?, ?, ?)";
		try {
			if (loaiTaiKhoan.getMaTk() != null) {
				jdbcTmpl.update(insertTkQry, loaiTaiKhoan.getMaTk(), loaiTaiKhoan.getTenTk(), loaiTaiKhoan.getMaTkCha(),
						loaiTaiKhoan.getSoDu());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void capNhatTaiKhoan(LoaiTaiKhoan loaiTaiKhoan) {
		if (loaiTaiKhoan == null)
			return;

		String updateTkQry = "UPDATE TAI_KHOAN_DANH_MUC SET TEN_TK=?, MA_TK_CHA=?, SO_DU=? WHERE MA_TK=?";

		// Update to DANH_MUC_TAI_KHOAN
		try {
			jdbcTmpl.update(updateTkQry, loaiTaiKhoan.getTenTk(), loaiTaiKhoan.getMaTkCha(), loaiTaiKhoan.getSoDu(),
					loaiTaiKhoan.getMaTk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertOrUpdateTaiKhoanDs(LoaiTaiKhoan loaiTaiKhoan) {
		if (loaiTaiKhoan == null)
			return;

		String updateTkQry = "UPDATE TAI_KHOAN_DANH_MUC SET TEN_TK=?, MA_TK_CHA=?, SO_DU=? WHERE MA_TK=?";
		String insertTkQry = "INSERT INTO TAI_KHOAN_DANH_MUC (MA_TK, TEN_TK, MA_TK_CHA, SO_DU) VALUES(?, ?, ?, ?)";

		// Update to DANH_MUC_TAI_KHOAN
		int count = 0;
		try {

			// update firstly, if now row is updated, we will be insert data
			count = jdbcTmpl.update(updateTkQry, loaiTaiKhoan.getTenTk(), loaiTaiKhoan.getMaTkCha(),
					loaiTaiKhoan.getSoDu(), loaiTaiKhoan.getMaTk());

			// This is new data, so insert it.
			if (count == 0) {
				if (loaiTaiKhoan.getMaTk() != null) {
					count = jdbcTmpl.update(insertTkQry, loaiTaiKhoan.getMaTk(), loaiTaiKhoan.getTenTk(),
							loaiTaiKhoan.getMaTkCha(), loaiTaiKhoan.getSoDu());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

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
	public void xoaTaiKhoan(String maTk) {
		logger.info("Xóa tài khoản có MA_TK = " + maTk);
		String xoa = "DELETE FROM TAI_KHOAN_DANH_MUC WHERE MA_TK=?";
		jdbcTmpl.update(xoa, maTk);
	}

	@Override
	public List<LoaiTaiKhoan> danhSachTaiKhoan() {
		String query = "SELECT * FROM TAI_KHOAN_DANH_MUC ORDER BY MA_TK";

		// logger.info("Lấy danh mục tài khoản kế toán từ bảng TAI_KHOAN_DANH_MUC ...");
		// logger.info(query);

		List<LoaiTaiKhoan> taiKhoanDs = jdbcTmpl.query(query, new LoaiTaiKhoanMapper());

		return taiKhoanDs;
	}

	public class LoaiTaiKhoanMapper implements RowMapper<LoaiTaiKhoan> {
		public LoaiTaiKhoan mapRow(ResultSet rs, int rowNum) throws SQLException {

			LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();

			loaiTaiKhoan.setMaTk(rs.getString("MA_TK"));
			loaiTaiKhoan.setTenTk(rs.getString("TEN_TK"));
			loaiTaiKhoan.setMaTenTk(rs.getString("MA_TK") + " - " + rs.getString("TEN_TK"));
			loaiTaiKhoan.setMaTkCha(rs.getString("MA_TK_CHA"));
			loaiTaiKhoan.setSoDu(rs.getInt("SO_DU"));

			return loaiTaiKhoan;
		}
	}

	@Override
	public List<LoaiTaiKhoan> danhSachTaiKhoanTheoCap1(String maTkCap1) {
		String query = DANH_SACH_TAI_KHOAN_THEO_CAP1;
		query = query.replaceAll("\\?", maTkCap1);

		// logger.info("Lấy danh mục tài khoản kế toán từ bảng TAI_KHOAN_DANH_MUC theo
		// cấp 1 ... " + maTkCap1);
		// logger.info(query);

		List<LoaiTaiKhoan> taiKhoanDs = jdbcTmpl.query(query, new LoaiTaiKhoanMapper());

		return taiKhoanDs;
	}

}
