package com.idi.hr.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.idi.hr.bean.Department;
import com.idi.hr.bean.EmployeeInfo;
import com.idi.hr.bean.LeaveReport;
import com.idi.hr.bean.Salary;
import com.idi.hr.bean.SalaryDetail;
import com.idi.hr.bean.SalaryReport;
import com.idi.hr.bean.SalaryReportPerEmployee;
import com.idi.hr.bean.WorkingDay;
import com.idi.hr.common.PropertiesManager;
import com.idi.hr.common.Utils;
import com.idi.hr.dao.DepartmentDAO;
import com.idi.hr.dao.EmployeeDAO;
import com.idi.hr.dao.SalaryDAO;
import com.idi.hr.dao.WorkingDayDAO;
import com.idi.hr.form.SalaryForm;
import com.idi.hr.form.SendReportForm;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
public class SalaryController {
	private static final Logger log = Logger.getLogger(SalaryController.class.getName());

	@Autowired
	private SalaryDAO salaryDAO;

	@Autowired
	private EmployeeDAO employeeDAO;

	@Autowired
	private WorkingDayDAO workingDayDAO;
	
	@Autowired
	private DepartmentDAO departmentDAO;
	
	@Autowired
	private JavaMailSender mailSender;

	PropertiesManager hr = new PropertiesManager("hr.properties");

	public static File fontFile = new File("/home/idi/properties/vuTimes.ttf");

