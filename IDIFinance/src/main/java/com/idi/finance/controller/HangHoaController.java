package com.idi.finance.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import com.idi.finance.bean.hanghoa.DonVi;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.hanghoa.KhoHang;
import com.idi.finance.bean.hanghoa.NhomHang;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.HangHoaDAO;
import com.idi.finance.dao.TaiKhoanDAO;
import com.idi.finance.form.BalanceAssetForm;
import com.idi.finance.validator.DonViValidator;
import com.idi.finance.validator.HangHoaValidator;
import com.idi.finance.validator.KhoBaiValidator;
import com.idi.finance.validator.NhomHangHoaValidator;

@Controller
public class HangHoaController {
	private static final Logger logger = Logger.getLogger(HangHoaController.class);

	@Autowired
	DungChung dungChung;

	@Autowired
	HangHoaDAO hangHoaDAO;

	@Autowired
	TaiKhoanDAO taiKhoanDAO;

	@Autowired
	DonViValidator donViValidator;

	@Autowired
	NhomHangHoaValidator nhomHangHoaValidator;

	@Autowired
	HangHoaValidator hangHoaValidator;

	@Autowired
	KhoBaiValidator khoBaiValidator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

		// Form mục tiêu
		Object target = binder.getTarget();
		if (target == null) {
			return;
		}

