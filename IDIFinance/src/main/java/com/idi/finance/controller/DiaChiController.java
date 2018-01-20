package com.idi.finance.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.idi.finance.bean.doitac.CapDiaChi;
import com.idi.finance.bean.doitac.VungDiaChi;
import com.idi.finance.dao.DiaChiDAO;
import com.idi.finance.form.DiaChiForm;
import com.idi.finance.utils.ExcelProcessor;

@Controller
public class DiaChiController {
	private static final Logger logger = Logger.getLogger(DiaChiController.class);

	@Autowired
	DiaChiDAO diaChiDAO;

	@RequestMapping("/diachi/danhsach")
	public String danhSachDiaChi(Model model, @ModelAttribute("mainFinanceForm") DiaChiForm form) {
		try {
			// Đặt giá trị mặc định cho form tìm kiếm
			if (form.getMien() == null) {
				form.setMien(VungDiaChi.TC);
			}

			if (form.getThanhPho() == null) {
				form.setThanhPho(VungDiaChi.TC);
			}

			if (form.getQuan() == null) {
				form.setQuan(VungDiaChi.TC);
			}

			// Lấy danh sách vùng địa chỉ
			VungDiaChi mienDc = new VungDiaChi();
			mienDc.setMaDc(form.getMien());
			VungDiaChi thanhPhoDc = new VungDiaChi();
			thanhPhoDc.setMaDc(form.getThanhPho());
			VungDiaChi quanDc = new VungDiaChi();
			quanDc.setMaDc(form.getQuan());

			quanDc.setVungDiaChi(thanhPhoDc);
			thanhPhoDc.themVungDiaChi(quanDc);
			thanhPhoDc.setVungDiaChi(mienDc);
			mienDc.themVungDiaChi(thanhPhoDc);

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

			if (form.getTotalRecords() == 0) {
				form.setTotalRecords(diaChiDAO.demTongSoVungDiaChi(mienDc));
			}

			int totalPages = form.getTotalRecords() % form.getNumberRecordsOfPage() > 0
					? form.getTotalRecords() / form.getNumberRecordsOfPage() + 1
					: form.getTotalRecords() / form.getNumberRecordsOfPage();
			form.setTotalPages(totalPages);

			// Lấy danh sách miền
			List<VungDiaChi> mienDs = diaChiDAO.danhSachDiaChi(CapDiaChi.MIEN);

			// Lấy danh sách thành phố
			List<VungDiaChi> thanhPhoDs = new ArrayList<>();
			if (form.getMien() != null && !form.getMien().equals("") && !form.getMien().equals(VungDiaChi.TC)) {
				thanhPhoDs = diaChiDAO.danhSachDiaChi(form.getMien());
			} else {
				thanhPhoDs = diaChiDAO.danhSachDiaChi(CapDiaChi.THANH_PHO);
			}

			// Lấy danh sách quận
			List<VungDiaChi> quanDs = new ArrayList<>();
			if (form.getThanhPho() != null && !form.getThanhPho().equals("")
					&& !form.getThanhPho().equals(VungDiaChi.TC)) {
				quanDs = diaChiDAO.danhSachDiaChi(form.getThanhPho());
			}

			int batDau = (form.getPageIndex() - 1) * form.getNumberRecordsOfPage() + 1;
			List<VungDiaChi> vungDiaChiDs = diaChiDAO.danhSachDiaChi(mienDc, batDau, form.getNumberRecordsOfPage());

			logger.info("Page index: " + form.getPageIndex());
			logger.info("Number records of page: " + form.getNumberRecordsOfPage());
			logger.info("Total pages: " + form.getTotalPages());
			logger.info("Total records: " + form.getTotalRecords());

			model.addAttribute("mienDs", mienDs);
			model.addAttribute("thanhPhoDs", thanhPhoDs);
			model.addAttribute("quanDs", quanDs);
			model.addAttribute("vungDiaChiDs", vungDiaChiDs);

			model.addAttribute("mainFinanceForm", form);
			model.addAttribute("tab", "tabDCDSDC");

			return "danhSachDiaChi";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/diachi/capnhat")
	public String capNhatDiaChi(Model model) {
		try {
			model.addAttribute("tab", "tabDCCNDC");

			return "capNhatDiaChi";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/diachi/luucapnhat", method = RequestMethod.POST)
	public String luuCapNhatDiaChi(Model model, @ModelAttribute("mainFinanceForm") DiaChiForm form) {
		if (form != null && form.getVungFile() != null && form.getVungFile().getSize() > 0) {
			MultipartFile file = form.getVungFile();
			logger.info(file.getName() + " - " + file.getSize());
			try {
				// Đọc danh sách các vùng miền theo cây phân cấp
				List<VungDiaChi> vungDiaChiDs = ExcelProcessor.docVungMienExcel(file.getInputStream(), diaChiDAO);
				diaChiDAO.capNhatVung(vungDiaChiDs);
				logger.info(vungDiaChiDs);

				return "redirect:/diachi/capnhat";
			} catch (Exception e) {
				e.printStackTrace();
				String comment = "Không thể đọc excel file " + file.getName()
						+ ". Có thể file bị lỗi, không đúng định dạng, hoặc đường truyền chậm, xin mời thử lại.";
				model.addAttribute("comment", comment);
				model.addAttribute("tab", "tabCNDL");

				return "capNhatDiaChi";
			}
		} else {
			String comment = "Hãy chọn file exel dữ liệu kế toán.";
			model.addAttribute("comment", comment);
			model.addAttribute("tab", "tabCNDL");

			return "capNhatDiaChi";
		}
	}

	// Những hàm sau dùng cho các ajax request
	@RequestMapping("/diachi/thanhpho")
	public @ResponseBody List<VungDiaChi> danhSachThanhPho() {
		return diaChiDAO.danhSachDiaChi(CapDiaChi.THANH_PHO);
	}

	@RequestMapping("/diachi/vung")
	public @ResponseBody List<VungDiaChi> danhSachVung(@RequestParam("maVung") String maDc) {
		return diaChiDAO.danhSachDiaChi(maDc);
	}
}
