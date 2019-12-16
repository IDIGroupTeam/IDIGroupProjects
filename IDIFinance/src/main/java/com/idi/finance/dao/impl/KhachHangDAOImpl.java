package com.idi.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.idi.finance.bean.doituong.DoiTuong;
import com.idi.finance.bean.doituong.KhachHang;
import com.idi.finance.dao.KhachHangDAO;

public class KhachHangDAOImpl implements KhachHangDAO {
	private static final Logger logger = Logger.getLogger(KhachHangDAOImpl.class);

	@Value("${DANH_SACH_KHACH_HANG_THEO_MA_HOAC_TEN}")
	private String DANH_SACH_KHACH_HANG_THEO_MA_HOAC_TEN;

	@Value("${DANH_SACH_KHACH_HANG_PHAT_SINH_CHUNG_TU}")
	private String DANH_SACH_KHACH_HANG_PHAT_SINH_CHUNG_TU;

	@Value("${DANH_SACH_KHACH_HANG_PHAT_SINH_NVKT}")
	private String DANH_SACH_KHACH_HANG_PHAT_SINH_NVKT;

	@Value("${KIEM_TRA_KHACH_HANG_PHAT_SINH_CHUNG_TU}")
	private String KIEM_TRA_KHACH_HANG_PHAT_SINH_CHUNG_TU;

	@Value("${KIEM_TRA_KHACH_HANG_PHAT_SINH_NVKT}")
	private String KIEM_TRA_KHACH_HANG_PHAT_SINH_NVKT;

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

