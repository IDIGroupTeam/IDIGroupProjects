package com.idi.finance.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idi.finance.bean.bieudo.KpiGroup;
import com.idi.finance.bean.cdkt.BalanceAssetData;
import com.idi.finance.bean.hanghoa.DonVi;
import com.idi.finance.bean.hanghoa.LoaiHangHoa;
import com.idi.finance.bean.hanghoa.NhomHangHoa;
import com.idi.finance.dao.HangHoaDAO;
import com.idi.finance.dao.KpiChartDAO;
import com.idi.finance.form.BalanceAssetForm;
import com.idi.finance.utils.Utils;

@Controller
public class HangHoaController {
	private static final Logger logger = Logger.getLogger(HangHoaController.class);

	@Autowired
	KpiChartDAO kpiChartDAO;

	@Autowired
	HangHoaDAO hangHoaDAO;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("/hanghoa/donvi/danhsach")
	public String danhSachDonViHangHoa(@ModelAttribute("mainFinanceForm") BalanceAssetForm balanceSheetForm,
			Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			List<DonVi> donViDs = hangHoaDAO.danhSachDonViHangHoa();
			model.addAttribute("donViDs", donViDs);

			model.addAttribute("tab", "tabDSDVHH");
			return "danhSachDonViHangHoa";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/hanghoa/nhom/danhsach")
	public String danhSachNhomHangHoa(@ModelAttribute("mainFinanceForm") BalanceAssetForm balanceSheetForm,
			Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			NhomHangHoa nhomHangHoa = new NhomHangHoa();
			nhomHangHoa = hangHoaDAO.danhSachNhomHangHoa(nhomHangHoa);

			model.addAttribute("nhomHangHoa", nhomHangHoa);

			model.addAttribute("tab", "tabDSNHH");
			return "danhSachNhomHangHoa";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/hanghoa/danhsach")
	public String danhSachLoaiHangHoa(@ModelAttribute("mainFinanceForm") BalanceAssetForm balanceSheetForm, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			List<LoaiHangHoa> loaiHangHoaDs = hangHoaDAO.danhSachLoaiHangHoa();
			model.addAttribute("loaiHangHoaDs", loaiHangHoaDs);

			model.addAttribute("tab", "tabDSHH");
			return "danhSachLoaiHangHoa";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
}
