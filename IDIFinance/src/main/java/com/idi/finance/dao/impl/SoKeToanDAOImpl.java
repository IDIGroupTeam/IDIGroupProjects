package com.idi.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.idi.finance.bean.LoaiTien;
import com.idi.finance.bean.cdkt.BalanceAssetItem;
import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.DoiTuong;
import com.idi.finance.bean.chungtu.TaiKhoan;
import com.idi.finance.bean.chungtu.Tien;
import com.idi.finance.bean.soketoan.NghiepVuKeToan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.SoKeToanDAO;

public class SoKeToanDAOImpl implements SoKeToanDAO {
	private static final Logger logger = Logger.getLogger(SoKeToanDAOImpl.class);

	@Value("${DANH_SACH_CHUNG_TU_NHAT_KY_CHUNG_THEO_DIEU_KIEN}")
	private String DANH_SACH_CHUNG_TU_NHAT_KY_CHUNG_THEO_DIEU_KIEN;

	@Value("${DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_MA_TAI_KHOAN}")
	private String DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_MA_TAI_KHOAN;

	@Value("${DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_DIEU_KIEN}")
	private String DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_DIEU_KIEN;

	@Value("${DANH_SACH_NGHIEP_VU_KE_TOAN_KTTH_THEO_DIEU_KIEN}")
	private String DANH_SACH_NGHIEP_VU_KE_TOAN_KTTH_THEO_DIEU_KIEN;

	@Value("${TONG_PHAT_SINH}")
	private String TONG_PHAT_SINH;

	@Value("${DANH_SACH_TKKT_THEO_DIEU_KIEN}")
	private String DANH_SACH_TKKT_THEO_DIEU_KIEN;

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public List<ChungTu> danhSachChungTu(Date dau, Date cuoi, List<String> loaiCts) {
		String query = DANH_SACH_CHUNG_TU_NHAT_KY_CHUNG_THEO_DIEU_KIEN;

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
		String batDau = sdf.format(dau);
		String ketThuc = sdf.format(cuoi);

		logger.info("Từ " + batDau + " đến " + ketThuc);
		logger.info(query);

		Object[] objs = { batDau, ketThuc, batDau, ketThuc, batDau, ketThuc };
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
				chungTu.setLyDo(rs.getString("LY_DO"));
				chungTu.setKemTheo(rs.getInt("KEM_THEO"));
				chungTu.setNgayLap(rs.getDate("NGAY_LAP"));
				chungTu.setNgayHt(rs.getDate("NGAY_HT"));

				DoiTuong doiTuong = new DoiTuong();
				doiTuong.setMaDt(rs.getInt("MA_DT"));
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
				taiKhoan.setSoDu(rs.getInt("SO_DU"));
				taiKhoan.setLyDo(rs.getString("TK_LY_DO"));

				LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
				loaiTaiKhoan.setMaTk(rs.getString("MA_TK"));
				loaiTaiKhoan.setTenTk(rs.getString("TEN_TK"));
				taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);

				Tien tien = new Tien();
				tien.setSoTien(rs.getDouble("SO_TIEN"));
				// if (loaiTaiKhoan.getMaTk().equals(LoaiTaiKhoan.TIEN_MAT_NT)
				// || loaiTaiKhoan.getMaTk().equals(LoaiTaiKhoan.TIEN_GUI_NGAN_HANG_NT)
				// || loaiTaiKhoan.getMaTk().equals(LoaiTaiKhoan.TIEN_DANG_CHUYEN_NT)
				// || loaiTaiKhoan.getMaTk().equals(LoaiTaiKhoan.TIEN_MAT_VANG)
				// || loaiTaiKhoan.getMaTk().equals(LoaiTaiKhoan.TIEN_GUI_NGAN_HANG_VANG)) {
				tien.setLoaiTien(loaiTien);
				// }
				tien.setGiaTri(tien.getLoaiTien().getBanRa() * tien.getSoTien());
				taiKhoan.setSoTien(tien);

				chungTu.themTaiKhoan(taiKhoan);
				taiKhoan.setChungTu(chungTu);

