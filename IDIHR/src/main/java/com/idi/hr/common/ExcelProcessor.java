package com.idi.hr.common;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.idi.hr.bean.Timekeeping;

public class ExcelProcessor {
	private static final Logger logger = Logger.getLogger(ExcelProcessor.class);
	PropertiesManager hr = new PropertiesManager("hr.properties");

	public List<Timekeeping> loadTimekeepingDataFromExcel(InputStream in) throws IOException {
		List<Timekeeping> timekeepings = new ArrayList<Timekeeping>();
		try {
			// Reading xls file
			XSSFWorkbook workbook = new XSSFWorkbook(in);

			// Reading sheet 1
			XSSFSheet sheet = workbook.getSheetAt(0);
			// logger.info(sheet.getSheetName());

			// Reading each row
			Iterator<Row> rowIter = sheet.iterator();
			while (rowIter.hasNext()) {
				Row row = rowIter.next();
				if (row.getRowNum() >= 8) {
					// continue;
					logger.info("Row number: " + row.getRowNum());
					Timekeeping timekeeping = new Timekeeping();
					// Read by shell
					Iterator<Cell> cellIter = row.iterator();
					while (cellIter.hasNext()) {
						Cell cell = cellIter.next();
						String columnStrIndex = CellReference.convertNumToColString(cell.getColumnIndex());

						// Đọc ngay o cot C
						if (columnStrIndex.equalsIgnoreCase("C")) {
							// CellType cellType = cell.getCellTypeEnum();
							if (DateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								logger.info("Ngay: " + date.getDate() + "/" + (date.getMonth() + 1) + "/"
										+ (1900 + date.getYear()));
								DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
								String d = date.getDate() + "/" + (date.getMonth() + 1) + "/" + (1900 + date.getYear());
								timekeeping.setDate(df.parse(d));
							}

						}

						// Đọc mã NV
						if (columnStrIndex.equalsIgnoreCase("E")) {
							String value = cell.getStringCellValue();
							if (value != null && !value.trim().equals("")) {
								value = value.trim();
								if (value.length() > 3) {
									value = value.substring(2, value.length());
									timekeeping.setEmployeeId(Integer.parseInt(value));
									logger.info("Ma NV: " + value);
								}
							}
						}

						// Đọc giờ vào
						if (columnStrIndex.equalsIgnoreCase("L")) {
							CellType cellType = cell.getCellTypeEnum();
							switch (cellType) {
							case STRING:
								String value = cell.getStringCellValue();
								timekeeping.setTimeIn(value);
								logger.info("Gio vao: " + value);
								StringTokenizer st = new StringTokenizer(value, ":");
								String h = "0";
								String s = "0";
								while (st.hasMoreElements()) {
									h = st.nextToken();// System.out.println(h);
									s = st.nextToken();// System.out.println(s);
								}
								// check in late at morning
								int lateMValue = 0;
								if (Integer.parseInt(h) > Integer.parseInt(hr.getProperty("TIME_CHECK_IN_MORNING"))
										&& Integer.parseInt(h) < Integer
												.parseInt(hr.getProperty("TIME_CHECK_OUT_MORNING"))) {
									lateMValue = Integer.parseInt(h)
											- Integer.parseInt(hr.getProperty("TIME_CHECK_IN_MORNING"));
									System.err.println("Muon sang: " + lateMValue + ":" + s);
									timekeeping.setComeLateM("0" + lateMValue + ":" + s);
								} else if (Integer.parseInt(h) == Integer
										.parseInt(hr.getProperty("TIME_CHECK_IN_MORNING")) && Integer.parseInt(s) > 0) {
									System.err.println("Muon sang: " + 0 + ":" + s);
									timekeeping.setComeLateM(0 + ":" + s);
								}
								// check in late at afternoon
								int lateAValue = 0;
								if (Integer.parseInt(h) > Integer.parseInt(hr.getProperty("TIME_CHECK_IN_AFTERNOON"))) {
									lateAValue = Integer.parseInt(h)
											- Integer.parseInt(hr.getProperty("TIME_CHECK_IN_AFTERNOON"));
									System.err.println("Muon chieu: " + lateAValue + ":" + s);
									timekeeping.setComeLateA("0" + lateAValue + ":" + s);
								} else if (Integer.parseInt(h) == Integer
										.parseInt(hr.getProperty("TIME_CHECK_IN_AFTERNOON"))
										&& Integer.parseInt(s) > 0) {
									System.err.println("Muon chieu: " + 0 + ":" + s);
									timekeeping.setComeLateA(0 + ":" + s);
								}

								break;
							case NUMERIC:
								Double doubleValue = cell.getNumericCellValue();
								if (!(doubleValue > 0)) {
									timekeeping.setTimeIn(doubleValue.toString());
									logger.info("Gio vao: " + doubleValue.toString());
									break;
								}
							}
						}
						// Đọc giờ ra
						if (columnStrIndex.equalsIgnoreCase("M")) {
							CellType cellType = cell.getCellTypeEnum();
							switch (cellType) {
							case STRING:
								String value = cell.getStringCellValue();
								timekeeping.setTimeOut(value);
								logger.info("Gio ra: " + value);

								StringTokenizer st = new StringTokenizer(value, ":");
								String h = "0";
								String s = "0";
								while (st.hasMoreElements()) {
									h = st.nextToken();// System.out.println(h);
									s = st.nextToken();// System.out.println(s);
								}
								// check out soon morning
								int soonMValue = 0;
								if (Integer.parseInt(h) < Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_MORNING"))) {
									soonMValue = Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_MORNING"))
											- (Integer.parseInt(h) + 1);
									System.err.println("Ve som sang: " + soonMValue + ":" + (60 - Integer.parseInt(s)));
									String hour = "0";
									int min = 60 - Integer.parseInt(s);

									if (soonMValue > 0 && soonMValue < 10)
										hour = "0" + soonMValue;
									if (0 < min && min < 10)
										s = "0" + min;

									timekeeping.setLeaveSoonM(hour + ":" + s);
								}
								// check out soon afternoon
								int soonAValue = 0;
								if (Integer.parseInt(h) >= Integer.parseInt(hr.getProperty("TIME_CHECK_IN_AFTERNOON"))
										&& Integer.parseInt(h) < Integer
												.parseInt(hr.getProperty("TIME_CHECK_OUT_AFTERNOON"))) {
									soonAValue = Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_AFTERNOON"))
											- (Integer.parseInt(h) + 1);
									System.err
											.println("Ve som chieu: " + soonAValue + ":" + (60 - Integer.parseInt(s)));
									String hour = "0";
									int min = 60 - Integer.parseInt(s);

									if (soonAValue > 0 && soonAValue < 10)
										hour = "0" + soonAValue;
									if (0 < min && min < 10)
										s = "0" + min;
									timekeeping.setLeaveSoonA(hour + ":" + s);
								}

								break;
							case NUMERIC:
								Double doubleValue = cell.getNumericCellValue();
								if (!(doubleValue > 0)) {
									timekeeping.setTimeOut(doubleValue.toString());
									logger.info("Gio ra: " + doubleValue.toString());
									break;
								}
							}
						}

						// Đọc ghi chú
						if (columnStrIndex.equalsIgnoreCase("P")) {
							// CellType cellType = cell.getCellTypeEnum();
							String value = cell.getStringCellValue();
							if (value != null && !value.trim().equals("")) {
								timekeeping.setComment(value.trim());
								logger.info("Comment: " + value.trim());
							}
						}
					}

					// logger.info(timekeeping);
					if (timekeeping.getEmployeeId() > 0 && timekeeping.getTimeIn() != null) {
						System.err.println(timekeeping.getEmployeeId() + "/" + timekeeping.getTimeIn());
						timekeepings.add(timekeeping);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timekeepings;
	}

	public boolean validateData() {
		return true;
	}

}
