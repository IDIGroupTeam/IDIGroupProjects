package com.idi.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.idi.finance.bean.chungtu.DoiTuong;
import com.idi.finance.bean.doitac.KhachHang;
import com.idi.finance.dao.KhachHangDAO;

public class KhachHangDAOImpl implements KhachHangDAO {
	private static final Logger logger = Logger.getLogger(KhachHangDAOImpl.class);

	@Value("${DANH_SACH_KHACH_HANG_THEO_MA_HOAC_TEN}")
	private String DANH_SACH_KHACH_HANG_THEO_MA_HOAC_TEN;

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public List<DoiTuong> danhSachDoiTuong() {
		String query = "SELECT * FROM KHACH_HANG WHERE MA_KH!=1 ORDER BY TEN_KH";

		logger.info("Danh sách khách hàng ...");
		logger.info(query);

		List<DoiTuong> doiTuongDs = jdbcTmpl.query(query, new DoiTuongMapper());
		return doiTuongDs;
	}

	public class DoiTuongMapper implements RowMapper<DoiTuong> {
		public DoiTuong mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				DoiTuong doiTuong = new DoiTuong();

				doiTuong.setMaDt(rs.getInt("MA_KH"));
				doiTuong.setLoaiDt(DoiTuong.KHACH_HANG);
				doiTuong.setTenDt(rs.getString("TEN_KH"));
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
	public List<KhachHang> danhSachKhachHang() {
		String query = "SELECT * FROM KHACH_HANG WHERE MA_KH!=1 ORDER BY TEN_KH";

		logger.info("Danh sách khách hàng ...");
		logger.info(query);

		List<KhachHang> khachHangDs = jdbcTmpl.query(query, new KhachHangMapper());
		return khachHangDs;
	}

	public class KhachHangMapper implements RowMapper<KhachHang> {
		public KhachHang mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				KhachHang khachHang = new KhachHang();

				khachHang.setMaKh(rs.getInt("MA_KH"));
				khachHang.setTenKh(rs.getString("TEN_KH"));
				khachHang.setMaThue(rs.getString("MA_THUE"));
				khachHang.setDiaChi(rs.getString("DIA_CHI"));
				khachHang.setSdt(rs.getString("SDT"));
				khachHang.setEmail(rs.getString("EMAIL"));
				khachHang.setWebSite(rs.getString("WEBSITE"));

				// logger.info(khachHang);
				return khachHang;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public KhachHang layKhachHang(Integer maKh) {
		String query = "SELECT * FROM KHACH_HANG WHERE MA_KH=?";

		logger.info("Lấy khách hàng có MA_KH = " + maKh + " ...");
		logger.info(query);

		KhachHang khachHang = jdbcTmpl.queryForObject(query, new Object[] { maKh }, new KhachHangMapper());
		return khachHang;
	}

	@Override
	public void xoaKhachHang(Integer maKh) {
		String xoa = "DELETE FROM KHACH_HANG WHERE MA_KH=?";
		jdbcTmpl.update(xoa, maKh);
		logger.info("Xóa khách hàng có MA_KH = " + maKh);
	}

	@Override
	public void luuCapNhatKhachHang(KhachHang khachHang) {
		int count = 0;
		String capNhat = "UPDATE KHACH_HANG SET TEN_KH=?, MA_THUE=?, DIA_CHI=?, EMAIL=?, SDT=?, WEBSITE=? WHERE MA_KH=?";
		String taoMoi = "INSERT INTO KHACH_HANG(TEN_KH, MA_THUE, DIA_CHI, EMAIL, SDT, WEBSITE) VALUES(?,?,?,?,?,?)";
		try {
			// update firstly, if now row is updated, we will be insert data
			count = jdbcTmpl.update(capNhat, khachHang.getTenKh(), khachHang.getMaThue(), khachHang.getDiaChi(),
					khachHang.getEmail(), khachHang.getSdt(), khachHang.getWebSite(), khachHang.getMaKh());

			// This is new data, so insert it.
			if (count == 0) {
				if (khachHang.getTenKh() != null && !khachHang.getTenKh().trim().equals("")) {
					count = jdbcTmpl.update(taoMoi, khachHang.getTenKh(), khachHang.getMaThue(), khachHang.getDiaChi(),
							khachHang.getEmail(), khachHang.getSdt(), khachHang.getWebSite());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<KhachHang> layKhachHangTheoMaHoacTen(String maHoacTen) {
		if (maHoacTen == null || maHoacTen.trim().equals("")) {
			return null;
		}

		String query = DANH_SACH_KHACH_HANG_THEO_MA_HOAC_TEN;
		query = query.replaceAll("\\?", maHoacTen.trim());

		logger.info("Danh sách khách hàng theo MA_KH hoặc TEN_KH ...");
		logger.info(query);

		List<KhachHang> khachHangDs = jdbcTmpl.query(query, new KhachHangMapper());
		return khachHangDs;
	}
}
