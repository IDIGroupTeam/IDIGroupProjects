package com.idi.hr.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

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
import com.idi.hr.dao.DepartmentDAO;
import com.idi.hr.dao.EmployeeDAO;
import com.idi.hr.dao.JobTitleDAO;
import com.idi.hr.validator.EmployeeValidator;

@Controller
public class EmployeeController {// extends BaseController {

	private static final Logger log = Logger.getLogger(EmployeeController.class.getName());

	@Autowired
	private EmployeeDAO employeeDAO;

	@Autowired
	private JobTitleDAO jobTitleDAO;

	@Autowired
	private DepartmentDAO departmentDAO;

	@Autowired
	private EmployeeValidator employeeValidator;

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String ListEmployees(Model model) {
		try {
			List<EmployeeInfo> list = employeeDAO.getEmployees();
			model.addAttribute("employees", list);
			model.addAttribute("formTitle", "Danh sách nhân viên");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listEmployee";
	}

	// Set a form validator
	@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {

		// Form mục tiêu
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		//System.out.println("Target=" + target);

		if (target.getClass() == EmployeeInfo.class) {
			dataBinder.setValidator(employeeValidator);
		}
	}

	@RequestMapping(value = "/insertOrUpdateEmployee", method = RequestMethod.POST)
	public String insertOrUpdateEmployee(Model model, HttpServletRequest request, @RequestParam MultipartFile image,
			@ModelAttribute("employeeForm") @Validated EmployeeInfo employeeInfo, BindingResult result,
			final RedirectAttributes redirectAttributes) {
		try {
			// Nếu validate có lỗi.
			if (!(employeeInfo.getEmployeeId() > 0)) {
				if (result.hasErrors()) {
					//System.err.println("co loi validate");
					return this.employeeForm(model, employeeInfo);
				}
			}
			String pathInfo = "";

			String rootPath = request.getSession().getServletContext().getRealPath("/");
			//System.err.println("rootPath: " + rootPath);
			File dir = new File(rootPath + File.separator + "employeeImage");
			if (!dir.exists()) {
				dir.mkdirs();
			}

			File serverFile = new File(dir.getAbsolutePath() + File.separator + image.getOriginalFilename());
			// latestUploadPhoto = image.getOriginalFilename();
			pathInfo = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
					+ "/employeeImage/" + image.getOriginalFilename();
			if(!image.isEmpty()) {
				// write uploaded image to disk
				//System.err.println("saving image file");
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
			redirectAttributes.addFlashAttribute("message", "Insert/Update employee successful!");
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
	private String employeeForm(Model model, EmployeeInfo employeeInfo) {
		model.addAttribute("employeeForm", employeeInfo);

		// get list title
		Map<String, String> titleMap = this.dataForTitles();
		model.addAttribute("titleMap", titleMap);
		// get list department
		Map<String, String> departmentMap = this.dataForDepartments();
		model.addAttribute("departmentMap", departmentMap);
		// get works status
		Map<String, String> workStatusMap = this.workStatusMap();
		model.addAttribute("workStatusMap", workStatusMap);

		if (employeeInfo.getEmployeeId() > 0) {
			model.addAttribute("formTitle", "Sửa thông tin nhân viên");
		} else {
			model.addAttribute("formTitle", "Thêm mới nhân viên");
		}
		return "formEmployee";
	}

	@RequestMapping(value = { "/insertEmployee" })
	public String insertEmployee(Model model) {
		EmployeeInfo employeeInfo = new EmployeeInfo();
		return this.employeeForm(model, employeeInfo);
	}

	private Map<String, String> dataForTitles() {
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

	@RequestMapping("/editEmployee")
	public String editEmployee(Model model, @RequestParam("employeeId") String employeeId) {
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
	public String viewEmployee(Model model, @RequestParam("employeeId") String employeeId) {
		EmployeeInfo employeeInfo = null;
		if (employeeId != null) {
			employeeInfo = this.employeeDAO.getEmployee(employeeId);
			//System.err.println(employeeInfo.getImagePath());
			model.addAttribute("employeeForm", employeeInfo);
			model.addAttribute("formTitle", "Thông tin nhân viên");

		}
		if (employeeInfo == null) {
			return "redirect:/";
		}

		return "viewEmployee";
	}

	private Map<String, String> workStatusMap() {
		Map<String, String> workStatusMap = new LinkedHashMap<String, String>();
		workStatusMap.put("Thu viec", "Thử việc");
		workStatusMap.put("Thoi vu", "Thời vụ");
		workStatusMap.put("Cong tac vien", "Cộng tác viên");
		workStatusMap.put("Chinh thuc", "Chính thức");
		workStatusMap.put("Nghi thai san", "Nghỉ thai sản");
		workStatusMap.put("Nghi om", "Nghỉ ốm");
		workStatusMap.put("Nghi khong luong", "Nghỉ không lương");
		workStatusMap.put("Da thoi viec", "Đã thôi việc");
		return workStatusMap;
	}
}
