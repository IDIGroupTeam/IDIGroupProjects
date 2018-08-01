package com.idi.hr.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.idi.hr.bean.Department;
import com.idi.hr.bean.EmployeeInfo;
import com.idi.hr.bean.JobTitle;
import com.idi.hr.common.Utils;
import com.idi.hr.dao.DepartmentDAO;
import com.idi.hr.dao.EmployeeDAO;
import com.idi.hr.dao.JobTitleDAO;
import com.idi.hr.form.EmployeeForm;
import com.idi.hr.validator.EmployeeValidator;

@Controller
public class EmployeeController {

	private static final Logger log = Logger.getLogger(EmployeeController.class.getName());

	@Autowired
	private EmployeeDAO employeeDAO;

	@Autowired
	private JobTitleDAO jobTitleDAO;

	@Autowired
	private DepartmentDAO departmentDAO;

	@Autowired
	private EmployeeValidator employeeValidator;

	@RequestMapping(value = { "/" })
	public String listEmployees(Model model, @ModelAttribute("employeeForm") EmployeeForm form) throws Exception {
		try {
			
			List<EmployeeInfo> list = null;

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
			
			boolean search = false;
			if (form.getSearchValue() != null && form.getSearchValue().length() > 0) {
				log.info("Searching for: " + form.getSearchValue());
				search = true;
				list = employeeDAO.getEmployeesBySearch(form.getSearchValue());
			} else
				list = employeeDAO.getAllEmployees();
			form.setTotalRecords(list.size());
			
			int totalPages = form.getTotalRecords() % form.getNumberRecordsOfPage() > 0
					? form.getTotalRecords() / form.getNumberRecordsOfPage() + 1
					: form.getTotalRecords() / form.getNumberRecordsOfPage();
			form.setTotalPages(totalPages);

			List<EmployeeInfo> listEmployeesForPage = new ArrayList<EmployeeInfo>();
			
			if (form.getPageIndex() < totalPages) {
				if (form.getPageIndex() == 1) {
					for (int i = 0; i < form.getNumberRecordsOfPage(); i++) {
						EmployeeInfo employee = new EmployeeInfo();
						employee = list.get(i);
						listEmployeesForPage.add(employee);
					}
				} else if (form.getPageIndex() > 1) {
					for (int i = ((form.getPageIndex() - 1) * form.getNumberRecordsOfPage()); i < form.getPageIndex()
							* form.getNumberRecordsOfPage(); i++) {
						EmployeeInfo employee = new EmployeeInfo();
						employee = list.get(i);
						listEmployeesForPage.add(employee);
					}
				}
			} else if (form.getPageIndex() == totalPages) {
				for (int i = ((form.getPageIndex() - 1) * form.getNumberRecordsOfPage()); i < form
						.getTotalRecords(); i++) {
					EmployeeInfo employee = new EmployeeInfo();
					employee = list.get(i);
					listEmployeesForPage.add(employee);
				}
			}
			
			if (list != null && list.size() < 1 && !search)
				model.addAttribute("message", "Chưa có thông tin nhân viên nào");
			else if (list != null && list.size() < 1 && search)
				model.addAttribute("message",
						"Không có nhân viên nào khớp với thông tin: '" + form.getSearchValue() + "'");

			model.addAttribute("employees", listEmployeesForPage);			
			
			Date date = new Date();// your date
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);		
			int currentQuarter = cal.get(Calendar.MONTH)/3 + 1;
			model.addAttribute("employeeForm", form);
			model.addAttribute("search", search);
			model.addAttribute("quarter", currentQuarter);
			model.addAttribute("formTitle", "Danh sách nhân viên");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listEmployee";
	}

/*	@RequestMapping(value = "/listEmployeeSearch", method = RequestMethod.GET)
	public String listEmployeeSearch(Model model, @RequestParam("searchValue") String searchValue) throws Exception{
		try {			
			Date date = new Date();// your date
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);		
			int currentQuarter = cal.get(Calendar.MONTH)/3 + 1;
			model.addAttribute("quarter", currentQuarter);
			
			EmployeeForm employeeForm = new EmployeeForm();
			model.addAttribute("employeeForm", employeeForm);
			List<EmployeeInfo> list = employeeDAO.getEmployeesBySearch(searchValue);
			if (list.size() < 1)
				model.addAttribute("message", "Không có nhân viên nào khớp với thông tin: '" + searchValue + "'");
			model.addAttribute("employees", list);
			model.addAttribute("formTitle", "Kết quả tìm kiếm nhân viên");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listEmployee";
	}*/
	
