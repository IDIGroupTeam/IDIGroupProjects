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
import com.idi.finance.bean.cdkt.DuLieuKeToan;
import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.DoiTuong;
import com.idi.finance.bean.chungtu.KetChuyenButToan;
import com.idi.finance.bean.chungtu.TaiKhoan;
import com.idi.finance.bean.chungtu.Tien;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.soketoan.NghiepVuKeToan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.SoKeToanDAO;

public class SoKeToanDAOImpl implements SoKeToanDAO {
	private static final Logger logger = Logger.getLogger(SoKeToanDAOImpl.class);

	@Value("${DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_MA_TAI_KHOAN}")
	private String DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_MA_TAI_KHOAN;

	@Value("${DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_DIEU_KIEN}")
	private String DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_DIEU_KIEN;

	@Value("${DANH_SACH_NGHIEP_VU_KE_TOAN_KHO_THEO_DIEU_KIEN}")
	private String DANH_SACH_NGHIEP_VU_KE_TOAN_KHO_THEO_DIEU_KIEN;

	@Value("${DANH_SACH_NGHIEP_VU_KE_TOAN_KC_THEO_DIEU_KIEN}")
	private String DANH_SACH_NGHIEP_VU_KE_TOAN_KC_THEO_DIEU_KIEN;

	@Value("${TONG_PHAT_SINH}")
	private String TONG_PHAT_SINH;

	@Value("${TONG_PHAT_SINH_DOI_TUONG}")
	private String TONG_PHAT_SINH_DOI_TUONG;

	@Value("${TONG_PHAT_SINH_TOAN_BO}")
	private String TONG_PHAT_SINH_TOAN_BO;

	@Value("${DANH_SACH_TONG_HOP_CONG_NO}")
	private String DANH_SACH_TONG_HOP_CONG_NO;

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

	@Override
	public List<DuLieuKeToan> danhSachTongPhatSinhDoiTuong(String maTk, Date dau, Date cuoi) {
		if (maTk == null || maTk.trim().equals("")) {
			return null;
		}

		String query = TONG_PHAT_SINH_DOI_TUONG;

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

		logger.info(query);
		logger.info("maTk " + maTk);

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

				return nghiepVuKeToan;
			} catch (Exception e) {
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

				HangHoa hangHoa = new HangHoa();
				hangHoa.setMaHh(rs.getInt("MA_HH"));

				TaiKhoan taiKhoanNo = new TaiKhoan();
				taiKhoanNo.setSoDu(LoaiTaiKhoan.NO);
				taiKhoanNo.setLyDo(rs.getString("LY_DO_NO"));

				LoaiTaiKhoan loaiTaiKhoanNo = new LoaiTaiKhoan();
				loaiTaiKhoanNo.setMaTk(rs.getString("MA_TK_NO"));
				loaiTaiKhoanNo.setTenTk(rs.getString("TEN_TK_NO"));
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

		List<TaiKhoan> taiKhoanDs = new ArrayList<>();
		try {
			logger.info(query);
			taiKhoanDs = jdbcTmpl.query(query, new TongPhatSinhMapper());
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
				tien.setSoTien(tien.getGiaTri() / tien.getLoaiTien().getBanRa());

				taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);
				taiKhoan.setSoTien(tien);
				taiKhoan.setSoDu(rs.getInt("SO_DU"));

				return taiKhoan;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public List<DuLieuKeToan> danhSachTongHopCongNo(String maTk, Date dau, Date cuoi) {
		if (maTk == null) {
			return null;
		}

		String query = DANH_SACH_TONG_HOP_CONG_NO;
		query = query.replaceAll("\\$MA_TK\\$", maTk);

		logger.info("Danh sách tổng hợp công nợ theo loại tài khoản: '" + maTk + "' ...");
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

	public class DuLieuKeToanMapper implements RowMapper<DuLieuKeToan> {
		public DuLieuKeToan mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				DuLieuKeToan duLieuKeToan = new DuLieuKeToan();

				DoiTuong doiTuong = new DoiTuong();
				doiTuong.setLoaiDt(rs.getInt("LOAI_DT"));
				doiTuong.setMaDt(rs.getInt("MA_DT"));
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
