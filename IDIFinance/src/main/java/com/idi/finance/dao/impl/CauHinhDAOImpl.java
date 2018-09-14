package com.idi.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.idi.finance.bean.CauHinh;
import com.idi.finance.dao.CauHinhDAO;

public class CauHinhDAOImpl implements CauHinhDAO {
	private static final Logger logger = Logger.getLogger(CauHinhDAOImpl.class);

	@Value("${DANH_SACH_CAU_HINH}")
	private String DANH_SACH_CAU_HINH;

	@Value("${CAP_NHAT_CAU_HINH}")
	private String CAP_NHAT_CAU_HINH;

	@Value("${LAY_CAU_HINH}")
	private String LAY_CAU_HINH;

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public List<CauHinh> danhSachCauHinh() {
		String query = DANH_SACH_CAU_HINH;

		try {
			return jdbcTmpl.query(query, new CauHinhMapper());
		} catch (Exception e) {
			return null;
		}
	}

	public class CauHinhMapper implements RowMapper<CauHinh> {
		public CauHinh mapRow(ResultSet rs, int rowNum) throws SQLException {
			CauHinh cauHinh = new CauHinh();

			cauHinh.setMa(rs.getString("MA"));
			cauHinh.setTen(rs.getString("TEN"));
			cauHinh.setGiaTri(rs.getString("GIA_TRI"));

			return cauHinh;
		}
	}

	public int capNhatCauHinh(CauHinh cauHinh) {
		if (cauHinh == null) {
			return 0;
		}

		String query = CAP_NHAT_CAU_HINH;
		int res = 0;
		try {
			res = jdbcTmpl.update(query, cauHinh.getGiaTri(), cauHinh.getMa());
		} catch (Exception e) {
		}
		return res;
	}

	public CauHinh layCauHinh(String maCh) {
		if (maCh == null) {
			return null;
		}

		String query = LAY_CAU_HINH;
		try {
			Object[] objs = { maCh };
			return jdbcTmpl.queryForObject(query, objs, new CauHinhMapper());
		} catch (Exception e) {
		}
		return null;
	}
}
