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
import com.idi.finance.bean.doituong.DoiTuong;
import com.idi.finance.bean.doituong.NganHangTaiKhoan;
import com.idi.finance.bean.hanghoa.DonVi;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.hanghoa.KhoHang;
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

	@Value("${LAY_SO_DU_KY_THEO_KKT_DOI_TUONG_MA_TK_CON}")
	private String LAY_SO_DU_KY_THEO_KKT_DOI_TUONG_MA_TK_CON;

	@Value("${LAY_SO_DU_KY_THEO_KKT_HANG_HOA}")
	private String LAY_SO_DU_KY_THEO_KKT_HANG_HOA;

	@Value("${LAY_SO_DU_KY_THEO_KKT_HANG_HOA_MA_TK_CON_KHO}")
	private String LAY_SO_DU_KY_THEO_KKT_HANG_HOA_MA_TK_CON_KHO;

	@Value("${LAY_SO_DU_KY_THEO_KKT_HANG_HOA_MA_TK}")
	private String LAY_SO_DU_KY_THEO_KKT_HANG_HOA_MA_TK;

	@Value("${LAY_SO_DU_KY_THEO_KKT_HANG_HOA_MA_TK_KHO}")
	private String LAY_SO_DU_KY_THEO_KKT_HANG_HOA_MA_TK_KHO;

	@Value("${LAY_SO_DU_KY_THEO_KKT_HANG_HOA_CU_THE}")
	private String LAY_SO_DU_KY_THEO_KKT_HANG_HOA_CU_THE;

	@Value("${LAY_SO_DU_KY_THEO_HO_TK_KKT}")
	private String LAY_SO_DU_KY_THEO_HO_TK_KKT;

	@Value("${LAY_SO_DU_KY_THEO_TK_KKT}")
	private String LAY_SO_DU_KY_THEO_TK_KKT;

	@Value("${TINH_SO_DU_KY_THEO_DOI_TUONG}")
	private String TINH_SO_DU_KY_THEO_DOI_TUONG;

	@Value("${TINH_SO_DU_KY_KTTH_THEO_DOI_TUONG}")
	private String TINH_SO_DU_KY_KTTH_THEO_DOI_TUONG;

	@Value("${THEM_SO_DU_KY}")
	private String THEM_SO_DU_KY;

	@Value("${CAP_NHAT_SO_DU_DAU_KY}")
	private String CAP_NHAT_SO_DU_DAU_KY;

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
		String ktthQuery = TINH_SO_DU_KY_KTTH_THEO_DOI_TUONG;
		query = query.replaceAll("\\$MA_TK\\$", maTk);
		ktthQuery = query.replaceAll("\\$MA_TK\\$", maTk);
		logger.info(query);
		logger.info(ktthQuery);
		logger.info("kyKeToan " + kyKeToan);
		logger.info("loaiDt " + loaiDt);
		logger.info("maTk " + maTk);

		Object[] objs = { loaiDt, kyKeToan.getBatDau(), kyKeToan.getKetThuc(), loaiDt, kyKeToan.getBatDau(),
				kyKeToan.getKetThuc(), loaiDt, kyKeToan.getBatDau(), kyKeToan.getKetThuc() };
		List<SoDuKy> soDuKyDs = jdbcTmpl.query(query, objs, new SoDuKyTruocDoiTuongMapper());
		List<SoDuKy> soDuKyKtthDs = jdbcTmpl.query(ktthQuery, objs, new SoDuKyTruocDoiTuongMapper());

		List<SoDuKy> ketQua = new ArrayList<>();
		if (soDuKyDs != null) {
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

		if (soDuKyKtthDs != null) {
			Iterator<SoDuKy> iter = soDuKyKtthDs.iterator();
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
				doiTuong.setKhDt(rs.getString("KH_DT"));
				doiTuong.setTenDt(rs.getString("TEN_DT"));
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
	public List<SoDuKy> danhSachSoDuKy(String maTkCon, int maKkt) {
		String query = LAY_SO_DU_KY_THEO_HO_TK_KKT;

		try {
			Object[] objs = { maTkCon, maKkt };
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
	public List<SoDuKy> danhSachSoDuKyTheoDoiTuong(String maTkCon, int maKkt, int loaiDt, int maDt) {
		if (maTkCon == null) {
			return null;
		}

		String query = LAY_SO_DU_KY_THEO_KKT_DOI_TUONG_MA_TK_CON;

		try {
			logger.info(query);
			logger.info("Mã tài khoản kế toán con " + maTkCon);
			logger.info("Mã kỳ kế toán " + maKkt);
			logger.info("Loại đối tượng " + loaiDt);
			logger.info("Mã đối tượng " + maDt);

			Object[] objs = { maTkCon, maKkt, loaiDt, maDt, maTkCon, maKkt, loaiDt, maDt, maTkCon, maKkt, loaiDt,
					maDt };
			return jdbcTmpl.query(query, objs, new SoDuKyDoiTuongMapper());
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<SoDuKy> danhSachSoDuKyTheoHangHoa(int maKkt) {
		String query = LAY_SO_DU_KY_THEO_KKT_HANG_HOA;

		try {
			logger.info(query);
			logger.info("Mã kỳ kế toán " + maKkt);

			Object[] objs = { maKkt };
			return jdbcTmpl.query(query, objs, new SoDuKyHangHoaKhoMapper());
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<SoDuKy> danhSachSoDuKyTheoHangHoa(String maTk, int maKkt) {
		if (maTk == null) {
			return null;
		}
		String query = LAY_SO_DU_KY_THEO_KKT_HANG_HOA_MA_TK;

		try {
			logger.info(query);
			logger.info("Mã kỳ kế toán " + maKkt);
			logger.info("Mã tài khoản " + maTk);

			Object[] objs = { maKkt, maTk };
			return jdbcTmpl.query(query, objs, new SoDuKyHangHoaKhoMapper());
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<SoDuKy> danhSachSoDuKyTheoHangHoa(String maTk, int maKkt, List<Integer> maKhoDs) {
		if (maTk == null || maKhoDs == null) {
			return null;
		}

		String query = LAY_SO_DU_KY_THEO_KKT_HANG_HOA_MA_TK_KHO;

		try {
			String dieuKienKho = "";
			for (Iterator<Integer> iter = maKhoDs.iterator(); iter.hasNext();) {
				Integer maKho = iter.next();
				dieuKienKho += maKho + ", ";
			}
			if (!dieuKienKho.isEmpty()) {
				dieuKienKho = dieuKienKho.substring(0, dieuKienKho.length() - 1);
			}
			query = query.replace("$DIEU_KIEN_MA_KHO$", dieuKienKho);

			logger.info(query);
			logger.info("Mã kỳ kế toán " + maKkt);
			logger.info("Mã tài khoản " + maTk);
			logger.info("Mã kho " + maKhoDs);

			Object[] objs = { maKkt, maTk };
			return jdbcTmpl.query(query, objs, new SoDuKyHangHoaKhoMapper());
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<SoDuKy> danhSachSoDuKyTheoHangHoa(String maTk, int maKkt, int maHh, int maKho) {
		if (maTk == null) {
			return null;
		}
		String query = LAY_SO_DU_KY_THEO_KKT_HANG_HOA_MA_TK_CON_KHO;

		try {
			logger.info(query);
			logger.info("Mã kỳ kế toán " + maKkt);
			logger.info("Mã tài khoản " + maTk);
			logger.info("Mã hàng hóa " + maHh);
			logger.info("Mã kho " + maKho);

			Object[] objs = { maKkt, maTk, maHh, maKho };
			return jdbcTmpl.query(query, objs, new SoDuKyHangHoaKhoMapper());
		} catch (Exception e) {
			// e.printStackTrace();
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
		logger.info("Mã tài khoản " + maTk);
		logger.info("Mã kỳ kế toán " + maKkt);
		logger.info("Loại đối tượng " + loaiDt);
		logger.info("Mã đối tượng " + maDt);

		try {
			Object[] objs = { loaiDt, maDt, maTk, maKkt, loaiDt, maDt, maTk, maKkt, loaiDt, maDt, maTk, maKkt };
			return jdbcTmpl.queryForObject(query, objs, new SoDuKyDoiTuongMapper());
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	@Override
	public SoDuKy laySoDuKyTheoHangHoa(String maTk, int maKkt, int maHh, int maKho) {
		if (maTk == null) {
			return null;
		}

		String query = LAY_SO_DU_KY_THEO_KKT_HANG_HOA_CU_THE;
		logger.info(query);
		logger.info("Mã tài khoản " + maTk);
		logger.info("Mã kỳ kế toán " + maKkt);
		logger.info("Mã hàng hóa " + maHh);
		logger.info("Mã kho " + maKho);

		try {
			Object[] objs = { maKkt, maTk, maHh, maKho };
			return jdbcTmpl.queryForObject(query, objs, new SoDuKyHangHoaKhoMapper());
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	public class SoDuKyMapper implements RowMapper<SoDuKy> {
		public SoDuKy mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				LoaiTien loaiTien = new LoaiTien();
				loaiTien.setMaLt(rs.getString("MA_NT"));
				loaiTien.setTenLt(rs.getString("TEN_NT"));

				SoDuKy soDuKy = new SoDuKy();
				soDuKy.setNoDauKy(rs.getDouble("NO_DAU_KY"));
				soDuKy.setNoDauKyNt(rs.getDouble("NO_DAU_KY_NT"));
				soDuKy.setCoDauKy(rs.getDouble("CO_DAU_KY"));
				soDuKy.setCoDauKyNt(rs.getDouble("CO_DAU_KY_NT"));
				soDuKy.setNoCuoiKy(rs.getDouble("NO_CUOI_KY"));
				soDuKy.setNoCuoiKyNt(rs.getDouble("NO_CUOI_KY_NT"));
				soDuKy.setCoCuoiKy(rs.getDouble("CO_CUOI_KY"));
				soDuKy.setCoCuoiKyNt(rs.getDouble("CO_CUOI_KY_NT"));
				soDuKy.setLoaiTien(loaiTien);

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
				LoaiTien loaiTien = new LoaiTien();
				loaiTien.setMaLt(rs.getString("MA_NT"));
				loaiTien.setTenLt(rs.getString("TEN_NT"));

				SoDuKy soDuKy = new SoDuKy();
				soDuKy.setNoDauKy(rs.getDouble("NO_DAU_KY"));
				soDuKy.setNoDauKyNt(rs.getDouble("NO_DAU_KY_NT"));
				soDuKy.setCoDauKy(rs.getDouble("CO_DAU_KY"));
				soDuKy.setCoDauKyNt(rs.getDouble("CO_DAU_KY_NT"));
				soDuKy.setNoCuoiKy(rs.getDouble("NO_CUOI_KY"));
				soDuKy.setNoCuoiKyNt(rs.getDouble("NO_CUOI_KY_NT"));
				soDuKy.setCoCuoiKy(rs.getDouble("CO_CUOI_KY"));
				soDuKy.setCoCuoiKyNt(rs.getDouble("CO_CUOI_KY_NT"));
				soDuKy.setLoaiTien(loaiTien);

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
				doiTuong.setKhDt(rs.getString("KH_DT"));
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
				e.printStackTrace();
				return null;
			}
		}
	}

	public class SoDuKyHangHoaKhoMapper implements RowMapper<SoDuKy> {
		public SoDuKy mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				LoaiTien loaiTien = new LoaiTien();
				loaiTien.setMaLt(rs.getString("MA_NT"));
				loaiTien.setTenLt(rs.getString("TEN_NT"));

				SoDuKy soDuKy = new SoDuKy();
				soDuKy.setNoDauKy(rs.getDouble("NO_DAU_KY"));
				soDuKy.setNoDauKyNt(rs.getDouble("NO_DAU_KY_NT"));
				soDuKy.setCoDauKy(rs.getDouble("CO_DAU_KY"));
				soDuKy.setCoDauKyNt(rs.getDouble("CO_DAU_KY_NT"));
				soDuKy.setNoCuoiKy(rs.getDouble("NO_CUOI_KY"));
				soDuKy.setNoCuoiKyNt(rs.getDouble("NO_CUOI_KY_NT"));
				soDuKy.setCoCuoiKy(rs.getDouble("CO_CUOI_KY"));
				soDuKy.setCoCuoiKyNt(rs.getDouble("CO_CUOI_KY_NT"));
				soDuKy.setLoaiTien(loaiTien);

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

				soDuKy.setLoaiTaiKhoan(loaiTaiKhoan);

				HangHoa hangHoa = new HangHoa();
				hangHoa.setMaHh(rs.getInt("MA_HH"));
				hangHoa.setKyHieuHh(rs.getString("KH_HH"));
				hangHoa.setTenHh(rs.getString("TEN_HH"));
				hangHoa.setKyHieuTenHh(rs.getString("KH_HH") + " - " + rs.getString("TEN_HH"));
				hangHoa.setSoLuong(rs.getDouble("SO_LUONG"));

				DonVi donVi = new DonVi();
				donVi.setMaDv(rs.getInt("MA_DV"));
				donVi.setTenDv(rs.getString("TEN_DV"));
				donVi.setMoTa(rs.getString("MO_TA"));
				hangHoa.setDonVi(donVi);

				KhoHang khoHang = new KhoHang();
				khoHang.setMaKho(rs.getInt("MA_KHO"));
				khoHang.setKyHieuKho(rs.getString("KH_KHO"));
				khoHang.setTenKho(rs.getString("TEN_KHO"));
				hangHoa.setKho(khoHang);

				soDuKy.setHangHoa(hangHoa);
				soDuKy.setKhoHang(khoHang);

				return soDuKy;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	@Override
	public void themCapNhatSoDuDauKy(SoDuKy soDuKy) {
		if (soDuKy == null || soDuKy.getKyKeToan() == null || soDuKy.getLoaiTaiKhoan() == null) {
			return;
		}

		int maDt = soDuKy.getDoiTuong() != null ? soDuKy.getDoiTuong().getMaDt() : 0;
		int loaiDt = soDuKy.getDoiTuong() != null ? soDuKy.getDoiTuong().getLoaiDt() : 0;
		int maHh = soDuKy.getHangHoa() != null ? soDuKy.getHangHoa().getMaHh() : 0;
		int maKho = soDuKy.getKhoHang() != null ? soDuKy.getKhoHang().getMaKho() : 0;
		double soLuong = soDuKy.getHangHoa() != null ? soDuKy.getHangHoa().getSoLuong() : 0;
		int maDv = soDuKy.getHangHoa() != null && soDuKy.getHangHoa().getDonVi() != null
				? soDuKy.getHangHoa().getDonVi().getMaDv()
				: 0;

		try {
			// Thêm mới
			String query = THEM_SO_DU_KY;
			logger.info(query);

			jdbcTmpl.update(query, soDuKy.getKyKeToan().getMaKyKt(), soDuKy.getLoaiTaiKhoan().getMaTk(), loaiDt, maDt,
					maHh, maKho, soDuKy.getNoDauKy(), soDuKy.getNoDauKyNt(), soDuKy.getCoDauKy(), soDuKy.getCoDauKyNt(),
					soDuKy.getLoaiTien().getMaLt(), soLuong, maDv);
		} catch (Exception e) {
			// e.printStackTrace();
			// Nếu đã có thì cập nhật
			String query = CAP_NHAT_SO_DU_DAU_KY;
			logger.info(query);

			jdbcTmpl.update(query, soDuKy.getNoDauKy(), soDuKy.getNoDauKyNt(), soDuKy.getCoDauKy(),
					soDuKy.getCoDauKyNt(), soDuKy.getLoaiTien().getMaLt(), soLuong, maDv,
					soDuKy.getKyKeToan().getMaKyKt(), soDuKy.getLoaiTaiKhoan().getMaTk(), loaiDt, maDt, maHh, maKho);
		}
	}
}
