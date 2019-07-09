package com.idi.finance.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.idi.finance.bean.diachi.CapDiaChi;
import com.idi.finance.bean.diachi.VungDiaChi;
import com.idi.finance.dao.DiaChiDAO;

public class DiaChiDAOImpl implements DiaChiDAO {
	private static final Logger logger = Logger.getLogger(DiaChiDAOImpl.class);

	@Value("${LAY_VUNG_DIA_CHI}")
	private String LAY_VUNG_DIA_CHI;

	@Value("${DANH_SACH_CAP_DIA_CHI}")
	private String DANH_SACH_CAP_DIA_CHI;

	@Value("${DANH_SACH_VUNG_DIA_CHI_THEO_CHA}")
	private String DANH_SACH_VUNG_DIA_CHI_THEO_CHA;

	@Value("${DANH_SACH_VUNG_DIA_CHI_THEO_CAP}")
	private String DANH_SACH_VUNG_DIA_CHI_THEO_CAP;

	@Value("${LAY_MA_VUNG_DIA_CHI_CON}")
	private String LAY_MA_VUNG_DIA_CHI_CON;

	@Value("${DEM_SO_VUNG_DIA_CHI_THEO_CAP}")
	private String DEM_SO_VUNG_DIA_CHI_THEO_CAP;

	@Value("${THEM_VUNG_DIA_CHI}")
	private String THEM_VUNG_DIA_CHI;

	@Value("${CAP_NHAT_VUNG_DIA_CHI}")
	private String CAP_NHAT_VUNG_DIA_CHI;

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public List<CapDiaChi> danhSachCapDiaChi(int capDc) {
		String query = DANH_SACH_CAP_DIA_CHI;

		Object[] params = { capDc };
		List<CapDiaChi> capDiaChiDs = jdbcTmpl.query(query, params, new CapDiaChiMapper());

		return capDiaChiDs;
	}

	public class CapDiaChiMapper implements RowMapper<CapDiaChi> {
		public CapDiaChi mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				CapDiaChi capDiaChi = new CapDiaChi();

				capDiaChi.setMaCapDc(rs.getInt("MA_CAP_DC"));
				capDiaChi.setTenCapDc(rs.getString("TEN_CAP_DC"));
				capDiaChi.setMoTa(rs.getString("MO_TA"));
				capDiaChi.setCapDc(rs.getInt("CAP_DC"));

				return capDiaChi;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public VungDiaChi danhSachDiaChi(VungDiaChi vungDiaChi) {
		String query = DANH_SACH_VUNG_DIA_CHI_THEO_CHA;

		if (vungDiaChi.getMaDc() != null) {
			query = query.replaceAll("\\$DIEU_KIEN_VUNG_CHA\\$",
					"VUNG.MA_VUNG_DC_CHA='" + vungDiaChi.getMaDc().trim() + "'");
		} else {
			query = query.replaceAll("\\$DIEU_KIEN_VUNG_CHA\\$", "VUNG.MA_VUNG_DC_CHA IS NULL");
		}

		List<VungDiaChi> vungDiaChiDs = jdbcTmpl.query(query, new VungDiaChiMapper());

		if (vungDiaChiDs != null && vungDiaChiDs.size() > 0) {
			Iterator<VungDiaChi> iter = vungDiaChiDs.iterator();
			while (iter.hasNext()) {
				VungDiaChi vungDiaChiCon = iter.next();
				vungDiaChiCon.setVungDiaChi(vungDiaChi);

				vungDiaChiCon = danhSachDiaChi(vungDiaChiCon);
			}

			vungDiaChi.themVungDiaChi(vungDiaChiDs);
		}

		return vungDiaChi;
	}