		if (target.getClass() == DonVi.class) {
			binder.setValidator(donViValidator);
		} else if (target.getClass() == NhomHang.class) {
			binder.setValidator(nhomHangHoaValidator);
		} else if (target.getClass() == HangHoa.class) {
			binder.setValidator(hangHoaValidator);
		} else if (target.getClass() == KhoHang.class) {
			binder.setValidator(khoBaiValidator);
		}
	}

	@RequestMapping("/hanghoa/donvi/danhsach")
	public String danhSachDonViHangHoa(@ModelAttribute("mainFinanceForm") BalanceAssetForm balanceSheetForm,
			Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			List<DonVi> donViDs = hangHoaDAO.danhSachDonViHangHoa();
			List<DonVi> donViPhatSinhDs = hangHoaDAO.danhSachDonViHangHoaPhatSinh();
			List<DonVi> donViSoDuKyDs = hangHoaDAO.danhSachDonViHangHoaSoDuKy();
			if (donViDs != null) {
				for (Iterator<DonVi> iter = donViDs.iterator(); iter.hasNext();) {
					DonVi donVi = iter.next();

					if (donViPhatSinhDs != null && donViPhatSinhDs.contains(donVi)) {
						donVi.setXoa(false);
					}
					if (donViSoDuKyDs != null && donViSoDuKyDs.contains(donVi)) {
						donVi.setXoa(false);
					}
				}
			}

			model.addAttribute("donViDs", donViDs);
			model.addAttribute("tab", "tabDSDVHH");
			return "danhSachDonViHangHoa";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/hanghoa/donvi/sua/{id}")
	public String suaDonVi(@PathVariable("id") int maDv, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			DonVi donVi = hangHoaDAO.layDonVi(maDv);
			if (donVi == null) {
				return "redirect:/hanghoa/donvi/danhsach";
			}

			return chuanBiFormDonVi(model, donVi);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/hanghoa/donvi/taomoi")
	public String taoMoiDonVi(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			DonVi donVi = new DonVi();

			return chuanBiFormDonVi(model, donVi);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/hanghoa/donvi/luu", method = RequestMethod.POST)
	public String luuTaoMoiDonVi(@ModelAttribute("mainFinanceForm") @Validated DonVi donVi, BindingResult result,
			Model model) {
		try {
			if (result.hasErrors()) {
				return chuanBiFormDonVi(model, donVi);
			}

			logger.info("Lưu đơn vị tính: " + donVi);

			if (donVi.getMaDv() > 0) {
				hangHoaDAO.capNhatDonVi(donVi);
				return "redirect:/hanghoa/donvi/danhsach";
			} else {
				hangHoaDAO.themDonVi(donVi);
			}

			return "redirect:/hanghoa/donvi/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormDonVi(Model model, DonVi donVi) {
		try {
			model.addAttribute("mainFinanceForm", donVi);

			model.addAttribute("tab", "tabDSDVHH");
			if (donVi.getMaDv() > 0) {
				// Đây là trường hợp sửa DV
				return "suaDonVi";
			} else {
				// Đây là trường hợp tạo mới DV
				return "taoMoiDonVi";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/hanghoa/donvi/xoa/{id}")
	public String xoaDonVi(@PathVariable("id") int maDv, Model model) {
		try {
			// Xóa đơn vị tính có MA_DV=maDv
			hangHoaDAO.xoaDonVi(maDv);

			return "redirect:/hanghoa/donvi/danhsach";
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
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			NhomHang nhomHangHoa = new NhomHang();
			nhomHangHoa = hangHoaDAO.danhSachNhomHangHoa(nhomHangHoa);
			List<NhomHang> nhomHangHoaPhatSinhDs = hangHoaDAO.danhSachNhomHangHoaPhatSinh();
			List<NhomHang> nhomHangHoaChaDs = hangHoaDAO.danhSachNhomHangHoaCha();
			kiemTraXoaNhomHangHoa(nhomHangHoa, nhomHangHoaPhatSinhDs, nhomHangHoaChaDs);

			model.addAttribute("nhomHangHoa", nhomHangHoa);
			model.addAttribute("tab", "tabDSNHH");
			return "danhSachNhomHangHoa";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private void kiemTraXoaNhomHangHoa(NhomHang nhomHangHoa, List<NhomHang> nhomHangHoaPhatSinhDs,
			List<NhomHang> nhomHangHoaChaDs) {
		logger.info("Kiểm tra nhóm hàng hóa có xóa được không: ");
		logger.info("Nhóm hàng hóa: " + nhomHangHoa);
		logger.info("Danh sách phát sinh: " + nhomHangHoaPhatSinhDs);
		logger.info("Danh sách cha: " + nhomHangHoaChaDs);
		if (nhomHangHoa != null) {
			if (nhomHangHoaPhatSinhDs != null && nhomHangHoaPhatSinhDs.contains(nhomHangHoa)) {
				nhomHangHoa.setXoa(false);
			}
			if (nhomHangHoaChaDs != null && nhomHangHoaChaDs.contains(nhomHangHoa)) {
				nhomHangHoa.setXoa(false);
			}

			logger.info("Kết quả: " + nhomHangHoa.isXoa());
			logger.info("Tiếp tục kiểm tra cấp con ... ");
			if (nhomHangHoa.getNhomHhDs() != null) {
				logger.info("Có số nhóm hàng hóa con: " + nhomHangHoa.getNhomHhDs().size());
				for (Iterator<NhomHang> iter = nhomHangHoa.getNhomHhDs().iterator(); iter.hasNext();) {
					NhomHang nhomHangHoaCon = iter.next();
					kiemTraXoaNhomHangHoa(nhomHangHoaCon, nhomHangHoaPhatSinhDs, nhomHangHoaChaDs);
				}
			}
		}
	}

	@RequestMapping("/hanghoa/nhom/sua/{id}")
	public String suaNhomHangHoa(@PathVariable("id") int maNhomHh, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			NhomHang nhomHangHoa = hangHoaDAO.layNhomHangHoa(maNhomHh);
			if (nhomHangHoa == null) {
				return "redirect:/hanghoa/nhom/danhsach";
			}

			return chuanBiFormNhomHangHoa(model, nhomHangHoa);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/hanghoa/nhom/taomoi")
	public String taoMoiNhomHangHoa(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			NhomHang nhomHangHoa = new NhomHang();

			return chuanBiFormNhomHangHoa(model, nhomHangHoa);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/hanghoa/nhom/luu", method = RequestMethod.POST)
	public String luuTaoMoiNhomHangHoa(@ModelAttribute("mainFinanceForm") @Validated NhomHang nhomHangHoa,
			BindingResult result, Model model) {
		try {
			if (result.hasErrors()) {
				return chuanBiFormNhomHangHoa(model, nhomHangHoa);
			}

			logger.info("Lưu nhóm hàng hóa: " + nhomHangHoa);

			if (nhomHangHoa.getMaNhomHh() > 0) {
				hangHoaDAO.capNhatNhomHangHoa(nhomHangHoa);
				return "redirect:/hanghoa/nhom/danhsach";
			} else {
				hangHoaDAO.themNhomHangHoa(nhomHangHoa);
			}

			return "redirect:/hanghoa/nhom/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormNhomHangHoa(Model model, NhomHang nhomHangHoa) {
		try {
			model.addAttribute("mainFinanceForm", nhomHangHoa);

			List<NhomHang> nhomHangHoaDs = hangHoaDAO.danhSachNhomHangHoa();
			model.addAttribute("nhomHangHoaDs", nhomHangHoaDs);

			model.addAttribute("tab", "tabDSNHH");
			if (nhomHangHoa.getMaNhomHh() > 0) {
				// Đây là trường hợp sửa NHH
				return "suaNhomHangHoa";
			} else {
				// Đây là trường hợp tạo mới NHH
				return "taoMoiNhomHangHoa";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/hanghoa/nhom/xoa/{id}")
	public String xoaNhomHangHoa(@PathVariable("id") int maNhomHh, Model model) {
		try {
			// Xóa đơn vị tính có MA_NHOM_HH=maNhomHh
			hangHoaDAO.xoaNhomHangHoa(maNhomHh);

			return "redirect:/hanghoa/nhom/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/hanghoa/danhsach")
	public String danhSachHangHoa(@ModelAttribute("mainFinanceForm") BalanceAssetForm balanceSheetForm, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			List<HangHoa> hangHoaDs = hangHoaDAO.danhSachHangHoa();

			model.addAttribute("hangHoaDs", hangHoaDs);
			model.addAttribute("tab", "tabDSHH");
			return "danhSachHangHoa";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/hanghoa/xem/{id}")
	public String xemHangHoa(@PathVariable("id") int maHh, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			HangHoa hangHoa = hangHoaDAO.layHangHoa(maHh);
			if (hangHoa == null) {
				return "redirect:/hanghoa/danhsach";
			}

			List<HangHoa> hangHoaPhatSinhDs = hangHoaDAO.danhSachHangHoaPhatSinh();
			if (hangHoaPhatSinhDs != null && hangHoaPhatSinhDs.contains(hangHoa)) {
				hangHoa.setXoa(false);
			}

			model.addAttribute("hangHoa", hangHoa);
			model.addAttribute("tab", "tabDSHH");
			return "xemHangHoa";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/hanghoa/sua/{id}")
	public String suaHangHoa(@PathVariable("id") int maHh, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			HangHoa hangHoa = hangHoaDAO.layHangHoa(maHh);
			if (hangHoa == null) {
				return "redirect:/hanghoa/danhsach";
			}

			return chuanBiFormHangHoa(model, hangHoa);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/hanghoa/taomoi")
	public String taoMoiHangHoa(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			HangHoa hangHoa = new HangHoa();

			return chuanBiFormHangHoa(model, hangHoa);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/hanghoa/luu", method = RequestMethod.POST)
	public String luuTaoMoiHangHoa(@ModelAttribute("mainFinanceForm") @Validated HangHoa hangHoa, BindingResult result,
			Model model) {
		try {
			if (result.hasErrors()) {
				return chuanBiFormHangHoa(model, hangHoa);
			}

			logger.info("Lưu hàng hóa: " + hangHoa);

			if (hangHoa.getMaHh() > 0) {
				hangHoaDAO.capNhatHangHoa(hangHoa);
				return "redirect:/hanghoa/xem/" + hangHoa.getMaHh();
			} else {
				hangHoaDAO.themHangHoa(hangHoa);
			}

			return "redirect:/hanghoa/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormHangHoa(Model model, HangHoa hangHoa) {
		try {
			model.addAttribute("mainFinanceForm", hangHoa);

			List<DonVi> donViDs = hangHoaDAO.danhSachDonViHangHoa();
			model.addAttribute("donViDs", donViDs);

			List<NhomHang> nhomHangHoaDs = hangHoaDAO.danhSachNhomHangHoa();
			model.addAttribute("nhomHangHoaDs", nhomHangHoaDs);

			List<KhoHang> khoBaiDs = hangHoaDAO.danhSachKhoBai();
			model.addAttribute("khoBaiDs", khoBaiDs);

			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();
			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);

			model.addAttribute("tab", "tabDSHH");
			if (hangHoa.getMaHh() > 0) {
				// Đây là trường hợp sửa HH
				return "suaHangHoa";
			} else {
				// Đây là trường hợp tạo mới HH
				return "taoMoiHangHoa";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/hanghoa/xoa/{id}")
	public String xoaHangHoa(@PathVariable("id") int maHh, Model model) {
		try {
			// Xóa đơn vị tính có MA_DV=maDv
			hangHoaDAO.xoaHangHoa(maHh);

			return "redirect:/hanghoa/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/hanghoa/kho/danhsach")
	public String danhSachKhoBai(@ModelAttribute("mainFinanceForm") BalanceAssetForm balanceSheetForm, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			List<KhoHang> khoBaiDs = hangHoaDAO.danhSachKhoBai();

			List<KhoHang> khoBaiPsDs = hangHoaDAO.danhSachKhoBaiPhatSinh();
			List<KhoHang> khoBaiMdDs = hangHoaDAO.danhSachKhoBaiMacDinh();
			for (Iterator<KhoHang> iter = khoBaiDs.iterator(); iter.hasNext();) {
				KhoHang khoHang = iter.next();
				if (khoBaiPsDs.contains(khoHang) || khoBaiMdDs.contains(khoHang)) {
					khoHang.setXoa(false);
				}
			}

			model.addAttribute("khoBaiDs", khoBaiDs);
			logger.info(khoBaiDs);
			model.addAttribute("tab", "tabDSK");
			return "danhSachKhoBai";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/hanghoa/kho/sua/{id}")
	public String suaKhoBai(@PathVariable("id") int maKho, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			KhoHang khoBai = hangHoaDAO.layKhoBai(maKho);
			if (khoBai == null) {
				return "redirect:/hanghoa/kho/danhsach";
			}

			return chuanBiFormKhoBai(model, khoBai);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/hanghoa/kho/taomoi")
	public String taoMoiKhoBai(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			KhoHang khoBai = new KhoHang();

			return chuanBiFormKhoBai(model, khoBai);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/hanghoa/kho/luu", method = RequestMethod.POST)
	public String luuTaoMoiKhoBai(@ModelAttribute("mainFinanceForm") @Validated KhoHang khoBai, BindingResult result,
			Model model) {
		try {
			if (result.hasErrors()) {
				return chuanBiFormKhoBai(model, khoBai);
			}

			logger.info("Lưu kho bãi: " + khoBai);

			if (khoBai.getMaKho() > 0) {
				hangHoaDAO.capNhatKhoBai(khoBai);
				return "redirect:/hanghoa/kho/danhsach";
			} else {
				hangHoaDAO.themKhoBai(khoBai);
			}

			return "redirect:/hanghoa/kho/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormKhoBai(Model model, KhoHang khoBai) {
		try {
			model.addAttribute("mainFinanceForm", khoBai);

			model.addAttribute("tab", "tabDSK");
			if (khoBai.getMaKho() > 0) {
				// Đây là trường hợp sửa HH
				return "suaKhoBai";
			} else {
				// Đây là trường hợp tạo mới HH
				return "taoMoiKhoBai";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/hanghoa/kho/xoa/{id}")
	public String xoaKhoBai(@PathVariable("id") int maKho, Model model) {
		try {
			// Xóa đơn vị tính có MA_DV=maDv
			hangHoaDAO.xoaKhoBai(maKho);

			return "redirect:/hanghoa/kho/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
}