				return chungTu;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public double tongPhatSinh(String maTk, int soDu, Date dau, Date cuoi) {
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

		logger.info("Tổng phát sinh tài khoản " + maTk + ". Số dư " + soDu + ". Từ " + dau + " đến " + cuoi);
		logger.info(query);

		try {
			Object[] objs = { soDu };
			double result = jdbcTmpl.queryForObject(query, objs, Double.class);
			// logger.info("Phát sinh: " + result);
			return result;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public List<NghiepVuKeToan> danhSachNghiepVuKeToanTheoLoaiTaiKhoan(String maTk) {
		String query = DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_MA_TAI_KHOAN;

		logger.info("Danh sách nghiệp vụ kế toán theo loại tài khoản: '" + maTk + "' ...");
		query = query.replaceAll("\\?", maTk);
		logger.info(query);

		List<NghiepVuKeToan> nghiepVuKeToanDs = jdbcTmpl.query(query, new NghiepVuKeToanMapper());
		return nghiepVuKeToanDs;
	}

	@Override
	public List<NghiepVuKeToan> danhSachNghiepVuKeToanTheoLoaiTaiKhoan(String maTk, Date dau, Date cuoi,
			List<String> loaiCts) {
		String query = DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_DIEU_KIEN;

		logger.info("Danh sách nghiệp vụ kế toán theo loại tài khoản: '" + maTk + "' ...");
		if (loaiCts.contains(ChungTu.TAT_CA)) {
			query = query.replaceAll("\\$DIEU_KIEN_LOAI_CT\\$", "");
			logger.info("Của tất cả các loại chứng từ ...");
		} else {
			logger.info("Của các loại chứng từ " + loaiCts + " ...");

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
		String batDau = sdf.format(dau);
		String ketThuc = sdf.format(cuoi);

		logger.info("Từ " + batDau + " đến " + ketThuc);

		query = query.replaceAll("\\$MA_TK\\$", maTk);
		logger.info(query);

		Object[] objs = { batDau, ketThuc, batDau, ketThuc, batDau, ketThuc };
		List<NghiepVuKeToan> nghiepVuKeToanDs = jdbcTmpl.query(query, objs, new NghiepVuKeToanMapper());
		return nghiepVuKeToanDs;
	}

	public class NghiepVuKeToanMapper implements RowMapper<NghiepVuKeToan> {
		public NghiepVuKeToan mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				ChungTu chungTu = new ChungTu();
				chungTu.setMaCt(rs.getInt("MA_CT"));
				chungTu.setSoCt(rs.getInt("SO_CT"));
				chungTu.setLoaiCt(rs.getString("LOAI_CT"));
				chungTu.setLyDo(rs.getString("LY_DO"));
				chungTu.setKemTheo(rs.getInt("KEM_THEO"));
				chungTu.setNgayLap(rs.getDate("NGAY_LAP"));
				chungTu.setNgayHt(rs.getDate("NGAY_HT"));

				LoaiTien loaiTien = new LoaiTien();
				loaiTien.setMaLt(rs.getString("LOAI_TIEN"));
				loaiTien.setTenLt(rs.getString("TEN_NT"));
				loaiTien.setBanRa(rs.getDouble("TY_GIA"));
				chungTu.setLoaiTien(loaiTien);

				DoiTuong doiTuong = new DoiTuong();
				doiTuong.setMaDt(rs.getInt("MA_DT"));
				doiTuong.setTenDt(rs.getString("TEN_DT"));
				doiTuong.setLoaiDt(rs.getInt("LOAI_DT"));
				doiTuong.setDiaChi(rs.getString("DIA_CHI"));
				doiTuong.setMaThue(rs.getString("MA_THUE"));
				doiTuong.setNguoiNop(rs.getString("NGUOI_NOP"));
				chungTu.setDoiTuong(doiTuong);

				TaiKhoan taiKhoanNo = new TaiKhoan();
				taiKhoanNo.setSoDu(LoaiTaiKhoan.NO);
				taiKhoanNo.setLyDo(rs.getString("LY_DO_NO"));

				LoaiTaiKhoan loaiTaiKhoanNo = new LoaiTaiKhoan();
				loaiTaiKhoanNo.setMaTk(rs.getString("MA_TK_NO"));
				loaiTaiKhoanNo.setTenTk(rs.getString("TEN_TK_NO"));
				taiKhoanNo.setLoaiTaiKhoan(loaiTaiKhoanNo);

				Tien tienNo = new Tien();
				tienNo.setSoTien(rs.getDouble("SO_TIEN_NO"));
				tienNo.setLoaiTien(loaiTien);
				tienNo.setGiaTri(tienNo.getLoaiTien().getBanRa() * tienNo.getSoTien());
				taiKhoanNo.setSoTien(tienNo);

				chungTu.themTaiKhoan(taiKhoanNo);
				taiKhoanNo.setChungTu(chungTu);

				TaiKhoan taiKhoanCo = new TaiKhoan();
				taiKhoanCo.setSoDu(LoaiTaiKhoan.CO);
				taiKhoanCo.setLyDo(rs.getString("LY_DO_CO"));

				LoaiTaiKhoan loaiTaiKhoanCo = new LoaiTaiKhoan();
				loaiTaiKhoanCo.setMaTk(rs.getString("MA_TK_CO"));
				loaiTaiKhoanCo.setTenTk(rs.getString("TEN_TK_CO"));
				taiKhoanCo.setLoaiTaiKhoan(loaiTaiKhoanCo);

				Tien tienCo = new Tien();
				tienCo.setSoTien(rs.getDouble("SO_TIEN_CO"));
				tienCo.setLoaiTien(loaiTien);
				tienCo.setGiaTri(tienCo.getLoaiTien().getBanRa() * tienCo.getSoTien());
				taiKhoanCo.setSoTien(tienCo);

				chungTu.themTaiKhoan(taiKhoanCo);
				taiKhoanCo.setChungTu(chungTu);

				NghiepVuKeToan nghiepVuKeToan = new NghiepVuKeToan();
				nghiepVuKeToan.setChungTu(chungTu);
				nghiepVuKeToan.setTaiKhoanNo(taiKhoanNo);
				nghiepVuKeToan.setTaiKhoanCo(taiKhoanCo);

				return nghiepVuKeToan;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public List<NghiepVuKeToan> danhSachNghiepVuKeToanTheoLoaiTaiKhoan(String maTk, Date dau, Date cuoi) {
		String query = DANH_SACH_NGHIEP_VU_KE_TOAN_KTTH_THEO_DIEU_KIEN;

		logger.info("Danh sách nghiệp vụ kế toán theo của phiếu kế toán tổng hợp ...");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		String batDau = sdf.format(dau);
		String ketThuc = sdf.format(cuoi);

		logger.info("Từ " + batDau + " đến " + ketThuc);

		query = query.replaceAll("\\$MA_TK\\$", maTk);
		logger.info(query);

		Object[] objs = { batDau, ketThuc, batDau, ketThuc, batDau, ketThuc };
		List<NghiepVuKeToan> nghiepVuKeToanDs = jdbcTmpl.query(query, objs, new NghiepVuKeToanKtthMapper());
		return nghiepVuKeToanDs;
	}

	public class NghiepVuKeToanKtthMapper implements RowMapper<NghiepVuKeToan> {
		public NghiepVuKeToan mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				ChungTu chungTu = new ChungTu();
				chungTu.setMaCt(rs.getInt("MA_CT"));
				chungTu.setSoCt(rs.getInt("SO_CT"));
				chungTu.setLoaiCt(rs.getString("LOAI_CT"));
				chungTu.setLyDo(rs.getString("LY_DO"));
				chungTu.setKemTheo(rs.getInt("KEM_THEO"));
				chungTu.setNgayLap(rs.getDate("NGAY_LAP"));
				chungTu.setNgayHt(rs.getDate("NGAY_HT"));

				LoaiTien loaiTien = new LoaiTien();
				loaiTien.setMaLt(rs.getString("LOAI_TIEN"));
				loaiTien.setTenLt(rs.getString("TEN_NT"));
				loaiTien.setBanRa(rs.getDouble("TY_GIA"));
				chungTu.setLoaiTien(loaiTien);

				DoiTuong doiTuong = new DoiTuong();
				doiTuong.setMaDt(rs.getInt("MA_DT"));
				doiTuong.setTenDt(rs.getString("TEN_DT"));
				doiTuong.setLoaiDt(rs.getInt("LOAI_DT"));
				doiTuong.setDiaChi(rs.getString("DIA_CHI"));
				doiTuong.setMaThue(rs.getString("MA_THUE"));
				doiTuong.setNguoiNop(rs.getString("NGUOI_NOP"));
				chungTu.setDoiTuong(doiTuong);

				TaiKhoan taiKhoan = new TaiKhoan();
				taiKhoan.setSoDu(rs.getInt("SO_DU"));
				taiKhoan.setLyDo(rs.getString("LY_DO"));

				LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
				loaiTaiKhoan.setMaTk(rs.getString("MA_TK"));
				loaiTaiKhoan.setTenTk(rs.getString("TEN_TK"));
				taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);

				Tien tien = new Tien();
				tien.setSoTien(rs.getDouble("SO_TIEN"));
				tien.setLoaiTien(loaiTien);
				tien.setGiaTri(tien.getLoaiTien().getBanRa() * tien.getSoTien());
				taiKhoan.setSoTien(tien);

				chungTu.themTaiKhoan(taiKhoan);
				taiKhoan.setChungTu(chungTu);

				NghiepVuKeToan nghiepVuKeToan = new NghiepVuKeToan();
				nghiepVuKeToan.setChungTu(chungTu);
				if (taiKhoan.getSoDu() == LoaiTaiKhoan.NO) {
					nghiepVuKeToan.setTaiKhoanNo(taiKhoan);
					nghiepVuKeToan.setTaiKhoanCo(null);
				} else {
					nghiepVuKeToan.setTaiKhoanNo(null);
					nghiepVuKeToan.setTaiKhoanCo(taiKhoan);
				}

				return nghiepVuKeToan;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public List<TaiKhoan> danhSachTaiKhoanKeToanTheoLoaiTaiKhoan(String maTk, int soDu, Date dau, Date cuoi) {
		String query = DANH_SACH_TKKT_THEO_DIEU_KIEN;

		logger.info("Danh sách tài khoản kế toán theo loại tài khoản: '" + maTk + "' ...");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		String batDau = sdf.format(dau);
		String ketThuc = sdf.format(cuoi);
		logger.info("Từ " + batDau + " đến " + ketThuc);

		query = query.replaceAll("\\$MA_TK\\$", maTk);
		logger.info(query);

		Object[] objs = { batDau, ketThuc, soDu };
		List<TaiKhoan> taiKhoanDs = jdbcTmpl.query(query, objs, new TaiKhoanMapper());
		return taiKhoanDs;
	}

	public class TaiKhoanMapper implements RowMapper<TaiKhoan> {
		public TaiKhoan mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				ChungTu chungTu = new ChungTu();
				chungTu.setMaCt(rs.getInt("MA_CT"));
				chungTu.setSoCt(rs.getInt("SO_CT"));
				chungTu.setLoaiCt(rs.getString("LOAI_CT"));
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
				taiKhoan.setSoDu(rs.getInt("SO_DU"));
				taiKhoan.setLyDo(rs.getString("LY_DO"));

				LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
				loaiTaiKhoan.setMaTk(rs.getString("MA_TK"));
				loaiTaiKhoan.setTenTk(rs.getString("TEN_TK"));
				taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);

				BalanceAssetItem bai = new BalanceAssetItem();
				bai.setAssetCode(rs.getString("ASSET_CODE"));
				taiKhoan.setBai(bai);

				Tien tien = new Tien();
				tien.setSoTien(rs.getDouble("SO_TIEN"));
				tien.setLoaiTien(loaiTien);
				tien.setGiaTri(tien.getLoaiTien().getBanRa() * tien.getSoTien());
				taiKhoan.setSoTien(tien);

				chungTu.themTaiKhoan(taiKhoan);
				taiKhoan.setChungTu(chungTu);

				return taiKhoan;
			} catch (Exception e) {
				return null;
			}
		}
	}
}
