package com.idi.finance.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.idi.finance.bean.Tien;
import com.idi.finance.bean.bctc.BalanceAssetItem;
import com.idi.finance.bean.bctc.BaoCaoTaiChinh;
import com.idi.finance.bean.bctc.BaoCaoTaiChinhChiTiet;
import com.idi.finance.bean.bctc.BaoCaoTaiChinhCon;
import com.idi.finance.bean.bctc.DuLieuKeToan;
import com.idi.finance.bean.kyketoan.KyKeToan;
import com.idi.finance.dao.BaoCaoTaiChinhDAO;

public class BaoCaoTaiChinhDAOImpl implements BaoCaoTaiChinhDAO {
	private static final Logger logger = Logger.getLogger(BaoCaoTaiChinhDAOImpl.class);

	@Value("${DANH_SACH_BCTC}")
	private String DANH_SACH_BCTC;

	@Value("${THEM_BCTC}")
	private String THEM_BCTC;

	@Value("${THEM_BCTC_CON}")
	private String THEM_BCTC_CON;

	@Value("${THEM_BCTC_CHI_TIET}")
	private String THEM_BCTC_CHI_TIET;

	@Value("${CAP_NHAT_BCTC_CHI_TIET}")
	private String CAP_NHAT_BCTC_CHI_TIET;

	@Value("${LAY_BCTC}")
	private String LAY_BCTC;

	@Value("${LAY_BCTC_CON_CDKT}")
	private String LAY_BCTC_CON_CDKT;

	@Value("${LAY_BCTC_CON_KQHDKD}")
	private String LAY_BCTC_CON_KQHDKD;

	@Value("${LAY_BCTC_CON_LCTT}")
	private String LAY_BCTC_CON_LCTT;

	@Value("${LAY_BCTC_CDKT_CHI_TIET}")
	private String LAY_BCTC_CDKT_CHI_TIET;

	@Value("${LAY_BCTC_KQHDKD_CHI_TIET}")
	private String LAY_BCTC_KQHDKD_CHI_TIET;

	@Value("${LAY_BCTC_LCTT_CHI_TIET}")
	private String LAY_BCTC_LCTT_CHI_TIET;

	@Value("${XOA_BCTC}")
	private String XOA_BCTC;

	@Value("${XOA_BCTC_CON}")
	private String XOA_BCTC_CON;

	@Value("${XOA_BCTC_CHI_TIET}")
	private String XOA_BCTC_CHI_TIET;

	@Value("${TINH_CDKT_CUOI_KY}")
	private String TINH_CDKT_CUOI_KY;

	@Value("${TINH_KQHDKD_THEO_MATK}")
	private String TINH_KQHDKD_THEO_MATK;

	@Value("${TINH_LCTT_THEO_MATK}")
	private String TINH_LCTT_THEO_MATK;

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public List<BaoCaoTaiChinh> danhSachBctc(int maKkt) {
		String query = DANH_SACH_BCTC;

		logger.info("Danh sách báo cáo tài chính thuộc kỳ kế toán: " + maKkt);
		logger.info(query);

		Object[] objs = { maKkt };
		List<BaoCaoTaiChinh> bctcDs = jdbcTmpl.query(query, objs, new BaoCaoTaiChinhMapper());

		return bctcDs;
	}

	public class BaoCaoTaiChinhMapper implements RowMapper<BaoCaoTaiChinh> {
		public BaoCaoTaiChinh mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				BaoCaoTaiChinh bctc = new BaoCaoTaiChinh();

				bctc.setMaBctc(rs.getInt("MA_BCTC"));
				bctc.setTieuDe(rs.getString("TIEU_DE"));
				bctc.setLoaiTg(rs.getInt("LOAI_TG"));
				bctc.setBatDau(rs.getDate("BAT_DAU"));
				bctc.setKetThuc(rs.getDate("KET_THUC"));
				bctc.setNguoiLap(rs.getString("NGUOI_LAP"));
				bctc.setGiamDoc(rs.getString("GIAM_DOC"));
				bctc.setNgayLap(rs.getDate("NGAY_LAP"));

