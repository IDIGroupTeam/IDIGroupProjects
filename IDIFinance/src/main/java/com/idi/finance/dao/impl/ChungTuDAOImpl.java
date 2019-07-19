package com.idi.finance.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.idi.finance.bean.LoaiTien;
import com.idi.finance.bean.Tien;
import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.KetChuyenButToan;
import com.idi.finance.bean.doituong.DoiTuong;
import com.idi.finance.bean.hanghoa.DonVi;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.hanghoa.KhoHang;
import com.idi.finance.bean.hanghoa.NhomHang;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.bean.taikhoan.TaiKhoan;
import com.idi.finance.dao.ChungTuDAO;
import com.idi.finance.utils.ExpressionEval;
import com.idi.finance.utils.Utils;

public class ChungTuDAOImpl implements ChungTuDAO {
	private static final Logger logger = Logger.getLogger(ChungTuDAOImpl.class);

	@Value("${DANH_SACH_CHUNG_TU_THEO_LOAI}")
	private String DANH_SACH_CHUNG_TU_THEO_LOAI;

	@Value("${DANH_SACH_CHUNG_TU_THEO_LOAI_KTTH}")
	private String DANH_SACH_CHUNG_TU_THEO_LOAI_KTTH;

	@Value("${DANH_SACH_CHUNG_TU_KHO_THEO_LOAI}")
	private String DANH_SACH_CHUNG_TU_KHO_THEO_LOAI;

	@Value("${LAY_CHUNG_TU_THEO_MACT_LOAICT}")
	private String LAY_CHUNG_TU_THEO_MACT_LOAICT;

	@Value("${LAY_CHUNG_TU_THEO_MACT_LOAICT_KTTH}")
	private String LAY_CHUNG_TU_THEO_MACT_LOAICT_KTTH;

	@Value("${LAY_CHUNG_TU_KHO_THEO_MACT_LOAICT}")
	private String LAY_CHUNG_TU_KHO_THEO_MACT_LOAICT;

	@Value("${DANH_SACH_LOAI_TIEN}")
	private String DANH_SACH_LOAI_TIEN;

	@Value("${DEM_SO_CHUNG_TU}")
	private String DEM_SO_CHUNG_TU;

	@Value("${LAY_SO_CHUNG_TU_LON_NHAT}")
	private String LAY_SO_CHUNG_TU_LON_NHAT;

	@Value("${KIEM_TRA_SO_CHUNG_TU}")
	private String KIEM_TRA_SO_CHUNG_TU;

	@Value("${THEM_CHUNG_TU}")
	private String THEM_CHUNG_TU;

	@Value("${THEM_CHUNG_TU_TAI_KHOAN}")
	private String THEM_CHUNG_TU_TAI_KHOAN;

	@Value("${THEM_CHUNG_TU_TAI_KHOAN_KTTH}")
	private String THEM_CHUNG_TU_TAI_KHOAN_KTTH;

	@Value("${CAP_NHAT_CHUNG_TU}")
	private String CAP_NHAT_CHUNG_TU;

	@Value("${CAP_NHAT_CHUNG_TU_NVKT}")
	private String CAP_NHAT_CHUNG_TU_NVKT;

	@Value("${LAY_CHUNG_TU_TAI_KHOAN}")
	private String LAY_CHUNG_TU_TAI_KHOAN;

	@Value("${XOA_CHUNG_TU_NVKT}")
	private String XOA_CHUNG_TU_NVKT;

	@Value("${THEM_CHUNG_TU_KHO}")
	private String THEM_CHUNG_TU_KHO;

	@Value("${THEM_TAI_KHOAN}")
	private String THEM_TAI_KHOAN;

	@Value("${THEM_TAI_KHOAN_KTTH}")
	private String THEM_TAI_KHOAN_KTTH;

	@Value("${THEM_CHUNG_TU_TAI_KHOAN_KHO}")
	private String THEM_CHUNG_TU_TAI_KHOAN_KHO;

	@Value("${THEM_CHUNG_TU_HANG_HOA}")
	private String THEM_CHUNG_TU_HANG_HOA;

	@Value("${THEM_DON_GIA}")
	private String THEM_DON_GIA;

	@Value("${KIEM_TRA_DON_GIA}")
	private String KIEM_TRA_DON_GIA;

	@Value("${CAP_NHAT_CHUNG_TU_KHO}")
	private String CAP_NHAT_CHUNG_TU_KHO;

	@Value("${XOA_CHUNG_TU_HANG_HOA}")
	private String XOA_CHUNG_TU_HANG_HOA;

	@Value("${XOA_CHUNG_TU_NVKT_KHO}")
	private String XOA_CHUNG_TU_NVKT_KHO;

	@Value("${XOA_NVKT}")
	private String XOA_NVKT;

	@Value("${XOA_CHUNG_TU_THEO_MACT_LOAICT}")
	private String XOA_CHUNG_TU_THEO_MACT_LOAICT;

	@Value("${DANH_SACH_KET_CHUYEN_BUT_TOAN}")
	private String DANH_SACH_KET_CHUYEN_BUT_TOAN;

	@Value("${DANH_SACH_KET_CHUYEN_BUT_TOAN_THEO_LOAI}")
	private String DANH_SACH_KET_CHUYEN_BUT_TOAN_THEO_LOAI;

	@Value("${THEM_KET_CHUYEN_BUT_TOAN}")
	private String THEM_KET_CHUYEN_BUT_TOAN;

	@Value("${CAP_NHAT_KET_CHUYEN_BUT_TOAN}")
	private String CAP_NHAT_KET_CHUYEN_BUT_TOAN;

	@Value("${LAY_KET_CHUYEN_BUT_TOAN}")
	private String LAY_KET_CHUYEN_BUT_TOAN;

	@Value("${XOA_KET_CHUYEN_BUT_TOAN}")
	private String XOA_KET_CHUYEN_BUT_TOAN;

	@Value("${DANH_SACH_CHUNG_TU_KET_CHUYEN}")
	private String DANH_SACH_CHUNG_TU_KET_CHUYEN;

	@Value("${LAY_CHUNG_TU_KET_CHUYEN}")
	private String LAY_CHUNG_TU_KET_CHUYEN;

	@Value("${LAY_NGAY_KET_CHUYEN_GAN_NHAT}")
	private String LAY_NGAY_KET_CHUYEN_GAN_NHAT;

	@Value("${THEM_CHUNG_TU_KET_CHUYEN}")
	private String THEM_CHUNG_TU_KET_CHUYEN;

	@Value("${THEM_CHUNG_TU_TAI_KHOAN_KET_CHUYEN}")
	private String THEM_CHUNG_TU_TAI_KHOAN_KET_CHUYEN;

