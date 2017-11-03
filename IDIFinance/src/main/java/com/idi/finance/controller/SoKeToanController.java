package com.idi.finance.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idi.finance.bean.bieudo.KpiGroup;
import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.soketoan.NghiepVuKeToan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.ChungTuDAO;
import com.idi.finance.dao.KhachHangDAO;
import com.idi.finance.dao.KpiChartDAO;
import com.idi.finance.dao.NhaCungCapDAO;
import com.idi.finance.dao.NhanVienDAO;
import com.idi.finance.dao.SoKeToanDAO;
import com.idi.finance.dao.TaiKhoanDAO;

@Controller
public class SoKeToanController {
	private static final Logger logger = Logger.getLogger(SoKeToanController.class);

	@Autowired
	KpiChartDAO kpiChartDAO;

	@Autowired
	TaiKhoanDAO taiKhoanDAO;

	@Autowired
	ChungTuDAO chungTuDAO;

	@Autowired
	KhachHangDAO khachHangDAO;

	@Autowired
	NhaCungCapDAO nhaCungCapDAO;

	@Autowired
	NhanVienDAO nhanVienDAO;

	@Autowired
	SoKeToanDAO soKeToanDAO;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("/soketoan/nhatkychung")
	public String sktNhatKyChung(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			// Lấy danh sách tất cả chứng từ
			List<ChungTu> chungTuDs = soKeToanDAO.danhSachChungTu();
			model.addAttribute("chungTuDs", chungTuDs);

			model.addAttribute("tab", "tabSKTNKC");
			return "sktNhatKyChung";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/soketoan/socai")
	public String sktSoCai(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			// Lấy danh sách phiếu thu

			model.addAttribute("tab", "tabSKTSC");
			return "sktSoCai";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/soketoan/sotienmat")
	public String sktSoTienMat(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			String taiKhoan = LoaiTaiKhoan.TIEN_MAT;
			List<NghiepVuKeToan> nghiepVuKeToanDs = soKeToanDAO.danhSachNghiepVuKeToanTheoLoaiTaiKhoan(taiKhoan);
			model.addAttribute("taiKhoan", taiKhoan);
			model.addAttribute("nghiepVuKeToanDs", nghiepVuKeToanDs);

			model.addAttribute("tab", "tabSKTSTM");
			return "sktSoTienMat";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/soketoan/socongno")
	public String sktSoCongNo(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			// Lấy danh sách phiếu thu

			model.addAttribute("tab", "tabSKTSCN");
			return "sktSoCongNo";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping("/soketoan/sotienguinganhang")
	public String sktSoTienGuiNganHang(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			String taiKhoan = LoaiTaiKhoan.TIEN_GUI_NGAN_HANG;
			List<NghiepVuKeToan> nghiepVuKeToanDs = soKeToanDAO.danhSachNghiepVuKeToanTheoLoaiTaiKhoan(taiKhoan);
			model.addAttribute("taiKhoan", taiKhoan);
			model.addAttribute("nghiepVuKeToanDs", nghiepVuKeToanDs);

			model.addAttribute("tab", "tabSKTSTGNH");
			return "sktSoTienGuiNganHang";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
}
