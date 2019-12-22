package com.idi.finance.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.idi.finance.bean.CauHinh;
import com.idi.finance.bean.DungChung;
import com.idi.finance.bean.LoaiTien;
import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.KetChuyenButToan;
import com.idi.finance.bean.doituong.DoiTuong;
import com.idi.finance.bean.doituong.KhachHang;
import com.idi.finance.bean.doituong.NhaCungCap;
import com.idi.finance.bean.doituong.NhanVien;
import com.idi.finance.bean.hanghoa.DonGia;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.hanghoa.KhoHang;
import com.idi.finance.bean.kyketoan.KyKeToan;
import com.idi.finance.bean.soketoan.NghiepVuKeToan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.bean.taikhoan.TaiKhoan;
import com.idi.finance.dao.BaoCaoDAO;
import com.idi.finance.dao.CauHinhDAO;
import com.idi.finance.dao.ChungTuDAO;
import com.idi.finance.dao.HangHoaDAO;
import com.idi.finance.dao.KhachHangDAO;
import com.idi.finance.dao.KhoHangDAO;
import com.idi.finance.dao.KyKeToanDAO;
import com.idi.finance.dao.LoaiTienDAO;
import com.idi.finance.dao.NhaCungCapDAO;
import com.idi.finance.dao.NhanVienDAO;
import com.idi.finance.dao.SoKeToanDAO;
import com.idi.finance.dao.TaiKhoanDAO;
import com.idi.finance.form.ChungTuForm;
import com.idi.finance.hangso.Contants;
import com.idi.finance.hangso.PropCont;
import com.idi.finance.utils.Utils;
import com.idi.finance.validator.ChungTuValidator;
import com.idi.finance.validator.KetChuyenButToanValidator;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
public class ChungTuController {
	private static final Logger logger = Logger.getLogger(ChungTuController.class);

	@Autowired
	MessageSource messageSource;

	@Autowired
	DungChung dungChung;

	@Autowired
	PropCont props;

	@Autowired
	CauHinhDAO cauHinhDAO;

	@Autowired
	TaiKhoanDAO taiKhoanDAO;

	@Autowired
	LoaiTienDAO loaiTienDAO;

	@Autowired
	ChungTuDAO chungTuDAO;

	@Autowired
	SoKeToanDAO soKeToanDAO;

	@Autowired
	KhachHangDAO khachHangDAO;

	@Autowired
	NhaCungCapDAO nhaCungCapDAO;

	@Autowired
	NhanVienDAO nhanVienDAO;

	@Autowired
	BaoCaoDAO baoCaoDAO;

	@Autowired
	HangHoaDAO hangHoaDAO;

	@Autowired
	KhoHangDAO khoHangDAO;

	@Autowired
	KyKeToanDAO kyKeToanDAO;

	@Autowired
	private ChungTuValidator chungTuValidator;

	@Autowired
	private KetChuyenButToanValidator ketChuyenButToanValidator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

		// Form mục tiêu
		Object target = binder.getTarget();
		if (target == null) {
			return;
		}

		if (target.getClass() == ChungTu.class) {
			binder.setValidator(chungTuValidator);
		}

