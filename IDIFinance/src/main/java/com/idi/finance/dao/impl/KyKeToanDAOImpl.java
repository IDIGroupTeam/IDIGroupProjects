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

import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.DoiTuong;
import com.idi.finance.bean.doitac.NganHangTaiKhoan;
import com.idi.finance.bean.kyketoan.KyKeToan;
import com.idi.finance.bean.kyketoan.SoDuKy;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.KyKeToanDAO;

public class KyKeToanDAOImpl implements KyKeToanDAO {
	private static final Logger logger = Logger.getLogger(KyKeToanDAOImpl.class);

	@Value("${DANH_SACH_KY_KE_TOAN}")
	private String DANH_SACH_KY_KE_TOAN;

	@Value("${LAY_KY_KE_TOAN}")
	private String LAY_KY_KE_TOAN;

	@Value("${LAY_KY_KE_TOAN_CHUNG_TU}")
	private String LAY_KY_KE_TOAN_CHUNG_TU;

	@Value("${LAY_KY_KE_TOAN_MAC_DINH}")
	private String LAY_KY_KE_TOAN_MAC_DINH;

	@Value("${THEM_KY_KE_TOAN}")
	private String THEM_KY_KE_TOAN;

	@Value("${CAP_NHAT_KY_KE_TOAN}")
	private String CAP_NHAT_KY_KE_TOAN;

	@Value("${LAY_KET_THUC_CUOI}")
	private String LAY_KET_THUC_CUOI;

	@Value("${LAY_KET_THUC_CUOI_GIOI_HAN}")
	private String LAY_KET_THUC_CUOI_GIOI_HAN;

	@Value("${LAY_KY_KE_TOAN_TRUOC}")
	private String LAY_KY_KE_TOAN_TRUOC;

	@Value("${MAC_DINH_KY_KE_TOAN_TAT_CA}")
	private String MAC_DINH_KY_KE_TOAN_TAT_CA;

	@Value("${MAC_DINH_KY_KE_TOAN}")
	private String MAC_DINH_KY_KE_TOAN;

	@Value("${DONG_MO_KY_KE_TOAN}")
	private String DONG_MO_KY_KE_TOAN;

	@Value("${XOA_KY_SO_DU_DAU_KY_KE_TOAN}")
	private String XOA_KY_SO_DU_DAU_KY_KE_TOAN;

	@Value("${XOA_KY_KE_TOAN}")
	private String XOA_KY_KE_TOAN;

	@Value("${LAY_SO_DU_KY_THEO_KKT}")
	private String LAY_SO_DU_KY_THEO_KKT;

	@Value("${LAY_SO_DU_KY_THEO_KKT_DOI_TUONG}")
	private String LAY_SO_DU_KY_THEO_KKT_DOI_TUONG;

	@Value("${LAY_SO_DU_KY_THEO_KKT_DOI_TUONG_CU_THE}")
	private String LAY_SO_DU_KY_THEO_KKT_DOI_TUONG_CU_THE;

	@Value("${LAY_SO_DU_KY_THEO_KKT_DOI_TUONG_MA_TK}")
	private String LAY_SO_DU_KY_THEO_KKT_DOI_TUONG_MA_TK;

	@Value("${LAY_SO_DU_KY_THEO_HO_TK_KKT}")
	private String LAY_SO_DU_KY_THEO_HO_TK_KKT;

	@Value("${LAY_SO_DU_KY_THEO_TK_KKT}")
	private String LAY_SO_DU_KY_THEO_TK_KKT;

	@Value("${TINH_SO_DU_KY_THEO_DOI_TUONG}")
	private String TINH_SO_DU_KY_THEO_DOI_TUONG;

	@Value("${THEM_SO_DU_KY}")
	private String THEM_SO_DU_KY;

	@Value("${CAP_NHAT_SO_DU_DAU_KY}")
	private String CAP_NHAT_SO_DU_DAU_KY;

	@Value("${CAP_NHAT_SO_DU_CUOI_KY}")
	private String CAP_NHAT_SO_DU_CUOI_KY;

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public List<KyKeToan> danhSachKyKeToan() {
		String query = DANH_SACH_KY_KE_TOAN;

		List<KyKeToan> kyKeToanDs = jdbcTmpl.query(query, new KyKeToanMapper());

		return kyKeToanDs;
	}

