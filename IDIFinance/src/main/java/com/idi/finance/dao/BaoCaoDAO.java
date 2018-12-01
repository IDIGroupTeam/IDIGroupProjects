package com.idi.finance.dao;

import java.util.HashMap;
import java.util.List;

import com.idi.finance.bean.bctc.BalanceAssetData;
import com.idi.finance.bean.bctc.DuLieuKeToan;
import com.idi.finance.bean.chungtu.ChungTu;

import net.sf.jasperreports.engine.JasperReport;

public interface BaoCaoDAO {
	public byte[] taoBaoCaoChungTu(JasperReport jasperReport, HashMap<String, Object> hmParams,
			List<ChungTu> chungTuDs);

	public byte[] taoBangCdkt(JasperReport jasperReport, HashMap<String, Object> hmParams,
			List<BalanceAssetData> baiDs);

	public byte[] taoBangCdps(JasperReport jasperReport, HashMap<String, Object> hmParams, DuLieuKeToan duLieuKeToan);
}
