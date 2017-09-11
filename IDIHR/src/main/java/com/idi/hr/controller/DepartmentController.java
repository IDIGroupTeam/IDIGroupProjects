package com.idi.hr.controller;

import java.util.List;

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
import com.idi.hr.dao.DepartmentDAO;

@Controller
public class DepartmentController {

	private static final Logger log = Logger.getLogger(DepartmentController.class.getName());

	@Autowired
	private DepartmentDAO departmentDAO;

	@RequestMapping(value = { "/department/" }, method = RequestMethod.GET)
	public String ListDepartments(Model model) {
		try {
			List<Department> list = departmentDAO.getDepartments();
			model.addAttribute("departments", list);
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listDepartment";
	}

	@RequestMapping(value = "/department/addDepartment", method = RequestMethod.POST)
	public String addDepartment(Model model, @ModelAttribute("departmentForm") @Validated Department department,
			final RedirectAttributes redirectAttributes) {
		try {
			departmentDAO.insertDepartment(department);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Insert department successful");

		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/department/";
	}

	@RequestMapping(value = "/department/updateDepartment", method = RequestMethod.POST)
	public String updateDepartment(Model model, @ModelAttribute("departmentForm") @Validated Department department,
			final RedirectAttributes redirectAttributes) {
		try {
			departmentDAO.updateDepartment(department);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Update department successful");

		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/department/";
	}

	private String departmentForm(Model model, Department department) {
		model.addAttribute("departmentForm", department);
		String actionform = "";
		if (department.getDepartmentId() != null) {
			model.addAttribute("formTitle", "Sửa thông tin phòng ban");
			actionform = "formDepartment";
		} else {
			model.addAttribute("formTitle", "Thêm mới phòng ban");
			actionform = "insertDepartment";
		}
		System.err.println(actionform);
		return actionform;
	}

	@RequestMapping("/department/insertDepartment")
	public String addDepartment(Model model) {
		Department department = new Department();
		return this.departmentForm(model, department);
	}

	@RequestMapping("/department/editDepartment")
	public String editDepartment(Model model, @RequestParam("departmentId") String departmentId) {
		Department department = null;
		if (departmentId != null) {
			department = this.departmentDAO.getDepartment(departmentId);
		}
		if (department == null) {
			return "redirect:/department/";
		}

		return this.departmentForm(model, department);
	}

}
