package com.idi.finance.dao;

import com.idi.finance.bean.chungtu.ChungTu;

import net.sf.jasperreports.engine.JasperReport;

public interface BaoCaoDAO {
	public byte[] taoBaoCaoChungTu(JasperReport jasperReport, int maCt);

	public byte[] taoBaoCaoChungTu(JasperReport jasperReport, ChungTu chungTu);
}
