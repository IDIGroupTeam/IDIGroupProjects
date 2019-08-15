package com.idi.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.idi.finance.bean.doituong.NganHangTaiKhoan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.TaiKhoanDAO;

public class TaiKhoanDAOImpl implements TaiKhoanDAO {
	private static final Logger logger = Logger.getLogger(TaiKhoanDAOImpl.class);

	@Value("${DANH_SACH_TAI_KHOAN_THEO_CAP1}")
	private String DANH_SACH_TAI_KHOAN_THEO_CAP1;

	@Value("${DANH_SACH_TAI_KHOAN_THEO_MA_TK}")
	private String DANH_SACH_TAI_KHOAN_THEO_MA_TK;

	@Value("${DANH_SACH_TAI_KHOAN_THEO_MA_TK_CON}")
	private String DANH_SACH_TAI_KHOAN_THEO_MA_TK_CON;

	@Value("${LAY_LOAI_TAI_KHOAN_THEO_MA_TK}")
	private String LAY_LOAI_TAI_KHOAN_THEO_MA_TK;

	@Value("${DANH_SACH_TAI_KHOAN_CON}")
	private String DANH_SACH_TAI_KHOAN_CON;

	@Value("${DANH_SACH_TAI_KHOAN_CON_TAT_CA}")
	private String DANH_SACH_TAI_KHOAN_CON_TAT_CA;

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

		logger.info("Lấy tài khoản");
		logger.info(query);
		logger.info("Mã tài khoản: " + maTk);
		Object[] objs = { maTk };
		List<LoaiTaiKhoan> loaiTaiKhoanDs = jdbcTmpl.query(query, objs, new LoaiTaiKhoanMapper());
		if (loaiTaiKhoanDs != null) {
			logger.info("Kết quả: " + loaiTaiKhoanDs.size());
		} else {
			logger.info("Kết quả: " + 0);
		}

