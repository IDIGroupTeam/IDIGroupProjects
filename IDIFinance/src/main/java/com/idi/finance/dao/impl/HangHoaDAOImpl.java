package com.idi.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.idi.finance.bean.hanghoa.DonVi;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.hanghoa.KhoBai;
import com.idi.finance.bean.hanghoa.NhomHangHoa;
import com.idi.finance.dao.HangHoaDAO;

public class HangHoaDAOImpl implements HangHoaDAO {
	private static final Logger logger = Logger.getLogger(HangHoaDAOImpl.class);

	@Value("${DANH_SACH_DON_VI}")
	private String DANH_SACH_DON_VI;

	@Value("${LAY_DON_VI}")
	private String LAY_DON_VI;

	@Value("${CAP_NHAT_DON_VI}")
	private String CAP_NHAT_DON_VI;

	@Value("${THEM_DON_VI}")
	private String THEM_DON_VI;

	@Value("${DANH_SACH_NHOM_HANG_HOA}")
	private String DANH_SACH_NHOM_HANG_HOA;

	@Value("${DANH_SACH_NHOM_HANG_HOA_THEO_CHA}")
	private String DANH_SACH_NHOM_HANG_HOA_THEO_CHA;

	@Value("${LAY_NHOM_HANG_HOA}")
	private String LAY_NHOM_HANG_HOA;

	@Value("${CAP_NHAT_NHOM_HANG_HOA}")
	private String CAP_NHAT_NHOM_HANG_HOA;

	@Value("${THEM_NHOM_HANG_HOA}")
	private String THEM_NHOM_HANG_HOA;

	@Value("${DANH_SACH_HANG_HOA}")
	private String DANH_SACH_HANG_HOA;

	@Value("${LAY_HANG_HOA}")
	private String LAY_HANG_HOA;

	@Value("${CAP_NHAT_HANG_HOA}")
	private String CAP_NHAT_HANG_HOA;

	@Value("${THEM_HANG_HOA}")
	private String THEM_HANG_HOA;

	@Value("${DANH_SACH_KHO}")
	private String DANH_SACH_KHO;

	@Value("${LAY_KHO}")
	private String LAY_KHO;

	@Value("${CAP_NHAT_KHO}")
	private String CAP_NHAT_KHO;

	@Value("${THEM_KHO}")
	private String THEM_KHO;

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public List<DonVi> danhSachDonViHangHoa() {
		String query = DANH_SACH_DON_VI;

		logger.info("Danh sách đơn vị tính của hàng hóa ...");
		logger.info(query);

		List<DonVi> donViDs = jdbcTmpl.query(query, new DonViMapper());
		return donViDs;
	}

	public class DonViMapper implements RowMapper<DonVi> {
		public DonVi mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				DonVi donVi = new DonVi();

				donVi.setMaDv(rs.getInt("MA_DV"));
				donVi.setTenDv(rs.getString("TEN_DV"));
				donVi.setMoTa(rs.getString("MO_TA"));

