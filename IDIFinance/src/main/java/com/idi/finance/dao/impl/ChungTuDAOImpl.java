package com.idi.finance.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.DoiTuong;
import com.idi.finance.bean.chungtu.TaiKhoan;
import com.idi.finance.bean.chungtu.Tien;
import com.idi.finance.bean.hanghoa.DonVi;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.hanghoa.KhoHang;
import com.idi.finance.bean.hanghoa.NhomHang;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.ChungTuDAO;

public class ChungTuDAOImpl implements ChungTuDAO {
	private static final Logger logger = Logger.getLogger(ChungTuDAOImpl.class);

	@Value("${DANH_SACH_CHUNG_TU_THEO_LOAI}")
	private String DANH_SACH_CHUNG_TU_THEO_LOAI;

	@Value("${DANH_SACH_CHUNG_TU_KHO_THEO_LOAI}")
	private String DANH_SACH_CHUNG_TU_KHO_THEO_LOAI;

	@Value("${LAY_CHUNG_TU_THEO_MACT_LOAICT}")
	private String LAY_CHUNG_TU_THEO_MACT_LOAICT;

	@Value("${LAY_CHUNG_TU_KHO_THEO_MACT_LOAICT}")
	private String LAY_CHUNG_TU_KHO_THEO_MACT_LOAICT;

	@Value("${DANH_SACH_LOAI_TIEN}")
	private String DANH_SACH_LOAI_TIEN;

	@Value("${DEM_SO_CHUNG_TU}")
	private String DEM_SO_CHUNG_TU;

	@Value("${THEM_CHUNG_TU}")
	private String THEM_CHUNG_TU;

	@Value("${THEM_CHUNG_TU_TAI_KHOAN}")
	private String THEM_CHUNG_TU_TAI_KHOAN;

	@Value("${CAP_NHAT_CHUNG_TU}")
	private String CAP_NHAT_CHUNG_TU;

	@Value("${XOA_CHUNG_TU_TAI_KHOAN}")
	private String XOA_CHUNG_TU_TAI_KHOAN;

	@Value("${THEM_CHUNG_TU_KHO}")
	private String THEM_CHUNG_TU_KHO;

	@Value("${THEM_TAI_KHOAN}")
	private String THEM_TAI_KHOAN;

	@Value("${THEM_CHUNG_TU_TAI_KHOAN_KHO}")
	private String THEM_CHUNG_TU_TAI_KHOAN_KHO;