	@Override
	public List<VungDiaChi> danhSachDiaChi(VungDiaChi mienDc, int batDau, int soDong) {
		String query = DANH_SACH_VUNG_DIA_CHI_THEO_CAP;

		query = query.replaceAll("\\$DIEU_KIEN_GIOI_HAN\\$", "LIMIT " + (batDau - 1) + ", " + soDong);

		// Xây dựng các điệu kiện lọc theo miền, thành phố, quận
		String dieuKien = "";
		String subQuery = LAY_MA_VUNG_DIA_CHI_CON;
		if (mienDc != null && !mienDc.getMaDc().equals("") && !mienDc.getMaDc().equals(VungDiaChi.TC)) {
			dieuKien = "AND MA_VUNG_DC_CHA IN (?)";

			VungDiaChi thanhPho = mienDc.getVungDiaChiDs().get(0);
			if (thanhPho != null && !thanhPho.getMaDc().equals("") && !thanhPho.getMaDc().equals(VungDiaChi.TC)) {
				VungDiaChi quan = thanhPho.getVungDiaChiDs().get(0);
				if (quan != null && !quan.getMaDc().equals("") && !quan.getMaDc().equals(VungDiaChi.TC)) {
					// Trong một thành phố cụ thể, tất cả quận
					dieuKien = dieuKien.replaceAll("\\?", "'" + quan.getMaDc() + "'");
				} else {
					// Trong một thành phố cụ thể, tất cả quận
					dieuKien = dieuKien.replaceAll("\\?", subQuery);
					dieuKien = dieuKien.replaceAll("\\?", "'" + thanhPho.getMaDc() + "'");
				}
			} else {
				// Trong một miền cụ thể, tất cả thành phố, quận
				dieuKien = dieuKien.replaceAll("\\?", subQuery);
				dieuKien = dieuKien.replaceAll("\\?", subQuery);
				dieuKien = dieuKien.replaceAll("\\?", "'" + mienDc.getMaDc() + "'");
			}
		} else {
			// Miền là tất cả nhưng thành phố và quận có thể được chọn
			VungDiaChi thanhPho = mienDc.getVungDiaChiDs().get(0);
			if (thanhPho != null && !thanhPho.getMaDc().equals("") && !thanhPho.getMaDc().equals(VungDiaChi.TC)) {
				dieuKien = "AND MA_VUNG_DC_CHA IN (?)";
				VungDiaChi quan = thanhPho.getVungDiaChiDs().get(0);
				if (quan != null && !quan.getMaDc().equals("") && !quan.getMaDc().equals(VungDiaChi.TC)) {
					// Trong một thành phố cụ thể, tất cả quận
					dieuKien = dieuKien.replaceAll("\\?", "'" + quan.getMaDc() + "'");
				} else {
					// Trong một thành phố cụ thể, tất cả quận
					dieuKien = dieuKien.replaceAll("\\?", subQuery);
					dieuKien = dieuKien.replaceAll("\\?", "'" + thanhPho.getMaDc() + "'");
				}
			}
		}
		query = query.replaceAll("\\$DIEU_KIEN_KHAC\\$", dieuKien);

		// Lấy cấp phường
		logger.info(query);
		Object[] params = { CapDiaChi.PHUONG };
		List<VungDiaChi> phuongDs = jdbcTmpl.query(query, params, new VungDiaChiMapper());

		// Lấy thông tin quận
		List<VungDiaChi> quanDs = new ArrayList<>();
		Iterator<VungDiaChi> phuongIter = phuongDs.iterator();
		while (phuongIter.hasNext()) {
			try {
				VungDiaChi phuong = phuongIter.next();

				VungDiaChi quan = null;
				int pos = quanDs.indexOf(phuong.getVungDiaChi());
				if (pos > -1) {
					quan = quanDs.get(pos);

					phuong.setVungDiaChi(quan);
					quan.themVungDiaChi(phuong);
				} else {
					quan = layVungDiaChi(phuong.getVungDiaChi().getMaDc());

					phuong.setVungDiaChi(quan);
					quan.themVungDiaChi(phuong);
					quanDs.add(quan);
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}

		// Lấy thông tin thành phố
		List<VungDiaChi> thanhPhoDs = new ArrayList<>();
		Iterator<VungDiaChi> quanIter = quanDs.iterator();
		while (quanIter.hasNext()) {
			try {
				VungDiaChi quan = quanIter.next();

				VungDiaChi thanhPho = null;
				int pos = thanhPhoDs.indexOf(quan.getVungDiaChi());
				if (pos > -1) {
					thanhPho = thanhPhoDs.get(pos);

					quan.setVungDiaChi(thanhPho);
					thanhPho.themVungDiaChi(quan);
				} else {
					thanhPho = layVungDiaChi(quan.getVungDiaChi().getMaDc());

					quan.setVungDiaChi(thanhPho);
					thanhPho.themVungDiaChi(quan);
					thanhPhoDs.add(thanhPho);
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}

		// Lấy thông tin miền
		List<VungDiaChi> mienDs = new ArrayList<>();
		Iterator<VungDiaChi> thanhPhoIter = thanhPhoDs.iterator();
		while (thanhPhoIter.hasNext()) {
			try {
				VungDiaChi thanhPho = thanhPhoIter.next();

				VungDiaChi mien = null;
				int pos = mienDs.indexOf(thanhPho.getVungDiaChi());
				if (pos > -1) {
					mien = mienDs.get(pos);

					thanhPho.setVungDiaChi(mien);
					mien.themVungDiaChi(thanhPho);
				} else {
					mien = layVungDiaChi(thanhPho.getVungDiaChi().getMaDc());

					thanhPho.setVungDiaChi(mien);
					mien.themVungDiaChi(thanhPho);
					mienDs.add(mien);
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}

		return mienDs;
	}

	@Override
	public List<VungDiaChi> danhSachDiaChi(int capDc) {
		String query = DANH_SACH_VUNG_DIA_CHI_THEO_CAP;
		query = query.replaceAll("\\$DIEU_KIEN_GIOI_HAN\\$", "");
		query = query.replaceAll("\\$DIEU_KIEN_KHAC\\$", "");

		// Lấy danh sách địa chỉ theo cấp
		Object[] params = { capDc };
		List<VungDiaChi> vungDiaChiDs = jdbcTmpl.query(query, params, new VungDiaChiMapper());

		return vungDiaChiDs;
	}

	@Override
	public List<VungDiaChi> danhSachDiaChi(String maVungDcCha) {
		String query = DANH_SACH_VUNG_DIA_CHI_THEO_CHA;

		query = query.replaceAll("\\$DIEU_KIEN_VUNG_CHA\\$", "VUNG.MA_VUNG_DC_CHA='" + maVungDcCha.trim() + "'");
		List<VungDiaChi> vungDiaChiDs = jdbcTmpl.query(query, new VungDiaChiMapper());

		return vungDiaChiDs;
	}

	@Override
	public int demTongSoVungDiaChi(VungDiaChi mienDc) {
		String query = DEM_SO_VUNG_DIA_CHI_THEO_CAP;

		// Xây dựng các điệu kiện lọc theo miền, thành phố, quận
		String dieuKien = "";
		String subQuery = LAY_MA_VUNG_DIA_CHI_CON;
		if (mienDc != null && !mienDc.getMaDc().equals("") && !mienDc.getMaDc().equals(VungDiaChi.TC)) {
			dieuKien = "AND MA_VUNG_DC_CHA IN (?)";

			VungDiaChi thanhPho = mienDc.getVungDiaChiDs().get(0);
			if (thanhPho != null && !thanhPho.getMaDc().equals("") && !thanhPho.getMaDc().equals(VungDiaChi.TC)) {
				VungDiaChi quan = thanhPho.getVungDiaChiDs().get(0);
				if (quan != null && !quan.getMaDc().equals("") && !quan.getMaDc().equals(VungDiaChi.TC)) {
					// Trong một thành phố cụ thể, tất cả quận
					dieuKien = dieuKien.replaceAll("\\?", "'" + quan.getMaDc() + "'");
				} else {
					// Trong một thành phố cụ thể, tất cả quận
					dieuKien = dieuKien.replaceAll("\\?", subQuery);
					dieuKien = dieuKien.replaceAll("\\?", "'" + thanhPho.getMaDc() + "'");
				}
			} else {
				// Trong một miền cụ thể, tất cả thành phố, quận
				dieuKien = dieuKien.replaceAll("\\?", subQuery);
				dieuKien = dieuKien.replaceAll("\\?", subQuery);
				dieuKien = dieuKien.replaceAll("\\?", "'" + mienDc.getMaDc() + "'");
			}
		} else {
			// Miền là tất cả nhưng thành phố và quận có thể được chọn
			VungDiaChi thanhPho = mienDc.getVungDiaChiDs().get(0);
			if (thanhPho != null && !thanhPho.getMaDc().equals("") && !thanhPho.getMaDc().equals(VungDiaChi.TC)) {
				dieuKien = "AND MA_VUNG_DC_CHA IN (?)";
				VungDiaChi quan = thanhPho.getVungDiaChiDs().get(0);
				if (quan != null && !quan.getMaDc().equals("") && !quan.getMaDc().equals(VungDiaChi.TC)) {
					// Trong một thành phố cụ thể, tất cả quận
					dieuKien = dieuKien.replaceAll("\\?", "'" + quan.getMaDc() + "'");
				} else {
					// Trong một thành phố cụ thể, tất cả quận
					dieuKien = dieuKien.replaceAll("\\?", subQuery);
					dieuKien = dieuKien.replaceAll("\\?", "'" + thanhPho.getMaDc() + "'");
				}
			}
		}
		query = query.replaceAll("\\$DIEU_KIEN_KHAC\\$", dieuKien);

		try {
			Object[] params = { CapDiaChi.PHUONG };
			return jdbcTmpl.queryForObject(query, params, Integer.class);
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public VungDiaChi layVungDiaChi(String maVungDc) {
		String query = LAY_VUNG_DIA_CHI;

		Object[] params = { maVungDc };
		VungDiaChi vungDiaChi = jdbcTmpl.queryForObject(query, params, new VungDiaChiMapper());
		return vungDiaChi;
	}

	public class VungDiaChiMapper implements RowMapper<VungDiaChi> {
		public VungDiaChi mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				VungDiaChi vungDiaChi = new VungDiaChi();

				vungDiaChi.setMaDc(rs.getString("MA_VUNG_DC"));
				vungDiaChi.setTenDc(rs.getString("TEN_VUNG_DC"));

				CapDiaChi capDiaChi = new CapDiaChi();
				capDiaChi.setMaCapDc(rs.getInt("MA_CAP_DC"));
				capDiaChi.setTenCapDc(rs.getString("TEN_CAP_DC"));
				capDiaChi.setMoTa(rs.getString("MO_TA"));
				capDiaChi.setCapDc(rs.getInt("CAP_DC"));

				vungDiaChi.setCap(capDiaChi);

				VungDiaChi vungDiaChiCha = new VungDiaChi();
				vungDiaChiCha.setMaDc(rs.getString("MA_VUNG_DC_CHA"));

				vungDiaChi.setVungDiaChi(vungDiaChiCha);

				return vungDiaChi;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public void capNhatVung(List<VungDiaChi> vungDiaChiDs) {
		if (vungDiaChiDs == null || vungDiaChiDs.size() == 0) {
			return;
		}

		logger.info("Cập nhật vùng địa chỉ theo lô " + vungDiaChiDs.size());

		String capNhatQr = CAP_NHAT_VUNG_DIA_CHI;
		String themQr = THEM_VUNG_DIA_CHI;

		try {
			int[] counts = jdbcTmpl.batchUpdate(capNhatQr, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, vungDiaChiDs.get(i).getTenDc());
					if (vungDiaChiDs.get(i).getVungDiaChi() != null) {
						ps.setString(2, vungDiaChiDs.get(i).getVungDiaChi().getMaDc());
					} else {
						ps.setString(2, null);
					}
					ps.setInt(3, vungDiaChiDs.get(i).getCap().getMaCapDc());
					ps.setString(4, vungDiaChiDs.get(i).getMaDc());
				}

				@Override
				public int getBatchSize() {
					return vungDiaChiDs.size();
				}
			});

			// This is new data, so insert it.
			List<VungDiaChi> vungDiaChiMoiDs = new ArrayList<>();
			for (int i = 0; i < counts.length; i++) {
				if (counts[i] == 0) {
					vungDiaChiMoiDs.add(vungDiaChiDs.get(i));
				}
			}

			jdbcTmpl.batchUpdate(themQr, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, vungDiaChiMoiDs.get(i).getMaDc());
					ps.setString(2, vungDiaChiMoiDs.get(i).getTenDc());
					if (vungDiaChiMoiDs.get(i).getVungDiaChi() != null) {
						ps.setString(3, vungDiaChiMoiDs.get(i).getVungDiaChi().getMaDc());
					} else {
						ps.setString(3, null);
					}
					ps.setInt(4, vungDiaChiMoiDs.get(i).getCap().getMaCapDc());

				}

				@Override
				public int getBatchSize() {
					return vungDiaChiMoiDs.size();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		Iterator<VungDiaChi> iter = vungDiaChiDs.iterator();
		while (iter.hasNext()) {
			VungDiaChi vungDiaChi = iter.next();
			capNhatVung(vungDiaChi.getVungDiaChiDs());
		}
	}
}
