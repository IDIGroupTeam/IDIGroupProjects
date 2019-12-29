package com.idi.finance.utils;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.idi.finance.bean.taikhoan.TaiKhoan;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class ReportUtils {
	private static final Logger logger = Logger.getLogger(ReportUtils.class);

	public static String layDanhSachTaiKhoan(List<TaiKhoan> taiKhoanDs) {
		if (taiKhoanDs == null || taiKhoanDs.size() == 0) {
			return "";
		}

		StringBuilder sb = new StringBuilder("");
		Iterator<TaiKhoan> iter = taiKhoanDs.iterator();
		while (iter.hasNext()) {
			TaiKhoan taiKhoan = iter.next();

			if (taiKhoan.getLoaiTaiKhoan() != null && taiKhoan.getLoaiTaiKhoan().getMaTk() != null
					&& !taiKhoan.getLoaiTaiKhoan().getMaTk().trim().equals("")) {
				sb.append(taiKhoan.getLoaiTaiKhoan().getMaTk());
				sb.append(",");
			}
		}

		if (sb.length() > 0) {
			return sb.substring(0, sb.length() - 1);
		}

		return "";
	}

	public static JasperReport compileReport(String fileName, HttpServletRequest req) throws JRException {
		String jrxml = req.getSession().getServletContext().getRealPath("/baocao/bctc/" + fileName + ".jrxml");
		String jasper = req.getSession().getServletContext().getRealPath("/baocao/bctc/" + fileName + ".jasper");

		File reportFile = new File(jasper);
		// If compiled file is not found, then compile XML template
		// if (!reportFile.exists()) {
		logger.info("Compile Jasper report ... " + fileName);
		JasperCompileManager.compileReportToFile(jrxml, jasper);
		// }
		JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportFile.getPath());
		return jasperReport;
	}

	public static JasperReport compileReport(String fileName, List<String> subFiles, HttpServletRequest req)
			throws JRException {
		if (subFiles != null) {
			for (String subFile : subFiles) {
				compileReport(subFile, req);
			}
		}

		return compileReport(fileName, req);
	}
}