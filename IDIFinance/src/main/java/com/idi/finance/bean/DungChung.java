package com.idi.finance.bean;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.idi.finance.bean.bieudo.KpiGroup;
import com.idi.finance.bean.kyketoan.KyKeToan;
import com.idi.finance.dao.CauHinhDAO;
import com.idi.finance.dao.KpiChartDAO;
import com.idi.finance.dao.KyKeToanDAO;

public class DungChung {
	private static final Logger logger = Logger.getLogger(DungChung.class);

	@Autowired
	KpiChartDAO kpiChartDAO;

	@Autowired
	KyKeToanDAO kyKeToanDAO;

	@Autowired
	CauHinhDAO cauHinhDAO;

	private KyKeToan kyKeToan;

	private List<KpiGroup> kpiGroups;

	public void init() {
		kpiGroups = kpiChartDAO.listKpiGroups();
		kyKeToan = kyKeToanDAO.layKyKeToanMacDinh();
		logger.info("kpiGroups " + kpiGroups);
		logger.info("kyKeToan " + kyKeToan);
	}

	public List<KpiGroup> getKpiGroups() {
		kpiGroups = kpiChartDAO.listKpiGroups();
		return kpiGroups;
	}

	public void setKpiGroups(List<KpiGroup> kpiGroups) {
		this.kpiGroups = kpiGroups;
	}

	public KyKeToan getKyKeToan() {
		kyKeToan = kyKeToanDAO.layKyKeToanMacDinh();
		return kyKeToan;
	}

	public void setKyKeToan(KyKeToan kyKeToan) {
		this.kyKeToan = kyKeToan;
	}
}
