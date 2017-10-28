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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idi.finance.bean.KhachHang;
import com.idi.finance.bean.LoaiTien;
import com.idi.finance.bean.NhaCungCap;
import com.idi.finance.bean.NhanVien;
import com.idi.finance.bean.bieudo.KpiGroup;
import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.TaiKhoan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.ChungTuDAO;
import com.idi.finance.dao.KhachHangDAO;
import com.idi.finance.dao.KpiChartDAO;
import com.idi.finance.dao.NhaCungCapDAO;
import com.idi.finance.dao.NhanVienDAO;
import com.idi.finance.dao.TaiKhoanDAO;
import com.idi.finance.validator.ChungTuValidator;

@Controller
public class ChungTuController {
	private static final Logger logger = Logger.getLogger(ChungTuController.class);

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
	private ChungTuValidator chungTuValidator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

		// Form mục tiêu
		Object target = binder.getTarget();
		if (target == null) {
			return;
		}

		if (target.getClass() == ChungTu.class) {
			binder.setValidator(chungTuValidator);
		}
	}

	@RequestMapping("/danhsachphieuthu")
	public String danhSachPhieuThu(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			// Lấy danh sách phiếu thu
			List<ChungTu> phieuThuDs = chungTuDAO.danhSachChungTuTheoLoaiCt(ChungTu.CHUNG_TU_PHIEU_THU);
			model.addAttribute("phieuThuDs", phieuThuDs);

			model.addAttribute("tab", "tabCTPT");
			return "danhSachPhieuThu";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/xemphieuthu/{id}")
	public String xemPhieuThu(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_THU);
			if (chungTu == null) {
				return "redirect:/danhsachphieuthu";
			}
			model.addAttribute("chungTu", chungTu);

			model.addAttribute("tab", "tabCTPT");
			return "xemPhieuThu";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/suaphieuthu/{id}")
	public String suaPhieuThu(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_THU);
			if (chungTu == null) {
				return "redirect:/danhsachphieuthu";
			}

			return chuanBiFormPhieuThu(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/taomoiphieuthu")
	public String taoMoiPhieuThu(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			ChungTu chungTu = new ChungTu();
			chungTu.setLoaiCt(ChungTu.CHUNG_TU_PHIEU_THU);

			// Lấy số phiếu thu của năm hiện tại
			int soPhieuThu = chungTuDAO.demSoChungTuTheoLoaiCtVaNam(ChungTu.CHUNG_TU_PHIEU_THU, new Date());
			soPhieuThu++;
			chungTu.setSoCt(soPhieuThu);

			Date ngayLap = new Date();
			chungTu.setNgayLap(ngayLap);
			chungTu.setNgayHt(ngayLap);

			// Tài khoản tiềm mặt, ghi nợ
			TaiKhoan taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			taiKhoan.setGhiNo(0);
			chungTu.themTaiKhoan(taiKhoan);

			// Tài khoản khác, ghi có
			taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			taiKhoan.setGhiNo(1);
			chungTu.themTaiKhoan(taiKhoan);

			return chuanBiFormPhieuThu(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/luutaomoiphieuthu", method = RequestMethod.POST)
	public String luuTaoMoiPhieuThu(@ModelAttribute("mainFinanceForm") @Validated ChungTu chungTu, BindingResult result,
			Model model) {
		try {
			if (result.hasErrors()) {
				return chuanBiFormPhieuThu(model, chungTu);
			}

			logger.info("Lưu chứng từ: " + chungTu);

			chungTu.setLoaiCt(ChungTu.CHUNG_TU_PHIEU_THU);

			if (chungTu.getMaCt() > 0) {
				chungTuDAO.capNhatChungTu(chungTu);
			} else {
				chungTuDAO.themChungTu(chungTu);
			}

			return "redirect:/danhsachphieuthu";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormPhieuThu(Model model, ChungTu chungTu) {
		try {
			model.addAttribute("mainFinanceForm", chungTu);

			// Lấy danh sách các loại tiền
			List<LoaiTien> loaiTienDs = chungTuDAO.danhSachLoaiTien();
			model.addAttribute("loaiTienDs", loaiTienDs);

			// Lấy danh sách tài khoản tiền mặt, dùng cho bên nợ
			List<LoaiTaiKhoan> loaiTaiKhoanTmDs = taiKhoanDAO.danhSachTaiKhoanTheoCap1(LoaiTaiKhoan.TIEN_MAT);
			model.addAttribute("loaiTaiKhoanTmDs", loaiTaiKhoanTmDs);

			// Lấy danh sách tài khoản, dùng cho bên có
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();
			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);

			model.addAttribute("tab", "tabCTPT");
			if (chungTu.getMaCt() > 0) {
				// Đây là trường hợp sửa CT
				return "suaPhieuThu";
			} else {
				// Đây là trường hợp tạo mới CT
				return "taoMoiPhieuThu";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/xoaphieuthu/{id}")
	public String xoaPhieuThu(@PathVariable("id") int maCt, Model model) {
		try {
			// Xóa phiếu thu có MA_CT là maCt, LOAI_CT là ChungTu.CHUNG_TU_PHIEU_THU
			chungTuDAO.xoaChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_THU);

			return "redirect:/danhsachphieuthu";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/danhsachphieuchi")
	public String danhSachPhieuChi(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			// Lấy danh sách phiếu chi
			List<ChungTu> phieuChiDs = chungTuDAO.danhSachChungTuTheoLoaiCt(ChungTu.CHUNG_TU_PHIEU_CHI);
			model.addAttribute("phieuChiDs", phieuChiDs);

			model.addAttribute("tab", "tabCTPC");
			return "danhSachPhieuChi";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/xemphieuchi/{id}")
	public String xemPhieuChi(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_CHI);
			if (chungTu == null) {
				return "redirect:/danhsachphieuthu";
			}
			model.addAttribute("chungTu", chungTu);

			model.addAttribute("tab", "tabCTPC");
			return "xemPhieuChi";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/suaphieuchi/{id}")
	public String suaPhieuChi(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_CHI);
			if (chungTu == null) {
				return "redirect:/danhsachphieuthu";
			}

			return chuanBiFormPhieuChi(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/taomoiphieuchi")
	public String taoMoiPhieuChi(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			ChungTu chungTu = new ChungTu();
			chungTu.setLoaiCt(ChungTu.CHUNG_TU_PHIEU_CHI);

			// Lấy số phiếu thu của năm hiện tại
			int soPhieuChi = chungTuDAO.demSoChungTuTheoLoaiCtVaNam(ChungTu.CHUNG_TU_PHIEU_CHI, new Date());
			soPhieuChi++;

			chungTu.setSoCt(soPhieuChi);
			Date ngayLap = new Date();
			chungTu.setNgayLap(ngayLap);
			chungTu.setNgayHt(ngayLap);

			// Tài khoản khác, ghi nợ
			TaiKhoan taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			taiKhoan.setGhiNo(1);
			chungTu.themTaiKhoan(taiKhoan);

			// Tài khoản tiềm mặt, ghi có
			taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			taiKhoan.setGhiNo(0);
			chungTu.themTaiKhoan(taiKhoan);

			return chuanBiFormPhieuChi(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/luutaomoiphieuchi", method = RequestMethod.POST)
	public String luuTaoMoiPhieuChi(@ModelAttribute("mainFinanceForm") @Validated ChungTu chungTu, BindingResult result,
			Model model) {
		try {
			if (result.hasErrors()) {
				return chuanBiFormPhieuChi(model, chungTu);
			}

			logger.info("Lưu chứng từ: " + chungTu);

			chungTu.setLoaiCt(ChungTu.CHUNG_TU_PHIEU_CHI);

			if (chungTu.getMaCt() > 0) {
				chungTuDAO.capNhatChungTu(chungTu);
			} else {
				chungTuDAO.themChungTu(chungTu);
			}

			return "redirect:/danhsachphieuchi";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormPhieuChi(Model model, ChungTu chungTu) {
		try {
			model.addAttribute("mainFinanceForm", chungTu);

			// Lấy danh sách các loại tiền
			List<LoaiTien> loaiTienDs = chungTuDAO.danhSachLoaiTien();
			model.addAttribute("loaiTienDs", loaiTienDs);

			// Lấy danh sách tài khoản tiền mặt, dùng cho bên có
			List<LoaiTaiKhoan> loaiTaiKhoanTmDs = taiKhoanDAO.danhSachTaiKhoanTheoCap1(LoaiTaiKhoan.TIEN_MAT);
			model.addAttribute("loaiTaiKhoanTmDs", loaiTaiKhoanTmDs);

			// Lấy danh sách tài khoản, dùng cho bên nợ
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();
			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);

			model.addAttribute("tab", "tabCTPC");
			if (chungTu.getMaCt() > 0) {
				// Đây là trường hợp sửa CT
				return "suaPhieuChi";
			} else {
				// Đây là trường hợp tạo mới CT
				return "taoMoiPhieuChi";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/xoaphieuchi/{id}")
	public String xoaPhieuChi(@PathVariable("id") int maCt, Model model) {
		try {
			// Xóa phiếu thu có MA_CT là maCt, LOAI_CT là ChungTu.CHUNG_TU_PHIEU_CHI
			chungTuDAO.xoaChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_CHI);

			return "redirect:/danhsachphieuchi";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/danhsachbaono")
	public String danhSachBaoNo(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			// Lấy danh sách phiếu chi
			List<ChungTu> baoNoDs = chungTuDAO.danhSachChungTuTheoLoaiCt(ChungTu.CHUNG_TU_BAO_NO);
			model.addAttribute("baoNoDs", baoNoDs);

			model.addAttribute("tab", "tabCTBN");
			return "danhSachBaoNo";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/taomoibaono")
	public String taoMoiBaoNo(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			model.addAttribute("tab", "tabCTBN");
			return "taoMoiBaoNo";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/danhsachbaoco")
	public String danhSachBaoCo(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			// Lấy danh sách phiếu chi
			List<ChungTu> baoCoDs = chungTuDAO.danhSachChungTuTheoLoaiCt(ChungTu.CHUNG_TU_BAO_CO);
			model.addAttribute("baoCoDs", baoCoDs);

			model.addAttribute("tab", "tabCTBC");
			return "danhSachBaoCo";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/taomoibaoco")
	public String taoMoiBaoCo(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			model.addAttribute("tab", "tabCTBC");
			return "taoMoiBaoCo";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	// Những hàm sau dùng cho các ajax request
	@RequestMapping("/chungtu/nhanvien")
	public @ResponseBody List<NhanVien> danhSachNhanVien(@RequestParam("query") String maHoacTen) {
		List<NhanVien> nhanVienDs = nhanVienDAO.layNhanVienTheoMaHoacTen(maHoacTen);
		return nhanVienDs;
	}

	@RequestMapping("/chungtu/khachhang")
	public @ResponseBody List<KhachHang> danhSachKhachHang(@RequestParam("query") String maHoacTen) {
		List<KhachHang> khachHangDs = khachHangDAO.layKhachHangTheoMaHoacTen(maHoacTen);
		return khachHangDs;
	}

	@RequestMapping("/chungtu/nhacungcap")
	public @ResponseBody List<NhaCungCap> danhSachNhaCungCap(@RequestParam("query") String maHoacTen) {
		List<NhaCungCap> nhaCungCapDs = nhaCungCapDAO.layNhaCungCapTheoMaHoacTen(maHoacTen);
		return nhaCungCapDs;
	}
}
