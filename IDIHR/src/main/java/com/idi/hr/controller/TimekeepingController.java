package com.idi.hr.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.idi.hr.bean.Department;
import com.idi.hr.bean.EmployeeInfo;
import com.idi.hr.bean.LeaveInfo;
import com.idi.hr.bean.LeaveReport;
import com.idi.hr.bean.LeaveType;
import com.idi.hr.bean.Timekeeping;
import com.idi.hr.bean.ValueReport;
import com.idi.hr.dao.DepartmentDAO;
import com.idi.hr.dao.EmployeeDAO;
import com.idi.hr.dao.LeaveDAO;
import com.idi.hr.dao.TimekeepingDAO;
import com.idi.hr.dao.WorkingDayDAO;
import com.idi.hr.form.LeaveInfoForm;
import com.idi.hr.validator.LeaveValidator;
import com.idi.hr.common.PropertiesManager;
import com.idi.hr.common.ExcelProcessor;
import com.idi.hr.common.Utils;

@Controller
public class TimekeepingController {
	private static final Logger log = Logger.getLogger(TimekeepingController.class.getName());

	@Autowired
	private TimekeepingDAO timekeepingDAO;

	@Autowired
	private LeaveDAO leaveDAO;

	@Autowired
	private EmployeeDAO employeeDAO;

	@Autowired
	private DepartmentDAO departmentDAO;

	@Autowired
	private WorkingDayDAO workingDayDAO;

	@Autowired
	private LeaveValidator leaveValidator;

	private ArrayList<LeaveReport> list;

	Map<String, String> leaveForReport;

	ExcelProcessor xp = new ExcelProcessor();

	PropertiesManager properties = new PropertiesManager("hr.properties");
	
	@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {

		// Form mục tiêu
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		if (target.getClass() == LeaveInfo.class) {
			dataBinder.setValidator(leaveValidator);
		}
	}

	@RequestMapping(value = "/timekeeping/updateData", method = RequestMethod.POST)
	public String updateData(Model model, @RequestParam("timeKeepingFile") MultipartFile timeKeepingFile,
			LeaveInfoForm leaveInfoForm) {
		// System.err.println("import excel file");
		// System.err.println(timeKeepingFile.getName());
		Timekeeping timekeeping = new Timekeeping();
		model.addAttribute("timekeepingForm", timekeeping);
		model.addAttribute("leaveInfoForm", leaveInfoForm);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		String currentDate = dateFormat.format(date);
		// get list department
		Map<String, String> departmentMap = this.dataForDepartments();

		// get list employee id
		Map<String, String> employeeMap = this.employees();
		model.addAttribute("employeeMap", employeeMap);

		model.addAttribute("departmentMap", departmentMap);
		
		if (timeKeepingFile != null && timeKeepingFile.getSize() > 0) {
			log.info(timeKeepingFile.getOriginalFilename() + " - " + timeKeepingFile.getSize());
			try {
				// Read & insert time keeping data
				List<Timekeeping> timekeepings = xp.loadTimekeepingDataFromExcel(timeKeepingFile.getInputStream());
				timekeepingDAO.insert(timekeepings);

				List<Timekeeping> list = timekeepingDAO.getTimekeepings(currentDate, null, null, null);
				model.addAttribute("timekeepings", list);
				model.addAttribute("formTitle", "Dữ liệu chấm công ngày " + currentDate);

				return "formTimekeeping";
			} catch (Exception e) {
				e.printStackTrace();
				String comment = "Không thể đọc excel file " + timeKeepingFile.getOriginalFilename()
						+ ". Có thể file bị lỗi, không đúng định dạng, hoặc đường truyền chậm, xin mời thử lại...";
				model.addAttribute("comment", comment);
				// model.addAttribute("tab", "tabCNDL");
				return "formTimekeeping";
			}
		} else {
			String comment = "Hãy chọn file excel dữ liệu chấm công.";
			model.addAttribute("comment", comment);
			List<Timekeeping> list = timekeepingDAO.getTimekeepings(currentDate, null, null, null);
			model.addAttribute("timekeepings", list);
			model.addAttribute("formTitle", "Dữ liệu chấm công ngày " + currentDate);
			return "formTimekeeping";
		}
	}

	@RequestMapping(value = { "/timekeeping/" }, method = RequestMethod.GET)
	public String listForTimekeeping(Model model, Timekeeping timekeeping, LeaveInfoForm leaveInfoForm) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String currentDate = dateFormat.format(date);
			List<Timekeeping> list = null;
			list = timekeepingDAO.getTimekeepings(currentDate, null, null, null);
			List<LeaveInfo> listL = null;
			listL = leaveDAO.getLeaves(currentDate, null, null, null);
			if (list.size() == 0 && listL.size() == 0)
				model.addAttribute("message", "Không có dữ liệu chấm công cho ngày " + currentDate);

			// get list department
			Map<String, String> departmentMap = this.dataForDepartments();

			// get list employee id
			Map<String, String> employeeMap = this.employees();
			model.addAttribute("employeeMap", employeeMap);

