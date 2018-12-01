package com.idi.finance.bean;

import java.util.HashMap;
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

	private HashMap<String, Object> params;

	public void init() {
		kpiGroups = kpiChartDAO.listKpiGroups();
		kyKeToan = kyKeToanDAO.layKyKeToanMacDinh();
		logger.info("kpiGroups " + kpiGroups);
		logger.info("kyKeToan " + kyKeToan);
	}

	private HashMap<String, Object> layDanhSachCauHinh() {
		HashMap<String, Object> params = new HashMap<>();

		// Lấy tên công ty
		CauHinh tenCt = cauHinhDAO.layCauHinh(CauHinh.TEN_CONG_TY);
		if (tenCt != null) {
			params.put(CauHinh.TEN_CONG_TY, tenCt);
		}

		// Lấy địa chỉ
		CauHinh diaChi = cauHinhDAO.layCauHinh(CauHinh.DIA_CHI);
		if (diaChi != null) {
			params.put(CauHinh.DIA_CHI, diaChi);
		}

		// Lấy chủ tịch
		CauHinh chuTich = cauHinhDAO.layCauHinh(CauHinh.CHU_TICH);
		if (chuTich != null) {
			params.put(CauHinh.CHU_TICH, chuTich);
		}

		// Lấy giám đốc
		CauHinh giamDoc = cauHinhDAO.layCauHinh(CauHinh.GIAM_DOC);
		if (giamDoc != null) {
			params.put(CauHinh.GIAM_DOC, giamDoc);
		}

		// Lấy kế toán trưởng
		CauHinh ktt = cauHinhDAO.layCauHinh(CauHinh.KE_TOAN_TRUONG);
		if (ktt != null) {
			params.put(CauHinh.KE_TOAN_TRUONG, ktt);
		}

		// Lấy thủ quỹ
		CauHinh thuQuy = cauHinhDAO.layCauHinh(CauHinh.THU_QUY);
		if (thuQuy != null) {
			params.put(CauHinh.THU_QUY, thuQuy);
		}

		// Lấy thủ kho
		CauHinh thuKho = cauHinhDAO.layCauHinh(CauHinh.THU_KHO);
		if (thuKho != null) {
			params.put(CauHinh.THU_KHO, thuKho);
		}

		// Lấy mã số thuế
		CauHinh mst = cauHinhDAO.layCauHinh(CauHinh.MST);
		if (mst != null) {
			params.put(CauHinh.MST, mst);
		}

		return params;
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

	public HashMap<String, Object> getParams() {
		this.params = layDanhSachCauHinh();
		return params;
	}

	public void setParams(HashMap<String, Object> params) {
		this.params = params;
	}
}
