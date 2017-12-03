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

	@Value("${DANH_SACH_CHUNG_TU_NHAT_KY_CHUNG}")
	private String DANH_SACH_CHUNG_TU_NHAT_KY_CHUNG;

	@Value("${DANH_SACH_CHUNG_TU_NHAT_KY_CHUNG_THEO_DIEU_KIEN}")
	private String DANH_SACH_CHUNG_TU_NHAT_KY_CHUNG_THEO_DIEU_KIEN;

	@Value("${DANH_SACH_CHUNG_TU_THEO_MA_TAI_KHOAN}")
	private String DANH_SACH_CHUNG_TU_THEO_MA_TAI_KHOAN;

	@Value("${DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_MA_TAI_KHOAN}")
	private String DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_MA_TAI_KHOAN;

	@Value("${DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_DIEU_KIEN}")
	private String DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_DIEU_KIEN;

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
	public List<ChungTu> danhSachChungTu() {
		String query = DANH_SACH_CHUNG_TU_NHAT_KY_CHUNG;

		logger.info("Danh sách tất cả chứng từ ...");
		logger.info(query);

		List<ChungTu> chungTuDs = jdbcTmpl.query(query, new ChungTuMapper());

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

	@Override
	public List<ChungTu> danhSachChungTuTheoLoaiTaiKhoan(String maTk) {
		String query = DANH_SACH_CHUNG_TU_THEO_MA_TAI_KHOAN;

		logger.info("Danh sách chứng từ theo loại tài khoản: '" + maTk + "' ...");
		logger.info(query);

		Object[] objs = { maTk, maTk, maTk };
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

				LoaiTien loaiTien = new LoaiTien();
				loaiTien.setMaLt(rs.getString("LOAI_TIEN"));
				loaiTien.setTenLt(rs.getString("TEN_NT"));
				loaiTien.setBanRa(rs.getDouble("TY_GIA"));
				Tien tien = new Tien();
				tien.setTien(loaiTien);
				tien.setSoTien(rs.getDouble("TONG_SO_TIEN"));
				tien.setGiaTri(loaiTien.getBanRa() * tien.getSoTien());
				chungTu.setSoTien(tien);

				DoiTuong doiTuong = new DoiTuong();
				doiTuong.setMaDt(rs.getInt("MA_DT"));
				doiTuong.setTenDt(rs.getString("TEN_DT"));
				doiTuong.setLoaiDt(rs.getInt("LOAI_DT"));
				doiTuong.setDiaChi(rs.getString("DIA_CHI"));
				doiTuong.setMaThue(rs.getString("MA_THUE"));
				doiTuong.setNguoiNop(rs.getString("NGUOI_NOP"));
				chungTu.setDoiTuong(doiTuong);

				TaiKhoan taiKhoan = new TaiKhoan();
				LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
				loaiTaiKhoan.setMaTk(rs.getString("MA_TK"));
				loaiTaiKhoan.setTenTk(rs.getString("TEN_TK"));
				taiKhoan.setTaiKhoan(loaiTaiKhoan);
				taiKhoan.setSoTien(rs.getDouble("SO_TIEN"));
				taiKhoan.setGhiNo(rs.getInt("SO_DU"));
				taiKhoan.setLyDo(rs.getString("TK_LY_DO"));
				chungTu.themTaiKhoan(taiKhoan);
				taiKhoan.setChungTu(chungTu);

				return chungTu;
			} catch (Exception e) {
				return null;
			}
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
				Tien tien = new Tien();
				tien.setTien(loaiTien);
				tien.setSoTien(rs.getDouble("TONG_SO_TIEN"));
				tien.setGiaTri(loaiTien.getBanRa() * tien.getSoTien());
				chungTu.setSoTien(tien);

				DoiTuong doiTuong = new DoiTuong();
				doiTuong.setMaDt(rs.getInt("MA_DT"));
				doiTuong.setTenDt(rs.getString("TEN_DT"));
				doiTuong.setLoaiDt(rs.getInt("LOAI_DT"));
				doiTuong.setDiaChi(rs.getString("DIA_CHI"));
				doiTuong.setMaThue(rs.getString("MA_THUE"));
				doiTuong.setNguoiNop(rs.getString("NGUOI_NOP"));
				chungTu.setDoiTuong(doiTuong);

				TaiKhoan taiKhoanNo = new TaiKhoan();
				LoaiTaiKhoan loaiTaiKhoanNo = new LoaiTaiKhoan();
				loaiTaiKhoanNo.setMaTk(rs.getString("MA_TK_NO"));
				loaiTaiKhoanNo.setTenTk(rs.getString("TEN_TK_NO"));
				taiKhoanNo.setTaiKhoan(loaiTaiKhoanNo);
				taiKhoanNo.setSoTien(rs.getDouble("SO_TIEN_NO"));
				taiKhoanNo.setGhiNo(LoaiTaiKhoan.NO);
				taiKhoanNo.setLyDo(rs.getString("LY_DO_NO"));
				chungTu.themTaiKhoan(taiKhoanNo);
				taiKhoanNo.setChungTu(chungTu);

				TaiKhoan taiKhoanCo = new TaiKhoan();
				LoaiTaiKhoan loaiTaiKhoanCo = new LoaiTaiKhoan();
				loaiTaiKhoanCo.setMaTk(rs.getString("MA_TK_CO"));
				loaiTaiKhoanCo.setTenTk(rs.getString("TEN_TK_CO"));
				taiKhoanCo.setTaiKhoan(loaiTaiKhoanCo);
				taiKhoanCo.setSoTien(rs.getDouble("SO_TIEN_CO"));
				taiKhoanCo.setGhiNo(LoaiTaiKhoan.CO);
				taiKhoanCo.setLyDo(rs.getString("LY_DO_CO"));
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
				Tien tien = new Tien();
				tien.setTien(loaiTien);
				tien.setSoTien(rs.getDouble("TONG_SO_TIEN"));
				tien.setGiaTri(loaiTien.getBanRa() * tien.getSoTien());
				chungTu.setSoTien(tien);

				TaiKhoan taiKhoan = new TaiKhoan();
				LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
				loaiTaiKhoan.setMaTk(rs.getString("MA_TK"));
				loaiTaiKhoan.setTenTk(rs.getString("TEN_TK"));
				taiKhoan.setTaiKhoan(loaiTaiKhoan);
				BalanceAssetItem bai = new BalanceAssetItem();
				bai.setAssetCode(rs.getString("ASSET_CODE"));
				taiKhoan.setBai(bai);
				taiKhoan.setSoTien(rs.getDouble("SO_TIEN"));
				taiKhoan.setGhiNo(rs.getInt("SO_DU"));
				taiKhoan.setLyDo(rs.getString("LY_DO"));
				chungTu.themTaiKhoan(taiKhoan);
				taiKhoan.setChungTu(chungTu);

				return taiKhoan;
			} catch (Exception e) {
				return null;
			}
		}
	}
}