				return donVi;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public DonVi layDonVi(int maDv) {
		String query = LAY_DON_VI;

		try {
			Object[] objs = { maDv };
			return jdbcTmpl.queryForObject(query, objs, new DonViMapper());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void capNhatDonVi(DonVi donVi) {
		if (donVi == null) {
			return;
		}
		String query = CAP_NHAT_DON_VI;

		try {
			jdbcTmpl.update(query, donVi.getTenDv(), donVi.getMoTa(), donVi.getMaDv());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void themDonVi(DonVi donVi) {
		if (donVi == null) {
			return;
		}

		String query = THEM_DON_VI;
		try {
			jdbcTmpl.update(query, donVi.getTenDv(), donVi.getMoTa());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void xoaDonVi(int maDv) {
		logger.info("Xóa đơn vị tính có MA_DV = " + maDv);
		String xoa = "DELETE FROM HANG_HOA_DON_VI WHERE MA_DV=?";
		jdbcTmpl.update(xoa, maDv);
	}

	@Override
	public NhomHangHoa danhSachNhomHangHoa(NhomHangHoa nhomHangHoa) {
		String query = DANH_SACH_NHOM_HANG_HOA_THEO_CHA;

		Object[] params = { nhomHangHoa.getMaNhomHh() };
		List<NhomHangHoa> nhomHangHoaDs = jdbcTmpl.query(query, params, new NhomHangHoaMapper());

		if (nhomHangHoaDs != null && nhomHangHoaDs.size() > 0) {
			int doSau = 1;

			Iterator<NhomHangHoa> iter = nhomHangHoaDs.iterator();
			while (iter.hasNext()) {
				NhomHangHoa nhomHangHoaCon = iter.next();
				nhomHangHoaCon.setNhomHh(nhomHangHoa);
				nhomHangHoaCon.setMuc(nhomHangHoa.getMuc() + 1);

				nhomHangHoaCon = danhSachNhomHangHoa(nhomHangHoaCon);
				doSau = doSau > nhomHangHoaCon.getDoSau() ? doSau : nhomHangHoaCon.getDoSau();
			}

			nhomHangHoa.setDoSau(doSau + 1);
			nhomHangHoa.themNhomHh(nhomHangHoaDs);
		}

		return nhomHangHoa;
	}

	@Override
	public List<NhomHangHoa> danhSachNhomHangHoa() {
		String query = DANH_SACH_NHOM_HANG_HOA;

		List<NhomHangHoa> nhomHangHoaDs = jdbcTmpl.query(query, new NhomHangHoaMapper());

		return nhomHangHoaDs;
	}

	public class NhomHangHoaMapper implements RowMapper<NhomHangHoa> {
		public NhomHangHoa mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				NhomHangHoa nhomHangHoa = new NhomHangHoa();

				nhomHangHoa.setMaNhomHh(rs.getInt("MA_NHOM_HH"));
				nhomHangHoa.setTenNhomHh(rs.getString("TEN_NHOM_HH"));

				NhomHangHoa nhomHangHoaCha = new NhomHangHoa();
				nhomHangHoaCha.setMaNhomHh(rs.getInt("MA_NHOM_HH_CHA"));
				nhomHangHoa.setNhomHh(nhomHangHoaCha);

				return nhomHangHoa;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public NhomHangHoa layNhomHangHoa(int maNhomHn) {
		String query = LAY_NHOM_HANG_HOA;

		try {
			Object[] objs = { maNhomHn };
			return jdbcTmpl.queryForObject(query, objs, new NhomHangHoaMapper());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void capNhatNhomHangHoa(NhomHangHoa nhomHangHoa) {
		if (nhomHangHoa == null || nhomHangHoa.getNhomHh() == null) {
			return;
		}
		String query = CAP_NHAT_NHOM_HANG_HOA;

		try {
			jdbcTmpl.update(query, nhomHangHoa.getTenNhomHh(), nhomHangHoa.getNhomHh().getMaNhomHh(),
					nhomHangHoa.getMaNhomHh());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void themNhomHangHoa(NhomHangHoa nhomHangHoa) {
		if (nhomHangHoa == null || nhomHangHoa.getNhomHh() == null) {
			return;
		}

		String query = THEM_NHOM_HANG_HOA;
		try {
			jdbcTmpl.update(query, nhomHangHoa.getTenNhomHh(), nhomHangHoa.getNhomHh().getMaNhomHh());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void xoaNhomHangHoa(int maNhomHh) {
		logger.info("Xóa nhóm hàng hóa MA_NHOM_HH = " + maNhomHh);
		String xoa = "DELETE FROM HANG_HOA_NHOM WHERE MA_NHOM_HH=?";
		jdbcTmpl.update(xoa, maNhomHh);
	}

	@Override
	public List<HangHoa> danhSachHangHoa() {
		String query = DANH_SACH_HANG_HOA;

		logger.info("Danh sách hàng hóa ...");
		logger.info(query);

		List<HangHoa> hangHoaDs = jdbcTmpl.query(query, new HangHoaMapper());
		return hangHoaDs;
	}

	public class HangHoaMapper implements RowMapper<HangHoa> {
		public HangHoa mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				HangHoa hangHoa = new HangHoa();

				hangHoa.setMaHh(rs.getInt("MA_HH"));
				hangHoa.setTenHh(rs.getString("TEN_HH"));

				DonVi donVi = new DonVi();
				donVi.setMaDv(rs.getInt("MA_DV"));
				donVi.setTenDv(rs.getString("TEN_DV"));
				donVi.setMoTa(rs.getString("MO_TA"));
				hangHoa.setDonVi(donVi);

				NhomHangHoa nhomHangHoa = new NhomHangHoa();
				nhomHangHoa.setMaNhomHh(rs.getInt("MA_NHOM_HH"));
				nhomHangHoa.setTenNhomHh(rs.getString("TEN_NHOM_HH"));
				hangHoa.setNhomHh(nhomHangHoa);

				return hangHoa;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public HangHoa layHangHoa(int maHh) {
		String query = LAY_HANG_HOA;

		try {
			Object[] objs = { maHh };
			return jdbcTmpl.queryForObject(query, objs, new HangHoaMapper());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void capNhatHangHoa(HangHoa hangHoa) {
		if (hangHoa == null || hangHoa.getDonVi() == null || hangHoa.getNhomHh() == null) {
			return;
		}
		String query = CAP_NHAT_HANG_HOA;

		try {
			jdbcTmpl.update(query, hangHoa.getTenHh(), hangHoa.getDonVi().getMaDv(), hangHoa.getNhomHh().getMaNhomHh(),
					hangHoa.getMaHh());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void themHangHoa(HangHoa hangHoa) {
		if (hangHoa == null || hangHoa.getDonVi() == null || hangHoa.getNhomHh() == null) {
			return;
		}

		String query = THEM_HANG_HOA;
		try {
			jdbcTmpl.update(query, hangHoa.getTenHh(), hangHoa.getDonVi().getMaDv(), hangHoa.getNhomHh().getMaNhomHh());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void xoaHangHoa(int maHh) {
		logger.info("Xóa hàng hóa MA_HH = " + maHh);
		String xoa = "DELETE FROM HANG_HOA_DANH_MUC WHERE MA_HH=?";
		jdbcTmpl.update(xoa, maHh);
	}

	@Override
	public List<KhoBai> danhSachKhoBai() {
		String query = DANH_SACH_KHO;

		logger.info("Danh sách kho bãi ...");
		logger.info(query);

		List<KhoBai> hangHoaDs = jdbcTmpl.query(query, new KhoBaiMapper());
		return hangHoaDs;
	}

	public class KhoBaiMapper implements RowMapper<KhoBai> {
		public KhoBai mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				KhoBai khoBai = new KhoBai();

				khoBai.setMaKho(rs.getInt("MA_KHO"));
				khoBai.setTenKho(rs.getString("TEN_KHO"));
				khoBai.setDiaChi(rs.getString("DIA_CHI"));
				khoBai.setMoTa(rs.getString("MO_TA"));

				return khoBai;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public KhoBai layKhoBai(int maKho) {
		String query = LAY_KHO;

		try {
			Object[] objs = { maKho };
			return jdbcTmpl.queryForObject(query, objs, new KhoBaiMapper());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void capNhatKhoBai(KhoBai khoBai) {
		if (khoBai == null) {
			return;
		}
		String query = CAP_NHAT_KHO;

		try {
			jdbcTmpl.update(query, khoBai.getTenKho(), khoBai.getDiaChi(), khoBai.getMoTa(), khoBai.getMaKho());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void themKhoBai(KhoBai khoBai) {
		if (khoBai == null) {
			return;
		}
		String query = THEM_KHO;

		try {
			jdbcTmpl.update(query, khoBai.getTenKho(), khoBai.getDiaChi(), khoBai.getMoTa());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void xoaKhoBai(int maKho) {
		logger.info("Xóa kho MA_HH = " + maKho);
		String xoa = "DELETE FROM HANG_HOA_KHO WHERE MA_KHO=?";
		jdbcTmpl.update(xoa, maKho);
	}
}
