package com.idi.finance.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
	public static List<BalanceSheet> readExcel(InputStream in) throws IOException {
		List<BalanceSheet> financies = new ArrayList<BalanceSheet>();

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

			BalanceSheet finance = new BalanceSheet();
			// Read by shell
			Iterator<Cell> cellIter = row.iterator();
			while (cellIter.hasNext()) {
				Cell cell = cellIter.next();
				String columnStrIndex = CellReference.convertNumToColString(cell.getColumnIndex());

				// Read title
				if (columnStrIndex.equalsIgnoreCase("B")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case STRING:
						String title = cell.getStringCellValue();
						String rule = "";
						if (title != null && title.indexOf(".") > 0) {
							title = title.substring(title.indexOf(".") + 1, title.length());
						}
						if (title != null && title.indexOf("-") > 0) {
							title = title.substring(title.indexOf("-") + 1, title.length());
						}
						if (title != null && title.indexOf("(") > 0) {
							rule = title.substring(title.indexOf("(") + 1, title.indexOf(")"));
							title = title.substring(0, title.indexOf("("));
						}
						if (title != null) {
							title = title.trim();
						}
						if (rule != null) {
							rule = rule.trim();
						}
						finance.setAssetsName(title);
						finance.setRule(rule);
						break;
					}
				}

				// Read code
				if (columnStrIndex.equalsIgnoreCase("G")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case NUMERIC:
						String code = cell.getNumericCellValue() + "";
						finance.setAssetsCode(code.substring(0, code.indexOf(".")));
						break;
					case STRING:
						finance.setAssetsCode(cell.getStringCellValue());
						break;
					}
				}

				// Read description
				if (columnStrIndex.equalsIgnoreCase("H")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case STRING:
						finance.setDescription(cell.getStringCellValue());
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
							finance.setAssetsValue(value.getNumberValue());
							break;
						}
						break;
					case NUMERIC:
						finance.setAssetsValue(cell.getNumericCellValue());
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
						case STRING:
							finance.setChangedRatio(value.getNumberValue());
							break;
						}
						break;
					case STRING:
						finance.setChangedRatio(cell.getNumericCellValue());
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
							finance.setAssetsPeriod(getPeriod(value.getStringValue()));
							break;
						}
						break;
					case STRING:
						finance.setAssetsPeriod(getPeriod(cell.getStringCellValue()));
						break;
					}
				}
			}

			financies.add(finance);
			System.out.println(finance);
		}

		return financies;
	}

	private static Date getPeriod(String month) {
		Calendar cal = Calendar.getInstance();
		month = month.substring(5).trim();
		switch (month) {
		case "1":
			cal.set(Calendar.MONTH, 1);
			break;
		case "2":
			cal.set(Calendar.MONTH, 2);
			break;
		case "3":
			cal.set(Calendar.MONTH, 3);
			break;
		case "4":
			cal.set(Calendar.MONTH, 4);
			break;
		case "5":
			cal.set(Calendar.MONTH, 5);
			break;
		case "6":
			cal.set(Calendar.MONTH, 6);
			break;
		case "7":
			cal.set(Calendar.MONTH, 7);
			break;
		case "8":
			cal.set(Calendar.MONTH, 8);
			break;
		case "9":
			cal.set(Calendar.MONTH, 9);
			break;
		case "10":
			cal.set(Calendar.MONTH, 10);
			break;
		case "11":
			cal.set(Calendar.MONTH, 11);
			break;
		case "12":
			cal.set(Calendar.MONTH, 12);
			break;
		}
		return cal.getTime();
	}
}
