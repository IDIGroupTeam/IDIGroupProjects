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

import com.idi.finance.bean.doitac.NhaCungCap;
import com.idi.finance.dao.NhaCungCapDAO;
import com.idi.finance.validator.NhaCungCapValidator;

@Controller
public class NhaCungCapController {
	private static final Logger logger = Logger.getLogger(NhaCungCapController.class);

	@Autowired
	NhaCungCapDAO nhaCungCapDAO;

	@Autowired
	private NhaCungCapValidator nhaCungCapValidator;

	@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {

		// Form mục tiêu
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}

		if (target.getClass() == NhaCungCap.class) {
			dataBinder.setValidator(nhaCungCapValidator);
		}
	}

	@RequestMapping("/nhacungcap/danhsach")
	public String danhSachNhaCungCap(Model model) {
		try {
			// Lấy danh sách nhà cung cấp
			List<NhaCungCap> nhaCungCapDs = nhaCungCapDAO.danhSachNhaCungCap();
			model.addAttribute("nhaCungCapDs", nhaCungCapDs);

			model.addAttribute("tab", "tabDSNCC");
			return "danhSachNhaCungCap";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/nhacungcap/xem/{id}")
	public String xemNhaCungCap(@PathVariable("id") int maNcc, Model model) {
		try {
			NhaCungCap nhaCungCap = nhaCungCapDAO.layNhaCungCap(maNcc);
			model.addAttribute("nhaCungCap", nhaCungCap);

			model.addAttribute("tab", "tabDSNCC");
			return "xemNhaCungCap";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/nhacungcap/sua/{id}")
	public String suaNhaCungCap(@PathVariable("id") int maNcc, Model model) {
		try {
			NhaCungCap nhaCungCap = nhaCungCapDAO.layNhaCungCap(maNcc);
			model.addAttribute("mainFinanceForm", nhaCungCap);

			model.addAttribute("tab", "tabDSNCC");
			return "suaNhaCungCap";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/nhacungcap/taomoi")
	public String taoMoiNhaCungCap(Model model) {
		try {
			NhaCungCap nhaCungCap = new NhaCungCap();
			model.addAttribute("mainFinanceForm", nhaCungCap);

			model.addAttribute("tab", "tabDSNCC");
			return "taoMoiNhaCungCap";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/nhacungcap/luu")
	public String luuTaoMoiNhaCungCap(@ModelAttribute("mainFinanceForm") @Validated NhaCungCap nhaCungCap,
			BindingResult result, Model model) {
		try {
			logger.info("Nhà cung cấp: " + nhaCungCap);
			if (result.hasErrors()) {
				model.addAttribute("mainFinanceForm", nhaCungCap);
				model.addAttribute("tab", "tabDSNCC");

				if (nhaCungCap.getMaNcc() > 0) {
					// Đây là trường hợp sửa NCC
					return "suaNhaCungCap";
				} else {
					// Đây là trường hợp tạo mới NCC
					return "taoMoiNhaCungCap";
				}
			}

			// Khi người dùng đã nhập đúng thông tin
			nhaCungCapDAO.luuCapNhatNhaCungCap(nhaCungCap);

			if (nhaCungCap.getMaNcc() > 0) {
				// Đây là trường hợp sửa NCC
				return "redirect:/nhacungcap/xem/" + nhaCungCap.getMaNcc();
			} else {
				// Đây là trường hợp tạo mới NCC
				return "redirect:/nhacungcap/danhsach";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/nhacungcap/xoa/{id}")
	public String xoaNhaCungCap(@PathVariable("id") int maNcc, Model model) {
		try {
			// Xóa nhà cung cấp có MA_NCC là maNcc
			nhaCungCapDAO.xoaNhaCungCap(maNcc);

			return "redirect:/nhacungcap/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
}
