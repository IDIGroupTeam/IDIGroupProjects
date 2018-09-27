package com.idi.finance.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idi.finance.bean.CauHinh;
import com.idi.finance.bean.DungChung;
import com.idi.finance.dao.CauHinhDAO;

@Controller
public class CauHinhController {
	private static final Logger logger = Logger.getLogger(CauHinhController.class);

	@Autowired
	DungChung dungChung;

	@Autowired
	CauHinhDAO cauHinhDAO;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

		// Form mục tiêu
		Object target = binder.getTarget();
		if (target == null) {
			return;
		}

		if (target.getClass() == CauHinh.class) {
			// binder.setValidator(balanceSheetValidator);
		}
	}

	@RequestMapping("/cauhinh/danhsach")
	public String danhSachCauHinh(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			List<CauHinh> cauHinhDs = cauHinhDAO.danhSachCauHinh();

			model.addAttribute("cauHinhDs", cauHinhDs);
			model.addAttribute("tab", "tabDSCH");
			return "danhSachCauHinh";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/cauhinh/capnhat")
	public @ResponseBody CauHinh cauHinhCapNhat(@RequestParam("maCh") String maCh,
			@RequestParam("giaTri") String giaTri) {
		logger.info("maCh " + maCh + ". giaTri " + giaTri);

		CauHinh cauHinh = new CauHinh();
		cauHinh.setMa(maCh);
		cauHinh.setGiaTri(giaTri);
		cauHinhDAO.capNhatCauHinh(cauHinh);

		return cauHinh;
	}
}