package com.idi.finance.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.idi.finance.bean.bieudo.KpiGroup;
import com.idi.finance.bean.hanghoa.DonVi;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.hanghoa.KhoBai;
import com.idi.finance.bean.hanghoa.NhomHangHoa;
import com.idi.finance.dao.HangHoaDAO;
import com.idi.finance.dao.KpiChartDAO;
import com.idi.finance.form.BalanceAssetForm;
import com.idi.finance.validator.DonViValidator;
import com.idi.finance.validator.HangHoaValidator;
import com.idi.finance.validator.KhoBaiValidator;
import com.idi.finance.validator.NhomHangHoaValidator;

@Controller
public class HangHoaController {
	private static final Logger logger = Logger.getLogger(HangHoaController.class);

	@Autowired
	KpiChartDAO kpiChartDAO;

	@Autowired
	HangHoaDAO hangHoaDAO;

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
		} else if (target.getClass() == NhomHangHoa.class) {
			binder.setValidator(nhomHangHoaValidator);
		} else if (target.getClass() == HangHoa.class) {
			binder.setValidator(hangHoaValidator);
		} else if (target.getClass() == KhoBai.class) {
			binder.setValidator(khoBaiValidator);
		}
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

	@RequestMapping("/hanghoa/donvi/sua/{id}")
	public String suaDonVi(@PathVariable("id") int maDv, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

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
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

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

	@RequestMapping("/hanghoa/nhom/sua/{id}")
	public String suaNhomHangHoa(@PathVariable("id") int maNhomHh, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			NhomHangHoa nhomHangHoa = hangHoaDAO.layNhomHangHoa(maNhomHh);
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
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			NhomHangHoa nhomHangHoa = new NhomHangHoa();

			return chuanBiFormNhomHangHoa(model, nhomHangHoa);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/hanghoa/nhom/luu", method = RequestMethod.POST)
	public String luuTaoMoiNhomHangHoa(@ModelAttribute("mainFinanceForm") @Validated NhomHangHoa nhomHangHoa,
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

	private String chuanBiFormNhomHangHoa(Model model, NhomHangHoa nhomHangHoa) {
		try {
			model.addAttribute("mainFinanceForm", nhomHangHoa);

			List<NhomHangHoa> nhomHangHoaDs = hangHoaDAO.danhSachNhomHangHoa();
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
			// Xóa đơn vị tính có MA_DV=maDv
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
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			List<HangHoa> hangHoaDs = hangHoaDAO.danhSachHangHoa();
			model.addAttribute("hangHoaDs", hangHoaDs);

			model.addAttribute("tab", "tabDSHH");
			return "danhSachHangHoa";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/hanghoa/sua/{id}")
	public String suaHangHoa(@PathVariable("id") int maHh, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

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
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

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
				return "redirect:/hanghoa/danhsach";
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

			List<NhomHangHoa> nhomHangHoaDs = hangHoaDAO.danhSachNhomHangHoa();
			model.addAttribute("nhomHangHoaDs", nhomHangHoaDs);

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
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			List<KhoBai> khoBaiDs = hangHoaDAO.danhSachKhoBai();
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
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			KhoBai khoBai = hangHoaDAO.layKhoBai(maKho);
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
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			KhoBai khoBai = new KhoBai();

			return chuanBiFormKhoBai(model, khoBai);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/hanghoa/kho/luu", method = RequestMethod.POST)
	public String luuTaoMoiKhoBai(@ModelAttribute("mainFinanceForm") @Validated KhoBai khoBai, BindingResult result,
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

	private String chuanBiFormKhoBai(Model model, KhoBai khoBai) {
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
