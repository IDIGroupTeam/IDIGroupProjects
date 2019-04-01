package com.idi.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.idi.finance.bean.Tien;
import com.idi.finance.bean.hanghoa.DonGia;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.dao.KhoHangDAO;

public class KhoHangDAOImpl implements KhoHangDAO {
	private static final Logger logger = Logger.getLogger(KhoHangDAOImpl.class);

	@Value("${LAY_DANH_SACH_DON_GIA}")
	private String LAY_DANH_SACH_DON_GIA;

	@Value("${LAY_DANH_SACH_DON_GIA_KHO}")
	private String LAY_DANH_SACH_DON_GIA_KHO;

	@Value("${KIEM_TRA_KHO}")
	private String KIEM_TRA_KHO;

	@Value("${THEM_NHAP_KHO}")
	private String THEM_NHAP_KHO;

	@Value("${SUA_NHAP_KHO}")
	private String SUA_NHAP_KHO;

	@Value("${XOA_KHO_HANG}")
	private String XOA_KHO_HANG;

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public List<DonGia> layDonGiaDs(int maHh, int maKho) {
		String layDonGia = LAY_DANH_SACH_DON_GIA;
		String layDonGiaKho = LAY_DANH_SACH_DON_GIA_KHO;

		if (maKho != 0) {
			Object[] objs = { maHh, maKho };
			List<DonGia> donGiaDs = jdbcTmpl.query(layDonGiaKho, objs, new DonGiaMapper());
			return donGiaDs;
		} else {
			Object[] objs = { maHh };
			List<DonGia> donGiaDs = jdbcTmpl.query(layDonGia, objs, new DonGiaMapper());
			return donGiaDs;
		}
	}

	@Override
	public List<DonGia> layDonGiaDs(HangHoa hangHoa) {
		String layDonGia = LAY_DANH_SACH_DON_GIA;
		String layDonGiaKho = LAY_DANH_SACH_DON_GIA_KHO;

		if (hangHoa.getKho() != null) {
			Object[] objs = { hangHoa.getMaHh(), hangHoa.getKho().getMaKho() };
			List<DonGia> donGiaDs = jdbcTmpl.query(layDonGia, objs, new DonGiaMapper());
			return donGiaDs;
		} else {
			Object[] objs = { hangHoa.getMaHh() };
			List<DonGia> donGiaDs = jdbcTmpl.query(layDonGiaKho, objs, new DonGiaMapper());
			return donGiaDs;
		}
	}

	public class DonGiaMapper implements RowMapper<DonGia> {
		public DonGia mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				DonGia donGia = new DonGia();
				donGia.setMaGia(rs.getInt("MA_GIA"));

				Tien tien = new Tien();
				tien.setSoTien(rs.getDouble("DON_GIA"));
				tien.setGiaTri(rs.getDouble("DON_GIA"));
				donGia.setDonGia(tien);

				return donGia;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public double laySoLuong(HangHoa hangHoa) {
		String kiemTraKho = KIEM_TRA_KHO;

		try {
			return jdbcTmpl.queryForObject(kiemTraKho,
					new Object[] { hangHoa.getMaHh(), hangHoa.getKho().getMaKho(), hangHoa.getGiaKho().getMaGia() },
					Double.class);
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public void themNhapKho(HangHoa hangHoa) {
		if (hangHoa == null) {
			return;
		}

		String themNhapKho = THEM_NHAP_KHO;
		Date homNay = new Date();
		try {
			jdbcTmpl.update(themNhapKho, hangHoa.getMaHh(), hangHoa.getKho().getMaKho(), hangHoa.getGiaKho().getMaGia(),
					hangHoa.getSoLuong(), new java.sql.Date(homNay.getTime()), new java.sql.Date(homNay.getTime()));
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

	@Override
	public void suaNhapKho(HangHoa hangHoa) {
		if (hangHoa == null) {
			return;
		}

		String suaNhapKho = SUA_NHAP_KHO;
		Date homNay = new Date();
		try {
			jdbcTmpl.update(suaNhapKho, hangHoa.getSoLuong(), new java.sql.Date(homNay.getTime()), hangHoa.getMaHh(),
					hangHoa.getKho().getMaKho(), hangHoa.getGiaKho().getMaGia());
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

	@Override
	public void xoaKho(HangHoa hangHoa) {
		if (hangHoa == null) {
			return;
		}

		String xoaKho = XOA_KHO_HANG;
		try {
			jdbcTmpl.update(xoaKho, hangHoa.getMaHh(), hangHoa.getKho().getMaKho(), hangHoa.getGiaKho().getMaGia());
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
}
