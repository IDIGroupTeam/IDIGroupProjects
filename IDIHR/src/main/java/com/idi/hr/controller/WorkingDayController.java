package com.idi.hr.controller;

import java.util.List;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.idi.hr.bean.WorkingDay;
import com.idi.hr.dao.WorkingDayDAO;
import com.idi.hr.validator.WorkingDayValidator;

@Controller
public class WorkingDayController {

	private static final Logger log = Logger.getLogger(WorkingDayController.class.getName());

	@Autowired
	private WorkingDayDAO workingDayDAO;
	
	@Autowired
	private WorkingDayValidator workingDayValidator;
	
	@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {

		// Form mục tiêu
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}

		if (target.getClass() == WorkingDay.class) {
			dataBinder.setValidator(workingDayValidator);
		}
	}
	
	@RequestMapping(value = "/timekeeping/addWorkingDay", method = RequestMethod.POST)
	public String addWorkingDay(Model model, @ModelAttribute("workingDayForm") @Validated WorkingDay workingDay,
			BindingResult result,final RedirectAttributes redirectAttributes) {
		try {
			if (result.hasErrors()) {
				// System.err.println("co loi validate");
				return this.setWorkingDayForMonth(model, workingDay);
			}
			workingDayDAO.insertWorkingDay(workingDay);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Định nghĩa số ngày công chuẩn thành công!");

		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/timekeeping/listWorkingDay";
	}

	@RequestMapping(value = "/timekeeping/updateWorkingDay", method = RequestMethod.POST)
	public String updateWorkingDay(Model model, @ModelAttribute("workingDayForm") WorkingDay workingDay,
			final RedirectAttributes redirectAttributes) {
		try {
			workingDayDAO.updateDepartment(workingDay);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Sửa số ngày công chuẩn thành công!");

		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/timekeeping/listWorkingDay";
	}

	@RequestMapping("/timekeeping/editWorkingDay")
	public String editWorkingDay(Model model, @RequestParam("month") String month,
			@RequestParam("forCompany") String forCompany) {
		
		WorkingDay workingDay = null;
		if (month != null && forCompany != null) {
			workingDay = this.workingDayDAO.getWorkingDay(month, forCompany);
		}
		if (workingDay == null) {
			return "redirect:/timekeeping/listWorkingDay";
		}
		model.addAttribute("workingDayForm", workingDay);
		return "editWorkDay";
	}

	@RequestMapping(value="/timekeeping/setWorkingDayForMonth")
	public String setWorkingDayForMonth(Model model, WorkingDay workingDay) {
		model.addAttribute("formTitle", "Định nghĩa số ngày công chuẩn");
		model.addAttribute("workingDayForm", workingDay);
		
		return "setWorkDayForMonth";
	}
		
	@RequestMapping(value = { "/timekeeping/listWorkingDay" }, method = RequestMethod.GET)
	public String listWorkingDay(Model model) {
		try {
			List<WorkingDay> list = workingDayDAO.getWorkingDays();
			model.addAttribute("workingDays", list);
			WorkingDay workingDayForm = new WorkingDay();
			model.addAttribute("workingDayForm", workingDayForm);
			model.addAttribute("formTitle", "Danh sách các tháng được định nghĩa ngày công chuẩn");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listWorkingDay";
	}
}