	@Override
	public List<DoiTuong> danhSachDoiTuong(String maHoacTen) {
		if (maHoacTen == null || maHoacTen.trim().equals("")) {
			return null;
		}

		String query = DANH_SACH_KHACH_HANG_THEO_MA_HOAC_TEN;
		query = query.replaceAll("\\?", maHoacTen.trim());

		logger.info("Danh sách khách hàng theo MA_KH hoặc TEN_KH ...");
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
				doiTuong.setKhDt(rs.getString("KH_KH"));
				doiTuong.setTenDt(rs.getString("TEN_KH"));
				doiTuong.setMaThue(rs.getString("MA_THUE"));
				doiTuong.setDiaChi(rs.getString("DIA_CHI"));
				doiTuong.setSdt(rs.getString("SDT"));
				doiTuong.setEmail(rs.getString("EMAIL"));
				doiTuong.setWebSite(rs.getString("WEBSITE"));

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
				khachHang.setKhKh(rs.getString("KH_KH"));
				khachHang.setTenKh(rs.getString("TEN_KH"));
				khachHang.setMaThue(rs.getString("MA_THUE"));
				khachHang.setDiaChi(rs.getString("DIA_CHI"));
				khachHang.setSdt(rs.getString("SDT"));
				khachHang.setEmail(rs.getString("EMAIL"));
				khachHang.setWebSite(rs.getString("WEBSITE"));

				logger.info(khachHang);
				return khachHang;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public List<KhachHang> danhSachKhachHangPhatSinh() {
		String queryCt = DANH_SACH_KHACH_HANG_PHAT_SINH_CHUNG_TU;
		String queryNvkt = DANH_SACH_KHACH_HANG_PHAT_SINH_NVKT;

		logger.info("Danh sách khách hàng phát sinh ...");
		logger.info(queryCt);
		logger.info(queryNvkt);

		List<KhachHang> khachHangDs = new ArrayList<KhachHang>();
		try {
			khachHangDs = jdbcTmpl.query(queryCt, new KhachHangPhatSinhMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			khachHangDs.addAll(jdbcTmpl.query(queryNvkt, new KhachHangPhatSinhMapper()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return khachHangDs;
	}

	public class KhachHangPhatSinhMapper implements RowMapper<KhachHang> {
		public KhachHang mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				KhachHang khachHang = new KhachHang();
				khachHang.setMaKh(rs.getInt("MA_DT"));

				logger.info(khachHang);
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
		logger.info("Kiểm tra việc xóa khách hàng có MA_KH = " + maKh);
		String phatSinhcT = KIEM_TRA_KHACH_HANG_PHAT_SINH_CHUNG_TU;
		String phatSinhnVKT = KIEM_TRA_KHACH_HANG_PHAT_SINH_NVKT;
		Integer phatSinhCtCount = 0;
		Integer phatSinhNvktCount = 0;

		Object[] objs = { maKh };
		try {
			phatSinhCtCount = jdbcTmpl.queryForObject(phatSinhcT, objs, Integer.class);
		} catch (Exception e) {
		}

		try {
			phatSinhNvktCount = jdbcTmpl.queryForObject(phatSinhnVKT, objs, Integer.class);
		} catch (Exception e) {
		}

		if (phatSinhCtCount == 0 && phatSinhNvktCount == 0) {
			logger.info("Xóa khách hàng có MA_KH = " + maKh);
			String xoa = "DELETE FROM KHACH_HANG WHERE MA_KH=?";
			jdbcTmpl.update(xoa, maKh);
		} else {
			logger.info("Không thể xóa khách hàng có MA_KH = " + maKh + " vì nó vẫn đang được sử dụng");
			logger.info("Phát sinh chứng từ " + phatSinhCtCount);
			logger.info("Phát sinh nghiệp vụ kế toán" + phatSinhNvktCount);
		}
	}

	@Override
	public void luuCapNhatKhachHang(KhachHang khachHang) {
		if (khachHang == null) {
			return;
		}

		int count = 0;
		String capNhat = "";
		if (khachHang.getMaKh() > 0) {
			capNhat = "UPDATE KHACH_HANG SET TEN_KH=?, MA_THUE=?, DIA_CHI=?, EMAIL=?, SDT=?, WEBSITE=? WHERE MA_KH=?";
		} else if (khachHang.getKhKh() != null && !khachHang.getKhKh().trim().equals("")) {
			capNhat = "UPDATE KHACH_HANG SET TEN_KH=?, MA_THUE=?, DIA_CHI=?, EMAIL=?, SDT=?, WEBSITE=? WHERE KH_KH=?";
		} else {
			return;
		}

		String taoMoi = "INSERT INTO KHACH_HANG(KH_KH, TEN_KH, MA_THUE, DIA_CHI, EMAIL, SDT, WEBSITE) VALUES(?,?,?,?,?,?,?)";
		try {
			if (khachHang.getMaKh() > 0) {
				// update firstly, if now row is updated, we will be insert data
				count = jdbcTmpl.update(capNhat, khachHang.getTenKh(), khachHang.getMaThue(), khachHang.getDiaChi(),
						khachHang.getEmail(), khachHang.getSdt(), khachHang.getWebSite(), khachHang.getMaKh());
			} else if (khachHang.getKhKh() != null && !khachHang.getKhKh().trim().equals("")) {
				// update firstly, if now row is updated, we will be insert data
				count = jdbcTmpl.update(capNhat, khachHang.getTenKh(), khachHang.getMaThue(), khachHang.getDiaChi(),
						khachHang.getEmail(), khachHang.getSdt(), khachHang.getWebSite(), khachHang.getKhKh());
			}

			// This is new data, so insert it.
			if (count == 0) {
				if (khachHang.getKhKh() != null && !khachHang.getKhKh().trim().equals("")
						&& khachHang.getTenKh() != null && !khachHang.getTenKh().trim().equals("")) {
					count = jdbcTmpl.update(taoMoi, khachHang.getKhKh(), khachHang.getTenKh(), khachHang.getMaThue(),
							khachHang.getDiaChi(), khachHang.getEmail(), khachHang.getSdt(), khachHang.getWebSite());
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

	public boolean kiemTraKhKhachHang(String khKh) {
		int rs = 0;
		if (khKh != null && !khKh.trim().equals("")) {
			String query = "SELECT COUNT(KH_KH) AS RS FROM KHACH_HANG WHERE KH_KH=?";

			try {
				Object[] params = { khKh };
				rs = jdbcTmpl.queryForObject(query, params, Integer.class);
			} catch (Exception e) {

			}
		}

		return rs > 0;
	}
}
