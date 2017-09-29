package com.idi.hr.controller;

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

import com.idi.hr.bean.EmployeeInfo;
import com.idi.hr.bean.Insurance;
import com.idi.hr.dao.EmployeeDAO;
import com.idi.hr.dao.InsuranceDAO;

@Controller
public class InsuranceController {
	private static final Logger log = Logger.getLogger(InsuranceController.class.getName());

	@Autowired
	private InsuranceDAO insuranceDAO;

	@Autowired
	private EmployeeDAO employeeDAO;

	@RequestMapping(value = { "/insurance/" }, method = RequestMethod.GET)
	public String ListInsurances(Model model) {
		try {
			List<Insurance> list = insuranceDAO.getInsurances();
			model.addAttribute("insurances", list);
			model.addAttribute("formTitle", "Danh sách NV đóng bảo hiểm ");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listInsurance";
	}

	private Map<String, String> employees() {
		Map<String, String> employeeMap = new LinkedHashMap<String, String>();
		try {
			List<EmployeeInfo> list = employeeDAO.getEmployees();
			EmployeeInfo employee = new EmployeeInfo();
			for (int i = 0; i < list.size(); i++) {
				employee = (EmployeeInfo) list.get(i);
				Integer id = employee.getEmployeeId();
				employeeMap.put(id.toString(), employee.getFullName() + ": " + employee.getDepartment());
			}

		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return employeeMap;
	}

	@RequestMapping(value = "/insurance/insertInsurance", method = RequestMethod.POST)
	public String addInsurance(Model model, @ModelAttribute("insuranceForm") @Validated Insurance sInsurance,
			final RedirectAttributes redirectAttributes) {
		try {
			insuranceDAO.insertInsurance(sInsurance);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Thêm thông tin bảo hiểm thành công!");

		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/insurance/";
	}

	@RequestMapping(value = "/insurance/updateInsurance", method = RequestMethod.POST)
	public String updateInsurance(Model model, @ModelAttribute("insuranceForm") @Validated Insurance insurance,
			final RedirectAttributes redirectAttributes) {
		try {
			insuranceDAO.updateInsurance(insurance);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin bảo hiểm thành công!");

		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/insurance/";
	}

	private String insuranceForm(Model model, Insurance insurance) {
		model.addAttribute("insuranceForm", insurance);
		// get list employee id
		Map<String, String> employeeMap = this.employees();
		model.addAttribute("employeeMap", employeeMap);

		String actionform = "";
		if (insurance.getSocicalInsuNo() != null) {
			model.addAttribute("formTitle", "Sửa thông tin bảo hiểm ");
			actionform = "editInsurance";
		} else {
			model.addAttribute("formTitle", "Thêm mới thông tin bảo hiểm");
			actionform = "insertInsurance";
		}
		System.err.println(actionform);
		return actionform;
	}

	@RequestMapping("/insurance/insertInsurance")
	public String addInsurance(Model model) {
		Insurance insurance = new Insurance();
		return this.insuranceForm(model, insurance);
	}

	@RequestMapping("/insurance/viewInsurance")
	public String viewInsurance(Model model, @RequestParam("socicalInsuNo") String socicalInsuNo) {
		Insurance insurance = null;
		if (socicalInsuNo != null) {
			insurance = this.insuranceDAO.getInsurance(socicalInsuNo);
			model.addAttribute("insuranceForm", insurance);
			model.addAttribute("formTitle", "Thông tin bảo hiểm");
		}
		if (insurance == null) {
			return "redirect:/insurance/";
		}

		return "viewInsurance";
	}
	
	@RequestMapping("/insurance/editInsurance")
	public String editSocialInsurance(Model model, @RequestParam("socicalInsuNo") String socicalInsuNo ) {
		Insurance insurance = null;
		if (socicalInsuNo != null) {
			insurance = this.insuranceDAO.getInsurance(socicalInsuNo);
		}
		if (insurance == null) {
			return "redirect:/insurance/";
		}

		return this.insuranceForm(model, insurance);
	}
}
