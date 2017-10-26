package com.idi.finance.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.ChungTuDAO;

public class ChungTuDAOImpl implements ChungTuDAO {
	private static final Logger logger = Logger.getLogger(ChungTuDAOImpl.class);

	@Value("${DANH_SACH_CHUNG_TU_THEO_LOAI}")
	private String DANH_SACH_CHUNG_TU_THEO_LOAI;

	@Value("${LAY_CHUNG_TU_THEO_MACT}")
	private String LAY_CHUNG_TU_THEO_MACT;

	@Value("${LAY_CHUNG_TU_THEO_MACT_LOAICT}")
	private String LAY_CHUNG_TU_THEO_MACT_LOAICT;

	@Value("${DANH_SACH_LOAI_TIEN}")
	private String DANH_SACH_LOAI_TIEN;

	@Value("${DEM_SO_CHUNG_TU}")
	private String DEM_SO_CHUNG_TU;

	@Value("${THEM_CHUNG_TU}")
	private String THEM_CHUNG_TU;

	@Value("${THEM_CHUNG_TU_TAI_KHOAN}")
	private String THEM_CHUNG_TU_TAI_KHOAN;

	@Value("${XOA_CHUNG_TU}")
	private String XOA_CHUNG_TU;

	@Value("${XOA_CHUNG_TU_THEO_MACT_LOAICT}")
	private String XOA_CHUNG_TU_THEO_MACT_LOAICT;

	@Value("${XOA_CHUNG_TU_TAI_KHOAN}")
	private String XOA_CHUNG_TU_TAI_KHOAN;

	@Value("${CAP_NHAT_CHUNG_TU}")
	private String CAP_NHAT_CHUNG_TU;

	@Value("${CAP_NHAT_CHUNG_TU_TAI_KHOAN}")
	private String CAP_NHAT_CHUNG_TU_TAI_KHOAN;

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

				Timestamp ngayLapTs = rs.getTimestamp("NGAY_LAP");
				Date ngayLap = new Date(ngayLapTs.getTime());
				chungTu.setNgayLap(ngayLap);

				Timestamp ngayHtTs = rs.getTimestamp("NGAY_HT");
				Date ngayHt = new Date(ngayHtTs.getTime());
				chungTu.setNgayHt(ngayHt);

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
	public ChungTu layChungTu(int maCt) {
		String layChungTu = LAY_CHUNG_TU_THEO_MACT;

		logger.info("Lấy chứng từ theo MA_CT: '" + maCt + "' ...");
		logger.info(layChungTu);

		Object[] objs = { maCt, maCt, maCt };
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
					stt.setDouble(6, chungTu.getSoTien().getSoTien());
					stt.setString(7, chungTu.getSoTien().getTien().getMaLt());
					stt.setDouble(8, chungTu.getSoTien().getTien().getBanRa());
					stt.setInt(9, chungTu.getKemTheo());
					stt.setInt(10, chungTu.getDoiTuong().getMaDt());
					stt.setInt(11, chungTu.getDoiTuong().getLoaiDt());
					stt.setString(12, chungTu.getDoiTuong().getNguoiNop());

					return stt;
				}
			}, holder);

			// Thêm chứng từ tài khoản
			chungTu.setMaCt(holder.getKey().intValue());
			Iterator<TaiKhoan> iter = chungTu.getTaiKhoanDs().iterator();
			while (iter.hasNext()) {
				TaiKhoan taiKhoan = iter.next();

				try {
					if (!taiKhoan.getTaiKhoan().getMaTk().equals("0")) {
						logger.info("Thêm vào bảng chứng từ tài khoản " + taiKhoan);
						jdbcTmpl.update(themChungTuTaiKhoan, chungTu.getMaCt(), taiKhoan.getTaiKhoan().getMaTk(),
								taiKhoan.getSoTien(), taiKhoan.getGhiNo(), taiKhoan.getLyDo());
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
		// String capNhatChungTuTaiKhoan = CAP_NHAT_CHUNG_TU_TAI_KHOAN;
		String themChungTuTaiKhoan = THEM_CHUNG_TU_TAI_KHOAN;
		String xoaChungTuTk = XOA_CHUNG_TU_TAI_KHOAN;

		try {
			// Cập nhật chứng từ
			jdbcTmpl.update(capNhatChungTu, chungTu.getSoCt(), chungTu.getLoaiCt(), chungTu.getNgayLap(),
					chungTu.getNgayHt(), chungTu.getLyDo(), chungTu.getSoTien().getSoTien(),
					chungTu.getSoTien().getTien().getMaLt(), chungTu.getSoTien().getTien().getBanRa(),
					chungTu.getKemTheo(), chungTu.getDoiTuong().getMaDt(), chungTu.getDoiTuong().getLoaiDt(),
					chungTu.getDoiTuong().getNguoiNop(), chungTu.getMaCt());

			// Cập nhật (hoặc thêm mới) chứng từ tài khoản
			if (chungTu.getTaiKhoanDs() != null && chungTu.getTaiKhoanDs().size() > 0) {
				// Xóa hết tài khoản cũ rồi thêm mới từ đầu
				jdbcTmpl.update(xoaChungTuTk, chungTu.getMaCt());

				// Thêm mới từ đầu
				Iterator<TaiKhoan> iter = chungTu.getTaiKhoanDs().iterator();
				while (iter.hasNext()) {
					TaiKhoan taiKhoan = iter.next();

					/*
					 * int count = jdbcTmpl.update(capNhatChungTuTaiKhoan, taiKhoan.getSoTien(),
					 * taiKhoan.getGhiNo(), taiKhoan.getLyDo(), chungTu.getMaCt(),
					 * taiKhoan.getTaiKhoan().getMaTk());
					 */

					// Nếu chưa có thì thêm vào
					// if (count == 0) {
					jdbcTmpl.update(themChungTuTaiKhoan, chungTu.getMaCt(), taiKhoan.getTaiKhoan().getMaTk(),
							taiKhoan.getSoTien(), taiKhoan.getGhiNo(), taiKhoan.getLyDo());
					// }
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
	public void xoaChungTu(int maCt) {
		String xoaChungTu = XOA_CHUNG_TU;
		String xoaChungTuTk = XOA_CHUNG_TU_TAI_KHOAN;
		jdbcTmpl.update(xoaChungTu, maCt);
		jdbcTmpl.update(xoaChungTuTk, maCt);
		logger.info("Xóa chứng từ có MA_CT = " + maCt);
	}

	@Override
	public void xoaChungTu(int maCt, String loaiCt) {
		String xoaChungTu = XOA_CHUNG_TU_THEO_MACT_LOAICT;
		String xoaChungTuTk = XOA_CHUNG_TU_TAI_KHOAN;
		jdbcTmpl.update(xoaChungTu, maCt, loaiCt);
		jdbcTmpl.update(xoaChungTuTk, maCt);
		logger.info("Xóa chứng từ có MA_CT = " + maCt);
	}
}
