package com.idi.finance.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;

import com.idi.finance.bean.bctc.BaoCaoTaiChinhChiTiet;
import com.idi.finance.bean.bctc.DuLieuKeToan;
import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.dao.BaoCaoDAO;
import com.idi.finance.hangso.Contants;
import com.idi.finance.utils.DateUtils;

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
	public XSSFWorkbook taoChungTuDs(List<ChungTu> chungTuDs, HashMap<String, Object> hmParams) {
		if (chungTuDs == null) {
			return null;
		}

		SimpleDateFormat standardSdf = new SimpleDateFormat("dd/MM/yyyy");
		Date batDau = (Date) hmParams.get(Contants.BAT_DAU);
		Date ketThuc = (Date) hmParams.get(Contants.KET_THUC);

		String company = (String) hmParams.get(Contants.COMPANY);
		String diaChi = (String) hmParams.get(Contants.DIA_CHI);
		String pageTitle = (String) hmParams.get(Contants.PAGE_TITLE);
		String loaiCt = (String) hmParams.get(Contants.LOAI_CT);
		String pageSubTitle = "Từ " + standardSdf.format(batDau) + " đến " + standardSdf.format(ketThuc);

		int dataRow = 8;

		// Writing to excel
		XSSFWorkbook wb = new XSSFWorkbook();
		DataFormat format = wb.createDataFormat();

		// Tạo bold font for page header
		XSSFFont boldPageFont = wb.createFont();
		boldPageFont.setFontHeightInPoints((short) 12);
		boldPageFont.setFontName("Arial");
		boldPageFont.setItalic(false);
		boldPageFont.setBold(true);

		// Tạo italic font for page header
		XSSFFont italicPageFont = wb.createFont();
		italicPageFont.setFontHeightInPoints((short) 10);
		italicPageFont.setFontName("Arial");
		italicPageFont.setItalic(true);
		italicPageFont.setBold(false);

		// Tạo bold font for column header
		XSSFFont boldColumnFont = wb.createFont();
		boldColumnFont.setFontHeightInPoints((short) 10);
		boldColumnFont.setFontName("Arial");
		boldColumnFont.setItalic(false);
		boldColumnFont.setBold(true);

		// Tạo font mặc định
		XSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setFontName("Arial");
		font.setItalic(false);
		font.setBold(false);

		CellStyle companyStyle = wb.createCellStyle();
		companyStyle.setFont(boldPageFont);

		CellStyle diaChiStyle = wb.createCellStyle();
		diaChiStyle.setFont(italicPageFont);

		CellStyle homNayStyle = wb.createCellStyle();
		homNayStyle.setFont(italicPageFont);
		homNayStyle.setAlignment(HorizontalAlignment.RIGHT);

		CellStyle boldPageStyle = wb.createCellStyle();
		boldPageStyle.setFont(boldPageFont);
		boldPageStyle.setAlignment(HorizontalAlignment.CENTER);

		CellStyle italicPageStyle = wb.createCellStyle();
		italicPageStyle.setFont(italicPageFont);
		italicPageStyle.setAlignment(HorizontalAlignment.CENTER);

		CellStyle boldColHeaderStyle = wb.createCellStyle();
		boldColHeaderStyle.setFont(boldColumnFont);
		boldColHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
		boldColHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		boldColHeaderStyle.setBorderTop(BorderStyle.THIN);
		boldColHeaderStyle.setBorderRight(BorderStyle.THIN);
		boldColHeaderStyle.setBorderBottom(BorderStyle.THIN);
		boldColHeaderStyle.setBorderLeft(BorderStyle.THIN);

		CellStyle style = wb.createCellStyle();
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setFont(font);

		CellStyle currencyStyle = wb.createCellStyle();
		currencyStyle.setDataFormat(format.getFormat("#,##"));
		currencyStyle.setBorderTop(BorderStyle.THIN);
		currencyStyle.setBorderRight(BorderStyle.THIN);
		currencyStyle.setBorderBottom(BorderStyle.THIN);
		currencyStyle.setBorderLeft(BorderStyle.THIN);
		currencyStyle.setFont(font);

		CellStyle dateStyle = wb.createCellStyle();
		dateStyle.setDataFormat(format.getFormat("dd/MM/yyyy"));
		dateStyle.setBorderTop(BorderStyle.THIN);
		dateStyle.setBorderRight(BorderStyle.THIN);
		dateStyle.setBorderBottom(BorderStyle.THIN);
		dateStyle.setBorderLeft(BorderStyle.THIN);
		dateStyle.setFont(font);

		CellStyle wrapStyle = wb.createCellStyle();
		wrapStyle.setBorderTop(BorderStyle.THIN);
		wrapStyle.setBorderRight(BorderStyle.THIN);
		wrapStyle.setBorderBottom(BorderStyle.THIN);
		wrapStyle.setBorderLeft(BorderStyle.THIN);
		wrapStyle.setFont(font);
		wrapStyle.setWrapText(true);

		logger.info("Tạo sheet " + pageTitle + " ...");
		XSSFSheet chungTuDsSt = wb.createSheet(pageTitle);

		chungTuDsSt.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
		chungTuDsSt.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));
		chungTuDsSt.addMergedRegion(new CellRangeAddress(3, 3, 0, 5));
		chungTuDsSt.addMergedRegion(new CellRangeAddress(4, 4, 0, 5));
		chungTuDsSt.addMergedRegion(new CellRangeAddress(6, 6, 0, 1));
		chungTuDsSt.addMergedRegion(new CellRangeAddress(6, 7, 2, 2));
		chungTuDsSt.addMergedRegion(new CellRangeAddress(6, 7, 3, 3));
		chungTuDsSt.addMergedRegion(new CellRangeAddress(6, 7, 4, 4));
		chungTuDsSt.addMergedRegion(new CellRangeAddress(6, 7, 5, 5));
		chungTuDsSt.addMergedRegion(
				new CellRangeAddress(dataRow + chungTuDs.size() + 1, dataRow + chungTuDs.size() + 1, 0, 5));

		XSSFRow row0 = chungTuDsSt.createRow(0);
		XSSFCell cell00 = row0.createCell(0);
		cell00.setCellStyle(companyStyle);
		cell00.setCellValue(company);

		XSSFRow row1 = chungTuDsSt.createRow(1);
		XSSFCell cell10 = row1.createCell(0);
		cell10.setCellStyle(diaChiStyle);
		cell10.setCellValue(diaChi);

		XSSFRow row2 = chungTuDsSt.createRow(3);
		XSSFCell cell20 = row2.createCell(0);
		cell20.setCellStyle(boldPageStyle);
		cell20.setCellValue(pageTitle);

		XSSFRow row3 = chungTuDsSt.createRow(4);
		XSSFCell cell30 = row3.createCell(0);
		cell30.setCellStyle(italicPageStyle);
		cell30.setCellValue(pageSubTitle);

		// Tạo dòng header
		XSSFRow row4 = chungTuDsSt.createRow(6);

		// Tạo các cột cho dòng đầu tiên
		XSSFCell cell40 = row4.createCell(0);
		cell40.setCellStyle(boldColHeaderStyle);
		cell40.setCellValue(loaiCt);
		XSSFCell cell41 = row4.createCell(1);
		cell41.setCellStyle(boldColHeaderStyle);
		XSSFCell cell42 = row4.createCell(2);
		cell42.setCellStyle(boldColHeaderStyle);
		cell42.setCellValue("Lý do");
		XSSFCell cell43 = row4.createCell(3);
		cell43.setCellStyle(boldColHeaderStyle);
		cell43.setCellValue("Số tiền (VND)");
		XSSFCell cell44 = row4.createCell(4);
		cell44.setCellStyle(boldColHeaderStyle);
		cell44.setCellValue("Đối tượng");
		XSSFCell cell45 = row4.createCell(5);
		cell45.setCellStyle(boldColHeaderStyle);
		cell45.setCellValue("Địa chỉ");

		XSSFRow row5 = chungTuDsSt.createRow(7);
		XSSFCell cell50 = row5.createCell(0);
		cell50.setCellStyle(boldColHeaderStyle);
		cell50.setCellValue("Ngày ghi sổ");
		XSSFCell cell51 = row5.createCell(1);
		cell51.setCellStyle(boldColHeaderStyle);
		cell51.setCellValue("Số");
		XSSFCell cell52 = row5.createCell(2);
		cell52.setCellStyle(boldColHeaderStyle);
		XSSFCell cell53 = row5.createCell(3);
		cell53.setCellStyle(boldColHeaderStyle);
		XSSFCell cell54 = row5.createCell(4);
		cell54.setCellStyle(boldColHeaderStyle);
		XSSFCell cell55 = row5.createCell(5);
		cell55.setCellStyle(boldColHeaderStyle);

		if (chungTuDs != null) {
			for (int i = 0; i < chungTuDs.size(); i++) {
				ChungTu chungTu = chungTuDs.get(i);
				XSSFRow rowCt = chungTuDsSt.createRow(dataRow + i);

				XSSFCell cellCt0 = rowCt.createCell(0);
				cellCt0.setCellStyle(dateStyle);
				cellCt0.setCellValue(chungTu.getNgayHt());
				XSSFCell cellCt1 = rowCt.createCell(1);
				cellCt1.setCellStyle(style);
				cellCt1.setCellValue(chungTu.getLoaiCt() + chungTu.getSoCt());
				XSSFCell cellCt2 = rowCt.createCell(2);
				cellCt2.setCellStyle(wrapStyle);
				cellCt2.setCellValue(chungTu.getLyDo());
				XSSFCell cellCt3 = rowCt.createCell(3);
				cellCt3.setCellStyle(currencyStyle);
				cellCt3.setCellValue(chungTu.getSoTien().getGiaTri());
				XSSFCell cellCt4 = rowCt.createCell(4);
				cellCt4.setCellStyle(wrapStyle);
				cellCt4.setCellValue(chungTu.getDoiTuong().getTenDt());
				XSSFCell cellCt5 = rowCt.createCell(5);
				cellCt5.setCellStyle(wrapStyle);
				cellCt5.setCellValue(chungTu.getDoiTuong().getDiaChi());
			}
		}

		XSSFRow row6 = chungTuDsSt.createRow(dataRow + chungTuDs.size() + 1);
		XSSFCell cell60 = row6.createCell(0);
		cell60.setCellStyle(homNayStyle);
		cell60.setCellValue(DateUtils.format(new Date()));

		// Resize column's width
		chungTuDsSt.setColumnWidth(2, 16000);
		chungTuDsSt.setColumnWidth(3, 4000);
		chungTuDsSt.setColumnWidth(5, 14000);

		chungTuDsSt.autoSizeColumn(0);
		chungTuDsSt.autoSizeColumn(1);
		chungTuDsSt.autoSizeColumn(4);

		return wb;
	}

	@Override
	public XSSFWorkbook taoChungTuKtthDs(List<ChungTu> chungTuDs, HashMap<String, Object> hmParams) {
		if (chungTuDs == null) {
			return null;
		}

		SimpleDateFormat standardSdf = new SimpleDateFormat("dd/MM/yyyy");
		Date batDau = (Date) hmParams.get(Contants.BAT_DAU);
		Date ketThuc = (Date) hmParams.get(Contants.KET_THUC);

		String company = (String) hmParams.get(Contants.COMPANY);
		String diaChi = (String) hmParams.get(Contants.DIA_CHI);
		String pageTitle = (String) hmParams.get(Contants.PAGE_TITLE);
		String loaiCt = (String) hmParams.get(Contants.LOAI_CT);
		String pageSubTitle = "Từ " + standardSdf.format(batDau) + " đến " + standardSdf.format(ketThuc);

		int dataRow = 8;

		// Writing to excel
		XSSFWorkbook wb = new XSSFWorkbook();
		DataFormat format = wb.createDataFormat();

		// Tạo bold font for page header
		XSSFFont boldPageFont = wb.createFont();
		boldPageFont.setFontHeightInPoints((short) 12);
		boldPageFont.setFontName("Arial");
		boldPageFont.setItalic(false);
		boldPageFont.setBold(true);

		// Tạo italic font for page header
		XSSFFont italicPageFont = wb.createFont();
		italicPageFont.setFontHeightInPoints((short) 10);
		italicPageFont.setFontName("Arial");
		italicPageFont.setItalic(true);
		italicPageFont.setBold(false);

		// Tạo bold font for column header
		XSSFFont boldColumnFont = wb.createFont();
		boldColumnFont.setFontHeightInPoints((short) 10);
		boldColumnFont.setFontName("Arial");
		boldColumnFont.setItalic(false);
		boldColumnFont.setBold(true);

		// Tạo font mặc định
		XSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setFontName("Arial");
		font.setItalic(false);
		font.setBold(false);

		CellStyle companyStyle = wb.createCellStyle();
		companyStyle.setFont(boldPageFont);

		CellStyle diaChiStyle = wb.createCellStyle();
		diaChiStyle.setFont(italicPageFont);

		CellStyle homNayStyle = wb.createCellStyle();
		homNayStyle.setFont(italicPageFont);
		homNayStyle.setAlignment(HorizontalAlignment.RIGHT);

		CellStyle boldPageStyle = wb.createCellStyle();
		boldPageStyle.setFont(boldPageFont);
		boldPageStyle.setAlignment(HorizontalAlignment.CENTER);

		CellStyle italicPageStyle = wb.createCellStyle();
		italicPageStyle.setFont(italicPageFont);
		italicPageStyle.setAlignment(HorizontalAlignment.CENTER);

		CellStyle boldColHeaderStyle = wb.createCellStyle();
		boldColHeaderStyle.setFont(boldColumnFont);
		boldColHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
		boldColHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		boldColHeaderStyle.setBorderTop(BorderStyle.THIN);
		boldColHeaderStyle.setBorderRight(BorderStyle.THIN);
		boldColHeaderStyle.setBorderBottom(BorderStyle.THIN);
		boldColHeaderStyle.setBorderLeft(BorderStyle.THIN);

		CellStyle style = wb.createCellStyle();
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setFont(font);

		CellStyle currencyStyle = wb.createCellStyle();
		currencyStyle.setDataFormat(format.getFormat("#,##"));
		currencyStyle.setBorderTop(BorderStyle.THIN);
		currencyStyle.setBorderRight(BorderStyle.THIN);
		currencyStyle.setBorderBottom(BorderStyle.THIN);
		currencyStyle.setBorderLeft(BorderStyle.THIN);
		currencyStyle.setFont(font);

		CellStyle dateStyle = wb.createCellStyle();
		dateStyle.setDataFormat(format.getFormat("dd/MM/yyyy"));
		dateStyle.setBorderTop(BorderStyle.THIN);
		dateStyle.setBorderRight(BorderStyle.THIN);
		dateStyle.setBorderBottom(BorderStyle.THIN);
		dateStyle.setBorderLeft(BorderStyle.THIN);
		dateStyle.setFont(font);

		CellStyle wrapStyle = wb.createCellStyle();
		wrapStyle.setBorderTop(BorderStyle.THIN);
		wrapStyle.setBorderRight(BorderStyle.THIN);
		wrapStyle.setBorderBottom(BorderStyle.THIN);
		wrapStyle.setBorderLeft(BorderStyle.THIN);
		wrapStyle.setFont(font);
		wrapStyle.setWrapText(true);

		logger.info("Tạo sheet " + pageTitle + " ...");
		XSSFSheet chungTuDsSt = wb.createSheet(pageTitle);

		chungTuDsSt.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
		chungTuDsSt.addMergedRegion(new CellRangeAddress(1, 1, 0, 3));
		chungTuDsSt.addMergedRegion(new CellRangeAddress(3, 3, 0, 3));
		chungTuDsSt.addMergedRegion(new CellRangeAddress(4, 4, 0, 3));
		chungTuDsSt.addMergedRegion(new CellRangeAddress(6, 6, 0, 1));
		chungTuDsSt.addMergedRegion(new CellRangeAddress(6, 7, 2, 2));
		chungTuDsSt.addMergedRegion(new CellRangeAddress(6, 7, 3, 3));
		chungTuDsSt.addMergedRegion(
				new CellRangeAddress(dataRow + chungTuDs.size() + 1, dataRow + chungTuDs.size() + 1, 0, 3));

		XSSFRow row0 = chungTuDsSt.createRow(0);
		XSSFCell cell00 = row0.createCell(0);
		cell00.setCellStyle(companyStyle);
		cell00.setCellValue(company);

		XSSFRow row1 = chungTuDsSt.createRow(1);
		XSSFCell cell10 = row1.createCell(0);
		cell10.setCellStyle(diaChiStyle);
		cell10.setCellValue(diaChi);

		XSSFRow row2 = chungTuDsSt.createRow(3);
		XSSFCell cell20 = row2.createCell(0);
		cell20.setCellStyle(boldPageStyle);
		cell20.setCellValue(pageTitle);

		XSSFRow row3 = chungTuDsSt.createRow(4);
		XSSFCell cell30 = row3.createCell(0);
		cell30.setCellStyle(italicPageStyle);
		cell30.setCellValue(pageSubTitle);

		// Tạo dòng header
		XSSFRow row4 = chungTuDsSt.createRow(6);

		// Tạo các cột cho dòng đầu tiên
		XSSFCell cell40 = row4.createCell(0);
		cell40.setCellStyle(boldColHeaderStyle);
		cell40.setCellValue(loaiCt);
		XSSFCell cell41 = row4.createCell(1);
		cell41.setCellStyle(boldColHeaderStyle);
		XSSFCell cell42 = row4.createCell(2);
		cell42.setCellStyle(boldColHeaderStyle);
		cell42.setCellValue("Lý do");
		XSSFCell cell43 = row4.createCell(3);
		cell43.setCellStyle(boldColHeaderStyle);
		cell43.setCellValue("Số tiền (VND)");

		XSSFRow row5 = chungTuDsSt.createRow(7);
		XSSFCell cell50 = row5.createCell(0);
		cell50.setCellStyle(boldColHeaderStyle);
		cell50.setCellValue("Ngày ghi sổ");
		XSSFCell cell51 = row5.createCell(1);
		cell51.setCellStyle(boldColHeaderStyle);
		cell51.setCellValue("Số");
		XSSFCell cell52 = row5.createCell(2);
		cell52.setCellStyle(boldColHeaderStyle);
		XSSFCell cell53 = row5.createCell(3);
		cell53.setCellStyle(boldColHeaderStyle);

		if (chungTuDs != null) {
			for (int i = 0; i < chungTuDs.size(); i++) {
				ChungTu chungTu = chungTuDs.get(i);
				XSSFRow rowCt = chungTuDsSt.createRow(8 + i);

				XSSFCell cellCt0 = rowCt.createCell(0);
				cellCt0.setCellStyle(dateStyle);
				cellCt0.setCellValue(chungTu.getNgayHt());
				XSSFCell cellCt1 = rowCt.createCell(1);
				cellCt1.setCellStyle(style);
				cellCt1.setCellValue(chungTu.getLoaiCt() + chungTu.getSoCt());
				XSSFCell cellCt2 = rowCt.createCell(2);
				cellCt2.setCellStyle(wrapStyle);
				cellCt2.setCellValue(chungTu.getLyDo());
				XSSFCell cellCt3 = rowCt.createCell(3);
				cellCt3.setCellStyle(currencyStyle);
				cellCt3.setCellValue(chungTu.getSoTien().getGiaTri());
			}
		}

		XSSFRow row6 = chungTuDsSt.createRow(dataRow + chungTuDs.size() + 1);
		XSSFCell cell60 = row6.createCell(0);
		cell60.setCellStyle(homNayStyle);
		cell60.setCellValue(DateUtils.format(new Date()));

		// Resize column's width
		chungTuDsSt.setColumnWidth(0, 3500);
		chungTuDsSt.autoSizeColumn(1);
		chungTuDsSt.setColumnWidth(2, 20000);
		chungTuDsSt.setColumnWidth(3, 4000);

		return wb;
	}

	@Override
	public byte[] taoChungTu(JasperReport jasperReport, HashMap<String, Object> hmParams, List<ChungTu> chungTuDs) {
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
			List<BaoCaoTaiChinhChiTiet> chiTietDs) {
		if (jasperReport == null || chiTietDs == null) {
			return null;
		}

		if (hmParams == null) {
			hmParams = new HashMap<>();
		}

		try {
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(chiTietDs);
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