		if (target.getClass() == KetChuyenButToan.class) {
			binder.setValidator(ketChuyenButToanValidator);
		}
	}

	@RequestMapping(value = "/chungtu/phieuthu/danhsach", method = { RequestMethod.GET, RequestMethod.POST })
	public String danhSachPhieuThu(@ModelAttribute("mainFinanceForm") ChungTuForm form, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			KyKeToan kyKeToanLc = null;
			if (form.getKyKeToan() != null) {
				kyKeToanLc = kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt());
			}
			if (kyKeToanLc == null) {
				kyKeToanLc = kyKeToan;
			}
			form.setKyKeToan(kyKeToanLc);

			Date homNay = new Date();
			if (!form.getKyKeToan().getBatDau().after(homNay) && !form.getKyKeToan().getKetThuc().before(homNay)) {
				// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của
				// tháng hiện tại
				if (form.getDau() == null) {
					form.setDau(Utils.getStartDateOfMonth(homNay));
				}

				if (form.getCuoi() == null) {
					form.setCuoi(Utils.getEndDateOfMonth(homNay));
				}
			} else {
				if (form.getDau() == null) {
					form.setDau(form.getKyKeToan().getBatDau());
				}

				if (form.getCuoi() == null) {
					form.setCuoi(form.getKyKeToan().getKetThuc());
				}
			}

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_PHIEU_THU);

			// Lấy danh sách phiếu thu
			List<ChungTu> phieuThuDs = chungTuDAO.danhSachChungTu(loaiCts, form.getDau(), form.getCuoi());

			model.addAttribute("phieuThuDs", phieuThuDs);
			model.addAttribute("kyKeToanDs", kyKeToanDAO.danhSachKyKeToan());
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTPT");
			return "danhSachPhieuThu";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/phieuthu/danhsach/pdf/{dau}/{cuoi}", method = RequestMethod.GET)
	public void pdfDanhSachPhieuThu(HttpServletRequest req, HttpServletResponse res, @PathVariable("dau") String dau,
			@PathVariable("cuoi") String cuoi, Model model) {
		try {
			Date batDau = null;
			Date ketThuc = null;

			SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
			batDau = sdf.parse(dau);
			ketThuc = sdf.parse(cuoi);

			HashMap<String, Object> hmParams = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);
			hmParams.put(Contants.BAT_DAU, batDau);
			hmParams.put(Contants.KET_THUC, batDau);

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_PHIEU_THU);
			List<ChungTu> phieuThuDs = chungTuDAO.danhSachChungTu(loaiCts, batDau, ketThuc);

			JasperReport jasperReport = getCompiledFile("PhieuThuDs", req);
			byte[] bytes = baoCaoDAO.taoChungTu(jasperReport, hmParams, phieuThuDs);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			res.setHeader("Content-disposition", "inline; filename=PhieuThuDs.pdf");
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/chungtu/phieuthu/danhsach/excel/{dau}/{cuoi}", method = RequestMethod.GET)
	public void excelDanhSachPhieuThu(HttpServletRequest req, HttpServletResponse res, @PathVariable("dau") String dau,
			@PathVariable("cuoi") String cuoi, Model model) {
		try {
			Date batDau = null;
			Date ketThuc = null;

			SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
			batDau = sdf.parse(dau);
			ketThuc = sdf.parse(cuoi);

			HashMap<String, Object> hmParams = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);
			hmParams.put(Contants.BAT_DAU, batDau);
			hmParams.put(Contants.KET_THUC, batDau);
			hmParams.put(Contants.COMPANY, props.getCauHinh(PropCont.TEN_CONG_TY).getGiaTri());
			hmParams.put(Contants.DIA_CHI, props.getCauHinh(PropCont.DIA_CHI).getGiaTri());
			hmParams.put(Contants.PAGE_TITLE, "DANH SÁCH PHIẾU THU");
			hmParams.put(Contants.LOAI_CT, "Phiếu thu");

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_PHIEU_THU);
			List<ChungTu> phieuThuDs = chungTuDAO.danhSachChungTu(loaiCts, batDau, ketThuc);

			XSSFWorkbook wb = baoCaoDAO.taoChungTuDs(phieuThuDs, hmParams);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8");
			res.setHeader("Content-disposition", "attachment; filename=PhieuThuDs.xlsx");
			ServletOutputStream out = res.getOutputStream();
			wb.write(out);
			out.flush();
			out.close();
			wb.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/chungtu/phieuthu/xem/{id}")
	public String xemPhieuThu(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_THU);
			if (chungTu == null) {
				return "redirect:/chungtu/phieuthu/danhsach";
			}

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);

			model.addAttribute("chungTu", chungTu);
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTPT");
			return "xemPhieuThu";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/phieuthu/pdf/{id}", method = RequestMethod.GET)
	public void pdfPhieuThu(HttpServletRequest req, HttpServletResponse res, @PathVariable("id") int maCt,
			Model model) {
		try {
			HashMap<String, Object> hmParams = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_THU);
			List<ChungTu> chungTuDs = new ArrayList<>();
			chungTuDs.add(chungTu);

			JasperReport jasperReport = getCompiledFile("PhieuThu", req);
			byte[] bytes = baoCaoDAO.taoChungTu(jasperReport, hmParams, chungTuDs);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			res.setHeader("Content-disposition", "inline; filename=PhieuThu" + maCt + ".pdf");
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/chungtu/phieuthu/sua/{id}")
	public String suaPhieuThu(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_THU);
			if (chungTu == null) {
				return "redirect:/chungtu/phieuthu/danhsach";
			}
			chungTu.setNghiepVu("suaPhieuThu");

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/phieuthu/xem/" + maCt;
			}
			chungTu.setKyKeToan(kyKeToan);

			return chuanBiFormPhieuThu(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/phieuthu/saochep/{id}")
	public String saoChepPhieuThu(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/phieuthu/danhsach";
			}

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_THU);
			if (chungTu == null) {
				return "redirect:/chungtu/phieuthu/danhsach";
			}
			chungTu.setKyKeToan(kyKeToan);

			// Lấy số phiếu thu của năm hiện tại
			int soPhieuThu = chungTuDAO.laySoChungTuLonNhatTheoLoaiCtVaKy(ChungTu.CHUNG_TU_PHIEU_THU,
					kyKeToan.getBatDau(), kyKeToan.getKetThuc());
			soPhieuThu++;
			chungTu.setSoCt(soPhieuThu);
			chungTu.setNghiepVu("saoChepPhieuThu");

			return chuanBiFormPhieuThu(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/phieuthu/taomoi")
	public String taoMoiPhieuThu(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/phieuthu/danhsach";
			}

			ChungTu chungTu = new ChungTu();
			chungTu.setLoaiCt(ChungTu.CHUNG_TU_PHIEU_THU);
			chungTu.setNghiepVu("taoMoiPhieuThu");
			chungTu.setKyKeToan(kyKeToan);

			// Lấy số phiếu thu của năm hiện tại
			int soPhieuThu = chungTuDAO.laySoChungTuLonNhatTheoLoaiCtVaKy(ChungTu.CHUNG_TU_PHIEU_THU,
					kyKeToan.getBatDau(), kyKeToan.getKetThuc());
			soPhieuThu++;
			chungTu.setSoCt(soPhieuThu);

			Date homNay = new Date();
			chungTu.setNgayLap(homNay);
			chungTu.setNgayHt(homNay);

			// Tài khoản tiềm mặt, ghi nợ
			TaiKhoan taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
			loaiTaiKhoan.setMaTk("");
			taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);
			taiKhoan.setSoDu(LoaiTaiKhoan.NO);
			chungTu.themTaiKhoan(taiKhoan);

			// Tài khoản khác, ghi có
			taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			loaiTaiKhoan = new LoaiTaiKhoan();
			loaiTaiKhoan.setMaTk("");
			taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);
			taiKhoan.setSoDu(LoaiTaiKhoan.CO);
			chungTu.themTaiKhoan(taiKhoan);

			return chuanBiFormPhieuThu(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/phieuthu/luu", method = RequestMethod.POST)
	public String luuTaoMoiPhieuThu(@ModelAttribute("mainFinanceForm") @Validated ChungTu chungTu, BindingResult result,
			Model model, HttpServletRequest req) {
		FlashMap flash = RequestContextUtils.getOutputFlashMap(req);
		String message = messageSource.getMessage("Fail.TaoMoi.ChungTu", null, LocaleContextHolder.getLocale());
		String ketQua = "/chungtu/phieuthu/danhsach";
		if (chungTu.getMaCt() > 0) {
			ketQua = "/chungtu/phieuthu/xem/" + chungTu.getMaCt();
			message = messageSource.getMessage("Fail.CapNhat.ChungTu", null, LocaleContextHolder.getLocale());
		}

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
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(message);
			flash.put("message", message);
		}

		return "redirect:" + ketQua;
	}

	private String chuanBiFormPhieuThu(Model model, ChungTu chungTu) {
		try {
			model.addAttribute("mainFinanceForm", chungTu);

			Date homNay = new Date();
			model.addAttribute("homNay", homNay);

			// Lấy danh sách các loại tiền
			List<LoaiTien> loaiTienDs = loaiTienDAO.danhSachLoaiTien();
			model.addAttribute("loaiTienDs", loaiTienDs);

			// Lấy danh sách tài khoản tiền mặt, dùng cho bên nợ
			CauHinh taiKhoanDs = props.getCauHinh(PropCont.PHIEU_THU_DS_TK_NO);
			List<String> maTkDs = Utils.parseString(taiKhoanDs.getGiaTri());
			List<LoaiTaiKhoan> loaiTaiKhoanTmDs = taiKhoanDAO.danhSachTaiKhoanCon(maTkDs);
			model.addAttribute("loaiTaiKhoanTmDs", loaiTaiKhoanTmDs);

			// Lấy danh sách tài khoản, dùng cho bên có
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoanCon();
			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);

			model.addAttribute("tab", "tabCTPT");
			return chungTu.getNghiepVu();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/phieuthu/xoa/{id}")
	public String xoaPhieuThu(@PathVariable("id") int maCt, Model model) {
		ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_THU);
		if (chungTu == null) {
			return "redirect:/chungtu/phieuthu/danhsach";
		}

		KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
		if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/chungtu/phieuthu/xem/" + maCt;
		}

		try {
			// Xóa phiếu thu có MA_CT là maCt, LOAI_CT là ChungTu.CHUNG_TU_PHIEU_THU
			chungTuDAO.xoaChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_THU);

			return "redirect:/chungtu/phieuthu/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/phieuchi/danhsach", method = { RequestMethod.GET, RequestMethod.POST })
	public String danhSachPhieuChi(@ModelAttribute("mainFinanceForm") ChungTuForm form, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			KyKeToan kyKeToanLc = null;
			if (form.getKyKeToan() != null) {
				kyKeToanLc = kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt());
			}
			if (kyKeToanLc == null) {
				kyKeToanLc = kyKeToan;
			}
			form.setKyKeToan(kyKeToanLc);

			Date homNay = new Date();
			if (!form.getKyKeToan().getBatDau().after(homNay) && !form.getKyKeToan().getKetThuc().before(homNay)) {
				// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của
				// tháng hiện tại
				if (form.getDau() == null) {
					form.setDau(Utils.getStartDateOfMonth(homNay));
				}

				if (form.getCuoi() == null) {
					form.setCuoi(Utils.getEndDateOfMonth(homNay));
				}
			} else {
				if (form.getDau() == null) {
					form.setDau(form.getKyKeToan().getBatDau());
				}

				if (form.getCuoi() == null) {
					form.setCuoi(form.getKyKeToan().getKetThuc());
				}
			}

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_PHIEU_CHI);

			// Lấy danh sách phiếu chi
			List<ChungTu> phieuChiDs = chungTuDAO.danhSachChungTu(loaiCts, form.getDau(), form.getCuoi());

			model.addAttribute("phieuChiDs", phieuChiDs);
			model.addAttribute("kyKeToanDs", kyKeToanDAO.danhSachKyKeToan());
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTPC");
			return "danhSachPhieuChi";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/phieuchi/danhsach/pdf/{dau}/{cuoi}", method = RequestMethod.GET)
	public void pdfDanhSachPhieuChi(HttpServletRequest req, HttpServletResponse res, @PathVariable("dau") String dau,
			@PathVariable("cuoi") String cuoi, Model model) {
		try {
			Date batDau = null;
			Date ketThuc = null;

			SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
			batDau = sdf.parse(dau);
			ketThuc = sdf.parse(cuoi);

			HashMap<String, Object> hmParams = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);
			hmParams.put(Contants.BAT_DAU, batDau);
			hmParams.put(Contants.KET_THUC, batDau);

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_PHIEU_CHI);
			List<ChungTu> phieuChiDs = chungTuDAO.danhSachChungTu(loaiCts, batDau, ketThuc);

			JasperReport jasperReport = getCompiledFile("PhieuChiDs", req);
			byte[] bytes = baoCaoDAO.taoChungTu(jasperReport, hmParams, phieuChiDs);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			res.setHeader("Content-disposition", "inline; filename=PhieuChiDs.pdf");
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/chungtu/phieuchi/danhsach/excel/{dau}/{cuoi}", method = RequestMethod.GET)
	public void excelDanhSachPhieuChi(HttpServletRequest req, HttpServletResponse res, @PathVariable("dau") String dau,
			@PathVariable("cuoi") String cuoi, Model model) {
		try {
			Date batDau = null;
			Date ketThuc = null;

			SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
			batDau = sdf.parse(dau);
			ketThuc = sdf.parse(cuoi);

			HashMap<String, Object> hmParams = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);
			hmParams.put(Contants.BAT_DAU, batDau);
			hmParams.put(Contants.KET_THUC, batDau);
			hmParams.put(Contants.COMPANY, props.getCauHinh(PropCont.TEN_CONG_TY).getGiaTri());
			hmParams.put(Contants.DIA_CHI, props.getCauHinh(PropCont.DIA_CHI).getGiaTri());
			hmParams.put(Contants.PAGE_TITLE, "DANH SÁCH PHIẾU CHI");
			hmParams.put(Contants.LOAI_CT, "Phiếu chi");

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_PHIEU_CHI);
			List<ChungTu> phieuChiDs = chungTuDAO.danhSachChungTu(loaiCts, batDau, ketThuc);

			XSSFWorkbook wb = baoCaoDAO.taoChungTuDs(phieuChiDs, hmParams);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8");
			res.setHeader("Content-disposition", "attachment; filename=PhieuChiDs.xlsx");
			ServletOutputStream out = res.getOutputStream();
			wb.write(out);
			out.flush();
			out.close();
			wb.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/chungtu/phieuchi/xem/{id}")
	public String xemPhieuChi(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_CHI);
			if (chungTu == null) {
				return "redirect:/chungtu/phieuchi/danhsach";
			}

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);

			model.addAttribute("chungTu", chungTu);
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTPC");
			return "xemPhieuChi";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/phieuchi/pdf/{id}", method = RequestMethod.GET)
	public void pdfPhieuChi(HttpServletRequest req, HttpServletResponse res, @PathVariable("id") int maCt,
			Model model) {
		try {
			HashMap<String, Object> hmParams = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_CHI);
			List<ChungTu> chungTuDs = new ArrayList<>();
			chungTuDs.add(chungTu);

			JasperReport jasperReport = getCompiledFile("PhieuChi", req);
			byte[] bytes = baoCaoDAO.taoChungTu(jasperReport, hmParams, chungTuDs);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			res.setHeader("Content-disposition", "inline; filename=PhieuChi" + maCt + ".pdf");
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/chungtu/phieuchi/sua/{id}")
	public String suaPhieuChi(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_CHI);
			if (chungTu == null) {
				return "redirect:/chungtu/phieuchi/danhsach";
			}
			chungTu.setNghiepVu("suaPhieuChi");

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/phieuchi/xem/" + maCt;
			}
			chungTu.setKyKeToan(kyKeToan);

			return chuanBiFormPhieuChi(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/phieuchi/saochep/{id}")
	public String saoChepPhieuChi(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/phieuchi/danhsach";
			}

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_CHI);
			if (chungTu == null) {
				return "redirect:/chungtu/phieuchi/danhsach";
			}
			chungTu.setKyKeToan(kyKeToan);

			// Lấy số phiếu chi của năm hiện tại
			int soPhieuChi = chungTuDAO.laySoChungTuLonNhatTheoLoaiCtVaKy(ChungTu.CHUNG_TU_PHIEU_CHI,
					kyKeToan.getBatDau(), kyKeToan.getKetThuc());
			soPhieuChi++;
			chungTu.setSoCt(soPhieuChi);
			chungTu.setNghiepVu("saoChepPhieuChi");

			return chuanBiFormPhieuChi(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/phieuchi/taomoi")
	public String taoMoiPhieuChi(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/phieuchi/danhsach";
			}

			ChungTu chungTu = new ChungTu();
			chungTu.setLoaiCt(ChungTu.CHUNG_TU_PHIEU_CHI);
			chungTu.setNghiepVu("taoMoiPhieuChi");
			chungTu.setKyKeToan(kyKeToan);

			// Lấy số phiếu thu của năm hiện tại
			int soPhieuChi = chungTuDAO.laySoChungTuLonNhatTheoLoaiCtVaKy(ChungTu.CHUNG_TU_PHIEU_CHI,
					kyKeToan.getBatDau(), kyKeToan.getKetThuc());
			soPhieuChi++;
			chungTu.setSoCt(soPhieuChi);

			Date homNay = new Date();
			chungTu.setNgayLap(homNay);
			chungTu.setNgayHt(homNay);

			// Tài khoản khác, ghi nợ
			TaiKhoan taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
			loaiTaiKhoan.setMaTk("");
			taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);
			taiKhoan.setSoDu(LoaiTaiKhoan.NO);
			chungTu.themTaiKhoan(taiKhoan);

			// Tài khoản tiềm mặt, ghi có
			taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			loaiTaiKhoan = new LoaiTaiKhoan();
			loaiTaiKhoan.setMaTk("");
			taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);
			taiKhoan.setSoDu(LoaiTaiKhoan.CO);
			chungTu.themTaiKhoan(taiKhoan);

			return chuanBiFormPhieuChi(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/phieuchi/luu", method = RequestMethod.POST)
	public String luuTaoMoiPhieuChi(@ModelAttribute("mainFinanceForm") @Validated ChungTu chungTu, BindingResult result,
			Model model, HttpServletRequest req) {
		FlashMap flash = RequestContextUtils.getOutputFlashMap(req);
		String message = messageSource.getMessage("Fail.TaoMoi.ChungTu", null, LocaleContextHolder.getLocale());
		String ketQua = "/chungtu/phieuchi/danhsach";
		if (chungTu.getMaCt() > 0) {
			ketQua = "/chungtu/phieuchi/xem/" + chungTu.getMaCt();
			message = messageSource.getMessage("Fail.CapNhat.ChungTu", null, LocaleContextHolder.getLocale());
		}

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
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(message);
			flash.put("message", message);
		}

		return "redirect:" + ketQua;
	}

	private String chuanBiFormPhieuChi(Model model, ChungTu chungTu) {
		try {
			model.addAttribute("mainFinanceForm", chungTu);

			Date homNay = new Date();
			model.addAttribute("homNay", homNay);

			// Lấy danh sách các loại tiền
			List<LoaiTien> loaiTienDs = loaiTienDAO.danhSachLoaiTien();
			model.addAttribute("loaiTienDs", loaiTienDs);

			// Lấy danh sách tài khoản tiền mặt, dùng cho bên có
			CauHinh taiKhoanDs = props.getCauHinh(PropCont.PHIEU_CHI_DS_TK_CO);
			List<String> maTkDs = Utils.parseString(taiKhoanDs.getGiaTri());
			List<LoaiTaiKhoan> loaiTaiKhoanTmDs = taiKhoanDAO.danhSachTaiKhoanCon(maTkDs);
			model.addAttribute("loaiTaiKhoanTmDs", loaiTaiKhoanTmDs);

			// Lấy danh sách tài khoản, dùng cho bên nợ
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoanCon();
			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);

			model.addAttribute("tab", "tabCTPC");
			return chungTu.getNghiepVu();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/phieuchi/xoa/{id}")
	public String xoaPhieuChi(@PathVariable("id") int maCt, Model model) {
		ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_CHI);
		if (chungTu == null) {
			return "redirect:/chungtu/phieuchi/danhsach";
		}

		KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
		if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/chungtu/phieuchi/xem/" + maCt;
		}

		try {
			// Xóa phiếu thu có MA_CT là maCt, LOAI_CT là ChungTu.CHUNG_TU_PHIEU_CHI
			chungTuDAO.xoaChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_CHI);

			return "redirect:/chungtu/phieuchi/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/baoco/danhsach", method = { RequestMethod.GET, RequestMethod.POST })
	public String danhSachBaoCo(@ModelAttribute("mainFinanceForm") ChungTuForm form, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			KyKeToan kyKeToanLc = null;
			if (form.getKyKeToan() != null) {
				kyKeToanLc = kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt());
			}
			if (kyKeToanLc == null) {
				kyKeToanLc = kyKeToan;
			}
			form.setKyKeToan(kyKeToanLc);

			Date homNay = new Date();
			if (!form.getKyKeToan().getBatDau().after(homNay) && !form.getKyKeToan().getKetThuc().before(homNay)) {
				// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của
				// tháng hiện tại
				if (form.getDau() == null) {
					form.setDau(Utils.getStartDateOfMonth(homNay));
				}

				if (form.getCuoi() == null) {
					form.setCuoi(Utils.getEndDateOfMonth(homNay));
				}
			} else {
				if (form.getDau() == null) {
					form.setDau(form.getKyKeToan().getBatDau());
				}

				if (form.getCuoi() == null) {
					form.setCuoi(form.getKyKeToan().getKetThuc());
				}
			}

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_BAO_CO);

			// Lấy danh sách phiếu chi
			List<ChungTu> baoCoDs = chungTuDAO.danhSachChungTu(loaiCts, form.getDau(), form.getCuoi());

			model.addAttribute("baoCoDs", baoCoDs);
			model.addAttribute("kyKeToanDs", kyKeToanDAO.danhSachKyKeToan());
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTBC");
			return "danhSachBaoCo";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/baoco/danhsach/pdf/{dau}/{cuoi}", method = RequestMethod.GET)
	public void pdfDanhSachBaoCo(HttpServletRequest req, HttpServletResponse res, @PathVariable("dau") String dau,
			@PathVariable("cuoi") String cuoi, Model model) {
		try {
			Date batDau = null;
			Date ketThuc = null;

			SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
			batDau = sdf.parse(dau);
			ketThuc = sdf.parse(cuoi);

			HashMap<String, Object> hmParams = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);
			hmParams.put(Contants.BAT_DAU, batDau);
			hmParams.put(Contants.KET_THUC, batDau);

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_BAO_CO);
			List<ChungTu> baoCoDs = chungTuDAO.danhSachChungTu(loaiCts, batDau, ketThuc);

			JasperReport jasperReport = getCompiledFile("BaoCoDs", req);
			byte[] bytes = baoCaoDAO.taoChungTu(jasperReport, hmParams, baoCoDs);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			res.setHeader("Content-disposition", "inline; filename=BaoCoDs.pdf");
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/chungtu/baoco/danhsach/excel/{dau}/{cuoi}", method = RequestMethod.GET)
	public void excelDanhSachBaoCo(HttpServletRequest req, HttpServletResponse res, @PathVariable("dau") String dau,
			@PathVariable("cuoi") String cuoi, Model model) {
		try {
			Date batDau = null;
			Date ketThuc = null;

			SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
			batDau = sdf.parse(dau);
			ketThuc = sdf.parse(cuoi);

			HashMap<String, Object> hmParams = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);
			hmParams.put(Contants.BAT_DAU, batDau);
			hmParams.put(Contants.KET_THUC, batDau);
			hmParams.put(Contants.COMPANY, props.getCauHinh(PropCont.TEN_CONG_TY).getGiaTri());
			hmParams.put(Contants.DIA_CHI, props.getCauHinh(PropCont.DIA_CHI).getGiaTri());
			hmParams.put(Contants.PAGE_TITLE, "DANH SÁCH BÁO CÓ");
			hmParams.put(Contants.LOAI_CT, "Báo có");

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_BAO_CO);
			List<ChungTu> baoCoDs = chungTuDAO.danhSachChungTu(loaiCts, batDau, ketThuc);

			XSSFWorkbook wb = baoCaoDAO.taoChungTuDs(baoCoDs, hmParams);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8");
			res.setHeader("Content-disposition", "attachment; filename=BaoCoDs.xlsx");
			ServletOutputStream out = res.getOutputStream();
			wb.write(out);
			out.flush();
			out.close();
			wb.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/chungtu/baoco/xem/{id}")
	public String xemBaoCo(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_BAO_CO);
			if (chungTu == null) {
				return "redirect:/chungtu/baoco/danhsach";
			}

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);

			model.addAttribute("chungTu", chungTu);
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTBC");
			return "xemBaoCo";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/baoco/pdf/{id}", method = RequestMethod.GET)
	public void pdfBaoCo(HttpServletRequest req, HttpServletResponse res, @PathVariable("id") int maCt, Model model) {
		try {
			HashMap<String, Object> hmParams = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_BAO_CO);
			List<ChungTu> chungTuDs = new ArrayList<>();
			chungTuDs.add(chungTu);

			JasperReport jasperReport = getCompiledFile("BaoCo", req);
			byte[] bytes = baoCaoDAO.taoChungTu(jasperReport, hmParams, chungTuDs);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			res.setHeader("Content-disposition", "inline; filename=BaoCo" + maCt + ".pdf");
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/chungtu/baoco/sua/{id}")
	public String suaBaoCo(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_BAO_CO);
			if (chungTu == null) {
				return "redirect:/chungtu/baoco/danhsach";
			}
			chungTu.setNghiepVu("suaBaoCo");

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/baoco/xem/" + maCt;
			}
			chungTu.setKyKeToan(kyKeToan);

			return chuanBiFormBaoCo(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/baoco/saochep/{id}")
	public String saoChepBaoCo(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/baoco/danhsach";
			}

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_BAO_CO);
			if (chungTu == null) {
				return "redirect:/chungtu/baoco/danhsach";
			}
			chungTu.setKyKeToan(kyKeToan);

			// Lấy số báo có của năm hiện tại
			int soBaoCo = chungTuDAO.laySoChungTuLonNhatTheoLoaiCtVaKy(ChungTu.CHUNG_TU_BAO_CO, kyKeToan.getBatDau(),
					kyKeToan.getKetThuc());
			soBaoCo++;
			chungTu.setSoCt(soBaoCo);
			chungTu.setNghiepVu("saoChepBaoCo");

			return chuanBiFormBaoCo(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/baoco/taomoi")
	public String taoMoiBaoCo(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/baoco/danhsach";
			}

			ChungTu chungTu = new ChungTu();
			chungTu.setLoaiCt(ChungTu.CHUNG_TU_BAO_CO);
			chungTu.setNghiepVu("taoMoiBaoCo");
			chungTu.setKyKeToan(kyKeToan);

			// Lấy số báo có của năm hiện tại
			int soBaoCo = chungTuDAO.laySoChungTuLonNhatTheoLoaiCtVaKy(ChungTu.CHUNG_TU_BAO_CO, kyKeToan.getBatDau(),
					kyKeToan.getKetThuc());
			soBaoCo++;
			chungTu.setSoCt(soBaoCo);

			Date homNay = new Date();
			chungTu.setNgayLap(homNay);
			chungTu.setNgayHt(homNay);

			// Tài khoản tiềm gửi ngân hàng, ghi nợ
			TaiKhoan taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
			loaiTaiKhoan.setMaTk("");
			taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);
			taiKhoan.setSoDu(LoaiTaiKhoan.NO);
			chungTu.themTaiKhoan(taiKhoan);

			// Tài khoản khác, ghi có
			taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			loaiTaiKhoan = new LoaiTaiKhoan();
			loaiTaiKhoan.setMaTk("");
			taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);
			taiKhoan.setSoDu(LoaiTaiKhoan.CO);
			chungTu.themTaiKhoan(taiKhoan);

			return chuanBiFormBaoCo(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/baoco/luu", method = RequestMethod.POST)
	public String luuTaoMoiBaoCo(@ModelAttribute("mainFinanceForm") @Validated ChungTu chungTu, BindingResult result,
			Model model, HttpServletRequest req) {
		FlashMap flash = RequestContextUtils.getOutputFlashMap(req);
		String message = messageSource.getMessage("Fail.TaoMoi.ChungTu", null, LocaleContextHolder.getLocale());
		String ketQua = "/chungtu/baoco/danhsach";
		if (chungTu.getMaCt() > 0) {
			ketQua = "/chungtu/baoco/xem/" + chungTu.getMaCt();
			message = messageSource.getMessage("Fail.CapNhat.ChungTu", null, LocaleContextHolder.getLocale());
		}

		try {
			if (result.hasErrors()) {
				return chuanBiFormBaoCo(model, chungTu);
			}

			logger.info("Lưu chứng từ: " + chungTu);
			chungTu.setLoaiCt(ChungTu.CHUNG_TU_BAO_CO);

			if (chungTu.getMaCt() > 0) {
				chungTuDAO.capNhatChungTu(chungTu);
			} else {
				chungTuDAO.themChungTu(chungTu);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(message);
			flash.put("message", message);
		}

		return "redirect:" + ketQua;
	}

	private String chuanBiFormBaoCo(Model model, ChungTu chungTu) {
		try {
			model.addAttribute("mainFinanceForm", chungTu);

			Date homNay = new Date();
			model.addAttribute("homNay", homNay);

			// Lấy danh sách các loại tiền
			List<LoaiTien> loaiTienDs = loaiTienDAO.danhSachLoaiTien();
			model.addAttribute("loaiTienDs", loaiTienDs);

			// Lấy danh sách tài khoản tiền gửi ngân hàng, dùng cho bên nợ
			CauHinh taiKhoanDs = props.getCauHinh(PropCont.BAO_CO_DS_TK_CO);
			List<String> maTkDs = Utils.parseString(taiKhoanDs.getGiaTri());
			List<LoaiTaiKhoan> loaiTaiKhoanTgnhDs = taiKhoanDAO.danhSachTaiKhoanCon(maTkDs);
			model.addAttribute("loaiTaiKhoanTgnhDs", loaiTaiKhoanTgnhDs);

			// Lấy danh sách tài khoản, dùng cho bên có
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoanCon();
			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);

			model.addAttribute("tab", "tabCTBC");
			return chungTu.getNghiepVu();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/baoco/xoa/{id}")
	public String xoaBaoCo(@PathVariable("id") int maCt, Model model) {
		ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_BAO_CO);
		if (chungTu == null) {
			return "redirect:/chungtu/baoco/danhsach";
		}

		KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
		if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/chungtu/baoco/xem/" + maCt;
		}

		try {
			// Xóa phiếu thu có MA_CT là maCt, LOAI_CT là ChungTu.CHUNG_TU_BAO_CO
			chungTuDAO.xoaChungTu(maCt, ChungTu.CHUNG_TU_BAO_CO);

			return "redirect:/chungtu/baoco/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/baono/danhsach", method = { RequestMethod.GET, RequestMethod.POST })
	public String danhSachBaoNo(@ModelAttribute("mainFinanceForm") ChungTuForm form, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			KyKeToan kyKeToanLc = null;
			if (form.getKyKeToan() != null) {
				kyKeToanLc = kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt());
			}
			if (kyKeToanLc == null) {
				kyKeToanLc = kyKeToan;
			}
			form.setKyKeToan(kyKeToanLc);

			Date homNay = new Date();
			if (!form.getKyKeToan().getBatDau().after(homNay) && !form.getKyKeToan().getKetThuc().before(homNay)) {
				// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của
				// tháng hiện tại
				if (form.getDau() == null) {
					form.setDau(Utils.getStartDateOfMonth(homNay));
				}

				if (form.getCuoi() == null) {
					form.setCuoi(Utils.getEndDateOfMonth(homNay));
				}
			} else {
				if (form.getDau() == null) {
					form.setDau(form.getKyKeToan().getBatDau());
				}

				if (form.getCuoi() == null) {
					form.setCuoi(form.getKyKeToan().getKetThuc());
				}
			}

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_BAO_NO);

			// Lấy danh sách phiếu chi
			List<ChungTu> baoNoDs = chungTuDAO.danhSachChungTu(loaiCts, form.getDau(), form.getCuoi());

			model.addAttribute("baoNoDs", baoNoDs);
			model.addAttribute("kyKeToanDs", kyKeToanDAO.danhSachKyKeToan());
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTBN");
			return "danhSachBaoNo";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/baono/danhsach/pdf/{dau}/{cuoi}", method = RequestMethod.GET)
	public void pdfDanhSachBaoNo(HttpServletRequest req, HttpServletResponse res, @PathVariable("dau") String dau,
			@PathVariable("cuoi") String cuoi, Model model) {
		try {
			Date batDau = null;
			Date ketThuc = null;

			SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
			batDau = sdf.parse(dau);
			ketThuc = sdf.parse(cuoi);

			HashMap<String, Object> hmParams = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);
			hmParams.put(Contants.BAT_DAU, batDau);
			hmParams.put(Contants.KET_THUC, batDau);

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_BAO_NO);
			List<ChungTu> baoNoDs = chungTuDAO.danhSachChungTu(loaiCts, batDau, ketThuc);

			JasperReport jasperReport = getCompiledFile("BaoNoDs", req);
			byte[] bytes = baoCaoDAO.taoChungTu(jasperReport, hmParams, baoNoDs);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			res.setHeader("Content-disposition", "inline; filename=BaoNoDs.pdf");
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/chungtu/baono/danhsach/excel/{dau}/{cuoi}", method = RequestMethod.GET)
	public void excelDanhSachBaoNo(HttpServletRequest req, HttpServletResponse res, @PathVariable("dau") String dau,
			@PathVariable("cuoi") String cuoi, Model model) {
		try {
			Date batDau = null;
			Date ketThuc = null;

			SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
			batDau = sdf.parse(dau);
			ketThuc = sdf.parse(cuoi);

			HashMap<String, Object> hmParams = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);
			hmParams.put(Contants.BAT_DAU, batDau);
			hmParams.put(Contants.KET_THUC, batDau);
			hmParams.put(Contants.COMPANY, props.getCauHinh(PropCont.TEN_CONG_TY).getGiaTri());
			hmParams.put(Contants.DIA_CHI, props.getCauHinh(PropCont.DIA_CHI).getGiaTri());
			hmParams.put(Contants.PAGE_TITLE, "DANH SÁCH BÁO NỢ");
			hmParams.put(Contants.LOAI_CT, "Báo nợ");

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_BAO_NO);
			List<ChungTu> baoNoDs = chungTuDAO.danhSachChungTu(loaiCts, batDau, ketThuc);

			XSSFWorkbook wb = baoCaoDAO.taoChungTuDs(baoNoDs, hmParams);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8");
			res.setHeader("Content-disposition", "attachment; filename=BaoNoDs.xlsx");
			ServletOutputStream out = res.getOutputStream();
			wb.write(out);
			out.flush();
			out.close();
			wb.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/chungtu/baono/xem/{id}")
	public String xemBaoNo(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_BAO_NO);
			if (chungTu == null) {
				return "redirect:/chungtu/baono/danhsach";
			}

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);

			model.addAttribute("chungTu", chungTu);
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTBN");
			return "xemBaoNo";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/baono/pdf/{id}", method = RequestMethod.GET)
	public void pdfBaoNo(HttpServletRequest req, HttpServletResponse res, @PathVariable("id") int maCt, Model model) {
		try {
			HashMap<String, Object> hmParams = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_BAO_NO);
			List<ChungTu> chungTuDs = new ArrayList<>();
			chungTuDs.add(chungTu);

			JasperReport jasperReport = getCompiledFile("BaoNo", req);
			byte[] bytes = baoCaoDAO.taoChungTu(jasperReport, hmParams, chungTuDs);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			res.setHeader("Content-disposition", "inline; filename=BaoNo" + maCt + ".pdf");
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/chungtu/baono/sua/{id}")
	public String suaBaoNo(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_BAO_NO);
			if (chungTu == null) {
				return "redirect:/chungtu/baono/danhsach";
			}
			chungTu.setNghiepVu("suaBaoNo");

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/baono/xem/" + maCt;
			}
			chungTu.setKyKeToan(kyKeToan);

			return chuanBiFormBaoNo(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/baono/saochep/{id}")
	public String saoChepBaoNo(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/baono/danhsach";
			}

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_BAO_NO);
			if (chungTu == null) {
				return "redirect:/chungtu/baono/danhsach";
			}
			chungTu.setKyKeToan(kyKeToan);

			// Lấy số báo nợ của năm hiện tại
			int soBaoNo = chungTuDAO.laySoChungTuLonNhatTheoLoaiCtVaKy(ChungTu.CHUNG_TU_BAO_NO, kyKeToan.getBatDau(),
					kyKeToan.getKetThuc());
			soBaoNo++;
			chungTu.setSoCt(soBaoNo);
			chungTu.setNghiepVu("saoChepBaoNo");

			return chuanBiFormBaoNo(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/baono/taomoi")
	public String taoMoiBaoNo(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/baono/danhsach";
			}

			ChungTu chungTu = new ChungTu();
			chungTu.setLoaiCt(ChungTu.CHUNG_TU_BAO_NO);
			chungTu.setNghiepVu("taoMoiBaoNo");
			chungTu.setKyKeToan(kyKeToan);

			// Lấy số phiếu thu của năm hiện tại
			int soBaoNo = chungTuDAO.laySoChungTuLonNhatTheoLoaiCtVaKy(ChungTu.CHUNG_TU_BAO_NO, kyKeToan.getBatDau(),
					kyKeToan.getKetThuc());
			soBaoNo++;

			chungTu.setSoCt(soBaoNo);
			Date homNay = new Date();
			chungTu.setNgayLap(homNay);
			chungTu.setNgayHt(homNay);

			// Tài khoản khác, ghi nợ
			TaiKhoan taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
			loaiTaiKhoan.setMaTk("");
			taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);
			taiKhoan.setSoDu(LoaiTaiKhoan.NO);
			chungTu.themTaiKhoan(taiKhoan);

			// Tài khoản tiềm gửi ngân hàng, ghi có
			taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			loaiTaiKhoan = new LoaiTaiKhoan();
			loaiTaiKhoan.setMaTk("");
			taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);
			taiKhoan.setSoDu(LoaiTaiKhoan.CO);
			chungTu.themTaiKhoan(taiKhoan);

			return chuanBiFormBaoNo(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/baono/luu", method = RequestMethod.POST)
	public String luuTaoMoiBaoNo(@ModelAttribute("mainFinanceForm") @Validated ChungTu chungTu, BindingResult result,
			Model model, HttpServletRequest req) {
		FlashMap flash = RequestContextUtils.getOutputFlashMap(req);
		String message = messageSource.getMessage("Fail.TaoMoi.ChungTu", null, LocaleContextHolder.getLocale());
		String ketQua = "/chungtu/baono/danhsach";
		if (chungTu.getMaCt() > 0) {
			ketQua = "/chungtu/baono/xem/" + chungTu.getMaCt();
			message = messageSource.getMessage("Fail.CapNhat.ChungTu", null, LocaleContextHolder.getLocale());
		}

		try {
			if (result.hasErrors()) {
				return chuanBiFormBaoNo(model, chungTu);
			}

			logger.info("Lưu chứng từ: " + chungTu);
			chungTu.setLoaiCt(ChungTu.CHUNG_TU_BAO_NO);

			if (chungTu.getMaCt() > 0) {
				chungTuDAO.capNhatChungTu(chungTu);
			} else {
				chungTuDAO.themChungTu(chungTu);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(message);
			flash.put("message", message);
		}

		return "redirect:" + ketQua;
	}

	private String chuanBiFormBaoNo(Model model, ChungTu chungTu) {
		try {
			model.addAttribute("mainFinanceForm", chungTu);

			Date homNay = new Date();
			model.addAttribute("homNay", homNay);

			// Lấy danh sách các loại tiền
			List<LoaiTien> loaiTienDs = loaiTienDAO.danhSachLoaiTien();
			model.addAttribute("loaiTienDs", loaiTienDs);

			// Lấy danh sách tài khoản tiền gửi ngân hàng, dùng cho bên có
			CauHinh taiKhoanDs = props.getCauHinh(PropCont.BAO_NO_DS_TK_NO);
			List<String> maTkDs = Utils.parseString(taiKhoanDs.getGiaTri());
			List<LoaiTaiKhoan> loaiTaiKhoanTgnhDs = taiKhoanDAO.danhSachTaiKhoanCon(maTkDs);
			model.addAttribute("loaiTaiKhoanTgnhDs", loaiTaiKhoanTgnhDs);

			// Lấy danh sách tài khoản, dùng cho bên nợ
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoanCon();
			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);

			model.addAttribute("tab", "tabCTBN");
			return chungTu.getNghiepVu();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/baono/xoa/{id}")
	public String xoaBaoNo(@PathVariable("id") int maCt, Model model) {
		ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_BAO_NO);
		if (chungTu == null) {
			return "redirect:/chungtu/baono/danhsach";
		}
		chungTu.setNghiepVu("suaBaoNo");

		KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
		if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/chungtu/baono/xem/" + maCt;
		}

		try {
			// Xóa phiếu thu có MA_CT là maCt, LOAI_CT là ChungTu.CHUNG_TU_BAO_NO
			chungTuDAO.xoaChungTu(maCt, ChungTu.CHUNG_TU_BAO_NO);

			return "redirect:/chungtu/baono/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/ktth/danhsach", method = { RequestMethod.GET, RequestMethod.POST })
	public String danhSachKeToanTongHop(@ModelAttribute("mainFinanceForm") ChungTuForm form, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			KyKeToan kyKeToanLc = null;
			if (form.getKyKeToan() != null) {
				kyKeToanLc = kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt());
			}
			if (kyKeToanLc == null) {
				kyKeToanLc = kyKeToan;
			}
			form.setKyKeToan(kyKeToanLc);

			Date homNay = new Date();
			if (!form.getKyKeToan().getBatDau().after(homNay) && !form.getKyKeToan().getKetThuc().before(homNay)) {
				// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của
				// tháng hiện tại
				if (form.getDau() == null) {
					form.setDau(Utils.getStartDateOfMonth(homNay));
				}

				if (form.getCuoi() == null) {
					form.setCuoi(Utils.getEndDateOfMonth(homNay));
				}
			} else {
				if (form.getDau() == null) {
					form.setDau(form.getKyKeToan().getBatDau());
				}

				if (form.getCuoi() == null) {
					form.setCuoi(form.getKyKeToan().getKetThuc());
				}
			}

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_KT_TH);

			// Lấy danh sách phiếu kế toán tổng hợp
			List<ChungTu> keToanTongHopDs = chungTuDAO.danhSachChungTuKtth(loaiCts, form.getDau(), form.getCuoi());

			model.addAttribute("keToanTongHopDs", keToanTongHopDs);
			model.addAttribute("kyKeToanDs", kyKeToanDAO.danhSachKyKeToan());
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTKTTH");
			return "danhSachKeToanTongHop";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/ktth/danhsach/pdf/{dau}/{cuoi}", method = RequestMethod.GET)
	public void pdfDanhSachKtth(HttpServletRequest req, HttpServletResponse res, @PathVariable("dau") String dau,
			@PathVariable("cuoi") String cuoi, Model model) {
		try {
			Date batDau = null;
			Date ketThuc = null;

			SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
			batDau = sdf.parse(dau);
			ketThuc = sdf.parse(cuoi);

			HashMap<String, Object> hmParams = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);
			hmParams.put(Contants.BAT_DAU, batDau);
			hmParams.put(Contants.KET_THUC, batDau);

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_KT_TH);
			List<ChungTu> ktthDs = chungTuDAO.danhSachChungTuKtth(loaiCts, batDau, ketThuc);

			JasperReport jasperReport = getCompiledFile("KeToanTongHopDs", req);
			byte[] bytes = baoCaoDAO.taoChungTu(jasperReport, hmParams, ktthDs);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			res.setHeader("Content-disposition", "inline; filename=KeToanTongHopDs.pdf");
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/chungtu/ktth/danhsach/excel/{dau}/{cuoi}", method = RequestMethod.GET)
	public void excelDanhSachKtth(HttpServletRequest req, HttpServletResponse res, @PathVariable("dau") String dau,
			@PathVariable("cuoi") String cuoi, Model model) {
		try {
			Date batDau = null;
			Date ketThuc = null;

			SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
			batDau = sdf.parse(dau);
			ketThuc = sdf.parse(cuoi);

			HashMap<String, Object> hmParams = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);
			hmParams.put(Contants.BAT_DAU, batDau);
			hmParams.put(Contants.KET_THUC, batDau);
			hmParams.put(Contants.COMPANY, props.getCauHinh(PropCont.TEN_CONG_TY).getGiaTri());
			hmParams.put(Contants.DIA_CHI, props.getCauHinh(PropCont.DIA_CHI).getGiaTri());
			hmParams.put(Contants.PAGE_TITLE, "DANH SÁCH PHIẾU KẾ TOÁN TỔNG HỢP");
			hmParams.put(Contants.LOAI_CT, "Phiếu kế toán tổng hợp");

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_KT_TH);
			List<ChungTu> ktthDs = chungTuDAO.danhSachChungTuKtth(loaiCts, batDau, ketThuc);

			XSSFWorkbook wb = baoCaoDAO.taoChungTuKtthDs(ktthDs, hmParams);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8");
			res.setHeader("Content-disposition", "attachment; filename=KeToanTongHopDs.xlsx");
			ServletOutputStream out = res.getOutputStream();
			wb.write(out);
			out.flush();
			out.close();
			wb.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/chungtu/ktth/xem/{id}")
	public String xemKeToanTongHop(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layChungTuKtth(maCt, ChungTu.CHUNG_TU_KT_TH);
			if (chungTu == null) {
				return "redirect:/chungtu/ktth/danhsach";
			}

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);

			model.addAttribute("chungTu", chungTu);
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTKTTH");
			return "xemKeToanTongHop";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/ktth/pdf/{id}", method = RequestMethod.GET)
	public void pdfKeToanTongHop(HttpServletRequest req, HttpServletResponse res, @PathVariable("id") int maCt,
			Model model) {
		try {
			HashMap<String, Object> hmParams = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);

			ChungTu chungTu = chungTuDAO.layChungTuKtth(maCt, ChungTu.CHUNG_TU_KT_TH);
			List<ChungTu> chungTuDs = new ArrayList<>();
			chungTuDs.add(chungTu);

			JasperReport jasperReport = getCompiledFile("KeToanTongHop", req);
			byte[] bytes = baoCaoDAO.taoChungTu(jasperReport, hmParams, chungTuDs);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			res.setHeader("Content-disposition", "inline; filename=KeToanTongHop" + maCt + ".pdf");
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/chungtu/ktth/sua/{id}")
	public String suaKeToanTongHop(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layChungTuKtth(maCt, ChungTu.CHUNG_TU_KT_TH);
			if (chungTu == null) {
				return "redirect:/chungtu/ktth/danhsach";
			}
			chungTu.setNghiepVu("suaKeToanTongHop");

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/ktth/xem/" + maCt;
			}
			chungTu.setKyKeToan(kyKeToan);

			return chuanBiFormKeToanTongHop(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/ktth/saochep/{id}")
	public String saoChepKeToanTongHop(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/ktth/danhsach";
			}

			ChungTu chungTu = chungTuDAO.layChungTuKtth(maCt, ChungTu.CHUNG_TU_KT_TH);
			if (chungTu == null) {
				return "redirect:/chungtu/ktth/danhsach";
			}
			chungTu.setKyKeToan(kyKeToan);

			// Lấy số kế toán tổng hợp của năm hiện tại
			int soKeToanTongHop = chungTuDAO.laySoChungTuLonNhatTheoLoaiCtVaKy(ChungTu.CHUNG_TU_KT_TH,
					kyKeToan.getBatDau(), kyKeToan.getKetThuc());
			soKeToanTongHop++;
			chungTu.setSoCt(soKeToanTongHop);
			chungTu.setNghiepVu("saoChepKeToanTongHop");

			return chuanBiFormKeToanTongHop(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/ktth/taomoi")
	public String taoMoiKeToanTongHop(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/ktth/danhsach";
			}

			ChungTu chungTu = new ChungTu();
			chungTu.setLoaiCt(ChungTu.CHUNG_TU_KT_TH);
			chungTu.setNghiepVu("taoMoiKeToanTongHop");
			chungTu.setKyKeToan(kyKeToan);

			// Lấy số phiếu thu của năm hiện tại
			int soKeToanTongHop = chungTuDAO.laySoChungTuLonNhatTheoLoaiCtVaKy(ChungTu.CHUNG_TU_KT_TH,
					kyKeToan.getBatDau(), kyKeToan.getKetThuc());
			soKeToanTongHop++;

			chungTu.setSoCt(soKeToanTongHop);
			Date homNay = new Date();
			chungTu.setNgayLap(homNay);
			chungTu.setNgayHt(homNay);

			// Tài khoản ghi nợ
			TaiKhoan taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			taiKhoan.setSoDu(LoaiTaiKhoan.NO);
			LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
			loaiTaiKhoan.setMaTk("0");
			taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);
			chungTu.themTaiKhoanKtth(taiKhoan);

			// Tài khoản ghi có
			taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			taiKhoan.setSoDu(LoaiTaiKhoan.CO);
			loaiTaiKhoan = new LoaiTaiKhoan();
			loaiTaiKhoan.setMaTk("0");
			taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);
			chungTu.themTaiKhoanKtth(taiKhoan);

			return chuanBiFormKeToanTongHop(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/ktth/luu", method = RequestMethod.POST)
	public String luuTaoMoiKeToanTongHop(@ModelAttribute("mainFinanceForm") @Validated ChungTu chungTu,
			BindingResult result, Model model, HttpServletRequest req) {
		FlashMap flash = RequestContextUtils.getOutputFlashMap(req);
		String message = messageSource.getMessage("Fail.TaoMoi.ChungTu", null, LocaleContextHolder.getLocale());
		String ketQua = "/chungtu/ktth/danhsach";
		if (chungTu.getMaCt() > 0) {
			ketQua = "/chungtu/ktth/xem/" + chungTu.getMaCt();
			message = messageSource.getMessage("Fail.CapNhat.ChungTu", null, LocaleContextHolder.getLocale());
		}

		try {
			logger.info("Has error: " + result.getAllErrors());
			if (result.hasErrors()) {
				return chuanBiFormKeToanTongHop(model, chungTu);
			}

			logger.info("Lưu chứng từ: " + chungTu);
			chungTu.setLoaiCt(ChungTu.CHUNG_TU_KT_TH);

			if (chungTu.getMaCt() > 0) {
				chungTuDAO.capNhatChungTuKtth(chungTu);
			} else {
				chungTuDAO.themChungTuKtth(chungTu);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(message);
			flash.put("message", message);
		}

		return "redirect:" + ketQua;
	}

	private String chuanBiFormKeToanTongHop(Model model, ChungTu chungTu) {
		try {
			model.addAttribute("mainFinanceForm", chungTu);

			// Lấy danh sách các loại tiền
			List<LoaiTien> loaiTienDs = loaiTienDAO.danhSachLoaiTien();
			model.addAttribute("loaiTienDs", loaiTienDs);

			// Lấy danh sách tài khoản, dùng cho bên nợ & có
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoanCon();
			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);

			List<DoiTuong> khachHangDs = khachHangDAO.danhSachDoiTuong();
			List<DoiTuong> nhaCungCapDs = nhaCungCapDAO.danhSachDoiTuong();
			List<DoiTuong> nhanVienDs = nhanVienDAO.danhSachDoiTuong();

			List<DoiTuong> doiTuongDs = new ArrayList<>();
			doiTuongDs.addAll(khachHangDs);
			doiTuongDs.addAll(nhaCungCapDs);
			doiTuongDs.addAll(nhanVienDs);
			model.addAttribute("doiTuongDs", doiTuongDs);

			model.addAttribute("tab", "tabCTKTTH");
			return chungTu.getNghiepVu();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/ktth/xoa/{id}")
	public String xoaKeToanTongHop(@PathVariable("id") int maCt, Model model) {
		ChungTu chungTu = chungTuDAO.layChungTuKtth(maCt, ChungTu.CHUNG_TU_KT_TH);
		if (chungTu == null) {
			return "redirect:/chungtu/ktth/danhsach";
		}
		chungTu.setNghiepVu("suaKeToanTongHop");

		KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
		if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/chungtu/ktth/xem/" + maCt;
		}

		try {
			// Xóa phiếu thu có MA_CT là maCt, LOAI_CT là ChungTu.CHUNG_TU_BAO_NO
			chungTuDAO.xoaChungTu(maCt, ChungTu.CHUNG_TU_KT_TH);

			return "redirect:/chungtu/ktth/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/muahang/danhsach", method = { RequestMethod.GET, RequestMethod.POST })
	public String danhSachMuaHang(@ModelAttribute("mainFinanceForm") ChungTuForm form, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			KyKeToan kyKeToanLc = null;
			if (form.getKyKeToan() != null) {
				kyKeToanLc = kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt());
			}
			if (kyKeToanLc == null) {
				kyKeToanLc = kyKeToan;
			}
			form.setKyKeToan(kyKeToanLc);

			Date homNay = new Date();
			if (!form.getKyKeToan().getBatDau().after(homNay) && !form.getKyKeToan().getKetThuc().before(homNay)) {
				// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của
				// tháng hiện tại
				if (form.getDau() == null) {
					form.setDau(Utils.getStartDateOfMonth(homNay));
				}

				if (form.getCuoi() == null) {
					form.setCuoi(Utils.getEndDateOfMonth(homNay));
				}
			} else {
				if (form.getDau() == null) {
					form.setDau(form.getKyKeToan().getBatDau());
				}

				if (form.getCuoi() == null) {
					form.setCuoi(form.getKyKeToan().getKetThuc());
				}
			}

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_MUA_HANG);

			// Lấy danh sách chứng từ mua hàng
			List<ChungTu> muaHangDs = chungTuDAO.danhSachChungTuKho(loaiCts, form.getDau(), form.getCuoi());

			model.addAttribute("muaHangDs", muaHangDs);
			model.addAttribute("kyKeToanDs", kyKeToanDAO.danhSachKyKeToan());
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTMH");
			return "danhSachMuaHang";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/muahang/danhsach/pdf/{dau}/{cuoi}", method = RequestMethod.GET)
	public void pdfDanhSachMuaHang(HttpServletRequest req, HttpServletResponse res, @PathVariable("dau") String dau,
			@PathVariable("cuoi") String cuoi, Model model) {
		try {
			Date batDau = null;
			Date ketThuc = null;

			SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
			batDau = sdf.parse(dau);
			ketThuc = sdf.parse(cuoi);

			HashMap<String, Object> hmParams = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);
			hmParams.put(Contants.BAT_DAU, batDau);
			hmParams.put(Contants.KET_THUC, batDau);

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_MUA_HANG);
			List<ChungTu> muaHangDs = chungTuDAO.danhSachChungTuKho(loaiCts, batDau, ketThuc);

			JasperReport jasperReport = getCompiledFile("muaHangDs", req);
			byte[] bytes = baoCaoDAO.taoChungTu(jasperReport, hmParams, muaHangDs);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			res.setHeader("Content-disposition", "inline; filename=muaHangDs.pdf");
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/chungtu/muahang/danhsach/excel/{dau}/{cuoi}", method = RequestMethod.GET)
	public void excelDanhSachMuaHang(HttpServletRequest req, HttpServletResponse res, @PathVariable("dau") String dau,
			@PathVariable("cuoi") String cuoi, Model model) {
		try {
			Date batDau = null;
			Date ketThuc = null;

			SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
			batDau = sdf.parse(dau);
			ketThuc = sdf.parse(cuoi);

			HashMap<String, Object> hmParams = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);
			hmParams.put(Contants.BAT_DAU, batDau);
			hmParams.put(Contants.KET_THUC, batDau);
			hmParams.put(Contants.COMPANY, props.getCauHinh(PropCont.TEN_CONG_TY).getGiaTri());
			hmParams.put(Contants.DIA_CHI, props.getCauHinh(PropCont.DIA_CHI).getGiaTri());
			hmParams.put(Contants.PAGE_TITLE, "DANH SÁCH CHỨNG TỪ MUA HÀNG");
			hmParams.put(Contants.LOAI_CT, "Chứng từ mua hàng");

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_MUA_HANG);
			List<ChungTu> muaHangDs = chungTuDAO.danhSachChungTuKho(loaiCts, batDau, ketThuc);

			XSSFWorkbook wb = baoCaoDAO.taoChungTuDs(muaHangDs, hmParams);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8");
			res.setHeader("Content-disposition", "attachment; filename=MuaHangDs.xlsx");
			ServletOutputStream out = res.getOutputStream();
			wb.write(out);
			out.flush();
			out.close();
			wb.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/chungtu/muahang/xem/{id}")
	public String xemMuaHang(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layChungTuKho(maCt, ChungTu.CHUNG_TU_MUA_HANG);
			if (chungTu == null) {
				return "redirect:/chungtu/muahang/danhsach";
			}

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);

			model.addAttribute("chungTu", chungTu);
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTMH");
			return "xemMuaHang";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/muahang/pdf/{id}", method = RequestMethod.GET)
	public void pdfMuaHang(HttpServletRequest req, HttpServletResponse res, @PathVariable("id") int maCt, Model model) {
		try {
			HashMap<String, Object> hmParams = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_THU);
			List<ChungTu> chungTuDs = new ArrayList<>();
			chungTuDs.add(chungTu);

			JasperReport jasperReport = getCompiledFile("MuaHang", req);
			byte[] bytes = baoCaoDAO.taoChungTu(jasperReport, hmParams, chungTuDs);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			res.setHeader("Content-disposition", "attachment; filename=MuaHang" + maCt + ".pdf");
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/chungtu/muahang/sua/{id}")
	public String suaMuaHang(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layChungTuKho(maCt, ChungTu.CHUNG_TU_MUA_HANG);
			if (chungTu == null) {
				return "redirect:/chungtu/muahang/danhsach";
			}
			chungTu.setNghiepVu("suaMuaHang");

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/muahang/xem/" + maCt;
			}
			chungTu.setKyKeToan(kyKeToan);

			// Thêm một dòng hàng hóa sạch ở cuối để js lấy làm mẫu
			HangHoa hangHoa = new HangHoa();
			chungTu.themHangHoa(hangHoa);

			return chuanBiFormMuaHang(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/muahang/saochep/{id}")
	public String saoChepMuaHang(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/muahang/danhsach";
			}

			ChungTu chungTu = chungTuDAO.layChungTuKho(maCt, ChungTu.CHUNG_TU_MUA_HANG);
			if (chungTu == null) {
				return "redirect:/chungtu/muahang/danhsach";
			}
			chungTu.setKyKeToan(kyKeToan);
			logger.info("Danh sach hang hoa: " + chungTu.getHangHoaDs());

			// Lấy số phiếu thu của năm hiện tại
			int soMuaHang = chungTuDAO.laySoChungTuLonNhatTheoLoaiCtVaKy(ChungTu.CHUNG_TU_MUA_HANG,
					kyKeToan.getBatDau(), kyKeToan.getKetThuc());
			soMuaHang++;
			chungTu.setSoCt(soMuaHang);
			chungTu.setNghiepVu("saoChepMuaHang");

			// Thêm một dòng hàng hóa sạch ở cuối để js lấy làm mẫu
			HangHoa hangHoa = new HangHoa();
			chungTu.themHangHoa(hangHoa);

			return chuanBiFormMuaHang(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/muahang/taomoi/{tinhChatCt}")
	public String taoMoiMuaHang(Model model, @PathVariable("tinhChatCt") int tinhChatCt) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/muahang/danhsach";
			}

			ChungTu chungTu = new ChungTu();
			chungTu.setLoaiCt(ChungTu.CHUNG_TU_MUA_HANG);
			chungTu.setNghiepVu("taoMoiMuaHang");
			chungTu.setKyKeToan(kyKeToan);

			// Lấy số phiếu thu của năm hiện tại
			int soKeToanTongHop = chungTuDAO.laySoChungTuLonNhatTheoLoaiCtVaKy(ChungTu.CHUNG_TU_MUA_HANG,
					kyKeToan.getBatDau(), kyKeToan.getKetThuc());
			soKeToanTongHop++;

			chungTu.setTinhChatCt(tinhChatCt);
			chungTu.setChieu(ChungTu.MUA);

			chungTu.setSoCt(soKeToanTongHop);
			Date homNay = new Date();
			chungTu.setNgayLap(homNay);
			chungTu.setNgayHt(homNay);

			// Tạo tạm một hàng hóa mới làm mẫu, sau js sẽ xóa đi
			HangHoa hangHoa = new HangHoa();
			chungTu.themHangHoa(hangHoa);

			return chuanBiFormMuaHang(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/muahang/luu", method = RequestMethod.POST)
	public String luuTaoMoiMuaHang(@ModelAttribute("mainFinanceForm") @Validated ChungTu chungTu, BindingResult result,
			Model model, HttpServletRequest req) {
		FlashMap flash = RequestContextUtils.getOutputFlashMap(req);
		String message = messageSource.getMessage("Fail.TaoMoi.ChungTu", null, LocaleContextHolder.getLocale());
		String ketQua = "/chungtu/muahang/danhsach";
		if (chungTu.getMaCt() > 0) {
			ketQua = "/chungtu/muahang/xem/" + chungTu.getMaCt();
			message = messageSource.getMessage("Fail.CapNhat.ChungTu", null, LocaleContextHolder.getLocale());
		}

		try {
			if (result.hasErrors()) {
				// Tạo tạm một hàng hóa mới làm mẫu, sau js sẽ xóa đi
				HangHoa hangHoa = new HangHoa();
				chungTu.themHangHoa(hangHoa);

				return chuanBiFormMuaHang(model, chungTu);
			}

			logger.info("Lưu chứng từ: " + chungTu);

			chungTu.setLoaiCt(ChungTu.CHUNG_TU_MUA_HANG);

			// Chuẩn hóa dữ liệu trước khi cập nhật vào CSDL
			// Đảm bảo các trường dữ liệu đúng với ý nghĩa của nó
			// Đảm bảo giá trị được tính thống nhất theo loại tiền đã chọn
			// Với từng loại hàng hóa
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			int nhomDk = 1;
			DoiTuong doiTuong = chungTu.getDoiTuong();
			List<TaiKhoan> taiKhoanKtthDs = new ArrayList<>();

			int soHangHoa = chungTu.getHangHoaDs().size();
			Iterator<HangHoa> hhIter = chungTu.getHangHoaDs().iterator();
			while (hhIter.hasNext()) {
				HangHoa hangHoa = hhIter.next();
				hangHoa.getGiaKho().setLoaiTien(chungTu.getLoaiTien());
				hangHoa.getGiaKho().setGiaTri(hangHoa.getGiaKho().getSoTien() * chungTu.getLoaiTien().getBanRa());
				hangHoa.getDonGia().setLoaiTien(chungTu.getLoaiTien());

				// Tính tổng tiền mua hàng đề ghi vào tài khoản công nợ
				double tongDonGia = hangHoa.getSoLuong() * hangHoa.getDonGia().getSoTien();

				// Tổng thuế nhập khẩu, tiêu thụ đặc biệt, thuế GTGT (phương pháp trực tiếp)
				double tongThue = 0;
				double tongThueToanBo = 0;

				// Chuyển các loại thuế về loại tiền chính của chứng từ
				if (hangHoa.getTkThueNk() != null && hangHoa.getTkThueNk().getLoaiTaiKhoan().getMaTk() != null
						&& hangHoa.getTkThueNk().getSoTien() != null) {
					double thue = hangHoa.getTkThueNk().getSoTien().getGiaTri() / chungTu.getLoaiTien().getBanRa();
					hangHoa.getTkThueNk().getSoTien().setSoTien(thue);
					hangHoa.getTkThueNk().getSoTien().setLoaiTien(chungTu.getLoaiTien());
					// hangHoa.getTkThueNk().setNhomDk(1);
					tongThue = tongThue + thue;

					if (soHangHoa > 1) {
						hangHoa.getTkThueNk().setLyDo(chungTu.getLyDo() + ": " + hangHoa.getTenHh());
					} else {
						hangHoa.getTkThueNk().setLyDo(chungTu.getLyDo());
					}
				}

				if (hangHoa.getTkThueTtdb() != null && hangHoa.getTkThueTtdb().getLoaiTaiKhoan().getMaTk() != null
						&& hangHoa.getTkThueTtdb().getSoTien() != null) {
					double thue = hangHoa.getTkThueTtdb().getSoTien().getGiaTri() / chungTu.getLoaiTien().getBanRa();
					hangHoa.getTkThueTtdb().getSoTien().setSoTien(thue);
					hangHoa.getTkThueTtdb().getSoTien().setLoaiTien(chungTu.getLoaiTien());
					// hangHoa.getTkThueTtdb().setNhomDk(1);
					tongThue = tongThue + thue;

					if (soHangHoa > 1) {
						hangHoa.getTkThueTtdb().setLyDo(chungTu.getLyDo() + ": " + hangHoa.getTenHh());
					} else {
						hangHoa.getTkThueTtdb().setLyDo(chungTu.getLyDo());
					}
				}

				if (hangHoa.getTkThueGtgt() != null && hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk() != null
						&& !hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk().trim().equals("")
						&& hangHoa.getTkThueGtgt().getSoTien() != null) {
					// Có thuế Gtgt
					double thue = hangHoa.getTkThueGtgt().getSoTien().getGiaTri() / chungTu.getLoaiTien().getBanRa();
					hangHoa.getTkThueGtgt().getSoTien().setSoTien(thue);
					hangHoa.getTkThueGtgt().getSoTien().setLoaiTien(chungTu.getLoaiTien());
					if (chungTu.getTinhChatCt() == ChungTu.HANG_HOA_TRONG_NUOC
							|| chungTu.getTinhChatCt() == ChungTu.DICH_VU_TRONG_NUOC) {
						// hangHoa.getTkThueGtgt().setNhomDk(0);
					} else {
						// hangHoa.getTkThueGtgt().setNhomDk(1);
					}

					if (soHangHoa > 1) {
						hangHoa.getTkThueGtgt().setLyDo(chungTu.getLyDo() + ": " + hangHoa.getTenHh());
					} else {
						hangHoa.getTkThueGtgt().setLyDo(chungTu.getLyDo());
					}

					tongThueToanBo = tongThue + thue;
					if (hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
						// Đây là trường hợp thuế Gtgt tính theo phương pháp trực tiếp
						// Ghi thuế gtgt vào hàng hóa
						tongThue = tongThue + thue;
					}
				}

				// Tính chi phí và phân bổ chi phí
				// Phần này hiện chưa có
				// double tongChiPhi = 0;

				// Từ đó tính ra giá nhập kho
				// Đã tính ở phần client gằng js nên không cần tính lại
				double tongTien = tongDonGia + tongThue;
				double tongTienToanBo = tongDonGia + tongThueToanBo;

				// Tính giá trị tài khoản kho
				if (hangHoa.getTkKho() != null && hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk() != null) {
					hangHoa.getTkKho().getSoTien().setLoaiTien(chungTu.getLoaiTien());
					hangHoa.getTkKho().getSoTien().setSoTien(tongTien);
					if (chungTu.getTinhChatCt() == ChungTu.HANG_HOA_TRONG_NUOC
							|| chungTu.getTinhChatCt() == ChungTu.DICH_VU_TRONG_NUOC) {
						if (hangHoa.getTkThueGtgt() != null
								&& hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk() != null
								&& !hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk().trim().equals("")
								&& hangHoa.getTkThueGtgt().getSoTien() != null) {
							// Trường hợp có thuế Gtgt
							// Thuế Gtgt: Phương pháp khấu trừ
							hangHoa.getTkKho().getSoTien().setSoTien(tongDonGia);
						} else {
							// Trường hợp có thuế Gtgt
							// Thuế Gtgt: Phương pháp trực tiếp
							hangHoa.getTkKho().getSoTien().setSoTien(tongTienToanBo);
						}
					}
					hangHoa.getTkKho().getSoTien()
							.setGiaTri(hangHoa.getTkKho().getSoTien().getSoTien() * chungTu.getLoaiTien().getBanRa());

					if (soHangHoa > 1) {
						hangHoa.getTkKho().setLyDo(chungTu.getLyDo() + ": " + hangHoa.getTenHh());
					} else {
						hangHoa.getTkKho().setLyDo(chungTu.getLyDo());
					}
				}

				// Tính giá trị tài khoản thanh toán (công nợ)
				if (hangHoa.getTkThanhtoan() != null && hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk() != null) {
					hangHoa.getTkThanhtoan().getSoTien().setLoaiTien(chungTu.getLoaiTien());
					hangHoa.getTkThanhtoan().getSoTien().setSoTien(tongDonGia);
					// hangHoa.getTkThanhtoan().setNhomDk(1);
					if (chungTu.getTinhChatCt() == ChungTu.HANG_HOA_TRONG_NUOC
							|| chungTu.getTinhChatCt() == ChungTu.DICH_VU_TRONG_NUOC) {
						hangHoa.getTkThanhtoan().getSoTien().setSoTien(tongTienToanBo);
					}
					hangHoa.getTkThanhtoan().getSoTien().setGiaTri(
							hangHoa.getTkThanhtoan().getSoTien().getSoTien() * chungTu.getLoaiTien().getBanRa());

					if (soHangHoa > 1) {
						hangHoa.getTkThanhtoan().setLyDo(chungTu.getLyDo() + ": " + hangHoa.getTenHh());
					} else {
						hangHoa.getTkThanhtoan().setLyDo(chungTu.getLyDo());
					}
				}

				// Chuẩn bị dữ liệu ktth, nếu có
				if (hangHoa != null && hangHoa.getNvktDs() != null && hangHoa.getNvktDs().size() > 0) {
					NghiepVuKeToan nvkt = hangHoa.getNvktDs().get(0);

					TaiKhoan taiKhoanNo = nvkt.getTaiKhoanNo();
					taiKhoanNo.getSoTien()
							.setGiaTri(taiKhoanNo.getSoTien().getSoTien() * chungTu.getLoaiTien().getBanRa());
					taiKhoanNo.setSoDu(LoaiTaiKhoan.NO);
					taiKhoanNo.setNhomDk(nhomDk);
					taiKhoanNo.setDoiTuong(doiTuong);
					taiKhoanNo.setNo(taiKhoanNo.getSoTien());

					TaiKhoan taiKhoanCo = nvkt.getTaiKhoanCo();
					taiKhoanCo.setLyDo(taiKhoanNo.getLyDo());
					taiKhoanCo.setSoDu(LoaiTaiKhoan.CO);
					taiKhoanCo.setCo(taiKhoanNo.getSoTien());
					taiKhoanCo.setNhomDk(nhomDk);
					taiKhoanCo.setDoiTuong(doiTuong);

					if ((taiKhoanNo.getLyDo() == null || taiKhoanNo.getLyDo().isEmpty())
							&& taiKhoanNo.getSoTien().getSoTien() == 0
							&& (taiKhoanNo.getLoaiTaiKhoan() == null || taiKhoanNo.getLoaiTaiKhoan().getMaTk() == null
									|| taiKhoanNo.getLoaiTaiKhoan().getMaTk().isEmpty())
							&& (taiKhoanCo.getLoaiTaiKhoan() == null || taiKhoanCo.getLoaiTaiKhoan().getMaTk() == null
									|| taiKhoanCo.getLoaiTaiKhoan().getMaTk().isEmpty())) {
						// Không có dữ liệu, bỏ qua
						logger.info("Không có dữ liệu, bỏ qua");
					} else {
						taiKhoanKtthDs.add(taiKhoanNo);
						taiKhoanKtthDs.add(taiKhoanCo);
						nhomDk++;
					}
				}
			}

			if (chungTu.getMaCt() > 0) {
				chungTuDAO.capNhatChungTuKho(chungTu);
			} else {
				chungTuDAO.themChungTuKho(chungTu);
			}

			// Tạo phiếu kế toán tổng hợp kèm theo nếu cần
			if (taiKhoanKtthDs != null && taiKhoanKtthDs.size() > 0) {
				chungTu.setDoiTuong(new DoiTuong());
				chungTu.setLoaiCt(ChungTu.CHUNG_TU_KT_TH);
				// Lấy số phiếu thu của năm hiện tại
				int soKeToanTongHop = chungTuDAO.laySoChungTuLonNhatTheoLoaiCtVaKy(ChungTu.CHUNG_TU_KT_TH,
						kyKeToan.getBatDau(), kyKeToan.getKetThuc());
				soKeToanTongHop++;
				chungTu.setSoCt(soKeToanTongHop);
				chungTu.setTaiKhoanKtthDs(taiKhoanKtthDs);
				chungTuDAO.themChungTuKtth(chungTu);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(message);
			flash.put("message", message);
		}

		return "redirect:" + ketQua;
	}

	private String chuanBiFormMuaHang(Model model, ChungTu chungTu) {
		try {
			model.addAttribute("mainFinanceForm", chungTu);

			CauHinh taiKhoanKhoDs = props.getCauHinh(PropCont.MUA_HANG_DS_TK_KHO_NO);
			List<String> maTkKhoDs = Utils.parseString(taiKhoanKhoDs.getGiaTri());
			List<LoaiTaiKhoan> loaiTaiKhoanKhoDs = taiKhoanDAO.danhSachTaiKhoanCon(maTkKhoDs);
			model.addAttribute("loaiTaiKhoanKhoDs", loaiTaiKhoanKhoDs);

			CauHinh taiKhoanThanhToanDs = props.getCauHinh(PropCont.MUA_HANG_DS_TK_THANH_TOAN_CO);
			List<String> maTkThanhToanDs = Utils.parseString(taiKhoanThanhToanDs.getGiaTri());
			List<LoaiTaiKhoan> loaiTaiKhoanThanhToanDs = taiKhoanDAO.danhSachTaiKhoanCon(maTkThanhToanDs);
			model.addAttribute("loaiTaiKhoanThanhToanDs", loaiTaiKhoanThanhToanDs);

			CauHinh taiKhoanGtgtDs = props.getCauHinh(PropCont.MUA_HANG_DS_TK_GTGT_NO);
			List<String> maTkGtgtDs = Utils.parseString(taiKhoanGtgtDs.getGiaTri());
			List<LoaiTaiKhoan> loaiTaiKhoanGtgtDs = taiKhoanDAO.danhSachTaiKhoanCon(maTkGtgtDs);
			model.addAttribute("loaiTaiKhoanGtgtDs", loaiTaiKhoanGtgtDs);

			CauHinh taiKhoanTtdbDs = props.getCauHinh(PropCont.MUA_HANG_DS_TK_TTDB_CO);
			List<String> maTkTtdbDs = Utils.parseString(taiKhoanTtdbDs.getGiaTri());
			List<LoaiTaiKhoan> loaiTaiKhoanTtdbDs = taiKhoanDAO.danhSachTaiKhoanCon(maTkTtdbDs);
			model.addAttribute("loaiTaiKhoanTtdbDs", loaiTaiKhoanTtdbDs);

			CauHinh taiKhoanNkDs = props.getCauHinh(PropCont.MUA_HANG_DS_TK_NK_CO);
			List<String> maTkNkDs = Utils.parseString(taiKhoanNkDs.getGiaTri());
			List<LoaiTaiKhoan> loaiTaiKhoanNkDs = taiKhoanDAO.danhSachTaiKhoanCon(maTkNkDs);
			model.addAttribute("loaiTaiKhoanNkDs", loaiTaiKhoanNkDs);

			// Lấy danh sách các loại tiền
			List<LoaiTien> loaiTienDs = loaiTienDAO.danhSachLoaiTien();
			model.addAttribute("loaiTienDs", loaiTienDs);

			// Lấy danh sách tài khoản, dùng cho bên nợ & có
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoanCon();
			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);

			// Lấy danh sách kho
			List<KhoHang> khoBaiDs = hangHoaDAO.danhSachKhoBai();
			model.addAttribute("khoBaiDs", khoBaiDs);

			// Lấy danh sách kỹ hiệu hàng hóa
			List<HangHoa> khHangHoaDs = hangHoaDAO.danhSachKhHangHoa();
			model.addAttribute("khHangHoaDs", khHangHoaDs);

			model.addAttribute("tab", "tabCTMH");
			return chungTu.getNghiepVu();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/muahang/xoa/{id}")
	public String xoaMuaHang(@PathVariable("id") int maCt, Model model) {
		ChungTu chungTu = chungTuDAO.layChungTuKho(maCt, ChungTu.CHUNG_TU_MUA_HANG);
		if (chungTu == null) {
			return "redirect:/chungtu/muahang/danhsach";
		}

		KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
		if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/chungtu/muahang/xem/" + maCt;
		}

		try {
			// Xóa phiếu thu có MA_CT là maCt, LOAI_CT là ChungTu.CHUNG_TU_MUA_HANG
			chungTuDAO.xoaChungTuKho(chungTu);

			return "redirect:/chungtu/muahang/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/banhang/danhsach", method = { RequestMethod.GET, RequestMethod.POST })
	public String danhSachBanHang(@ModelAttribute("mainFinanceForm") ChungTuForm form, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			KyKeToan kyKeToanLc = null;
			if (form.getKyKeToan() != null) {
				kyKeToanLc = kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt());
			}
			if (kyKeToanLc == null) {
				kyKeToanLc = kyKeToan;
			}
			form.setKyKeToan(kyKeToanLc);

			Date homNay = new Date();
			if (!form.getKyKeToan().getBatDau().after(homNay) && !form.getKyKeToan().getKetThuc().before(homNay)) {
				// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của
				// tháng hiện tại
				if (form.getDau() == null) {
					form.setDau(Utils.getStartDateOfMonth(homNay));
				}

				if (form.getCuoi() == null) {
					form.setCuoi(Utils.getEndDateOfMonth(homNay));
				}
			} else {
				if (form.getDau() == null) {
					form.setDau(form.getKyKeToan().getBatDau());
				}

				if (form.getCuoi() == null) {
					form.setCuoi(form.getKyKeToan().getKetThuc());
				}
			}

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_BAN_HANG);

			// Lấy danh sách chứng từ mua hàng
			List<ChungTu> banHangDs = chungTuDAO.danhSachChungTuKho(loaiCts, form.getDau(), form.getCuoi());

			model.addAttribute("banHangDs", banHangDs);
			model.addAttribute("kyKeToanDs", kyKeToanDAO.danhSachKyKeToan());
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTBH");
			return "danhSachBanHang";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/banhang/danhsach/pdf/{dau}/{cuoi}", method = RequestMethod.GET)
	public void pdfDanhSachBanHang(HttpServletRequest req, HttpServletResponse res, @PathVariable("dau") String dau,
			@PathVariable("cuoi") String cuoi, Model model) {
		try {
			Date batDau = null;
			Date ketThuc = null;

			SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
			batDau = sdf.parse(dau);
			ketThuc = sdf.parse(cuoi);

			HashMap<String, Object> hmParams = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);
			hmParams.put(Contants.BAT_DAU, batDau);
			hmParams.put(Contants.KET_THUC, batDau);

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_BAN_HANG);
			List<ChungTu> banHangDs = chungTuDAO.danhSachChungTuKho(loaiCts, batDau, ketThuc);

			JasperReport jasperReport = getCompiledFile("banHangDs", req);
			byte[] bytes = baoCaoDAO.taoChungTu(jasperReport, hmParams, banHangDs);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			res.setHeader("Content-disposition", "inline; filename=banHangDs.pdf");
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/chungtu/banhang/danhsach/excel/{dau}/{cuoi}", method = RequestMethod.GET)
	public void excelDanhSachBanHang(HttpServletRequest req, HttpServletResponse res, @PathVariable("dau") String dau,
			@PathVariable("cuoi") String cuoi, Model model) {
		try {
			Date batDau = null;
			Date ketThuc = null;

			SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
			batDau = sdf.parse(dau);
			ketThuc = sdf.parse(cuoi);

			HashMap<String, Object> hmParams = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);
			hmParams.put(Contants.BAT_DAU, batDau);
			hmParams.put(Contants.KET_THUC, batDau);
			hmParams.put(Contants.COMPANY, props.getCauHinh(PropCont.TEN_CONG_TY).getGiaTri());
			hmParams.put(Contants.DIA_CHI, props.getCauHinh(PropCont.DIA_CHI).getGiaTri());
			hmParams.put(Contants.PAGE_TITLE, "DANH SÁCH CHỨNG TỪ BÁN HÀNG");
			hmParams.put(Contants.LOAI_CT, "Chứng từ bán hàng");

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_BAN_HANG);
			List<ChungTu> banHangDs = chungTuDAO.danhSachChungTuKho(loaiCts, batDau, ketThuc);

			XSSFWorkbook wb = baoCaoDAO.taoChungTuDs(banHangDs, hmParams);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8");
			res.setHeader("Content-disposition", "attachment; filename=BanHangDs.xlsx");
			ServletOutputStream out = res.getOutputStream();
			wb.write(out);
			out.flush();
			out.close();
			wb.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/chungtu/banhang/xem/{id}")
	public String xemBanHang(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layChungTuKho(maCt, ChungTu.CHUNG_TU_BAN_HANG);
			if (chungTu == null) {
				return "redirect:/chungtu/banhang/danhsach";
			}

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);

			model.addAttribute("chungTu", chungTu);
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTBH");
			return "xemBanHang";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/banhang/pdf/{id}", method = RequestMethod.GET)
	public void pdfBanHang(HttpServletRequest req, HttpServletResponse res, @PathVariable("id") int maCt, Model model) {
		try {
			HashMap<String, Object> hmParams = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_THU);
			List<ChungTu> chungTuDs = new ArrayList<>();
			chungTuDs.add(chungTu);

			JasperReport jasperReport = getCompiledFile("BanHang", req);
			byte[] bytes = baoCaoDAO.taoChungTu(jasperReport, hmParams, chungTuDs);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			res.setHeader("Content-disposition", "attachment; filename=BanHang" + maCt + ".pdf");
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/chungtu/banhang/sua/{id}")
	public String suaBanHang(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layChungTuKho(maCt, ChungTu.CHUNG_TU_BAN_HANG);
			if (chungTu == null) {
				return "redirect:/chungtu/banhang/danhsach";
			}
			chungTu.setNghiepVu("suaBanHang");

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/banhang/xem/" + maCt;
			}
			chungTu.setKyKeToan(kyKeToan);

			// Tạo tạm một hàng hóa mới làm mẫu, sau js sẽ xóa đi
			HangHoa hangHoa = new HangHoa();
			chungTu.themHangHoa(hangHoa);

			return chuanBiFormBanHang(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/banhang/saochep/{id}")
	public String saoChepBanHang(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/banhang/danhsach";
			}

			ChungTu chungTu = chungTuDAO.layChungTuKho(maCt, ChungTu.CHUNG_TU_BAN_HANG);
			if (chungTu == null) {
				return "redirect:/chungtu/banhang/danhsach";
			}
			chungTu.setKyKeToan(kyKeToan);

			// Lấy số phiếu thu của năm hiện tại
			int soBanHang = chungTuDAO.laySoChungTuLonNhatTheoLoaiCtVaKy(ChungTu.CHUNG_TU_BAN_HANG,
					kyKeToan.getBatDau(), kyKeToan.getKetThuc());
			soBanHang++;
			chungTu.setSoCt(soBanHang);
			chungTu.setNghiepVu("saoChepBanHang");

			// Tạo tạm một hàng hóa mới làm mẫu, sau js sẽ xóa đi
			HangHoa hangHoa = new HangHoa();
			chungTu.themHangHoa(hangHoa);

			return chuanBiFormBanHang(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/banhang/taomoi/{tinhChatCt}")
	public String taoMoiBanHang(Model model, @PathVariable("tinhChatCt") int tinhChatCt) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/banhang/danhsach";
			}

			ChungTu chungTu = new ChungTu();
			chungTu.setLoaiCt(ChungTu.CHUNG_TU_BAN_HANG);
			chungTu.setNghiepVu("taoMoiBanHang");
			chungTu.setKyKeToan(kyKeToan);

			// Lấy số phiếu thu của năm hiện tại
			int soKeToanTongHop = chungTuDAO.laySoChungTuLonNhatTheoLoaiCtVaKy(ChungTu.CHUNG_TU_BAN_HANG,
					kyKeToan.getBatDau(), kyKeToan.getKetThuc());
			soKeToanTongHop++;

			chungTu.setTinhChatCt(tinhChatCt);
			chungTu.setChieu(ChungTu.BAN);

			chungTu.setSoCt(soKeToanTongHop);
			Date homNay = new Date();
			chungTu.setNgayLap(homNay);
			chungTu.setNgayHt(homNay);

			// Tạo tạm một hàng hóa mới làm mẫu, sau js sẽ xóa đi
			HangHoa hangHoa = new HangHoa();
			chungTu.themHangHoa(hangHoa);

			return chuanBiFormBanHang(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/banhang/luu", method = RequestMethod.POST)
	public String luuTaoMoiBanHang(@ModelAttribute("mainFinanceForm") @Validated ChungTu chungTu, BindingResult result,
			Model model, HttpServletRequest req) {
		FlashMap flash = RequestContextUtils.getOutputFlashMap(req);
		String message = messageSource.getMessage("Fail.TaoMoi.ChungTu", null, LocaleContextHolder.getLocale());
		String ketQua = "/chungtu/banhang/danhsach";
		if (chungTu.getMaCt() > 0) {
			ketQua = "/chungtu/banhang/xem/" + chungTu.getMaCt();
			message = messageSource.getMessage("Fail.CapNhat.ChungTu", null, LocaleContextHolder.getLocale());
		}

		try {
			if (result.hasErrors()) {
				HangHoa hangHoa = new HangHoa();
				chungTu.themHangHoa(hangHoa);

				return chuanBiFormBanHang(model, chungTu);
			}

			logger.info("Lưu chứng từ: " + chungTu);

			chungTu.setLoaiCt(ChungTu.CHUNG_TU_BAN_HANG);

			if (chungTu.getHangHoaDs() != null) {
				// Chuẩn hóa dữ liệu trước khi cập nhật vào CSDL
				// Đảm bảo các trường dữ liệu đúng với ý nghĩa của nó
				// Đảm bảo giá trị được tính thống nhất theo loại tiền đã chọn
				// Với từng loại hàng hóa
				KyKeToan kyKeToan = dungChung.getKyKeToan();
				int nhomDk = 1;
				DoiTuong doiTuong = chungTu.getDoiTuong();
				List<TaiKhoan> taiKhoanKtthDs = new ArrayList<>();

				int soHangHoa = chungTu.getHangHoaDs().size();
				Iterator<HangHoa> hhIter = chungTu.getHangHoaDs().iterator();
				while (hhIter.hasNext()) {
					HangHoa hangHoa = hhIter.next();
					hangHoa.getGiaKho().setLoaiTien(chungTu.getLoaiTien());
					hangHoa.getGiaKho().setSoTien(hangHoa.getGiaKho().getGiaTri() / chungTu.getLoaiTien().getBanRa());
					hangHoa.getDonGia().setLoaiTien(chungTu.getLoaiTien());

					// Tổng thuế: thực ra chỉ một trong 2: thuế XNK hoặc thuế GTGT
					double tongThue = 0;
					if (hangHoa.getTkThueGtgt() != null && hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk() != null
							&& !hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk().trim().equals("")
							&& hangHoa.getTkThueGtgt().getSoTien() != null) {
						// Có thuế Gtgt, tính theo phương pháp khấu trừ
						double thue = hangHoa.getTkThueGtgt().getSoTien().getGiaTri()
								/ chungTu.getLoaiTien().getBanRa();
						hangHoa.getTkThueGtgt().getSoTien().setSoTien(thue);
						hangHoa.getTkThueGtgt().getSoTien().setLoaiTien(chungTu.getLoaiTien());
						tongThue = tongThue + thue;

						if (soHangHoa > 1) {
							hangHoa.getTkThueGtgt().setLyDo(chungTu.getLyDo() + ": " + hangHoa.getTenHh());
						} else {
							hangHoa.getTkThueGtgt().setLyDo(chungTu.getLyDo());
						}
					}

					if (hangHoa.getTkThueXk() != null && hangHoa.getTkThueXk().getLoaiTaiKhoan().getMaTk() != null
							&& !hangHoa.getTkThueXk().getLoaiTaiKhoan().getMaTk().trim().equals("")
							&& hangHoa.getTkThueXk().getSoTien() != null) {
						double thue = hangHoa.getTkThueXk().getSoTien().getGiaTri() / chungTu.getLoaiTien().getBanRa();
						hangHoa.getTkThueXk().getSoTien().setSoTien(thue);
						hangHoa.getTkThueXk().getSoTien().setLoaiTien(chungTu.getLoaiTien());
						tongThue = tongThue + thue;

						if (soHangHoa > 1) {
							hangHoa.getTkThueXk().setLyDo(chungTu.getLyDo() + ": " + hangHoa.getTenHh());
						} else {
							hangHoa.getTkThueXk().setLyDo(chungTu.getLyDo());
						}
					}

					// Tổng doanh thu
					double tongDoanhThu = hangHoa.getSoLuong() * hangHoa.getDonGia().getSoTien();
					if (hangHoa.getTkDoanhThu() != null && hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk() != null
							&& !hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
						hangHoa.getTkDoanhThu().getSoTien().setLoaiTien(chungTu.getLoaiTien());
						hangHoa.getTkDoanhThu().getSoTien().setSoTien(tongDoanhThu);
						hangHoa.getTkDoanhThu().getSoTien().setGiaTri(
								hangHoa.getTkDoanhThu().getSoTien().getSoTien() * chungTu.getLoaiTien().getBanRa());

						if (soHangHoa > 1) {
							hangHoa.getTkDoanhThu().setLyDo(chungTu.getLyDo() + ": " + hangHoa.getTenHh());
						} else {
							hangHoa.getTkDoanhThu().setLyDo(chungTu.getLyDo());
						}
					}

					// Tổng thanh toán
					double tongThanhToan = tongDoanhThu + tongThue;
					if (hangHoa.getTkThanhtoan() != null && hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk() != null
							&& !hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
						hangHoa.getTkThanhtoan().getSoTien().setLoaiTien(chungTu.getLoaiTien());
						hangHoa.getTkThanhtoan().getSoTien().setSoTien(tongThanhToan);
						hangHoa.getTkThanhtoan().getSoTien().setGiaTri(
								hangHoa.getTkThanhtoan().getSoTien().getSoTien() * chungTu.getLoaiTien().getBanRa());

						if (soHangHoa > 1) {
							hangHoa.getTkThanhtoan().setLyDo(chungTu.getLyDo() + ": " + hangHoa.getTenHh());
						} else {
							hangHoa.getTkThanhtoan().setLyDo(chungTu.getLyDo());
						}
					}

					// Tổng giá vốn
					double tongGiaVon = hangHoa.getSoLuong() * hangHoa.getGiaKho().getSoTien();
					if (hangHoa.getTkGiaVon() != null && hangHoa.getTkGiaVon().getLoaiTaiKhoan().getMaTk() != null
							&& !hangHoa.getTkGiaVon().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
						hangHoa.getTkGiaVon().getSoTien().setLoaiTien(chungTu.getLoaiTien());
						hangHoa.getTkGiaVon().getSoTien().setSoTien(tongGiaVon);
						hangHoa.getTkGiaVon().getSoTien().setGiaTri(
								hangHoa.getTkGiaVon().getSoTien().getSoTien() * chungTu.getLoaiTien().getBanRa());
						hangHoa.getTkGiaVon().setNhomDk(1);

						if (soHangHoa > 1) {
							hangHoa.getTkGiaVon().setLyDo(chungTu.getLyDo() + ": " + hangHoa.getTenHh());
						} else {
							hangHoa.getTkGiaVon().setLyDo(chungTu.getLyDo());
						}
					}

					if (hangHoa.getTkKho() != null && hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk() != null
							&& !hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
						hangHoa.getTkKho().getSoTien().setLoaiTien(chungTu.getLoaiTien());
						hangHoa.getTkKho().getSoTien().setSoTien(tongGiaVon);
						hangHoa.getTkKho().getSoTien().setGiaTri(
								hangHoa.getTkKho().getSoTien().getSoTien() * chungTu.getLoaiTien().getBanRa());
						hangHoa.getTkKho().setNhomDk(1);

						if (soHangHoa > 1) {
							hangHoa.getTkKho().setLyDo(chungTu.getLyDo() + ": " + hangHoa.getTenHh());
						} else {
							hangHoa.getTkKho().setLyDo(chungTu.getLyDo());
						}
					}

					// Chuẩn bị dữ liệu ktth, nếu có
					if (hangHoa != null && hangHoa.getNvktDs() != null && hangHoa.getNvktDs().size() > 0) {
						NghiepVuKeToan nvkt = hangHoa.getNvktDs().get(0);

						TaiKhoan taiKhoanNo = nvkt.getTaiKhoanNo();
						taiKhoanNo.getSoTien()
								.setGiaTri(taiKhoanNo.getSoTien().getSoTien() * chungTu.getLoaiTien().getBanRa());
						taiKhoanNo.setSoDu(LoaiTaiKhoan.NO);
						taiKhoanNo.setNhomDk(nhomDk);
						taiKhoanNo.setDoiTuong(doiTuong);
						taiKhoanNo.setNo(taiKhoanNo.getSoTien());

						TaiKhoan taiKhoanCo = nvkt.getTaiKhoanCo();
						taiKhoanCo.getSoTien()
								.setGiaTri(taiKhoanCo.getSoTien().getSoTien() * chungTu.getLoaiTien().getBanRa());
						taiKhoanCo.setLyDo(taiKhoanNo.getLyDo());
						taiKhoanCo.setSoDu(LoaiTaiKhoan.CO);
						taiKhoanCo.setCo(taiKhoanNo.getSoTien());
						taiKhoanCo.setNhomDk(nhomDk);
						taiKhoanCo.setDoiTuong(doiTuong);

						if ((taiKhoanNo.getLyDo() == null || taiKhoanNo.getLyDo().isEmpty())
								&& taiKhoanNo.getSoTien().getSoTien() == 0
								&& (taiKhoanNo.getLoaiTaiKhoan() == null
										|| taiKhoanNo.getLoaiTaiKhoan().getMaTk() == null
										|| taiKhoanNo.getLoaiTaiKhoan().getMaTk().isEmpty())
								&& (taiKhoanCo.getLoaiTaiKhoan() == null
										|| taiKhoanCo.getLoaiTaiKhoan().getMaTk() == null
										|| taiKhoanCo.getLoaiTaiKhoan().getMaTk().isEmpty())) {
							// Không có dữ liệu, bỏ qua
							logger.info("Không có dữ liệu, bỏ qua");
						} else {
							taiKhoanKtthDs.add(taiKhoanNo);
							taiKhoanKtthDs.add(taiKhoanCo);
							nhomDk++;
						}
					}
				}

				if (chungTu.getMaCt() > 0) {
					chungTuDAO.capNhatChungTuKho(chungTu);
				} else {
					chungTuDAO.themChungTuKho(chungTu);
				}

				// Tạo phiếu kế toán tổng hợp kèm theo nếu cần
				if (taiKhoanKtthDs != null && taiKhoanKtthDs.size() > 0) {
					chungTu.setDoiTuong(new DoiTuong());
					chungTu.setLoaiCt(ChungTu.CHUNG_TU_KT_TH);
					// Lấy số phiếu thu của năm hiện tại
					int soKeToanTongHop = chungTuDAO.laySoChungTuLonNhatTheoLoaiCtVaKy(ChungTu.CHUNG_TU_KT_TH,
							kyKeToan.getBatDau(), kyKeToan.getKetThuc());
					soKeToanTongHop++;
					chungTu.setSoCt(soKeToanTongHop);
					chungTu.setTaiKhoanKtthDs(taiKhoanKtthDs);
					chungTuDAO.themChungTuKtth(chungTu);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(message);
			flash.put("message", message);
		}

		return "redirect:" + ketQua;
	}

	private String chuanBiFormBanHang(Model model, ChungTu chungTu) {
		try {
			model.addAttribute("mainFinanceForm", chungTu);

			CauHinh taiKhoanThanhToanDs = props.getCauHinh(PropCont.BAN_HANG_DS_TK_THANH_TOAN_NO);
			List<String> maTkThanhToanDs = Utils.parseString(taiKhoanThanhToanDs.getGiaTri());
			List<LoaiTaiKhoan> loaiTaiKhoanThanhToanDs = taiKhoanDAO.danhSachTaiKhoanCon(maTkThanhToanDs);
			model.addAttribute("loaiTaiKhoanThanhToanDs", loaiTaiKhoanThanhToanDs);

			CauHinh taiKhoanDoanhThuDs = props.getCauHinh(PropCont.BAN_HANG_DS_TK_DOANH_THU_CO);
			List<String> maTkDoanhThuDs = Utils.parseString(taiKhoanDoanhThuDs.getGiaTri());
			List<LoaiTaiKhoan> loaiTaiKhoanDoanhThuDs = taiKhoanDAO.danhSachTaiKhoanCon(maTkDoanhThuDs);
			model.addAttribute("loaiTaiKhoanDoanhThuDs", loaiTaiKhoanDoanhThuDs);

			CauHinh taiKhoanGiaVonDs = props.getCauHinh(PropCont.BAN_HANG_DS_TK_GIA_VON_NO);
			List<String> maTkGiaVonDs = Utils.parseString(taiKhoanGiaVonDs.getGiaTri());
			List<LoaiTaiKhoan> loaiTaiKhoanGiaVonDs = taiKhoanDAO.danhSachTaiKhoanCon(maTkGiaVonDs);
			model.addAttribute("loaiTaiKhoanGiaVonDs", loaiTaiKhoanGiaVonDs);

			CauHinh taiKhoanKhoDs = props.getCauHinh(PropCont.BAN_HANG_DS_TK_KHO_NO);
			List<String> maTkKhoDs = Utils.parseString(taiKhoanKhoDs.getGiaTri());
			List<LoaiTaiKhoan> loaiTaiKhoanKhoDs = taiKhoanDAO.danhSachTaiKhoanCon(maTkKhoDs);
			model.addAttribute("loaiTaiKhoanKhoDs", loaiTaiKhoanKhoDs);

			CauHinh taiKhoanGtgtDs = props.getCauHinh(PropCont.BAN_HANG_DS_TK_GTGT_CO);
			List<String> maTkGtgtDs = Utils.parseString(taiKhoanGtgtDs.getGiaTri());
			List<LoaiTaiKhoan> loaiTaiKhoanGtgtDs = taiKhoanDAO.danhSachTaiKhoanCon(maTkGtgtDs);
			model.addAttribute("loaiTaiKhoanGtgtDs", loaiTaiKhoanGtgtDs);

			CauHinh taiKhoanXkDs = props.getCauHinh(PropCont.BAN_HANG_DS_TK_XK_CO);
			List<String> maTkXkDs = Utils.parseString(taiKhoanXkDs.getGiaTri());
			List<LoaiTaiKhoan> loaiTaiKhoanXkDs = taiKhoanDAO.danhSachTaiKhoanCon(maTkXkDs);
			model.addAttribute("loaiTaiKhoanXkDs", loaiTaiKhoanXkDs);

			// Lấy danh sách các loại tiền
			List<LoaiTien> loaiTienDs = loaiTienDAO.danhSachLoaiTien();
			model.addAttribute("loaiTienDs", loaiTienDs);

			// Lấy danh sách tài khoản, dùng cho bên nợ & có
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoanCon();
			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);

			// Lấy danh sách kho
			List<KhoHang> khoBaiDs = hangHoaDAO.danhSachKhoBai();
			model.addAttribute("khoBaiDs", khoBaiDs);

			// Lấy danh sách kỹ hiệu hàng hóa
			List<HangHoa> khHangHoaDs = hangHoaDAO.danhSachKhHangHoa();
			model.addAttribute("khHangHoaDs", khHangHoaDs);

			// Chuẩn bị danh sách đơn giá
			if (chungTu.getHangHoaDs() != null) {
				Iterator<HangHoa> iter = chungTu.getHangHoaDs().iterator();
				while (iter.hasNext()) {
					HangHoa hangHoa = iter.next();

					List<DonGia> donGiaDs = new ArrayList<>();
					if (hangHoa.getKho() != null) {
						donGiaDs = khoHangDAO.layDonGiaDs(hangHoa.getMaHh(), hangHoa.getKho().getMaKho());
					} else {
						donGiaDs = khoHangDAO.layDonGiaDs(hangHoa.getMaHh(), 0);
					}

					hangHoa.setDonGiaDs(donGiaDs);
				}
			}

			model.addAttribute("tab", "tabCTBH");
			return chungTu.getNghiepVu();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/banhang/xoa/{id}")
	public String xoaBanHang(@PathVariable("id") int maCt, Model model) {
		ChungTu chungTu = chungTuDAO.layChungTuKho(maCt, ChungTu.CHUNG_TU_BAN_HANG);
		if (chungTu == null) {
			return "redirect:/chungtu/banhang/danhsach";
		}

		KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
		if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/chungtu/banhang/xem/" + maCt;
		}

		try {
			// Xóa phiếu thu có MA_CT là maCt, LOAI_CT là ChungTu.CHUNG_TU_BAN_HANG
			chungTuDAO.xoaChungTuKho(chungTu);

			return "redirect:/chungtu/banhang/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/ketchuyen/danhsach", method = { RequestMethod.GET, RequestMethod.POST })
	public String danhSachKetChuyen(@ModelAttribute("mainFinanceForm") ChungTuForm form, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			KyKeToan kyKeToanLc = null;
			if (form.getKyKeToan() != null) {
				kyKeToanLc = kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt());
			}
			if (kyKeToanLc == null) {
				kyKeToanLc = kyKeToan;
			}
			form.setKyKeToan(kyKeToanLc);

			if (form.getDau() == null) {
				form.setDau(form.getKyKeToan().getBatDau());
			}

			if (form.getCuoi() == null) {
				form.setCuoi(form.getKyKeToan().getKetThuc());
			}

			// Lấy danh sách kết chuyển
			List<ChungTu> ketChuyenDs = chungTuDAO.danhSachKetChuyen(form.getDau(), form.getCuoi());

			model.addAttribute("ketChuyenDs", ketChuyenDs);
			model.addAttribute("kyKeToanDs", kyKeToanDAO.danhSachKyKeToan());
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTKC");
			return "danhSachKetChuyen";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/ketchuyen/xem/{id}")
	public String xemKetChuyen(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layKetChuyen(maCt);
			if (chungTu == null) {
				return "redirect:/chungtu/ketchuyen/danhsach";
			}

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);

			model.addAttribute("chungTu", chungTu);
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTKC");
			return "xemKetChuyen";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/ketchuyen/taomoi/{tinhChatCt}")
	public String taoMoiKetChuyen(Model model, @PathVariable("tinhChatCt") int tinhChatCt) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/ketchuyen/danhsach";
			}

			ChungTu chungTu = new ChungTu();
			chungTu.setLoaiCt(ChungTu.CHUNG_TU_KET_CHUYEN);
			chungTu.setTinhChatCt(tinhChatCt);
			chungTu.setKyKeToan(kyKeToan);

			// Lấy số phiếu thu của năm hiện tại
			int soKetChuyen = chungTuDAO.laySoChungTuLonNhatTheoLoaiCtVaKy(ChungTu.CHUNG_TU_KET_CHUYEN,
					kyKeToan.getBatDau(), kyKeToan.getKetThuc());
			soKetChuyen++;
			chungTu.setSoCt(soKetChuyen);

			Date ngayHt = new Date();
			chungTu.setNgayHt(ngayHt);

			Date ngayLap = chungTuDAO.layKetChuyenGanNhat(tinhChatCt, kyKeToan.getBatDau(), kyKeToan.getKetThuc());
			if (ngayLap == null)
				ngayLap = kyKeToan.getBatDau();
			chungTu.setNgayLap(ngayLap);

			return chuanBiFormKetChuyen(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/ketchuyen/luu", method = RequestMethod.POST)
	public String luuTaoMoiKetChuyen(@ModelAttribute("mainFinanceForm") @Validated ChungTu chungTu,
			BindingResult result, Model model) {
		try {
			if (result.hasErrors()) {
				return chuanBiFormKetChuyen(model, chungTu);
			}

			logger.info("Lưu chứng từ: " + chungTu);

			List<KetChuyenButToan> kcbtDs = new ArrayList<>();
			if (chungTu.getKcbtDs() != null) {
				Iterator<KetChuyenButToan> iter = chungTu.getKcbtDs().iterator();
				while (iter.hasNext()) {
					KetChuyenButToan ketChuyenButToan = iter.next();
					if (ketChuyenButToan.isChon()) {
						kcbtDs.add(ketChuyenButToan);
					}
				}
			}
			chungTu.setKcbtDs(kcbtDs);

			chungTuDAO.themChungTuKetChuyen(chungTu);

			return "redirect:/chungtu/ketchuyen/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormKetChuyen(Model model, ChungTu chungTu) {
		try {
			if (chungTu.getKcbtDs() == null || chungTu.getKcbtDs().size() == 0) {
				chungTu.themKetChuyenButToan(chungTuDAO.danhSachKetChuyenButToan(chungTu.getTinhChatCt()));
			}

			model.addAttribute("mainFinanceForm", chungTu);
			model.addAttribute("tab", "tabCTKC");
			return "taoMoiKetChuyen";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/ketchuyen/xoa/{id}")
	public String xoaKetChuyen(@PathVariable("id") int maCt, Model model) {
		ChungTu chungTu = chungTuDAO.layKetChuyen(maCt);
		if (chungTu == null) {
			return "redirect:/chungtu/ketchuyen/danhsach";
		}

		KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
		if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/chungtu/ketchuyen/xem/" + maCt;
		}

		try {
			// Xóa kết chuyển có MA_CT là maCt, LOAI_CT là ChungTu.CHUNG_TU_PHIEU_THU
			chungTuDAO.xoaKetChuyen(chungTu);

			return "redirect:/chungtu/ketchuyen/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/ketchuyen/buttoan/danhsach")
	public String danhSachKetChuyenButToan(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			// Lấy danh sách bút toán kết chuyển
			List<KetChuyenButToan> ketChuyenButToanDs = chungTuDAO.danhSachKetChuyenButToan();

			model.addAttribute("ketChuyenButToanDs", ketChuyenButToanDs);
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTKC");
			return "danhSachKetChuyenButToan";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/ketchuyen/buttoan/xem/{id}")
	public String xemKetChuyenButToan(@PathVariable("id") int maKc, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			KetChuyenButToan ketChuyenButToan = chungTuDAO.layKetChuyenButToan(maKc);
			if (ketChuyenButToan == null) {
				return "redirect:/chungtu/ketchuyen/buttoan/danhsach";
			}

			model.addAttribute("ketChuyenButToan", ketChuyenButToan);
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTKC");
			return "xemKetChuyenButToan";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/ketchuyen/buttoan/pdf/{id}", method = RequestMethod.GET)
	public void pdfKetChuyenButToan(HttpServletRequest req, HttpServletResponse res, @PathVariable("id") int maCt,
			Model model) {
		try {
			HashMap<String, Object> hmParams = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_THU);
			List<ChungTu> chungTuDs = new ArrayList<>();
			chungTuDs.add(chungTu);

			JasperReport jasperReport = getCompiledFile("KetChuyenButToan", req);
			byte[] bytes = baoCaoDAO.taoChungTu(jasperReport, hmParams, chungTuDs);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			res.setHeader("Content-disposition", "attachment; filename=KetChuyenButToan" + maCt + ".pdf");
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/chungtu/ketchuyen/buttoan/sua/{id}")
	public String suaKetChuyenButToan(@PathVariable("id") int maKc, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			KetChuyenButToan ketChuyenButToan = chungTuDAO.layKetChuyenButToan(maKc);
			if (ketChuyenButToan == null) {
				return "redirect:/chungtu/ketchuyen/buttoan/danhsach";
			}

			return chuanBiFormKetChuyenButToan(model, ketChuyenButToan);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/ketchuyen/buttoan/taomoi")
	public String taoMoiKetChuyenButToan(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/ketchuyen/buttoan/danhsach";
			}

			KetChuyenButToan ketChuyenButToan = new KetChuyenButToan();

			return chuanBiFormKetChuyenButToan(model, ketChuyenButToan);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/ketchuyen/buttoan/luu", method = RequestMethod.POST)
	public String luuTaoMoiKetChuyenButToan(
			@ModelAttribute("mainFinanceForm") @Validated KetChuyenButToan ketChuyenButToan, BindingResult result,
			Model model) {
		try {
			if (result.hasErrors()) {
				return chuanBiFormKetChuyenButToan(model, ketChuyenButToan);
			}

			logger.info("Lưu bút toán kết chuyển tự động: " + ketChuyenButToan);

			if (ketChuyenButToan.getMaKc() > 0) {
				chungTuDAO.capNhatKetChuyenButToan(ketChuyenButToan);
				return "redirect:/chungtu/ketchuyen/buttoan/xem/" + ketChuyenButToan.getMaKc();
			} else {
				chungTuDAO.themKetChuyenButToan(ketChuyenButToan);
			}

			return "redirect:/chungtu/ketchuyen/buttoan/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormKetChuyenButToan(Model model, KetChuyenButToan ketChuyenButToan) {
		try {
			model.addAttribute("mainFinanceForm", ketChuyenButToan);

			// Lấy danh sách tài khoản
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoanCon();
			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);

			model.addAttribute("tab", "tabCTKC");
			if (ketChuyenButToan.getMaKc() > 0) {
				// Đây là trường hợp sửa KC
				return "suaKetChuyenButToan";
			} else {
				// Đây là trường hợp tạo mới KC
				return "taoMoiKetChuyenButToan";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/ketchuyen/buttoan/xoa/{id}")
	public String xoaKetChuyenButToan(@PathVariable("id") int maKc, Model model) {
		try {
			// Xóa bút toán kết chuyển có MA_KC là maKc
			chungTuDAO.xoaKetChuyenButToan(maKc);

			return "redirect:/chungtu/ketchuyen/buttoan/danhsach";
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

	@RequestMapping("/chungtu/doituong")
	public @ResponseBody List<DoiTuong> danhSachDoiTuong(@RequestParam("query") String maHoacTen) {
		List<DoiTuong> nhanVienDs = nhanVienDAO.danhSachDoiTuong(maHoacTen);
		List<DoiTuong> khachHangDs = khachHangDAO.danhSachDoiTuong(maHoacTen);
		List<DoiTuong> nhaCungCapDs = nhaCungCapDAO.danhSachDoiTuong(maHoacTen);

		List<DoiTuong> doiTuongDs = new ArrayList<>();
		doiTuongDs.addAll(nhanVienDs);
		doiTuongDs.addAll(khachHangDs);
		doiTuongDs.addAll(nhaCungCapDs);

		return doiTuongDs;
	}

	@RequestMapping("/chungtu/sochungtu")
	public @ResponseBody String laySoChungTuLonNhat(@RequestParam("loaiCt") String loaiCt) {
		KyKeToan kyKeToan = dungChung.getKyKeToan();
		if (kyKeToan == null) {
			return ")";
		}
		if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "0";
		}

		int soCt = chungTuDAO.laySoChungTuLonNhatTheoLoaiCtVaKy(loaiCt, kyKeToan.getBatDau(), kyKeToan.getKetThuc());
		logger.info("Số chứng từ lớn nhất của loại chứng từ " + loaiCt + " trong kỳ kế toán " + kyKeToan.getTenKyKt()
				+ ": " + soCt);

		return soCt + "";
	}

	@RequestMapping("/chungtu/hanghoa")
	public @ResponseBody HangHoa layHangHoa(@RequestParam("maHh") int maHh) {
		logger.info("maHh " + maHh);
		return hangHoaDAO.layHangHoa(maHh);
	}

	@RequestMapping("/chungtu/hanghoa/danhsach")
	public @ResponseBody List<HangHoa> danhSachHangHoa(@RequestParam("tuKhoa") String tuKhoa) {
		return hangHoaDAO.danhSachHangHoa(tuKhoa);
	}

	@RequestMapping("/chungtu/hanghoa/giavonds")
	public @ResponseBody List<DonGia> danhSachGiaVon(@RequestParam("maHh") int maHh, @RequestParam("maKho") int maKho) {
		logger.info("maHh " + maHh + ", maKho " + maKho);
		return khoHangDAO.layDonGiaDs(maHh, maKho);
	}

	@RequestMapping("/chungtu/ketchuyen/buttoan")
	public @ResponseBody KetChuyenButToan layKetChuyenButToan(@RequestParam("maKc") int maKc) {
		logger.info("maKc " + maKc);
		return chungTuDAO.layKetChuyenButToan(maKc);
	}

	private JasperReport getCompiledFile(String fileName, HttpServletRequest req) throws JRException {
		String jrxml = req.getSession().getServletContext().getRealPath("/baocao/chungtu/" + fileName + ".jrxml");
		String jasper = req.getSession().getServletContext().getRealPath("/baocao/chungtu/" + fileName + ".jasper");

		File reportFile = new File(jasper);
		// If compiled file is not found, then compile XML template
		// if (!reportFile.exists()) {
		logger.info("Compile Jasper report ...");
		JasperCompileManager.compileReportToFile(jrxml, jasper);
		// }
		JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportFile.getPath());
		return jasperReport;
	}
}
