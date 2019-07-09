package com.idi.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.idi.finance.bean.congviec.LinhVuc;
import com.idi.finance.bean.congviec.NghiepVu;
import com.idi.finance.dao.CongViecDAO;

public class CongViecDAOImpl implements CongViecDAO {
	private static final Logger logger = Logger.getLogger(CongViecDAOImpl.class);

	@Value("${DANH_SACH_LINH_VUC}")
	private String DANH_SACH_LINH_VUC;

	@Value("${DANH_SACH_NGHIEP_VU}")
	private String DANH_SACH_NGHIEP_VU;

	@Value("${DANH_SACH_NGHIEP_VU_THEO_LINH_VUC}")
	private String DANH_SACH_NGHIEP_VU_THEO_LINH_VUC;

	@Value("${LAY_NGHIEP_VU}")
	private String LAY_NGHIEP_VU;

	@Value("${THEM_NGHIEP_VU}")
	private String THEM_NGHIEP_VU;

	@Value("${CAP_NHAT_NGHIEP_VU}")
	private String CAP_NHAT_NGHIEP_VU;

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public List<LinhVuc> danhSachLinhVuc() {
		String query = DANH_SACH_LINH_VUC;

		List<LinhVuc> linhVucDs = jdbcTmpl.query(query, new LinhVucMapper());

		return linhVucDs;
	}

	public class LinhVucMapper implements RowMapper<LinhVuc> {
		public LinhVuc mapRow(ResultSet rs, int rowNum) throws SQLException {

			LinhVuc linhVuc = new LinhVuc();

			linhVuc.setMaLv(rs.getInt("MA_LV"));
			linhVuc.setTenLv(rs.getString("TEN_LV"));
			linhVuc.setMoTa(rs.getString("MO_TA"));

			return linhVuc;
		}
	}

	@Override
	public List<NghiepVu> danhSachNghiepVu(int maLv) {
		String query = DANH_SACH_NGHIEP_VU_THEO_LINH_VUC;

		Object[] objs = { maLv };
		List<NghiepVu> nghiepVuDs = jdbcTmpl.query(query, objs, new NghiepVuMapper());

		return nghiepVuDs;
	}

	@Override
	public List<NghiepVu> danhSachNghiepVu() {
		String query = DANH_SACH_NGHIEP_VU;

		List<NghiepVu> nghiepVuDs = jdbcTmpl.query(query, new NghiepVuMapper());
		return nghiepVuDs;
	}

	public class NghiepVuMapper implements RowMapper<NghiepVu> {
		public NghiepVu mapRow(ResultSet rs, int rowNum) throws SQLException {

			NghiepVu nghiepVu = new NghiepVu();
			nghiepVu.setMaNv(rs.getInt("MA_NV"));
			nghiepVu.setTenNv(rs.getString("TEN_NV"));
			nghiepVu.setMoTa(rs.getString("MO_TA"));
			nghiepVu.setDoKho(rs.getInt("DO_KHO"));

			LinhVuc linhVuc = new LinhVuc();
			linhVuc.setMaLv(rs.getInt("MA_NV_CHA"));
			linhVuc.setTenLv(rs.getString("TEN_LV"));
			nghiepVu.setLinhVuc(linhVuc);

			return nghiepVu;
		}
	}

	@Override
	public NghiepVu layNghiepVu(String maNv) {
		String query = LAY_NGHIEP_VU;

		Object[] objs = { maNv };
		List<NghiepVu> nghiepVuDs = jdbcTmpl.query(query, objs, new NghiepVuMapper());

		if (nghiepVuDs != null && nghiepVuDs.size() > 0) {
			return nghiepVuDs.get(0);
		}
		return null;
	}

	@Override
	public void capNhatNghiepVu(NghiepVu nghiepVu) {
		if (nghiepVu == null) {
			return;
		}
		String query = CAP_NHAT_NGHIEP_VU;

		try {
			jdbcTmpl.update(query, nghiepVu.getTenNv(), nghiepVu.getMoTa(), nghiepVu.getLinhVuc().getMaLv(),
					nghiepVu.getDoKho(), nghiepVu.getMaNv());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void themNghiepVu(NghiepVu nghiepVu) {
		if (nghiepVu == null) {
			return;
		}

		String query = THEM_NGHIEP_VU;
		try {
			jdbcTmpl.update(query, nghiepVu.getTenNv(), nghiepVu.getMoTa(), nghiepVu.getLinhVuc().getMaLv(),
					nghiepVu.getDoKho());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void xoaNghiepVu(String maNv) {
		logger.info("Xóa tài khoản có MA_TK = " + maNv);
		String xoa = "DELETE FROM NGHIEP_VU WHERE MA_NV=?";
		jdbcTmpl.update(xoa, maNv);
	}
}
