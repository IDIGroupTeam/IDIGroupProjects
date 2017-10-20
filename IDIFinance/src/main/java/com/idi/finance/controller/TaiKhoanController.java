package com.idi.finance.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.idi.finance.bean.bieudo.KpiGroup;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.BalanceSheetDAO;
import com.idi.finance.dao.KpiChartDAO;
import com.idi.finance.dao.TaiKhoanDAO;
import com.idi.finance.form.BalanceAssetForm;
import com.idi.finance.utils.ExcelProcessor;

@Controller
public class TaiKhoanController {
	private static final Logger logger = Logger.getLogger(TaiKhoanController.class);

	@Autowired
	BalanceSheetDAO balanceSheetDAO;

	@Autowired
	KpiChartDAO kpiChartDAO;

	@Autowired
	TaiKhoanDAO taiKhoanDAO;

	@RequestMapping("/danhsachtaikhoan")
	public String danhSachTaiKhoan(Model model) {
		// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
		List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
		model.addAttribute("kpiGroups", kpiGroups);

		List<LoaiTaiKhoan> taiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();
		model.addAttribute("taiKhoanDs", taiKhoanDs);

		model.addAttribute("tab", "tabDMTK");
		return "danhSachTaiKhoan";
	}

	@RequestMapping(value = "/luuTaiKhoan", method = RequestMethod.POST)
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

				return "redirect:/danhsachtaikhoan";
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
}
