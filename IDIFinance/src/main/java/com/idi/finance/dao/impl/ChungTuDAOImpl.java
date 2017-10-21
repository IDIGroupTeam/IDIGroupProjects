package com.idi.finance.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
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

	@Value("${DANH_SACH_LOAI_TIEN}")
	private String DANH_SACH_LOAI_TIEN;

	@Value("${THEM_CHUNG_TU}")
	private String THEM_CHUNG_TU;

	@Value("${THEM_CHUNG_TU_TAI_KHOAN}")
	private String THEM_CHUNG_TU_TAI_KHOAN;

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

				LoaiTien loaiTien = new LoaiTien();
				loaiTien.setMaLt(rs.getString("LOAI_TIEN"));
				loaiTien.setTenLt(rs.getString("TEN_NT"));
				loaiTien.setBanRa(rs.getDouble("TY_GIA"));
				Tien tien = new Tien();
				tien.setTien(loaiTien);
				tien.setSoTien(rs.getDouble("SO_TIEN"));
				tien.setGiaTri(loaiTien.getBanRa() * tien.getSoTien());
				chungTu.setSoTien(tien);

				DoiTuong doiTuong = new DoiTuong();
				doiTuong.setMaDt(rs.getInt("MA_DT"));
				doiTuong.setTenDt(rs.getString("TEN_DT"));
				doiTuong.setDiaChi(rs.getString("DIA_CHI"));
				doiTuong.setMaThue(rs.getString("MA_THUE"));
				doiTuong.setNguoiNop(rs.getString("NGUOI_NOP"));
				chungTu.setDoiTuong(doiTuong);

				TaiKhoan taiKhoan = new TaiKhoan();
				LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
				loaiTaiKhoan.setMaTk(rs.getString("MA_TK"));
				loaiTaiKhoan.setTenTk(rs.getString("TEN_TK"));
				loaiTaiKhoan.setSoDu(rs.getInt("SO_DU"));
				taiKhoan.setTaiKhoan(loaiTaiKhoan);
				taiKhoan.setSoTien(rs.getDouble("SO_TIEN"));
				chungTu.themTaiKhoan(taiKhoan);

				return chungTu;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public ChungTu layChungTuTheoMaCt(int maCt) {
		String layChungTu = LAY_CHUNG_TU_THEO_MACT;

		logger.info("Lấy chứng từ theo MA_CT: '" + maCt + "' ...");
		logger.info(layChungTu);

		Object[] objs = { maCt, maCt, maCt };
		ChungTu chungTu = jdbcTmpl.queryForObject(layChungTu, objs, new ChungTuMapper());

		return chungTu;
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
					// SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
					// logger.info(df.format(chungTu.getNgayLap()));
					// Timestamp ts = Timestamp.valueOf(df.format(chungTu.getNgayLap()));
					java.sql.Date date = new java.sql.Date(chungTu.getNgayLap().getTime());
					stt.setDate(3, date);

					// stt.setTimestamp(3, ts);
					stt.setString(4, chungTu.getLyDo());
					stt.setDouble(5, chungTu.getSoTien().getSoTien());
					stt.setString(6, chungTu.getSoTien().getTien().getMaLt());
					stt.setDouble(7, chungTu.getSoTien().getTien().getBanRa());
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
					if (!taiKhoan.getTaiKhoan().getMaTk().equals("0")) {
						logger.info("Thêm vào bảng chứng từ tài khoản " + taiKhoan);
						jdbcTmpl.update(themChungTuTaiKhoan, chungTu.getMaCt(), taiKhoan.getTaiKhoan().getMaTk(),
								taiKhoan.getSoTien(), taiKhoan.getGhiNo());
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