	@RequestMapping(value = "/listEmployeeBirth", method = RequestMethod.GET)
	public String listEmployeeBirth(Model model, @RequestParam("quarter") int quarter,
			@ModelAttribute("bithForm") EmployeeForm bith, final RedirectAttributes redirectAttributes) throws Exception{
		try {
			System.err.println(bith.getQuarter());
			List<EmployeeInfo> list = employeeDAO.getEmployeesBirth(quarter);
			if (list.size() < 1)
				redirectAttributes.addFlashAttribute("message", "Không có nhân viên nào SN quý " + bith.getQuarter());
			List<EmployeeInfo> listEmployee = new ArrayList<EmployeeInfo>();
			for (int i = 0; i < list.size(); i++) {
				EmployeeInfo employee = new EmployeeInfo();
				employee = list.get(i);

				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				String currentDate = dateFormat.format(date);
				String joinDate = employee.getJoinDate();
				int seniority = Utils.monthsBetween(dateFormat.parse(joinDate), dateFormat.parse(currentDate));
				employee.setSeniority(seniority);
				/* System.err.println("tham nien " + seniority); */
				listEmployee.add(employee);
			}
			model.addAttribute("employees", listEmployee);
			model.addAttribute("formTitle", "Danh sách nhân viên sinh nhật quý " + bith.getQuarter());
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listEmployeeBirth";
	}

	@RequestMapping(value = "/workStatusReport", method = RequestMethod.GET)
	public String workStatusReport(Model model, HttpServletResponse response,  HttpServletRequest request) throws Exception {
		Map<String, String> items = employeeDAO.getWorkStatusMap();
		Map<String, Integer> memberWorkStatus = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, String> entry : items.entrySet()) {
			System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
			memberWorkStatus.put(entry.getValue(), employeeDAO.countMemberByWorkStatus(entry.getKey(), "all"));
		}
		model.addAttribute("formTitle", "Thống kê số lượng nhân viên theo trạng thái lao động");
		model.addAttribute("memberWorkStatus", memberWorkStatus);
		
		
		//drawPieChart
		response.setContentType("image/png");
		//Map<String, String> items = workStatusMap();
		DefaultPieDataset dpd = new DefaultPieDataset();
		for (Map.Entry<String, String> entry : items.entrySet()) {
			System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
			dpd.setValue(entry.getValue() + ":" + employeeDAO.countMemberByWorkStatus(entry.getKey(),"all"), employeeDAO.countMemberByWorkStatus(entry.getKey(), "all"));
		}
		JFreeChart chart = createChart(dpd, "Biểu đồ trạng thái LĐ");

		try {
			
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			File dir = new File(rootPath + "charts/");
			if (!dir.exists()) {
				dir.mkdirs();
			}

			File file = new File(dir + "/workStatusChart.png");
			model.addAttribute("chart", "/charts/workStatusChart.png");
			//System.err.println(dir);
			ChartUtilities.saveChartAsJPEG(file, chart, 750, 400);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return "reportEmployeeByWorkStatus";
	}

/*	@RequestMapping(value = "/pieChart", method = RequestMethod.GET)
	public void drawPieChart(HttpServletResponse response,  HttpServletRequest request) {
		response.setContentType("image/png");
		Map<String, String> items = workStatusMap();
		DefaultPieDataset dpd = new DefaultPieDataset();
		for (Map.Entry<String, String> entry : items.entrySet()) {
			System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
			dpd.setValue(entry.getValue(), employeeDAO.countMemberByWorkStatus(entry.getKey()));
		}
		JFreeChart chart = createChart(dpd, "Biểu đồ trạng thái LĐ");

		try {
			
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			File dir = new File(rootPath + "charts/");
			if (!dir.exists()) {
				dir.mkdirs();
			}

			File file = new File(dir + "/workStartChart.png");
			System.err.println(dir);
			ChartUtilities.saveChartAsJPEG(file, chart, 650, 350);
			
			ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart, 650, 350);
			response.getOutputStream().close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}*/

	private JFreeChart createChart(PieDataset pdSet, String chartTitle)  throws Exception {

		JFreeChart chart = ChartFactory.createPieChart3D(chartTitle, pdSet, true, true, false);
		PiePlot3D plot = (PiePlot3D)chart.getPlot();
		PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator("{0} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
		plot.setLabelGenerator(generator);
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);

		return chart;
	}

	// Set a form validator
	@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {

		// Form mục tiêu
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		// System.out.println("Target=" + target);

		if (target.getClass() == EmployeeInfo.class) {
			dataBinder.setValidator(employeeValidator);
		}
	}

	@RequestMapping(value = "/insertOrUpdateEmployee", method = RequestMethod.POST)
	public String insertOrUpdateEmployee(Model model, HttpServletRequest request, @RequestParam MultipartFile image,
			@ModelAttribute("employeeForm") @Validated EmployeeInfo employeeInfo, BindingResult result,
			final RedirectAttributes redirectAttributes) throws Exception {
		try {
			// Nếu validate có lỗi.
			if (!(employeeInfo.getEmployeeId() > 0)) {
				if (result.hasErrors()) {
					// System.err.println("co loi validate");
					return this.employeeForm(model, employeeInfo);
				}
			}
			String pathInfo = "";

			String rootPath = request.getSession().getServletContext().getRealPath("/");
			// System.err.println("rootPath: " + rootPath);
			File dir = new File(rootPath + File.separator + "employeeImage");
			if (!dir.exists()) {
				dir.mkdirs();
			}

			File serverFile = new File(dir.getAbsolutePath() + File.separator + image.getOriginalFilename());
			// latestUploadPhoto = image.getOriginalFilename();
			pathInfo = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
					+ "/employeeImage/" + image.getOriginalFilename();
			if (!image.isEmpty()) {
				// write uploaded image to disk
				// System.err.println("saving image file");
				try {
					try (InputStream is = image.getInputStream();
							BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
						int i;
						while ((i = is.read()) != -1) {
							stream.write(i);
						}
						stream.flush();

					}
					employeeInfo.setImagePath(pathInfo);
				} catch (IOException e) {
					log.error("Error : " + e.getMessage());
				}
			}
			employeeDAO.insertOrUpdateEmployee(employeeInfo);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Thêm mới/cập nhật thông tin nhân viên thành công!");
		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/";
	}

	/**
	 * 
	 * @param model
	 * @param employeeInfo
	 * @return
	 */
	private String employeeForm(Model model, EmployeeInfo employeeInfo) throws Exception {
		model.addAttribute("employeeForm", employeeInfo);

		// get list title
		Map<String, String> titleMap = this.dataForTitles();
		model.addAttribute("titleMap", titleMap);
		// get list department
		Map<String, String> departmentMap = this.dataForDepartments();
		model.addAttribute("departmentMap", departmentMap);
		// get works status
		Map<String, String> workStatusMap = employeeDAO.getWorkStatusMap();
		model.addAttribute("workStatusMap", workStatusMap);

		if (employeeInfo.getEmployeeId() > 0) {
			model.addAttribute("formTitle", "Sửa thông tin nhân viên");
		} else {
			model.addAttribute("formTitle", "Thêm mới nhân viên");
		}
		return "formEmployee";
	}

	@RequestMapping(value = { "/insertEmployee" })
	public String insertEmployee(Model model) throws Exception {
		EmployeeInfo employeeInfo = new EmployeeInfo();
		return this.employeeForm(model, employeeInfo);
	}

	private Map<String, String> dataForTitles() throws Exception {
		Map<String, String> titleMap = new LinkedHashMap<String, String>();
		try {
			List<JobTitle> list = jobTitleDAO.getJobTitles();
			JobTitle jobTitle = new JobTitle();
			for (int i = 0; i < list.size(); i++) {
				jobTitle = (JobTitle) list.get(i);
				titleMap.put(jobTitle.getTitleId(), jobTitle.getTitleName());
			}

		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return titleMap;
	}

	private Map<String, String> dataForDepartments() throws Exception {
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

	@RequestMapping("/editEmployee")
	public String editEmployee(Model model, @RequestParam("employeeId") String employeeId) throws Exception {
		EmployeeInfo employeeInfo = null;
		if (employeeId != null) {
			employeeInfo = this.employeeDAO.getEmployee(employeeId);
		}
		if (employeeInfo == null) {
			return "redirect:/";
		}

		return this.employeeForm(model, employeeInfo);
	}

	@RequestMapping("/viewEmployee")
	public String viewEmployee(Model model, @RequestParam("employeeId") String employeeId) throws Exception {
		EmployeeInfo employeeInfo = null;
		if (employeeId != null) {
			employeeInfo = this.employeeDAO.getEmployee(employeeId);
			// System.err.println(employeeInfo.getImagePath());
			model.addAttribute("employeeForm", employeeInfo);
			model.addAttribute("formTitle", "Thông tin nhân viên");

		}
		if (employeeInfo == null) {
			return "redirect:/";
		}

		return "viewEmployee";
	}

}
