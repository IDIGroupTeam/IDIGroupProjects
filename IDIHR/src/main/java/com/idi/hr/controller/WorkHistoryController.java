package com.idi.hr.controller;

import java.util.ArrayList;
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
import com.idi.hr.bean.WorkHistory;
import com.idi.hr.dao.DepartmentDAO;
import com.idi.hr.dao.EmployeeDAO;
import com.idi.hr.dao.JobTitleDAO;
import com.idi.hr.dao.WorkHistoryDAO;
import com.idi.hr.form.WorkHistoryForm;

@Controller
public class WorkHistoryController {
	private static final Logger log = Logger.getLogger(WorkHistoryController.class.getName());

	@Autowired
	private WorkHistoryDAO workHistoryDAO;

	@Autowired
	private EmployeeDAO employeeDAO;
	
	@Autowired
	private JobTitleDAO jobTitleDAO;

	@Autowired
	private DepartmentDAO departmentDAO;

	@RequestMapping(value = {"/workHistory/"})
	public String ListWorkHistorys(Model model, @ModelAttribute("workHistoryForm") WorkHistoryForm form) {
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
			
			List<WorkHistory> list = workHistoryDAO.getWorkHistorys();
			
			form.setTotalRecords(list.size());
			
			int totalPages = form.getTotalRecords() % form.getNumberRecordsOfPage() > 0
					? form.getTotalRecords() / form.getNumberRecordsOfPage() + 1
					: form.getTotalRecords() / form.getNumberRecordsOfPage();
			form.setTotalPages(totalPages);

			List<WorkHistory> listWorkHistoryForPage = new ArrayList<WorkHistory>();
			
			if (form.getPageIndex() < totalPages) {
				if (form.getPageIndex() == 1) {
					for (int i = 0; i < form.getNumberRecordsOfPage(); i++) {
						WorkHistory workHistory = new WorkHistory();
						workHistory = list.get(i);
						listWorkHistoryForPage.add(workHistory);
					}
				} else if (form.getPageIndex() > 1) {
					for (int i = ((form.getPageIndex() - 1) * form.getNumberRecordsOfPage()); i < form.getPageIndex()
							* form.getNumberRecordsOfPage(); i++) {
						WorkHistory workHistory = new WorkHistory();
						workHistory = list.get(i);
						listWorkHistoryForPage.add(workHistory);
					}
				}
			} else if (form.getPageIndex() == totalPages) {
				for (int i = ((form.getPageIndex() - 1) * form.getNumberRecordsOfPage()); i < form
						.getTotalRecords(); i++) {
					WorkHistory workHistory = new WorkHistory();
					workHistory = list.get(i);
					listWorkHistoryForPage.add(workHistory);
				}
			}
			
			model.addAttribute("workHistorys", listWorkHistoryForPage);
			model.addAttribute("formTitle", "Danh sách lịch sử công tác ");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listWorkHistory";
	}
	
/*	@RequestMapping(value = { "/workHistory/workHistoryByEmployee" }, method = RequestMethod.GET)
	public String listWorkHistorysByEmployee(Model model, @RequestParam("employeeId") int employeeId) {
		try {
			System.out.println("list by employee");
			List<WorkHistory> list = workHistoryDAO.getWorkHistorys(employeeId);
			model.addAttribute("workHistorys", list);
			model.addAttribute("formTitle", "Danh sách lịch sử công tác của MNV: " + employeeId);
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listWorkHistory";
	}*/

	private Map<String, String> employees() {
		Map<String, String> employeeMap = new LinkedHashMap<String, String>();
		try {
			List<EmployeeInfo> list = employeeDAO.getEmployees();
			EmployeeInfo employee = new EmployeeInfo();
			for (int i = 0; i < list.size(); i++) {
				employee = (EmployeeInfo) list.get(i);
				Integer id = employee.getEmployeeId();
				employeeMap.put(id.toString(),
						employee.getFullName() + ", phòng " + employee.getDepartment());
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
			System.err.println(workHistory.getAchievement());
			System.err.println(workHistory.getAppraise());
			workHistoryDAO.updateWorkHistory(workHistory);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin lịch sử công tác thành công!");

		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/workHistory/";
	}

	private String workHistoryForm(Model model,  WorkHistory workHistory) {
		model.addAttribute("workHistoryForm", workHistory);
		// get list employee id
		Map<String, String> employeeMap = this.employees();
		model.addAttribute("employeeMap", employeeMap);
		// get list title
		Map<String, String> titleMap = this.dataForTitles();
		model.addAttribute("titleMap", titleMap);
		// get list department
		Map<String, String> departmentMap = this.dataForDepartments();
		model.addAttribute("departmentMap", departmentMap);
		
		String actionform = "";
		if (workHistory.getFromDate() != null) {
			model.addAttribute("formTitle", "Sửa thông tin lịch sử công tác ");
			actionform = "editWorkHistory";
		} else {
			model.addAttribute("formTitle", "Thêm mới thông tin lịch sử công tác ");
			actionform = "insertWorkHistory";
		}
		return actionform;
	}

	@RequestMapping("/workHistory/addWorkHistory")
	public String addWorkHistory(Model model) {		
		WorkHistory workHistory = new WorkHistory();
		return this.workHistoryForm(model, workHistory);
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
		WorkHistory workHistory = null;
		if (employeeId > 0 && fromDate != null) {
			workHistory = this.workHistoryDAO.getWorkHistory(employeeId, fromDate);
		}
		if (workHistory == null) {
			return "redirect:/workHistory/";
		}

		return this.workHistoryForm(model, workHistory);
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
	}

}
