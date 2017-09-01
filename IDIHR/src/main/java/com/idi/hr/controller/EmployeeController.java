package com.idi.hr.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.idi.hr.bean.EmployeeInfo;
import com.idi.hr.bean.JobTitle;
import com.idi.hr.dao.EmployeeDAO;
import com.idi.hr.dao.JobTitleDAO;

@Controller
public class EmployeeController {

	private static final Logger log = Logger.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeDAO employeeDAO;
	@Autowired
	private JobTitleDAO jobTitleDAO;

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String ListEmployees(Model model) {
		try {
			List<EmployeeInfo> list = employeeDAO.getEmployees();
			model.addAttribute("employees", list);
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listEmployee";
	}

	@RequestMapping(value = "/insertOrUpdateEmployee", method = RequestMethod.POST)
	public String insertOrUpdateEmployee(Model model, @ModelAttribute("employeeForm") EmployeeInfo employeeInfo) {
		System.err.println("before insert here");
		try {
			// employeeDAO.insertOrUpdateEmployee(employeeInfo);
			// redirectAttributes.addFlashAttribute("message", "Insert new employee
			// successful");
		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/listEmployee";
	}

	private String employeeForm(Model model, EmployeeInfo employeeInfo) {
		model.addAttribute("employeeForm", employeeInfo);
		Map<String, String> titleMap = this.dataForTitles();
		// get list title
		model.addAttribute("titleMap", titleMap);
		// get list department
		
		if (employeeInfo.getEmployeeId() == null) {
			model.addAttribute("formTitle", "Thêm mới nhân viên");
		} else {
			model.addAttribute("formTitle", "Sửa thông tin nhân viên");
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

	   @RequestMapping("/editEmployee")
	   public String editEmployee(Model model, @RequestParam("employeeId") String employeeId) {
		   EmployeeInfo employeeInfo = null;
	       if (employeeId != null) {
	    	   employeeInfo = this.employeeDAO.getEmployee(employeeId);
	       }
	       if (employeeInfo == null) {
	           return "redirect:/listEmployee";
	       }
	 
	       return this.employeeForm(model, employeeInfo);
	   }
	
	   @RequestMapping("/viewEmployee")
	   public String viewEmployee(Model model, @RequestParam("employeeId") String employeeId) {
		   EmployeeInfo employeeInfo = null;
	       if (employeeId != null) {
	    	   employeeInfo = this.employeeDAO.getEmployee(employeeId);
	    	   model.addAttribute("employeeForm", employeeInfo);
	       }
	       if (employeeInfo == null) {
	           return "redirect:/listEmployee";
	       }
	 
	       return "viewEmployee";
	   }
	
}