			model.addAttribute("departmentMap", departmentMap);
			model.addAttribute("timekeepings", list);
			model.addAttribute("leaveInfos", listL);
			model.addAttribute("timekeepingForm", timekeeping);
			model.addAttribute("leaveInfoForm", leaveInfoForm);
			model.addAttribute("formTitle", "Dữ liệu chấm công ngày " + currentDate);
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "formTimekeeping";
	}

	@RequestMapping(value = { "/timekeeping/listByDate" })
	public String listTimekeeping(Model model, @ModelAttribute("leaveInfoForm") LeaveInfoForm leaveInfoForm) {
		try {
			Timekeeping timekeeping = new Timekeeping();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String currentDate = dateFormat.format(date);
			List<Timekeeping> list = null;
			List<LeaveInfo> listL = null;

			String fromDate = leaveInfoForm.getDate();
			String toDate = leaveInfoForm.getToDate();
			String dept = leaveInfoForm.getDept();
			String eId = leaveInfoForm.geteId();

			// System.err.println(fromDate + "|" + toDate + "|" + dept + "|" + eId);
			if (fromDate != null && toDate != null) {
				if (fromDate != null && toDate.equalsIgnoreCase("viewDetail")) {
					Calendar calendar = Calendar.getInstance();
					// System.err.println(fromDate.substring(0, 4) + "|" + fromDate.substring(5,
					// 7));
					calendar.set(Integer.parseInt(fromDate.substring(0, 4)),
							Integer.parseInt(fromDate.substring(5, 7)) - 1, 1);
					int lastDate = calendar.getActualMaximum(calendar.DAY_OF_MONTH);
					// calendar.set(Calendar.DATE, lastDate);
					// int lastDay = calendar.get(Calendar.DAY_OF_WEEK);
					// System.out.println("Last Date: " + lastDate +"|"+ calendar.getTime());
					// System.out.println("Last Day : " + lastDay);
					toDate = fromDate.substring(0, 4) + "-" + fromDate.substring(5, 7) + "-" + lastDate;
					// System.out.println("to date " + toDate);
				}
				list = timekeepingDAO.getTimekeepings(fromDate, toDate, dept, eId);
				listL = leaveDAO.getLeaves(fromDate, toDate, dept, eId);
				if (list.size() == 0 && listL.size() == 0)
					model.addAttribute("message",
							"Không có dữ liệu chấm công từ ngày " + fromDate + " đến ngày " + toDate);
				;
			} else {
				list = timekeepingDAO.getTimekeepings(currentDate, null, null, null);
				listL = leaveDAO.getLeaves(currentDate, null, null, null);
				if (list.size() == 0 && listL.size() == 0)
					model.addAttribute("message", "Không có dữ liệu chấm công cho ngày " + currentDate);
			}
			// System.err.println(currentDate + "|" + leaveInfoForm.getDate());
			// if(list.size() == 0 && listL.size() == 0)
			// model.addAttribute("message", "Không có dữ liệu chấm công cho ngày " +
			// leaveInfoForm.getDate());

			// get list department
			Map<String, String> departmentMap = this.dataForDepartments();
			model.addAttribute("departmentMap", departmentMap);

			// get list employee id
			Map<String, String> employeeMap = this.employees();
			model.addAttribute("employeeMap", employeeMap);

			model.addAttribute("timekeepings", list);
			model.addAttribute("leaveInfos", listL);
			model.addAttribute("timekeepingForm", timekeeping);
			model.addAttribute("leaveInfoForm", leaveInfoForm);
			
			if (leaveInfoForm.getDate() != null)
				model.addAttribute("formTitle", "Dữ liệu chấm công từ ngày " + fromDate + " đến ngày " + toDate);
			else
				model.addAttribute("formTitle", "Dữ liệu chấm công ngày " + currentDate);

		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "formTimekeeping";
	}

	@RequestMapping(value = "/timekeeping/exportToExcel", method = RequestMethod.GET)
	public String exportToExcel(Model model, @RequestParam("date") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("dept") String dept, @RequestParam("eId") String eId) {
		try {

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String currentDate = dateFormat.format(date);
			List<Timekeeping> list = null;
			List<LeaveInfo> listL = null;
			LeaveInfoForm leaveInfoForm = new LeaveInfoForm();
			leaveInfoForm.setDate(fromDate);
			leaveInfoForm.setToDate(toDate);
			leaveInfoForm.setDept(dept);
			leaveInfoForm.seteId(eId);
			
			// System.err.println(fromDate + "|" + toDate + "|" + dept + "|" + eId);
			if (fromDate != null && toDate != null) {
				if (fromDate != null && toDate.equalsIgnoreCase("viewDetail")) {
					Calendar calendar = Calendar.getInstance();
					// System.err.println(fromDate.substring(0, 4) + "|" + fromDate.substring(5,
					// 7));
					calendar.set(Integer.parseInt(fromDate.substring(0, 4)),
							Integer.parseInt(fromDate.substring(5, 7)) - 1, 1);
					int lastDate = calendar.getActualMaximum(calendar.DAY_OF_MONTH);
					// calendar.set(Calendar.DATE, lastDate);
					// int lastDay = calendar.get(Calendar.DAY_OF_WEEK);
					// System.out.println("Last Date: " + lastDate +"|"+ calendar.getTime());
					// System.out.println("Last Day : " + lastDay);
					toDate = fromDate.substring(0, 4) + "-" + fromDate.substring(5, 7) + "-" + lastDate;
					// System.out.println("to date " + toDate);
				}
				list = timekeepingDAO.getTimekeepings(fromDate, toDate, dept, eId);
				listL = leaveDAO.getLeaves(fromDate, toDate, dept, eId);
				if (list.size() == 0 && listL.size() == 0)
					model.addAttribute("message",
							"Không có dữ liệu chấm công từ ngày " + fromDate + " đến ngày " + toDate);
				;
			} else {
				list = timekeepingDAO.getTimekeepings(currentDate, null, null, null);
				listL = leaveDAO.getLeaves(currentDate, null, null, null);
				if (list.size() == 0 && listL.size() == 0)
					model.addAttribute("message", "Không có dữ liệu chấm công cho ngày " + currentDate);
			}			
			
			String path = properties.getProperty("REPORT_PATH");
			File dir = new File(path);
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Dữ liệu chấm công");
	
			CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
			Font font = sheet.getWorkbook().createFont();
			font.setBold(true);
			font.setFontHeightInPoints((short) 12);
			cellStyle.setFont(font);
	
			Row row1 = sheet.createRow(0);
			Cell cell = row1.createCell(3);

			if (dept != null && dept.length() > 0) {
				if (fromDate != null) {
					cell.setCellStyle(cellStyle);
					cell.setCellValue("Dữ liệu chấm công từ ngày " + fromDate + " đến ngày " + toDate + " của phòng " + dept);
				}
			}else {
				if (fromDate != null) {
					cell.setCellStyle(cellStyle);
					cell.setCellValue("Dữ liệu chấm công từ ngày " + fromDate + " đến ngày " + toDate);
				}
			}
			
			int rowNum = 2;
			Row row2 = sheet.createRow(rowNum);
			Cell cell0 = row2.createCell(0);
			CellStyle cellStyle1 = sheet.getWorkbook().createCellStyle();
			Font font1 = sheet.getWorkbook().createFont();
			font1.setBold(true);
			font1.setFontHeightInPoints((short) 11);
			cellStyle1.setFont(font1);
			cell0.setCellStyle(cellStyle1);
			cell0.setCellValue("Dữ liệu chấm công phát sinh");
			
			// Generate column name
			rowNum = 3;
			Row row = sheet.createRow(rowNum);
			Cell cell11 = row.createCell(0);
			cell11.setCellValue("Mã NV");
			Cell cell21 = row.createCell(1);
			cell21.setCellValue("Họ tên");
			Cell cell31 = row.createCell(2);
			cell31.setCellValue("Bộ phận");
			Cell cell41 = row.createCell(3);
			cell41.setCellValue("Chức vụ");
			Cell cell51 = row.createCell(4);
			cell51.setCellValue("Ngày");
			Cell cell61 = row.createCell(5);
			cell61.setCellValue("Loại");
			Cell cell71 = row.createCell(6);
			cell71.setCellValue("Số giờ/số lần");
			Cell cell81 = row.createCell(7);
			cell81.setCellValue("Ghi chú");
	
			// generate values
			rowNum = 4;
			for (int i = 0; i < listL.size(); i++) {
				row = sheet.createRow(rowNum++);
				int colNum = 0;
				LeaveInfo leaveInfo = new LeaveInfo();
				leaveInfo = listL.get(i);
				Cell cell1 = row.createCell(colNum++);
				cell1.setCellValue((Integer) leaveInfo.getEmployeeId());
				Cell cell2 = row.createCell(colNum++);
				cell2.setCellValue((String) leaveInfo.getEmployeeName());
				Cell cell3 = row.createCell(colNum++);
				cell3.setCellValue((String) leaveInfo.getDepartment());
				Cell cell4 = row.createCell(colNum++);
				cell4.setCellValue((String) leaveInfo.getTitle());
				Cell cell5 = row.createCell(colNum++);
				cell5.setCellValue(leaveInfo.getDate().toString());
				Cell cell6 = row.createCell(colNum++);
				cell6.setCellValue((String) leaveInfo.getLeaveName());
				Cell cell7 = row.createCell(colNum++);
				cell7.setCellValue((float) leaveInfo.getTimeValue());
				Cell cell8 = row.createCell(colNum++);
				cell8.setCellValue((String) leaveInfo.getComment());
			}
	
			rowNum = 4 + listL.size() + 1;			
			Row rowM = sheet.createRow(rowNum);
			Cell cellM = rowM.createCell(0);
			cellM.setCellStyle(cellStyle1);
			cellM.setCellValue("Dữ liệu từ máy chấm công");			
			
			// Generate column name
			rowNum = 4 + listL.size() + 2;
			rowM = sheet.createRow(rowNum);
			Cell cell12 = rowM.createCell(0);
			cell12.setCellValue("Mã NV");
			Cell cell22 = rowM.createCell(1);
			cell22.setCellValue("Họ tên");
			Cell cell32 = rowM.createCell(2);
			cell32.setCellValue("Bộ phận");
			Cell cell42 = rowM.createCell(3);
			cell42.setCellValue("Chức vụ");
			Cell cell52 = rowM.createCell(4);
			cell52.setCellValue("Ngày");
			Cell cell62 = rowM.createCell(5);
			cell62.setCellValue("Giờ vào");
			Cell cell72 = rowM.createCell(6);
			cell72.setCellValue("Giờ ra");
			Cell cell82 = rowM.createCell(7);
			cell82.setCellValue("Tổng thời gian");		
			Cell cell92 = rowM.createCell(8);
			cell92.setCellValue("Đi muộn sáng");		
			Cell cell102 = rowM.createCell(9);
			cell102.setCellValue("Đi muộn chiều");		
			Cell cell112 = rowM.createCell(10);
			cell112.setCellValue("Về sớm sáng");		
			Cell cell122 = rowM.createCell(11);
			cell122.setCellValue("Về sớm chiều");		
			Cell cell132 = rowM.createCell(12);
			cell132.setCellValue("Ghi chú");
			
			// generate values
			rowNum = 4 + listL.size() + 3;
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow(rowNum++);
				int colNum = 0;
				Timekeeping timekeeping = new Timekeeping();
				timekeeping = list.get(i);
				Cell cell1 = row.createCell(colNum++);
				cell1.setCellValue((Integer) timekeeping.getEmployeeId());
				Cell cell2 = row.createCell(colNum++);
				cell2.setCellValue((String) timekeeping.getEmployeeName());
				Cell cell3 = row.createCell(colNum++);
				cell3.setCellValue((String) timekeeping.getDepartment());
				Cell cell4 = row.createCell(colNum++);
				cell4.setCellValue((String) timekeeping.getTitle());
				Cell cell5 = row.createCell(colNum++);
				cell5.setCellValue(timekeeping.getDate().toString());
				Cell cell6 = row.createCell(colNum++);
				cell6.setCellValue((String) timekeeping.getTimeIn());
				Cell cell7 = row.createCell(colNum++);
				cell7.setCellValue((String) timekeeping.getTimeOut());
				Cell cell8 = row.createCell(colNum++);
				cell8.setCellValue((String) timekeeping.getWorkedTime());
				Cell cell9 = row.createCell(colNum++);
				if(timekeeping.getComeLateM() != null)
					cell9.setCellValue(timekeeping.getComeLateM() + " phút");
				else
					cell9.setCellValue(timekeeping.getComeLateM());
				Cell cell10 = row.createCell(colNum++);
				if(timekeeping.getComeLateA() != null)
					cell10.setCellValue(timekeeping.getComeLateA() + " phút");
				else
					cell10.setCellValue(timekeeping.getComeLateA());
				Cell cell01 = row.createCell(colNum++);
				if(timekeeping.getLeaveSoonM() != null)
					cell01.setCellValue(timekeeping.getLeaveSoonM() + " phút");
				else
					cell01.setCellValue(timekeeping.getLeaveSoonM());
				Cell cell02 = row.createCell(colNum++);
				if(timekeeping.getLeaveSoonA() != null)
				 cell02.setCellValue(timekeeping.getLeaveSoonA() + " phút");
				else
					cell02.setCellValue(timekeeping.getLeaveSoonA());
				Cell cell03 = row.createCell(colNum++);
				cell03.setCellValue((String) timekeeping.getComment());
			}
			
			try {
				if (!dir.exists()) {
					dir.mkdirs();
				}
				FileOutputStream outputStream = new FileOutputStream(
						dir + "/" + "/Dữ liệu chấm công từ ngày "+ fromDate + " đến ngày " + toDate + "_" + currentDate + ".xlsx");
				workbook.write(outputStream);
				workbook.close();
				model.addAttribute("message", "Dữ liệu chấm công được export thành công ra file "+ dir + ":Dữ liệu chấm công từ ngày "+ fromDate + " đến ngày " + toDate + "_" + currentDate + ".xlsx");
			} catch (FileNotFoundException e) {
				model.addAttribute("message", "Vui lòng tắt file "+ dir + ":Dữ liệu chấm công từ ngày "+ fromDate + " đến ngày " + toDate + "_" + currentDate + ".xlsx trước khi export");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			// get list department
			Map<String, String> departmentMap = this.dataForDepartments();
			model.addAttribute("departmentMap", departmentMap);
	
			// get list employee id
			Map<String, String> employeeMap = this.employees();
			model.addAttribute("employeeMap", employeeMap);
	
			model.addAttribute("timekeepings", list);
			model.addAttribute("leaveInfos", listL);
			Timekeeping timekeepingForm = new Timekeeping();
			model.addAttribute("timekeepingForm", timekeepingForm);
			model.addAttribute("leaveInfoForm", leaveInfoForm);
			
			if (fromDate != null && toDate != null)
				model.addAttribute("formTitle", "Dữ liệu chấm công từ ngày " + fromDate + " đến ngày " + toDate);
			else
				model.addAttribute("formTitle", "Dữ liệu chấm công ngày " + currentDate);
	
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
	return "formTimekeeping";

	}	
	
	@RequestMapping(value = { "/timekeeping/leaveInfo" }, method = RequestMethod.GET)
	public String listLeaveInfo(Model model, @ModelAttribute("leaveInfoForm") LeaveInfoForm leaveInfoForm) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();// your date
			String currentDate = dateFormat.format(date);
			// Calendar cal = Calendar.getInstance();
			// cal.setTime(date);
			List<LeaveInfo> list = null;
			String fromDate = leaveInfoForm.getDate();
			String toDate = leaveInfoForm.getToDate();
			String dept = leaveInfoForm.getDept();
			String eId = leaveInfoForm.geteId();

			if (fromDate != null && toDate != null) {
				list = leaveDAO.getLeaves(fromDate, toDate, dept, eId);
				if (list.size() == 0)
					model.addAttribute("message",
							"Không có dữ liệu chấm công phát sinh từ ngày " + fromDate + " đến ngày " + toDate);
				model.addAttribute("formTitle",
						"Dữ liệu chấm công phát sinh từ ngày " + fromDate + " đến ngày " + toDate);
			} else {
				list = leaveDAO.getLeaves(currentDate, null, null, null);
				if (list.size() == 0)
					model.addAttribute("message", "Không có dữ liệu chấm công phát sinh cho ngày " + currentDate);
				model.addAttribute("formTitle", "Dữ liệu chấm công phát sinh ngày " + currentDate);
			}

			// get list department
			Map<String, String> departmentMap = this.dataForDepartments();
			model.addAttribute("departmentMap", departmentMap);

			// get list employee id
			Map<String, String> employeeMap = this.employees();
			model.addAttribute("employeeMap", employeeMap);

			model.addAttribute("leaveInfos", list);

		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listLeaveInfo";
	}

	@RequestMapping(value = "/timekeeping/prepareGenerateLeaveReport", method = RequestMethod.GET)
	public String prepareGenerateLeaveReport(Model model, LeaveReport leaveReport) {
		try {
			model.addAttribute("leaveReportForm", leaveReport);

			// get list department
			Map<String, String> departmentMap = this.dataForDepartments();
			model.addAttribute("departmentMap", departmentMap);

			// get list employee id
			Map<String, String> employeeMap = this.employees();
			model.addAttribute("employeeMap", employeeMap);

			// get list leave type
			Map<String, String> leaveTypeMap = this.leaveTypesForReport();
			model.addAttribute("leaveTypeMap", leaveTypeMap);

			model.addAttribute("formTitle", "Tùy chọn dữ liệu cần cho báo cáo");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "prepareLeaveReport";
	}

	@RequestMapping(value = "/timekeeping/generateLeaveReport", method = RequestMethod.POST)
	public String generateLeaveReport(Model model,
			@ModelAttribute("generateLeaveReport") @Validated LeaveReport leaveReport,
			final RedirectAttributes redirectAttributes) {
		try {
			list = new ArrayList<>();
			LeaveReport leaveForGenReport;
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String currentDate = dateFormat.format(date);
			// System.err.println(currentDate);
			String year = leaveReport.getYearReport();
			String month = leaveReport.getMonthReport();
			int employeeId = leaveReport.getEmployeeId();
			//System.err.println(leaveReport.getLeaveTypeReport());// use only for display, get all types from DB
			if (leaveReport.getLeaveTypeReport() == null) {
				model.addAttribute("message", "Vui lòng chọn thông tin cần báo cáo!");
				redirectAttributes.addFlashAttribute("message", "Vui lòng chọn thông tin cần báo cáo!");
			} else {
				if (employeeId > 0) {
					log.info("Báo cáo cho nhân viên có mã nv: " + leaveReport.getEmployeeId() + ". Tháng "
							+ leaveReport.getMonthReport() + ", năm " + leaveReport.getYearReport());
					EmployeeInfo employee = employeeDAO.getEmployee(String.valueOf(leaveReport.getEmployeeId()));
					leaveForGenReport = new LeaveReport();
					Integer id = employee.getEmployeeId();
					leaveForGenReport.setEmployeeId(id);
					leaveForGenReport.setName(employee.getFullName());
					leaveForGenReport.setDepartment(employee.getDepartment());
					// tinh tham nien
					String joinDate = employee.getJoinDate();
					leaveForGenReport.setJoinDate(joinDate);
					int seniority = Utils.monthsBetween(dateFormat.parse(joinDate), dateFormat.parse(currentDate));
					// System.err.println("tham nien " + seniority);
					leaveForGenReport.setSeniority(String.valueOf(seniority));

					// tinh so ngay phep
					int quataLeave = 12;
					if (seniority > 36 && seniority < 72)
						quataLeave = 13;

					if (seniority >= 72 && seniority < 108)
						quataLeave = 14;

					if (seniority >= 108)
						quataLeave = 15;
					// System.err.println(quataLeave);
					leaveForGenReport.setQuataLeave(String.valueOf(quataLeave));

					// tinh toan de lay gia tri va gen thong tin leave info theo cac chi so dc nguoi
					// dung lua chon
					Map<String, String> leaveInfos = new LinkedHashMap<String, String>();
					leaveForReport = new LinkedHashMap<String, String>();
					StringTokenizer st = new StringTokenizer(leaveReport.getLeaveTypeReport(), ",");
					while (st.hasMoreTokens()) {
						String lT = st.nextToken();
						// String lTSum = "";
						String lTV = "";
						if (lT.equalsIgnoreCase("VS") || lT.equalsIgnoreCase("DM")) {
							LeaveReport comeLLeaveS;
							comeLLeaveS = timekeepingDAO.getTimekeepingReport(year, month, id, lT);
							// System.err.println("leave type: " + lT);
							if (lT.equalsIgnoreCase("VS")) {
								leaveForReport.put(lT, "SL Về sớm/tổng tg(p)");
								// leaveForReport.put("TGVS", "TG Về sớm");
								lTV = String.valueOf(comeLLeaveS.getLeaveSoon()) + "/"
										+ (comeLLeaveS.getLeaveSoonAValue() + comeLLeaveS.getLeaveSoonMValue());
							} else {
								leaveForReport.put(lT, "SL Đi muộn/tổng tg(p)");
								// leaveForReport.put("TGDM", "TG Đi muộn");
								lTV = String.valueOf(comeLLeaveS.getComeLate()) + "/"
										+ (comeLLeaveS.getLateAValue() + comeLLeaveS.getLateMValue());
							}
						} else if (lT.equalsIgnoreCase("TNC")) {
							if (month == null || month.length() == 0) {
								model.addAttribute("message", "Tính ngày công bắt buộc phải chọn tháng cụ thể!");
								return "leaveReport";
							}
							// co can yeu cau phai chon thang cu the ko hay cho pheo tinh ca nam
							// model.addAttribute("message", "Với phần tính ngày công yêu cầu chọn tháng cụ
							// the!");
							// Tính ngày công cho nhân viên của phòng
							leaveForReport.put(lT, "Ngày công");
							// ngay cong thuc te
							if (month.length() < 2)
								lTV = (float) leaveDAO.getLeaveReport(year, month, id, "NP','CT','HT") / 8
										+ (float) timekeepingDAO.getWorkedTime(year, month, id) / 2 + "/"
										+ workingDayDAO.getWorkingDay(year + "-0" + month, "IDI").getWorkDayOfMonth();
							else
								lTV = (float) leaveDAO.getLeaveReport(year, month, id, "NP','CT','HT") / 8
										+ (float) timekeepingDAO.getWorkedTime(year, month, id) / 2 + "/"
										+ workingDayDAO.getWorkingDay(year + "-" + month, "IDI").getWorkDayOfMonth();
							// số ngày nghỉ phép, đi công tác của tháng
							// System.err.println(Float.toString((float)leaveDAO.getLeaveReport(year, month,
							// id, "NP','CT','HT")/8));
							//log.info("Số ngày nghỉ phép, công tác và học tập trong tháng: "
							//		+ leaveDAO.getLeaveReport(year, month, id, "NP','CT','HT"));
							// gio cong thuc te
						} else {
							if (lT.startsWith("LT") || lT.startsWith("KCC"))	{//|| leaveDAO.getLeaveReport(year, month, id, lT) == 0) {
								lTV = String.valueOf(leaveDAO.getLeaveReport(year, month, id, lT));
							}
							else if(lT.startsWith("CVBN")) {
								ValueReport valueReport = null;	
								valueReport = leaveDAO.getWorkCount(year, month, employeeId, lT);
								lTV = valueReport.getCount() + "/" + valueReport.getValue();
							}							
							else {
								if(lT.startsWith("NKP")) {
									//System.err.println("Nghi khong phep");
									int cameLateUnaccepted = timekeepingDAO.countComleLateOver(year, month, employeeId);
									//System.err.println("so lan di muon qua 60': " + cameLateUnaccepted);
									//System.err.println("so lan di muon qua 60/2': " + (float)cameLateUnaccepted/2);
									lTV = Float.toString((float) leaveDAO.getLeaveReport(year, month, id, lT) / 8 + (float)cameLateUnaccepted/2);									
								}else {
									if (leaveDAO.getLeaveReport(year, month, id, lT) > 0)
										lTV = Float.toString((float) leaveDAO.getLeaveReport(year, month, id, lT) / 8);
									else
										lTV = String.valueOf(leaveDAO.getLeaveReport(year, month, id, lT));
								}
							}

							// Get name to gen report (<th>)
							// if(!lT.endsWith("2")) {
							// System.err.println("leave type: " + lT);
							String leaveTypeName = leaveDAO.getLeaveType(lT);
							leaveForReport.put(lT, leaveTypeName);
							// }else if(lT.endsWith("2")) {
							// lTSum = lT.substring(0, lT.length() - 1 );
							// }

							/*
							 * if(lTSum.equalsIgnoreCase(lT)) {
							 * 
							 * }
							 */
						}
						leaveInfos.put(lT, lTV);
					}
					model.addAttribute("leaveForReport", leaveForReport);

					// Tinh toan so ngay phep da su dung va so ngay phep con lai
					double leaveUsed = getLeaveUsed(year, id);
					leaveForGenReport.setLeaveUsed(String.valueOf(leaveUsed));

					// Tinh so ngay nghi con lai cua nam truoc
					/*
					 * if(Integer.valueOf(year) - 1 <= 2016) leaveForGenReport.setRestQuata("0");
					 * else { int lastYear = Integer.valueOf(year) -1; int seniorityLastYear = 0;
					 * if(joinDate != null && joinDate.length() > 0) seniorityLastYear =
					 * Utils.monthsBetween(dateFormat.parse(joinDate), dateFormat.parse(lastYear +
					 * "-12-31")); System.err.println("tham nien tinh den het nam truoc: " +
					 * seniorityLastYear);
					 * //leaveForGenReport.setSeniority(String.valueOf(seniorityLastYear));
					 * 
					 * // tinh so ngay phep int quataLeaveLastYear = 12; if (seniorityLastYear > 36
					 * && seniorityLastYear < 72) quataLeaveLastYear = 13;
					 * 
					 * if (seniorityLastYear >= 72 && seniorityLastYear < 108) quataLeaveLastYear =
					 * 14;
					 * 
					 * if (seniorityLastYear >= 108) quataLeaveLastYear = 15;
					 * 
					 * double leaveUsedLastYear = getLeaveUsed(String.valueOf(lastYear), id);
					 * leaveForGenReport.setRestQuata(String.valueOf(quataLeaveLastYear -
					 * leaveUsedLastYear)); } }
					 */
					leaveForGenReport.setLeaveRemain(String.valueOf(quataLeave - leaveUsed));
					//

					leaveForGenReport.setLeaveTypes(leaveInfos);
					model.addAttribute("leaveInfos", leaveInfos);
					System.out.println("leave info size: " + leaveInfos.size());

					list.add(leaveForGenReport);
				} else if (!leaveReport.getDepartment().equalsIgnoreCase("all") && employeeId == 0) {
					log.info("Báo cáo cho phòng: " + leaveReport.getDepartment() + ". Tháng "
							+ leaveReport.getMonthReport() + ", năm " + leaveReport.getYearReport());
					List<EmployeeInfo> listE = employeeDAO.getEmployeesByDepartment(leaveReport.getDepartment());

					for (int i = 0; i < listE.size(); i++) {
						EmployeeInfo employee = new EmployeeInfo();
						leaveForGenReport = new LeaveReport();
						employee = (EmployeeInfo) listE.get(i);
						Integer id = employee.getEmployeeId();
						leaveForGenReport.setEmployeeId(id);
						leaveForGenReport.setName(employee.getFullName());
						leaveForGenReport.setDepartment(employee.getDepartment());
						// tinh tham nien
						String joinDate = employee.getJoinDate();
						leaveForGenReport.setJoinDate(joinDate);
						int seniority = Utils.monthsBetween(dateFormat.parse(joinDate), dateFormat.parse(currentDate));
						//System.err.println("tham nien " + seniority);
						leaveForGenReport.setSeniority(String.valueOf(seniority));

						// tinh so ngay phep
						int quataLeave = 12;
						if (seniority > 36 && seniority < 72)
							quataLeave = 13;

						if (seniority >= 72 && seniority < 108)
							quataLeave = 14;

						if (seniority >= 108)
							quataLeave = 15;
						//System.err.println(quataLeave);
						leaveForGenReport.setQuataLeave(String.valueOf(quataLeave));

						// tinh toan de lay gia tri va gen thong tin leave info theo cac chi so dc nguoi
						// dung lua chon
						Map<String, String> leaveInfos = new LinkedHashMap<String, String>();
						leaveForReport = new LinkedHashMap<String, String>();
						StringTokenizer st = new StringTokenizer(leaveReport.getLeaveTypeReport(), ",");
						while (st.hasMoreTokens()) {
							String lT = st.nextToken();
							// String lTSum = "";
							String lTV = "";
							if (lT.equalsIgnoreCase("VS") || lT.equalsIgnoreCase("DM")) {
								LeaveReport comeLLeaveS;
								comeLLeaveS = timekeepingDAO.getTimekeepingReport(year, month, id, lT);
								// System.err.println("leave type: " + lT);
								if (lT.equalsIgnoreCase("VS")) {
									leaveForReport.put(lT, "SL Về sớm/tổng tg(p)");
									// leaveForReport.put("TGVS", "TG Về sớm");
									lTV = String.valueOf(comeLLeaveS.getLeaveSoon()) + "/"
											+ (comeLLeaveS.getLeaveSoonAValue() + comeLLeaveS.getLeaveSoonMValue());
								} else {
									leaveForReport.put(lT, "SL Đi muộn/tổng tg(p)");
									// leaveForReport.put("TGDM", "TG Đi muộn");
									lTV = String.valueOf(comeLLeaveS.getComeLate()) + "/"
											+ (comeLLeaveS.getLateAValue() + comeLLeaveS.getLateMValue());
								}
							} else if (lT.equalsIgnoreCase("TNC")) {
								if (month == null || month.length() == 0) {
									model.addAttribute("message", "Tính ngày công bắt buộc phải chọn tháng cụ thể!");
									return "leaveReport";
								}
								// co can yeu cau phai chon thang cu the ko hay cho pheo tinh ca nam
								// model.addAttribute("message", "Với phần tính ngày công yêu cầu chọn tháng cụ
								// the!");
								// Tính ngày công cho nhân viên của phòng
								leaveForReport.put(lT, "Ngày công");
								// ngay cong thuc te
								if (month.length() < 2)
									lTV = (float) leaveDAO.getLeaveReport(year, month, id, "NP','CT','HT") / 8
											+ (float) timekeepingDAO.getWorkedTime(year, month, id) / 2 + "/"
											+ workingDayDAO.getWorkingDay(year + "-0" + month, "IDI")
													.getWorkDayOfMonth();
								else
									lTV = (float) leaveDAO.getLeaveReport(year, month, id, "NP','CT','HT") / 8
											+ (float) timekeepingDAO.getWorkedTime(year, month, id) / 2 + "/"
											+ workingDayDAO.getWorkingDay(year + "-" + month, "IDI")
													.getWorkDayOfMonth();
								// số ngày nghỉ phép, đi công tác của tháng
								// System.err.println(Float.toString((float)leaveDAO.getLeaveReport(year, month,
								// id, "NP','CT','HT")/8));
								//log.info("Số ngày nghỉ phép, công tác và học tập trong tháng: "
								//		+ leaveDAO.getLeaveReport(year, month, id, "NP','CT','HT"));
								// gio cong thuc te
							} else {
								if (lT.startsWith("LT") || lT.startsWith("KCC")) {//|| leaveDAO.getLeaveReport(year, month, id, lT) == 0) {
									lTV = String.valueOf(leaveDAO.getLeaveReport(year, month, id, lT));
								}							
								else if(lT.startsWith("CVBN")) {
									ValueReport valueReport = null;	
									valueReport = leaveDAO.getWorkCount(year, month, id, lT);
									lTV = valueReport.getCount() + "/" + valueReport.getValue();
								}
								else {
									if(lT.startsWith("NKP")) {
									//	System.err.println("Nghi khong phep");
										int cameLateUnaccepted = timekeepingDAO.countComleLateOver(year, month, id);
									//	System.err.println("so lan di muon qua 60': " + cameLateUnaccepted);
									//	System.err.println("so lan di muon qua 60/2': " + (float)cameLateUnaccepted/2);
										lTV = Float.toString((float) leaveDAO.getLeaveReport(year, month, id, lT) / 8 + (float)cameLateUnaccepted/2);									
									}else {
										if (leaveDAO.getLeaveReport(year, month, id, lT) > 0)
											lTV = Float.toString((float) leaveDAO.getLeaveReport(year, month, id, lT) / 8);
										else
											lTV = String.valueOf(leaveDAO.getLeaveReport(year, month, id, lT));
									}
								}
								// Get name to gen report (<th>)
								// if(!lT.endsWith("2")) {
								// System.err.println("leave type: " + lT);
								String leaveTypeName = leaveDAO.getLeaveType(lT);
								leaveForReport.put(lT, leaveTypeName);
								// }else if(lT.endsWith("2")) {
								// lTSum = lT.substring(0, lT.length() - 1 );
								// }

								// if(lTSum.equalsIgnoreCase(lT)) {

								// }
							}
							leaveInfos.put(lT, lTV);
						}
						model.addAttribute("leaveForReport", leaveForReport);

						// Tinh toan so ngay phep da su dung va so ngay phep con lai
						double leaveUsed = getLeaveUsed(year, id);
						leaveForGenReport.setLeaveUsed(String.valueOf(leaveUsed));
						// leaveForGenReport.setLeaveRemain(String.valueOf(quataLeave - leaveUsed));

						/*
						 * //Tinh so ngay nghi con lai cua nam truoc if(Integer.valueOf(year) - 1 <=
						 * 2016) leaveForGenReport.setRestQuata("0"); else { int lastYear =
						 * Integer.valueOf(year) -1; int seniorityLastYear = 0; if(joinDate != null &&
						 * joinDate.length() > 0) seniorityLastYear =
						 * Utils.monthsBetween(dateFormat.parse(joinDate), dateFormat.parse(lastYear +
						 * "-12-31")); System.err.println("tham nien tinh den het nam truoc: " +
						 * seniorityLastYear);
						 * //leaveForGenReport.setSeniority(String.valueOf(seniorityLastYear));
						 * 
						 * // tinh so ngay phep int quataLeaveLastYear = 12; if (seniorityLastYear > 36
						 * && seniorityLastYear < 72) quataLeaveLastYear = 13;
						 * 
						 * if (seniorityLastYear >= 72 && seniorityLastYear < 108) quataLeaveLastYear =
						 * 14;
						 * 
						 * if (seniorityLastYear >= 108) quataLeaveLastYear = 15;
						 * 
						 * double leaveUsedLastYear = getLeaveUsed(String.valueOf(lastYear), id);
						 * leaveForGenReport.setRestQuata(String.valueOf(quataLeaveLastYear -
						 * leaveUsedLastYear)); }
						 */
						leaveForGenReport.setLeaveRemain(String.valueOf(quataLeave - leaveUsed));
						//

						model.addAttribute("leaveInfos", leaveInfos);
						leaveForGenReport.setLeaveTypes(leaveInfos);
						//System.out.println("leave info size: " + leaveInfos.size());

						list.add(leaveForGenReport);
					}
				} else {
					log.info("Báo cáo cho toàn bộ nhân viên. Tháng " + leaveReport.getMonthReport() + ", năm "
							+ leaveReport.getYearReport());
					List<EmployeeInfo> listE = employeeDAO.getEmployees();

					for (int i = 0; i < listE.size(); i++) {
						EmployeeInfo employee = new EmployeeInfo();
						leaveForGenReport = new LeaveReport();
						employee = (EmployeeInfo) listE.get(i);
						Integer id = employee.getEmployeeId();
						leaveForGenReport.setEmployeeId(id);
						leaveForGenReport.setName(employee.getFullName());
						leaveForGenReport.setDepartment(employee.getDepartment());
						// tinh tham nien
						String joinDate = employee.getJoinDate();
						leaveForGenReport.setJoinDate(joinDate);
						int seniority = 0;
						if (joinDate != null && joinDate.length() > 0)
							seniority = Utils.monthsBetween(dateFormat.parse(joinDate), dateFormat.parse(currentDate));
						// System.err.println("tham nien " + seniority);
						leaveForGenReport.setSeniority(String.valueOf(seniority));

						// tinh so ngay phep
						int quataLeave = 12;
						if (seniority > 36 && seniority < 72)
							quataLeave = 13;

						if (seniority >= 72 && seniority < 108)
							quataLeave = 14;

						if (seniority >= 108)
							quataLeave = 15;
						// System.err.println(quataLeave);
						leaveForGenReport.setQuataLeave(String.valueOf(quataLeave));

						// tinh toan de lay gia tri va gen thong tin leave info theo cac chi so dc nguoi
						// dung lua chon
						Map<String, String> leaveInfos = new LinkedHashMap<String, String>();
						leaveForReport = new LinkedHashMap<String, String>();
						StringTokenizer st = new StringTokenizer(leaveReport.getLeaveTypeReport(), ",");
						while (st.hasMoreTokens()) {
							String lT = st.nextToken();
							// String lTSum = "";
							String lTV = "";
							if (lT.equalsIgnoreCase("VS") || lT.equalsIgnoreCase("DM")) {
								LeaveReport comeLLeaveS;
								comeLLeaveS = timekeepingDAO.getTimekeepingReport(year, month, id, lT);
								// System.err.println("leave type: " + lT);
								if (lT.equalsIgnoreCase("VS")) {
									leaveForReport.put(lT, "SL Về sớm/tổng tg(p)");
									// leaveForReport.put("TGVS", "TG Về sớm");
									//System.err.println("ve som: " + comeLLeaveS.getLeaveSoonAValue() + "|"
									//		+ comeLLeaveS.getLeaveSoonMValue());
									lTV = String.valueOf(comeLLeaveS.getLeaveSoon()) + "/"
											+ (comeLLeaveS.getLeaveSoonAValue() + comeLLeaveS.getLeaveSoonMValue());
								} else {
									leaveForReport.put(lT, "SL Đi muộn/tổng tg(p)");
									// leaveForReport.put("TGDM", "TG Đi muộn");
									//System.err.println("di muon: " + comeLLeaveS.getLateAValue() + "|"
									//		+ comeLLeaveS.getLateMValue());
									lTV = String.valueOf(comeLLeaveS.getComeLate()) + "/"
											+ (comeLLeaveS.getLateAValue() + comeLLeaveS.getLateMValue());
								}
							} else if (lT.equalsIgnoreCase("TNC")) {
								if (month == null || month.length() == 0) {
									model.addAttribute("message", "Tính ngày công bắt buộc phải chọn tháng cụ thể!");
									return "leaveReport";
								}
								// co can yeu cau phai chon thang cu the ko hay cho pheo tinh ca nam
								// model.addAttribute("message", "Với phần tính ngày công yêu cầu chọn tháng cụ
								// the!");
								// Tính ngày công cho nhân viên của phòng
								leaveForReport.put(lT, "Ngày công");
								// ngay cong thuc te
								if (month.length() < 2)
									lTV = (float) leaveDAO.getLeaveReport(year, month, id, "NP','CT','HT") / 8
											+ (float) timekeepingDAO.getWorkedTime(year, month, id) / 2 + "/"
											+ workingDayDAO.getWorkingDay(year + "-0" + month, "IDI")
													.getWorkDayOfMonth();
								else
									lTV = (float) leaveDAO.getLeaveReport(year, month, id, "NP','CT','HT") / 8
											+ (float) timekeepingDAO.getWorkedTime(year, month, id) / 2 + "/"
											+ workingDayDAO.getWorkingDay(year + "-" + month, "IDI")
													.getWorkDayOfMonth();
								// số ngày nghỉ phép, đi công tác của tháng
								// System.err.println(Float.toString((float)leaveDAO.getLeaveReport(year, month,
								// id, "NP','CT','HT")/8));
								//log.info("Số ngày nghỉ phép, công tác và học tập trong tháng: "
								//		+ leaveDAO.getLeaveReport(year, month, id, "NP','CT','HT"));
								// gio cong thuc te
							} else {
								if (lT.startsWith("LT") || lT.startsWith("KCC"))	{//|| leaveDAO.getLeaveReport(year, month, id, lT) == 0) {
									lTV = String.valueOf(leaveDAO.getLeaveReport(year, month, id, lT));
								}
								else if(lT.startsWith("CVBN")) {
									ValueReport valueReport = null;	
									valueReport = leaveDAO.getWorkCount(year, month, id, lT);
									lTV = valueReport.getCount() + "/" + valueReport.getValue();								
								} else {
									if(lT.startsWith("NKP")) {
										int cameLateUnaccepted = timekeepingDAO.countComleLateOver(year, month, id);
										//System.err.println("so lan di muon qua 60': " + cameLateUnaccepted);
										//System.err.println("so lan di muon qua 60/2': " + (float)cameLateUnaccepted/2);
										lTV = Float.toString((float) leaveDAO.getLeaveReport(year, month, id, lT) / 8 + (float)cameLateUnaccepted/2);									
									}else {
										if (leaveDAO.getLeaveReport(year, month, id, lT) > 0)
											lTV = Float.toString((float) leaveDAO.getLeaveReport(year, month, id, lT) / 8);
										else
											lTV = String.valueOf(leaveDAO.getLeaveReport(year, month, id, lT));
									}
								}
								// System.err.println(lT + ":" + id + " value: " + lTV);

								// Get name to gen report (<th>)
								// if(!lT.endsWith("2")) {
								// System.err.println("leave type: " + lT);
								String leaveTypeName = leaveDAO.getLeaveType(lT);
								leaveForReport.put(lT, leaveTypeName);
								// }else if(lT.endsWith("2")) {
								// lTSum = lT.substring(0, lT.length() - 1 );
								// }
								// if(lTSum.equalsIgnoreCase(lT)) {
								// }
							}
							leaveInfos.put(lT, lTV);
						}
						model.addAttribute("leaveForReport", leaveForReport);
						// Tinh toan so ngay phep da su dung va so ngay phep con lai
						/*
						 * int leaveFUsed = Integer.valueOf(leaveDAO.getLeaveTakenF(year, id));
						 * System.out.println("F "+leaveFUsed); float leaveHUsed =
						 * Integer.valueOf(leaveDAO.getLeaveTakenH(year, id));
						 * System.out.println("H "+leaveHUsed); if(leaveHUsed > 0) { leaveHUsed =
						 * (float)leaveHUsed/2;
						 * 
						 * } System.out.println("HH "+leaveHUsed); double leaveUsed = (float)(leaveFUsed
						 * + leaveHUsed);
						 */

						double leaveUsed = getLeaveUsed(year, id);
						leaveForGenReport.setLeaveUsed(String.valueOf(leaveUsed));
						// leaveForGenReport.setLeaveRemain(String.valueOf(quataLeave - leaveUsed));

						/*
						 * //Tinh so ngay nghi con lai cua nam truoc if(Integer.valueOf(year) - 1 <=
						 * 2016) leaveForGenReport.setRestQuata("0"); else { int lastYear =
						 * Integer.valueOf(year) -1; int seniorityLastYear = 0; if(joinDate != null &&
						 * joinDate.length() > 0) seniorityLastYear =
						 * Utils.monthsBetween(dateFormat.parse(joinDate), dateFormat.parse(lastYear +
						 * "-12-31")); System.err.println("tham nien tinh den het nam truoc: " +
						 * seniorityLastYear);
						 * //leaveForGenReport.setSeniority(String.valueOf(seniorityLastYear));
						 * 
						 * // tinh so ngay phep int quataLeaveLastYear = 12; if (seniorityLastYear > 36
						 * && seniorityLastYear < 72) quataLeaveLastYear = 13;
						 * 
						 * if (seniorityLastYear >= 72 && seniorityLastYear < 108) quataLeaveLastYear =
						 * 14;
						 * 
						 * if (seniorityLastYear >= 108) quataLeaveLastYear = 15;
						 * 
						 * double leaveUsedLastYear = getLeaveUsed(String.valueOf(lastYear), id);
						 * leaveForGenReport.setRestQuata(String.valueOf(quataLeaveLastYear -
						 * leaveUsedLastYear)); }
						 */
						leaveForGenReport.setLeaveRemain(String.valueOf(quataLeave - leaveUsed));

						model.addAttribute("leaveInfos", leaveInfos);
						leaveForGenReport.setLeaveTypes(leaveInfos);
						//System.out.println("leave info size: " + leaveInfos.size());
						list.add(leaveForGenReport);
					}
				}
				model.addAttribute("month", month);
				model.addAttribute("year", year);
				model.addAttribute("generateLeaveReport", leaveReport);
				model.addAttribute("department", leaveReport.getDepartment());
			}
			model.addAttribute("leaveReports", list);
			model.addAttribute("formTitle", "Thống kê dữ liệu chuyên cần");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "leaveReport";
	}

	@RequestMapping(value = "/timekeeping/export.xls", method = RequestMethod.GET)
	public String getExcel(Model model, @RequestParam("department") String department,
			@RequestParam("month") String month, @RequestParam("year") String year) {
		// List animalList = animalService.getAnimalList();
		// TimekeepingListExcelView excel = new TimekeepingListExcelView();
		model.addAttribute("leaveReports", list);
		// excel.setExcelHeader(excelSheet);
		// excel.setExcelHeader(excelSheet);
		String path = properties.getProperty("REPORT_PATH");
		File dir = new File(path);
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("data");
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String currentDate = dateFormat.format(date);

		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 12);
		cellStyle.setFont(font);

		Row row1 = sheet.createRow(0);
		Cell cell = row1.createCell(3);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("Thống kê báo cáo cho ");
		if (department != null && !department.equalsIgnoreCase("all")) {
			cell = row1.createCell(4);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(" Phòng " + department);
		} else {
			cell = row1.createCell(4);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(" Tất cả các phòng ban ");
		}

		if (month != null && month.length() > 0) {
			cell = row1.createCell(5);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(" Tháng " + month);
			cell = row1.createCell(6);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(" Năm " + year);
		} else {
			cell = row1.createCell(5);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(" Tất cả các tháng ");
			cell = row1.createCell(6);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(" Năm " + year);
		}

		// gen column name
		int rowNum = 2;
		Row row = sheet.createRow(rowNum);
		Cell cell11 = row.createCell(0);
		cell11.setCellValue("Mã NV");
		Cell cell21 = row.createCell(1);
		cell21.setCellValue("Họ tên");
		Cell cell31 = row.createCell(2);
		cell31.setCellValue("Phòng");
		Cell cell41 = row.createCell(3);
		cell41.setCellValue("Tổng số ngày phép");
		Cell cell51 = row.createCell(4);
		cell51.setCellValue("Ngày phép đã dùng");
		Cell cell61 = row.createCell(5);
		cell61.setCellValue("Ngày phép còn lại");

		// Map<String, String> leaveTypes = list.get(0).getLeaveTypes();

		int column = 6;
		for (Map.Entry<String, String> entry : leaveForReport.entrySet()) {
			Cell cell71 = row.createCell(column++);
			cell71.setCellValue(entry.getValue());
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}

		// gen value
		rowNum = 3;
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(rowNum++);
			int colNum = 0;
			LeaveReport leaveReport = new LeaveReport();
			leaveReport = list.get(i);
			Cell cell1 = row.createCell(colNum++);
			cell1.setCellValue((Integer) leaveReport.getEmployeeId());
			Cell cell2 = row.createCell(colNum++);
			cell2.setCellValue((String) leaveReport.getName());
			Cell cell3 = row.createCell(colNum++);
			cell3.setCellValue((String) leaveReport.getDepartment());
			Cell cell4 = row.createCell(colNum++);
			cell4.setCellValue((String) leaveReport.getQuataLeave());
			Cell cell5 = row.createCell(colNum++);
			cell5.setCellValue((String) leaveReport.getLeaveUsed());
			Cell cell6 = row.createCell(colNum++);
			cell6.setCellValue((String) leaveReport.getLeaveRemain());
			Map<String, String> leaveTypesValue = list.get(i).getLeaveTypes();
			for (Map.Entry<String, String> entry : leaveTypesValue.entrySet()) {
				Cell cell7 = row.createCell(colNum++);
				cell7.setCellValue(entry.getValue());

				// System.out.println("Key = " + entry.getKey() + ", Value = " +
				// entry.getValue());
			}
		}

		try {
			if (!dir.exists()) {
				dir.mkdirs();
			}
			FileOutputStream outputStream = new FileOutputStream(
					dir + "/" + "/Thong ke du lieu chuyen can " + currentDate + ".xlsx");
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//System.out.println("Done");
		model.addAttribute("message", "Dữ liệu đã được export ra file "+ path + "Thong ke du lieu chuyen can " + currentDate + ".xlsx!");
		model.addAttribute("leaveForReport", leaveForReport);
		model.addAttribute("leaveReports", list);
		model.addAttribute("formTitle", "Xuất ra file excel thống kê dữ liệu chuyên cần");
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("department", department);
		return "leaveReport";

	}

	@RequestMapping(value = "/timekeeping/insertLeaveInfo", method = RequestMethod.POST)
	public String insertLeaveInfo(Model model, @ModelAttribute("leaveInfoForm") @Validated LeaveInfo leaveInfo,
			BindingResult result, final RedirectAttributes redirectAttributes) {
		try {

			// validation
			if (result.hasErrors()) {
				System.err.println("validate data is fail");
				return this.addLeaveInfo(model, leaveInfo);
			}
			
			List<Date> datesInRange = new ArrayList<>();
			if(leaveInfo.getToDate() != null) {
				String leaveType = leaveInfo.getLeaveType();
				if(leaveType.equalsIgnoreCase("HT") || leaveType.equalsIgnoreCase("NKL") ||
						leaveType.equalsIgnoreCase("NKP") || leaveType.equalsIgnoreCase("NP") ||
						leaveType.equalsIgnoreCase("O") || leaveType.equalsIgnoreCase("CT")) {				
				//	System.err.println("to date: " + leaveInfo.getToDate());
				//	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(leaveInfo.getDate());				
					Calendar endCalendar = Calendar.getInstance();
					endCalendar.setTime(leaveInfo.getToDate());
					endCalendar.add(Calendar.DAY_OF_YEAR, 1); //Add 1 day to endDate to make sure endDate is included into the final list
					while (calendar.before(endCalendar)) {
						Date resultDate = calendar.getTime();
					//	System.err.println("date each:" + resultDate);
						datesInRange.add(resultDate);
						calendar.add(Calendar.DATE, 1);
					//	System.err.println("date each:" + resultDate);
					}
				}
			}

			leaveDAO.insertLeaveInfo(leaveInfo, datesInRange);

/*			// add thong tin xin phep di muon ve som vao table timekeeping
			PropertiesManager hr = new PropertiesManager("hr.properties");
			String leaveType = leaveInfo.getLeaveType();
			Timekeeping timekeeping = new Timekeeping();
			timekeeping.setEmployeeId(leaveInfo.getEmployeeId());
			timekeeping.setEmployeeName(leaveInfo.getEmployeeName());
			timekeeping.setDepartment(leaveInfo.getDepartment());
			timekeeping.setTitle(leaveInfo.getTitle());
			timekeeping.setDate(leaveInfo.getDate());
			timekeeping.setComment(leaveInfo.getComment());
			if (leaveType.equalsIgnoreCase("DMS")) {
				timekeeping.setComment(hr.getProperty("LATE_M_REASON"));
				//timekeeping.setTimeIn(hr.getProperty("TIME_CHECK_IN_MORNING").toString());
			} else if (leaveType.equalsIgnoreCase("DMC")) {
				timekeeping.setComment(hr.getProperty("LATE_A_REASON"));
				//timekeeping.setTimeIn(hr.getProperty("TIME_CHECK_IN_AFTERNOON").toString());
			} else if (leaveType.equalsIgnoreCase("VSS")) {
				//timekeeping.setTimeOut(hr.getProperty("TIME_CHECK_OUT_MORNING").toString());
				timekeeping.setComment(hr.getProperty("SOON_M_REASON"));
			} else if (leaveType.equalsIgnoreCase("VSC")) {
				//timekeeping.setTimeOut(hr.getProperty("TIME_CHECK_OUT_AFTERNOON").toString());
				timekeeping.setComment(hr.getProperty("SOON_A_REASON"));
			}

			timekeepingDAO.update(timekeeping);*/

			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Thêm thông tin ngày nghỉ thành công!");

		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/timekeeping/leaveInfo";
	}

	@RequestMapping(value = "/timekeeping/addLeaveInfo")
	public String addLeaveInfo(Model model, LeaveInfo leaveInfo) {
		model.addAttribute("formTitle", "Thêm dữ liệu chấm công");
		// get list employee id
		Map<String, String> employeeMap = this.employees();
		model.addAttribute("employeeMap", employeeMap);

		// get list leave type
		Map<String, String> leaveTypeMap = this.leaveTypes();
		model.addAttribute("leaveTypeMap", leaveTypeMap);

		model.addAttribute("leaveInfoForm", leaveInfo);

		return "addLeaveInfo";
	}

	@RequestMapping(value = "/timekeeping/deleteLeaveInfo")
	public String deleteLeaveInfo(Model model, @RequestParam("employeeId") int employeeId,
			@RequestParam("date") String date, @RequestParam("leaveType") String leaveType,
			final RedirectAttributes redirectAttributes) {
		try {
			System.err.println("delete leave info ");
			leaveDAO.deleteLeaveInfo(employeeId, date, leaveType);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Xóa thông tin phát sinh thành công!");
		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/timekeeping/leaveInfo";
	}

	private Map<String, String> employees() {
		Map<String, String> employeeMap = new LinkedHashMap<String, String>();
		try {
			List<EmployeeInfo> list = employeeDAO.getEmployees();
			EmployeeInfo employee = new EmployeeInfo();
			for (int i = 0; i < list.size(); i++) {
				employee = (EmployeeInfo) list.get(i);
				Integer id = employee.getEmployeeId();
				employeeMap.put(id.toString(),
						"Mã NV " + id + ", " + employee.getFullName() + ", phòng " + employee.getDepartment());
			}

		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return employeeMap;
	}

	private Map<String, String> leaveTypes() {
		Map<String, String> leaveTypeMap = new LinkedHashMap<String, String>();
		try {
			List<LeaveType> list = leaveDAO.getLeaveTypes();
			LeaveType leaveType = new LeaveType();
			for (int i = 0; i < list.size(); i++) {
				leaveType = (LeaveType) list.get(i);
				String leaveId = leaveType.getLeaveId();
				leaveTypeMap.put(leaveId, leaveType.getLeaveName());
			}

		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return leaveTypeMap;
	}

	private Map<String, String> leaveTypesForReport() {
		Map<String, String> leaveTypeMap = new LinkedHashMap<String, String>();
		try {
			List<LeaveType> list = leaveDAO.getLeaveTypesForReport();
			LeaveType leaveType = new LeaveType();
			for (int i = 0; i < list.size(); i++) {
				leaveType = (LeaveType) list.get(i);
				String leaveId = leaveType.getLeaveId();
				if (!leaveId.endsWith("2"))
					leaveTypeMap.put(leaveId, leaveType.getLeaveName());
			}

		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return leaveTypeMap;
	}

	private Map<String, String> dataForDepartments() {
		Map<String, String> departmentMap = new LinkedHashMap<String, String>();
		try {
			List<Department> list = departmentDAO.getDepartments();
			Department department = new Department();
			for (int i = 0; i < list.size(); i++) {
				department = (Department) list.get(i);
				departmentMap.put(department.getDepartmentId(), department.getDepartmentName());
			}

		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return departmentMap;
	}

	private float getLeaveUsed(String year, int id) {
		// Tinh toan so ngay phep da su dung va so ngay phep con lai
		float leaveUsed = 0;
		if (leaveDAO.getLeaveTaken(year, id) > 0)
			leaveUsed = leaveDAO.getLeaveTaken(year, id);
		// System.out.println("leave used " + leaveUsed);
		leaveUsed = leaveUsed / 8;
		return leaveUsed;
	}

	// For Ajax
	@RequestMapping("/timekeeping/selection")
	public @ResponseBody List<EmployeeInfo> employeesByDepartment(@RequestParam("dept") String department) {
		// System.err.println("ajax method");
		List<EmployeeInfo> list = null;
		if (!department.equalsIgnoreCase("all"))
			list = employeeDAO.getEmployeesByDepartment(department);
		else
			list = employeeDAO.getEmployees();

		return list;
	}
}
