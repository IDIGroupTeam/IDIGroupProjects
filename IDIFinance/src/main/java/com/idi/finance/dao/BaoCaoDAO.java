package com.idi.finance.dao;

import net.sf.jasperreports.engine.JasperReport;

public interface BaoCaoDAO {
	public byte[] taoBaoCaoChungTu(JasperReport jasperReport, int maCt);
}
