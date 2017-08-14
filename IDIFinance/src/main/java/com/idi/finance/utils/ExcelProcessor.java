package com.idi.finance.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.idi.finance.bean.BalanceSheet;

public class ExcelProcessor {
	private static final Logger logger = Logger.getLogger(ExcelProcessor.class);

	public static List<BalanceSheet> readExcel(InputStream in) throws IOException {
		List<BalanceSheet> bss = new ArrayList<BalanceSheet>();

		// Reading xls file
		XSSFWorkbook workbook = new XSSFWorkbook(in);

		// Reading sheet 35
		XSSFSheet sheet = workbook.getSheetAt(35);

		// Reading each row
		Iterator<Row> rowIter = sheet.iterator();
		while (rowIter.hasNext()) {
			Row row = rowIter.next();
			if (row.getRowNum() < 17) {
				continue;
			}

			BalanceSheet bs = new BalanceSheet();
			// Read by shell
			Iterator<Cell> cellIter = row.iterator();
			while (cellIter.hasNext()) {
				Cell cell = cellIter.next();
				String columnStrIndex = CellReference.convertNumToColString(cell.getColumnIndex());

				// Read name
				if (columnStrIndex.equalsIgnoreCase("B")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case STRING:
						String name = cell.getStringCellValue();
						String rule = "";
						if (name != null && name.indexOf(".") > 0) {
							name = name.substring(name.indexOf(".") + 1, name.length());
						}
						if (name != null && name.indexOf("-") > 0) {
							name = name.substring(name.indexOf("-") + 1, name.length());
						}
						if (name != null && name.indexOf("(") > 0) {
							rule = name.substring(name.indexOf("(") + 1, name.indexOf(")"));
							name = name.substring(0, name.indexOf("("));
						}
						if (name != null) {
							name = name.trim();
						}
						if (rule != null) {
							rule = rule.trim();
						}
						bs.setAssetsName(name);
						bs.setRule(rule);
						break;
					}
				}

				// Read code
				if (columnStrIndex.equalsIgnoreCase("G")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case NUMERIC:
						String code = cell.getNumericCellValue() + "";
						bs.setAssetsCode(code.substring(0, code.indexOf(".")));
						break;
					case STRING:
						bs.setAssetsCode(cell.getStringCellValue());
						break;
					}
				}

				// Read description
				if (columnStrIndex.equalsIgnoreCase("H")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case STRING:
						bs.setDescription(cell.getStringCellValue());
						break;
					}
				}

				// Read value
				if (columnStrIndex.equalsIgnoreCase("I")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case FORMULA:
						// Formula
						FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

						// Get value
						CellValue value = evaluator.evaluate(cell);
						CellType cellValueType = value.getCellTypeEnum();
						switch (cellValueType) {
						case NUMERIC:
							bs.setAssetsValue(value.getNumberValue());
							break;
						}
						break;
					case NUMERIC:
						bs.setAssetsValue(cell.getNumericCellValue());
						break;
					}
				}

				// Read changed ration
				if (columnStrIndex.equalsIgnoreCase("M")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case FORMULA:
						// Formula
						FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

						// Get value
						CellValue value = evaluator.evaluate(cell);
						CellType cellValueType = value.getCellTypeEnum();
						switch (cellValueType) {
						case NUMERIC:
							bs.setChangedRatio(value.getNumberValue());
							break;
						}
						break;
					}
				}

				// Read period
				if (columnStrIndex.equalsIgnoreCase("N")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case FORMULA:
						// Formula
						FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

						// Get value
						CellValue value = evaluator.evaluate(cell);
						CellType cellValueType = value.getCellTypeEnum();
						switch (cellValueType) {
						case STRING:
							bs.setAssetsPeriod(getPeriod(value.getStringValue()));
							break;
						}
						break;
					case STRING:
						bs.setAssetsPeriod(getPeriod(cell.getStringCellValue()));
						break;
					}
				}
			}

			bss.add(bs);
		}

		return bss;
	}

	public boolean validateData() {
		return true;
	}

	private static Date getPeriod(String month) {
		Calendar cal = Calendar.getInstance();
		try {
			month = month.substring(5).trim();
			int monthNum = Integer.parseInt(month) - 1;
			cal.set(Calendar.MONTH, monthNum);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}

		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}
}