				KyKeToan kyKeToan = new KyKeToan();
				kyKeToan.setMaKyKt(rs.getInt("MA_KKT"));
				bctc.setKyKeToan(kyKeToan);

				logger.info(bctc);
				return bctc;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public BaoCaoTaiChinh layBctc(int maBctc) {
		String layBctc = LAY_BCTC;
		String layBctcChiTiet = LAY_BCTC_CDKT_CHI_TIET;

		logger.info("Lấy báo cáo tài chính có MA_BCTC: " + maBctc);
		logger.info(layBctc);

		Object[] objs = { maBctc };
		List<BaoCaoTaiChinh> bctcDs = jdbcTmpl.query(layBctc, objs, new BaoCaoTaiChinhDayDuMapper());

		BaoCaoTaiChinh ketQua = null;

		if (bctcDs != null) {
			logger.info("Trộn kết quả thành một báo cáo tài chính");
			for (BaoCaoTaiChinh bctc : bctcDs) {
				if (ketQua == null) {
					ketQua = bctc;
				} else {
					ketQua.themBctcCon(bctc.getBctcDs());
				}
			}
		}

		logger.info("Lấy báo cáo tài chính chi tiết");
		if (ketQua != null && ketQua.getBctcDs() != null) {
			for (BaoCaoTaiChinhCon bctcCon : ketQua.getBctcDs()) {
				switch (bctcCon.getLoaiBctc()) {
				case BaoCaoTaiChinhCon.LOAI_CDKT:
					layBctcChiTiet = LAY_BCTC_CDKT_CHI_TIET;
					break;
				case BaoCaoTaiChinhCon.LOAI_KQHDKD:
					layBctcChiTiet = LAY_BCTC_KQHDKD_CHI_TIET;
					break;
				case BaoCaoTaiChinhCon.LOAI_LCTT:
					layBctcChiTiet = LAY_BCTC_LCTT_CHI_TIET;
					break;
				case BaoCaoTaiChinhCon.LOAI_CDPS:
					break;
				default:
					break;
				}

				logger.info(layBctcChiTiet);
				Object[] objTmpl = { bctcCon.getMaBctcCon() };
				List<BaoCaoTaiChinhChiTiet> bctcChiTietDs = jdbcTmpl.query(layBctcChiTiet, objTmpl,
						new BaoCaoTaiChinhChiTietDayDuMapper());
				for (BaoCaoTaiChinhChiTiet bctcChiTiet : bctcChiTietDs) {
					bctcChiTiet.setBctc(bctcCon);
				}

				bctcCon.setChiTietDs(bctcChiTietDs);
			}
		}

		return ketQua;
	};

	public class BaoCaoTaiChinhDayDuMapper implements RowMapper<BaoCaoTaiChinh> {
		public BaoCaoTaiChinh mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				BaoCaoTaiChinh bctc = new BaoCaoTaiChinh();

				bctc.setMaBctc(rs.getInt("MA_BCTC"));
				bctc.setTieuDe(rs.getString("TIEU_DE"));
				bctc.setLoaiTg(rs.getInt("LOAI_TG"));
				bctc.setBatDau(rs.getDate("BAT_DAU"));
				bctc.setKetThuc(rs.getDate("KET_THUC"));
				bctc.setNguoiLap(rs.getString("NGUOI_LAP"));
				bctc.setGiamDoc(rs.getString("GIAM_DOC"));
				bctc.setNgayLap(rs.getDate("NGAY_LAP"));

				KyKeToan kyKeToan = new KyKeToan();
				kyKeToan.setMaKyKt(rs.getInt("MA_KKT"));
				bctc.setKyKeToan(kyKeToan);