	@Value("${THEM_CHUNG_TU_HANG_HOA}")
	private String THEM_CHUNG_TU_HANG_HOA;

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

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public List<ChungTu> danhSachChungTuTheoLoaiCt(String loaiCt) {
		String query = DANH_SACH_CHUNG_TU_THEO_LOAI;

		logger.info("Danh sách chứng từ theo loại chứng từ: '" + loaiCt + "' ...");
		logger.info(query);

		Object[] objs = { loaiCt, loaiCt, loaiCt };
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
	public List<ChungTu> danhSachChungTuKhoTheoLoaiCt(String loaiCt) {
		String query = DANH_SACH_CHUNG_TU_KHO_THEO_LOAI;

		logger.info("Danh sách chứng từ kho theo loại chứng từ: '" + loaiCt + "' ...");
		logger.info(query);

		Object[] objs = { loaiCt, loaiCt, loaiCt };
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

				Tien giaMua = new Tien();
				giaMua.setSoTien(rs.getDouble("GIA_MUA"));
				giaMua.setLoaiTien(loaiTien);
				giaMua.setGiaTri(loaiTien.getBanRa() * giaMua.getSoTien());
				hangHoa.setGiaMua(giaMua);

				Tien giaNhapKho = new Tien();
				giaNhapKho.setSoTien(rs.getDouble("GIA_NHAP_KHO"));
				giaNhapKho.setLoaiTien(loaiTien);
				giaNhapKho.setGiaTri(loaiTien.getBanRa() * giaNhapKho.getSoTien());
				hangHoa.setGiaNhapKho(giaNhapKho);

				Tien giaBan = new Tien();
				giaBan.setSoTien(rs.getDouble("GIA_BAN"));
				giaBan.setLoaiTien(loaiTien);
				giaBan.setGiaTri(loaiTien.getBanRa() * giaBan.getSoTien());
				hangHoa.setGiaMua(giaBan);

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
				tkTien.setSoTien(rs.getDouble("SO_TIEN"));
				tkTien.setLoaiTien(loaiTien);
				tkTien.setGiaTri(tkTien.getLoaiTien().getBanRa() * tkTien.getSoTien());
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
				}

				chungTu.themHangHoa(hangHoa);

				return chungTu;
			} catch (Exception e) {
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
					java.sql.Date ngayLap = new java.sql.Date(chungTu.getNgayLap().getTime());
					stt.setDate(3, ngayLap);
					java.sql.Date ngayHt = new java.sql.Date(chungTu.getNgayHt().getTime());
					stt.setDate(4, ngayHt);
					stt.setString(5, chungTu.getLyDo());
					stt.setString(6, chungTu.getLoaiTien().getMaLt());
					stt.setDouble(7, chungTu.getLoaiTien().getBanRa());
					stt.setInt(8, chungTu.getKemTheo());
					stt.setInt(9, chungTu.getDoiTuong().getMaDt());
					stt.setInt(10, chungTu.getDoiTuong().getLoaiDt());
					stt.setString(11, chungTu.getDoiTuong().getNguoiNop());

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
						logger.info("Thêm vào bảng chứng từ tài khoản " + taiKhoan);
						jdbcTmpl.update(themChungTuTaiKhoan, chungTu.getMaCt(), taiKhoan.getLoaiTaiKhoan().getMaTk(),
								taiKhoan.getSoTien().getSoTien(), taiKhoan.getSoDu(), taiKhoan.getLyDo());
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
		String capNhatChungTu = CAP_NHAT_CHUNG_TU;
		String themChungTuTaiKhoan = THEM_CHUNG_TU_TAI_KHOAN;
		String xoaChungTuTk = XOA_CHUNG_TU_TAI_KHOAN;

		try {
			// Cập nhật chứng từ
			jdbcTmpl.update(capNhatChungTu, chungTu.getSoCt(), chungTu.getLoaiCt(), chungTu.getNgayLap(),
					chungTu.getNgayHt(), chungTu.getLyDo(), chungTu.getLoaiTien().getMaLt(),
					chungTu.getLoaiTien().getBanRa(), chungTu.getKemTheo(), chungTu.getDoiTuong().getMaDt(),
					chungTu.getDoiTuong().getLoaiDt(), chungTu.getDoiTuong().getNguoiNop(), chungTu.getMaCt());

			// Cập nhật (hoặc thêm mới) chứng từ tài khoản
			if (chungTu.getTaiKhoanDs() != null && chungTu.getTaiKhoanDs().size() > 0) {
				// Xóa hết tài khoản cũ rồi thêm mới từ đầu
				jdbcTmpl.update(xoaChungTuTk, chungTu.getMaCt());

				// Thêm mới từ đầu
				Iterator<TaiKhoan> iter = chungTu.getTaiKhoanDs().iterator();
				while (iter.hasNext()) {
					TaiKhoan taiKhoan = iter.next();

					jdbcTmpl.update(themChungTuTaiKhoan, chungTu.getMaCt(), taiKhoan.getLoaiTaiKhoan().getMaTk(),
							taiKhoan.getSoTien().getSoTien(), taiKhoan.getSoDu(), taiKhoan.getLyDo());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void themChungTuKho(ChungTu chungTu) {
		if (chungTu == null || chungTu.getHangHoaDs() == null) {
			return;
		}

		String themChungTu = THEM_CHUNG_TU_KHO;
		String themTaiKhoan = THEM_TAI_KHOAN;
		String themChungTuTaiKhoanKho = THEM_CHUNG_TU_TAI_KHOAN_KHO;
		String themChungTuHangHoa = THEM_CHUNG_TU_HANG_HOA;

		try {
			// Thêm chứng từ
			GeneratedKeyHolder holder = new GeneratedKeyHolder();
			jdbcTmpl.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement stt = con.prepareStatement(themChungTu, Statement.RETURN_GENERATED_KEYS);
					stt.setInt(1, chungTu.getSoCt());
					stt.setString(2, chungTu.getLoaiCt());
					java.sql.Date ngayLap = new java.sql.Date(chungTu.getNgayLap().getTime());
					stt.setDate(3, ngayLap);
					java.sql.Date ngayHt = new java.sql.Date(chungTu.getNgayHt().getTime());
					stt.setDate(4, ngayHt);
					stt.setString(5, chungTu.getLyDo());
					stt.setString(6, chungTu.getLoaiTien().getMaLt());
					stt.setDouble(7, chungTu.getLoaiTien().getBanRa());
					stt.setInt(8, chungTu.getKemTheo());
					stt.setInt(9, chungTu.getDoiTuong().getMaDt());
					stt.setInt(10, chungTu.getDoiTuong().getLoaiDt());
					stt.setString(11, chungTu.getDoiTuong().getNguoiNop());
					// stt.setInt(12, chungTu.getKhoBai().getMaKho());

					return stt;
				}
			}, holder);

			chungTu.setMaCt(holder.getKey().intValue());

			// Với từng loại hàng hóa
			Iterator<HangHoa> hhIter = chungTu.getHangHoaDs().iterator();
			while (hhIter.hasNext()) {
				HangHoa hangHoa = hhIter.next();
				logger.info("Hàng hóa: " + hangHoa);

				logger.info("Thêm vào chung_tu_hang_hoa");
				jdbcTmpl.update(themChungTuHangHoa, chungTu.getMaCt(), hangHoa.getMaHh(), hangHoa.getSoLuong(),
						hangHoa.getGiaMua().getSoTien(), hangHoa.getGiaNhapKho().getSoTien(),
						hangHoa.getGiaBan().getSoTien(), hangHoa.getKho().getMaKho());

				logger.info("Thêm vào bảng nghiep_vu_ke_toan: ");
				if (hangHoa.getTkKho() != null && hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk() != null) {
					logger.info("Tài khoản kho: " + hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, hangHoa.getSoLuong() * hangHoa.getGiaMua().getSoTien());
							stt.setInt(3, hangHoa.getTkKho().getSoDu());
							stt.setString(4, chungTu.getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), HangHoa.TK_KHO);
				}

				if (hangHoa.getTkDoanhThu() != null && hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk() != null) {
					logger.info("Tài khoản doanh thu:" + hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, hangHoa.getSoLuong() * hangHoa.getGiaMua().getSoTien());
							stt.setInt(3, hangHoa.getTkDoanhThu().getSoDu());
							stt.setString(4, chungTu.getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), HangHoa.TK_DOANH_THU);
				}

				if (hangHoa.getTkChiPhi() != null && hangHoa.getTkChiPhi().getLoaiTaiKhoan().getMaTk() != null) {
					logger.info("Tài khoản chi phí:" + hangHoa.getTkChiPhi().getLoaiTaiKhoan().getMaTk());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkChiPhi().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, hangHoa.getSoLuong() * hangHoa.getGiaMua().getSoTien());
							stt.setInt(3, hangHoa.getTkChiPhi().getSoDu());
							stt.setString(4, chungTu.getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), HangHoa.TK_CHI_PHI);
				}

