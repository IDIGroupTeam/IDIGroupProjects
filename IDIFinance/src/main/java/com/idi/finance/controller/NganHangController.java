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

import com.idi.finance.bean.DungChung;
import com.idi.finance.bean.doitac.NganHang;
import com.idi.finance.bean.doitac.NganHangTaiKhoan;
import com.idi.finance.bean.kyketoan.KyKeToan;
import com.idi.finance.dao.NganHangDAO;
import com.idi.finance.validator.NganHangTaiKhoanValidator;
import com.idi.finance.validator.NganHangValidator;

@Controller
public class NganHangController {
	private static final Logger logger = Logger.getLogger(NganHangController.class);

	@Autowired
	DungChung dungChung;

	@Autowired
	NganHangDAO nganHangDAO;

	@Autowired
	NganHangValidator nganHangValidator;

	@Autowired
	NganHangTaiKhoanValidator nganHangTaiKhoanValidator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// Form mục tiêu
		Object target = binder.getTarget();
		if (target == null) {
			return;
		}

		if (target.getClass() == NganHang.class) {
			binder.setValidator(nganHangValidator);
		}

		if (target.getClass() == NganHangTaiKhoan.class) {
			binder.setValidator(nganHangTaiKhoanValidator);
		}
	}

	@RequestMapping("/nganhang/danhsach")
	public String danhSachNganHang(Model model) {
		try {
			// Lấy danh sách ngân hàng
			List<NganHang> nganHangDs = nganHangDAO.danhSachNganHang();
			model.addAttribute("nganHangDs", nganHangDs);

			model.addAttribute("tab", "tabDSNH");
			return "danhSachNganHang";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/nganhang/sua/{id}")
	public String suaNganHang(@PathVariable("id") int maNh, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/nganhang/danhsach";
			}

			NganHang nganHang = nganHangDAO.layNganHang(maNh);
			if (nganHang == null) {
				return "redirect:/nganhang/danhsach";
			}

			return chuanBiFormNganHang(model, nganHang);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/nganhang/taomoi")
	public String taoMoiNganHang(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/nganhang/danhsach";
			}

			NganHang nganHang = new NganHang();

			return chuanBiFormNganHang(model, nganHang);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/nganhang/luu", method = RequestMethod.POST)
	public String luuTaoMoiNganHang(@ModelAttribute("mainFinanceForm") @Validated NganHang nganHang,
			BindingResult result, Model model) {
		try {
			if (result.hasErrors()) {
				return chuanBiFormNganHang(model, nganHang);
			}

			logger.info("Lưu ngân hàng: " + nganHang);

			if (nganHang.getMaNh() > 0) {
				nganHangDAO.capNhatNganHang(nganHang);
			} else {
				nganHangDAO.themNganHang(nganHang);
			}

			return "redirect:/nganhang/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormNganHang(Model model, NganHang nganHang) {
		try {
			model.addAttribute("mainFinanceForm", nganHang);

			model.addAttribute("tab", "tabDSNH");
			if (nganHang.getMaNh() > 0) {
				// Đây là trường hợp sửa NH
				return "suaNganHang";
			} else {
				// Đây là trường hợp tạo mới NH
				return "taoMoiNganHang";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/nganhang/xoa/{id}")
	public String xoaNganHang(@PathVariable("id") int maNh, Model model) {
		try {
			// Xóa ngân hàng và các tài khoản ngân hàng tương ứng
			nganHangDAO.xoaNganHang(maNh);

			return "redirect:/nganhang/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/nganhang/taikhoan/danhsach")
	public String danhSachTaiKhoanNganHang(Model model) {
		try {
			// Lấy danh sách ngân hàng
			List<NganHangTaiKhoan> nganHangTaiKhoanDs = nganHangDAO.danhSachTaiKhoanNganHang();
			model.addAttribute("nganHangTaiKhoanDs", nganHangTaiKhoanDs);

			model.addAttribute("tab", "tabDSNHTK");
			return "danhSachTaiKhoanNganHang";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/nganhang/taikhoan/sua/{id}")
	public String suaTaiKhoanNganHang(@PathVariable("id") int maTk, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/nganhang/taikhoan/danhsach";
			}

			NganHangTaiKhoan nganHangTaiKhoan = nganHangDAO.layTaiKhoanNganHang(maTk);

			if (nganHangTaiKhoan == null) {
				return "redirect:/nganhang/taikhoan/danhsach";
			}

			return chuanBiFormTaiKhoanNganHang(model, nganHangTaiKhoan);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/nganhang/taikhoan/taomoi")
	public String taoMoiTaiKhoanNganHang(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/nganhang/taikhoan/danhsach";
			}

			NganHangTaiKhoan nganHangTaiKhoan = new NganHangTaiKhoan();

			return chuanBiFormTaiKhoanNganHang(model, nganHangTaiKhoan);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/nganhang/taikhoan/luu", method = RequestMethod.POST)
	public String luuTaoMoiTaiKhoanNganHang(
			@ModelAttribute("mainFinanceForm") @Validated NganHangTaiKhoan nganHangTaiKhoan, BindingResult result,
			Model model) {
		try {
			if (result.hasErrors()) {
				return chuanBiFormTaiKhoanNganHang(model, nganHangTaiKhoan);
			}

			logger.info("Lưu tài khoản ngân hàng: " + nganHangTaiKhoan);

			if (nganHangTaiKhoan.getMaTk() > 0) {
				nganHangDAO.capNhatTaiKhoanNganHang(nganHangTaiKhoan);
			} else {
				nganHangDAO.themTaiKhoanNganHang(nganHangTaiKhoan);
			}

			return "redirect:/nganhang/taikhoan/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormTaiKhoanNganHang(Model model, NganHangTaiKhoan nganHangTaiKhoan) {
		try {
			model.addAttribute("mainFinanceForm", nganHangTaiKhoan);

			// Lấy danh sách ngân hàng
			List<NganHang> nganHangDs = nganHangDAO.danhSachNganHang();
			model.addAttribute("nganHangDs", nganHangDs);

			model.addAttribute("tab", "tabDSNHTK");
			if (nganHangTaiKhoan.getMaTk() > 0) {
				// Đây là trường hợp sửa NHTK
				return "suaTaiKhoanNganHang";
			} else {
				// Đây là trường hợp tạo mới NHTK
				return "taoMoiTaiKhoanNganHang";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/nganhang/taikhoan/xoa/{id}")
	public String xoaNganHangTaiKhoan(@PathVariable("id") int maTk, Model model) {
		try {
			// Xóa tài khoản ngân hàng tương ứng
			nganHangDAO.xoaTaiKhoanNganHang(maTk);

			return "redirect:/nganhang/taikhoan/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
}
