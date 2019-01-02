package com.idi.finance.controller;

import java.util.Iterator;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.idi.finance.bean.bieudo.KpiGroup;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.BalanceSheetDAO;
import com.idi.finance.dao.KpiChartDAO;
import com.idi.finance.dao.TaiKhoanDAO;
import com.idi.finance.form.BalanceAssetForm;
import com.idi.finance.utils.ExcelProcessor;
import com.idi.finance.validator.LoaiTaiKhoanValidator;

@Controller
public class TaiKhoanController {
	private static final Logger logger = Logger.getLogger(TaiKhoanController.class);

	@Autowired
	BalanceSheetDAO balanceSheetDAO;

	@Autowired
	KpiChartDAO kpiChartDAO;

	@Autowired
	TaiKhoanDAO taiKhoanDAO;

	@Autowired
	private LoaiTaiKhoanValidator loaiTaiKhoanValidator;

	@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {
		// Form mục tiêu
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}

		if (target.getClass() == LoaiTaiKhoan.class) {
			dataBinder.setValidator(loaiTaiKhoanValidator);
		}
	}

	@RequestMapping("/taikhoan/danhsach")
	public String danhSachTaiKhoan(Model model) {
		// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
		List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
		model.addAttribute("kpiGroups", kpiGroups);

		List<LoaiTaiKhoan> taiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();
		model.addAttribute("taiKhoanDs", taiKhoanDs);

		Iterator<LoaiTaiKhoan> iter = taiKhoanDs.iterator();
		while (iter.hasNext()) {
			LoaiTaiKhoan loaiTaiKhoan = iter.next();

		}

		model.addAttribute("tab", "tabDMTK");
		return "danhSachTaiKhoan";
	}

	@RequestMapping("/taikhoan/taomoi")
	public String taoMoiTaiKhoan(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
			loaiTaiKhoan.setNew(true);

			return chuanBiFormTaoMoi(model, loaiTaiKhoan);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/taikhoan/luutaomoitk", method = RequestMethod.POST)
	public String luuTaoMoi(@ModelAttribute("mainFinanceForm") @Validated LoaiTaiKhoan loaiTaiKhoan,
			BindingResult result, Model model) {
		try {
			if (result.hasErrors()) {
				return chuanBiFormTaoMoi(model, loaiTaiKhoan);
			}

			logger.info("Thêm loại tài khoản: " + loaiTaiKhoan);
			taiKhoanDAO.themTaiKhoan(loaiTaiKhoan);

			return "redirect:/taikhoan/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormTaoMoi(Model model, LoaiTaiKhoan loaiTaiKhoan) {
		try {
			model.addAttribute("mainFinanceForm", loaiTaiKhoan);

			// Lấy danh sách các loại tài khoản
			List<LoaiTaiKhoan> taiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();
			model.addAttribute("taiKhoanDs", taiKhoanDs);
			model.addAttribute("tab", "tabDMTK");

			// Đây là trường hợp tạo mới loại tài khoản
			return "taoMoiLoaiTaiKhoan";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/taikhoan/sua/{maTk}")
	public String suaTaiKhoan(@PathVariable("maTk") String maTk, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			LoaiTaiKhoan loaiTaiKhoan = taiKhoanDAO.layTaiKhoan(maTk);

			return chuanBiFormSua(model, loaiTaiKhoan);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/taikhoan/luusuatk", method = RequestMethod.POST)
	public String luuSua(@ModelAttribute("mainFinanceForm") @Validated LoaiTaiKhoan loaiTaiKhoan, BindingResult result,
			Model model) {
		try {
			if (result.hasErrors()) {
				return chuanBiFormSua(model, loaiTaiKhoan);
			}

			logger.info("Cập nhật loại tài khoản: " + loaiTaiKhoan);
			taiKhoanDAO.capNhatTaiKhoan(loaiTaiKhoan);

			return "redirect:/taikhoan/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/taikhoan/xoa/{maTk}")
	public String xoaTaiKhoan(@PathVariable("maTk") String maTk, Model model) {
		try {
			taiKhoanDAO.xoaTaiKhoan(maTk);
			return "redirect:/taikhoan/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormSua(Model model, LoaiTaiKhoan loaiTaiKhoan) {
		try {
			model.addAttribute("mainFinanceForm", loaiTaiKhoan);

			// Lấy danh sách các loại tài khoản
			List<LoaiTaiKhoan> taiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();
			model.addAttribute("taiKhoanDs", taiKhoanDs);
			model.addAttribute("tab", "tabDMTK");

			// Đây là trường hợp sửa loại tài khoản
			return "suaLoaiTaiKhoan";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/taikhoan/luu", method = RequestMethod.POST)
	public String save(Model model, @ModelAttribute("mainFinanceForm") BalanceAssetForm balanceSheetForm) {
		// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
		List<KpiGroup> kpiGroupsDb = kpiChartDAO.listKpiGroups();
		model.addAttribute("kpiGroups", kpiGroupsDb);

		if (balanceSheetForm != null && balanceSheetForm.getTaiKhoanFile() != null
				&& balanceSheetForm.getTaiKhoanFile().getSize() > 0) {
			MultipartFile file = balanceSheetForm.getTaiKhoanFile();
			logger.info(file.getName() + " - " + file.getSize());
			try {
				// Đọc và cập nhật danh mục tài khoản
				List<LoaiTaiKhoan> taiKhoanDs = ExcelProcessor.docTaiKhoanExcel(file.getInputStream());
				taiKhoanDAO.insertOrUpdateTaiKhoanDs(taiKhoanDs);

				return "redirect:/taikhoan/danhsach";
			} catch (Exception e) {
				e.printStackTrace();
				String comment = "Không thể đọc excel file " + file.getName()
						+ ". Có thể file bị lỗi, không đúng định dạng, hoặc đường truyền chậm, xin mời thử lại.";
				model.addAttribute("taiKhoanComment", comment);
				model.addAttribute("tab", "tabCNDL");
				return "update";
			}
		} else {
			String comment = "Hãy chọn file exel danh mục tài khoản.";
			model.addAttribute("taiKhoanComment", comment);
			model.addAttribute("tab", "tabCNDL");
			return "update";
		}
	}

	@RequestMapping("/taikhoan/danhsach/capduoi")
	public @ResponseBody List<LoaiTaiKhoan> layDanhSachLoaiTaiKhoan(@RequestParam("maTk") String maTk) {
		logger.info("maTk " + maTk);
		return taiKhoanDAO.danhSachTaiKhoanTheoCap1(maTk);
	}
}
