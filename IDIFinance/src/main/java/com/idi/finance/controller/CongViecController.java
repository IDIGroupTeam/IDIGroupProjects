package com.idi.finance.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.idi.finance.bean.bieudo.KpiGroup;
import com.idi.finance.bean.congviec.LinhVuc;
import com.idi.finance.bean.congviec.NghiepVu;
import com.idi.finance.dao.CongViecDAO;
import com.idi.finance.dao.KpiChartDAO;
import com.idi.finance.validator.NghiepVuValidator;

@Controller
public class CongViecController {
	private static final Logger logger = Logger.getLogger(CongViecController.class);

	@Autowired
	KpiChartDAO kpiChartDAO;

	@Autowired
	CongViecDAO congViecDAO;

	@Autowired
	private NghiepVuValidator nghiepVuValidator;

	@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {
		// Form mục tiêu
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}

		if (target.getClass() == NghiepVu.class) {
			dataBinder.setValidator(nghiepVuValidator);
		} else if (target.getClass() == LinhVuc.class) {
			// dataBinder.setValidator(nghiepVuValidator);
		}
	}

	@RequestMapping("/congviec/linhvuc/danhsach")
	public String danhSachLinhVuc(Model model) {
		// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
		List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
		model.addAttribute("kpiGroups", kpiGroups);

		List<LinhVuc> linhVucDs = congViecDAO.danhSachLinhVuc();
		model.addAttribute("linhVucDs", linhVucDs);

		model.addAttribute("tab", "tabDSLV");
		return "danhSachLinhVuc";
	}

	@RequestMapping("/congviec/nghiepvu/danhsach")
	public String danhSachNghiepVu(Model model) {
		// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
		List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
		model.addAttribute("kpiGroups", kpiGroups);

		List<NghiepVu> nghiepVuDs = congViecDAO.danhSachNghiepVu();
		model.addAttribute("nghiepVuDs", nghiepVuDs);

		model.addAttribute("tab", "tabDSNV");
		return "danhSachNghiepVu";
	}

	@RequestMapping("/congviec/nghiepvu/xem/{maNv}")
	public String xemNghiepVu(@PathVariable("maNv") String maNv, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			NghiepVu nghiepVu = congViecDAO.layNghiepVu(maNv);
			if (nghiepVu == null) {
				return "redirect:/congviec/nghiepvu/danhsach";
			}
			model.addAttribute("nghiepVu", nghiepVu);

			model.addAttribute("tab", "tabDSNV");
			return "xemNghiepVu";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/congviec/nghiepvu/taomoi")
	public String taoMoiNghiepVu(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			NghiepVu nghiepVu = new NghiepVu();

			return chuanBiFormNghiepVu(model, nghiepVu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/congviec/nghiepvu/sua/{maNv}")
	public String suaPhieuThu(@PathVariable("maNv") String maNv, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			NghiepVu nghiepVu = congViecDAO.layNghiepVu(maNv);
			if (nghiepVu == null) {
				return "redirect:/congviec/nghiepvu/danhsach";
			}

			return chuanBiFormNghiepVu(model, nghiepVu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/congviec/nghiepvu/luu", method = RequestMethod.POST)
	public String luuNghiepVu(@ModelAttribute("mainFinanceForm") @Validated NghiepVu nghiepVu, BindingResult result,
			Model model) {
		try {
			if (result.hasErrors()) {
				return chuanBiFormNghiepVu(model, nghiepVu);
			}

			logger.info("Thêm loại nghiệp vụ: " + nghiepVu);
			if (nghiepVu.getMaNv() > 0) {
				congViecDAO.capNhatNghiepVu(nghiepVu);
				return "redirect:/congviec/nghiepvu/danhsach";
			} else {
				congViecDAO.themNghiepVu(nghiepVu);
			}

			return "redirect:/congviec/nghiepvu/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormNghiepVu(Model model, NghiepVu nghiepVu) {
		try {
			model.addAttribute("mainFinanceForm", nghiepVu);
			model.addAttribute("tab", "tabDSNV");

			List<LinhVuc> linhVucDs = congViecDAO.danhSachLinhVuc();
			model.addAttribute("linhVucDs", linhVucDs);

			if (nghiepVu.getMaNv() > 0) {
				// Đây là trường hợp sửa NV
				return "suaNghiepVu";
			} else {
				// Đây là trường hợp tạo mới NV
				return "taoMoiNghiepVu";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/congviec/nghiepvu/xoa/{maNv}")
	public String xoaTaiKhoan(@PathVariable("maNv") String maNv, Model model) {
		try {
			congViecDAO.xoaNghiepVu(maNv);
			return "redirect:/congviec/nghiepvu/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

}
