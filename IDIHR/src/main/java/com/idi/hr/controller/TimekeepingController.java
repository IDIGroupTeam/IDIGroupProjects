package com.idi.hr.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.idi.hr.bean.Department;
import com.idi.hr.bean.EmployeeInfo;
import com.idi.hr.bean.LeaveInfo;
import com.idi.hr.bean.LeaveReport;
import com.idi.hr.bean.LeaveType;
import com.idi.hr.bean.Timekeeping;
import com.idi.hr.dao.DepartmentDAO;
import com.idi.hr.dao.EmployeeDAO;
import com.idi.hr.dao.LeaveDAO;
import com.idi.hr.dao.TimekeepingDAO;
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

	ExcelProcessor xp = new ExcelProcessor();

	@RequestMapping(value = "/timekeeping/updateData", method = RequestMethod.POST)
	public String updateData(Model model, @RequestParam("timeKeepingFile") MultipartFile timeKeepingFile) {
		System.err.println("import excel file");
		System.err.println(timeKeepingFile.getName());
		Timekeeping timekeeping = new Timekeeping();
		model.addAttribute("timekeepingForm", timekeeping);
		if (timeKeepingFile != null && timeKeepingFile.getSize() > 0) {
			log.info(timeKeepingFile.getOriginalFilename() + " - " + timeKeepingFile.getSize());
			try {
				// Read & insert time keeping data
				List<Timekeeping> timekeepings = xp.loadTimekeepingDataFromExcel(timeKeepingFile.getInputStream());
				timekeepingDAO.insertOrUpdate(timekeepings);

				List<Timekeeping> list = timekeepingDAO.getTimekeepings();
				model.addAttribute("timekeepings", list);
				model.addAttribute("formTitle", "Dữ liệu chấm công");

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
			List<Timekeeping> list = timekeepingDAO.getTimekeepings();
			model.addAttribute("timekeepings", list);
			model.addAttribute("formTitle", "Dữ liệu chấm công");
			return "formTimekeeping";
		}
	}

	@RequestMapping(value = { "/timekeeping/" }, method = RequestMethod.GET)
	public String listForTimekeeping(Model model, Timekeeping timekeeping) {
		try {
			// System.out.println("list by employee");
			List<Timekeeping> list = timekeepingDAO.getTimekeepings();
			model.addAttribute("timekeepings", list);
			model.addAttribute("timekeepingForm", timekeeping);
			model.addAttribute("formTitle", "Dữ liệu chấm công");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "formTimekeeping";
	}

	@RequestMapping(value = { "/timekeeping/leaveInfo" }, method = RequestMethod.GET)
	public String listLeaveInfo(Model model) {
		try {
			List<LeaveInfo> list = leaveDAO.getLeaves();
			model.addAttribute("leaveInfos", list);
			model.addAttribute("formTitle", "Dữ liệu ngày nghỉ");
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
			ArrayList<LeaveReport> list = new ArrayList<>();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String currentDate = dateFormat.format(date);
			//System.err.println(currentDate);
			String year = leaveReport.getYearReport();
			String month = leaveReport.getMonthReport();
			int employeeId = leaveReport.getEmployeeId();
			System.err.println(leaveReport.getLeaveTypeReport());// use only for display, get all types from DB

			if (employeeId > 0) {
				log.info("Báo cáo cho nhân viên có mã nv: " + leaveReport.getEmployeeId() + ". Tháng "
						+ leaveReport.getMonthReport() + ", năm " + leaveReport.getYearReport());
				EmployeeInfo employee = employeeDAO.getEmployee(String.valueOf(leaveReport.getEmployeeId()));
				LeaveReport leaveForGenReport = new LeaveReport();
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
				Map<String, String> leaveForReport = new LinkedHashMap<String, String>();
				StringTokenizer st = new StringTokenizer(leaveReport.getLeaveTypeReport(), ",");
				while (st.hasMoreTokens()) {
					String lT = st.nextToken();
					//String lTSum = "";
					String lTV = "";
					if (lT.equalsIgnoreCase("VS") || lT.equalsIgnoreCase("DM")) {
						lTV = timekeepingDAO.getTimekeepingReport(year, month, id, lT);
						//System.err.println("leave type: " + lT);
						if(lT.equalsIgnoreCase("VS")) 
							leaveForReport.put(lT, "Về sớm");
						else
							leaveForReport.put(lT, "Đi muộn");
						
					} else {
						if(lT.startsWith("LT") || lT.startsWith("KCC")|| leaveDAO.getLeaveReport(year, month, id, lT) == 0) {
							lTV = String.valueOf(leaveDAO.getLeaveReport(year, month, id, lT));
						}else {
							if(leaveDAO.getLeaveReport(year, month, id, lT) > 0) 
								lTV = Float.toString((float)leaveDAO.getLeaveReport(year, month, id, lT)/8);								
						}
						
						//Get name to gen report (<th>)
						//if(!lT.endsWith("2")) {
						//	System.err.println("leave type: " + lT);
							String leaveTypeName = leaveDAO.getLeaveType(lT);
							leaveForReport.put(lT, leaveTypeName);
						//}else if(lT.endsWith("2")) {
						//	lTSum = lT.substring(0, lT.length() - 1 );
						//}
						
						/*if(lTSum.equalsIgnoreCase(lT)) {
							
						}*/
						
					}						
					
					leaveInfos.put(lT, lTV);
				}
				model.addAttribute("leaveForReport", leaveForReport);
				
				//Tinh toan so ngay phep da su dung va so ngay phep con lai
				double leaveUsed = getLeaveUsed(year, id);
				leaveForGenReport.setLeaveUsed(String.valueOf(leaveUsed));
							
				//Tinh so ngay nghi con lai cua nam truoc 
/*				if(Integer.valueOf(year) - 1 <= 2016)
					leaveForGenReport.setRestQuata("0");
				else {
					int lastYear = Integer.valueOf(year) -1;
					int seniorityLastYear = 0;
					if(joinDate != null && joinDate.length() > 0)
						seniorityLastYear = Utils.monthsBetween(dateFormat.parse(joinDate), dateFormat.parse(lastYear + "-12-31"));
					System.err.println("tham nien tinh den het nam truoc: " + seniorityLastYear);
					//leaveForGenReport.setSeniority(String.valueOf(seniorityLastYear));

					// tinh so ngay phep
					int quataLeaveLastYear = 12;
					if (seniorityLastYear > 36 && seniorityLastYear < 72)
						quataLeaveLastYear = 13;

					if (seniorityLastYear >= 72 && seniorityLastYear < 108)
						quataLeaveLastYear = 14;

					if (seniorityLastYear >= 108)
						quataLeaveLastYear = 15;
					
					double leaveUsedLastYear = getLeaveUsed(String.valueOf(lastYear), id);
					leaveForGenReport.setRestQuata(String.valueOf(quataLeaveLastYear - leaveUsedLastYear));
				}
				}*/	
				leaveForGenReport.setLeaveRemain(String.valueOf(quataLeave - leaveUsed));
				//
					
				leaveForGenReport.setLeaveTypes(leaveInfos);
				model.addAttribute("leaveInfos", leaveInfos);
				System.out.println("leave info size: " + leaveInfos.size());

				list.add(leaveForGenReport);
			} else if (!leaveReport.getDepartment().equalsIgnoreCase("all") && employeeId == 0) {
				log.info("Báo cáo cho phòng: " + leaveReport.getDepartment() + ". Tháng " + leaveReport.getMonthReport()
						+ ", năm " + leaveReport.getYearReport());
				List<EmployeeInfo> listE = employeeDAO.getEmployeesByDepartment(leaveReport.getDepartment());

				for (int i = 0; i < listE.size(); i++) {
					EmployeeInfo employee = new EmployeeInfo();
					LeaveReport leaveForGenReport = new LeaveReport();
					employee = (EmployeeInfo) listE.get(i);
					Integer id = employee.getEmployeeId();
					leaveForGenReport.setEmployeeId(id);
					leaveForGenReport.setName(employee.getFullName());
					leaveForGenReport.setDepartment(employee.getDepartment());
					// tinh tham nien
					String joinDate = employee.getJoinDate();
					leaveForGenReport.setJoinDate(joinDate);
					int seniority = Utils.monthsBetween(dateFormat.parse(joinDate), dateFormat.parse(currentDate));
					System.err.println("tham nien " + seniority);
					leaveForGenReport.setSeniority(String.valueOf(seniority));

					// tinh so ngay phep
					int quataLeave = 12;
					if (seniority > 36 && seniority < 72)
						quataLeave = 13;

					if (seniority >= 72 && seniority < 108)
						quataLeave = 14;

					if (seniority >= 108)
						quataLeave = 15;
					System.err.println(quataLeave);
					leaveForGenReport.setQuataLeave(String.valueOf(quataLeave));

					// tinh toan de lay gia tri va gen thong tin leave info theo cac chi so dc nguoi
					// dung lua chon
					Map<String, String> leaveInfos = new LinkedHashMap<String, String>();
					Map<String, String> leaveForReport = new LinkedHashMap<String, String>();
					StringTokenizer st = new StringTokenizer(leaveReport.getLeaveTypeReport(), ",");
					while (st.hasMoreTokens()) {
						String lT = st.nextToken();
						//String lTSum = "";
						String lTV = "";
						if (lT.equalsIgnoreCase("VS") || lT.equalsIgnoreCase("DM")) {
							lTV = timekeepingDAO.getTimekeepingReport(year, month, id, lT);
							//System.err.println("leave type: " + lT);
							if(lT.equalsIgnoreCase("VS")) 
								leaveForReport.put(lT, "Về sớm");
							else
								leaveForReport.put(lT, "Đi muộn");
							
						} else {
							if(lT.startsWith("LT") || lT.startsWith("KCC")|| leaveDAO.getLeaveReport(year, month, id, lT) == 0) {
								lTV = String.valueOf(leaveDAO.getLeaveReport(year, month, id, lT));
							}else {
								if(leaveDAO.getLeaveReport(year, month, id, lT) > 0) 
									lTV = Float.toString((float)leaveDAO.getLeaveReport(year, month, id, lT)/8);								
							}
							
							//Get name to gen report (<th>)
							//if(!lT.endsWith("2")) {
							//	System.err.println("leave type: " + lT);
								String leaveTypeName = leaveDAO.getLeaveType(lT);
								leaveForReport.put(lT, leaveTypeName);
							//}else if(lT.endsWith("2")) {
							//	lTSum = lT.substring(0, lT.length() - 1 );
							//}
							
							//if(lTSum.equalsIgnoreCase(lT)) {
								
							//}
							
						}						
						
						leaveInfos.put(lT, lTV);
					}
					model.addAttribute("leaveForReport", leaveForReport);
					
					//Tinh toan so ngay phep da su dung va so ngay phep con lai
					double leaveUsed = getLeaveUsed(year, id);
					leaveForGenReport.setLeaveUsed(String.valueOf(leaveUsed));
					//leaveForGenReport.setLeaveRemain(String.valueOf(quataLeave - leaveUsed));
					
/*					//Tinh so ngay nghi con lai cua nam truoc 
					if(Integer.valueOf(year) - 1 <= 2016)
						leaveForGenReport.setRestQuata("0");
					else {
						int lastYear = Integer.valueOf(year) -1;
						int seniorityLastYear = 0;
						if(joinDate != null && joinDate.length() > 0)
							seniorityLastYear = Utils.monthsBetween(dateFormat.parse(joinDate), dateFormat.parse(lastYear + "-12-31"));
						System.err.println("tham nien tinh den het nam truoc: " + seniorityLastYear);
						//leaveForGenReport.setSeniority(String.valueOf(seniorityLastYear));

						// tinh so ngay phep
						int quataLeaveLastYear = 12;
						if (seniorityLastYear > 36 && seniorityLastYear < 72)
							quataLeaveLastYear = 13;

						if (seniorityLastYear >= 72 && seniorityLastYear < 108)
							quataLeaveLastYear = 14;

						if (seniorityLastYear >= 108)
							quataLeaveLastYear = 15;
						
						double leaveUsedLastYear = getLeaveUsed(String.valueOf(lastYear), id);
						leaveForGenReport.setRestQuata(String.valueOf(quataLeaveLastYear - leaveUsedLastYear));
					}*/	
					leaveForGenReport.setLeaveRemain(String.valueOf(quataLeave - leaveUsed));
					//
				
					model.addAttribute("leaveInfos", leaveInfos);
					leaveForGenReport.setLeaveTypes(leaveInfos);
					System.out.println("leave info size: " + leaveInfos.size());

					list.add(leaveForGenReport);
				}
			} else {
				log.info("Báo cáo cho toàn bộ nhân viên. Tháng " + leaveReport.getMonthReport() + ", năm "
						+ leaveReport.getYearReport());
				List<EmployeeInfo> listE = employeeDAO.getEmployees();

				for (int i = 0; i < listE.size(); i++) {
					EmployeeInfo employee = new EmployeeInfo();
					LeaveReport leaveForGenReport = new LeaveReport();
					employee = (EmployeeInfo) listE.get(i);
					Integer id = employee.getEmployeeId();
					leaveForGenReport.setEmployeeId(id);
					leaveForGenReport.setName(employee.getFullName());
					leaveForGenReport.setDepartment(employee.getDepartment());
					// tinh tham nien
					String joinDate = employee.getJoinDate();
					leaveForGenReport.setJoinDate(joinDate);
					int seniority = 0;
					if(joinDate != null && joinDate.length() > 0)
						seniority = Utils.monthsBetween(dateFormat.parse(joinDate), dateFormat.parse(currentDate));
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
					Map<String, String> leaveForReport = new LinkedHashMap<String, String>();
					StringTokenizer st = new StringTokenizer(leaveReport.getLeaveTypeReport(), ",");
					while (st.hasMoreTokens()) {
						String lT = st.nextToken();
						//String lTSum = "";
						String lTV = "";
						if (lT.equalsIgnoreCase("VS") || lT.equalsIgnoreCase("DM")) {
							lTV = timekeepingDAO.getTimekeepingReport(year, month, id, lT);
							//System.err.println("leave type: " + lT);
							if(lT.equalsIgnoreCase("VS")) 
								leaveForReport.put(lT, "Về sớm");
							else
								leaveForReport.put(lT, "Đi muộn");
							
						} else {
							if(lT.startsWith("LT") || lT.startsWith("KCC")|| leaveDAO.getLeaveReport(year, month, id, lT) == 0) {
								lTV = String.valueOf(leaveDAO.getLeaveReport(year, month, id, lT));
							}else {
								if(leaveDAO.getLeaveReport(year, month, id, lT) > 0) 
									lTV = Float.toString((float)leaveDAO.getLeaveReport(year, month, id, lT)/8);								
							}
							
							//System.err.println(lT + ":" + id + " value: " + lTV);
							
							//Get name to gen report (<th>)
							//if(!lT.endsWith("2")) {
							//	System.err.println("leave type: " + lT);
								String leaveTypeName = leaveDAO.getLeaveType(lT);
								leaveForReport.put(lT, leaveTypeName);
							//}else if(lT.endsWith("2")) {
							//	lTSum = lT.substring(0, lT.length() - 1 );
							//}
							
							//if(lTSum.equalsIgnoreCase(lT)) {
								
							//}
							
						}						
						
						leaveInfos.put(lT, lTV);
					}
					model.addAttribute("leaveForReport", leaveForReport);
					//Tinh toan so ngay phep da su dung va so ngay phep con lai
/*					int leaveFUsed = Integer.valueOf(leaveDAO.getLeaveTakenF(year, id));
					System.out.println("F "+leaveFUsed);
					float leaveHUsed =  Integer.valueOf(leaveDAO.getLeaveTakenH(year, id));
					System.out.println("H "+leaveHUsed);
					if(leaveHUsed > 0) {
						leaveHUsed = (float)leaveHUsed/2;
					
					}
					System.out.println("HH "+leaveHUsed);
					double leaveUsed = (float)(leaveFUsed + leaveHUsed);*/
					
					double leaveUsed = getLeaveUsed(year, id);
					leaveForGenReport.setLeaveUsed(String.valueOf(leaveUsed));
					//leaveForGenReport.setLeaveRemain(String.valueOf(quataLeave - leaveUsed));
					
/*					//Tinh so ngay nghi con lai cua nam truoc 
					if(Integer.valueOf(year) - 1 <= 2016)
						leaveForGenReport.setRestQuata("0");
					else {
						int lastYear = Integer.valueOf(year) -1;
						int seniorityLastYear = 0;
						if(joinDate != null && joinDate.length() > 0)
							seniorityLastYear = Utils.monthsBetween(dateFormat.parse(joinDate), dateFormat.parse(lastYear + "-12-31"));
						System.err.println("tham nien tinh den het nam truoc: " + seniorityLastYear);
						//leaveForGenReport.setSeniority(String.valueOf(seniorityLastYear));

						// tinh so ngay phep
						int quataLeaveLastYear = 12;
						if (seniorityLastYear > 36 && seniorityLastYear < 72)
							quataLeaveLastYear = 13;

						if (seniorityLastYear >= 72 && seniorityLastYear < 108)
							quataLeaveLastYear = 14;

						if (seniorityLastYear >= 108)
							quataLeaveLastYear = 15;
						
						double leaveUsedLastYear = getLeaveUsed(String.valueOf(lastYear), id);
						leaveForGenReport.setRestQuata(String.valueOf(quataLeaveLastYear - leaveUsedLastYear));
					}*/	
					leaveForGenReport.setLeaveRemain(String.valueOf(quataLeave - leaveUsed));
					//
				
					model.addAttribute("leaveInfos", leaveInfos);
					leaveForGenReport.setLeaveTypes(leaveInfos);
					System.out.println("leave info size: " + leaveInfos.size());
					list.add(leaveForGenReport);
				}
			}

			model.addAttribute("leaveReports", list);
			model.addAttribute("formTitle", "Thống kê dữ liệu chuyên cần");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "leaveReport";
	}

	@RequestMapping(value = "/timekeeping/insertLeaveInfo", method = RequestMethod.POST)
	public String insertLeaveInfo(Model model, @ModelAttribute("leaveInfoForm") @Validated LeaveInfo leaveInfo,
			final RedirectAttributes redirectAttributes) {
		try {
			leaveDAO.insertLeaveInfo(leaveInfo);
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
			@RequestParam("date") Date date, @RequestParam("leaveType") String leaveType,
			final RedirectAttributes redirectAttributes) {
		try {
			leaveDAO.deleteLeaveInfo(employeeId, date, leaveType);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Xóa thông tin ngày nghỉ thành công!");
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
						"Ma NV " + id + ", " + employee.getFullName() + ", phòng " + employee.getDepartment());
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
			List<LeaveType> list = leaveDAO.getLeaveTypes();
			LeaveType leaveType = new LeaveType();
			for (int i = 0; i < list.size(); i++) {
				leaveType = (LeaveType) list.get(i);
				String leaveId = leaveType.getLeaveId();
				if(!leaveId.endsWith("2"))
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
		//Tinh toan so ngay phep da su dung va so ngay phep con lai
		int leaveFUsed = Integer.valueOf(leaveDAO.getLeaveTakenF(year, id));
		System.out.println("F "+leaveFUsed);
		float leaveHUsed =  Integer.valueOf(leaveDAO.getLeaveTakenH(year, id));
		System.out.println("H "+leaveHUsed);
		if(leaveHUsed > 0) {
			leaveHUsed = (float)leaveHUsed/2;
		
		}
		System.out.println("HH "+leaveHUsed);
		float leaveUsed = (float)(leaveFUsed + leaveHUsed);
		
		return leaveUsed;
	}
}