	public class KyKeToanMapper implements RowMapper<KyKeToan> {
		public KyKeToan mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				KyKeToan kyKeToan = new KyKeToan();

				kyKeToan.setMaKyKt(rs.getInt("MA_KKT"));
				kyKeToan.setTenKyKt(rs.getString("TEN_KKT"));
				kyKeToan.setBatDau(rs.getDate("BAT_DAU"));
				kyKeToan.setKetThuc(rs.getDate("KET_THUC"));
				kyKeToan.setTrangThai(rs.getInt("TRANG_THAI"));
				kyKeToan.setMacDinh(rs.getInt("MAC_DINH"));

				return kyKeToan;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public KyKeToan layKyKeToan(int maKyKt) {
		String query = LAY_KY_KE_TOAN;

		try {
			Object[] objs = { maKyKt };
			KyKeToan kyKeToan = jdbcTmpl.queryForObject(query, objs, new KyKeToanMapper());

			return kyKeToan;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public KyKeToan layKyKeToan(ChungTu chungTu) {
		if (chungTu == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		String ngayHt = sdf.format(chungTu.getNgayHt());

		String query = LAY_KY_KE_TOAN_CHUNG_TU;

		try {
			Object[] objs = { ngayHt, ngayHt };
			KyKeToan kyKeToan = jdbcTmpl.queryForObject(query, objs, new KyKeToanMapper());

			return kyKeToan;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public KyKeToan layKyKeToanMacDinh() {
		String query = LAY_KY_KE_TOAN_MAC_DINH;

		try {
			List<KyKeToan> kyKeToanDs = jdbcTmpl.query(query, new KyKeToanMapper());

			KyKeToan kyKeToan = kyKeToanDs.get(0);
			kyKeToan.setSoDuKyDs(danhSachSoDuKy(kyKeToan.getMaKyKt()));

			return kyKeToan;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void capNhatKyKeToan(KyKeToan kyKeToan) {
		if (kyKeToan == null) {
			return;
		}
		String query = CAP_NHAT_KY_KE_TOAN;

		jdbcTmpl.update(query, kyKeToan.getTenKyKt(), kyKeToan.getBatDau(), kyKeToan.getKetThuc(),
				kyKeToan.getTrangThai(), kyKeToan.getMacDinh(), kyKeToan.getMaKyKt());
	}

	@Override
	public void themKyKeToan(KyKeToan kyKeToan) {
		if (kyKeToan == null) {
			return;
		}

		String query = THEM_KY_KE_TOAN;

		jdbcTmpl.update(query, kyKeToan.getTenKyKt(), kyKeToan.getBatDau(), kyKeToan.getKetThuc(),
				kyKeToan.getTrangThai(), kyKeToan.getMacDinh());
	}

	@Override
	public Date layKetThucLonNhat() {
		String query = LAY_KET_THUC_CUOI;
		try {
			Date ketThuc = jdbcTmpl.queryForObject(query, Date.class);

			return ketThuc;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Date layKetThucLonNhat(Date gioiHan) {
		if (gioiHan == null)
			return null;

		String query = LAY_KET_THUC_CUOI_GIOI_HAN;
		try {
			Object[] objs = { gioiHan };
			Date ketThuc = jdbcTmpl.queryForObject(query, objs, Date.class);

			return ketThuc;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public KyKeToan layKyKeToanTruoc(KyKeToan kyKeToan) {
		if (kyKeToan == null || kyKeToan.getBatDau() == null)
			return null;

		String query = LAY_KY_KE_TOAN_TRUOC;
		try {
			Object[] objs = { kyKeToan.getBatDau() };
			List<KyKeToan> kyKeToanDs = jdbcTmpl.query(query, objs, new KyKeToanMapper());

			return kyKeToanDs.get(0);
		} catch (Exception e) {
			return null;
		}
	}

	public List<SoDuKy> tinhSoDuKyTheoDoiTuong(KyKeToan kyKeToan, int loaiDt, String maTk) {
		if (kyKeToan == null || maTk == null || maTk.trim().equals("")) {
			return null;
		}

		String query = TINH_SO_DU_KY_THEO_DOI_TUONG;
		query = query.replaceAll("\\$MA_TK\\$", maTk);
		logger.info(query);
		logger.info("kyKeToan " + kyKeToan);
		logger.info("loaiDt " + loaiDt);
		logger.info("maTk " + maTk);

		Object[] objs = { loaiDt, kyKeToan.getBatDau(), kyKeToan.getKetThuc() };
		List<SoDuKy> soDuKyDs = jdbcTmpl.query(query, objs, new SoDuKyTruocDoiTuongMapper());
		logger.info("soDuKyDs " + soDuKyDs.size());
		List<SoDuKy> ketQua = null;
		if (soDuKyDs != null) {
			ketQua = new ArrayList<>();
			Iterator<SoDuKy> iter = soDuKyDs.iterator();
			while (iter.hasNext()) {
				SoDuKy soDuKy = iter.next();

				try {
					soDuKy.setKyKeToan(kyKeToan);

					int pos = ketQua.indexOf(soDuKy);
					if (pos > -1) {
						SoDuKy soDuKyTt = ketQua.get(pos);
						soDuKyTt.tron(soDuKy);
					} else {
						ketQua.add(soDuKy);
					}
				} catch (Exception e) {

				}
			}
		}

		logger.info("ketQua " + ketQua.size());

		return ketQua;
	}

	public class SoDuKyTruocDoiTuongMapper implements RowMapper<SoDuKy> {
		public SoDuKy mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				SoDuKy soDuKy = new SoDuKy();

				DoiTuong doiTuong = new DoiTuong();
				doiTuong.setMaDt(rs.getInt("MA_DT"));
				doiTuong.setLoaiDt(rs.getInt("LOAI_DT"));
				soDuKy.setDoiTuong(doiTuong);

				LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
				loaiTaiKhoan.setMaTk(rs.getString("MA_TK"));
				loaiTaiKhoan.setSoDu(rs.getInt("SO_DU"));
				soDuKy.setLoaiTaiKhoan(loaiTaiKhoan);

				if (loaiTaiKhoan.getSoDu() == LoaiTaiKhoan.NO) {
					soDuKy.setNoDauKy(rs.getDouble("SO_TIEN"));
				} else {
					soDuKy.setCoDauKy(rs.getDouble("SO_TIEN"));
				}

				return soDuKy;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public void macDinhKyKeToan(int maKyKt) {
		logger.info("Đặt mặc định kỳ kế toán có MA_KKT = " + maKyKt);

		String query = MAC_DINH_KY_KE_TOAN_TAT_CA;
		jdbcTmpl.update(query);

		query = MAC_DINH_KY_KE_TOAN;
		jdbcTmpl.update(query, maKyKt);
	}

	@Override
	public void dongMoKyKeToan(int maKyKt, int trangThai) {
		logger.info("Đóng/mở kỳ kế toán có MA_KKT = " + maKyKt + ", trạng thái " + trangThai);

		// Cuối cùng là xóa kỳ kế toán
		String query = DONG_MO_KY_KE_TOAN;
		jdbcTmpl.update(query, trangThai, maKyKt);
	}

	@Override
	public void xoaKyKeToan(int maKyKt) {
		logger.info("Bắt đầu xóa kỳ kế toán có MA_KKT = " + maKyKt);
		// Có thể xóa toàn bộ nghiệp vụ kế toán trong kỳ

		// Xóa số dư đầu kỳ
		String query = XOA_KY_SO_DU_DAU_KY_KE_TOAN;
		jdbcTmpl.update(query, maKyKt);

		// Cuối cùng là xóa kỳ kế toán
		query = XOA_KY_KE_TOAN;
		jdbcTmpl.update(query, maKyKt);
		logger.info("Xóa xong kỳ kế toán có MA_KKT = " + maKyKt);
	}

	@Override
	public List<SoDuKy> danhSachSoDuKy(int maKkt) {
		String query = LAY_SO_DU_KY_THEO_KKT;

		try {
			logger.info(query);
			logger.info("Mã kỳ kế toán " + maKkt);
			Object[] objs = { maKkt };
			List<SoDuKy> soDuKyDs = jdbcTmpl.query(query, objs, new SoDuKyMapper());
			return soDuKyDs;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<SoDuKy> danhSachSoDuKyTheoDoiTuong(int maKkt, int loaiDt) {
		String query = LAY_SO_DU_KY_THEO_KKT_DOI_TUONG;
		logger.info(query);
		logger.info("Loại đối tượng " + loaiDt);
		logger.info("Mã kỳ kế toán " + maKkt);

		try {
			Object[] objs = { loaiDt, maKkt, loaiDt, maKkt, loaiDt, maKkt };
			List<SoDuKy> soDuKyDs = jdbcTmpl.query(query, objs, new SoDuKyDoiTuongMapper());
			return soDuKyDs;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<SoDuKy> danhSachSoDuKyTheoDoiTuong(String maTk, int maKkt) {
		if (maTk == null) {
			return null;
		}

		String query = LAY_SO_DU_KY_THEO_KKT_DOI_TUONG_MA_TK;

		try {
			logger.info(query);
			logger.info("Mã tài khoản kế toán " + maTk);
			logger.info("Mã kỳ kế toán " + maKkt);

			Object[] objs = { maTk, maKkt, maTk, maKkt, maTk, maKkt };
			List<SoDuKy> soDuKyDs = jdbcTmpl.query(query, objs, new SoDuKyDoiTuongMapper());
			return soDuKyDs;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<SoDuKy> danhSachSoDuKy(String maTk, int maKkt) {
		String query = LAY_SO_DU_KY_THEO_HO_TK_KKT;
		query = query.replaceAll("\\$MA_TK\\$", maTk);

		try {
			Object[] objs = { maKkt };
			List<SoDuKy> soDuKyDs = jdbcTmpl.query(query, objs, new SoDuKyMapper());
			return soDuKyDs;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public SoDuKy laySoDuKy(String maTk, int maKkt) {
		String query = LAY_SO_DU_KY_THEO_TK_KKT;

		try {
			Object[] objs = { maTk, maKkt };
			SoDuKy soDuKy = jdbcTmpl.queryForObject(query, objs, new SoDuKyMapper());

			return soDuKy;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public SoDuKy laySoDuKyTheoDoiTuong(String maTk, int maKkt, int loaiDt, int maDt) {
		if (maTk == null) {
			return null;
		}

		String query = LAY_SO_DU_KY_THEO_KKT_DOI_TUONG_CU_THE;
		logger.info(query);
		logger.info("Loại đối tượng " + loaiDt);
		logger.info("Mã đối tượng " + maDt);
		logger.info("Mã tài khoản " + maTk);
		logger.info("Mã kỳ kế toán " + maKkt);

		try {
			Object[] objs = { loaiDt, maDt, maTk, maKkt, loaiDt, maDt, maTk, maKkt, loaiDt, maDt, maTk, maKkt };
			SoDuKy soDuKy = jdbcTmpl.queryForObject(query, objs, new SoDuKyDoiTuongMapper());

			return soDuKy;
		} catch (Exception e) {
			return null;
		}
	}

	public class SoDuKyMapper implements RowMapper<SoDuKy> {
		public SoDuKy mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				SoDuKy soDuKy = new SoDuKy();
				soDuKy.setNoDauKy(rs.getDouble("NO_DAU_KY"));
				soDuKy.setCoDauKy(rs.getDouble("CO_DAU_KY"));
				soDuKy.setNoCuoiKy(rs.getDouble("NO_CUOI_KY"));
				soDuKy.setCoCuoiKy(rs.getDouble("CO_CUOI_KY"));

				KyKeToan kyKeToan = new KyKeToan();
				kyKeToan.setMaKyKt(rs.getInt("MA_KKT"));
				kyKeToan.setTenKyKt(rs.getString("TEN_KKT"));
				kyKeToan.setBatDau(rs.getDate("BAT_DAU"));
				kyKeToan.setKetThuc(rs.getDate("KET_THUC"));
				kyKeToan.setTrangThai(rs.getInt("TRANG_THAI"));
				kyKeToan.setMacDinh(rs.getInt("MAC_DINH"));
				soDuKy.setKyKeToan(kyKeToan);

				LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
				loaiTaiKhoan.setMaTk(rs.getString("MA_TK"));
				loaiTaiKhoan.setTenTk(rs.getString("TEN_TK"));
				loaiTaiKhoan.setMaTenTk(rs.getString("MA_TK") + " - " + rs.getString("TEN_TK"));
				loaiTaiKhoan.setMaTkCha(rs.getString("MA_TK_CHA"));
				loaiTaiKhoan.setSoDu(rs.getInt("SO_DU"));
				loaiTaiKhoan.setLuongTinh(rs.getBoolean("LUONG_TINH"));

				NganHangTaiKhoan nganHangTaiKhoan = new NganHangTaiKhoan();
				nganHangTaiKhoan.setMaTk(rs.getInt("MA_TK_NH"));
				loaiTaiKhoan.setNganHangTaiKhoan(nganHangTaiKhoan);

				soDuKy.setLoaiTaiKhoan(loaiTaiKhoan);

				return soDuKy;
			} catch (Exception e) {
				return null;
			}
		}
	}

	public class SoDuKyDoiTuongMapper implements RowMapper<SoDuKy> {
		public SoDuKy mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				SoDuKy soDuKy = new SoDuKy();
				soDuKy.setNoDauKy(rs.getDouble("NO_DAU_KY"));
				soDuKy.setCoDauKy(rs.getDouble("CO_DAU_KY"));
				soDuKy.setNoCuoiKy(rs.getDouble("NO_CUOI_KY"));
				soDuKy.setCoCuoiKy(rs.getDouble("CO_CUOI_KY"));

				KyKeToan kyKeToan = new KyKeToan();
				kyKeToan.setMaKyKt(rs.getInt("MA_KKT"));
				kyKeToan.setTenKyKt(rs.getString("TEN_KKT"));
				kyKeToan.setBatDau(rs.getDate("BAT_DAU"));
				kyKeToan.setKetThuc(rs.getDate("KET_THUC"));
				kyKeToan.setTrangThai(rs.getInt("TRANG_THAI"));
				kyKeToan.setMacDinh(rs.getInt("MAC_DINH"));
				soDuKy.setKyKeToan(kyKeToan);

				DoiTuong doiTuong = new DoiTuong();
				doiTuong.setMaDt(rs.getInt("MA_DT"));
				doiTuong.setTenDt(rs.getString("TEN_DT"));
				doiTuong.setLoaiDt(rs.getInt("LOAI_DT"));
				doiTuong.setDiaChi(rs.getString("DIA_CHI"));
				doiTuong.setMaThue(rs.getString("MA_THUE"));
				soDuKy.setDoiTuong(doiTuong);

				LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
				loaiTaiKhoan.setMaTk(rs.getString("MA_TK"));
				loaiTaiKhoan.setTenTk(rs.getString("TEN_TK"));
				loaiTaiKhoan.setMaTenTk(rs.getString("MA_TK") + " - " + rs.getString("TEN_TK"));
				loaiTaiKhoan.setMaTkCha(rs.getString("MA_TK_CHA"));
				loaiTaiKhoan.setSoDu(rs.getInt("SO_DU"));
				loaiTaiKhoan.setLuongTinh(rs.getBoolean("LUONG_TINH"));

				NganHangTaiKhoan nganHangTaiKhoan = new NganHangTaiKhoan();
				nganHangTaiKhoan.setMaTk(rs.getInt("MA_TK_NH"));
				loaiTaiKhoan.setNganHangTaiKhoan(nganHangTaiKhoan);

				soDuKy.setLoaiTaiKhoan(loaiTaiKhoan);

				return soDuKy;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public void themSoDuDauKy(SoDuKy soDuKy) {
		if (soDuKy == null || soDuKy.getKyKeToan() == null || soDuKy.getLoaiTaiKhoan() == null) {
			return;
		}

		try {
			// Thêm mới
			String query = THEM_SO_DU_KY;

			jdbcTmpl.update(query, soDuKy.getKyKeToan().getMaKyKt(), soDuKy.getLoaiTaiKhoan().getMaTk(),
					soDuKy.getDoiTuong().getLoaiDt(), soDuKy.getDoiTuong().getMaDt(), soDuKy.getNoDauKy(),
					soDuKy.getCoDauKy(), soDuKy.getNoCuoiKy(), soDuKy.getCoCuoiKy());
		} catch (Exception e) {
			// Nếu đã có thì cập nhật
			String query = CAP_NHAT_SO_DU_DAU_KY;

			jdbcTmpl.update(query, soDuKy.getNoDauKy(), soDuKy.getCoDauKy(), soDuKy.getKyKeToan().getMaKyKt(),
					soDuKy.getLoaiTaiKhoan().getMaTk(), soDuKy.getDoiTuong().getLoaiDt(),
					soDuKy.getDoiTuong().getMaDt());
		}

	}
}
