package com.idi.finance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.idi.finance.bean.hanghoa.DonVi;
import com.idi.finance.bean.hanghoa.LoaiHangHoa;
import com.idi.finance.bean.hanghoa.NhomHangHoa;
import com.idi.finance.dao.HangHoaDAO;

public class HangHoaDAOImpl implements HangHoaDAO {
	private static final Logger logger = Logger.getLogger(HangHoaDAOImpl.class);

	@Value("${DANH_SACH_DON_VI}")
	private String DANH_SACH_DON_VI;

	@Value("${DANH_SACH_NHOM_HANG_HOA}")
	private String DANH_SACH_NHOM_HANG_HOA;

	@Value("${DANH_SACH_LOAI_HANG_HOA}")
	private String DANH_SACH_LOAI_HANG_HOA;

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public List<DonVi> danhSachDonViHangHoa() {
		String query = DANH_SACH_DON_VI;

		logger.info("Danh sách đơn vị tính của hàng hóa ...");
		logger.info(query);

		List<DonVi> donViDs = jdbcTmpl.query(query, new DonViMapper());
		return donViDs;
	}

	public class DonViMapper implements RowMapper<DonVi> {
		public DonVi mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				DonVi donVi = new DonVi();

				donVi.setMaDv(rs.getInt("MA_DV"));
				donVi.setTenDv(rs.getString("TEN_DV"));
				donVi.setMoTa(rs.getString("MO_TA"));

				return donVi;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public NhomHangHoa danhSachNhomHangHoa(NhomHangHoa nhomHangHoa) {
		String query = DANH_SACH_NHOM_HANG_HOA;

		// logger.info("Cập nhật nhóm của hàng hóa " + nhomHangHoa);
		// logger.info(query);

		Object[] params = { nhomHangHoa.getMaNhomHh() };
		List<NhomHangHoa> nhomHangHoaDs = jdbcTmpl.query(query, params, new NhomHangHoaMapper());

		if (nhomHangHoaDs != null && nhomHangHoaDs.size() > 0) {
			int doSau = 1;

			Iterator<NhomHangHoa> iter = nhomHangHoaDs.iterator();
			while (iter.hasNext()) {
				NhomHangHoa nhomHangHoaCon = iter.next();
				nhomHangHoaCon.setNhomHh(nhomHangHoa);
				nhomHangHoaCon.setMuc(nhomHangHoa.getMuc() + 1);

				nhomHangHoaCon = danhSachNhomHangHoa(nhomHangHoaCon);
				doSau = doSau > nhomHangHoaCon.getDoSau() ? doSau : nhomHangHoaCon.getDoSau();
			}

			nhomHangHoa.setDoSau(doSau + 1);
			nhomHangHoa.themNhomHh(nhomHangHoaDs);
		}

		logger.info(nhomHangHoa);
		return nhomHangHoa;
	}

	public class NhomHangHoaMapper implements RowMapper<NhomHangHoa> {
		public NhomHangHoa mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				NhomHangHoa nhomHangHoa = new NhomHangHoa();

				nhomHangHoa.setMaNhomHh(rs.getInt("MA_NHOM_HH"));
				nhomHangHoa.setTenNhomHh(rs.getString("TEN_NHOM_HH"));

				return nhomHangHoa;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public List<LoaiHangHoa> danhSachLoaiHangHoa() {
		String query = DANH_SACH_LOAI_HANG_HOA;

		logger.info("Danh sách loại hàng hóa ...");
		logger.info(query);

		List<LoaiHangHoa> loaiHangHoaDs = jdbcTmpl.query(query, new LoaiHangHoaMapper());
		return loaiHangHoaDs;
	}

	public class LoaiHangHoaMapper implements RowMapper<LoaiHangHoa> {
		public LoaiHangHoa mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				LoaiHangHoa loaiHangHoa = new LoaiHangHoa();

				loaiHangHoa.setMaHh(rs.getInt("MA_HH"));
				loaiHangHoa.setTenHh(rs.getString("TEN_HH"));

				DonVi donVi = new DonVi();
				donVi.setMaDv(rs.getInt("MA_DV"));
				donVi.setTenDv(rs.getString("TEN_DV"));
				donVi.setMoTa(rs.getString("MO_TA"));
				loaiHangHoa.setDonVi(donVi);

				NhomHangHoa nhomHangHoa = new NhomHangHoa();
				nhomHangHoa.setMaNhomHh(rs.getInt("MA_NHOM_HH"));
				nhomHangHoa.setTenNhomHh(rs.getString("TEN_NHOM_HH"));
				loaiHangHoa.setNhomHh(nhomHangHoa);

				return loaiHangHoa;
			} catch (Exception e) {
				return null;
			}
		}
	}
}
