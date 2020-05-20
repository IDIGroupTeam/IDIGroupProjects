package com.idi.hr.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.idi.hr.bean.EmployeeInfo;
import com.idi.hr.bean.Insurance;
import com.idi.hr.bean.ProcessInsurance;
import com.idi.hr.common.Utils;
import com.idi.hr.dao.EmployeeDAO;
import com.idi.hr.dao.InsuranceDAO;
import com.idi.hr.form.InsuranceForm;

@Controller
public class InsuranceController {
	private static final Logger log = Logger.getLogger(InsuranceController.class.getName());

	@Autowired
	private InsuranceDAO insuranceDAO;

	@Autowired
	private EmployeeDAO employeeDAO;

	@RequestMapping(value = { "/insurance/" })
	public String ListInsurances(Model model, @ModelAttribute("insuranceForm") InsuranceForm form) throws Exception {
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
			
			boolean search = false;
			List<Insurance> list = new ArrayList<Insurance>();
			
			if (form.getSearchValue() != null && form.getSearchValue().length() > 0) {
				log.info("Searching for: " + form.getSearchValue());
				search = true;
				list = insuranceDAO.getInsurances(form.getSearchValue());
			}else {
				list = insuranceDAO.getInsurances(null);
			}
			form.setTotalRecords(list.size());
			
			int totalPages = form.getTotalRecords() % form.getNumberRecordsOfPage() > 0
					? form.getTotalRecords() / form.getNumberRecordsOfPage() + 1
					: form.getTotalRecords() / form.getNumberRecordsOfPage();
			form.setTotalPages(totalPages);

			List<Insurance> listInsuranceForPage = new ArrayList<Insurance>();
			
			if (form.getPageIndex() < totalPages) {
				if (form.getPageIndex() == 1) {
					for (int i = 0; i < form.getNumberRecordsOfPage(); i++) {
						Insurance insurance = new Insurance();
						insurance = list.get(i);
						listInsuranceForPage.add(insurance);
					}
				} else if (form.getPageIndex() > 1) {
					for (int i = ((form.getPageIndex() - 1) * form.getNumberRecordsOfPage()); i < form.getPageIndex()
							* form.getNumberRecordsOfPage(); i++) {
						Insurance insurance = new Insurance();
						insurance = list.get(i);
						listInsuranceForPage.add(insurance);
					}
				}
			} else if (form.getPageIndex() == totalPages) {
				for (int i = ((form.getPageIndex() - 1) * form.getNumberRecordsOfPage()); i < form
						.getTotalRecords(); i++) {
					Insurance insurance = new Insurance();
					insurance = list.get(i);
					listInsuranceForPage.add(insurance);
				}
			}					
			
			if (list != null && list.size() < 1 && !search)
				model.addAttribute("message", "Chưa có thông tin BHXH của nhân viên nào");
			else if (list != null && list.size() < 1 && search)
				model.addAttribute("message",
						"Không có thông tin BHXH nào khớp với thông tin tìm kiếm là: '" + form.getSearchValue() + "'");
			model.addAttribute("insurances", listInsuranceForPage);
			model.addAttribute("formTitle", "Danh sách NV đóng bảo hiểm ");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listInsurance";
	}

