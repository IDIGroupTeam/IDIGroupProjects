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
import com.idi.finance.bean.Tien;
import com.idi.finance.bean.bctc.DuLieuKeToan;
import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.KetChuyenButToan;
import com.idi.finance.bean.doituong.DoiTuong;
import com.idi.finance.bean.hanghoa.DonVi;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.soketoan.NghiepVuKeToan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.bean.taikhoan.TaiKhoan;
import com.idi.finance.dao.SoKeToanDAO;
import com.idi.finance.utils.Utils;

public class SoKeToanDAOImpl implements SoKeToanDAO {
	private static final Logger logger = Logger.getLogger(SoKeToanDAOImpl.class);

	@Value("${DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_MA_TAI_KHOAN}")
	private String DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_MA_TAI_KHOAN;

	@Value("${DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_DIEU_KIEN}")
	private String DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_DIEU_KIEN;

	@Value("${DANH_SACH_NGHIEP_VU_KE_TOAN_KTTH_THEO_DIEU_KIEN}")
	private String DANH_SACH_NGHIEP_VU_KE_TOAN_KTTH_THEO_DIEU_KIEN;

	@Value("${DANH_SACH_NGHIEP_VU_KE_TOAN_KHO_THEO_DIEU_KIEN}")
	private String DANH_SACH_NGHIEP_VU_KE_TOAN_KHO_THEO_DIEU_KIEN;

	@Value("${DANH_SACH_NGHIEP_VU_KE_TOAN_KHO_THEO_MA_HH}")
	private String DANH_SACH_NGHIEP_VU_KE_TOAN_KHO_THEO_MA_HH;

	@Value("${DANH_SACH_NGHIEP_VU_KE_TOAN_KHO_THEO_MA_HH_MA_KHOS}")
	private String DANH_SACH_NGHIEP_VU_KE_TOAN_KHO_THEO_MA_HH_MA_KHOS;

	@Value("${DANH_SACH_NGHIEP_VU_KE_TOAN_KC_THEO_DIEU_KIEN}")
	private String DANH_SACH_NGHIEP_VU_KE_TOAN_KC_THEO_DIEU_KIEN;

	@Value("${TONG_PHAT_SINH}")
	private String TONG_PHAT_SINH;

	@Value("${TONG_PHAT_SINH_DOI_TUONG}")
	private String TONG_PHAT_SINH_DOI_TUONG;

	@Value("${TONG_PHAT_SINH_DOI_TUONG_KTTH}")
	private String TONG_PHAT_SINH_DOI_TUONG_KTTH;

	@Value("${TONG_PHAT_SINH_TOAN_BO}")
	private String TONG_PHAT_SINH_TOAN_BO;

	@Value("${DANH_SACH_TONG_HOP_CONG_NO}")
	private String DANH_SACH_TONG_HOP_CONG_NO;

	@Value("${DANH_SACH_TONG_HOP_CONG_NO_KTTH}")
	private String DANH_SACH_TONG_HOP_CONG_NO_KTTH;

	@Value("${DANH_SACH_TONG_HOP_NXT}")
	private String DANH_SACH_TONG_HOP_NXT;

	@Value("${DANH_SACH_TONG_HOP_NXT_MA_HH}")
	private String DANH_SACH_TONG_HOP_NXT_MA_HH;

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public double tongPhatSinh(String maTk, int soDu, Date dau, Date cuoi) {
		if (maTk == null || maTk.trim().equals("")) {
			return 0;
		}

		String query = TONG_PHAT_SINH;
		query = query.replaceAll("\\$MA_TK\\$", maTk);

		logger.info("Tổng phát sinh");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		if (dau != null) {
			String batDau = sdf.format(dau);
			logger.info("Từ: " + batDau);
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "AND CT.NGAY_HT >= '" + batDau + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "");
		}