				if (hangHoa.getTkThanhtoan() != null && hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk() != null) {
					logger.info("Tài khoản thanh toán:" + hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, hangHoa.getSoLuong() * hangHoa.getGiaMua().getSoTien());
							stt.setInt(3, hangHoa.getTkThanhtoan().getSoDu());
							stt.setString(4, chungTu.getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), HangHoa.TK_THANH_TOAN);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void capNhatChungTuKho(ChungTu chungTu) {
		String capNhatChungTu = CAP_NHAT_CHUNG_TU_KHO;

		String themTaiKhoan = THEM_TAI_KHOAN;
		String themChungTuTaiKhoanKho = THEM_CHUNG_TU_TAI_KHOAN_KHO;
		String themChungTuHangHoa = THEM_CHUNG_TU_HANG_HOA;

		String xoaChungTuHangHoa = XOA_CHUNG_TU_HANG_HOA;
		String xoaChungTuNvktKho = XOA_CHUNG_TU_NVKT_KHO;
		String xoaChungTuNvkt = XOA_NVKT;

		try {
			logger.info("Cập nhật chứng từ " + chungTu);
			jdbcTmpl.update(capNhatChungTu, chungTu.getSoCt(), chungTu.getLoaiCt(), chungTu.getNgayLap(),
					chungTu.getNgayHt(), chungTu.getLyDo(), chungTu.getLoaiTien().getMaLt(),
					chungTu.getLoaiTien().getBanRa(), chungTu.getKemTheo(), chungTu.getDoiTuong().getMaDt(),
					chungTu.getDoiTuong().getLoaiDt(), chungTu.getDoiTuong().getNguoiNop(), chungTu.getMaCt());

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

					if (hangHoa.getTkKho() != null && hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk() != null) {
						jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkKho().getMaNvkt());
					}

					if (hangHoa.getTkDoanhThu() != null
							&& hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk() != null) {
						jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkDoanhThu().getMaNvkt());
					}

