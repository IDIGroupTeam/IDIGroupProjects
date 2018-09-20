package com.idi.finance.dao;

import java.util.HashMap;
import java.util.List;

import com.idi.finance.bean.chungtu.ChungTu;

import net.sf.jasperreports.engine.JasperReport;

public interface BaoCaoDAO {
	public byte[] taoBaoCaoChungTu(JasperReport jasperReport, HashMap<String, Object> hmParams,
			List<ChungTu> chungTuDs);
}
