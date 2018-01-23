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

import com.idi.finance.bean.bieudo.KpiChart;
import com.idi.finance.bean.bieudo.KpiGroup;
import com.idi.finance.bean.cdkt.BalanceAssetData;
import com.idi.finance.bean.cdkt.BalanceAssetItem;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public class ExcelProcessor {
	private static final Logger logger = Logger.getLogger(ExcelProcessor.class);

	public static List<KpiGroup> readKpiMeasuresSheet(InputStream in) throws IOException {
		List<KpiGroup> kpiGroups = new ArrayList<KpiGroup>();

		// Reading xls file
		XSSFWorkbook workbook = new XSSFWorkbook(in);

		// Reading sheet 3
		XSSFSheet sheet = workbook.getSheetAt(3);
		logger.info(sheet.getSheetName());

		KpiGroup kpiGroup = null;

		// Reading each row
		Iterator<Row> rowIter = sheet.iterator();
		while (rowIter.hasNext()) {
			Row row = rowIter.next();
			if (row.getRowNum() < 3) {
				continue;
			}

			KpiChart kpiChart = new KpiChart();

			// Read by shell
			Iterator<Cell> cellIter = row.iterator();
			while (cellIter.hasNext()) {
				Cell cell = cellIter.next();
				String columnStrIndex = CellReference.convertNumToColString(cell.getColumnIndex());

				// Read name of kpi group
				if (columnStrIndex.equalsIgnoreCase("B")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case STRING:
						String name = cell.getStringCellValue();

						kpiGroup = new KpiGroup();
						kpiGroup.setGroupName(Utils.format(name));

						// Add kpiGroup item to kpiGroups list
						int pos = kpiGroups.indexOf(kpiGroup);
						if (pos > -1) {
							kpiGroup = kpiGroups.get(pos);
						} else {
							kpiGroups.add(kpiGroup);
						}
						break;
					}
				}

				// Read name of kpi chart's title
				if (columnStrIndex.equalsIgnoreCase("C")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case STRING:
						String title = cell.getStringCellValue();
						kpiChart.setChartTitle(Utils.format(title));
						break;
					}
				}

				// Read name of kpi chart's title in english
				if (columnStrIndex.equalsIgnoreCase("D")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case STRING:
						String title = cell.getStringCellValue();
						kpiChart.setChartTitleEn(Utils.format(title));
						break;
					}
				}
			}

			// kpiChart.setKpiGroup(kpiGroup);
			kpiGroup.addKpiCharts(kpiChart);

			// logger.info(kpiGroup + " " + kpiGroup.getKpiCharts().size());
		}

		return kpiGroups;
	}

	public static List<BalanceAssetData> readBalanceAssetSheetExcel(InputStream in) throws IOException {
		List<BalanceAssetData> bss = new ArrayList<BalanceAssetData>();

		// Reading xls file
		XSSFWorkbook workbook = new XSSFWorkbook(in);

		// Reading sheet 35
		XSSFSheet sheet = workbook.getSheetAt(35);
		logger.info(sheet.getSheetName());

		// Reading each row
		Iterator<Row> rowIter = sheet.iterator();
		while (rowIter.hasNext()) {
			Row row = rowIter.next();
			if (row.getRowNum() < 17) {
				continue;
			}

			BalanceAssetData bs = new BalanceAssetData();
			BalanceAssetItem bai = new BalanceAssetItem();
			bs.setAsset(bai);

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
						String rule = parseRule(name);
						name = parseName(name);

						bs.getAsset().setAssetName(Utils.format(name));
						bs.getAsset().setRule(rule);
						break;
					}
				}

				// Read code
				if (columnStrIndex.equalsIgnoreCase("G")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case NUMERIC:
						String code = cell.getNumericCellValue() + "";
						bs.getAsset().setAssetCode(code.substring(0, code.indexOf(".")));
						break;
					case STRING:
						bs.getAsset().setAssetCode(cell.getStringCellValue());
						break;
					}
				}

				// Read description
				if (columnStrIndex.equalsIgnoreCase("H")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case STRING:
						bs.getAsset().setNote(cell.getStringCellValue());
						break;
					}
				}

				// Read end value
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
							bs.setEndValue(value.getNumberValue());
							break;
						}
						break;
					case NUMERIC:
						bs.setEndValue(cell.getNumericCellValue());
						break;
					}
				}

				// Read start value
				if (columnStrIndex.equalsIgnoreCase("J")) {
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
							bs.setStartValue(value.getNumberValue());
							break;
						}
						break;
					case NUMERIC:
						bs.setStartValue(cell.getNumericCellValue());
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
							bs.setPeriod(getPeriod(value.getStringValue()));
							break;
						}
						break;
					case STRING:
						bs.setPeriod(getPeriod(cell.getStringCellValue()));
						break;
					}
				}
			}

			bss.add(bs);
		}

		return bss;
	}

	public static List<BalanceAssetData> readSaleResultSheetExcel(InputStream in) throws IOException {
		List<BalanceAssetData> bss = new ArrayList<BalanceAssetData>();

		// Reading xls file
		XSSFWorkbook workbook = new XSSFWorkbook(in);

		// Reading sheet 37
		XSSFSheet sheet = workbook.getSheetAt(37);
		logger.info(sheet.getSheetName());

		// Reading each row
		Iterator<Row> rowIter = sheet.iterator();
		while (rowIter.hasNext()) {
			Row row = rowIter.next();
			if (row.getRowNum() < 4) {
				continue;
			}

			BalanceAssetData bs = new BalanceAssetData();
			BalanceAssetItem bai = new BalanceAssetItem();
			bs.setAsset(bai);

			// Read by shell
			Iterator<Cell> cellIter = row.iterator();
			while (cellIter.hasNext()) {
				Cell cell = cellIter.next();
				String columnStrIndex = CellReference.convertNumToColString(cell.getColumnIndex());

				// Read name
				if (columnStrIndex.equalsIgnoreCase("A")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case STRING:
						String name = cell.getStringCellValue();
						String rule = parseRule(name);
						name = parseName(name);

						bs.getAsset().setAssetName(Utils.format(name));
						bs.getAsset().setRule(rule);
						break;
					}
				}

				// Read code
				if (columnStrIndex.equalsIgnoreCase("B")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case NUMERIC:
						String code = cell.getNumericCellValue() + "";
						bs.getAsset().setAssetCode(code.substring(0, code.indexOf(".")));
						break;
					case STRING:
						bs.getAsset().setAssetCode(cell.getStringCellValue());
						break;
					}
				}

				// Read description
				if (columnStrIndex.equalsIgnoreCase("C")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case STRING:
						bs.getAsset().setNote(cell.getStringCellValue());
						break;
					}
				}

				// Read end value
				if (columnStrIndex.equalsIgnoreCase("D")) {
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
							bs.setEndValue(value.getNumberValue());
							break;
						}
						break;
					case NUMERIC:
						bs.setEndValue(cell.getNumericCellValue());
						break;
					}
				}

				// Read start value
				if (columnStrIndex.equalsIgnoreCase("E")) {
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
							bs.setStartValue(value.getNumberValue());
							break;
						}
						break;
					case NUMERIC:
						bs.setStartValue(cell.getNumericCellValue());
						break;
					}
				}

				// Read changed ration
				if (columnStrIndex.equalsIgnoreCase("H")) {
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
						case STRING:
							bs.setPeriod(getPeriod(value.getStringValue()));
							break;
						}
						break;
					case STRING:
						bs.setPeriod(getPeriod(cell.getStringCellValue()));
						break;
					}
				}
			}

			bss.add(bs);
		}

		return bss;
	}

	public static List<BalanceAssetData> readCashFlowsSheetExcel(InputStream in) throws IOException {
		List<BalanceAssetData> bss = new ArrayList<BalanceAssetData>();

		// Reading xls file
		XSSFWorkbook workbook = new XSSFWorkbook(in);

		// Reading sheet 38
		XSSFSheet sheet = workbook.getSheetAt(38);
		logger.info(sheet.getSheetName());

		// Reading each row
		Iterator<Row> rowIter = sheet.iterator();
		while (rowIter.hasNext()) {
			Row row = rowIter.next();
			if (row.getRowNum() < 4) {
				continue;
			}

			BalanceAssetData bs = new BalanceAssetData();
			BalanceAssetItem bai = new BalanceAssetItem();
			bs.setAsset(bai);

			// Read by shell
			Iterator<Cell> cellIter = row.iterator();
			while (cellIter.hasNext()) {
				Cell cell = cellIter.next();
				String columnStrIndex = CellReference.convertNumToColString(cell.getColumnIndex());

				// Read name
				if (columnStrIndex.equalsIgnoreCase("A")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case STRING:
						String name = cell.getStringCellValue();
						String rule = parseRule(name);
						name = parseName(name);

						bs.getAsset().setAssetName(Utils.format(name));
						bs.getAsset().setRule(rule);
						break;
					}
				}

				// Read code
				if (columnStrIndex.equalsIgnoreCase("B")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case NUMERIC:
						String code = cell.getNumericCellValue() + "";
						bs.getAsset().setAssetCode(code.substring(0, code.indexOf(".")));
						break;
					case STRING:
						bs.getAsset().setAssetCode(cell.getStringCellValue());
						break;
					}
				}

				// Read description
				if (columnStrIndex.equalsIgnoreCase("C")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case STRING:
						bs.getAsset().setNote(cell.getStringCellValue());
						break;
					}
				}

				// Read end value
				if (columnStrIndex.equalsIgnoreCase("D")) {
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
							bs.setEndValue(value.getNumberValue());
							break;
						}
						break;
					case NUMERIC:
						bs.setEndValue(cell.getNumericCellValue());
						break;
					}
				}

				// Read start value
				if (columnStrIndex.equalsIgnoreCase("E")) {
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
							bs.setStartValue(value.getNumberValue());
							break;
						}
						break;
					case NUMERIC:
						bs.setStartValue(cell.getNumericCellValue());
						break;
					}
				}

				// Read changed ration
				if (columnStrIndex.equalsIgnoreCase("H")) {
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
						case STRING:
							bs.setPeriod(getPeriod(value.getStringValue()));
							break;
						}
						break;
					case STRING:
						bs.setPeriod(getPeriod(cell.getStringCellValue()));
						break;
					}
				}
			}

			// logger.info(bs);
			bss.add(bs);
		}

		return bss;
	}

	public static List<LoaiTaiKhoan> docTaiKhoanExcel(InputStream in) throws IOException {
		List<LoaiTaiKhoan> taiKhoanDm = new ArrayList<LoaiTaiKhoan>();

		// Reading xls file
		XSSFWorkbook workbook = new XSSFWorkbook(in);

		// Reading sheet 1
		XSSFSheet sheet = workbook.getSheetAt(0);
		logger.info(sheet.getSheetName());

		// Reading each row
		Iterator<Row> rowIter = sheet.iterator();
		while (rowIter.hasNext()) {
			Row row = rowIter.next();
			if (row.getRowNum() < 3) {
				continue;
			}

			LoaiTaiKhoan taiKhoan = new LoaiTaiKhoan();
			// Read by shell
			Iterator<Cell> cellIter = row.iterator();
			while (cellIter.hasNext()) {
				Cell cell = cellIter.next();
				String columnStrIndex = CellReference.convertNumToColString(cell.getColumnIndex());

				// Đọc mã tài khoản cấp 1
				if (columnStrIndex.equalsIgnoreCase("A")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case STRING:
						String value = cell.getStringCellValue();

						if (value != null && !value.trim().equals("")) {
							value = value.trim();
							taiKhoan.setMaTk(value);
						}
						break;
					case NUMERIC:
						Double doubleValue = cell.getNumericCellValue();
						taiKhoan.setMaTk(doubleValue.intValue() + "");
						break;
					}
				}

				// Đọc mã tài khoản cấp 2
				if (columnStrIndex.equalsIgnoreCase("B")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case STRING:
						String value = cell.getStringCellValue();

						if (value != null && !value.trim().equals("")) {
							value = value.trim();
							taiKhoan.setMaTk(value);
							if (value.length() > 3) {
								taiKhoan.setMaTkCha(value.substring(0, value.length() - 1));
							}
						}
						break;
					case NUMERIC:
						Double doubleValue = cell.getNumericCellValue();
						taiKhoan.setMaTk(doubleValue.intValue() + "");
						String maTk = taiKhoan.getMaTk();
						if (maTk != null) {
							taiKhoan.setMaTkCha(maTk.substring(0, maTk.length() - 1));
						}
						break;
					}
				}

				// Đọc tên tài khoản cấp
				if (columnStrIndex.equalsIgnoreCase("C")) {
					CellType cellType = cell.getCellTypeEnum();
					switch (cellType) {
					case STRING:
						String value = cell.getStringCellValue();

						if (value != null) {
							taiKhoan.setTenTk(value.trim());
						}
						break;
					case NUMERIC:
						taiKhoan.setMaTk(cell.getNumericCellValue() + "");
						break;
					}
				}
			}
			logger.info(taiKhoan);
			taiKhoanDm.add(taiKhoan);
		}

		return taiKhoanDm;
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

		return Utils.standardDate(cal.getTime());
	}

	private static String parseRule(String name) {
		if (name != null) {
			int rulePos = hasRule(name);
			if (rulePos > -1) {
				return name.substring(rulePos).trim();
			}
		}
		return "";
	}

	private static String parseName(String name) {
		if (name != null) {
			String[] firstStrs = { ".", "-" };
			int firstPos = getPos(name, firstStrs);
			if (firstPos > -1)
				name = name.substring(firstPos + 1, name.length()).trim();

			int rulePos = hasRule(name);
			if (rulePos > -1)
				name = name.substring(0, rulePos).trim();
		}

		return name;
	}

	private static int hasRule(String name) {
		if (name != null) {
			String[] prefixStrs = { "(", "[" };
			int prefixPos = getPos(name, prefixStrs);
			if (prefixPos > -1) {
				name = name.substring(prefixPos, name.length()).trim();

				String[] equalStrs = { "=", "*" };
				int equalPos = getPos(name, equalStrs);

				if (equalPos > -1)
					return prefixPos;
				else
					return -1;
			}
		}

		return -1;
	}

	private static int getPos(String name, String[] strs) {
		if (name == null)
			return -1;

		int minPos = name.length();
		for (String str : strs) {
			int pos = name.indexOf(str);
			if (pos > -1) {
				if (minPos > pos) {
					minPos = pos;
				}
			}
		}

		if (minPos == name.length()) {
			return -1;
		}

		return minPos;
	}
}