					if (hangHoa.getTkChiPhi() != null && hangHoa.getTkChiPhi().getLoaiTaiKhoan().getMaTk() != null) {
						jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkChiPhi().getMaNvkt());
					}

					if (hangHoa.getTkThanhtoan() != null
							&& hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk() != null) {
						jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkThanhtoan().getMaNvkt());
					}
				}
			}

			// Với từng loại hàng hóa
			Iterator<HangHoa> hhIter = chungTu.getHangHoaDs().iterator();
			while (hhIter.hasNext()) {
				HangHoa hangHoa = hhIter.next();
				logger.info("Hàng hóa: " + hangHoa);

				// Thêm vào chung_tu_hang_hoa
				jdbcTmpl.update(themChungTuHangHoa, chungTu.getMaCt(), hangHoa.getMaHh(), hangHoa.getSoLuong(),
						hangHoa.getGiaMua().getSoTien(), hangHoa.getKho().getMaKho(), hangHoa.getGiaMua().getSoTien());

				// Thêm vào bảng nghiep_vu_ke_toan
				if (hangHoa.getTkKho() != null && hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk() != null) {
					logger.info("Tài khoản kho: " + hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, hangHoa.getSoLuong() * hangHoa.getGiaMua().getSoTien());
							stt.setInt(3, hangHoa.getTkKho().getSoDu());
							stt.setString(4, chungTu.getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), HangHoa.TK_KHO);
				}

				if (hangHoa.getTkDoanhThu() != null && hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk() != null) {
					logger.info("Tài khoản doanh thu:" + hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, hangHoa.getSoLuong() * hangHoa.getGiaMua().getSoTien());
							stt.setInt(3, hangHoa.getTkDoanhThu().getSoDu());
							stt.setString(4, chungTu.getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), HangHoa.TK_DOANH_THU);
				}

				if (hangHoa.getTkChiPhi() != null && hangHoa.getTkChiPhi().getLoaiTaiKhoan().getMaTk() != null) {
					logger.info("Tài khoản chi phí:" + hangHoa.getTkChiPhi().getLoaiTaiKhoan().getMaTk());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkChiPhi().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, hangHoa.getSoLuong() * hangHoa.getGiaMua().getSoTien());
							stt.setInt(3, hangHoa.getTkChiPhi().getSoDu());
							stt.setString(4, chungTu.getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), HangHoa.TK_CHI_PHI);
				}

				if (hangHoa.getTkThanhtoan() != null && hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk() != null) {
					logger.info("Tài khoản thanh toán:" + hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk());
					GeneratedKeyHolder nvktHolder = new GeneratedKeyHolder();
					jdbcTmpl.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement stt = con.prepareStatement(themTaiKhoan, Statement.RETURN_GENERATED_KEYS);
							stt.setString(1, hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk());
							stt.setDouble(2, hangHoa.getSoLuong() * hangHoa.getGiaMua().getSoTien());
							stt.setInt(3, hangHoa.getTkThanhtoan().getSoDu());
							stt.setString(4, chungTu.getLyDo());

							return stt;
						}
					}, nvktHolder);
					jdbcTmpl.update(themChungTuTaiKhoanKho, chungTu.getMaCt(), hangHoa.getMaHh(),
							nvktHolder.getKey().intValue(), HangHoa.TK_THANH_TOAN);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int demSoChungTuTheoLoaiCtVaNam(String loaiCt, Date nam) {
		String demSoChungTu = DEM_SO_CHUNG_TU;
		Calendar cal = Calendar.getInstance();
		cal.setTime(nam);
		int count = jdbcTmpl.queryForObject(demSoChungTu, new Object[] { loaiCt, cal.get(Calendar.YEAR) },
				Integer.class);
		return count;
	}

	@Override
	public void xoaChungTu(int maCt, String loaiCt) {
		String xoaChungTu = XOA_CHUNG_TU_THEO_MACT_LOAICT;
		String xoaChungTuTk = XOA_CHUNG_TU_TAI_KHOAN;
		jdbcTmpl.update(xoaChungTu, maCt, loaiCt);
		jdbcTmpl.update(xoaChungTuTk, maCt);
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

				if (hangHoa.getTkKho() != null && hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk() != null) {
					jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkKho().getMaNvkt());
				}

				if (hangHoa.getTkDoanhThu() != null && hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk() != null) {
					jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkDoanhThu().getMaNvkt());
				}

				if (hangHoa.getTkChiPhi() != null && hangHoa.getTkChiPhi().getLoaiTaiKhoan().getMaTk() != null) {
					jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkChiPhi().getMaNvkt());
				}

				if (hangHoa.getTkThanhtoan() != null && hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk() != null) {
					jdbcTmpl.update(xoaChungTuNvkt, hangHoa.getTkThanhtoan().getMaNvkt());
				}
			}
		}

		logger.info("Xóa chứng từ có MA_CT = " + chungTu.getMaCt());
	}
}
