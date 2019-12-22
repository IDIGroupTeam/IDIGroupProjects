package com.idi.finance.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.idi.finance.bean.bctc.BalanceAssetData;
import com.idi.finance.bean.bctc.DuLieuKeToan;
import com.idi.finance.bean.chungtu.ChungTu;

import net.sf.jasperreports.engine.JasperReport;

public interface BaoCaoDAO {
	public XSSFWorkbook taoChungTuDs(List<ChungTu> chungTuDs, HashMap<String, Object> hmParams);

	public XSSFWorkbook taoChungTuKtthDs(List<ChungTu> chungTuDs, HashMap<String, Object> hmParams);

	public byte[] taoChungTu(JasperReport jasperReport, HashMap<String, Object> hmParams, List<ChungTu> chungTuDs);

	public byte[] taoBangCdkt(JasperReport jasperReport, HashMap<String, Object> hmParams,
			List<BalanceAssetData> baiDs);

	public byte[] taoBangCdps(JasperReport jasperReport, HashMap<String, Object> hmParams, DuLieuKeToan duLieuKeToan);
}
