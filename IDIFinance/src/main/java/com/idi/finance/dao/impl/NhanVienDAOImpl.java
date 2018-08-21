package com.idi.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.idi.finance.bean.NhanVien;
import com.idi.finance.bean.chungtu.DoiTuong;
import com.idi.finance.dao.NhanVienDAO;

public class NhanVienDAOImpl implements NhanVienDAO {
	private static final Logger logger = Logger.getLogger(NhanVienDAOImpl.class);

	@Value("${DANH_SACH_NHAN_VIEN}")
	private String DANH_SACH_NHAN_VIEN;

	@Value("${DANH_SACH_NHAN_VIEN_THEO_MA_HOAC_TEN}")
	private String DANH_SACH_NHAN_VIEN_THEO_MA_HOAC_TEN;

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public List<DoiTuong> danhSachDoiTuong() {
		String query = DANH_SACH_NHAN_VIEN;
		logger.info("Danh sách nhân viên ...");
		logger.info(query);

		List<DoiTuong> doiTuongDs = jdbcTmpl.query(query, new DoiTuongMapper());
		return doiTuongDs;
	}

	public class DoiTuongMapper implements RowMapper<DoiTuong> {
		public DoiTuong mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				DoiTuong doiTuong = new DoiTuong();

				doiTuong.setMaDt(rs.getInt("EMPLOYEE_ID"));
				doiTuong.setLoaiDt(DoiTuong.NHAN_VIEN);
				doiTuong.setTenDt(rs.getString("FULL_NAME"));
				doiTuong.setDiaChi(rs.getString("DEPARTMENT"));
				doiTuong.setSdt(rs.getString("PHONE_NO"));
				doiTuong.setEmail(rs.getString("EMAIL"));

				return doiTuong;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public List<NhanVien> danhSachNhanVien() {
		String query = DANH_SACH_NHAN_VIEN;
		logger.info("Danh sách nhân viên ...");
		logger.info(query);

		List<NhanVien> nhanVienDs = jdbcTmpl.query(query, new NhanVienCapMapper());
		return nhanVienDs;
	}

	@Override
	public List<NhanVien> layNhanVienTheoMaHoacTen(String maHoacTen) {
		if (maHoacTen == null || maHoacTen.trim().equals("")) {
			return null;
		}

		String query = DANH_SACH_NHAN_VIEN_THEO_MA_HOAC_TEN;
		query = query.replaceAll("\\?", maHoacTen.trim());

		logger.info("Danh sách nhân viên theo MA_KH hoặc TEN_KH ...");
		logger.info(query);

		List<NhanVien> nhanVienDs = jdbcTmpl.query(query, new NhanVienCapMapper());
		return nhanVienDs;
	}

	public class NhanVienCapMapper implements RowMapper<NhanVien> {
		public NhanVien mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				NhanVien nhanVien = new NhanVien();

				nhanVien.setEmployeedId(rs.getInt("EMPLOYEE_ID"));
				nhanVien.setFullName(rs.getString("FULL_NAME"));
				nhanVien.setDepartment(rs.getString("DEPARTMENT"));

				logger.info(nhanVien);
				return nhanVien;
			} catch (Exception e) {
				return null;
			}
		}
	}
}