	@RequestMapping(value = { "/salary/" })
	public String ListSalarys(Model model, @ModelAttribute("salaryForm") SalaryForm form) throws Exception {
		try {
			// Paging:
			// Number records of a Page: Default: 25
			// Page Index: Default: 1
			// Total records
			// Total of page
			if (form.getNumberRecordsOfPage() == 0) {
				form.setNumberRecordsOfPage(25);
			}
			if (form.getPageIndex() == 0) {
				form.setPageIndex(1);
			}

			List<Salary> list = salaryDAO.getSalarys();

			form.setTotalRecords(list.size());

			int totalPages = form.getTotalRecords() % form.getNumberRecordsOfPage() > 0
					? form.getTotalRecords() / form.getNumberRecordsOfPage() + 1
					: form.getTotalRecords() / form.getNumberRecordsOfPage();
			form.setTotalPages(totalPages);

			List<Salary> listSalaryForPage = new ArrayList<Salary>();

			if (form.getPageIndex() < totalPages) {
				if (form.getPageIndex() == 1) {
					for (int i = 0; i < form.getNumberRecordsOfPage(); i++) {
						Salary salary = new Salary();
						salary = list.get(i);
						listSalaryForPage.add(salary);
					}
				} else if (form.getPageIndex() > 1) {
					for (int i = ((form.getPageIndex() - 1) * form.getNumberRecordsOfPage()); i < form.getPageIndex()
							* form.getNumberRecordsOfPage(); i++) {
						Salary salary = new Salary();
						salary = list.get(i);
						listSalaryForPage.add(salary);
					}
				}
			} else if (form.getPageIndex() == totalPages) {
				for (int i = ((form.getPageIndex() - 1) * form.getNumberRecordsOfPage()); i < form
						.getTotalRecords(); i++) {
					Salary salary = new Salary();
					salary = list.get(i);
					listSalaryForPage.add(salary);
				}
			}
			model.addAttribute("salaryForm", form);
			model.addAttribute("salarys", listSalaryForPage);
			model.addAttribute("formTitle", "Danh sách lương của nhân viên ");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listSalaryInfo";
	}

	private Map<String, String> employees() {
		Map<String, String> employeeMap = new LinkedHashMap<String, String>();
		try {
			List<EmployeeInfo> list = employeeDAO.getEmployees();
			EmployeeInfo employee = new EmployeeInfo();
			for (int i = 0; i < list.size(); i++) {
				employee = (EmployeeInfo) list.get(i);
				Integer id = employee.getEmployeeId();
				employeeMap.put(id.toString(), employee.getFullName() + ", phòng " + employee.getDepartment());
			}

		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return employeeMap;
	}

	private Map<String, String> employeesNoInfo() {
		Map<String, String> employeeMap = new LinkedHashMap<String, String>();
		try {
			List<EmployeeInfo> list = employeeDAO.getEmployeesForInsertSalary();
			EmployeeInfo employee = new EmployeeInfo();
			for (int i = 0; i < list.size(); i++) {
				employee = (EmployeeInfo) list.get(i);
				Integer id = employee.getEmployeeId();
				employeeMap.put(id.toString(), employee.getFullName() + ", phòng " + employee.getDepartment());
			}

		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return employeeMap;
	}

	@RequestMapping(value = "/salary/insertSalary", method = RequestMethod.POST)
	public String addSalary(Model model, @ModelAttribute("salaryForm") @Validated Salary salary,
			final RedirectAttributes redirectAttributes) {
		try {
			salaryDAO.insertSalary(salary);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Thêm thông lương nhân viên thành công!");

		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/salary/";
	}

	@RequestMapping(value = "/salary/updateSalary", method = RequestMethod.POST)
	public String updateSalary(Model model, @ModelAttribute("salaryForm") @Validated Salary salary,
			final RedirectAttributes redirectAttributes) {
		try {
			salaryDAO.updateSalary(salary);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin lương nhân viên thành công!");

		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/salary/";
	}

	private String salaryForm(Model model, Salary salary) {
		model.addAttribute("salaryForm", salary);
		// get list employee id
		Map<String, String> employeeMap = this.employeesNoInfo();
		model.addAttribute("employeeMap", employeeMap);

		String actionform = "";
		if (salary.getEmployeeId() > 0) {
			model.addAttribute("formTitle", "Sửa thông tin lương nhân viên ");
			actionform = "editSalaryInfo";
		} else {
			model.addAttribute("formTitle", "Thêm mới thông tin lương nhân viên");
			actionform = "insertSalaryInfo";
		}
		return actionform;
	}

	@RequestMapping("/salary/insertSalary")
	public String addSalary(Model model) {
		Salary salary = new Salary();
		return this.salaryForm(model, salary);
	}

	@RequestMapping("/salary/editSalary")
	public String editSalary(Model model, @RequestParam("employeeId") int employeeId) {
		Salary salary = null;
		if (employeeId > 0) {
			salary = salaryDAO.getSalary(employeeId);
		}
		if (salary == null) {
			return "redirect:/salary/";
		}

		return this.salaryForm(model, salary);
	}

	// ------ Tính lương thực nhận cho NV theo tháng --------//
	@RequestMapping(value = "/salary/listSalaryDetail")
	public String listSalaryDetail(Model model, @RequestParam("employeeId") int employeeId,
			@ModelAttribute("salaryForm") SalaryForm form) {
		try {

			List<SalaryDetail> list = salaryDAO.getSalaryDetails(employeeId);

			// Paging:
			// Number records of a Page: Default: 25
			// Page Index: Default: 1
			// Total records
			// Total of page
			if (form.getNumberRecordsOfPage() == 0) {
				form.setNumberRecordsOfPage(25);
			}
			if (form.getPageIndex() == 0) {
				form.setPageIndex(1);
			}

			form.setTotalRecords(list.size());

			int totalPages = form.getTotalRecords() % form.getNumberRecordsOfPage() > 0
					? form.getTotalRecords() / form.getNumberRecordsOfPage() + 1
					: form.getTotalRecords() / form.getNumberRecordsOfPage();
			form.setTotalPages(totalPages);

			List<SalaryDetail> listSalaryForPage = new ArrayList<SalaryDetail>();

			if (form.getPageIndex() < totalPages) {
				if (form.getPageIndex() == 1) {
					for (int i = 0; i < form.getNumberRecordsOfPage(); i++) {
						SalaryDetail salary = new SalaryDetail();
						salary = list.get(i);
						listSalaryForPage.add(salary);
					}
				} else if (form.getPageIndex() > 1) {
					for (int i = ((form.getPageIndex() - 1) * form.getNumberRecordsOfPage()); i < form.getPageIndex()
							* form.getNumberRecordsOfPage(); i++) {
						SalaryDetail salary = new SalaryDetail();
						salary = list.get(i);
						listSalaryForPage.add(salary);
					}
				}
			} else if (form.getPageIndex() == totalPages) {
				for (int i = ((form.getPageIndex() - 1) * form.getNumberRecordsOfPage()); i < form
						.getTotalRecords(); i++) {
					SalaryDetail salary = new SalaryDetail();
					salary = list.get(i);
					listSalaryForPage.add(salary);
				}
			}
			model.addAttribute("salaryForm", form);
			model.addAttribute("employeeId", employeeId);
			Map<String, String> employeeMap = this.employees();
			String name = "";
			name = employeeMap.get(String.valueOf(employeeId));
			model.addAttribute("name", name);

			if (list != null && list.size() < 1) {
				model.addAttribute("message", "Chưa có thông tin lương cho nhân viên này");
				model.addAttribute("formTitle", "Danh sách lương tháng của " + name);
			} else
				model.addAttribute("formTitle", "Danh sách lương tháng của " + list.get(0).getFullName());

			model.addAttribute("salaryDetails", listSalaryForPage);

		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listSalaryByEmployee";
	}

	@RequestMapping(value = "/salary/insertSalaryDetailForm")
	public String insertSalaryDetailForm(Model model, @RequestParam("employeeId") int employeeId,
			SalaryDetail salaryDetail) {
		try {
			Calendar now = Calendar.getInstance();
			int month = now.get(Calendar.MONTH) + 1;
			if (salaryDetail.getMonth() > 0)
				month = salaryDetail.getMonth();
			int year = now.get(Calendar.YEAR);
			if (salaryDetail.getYear() > 0)
				year = salaryDetail.getYear();
			// System.out.println("Current Month is: " + month + ", year: " + year);
			// System.out.println("tinh luong cho Month is: " + salaryDetail.getMonth() + ",
			// year: " + year);
			// SalaryDetail salaryDetail = new SalaryDetail();
			try {
				salaryDetail = salaryDAO.getSalaryDetail(employeeId, month, year);
				log.info("Thang nay da duoc tinh luong ... update hoac chon thang khac ....");
				// return editSalaryDetailForm(model, salaryDetail);
			} catch (Exception e) {
				salaryDetail = salaryDAO.getSalaryDetail(employeeId, 0, 0);
				log.info("Tinh cho thang moi");
				salaryDetail.setMonth(month);
				salaryDetail.setYear(year);
				salaryDetail.setAdvancePayed("");
				salaryDetail.setBounus("");
				salaryDetail.setFinalSalary("");
				salaryDetail.setTaxPersonal("");
				salaryDetail.setOverTimeH("");
				salaryDetail.setOverTimeN("");
				salaryDetail.setOverTimeW("");
				salaryDetail.setSubsidize("");
				salaryDetail.setWorkedDay(null);
			}

			String moneyType = salaryDAO.getSalary(employeeId).getMoneyType();
			model.addAttribute("moneyType", moneyType);

			WorkingDay workingDay = null;
			if (month < 10)
				workingDay = workingDayDAO.getWorkingDay(year + "-0" + month, "IDI");
			else
				workingDay = workingDayDAO.getWorkingDay(year + "-" + month, "IDI");

			float salaryPerHour = 0;
			if (workingDay.getWorkDayOfMonth() != null) {
				float workingDayOfMonth = workingDay.getWorkDayOfMonth();

				String carDriver = "";
				carDriver = hr.getProperty("WORK_SATURDAY");
				// System.err.println(workingDayOfMonth + " thang " + month);
				// System.err.println(employeeDAO.getEmployee(String.valueOf(employeeId)).getJobTitle()
				// +"|" + carDriver);
				if (carDriver.contains(employeeDAO.getEmployee(String.valueOf(employeeId)).getJobTitle())) {
					log.info(salaryDetail.getFullName() + " la lai xe");
					log.info("Thang " + month + " co " + Utils.countWeekendDays(year, month) + " ngay thu 7 ");
					workingDayOfMonth = workingDayOfMonth + Utils.countWeekendDays(year, month);
					log.info("Cong them " + Utils.countWeekendDays(year, month) + " vao ngay cong chuan");
				}

				// System.err.println(workingDayOfMonth);
				if (salaryDetail.getSalary() != null && salaryDetail.getSalary().length() > 0) {
					salaryPerHour = Float.valueOf(salaryDetail.getSalary()) / workingDayOfMonth / 8;
					salaryPerHour = Math.round((salaryPerHour * 10) / 10);
				}
				// System.err.println("luong/gio" + salaryPerHour);
			}
			salaryDetail.setSalaryPerHour(salaryPerHour);
			model.addAttribute("salaryPerHour", salaryPerHour);

			Map<String, String> employeeMap = this.employees();
			String name = "";
			name = employeeMap.get(String.valueOf(employeeId));
			model.addAttribute("name", name);

			model.addAttribute("salaryDetail", salaryDetail);
			model.addAttribute("employeeId", employeeId);

			model.addAttribute("formTitle", "Thêm thông tin lương chi tiết của " + name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "formSalaryDetail";
	}

	@RequestMapping(value = "/salary/insertSalaryDetail", method = RequestMethod.POST)
	public String insertSalaryDetail(Model model,
			@ModelAttribute("salaryDetailForm") @Validated SalaryDetail salaryDetail,
			final RedirectAttributes redirectAttributes) {
		try {
			// System.err.println(salaryDetail.getMonth());
			//tinh/xac dinh luong co ban
			Float s = null;
			if (salaryDetail.getExchangeRate() != null && salaryDetail.getExchangeRate().length() > 0) {
				//cho t/h tinh luong theo ngoai te
				s = Float.valueOf(salaryDetail.getSalary()) * Float.valueOf(salaryDetail.getExchangeRate());
				salaryDetail.setBasicSalary(String.valueOf(s));
			} else if (s == null)
				s = Float.valueOf(salaryDetail.getSalary());

			WorkingDay workingDay = null;
			int month = salaryDetail.getMonth();
			int year = salaryDetail.getYear();
			int employeeId = salaryDetail.getEmployeeId();
			if (month < 10)
				workingDay = workingDayDAO.getWorkingDay(year + "-0" + month, "IDI");
			else
				workingDay = workingDayDAO.getWorkingDay(year + "-" + month, "IDI");

			float salaryPerHour = 0;
			if (workingDay.getWorkDayOfMonth() != null) {
				float workingDayOfMonth = workingDay.getWorkDayOfMonth();
				String carDriver = "";
				carDriver = hr.getProperty("WORK_SATURDAY");
				// System.err.println(workingDayOfMonth + " thang " + month);
				// System.err.println(employeeDAO.getEmployee(String.valueOf(employeeId)).getJobTitle()
				// +"|" + carDriver);
				if (carDriver.contains(employeeDAO.getEmployee(String.valueOf(employeeId)).getJobTitle())) {
					log.info(salaryDetail.getFullName() + " la lai xe");
					log.info("Thang " + month + " co " + Utils.countWeekendDays(year, month) + " ngay thu 7 ");
					workingDayOfMonth = workingDayOfMonth + Utils.countWeekendDays(year, month);
					log.info("Cong them " + Utils.countWeekendDays(year, month) + " vao ngay cong chuan");
				}

				// System.err.println(workingDayOfMonth);
				if (s != null && s > 0) {
					salaryPerHour = s / workingDayOfMonth / 8;
					salaryPerHour = Math.round((salaryPerHour * 10) / 10);
				}
				// System.err.println("luong/gio" + salaryPerHour);

				// Không lv đủ cả tháng
				String workedDay = salaryDetail.getWorkedDay();
				if (workedDay != null && workedDay.length() > 0) {
					float currentSalary = (Float.parseFloat(workedDay) / workingDayOfMonth) * s;
					log.info("Ngay lv thuc te trong thang: " + workedDay + "/" + workingDayOfMonth);
					salaryDetail.setSalaryForWorkedDay(String.valueOf(currentSalary));
				}
			} else {
				model.addAttribute("workDayDefine",
						"Vui lòng định nghĩa ngày công chuẩn cho tháng tại phần chấm công để việc tính lương được chính sác!");
			}
			salaryDetail.setSalaryPerHour(salaryPerHour);

			// ting toan luong over time
			double overTimeSalary = 0;
			// float salaryPerHour = salaryDetail.getSalaryPerHour();
			String overTimeN = salaryDetail.getOverTimeN();
			if (overTimeN != null && overTimeN.length() > 0 && Float.parseFloat(overTimeN) > 0) {
				overTimeSalary = overTimeSalary + Float.parseFloat(overTimeN) * salaryPerHour * 1.5;
				// System.err.println(overTimeSalary + " overTime N controller " + overTimeN);
			}
			String overTimeW = salaryDetail.getOverTimeW();
			if (overTimeW != null && overTimeW.length() > 0 && Float.parseFloat(overTimeW) > 0) {
				overTimeSalary = overTimeSalary + Float.parseFloat(overTimeW) * salaryPerHour * 2;
				// System.err.println(overTimeSalary + " overTime W controller " + overTimeW);
			}
			String overTimeH = salaryDetail.getOverTimeH();
			if (overTimeH != null && overTimeH.length() > 0 && Float.parseFloat(overTimeH) > 0) {
				overTimeSalary = overTimeSalary + Float.parseFloat(overTimeH) * salaryPerHour * 3;
				// System.err.println(overTimeSalary + " overTime H controller " + overTimeH);
			}
			
			String overTimeNN = salaryDetail.getOverTimeNN();
			if (overTimeNN != null && overTimeNN.length() > 0 && Float.parseFloat(overTimeNN) > 0) {
				overTimeSalary = overTimeSalary + Float.parseFloat(overTimeNN) * salaryPerHour * 1.8;
				// System.err.println(overTimeSalary + " overTime NN controller " + overTimeNN);
			}
			String overTimeWN = salaryDetail.getOverTimeWN();
			if (overTimeWN != null && overTimeWN.length() > 0 && Float.parseFloat(overTimeWN) > 0) {
				overTimeSalary = overTimeSalary + Float.parseFloat(overTimeWN) * salaryPerHour * 2.3;
				// System.err.println(overTimeSalary + " overTime WN controller " + overTimeWN);
			}
			String overTimeHN = salaryDetail.getOverTimeHN();
			if (overTimeHN != null && overTimeHN.length() > 0 && Float.parseFloat(overTimeHN) > 0) {
				overTimeSalary = overTimeSalary + Float.parseFloat(overTimeHN) * salaryPerHour * 3.3;
				// System.err.println(overTimeSalary + " overTime HN controller " + overTimeHN);
			}

			// update ... lay salary o bang salary info sang bang salary detail lam basic
			// salary
			if (salaryDetail.getBasicSalary() == null) {
				salaryDetail.setBasicSalary(String.valueOf(s));
			}

			// System.err.println("salaryPerHour: " + salaryPerHour + " overTimeSalary: " +
			// overTimeSalary);
			overTimeSalary = Math.round(overTimeSalary);
			salaryDetail.setOverTimeSalary(String.valueOf(overTimeSalary));

			//tinh phan dong bhxh cua nv
			if (salaryDetail.getSalaryInsurance() != null && salaryDetail.getSalaryInsurance().length() > 0)
				salaryDetail.setPayedInsurance(String.valueOf(Float.parseFloat(salaryDetail.getSalaryInsurance())
						* Float.parseFloat(salaryDetail.getPercentEmployeePay()) / 100));
			
			//tinh so tien cty chi cho bhxh
			if (salaryDetail.getSalaryInsurance() != null && salaryDetail.getSalaryInsurance().length() > 0)
				salaryDetail.setcPayedInsur(String.valueOf(Float.parseFloat(salaryDetail.getSalaryInsurance())
						* Float.parseFloat(salaryDetail.getPercentCompanyPay()) / 100));

			salaryDAO.insertSalaryDetail(salaryDetail);
			model.addAttribute("salaryDetail", salaryDetail);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Thêm thông tin lương chi tiết của thành công!");

		} catch (Exception e) {
			log.error(e, e);
		}
		return editSalaryDetailForm(model, salaryDetail);
	}

	@RequestMapping("/salary/editSalaryDetailForm")
	public String editSalaryDetailForm(Model model,
			@ModelAttribute("salaryDetail") @Validated SalaryDetail salaryDetail) {
		try {
			// SalaryDetail salaryDetail = new SalaryDetail();

			WorkingDay workingDay = null;
			int month = salaryDetail.getMonth();
			int year = salaryDetail.getYear();
			int employeeId = salaryDetail.getEmployeeId();
			salaryDetail = salaryDAO.getSalaryDetail(employeeId, month, year);
			if (month < 10)
				workingDay = workingDayDAO.getWorkingDay(year + "-0" + month, "IDI");
			else
				workingDay = workingDayDAO.getWorkingDay(year + "-" + month, "IDI");

			float salaryPerHour = 0;
			if (workingDay.getWorkDayOfMonth() != null) {
				float workingDayOfMonth = workingDay.getWorkDayOfMonth();

				String carDriver = "";
				carDriver = hr.getProperty("WORK_SATURDAY");
				// System.err.println(workingDayOfMonth + " thang " + month);
				// System.err.println(employeeDAO.getEmployee(String.valueOf(employeeId)).getJobTitle()
				// +"|" + carDriver);
				if (carDriver.contains(employeeDAO.getEmployee(String.valueOf(employeeId)).getJobTitle())) {
					log.info(salaryDetail.getFullName() + " la lai xe");
					log.info("Thang " + month + " co " + Utils.countWeekendDays(year, month) + " ngay thu 7 ");
					workingDayOfMonth = workingDayOfMonth + Utils.countWeekendDays(year, month);
					log.info("Cong them " + Utils.countWeekendDays(year, month) + " vao ngay cong chuan");
				}
				if (salaryDetail.getSalary() != null && salaryDetail.getSalary().length() > 0) {
					salaryPerHour = Float.valueOf(salaryDetail.getSalary()) / workingDayOfMonth / 8;
					salaryPerHour = Math.round((salaryPerHour * 10) / 10);
				}
			}

			String moneyType = salaryDAO.getSalary(employeeId).getMoneyType();
			model.addAttribute("moneyType", moneyType);

			salaryDetail.setSalaryPerHour(salaryPerHour);
			model.addAttribute("salaryPerHour", salaryPerHour);
			model.addAttribute("salaryDetail", salaryDetail);
			model.addAttribute("employeeId", salaryDetail.getEmployeeId());

			model.addAttribute("formTitle", "Thay đổi thông tin tính lương chi tiết của " + salaryDetail.getFullName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "updateSalaryDetail";
	}

	@RequestMapping("/salary/exportToPDF")
	public String exportToPDF(Model model, @RequestParam("employeeId") int employeeId, @RequestParam("month") int month,
			@RequestParam("year") int year) throws Exception {
		SalaryDetail salaryDetail = new SalaryDetail();
		salaryDetail.setMonth(month);
		salaryDetail.setYear(year);
		salaryDetail.setEmployeeId(employeeId);

		String fileName = "";
		String title = "";
		String path = hr.getProperty("REPORT_PATH");
		File dir = new File(path);
		//String moneyType = null;
		float salaryPerHour = 0;
		SendReportForm sendForm = new SendReportForm();
		try {			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String currentDate = dateFormat.format(date);
			
			WorkingDay workingDay = null;

			salaryDetail = salaryDAO.getSalaryDetail(employeeId, month, year);
			if (month < 10)
				workingDay = workingDayDAO.getWorkingDay(year + "-0" + month, "IDI");
			else
				workingDay = workingDayDAO.getWorkingDay(year + "-" + month, "IDI");

			if (workingDay.getWorkDayOfMonth() != null) {
				float workingDayOfMonth = workingDay.getWorkDayOfMonth();

				String carDriver = "";
				carDriver = hr.getProperty("WORK_SATURDAY");
				// System.err.println(workingDayOfMonth + " thang " + month);
				// System.err.println(employeeDAO.getEmployee(String.valueOf(employeeId)).getJobTitle()
				// +"|" + carDriver);
				if (carDriver.contains(employeeDAO.getEmployee(String.valueOf(employeeId)).getJobTitle())) {
					log.info(salaryDetail.getFullName() + " la lai xe");
					log.info("Thang " + month + " co " + Utils.countWeekendDays(year, month) + " ngay thu 7 ");
					workingDayOfMonth = workingDayOfMonth + Utils.countWeekendDays(year, month);
					log.info("Cong them " + Utils.countWeekendDays(year, month) + " vao ngay cong chuan");
				}
				if (salaryDetail.getSalary() != null && salaryDetail.getSalary().length() > 0) {
					salaryPerHour = Float.valueOf(salaryDetail.getSalary()) / workingDayOfMonth / 8;
					salaryPerHour = Math.round((salaryPerHour * 10) / 10);
				}
				salaryDetail.setWorkingDayOfMonth(workingDayOfMonth);
			}

			//moneyType = salaryDAO.getSalary(employeeId).getMoneyType();
			salaryDetail.setSalaryPerHour(salaryPerHour);

			fileName = "Thong tin luong chi tiet thang " + month + " nam " + year + " cua "
					+ salaryDetail.getFullName();
			title = "Thông tin lương chi tiết tháng " + month + " năm " + year + " của " + salaryDetail.getFullName()
					+ " phòng " + salaryDetail.getDepartment();

			Document document = new Document(PageSize.A4.rotate());
			if (!dir.exists()) {
				dir.mkdirs();
			}

			PdfWriter.getInstance(document, new FileOutputStream(dir + "/" + fileName + ".pdf"));
			BaseFont bf = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font fontB = new Font(bf, 18);
			Font font = new Font(bf, 14);
			document.open();

			PdfPTable table = new PdfPTable(4);
			
			addSummarySalaryToPDFRows(table, salaryDetail);
			document.add(new Paragraph("                  " + title, fontB));
			document.add(new Paragraph("                  ", font));
			document.add(table);
			document.add(new Paragraph("                  ", font));
			document.add(new Paragraph("                                                                                                                                                          Ngày lập: " + Utils.convertDateToDisplay(currentDate), font));
			document.add(new Paragraph("                                                                                                                                                          Người lập: Vũ Quốc Vinh " , font));			
			document.add(new Paragraph("                  ", font));
			document.add(new Paragraph("                  ", font));
			document.close();
		} catch (Exception e) {
			model.addAttribute("isOpen", "Yes");
			model.addAttribute("warning", "Vui lòng tắt file " + dir + fileName + " nếu đang mở trước khi export");
			e.printStackTrace();
		}
				
		EmployeeInfo emp = new EmployeeInfo();
		emp = employeeDAO.getEmployee(String.valueOf(employeeId));
		if(emp.getEmail() != null && emp.getEmail().length() > 6 && emp.getEmail().contains("@") && emp.getEmail().contains(".com")) {
			model.addAttribute("validateEmail", "");
		}else {
			model.addAttribute("validateEmail", "Địa chỉ email chưa có hoặc không chính sác, vui lòng update tại phần thông tin nhân viên");
		}
		sendForm.setEmployeeId(employeeId);
		sendForm.setSubject(title);
		sendForm.setFileName(fileName);
		sendForm.setSendFrom("Phần mềm quản lý tiền lương");
		sendForm.setSendTo(emp.getEmail());
		model.addAttribute("fileName", fileName);
		
		model.addAttribute("sendForm", sendForm);
		model.addAttribute("employeeId", salaryDetail.getEmployeeId());
		model.addAttribute("formTitle", title);		
		model.addAttribute("path", path);
		model.addAttribute("fileSave", fileName + "', đã được export tại thư mục " + dir );
		model.addAttribute("message",  title + " được export thành công tại thư mục " + dir);
		
		return "sendSummarySalary";
	}
	
	@RequestMapping(value = "/salary/sendSummarySalaryReport")
	public String sendSummarySalaryReport(Model model, @ModelAttribute("sendForm") @Validated SendReportForm sendForm,
			@RequestParam(required = false, value = "formTitle") String formTitle) throws Exception {
		log.info("sending SummaryReport");
		try {
			//System.err.println("formTitle " + formTitle);
			if (formTitle == null) {
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				String path = hr.getProperty("REPORT_PATH");

				File file = new File(path + sendForm.getFileName() + ".pdf");
				log.info("sending report: " + path + sendForm.getFileName() + ".pdf");
				if (file.exists()) {
					mimeMessage.setFrom("IDIHR-NotReply");
					helper.setSubject("Thông tin lương tháng chi tiết --> Gửi từ PM Quản lý tiền lương");
					//System.err.println("Subject: " + sendReportForm.getSubject());
					mimeMessage.setContent(sendForm.getFileName(), "text/html; charset=UTF-8");

					Multipart multipart = new MimeMultipart();
					BodyPart attach = new MimeBodyPart();
					DataSource source = new FileDataSource(path + sendForm.getFileName() + ".pdf");
					attach.setDataHandler(new DataHandler(source));
					attach.setFileName(sendForm.getFileName() + ".pdf");

					multipart.addBodyPart(attach);
					BodyPart content = new MimeBodyPart();
					content.setContent("Thông tin lương tháng chi tiết ", "text/html; charset=UTF-8");
					multipart.addBodyPart(content);
					mimeMessage.setContent(multipart, "text/html; charset=UTF-8");
					//helper.setTo(sendForm.getSendTo()); //gui cho 1 nguoi
					helper.setTo(InternetAddress.parse(sendForm.getSendTo()));//gui cho nhieu nguoi
					mailSender.send(mimeMessage);
							
					model.addAttribute("message", "Đã gửi thông tin lương tháng chi tiết thành công ...");
					model.addAttribute("formTitle", "Gửi thông tin lương tháng chi tiết");
					model.addAttribute("employeeId", sendForm.getEmployeeId());
					
					return "sentSummarySalaryReport";
				} else {
					log.info("File ko ton tai hoac chua dc export");
					model.addAttribute("formTitle", "Gửi thông tin lương tháng chi tiết");
					model.addAttribute("message", "Vui lòng export file trước khi gửi");
				}
			} else {
				log.info("try to sending report again...");		
				model.addAttribute("formTitle", "Gửi thông tin lương tháng chi tiết");
				model.addAttribute("warning", "Vui lòng export file trước khi gửi");
			}
			
			model.addAttribute("formTitle", "Gửi thông tin lương tháng chi tiết");			
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("formTitle", "Gửi thông tin lương tháng chi tiết");
			model.addAttribute("error", "Xin lỗi, đã có lỗi xẩy ra trong quá trình gửi mail, vui lòng thử lại!");
		}
		return "sentSummarySalaryReport";
	}
	
	@RequestMapping(value = "/salary/sendSummarySalary")
	public String sendSummarySalary(Model model, @ModelAttribute("sendForm") @Validated SendReportForm sendForm,
			@RequestParam(required = false, value = "formTitle") String formTitle) throws Exception {
		log.info("sending SummaryReport");
		try {
			//System.err.println("formTitle " + formTitle);
			if (formTitle == null) {
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				String path = hr.getProperty("REPORT_PATH");

				File file = new File(path + sendForm.getFileName() + ".pdf");
				log.info("sending report: " + path + sendForm.getFileName() + ".pdf");
				if (file.exists()) {
					mimeMessage.setFrom("IDIHR-NotReply");
					helper.setSubject("Thông tin lương tháng chi tiết --> Gửi từ PM Quản lý tiền lương");
					//System.err.println("Subject: " + sendReportForm.getSubject());
					mimeMessage.setContent(sendForm.getFileName(), "text/html; charset=UTF-8");

					Multipart multipart = new MimeMultipart();
					BodyPart attach = new MimeBodyPart();
					DataSource source = new FileDataSource(path + sendForm.getFileName() + ".pdf");
					attach.setDataHandler(new DataHandler(source));
					attach.setFileName(sendForm.getFileName() + ".pdf");

					multipart.addBodyPart(attach);
					BodyPart content = new MimeBodyPart();
					content.setContent("Thông tin lương tháng chi tiết ", "text/html; charset=UTF-8");
					multipart.addBodyPart(content);
					mimeMessage.setContent(multipart, "text/html; charset=UTF-8");
					helper.setTo(sendForm.getSendTo());//xxx
					mailSender.send(mimeMessage);
							
					model.addAttribute("message", "Đã gửi thông tin lương tháng chi tiết thành công ...");
					model.addAttribute("formTitle", "Gửi thông tin lương tháng chi tiết");
					model.addAttribute("employeeId", sendForm.getEmployeeId());
					
					return "sentSummarySalary";
				} else {
					log.info("File ko ton tai hoac chua dc export");
					model.addAttribute("employeeId", sendForm.getEmployeeId());
					model.addAttribute("formTitle", "Gửi thông tin lương tháng chi tiết");
					model.addAttribute("message", "Vui lòng export file trước khi gửi");
				}
			} else {
				log.info("try to sending report again...");		
				model.addAttribute("employeeId", sendForm.getEmployeeId());
				model.addAttribute("formTitle", "Gửi thông tin lương tháng chi tiết");
				model.addAttribute("warning", "Vui lòng export file trước khi gửi");
			}
			
			model.addAttribute("formTitle", "Gửi thông tin lương tháng chi tiết");			
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("employeeId", sendForm.getEmployeeId());
			model.addAttribute("formTitle", "Gửi thông tin lương tháng chi tiết");
			model.addAttribute("error", "Xin lỗi, đã có lỗi xẩy ra trong quá trình gửi mail, vui lòng thử lại!");
		}
		return "sentSummarySalary";
	}
		  
	private void addSummarySalaryToPDFRows(PdfPTable table, SalaryDetail salaryDetail)
			throws DocumentException, IOException {
		BaseFont bf = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 12);
		
		float arreas = 0;
		if(salaryDetail.getArrears() != null && salaryDetail.getArrears().length() > 0)
			arreas = Float.valueOf(salaryDetail.getArrears());
		float tax = 0;
		if(salaryDetail.getTaxPersonal() != null && salaryDetail.getTaxPersonal().length() > 0)
			tax = Float.valueOf(salaryDetail.getTaxPersonal());
		float advance = 0;
		if(salaryDetail.getAdvancePayed() != null && salaryDetail.getAdvancePayed().length() > 0)
			advance = Float.valueOf(salaryDetail.getAdvancePayed());
		float insurance = 0;
		if(salaryDetail.getPayedInsurance() != null && salaryDetail.getPayedInsurance().length() > 0)
			insurance = Float.valueOf(salaryDetail.getPayedInsurance());
		
		float overTimeN = 0;
		float overTimeH = 0;
		float overTimeW = 0;
		float overTimeNN = 0;
		float overTimeHN = 0;
		float overTimeWN = 0;
		
		for (int i = 0; i < 16; i++) {
			if(i==1) { 
				table.addCell(new Paragraph("Số tài khoản: ", font));
				table.addCell(String.valueOf(salaryDetail.getBankNo()));
				table.addCell(new Paragraph("Tên ngân hàng: ", font));
				table.addCell(String.valueOf(salaryDetail.getBankName()));
			}
			if(i==2) {
				table.addCell(new Paragraph("Lương: ", font));
				table.addCell(String.valueOf(salaryDetail.getSalary() + " " +salaryDAO.getSalary(salaryDetail.getEmployeeId()).getMoneyType()));
				table.addCell(new Paragraph("Tỷ giá (với ngoại tệ): ", font));
				if(salaryDetail.getExchangeRate() != null)
					table.addCell(String.valueOf(salaryDetail.getExchangeRate()));		
				else
					table.addCell("");
			}
			if(i==3) { 
				table.addCell(new Paragraph("Hệ số hoàn thành công việc: ", font));
				table.addCell(String.valueOf(salaryDetail.getWorkComplete() + " %"));
				table.addCell(new Paragraph("Số ngày làm việc thực tế: ", font));
				if(salaryDetail.getWorkedDay() != null && salaryDetail.getWorkedDay().length() > 0)
					if(salaryDetail.getWorkingDayOfMonth() != null && salaryDetail.getWorkingDayOfMonth() > 0)
						table.addCell(String.valueOf(salaryDetail.getWorkedDay()) + "/" + salaryDetail.getWorkingDayOfMonth());
					else
						table.addCell(String.valueOf(salaryDetail.getWorkedDay()));
				else
					table.addCell(new Paragraph("làm việc đủ cả tháng", font));
			}
			if(i==4) {
				table.addCell(new Paragraph("Lương đóng BHXH: ", font));
				if(salaryDetail.getSalaryInsurance() != null && salaryDetail.getSalaryInsurance().length() > 0) {
					table.addCell(String.valueOf(salaryDetail.getSalaryInsurance()));
					table.addCell(new Paragraph("Đóng BHXH (" + salaryDetail.getPercentEmployeePay() +" %): ", font));
					table.addCell(String.valueOf(salaryDetail.getPayedInsurance()));
				}else {
					table.addCell(new Paragraph("không tham gia ", font));
					table.addCell("");
					table.addCell("");
				}					
			}
			if(i==5) {
				table.addCell(new Paragraph("Thưởng: ", font));
				table.addCell(String.valueOf(salaryDetail.getBounus()));
				table.addCell(new Paragraph("Tạm ứng: ", font));
				table.addCell(String.valueOf(salaryDetail.getAdvancePayed()));
			}
			if(i==6) {
				table.addCell(new Paragraph("Trợ cấp độc hại/trách nhiệm: ", font));
				table.addCell(String.valueOf(salaryDetail.getSubsidize()));
				table.addCell(new Paragraph("Thuế thu nhập cá nhân: ", font));
				table.addCell(String.valueOf(salaryDetail.getTaxPersonal()));
			}
			if(i==7) {
				table.addCell(new Paragraph("Các khoản thu khác: ", font));
				table.addCell(String.valueOf(salaryDetail.getOther()));
				table.addCell(new Paragraph("Truy thu: ", font));
				table.addCell(String.valueOf(salaryDetail.getArrears()));
			}

			if(salaryDetail.getSalaryPerHour() > 0) {				
				if(i==8) {
					table.addCell(new Paragraph("Làm thêm ngày thường: ", font));
					if(salaryDetail.getOverTimeN() != null && salaryDetail.getOverTimeN().length() > 0) {
						overTimeN = salaryDetail.getSalaryPerHour()*Float.valueOf(salaryDetail.getOverTimeN())*3/2;
						table.addCell(salaryDetail.getOverTimeN() + "h*" + String.format ("%,.0f", Float.valueOf(salaryDetail.getSalaryPerHour())) + "/h*1.5 = " + String.format ("%,.0f", overTimeN));
						table.addCell("");
						table.addCell("");
					}else {
						table.addCell("");
						table.addCell("");
						table.addCell("");
					}
				}if(i==9) {
					table.addCell(new Paragraph("Làm thêm cuối tuần: ", font));
					if(salaryDetail.getOverTimeW() != null && salaryDetail.getOverTimeW().length() > 0) {
						overTimeW = salaryDetail.getSalaryPerHour()*Float.valueOf(salaryDetail.getOverTimeW())*2;
						table.addCell(salaryDetail.getOverTimeW() + "h*" + String.format ("%,.0f", Float.valueOf(salaryDetail.getSalaryPerHour())) + "/h*2 = " + String.format ("%,.0f", overTimeW));
						table.addCell("");
						table.addCell("");
					}else {
						table.addCell("");
						table.addCell("");
						table.addCell("");
					}	
				}if(i==10) {
					table.addCell(new Paragraph("Làm thêm ngày lễ: ", font));
					if(salaryDetail.getOverTimeH() != null && salaryDetail.getOverTimeH().length() > 0) {
						overTimeH = salaryDetail.getSalaryPerHour()*Float.valueOf(salaryDetail.getOverTimeH())*3;
						table.addCell(salaryDetail.getOverTimeH() + "h*" + String.format ("%,.0f", Float.valueOf(salaryDetail.getSalaryPerHour())) + "/h*3 = " + String.format ("%,.0f", overTimeH));
						table.addCell("");
						table.addCell("");
					}else {
						table.addCell("");
						table.addCell("");
						table.addCell("");
					}					
				}if(i==11) {
					table.addCell(new Paragraph("Làm thêm đêm ngày thường: ", font));
					if(salaryDetail.getOverTimeNN() != null && salaryDetail.getOverTimeNN().length() > 0) {
						overTimeNN = salaryDetail.getSalaryPerHour()*Float.valueOf(salaryDetail.getOverTimeNN())*(float)1.8;
						table.addCell(salaryDetail.getOverTimeNN() + "h*" + String.format ("%,.0f", Float.valueOf(salaryDetail.getSalaryPerHour())) + "/h*1.8 = " + String.format ("%,.0f", overTimeNN));
						table.addCell("");
						table.addCell("");
					}else {
						table.addCell("");
						table.addCell("");
						table.addCell("");
					}
				}if(i==12) {
					table.addCell(new Paragraph("Làm thêm đêm cuối tuần: ", font));
					if(salaryDetail.getOverTimeWN() != null && salaryDetail.getOverTimeWN().length() > 0) {
						overTimeWN = salaryDetail.getSalaryPerHour()*Float.valueOf(salaryDetail.getOverTimeWN())*(float)2.3;
						table.addCell(salaryDetail.getOverTimeWN() + "h*" + String.format ("%,.0f", Float.valueOf(salaryDetail.getSalaryPerHour())) + "/h*2.3 = " + String.format ("%,.0f", overTimeWN));
						table.addCell("");
						table.addCell("");
					}else {
						table.addCell("");
						table.addCell("");
						table.addCell("");
					}	
				}if(i==13) {
					table.addCell(new Paragraph("Làm thêm đêm ngày lễ: ", font));
					if(salaryDetail.getOverTimeHN() != null && salaryDetail.getOverTimeHN().length() > 0) {
						overTimeHN = salaryDetail.getSalaryPerHour()*Float.valueOf(salaryDetail.getOverTimeH())*(float)3.3;
						table.addCell(salaryDetail.getOverTimeHN() + "h*" + String.format ("%,.0f", Float.valueOf(salaryDetail.getSalaryPerHour())) + "/h*3.3 = " + String.format ("%,.0f", overTimeHN));
						table.addCell("");
						table.addCell("");
					}else {
						table.addCell("");
						table.addCell("");
						table.addCell("");
					}
				}if(i==14) {
					float other = 0;
					if(salaryDetail.getOther() != null && salaryDetail.getOther().length() > 0)
						other = Float.valueOf(salaryDetail.getOther());
					float bounus = 0;
					if(salaryDetail.getBounus() != null && salaryDetail.getBounus().length() > 0)
						bounus = Float.valueOf(salaryDetail.getBounus());					
					float subsidize = 0;					
					if(salaryDetail.getSubsidize() != null && salaryDetail.getSubsidize().length() > 0)
						subsidize = Float.valueOf(salaryDetail.getSubsidize());
					
					float salary = 0;
					float bSsalary = 0;
					if(salaryDetail.getExchangeRate() != null && salaryDetail.getExchangeRate().length() > 0)
						bSsalary = Float.valueOf(salaryDetail.getSalary())* Float.valueOf(salaryDetail.getExchangeRate());
					else
						bSsalary = Float.valueOf(salaryDetail.getSalary());
					
					if(salaryDetail.getWorkedDay() != null && salaryDetail.getWorkedDay().length() > 0 && salaryDetail.getWorkingDayOfMonth() != null && salaryDetail.getWorkingDayOfMonth() > 0)
						salary = Float.valueOf(salaryDetail.getWorkedDay())/salaryDetail.getWorkingDayOfMonth() + bSsalary*salaryDetail.getWorkComplete()/100;
					else
						salary = bSsalary*salaryDetail.getWorkComplete()/100;
					
					table.addCell(new Paragraph("Tổng thu: ", font));
					table.addCell(String.valueOf(String.format ("%,.0f", overTimeH + overTimeN + overTimeW + overTimeHN + overTimeNN + overTimeWN + other + subsidize + bounus + salary)));

					table.addCell(new Paragraph("Tổng giảm trừ: ", font));						
					table.addCell(new Paragraph(String.valueOf(String.format ("%,.0f", arreas + tax + advance + insurance)), font));
				}if(i==15) {
					table.addCell(new Paragraph("Lương thực nhận: ", font));
					table.addCell(new Paragraph(String.valueOf(String.format ("%,.0f", Float.valueOf(salaryDetail.getFinalSalary()))) + " vnđ", font));
					table.addCell("");
					table.addCell("");
				}
				
			} else {
				if(i==8) {
					float other = 0;
					if(salaryDetail.getOther() != null && salaryDetail.getOther().length() > 0)
						other = Float.valueOf(salaryDetail.getOther());
					float bounus = 0;
					if(salaryDetail.getBounus() != null && salaryDetail.getBounus().length() > 0)
						bounus = Float.valueOf(salaryDetail.getBounus());
					float subsidize = 0;					
					if(salaryDetail.getSubsidize() != null && salaryDetail.getSubsidize().length() > 0)
						subsidize = Float.valueOf(salaryDetail.getSubsidize());
					
					float salary = 0;
					float bSsalary = 0;
					if(salaryDetail.getExchangeRate() != null && salaryDetail.getExchangeRate().length() > 0)
						bSsalary = Float.valueOf(salaryDetail.getSalary())* Float.valueOf(salaryDetail.getExchangeRate());
					else
						bSsalary = Float.valueOf(salaryDetail.getSalary());
					
					if(salaryDetail.getWorkedDay() != null && salaryDetail.getWorkedDay().length() > 0 && salaryDetail.getWorkingDayOfMonth() != null && salaryDetail.getWorkingDayOfMonth() > 0)
						salary = Float.valueOf(salaryDetail.getWorkedDay())/salaryDetail.getWorkingDayOfMonth() + bSsalary*salaryDetail.getWorkComplete()/100;
					else
						salary = bSsalary*salaryDetail.getWorkComplete()/100;

					table.addCell(new Paragraph("Tổng thu: ", font));
					table.addCell(String.valueOf(String.format ("%,.0f", other + bounus + subsidize + salary)));
					table.addCell(new Paragraph("Tổng giảm trừ: ", font));
					table.addCell(String.valueOf(String.format ("%,.0f", arreas + tax + advance + insurance)));
				}if(i==9){
					table.addCell(new Paragraph("Lương thực nhận: ", font));
					table.addCell(new Paragraph(String.valueOf(String.format ("%,.0f", Float.valueOf(salaryDetail.getFinalSalary()))) + " vnđ", font));
					table.addCell("");
					table.addCell("");	
				}
			}
		}
	}

	@RequestMapping(value = "/salary/updateSalaryDetail", method = RequestMethod.POST)
	public String updateSalaryDetail(Model model, @ModelAttribute("salaryDetail") @Validated SalaryDetail salaryDetail,
			final RedirectAttributes redirectAttributes) {
		try {
			// System.err.println(salaryDetail.getSalary());
			Float s = null;
			//vi la update nen chck de lay luong co ban cu			
			if(salaryDetail.getBasicSalary() != null && salaryDetail.getBasicSalary().trim().length()>0) {
				s = Float.valueOf(salaryDetail.getBasicSalary());
			}else {
				//binh thuong t/h nay se ko xay ra --> care cho t/h data cu co van de
				if (salaryDetail.getExchangeRate() != null && salaryDetail.getExchangeRate().length() > 0) {
					s = Float.valueOf(salaryDetail.getSalary()) * Float.valueOf(salaryDetail.getExchangeRate());
					salaryDetail.setBasicSalary(String.valueOf(s));
				} else if (s == null)
					s = Float.valueOf(salaryDetail.getSalary());
				/// System.err.println(s);
			}
			WorkingDay workingDay = null;
			int month = salaryDetail.getMonth();
			int year = salaryDetail.getYear();
			int employeeId = salaryDetail.getEmployeeId();
			if (month < 10)
				workingDay = workingDayDAO.getWorkingDay(year + "-0" + month, "IDI");
			else
				workingDay = workingDayDAO.getWorkingDay(year + "-" + month, "IDI");

			float salaryPerHour = 0;
			// System.err.println(workingDay.getWorkDayOfMonth());
			if (workingDay.getWorkDayOfMonth() != null) {
				float workingDayOfMonth = workingDay.getWorkDayOfMonth();

				String carDriver = "";
				carDriver = hr.getProperty("WORK_SATURDAY");
				// System.err.println(workingDayOfMonth + " thang " + month);
				// System.err.println(employeeDAO.getEmployee(String.valueOf(employeeId)).getJobTitle()
				// +"|" + carDriver);
				if (carDriver.contains(employeeDAO.getEmployee(String.valueOf(employeeId)).getJobTitle())) {
					log.info(salaryDetail.getFullName() + " la lai xe");
					log.info("Thang " + month + " co " + Utils.countWeekendDays(year, month) + " ngay thu 7 ");
					workingDayOfMonth = workingDayOfMonth + Utils.countWeekendDays(year, month);
					log.info("Cong them " + Utils.countWeekendDays(year, month) + " vao ngay cong chuan");
				}

				if (s != null && s > 0) {
					salaryPerHour = s / workingDayOfMonth / 8;
					salaryPerHour = Math.round(salaryPerHour);
					// System.err.println("Lương theo giờ: " + Math.round(salaryPerHour));
				}

				// Không lv đủ cả tháng
				String workedDay = salaryDetail.getWorkedDay();
				if (workedDay != null && workedDay.length() > 0) {
					log.info("Ngay lv thuc te trong thang: " + workedDay + "/" + workingDayOfMonth);
					float currentSalary = (Float.parseFloat(workedDay) / workingDayOfMonth) * s;
					salaryDetail.setSalaryForWorkedDay(String.valueOf(currentSalary));
				}
			} else {
				model.addAttribute("workDayDefine",
						"Vui lòng định nghĩa ngày công chuẩn cho tháng tại phần chấm công để việc tính lương được chính sác!");
			}
			salaryDetail.setSalaryPerHour(salaryPerHour);

			// ting toan luong over time
			double overTimeSalary = 0;
			String overTimeN = salaryDetail.getOverTimeN();
			if (overTimeN != null && overTimeN.length() > 0 && Float.parseFloat(overTimeN) > 0)
				overTimeSalary = overTimeSalary + Float.parseFloat(overTimeN) * salaryPerHour * 1.5;
			String overTimeW = salaryDetail.getOverTimeW();
			if (overTimeW != null && overTimeW.length() > 0 && Float.parseFloat(overTimeW) > 0)
				overTimeSalary = overTimeSalary + Float.parseFloat(overTimeW) * salaryPerHour * 2;
			String overTimeH = salaryDetail.getOverTimeH();
			if (overTimeH != null && overTimeH.length() > 0 && Float.parseFloat(overTimeH) > 0)
				overTimeSalary = overTimeSalary + Float.parseFloat(overTimeH) * salaryPerHour * 3;

			String overTimeNN = salaryDetail.getOverTimeNN();
			if (overTimeNN != null && overTimeNN.length() > 0 && Float.parseFloat(overTimeNN) > 0) {
				overTimeSalary = overTimeSalary + Float.parseFloat(overTimeNN) * salaryPerHour * 1.8;
				// System.err.println(overTimeSalary + " overTime NN controller " + overTimeNN);
			}
			String overTimeWN = salaryDetail.getOverTimeWN();
			if (overTimeWN != null && overTimeWN.length() > 0 && Float.parseFloat(overTimeWN) > 0) {
				overTimeSalary = overTimeSalary + Float.parseFloat(overTimeWN) * salaryPerHour * 2.3;
				// System.err.println(overTimeSalary + " overTime WN controller " + overTimeWN);
			}
			String overTimeHN = salaryDetail.getOverTimeHN();
			if (overTimeHN != null && overTimeHN.length() > 0 && Float.parseFloat(overTimeHN) > 0) {
				overTimeSalary = overTimeSalary + Float.parseFloat(overTimeHN) * salaryPerHour * 3.3;
				// System.err.println(overTimeSalary + " overTime HN controller " + overTimeHN);
			}
			overTimeSalary = Math.round(overTimeSalary);
			salaryDetail.setOverTimeSalary(String.valueOf(overTimeSalary));

			//tinh so tien nv chi cho bhxh
			if (salaryDetail.getSalaryInsurance() != null && salaryDetail.getSalaryInsurance().length() > 0)
				salaryDetail.setPayedInsurance(String.valueOf(Float.parseFloat(salaryDetail.getSalaryInsurance())
						* Float.parseFloat(salaryDetail.getPercentEmployeePay()) / 100));

			//tinh so tien cty chi cho bhxh
			if (salaryDetail.getSalaryInsurance() != null && salaryDetail.getSalaryInsurance().length() > 0)
				salaryDetail.setcPayedInsur(String.valueOf(Float.parseFloat(salaryDetail.getSalaryInsurance())
						* Float.parseFloat(salaryDetail.getPercentCompanyPay()) / 100));
			
			// update ... lay salary o bang salary info sang bang salary detail lam basic salary
			//-->vi la update nen se ko can
			//if (salaryDetail.getBasicSalary() == null) {
			//	salaryDetail.setBasicSalary(String.valueOf(s));
			//}

			model.addAttribute("salaryPerHour", salaryPerHour);
			model.addAttribute("employeeId", salaryDetail.getEmployeeId());
			salaryDAO.updateSalaryDetail(salaryDetail);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Tính lại lương thành công!");

		} catch (Exception e) {
			log.error(e, e);
		}
		return editSalaryDetailForm(model, salaryDetail);// "updateSalaryDetail";
	}

	@RequestMapping(value = "/salary/prepareSummarySalary", method = RequestMethod.GET)
	public String pepareSummarySalary(Model model, LeaveReport leaveReport) {
		try {
			// System.out.println("PepareSummarySalary 0");
			model.addAttribute("salaryReportForm", leaveReport);

			// get list department
			Map<String, String> departmentMap = this.dataForDepartments();
			model.addAttribute("departmentMap", departmentMap);

			// get list employee id
			Map<String, String> employeeMap = this.employees();
			model.addAttribute("employeeMap", employeeMap);

			model.addAttribute("formTitle", "Tùy chọn phạm vi cần thống kê lương");
			// System.out.println("PepareSummarySalary 1");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "prepareSummarySalary";
	}

	@RequestMapping(value = "/salary/viewPDF", method = RequestMethod.GET)
	public void viewSalaryReportToPDF(HttpServletRequest req, HttpServletResponse res, @RequestParam("fileName") String fileName, Model model) {
		try {
		    byte[] bytes = Files.readAllBytes(new File(fileName).toPath());			
			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			res.setHeader("Content-disposition", "inline; filename=" + fileName);
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/salary/generateSalaryReportToPDF")
	public String generateSalaryReportToPDF(Model model, @RequestParam("month") String month, @RequestParam("year") String year, @RequestParam("dept") String dept) throws Exception {

		String fileName = "Thong ke thong tin chi tiet luong ";
		String title = "Thống kê thông tin chi tiết lương nhân viên ";
		String path = hr.getProperty("REPORT_PATH");
		File dir = new File(path);
		try {						
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String currentDate = dateFormat.format(date);		
			List<SalaryReportPerEmployee> listY = new ArrayList<SalaryReportPerEmployee>();
			List<SalaryDetail> listM = new ArrayList<SalaryDetail>();
			SalaryReport salaryReportS = new SalaryReport();
			if(month  != null && Integer.parseInt(month) > 0) {
				fileName = fileName + "thang " + month + " nam " + year;					
				title = title + "tháng " + month + " năm " + year;
				if(dept != null && !dept.equalsIgnoreCase("all")) {
					fileName = fileName + " cua phong " + dept;					
					title = title + " của phòng " + dataForDepartments().get(dept);
				}
				listM = salaryDAO.getSalaryReportDetail(month, year, dept);
				
				salaryReportS = salaryDAO.getSalaryReport(0, month, year);
				if(listM.size() > 0)
					salaryReportS.setAverageSalary(String.valueOf((Float.valueOf(salaryReportS.getFinalSalary()) + Float.valueOf(salaryReportS.getAdvancePayed()))/listM.size()));	
				
			}else {
				fileName = fileName + "nam " + year;					
				title = title + "năm " + year;
				if(dept != null && !dept.equalsIgnoreCase("all")) {
					fileName = fileName + " cua phong " + dept;					
					title = title + " của phòng " + dataForDepartments().get(dept);
				}
				listY = salaryDAO.getSalaryReportDetail(year, dept);
				//Xu ly tinh luong trung binh
				float averageSalaryTotal = 0;
				float averageSalary = 0;
				for(int i = 0; i < listY.size() ; i++) {
					SalaryReportPerEmployee salaryReport = listY.get(i);
					int empId = salaryReport.getEmployeeId();
					//tinh so thang da tra luong cho tung nv
					int months = salaryDAO.countMonthPerYearByEmp(empId, year);
					
					averageSalary = (Float.valueOf(salaryReport.getFinalSalary()) + Float.valueOf(salaryReport.getAdvancePayed()))/months;						
					salaryReport.setAverageSalary(String.valueOf(averageSalary));			
					
					averageSalaryTotal = averageSalaryTotal + averageSalary;
				}		
				
				salaryReportS = salaryDAO.getSalaryReport(0, month, year);
				//Xu ly tinh luong trung binh
				if(listY.size() > 0)
					salaryReportS.setAverageSalary(String.valueOf(averageSalaryTotal/listY.size()));	
			}			
	
			Document document = new Document(PageSize.A3.rotate());
			if (!dir.exists()) {
				dir.mkdirs();
			}
	
			PdfWriter.getInstance(document, new FileOutputStream(dir + "/" + fileName + ".pdf"));
			BaseFont bf = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font fontB = new Font(bf, 23);
			Font font = new Font(bf, 14);
			document.open();
			PdfPTable table = new PdfPTable(12);			
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100f);
			
			PdfPTable tableM = new PdfPTable(11);
			tableM.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableM.setWidthPercentage(100f);
								
			if(month  != null && Integer.parseInt(month) > 0) {
				addTableHeaderSummaryReportToPDF(tableM, font, month);	
				addSummaryReportToPDFRowsForMonth(tableM, listM);
			}else {
				addTableHeaderSummaryReportToPDF(table, font, null);	
				addSummaryReportToPDFRows(table, listY);
			}
			
			document.add(new Paragraph("                  	                " + title, fontB));
			document.add(new Paragraph("                  ", font));
			document.add(new Paragraph(" - Tổng tiền lương phải chi: " + Utils.customFormat(Float.valueOf(salaryReportS.getFinalSalary()) + Float.valueOf(salaryReportS.getAdvancePayed())), font));
			document.add(new Paragraph(" - Trong đó bao gồm: Thưởng: " + Utils.customFormat(Float.valueOf(salaryReportS.getBounus())) +", trợ cấp/trách nhiệm: " + Utils.customFormat(Float.valueOf(salaryReportS.getSubsidize())) + ", lương ngoài giờ: " + Utils.customFormat(Float.valueOf(salaryReportS.getOverTimeSalary())), font));
			document.add(new Paragraph(" - Đã trừ đi: Tạm ứng: " + Utils.customFormat(Float.valueOf(salaryReportS.getAdvancePayed())) + ", thuế thu nhập cá nhân: " + Utils.customFormat(Float.valueOf(salaryReportS.getTaxPersonal())) + ", phần nhân viên đóng BHXH: " + Utils.customFormat(Float.valueOf(salaryReportS.getPayedInsurance())), font));
			document.add(new Paragraph(" - Thu nhập bình quân của người lao động: " + Utils.customFormat(Float.valueOf(salaryReportS.getAverageSalary())) + " người/tháng", font));
			if(salaryReportS.getcPayedInsur() != null && salaryReportS.getcPayedInsur().trim().length() > 0)
				document.add(new Paragraph(" - Phần công ty đóng BHXH: " + Utils.customFormat(Float.valueOf(salaryReportS.getcPayedInsur())), font));
			else
				document.add(new Paragraph(" - Phần công ty đóng BHXH --> dữ liệu cũ chưa tính cho phần công ty chi trả" , font));
			document.add(new Paragraph("                  ", font));
			document.add(new Paragraph(" (Đơn vị: Việt Nam đồng)", font));
			document.add(new Paragraph("                  ", font));
			if(month  != null && Integer.parseInt(month) > 0) 
				document.add(tableM);
			else
				document.add(table);
			document.add(new Paragraph("                  ", font));
			document.add(new Paragraph(" Ngày lập:  "	+ Utils.convertDateToDisplay(currentDate), font)); 
			document.add(new Paragraph(" Người lập: Vũ Quốc Vinh                                                                                                                                                          Người duyệt: Nguyễn Công Điểm ", font));	
			document.add(new Paragraph("                  ", font));		
			document.add(new Paragraph("                  ", font));
			document.close();
			
			SendReportForm sendForm = new SendReportForm();			
			sendForm.setSubject(title);
			sendForm.setFileName(fileName);
			sendForm.setSendFrom("Phần mềm quản lý tiền lương");
			sendForm.setSendTo(hr.getProperty("emailCT"));
			model.addAttribute("title", title);
			model.addAttribute("fileName", fileName);
			model.addAttribute("sendForm", sendForm);
			model.addAttribute("formTitle", title);	
			model.addAttribute("message",  title + " đã được export thành công. Vui lòng click 'Xem và In' bên trên để xem hoặc in báo cáo");
			model.addAttribute("link", path + fileName + ".pdf");			
			
		} catch (Exception e) {
			model.addAttribute("isOpen", "Yes");
			model.addAttribute("warning", "Vui lòng tắt file " + dir + fileName + " nếu đang mở trước khi export");
			e.printStackTrace();
		}
		
		return "sendSummarySalaryReport";
	}
	
	private void addTableHeaderSummaryReportToPDF(PdfPTable table, Font font, String month) throws Exception {
		PdfPCell header = new PdfPCell();		
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Họ tên", font));
		table.addCell(header);			
		
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Phòng", font));
		table.addCell(header);		

		/*
		 * header.setBackgroundColor(BaseColor.LIGHT_GRAY); header.setPhrase(new
		 * Phrase("Chức danh", font)); table.addCell(header);
		 */
		
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Lương cơ bản", font));
		table.addCell(header);
		
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Lương thực nhận", font));
		table.addCell(header);		

		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Thưởng", font));
		table.addCell(header);
		
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Trợ cấp - trách nhiệm", font));
		table.addCell(header);		

		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Lương ngoài giờ", font));
		table.addCell(header);				
		
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Tạm ứng", font));
		table.addCell(header);
		
		if(month == null) {
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setPhrase(new Phrase("Thu nhập bình quân", font));
			table.addCell(header);
		}
		
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Thuế TNCN", font));
		table.addCell(header);
		
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("NV đóng BHXH", font));
		table.addCell(header);
		
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Cty đóng BHXH", font));
		table.addCell(header);		

	}

	private void addSummaryReportToPDFRows(PdfPTable table, List<SalaryReportPerEmployee> salaryReportPerEmployees) throws DocumentException, IOException {
		BaseFont bf = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 12);
		for (int i = 0; i < salaryReportPerEmployees.size(); i++) {
			SalaryReportPerEmployee salaryReportPerEmployee = new SalaryReportPerEmployee();
			salaryReportPerEmployee = (SalaryReportPerEmployee) salaryReportPerEmployees.get(i);

			table.addCell(new Paragraph(salaryReportPerEmployee.getFullName(), font));
			table.addCell(new Paragraph(salaryReportPerEmployee.getDepartment(), font));
			/* table.addCell(new Paragraph(salaryReportPerEmployee.getJobTitle(), font)); */
			if(salaryReportPerEmployee.getBasicSalary() != null && salaryReportPerEmployee.getBasicSalary().length() > 0)
				table.addCell(new Paragraph(Utils.customFormat(Float.valueOf(salaryReportPerEmployee.getBasicSalary())), font));
			else
				table.addCell(new Paragraph(Utils.customFormat(Float.valueOf(salaryReportPerEmployee.getSalary())), font));
			table.addCell(new Paragraph(Utils.customFormat(Float.valueOf(salaryReportPerEmployee.getFinalSalary()) + Float.valueOf(salaryReportPerEmployee.getAdvancePayed())), font));
			table.addCell(new Paragraph(Utils.customFormat(Float.valueOf(salaryReportPerEmployee.getBounus())), font));
			table.addCell(new Paragraph(Utils.customFormat(Float.valueOf(salaryReportPerEmployee.getSubsidize())), font));
			table.addCell(new Paragraph(Utils.customFormat(Float.valueOf(salaryReportPerEmployee.getOverTimeSalary())), font));
			table.addCell(new Paragraph(Utils.customFormat(Float.valueOf(salaryReportPerEmployee.getAdvancePayed())), font));
			table.addCell(new Paragraph(Utils.customFormat(Float.valueOf(salaryReportPerEmployee.getAverageSalary())), font));
			table.addCell(new Paragraph(Utils.customFormat(Float.valueOf(salaryReportPerEmployee.getTaxPersonal())), font));
			
			if(salaryReportPerEmployee.getPayedInsurance() != null && salaryReportPerEmployee.getPayedInsurance().length() > 0)
				table.addCell(new Paragraph(Utils.customFormat(Float.valueOf(salaryReportPerEmployee.getPayedInsurance())), font));
			else
				table.addCell(new Paragraph("không đóng", font));
			//xu ly cho truong hop du leu cu 
			if(salaryReportPerEmployee.getcPayedInsur() != null && salaryReportPerEmployee.getcPayedInsur().length() > 0)
				table.addCell(new Paragraph(Utils.customFormat(Float.valueOf(salaryReportPerEmployee.getcPayedInsur())), font));
			else
				table.addCell(new Paragraph("dữ liệu cũ chưa tính", font));
		}
	}	
	
	private void addSummaryReportToPDFRowsForMonth(PdfPTable table, List<SalaryDetail> SalaryReportPerEmployees) throws DocumentException, IOException {
		BaseFont bf = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 12);
		for (int i = 0; i < SalaryReportPerEmployees.size(); i++) {
			SalaryDetail salaryReportPerEmployee = new SalaryDetail();
			salaryReportPerEmployee = (SalaryDetail) SalaryReportPerEmployees.get(i);

			table.addCell(new Paragraph(salaryReportPerEmployee.getFullName(), font));
			table.addCell(new Paragraph(salaryReportPerEmployee.getDepartment(), font));
			/* table.addCell(new Paragraph(salaryReportPerEmployee.getJobTitle(), font)); */
			table.addCell(new Paragraph(Utils.customFormat(Float.valueOf(salaryReportPerEmployee.getBasicSalary())), font));
			
			if(salaryReportPerEmployee.getAdvancePayed() != null && salaryReportPerEmployee.getAdvancePayed().trim().length() > 0)
				table.addCell(new Paragraph(Utils.customFormat(Float.valueOf(salaryReportPerEmployee.getFinalSalary()) + Float.valueOf(salaryReportPerEmployee.getAdvancePayed())), font));
			else
				table.addCell(new Paragraph(Utils.customFormat(Float.valueOf(salaryReportPerEmployee.getFinalSalary())), font));
			
			if(salaryReportPerEmployee.getBounus() != null && salaryReportPerEmployee.getBounus().trim().length() > 0)
				table.addCell(new Paragraph(Utils.customFormat(Float.valueOf(salaryReportPerEmployee.getBounus())), font));
			else
				table.addCell(new Paragraph("0", font));
			
			if(salaryReportPerEmployee.getSubsidize() != null && salaryReportPerEmployee.getSubsidize().trim().length() > 0)
				table.addCell(new Paragraph(Utils.customFormat(Float.valueOf(salaryReportPerEmployee.getSubsidize())), font));
			else
				table.addCell(new Paragraph("0", font));
			
			table.addCell(new Paragraph(Utils.customFormat(Float.valueOf(salaryReportPerEmployee.getOverTimeSalary())), font));
			
			if(salaryReportPerEmployee.getAdvancePayed() != null && salaryReportPerEmployee.getAdvancePayed().trim().length() > 0)
				table.addCell(new Paragraph(Utils.customFormat(Float.valueOf(salaryReportPerEmployee.getAdvancePayed())), font));
			else
				table.addCell(new Paragraph("0", font));			
	
			//table.addCell(new Paragraph(Utils.customFormat(Float.valueOf(salaryReportPerEmployee.getAverageSalary())), font));
			
			if(salaryReportPerEmployee.getTaxPersonal() != null && salaryReportPerEmployee.getTaxPersonal().trim().length() > 0)
				table.addCell(new Paragraph(Utils.customFormat(Float.valueOf(salaryReportPerEmployee.getTaxPersonal())), font));
			else
				table.addCell(new Paragraph("0", font));
			
			if(salaryReportPerEmployee.getPayedInsurance() != null && salaryReportPerEmployee.getPayedInsurance().length() > 0)
				table.addCell(new Paragraph(Utils.customFormat(Float.valueOf(salaryReportPerEmployee.getPayedInsurance())), font));
			else
				table.addCell(new Paragraph("không đóng", font));
			//xu ly cho truong hop du leu cu 
			if(salaryReportPerEmployee.getcPayedInsur() != null && salaryReportPerEmployee.getcPayedInsur().length() > 0)
				table.addCell(new Paragraph(Utils.customFormat(Float.valueOf(salaryReportPerEmployee.getcPayedInsur())), font));
			else
				table.addCell(new Paragraph("dữ liệu cũ chưa tính", font));
		
		}
	}
	
	@RequestMapping(value = "/salary/generateSalaryReport", method = RequestMethod.POST)
	public String generateSalaryReport(Model model, HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute("salaryReportForm") @Validated LeaveReport leaveReport,
			@ModelAttribute("salaryForm") SalaryForm form, final RedirectAttributes redirectAttributes)
			throws Exception {
		try {
			Map<String, String> employeeMap = this.employees();
			String name = "";
			
			if (leaveReport.getMonthReport() == null || Integer.parseInt(leaveReport.getMonthReport()) == 0) {
				if (leaveReport.getEmployeeId() > 0) {
					String id = String.valueOf(leaveReport.getEmployeeId());
					name = employeeMap.get(id);
					model.addAttribute("formTitle",
							"Thông tin thống kê lương của " + name + " năm " + leaveReport.getYearReport());
					SalaryReport salaryReport = new SalaryReport();
					salaryReport = salaryDAO.getSalaryReport(leaveReport.getEmployeeId(), leaveReport.getMonthReport(),
							leaveReport.getYearReport());
					model.addAttribute("salaryReport", salaryReport);
					return "summarySalaryReport";
				} else {
					List<SalaryReportPerEmployee> list = salaryDAO.getSalaryReportDetail(leaveReport.getYearReport(), leaveReport.getDepartment());
					model.addAttribute("formTitle",	"Thông tin thống kê lương nhân viên năm " + leaveReport.getYearReport());
					
					//Xu ly tinh luong trung binh
					float averageSalaryTotal = 0;
					float averageSalary = 0;
					for(int i = 0; i < list.size() ; i++) {
						SalaryReportPerEmployee salaryReport = list.get(i);
						int empId = salaryReport.getEmployeeId();
						//tinh so thang da tra luong cho tung nv
						int months = salaryDAO.countMonthPerYearByEmp(empId, leaveReport.getYearReport());
						averageSalary = (Float.valueOf(salaryReport.getFinalSalary()) + Float.valueOf(salaryReport.getAdvancePayed()))/months;						
						salaryReport.setAverageSalary(String.valueOf(averageSalary));			
						
						averageSalaryTotal = averageSalaryTotal + averageSalary;
					}

					if (form.getNumberRecordsOfPage() == 0) {
						form.setNumberRecordsOfPage(25);
					}
					if (form.getPageIndex() == 0) {
						form.setPageIndex(1);
					}

					form.setTotalRecords(list.size());

					int totalPages = form.getTotalRecords() % form.getNumberRecordsOfPage() > 0
							? form.getTotalRecords() / form.getNumberRecordsOfPage() + 1
							: form.getTotalRecords() / form.getNumberRecordsOfPage();
					form.setTotalPages(totalPages);

					List<SalaryReportPerEmployee> listSalaryForPage = new ArrayList<SalaryReportPerEmployee>();

					if (form.getPageIndex() < totalPages) {
						if (form.getPageIndex() == 1) {
							for (int i = 0; i < form.getNumberRecordsOfPage(); i++) {
								SalaryReportPerEmployee salary = new SalaryReportPerEmployee();
								salary = list.get(i);
								listSalaryForPage.add(salary);
							}
						} else if (form.getPageIndex() > 1) {
							for (int i = ((form.getPageIndex() - 1) * form.getNumberRecordsOfPage()); i < form
									.getPageIndex() * form.getNumberRecordsOfPage(); i++) {
								SalaryReportPerEmployee salary = new SalaryReportPerEmployee();
								salary = list.get(i);
								listSalaryForPage.add(salary);
							}
						}
					} else if (form.getPageIndex() == totalPages) {
						for (int i = ((form.getPageIndex() - 1) * form.getNumberRecordsOfPage()); i < form
								.getTotalRecords(); i++) {
							SalaryReportPerEmployee salary = new SalaryReportPerEmployee();
							salary = list.get(i);
							listSalaryForPage.add(salary);
						}
					}
					// form.setYearReport(Integer.parseInt(leaveReport.getYearReport()));
					model.addAttribute("salaryForm", form);
					model.addAttribute("salaryDetails", listSalaryForPage);
					if(leaveReport.getDepartment() != null && !leaveReport.getDepartment().equalsIgnoreCase("all"))
						model.addAttribute("formTitle",	"Thông tin thống kê lương nhân viên tổng cả năm " + leaveReport.getYearReport() + " của " + dataForDepartments().get(leaveReport.getDepartment()));
					else
						model.addAttribute("formTitle",	"Thông tin thống kê lương nhân viên tổng cả năm " + leaveReport.getYearReport());
					// model.addAttribute("listSalaryReportDetail", listSalaryReportDetail);
					SalaryReport salaryReport = salaryDAO.getSalaryReport(leaveReport.getEmployeeId(), leaveReport.getMonthReport(), leaveReport.getYearReport());
					//Xu ly tinh luong trung binh
					if(list.size() > 0)
						salaryReport.setAverageSalary(String.valueOf(averageSalaryTotal/list.size()));					
					
					model.addAttribute("salaryReport", salaryReport);
					model.addAttribute("listSalaryReport", list);
					model.addAttribute("monthReport", leaveReport.getMonthReport());
					model.addAttribute("yearReport", leaveReport.getYearReport());
					model.addAttribute("dept", leaveReport.getDepartment());
					return "listSalarySumaryDetail";
				}
			} else {
				if (leaveReport.getEmployeeId() > 0) {
					String id = String.valueOf(leaveReport.getEmployeeId());
					name = employeeMap.get(id);
					model.addAttribute("formTitle", "Thông tin thống kê lương của " + name + " tháng "
							+ leaveReport.getMonthReport() + ", năm " + leaveReport.getYearReport());
					SalaryReport salaryReport = new SalaryReport();
					salaryReport = salaryDAO.getSalaryReport(leaveReport.getEmployeeId(), leaveReport.getMonthReport(),
							leaveReport.getYearReport());
					model.addAttribute("salaryReport", salaryReport);

					return "summarySalaryReport";
				} else {
					List<SalaryDetail> list = salaryDAO.getSalaryReportDetail(leaveReport.getMonthReport(),
							leaveReport.getYearReport(), leaveReport.getDepartment());

					if (form.getNumberRecordsOfPage() == 0) {
						form.setNumberRecordsOfPage(25);
					}
					if (form.getPageIndex() == 0) {
						form.setPageIndex(1);
					}

					form.setTotalRecords(list.size());

					int totalPages = form.getTotalRecords() % form.getNumberRecordsOfPage() > 0
							? form.getTotalRecords() / form.getNumberRecordsOfPage() + 1
							: form.getTotalRecords() / form.getNumberRecordsOfPage();
					form.setTotalPages(totalPages);

					List<SalaryDetail> listSalaryForPage = new ArrayList<SalaryDetail>();

					if (form.getPageIndex() < totalPages) {
						if (form.getPageIndex() == 1) {
							for (int i = 0; i < form.getNumberRecordsOfPage(); i++) {
								SalaryDetail salary = new SalaryDetail();
								salary = list.get(i);
								listSalaryForPage.add(salary);
							}
						} else if (form.getPageIndex() > 1) {
							for (int i = ((form.getPageIndex() - 1) * form.getNumberRecordsOfPage()); i < form
									.getPageIndex() * form.getNumberRecordsOfPage(); i++) {
								SalaryDetail salary = new SalaryDetail();
								salary = list.get(i);
								listSalaryForPage.add(salary);
							}
						}
					} else if (form.getPageIndex() == totalPages) {
						for (int i = ((form.getPageIndex() - 1) * form.getNumberRecordsOfPage()); i < form
								.getTotalRecords(); i++) {
							SalaryDetail salary = new SalaryDetail();
							salary = list.get(i);
							listSalaryForPage.add(salary);
						}
					}
					SalaryReport salaryReport = salaryDAO.getSalaryReport(leaveReport.getEmployeeId(), leaveReport.getMonthReport(), leaveReport.getYearReport());
					if(list.size() > 0)
						salaryReport.setAverageSalary(String.valueOf((Float.valueOf(salaryReport.getFinalSalary()) + Float.valueOf(salaryReport.getAdvancePayed()))/list.size()));		
										
					model.addAttribute("salaryReport", salaryReport);
					model.addAttribute("salaryForm", form);
					model.addAttribute("salaryDetails", listSalaryForPage);
					if(leaveReport.getDepartment() != null && !leaveReport.getDepartment().equalsIgnoreCase("all"))
						model.addAttribute("formTitle", "Thông tin thống kê lương nhân viên tháng "	+ leaveReport.getMonthReport() + ", năm " + leaveReport.getYearReport() + " của " + dataForDepartments().get(leaveReport.getDepartment()));
					else
						model.addAttribute("formTitle", "Thông tin thống kê lương nhân viên tháng "	+ leaveReport.getMonthReport() + ", năm " + leaveReport.getYearReport());
					model.addAttribute("dept", leaveReport.getDepartment());
					model.addAttribute("monthReport", leaveReport.getMonthReport());
					model.addAttribute("yearReport", leaveReport.getYearReport());
					model.addAttribute("listSalaryReport", list);
					return "listSalarySumaryDetail";
				}
			}
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listSalarySumaryDetail";
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

	// For Ajax	
	@RequestMapping("/salary/selection")
	public @ResponseBody List<EmployeeInfo> employeesByDepartment(@RequestParam("department") String department) {
		List<EmployeeInfo> list = null;
		if (!department.equalsIgnoreCase("all"))
			list = employeeDAO.getEmployeesByDepartment(department);
		else
			list = employeeDAO.getEmployees();

		return list;
	}	 
}
