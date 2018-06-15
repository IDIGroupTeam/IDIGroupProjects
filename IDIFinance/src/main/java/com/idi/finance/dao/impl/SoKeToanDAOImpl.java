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
import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.DoiTuong;
import com.idi.finance.bean.chungtu.TaiKhoan;
import com.idi.finance.bean.chungtu.Tien;
import com.idi.finance.bean.hanghoa.DonVi;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.hanghoa.KhoHang;
import com.idi.finance.bean.hanghoa.NhomHang;
import com.idi.finance.bean.soketoan.NghiepVuKeToan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.SoKeToanDAO;

public class SoKeToanDAOImpl implements SoKeToanDAO {
	private static final Logger logger = Logger.getLogger(SoKeToanDAOImpl.class);

	@Value("${DANH_SACH_CHUNG_TU_NHAT_KY_CHUNG_THEO_DIEU_KIEN}")
	private String DANH_SACH_CHUNG_TU_NHAT_KY_CHUNG_THEO_DIEU_KIEN;

	@Value("${DANH_SACH_CHUNG_TU_KHO_NHAT_KY_CHUNG_THEO_DIEU_KIEN}")
	private String DANH_SACH_CHUNG_TU_KHO_NHAT_KY_CHUNG_THEO_DIEU_KIEN;

	@Value("${DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_MA_TAI_KHOAN}")
	private String DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_MA_TAI_KHOAN;

	@Value("${DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_DIEU_KIEN}")
	private String DANH_SACH_NGHIEP_VU_KE_TOAN_THEO_DIEU_KIEN;

	@Value("${DANH_SACH_NGHIEP_VU_KE_TOAN_KTTH_THEO_DIEU_KIEN}")
	private String DANH_SACH_NGHIEP_VU_KE_TOAN_KTTH_THEO_DIEU_KIEN;

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
	public List<ChungTu> danhSachChungTu(Date dau, Date cuoi, List<String> loaiCts) {
		if (loaiCts == null || loaiCts.size() == 0) {
			return null;
		}

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
				chungTu.setTinhChatCt(rs.getInt("TINH_CHAT"));
				chungTu.setLyDo(rs.getString("LY_DO"));
				chungTu.setKemTheo(rs.getInt("KEM_THEO"));
				chungTu.setNgayLap(rs.getDate("NGAY_LAP"));
				chungTu.setNgayHt(rs.getDate("NGAY_HT"));
				chungTu.setNgayTt(rs.getDate("NGAY_TT"));

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

				chungTu.themTaiKhoan(taiKhoan);
				taiKhoan.setChungTu(chungTu);

				return chungTu;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public List<ChungTu> danhSachChungTuKho(Date dau, Date cuoi, List<String> loaiCts) {
		if (loaiCts == null || loaiCts.size() == 0) {
			return null;
		}

		String query = DANH_SACH_CHUNG_TU_KHO_NHAT_KY_CHUNG_THEO_DIEU_KIEN;

		if (loaiCts.contains(ChungTu.TAT_CA)) {
			query = query.replaceAll("\\$DIEU_KIEN_LOAI_CT\\$", "");
			logger.info("Danh sách tất cả chứng từ ...");
		} else {
			logger.info("Danh sách tất cả chứng từ kho loại " + loaiCts + " ...");

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
					// Nếu chứng từ mới có danh sách hàng hóa cần bổ sung
					// Thì sắp xếp danh sách hàng hóa của chứng từ mới trước
					List<HangHoa> hangHoaDs = new ArrayList<>();
					Iterator<HangHoa> hangHoaIter = chungTu.getHangHoaDs().iterator();
					while (hangHoaIter.hasNext()) {
						HangHoa hangHoa = hangHoaIter.next();

						int pos = hangHoaDs.indexOf(hangHoa);
						if (pos > -1) {
							hangHoaDs.get(pos).tronTk(hangHoa);
						} else {
							hangHoaDs.add(hangHoa);
						}
					}
					chungTu.setHangHoaDs(hangHoaDs);

					// Rồi gộp danh sách hàng hóa chứng từ mới vào danh sách hàng hóa chứng từ cũ
					int pos = ketQua.indexOf(chungTu);
					if (pos > -1) {
						ChungTu chungTuTmpl = ketQua.get(pos);

						if (chungTuTmpl.getHangHoaDs() != null) {
							Iterator<HangHoa> hhIter = chungTu.getHangHoaDs().iterator();
							while (hhIter.hasNext()) {
								HangHoa hangHoa = hhIter.next();

								int posTmpl = chungTuTmpl.getHangHoaDs().indexOf(hangHoa);
								if (posTmpl > -1) {
									chungTuTmpl.getHangHoaDs().get(posTmpl).tronTk(hangHoa);
								} else {
									chungTuTmpl.themHangHoa(hangHoa);
								}
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

				LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
				loaiTaiKhoan.setMaTk(rs.getString("MA_TK"));
				loaiTaiKhoan.setTenTk(rs.getString("TEN_TK"));
				taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);

				Tien tkTien = new Tien();
				tkTien.setLoaiTien(loaiTien);
				tkTien.setGiaTri(rs.getDouble("SO_TIEN"));
				tkTien.setSoTien(tkTien.getGiaTri() / tkTien.getLoaiTien().getBanRa());
				taiKhoan.setSoTien(tkTien);

				taiKhoan.setChungTu(chungTu);

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

				chungTu.themHangHoa(hangHoa);

				return chungTu;
			} catch (Exception e) {
				return null;
			}
		}
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
		logger.info(nghiepVuKeToanDs);
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

				TaiKhoan taiKhoan = new TaiKhoan();
				taiKhoan.setSoDu(rs.getInt("SO_DU"));
				taiKhoan.setLyDo(rs.getString("LY_DO"));

				LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
				loaiTaiKhoan.setMaTk(rs.getString("MA_TK"));
				loaiTaiKhoan.setTenTk(rs.getString("TEN_TK"));
				taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);

				Tien tien = new Tien();
				tien.setLoaiTien(loaiTien);
				tien.setGiaTri(rs.getDouble("SO_TIEN"));
				tien.setSoTien(tien.getGiaTri() / tien.getLoaiTien().getBanRa());
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
}
