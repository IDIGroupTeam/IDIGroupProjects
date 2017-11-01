package com.idi.hr.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.idi.hr.bean.Department;
import com.idi.hr.bean.EmployeeInfo;
import com.idi.hr.bean.JobTitle;
import com.idi.hr.bean.Timekeeping;
import com.idi.hr.bean.WorkHistory;
import com.idi.hr.dao.DepartmentDAO;
import com.idi.hr.dao.EmployeeDAO;
import com.idi.hr.dao.JobTitleDAO;
import com.idi.hr.dao.TimekeepingDAO;
import com.idi.hr.dao.WorkHistoryDAO;

@Controller
public class TimekeepingController {
	private static final Logger log = Logger.getLogger(TimekeepingController.class.getName());

	@Autowired
	private TimekeepingDAO timekeepingDAO;

	@Autowired
	private EmployeeDAO employeeDAO;
	
	@Autowired
	private JobTitleDAO jobTitleDAO;

	@Autowired
	private DepartmentDAO departmentDAO;

	
	@RequestMapping(value = { "/timekeeping/" }, method = RequestMethod.GET)
	public String listForTimekeeping(Model model, Timekeeping timekeeping) {
		try {
			System.out.println("list by employee");
			List<Timekeeping> list = timekeepingDAO.getTimekeepings();
			model.addAttribute("timekeepings", list);
			model.addAttribute("formTitle", "Danh sách chấm công");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return this.timekeepingForm(model, timekeeping);
	}
	/*
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
	
	@RequestMapping(value = "/workHistory/insertWorkHistory", method = RequestMethod.POST)
	public String insertWorkHistory(Model model, @ModelAttribute("workHistoryForm") @Validated WorkHistory workHistory,
			final RedirectAttributes redirectAttributes) {
		try {
			workHistoryDAO.insertWorkHistory(workHistory);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Thêm thông tin lịch sử công tác thành công!");

		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/workHistory/";
	}

	@RequestMapping(value = "/workHistory/updateWorkHistory", method = RequestMethod.POST)
	public String updateWorkHistory(Model model, @ModelAttribute("workHistoryForm") @Validated WorkHistory workHistory,
			final RedirectAttributes redirectAttributes) {
		try {
			workHistoryDAO.updateWorkHistory(workHistory);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin lịch sử công tác thành công!");

		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/workHistory/";
	}
*/
	private String timekeepingForm(Model model,  Timekeeping timekeeping) {
		model.addAttribute("timekeepingForm", timekeeping);
		// get list employee id
		model.addAttribute("formTitle", "Chấm công ");
//		actionform = "insertTimekeeping";
	//	}
		return "formTimekeeping";
	}
/*
	@RequestMapping("/timekeeping/addTimekeeping")
	public String addWorkHistory(Model model) {		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String date = simpleDateFormat.format(new Date());
		System.out.println(date);
		Timekeeping timekeeping = new Timekeeping();
		return this.timekeepingForm(model, timekeeping);
	}

	@RequestMapping("/workHistory/viewWorkHistory")
	public String viewWorkHistory(Model model, @RequestParam("employeeId") int employeeId,  @RequestParam("fromDate") String fromDate) {
		WorkHistory workHistory = null;
		if (employeeId > 0 && fromDate != null) {
			workHistory = this.workHistoryDAO.getWorkHistory(employeeId, fromDate);
			model.addAttribute("workHistoryForm", workHistory);
			model.addAttribute("formTitle", "Thông tin lịch sử công tác");
		}
		if (workHistory == null) {
			return "redirect:/workHistory/";
		}

		return "viewWorkHistory";
	}

	@RequestMapping("/workHistory/editWorkHistory")
	public String editWorkHistory(Model model, @RequestParam("employeeId") int employeeId,  @RequestParam("fromDate") String fromDate) {
		Timekeeping timekeeping = null;
		if (employeeId > 0 && fromDate != null) {
			timekeeping = null; //this.workHistoryDAO.getWorkHistory(employeeId, fromDate);
		}
		if (timekeeping == null) {
			return "redirect:/workHistory/";
		}

		return this.timekeepingForm(model, timekeeping);
	}

	
	@RequestMapping(value = "/workHistory/deleteWorkHistory")
	public String deleteWorkHistory(Model model, @RequestParam("employeeId") int employeeId,  @RequestParam("fromDate") String fromDate,
			final RedirectAttributes redirectAttributes) {
		try {
			workHistoryDAO.deleteWorkHistory(employeeId, fromDate);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Xóa thông tin lịch sử công tác thành công!");
		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/workHistory/";
	}*/

}