				BaoCaoTaiChinhCon bctcCon = new BaoCaoTaiChinhCon();
				bctcCon.setMaBctcCon(rs.getInt("MA_BCTC_CON"));
				bctcCon.setLoaiBctc(rs.getInt("LOAI_BCTC"));
				bctcCon.setBctc(bctc);

				bctc.themBctcCon(bctcCon);

				logger.info(bctc);
				return bctc;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	@Override
	public BaoCaoTaiChinhCon layBctcConCdkt(int maBctcCon) {
		String layBctcCon = LAY_BCTC_CON_CDKT;

		logger.info("Lấy báo cáo tài chính con CDKT có MA_BCTC_CON: " + maBctcCon);
		logger.info(layBctcCon);

		Object[] objs = { maBctcCon };
		List<BaoCaoTaiChinhCon> bctcConDs = jdbcTmpl.query(layBctcCon, objs, new BaoCaoTaiChinhConMapper());

		BaoCaoTaiChinhCon ketQua = null;
		if (bctcConDs != null) {
			logger.info("Trộn kết quả thành một báo cáo tài chính con");
			for (BaoCaoTaiChinhCon bctcCon : bctcConDs) {
				if (ketQua == null) {
					ketQua = bctcCon;
				} else {
					ketQua.themBctcChiTiet(bctcCon.getChiTietDs());
				}
			}
		}

		return ketQua;
	}

	@Override
	public BaoCaoTaiChinhCon layBctcConKqhdkd(int maBctcCon) {
		String layBctcCon = LAY_BCTC_CON_KQHDKD;

		logger.info("Lấy báo cáo tài chính con KQHDKD có MA_BCTC_CON: " + maBctcCon);
		logger.info(layBctcCon);

		Object[] objs = { maBctcCon };
		List<BaoCaoTaiChinhCon> bctcConDs = jdbcTmpl.query(layBctcCon, objs, new BaoCaoTaiChinhConMapper());

		BaoCaoTaiChinhCon ketQua = null;
		if (bctcConDs != null) {
			logger.info("Trộn kết quả thành một báo cáo tài chính con");
			for (BaoCaoTaiChinhCon bctcCon : bctcConDs) {
				if (ketQua == null) {
					ketQua = bctcCon;
				} else {
					ketQua.themBctcChiTiet(bctcCon.getChiTietDs());
				}
			}
		}

		return ketQua;
	}

	@Override
	public BaoCaoTaiChinhCon layBctcConLctt(int maBctcCon) {
		String layBctcCon = LAY_BCTC_CON_LCTT;

		logger.info("Lấy báo cáo tài chính con LCTT có MA_BCTC_CON: " + maBctcCon);
		logger.info(layBctcCon);

		Object[] objs = { maBctcCon };
		List<BaoCaoTaiChinhCon> bctcConDs = jdbcTmpl.query(layBctcCon, objs, new BaoCaoTaiChinhConMapper());

		BaoCaoTaiChinhCon ketQua = null;
		if (bctcConDs != null) {
			logger.info("Trộn kết quả thành một báo cáo tài chính con");
			for (BaoCaoTaiChinhCon bctcCon : bctcConDs) {
				if (ketQua == null) {
					ketQua = bctcCon;
				} else {
					ketQua.themBctcChiTiet(bctcCon.getChiTietDs());
				}
			}
		}

		return ketQua;
	}

	public class BaoCaoTaiChinhConMapper implements RowMapper<BaoCaoTaiChinhCon> {
		public BaoCaoTaiChinhCon mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				BaoCaoTaiChinh bctc = new BaoCaoTaiChinh();
				bctc.setMaBctc(rs.getInt("MA_BCTC"));
				bctc.setTieuDe(rs.getString("TIEU_DE"));
				bctc.setLoaiTg(rs.getInt("LOAI_TG"));
				bctc.setBatDau(rs.getDate("BAT_DAU"));
				bctc.setKetThuc(rs.getDate("KET_THUC"));
				bctc.setNguoiLap(rs.getString("NGUOI_LAP"));
				bctc.setGiamDoc(rs.getString("GIAM_DOC"));
				bctc.setNgayLap(rs.getDate("NGAY_LAP"));

