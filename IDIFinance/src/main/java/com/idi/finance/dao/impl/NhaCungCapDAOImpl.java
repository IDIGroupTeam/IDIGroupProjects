package com.idi.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.idi.finance.bean.chungtu.DoiTuong;
import com.idi.finance.bean.doitac.NhaCungCap;
import com.idi.finance.dao.NhaCungCapDAO;

public class NhaCungCapDAOImpl implements NhaCungCapDAO {
	private static final Logger logger = Logger.getLogger(NhaCungCapDAOImpl.class);

	@Value("${DANH_SACH_NHA_CUNG_CAP_THEO_MA_HOAC_TEN}")
	private String DANH_SACH_NHA_CUNG_CAP_THEO_MA_HOAC_TEN;

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public List<DoiTuong> danhSachDoiTuong() {
		String query = "SELECT * FROM NHA_CUNG_CAP ORDER BY TEN_NCC";

		logger.info("Danh sách nhà cung cấp ...");
		logger.info(query);

		List<DoiTuong> doiTuongDs = jdbcTmpl.query(query, new DoiTuongMapper());
		return doiTuongDs;
	}

	public class DoiTuongMapper implements RowMapper<DoiTuong> {
		public DoiTuong mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				DoiTuong doiTuong = new DoiTuong();

				doiTuong.setMaDt(rs.getInt("MA_NCC"));
				doiTuong.setLoaiDt(DoiTuong.NHA_CUNG_CAP);
				doiTuong.setTenDt(rs.getString("TEN_NCC"));
				doiTuong.setMaThue(rs.getString("MA_THUE"));
				doiTuong.setDiaChi(rs.getString("DIA_CHI"));
				doiTuong.setSdt(rs.getString("SDT"));
				doiTuong.setEmail(rs.getString("EMAIL"));

				return doiTuong;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public List<NhaCungCap> danhSachNhaCungCap() {
		String query = "SELECT * FROM NHA_CUNG_CAP ORDER BY TEN_NCC";

		logger.info("Danh sách nhà cung cấp ...");
		logger.info(query);

		List<NhaCungCap> nhaCungCapDs = jdbcTmpl.query(query, new NhaCungCapMapper());
		return nhaCungCapDs;
	}

	public class NhaCungCapMapper implements RowMapper<NhaCungCap> {
		public NhaCungCap mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				NhaCungCap nhaCungCap = new NhaCungCap();

				nhaCungCap.setMaNcc(rs.getInt("MA_NCC"));
				nhaCungCap.setKhNcc(rs.getString("KH_NCC"));
				nhaCungCap.setTenNcc(rs.getString("TEN_NCC"));
				nhaCungCap.setMaThue(rs.getString("MA_THUE"));
				nhaCungCap.setDiaChi(rs.getString("DIA_CHI"));
				nhaCungCap.setSdt(rs.getString("SDT"));
				nhaCungCap.setEmail(rs.getString("EMAIL"));
				nhaCungCap.setWebSite(rs.getString("WEBSITE"));

				logger.info(nhaCungCap);
				return nhaCungCap;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public NhaCungCap layNhaCungCap(Integer maNcc) {
		String query = "SELECT * FROM NHA_CUNG_CAP WHERE MA_NCC=?";

		logger.info("Lấy nhà cung cấp có MA_NCC = " + maNcc + " ...");
		logger.info(query);

		NhaCungCap nhaCungCap = jdbcTmpl.queryForObject(query, new Object[] { maNcc }, new NhaCungCapMapper());
		return nhaCungCap;
	}

	@Override
	public void xoaNhaCungCap(Integer maNcc) {
		String xoa = "DELETE FROM NHA_CUNG_CAP WHERE MA_NCC=?";
		jdbcTmpl.update(xoa, maNcc);
		logger.info("Xóa nhà cung cấp có MA_NCC = " + maNcc);
	}

	@Override
	public void luuCapNhatNhaCungCap(NhaCungCap nhaCungCap) {
		int count = 0;
		String capNhat = "UPDATE NHA_CUNG_CAP SET TEN_NCC=?, MA_THUE=?, DIA_CHI=?, EMAIL=?, SDT=?, WEBSITE=? WHERE MA_NCC=?";
		String taoMoi = "INSERT INTO NHA_CUNG_CAP(KH_NCC, TEN_NCC, MA_THUE, DIA_CHI, EMAIL, SDT, WEBSITE) VALUES(?,?,?,?,?,?,?)";
		// TODO Auto-generated method stub
		try {
			// update firstly, if now row is updated, we will be insert data
			count = jdbcTmpl.update(capNhat, nhaCungCap.getTenNcc(), nhaCungCap.getMaThue(), nhaCungCap.getDiaChi(),
					nhaCungCap.getEmail(), nhaCungCap.getSdt(), nhaCungCap.getWebSite(), nhaCungCap.getMaNcc());

			// This is new data, so insert it.
			if (count == 0) {
				if (nhaCungCap.getKhNcc() != null && !nhaCungCap.getKhNcc().trim().equals("")
						&& nhaCungCap.getTenNcc() != null && !nhaCungCap.getTenNcc().trim().equals("")) {
					count = jdbcTmpl.update(taoMoi, nhaCungCap.getKhNcc(), nhaCungCap.getTenNcc(),
							nhaCungCap.getMaThue(), nhaCungCap.getDiaChi(), nhaCungCap.getEmail(), nhaCungCap.getSdt(),
							nhaCungCap.getWebSite());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<NhaCungCap> layNhaCungCapTheoMaHoacTen(String maHoacTen) {
		if (maHoacTen == null || maHoacTen.trim().equals("")) {
			return null;
		}

		String query = DANH_SACH_NHA_CUNG_CAP_THEO_MA_HOAC_TEN;
		query = query.replaceAll("\\?", maHoacTen.trim());

		logger.info("Danh sách nhà cung cấp theo MA_KH hoặc TEN_KH ...");
		logger.info(query);

		List<NhaCungCap> nhaCungCapDs = jdbcTmpl.query(query, new NhaCungCapMapper());
		return nhaCungCapDs;
	}

	public boolean kiemTraKhNhaCungCap(String khNcc) {
		int rs = 0;
		if (khNcc != null && !khNcc.trim().equals("")) {
			String query = "SELECT COUNT(KH_NCC) AS RS FROM NHA_CUNG_CAP WHERE KH_NCC=?";

			try {
				Object[] params = { khNcc };
				rs = jdbcTmpl.queryForObject(query, params, Integer.class);
			} catch (Exception e) {

			}
		}

		return rs > 0;
	}
}