		if (cuoi != null) {
			String ketThuc = sdf.format(cuoi);
			logger.info("Đến: " + ketThuc);
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "AND CT.NGAY_HT <= '" + ketThuc + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "");
		}

		logger.info(query);
		logger.info("maTk " + maTk);

		try {
			Object[] objs = { soDu };
			double result = jdbcTmpl.queryForObject(query, objs, Double.class);
			logger.info("Kết quả: " + result);
			return result;
		} catch (Exception e) {
			logger.info("Kết quả: " + 0);
			return 0;
		}
	}

	@Override
	public List<DuLieuKeToan> danhSachTongPhatSinhDoiTuong(String maTk, Date dau, Date cuoi) {
		if (maTk == null || maTk.trim().equals("")) {
			return null;
		}

		String query = TONG_PHAT_SINH_DOI_TUONG;
		query = query.replaceAll("\\$MA_TK\\$", maTk);

		logger.info("Danh sách tổng phát sinh nợ/có theo đối tượng");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		if (dau != null) {
			String batDau = sdf.format(dau);
			logger.info("Từ: " + batDau);
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "AND CT.NGAY_HT >= '" + batDau + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "");
		}

		if (cuoi != null) {
			String ketThuc = sdf.format(cuoi);
			logger.info("Đến: " + ketThuc);
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "AND CT.NGAY_HT <= '" + ketThuc + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "");
		}

		logger.info("maTk " + maTk);
		logger.info(query);

		List<DuLieuKeToan> duLieuKeToanDs = jdbcTmpl.query(query, new DuLieuKeToanDoiTuongMapper());
		if (duLieuKeToanDs != null) {
			Iterator<DuLieuKeToan> iter = duLieuKeToanDs.iterator();
			while (iter.hasNext()) {
				DuLieuKeToan duLieuKeToan = iter.next();
				duLieuKeToan.getLoaiTaiKhoan().setMaTk(maTk.trim());
			}
		}

		List<DuLieuKeToan> ketQua = null;
		// Ghép dữ liệu tại đây
		if (duLieuKeToanDs != null) {
			ketQua = new ArrayList<>();
			Iterator<DuLieuKeToan> iter = duLieuKeToanDs.iterator();
			while (iter.hasNext()) {
				DuLieuKeToan duLieuKeToan = iter.next();

				int pos = ketQua.indexOf(duLieuKeToan);
				if (pos > -1) {
					DuLieuKeToan duLieuKeToanTmpl = ketQua.get(pos);
					duLieuKeToanTmpl.tron(duLieuKeToan);
				} else {
					ketQua.add(duLieuKeToan);
				}
			}
		}

		return ketQua;
	}

	@Override
	public List<DuLieuKeToan> danhSachTongPhatSinhDoiTuongKtth(String maTk, Date dau, Date cuoi) {
		if (maTk == null || maTk.trim().equals("")) {
			return null;
		}

		String query = TONG_PHAT_SINH_DOI_TUONG_KTTH;
		query = query.replaceAll("\\$MA_TK\\$", maTk);

		logger.info("Danh sách tổng phát sinh nợ/có ktth theo đối tượng");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		if (dau != null) {
			String batDau = sdf.format(dau);
			logger.info("Từ: " + batDau);
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "AND CT.NGAY_HT >= '" + batDau + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "");
		}

		if (cuoi != null) {
			String ketThuc = sdf.format(cuoi);
			logger.info("Đến: " + ketThuc);
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "AND CT.NGAY_HT <= '" + ketThuc + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "");
		}

		logger.info("maTk " + maTk);
		logger.info(query);

		List<DuLieuKeToan> duLieuKeToanDs = jdbcTmpl.query(query, new DuLieuKeToanDoiTuongMapper());
		if (duLieuKeToanDs != null) {
			Iterator<DuLieuKeToan> iter = duLieuKeToanDs.iterator();
			while (iter.hasNext()) {
				DuLieuKeToan duLieuKeToan = iter.next();
				duLieuKeToan.getLoaiTaiKhoan().setMaTk(maTk.trim());
			}
		}

		List<DuLieuKeToan> ketQua = null;
		// Ghép dữ liệu tại đây
		if (duLieuKeToanDs != null) {
			ketQua = new ArrayList<>();
			Iterator<DuLieuKeToan> iter = duLieuKeToanDs.iterator();
			while (iter.hasNext()) {
				DuLieuKeToan duLieuKeToan = iter.next();

				int pos = ketQua.indexOf(duLieuKeToan);
				if (pos > -1) {
					DuLieuKeToan duLieuKeToanTmpl = ketQua.get(pos);
					duLieuKeToanTmpl.tron(duLieuKeToan);
				} else {
					ketQua.add(duLieuKeToan);
				}
			}
		}

		return ketQua;

	}

	@Override
	public List<NghiepVuKeToan> danhSachNghiepVuKeToanTheoLoaiTaiKhoan(String maTk) {
		if (maTk == null) {
			return null;
		}
		String query = DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_MA_TAI_KHOAN;

		logger.info("Danh sách nghiệp vụ kế toán theo loại tài khoản: '" + maTk + "' ...");
		query = query.replaceAll("\\?", maTk);
		logger.info(query);

		List<NghiepVuKeToan> nghiepVuKeToanDs = jdbcTmpl.query(query, new NghiepVuKeToanMapper());
		if (nghiepVuKeToanDs != null) {
			logger.info("Kết quả: " + nghiepVuKeToanDs.size());
		} else {
			logger.info("Kết quả: " + 0);
		}

		return nghiepVuKeToanDs;
	}

	@Override
	public List<NghiepVuKeToan> danhSachNghiepVuKeToanTheoLoaiTaiKhoan(String maTk, Date dau, Date cuoi) {
		if (maTk == null) {
			return null;
		}
		String query = DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_DIEU_KIEN;
		query = query.replaceAll("\\$MA_TK\\$", maTk);

		logger.info("Danh sách nghiệp vụ kế toán theo loại tài khoản: '" + maTk + "' ...");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		if (dau != null) {
			String batDau = sdf.format(dau);
			logger.info("Từ " + batDau);
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "AND CT.NGAY_HT >= '" + batDau + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "");
		}

		if (cuoi != null) {
			String ketThuc = sdf.format(cuoi);
			logger.info("Đến " + ketThuc);
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "AND CT.NGAY_HT <= '" + ketThuc + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "");
		}

		logger.info(query);
		List<NghiepVuKeToan> nghiepVuKeToanDs = jdbcTmpl.query(query, new NghiepVuKeToanMapper());
		if (nghiepVuKeToanDs != null) {
			logger.info("Kết quả: " + nghiepVuKeToanDs.size());
		} else {
			logger.info("Kết quả: " + 0);
		}

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
				chungTu.setNgayTt(rs.getDate("NGAY_TT"));

				LoaiTien loaiTien = new LoaiTien();
				loaiTien.setMaLt(rs.getString("LOAI_TIEN"));
				loaiTien.setTenLt(rs.getString("TEN_NT"));
				loaiTien.setBanRa(rs.getDouble("TY_GIA"));
				chungTu.setLoaiTien(loaiTien);

				DoiTuong doiTuong = new DoiTuong();
				doiTuong.setMaDt(rs.getInt("MA_DT"));
				doiTuong.setKhDt(rs.getString("KH_DT"));
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
				loaiTaiKhoanNo.setSoDu(rs.getInt("SO_DU_GOC_NO"));
				loaiTaiKhoanNo.setLuongTinh(rs.getBoolean("LUONG_TINH_NO"));
				taiKhoanNo.setLoaiTaiKhoan(loaiTaiKhoanNo);

				Tien tienNo = new Tien();
				tienNo.setLoaiTien(loaiTien);
				tienNo.setGiaTri(rs.getDouble("SO_TIEN_NO"));
				tienNo.setSoTien(tienNo.getGiaTri() / tienNo.getLoaiTien().getBanRa());
				taiKhoanNo.setSoTien(tienNo);

				chungTu.themTaiKhoan(taiKhoanNo);
				taiKhoanNo.setChungTu(chungTu);

				TaiKhoan taiKhoanCo = new TaiKhoan();
				taiKhoanCo.setSoDu(LoaiTaiKhoan.CO);
				taiKhoanCo.setLyDo(rs.getString("LY_DO_CO"));

				LoaiTaiKhoan loaiTaiKhoanCo = new LoaiTaiKhoan();
				loaiTaiKhoanCo.setMaTk(rs.getString("MA_TK_CO"));
				loaiTaiKhoanCo.setTenTk(rs.getString("TEN_TK_CO"));
				loaiTaiKhoanCo.setSoDu(rs.getInt("SO_DU_GOC_CO"));
				loaiTaiKhoanCo.setLuongTinh(rs.getBoolean("LUONG_TINH_CO"));
				taiKhoanCo.setLoaiTaiKhoan(loaiTaiKhoanCo);

				Tien tienCo = new Tien();
				tienCo.setLoaiTien(loaiTien);
				tienCo.setGiaTri(rs.getDouble("SO_TIEN_CO"));
				tienCo.setSoTien(tienCo.getGiaTri() / tienCo.getLoaiTien().getBanRa());
				taiKhoanCo.setSoTien(tienCo);

				chungTu.themTaiKhoan(taiKhoanCo);
				taiKhoanCo.setChungTu(chungTu);

				NghiepVuKeToan nghiepVuKeToan = new NghiepVuKeToan();
				nghiepVuKeToan.setChungTu(chungTu);
				nghiepVuKeToan.setTaiKhoanNo(taiKhoanNo);
				nghiepVuKeToan.setTaiKhoanCo(taiKhoanCo);

				logger.info(nghiepVuKeToan);

				return nghiepVuKeToan;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public List<NghiepVuKeToan> danhSachNghiepVuKeToanKtthTheoLoaiTaiKhoan(String maTk, Date dau, Date cuoi) {
		if (maTk == null) {
			return null;
		}
		String query = DANH_SACH_NGHIEP_VU_KE_TOAN_KTTH_THEO_DIEU_KIEN;
		query = query.replaceAll("\\$MA_TK\\$", maTk);

		logger.info("Danh sách nghiệp vụ kế toán tổng hợp theo loại tài khoản: '" + maTk + "' ...");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		if (dau != null) {
			String batDau = sdf.format(dau);
			logger.info("Từ " + batDau);
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "AND CT.NGAY_HT >= '" + batDau + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "");
		}

		if (cuoi != null) {
			String ketThuc = sdf.format(cuoi);
			logger.info("Đến " + ketThuc);
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "AND CT.NGAY_HT <= '" + ketThuc + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "");
		}

		logger.info(query);
		List<NghiepVuKeToan> nghiepVuKeToanDs = jdbcTmpl.query(query, new NghiepVuKeToanKtthMapper());
		if (nghiepVuKeToanDs != null) {
			logger.info("Kết quả: " + nghiepVuKeToanDs.size());
		} else {
			logger.info("Kết quả: " + 0);
		}

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
				chungTu.setNgayTt(rs.getDate("NGAY_TT"));

				LoaiTien loaiTien = new LoaiTien();
				loaiTien.setMaLt(rs.getString("LOAI_TIEN"));
				loaiTien.setTenLt(rs.getString("TEN_NT"));
				loaiTien.setBanRa(rs.getDouble("TY_GIA"));
				chungTu.setLoaiTien(loaiTien);

				TaiKhoan taiKhoanNo = new TaiKhoan();
				taiKhoanNo.setSoDu(LoaiTaiKhoan.NO);
				taiKhoanNo.setLyDo(rs.getString("LY_DO_NO"));

				DoiTuong doiTuongNo = new DoiTuong();
				doiTuongNo.setMaDt(rs.getInt("MA_DT_NO"));
				doiTuongNo.setKhDt(rs.getString("KH_DT_NO"));
				doiTuongNo.setTenDt(rs.getString("TEN_DT_NO"));
				doiTuongNo.setLoaiDt(rs.getInt("LOAI_DT_NO"));
				doiTuongNo.setDiaChi(rs.getString("DIA_CHI_NO"));
				doiTuongNo.setMaThue(rs.getString("MA_THUE_NO"));
				taiKhoanNo.setDoiTuong(doiTuongNo);

				LoaiTaiKhoan loaiTaiKhoanNo = new LoaiTaiKhoan();
				loaiTaiKhoanNo.setMaTk(rs.getString("MA_TK_NO"));
				loaiTaiKhoanNo.setTenTk(rs.getString("TEN_TK_NO"));
				loaiTaiKhoanNo.setSoDu(rs.getInt("SO_DU_GOC_NO"));
				loaiTaiKhoanNo.setLuongTinh(rs.getBoolean("LUONG_TINH_NO"));
				taiKhoanNo.setLoaiTaiKhoan(loaiTaiKhoanNo);

				Tien tienNo = new Tien();
				tienNo.setLoaiTien(loaiTien);
				tienNo.setGiaTri(rs.getDouble("SO_TIEN_NO"));
				tienNo.setSoTien(tienNo.getGiaTri() / tienNo.getLoaiTien().getBanRa());
				taiKhoanNo.setSoTien(tienNo);

				chungTu.themTaiKhoanKtth(taiKhoanNo);
				taiKhoanNo.setChungTu(chungTu);

				TaiKhoan taiKhoanCo = new TaiKhoan();
				taiKhoanCo.setSoDu(LoaiTaiKhoan.CO);
				taiKhoanCo.setLyDo(rs.getString("LY_DO_CO"));

				DoiTuong doiTuongCo = new DoiTuong();
				doiTuongCo.setMaDt(rs.getInt("MA_DT_CO"));
				doiTuongCo.setKhDt(rs.getString("KH_DT_CO"));
				doiTuongCo.setTenDt(rs.getString("TEN_DT_CO"));
				doiTuongCo.setLoaiDt(rs.getInt("LOAI_DT_CO"));
				doiTuongCo.setDiaChi(rs.getString("DIA_CHI_CO"));
				doiTuongCo.setMaThue(rs.getString("MA_THUE_CO"));
				taiKhoanCo.setDoiTuong(doiTuongCo);

				LoaiTaiKhoan loaiTaiKhoanCo = new LoaiTaiKhoan();
				loaiTaiKhoanCo.setMaTk(rs.getString("MA_TK_CO"));
				loaiTaiKhoanCo.setTenTk(rs.getString("TEN_TK_CO"));
				loaiTaiKhoanCo.setSoDu(rs.getInt("SO_DU_GOC_CO"));
				loaiTaiKhoanCo.setLuongTinh(rs.getBoolean("LUONG_TINH_CO"));
				taiKhoanCo.setLoaiTaiKhoan(loaiTaiKhoanCo);

				Tien tienCo = new Tien();
				tienCo.setLoaiTien(loaiTien);
				tienCo.setGiaTri(rs.getDouble("SO_TIEN_CO"));
				tienCo.setSoTien(tienCo.getGiaTri() / tienCo.getLoaiTien().getBanRa());
				taiKhoanCo.setSoTien(tienCo);

				chungTu.themTaiKhoanKtth(taiKhoanCo);
				taiKhoanCo.setChungTu(chungTu);

				NghiepVuKeToan nghiepVuKeToan = new NghiepVuKeToan();
				nghiepVuKeToan.setChungTu(chungTu);
				nghiepVuKeToan.setTaiKhoanNo(taiKhoanNo);
				nghiepVuKeToan.setTaiKhoanCo(taiKhoanCo);

				logger.info(nghiepVuKeToan);

				return nghiepVuKeToan;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	@Override
	public List<NghiepVuKeToan> danhSachNghiepVuKeToanKhoTheoLoaiTaiKhoan(String maTk, Date dau, Date cuoi) {
		if (maTk == null) {
			return null;
		}

		String query = DANH_SACH_NGHIEP_VU_KE_TOAN_KHO_THEO_DIEU_KIEN;
		query = query.replaceAll("\\$MA_TK\\$", maTk);

		logger.info("Danh sách nghiệp vụ kế toán kho theo loại tài khoản: '" + maTk + "' ...");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		if (dau != null) {
			String batDau = sdf.format(dau);
			logger.info("Từ " + batDau);
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "AND CT.NGAY_HT >= '" + batDau + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "");
		}

		if (cuoi != null) {
			String ketThuc = sdf.format(cuoi);
			logger.info("Đến " + ketThuc);
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "AND CT.NGAY_HT <= '" + ketThuc + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "");
		}

		logger.info(query);
		List<NghiepVuKeToan> nghiepVuKeToanDs = jdbcTmpl.query(query, new NghiepVuKeToanKhoMapper());
		if (nghiepVuKeToanDs != null) {
			logger.info("Kết quả: " + nghiepVuKeToanDs.size());
		} else {
			logger.info("Kết quả: " + 0);
		}

		return nghiepVuKeToanDs;
	}

	@Override
	public List<NghiepVuKeToan> danhSachNghiepVuKeToanKhoTheoLoaiTaiKhoan(String maTk, int maHh, Date dau, Date cuoi) {
		if (maTk == null) {
			return null;
		}

		String query = DANH_SACH_NGHIEP_VU_KE_TOAN_KHO_THEO_MA_HH;
		query = query.replaceAll("\\$MA_TK\\$", maTk);

		logger.info("Danh sách nghiệp vụ kế toán kho theo loại tài khoản, mã hàng hóa: '" + maTk + "' ...");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		if (dau != null) {
			String batDau = sdf.format(dau);
			logger.info("Từ " + batDau);
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "AND CT.NGAY_HT >= '" + batDau + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "");
		}

		if (cuoi != null) {
			String ketThuc = sdf.format(cuoi);
			logger.info("Đến " + ketThuc);
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "AND CT.NGAY_HT <= '" + ketThuc + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "");
		}

		logger.info(query);
		logger.info("Mã tài khoản: " + maTk);
		logger.info("Mã hàng hóa: " + maHh);

		Object[] objs = { maHh, maHh, maHh };
		List<NghiepVuKeToan> nghiepVuKeToanDs = jdbcTmpl.query(query, objs, new NghiepVuKeToanKhoMapper());
		if (nghiepVuKeToanDs != null) {
			logger.info("Kết quả: " + nghiepVuKeToanDs.size());
		} else {
			logger.info("Kết quả: " + 0);
		}

		return nghiepVuKeToanDs;
	}

	@Override
	public List<NghiepVuKeToan> danhSachNghiepVuKeToanKhoTheoLoaiTaiKhoan(String maTk, int maHh, List<Integer> maKhoDs,
			Date dau, Date cuoi) {
		if (maTk == null) {
			return null;
		}

		String query = DANH_SACH_NGHIEP_VU_KE_TOAN_KHO_THEO_MA_HH_MA_KHOS;
		query = query.replaceAll("\\$MA_TK\\$", maTk);

		if (maKhoDs != null) {
			StringBuffer dieuKienKhoBuff = new StringBuffer("AND CTHH.MA_KHO IN (");

			for (Iterator<Integer> iter = maKhoDs.iterator(); iter.hasNext();) {
				Integer maKho = iter.next();

				dieuKienKhoBuff = dieuKienKhoBuff.append(maKho);
				if (iter.hasNext()) {
					dieuKienKhoBuff = dieuKienKhoBuff.append(", ");
				}
			}
			dieuKienKhoBuff = dieuKienKhoBuff.append(")");

			query = query.replace("$DIEU_KIEN_MA_KHO$", dieuKienKhoBuff.toString());
		} else {
			query = query.replace("$DIEU_KIEN_MA_KHO$", "");
		}

		logger.info(
				"Danh sách nghiệp vụ kế toán kho theo loại tài khoản, mã hàng hóa, danh sách kho: '" + maTk + "' ...");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		if (dau != null) {
			String batDau = sdf.format(dau);
			logger.info("Từ " + batDau);
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "AND CT.NGAY_HT >= '" + batDau + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "");
		}

		if (cuoi != null) {
			String ketThuc = sdf.format(cuoi);
			logger.info("Đến " + ketThuc);
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "AND CT.NGAY_HT <= '" + ketThuc + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "");
		}

		logger.info(query);
		logger.info("Mã tài khoản: " + maTk);
		logger.info("Mã hàng hóa: " + maHh);
		logger.info("Danh sách kho: " + maKhoDs);

		Object[] objs = { maHh, maHh, maHh };
		List<NghiepVuKeToan> nghiepVuKeToanDs = jdbcTmpl.query(query, objs, new NghiepVuKeToanKhoMapper());
		if (nghiepVuKeToanDs != null) {
			logger.info("Kết quả: " + nghiepVuKeToanDs.size());
		} else {
			logger.info("Kết quả: " + 0);
		}

		return nghiepVuKeToanDs;
	}

	public class NghiepVuKeToanKhoMapper implements RowMapper<NghiepVuKeToan> {
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
				chungTu.setNgayTt(rs.getDate("NGAY_TT"));
				chungTu.setChieu(rs.getInt("CHIEU"));

				LoaiTien loaiTien = new LoaiTien();
				loaiTien.setMaLt(rs.getString("LOAI_TIEN"));
				loaiTien.setTenLt(rs.getString("TEN_NT"));
				loaiTien.setBanRa(rs.getDouble("TY_GIA"));
				chungTu.setLoaiTien(loaiTien);

				DoiTuong doiTuong = new DoiTuong();
				doiTuong.setMaDt(rs.getInt("MA_DT"));
				doiTuong.setKhDt(rs.getString("KH_DT"));
				doiTuong.setTenDt(rs.getString("TEN_DT"));
				doiTuong.setLoaiDt(rs.getInt("LOAI_DT"));
				doiTuong.setDiaChi(rs.getString("DIA_CHI"));
				doiTuong.setMaThue(rs.getString("MA_THUE"));
				doiTuong.setNguoiNop(rs.getString("NGUOI_NOP"));
				chungTu.setDoiTuong(doiTuong);

				HangHoa hangHoa = new HangHoa();
				hangHoa.setMaHh(rs.getInt("MA_HH"));
				hangHoa.setSoLuong(rs.getDouble("SO_LUONG_TN"));

				Tien donGia = new Tien();
				donGia.setLoaiTien(loaiTien);
				donGia.setGiaTri(rs.getDouble("DON_GIA"));
				donGia.setSoTien(Utils.divide(donGia.getGiaTri(), loaiTien.getBanRa()));
				hangHoa.setDonGia(donGia);

				Tien giaKho = new Tien();
				giaKho.setLoaiTien(loaiTien);
				giaKho.setMaGia(rs.getInt("MA_GIA"));
				giaKho.setGiaTri(rs.getDouble("GIA_KHO"));
				giaKho.setSoTien(Utils.divide(giaKho.getGiaTri(), loaiTien.getBanRa()));
				hangHoa.setGiaKho(giaKho);

				TaiKhoan taiKhoanNo = new TaiKhoan();
				taiKhoanNo.setSoDu(LoaiTaiKhoan.NO);
				taiKhoanNo.setLyDo(rs.getString("LY_DO_NO"));

				LoaiTaiKhoan loaiTaiKhoanNo = new LoaiTaiKhoan();
				loaiTaiKhoanNo.setMaTk(rs.getString("MA_TK_NO"));
				loaiTaiKhoanNo.setTenTk(rs.getString("TEN_TK_NO"));
				loaiTaiKhoanNo.setSoDu(rs.getInt("SO_DU_GOC_NO"));
				loaiTaiKhoanNo.setLuongTinh(rs.getBoolean("LUONG_TINH_NO"));
				taiKhoanNo.setLoaiTaiKhoan(loaiTaiKhoanNo);

				Tien tienNo = new Tien();
				tienNo.setLoaiTien(loaiTien);
				tienNo.setGiaTri(rs.getDouble("SO_TIEN_NO"));
				tienNo.setSoTien(tienNo.getGiaTri() / tienNo.getLoaiTien().getBanRa());
				taiKhoanNo.setSoTien(tienNo);

				chungTu.themTaiKhoan(taiKhoanNo);
				taiKhoanNo.setChungTu(chungTu);

				TaiKhoan taiKhoanCo = new TaiKhoan();
				taiKhoanCo.setSoDu(LoaiTaiKhoan.CO);
				taiKhoanCo.setLyDo(rs.getString("LY_DO_CO"));

				LoaiTaiKhoan loaiTaiKhoanCo = new LoaiTaiKhoan();
				loaiTaiKhoanCo.setMaTk(rs.getString("MA_TK_CO"));
				loaiTaiKhoanCo.setTenTk(rs.getString("TEN_TK_CO"));
				loaiTaiKhoanCo.setSoDu(rs.getInt("SO_DU_GOC_CO"));
				loaiTaiKhoanCo.setLuongTinh(rs.getBoolean("LUONG_TINH_CO"));
				taiKhoanCo.setLoaiTaiKhoan(loaiTaiKhoanCo);

				Tien tienCo = new Tien();
				tienCo.setLoaiTien(loaiTien);
				tienCo.setGiaTri(rs.getDouble("SO_TIEN_CO"));
				tienCo.setSoTien(tienCo.getGiaTri() / tienCo.getLoaiTien().getBanRa());
				taiKhoanCo.setSoTien(tienCo);

				chungTu.themTaiKhoan(taiKhoanCo);
				taiKhoanCo.setChungTu(chungTu);

				NghiepVuKeToan nghiepVuKeToan = new NghiepVuKeToan();
				nghiepVuKeToan.setChungTu(chungTu);
				nghiepVuKeToan.setHangHoa(hangHoa);
				nghiepVuKeToan.setTaiKhoanNo(taiKhoanNo);
				nghiepVuKeToan.setTaiKhoanCo(taiKhoanCo);

				logger.info(nghiepVuKeToan);

				return nghiepVuKeToan;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	@Override
	public List<NghiepVuKeToan> danhSachNghiepVuKeToanKcTheoLoaiTaiKhoan(String maTk, Date dau, Date cuoi) {
		if (maTk == null) {
			return null;
		}

		String query = DANH_SACH_NGHIEP_VU_KE_TOAN_KC_THEO_DIEU_KIEN;
		query = query.replaceAll("\\$MA_TK\\$", maTk);

		logger.info("Danh sách nghiệp vụ kế toán kết chuyển theo loại tài khoản: '" + maTk + "' ...");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		if (dau != null) {
			String batDau = sdf.format(dau);
			logger.info("Từ " + batDau);
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "AND CT.NGAY_HT >= '" + batDau + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "");
		}

		if (cuoi != null) {
			String ketThuc = sdf.format(cuoi);
			logger.info("Đến " + ketThuc);
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "AND CT.NGAY_HT <= '" + ketThuc + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "");
		}

		logger.info(query);
		List<NghiepVuKeToan> nghiepVuKeToanDs = jdbcTmpl.query(query, new NghiepVuKeToanKcMapper());
		if (nghiepVuKeToanDs != null) {
			logger.info("Kết quả: " + nghiepVuKeToanDs.size());
		} else {
			logger.info("Kết quả: " + 0);
		}

		return nghiepVuKeToanDs;
	}

	public class NghiepVuKeToanKcMapper implements RowMapper<NghiepVuKeToan> {
		public NghiepVuKeToan mapRow(ResultSet rs, int rowNum) throws SQLException {
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

				KetChuyenButToan ketChuyenButToan = new KetChuyenButToan();
				ketChuyenButToan.setMaKc(rs.getInt("MA_KC"));

				TaiKhoan taiKhoanNo = new TaiKhoan();
				taiKhoanNo.setSoDu(LoaiTaiKhoan.NO);
				taiKhoanNo.setLyDo(rs.getString("LY_DO_NO"));

				LoaiTaiKhoan loaiTaiKhoanNo = new LoaiTaiKhoan();
				loaiTaiKhoanNo.setMaTk(rs.getString("MA_TK_NO"));
				loaiTaiKhoanNo.setTenTk(rs.getString("TEN_TK_NO"));
				loaiTaiKhoanNo.setSoDu(rs.getInt("SO_DU_GOC_NO"));
				loaiTaiKhoanNo.setLuongTinh(rs.getBoolean("LUONG_TINH_NO"));
				taiKhoanNo.setLoaiTaiKhoan(loaiTaiKhoanNo);

				Tien tienNo = new Tien();
				tienNo.setLoaiTien(loaiTien);
				tienNo.setGiaTri(rs.getDouble("SO_TIEN_NO"));
				tienNo.setSoTien(tienNo.getGiaTri() / tienNo.getLoaiTien().getBanRa());
				taiKhoanNo.setSoTien(tienNo);

				taiKhoanNo.setChungTu(chungTu);

				TaiKhoan taiKhoanCo = new TaiKhoan();
				taiKhoanCo.setSoDu(LoaiTaiKhoan.CO);
				taiKhoanCo.setLyDo(rs.getString("LY_DO_CO"));

				LoaiTaiKhoan loaiTaiKhoanCo = new LoaiTaiKhoan();
				loaiTaiKhoanCo.setMaTk(rs.getString("MA_TK_CO"));
				loaiTaiKhoanCo.setTenTk(rs.getString("TEN_TK_CO"));
				loaiTaiKhoanCo.setSoDu(rs.getInt("SO_DU_GOC_CO"));
				loaiTaiKhoanCo.setLuongTinh(rs.getBoolean("LUONG_TINH_CO"));
				taiKhoanCo.setLoaiTaiKhoan(loaiTaiKhoanCo);

				Tien tienCo = new Tien();
				tienCo.setLoaiTien(loaiTien);
				tienCo.setGiaTri(rs.getDouble("SO_TIEN_CO"));
				tienCo.setSoTien(tienCo.getGiaTri() / tienCo.getLoaiTien().getBanRa());
				taiKhoanCo.setSoTien(tienCo);

				taiKhoanCo.setChungTu(chungTu);

				NghiepVuKeToan nghiepVuKeToan = new NghiepVuKeToan();
				nghiepVuKeToan.setChungTu(chungTu);
				nghiepVuKeToan.setKetChuyenButToan(ketChuyenButToan);
				nghiepVuKeToan.setTaiKhoanNo(taiKhoanNo);
				nghiepVuKeToan.setTaiKhoanCo(taiKhoanCo);

				logger.info(nghiepVuKeToan);

				return nghiepVuKeToan;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	@Override
	public List<TaiKhoan> tongPhatSinh(Date dau, Date cuoi) {
		String query = TONG_PHAT_SINH_TOAN_BO;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		logger.info("Tổng phát sinh");
		if (dau != null) {
			String batDau = sdf.format(dau);
			logger.info("Từ: " + batDau);
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "AND CT.NGAY_HT >= '" + batDau + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "");
		}

		if (cuoi != null) {
			String ketThuc = sdf.format(cuoi);
			logger.info("Đến: " + ketThuc);
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "AND CT.NGAY_HT <= '" + ketThuc + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "");
		}

		List<TaiKhoan> taiKhoanDs = new ArrayList<>();
		try {
			logger.info(query);
			taiKhoanDs = jdbcTmpl.query(query, new TongPhatSinhMapper());
			if (taiKhoanDs != null) {
				logger.info("Kết quả: " + taiKhoanDs.size());
			} else {
				logger.info("Kết quả: " + 0);
			}
		} catch (Exception e) {

		}

		return taiKhoanDs;
	}

	public class TongPhatSinhMapper implements RowMapper<TaiKhoan> {
		public TaiKhoan mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				TaiKhoan taiKhoan = new TaiKhoan();

				LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
				loaiTaiKhoan.setMaTk(rs.getString("MA_TK"));
				loaiTaiKhoan.setTenTk(rs.getString("TEN_TK"));
				loaiTaiKhoan.setLuongTinh(rs.getBoolean("LUONG_TINH"));

				Tien tien = new Tien();
				tien.setGiaTri(rs.getDouble("SO_TIEN"));

				taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);
				taiKhoan.setSoTien(tien);
				taiKhoan.setSoDu(rs.getInt("SO_DU"));

				logger.info(taiKhoan);

				return taiKhoan;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public List<DuLieuKeToan> danhSachPhatSinhCongNo(String maTk, Date dau, Date cuoi) {
		if (maTk == null) {
			return null;
		}

		String query = DANH_SACH_TONG_HOP_CONG_NO;
		query = query.replaceAll("\\$MA_TK\\$", maTk);

		logger.info("Danh sách tổng hợp công nợ phát sinh theo loại tài khoản: '" + maTk + "' ...");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		if (dau != null) {
			String batDau = sdf.format(dau);
			logger.info("Từ " + batDau);
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "AND CT.NGAY_HT >= '" + batDau + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "");
		}

		if (cuoi != null) {
			String ketThuc = sdf.format(cuoi);
			logger.info("Đến " + ketThuc);
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "AND CT.NGAY_HT <= '" + ketThuc + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "");
		}

		logger.info(query);

		List<DuLieuKeToan> duLieuKeToanDs = jdbcTmpl.query(query, new DuLieuKeToanMapper());
		if (duLieuKeToanDs != null) {
			logger.info("Kết quả: " + duLieuKeToanDs.size());
		} else {
			logger.info("Kết quả: " + 0);
		}

		List<DuLieuKeToan> ketQua = null;
		// Ghép dữ liệu tại đây
		if (duLieuKeToanDs != null) {
			ketQua = new ArrayList<>();
			Iterator<DuLieuKeToan> iter = duLieuKeToanDs.iterator();
			while (iter.hasNext()) {
				DuLieuKeToan duLieuKeToan = iter.next();

				int pos = ketQua.indexOf(duLieuKeToan);
				if (pos > -1) {
					DuLieuKeToan duLieuKeToanTmpl = ketQua.get(pos);
					duLieuKeToanTmpl.tron(duLieuKeToan);
				} else {
					ketQua.add(duLieuKeToan);
				}
			}
			logger.info("Kết quả trả về: " + ketQua.size());
		}

		return ketQua;
	}

	public List<DuLieuKeToan> danhSachPhatSinhCongNoKtth(String maTk, Date dau, Date cuoi) {
		if (maTk == null) {
			return null;
		}

		String query = DANH_SACH_TONG_HOP_CONG_NO_KTTH;
		query = query.replaceAll("\\$MA_TK\\$", maTk);

		logger.info("Danh sách tổng hợp công nợ ktth phát sinh theo loại tài khoản: '" + maTk + "' ...");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		if (dau != null) {
			String batDau = sdf.format(dau);
			logger.info("Từ " + batDau);
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "AND CT.NGAY_HT >= '" + batDau + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "");
		}

		if (cuoi != null) {
			String ketThuc = sdf.format(cuoi);
			logger.info("Đến " + ketThuc);
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "AND CT.NGAY_HT <= '" + ketThuc + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "");
		}

		logger.info(query);

		List<DuLieuKeToan> duLieuKeToanDs = jdbcTmpl.query(query, new DuLieuKeToanMapper());

		List<DuLieuKeToan> ketQua = null;
		// Ghép dữ liệu tại đây
		if (duLieuKeToanDs != null) {
			ketQua = new ArrayList<>();
			Iterator<DuLieuKeToan> iter = duLieuKeToanDs.iterator();
			while (iter.hasNext()) {
				DuLieuKeToan duLieuKeToan = iter.next();

				int pos = ketQua.indexOf(duLieuKeToan);
				if (pos > -1) {
					DuLieuKeToan duLieuKeToanTmpl = ketQua.get(pos);
					duLieuKeToanTmpl.tron(duLieuKeToan);
				} else {
					ketQua.add(duLieuKeToan);
				}
			}
		}

		return ketQua;
	}

	@Override
	public List<DuLieuKeToan> danhSachPhatSinhNxt(String maTk, List<Integer> maKhoDs, Date dau, Date cuoi) {
		if (maTk == null) {
			return null;
		}

		String query = DANH_SACH_TONG_HOP_NXT;
		query = query.replaceAll("\\$MA_TK\\$", maTk);

		if (maKhoDs != null) {
			StringBuffer dieuKienKhoBuff = new StringBuffer("AND CTHH.MA_KHO IN (");

			for (Iterator<Integer> iter = maKhoDs.iterator(); iter.hasNext();) {
				Integer maKho = iter.next();

				dieuKienKhoBuff = dieuKienKhoBuff.append(maKho);
				if (iter.hasNext()) {
					dieuKienKhoBuff = dieuKienKhoBuff.append(", ");
				}
			}
			dieuKienKhoBuff = dieuKienKhoBuff.append(")");

			query = query.replace("$DIEU_KIEN_MA_KHO$", dieuKienKhoBuff.toString());
		} else {
			query = query.replace("$DIEU_KIEN_MA_KHO$", "");
		}

		logger.info("Danh sách tổng hợp nhập xuất tồn theo loại tài khoản: '" + maTk + "' ...");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		if (dau != null) {
			String batDau = sdf.format(dau);
			logger.info("Từ " + batDau);
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "AND CT.NGAY_HT >= '" + batDau + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "");
		}

		if (cuoi != null) {
			String ketThuc = sdf.format(cuoi);
			logger.info("Đến " + ketThuc);
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "AND CT.NGAY_HT <= '" + ketThuc + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "");
		}

		logger.info(query);
		logger.info("Mã tài khoản: " + maTk);

		List<DuLieuKeToan> duLieuKeToanDs = jdbcTmpl.query(query, new DuLieuKeToanHangHoaMapper());
		if (duLieuKeToanDs != null) {
			logger.info("Kết quả: " + duLieuKeToanDs.size());
		} else {
			logger.info("Kết quả: " + 0);
		}

		List<DuLieuKeToan> ketQua = null;
		// Ghép dữ liệu tại đây
		if (duLieuKeToanDs != null) {
			logger.info("Ghép dữ liệu");
			ketQua = new ArrayList<>();
			Iterator<DuLieuKeToan> iter = duLieuKeToanDs.iterator();
			while (iter.hasNext()) {
				DuLieuKeToan duLieuKeToan = iter.next();

				int pos = ketQua.indexOf(duLieuKeToan);
				if (pos > -1) {
					DuLieuKeToan duLieuKeToanTmpl = ketQua.get(pos);
					duLieuKeToanTmpl.tron(duLieuKeToan);
				} else {
					ketQua.add(duLieuKeToan);
				}
			}
			logger.info("Kết quả trả về: " + ketQua.size());
		}

		return ketQua;
	}

	@Override
	public DuLieuKeToan tongPhatSinhNxt(String maTk, int maHh, List<Integer> maKhoDs, Date dau, Date cuoi) {
		if (maTk == null) {
			return null;
		}

		String query = DANH_SACH_TONG_HOP_NXT_MA_HH;
		query = query.replaceAll("\\$MA_TK\\$", maTk);

		if (maKhoDs != null) {
			StringBuffer dieuKienKhoBuff = new StringBuffer("AND CTHH.MA_KHO IN (");

			for (Iterator<Integer> iter = maKhoDs.iterator(); iter.hasNext();) {
				Integer maKho = iter.next();

				dieuKienKhoBuff = dieuKienKhoBuff.append(maKho);
				if (iter.hasNext()) {
					dieuKienKhoBuff = dieuKienKhoBuff.append(", ");
				}
			}
			dieuKienKhoBuff = dieuKienKhoBuff.append(")");

			query = query.replace("$DIEU_KIEN_MA_KHO$", dieuKienKhoBuff.toString());
		} else {
			query = query.replace("$DIEU_KIEN_MA_KHO$", "");
		}

		logger.info("Danh sách tổng hợp nhập xuất tồn theo loại tài khoản: '" + maTk + "' ...");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		if (dau != null) {
			String batDau = sdf.format(dau);
			logger.info("Từ " + batDau);
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "AND CT.NGAY_HT >= '" + batDau + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_BAT_DAT\\$", "");
		}

		if (cuoi != null) {
			String ketThuc = sdf.format(cuoi);
			logger.info("Đến " + ketThuc);
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "AND CT.NGAY_HT <= '" + ketThuc + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_KET_THUC\\$", "");
		}

		logger.info(query);
		logger.info("Mã tài khoản: " + maTk);
		logger.info("Mã hàng hóa: " + maHh);

		Object[] objs = { maHh };
		List<DuLieuKeToan> duLieuKeToanDs = jdbcTmpl.query(query, objs, new DuLieuKeToanHangHoaMapper());
		if (duLieuKeToanDs != null) {
			logger.info("Kết quả: " + duLieuKeToanDs.size());
		} else {
			logger.info("Kết quả: " + 0);
		}

		List<DuLieuKeToan> ketQua = null;
		// Ghép dữ liệu tại đây
		if (duLieuKeToanDs != null) {
			logger.info("Ghép dữ liệu");
			ketQua = new ArrayList<>();
			Iterator<DuLieuKeToan> iter = duLieuKeToanDs.iterator();
			while (iter.hasNext()) {
				DuLieuKeToan duLieuKeToan = iter.next();

				int pos = ketQua.indexOf(duLieuKeToan);
				if (pos > -1) {
					DuLieuKeToan duLieuKeToanTmpl = ketQua.get(pos);
					duLieuKeToanTmpl.tron(duLieuKeToan);
				} else {
					ketQua.add(duLieuKeToan);
				}
			}
			logger.info("Kết quả trả về: " + ketQua.size());
		}

		if (ketQua != null && ketQua.size() > 0) {
			return ketQua.get(0);
		}

		return null;
	}

	public class DuLieuKeToanHangHoaMapper implements RowMapper<DuLieuKeToan> {
		public DuLieuKeToan mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				DuLieuKeToan duLieuKeToan = new DuLieuKeToan();

				HangHoa hangHoa = new HangHoa();
				hangHoa.setMaHh(rs.getInt("MA_HH"));
				hangHoa.setTenHh(rs.getString("TEN_HH"));
				hangHoa.setKyHieuHh(rs.getString("KH_HH"));

				DonVi donVi = new DonVi();
				donVi.setMaDv(rs.getInt("MA_DV"));
				donVi.setTenDv(rs.getString("TEN_DV"));
				donVi.setMoTa(rs.getString("MO_TA"));
				hangHoa.setDonVi(donVi);

				// KhoHang khoHang = new KhoHang();
				// khoHang.setMaKho(rs.getInt("MA_KHO_GIA_TRI"));
				// khoHang.setKyHieuKho(rs.getString("KH_KHO"));
				// khoHang.setTenKho(rs.getString("TEN_KHO"));
				// khoHang.setChieu(rs.getInt("CHIEU"));
				// hangHoa.setKho(khoHang);

				duLieuKeToan.setHangHoa(hangHoa);
				duLieuKeToan.setChieu(rs.getInt("CHIEU"));
				// duLieuKeToan.setKhoHang(khoHang);

				LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
				loaiTaiKhoan.setSoDuGiaTri(rs.getInt("SO_DU_GIA_TRI"));
				loaiTaiKhoan.setMaTk(rs.getString("MA_TK_GIA_TRI"));
				// loaiTaiKhoan.setSoDu(rs.getInt("SO_DU"));
				// loaiTaiKhoan.setLuongTinh(rs.getBoolean("LUONG_TINH"));
				duLieuKeToan.setLoaiTaiKhoan(loaiTaiKhoan);

				if (loaiTaiKhoan.getSoDuGiaTri() == LoaiTaiKhoan.NO) {
					duLieuKeToan.setTongNoPhatSinh(rs.getDouble("SO_TIEN"));
				} else {
					duLieuKeToan.setTongCoPhatSinh(rs.getDouble("SO_TIEN"));
				}

				if (duLieuKeToan.getChieu() == ChungTu.MUA) {
					duLieuKeToan.setSoLuongNhapPhatSinh(rs.getDouble("SO_LUONG"));
				} else {
					duLieuKeToan.setSoLuongXuatPhatSinh(rs.getDouble("SO_LUONG"));
				}

				logger.info(duLieuKeToan);
				return duLieuKeToan;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public class DuLieuKeToanMapper implements RowMapper<DuLieuKeToan> {
		public DuLieuKeToan mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				DuLieuKeToan duLieuKeToan = new DuLieuKeToan();

				DoiTuong doiTuong = new DoiTuong();
				doiTuong.setLoaiDt(rs.getInt("LOAI_DT"));
				doiTuong.setMaDt(rs.getInt("MA_DT"));
				doiTuong.setKhDt(rs.getString("KH_DT"));
				doiTuong.setTenDt(rs.getString("TEN_DT"));
				doiTuong.setMaThue(rs.getString("MA_THUE"));
				doiTuong.setDiaChi(rs.getString("DIA_CHI"));
				doiTuong.setNguoiNop(rs.getString("NGUOI_NOP"));
				duLieuKeToan.setDoiTuong(doiTuong);

				LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
				loaiTaiKhoan.setMaTk(rs.getString("MA_TK"));
				loaiTaiKhoan.setSoDu(rs.getInt("SO_DU"));
				loaiTaiKhoan.setLuongTinh(rs.getBoolean("LUONG_TINH"));
				duLieuKeToan.setLoaiTaiKhoan(loaiTaiKhoan);

				if (loaiTaiKhoan.getSoDu() == LoaiTaiKhoan.NO) {
					duLieuKeToan.setTongNoPhatSinh(rs.getDouble("SO_TIEN"));
				} else {
					duLieuKeToan.setTongCoPhatSinh(rs.getDouble("SO_TIEN"));
				}

				logger.info(duLieuKeToan);

				return duLieuKeToan;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public class DuLieuKeToanDoiTuongMapper implements RowMapper<DuLieuKeToan> {
		public DuLieuKeToan mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				DuLieuKeToan duLieuKeToan = new DuLieuKeToan();

				DoiTuong doiTuong = new DoiTuong();
				doiTuong.setLoaiDt(rs.getInt("LOAI_DT"));
				doiTuong.setMaDt(rs.getInt("MA_DT"));
				duLieuKeToan.setDoiTuong(doiTuong);

				LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
				loaiTaiKhoan.setMaTk(rs.getString("MA_TK"));
				loaiTaiKhoan.setSoDu(rs.getInt("SO_DU"));
				loaiTaiKhoan.setLuongTinh(rs.getBoolean("LUONG_TINH"));
				duLieuKeToan.setLoaiTaiKhoan(loaiTaiKhoan);

				if (loaiTaiKhoan.getSoDu() == LoaiTaiKhoan.NO) {
					duLieuKeToan.setTongNoPhatSinh(rs.getDouble("SO_TIEN"));
				} else {
					duLieuKeToan.setTongCoPhatSinh(rs.getDouble("SO_TIEN"));
				}

				return duLieuKeToan;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}