	@Value("${TONG_PHAT_SINH}")
	private String TONG_PHAT_SINH;

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public List<ChungTu> danhSachChungTu(List<String> loaiCts, Date batDau, Date ketThuc) {
		if (loaiCts == null || loaiCts.size() == 0 || batDau == null || ketThuc == null) {
			return null;
		}

		String query = DANH_SACH_CHUNG_TU_THEO_LOAI;

		if (loaiCts.contains(ChungTu.TAT_CA)) {
			query = query.replaceAll("\\$DIEU_KIEN_LOAI_CT\\$", "");
			logger.info("Danh sách tất cả chứng từ ...");
		} else {
			logger.info("Danh sách tất cả chứng từ loại " + loaiCts + " ...");

			String dieuKien = "";
			Iterator<String> iter = loaiCts.iterator();
			while (iter.hasNext()) {
				String loaiCt = iter.next();
				dieuKien += "'" + loaiCt.trim() + "',";
			}
			if (!dieuKien.equals("")) {
				dieuKien = dieuKien.substring(0, dieuKien.length() - 1);
			}
			dieuKien = " AND CT.LOAI_CT IN (" + dieuKien + ")";

			query = query.replaceAll("\\$DIEU_KIEN_LOAI_CT\\$", dieuKien);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		String batDauStr = sdf.format(batDau);
		String ketThucStr = sdf.format(ketThuc);

		logger.info("Danh sách chứng từ theo loại chứng từ: '" + loaiCts + "' từ " + batDauStr + " đến " + ketThucStr);
		logger.info(query);

		Object[] objs = { batDauStr, ketThucStr, batDauStr, ketThucStr, batDauStr, ketThucStr };
		List<ChungTu> chungTuDs = jdbcTmpl.query(query, objs, new ChungTuMapper());

		// Gộp chứng từ trùng nhau nhưng có tài khoản ảnh hường khác nhau
		List<ChungTu> ketQua = null;
		if (chungTuDs != null) {
			ketQua = new ArrayList<>();
			Iterator<ChungTu> iter = chungTuDs.iterator();
			while (iter.hasNext()) {
				ChungTu chungTu = iter.next();

				int pos = ketQua.indexOf(chungTu);
				if (pos > -1) {
					ChungTu chungTuTmpl = ketQua.get(pos);
					chungTuTmpl.themTaiKhoan(chungTu.getTaiKhoanDs());
				} else {
					ketQua.add(chungTu);
				}
			}
		}

		return ketQua;
	}

	public class ChungTuMapper implements RowMapper<ChungTu> {
		public ChungTu mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				ChungTu chungTu = new ChungTu();
				chungTu.setMaCt(rs.getInt("MA_CT"));
				chungTu.setSoCt(rs.getInt("SO_CT"));
				chungTu.setLoaiCt(rs.getString("LOAI_CT"));
				chungTu.setTinhChatCt(rs.getInt("TINH_CHAT"));
				chungTu.setLyDo(rs.getString("LY_DO"));
				chungTu.setKemTheo(rs.getInt("KEM_THEO"));
				chungTu.setNgayLap(rs.getDate("NGAY_LAP"));
				chungTu.setNgayHt(rs.getDate("NGAY_HT"));
				chungTu.setNgayTt(rs.getDate("NGAY_TT"));

				DoiTuong doiTuong = new DoiTuong();
				doiTuong.setMaDt(rs.getInt("MA_DT"));
				doiTuong.setKhDt(rs.getString("KH_DT"));
				doiTuong.setTenDt(rs.getString("TEN_DT"));
				doiTuong.setLoaiDt(rs.getInt("LOAI_DT"));
				doiTuong.setDiaChi(rs.getString("DIA_CHI"));
				doiTuong.setMaThue(rs.getString("MA_THUE"));
				doiTuong.setNguoiNop(rs.getString("NGUOI_NOP"));
				chungTu.setDoiTuong(doiTuong);

				LoaiTien loaiTien = new LoaiTien();
				loaiTien.setMaLt(rs.getString("LOAI_TIEN"));
				loaiTien.setTenLt(rs.getString("TEN_NT"));
				loaiTien.setBanRa(rs.getDouble("TY_GIA"));
				chungTu.setLoaiTien(loaiTien);

				TaiKhoan taiKhoan = new TaiKhoan();
				taiKhoan.setMaNvkt(rs.getInt("MA_NVKT"));
				taiKhoan.setSoDu(rs.getInt("SO_DU"));
				taiKhoan.setLyDo(rs.getString("TK_LY_DO"));

				LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
				loaiTaiKhoan.setMaTk(rs.getString("MA_TK"));
				loaiTaiKhoan.setTenTk(rs.getString("TEN_TK"));
				loaiTaiKhoan.setMaTenTk(loaiTaiKhoan.getMaTk() + " - " + loaiTaiKhoan.getTenTk());
				taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);

				Tien tien = new Tien();
				tien.setLoaiTien(loaiTien);
				tien.setGiaTri(rs.getDouble("SO_TIEN"));
				tien.setSoTien(tien.getGiaTri() / tien.getLoaiTien().getBanRa());
				taiKhoan.setSoTien(tien);

				chungTu.themTaiKhoan(taiKhoan);
				taiKhoan.setChungTu(chungTu);

				logger.info(chungTu + " - " + taiKhoan);

				return chungTu;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public List<ChungTu> danhSachChungTuKtth(List<String> loaiCts, Date batDau, Date ketThuc) {
		if (loaiCts == null || loaiCts.size() == 0 || batDau == null || ketThuc == null) {
			return null;
		}

		String query = DANH_SACH_CHUNG_TU_THEO_LOAI_KTTH;

		if (loaiCts.contains(ChungTu.TAT_CA)) {
			query = query.replaceAll("\\$DIEU_KIEN_LOAI_CT\\$", "");
			logger.info("Danh sách tất cả chứng từ ...");
		} else {
			logger.info("Danh sách tất cả chứng từ loại " + loaiCts + " ...");

			String dieuKien = "";
			Iterator<String> iter = loaiCts.iterator();
			while (iter.hasNext()) {
				String loaiCt = iter.next();
				dieuKien += "'" + loaiCt.trim() + "',";
			}
			if (!dieuKien.equals("")) {
				dieuKien = dieuKien.substring(0, dieuKien.length() - 1);
			}
			dieuKien = " AND CT.LOAI_CT IN (" + dieuKien + ")";

			query = query.replaceAll("\\$DIEU_KIEN_LOAI_CT\\$", dieuKien);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		String batDauStr = sdf.format(batDau);
		String ketThucStr = sdf.format(ketThuc);

		logger.info("Danh sách chứng từ theo loại chứng từ: '" + loaiCts + "' từ " + batDauStr + " đến " + ketThucStr);
		logger.info(query);

		Object[] objs = { batDauStr, ketThucStr };
		List<ChungTu> chungTuDs = jdbcTmpl.query(query, objs, new ChungTuKtthMapper());

		// Gộp chứng từ trùng nhau nhưng có tài khoản ảnh hường khác nhau
		List<ChungTu> ketQua = null;
		if (chungTuDs != null) {
			ketQua = new ArrayList<>();
			Iterator<ChungTu> iter = chungTuDs.iterator();
			while (iter.hasNext()) {
				ChungTu chungTu = iter.next();

				int pos = ketQua.indexOf(chungTu);
				if (pos > -1) {
					ChungTu chungTuTmpl = ketQua.get(pos);
					chungTuTmpl.themTaiKhoanKtth(chungTu.getTaiKhoanKtthDs());
				} else {
					ketQua.add(chungTu);
				}
			}
		}

		return ketQua;
	}

	public class ChungTuKtthMapper implements RowMapper<ChungTu> {
		public ChungTu mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				ChungTu chungTu = new ChungTu();
				chungTu.setMaCt(rs.getInt("MA_CT"));
				chungTu.setSoCt(rs.getInt("SO_CT"));
				chungTu.setLoaiCt(rs.getString("LOAI_CT"));
				chungTu.setTinhChatCt(rs.getInt("TINH_CHAT"));
				chungTu.setLyDo(rs.getString("LY_DO"));
				chungTu.setKemTheo(rs.getInt("KEM_THEO"));
				chungTu.setNgayLap(rs.getDate("NGAY_LAP"));
				chungTu.setNgayHt(rs.getDate("NGAY_HT"));
				chungTu.setNgayTt(rs.getDate("NGAY_TT"));

				LoaiTien loaiTien = new LoaiTien();
				loaiTien.setMaLt(rs.getString("LOAI_TIEN"));
				loaiTien.setTenLt(rs.getString("TEN_NT"));
				loaiTien.setBanRa(rs.getDouble("TY_GIA"));
				chungTu.setLoaiTien(loaiTien);

				TaiKhoan taiKhoan = new TaiKhoan();
				taiKhoan.setMaNvkt(rs.getInt("MA_NVKT"));
				taiKhoan.setSoDu(rs.getInt("SO_DU"));
				taiKhoan.setLyDo(rs.getString("TK_LY_DO"));
				taiKhoan.setNhomDk(rs.getInt("NHOM_DK"));

				LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
				loaiTaiKhoan.setMaTk(rs.getString("MA_TK"));
				loaiTaiKhoan.setTenTk(rs.getString("TEN_TK"));
				loaiTaiKhoan.setMaTenTk(loaiTaiKhoan.getMaTk() + " - " + loaiTaiKhoan.getTenTk());
				taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);

				DoiTuong doiTuong = new DoiTuong();
				doiTuong.setMaDt(rs.getInt("MA_DT"));
				doiTuong.setKhDt(rs.getString("KH_DT"));
				doiTuong.setTenDt(rs.getString("TEN_DT"));
				logger.info(
						"doiTuong: " + doiTuong.getMaDt() + " - " + doiTuong.getTenDt() + " - " + doiTuong.getKhDt());
				doiTuong.setLoaiDt(rs.getInt("LOAI_DT"));
				doiTuong.setDiaChi(rs.getString("DIA_CHI"));
				doiTuong.setMaThue(rs.getString("MA_THUE"));
				taiKhoan.setDoiTuong(doiTuong);

				Tien tien = new Tien();
				tien.setLoaiTien(loaiTien);
				tien.setGiaTri(rs.getDouble("SO_TIEN"));
				tien.setSoTien(tien.getGiaTri() / tien.getLoaiTien().getBanRa());

				if (taiKhoan.getSoDu() == LoaiTaiKhoan.NO) {
					taiKhoan.setNo(tien);
				} else {
					taiKhoan.setCo(tien);
				}

				chungTu.themTaiKhoanKtth(taiKhoan);
				taiKhoan.setChungTu(chungTu);

				return chungTu;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public List<ChungTu> danhSachChungTuKho(List<String> loaiCts, Date batDau, Date ketThuc) {
		if (loaiCts == null || loaiCts.size() == 0 || batDau == null || ketThuc == null) {
			return null;
		}

		String query = DANH_SACH_CHUNG_TU_KHO_THEO_LOAI;

		if (loaiCts.contains(ChungTu.TAT_CA)) {
			query = query.replaceAll("\\$DIEU_KIEN_LOAI_CT\\$", "");
			logger.info("Danh sách tất cả chứng từ ...");
		} else {
			logger.info("Danh sách tất cả chứng từ loại " + loaiCts + " ...");

			String dieuKien = "";
			Iterator<String> iter = loaiCts.iterator();
			while (iter.hasNext()) {
				String loaiCt = iter.next();
				dieuKien += "'" + loaiCt.trim() + "',";
			}
			if (!dieuKien.equals("")) {
				dieuKien = dieuKien.substring(0, dieuKien.length() - 1);
			}
			dieuKien = " AND CT.LOAI_CT IN (" + dieuKien + ")";

			query = query.replaceAll("\\$DIEU_KIEN_LOAI_CT\\$", dieuKien);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		String batDauStr = sdf.format(batDau);
		String ketThucStr = sdf.format(ketThuc);

		logger.info(
				"Danh sách chứng từ kho theo loại chứng từ: '" + loaiCts + "' từ " + batDauStr + " đến " + ketThucStr);
		logger.info(query);

		Object[] objs = { batDauStr, ketThucStr, batDauStr, ketThucStr, batDauStr, ketThucStr };
		List<ChungTu> chungTuDs = jdbcTmpl.query(query, objs, new ChungTuKhoMapper());

		// Gộp chứng từ trùng nhau nhưng có tài khoản ảnh hưởng khác nhau
		List<ChungTu> ketQua = null;
		if (chungTuDs != null) {
			ketQua = new ArrayList<>();
			Iterator<ChungTu> iter = chungTuDs.iterator();
			while (iter.hasNext()) {
				ChungTu chungTu = iter.next();

				// Với mỗi chứng từ
				if (chungTu.getHangHoaDs() != null) {
					// Rồi gộp danh sách hàng hóa chứng từ mới vào danh sách hàng hóa chứng từ cũ
					int pos = ketQua.indexOf(chungTu);
					if (pos > -1) {
						ChungTu chungTuTmpl = ketQua.get(pos);
						chungTuTmpl.themHangHoa(chungTu.getHangHoaDs());
					} else {
						ketQua.add(chungTu);
					}
				} else {
					// Nếu chứng từ mới không có danh sách hàng hóa cần bổ sung,
					// thì đưa nó vào danh sách kết quả nếu trước đó chưa có nó
					if (ketQua.indexOf(chungTu) == -1) {
						ketQua.add(chungTu);
					}
				}
			}
		}

		return ketQua;
	}

	public class ChungTuKhoMapper implements RowMapper<ChungTu> {
		public ChungTu mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				ChungTu chungTu = new ChungTu();
				chungTu.setMaCt(rs.getInt("MA_CT"));
				chungTu.setSoCt(rs.getInt("SO_CT"));
				chungTu.setLoaiCt(rs.getString("LOAI_CT"));
				chungTu.setTinhChatCt(rs.getInt("TINH_CHAT"));
				chungTu.setLyDo(rs.getString("LY_DO"));
				chungTu.setKemTheo(rs.getInt("KEM_THEO"));
				chungTu.setNgayLap(rs.getDate("NGAY_LAP"));
				chungTu.setNgayHt(rs.getDate("NGAY_HT"));
				chungTu.setNgayTt(rs.getDate("NGAY_TT"));

				DoiTuong doiTuong = new DoiTuong();
				doiTuong.setMaDt(rs.getInt("MA_DT"));
				doiTuong.setKhDt(rs.getString("KH_DT"));
				doiTuong.setTenDt(rs.getString("TEN_DT"));
				doiTuong.setLoaiDt(rs.getInt("LOAI_DT"));
				doiTuong.setDiaChi(rs.getString("DIA_CHI"));
				doiTuong.setMaThue(rs.getString("MA_THUE"));
				doiTuong.setNguoiNop(rs.getString("NGUOI_NOP"));
				chungTu.setDoiTuong(doiTuong);

				LoaiTien loaiTien = new LoaiTien();
				loaiTien.setMaLt(rs.getString("LOAI_TIEN"));
				loaiTien.setTenLt(rs.getString("TEN_NT"));
				loaiTien.setBanRa(rs.getDouble("TY_GIA"));
				chungTu.setLoaiTien(loaiTien);

				HangHoa hangHoa = new HangHoa();
				hangHoa.setMaHh(rs.getInt("MA_HH"));
				hangHoa.setKyHieuHh(rs.getString("KH_HH"));
				hangHoa.setTenHh(rs.getString("TEN_HH"));
				hangHoa.setSoLuong(rs.getDouble("SO_LUONG_TN"));
				hangHoa.setSoLuongBanDau(rs.getDouble("SO_LUONG_TN"));

				DonVi donVi = new DonVi();
				donVi.setMaDv(rs.getInt("MA_DV"));
				donVi.setTenDv(rs.getString("TEN_DV"));
				hangHoa.setDonVi(donVi);

				NhomHang nhomHang = new NhomHang();
				nhomHang.setMaNhomHh(rs.getInt("MA_NHOM_HH"));
				nhomHang.setKyHieuNhomHh(rs.getString("KH_NHOM_HH"));
				nhomHang.setTenNhomHh(rs.getString("TEN_NHOM_HH"));
				hangHoa.setNhomHh(nhomHang);

				Tien donGia = new Tien();
				donGia.setLoaiTien(loaiTien);
				donGia.setGiaTri(rs.getDouble("DON_GIA"));
				donGia.setSoTien(donGia.getGiaTri() / loaiTien.getBanRa());
				hangHoa.setDonGia(donGia);

				Tien giaKho = new Tien();
				giaKho.setLoaiTien(loaiTien);
				giaKho.setGiaTri(rs.getDouble("GIA_KHO"));
				giaKho.setSoTien(giaKho.getGiaTri() / loaiTien.getBanRa());
				giaKho.setMaGia(rs.getInt("MA_GIA_KHO"));
				hangHoa.setGiaKho(giaKho);

				KhoHang khoHang = new KhoHang();
				khoHang.setMaKho(rs.getInt("MA_KHO"));
				khoHang.setKyHieuKho(rs.getString("KH_KHO"));
				khoHang.setTenKho(rs.getString("TEN_KHO"));
				khoHang.setDiaChi(rs.getString("DIA_CHI"));
				khoHang.setMoTa(rs.getString("MO_TA"));
				hangHoa.setKho(khoHang);

				TaiKhoan taiKhoan = new TaiKhoan();
				taiKhoan.setSoDu(rs.getInt("SO_DU"));
				taiKhoan.setLyDo(rs.getString("TK_LY_DO"));
				taiKhoan.setMaNvkt(rs.getInt("MA_NVKT"));
				taiKhoan.setNhomDk(rs.getInt("NHOM_DK"));

				LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
				loaiTaiKhoan.setMaTk(rs.getString("MA_TK"));
				loaiTaiKhoan.setTenTk(rs.getString("TEN_TK"));
				loaiTaiKhoan.setMaTenTk(loaiTaiKhoan.getMaTk() + " - " + loaiTaiKhoan.getTenTk());
				taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);

				Tien tkTien = new Tien();
				tkTien.setLoaiTien(loaiTien);
				tkTien.setGiaTri(rs.getDouble("SO_TIEN"));
				tkTien.setSoTien(tkTien.getGiaTri() / tkTien.getLoaiTien().getBanRa());
				taiKhoan.setSoTien(tkTien);

				int loaiTk = rs.getInt("LOAI_TK");
				if (loaiTk == HangHoa.TK_THANH_TOAN) {
					hangHoa.setTkThanhtoan(taiKhoan);
				} else if (loaiTk == HangHoa.TK_KHO) {
					hangHoa.setTkKho(taiKhoan);
				} else if (loaiTk == HangHoa.TK_DOANH_THU) {
					hangHoa.setTkDoanhThu(taiKhoan);
				} else if (loaiTk == HangHoa.TK_CHI_PHI) {
					hangHoa.setTkChiPhi(taiKhoan);
				} else if (loaiTk == HangHoa.TK_GIA_VON) {
					hangHoa.setTkGiaVon(taiKhoan);
				} else if (loaiTk == HangHoa.TK_THUE_NK) {
					hangHoa.setTkThueNk(taiKhoan);
					hangHoa.setThueSuatNk(rs.getDouble("THUE_SUAT"));
				} else if (loaiTk == HangHoa.TK_THUE_XK) {
					hangHoa.setTkThueXk(taiKhoan);
					hangHoa.setThueSuatXk(rs.getDouble("THUE_SUAT"));
				} else if (loaiTk == HangHoa.TK_THUE_TTDB) {
					hangHoa.setTkThueTtdb(taiKhoan);
					hangHoa.setThueSuatTtdb(rs.getDouble("THUE_SUAT"));
				} else if (loaiTk == HangHoa.TK_THUE_GTGT) {
					hangHoa.setTkThueGtgt(taiKhoan);
					hangHoa.setThueSuatGtgt(rs.getDouble("THUE_SUAT"));
				} else if (loaiTk == HangHoa.TK_THUE_GTGT_DU) {
					hangHoa.setTkThueGtgtDu(taiKhoan);
				}

				taiKhoan.setChungTu(chungTu);
				chungTu.themHangHoa(hangHoa);

				logger.info(chungTu);

				return chungTu;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	@Override
	public ChungTu layChungTu(int maCt, String loaiCt) {
		String layChungTu = LAY_CHUNG_TU_THEO_MACT_LOAICT;

		logger.info("Lấy chứng từ theo MA_CT: '" + maCt + "', LOAI_CT: '" + loaiCt + "' ...");
		logger.info(layChungTu);

		Object[] objs = { maCt, loaiCt, maCt, loaiCt, maCt, loaiCt };
		List<ChungTu> chungTuDs = jdbcTmpl.query(layChungTu, objs, new ChungTuMapper());

		// Gộp chứng từ trùng nhau nhưng có tài khoản ảnh hường khác nhau
		List<ChungTu> ketQua = null;
		if (chungTuDs != null) {
			ketQua = new ArrayList<>();
			Iterator<ChungTu> iter = chungTuDs.iterator();
			while (iter.hasNext()) {
				ChungTu chungTu = iter.next();

				int pos = ketQua.indexOf(chungTu);
				if (pos > -1) {
					ChungTu chungTuTmpl = ketQua.get(pos);
					chungTuTmpl.themTaiKhoan(chungTu.getTaiKhoanDs());
				} else {
					ketQua.add(chungTu);
				}
			}
		}

		if (ketQua != null && ketQua.size() > 0) {
			return ketQua.get(0);
		}

		return null;
	}

	@Override
	public ChungTu layChungTuKtth(int maCt, String loaiCt) {
		String layChungTu = LAY_CHUNG_TU_THEO_MACT_LOAICT_KTTH;

		logger.info("Lấy chứng từ theo MA_CT: '" + maCt + "', LOAI_CT: '" + loaiCt + "' ...");
		logger.info(layChungTu);

		Object[] objs = { maCt, loaiCt };
		List<ChungTu> chungTuDs = jdbcTmpl.query(layChungTu, objs, new ChungTuKtthMapper());

		// Gộp chứng từ trùng nhau nhưng có tài khoản ảnh hường khác nhau
		List<ChungTu> ketQua = null;
		if (chungTuDs != null) {
			ketQua = new ArrayList<>();
			Iterator<ChungTu> iter = chungTuDs.iterator();
			while (iter.hasNext()) {
				ChungTu chungTu = iter.next();

				int pos = ketQua.indexOf(chungTu);
				if (pos > -1) {
					ChungTu chungTuTmpl = ketQua.get(pos);
					chungTuTmpl.themTaiKhoanKtth(chungTu.getTaiKhoanKtthDs());
				} else {
					ketQua.add(chungTu);
				}
			}
		}

		if (ketQua != null && ketQua.size() > 0) {
			return ketQua.get(0);
		}

		return null;
	}

	@Override
	public ChungTu layChungTuKho(int maCt, String loaiCt) {
		String layChungTu = LAY_CHUNG_TU_KHO_THEO_MACT_LOAICT;

		logger.info("Lấy chứng từ theo MA_CT: '" + maCt + "', LOAI_CT: '" + loaiCt + "' ...");
		logger.info(layChungTu);

		Object[] objs = { maCt, loaiCt, maCt, loaiCt, maCt, loaiCt };
		List<ChungTu> chungTuDs = jdbcTmpl.query(layChungTu, objs, new ChungTuKhoMapper());

		// Gộp chứng từ trùng nhau nhưng có tài khoản ảnh hường khác nhau
		List<ChungTu> ketQua = null;
		if (chungTuDs != null) {
			ketQua = new ArrayList<>();
			Iterator<ChungTu> iter = chungTuDs.iterator();
			while (iter.hasNext()) {
				ChungTu chungTu = iter.next();

				// Với mỗi chứng từ
				if (chungTu.getHangHoaDs() != null) {
					// Rồi gộp danh sách hàng hóa chứng từ mới vào danh sách hàng hóa chứng từ cũ
					int pos = ketQua.indexOf(chungTu);
					if (pos > -1) {
						ChungTu chungTuTmpl = ketQua.get(pos);

						if (chungTuTmpl.getHangHoaDs() != null) {
							Iterator<HangHoa> hhIter = chungTu.getHangHoaDs().iterator();
							while (hhIter.hasNext()) {
								HangHoa hangHoa = hhIter.next();
								chungTuTmpl.themHangHoa(hangHoa);
							}
						} else {
							chungTuTmpl.themHangHoa(chungTu.getHangHoaDs());
						}
					} else {
						ketQua.add(chungTu);
					}
				} else {
					// Nếu chứng từ mới không có danh sách hàng hóa cần bổ sung,
					// thì đưa nó vào danh sách kết quả nếu trước đó chưa có nó
					if (ketQua.indexOf(chungTu) == -1) {
						ketQua.add(chungTu);
					}
				}
			}
		}

		if (ketQua != null && ketQua.size() > 0) {
			return ketQua.get(0);
		}

		return null;
	}

	@Override
	public List<LoaiTien> danhSachLoaiTien() {
		String query = DANH_SACH_LOAI_TIEN;

		logger.info("Danh sách các loại tiền ...");
		logger.info(query);

		List<LoaiTien> loaiTienDs = jdbcTmpl.query(query, new LoaiTienMapper());
		return loaiTienDs;
	}

	public class LoaiTienMapper implements RowMapper<LoaiTien> {
		public LoaiTien mapRow(ResultSet rs, int rowNum) throws SQLException {
			LoaiTien loaiTien = new LoaiTien();
			loaiTien.setMaLt(rs.getString("MA_NT"));
			loaiTien.setTenLt(rs.getString("TEN_NT"));
			loaiTien.setMuaTM(rs.getDouble("MUA_TM"));
			loaiTien.setMuaCk(rs.getDouble("MUA_CK"));
			loaiTien.setBanRa(rs.getDouble("BAN_RA"));
			return loaiTien;
		}
	}

	@Override
	public void themChungTu(ChungTu chungTu) {
		String themChungTu = THEM_CHUNG_TU;
		String themTaiKhoan = THEM_TAI_KHOAN;
		String themChungTuTaiKhoan = THEM_CHUNG_TU_TAI_KHOAN;

		try {
			// Thêm chứng từ
			GeneratedKeyHolder holder = new GeneratedKeyHolder();
			jdbcTmpl.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement stt = con.prepareStatement(themChungTu, Statement.RETURN_GENERATED_KEYS);
					stt.setInt(1, chungTu.getSoCt());
					stt.setString(2, chungTu.getLoaiCt());
					stt.setInt(3, chungTu.getTinhChatCt());
					java.sql.Date ngayLap = new java.sql.Date(chungTu.getNgayLap().getTime());
					stt.setDate(4, ngayLap);
					java.sql.Date ngayHt = new java.sql.Date(chungTu.getNgayHt().getTime());
					stt.setDate(5, ngayHt);
					java.sql.Date ngayTt = null;
					if (chungTu.getNgayTt() != null) {
						ngayTt = new java.sql.Date(chungTu.getNgayTt().getTime());
					}
					stt.setDate(6, ngayTt);
					stt.setString(7, chungTu.getLyDo());
					stt.setString(8, chungTu.getLoaiTien().getMaLt());
					stt.setDouble(9, chungTu.getLoaiTien().getBanRa());
					stt.setInt(10, chungTu.getKemTheo());
					stt.setInt(11, chungTu.getDoiTuong().getMaDt());
					stt.setInt(12, chungTu.getDoiTuong().getLoaiDt());
					stt.setString(13, chungTu.getDoiTuong().getNguoiNop());

					return stt;
				}
			}, holder);

			// Thêm chứng từ tài khoản
			chungTu.setMaCt(holder.getKey().intValue());
			Iterator<TaiKhoan> iter = chungTu.getTaiKhoanDs().iterator();
			while (iter.hasNext()) {
				TaiKhoan taiKhoan = iter.next();

				try {
					if (!taiKhoan.getLoaiTaiKhoan().getMaTk().equals("0")) {
						double giaTri = Utils.multiply(chungTu.getLoaiTien().getBanRa(),
								taiKhoan.getSoTien().getSoTien());

						logger.info("Tài khoản: " + taiKhoan.getLoaiTaiKhoan().getMaTk());
						logger.info("So tien: " + taiKhoan.getSoTien().getSoTien());
						logger.info("Gia tri: " + giaTri);
						logger.info("Ty gia: " + chungTu.getLoaiTien().getBanRa());
						GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
						jdbcTmpl.update(new PreparedStatementCreator() {
							@Override
							public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
								PreparedStatement stt = con.prepareStatement(themTaiKhoan,
										Statement.RETURN_GENERATED_KEYS);
								stt.setString(1, taiKhoan.getLoaiTaiKhoan().getMaTk());
								stt.setDouble(2, giaTri);
								stt.setInt(3, taiKhoan.getSoDu());
								stt.setString(4, taiKhoan.getLyDo());

								return stt;
							}
						}, nvktHolder);

						logger.info("Thêm vào bảng chung_tu_nvkt " + taiKhoan);
						jdbcTmpl.update(themChungTuTaiKhoan, chungTu.getMaCt(), nvktHolder.getKey().intValue());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void capNhatChungTu(ChungTu chungTu) {
		if (chungTu == null) {
			return;
		}

		String capNhatChungTu = CAP_NHAT_CHUNG_TU;
		String capNhatChungTuNvkt = CAP_NHAT_CHUNG_TU_NVKT;
		String layChungTuTaiKhoan = LAY_CHUNG_TU_TAI_KHOAN;
		String themTaiKhoan = THEM_TAI_KHOAN;
		String themChungTuTaiKhoan = THEM_CHUNG_TU_TAI_KHOAN;

		String xoaChungTuTk = XOA_CHUNG_TU_NVKT;
		String xoaChungTuNvkt = XOA_NVKT;

		try {
			// Cập nhật chứng từ
			jdbcTmpl.update(capNhatChungTu, chungTu.getSoCt(), chungTu.getLoaiCt(), chungTu.getTinhChatCt(),
					chungTu.getNgayLap(), chungTu.getNgayHt(), chungTu.getNgayTt(), chungTu.getLyDo(),
					chungTu.getLoaiTien().getMaLt(), chungTu.getLoaiTien().getBanRa(), chungTu.getKemTheo(),
					chungTu.getDoiTuong().getMaDt(), chungTu.getDoiTuong().getLoaiDt(),
					chungTu.getDoiTuong().getNguoiNop(), chungTu.getMaCt());

			// Lấy danh sách nvkt hiện có của chứng từ
			Object[] objs = { chungTu.getMaCt() };
			List<Integer> maNvktDs = jdbcTmpl.query(layChungTuTaiKhoan, objs, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getInt("MA_NVKT");
				}
			});

			if (chungTu.getTaiKhoanDs() != null && chungTu.getTaiKhoanDs().size() > 0) {
				Iterator<TaiKhoan> iter = chungTu.getTaiKhoanDs().iterator();
				while (iter.hasNext()) {
					TaiKhoan taiKhoan = iter.next();
					Integer maNvkt = taiKhoan.getMaNvkt();

					// Những nghiệp vụ kế toán có định khoản mới được cập nhật/thêm mới
					if (taiKhoan.getLoaiTaiKhoan() != null && !taiKhoan.getLoaiTaiKhoan().getMaTk().equals("0")) {
						double giaTri = Utils.multiply(chungTu.getLoaiTien().getBanRa(),
								taiKhoan.getSoTien().getSoTien());

						logger.info("Tài khoản: " + taiKhoan.getLoaiTaiKhoan().getMaTk());
						logger.info("So tien: " + taiKhoan.getSoTien().getSoTien());
						logger.info("Gia tri: " + giaTri);
						logger.info("Ty gia: " + chungTu.getLoaiTien().getBanRa());
						if (taiKhoan.getMaNvkt() == 0) {
							// Thêm mới nvkt người dùng vừa thêm
							try {
								// Thêm vào bảng NGHIEP_VU_KE_TOAN trước
								GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
								jdbcTmpl.update(new PreparedStatementCreator() {
									@Override
									public PreparedStatement createPreparedStatement(Connection con)
											throws SQLException {
										PreparedStatement stt = con.prepareStatement(themTaiKhoan,
												Statement.RETURN_GENERATED_KEYS);
										stt.setString(1, taiKhoan.getLoaiTaiKhoan().getMaTk());
										stt.setDouble(2, giaTri);
										stt.setInt(3, taiKhoan.getSoDu());
										stt.setString(4, taiKhoan.getLyDo());

										return stt;
									}
								}, nvktHolder);

								// Sau đó thêm vào bảng CHUNG_TU_NVKT
								logger.info("Thêm vào bảng chung_tu_nvkt " + taiKhoan);
								jdbcTmpl.update(themChungTuTaiKhoan, chungTu.getMaCt(), nvktHolder.getKey().intValue());
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							// Cập nhật những nvkt mà người dùng vừa thay đổi
							try {
								// Chỉ cân cập nhật trong bảng NGHIEP_VU_KE_TOAN là đủ
								// Trong bảng CHUNG_TU_NVKT không có thay đổi gì
								jdbcTmpl.update(capNhatChungTuNvkt, taiKhoan.getLoaiTaiKhoan().getMaTk(), giaTri,
										taiKhoan.getSoDu(), taiKhoan.getLyDo(), taiKhoan.getMaNvkt());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						// Những nghiệp vụ này sẽ không bị xóa ở bước cuối
						if (maNvktDs != null) {
							maNvktDs.remove(maNvkt);
						}
					}
				}

				// Xóa đi những nvkt đã bị người dùng xóa
				if (maNvktDs != null) {
					Iterator<Integer> maNvktIter = maNvktDs.iterator();
					while (maNvktIter.hasNext()) {
						Integer maNvkt = (Integer) maNvktIter.next();
						try {
							// Xóa trong bảng CHUNG_TU_NVKT trước
							jdbcTmpl.update(xoaChungTuTk, chungTu.getMaCt(), maNvkt);

							// Xóa trong bảng NGHIEP_VU_KE_TOAN
							jdbcTmpl.update(xoaChungTuNvkt, maNvkt);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void themChungTuKtth(ChungTu chungTu) {
		String themChungTu = THEM_CHUNG_TU;
		String themTaiKhoan = THEM_TAI_KHOAN_KTTH;
		String themChungTuTaiKhoan = THEM_CHUNG_TU_TAI_KHOAN_KTTH;

		try {
			// Thêm chứng từ
			GeneratedKeyHolder holder = new GeneratedKeyHolder();
			jdbcTmpl.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement stt = con.prepareStatement(themChungTu, Statement.RETURN_GENERATED_KEYS);
					stt.setInt(1, chungTu.getSoCt());
					stt.setString(2, chungTu.getLoaiCt());
					stt.setInt(3, chungTu.getTinhChatCt());
					java.sql.Date ngayLap = new java.sql.Date(chungTu.getNgayLap().getTime());
					stt.setDate(4, ngayLap);
					java.sql.Date ngayHt = new java.sql.Date(chungTu.getNgayHt().getTime());
					stt.setDate(5, ngayHt);
					java.sql.Date ngayTt = null;
					if (chungTu.getNgayTt() != null) {
						ngayTt = new java.sql.Date(chungTu.getNgayTt().getTime());
					}
					stt.setDate(6, ngayTt);
					stt.setString(7, chungTu.getLyDo());
					stt.setString(8, chungTu.getLoaiTien().getMaLt());
					stt.setDouble(9, chungTu.getLoaiTien().getBanRa());
					stt.setInt(10, chungTu.getKemTheo());
					stt.setInt(11, chungTu.getDoiTuong().getMaDt());
					stt.setInt(12, chungTu.getDoiTuong().getLoaiDt());
					stt.setString(13, chungTu.getDoiTuong().getNguoiNop());

					return stt;
				}
			}, holder);

			// Thêm chứng từ tài khoản
			chungTu.setMaCt(holder.getKey().intValue());
			Iterator<TaiKhoan> iter = chungTu.getTaiKhoanKtthDs().iterator();
			while (iter.hasNext()) {
				TaiKhoan taiKhoan = iter.next();

				try {
					if (taiKhoan.getLoaiTaiKhoan() != null && taiKhoan.getLoaiTaiKhoan().getMaTk() != null
							&& !taiKhoan.getLoaiTaiKhoan().getMaTk().isEmpty()) {
						double giaTriNo = Utils.multiply(chungTu.getLoaiTien().getBanRa(),
								taiKhoan.getNo().getSoTien());
						double giaTriCo = Utils.multiply(chungTu.getLoaiTien().getBanRa(),
								taiKhoan.getCo().getSoTien());

						logger.info("Tài khoản: " + taiKhoan.getLoaiTaiKhoan().getMaTk());
						logger.info("So du: " + taiKhoan.getSoDu());
						logger.info("So tien no: " + taiKhoan.getNo().getSoTien());
						logger.info("Gia tri no: " + giaTriNo);
						logger.info("So tien co: " + taiKhoan.getCo().getSoTien());
						logger.info("Gia tri co: " + giaTriCo);
						logger.info("Ty gia: " + chungTu.getLoaiTien().getBanRa());
						GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
						jdbcTmpl.update(new PreparedStatementCreator() {
							@Override
							public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
								PreparedStatement stt = con.prepareStatement(themTaiKhoan,
										Statement.RETURN_GENERATED_KEYS);
								stt.setString(1, taiKhoan.getLoaiTaiKhoan().getMaTk());
								if (taiKhoan.getSoDu() == LoaiTaiKhoan.NO) {
									stt.setDouble(2, giaTriNo);
								} else {
									stt.setDouble(2, giaTriCo);
								}
								stt.setInt(3, taiKhoan.getSoDu());
								stt.setString(4, taiKhoan.getLyDo());
								stt.setInt(5, taiKhoan.getDoiTuong().getMaDt());
								stt.setInt(6, taiKhoan.getDoiTuong().getLoaiDt());

								return stt;
							}
						}, nvktHolder);

						logger.info("Thêm vào bảng chung_tu_nvkt " + taiKhoan);
						jdbcTmpl.update(themChungTuTaiKhoan, chungTu.getMaCt(), nvktHolder.getKey().intValue(),
								taiKhoan.getNhomDk());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void capNhatChungTuKtth(ChungTu chungTu) {
		if (chungTu == null) {
			return;
		}

		String capNhatChungTu = CAP_NHAT_CHUNG_TU;
		String capNhatChungTuNvkt = CAP_NHAT_CHUNG_TU_NVKT;
		String layChungTuTaiKhoan = LAY_CHUNG_TU_TAI_KHOAN;
		String themTaiKhoan = THEM_TAI_KHOAN_KTTH;
		String themChungTuTaiKhoan = THEM_CHUNG_TU_TAI_KHOAN_KTTH;

		String xoaChungTuTk = XOA_CHUNG_TU_NVKT;
		String xoaChungTuNvkt = XOA_NVKT;

		try {
			// Cập nhật chứng từ
			jdbcTmpl.update(capNhatChungTu, chungTu.getSoCt(), chungTu.getLoaiCt(), chungTu.getTinhChatCt(),
					chungTu.getNgayLap(), chungTu.getNgayHt(), chungTu.getNgayTt(), chungTu.getLyDo(),
					chungTu.getLoaiTien().getMaLt(), chungTu.getLoaiTien().getBanRa(), chungTu.getKemTheo(),
					chungTu.getDoiTuong().getMaDt(), chungTu.getDoiTuong().getLoaiDt(),
					chungTu.getDoiTuong().getNguoiNop(), chungTu.getMaCt());

			// Lấy danh sách nvkt hiện có của chứng từ
			Object[] objs = { chungTu.getMaCt() };
			List<Integer> maNvktDs = jdbcTmpl.query(layChungTuTaiKhoan, objs, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getInt("MA_NVKT");
				}
			});

			if (chungTu.getTaiKhoanKtthDs() != null && chungTu.getTaiKhoanKtthDs().size() > 0) {
				Iterator<TaiKhoan> iter = chungTu.getTaiKhoanKtthDs().iterator();
				while (iter.hasNext()) {
					TaiKhoan taiKhoan = iter.next();
					Integer maNvkt = taiKhoan.getMaNvkt();

					// Những nghiệp vụ kế toán có định khoản mới được cập nhật/thêm mới
					if (taiKhoan.getLoaiTaiKhoan() != null && taiKhoan.getLoaiTaiKhoan().getMaTk() != null
							&& !taiKhoan.getLoaiTaiKhoan().getMaTk().isEmpty()) {
						double giaTriNo = Utils.multiply(chungTu.getLoaiTien().getBanRa(),
								taiKhoan.getNo().getSoTien());
						double giaTriCo = Utils.multiply(chungTu.getLoaiTien().getBanRa(),
								taiKhoan.getCo().getSoTien());

						logger.info("Tài khoản: " + taiKhoan.getLoaiTaiKhoan().getMaTk());
						logger.info("So du: " + taiKhoan.getSoDu());
						logger.info("So tien no: " + taiKhoan.getNo().getSoTien());
						logger.info("Gia tri no: " + giaTriNo);
						logger.info("So tien co: " + taiKhoan.getCo().getSoTien());
						logger.info("Gia tri co: " + giaTriCo);
						logger.info("Ty gia: " + chungTu.getLoaiTien().getBanRa());
						if (taiKhoan.getMaNvkt() == 0) {
							// Thêm mới nvkt người dùng vừa thêm
							try {
								// Thêm vào bảng NGHIEP_VU_KE_TOAN trước
								GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
								jdbcTmpl.update(new PreparedStatementCreator() {
									@Override
									public PreparedStatement createPreparedStatement(Connection con)
											throws SQLException {
										PreparedStatement stt = con.prepareStatement(themTaiKhoan,
												Statement.RETURN_GENERATED_KEYS);
										stt.setString(1, taiKhoan.getLoaiTaiKhoan().getMaTk());
										if (taiKhoan.getSoDu() == LoaiTaiKhoan.NO) {
											stt.setDouble(2, giaTriNo);
										} else {
											stt.setDouble(2, giaTriCo);
										}
										stt.setInt(3, taiKhoan.getSoDu());
										stt.setString(4, taiKhoan.getLyDo());
										stt.setInt(5, taiKhoan.getDoiTuong().getMaDt());
										stt.setInt(6, taiKhoan.getDoiTuong().getLoaiDt());

										return stt;
									}
								}, nvktHolder);

								// Sau đó thêm vào bảng CHUNG_TU_NVKT
								logger.info("Thêm vào bảng chung_tu_nvkt " + taiKhoan);
								jdbcTmpl.update(themChungTuTaiKhoan, chungTu.getMaCt(), nvktHolder.getKey().intValue(),
										taiKhoan.getNhomDk());
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							// Cập nhật những nvkt mà người dùng vừa thay đổi
							try {
								// Chỉ cân cập nhật trong bảng NGHIEP_VU_KE_TOAN là đủ
								// Trong bảng CHUNG_TU_NVKT không có thay đổi gì
								double soTien = 0;
								if (taiKhoan.getSoDu() == LoaiTaiKhoan.NO) {
									soTien = giaTriNo;
								} else {
									soTien = giaTriCo;
								}
								jdbcTmpl.update(capNhatChungTuNvkt, taiKhoan.getLoaiTaiKhoan().getMaTk(), soTien,
										taiKhoan.getSoDu(), taiKhoan.getLyDo(), taiKhoan.getMaNvkt());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						// Những nghiệp vụ này sẽ không bị xóa ở bước cuối
						if (maNvktDs != null) {
							maNvktDs.remove(maNvkt);
						}
					}
				}

				// Xóa đi những nvkt đã bị người dùng xóa
				if (maNvktDs != null) {
					Iterator<Integer> maNvktIter = maNvktDs.iterator();
					while (maNvktIter.hasNext()) {
						Integer maNvkt = (Integer) maNvktIter.next();
						try {
							// Xóa trong bảng CHUNG_TU_NVKT trước
							jdbcTmpl.update(xoaChungTuTk, chungTu.getMaCt(), maNvkt);

							// Xóa trong bảng NGHIEP_VU_KE_TOAN
							jdbcTmpl.update(xoaChungTuNvkt, maNvkt);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int themChungTuKho(ChungTu chungTu) {
		if (chungTu == null || chungTu.getHangHoaDs() == null || chungTu.getHangHoaDs().size() == 0) {
			return 0;
		}

		String themChungTu = THEM_CHUNG_TU_KHO;
		String themTaiKhoan = THEM_TAI_KHOAN;
		String themChungTuTaiKhoanKho = THEM_CHUNG_TU_TAI_KHOAN_KHO;
		String themChungTuHangHoa = THEM_CHUNG_TU_HANG_HOA;
		String themDonGia = THEM_DON_GIA;
		String kiemTraDonGia = KIEM_TRA_DON_GIA;

		try {
			// Thêm chứng từ
			GeneratedKeyHolder holder = new GeneratedKeyHolder();
			jdbcTmpl.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement stt = con.prepareStatement(themChungTu, Statement.RETURN_GENERATED_KEYS);
					stt.setInt(1, chungTu.getSoCt());
					stt.setString(2, chungTu.getLoaiCt());
					stt.setInt(3, chungTu.getTinhChatCt());
					java.sql.Date ngayLap = new java.sql.Date(chungTu.getNgayLap().getTime());
					stt.setDate(4, ngayLap);
					java.sql.Date ngayHt = new java.sql.Date(chungTu.getNgayHt().getTime());
					stt.setDate(5, ngayHt);
					java.sql.Date ngayTt = null;
					if (chungTu.getNgayTt() != null) {
						ngayTt = new java.sql.Date(chungTu.getNgayTt().getTime());
					}
					stt.setDate(6, ngayTt);
					stt.setString(7, chungTu.getLyDo());
					stt.setString(8, chungTu.getLoaiTien().getMaLt());
					stt.setDouble(9, chungTu.getLoaiTien().getBanRa());
					stt.setInt(10, chungTu.getKemTheo());
					stt.setInt(11, chungTu.getDoiTuong().getMaDt());
					stt.setInt(12, chungTu.getDoiTuong().getLoaiDt());
					stt.setString(13, chungTu.getDoiTuong().getNguoiNop());

					return stt;
				}
			}, holder);

			chungTu.setMaCt(holder.getKey().intValue());
			Date homNay = new Date();

			// Với từng loại hàng hóa
			Iterator<HangHoa> hhIter = chungTu.getHangHoaDs().iterator();
			while (hhIter.hasNext()) {
				HangHoa hangHoa = hhIter.next();
				logger.info("Hàng hóa: " + hangHoa);

				logger.info("Thêm vào chung_tu_hang_hoa");
				int maGia = hangHoa.getGiaKho().getMaGia();
				// Thêm vào hang_hoa_gia trước
				// Kiểm tra xem có chưa, chưa có thì mới thêm, có rồi thì lấy maGia ra
				if (hangHoa.getGiaKho() != null && hangHoa.getGiaKho().getMaGia() == 0) {
					double giaKho = Utils.multiply(hangHoa.getGiaKho().getSoTien(), chungTu.getLoaiTien().getBanRa());
					try {
						maGia = jdbcTmpl.queryForObject(kiemTraDonGia, new Object[] { hangHoa.getMaHh(), giaKho },
								Integer.class);
						if (maGia > 0) {
							logger.info("Đã có đơn giá " + giaKho + " của hàng hóa " + hangHoa.getMaHh() + ". maGia="
									+ maGia);
						} else {
							throw new Exception("Chưa có đơn giá " + giaKho + " của hàng hóa " + hangHoa.getMaHh());
						}
					} catch (Exception e) {
						logger.info("Chưa có đơn giá " + giaKho + " của hàng hóa " + hangHoa.getMaHh()
								+ ". Thêm mới vào ...");
						GeneratedKeyHolder dgHolder = new GeneratedKeyHolder();
						jdbcTmpl.update(new PreparedStatementCreator() {
							@Override
							public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
								PreparedStatement stt = con.prepareStatement(themDonGia,
										Statement.RETURN_GENERATED_KEYS);
								stt.setInt(1, hangHoa.getMaHh());
								stt.setDouble(2, giaKho);
								stt.setDate(3, new java.sql.Date(homNay.getTime()));

								return stt;
							}
						}, dgHolder);
						maGia = dgHolder.getKey().intValue();
					}
				}

				// Thêm vào chung_tu_hang_hoa
				logger.info("Mã đơn giá " + maGia);
				double donGia = Utils.multiply(hangHoa.getDonGia().getSoTien(), chungTu.getLoaiTien().getBanRa());
				if (hangHoa.getKho() != null) {
					jdbcTmpl.update(themChungTuHangHoa, chungTu.getMaCt(), hangHoa.getMaHh(), hangHoa.getSoLuong(),
							donGia, maGia, chungTu.getChieu(), hangHoa.getKho().getMaKho());
				} else {
					jdbcTmpl.update(themChungTuHangHoa, chungTu.getMaCt(), hangHoa.getMaHh(), hangHoa.getSoLuong(),
							donGia, maGia, chungTu.getChieu(), KhoHang.MA_KHO_MAC_DINH);
				}

				logger.info("Thêm vào bảng nghiep_vu_ke_toan: ");
				if (hangHoa.getTkKho() != null && hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk() != null
						&& !hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
					logger.info("Tài khoản kho: " + hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk());
					double kho = Utils.multiply(hangHoa.getTkKho().getSoTien().getSoTien(),
							chungTu.getLoaiTien().getBanRa());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, kho);
							stt.setInt(3, hangHoa.getTkKho().getSoDu());
							stt.setString(4, hangHoa.getTkKho().getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), hangHoa.getTkKho().getNhomDk(), HangHoa.TK_KHO, 0);
				}

				if (hangHoa.getTkGiaVon() != null && hangHoa.getTkGiaVon().getLoaiTaiKhoan().getMaTk() != null
						&& !hangHoa.getTkGiaVon().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
					logger.info("Tài khoản giá vốn: " + hangHoa.getTkGiaVon().getLoaiTaiKhoan().getMaTk());
					double giaVon = Utils.multiply(hangHoa.getTkGiaVon().getSoTien().getSoTien(),
							chungTu.getLoaiTien().getBanRa());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkGiaVon().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, giaVon);
							stt.setInt(3, hangHoa.getTkGiaVon().getSoDu());
							stt.setString(4, hangHoa.getTkGiaVon().getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), hangHoa.getTkGiaVon().getNhomDk(), HangHoa.TK_GIA_VON, 0);
				}

				if (hangHoa.getTkDoanhThu() != null && hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk() != null
						&& !hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
					logger.info("Tài khoản doanh thu:" + hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk());
					double doanhThu = Utils.multiply(hangHoa.getTkDoanhThu().getSoTien().getSoTien(),
							chungTu.getLoaiTien().getBanRa());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, doanhThu);
							stt.setInt(3, hangHoa.getTkDoanhThu().getSoDu());
							stt.setString(4, hangHoa.getTkDoanhThu().getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), hangHoa.getTkDoanhThu().getNhomDk(), HangHoa.TK_DOANH_THU,
							0);
				}

				if (hangHoa.getTkChiPhi() != null && hangHoa.getTkChiPhi().getLoaiTaiKhoan().getMaTk() != null
						&& !hangHoa.getTkChiPhi().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
					logger.info("Tài khoản chi phí:" + hangHoa.getTkChiPhi().getLoaiTaiKhoan().getMaTk());
					double chiPhi = Utils.multiply(hangHoa.getTkChiPhi().getSoTien().getSoTien(),
							chungTu.getLoaiTien().getBanRa());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkChiPhi().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, chiPhi);
							stt.setInt(3, hangHoa.getTkChiPhi().getSoDu());
							stt.setString(4, hangHoa.getTkChiPhi().getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), hangHoa.getTkChiPhi().getNhomDk(), HangHoa.TK_CHI_PHI, 0);
				}

				if (hangHoa.getTkThanhtoan() != null && hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk() != null
						&& !hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
					logger.info("Tài khoản thanh toán:" + hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk());
					double thanhToan = Utils.multiply(hangHoa.getTkThanhtoan().getSoTien().getSoTien(),
							chungTu.getLoaiTien().getBanRa());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, thanhToan);
							stt.setInt(3, hangHoa.getTkThanhtoan().getSoDu());
							stt.setString(4, hangHoa.getTkThanhtoan().getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), hangHoa.getTkThanhtoan().getNhomDk(), HangHoa.TK_THANH_TOAN,
							0);
				}

				// Các tài khoản về thuế nhập khẩu, xuất khẩu,
				// tiêu thụ đặc biệt, thuế giá trị gia tăng
				if (hangHoa.getTkThueNk() != null && hangHoa.getTkThueNk().getLoaiTaiKhoan().getMaTk() != null
						&& !hangHoa.getTkThueNk().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
					logger.info("Tài khoản thuế nhập khẩu:" + hangHoa.getTkThueNk().getLoaiTaiKhoan().getMaTk());
					double nhapKhau = Utils.multiply(hangHoa.getTkThueNk().getSoTien().getSoTien(),
							chungTu.getLoaiTien().getBanRa());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkThueNk().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, nhapKhau);
							stt.setInt(3, hangHoa.getTkThueNk().getSoDu());
							stt.setString(4, hangHoa.getTkThueNk().getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), hangHoa.getTkThueNk().getNhomDk(), HangHoa.TK_THUE_NK,
							hangHoa.getThueSuatNk());
				}

				if (hangHoa.getTkThueXk() != null && hangHoa.getTkThueXk().getLoaiTaiKhoan().getMaTk() != null
						&& !hangHoa.getTkThueXk().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
					logger.info("Tài khoản thuế xuất khẩu:" + hangHoa.getTkThueXk().getLoaiTaiKhoan().getMaTk());
					double xuatKhau = Utils.multiply(hangHoa.getTkThueXk().getSoTien().getSoTien(),
							chungTu.getLoaiTien().getBanRa());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkThueXk().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, xuatKhau);
							stt.setInt(3, hangHoa.getTkThueXk().getSoDu());
							stt.setString(4, hangHoa.getTkThueXk().getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), hangHoa.getTkThueXk().getNhomDk(), HangHoa.TK_THUE_XK,
							hangHoa.getThueSuatXk());
				}

				if (hangHoa.getTkThueTtdb() != null && hangHoa.getTkThueTtdb().getLoaiTaiKhoan().getMaTk() != null
						&& !hangHoa.getTkThueTtdb().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
					logger.info(
							"Tài khoản thuế tiêu thụ đặc biệt:" + hangHoa.getTkThueTtdb().getLoaiTaiKhoan().getMaTk());
					double ttdb = Utils.multiply(hangHoa.getTkThueTtdb().getSoTien().getSoTien(),
							chungTu.getLoaiTien().getBanRa());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkThueTtdb().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, ttdb);
							stt.setInt(3, hangHoa.getTkThueTtdb().getSoDu());
							stt.setString(4, hangHoa.getTkThueTtdb().getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), hangHoa.getTkThueTtdb().getNhomDk(), HangHoa.TK_THUE_TTDB,
							hangHoa.getThueSuatTtdb());
				}

				if (hangHoa.getTkThueGtgtDu() != null && hangHoa.getTkThueGtgtDu().getLoaiTaiKhoan().getMaTk() != null
						&& !hangHoa.getTkThueGtgtDu().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
					logger.info("Tài khoản đối ứng thuế gtgt:" + hangHoa.getTkThueGtgtDu().getLoaiTaiKhoan().getMaTk());
					double gtgtDu = Utils.multiply(hangHoa.getTkThueGtgtDu().getSoTien().getSoTien(),
							chungTu.getLoaiTien().getBanRa());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkThueGtgtDu().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, gtgtDu);
							stt.setInt(3, hangHoa.getTkThueGtgtDu().getSoDu());
							stt.setString(4, hangHoa.getTkThueGtgtDu().getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), hangHoa.getTkThueGtgtDu().getNhomDk(),
							HangHoa.TK_THUE_GTGT_DU, hangHoa.getThueSuatGtgt());
				}