	private Map<String, String> employeesForInsert() {
		Map<String, String> employeeMap = new LinkedHashMap<String, String>();
		try {
			//note: nhung 
			List<EmployeeInfo> list = employeeDAO.getEmployeesForInsertInsurrance();
			EmployeeInfo employee = new EmployeeInfo();
			for (int i = 0; i < list.size(); i++) {
				employee = (EmployeeInfo) list.get(i);
				Integer id = employee.getEmployeeId();
				employeeMap.put(id.toString(),
						employee.getFullName() + ", phòng " + employee.getDepartment() + ", mã NV " + id);
			}

		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return employeeMap;
	}
	
	/*
	 * private Map<String, String> employees() { Map<String, String> employeeMap =
	 * new LinkedHashMap<String, String>(); try { //note: nhung List<EmployeeInfo>
	 * list = employeeDAO.getEmployees(); EmployeeInfo employee = new
	 * EmployeeInfo(); for (int i = 0; i < list.size(); i++) { employee =
	 * (EmployeeInfo) list.get(i); Integer id = employee.getEmployeeId();
	 * employeeMap.put(id.toString(), employee.getFullName() + ", phòng " +
	 * employee.getDepartment() + ", mã NV " + id); }
	 * 
	 * } catch (Exception e) { log.error(e, e); e.printStackTrace(); } return
	 * employeeMap; }
	 */
	
	private Map<String, String> allEmployees() {
		Map<String, String> employeeMap = new LinkedHashMap<String, String>();
		try {
			//note: nhung 
			List<EmployeeInfo> list = employeeDAO.getAllEmployees();
			EmployeeInfo employee = new EmployeeInfo();
			for (int i = 0; i < list.size(); i++) {
				employee = (EmployeeInfo) list.get(i);
				Integer id = employee.getEmployeeId();
				employeeMap.put(id.toString(),
						employee.getFullName() + ", phòng " + employee.getDepartment() + ", mã NV " + id);
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
			System.err.println("insuranceForm: " + sInsurance.getEmployeeName() + sInsurance.getSocicalInsuNo());
			if(insuranceDAO.getInsurance(sInsurance.getSocicalInsuNo()).getEmployeeId() > 0) {
				System.err.println("Trung so so bhxh roi: " + sInsurance.getSocicalInsuNo());
				String duplicate = "Số sổ bhxh này đã đc xử dụng cho " + insuranceDAO.getInsurance(sInsurance.getSocicalInsuNo()).getEmployeeName() + ". Vui lòng kiểm tra lại";
				return insuranceForm(model, sInsurance, duplicate, "insert");
			}else {
				insuranceDAO.insertInsurance(sInsurance);
			}
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
			int row = insuranceDAO.updateInsurance(insurance);
			// Add message to flash scope
			if(row > 0)
				redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin bảo hiểm thành công!");
			else {
				String duplicate = "Số sổ bhxh này đã đc xử dụng. Vui lòng kiểm tra lại";
				return insuranceForm(model, insurance, duplicate, "update");
			}
		} catch (Exception e) {
			log.error(e, e);
			String duplicate = "Số sổ bhxh này đã đc xử dụng. Vui lòng kiểm tra lại";
			return insuranceForm(model, insurance, duplicate, "update");
		}
		return "redirect:/insurance/";
	}

	private String insuranceForm(Model model, Insurance insurance, String duplicate, String type) {
		model.addAttribute("insuranceForm", insurance);
		// get list employee id
		Map<String, String> employeeMap = null;
		
		String actionform = "";
		System.err.println(duplicate);
		if (insurance.getSocicalInsuNo() != null && type.equalsIgnoreCase("update")) {
			employeeMap = this.allEmployees();
			model.addAttribute("formTitle", "Sửa thông tin bảo hiểm ");		
			if(duplicate != null)
				model.addAttribute("duplicate", duplicate);
			actionform = "editInsurance";
		} else {
			employeeMap = this.employeesForInsert();
			model.addAttribute("formTitle", "Thêm mới thông tin bảo hiểm");			
			if(duplicate != null)
				model.addAttribute("duplicate", duplicate);
			actionform = "insertInsurance";
		}
		model.addAttribute("employeeMap", employeeMap);
		//System.err.println(actionform);
		return actionform;
	}

	@RequestMapping("/insurance/insertInsurance")
	public String addInsurance(Model model) {
		Insurance insurance = new Insurance();
		return this.insuranceForm(model, insurance, null, "insert");
	}

	@RequestMapping("/insurance/viewInsurance")
	public String viewInsurance(Model model, @RequestParam("socicalInsuNo") String socicalInsuNo) {
		Insurance insurance = null;
		if (socicalInsuNo != null) {
			insurance = this.insuranceDAO.getInsurance(socicalInsuNo);
			model.addAttribute("insuranceForm", insurance);
			model.addAttribute("formTitle", "Thông tin bảo hiểm");
			Map<String, String> employeeMap = this.allEmployees();
			String name = "";
			String id= String.valueOf(insurance.getEmployeeId());
			name = employeeMap.get(id);
			model.addAttribute("name", name);
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

		return this.insuranceForm(model, insurance, null, "update");
	}

	// ------ Quá trình đóng BHXH   --------//

	@RequestMapping(value = { "/insurance/listProcessInsurance" }, method = RequestMethod.GET)
	public String listProcessInsurance(Model model, @RequestParam("socicalInsuNo") String socicalInsuNo,
			@RequestParam("employeeId") String employeeId) {
		try {
			model.addAttribute("socicalInsuNo", socicalInsuNo);
			model.addAttribute("employeeId", employeeId);
			// Get employee info for insurance
			Map<String, String> employeeMap = this.allEmployees();
			String name = "";
			name = employeeMap.get(employeeId);
			model.addAttribute("name", name);
			List<ProcessInsurance> list = insuranceDAO.getProcessInsurances(socicalInsuNo);
			model.addAttribute("pInsurances", list);
			model.addAttribute("formTitle", "Quá trình đóng bảo hiểm ");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listProcessInsurance";
	}

	@RequestMapping("/processInsurance/insertProcessInsuranceForm")
	public String insertProcessInsuranceForm(Model model, @RequestParam("socicalInsuNo") String socicalInsuNo,
			@RequestParam("employeeId") String employeeId, ProcessInsurance processInsurance) {
		return this.processInsuranceForm(model, processInsurance, socicalInsuNo, employeeId, null);
	}

	private String processInsuranceForm(Model model, ProcessInsurance processInsurance, String socicalInsuNo,
			String employeeId, String validate) {
		model.addAttribute("pInsuranceForm", processInsurance);
		model.addAttribute("socicalInsuNo", socicalInsuNo);
		// Get employee info for insurance
		//Map<String, String> employeeMap = this.employees();
		String name = "";
		name = allEmployees().get(employeeId);
		model.addAttribute("name", name);
		model.addAttribute("employeeId", employeeId);
		String actionform = "";
		if (processInsurance.getFromDate() != null) {
			model.addAttribute("formTitle", "Sửa thông tin quá trình đóng BHXH");
			actionform = "editProcessInsurance";
		} else {
			model.addAttribute("formTitle", "Thêm thông tin quá trình đóng BHXH");
			actionform = "insertProcessInsurance";
		}
		
		if(validate != null)
			model.addAttribute("validate", validate);
		//System.out.println(name);
		return actionform;
	}

	@RequestMapping(value = "/processInsurance/insertProcessInsurance", method = RequestMethod.POST)
	public String insertProcessInsurance(Model model, 
			@ModelAttribute("pInsuranceForm") @Validated ProcessInsurance pInsurance,
			@RequestParam("employeeId") String employeeId, final RedirectAttributes redirectAttributes) {
		try {
			//check from date >= ngay vao cty < ngay hien tai			
			// to date < ngay hien tai
			// to date > from date
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date fDate = null;
			Date tDate = null;
			if(pInsurance.getFromDate() != null && pInsurance.getFromDate().length() > 0) {
				if(pInsurance.getFromDate() != null && pInsurance.getFromDate().contains("/")) {
					fDate = sdf. parse(Utils.convertDateToStore(pInsurance.getFromDate()));
				}else
					fDate = sdf. parse(pInsurance.getFromDate());
			}
			
			if(pInsurance.getToDate() != null && pInsurance.getToDate().contains("/")) {
				tDate = sdf. parse(Utils.convertDateToStore(pInsurance.getToDate()));
			}else if(pInsurance.getToDate() != null && pInsurance.getToDate().length() > 0)
				tDate = sdf. parse(pInsurance.getToDate());
						
			if(pInsurance.getToDate() != null && pInsurance.getToDate().length() > 0 && fDate.compareTo(tDate) >= 0) {				
				return processInsuranceForm(model, pInsurance, pInsurance.getSocicalInsuNo(), employeeId, "Vui lòng nhập ngày kết thức phải lớn hơn ngày bắt đầu");
			}
			
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
			@RequestParam("employeeId") String employeeId, ProcessInsurance processInsurance) throws Exception {
		// System.out.println(processInsurance.getFromDate());
		if (processInsurance.getFromDate() != null) {
			processInsurance = insuranceDAO.getProcessInsurance(socicalInsuNo, processInsurance.getFromDate());
			if(processInsurance.getToDate() != null && processInsurance.getToDate().length() > 0)
				processInsurance.setToDate(Utils.convertDateToDisplay(processInsurance.getToDate()));
		} else {
			return "redirect:/insurance/listProcessInsurance?socicalInsuNo=" + socicalInsuNo + "&employeeId="
					+ employeeId;
		}
		return this.processInsuranceForm(model, processInsurance, socicalInsuNo, employeeId, null);
	}

	@RequestMapping(value = "/processInsurance/updateProcessInsurance", method = RequestMethod.POST)
	public String updateProcessInsurance(Model model,
			@ModelAttribute("pInsuranceForm") @Validated ProcessInsurance pInsurance,
			@RequestParam("employeeId") String employeeId, final RedirectAttributes redirectAttributes) {

		try {
			// can check from date >= ngay vao cty < ngay hien tai
			// to date < ngay hien tai
			// to date > from date
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date fDate;
			Date tDate;
			if(pInsurance.getFromDate() != null && pInsurance.getFromDate().contains("/")) {
				fDate = sdf. parse(Utils.convertDateToStore(pInsurance.getFromDate()));
			}else
				fDate = sdf. parse(pInsurance.getFromDate());
			
			if(pInsurance.getToDate() != null && pInsurance.getToDate().contains("/")) {
				tDate = sdf. parse(Utils.convertDateToStore(pInsurance.getToDate()));
			}else
				tDate = sdf. parse(pInsurance.getToDate());
			
			if(fDate.compareTo(tDate) >= 0) {		
				//System.err.println("Ngày kết thức phải lớn hơn ngày bắt đầu");
				return processInsuranceForm(model, pInsurance, pInsurance.getSocicalInsuNo(), employeeId, "Vui lòng nhập ngày kết thức phải lớn hơn ngày bắt đầu");
			}
			
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
