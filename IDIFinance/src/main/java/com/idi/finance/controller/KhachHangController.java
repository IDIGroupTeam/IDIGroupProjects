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

import com.idi.finance.bean.doitac.KhachHang;
import com.idi.finance.dao.KhachHangDAO;
import com.idi.finance.validator.KhachHangValidator;

@Controller
public class KhachHangController {
	private static final Logger logger = Logger.getLogger(KhachHangController.class);

	@Autowired
	KhachHangDAO khachHangDAO;

	@Autowired
	private KhachHangValidator khachHangValidator;

	@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {

		// Form mục tiêu
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}

		if (target.getClass() == KhachHang.class) {
			dataBinder.setValidator(khachHangValidator);
		}
	}

	@RequestMapping("/danhsachkhachhang")
	public String danhSachKhachHang(Model model) {
		try {
			// Lấy danh sách khách hàng
			List<KhachHang> khachhangDs = khachHangDAO.danhSachKhachHang();
			model.addAttribute("khachhangDs", khachhangDs);

			model.addAttribute("tab", "tabDSKH");
			return "danhSachKhachHang";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/xemkhachhang/{id}")
	public String xemKhachHang(@PathVariable("id") int maKh, Model model) {
		try {
			KhachHang khachHang = khachHangDAO.layKhachHang(maKh);
			model.addAttribute("khachHang", khachHang);

			model.addAttribute("tab", "tabDSKH");
			return "xemKhachHang";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/suakhachhang/{id}")
	public String suaKhachHang(@PathVariable("id") int maKh, Model model) {
		try {
			KhachHang khachHang = khachHangDAO.layKhachHang(maKh);
			model.addAttribute("mainFinanceForm", khachHang);

			model.addAttribute("tab", "tabDSKH");
			return "suaKhachHang";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/taomoikhachhang")
	public String taoMoiKhachHang(Model model) {
		try {
			KhachHang khachHang = new KhachHang();
			model.addAttribute("mainFinanceForm", khachHang);

			model.addAttribute("tab", "tabDSKH");
			return "taoMoiKhachHang";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	// @RequestMapping(value = "/luutaomoikhachhang", produces =
	// "text/plain;charset=UTF-8")
	@RequestMapping("/luutaomoikhachhang")
	public String luuTaoMoiKhachHang(@ModelAttribute("mainFinanceForm") @Validated KhachHang khachHang,
			BindingResult result, Model model) {
		try {
			logger.info("Khách hàng: " + khachHang);
			if (result.hasErrors()) {
				model.addAttribute("mainFinanceForm", khachHang);
				model.addAttribute("tab", "tabDSKH");

				if (khachHang.getMaKh() > 0) {
					// Đây là trường hợp sửa KH
					return "suaKhachHang";
				} else {
					// Đây là trường hợp tạo mới KH
					return "taoMoiKhachHang";
				}
			}

			khachHangDAO.luuCapNhatKhachHang(khachHang);
			return "redirect:/danhsachkhachhang";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/xoakhachhang/{id}")
	public String xoaKhachHang(@PathVariable("id") int maKh, Model model) {
		try {
			// Xóa khách hàng có MA_KH là maKh
			khachHangDAO.xoaKhachHang(maKh);

			return "redirect:/danhsachkhachhang";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
}
