package com.idi.hr.controller;

import java.sql.Date;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.idi.hr.bean.EmployeeInfo;
import com.idi.hr.bean.LeaveInfo;
import com.idi.hr.bean.LeaveType;
import com.idi.hr.bean.Timekeeping;
import com.idi.hr.common.ExcelProcessor;
import com.idi.hr.dao.EmployeeDAO;
import com.idi.hr.dao.LeaveDAO;
import com.idi.hr.dao.TimekeepingDAO;

@Controller
public class TimekeepingController {
	private static final Logger log = Logger.getLogger(TimekeepingController.class.getName());

	@Autowired
	private TimekeepingDAO timekeepingDAO;
	
	@Autowired
	private LeaveDAO leaveDAO;
	
	@Autowired
	private EmployeeDAO employeeDAO;
	
	ExcelProcessor xp = new ExcelProcessor();
	
	@RequestMapping(value = "/timekeeping/updateData", method = RequestMethod.POST)
	public String updateData(Model model, @RequestParam("timeKeepingFile") MultipartFile timeKeepingFile) {
		System.err.println("import excel file");
		System.err.println(timeKeepingFile.getName());
		Timekeeping timekeeping = new Timekeeping();
		model.addAttribute("timekeepingForm", timekeeping);
		if (timeKeepingFile != null && timeKeepingFile.getSize() > 0) {
			log.info(timeKeepingFile.getName() + " - " + timeKeepingFile.getSize());
			try {
				// Read & insert timekeeping data
				List<Timekeeping> timekeepings = xp.loadTimekeepingDataFromExcel(timeKeepingFile.getInputStream());
				timekeepingDAO.insertOrUpdate(timekeepings);

				List<Timekeeping> list = timekeepingDAO.getTimekeepings();
				model.addAttribute("timekeepings", list);
				model.addAttribute("formTitle", "Dữ liệu chấm công");
				
				return "formTimekeeping";
			} catch (Exception e) {
				e.printStackTrace();
				String comment = "Không thể đọc excel file " + timeKeepingFile.getName()
						+ ". Có thể file bị lỗi, không đúng định dạng, hoặc đường truyền chậm, xin mời thử lại...";
				model.addAttribute("comment", comment);
				//model.addAttribute("tab", "tabCNDL");
				return "formTimekeeping";
			}
		} else {
			String comment = "Hãy chọn file excel dữ liệu chấm công.";
			model.addAttribute("comment", comment);
			List<Timekeeping> list = timekeepingDAO.getTimekeepings();
			model.addAttribute("timekeepings", list);
			model.addAttribute("formTitle", "Dữ liệu chấm công");
			return "formTimekeeping";
		}		
	}
	
	@RequestMapping(value = { "/timekeeping/" }, method = RequestMethod.GET)
	public String listForTimekeeping(Model model, Timekeeping timekeeping) {
		try {
			//System.out.println("list by employee");
			List<Timekeeping> list = timekeepingDAO.getTimekeepings();
			model.addAttribute("timekeepings", list);
			model.addAttribute("timekeepingForm", timekeeping);
			model.addAttribute("formTitle", "Dữ liệu chấm công");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "formTimekeeping";
	}
	
	@RequestMapping(value = { "/timekeeping/leaveInfo" }, method = RequestMethod.GET)
	public String listleaveInfo(Model model, LeaveInfo LeaveInfo) {
		try {
			List<LeaveInfo> list = leaveDAO.getLeaves();
			model.addAttribute("leaveInfos", list);
			//model.addAttribute("leaveInfoForm", LeaveInfo);
			model.addAttribute("formTitle", "Dữ liệu ngày nghỉ");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listLeaveInfo";
	}
	
	@RequestMapping(value = "/timekeeping/insertLeaveInfo", method = RequestMethod.POST)
	public String insertLeaveInfo(Model model, @ModelAttribute("leaveInfoForm") @Validated LeaveInfo leaveInfo,
			final RedirectAttributes redirectAttributes) {
		try {
			leaveDAO.insertLeaveInfo(leaveInfo);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Thêm thông tin ngày nghỉ thành công!");

		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/timekeeping/leaveInfo";
	}
	
	@RequestMapping(value= "/timekeeping/addLeaveInfo")
	public String addLeaveInfo(Model model, LeaveInfo leaveInfo) {
		model.addAttribute("formTitle", "Thêm dữ liệu chấm công");
		// get list employee id
		Map<String, String> employeeMap = this.employees();
		model.addAttribute("employeeMap", employeeMap);
		
		// get list leave type
		Map<String, String> leaveTypeMap = this.leaveTypes();
		model.addAttribute("leaveTypeMap", leaveTypeMap);
		
		model.addAttribute("leaveInfoForm", leaveInfo);
		
		return "addLeaveInfo";
	}
	
	@RequestMapping(value= "/timekeeping/deleteLeaveInfo")
	public String deleteLeaveInfo(Model model,@RequestParam("employeeId") int employeeId,  @RequestParam("date") Date date,
			@RequestParam("leaveType") String leaveType, final RedirectAttributes redirectAttributes) {
		try {
			leaveDAO.deleteLeaveInfo(employeeId, date, leaveType);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Xóa thông tin ngày nghỉ thành công!");
		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/timekeeping/leaveInfo";
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
	
	private Map<String, String> leaveTypes() {
		Map<String, String> leaveTypeMap = new LinkedHashMap<String, String>();
		try {
			List<LeaveType> list = leaveDAO.getLeaveTypes();
			LeaveType leaveType = new LeaveType();
			for (int i = 0; i < list.size(); i++) {
				leaveType = (LeaveType) list.get(i);
				String leaveId = leaveType.getLeaveId();
				leaveTypeMap.put(leaveId, leaveType.getLeaveName());
			}

		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return leaveTypeMap;
	}
}
