package com.idi.finance.utils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

	public static JasperReport compileReport(String fileName, String relativePath, HttpServletRequest req)
			throws JRException {
		if (relativePath == null) {
			relativePath = "";
		}

		String jrxml = req.getSession().getServletContext()
				.getRealPath("/baocao/" + relativePath + "/" + fileName + ".jrxml");
		String jasper = req.getSession().getServletContext()
				.getRealPath("/baocao/" + relativePath + "/" + fileName + ".jasper");

		File reportFile = new File(jasper);
		// If compiled file is not found, then compile XML template
		// if (!reportFile.exists()) {
		logger.info("Compile Jasper report ... " + fileName);
		JasperCompileManager.compileReportToFile(jrxml, jasper);
		// }
		JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportFile.getPath());
		return jasperReport;
	}

	public static JasperReport compileReport(String fileName, List<String> subFiles, String relativePath,
			HttpServletRequest req) throws JRException {
		if (subFiles != null) {
			for (String subFile : subFiles) {
				compileReport(subFile, relativePath, req);
			}
		}

		return compileReport(fileName, relativePath, req);
	}

	public static void writePdf2Response(byte[] bytes, String fileName, HttpServletResponse res) throws IOException {
		if (res == null) {
			return;
		}

		if (bytes == null) {
			bytes = new byte[0];
		}

		res.reset();
		res.resetBuffer();
		res.setContentType("application/pdf");
		res.setContentLength(bytes.length);
		res.setHeader("Content-disposition", "inline; filename=" + fileName + ".pdf");

		ServletOutputStream out = res.getOutputStream();
		out.write(bytes, 0, bytes.length);
		out.flush();
		out.close();
	}

	public static void writeExcel2Response(XSSFWorkbook wb, String fileName, HttpServletResponse res)
			throws IOException {
		if (res == null) {
			return;
		}

		if (wb == null) {
			wb = new XSSFWorkbook();
		}

		res.reset();
		res.resetBuffer();
		res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8");
		res.setHeader("Content-disposition", "inline; filename=" + fileName + ".xlsx");

		ServletOutputStream out = res.getOutputStream();
		wb.write(out);
		out.flush();
		out.close();
		wb.close();
	}
}