package com.idi.finance.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.idi.finance.dao.BaoCaoDAO;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

public class BaoCaoDAOImpl implements BaoCaoDAO {
	private static final Logger logger = Logger.getLogger(BaoCaoDAOImpl.class);

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public byte[] taoBaoCaoChungTu(JasperReport jasperReport, int maCt) {
		if (jasperReport == null) {
			return null;
		}

		HashMap<String, Object> hmParams = new HashMap<>();
		hmParams.put("MA_CT", new Integer(maCt));

		try {
			return JasperRunManager.runReportToPdf(jasperReport, hmParams, jdbcTmpl.getDataSource().getConnection());
		} catch (JRException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