		if (loaiTaiKhoanDs != null && loaiTaiKhoanDs.size() > 0) {
			return loaiTaiKhoanDs.get(0);
		}
		return null;
	}

	@Override
	public void themTaiKhoan(LoaiTaiKhoan loaiTaiKhoan) {
		if (loaiTaiKhoan == null)
			return;

		String insertTkQry = "INSERT INTO TAI_KHOAN_DANH_MUC (MA_TK, TEN_TK, MA_TK_CHA, SO_DU, LUONG_TINH) VALUES(?, ?, ?, ?, ?)";
		try {
			if (loaiTaiKhoan.getMaTk() != null) {
				jdbcTmpl.update(insertTkQry, loaiTaiKhoan.getMaTk(), loaiTaiKhoan.getTenTk(), loaiTaiKhoan.getMaTkCha(),
						loaiTaiKhoan.getSoDu(), loaiTaiKhoan.isLuongTinh());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void capNhatTaiKhoan(LoaiTaiKhoan loaiTaiKhoan) {
		if (loaiTaiKhoan == null)
			return;

		String updateTkQry = "UPDATE TAI_KHOAN_DANH_MUC SET TEN_TK=?, MA_TK_CHA=?, SO_DU=?, LUONG_TINH=? WHERE MA_TK=?";

		// Update to DANH_MUC_TAI_KHOAN
		try {
			jdbcTmpl.update(updateTkQry, loaiTaiKhoan.getTenTk(), loaiTaiKhoan.getMaTkCha(), loaiTaiKhoan.getSoDu(),
					loaiTaiKhoan.isLuongTinh(), loaiTaiKhoan.getMaTk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertOrUpdateTaiKhoanDs(LoaiTaiKhoan loaiTaiKhoan) {
		if (loaiTaiKhoan == null)
			return;

		String updateTkQry = "UPDATE TAI_KHOAN_DANH_MUC SET TEN_TK=?, MA_TK_CHA=?, SO_DU=?, LUONG_TINH=? WHERE MA_TK=?";
		String insertTkQry = "INSERT INTO TAI_KHOAN_DANH_MUC (MA_TK, TEN_TK, MA_TK_CHA, SO_DU, LUONG_TINH) VALUES(?, ?, ?, ?, ?)";

		// Update to DANH_MUC_TAI_KHOAN
		int count = 0;
		try {

			// update firstly, if now row is updated, we will be insert data
			count = jdbcTmpl.update(updateTkQry, loaiTaiKhoan.getTenTk(), loaiTaiKhoan.getMaTkCha(),
					loaiTaiKhoan.getSoDu(), loaiTaiKhoan.isLuongTinh(), loaiTaiKhoan.getMaTk());

			// This is new data, so insert it.
			if (count == 0) {
				if (loaiTaiKhoan.getMaTk() != null) {
					count = jdbcTmpl.update(insertTkQry, loaiTaiKhoan.getMaTk(), loaiTaiKhoan.getTenTk(),
							loaiTaiKhoan.getMaTkCha(), loaiTaiKhoan.getSoDu(), loaiTaiKhoan.isLuongTinh());
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

		String updateTkQry = "UPDATE TAI_KHOAN_DANH_MUC SET TEN_TK=?, MA_TK_CHA=?, SO_DU=?, LUONG_TINH=? WHERE MA_TK=?";
		String insertTkQry = "INSERT INTO TAI_KHOAN_DANH_MUC (MA_TK, TEN_TK, MA_TK_CHA, SO_DU, LUONG_TINH) VALUES(?, ?, ?, ?, ?)";

		Iterator<LoaiTaiKhoan> iter = taiKhoanDs.iterator();
		while (iter.hasNext()) {
			LoaiTaiKhoan taiKhoan = iter.next();

			// Update to DANH_MUC_TAI_KHOAN
			int count = 0;
			try {

				// update firstly, if now row is updated, we will be insert data
				count = jdbcTmpl.update(updateTkQry, taiKhoan.getTenTk(), taiKhoan.getMaTkCha(), taiKhoan.getSoDu(),
						taiKhoan.isLuongTinh(), taiKhoan.getMaTk());

				// This is new data, so insert it.
				if (count == 0) {
					if (taiKhoan.getMaTk() != null) {
						count = jdbcTmpl.update(insertTkQry, taiKhoan.getMaTk(), taiKhoan.getTenTk(),
								taiKhoan.getMaTkCha(), taiKhoan.getSoDu(), taiKhoan.isLuongTinh());
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
	public List<LoaiTaiKhoan> cayTaiKhoan() {
		String query = "SELECT * FROM TAI_KHOAN_DANH_MUC WHERE MA_TK_CHA IS NULL ORDER BY MA_TK";
		logger.info("Danh sách loại tài khoản cấp trên");
		logger.info("query: " + query);

		List<LoaiTaiKhoan> taiKhoanDs = jdbcTmpl.query(query, new LoaiTaiKhoanMapper());
		if (taiKhoanDs != null) {
			Iterator<LoaiTaiKhoan> iter = taiKhoanDs.iterator();
			while (iter.hasNext()) {
				LoaiTaiKhoan loaiTaiKhoan = iter.next();
				loaiTaiKhoan.themLoaiTaiKhoan(danhSachTaiKhoanCon(loaiTaiKhoan));
			}
		}

		return taiKhoanDs;
	}

	private List<LoaiTaiKhoan> danhSachTaiKhoanCon(LoaiTaiKhoan loaiTaiKhoan) {
		if (loaiTaiKhoan == null || loaiTaiKhoan.getMaTk() == null || loaiTaiKhoan.getMaTk().trim().equals("")) {
			return null;
		}

		String query = "SELECT * FROM TAI_KHOAN_DANH_MUC WHERE MA_TK_CHA=? ORDER BY MA_TK";
		logger.info("Danh sách loại tài khoản cấp dưới");
		logger.info("query: " + query);

		Object[] objs = { loaiTaiKhoan.getMaTk().trim() };
		List<LoaiTaiKhoan> taiKhoanDs = jdbcTmpl.query(query, objs, new LoaiTaiKhoanMapper());
		if (taiKhoanDs != null) {
			Iterator<LoaiTaiKhoan> iter = taiKhoanDs.iterator();
			while (iter.hasNext()) {
				LoaiTaiKhoan loaiTaiKhoanCon = iter.next();
				loaiTaiKhoanCon.setLoaiTaiKhoan(loaiTaiKhoan);
				loaiTaiKhoanCon.themLoaiTaiKhoan(danhSachTaiKhoanCon(loaiTaiKhoanCon));
			}
		}

		return taiKhoanDs;
	}

	@Override
	public boolean isPhatSinh(String maTk) {
		if (maTk == null) {
			return false;
		}

		String query = "SELECT DISTINCT MA_TK FROM NGHIEP_VU_KE_TOAN WHERE MA_TK=? ORDER BY MA_TK";

		List<LoaiTaiKhoan> taiKhoanDs = jdbcTmpl.query(query, new LoaiTaiKhoanMapper());

		return (taiKhoanDs != null && taiKhoanDs.size() > 0);
	}

	@Override
	public boolean isCha(String maTk) {
		if (maTk == null) {
			return false;
		}

		String query = "SELECT DISTINCT MA_TK_CHA AS MA_TK FROM TAI_KHOAN_DANH_MUC WHERE MA_TK_CHA=? ORDER BY MA_TK";

		List<LoaiTaiKhoan> taiKhoanDs = jdbcTmpl.query(query, new LoaiTaiKhoanMapper());

		return (taiKhoanDs != null && taiKhoanDs.size() > 0);
	}

	@Override
	public List<LoaiTaiKhoan> danhSachTaiKhoan() {
		String query = "SELECT * FROM TAI_KHOAN_DANH_MUC ORDER BY MA_TK";

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
			loaiTaiKhoan.setLuongTinh(rs.getBoolean("LUONG_TINH"));

			NganHangTaiKhoan nganHangTaiKhoan = new NganHangTaiKhoan();
			nganHangTaiKhoan.setMaTk(rs.getInt("MA_TK_NH"));
			loaiTaiKhoan.setNganHangTaiKhoan(nganHangTaiKhoan);

			return loaiTaiKhoan;
		}
	}

	@Override
	public List<LoaiTaiKhoan> danhSachTaiKhoanPhatSinh() {
		String query = "SELECT DISTINCT MA_TK FROM NGHIEP_VU_KE_TOAN ORDER BY MA_TK";

		List<LoaiTaiKhoan> taiKhoanDs = jdbcTmpl.query(query, new LoaiTaiKhoanPhatSinhMapper());

		return taiKhoanDs;
	}

	public class LoaiTaiKhoanPhatSinhMapper implements RowMapper<LoaiTaiKhoan> {
		public LoaiTaiKhoan mapRow(ResultSet rs, int rowNum) throws SQLException {
			LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
			loaiTaiKhoan.setMaTk(rs.getString("MA_TK"));
			return loaiTaiKhoan;
		}
	}

	@Override
	public List<LoaiTaiKhoan> danhSachTaiKhoanCha() {
		String query = "SELECT DISTINCT MA_TK_CHA AS MA_TK FROM TAI_KHOAN_DANH_MUC WHERE MA_TK_CHA IS NOT NULL ORDER BY MA_TK";

		List<LoaiTaiKhoan> taiKhoanDs = jdbcTmpl.query(query, new LoaiTaiKhoanPhatSinhMapper());

		return taiKhoanDs;
	}

	@Override
	public List<LoaiTaiKhoan> danhSachTaiKhoanTheoCap1(String maTkCap1) {
		String query = DANH_SACH_TAI_KHOAN_THEO_CAP1;
		query = query.replaceAll("\\?", maTkCap1);

		List<LoaiTaiKhoan> taiKhoanDs = jdbcTmpl.query(query, new LoaiTaiKhoanMapper());

		return taiKhoanDs;
	}

	@Override
	public List<LoaiTaiKhoan> danhSachTaiKhoan(List<String> maTkDs) {
		if (maTkDs == null || maTkDs == null || maTkDs.size() == 0)
			return null;
		logger.info("Danh sách tài khoản");
		String condition = "'";
		for (String maTk : maTkDs) {
			condition += "^" + maTk + "|";
		}
		condition = condition.substring(0, condition.length() - 1);
		condition += "'";

		String query = DANH_SACH_TAI_KHOAN_THEO_MA_TK;
		query = query.replaceAll("\\$MA_TK_LIST\\$", condition);
		logger.info(query);
		logger.info("Mã tài khoản: " + maTkDs);

		List<LoaiTaiKhoan> taiKhoanDs = jdbcTmpl.query(query, new LoaiTaiKhoanMapper());
		if (taiKhoanDs != null) {
			logger.info("Kết quả: " + taiKhoanDs.size());
		} else {
			logger.info("Kết quả: " + 0);
		}

		return taiKhoanDs;
	}

	@Override
	public List<LoaiTaiKhoan> danhSachTaiKhoanCon(List<String> maTkDs) {
		if (maTkDs == null || maTkDs.size() == 0) {
			return null;
		}

		String condition = "'";
		for (String maTk : maTkDs) {
			condition += "^" + maTk + "|";
		}
		condition = condition.substring(0, condition.length() - 1);
		condition += "'";

		logger.info("Danh sách tài khoản con");
		String query = DANH_SACH_TAI_KHOAN_CON;
		query = query.replaceAll("\\$MA_TK_LIST\\$", condition);
		logger.info(query);

		List<LoaiTaiKhoan> taiKhoanDs = jdbcTmpl.query(query, new LoaiTaiKhoanMapper());

		return taiKhoanDs;
	}

	@Override
	public List<LoaiTaiKhoan> danhSachTaiKhoanCon() {
		logger.info("Danh sách tài khoản con");
		String query = DANH_SACH_TAI_KHOAN_CON_TAT_CA;
		logger.info(query);

		List<LoaiTaiKhoan> taiKhoanDs = jdbcTmpl.query(query, new LoaiTaiKhoanMapper());

		return taiKhoanDs;
	}

	@Override
	public List<LoaiTaiKhoan> danhSachTaiKhoan(String maTkCon) {
		if (maTkCon == null || maTkCon.isEmpty())
			return null;

		String query = DANH_SACH_TAI_KHOAN_THEO_MA_TK_CON;

		Object[] params = { maTkCon };
		List<LoaiTaiKhoan> taiKhoanDs = jdbcTmpl.query(query, params, new LoaiTaiKhoanMapper());

		return taiKhoanDs;
	}

	@Override
	public LoaiTaiKhoan capNhatTaiKhoanNganHang(LoaiTaiKhoan loaiTaiKhoan) {
		if (loaiTaiKhoan != null && loaiTaiKhoan.getMaTk() != null) {
			String query = "SELECT * FROM NGAN_HANG_TAI_KHOAN WHERE MA_TK=?";

		}
		return loaiTaiKhoan;
	}
}
