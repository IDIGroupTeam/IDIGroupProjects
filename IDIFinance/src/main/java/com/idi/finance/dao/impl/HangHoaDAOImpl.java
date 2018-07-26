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
import com.idi.finance.bean.hanghoa.KhoHang;
import com.idi.finance.bean.hanghoa.NhomHang;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
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

	@Value("${DANH_SACH_HANG_HOA_THEO_TEN}")
	private String DANH_SACH_HANG_HOA_THEO_TEN;

	@Value("${DANH_SACH_KH_HANG_HOA}")
	private String DANH_SACH_KH_HANG_HOA;

	@Value("${LAY_HANG_HOA}")
	private String LAY_HANG_HOA;

	@Value("${KIEM_TRA_KH_HH}")
	private String KIEM_TRA_KH_HH;

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
	public NhomHang danhSachNhomHangHoa(NhomHang nhomHangHoa) {
		String query = DANH_SACH_NHOM_HANG_HOA_THEO_CHA;
		logger.info(query);
		logger.info(nhomHangHoa.getMaNhomHh());

		Object[] params = { nhomHangHoa.getMaNhomHh() };
		List<NhomHang> nhomHangHoaDs = jdbcTmpl.query(query, params, new NhomHangHoaMapper());

		if (nhomHangHoaDs != null && nhomHangHoaDs.size() > 0) {
			int doSau = 1;

			Iterator<NhomHang> iter = nhomHangHoaDs.iterator();
			while (iter.hasNext()) {
				NhomHang nhomHangHoaCon = iter.next();
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
	public List<NhomHang> danhSachNhomHangHoa() {
		String query = DANH_SACH_NHOM_HANG_HOA;

		List<NhomHang> nhomHangHoaDs = jdbcTmpl.query(query, new NhomHangHoaMapper());

		return nhomHangHoaDs;
	}

	public class NhomHangHoaMapper implements RowMapper<NhomHang> {
		public NhomHang mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				NhomHang nhomHangHoa = new NhomHang();

				nhomHangHoa.setMaNhomHh(rs.getInt("MA_NHOM_HH"));
				nhomHangHoa.setKyHieuNhomHh(rs.getString("KH_NHOM_HH"));
				nhomHangHoa.setTenNhomHh(rs.getString("TEN_NHOM_HH"));

				NhomHang nhomHangHoaCha = new NhomHang();
				nhomHangHoaCha.setMaNhomHh(rs.getInt("MA_NHOM_HH_CHA"));
				nhomHangHoa.setNhomHh(nhomHangHoaCha);

				return nhomHangHoa;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public NhomHang layNhomHangHoa(int maNhomHn) {
		String query = LAY_NHOM_HANG_HOA;

		try {
			Object[] objs = { maNhomHn };
			return jdbcTmpl.queryForObject(query, objs, new NhomHangHoaMapper());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void capNhatNhomHangHoa(NhomHang nhomHangHoa) {
		if (nhomHangHoa == null || nhomHangHoa.getNhomHh() == null) {
			return;
		}
		String query = CAP_NHAT_NHOM_HANG_HOA;

		try {
			jdbcTmpl.update(query, nhomHangHoa.getKyHieuNhomHh(), nhomHangHoa.getTenNhomHh(),
					nhomHangHoa.getNhomHh().getMaNhomHh(), nhomHangHoa.getMaNhomHh());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void themNhomHangHoa(NhomHang nhomHangHoa) {
		if (nhomHangHoa == null || nhomHangHoa.getNhomHh() == null) {
			return;
		}

		String query = THEM_NHOM_HANG_HOA;
		try {
			jdbcTmpl.update(query, nhomHangHoa.getKyHieuNhomHh(), nhomHangHoa.getTenNhomHh(),
					nhomHangHoa.getNhomHh().getMaNhomHh());
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

	@Override
	public List<HangHoa> danhSachHangHoa(String tuKhoa) {
		if (tuKhoa == null || tuKhoa.trim().equals("")) {
			String query = DANH_SACH_HANG_HOA;

			List<HangHoa> hangHoaDs = jdbcTmpl.query(query, new HangHoaMapper());
			return hangHoaDs;
		} else {
			String query = DANH_SACH_HANG_HOA_THEO_TEN;
			query = query.replaceAll("\\?", tuKhoa.trim());

			List<HangHoa> hangHoaDs = jdbcTmpl.query(query, new HangHoaMapper());

			return hangHoaDs;
		}
	}

	public class HangHoaMapper implements RowMapper<HangHoa> {
		public HangHoa mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				HangHoa hangHoa = new HangHoa();

				hangHoa.setMaHh(rs.getInt("MA_HH"));
				hangHoa.setKyHieuHh(rs.getString("KH_HH"));
				hangHoa.setTenHh(rs.getString("TEN_HH"));
				hangHoa.setKyHieuTenHh(rs.getString("KH_HH") + " - " + rs.getString("TEN_HH"));

				DonVi donVi = new DonVi();
				donVi.setMaDv(rs.getInt("MA_DV"));
				donVi.setTenDv(rs.getString("TEN_DV"));
				donVi.setMoTa(rs.getString("MO_TA"));
				hangHoa.setDonVi(donVi);

				NhomHang nhomHangHoa = new NhomHang();
				nhomHangHoa.setMaNhomHh(rs.getInt("MA_NHOM_HH"));
				nhomHangHoa.setKyHieuNhomHh(rs.getString("KH_NHOM_HH"));
				nhomHangHoa.setTenNhomHh(rs.getString("TEN_NHOM_HH"));
				hangHoa.setNhomHh(nhomHangHoa);

				hangHoa.setTinhChat(rs.getInt("TINH_CHAT"));
				hangHoa.setMoTa(rs.getString("MO_TA"));
				hangHoa.setNguonGoc(rs.getString("NGUON_GOC"));
				hangHoa.setThoiHanBh(rs.getInt("THOI_HAN_BH"));

				int maKho = rs.getInt("MA_KHO");
				if (maKho > 0) {
					KhoHang kho = layKhoBai(maKho);
					hangHoa.setKhoMd(kho);
				}

				LoaiTaiKhoan tkKhoMd = new LoaiTaiKhoan();
				tkKhoMd.setMaTk(rs.getString("MA_TK_KHO"));
				hangHoa.setTkKhoMd(tkKhoMd);

				LoaiTaiKhoan tkDoanhThuMd = new LoaiTaiKhoan();
				tkDoanhThuMd.setMaTk(rs.getString("MA_TK_DT"));
				hangHoa.setTkDoanhThuMd(tkDoanhThuMd);

				LoaiTaiKhoan tkChiPhiMd = new LoaiTaiKhoan();
				tkChiPhiMd.setMaTk(rs.getString("MA_TK_CP"));
				hangHoa.setTkChiPhiMd(tkChiPhiMd);

				LoaiTaiKhoan tkChietKhauMd = new LoaiTaiKhoan();
				tkChietKhauMd.setMaTk(rs.getString("MA_TK_CK"));
				hangHoa.setTkChietKhauMd(tkChietKhauMd);

				LoaiTaiKhoan tkGiamGiaMd = new LoaiTaiKhoan();
				tkGiamGiaMd.setMaTk(rs.getString("MA_TK_GG"));
				hangHoa.setTkGiamGiaMd(tkGiamGiaMd);

				LoaiTaiKhoan tkTraLaiMd = new LoaiTaiKhoan();
				tkTraLaiMd.setMaTk(rs.getString("MA_TK_TL"));
				hangHoa.setTkTraLaiMd(tkTraLaiMd);

				hangHoa.setTyLeCktmMd(rs.getDouble("TY_LE_CKTM"));
				hangHoa.setThueSuatGtgtMd(rs.getDouble("THUE_SUAT_GTGT"));
				hangHoa.setThueSuatXkMd(rs.getDouble("THUE_SUAT_XK"));
				hangHoa.setThueSuatNkMd(rs.getDouble("THUE_SUAT_NK"));
				hangHoa.setThueSuatTtdbMd(rs.getDouble("THUE_SUAT_TTDB"));

				return hangHoa;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public List<HangHoa> danhSachKhHangHoa() {
		String query = DANH_SACH_KH_HANG_HOA;

		List<HangHoa> hangHoaDs = jdbcTmpl.query(query, new KhHangHoaMapper());
		return hangHoaDs;
	}

	public class KhHangHoaMapper implements RowMapper<HangHoa> {
		public HangHoa mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				HangHoa hangHoa = new HangHoa();

				hangHoa.setMaHh(rs.getInt("MA_HH"));
				hangHoa.setKyHieuHh(rs.getString("KH_HH"));
				hangHoa.setTenHh(rs.getString("TEN_HH"));
				hangHoa.setKyHieuTenHh(rs.getString("KH_HH") + " - " + rs.getString("TEN_HH"));

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
			logger.info(query);
			logger.info("maHh " + maHh);
			return jdbcTmpl.queryForObject(query, objs, new HangHoaMapper());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean kiemTraDuyNhat(int maHh, String kyHieuHh) {
		if (kyHieuHh == null || kyHieuHh.trim().equals("")) {
			return true;
		}

		String query = KIEM_TRA_KH_HH;
		try {
			Object[] objs = { kyHieuHh, maHh };
			Integer number = jdbcTmpl.queryForObject(query, objs, Integer.class);

			if (number > 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return true;
		}
	};

	@Override
	public void capNhatHangHoa(HangHoa hangHoa) {
		if (hangHoa == null || hangHoa.getDonVi() == null || hangHoa.getNhomHh() == null) {
			return;
		}
		String query = CAP_NHAT_HANG_HOA;

		try {
			jdbcTmpl.update(query, hangHoa.getKyHieuHh(), hangHoa.getTenHh(), hangHoa.getDonVi().getMaDv(),
					hangHoa.getNhomHh().getMaNhomHh(), hangHoa.getTinhChat(), hangHoa.getMoTa(), hangHoa.getNguonGoc(),
					hangHoa.getThoiHanBh(), hangHoa.getKhoMd().getMaKho(), hangHoa.getTkKhoMd().getMaTk(),
					hangHoa.getTkDoanhThuMd().getMaTk(), hangHoa.getTkChiPhiMd().getMaTk(),
					hangHoa.getTkChietKhauMd().getMaTk(), hangHoa.getTkGiamGiaMd().getMaTk(),
					hangHoa.getTkTraLaiMd().getMaTk(), hangHoa.getTyLeCktmMd(), hangHoa.getThueSuatGtgtMd(),
					hangHoa.getThueSuatXkMd(), hangHoa.getThueSuatNkMd(), hangHoa.getThueSuatTtdbMd(),
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
			jdbcTmpl.update(query, hangHoa.getKyHieuHh(), hangHoa.getTenHh(), hangHoa.getDonVi().getMaDv(),
					hangHoa.getNhomHh().getMaNhomHh(), hangHoa.getTinhChat(), hangHoa.getMoTa(), hangHoa.getNguonGoc(),
					hangHoa.getThoiHanBh(), hangHoa.getKhoMd().getMaKho(), hangHoa.getTkKhoMd().getMaTk(),
					hangHoa.getTkDoanhThuMd().getMaTk(), hangHoa.getTkChiPhiMd().getMaTk(),
					hangHoa.getTkChietKhauMd().getMaTk(), hangHoa.getTkGiamGiaMd().getMaTk(),
					hangHoa.getTkTraLaiMd().getMaTk(), hangHoa.getTyLeCktmMd(), hangHoa.getThueSuatGtgtMd(),
					hangHoa.getThueSuatXkMd(), hangHoa.getThueSuatNkMd(), hangHoa.getThueSuatTtdbMd());
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
	public List<KhoHang> danhSachKhoBai() {
		String query = DANH_SACH_KHO;

		logger.info("Danh sách kho bãi ...");
		logger.info(query);

		List<KhoHang> hangHoaDs = jdbcTmpl.query(query, new KhoBaiMapper());
		return hangHoaDs;
	}

	public class KhoBaiMapper implements RowMapper<KhoHang> {
		public KhoHang mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				KhoHang khoBai = new KhoHang();

				khoBai.setMaKho(rs.getInt("MA_KHO"));
				khoBai.setKyHieuKho(rs.getString("KH_KHO"));
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
	public KhoHang layKhoBai(int maKho) {
		String query = LAY_KHO;

		try {
			Object[] objs = { maKho };
			return jdbcTmpl.queryForObject(query, objs, new KhoBaiMapper());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void capNhatKhoBai(KhoHang khoBai) {
		if (khoBai == null) {
			return;
		}
		String query = CAP_NHAT_KHO;

		try {
			jdbcTmpl.update(query, khoBai.getKyHieuKho(), khoBai.getTenKho(), khoBai.getDiaChi(), khoBai.getMoTa(),
					khoBai.getMaKho());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void themKhoBai(KhoHang khoBai) {
		if (khoBai == null) {
			return;
		}
		String query = THEM_KHO;

		try {
			jdbcTmpl.update(query, khoBai.getKyHieuKho(), khoBai.getTenKho(), khoBai.getDiaChi(), khoBai.getMoTa());
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
