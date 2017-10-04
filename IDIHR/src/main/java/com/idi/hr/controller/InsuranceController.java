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
import com.idi.hr.bean.ProcessInsurance;
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
				employeeMap.put(id.toString(),
						"Ma NV " + id + ", " + employee.getFullName() + ", phòng " + employee.getDepartment());
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
	public String editSocialInsurance(Model model, @RequestParam("socicalInsuNo") String socicalInsuNo) {
		Insurance insurance = null;
		if (socicalInsuNo != null) {
			insurance = this.insuranceDAO.getInsurance(socicalInsuNo);
		}
		if (insurance == null) {
			return "redirect:/insurance/";
		}

		return this.insuranceForm(model, insurance);
	}

	// -------------------------------- Quá trình đóng BHXH
	// ----------------------------//

	@RequestMapping(value = { "/insurance/listProcessInsurance" }, method = RequestMethod.GET)
	public String listProcessInsurance(Model model, @RequestParam("socicalInsuNo") String socicalInsuNo,
			@RequestParam("employeeId") String employeeId) {
		try {
			model.addAttribute("socicalInsuNo", socicalInsuNo);
			model.addAttribute("employeeId", employeeId);
			// Get employee info for insurance
			Map<String, String> employeeMap = this.employees();
			String name = "";
			name = employeeMap.get(employeeId);
			model.addAttribute("name", name);
			List<ProcessInsurance> list = insuranceDAO.getProcessInsurances();
			model.addAttribute("pInsurances", list);
			model.addAttribute("formTitle", "Quá trình đóng bảo hiểm của NV ");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listProcessInsurance";
	}

	@RequestMapping("/processInsurance/insertProcessInsuranceForm")
	public String insertProcessInsuranceForm(Model model, @RequestParam("socicalInsuNo") String socicalInsuNo,
			@RequestParam("employeeId") String employeeId, ProcessInsurance processInsurance) {
		return this.processInsuranceForm(model, processInsurance, socicalInsuNo, employeeId);
	}

	private String processInsuranceForm(Model model, ProcessInsurance processInsurance, String socicalInsuNo,
			String employeeId) {
		model.addAttribute("pInsuranceForm", processInsurance);
		model.addAttribute("socicalInsuNo", socicalInsuNo);
		// Get employee info for insurance
		Map<String, String> employeeMap = this.employees();
		String name = "";
		name = employeeMap.get(employeeId);
		model.addAttribute("name", name);
		model.addAttribute("employeeId", employeeId);
		String actionform = "";
		if (processInsurance.getFromDate() != null) {
			model.addAttribute("formTitle", "Sửa thông tin quá trình đóng BHXH của NV");
			actionform = "editProcessInsurance";
		} else {
			model.addAttribute("formTitle", "Thêm thông tin quá trình đóng BHXH của NV");
			actionform = "insertProcessInsurance";
		}
		System.out.println(actionform);
		return actionform;
	}

	@RequestMapping(value = "/processInsurance/insertProcessInsurance", method = RequestMethod.POST)
	public String insertProcessInsurance(Model model,
			@ModelAttribute("pInsuranceForm") @Validated ProcessInsurance pInsurance,
			@RequestParam("employeeId") String employeeId, final RedirectAttributes redirectAttributes) {
		// can check from date >= ngay vao cty < ngay hien tai
		// to date < ngay hien tai
		try {
			insuranceDAO.insertProcessInsurance(pInsurance);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Thêm thông tin quá trình đóng BHXH thành công!");

		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/insurance/listProcessInsurance?socicalInsuNo=" + pInsurance.getSocicalInsuNo()
				+ "&employeeId=" + employeeId;// this.listProcessInsurance(model, pInsurance.getSocicalInsuNo(),
												// employeeId);
	}

	@RequestMapping("/processInsurance/editProcessInsuranceForm")
	public String editProcessInsuranceForm(Model model, @RequestParam("socicalInsuNo") String socicalInsuNo,
			@RequestParam("employeeId") String employeeId, ProcessInsurance processInsurance) {
		// System.out.println(processInsurance.getFromDate());
		if (processInsurance.getFromDate() != null) {
			processInsurance = insuranceDAO.getProcessInsurance(socicalInsuNo, processInsurance.getFromDate());
		} else {
			return "redirect:/insurance/listProcessInsurance?socicalInsuNo=" + socicalInsuNo + "&employeeId="
					+ employeeId;
		}
		return this.processInsuranceForm(model, processInsurance, socicalInsuNo, employeeId);
	}

	@RequestMapping(value = "/processInsurance/updateProcessInsurance", method = RequestMethod.POST)
	public String updateProcessInsurance(Model model,
			@ModelAttribute("pInsuranceForm") @Validated ProcessInsurance pInsurance,
			@RequestParam("employeeId") String employeeId, final RedirectAttributes redirectAttributes) {
		// can check from date >= ngay vao cty < ngay hien tai
		// to date < ngay hien tai
		try {
			insuranceDAO.updateProcessInsurance(pInsurance);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Sửa thông tin quá trình đóng BHXH thành công!");

		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/insurance/listProcessInsurance?socicalInsuNo=" + pInsurance.getSocicalInsuNo()
				+ "&employeeId=" + employeeId;// this.listProcessInsurance(model, pInsurance.getSocicalInsuNo(),
												// employeeId);
	}
	
	@RequestMapping(value = "/processInsurance/deleteProcessInsurance")
	public String deleteProcessInsurance(Model model, @RequestParam("socicalInsuNo") String socicalInsuNo,
			@RequestParam("employeeId") String employeeId, @RequestParam("fromDate") String fromDate,
			final RedirectAttributes redirectAttributes) {
		try {
			insuranceDAO.deleteProcessInsurance(socicalInsuNo, fromDate);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Xóa thông tin quá trình đóng BHXH thành công!");
		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/insurance/listProcessInsurance?socicalInsuNo=" + socicalInsuNo + "&employeeId=" + employeeId;
	}

}