				KyKeToan kyKeToan = new KyKeToan();
				kyKeToan.setMaKyKt(rs.getInt("MA_KKT"));
				bctc.setKyKeToan(kyKeToan);

				BaoCaoTaiChinhCon bctcCon = new BaoCaoTaiChinhCon();
				bctcCon.setMaBctcCon(rs.getInt("MA_BCTC_CON"));
				bctcCon.setLoaiBctc(rs.getInt("LOAI_BCTC"));
				bctcCon.setBctc(bctc);

				bctc.themBctcCon(bctcCon);

				BaoCaoTaiChinhChiTiet bctcChiTiet = new BaoCaoTaiChinhChiTiet();
				bctcChiTiet.getAsset().setAssetCode(rs.getString("ASSET_CODE"));
				bctcChiTiet.getAsset().setAssetName(rs.getString("ASSET_NAME"));
				bctcChiTiet.getGiaTri().setGiaTri(rs.getDouble("GIA_TRI"));
				bctcChiTiet.getGiaTriKyTruoc().setGiaTri(rs.getDouble("GIA_TRI_KY_TRUOC"));
				bctcChiTiet.setBctc(bctcCon);

				bctcCon.themBctcChiTiet(bctcChiTiet);

				logger.info(bctcCon);
				return bctcCon;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public class BaoCaoTaiChinhChiTietDayDuMapper implements RowMapper<BaoCaoTaiChinhChiTiet> {
		public BaoCaoTaiChinhChiTiet mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				BaoCaoTaiChinhChiTiet bctcChiTiet = new BaoCaoTaiChinhChiTiet();

				bctcChiTiet.getBctc().setMaBctcCon(rs.getInt("MA_BCTC_CON"));
				bctcChiTiet.getAsset().setAssetCode(rs.getString("ASSET_CODE"));
				bctcChiTiet.getAsset().setAssetName(rs.getString("ASSET_NAME"));
				bctcChiTiet.getGiaTri().setGiaTri(rs.getDouble("GIA_TRI"));
				bctcChiTiet.getGiaTriKyTruoc().setGiaTri(rs.getDouble("GIA_TRI_KY_TRUOC"));

				logger.info(bctcChiTiet);
				return bctcChiTiet;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	@Override
	@Transactional
	public BaoCaoTaiChinh themBctc(BaoCaoTaiChinh bctc) {
		if (bctc == null) {
			return bctc;
		}
		logger.info("Tạo mới báo cáo tài chính ...");

		String themBctc = THEM_BCTC;
		String themBctcCon = THEM_BCTC_CON;

		logger.info("Thêm vào bảng BAO_CAO_TAI_CHINH");
		logger.info(themBctc);
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		jdbcTmpl.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement stt = con.prepareStatement(themBctc, Statement.RETURN_GENERATED_KEYS);
				stt.setString(1, bctc.getTieuDe());
				stt.setInt(2, bctc.getLoaiTg());
				stt.setDate(3, new java.sql.Date(bctc.getBatDau().getTime()));
				stt.setDate(4, new java.sql.Date(bctc.getKetThuc().getTime()));
				stt.setString(5, bctc.getNguoiLap());
				stt.setString(6, bctc.getGiamDoc());
				stt.setDate(7, new java.sql.Date(bctc.getNgayLap().getTime()));
				stt.setInt(8, bctc.getKyKeToan().getMaKyKt());

				return stt;
			}
		}, holder);
		bctc.setMaBctc(holder.getKey().intValue());

