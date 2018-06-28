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
			// Reading xlsx file
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
							System.err.println(cell.CELL_TYPE_BLANK + cell.toString());
							if (cell.CELL_TYPE_BLANK > 1 && !cell.toString().startsWith("Ng") && DateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								logger.info("Ngay: " + date.getDate() + "/" + (date.getMonth() + 1) + "/" + (1900 + date.getYear()));
								DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
								String d = date.getDate() + "/" + (date.getMonth() + 1) + "/" + (1900 + date.getYear());
								timekeeping.setDate(df.parse(d));
							}
						}

						// Đọc mã NV
						if (columnStrIndex.equalsIgnoreCase("E")) {
							String value = cell.getStringCellValue();
							if (value != null && !value.trim().equals("") && !value.startsWith("Mã")) {
								value = value.trim();
								if (value.length() > 3) {
									value = value.substring(2, value.length());
									timekeeping.setEmployeeId(Integer.parseInt(value));
									logger.info("Mã NV: " + value);
								}
							}
						}
						
						// Đọc tên NV
						if (columnStrIndex.equalsIgnoreCase("F")) {
							String value = cell.getStringCellValue();
							if (value != null && !value.trim().equals("")) {
								value = value.trim();
								timekeeping.setEmployeeName(value);
								logger.info("Tên NV: " + value);								
							}
						}

						// Đọc giờ vào
						if (columnStrIndex.equalsIgnoreCase("L")) {
							CellType cellType = cell.getCellTypeEnum();
							switch (cellType) {
							case STRING:
								String value = cell.getStringCellValue();
								if(value != null && !value.trim().equals("") && !value.startsWith("Giờ")) {
									timekeeping.setTimeIn(value);
									logger.info("Giờ vào: " + value);
									StringTokenizer st = new StringTokenizer(value, ":");
									int h = 0;
									int m = 0;
									while (st.hasMoreElements()) {
										h = Integer.parseInt(st.nextToken());// System.out.println(h);
										m = Integer.parseInt(st.nextToken());// System.out.println(s);
									}
									
									// check in late at morning
									int lateMValue = 0;
									int hRequireM = Integer.parseInt(hr.getProperty("TIME_CHECK_IN_MORNING_H"));
									int mRequireM = Integer.parseInt(hr.getProperty("TIME_CHECK_IN_MORNING_M"));								
									if ((h > hRequireM && h < Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_MORNING_H"))) 
											|| (h > hRequireM && h == Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_MORNING_H")) 
											    && m < Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_MORNING_M")))) {
										
										lateMValue = (h	- hRequireM);
										if(m >= mRequireM)
											timekeeping.setComeLateM(String.valueOf(lateMValue*60 + (m - mRequireM)));
										else
											timekeeping.setComeLateM(String.valueOf((lateMValue-1)*60 + ((60 - mRequireM) + m)));
										//timekeeping.setComeLateM("0" + lateMValue + ":" + s);
									} else if (h == Integer.parseInt(hr.getProperty("TIME_CHECK_IN_MORNING_H")) && m > mRequireM) {
										//System.err.println("Muon sang: " + 0 + ":" + m);
										timekeeping.setComeLateM(String.valueOf(m - mRequireM));
									}
									
									// check in late at afternoon
									int lateAValue = 0;
									int hRequireA = Integer.parseInt(hr.getProperty("TIME_CHECK_IN_AFTERNOON_H"));
									int mRequireA = Integer.parseInt(hr.getProperty("TIME_CHECK_IN_AFTERNOON_M"));
									
									//can nhac care them dam bao ko tinh di muon cho t/h check in sau gio ve, vi se tinh la nghi ko phep buoi chieu								
									if (h > hRequireA) {
										lateAValue = h - hRequireA;									
										//System.err.println("Muon chieu: " + lateAValue + ":" + m);
										//System.err.println(String.valueOf(lateAValue*60 + m));
										if(m >= mRequireA){
											timekeeping.setComeLateA(String.valueOf(lateAValue*60 + (m - mRequireA)));
										}else {
											timekeeping.setComeLateA(String.valueOf((lateAValue-1)*60 + (60 - mRequireA) + m));
										}
									} else if (h == hRequireA 
											&& m > Integer.parseInt(hr.getProperty("TIME_CHECK_IN_AFTERNOON_M"))) {
										//System.err.println("Muon chieu: " + 0 + ":" + m);
										timekeeping.setComeLateA(String.valueOf(m - Integer.parseInt(hr.getProperty("TIME_CHECK_IN_AFTERNOON_M"))));
									}
	
									break;
								}
							case NUMERIC:
								if (cell.CELL_TYPE_BLANK > 1 && !cell.toString().startsWith("Giờ")){
									Double doubleValue = cell.getNumericCellValue();
									if (!(doubleValue > 0)) {
										timekeeping.setTimeIn(doubleValue.toString());
										logger.info("Gio vao: " + doubleValue.toString());
										break;
									}
								}
							}
						}
						// Đọc giờ ra
						if (columnStrIndex.equalsIgnoreCase("M")) {
							CellType cellType = cell.getCellTypeEnum();
							switch (cellType) {
							case STRING:
								String value = cell.getStringCellValue();
								if(value != null && !value.trim().equals("") && !value.startsWith("Giờ")) {
									timekeeping.setTimeOut(value);
									logger.info("Gio ra: " + value);
	
									StringTokenizer st = new StringTokenizer(value, ":");
									String h = "0";
									String m = "0";
									while (st.hasMoreElements()) {
										h = st.nextToken();// System.out.println(h);
										m = st.nextToken();// System.out.println(s);
									}
									
									// check out soon morning
									int soonMValue = 0;
									if (Integer.parseInt(h) < Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_MORNING_H"))) {
										soonMValue = Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_MORNING_H")) - (Integer.parseInt(h));
										if(Integer.parseInt(m) <= Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_MORNING_M"))){
											timekeeping.setLeaveSoonM(String.valueOf((soonMValue*60 + Integer.parseInt(m))));
										}else {
											timekeeping.setLeaveSoonM(String.valueOf(((soonMValue - 1)*60 + ((60 - Integer.parseInt(m)) + Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_MORNING_M"))))));
										}
									}else if(Integer.parseInt(h) == Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_MORNING_H")) && Integer.parseInt(m) < Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_MORNING_M"))) {
										timekeeping.setLeaveSoonM(String.valueOf(Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_MORNING_M")) - Integer.parseInt(m)));
									}
									
									// check out soon afternoon
									int soonAValue = 0;								
									if ((Integer.parseInt(h) > Integer.parseInt(hr.getProperty("TIME_CHECK_IN_AFTERNOON_H")) 
											|| (Integer.parseInt(h) == Integer.parseInt(hr.getProperty("TIME_CHECK_IN_AFTERNOON_H")) 
											    && Integer.parseInt(m) == Integer.parseInt(hr.getProperty("TIME_CHECK_IN_AFTERNOON_M"))))
											&& (Integer.parseInt(h) < Integer.parseInt(hr.getProperty("TIME_CHECK_IN_AFTERNOON_H")) 
											|| (Integer.parseInt(h) == Integer.parseInt(hr.getProperty("TIME_CHECK_IN_AFTERNOON_H")) 
											   && (Integer.parseInt(m) < Integer.parseInt(hr.getProperty("TIME_CHECK_IN_AFTERNOON_M")))))){
										if(Integer.parseInt(h) < Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_AFTERNOON_H"))) {									
											soonAValue = Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_AFTERNOON_H"))	- (Integer.parseInt(h));
											if(Integer.parseInt(m) >= Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_AFTERNOON_M"))) {
												timekeeping.setLeaveSoonA(String.valueOf(soonAValue*60 + (Integer.parseInt(m) - Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_AFTERNOON_M")))));
											}else {
												timekeeping.setLeaveSoonA(String.valueOf((soonAValue-1)*60 + (60 - Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_AFTERNOON_M"))) + Integer.parseInt(m)));
											}
										}else if(Integer.parseInt(h) == Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_AFTERNOON_H")) && Integer.parseInt(m) > Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_AFTERNOON_M"))) {
											timekeeping.setLeaveSoonA(String.valueOf(Integer.parseInt(m) - Integer.parseInt(hr.getProperty("TIME_CHECK_OUT_AFTERNOON_M"))));
										}
									}
									break;
								}
							case NUMERIC:
								if (cell.CELL_TYPE_BLANK > 1 && !cell.toString().startsWith("Giờ")){
									Double doubleValue = cell.getNumericCellValue();
									if (!(doubleValue > 0)) {
										timekeeping.setTimeOut(doubleValue.toString());
										logger.info("Gio ra: " + doubleValue.toString());
										break;
									}
								}
							}
						}

					// Đọc ghi chú
					/* Comment lai chi update khi co update = tay
 					if (columnStrIndex.equalsIgnoreCase("P")) {
							// CellType cellType = cell.getCellTypeEnum();
							String value = cell.getStringCellValue();
							if (value != null && !value.trim().equals("")) {
								timekeeping.setComment(value.trim());
								logger.info("Comment: " + value.trim());
							}
						}*/
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