				if (hangHoa.getTkThueGtgt() != null && hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk() != null
						&& !hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
					logger.info("Tài khoản thuế gtgt:" + hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk());
					double gtgt = Utils.multiply(hangHoa.getTkThueGtgt().getSoTien().getSoTien(),
							chungTu.getLoaiTien().getBanRa());
					int nvKtId = 1;
					if (!hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk().trim().equals("0")) {
						// Đây là trường hợp tính thuế Gtgt bằng phương pháp khấu trừ
						// Ngược lại là phương pháp trực tiếp thì ko cần thêm vào đây
						GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
						jdbcTmpl.update(new PreparedStatementCreator() {
							@Override
							public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
								PreparedStatement stt = con.prepareStatement(themTaiKhoan,
										Statement.RETURN_GENERATED_KEYS);
								stt.setString(1, hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk());
								stt.setDouble(2, gtgt);
								stt.setInt(3, hangHoa.getTkThueGtgt().getSoDu());
								stt.setString(4, hangHoa.getTkThueGtgt().getLyDo());

								return stt;
							}
						}, nvktHolder);
						nvKtId = nvktHolder.getKey().intValue();
					}
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(), nvKtId,
							hangHoa.getTkThueGtgt().getNhomDk(), HangHoa.TK_THUE_GTGT, hangHoa.getThueSuatGtgt());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return chungTu.getMaCt();
	}

	@Override
	public void capNhatChungTuKho(ChungTu chungTu) {
		String capNhatChungTu = CAP_NHAT_CHUNG_TU_KHO;

		String themTaiKhoan = THEM_TAI_KHOAN;
		String themChungTuTaiKhoanKho = THEM_CHUNG_TU_TAI_KHOAN_KHO;
		String themChungTuHangHoa = THEM_CHUNG_TU_HANG_HOA;
		String themDonGia = THEM_DON_GIA;
		String kiemTraDonGia = KIEM_TRA_DON_GIA;

		String xoaChungTuHangHoa = XOA_CHUNG_TU_HANG_HOA;
		String xoaChungTuNvktKho = XOA_CHUNG_TU_NVKT_KHO;
		String xoaChungTuNvkt = XOA_NVKT;

		try {
			logger.info("Cập nhật chứng từ " + chungTu);
			jdbcTmpl.update(capNhatChungTu, chungTu.getSoCt(), chungTu.getLoaiCt(), chungTu.getTinhChatCt(),
					chungTu.getNgayLap(), chungTu.getNgayHt(), chungTu.getNgayTt(), chungTu.getLyDo(),
					chungTu.getLoaiTien().getMaLt(), chungTu.getLoaiTien().getBanRa(), chungTu.getKemTheo(),
					chungTu.getDoiTuong().getMaDt(), chungTu.getDoiTuong().getLoaiDt(),
					chungTu.getDoiTuong().getNguoiNop(), chungTu.getMaCt());

			// Xóa dữ liệu cũ liên quan đến chứng từ:
			// Xóa chung_tu_hang_hoa
			jdbcTmpl.update(xoaChungTuHangHoa, chungTu.getMaCt());

			// Xóa chung_tu_nvkt
			jdbcTmpl.update(xoaChungTuNvktKho, chungTu.getMaCt());

			// Xóa nghiep_vu_ke_toan
			if (chungTu.getHangHoaDs() != null && chungTu.getHangHoaDs().size() > 0) {
				// Với từng loại hàng hóa
				Iterator<HangHoa> hhIter = chungTu.getHangHoaDs().iterator();
				while (hhIter.hasNext()) {
					HangHoa hangHoa = hhIter.next();

					if (hangHoa.getTkKho() != null && hangHoa.getTkKho().getLoaiTaiKhoan() != null
							&& hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk() != null) {
						jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkKho().getMaNvkt());
					}

					if (hangHoa.getTkGiaVon() != null && hangHoa.getTkGiaVon().getLoaiTaiKhoan() != null
							&& hangHoa.getTkGiaVon().getLoaiTaiKhoan().getMaTk() != null) {
						jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkGiaVon().getMaNvkt());
					}

					if (hangHoa.getTkDoanhThu() != null && hangHoa.getTkDoanhThu().getLoaiTaiKhoan() != null
							&& hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk() != null) {
						jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkDoanhThu().getMaNvkt());
					}

					if (hangHoa.getTkChiPhi() != null && hangHoa.getTkChiPhi().getLoaiTaiKhoan() != null
							&& hangHoa.getTkChiPhi().getLoaiTaiKhoan().getMaTk() != null) {
						jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkChiPhi().getMaNvkt());
					}

					if (hangHoa.getTkThanhtoan() != null && hangHoa.getTkThanhtoan().getLoaiTaiKhoan() != null
							&& hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk() != null) {
						jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkThanhtoan().getMaNvkt());
					}

					if (hangHoa.getTkThueNk() != null && hangHoa.getTkThueNk().getLoaiTaiKhoan() != null
							&& hangHoa.getTkThueNk().getLoaiTaiKhoan().getMaTk() != null) {
						jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkThueNk().getMaNvkt());
					}

					if (hangHoa.getTkThueXk() != null && hangHoa.getTkThueXk().getLoaiTaiKhoan() != null
							&& hangHoa.getTkThueXk().getLoaiTaiKhoan().getMaTk() != null) {
						jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkThueXk().getMaNvkt());
					}

					if (hangHoa.getTkThueTtdb() != null && hangHoa.getTkThueTtdb().getLoaiTaiKhoan() != null
							&& hangHoa.getTkThueTtdb().getLoaiTaiKhoan().getMaTk() != null) {
						jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkThueTtdb().getMaNvkt());
					}

					if (hangHoa.getTkThueGtgtDu() != null && hangHoa.getTkThueGtgtDu().getLoaiTaiKhoan() != null
							&& hangHoa.getTkThueGtgtDu().getLoaiTaiKhoan().getMaTk() != null) {
						jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkThueGtgtDu().getMaNvkt());
					}

					if (hangHoa.getTkThueGtgt() != null && hangHoa.getTkThueGtgt().getLoaiTaiKhoan() != null
							&& hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk() != null) {
						jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkThueGtgt().getMaNvkt());
					}
				}
			}

			Date homNay = new Date();

			// Với từng loại hàng hóa
			Iterator<HangHoa> hhIter = chungTu.getHangHoaDs().iterator();
			while (hhIter.hasNext()) {
				HangHoa hangHoa = hhIter.next();
				logger.info("Hàng hóa: " + hangHoa);

				logger.info("Thêm vào chung_tu_hang_hoa");
				int maGia = hangHoa.getGiaKho().getMaGia();
				// Thêm vào hang_hoa_gia trước
				// Kiểm tra xem có chưa, chưa có thì mới thêm, có rồi thì lấy maGia ra
				if (hangHoa.getGiaKho() != null && hangHoa.getGiaKho().getMaGia() == 0) {
					double giaKho = Utils.multiply(hangHoa.getGiaKho().getSoTien(), chungTu.getLoaiTien().getBanRa());
					try {
						maGia = jdbcTmpl.queryForObject(kiemTraDonGia, new Object[] { hangHoa.getMaHh(), giaKho },
								Integer.class);
						if (maGia > 0) {
							logger.info("Đã có đơn giá " + giaKho + " của hàng hóa " + hangHoa.getMaHh() + ". maGia="
									+ maGia);
						} else {
							throw new Exception("Chưa có đơn giá " + giaKho + " của hàng hóa " + hangHoa.getMaHh());
						}
					} catch (Exception e) {
						logger.info("Chưa có đơn giá " + giaKho + " của hàng hóa " + hangHoa.getMaHh()
								+ ". Thêm mới vào ...");
						GeneratedKeyHolder dgHolder = new GeneratedKeyHolder();
						jdbcTmpl.update(new PreparedStatementCreator() {
							@Override
							public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
								PreparedStatement stt = con.prepareStatement(themDonGia,
										Statement.RETURN_GENERATED_KEYS);
								stt.setInt(1, hangHoa.getMaHh());
								stt.setDouble(2, giaKho);
								stt.setDate(3, new java.sql.Date(homNay.getTime()));

								return stt;
							}
						}, dgHolder);
						maGia = dgHolder.getKey().intValue();
					}
				}

				// Thêm vào chung_tu_hang_hoa
				logger.info("Mã đơn giá " + maGia);
				double donGia = Utils.multiply(hangHoa.getDonGia().getSoTien(), chungTu.getLoaiTien().getBanRa());
				if (hangHoa.getKho() != null) {
					jdbcTmpl.update(themChungTuHangHoa, chungTu.getMaCt(), hangHoa.getMaHh(), hangHoa.getSoLuong(),
							donGia, maGia, chungTu.getChieu(), hangHoa.getKho().getMaKho());
				} else {
					jdbcTmpl.update(themChungTuHangHoa, chungTu.getMaCt(), hangHoa.getMaHh(), hangHoa.getSoLuong(),
							donGia, maGia, chungTu.getChieu(), KhoHang.MA_KHO_MAC_DINH);
				}

				logger.info("Thêm vào bảng nghiep_vu_ke_toan: ");
				if (hangHoa.getTkKho() != null && hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk() != null
						&& !hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
					logger.info("Tài khoản kho: " + hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk());
					double kho = Utils.multiply(hangHoa.getTkKho().getSoTien().getSoTien(),
							chungTu.getLoaiTien().getBanRa());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, kho);
							stt.setInt(3, hangHoa.getTkKho().getSoDu());
							stt.setString(4, hangHoa.getTkKho().getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), hangHoa.getTkKho().getNhomDk(), HangHoa.TK_KHO, 0);
				}

				if (hangHoa.getTkGiaVon() != null && hangHoa.getTkGiaVon().getLoaiTaiKhoan().getMaTk() != null
						&& !hangHoa.getTkGiaVon().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
					logger.info("Tài khoản giá vốn: " + hangHoa.getTkGiaVon().getLoaiTaiKhoan().getMaTk());
					double giaVon = Utils.multiply(hangHoa.getTkGiaVon().getSoTien().getSoTien(),
							chungTu.getLoaiTien().getBanRa());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkGiaVon().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, giaVon);
							stt.setInt(3, hangHoa.getTkGiaVon().getSoDu());
							stt.setString(4, hangHoa.getTkGiaVon().getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), hangHoa.getTkGiaVon().getNhomDk(), HangHoa.TK_GIA_VON, 0);
				}

				if (hangHoa.getTkDoanhThu() != null && hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk() != null
						&& !hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
					logger.info("Tài khoản doanh thu:" + hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk());
					double doanhThu = Utils.multiply(hangHoa.getTkDoanhThu().getSoTien().getSoTien(),
							chungTu.getLoaiTien().getBanRa());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, doanhThu);
							stt.setInt(3, hangHoa.getTkDoanhThu().getSoDu());
							stt.setString(4, hangHoa.getTkDoanhThu().getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), hangHoa.getTkDoanhThu().getNhomDk(), HangHoa.TK_DOANH_THU,
							0);
				}

				if (hangHoa.getTkChiPhi() != null && hangHoa.getTkChiPhi().getLoaiTaiKhoan().getMaTk() != null
						&& !hangHoa.getTkChiPhi().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
					logger.info("Tài khoản chi phí:" + hangHoa.getTkChiPhi().getLoaiTaiKhoan().getMaTk());
					double chiPhi = Utils.multiply(hangHoa.getTkChiPhi().getSoTien().getSoTien(),
							chungTu.getLoaiTien().getBanRa());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkChiPhi().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, chiPhi);
							stt.setInt(3, hangHoa.getTkChiPhi().getSoDu());
							stt.setString(4, hangHoa.getTkChiPhi().getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), hangHoa.getTkChiPhi().getNhomDk(), HangHoa.TK_CHI_PHI, 0);
				}

				if (hangHoa.getTkThanhtoan() != null && hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk() != null
						&& !hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
					logger.info("Tài khoản thanh toán:" + hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk());
					double thanhToan = Utils.multiply(hangHoa.getTkThanhtoan().getSoTien().getSoTien(),
							chungTu.getLoaiTien().getBanRa());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, thanhToan);
							stt.setInt(3, hangHoa.getTkThanhtoan().getSoDu());
							stt.setString(4, hangHoa.getTkThanhtoan().getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), hangHoa.getTkThanhtoan().getNhomDk(), HangHoa.TK_THANH_TOAN,
							0);
				}

				// Các tài khoản về thuế nhập khẩu, xuất khẩu,
				// tiêu thụ đặc biệt, thuế giá trị gia tăng
				if (hangHoa.getTkThueNk() != null && hangHoa.getTkThueNk().getLoaiTaiKhoan().getMaTk() != null
						&& !hangHoa.getTkThueNk().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
					logger.info("Tài khoản thuế nhập khẩu:" + hangHoa.getTkThueNk().getLoaiTaiKhoan().getMaTk());
					double nhapKhau = Utils.multiply(hangHoa.getTkThueNk().getSoTien().getSoTien(),
							chungTu.getLoaiTien().getBanRa());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkThueNk().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, nhapKhau);
							stt.setInt(3, hangHoa.getTkThueNk().getSoDu());
							stt.setString(4, hangHoa.getTkThueNk().getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), hangHoa.getTkThueNk().getNhomDk(), HangHoa.TK_THUE_NK,
							hangHoa.getThueSuatNk());
				}

				if (hangHoa.getTkThueXk() != null && hangHoa.getTkThueXk().getLoaiTaiKhoan().getMaTk() != null
						&& !hangHoa.getTkThueXk().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
					logger.info("Tài khoản thuế xuất khẩu:" + hangHoa.getTkThueXk().getLoaiTaiKhoan().getMaTk());
					double xuatKhau = Utils.multiply(hangHoa.getTkThueXk().getSoTien().getSoTien(),
							chungTu.getLoaiTien().getBanRa());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkThueXk().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, xuatKhau);
							stt.setInt(3, hangHoa.getTkThueXk().getSoDu());
							stt.setString(4, hangHoa.getTkThueXk().getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), hangHoa.getTkThueXk().getNhomDk(), HangHoa.TK_THUE_XK,
							hangHoa.getThueSuatXk());
				}

				if (hangHoa.getTkThueTtdb() != null && hangHoa.getTkThueTtdb().getLoaiTaiKhoan().getMaTk() != null
						&& !hangHoa.getTkThueTtdb().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
					logger.info(
							"Tài khoản thuế tiêu thụ đặc biệt:" + hangHoa.getTkThueTtdb().getLoaiTaiKhoan().getMaTk());
					double ttdb = Utils.multiply(hangHoa.getTkThueTtdb().getSoTien().getSoTien(),
							chungTu.getLoaiTien().getBanRa());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkThueTtdb().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, ttdb);
							stt.setInt(3, hangHoa.getTkThueTtdb().getSoDu());
							stt.setString(4, hangHoa.getTkThueTtdb().getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), hangHoa.getTkThueTtdb().getNhomDk(), HangHoa.TK_THUE_TTDB,
							hangHoa.getThueSuatTtdb());
				}

				if (hangHoa.getTkThueGtgtDu() != null && hangHoa.getTkThueGtgtDu().getLoaiTaiKhoan().getMaTk() != null
						&& !hangHoa.getTkThueGtgtDu().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
					logger.info("Tài khoản đối ứng thuế gtgt:" + hangHoa.getTkThueGtgtDu().getLoaiTaiKhoan().getMaTk());
					double gtgtDu = Utils.multiply(hangHoa.getTkThueGtgtDu().getSoTien().getSoTien(),
							chungTu.getLoaiTien().getBanRa());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkThueGtgtDu().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, gtgtDu);
							stt.setInt(3, hangHoa.getTkThueGtgtDu().getSoDu());
							stt.setString(4, hangHoa.getTkThueGtgtDu().getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), hangHoa.getTkThueGtgtDu().getNhomDk(),
							HangHoa.TK_THUE_GTGT_DU, hangHoa.getThueSuatGtgt());
				}

				if (hangHoa.getTkThueGtgt() != null && hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk() != null
						&& !hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
					logger.info("Tài khoản thuế gtgt:" + hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk());
					double gtgt = Utils.multiply(hangHoa.getTkThueGtgt().getSoTien().getSoTien(),
							chungTu.getLoaiTien().getBanRa());
					int nvKtId = 1;
					if (!hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk().trim().equals("0")) {
						GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
						jdbcTmpl.update(new PreparedStatementCreator() {
							@Override
							public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
								PreparedStatement stt = con.prepareStatement(themTaiKhoan,
										Statement.RETURN_GENERATED_KEYS);
								stt.setString(1, hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk());
								stt.setDouble(2, gtgt);
								stt.setInt(3, hangHoa.getTkThueGtgt().getSoDu());
								stt.setString(4, hangHoa.getTkThueGtgt().getLyDo());

								return stt;
							}
						}, nvktHolder);
						nvKtId = nvktHolder.getKey().intValue();
					}
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(), nvKtId,
							hangHoa.getTkThueGtgt().getNhomDk(), HangHoa.TK_THUE_GTGT, hangHoa.getThueSuatGtgt());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int demSoChungTuTheoLoaiCtVaKy(String loaiCt, Date batDau, Date ketThuc) {
		if (batDau == null || ketThuc == null) {
			return 0;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		String batDauStr = sdf.format(batDau);
		String ketThucStr = sdf.format(ketThuc);

		String demSoChungTu = DEM_SO_CHUNG_TU;

		int count = jdbcTmpl.queryForObject(demSoChungTu, new Object[] { loaiCt, batDauStr, ketThucStr },
				Integer.class);
		return count;
	}

	@Override
	public int laySoChungTuLonNhatTheoLoaiCtVaKy(String loaiCt, Date batDau, Date ketThuc) {
		if (batDau == null || ketThuc == null) {
			return 0;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		String batDauStr = sdf.format(batDau);
		String ketThucStr = sdf.format(ketThuc);

		String laySoChungTuLonNhat = LAY_SO_CHUNG_TU_LON_NHAT;

		logger.info("Lấy số chứng từ lớn nhất");
		logger.info(laySoChungTuLonNhat);
		logger.info("Loại chứng từ: " + loaiCt);
		logger.info("Từ: " + batDauStr);
		logger.info("Đến: " + ketThucStr);

		int count = 0;
		try {
			count = jdbcTmpl.queryForObject(laySoChungTuLonNhat, new Object[] { loaiCt, batDauStr, ketThucStr },
					Integer.class);
		} catch (Exception e) {
		}

		return count;
	}

	@Override
	public int kiemTraSoChungTu(int soCt, String loaiCt, Date batDau, Date ketThuc) {
		if (batDau == null || ketThuc == null) {
			return 0;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		String batDauStr = sdf.format(batDau);
		String ketThucStr = sdf.format(ketThuc);

		String kiemTraSoChungTuLon = KIEM_TRA_SO_CHUNG_TU;

		logger.info("Kiểm tra số chứng từ đã tồn tại ?");
		logger.info(kiemTraSoChungTuLon);
		logger.info("Loại chứng từ: " + loaiCt);
		logger.info("Số chứng từ: " + soCt);
		logger.info("Từ: " + batDauStr);
		logger.info("Đến: " + ketThucStr);

		int count = jdbcTmpl.queryForObject(kiemTraSoChungTuLon, new Object[] { loaiCt, batDauStr, ketThucStr, soCt },
				Integer.class);
		logger.info("Kết quả:: " + count);
		return count;
	}

	@Override
	public void xoaChungTu(int maCt, String loaiCt) {
		String xoaChungTu = XOA_CHUNG_TU_THEO_MACT_LOAICT;
		String xoaChungTuTk = XOA_CHUNG_TU_NVKT;
		String xoaChungTuNvkt = XOA_NVKT;
		String layChungTuTaiKhoan = LAY_CHUNG_TU_TAI_KHOAN;

		// Lấy danh sách nvkt hiện có của chứng từ
		Object[] objs = { maCt };
		List<Integer> maNvktDs = jdbcTmpl.query(layChungTuTaiKhoan, objs, new RowMapper<Integer>() {
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("MA_NVKT");
			}
		});

		if (maNvktDs != null) {
			Iterator<Integer> maNvktIter = maNvktDs.iterator();
			while (maNvktIter.hasNext()) {
				Integer maNvkt = (Integer) maNvktIter.next();
				try {
					// Xóa trong bảng CHUNG_TU_NVKT
					jdbcTmpl.update(xoaChungTuTk, maCt, maNvkt);

					// Xóa trong bảng NGHIEP_VU_KE_TOAN
					jdbcTmpl.update(xoaChungTuNvkt, maNvkt);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		jdbcTmpl.update(xoaChungTu, maCt, loaiCt);
		logger.info("Xóa chứng từ có MA_CT = " + maCt);
	}

	@Override
	public void xoaChungTuKho(ChungTu chungTu) {
		if (chungTu == null)
			return;

		String xoaChungTu = XOA_CHUNG_TU_THEO_MACT_LOAICT;
		String xoaChungTuHangHoa = XOA_CHUNG_TU_HANG_HOA;
		String xoaChungTuNvktKho = XOA_CHUNG_TU_NVKT_KHO;
		String xoaChungTuNvkt = XOA_NVKT;

		// Xóa chứng từ
		jdbcTmpl.update(xoaChungTu, chungTu.getMaCt(), chungTu.getLoaiCt());

		// Xóa dữ liệu cũ liên quan đến chứng từ:
		// Xóa chung_tu_hang_hoa
		jdbcTmpl.update(xoaChungTuHangHoa, chungTu.getMaCt());

		// Xóa chung_tu_nvkt
		jdbcTmpl.update(xoaChungTuNvktKho, chungTu.getMaCt());

		// Xóa nghiep_vu_ke_toan
		if (chungTu.getHangHoaDs() != null && chungTu.getHangHoaDs().size() > 0) {
			// Với từng loại hàng hóa
			Iterator<HangHoa> hhIter = chungTu.getHangHoaDs().iterator();
			while (hhIter.hasNext()) {
				HangHoa hangHoa = hhIter.next();

				if (hangHoa.getTkThanhtoan() != null && hangHoa.getTkThanhtoan().getLoaiTaiKhoan() != null
						&& hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk() != null) {
					jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkThanhtoan().getMaNvkt());
				}

				if (hangHoa.getTkKho() != null && hangHoa.getTkKho().getLoaiTaiKhoan() != null
						&& hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk() != null) {
					jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkKho().getMaNvkt());
				}

				if (hangHoa.getTkDoanhThu() != null && hangHoa.getTkDoanhThu().getLoaiTaiKhoan() != null
						&& hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk() != null) {
					jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkDoanhThu().getMaNvkt());
				}

				if (hangHoa.getTkChiPhi() != null && hangHoa.getTkChiPhi().getLoaiTaiKhoan() != null
						&& hangHoa.getTkChiPhi().getLoaiTaiKhoan().getMaTk() != null) {
					jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkChiPhi().getMaNvkt());
				}

				if (hangHoa.getTkGiaVon() != null && hangHoa.getTkGiaVon().getLoaiTaiKhoan() != null
						&& hangHoa.getTkGiaVon().getLoaiTaiKhoan().getMaTk() != null) {
					jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkGiaVon().getMaNvkt());
				}

				if (hangHoa.getTkThueNk() != null && hangHoa.getTkThueNk().getLoaiTaiKhoan() != null
						&& hangHoa.getTkThueNk().getLoaiTaiKhoan().getMaTk() != null) {
					jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkThueNk().getMaNvkt());
				}

				if (hangHoa.getTkThueXk() != null && hangHoa.getTkThueXk().getLoaiTaiKhoan() != null
						&& hangHoa.getTkThueXk().getLoaiTaiKhoan().getMaTk() != null) {
					jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkThueXk().getMaNvkt());
				}

				if (hangHoa.getTkThueTtdb() != null && hangHoa.getTkThueTtdb().getLoaiTaiKhoan() != null
						&& hangHoa.getTkThueTtdb().getLoaiTaiKhoan().getMaTk() != null) {
					jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkThueTtdb().getMaNvkt());
				}

				if (hangHoa.getTkThueGtgt() != null && hangHoa.getTkThueGtgt().getLoaiTaiKhoan() != null
						&& hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk() != null) {
					jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkThueGtgt().getMaNvkt());
				}

				if (hangHoa.getTkThueGtgtDu() != null && hangHoa.getTkThueGtgtDu().getLoaiTaiKhoan() != null
						&& hangHoa.getTkThueGtgtDu().getLoaiTaiKhoan().getMaTk() != null) {
					jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkThueGtgtDu().getMaNvkt());
				}
			}
		}

		logger.info("Xóa chứng từ có MA_CT = " + chungTu.getMaCt());
	}

	@Override
	public void themKetChuyenButToan(KetChuyenButToan ketChuyenButToan) {
		if (ketChuyenButToan == null)
			return;

		String query = THEM_KET_CHUYEN_BUT_TOAN;
		try {
			jdbcTmpl.update(query, ketChuyenButToan.getTenKc(),
					ketChuyenButToan.getTaiKhoanNo().getLoaiTaiKhoan().getMaTk(),
					ketChuyenButToan.getTaiKhoanCo().getLoaiTaiKhoan().getMaTk(), ketChuyenButToan.getCongThuc(),
					ketChuyenButToan.getMoTa(), ketChuyenButToan.getThuTu(), ketChuyenButToan.getLoaiKc());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void capNhatKetChuyenButToan(KetChuyenButToan ketChuyenButToan) {
		if (ketChuyenButToan == null)
			return;

		String query = CAP_NHAT_KET_CHUYEN_BUT_TOAN;
		try {
			jdbcTmpl.update(query, ketChuyenButToan.getTenKc(),
					ketChuyenButToan.getTaiKhoanNo().getLoaiTaiKhoan().getMaTk(),
					ketChuyenButToan.getTaiKhoanCo().getLoaiTaiKhoan().getMaTk(), ketChuyenButToan.getCongThuc(),
					ketChuyenButToan.getMoTa(), ketChuyenButToan.getThuTu(), ketChuyenButToan.getLoaiKc(),
					ketChuyenButToan.getMaKc());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public KetChuyenButToan layKetChuyenButToan(int maKc) {
		String query = LAY_KET_CHUYEN_BUT_TOAN;

		Object[] objs = { maKc };
		try {
			return jdbcTmpl.queryForObject(query, objs, new KetChuyenButToanMapper());
		} catch (Exception e) {

		}

		return null;
	}

	@Override
	public void xoaKetChuyenButToan(int maKc) {
		String query = XOA_KET_CHUYEN_BUT_TOAN;
		try {
			jdbcTmpl.update(query, maKc);
		} catch (Exception e) {

		}
	}

	@Override
	public List<KetChuyenButToan> danhSachKetChuyenButToan() {
		String query = DANH_SACH_KET_CHUYEN_BUT_TOAN;

		logger.info(query);
		return jdbcTmpl.query(query, new KetChuyenButToanMapper());
	}

	@Override
	public List<KetChuyenButToan> danhSachKetChuyenButToan(int loaiKc) {
		String query = DANH_SACH_KET_CHUYEN_BUT_TOAN_THEO_LOAI;
		logger.info(query);
		logger.info("loaiKc " + loaiKc);

		Object[] objs = { loaiKc };
		return jdbcTmpl.query(query, objs, new KetChuyenButToanMapper());
	}

	public class KetChuyenButToanMapper implements RowMapper<KetChuyenButToan> {
		public KetChuyenButToan mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				// Bút toán kết chuyển
				KetChuyenButToan ketChuyenButToan = new KetChuyenButToan();
				ketChuyenButToan.setMaKc(rs.getInt("MA_KC"));
				ketChuyenButToan.setTenKc(rs.getString("TEN_KC"));
				ketChuyenButToan.setMoTa(rs.getString("MO_TA"));
				ketChuyenButToan.setCongThuc(rs.getString("CONG_THUC"));
				ketChuyenButToan.setThuTu(rs.getInt("THU_TU"));
				ketChuyenButToan.setLoaiKc(rs.getInt("LOAI_KC"));

				// Tài khoản nợ
				TaiKhoan taiKhoanNo = new TaiKhoan();
				LoaiTaiKhoan loaiTaiKhoanNo = new LoaiTaiKhoan();
				loaiTaiKhoanNo.setMaTk(rs.getString("MA_TK_NO"));
				loaiTaiKhoanNo.setTenTk(rs.getString("TEN_TK_NO"));
				loaiTaiKhoanNo.setMaTenTk(loaiTaiKhoanNo.getMaTk() + " - " + loaiTaiKhoanNo.getTenTk());
				loaiTaiKhoanNo.setSoDu(rs.getInt("SO_DU_NO"));
				loaiTaiKhoanNo.setSoDuGiaTri(LoaiTaiKhoan.NO);
				loaiTaiKhoanNo.setLuongTinh(rs.getBoolean("LUONG_TINH_NO"));

				taiKhoanNo.setLoaiTaiKhoan(loaiTaiKhoanNo);
				taiKhoanNo.setSoDu(LoaiTaiKhoan.NO);
				ketChuyenButToan.setTaiKhoanNo(taiKhoanNo);

				// Tài khoản có
				TaiKhoan taiKhoanCo = new TaiKhoan();
				LoaiTaiKhoan loaiTaiKhoanCo = new LoaiTaiKhoan();
				loaiTaiKhoanCo.setMaTk(rs.getString("MA_TK_CO"));
				loaiTaiKhoanCo.setTenTk(rs.getString("TEN_TK_CO"));
				loaiTaiKhoanCo.setMaTenTk(loaiTaiKhoanCo.getMaTk() + " - " + loaiTaiKhoanCo.getTenTk());
				loaiTaiKhoanCo.setSoDu(rs.getInt("SO_DU_CO"));
				loaiTaiKhoanCo.setSoDuGiaTri(LoaiTaiKhoan.CO);
				loaiTaiKhoanCo.setLuongTinh(rs.getBoolean("LUONG_TINH_CO"));

				taiKhoanCo.setLoaiTaiKhoan(loaiTaiKhoanCo);
				taiKhoanCo.setSoDu(LoaiTaiKhoan.CO);
				ketChuyenButToan.setTaiKhoanCo(taiKhoanCo);

				logger.info(ketChuyenButToan);

				return ketChuyenButToan;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public ChungTu layKetChuyen(int maCt) {
		String query = LAY_CHUNG_TU_KET_CHUYEN;
		logger.info(query);

		Object[] objs = { ChungTu.CHUNG_TU_KET_CHUYEN, maCt };
		try {
			List<ChungTu> ketChuyenDs = jdbcTmpl.query(query, objs, new ChungTuKetChuyenMapper());

			List<ChungTu> ketQua = null;
			if (ketChuyenDs != null) {
				ketQua = new ArrayList<>();
				Iterator<ChungTu> iter = ketChuyenDs.iterator();
				while (iter.hasNext()) {
					ChungTu chungTu = iter.next();

					int pos = ketQua.indexOf(chungTu);
					if (pos > -1) {
						ChungTu chungTuTmpl = ketQua.get(pos);
						chungTuTmpl.themKetChuyenButToan(chungTu.getKcbtDs());
					} else {
						ketQua.add(chungTu);
					}
				}
			}

			return ketQua.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Date layKetChuyenGanNhat(int loaiKc, Date batDau, Date ketThuc) {
		if (batDau == null || ketThuc == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		String batDauStr = sdf.format(batDau);
		String ketThucStr = sdf.format(ketThuc);

		String query = LAY_NGAY_KET_CHUYEN_GAN_NHAT;
		Object[] objs = { ChungTu.CHUNG_TU_KET_CHUYEN, loaiKc, batDauStr, ketThucStr };
		try {
			return jdbcTmpl.queryForObject(query, objs, Date.class);
		} catch (Exception e) {

		}

		return null;
	}

	public List<ChungTu> danhSachKetChuyen(Date batDau, Date ketThuc) {
		if (batDau == null || ketThuc == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		String batDauStr = sdf.format(batDau);
		String ketThucStr = sdf.format(ketThuc);

		String query = DANH_SACH_CHUNG_TU_KET_CHUYEN;

		Object[] objs = { ChungTu.CHUNG_TU_KET_CHUYEN, batDauStr, ketThucStr };
		List<ChungTu> ketChuyenDs = jdbcTmpl.query(query, objs, new ChungTuKetChuyenMapper());

		List<ChungTu> ketQua = null;
		if (ketChuyenDs != null) {
			ketQua = new ArrayList<>();
			Iterator<ChungTu> iter = ketChuyenDs.iterator();
			while (iter.hasNext()) {
				ChungTu chungTu = iter.next();

				int pos = ketQua.indexOf(chungTu);
				if (pos > -1) {
					ChungTu chungTuTmpl = ketQua.get(pos);
					chungTuTmpl.themKetChuyenButToan(chungTu.getKcbtDs());
				} else {
					ketQua.add(chungTu);
				}
			}
		}

		return ketQua;
	}

	public class ChungTuKetChuyenMapper implements RowMapper<ChungTu> {
		public ChungTu mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				ChungTu chungTu = new ChungTu();
				chungTu.setMaCt(rs.getInt("MA_CT"));
				chungTu.setSoCt(rs.getInt("SO_CT"));
				chungTu.setLoaiCt(rs.getString("LOAI_CT"));
				chungTu.setTinhChatCt(rs.getInt("TINH_CHAT"));
				chungTu.setLyDo(rs.getString("LY_DO"));
				chungTu.setKemTheo(rs.getInt("KEM_THEO"));
				chungTu.setNgayLap(rs.getDate("NGAY_LAP"));
				chungTu.setNgayHt(rs.getDate("NGAY_HT"));

				LoaiTien loaiTien = new LoaiTien();
				loaiTien.setMaLt(rs.getString("LOAI_TIEN"));
				loaiTien.setTenLt(rs.getString("TEN_NT"));
				loaiTien.setBanRa(rs.getDouble("TY_GIA"));
				chungTu.setLoaiTien(loaiTien);

				TaiKhoan taiKhoan = new TaiKhoan();
				taiKhoan.setMaNvkt(rs.getInt("MA_NVKT"));
				taiKhoan.setSoDu(rs.getInt("SO_DU"));
				taiKhoan.setLyDo(rs.getString("TK_LY_DO"));

				LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
				loaiTaiKhoan.setMaTk(rs.getString("MA_TK"));
				loaiTaiKhoan.setTenTk(rs.getString("TEN_TK"));
				taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);

				Tien tien = new Tien();
				tien.setLoaiTien(loaiTien);
				tien.setGiaTri(rs.getDouble("SO_TIEN"));
				tien.setSoTien(tien.getGiaTri() / tien.getLoaiTien().getBanRa());
				taiKhoan.setSoTien(tien);

				KetChuyenButToan ketChuyenButToan = new KetChuyenButToan();
				ketChuyenButToan.setMaKc(rs.getInt("MA_KC"));
				ketChuyenButToan.setTenKc(rs.getString("TEN_KC"));
				ketChuyenButToan.setCongThuc(rs.getString("CONG_THUC"));
				ketChuyenButToan.setMoTa(rs.getString("MO_TA"));
				ketChuyenButToan.setThuTu(rs.getInt("THU_TU"));
				ketChuyenButToan.setLoaiKc(rs.getInt("LOAI_KC"));

				if (taiKhoan.getSoDu() == LoaiTaiKhoan.NO) {
					ketChuyenButToan.setTaiKhoanNo(taiKhoan);
				} else {
					ketChuyenButToan.setTaiKhoanCo(taiKhoan);
				}

				chungTu.themKetChuyenButToan(ketChuyenButToan);
				taiKhoan.setChungTu(chungTu);

				return chungTu;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public void themChungTuKetChuyen(ChungTu chungTu) {
		if (chungTu == null || chungTu.getKcbtDs() == null || chungTu.getKcbtDs().size() == 0) {
			return;
		}

		String themChungTu = THEM_CHUNG_TU_KET_CHUYEN;
		String themTaiKhoan = THEM_TAI_KHOAN;
		String themChungTuTaiKhoan = THEM_CHUNG_TU_TAI_KHOAN_KET_CHUYEN;

		try {
			// Thêm chứng từ
			GeneratedKeyHolder holder = new GeneratedKeyHolder();
			jdbcTmpl.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement stt = con.prepareStatement(themChungTu, Statement.RETURN_GENERATED_KEYS);
					stt.setInt(1, chungTu.getSoCt());
					stt.setString(2, chungTu.getLoaiCt());
					stt.setInt(3, chungTu.getTinhChatCt());
					java.sql.Date ngayHt = new java.sql.Date(chungTu.getNgayHt().getTime());
					stt.setDate(4, ngayHt);// ngày lập
					stt.setDate(5, ngayHt);
					stt.setString(6, chungTu.getLyDo());
					stt.setString(7, chungTu.getLoaiTien().getMaLt());
					stt.setDouble(8, chungTu.getLoaiTien().getBanRa());

					return stt;
				}
			}, holder);

			chungTu.setMaCt(holder.getKey().intValue());

			// Với từng loại kết chuyển
			Iterator<KetChuyenButToan> iter = chungTu.getKcbtDs().iterator();
			while (iter.hasNext()) {
				KetChuyenButToan ketChuyenButToan = iter.next();
				logger.info("Kết chuyển: " + ketChuyenButToan);

				String congThuc = ketChuyenButToan.getCongThuc();
				logger.info("Công thức tính " + congThuc);

				// Tính giá trị các tài khoản
				List<String> toanHangDs = ExpressionEval.getOperands(congThuc);
				if (toanHangDs != null) {
					// Với mỗi toán hạng, đó là một tài khoản kết toán
					// Cần tính giá trị của tài khoản kế toán
					Iterator<String> toanHangIter = toanHangDs.iterator();
					while (toanHangIter.hasNext()) {
						String toanHang = toanHangIter.next();

						try {
							String[] phanDs = toanHang.split("\\.");
							String maTk = phanDs[0].trim();
							String soDuStr = phanDs[1].trim();

							int soDu = LoaiTaiKhoan.NO;
							if (soDuStr.equals(LoaiTaiKhoan.CO_XAU)) {
								soDu = LoaiTaiKhoan.CO;
							}

							Date ngayLap = chungTu.getNgayLap();
							Calendar cal = Calendar.getInstance();
							cal.setTime(ngayLap);
							cal.add(Calendar.DAY_OF_YEAR, 1);

							double giaTri = tongPhatSinh(maTk, soDu, cal.getTime(), chungTu.getNgayHt());
							congThuc = congThuc.replaceAll(toanHang, giaTri + "");

							logger.info(toanHang + ": " + giaTri);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				// Tính kết quả thực của công thức
				try {
					double ketQua = ExpressionEval.calExp(congThuc);
					logger.info("Kết quả " + ketQua);

					Tien soTien = new Tien();
					soTien.setLoaiTien(chungTu.getLoaiTien());

					if (ketQua >= 0) {
						soTien.setSoTien(ketQua / chungTu.getLoaiTien().getBanRa());
						soTien.setGiaTri(ketQua);
					} else {
						soTien.setSoTien((ketQua * -1) / chungTu.getLoaiTien().getBanRa());
						soTien.setGiaTri(ketQua * -1);

						TaiKhoan taiKhoanNo = ketChuyenButToan.getTaiKhoanNo();
						taiKhoanNo.setSoDu(LoaiTaiKhoan.CO);
						TaiKhoan taiKhoanCo = ketChuyenButToan.getTaiKhoanCo();
						taiKhoanCo.setSoDu(LoaiTaiKhoan.NO);

						ketChuyenButToan.setTaiKhoanNo(taiKhoanCo);
						ketChuyenButToan.setTaiKhoanCo(taiKhoanNo);
					}

					ketChuyenButToan.getTaiKhoanNo().setSoTien(soTien);
					ketChuyenButToan.getTaiKhoanCo().setSoTien(soTien);
				} catch (Exception e) {
					e.printStackTrace();
				}

				logger.info("Thêm vào bảng nghiep_vu_ke_toan: ");
				if (ketChuyenButToan.getTaiKhoanNo() != null
						&& ketChuyenButToan.getTaiKhoanNo().getLoaiTaiKhoan() != null
						&& ketChuyenButToan.getTaiKhoanNo().getLoaiTaiKhoan().getMaTk() != null
						&& !ketChuyenButToan.getTaiKhoanNo().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
					logger.info("Tài khoản nợ: " + ketChuyenButToan.getTaiKhoanNo().getLoaiTaiKhoan().getMaTk());

					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, ketChuyenButToan.getTaiKhoanNo().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, ketChuyenButToan.getTaiKhoanNo().getSoTien().getGiaTri());
							stt.setInt(3, ketChuyenButToan.getTaiKhoanNo().getSoDu());
							stt.setString(4, chungTu.getLyDo());

							return stt;
						}
					}, nvktHolder);

					jdbcTmpl.update(themChungTuTaiKhoan, chungTu.getMaCt(), ketChuyenButToan.getMaKc(),
							nvktHolder.getKey().intValue());
				}

				if (ketChuyenButToan.getTaiKhoanCo() != null
						&& ketChuyenButToan.getTaiKhoanCo().getLoaiTaiKhoan() != null
						&& ketChuyenButToan.getTaiKhoanCo().getLoaiTaiKhoan().getMaTk() != null
						&& !ketChuyenButToan.getTaiKhoanCo().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
					logger.info("Tài khoản có: " + ketChuyenButToan.getTaiKhoanCo().getLoaiTaiKhoan().getMaTk());

					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, ketChuyenButToan.getTaiKhoanCo().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, ketChuyenButToan.getTaiKhoanCo().getSoTien().getGiaTri());
							stt.setInt(3, ketChuyenButToan.getTaiKhoanCo().getSoDu());
							stt.setString(4, chungTu.getLyDo());

							return stt;
						}
					}, nvktHolder);

					jdbcTmpl.update(themChungTuTaiKhoan, chungTu.getMaCt(), ketChuyenButToan.getMaKc(),
							nvktHolder.getKey().intValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void xoaKetChuyen(ChungTu chungTu) {
		if (chungTu == null) {
			return;
		}

		String xoaChungTu = XOA_CHUNG_TU_THEO_MACT_LOAICT;
		String xoaChungTuNvktKho = XOA_CHUNG_TU_NVKT_KHO;
		String xoaChungTuNvkt = XOA_NVKT;

		// Xóa chứng từ
		jdbcTmpl.update(xoaChungTu, chungTu.getMaCt(), chungTu.getLoaiCt());

		// Xóa chung_tu_nvkt
		jdbcTmpl.update(xoaChungTuNvktKho, chungTu.getMaCt());

		// Xóa nghiep_vu_ke_toan
		if (chungTu.getKcbtDs() != null && chungTu.getKcbtDs().size() > 0) {
			// Với từng loại kết chuyển
			Iterator<KetChuyenButToan> iter = chungTu.getKcbtDs().iterator();
			while (iter.hasNext()) {
				KetChuyenButToan ketChuyenButToan = iter.next();

				if (ketChuyenButToan.getTaiKhoanNo() != null
						&& ketChuyenButToan.getTaiKhoanNo().getLoaiTaiKhoan() != null
						&& ketChuyenButToan.getTaiKhoanNo().getLoaiTaiKhoan() != null) {
					jdbcTmpl.update(xoaChungTuNvkt, ketChuyenButToan.getTaiKhoanNo().getMaNvkt());
				}

				if (ketChuyenButToan.getTaiKhoanCo() != null
						&& ketChuyenButToan.getTaiKhoanCo().getLoaiTaiKhoan() != null
						&& ketChuyenButToan.getTaiKhoanCo().getLoaiTaiKhoan() != null) {
					jdbcTmpl.update(xoaChungTuNvkt, ketChuyenButToan.getTaiKhoanCo().getMaNvkt());
				}

			}
		}

		logger.info("Xóa chứng từ có MA_CT = " + chungTu.getMaCt());
	}

	private double tongPhatSinh(String maTk, int soDu, Date dau, Date cuoi) {
		if (maTk == null || maTk.trim().equals("")) {
			return 0;
		}

		String query = TONG_PHAT_SINH;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");

		if (dau != null) {
			String batDau = sdf.format(dau);
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "AND CT.NGAY_HT >= '" + batDau + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "");
		}

		if (cuoi != null) {
			String ketThuc = sdf.format(cuoi);
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "AND CT.NGAY_HT <= '" + ketThuc + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "");
		}

		query = query.replaceAll("\\$MA_TK\\$", maTk);

		try {
			Object[] objs = { soDu };
			double result = jdbcTmpl.queryForObject(query, objs, Double.class);
			return result;
		} catch (Exception e) {
			return 0;
		}
	}
}
