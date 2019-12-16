package com.idi.finance.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.idi.finance.bean.bctc.BalanceAssetData;
import com.idi.finance.bean.bctc.DuLieuKeToan;
import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.dao.BaoCaoDAO;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
	public byte[] taoBaoCaoChungTu(JasperReport jasperReport, HashMap<String, Object> hmParams,
			List<ChungTu> chungTuDs) {
		if (jasperReport == null || chungTuDs == null) {
			return null;
		}

		if (hmParams == null) {
			hmParams = new HashMap<>();
		}

		try {
			JRBeanCollectionDataSource chungTuColDs = new JRBeanCollectionDataSource(chungTuDs);
			return JasperRunManager.runReportToPdf(jasperReport, hmParams, chungTuColDs);
		} catch (JRException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public byte[] taoBangCdkt(JasperReport jasperReport, HashMap<String, Object> hmParams,
			List<BalanceAssetData> baiDs) {
		if (jasperReport == null || baiDs == null) {
			return null;
		}

		if (hmParams == null) {
			hmParams = new HashMap<>();
		}

		try {
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(baiDs);
			return JasperRunManager.runReportToPdf(jasperReport, hmParams, dataSource);
		} catch (JRException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public byte[] taoBangCdps(JasperReport jasperReport, HashMap<String, Object> hmParams, DuLieuKeToan duLieuKeToan) {
		if (jasperReport == null || duLieuKeToan == null) {
			return null;
		}

		if (hmParams == null) {
			hmParams = new HashMap<>();
		}

		try {
			List<DuLieuKeToan> duLieuKeToanDs = new ArrayList<>();
			duLieuKeToanDs.add(duLieuKeToan);

			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(duLieuKeToanDs);
			JasperPrint print = JasperFillManager.fillReport(jasperReport, hmParams, dataSource);
			return JasperExportManager.exportReportToPdf(print);
			// return JasperRunManager.runReportToPdf(jasperReport, hmParams, dataSource);
		} catch (JRException e) {
			e.printStackTrace();
		}
		return null;
	}
}