		if (bctc.getBctcDs() != null) {
			for (BaoCaoTaiChinhCon bctcCon : bctc.getBctcDs()) {
				logger.info("Thêm vào bảng BAO_CAO_TAI_CHINH_CON");
				logger.info(themBctcCon);
				holder = new GeneratedKeyHolder();
				jdbcTmpl.update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						PreparedStatement stt = con.prepareStatement(themBctcCon, Statement.RETURN_GENERATED_KEYS);
						stt.setInt(1, bctc.getMaBctc());
						stt.setInt(2, bctcCon.getLoaiBctc());

						return stt;
					}
				}, holder);
				bctcCon.setMaBctcCon(holder.getKey().intValue());

				switch (bctcCon.getLoaiBctc()) {
				case BaoCaoTaiChinhCon.LOAI_CDKT:
					logger.info("Thêm Vảng cân đối kế toán vào bảng BAO_CAO_TAI_CHINH_CHI_TIẾT");
					themCapNhapBctcChiTiet(bctcCon.getChiTietDs());
					break;
				case BaoCaoTaiChinhCon.LOAI_KQHDKD:
					logger.info("Thêm Bảng kết quả HDKD vào bảng BAO_CAO_TAI_CHINH_CHI_TIẾT");
					themCapNhapBctcChiTiet(bctcCon.getChiTietDs());
					break;
				case BaoCaoTaiChinhCon.LOAI_LCTT:
					logger.info("Thêm Bảng lưu chuyển tiền tệ vào bảng BAO_CAO_TAI_CHINH_CHI_TIẾT");
					themCapNhapBctcChiTiet(bctcCon.getChiTietDs());
					break;
				case BaoCaoTaiChinhCon.LOAI_CDPS:
					logger.info("Thêm Bảng cân đối phát sinh vào bảng BAO_CAO_TAI_CHINH_CHI_TIẾT");
					themCapNhapBctcChiTietCdps(bctcCon.getDuLieuKeToan());
					break;
				default:
					break;
				}
			}
		}

		return bctc;
	}

	private void themCapNhapBctcChiTiet(List<BaoCaoTaiChinhChiTiet> bctcChiTietDs) {
		if (bctcChiTietDs == null) {
			return;
		}

		for (BaoCaoTaiChinhChiTiet bctcChiTiet : bctcChiTietDs) {
			themCapNhapBctcChiTiet(bctcChiTiet.getChiTietDs());

			themCapNhapBctcChiTiet(bctcChiTiet);
		}
	}

	private void themCapNhapBctcChiTietCdps(DuLieuKeToan duLieuKeToan) {
		if (duLieuKeToan == null) {
			return;
		}

		
	}

	private void themCapNhapBctcChiTiet(BaoCaoTaiChinhChiTiet bctcChiTiet) {
		if (bctcChiTiet == null || bctcChiTiet.getAsset() == null || bctcChiTiet.getAsset().getAssetCode() == null
				|| bctcChiTiet.getBctc() == null) {
			return;
		}

		String themBctcChiTiet = THEM_BCTC_CHI_TIET;
		String capNhatBctcChiTiet = CAP_NHAT_BCTC_CHI_TIET;

		try {
			logger.info("Thêm báo cáo tài chính chi tiết vào BAO_CAO_TAI_CHINH_CHI_TIET");
			logger.info(themBctcChiTiet);
			Object[] params = { bctcChiTiet.getBctc().getMaBctcCon(), bctcChiTiet.getAsset().getAssetCode(),
					bctcChiTiet.getGiaTri().getGiaTri(), bctcChiTiet.getGiaTriKyTruoc().getGiaTri() };
			jdbcTmpl.update(themBctcChiTiet, params);
		} catch (Exception e) {
			logger.info("Nếu không được thì cập nhật");
			logger.info(capNhatBctcChiTiet);
			Object[] params = { bctcChiTiet.getGiaTri().getGiaTri(), bctcChiTiet.getGiaTriKyTruoc().getGiaTri(),
					bctcChiTiet.getBctc().getMaBctcCon(), bctcChiTiet.getAsset().getAssetCode() };
			jdbcTmpl.update(capNhatBctcChiTiet, params);
		}
	}

	@Override
	@Transactional
	public void xoaBctc(int maBctc) {
		String xoaBctc = XOA_BCTC;
		String xoaBctcCon = XOA_BCTC_CON;
		String xoaBctcChiTiet = XOA_BCTC_CHI_TIET;

		BaoCaoTaiChinh bctc = layBctc(maBctc);
		if (bctc != null) {
			if (bctc.getBctcDs() != null) {
				for (BaoCaoTaiChinhCon bctcCon : bctc.getBctcDs()) {
					logger.info("Xóa trong bảng BAO_CAO_TAI_CHINH_CHI_TIET");
					jdbcTmpl.update(xoaBctcChiTiet, bctcCon.getMaBctcCon());

					logger.info("Xóa trong bảng BAO_CAO_TAI_CHINH_CON");
					jdbcTmpl.update(xoaBctcCon, bctcCon.getMaBctcCon());
				}
			}

			logger.info("Xóa trong bảng BAO_CAO_TAI_CHINH");
			jdbcTmpl.update(xoaBctc, maBctc);
		}
	}

	@Override
	public List<BaoCaoTaiChinhChiTiet> calculateEndBs(Date start, Date end, int maKkt) {
		if (start == null || end == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		String batDau = sdf.format(start);
		String ketThuc = sdf.format(end);

		String query = TINH_CDKT_CUOI_KY;
		logger.info(query);
		logger.info("Từ " + batDau + " đến " + ketThuc);
		logger.info("maKkt: " + maKkt);

		Object[] params = { batDau, ketThuc, batDau, ketThuc, batDau, ketThuc, maKkt, maKkt, maKkt, maKkt };
		List<BaoCaoTaiChinhChiTiet> bctcChiTiet = jdbcTmpl.query(query, params, new BaoCaoTaiChinhChiTietMapper());

		return bctcChiTiet;
	}

	public List<BaoCaoTaiChinhChiTiet> calculateEndSR(Date start, Date end) {
		if (start == null || end == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		String batDau = sdf.format(start);
		String ketThuc = sdf.format(end);

		String query = TINH_KQHDKD_THEO_MATK;
		logger.info(query);
		logger.info("Từ " + batDau + " đến " + ketThuc);

		Object[] params = { batDau, ketThuc };
		List<BaoCaoTaiChinhChiTiet> bctcChiTiet = jdbcTmpl.query(query, params, new BaoCaoTaiChinhChiTietMapper());

		return bctcChiTiet;
	}

	@Override
	public List<BaoCaoTaiChinhChiTiet> calculateEndCFBs(Date start, Date end) {
		if (start == null || end == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		String batDau = sdf.format(start);
		String ketThuc = sdf.format(end);

		String query = TINH_LCTT_THEO_MATK;
		logger.info("Tu " + batDau + " den " + ketThuc);
		logger.info(query);

		Object[] params = { batDau, ketThuc };
		List<BaoCaoTaiChinhChiTiet> bads = jdbcTmpl.query(query, params, new BaoCaoTaiChinhChiTietMapper());

		return bads;
	}

	public class BaoCaoTaiChinhChiTietMapper implements RowMapper<BaoCaoTaiChinhChiTiet> {
		public BaoCaoTaiChinhChiTiet mapRow(ResultSet rs, int rowNum) throws SQLException {
			BalanceAssetItem bai = new BalanceAssetItem();
			bai.setAssetCode(rs.getString("ASSET_CODE"));

			BaoCaoTaiChinhChiTiet bctcChiTiet = new BaoCaoTaiChinhChiTiet();
			bctcChiTiet.setAsset(bai);
			Tien giaTri = new Tien();
			giaTri.setGiaTri(rs.getDouble("SO_TIEN"));
			bctcChiTiet.setGiaTri(giaTri);

			logger.info(bctcChiTiet);

			return bctcChiTiet;
		}
	}

}
