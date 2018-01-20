package com.idi.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.idi.finance.bean.doitac.NganHang;
import com.idi.finance.bean.doitac.NganHangTaiKhoan;
import com.idi.finance.dao.NganHangDAO;

public class NganHangDAOImpl implements NganHangDAO {
	private static final Logger logger = Logger.getLogger(NganHangDAOImpl.class);

	@Value("${DANH_SACH_NGAN_HANG}")
	private String DANH_SACH_NGAN_HANG;

	@Value("${LAY_NGAN_HANG}")
	private String LAY_NGAN_HANG;

	@Value("${THEM_NGAN_HANG}")
	private String THEM_NGAN_HANG;

	@Value("${CAP_NHAT_NGAN_HANG}")
	private String CAP_NHAT_NGAN_HANG;

	@Value("${XOA_NGAN_HANG}")
	private String XOA_NGAN_HANG;

	@Value("${XOA_NGAN_HANG_TAI_KHOAN_THEONH}")
	private String XOA_NGAN_HANG_TAI_KHOAN_THEONH;

	@Value("${DANH_SACH_TAI_KHOAN_NGAN_HANG}")
	private String DANH_SACH_TAI_KHOAN_NGAN_HANG;

	@Value("${LAY_NGAN_HANG_TAI_KHOAN}")
	private String LAY_NGAN_HANG_TAI_KHOAN;

	@Value("${THEM_NGAN_HANG_TAI_KHOAN}")
	private String THEM_NGAN_HANG_TAI_KHOAN;

	@Value("${CAP_NHAT_NGAN_HANG_TAI_KHOAN}")
	private String CAP_NHAT_NGAN_HANG_TAI_KHOAN;

	@Value("${XOA_NGAN_HANG_TAI_KHOAN}")
	private String XOA_NGAN_HANG_TAI_KHOAN;

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public List<NganHang> danhSachNganHang() {
		String query = DANH_SACH_NGAN_HANG;

		List<NganHang> nganHangDs = jdbcTmpl.query(query, new NganHangMapper());
		return nganHangDs;
	}

	public class NganHangMapper implements RowMapper<NganHang> {
		public NganHang mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				NganHang nganHang = new NganHang();

				nganHang.setMaNh(rs.getInt("MA_NH"));
				nganHang.setTenVt(rs.getString("TEN_VT"));
				nganHang.setTenDd(rs.getString("TEN_DD"));
				nganHang.setTenTa(rs.getString("TEN_TA"));
				nganHang.setBieuTuong(rs.getString("BIEU_TUONG"));
				nganHang.setDiaChi(rs.getString("DIA_CHI_HSC"));
				nganHang.setMoTa(rs.getString("MO_TA"));

				return nganHang;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public NganHang layNganHang(int maNh) {
		String query = LAY_NGAN_HANG;
		try {
			Object[] params = { maNh };
			return jdbcTmpl.queryForObject(query, params, new NganHangMapper());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void themNganHang(NganHang nganHang) {
		String query = THEM_NGAN_HANG;

		jdbcTmpl.update(query, nganHang.getTenVt(), nganHang.getTenDd(), nganHang.getTenTa(), nganHang.getDiaChi(),
				nganHang.getBieuTuong(), nganHang.getMoTa());
	}

	@Override
	public void capNhatNganHang(NganHang nganHang) {
		String query = CAP_NHAT_NGAN_HANG;

		jdbcTmpl.update(query, nganHang.getTenVt(), nganHang.getTenDd(), nganHang.getTenTa(), nganHang.getDiaChi(),
				nganHang.getBieuTuong(), nganHang.getMoTa(), nganHang.getMaNh());
	}

	@Override
	public void xoaNganHang(int maNh) {
		String xoaNganHang = XOA_NGAN_HANG;
		String xoaNganHangTaiKhoan = XOA_NGAN_HANG_TAI_KHOAN_THEONH;

		jdbcTmpl.update(xoaNganHang, maNh);
		jdbcTmpl.update(xoaNganHangTaiKhoan, maNh);
		logger.info("Xóa ngân hàng có MA_NH = " + maNh);
	}

	@Override
	public List<NganHangTaiKhoan> danhSachTaiKhoanNganHang() {
		String query = DANH_SACH_TAI_KHOAN_NGAN_HANG;

		List<NganHangTaiKhoan> nganHangTaiKhoanDs = jdbcTmpl.query(query, new NganHangTaiKhoanMapper());
		return nganHangTaiKhoanDs;
	}

	public class NganHangTaiKhoanMapper implements RowMapper<NganHangTaiKhoan> {
		public NganHangTaiKhoan mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				NganHangTaiKhoan nganHangTaiKhoan = new NganHangTaiKhoan();

				nganHangTaiKhoan.setMaTk(rs.getInt("MA_TK"));
				nganHangTaiKhoan.setSoTk(rs.getString("SO_TK"));
				nganHangTaiKhoan.setChiNhanh(rs.getString("CHI_NHANH"));
				nganHangTaiKhoan.setDiaChiCn(rs.getString("DIA_CHI"));
				nganHangTaiKhoan.setChuTk(rs.getString("CHU_TK"));
				nganHangTaiKhoan.setMoTa(rs.getString("MO_TA"));

				NganHang nganHang = new NganHang();
				nganHang.setMaNh(rs.getInt("MA_NH"));
				nganHang.setTenVt(rs.getString("TEN_VT"));
				nganHang.setTenDd(rs.getString("TEN_DD"));

				nganHangTaiKhoan.setNganHang(nganHang);

				return nganHangTaiKhoan;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public NganHangTaiKhoan layTaiKhoanNganHang(int maTk) {
		String query = LAY_NGAN_HANG_TAI_KHOAN;
		try {
			Object[] params = { maTk };
			return jdbcTmpl.queryForObject(query, params, new NganHangTaiKhoanMapper());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void themTaiKhoanNganHang(NganHangTaiKhoan nganHangTaiKhoan) {
		String query = THEM_NGAN_HANG_TAI_KHOAN;

		jdbcTmpl.update(query, nganHangTaiKhoan.getSoTk(), nganHangTaiKhoan.getNganHang().getMaNh(),
				nganHangTaiKhoan.getChiNhanh(), nganHangTaiKhoan.getDiaChiCn(), nganHangTaiKhoan.getChuTk(),
				nganHangTaiKhoan.getMoTa());
	}

	@Override
	public void capNhatTaiKhoanNganHang(NganHangTaiKhoan nganHangTaiKhoan) {
		String query = CAP_NHAT_NGAN_HANG_TAI_KHOAN;

		jdbcTmpl.update(query, nganHangTaiKhoan.getSoTk(), nganHangTaiKhoan.getNganHang().getMaNh(),
				nganHangTaiKhoan.getChiNhanh(), nganHangTaiKhoan.getDiaChiCn(), nganHangTaiKhoan.getChuTk(),
				nganHangTaiKhoan.getMoTa(), nganHangTaiKhoan.getMaTk());
	}

	@Override
	public void xoaTaiKhoanNganHang(int maTk) {
		String xoaNganHangTaiKhoan = XOA_NGAN_HANG_TAI_KHOAN;

		jdbcTmpl.update(xoaNganHangTaiKhoan, maTk);
		logger.info("Xóa tài khoản ngân hàng có MA_TK = " + maTk);
	}

}
