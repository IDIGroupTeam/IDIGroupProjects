package com.idi.finance.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.idi.finance.bean.LoaiTien;
import com.idi.finance.bean.NhanVien;
import com.idi.finance.bean.bieudo.KpiGroup;
import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.DoiTuong;
import com.idi.finance.bean.chungtu.TaiKhoan;
import com.idi.finance.bean.doitac.KhachHang;
import com.idi.finance.bean.doitac.NhaCungCap;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.BaoCaoDAO;
import com.idi.finance.dao.ChungTuDAO;
import com.idi.finance.dao.KhachHangDAO;
import com.idi.finance.dao.KpiChartDAO;
import com.idi.finance.dao.NhaCungCapDAO;
import com.idi.finance.dao.NhanVienDAO;
import com.idi.finance.dao.TaiKhoanDAO;
import com.idi.finance.validator.ChungTuValidator;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

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
	BaoCaoDAO baoCaoDAO;

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

	@RequestMapping(value = "/pdfphieuthu/{id}", method = RequestMethod.GET)
	public void pdfPhieuThu(HttpServletRequest req, HttpServletResponse res, @PathVariable("id") int maCt,
			Model model) {
		try {
			JasperReport jasperReport = getCompiledFile("PhieuThu", req);
			byte[] bytes = baoCaoDAO.taoBaoCaoChungTu(jasperReport, maCt);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException e) {
			e.printStackTrace();
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
			taiKhoan.setSoDu(LoaiTaiKhoan.NO);
			chungTu.themTaiKhoan(taiKhoan);

			// Tài khoản khác, ghi có
			taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			taiKhoan.setSoDu(LoaiTaiKhoan.CO);
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
				return "redirect:/xemphieuthu/" + chungTu.getMaCt();
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
				return "redirect:/danhsachphieuchi";
			}
			model.addAttribute("chungTu", chungTu);

			model.addAttribute("tab", "tabCTPC");
			return "xemPhieuChi";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/pdfphieuchi/{id}", method = RequestMethod.GET)
	public void pdfPhieuChi(HttpServletRequest req, HttpServletResponse res, @PathVariable("id") int maCt,
			Model model) {
		try {
			JasperReport jasperReport = getCompiledFile("PhieuChi", req);
			byte[] bytes = baoCaoDAO.taoBaoCaoChungTu(jasperReport, maCt);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException e) {
			e.printStackTrace();
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
				return "redirect:/danhsachphieuchi";
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
			taiKhoan.setSoDu(LoaiTaiKhoan.NO);
			chungTu.themTaiKhoan(taiKhoan);

			// Tài khoản tiềm mặt, ghi có
			taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			taiKhoan.setSoDu(LoaiTaiKhoan.CO);
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
				return "redirect:/xemphieuchi/" + chungTu.getMaCt();
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

	@RequestMapping("/xembaoco/{id}")
	public String xemBaoCo(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_BAO_CO);
			if (chungTu == null) {
				return "redirect:/danhsachbaoco";
			}
			model.addAttribute("chungTu", chungTu);

			model.addAttribute("tab", "tabCTBC");
			return "xemBaoCo";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/pdfbaoco/{id}", method = RequestMethod.GET)
	public void pdfBaoCo(HttpServletRequest req, HttpServletResponse res, @PathVariable("id") int maCt, Model model) {
		try {
			JasperReport jasperReport = getCompiledFile("BaoCo", req);
			byte[] bytes = baoCaoDAO.taoBaoCaoChungTu(jasperReport, maCt);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/suabaoco/{id}")
	public String suaBaoCo(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_BAO_CO);
			if (chungTu == null) {
				return "redirect:/danhsachbaoco";
			}

			return chuanBiFormBaoCo(model, chungTu);
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

			ChungTu chungTu = new ChungTu();
			chungTu.setLoaiCt(ChungTu.CHUNG_TU_BAO_CO);

			// Lấy số báo có của năm hiện tại
			int soBaoCo = chungTuDAO.demSoChungTuTheoLoaiCtVaNam(ChungTu.CHUNG_TU_BAO_CO, new Date());
			soBaoCo++;
			chungTu.setSoCt(soBaoCo);

			Date ngayLap = new Date();
			chungTu.setNgayLap(ngayLap);
			chungTu.setNgayHt(ngayLap);

			// Tài khoản tiềm gửi ngân hàng, ghi nợ
			TaiKhoan taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			taiKhoan.setSoDu(LoaiTaiKhoan.NO);
			chungTu.themTaiKhoan(taiKhoan);

			// Tài khoản khác, ghi có
			taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			taiKhoan.setSoDu(LoaiTaiKhoan.CO);
			chungTu.themTaiKhoan(taiKhoan);

			return chuanBiFormBaoCo(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/luutaomoibaoco", method = RequestMethod.POST)
	public String luuTaoMoiBaoCo(@ModelAttribute("mainFinanceForm") @Validated ChungTu chungTu, BindingResult result,
			Model model) {
		try {
			if (result.hasErrors()) {
				return chuanBiFormBaoCo(model, chungTu);
			}

			logger.info("Lưu chứng từ: " + chungTu);

			chungTu.setLoaiCt(ChungTu.CHUNG_TU_BAO_CO);

			if (chungTu.getMaCt() > 0) {
				chungTuDAO.capNhatChungTu(chungTu);
				return "redirect:/xembaoco/" + chungTu.getMaCt();
			} else {
				chungTuDAO.themChungTu(chungTu);
			}

			return "redirect:/danhsachbaoco";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormBaoCo(Model model, ChungTu chungTu) {
		try {
			model.addAttribute("mainFinanceForm", chungTu);

			// Lấy danh sách các loại tiền
			List<LoaiTien> loaiTienDs = chungTuDAO.danhSachLoaiTien();
			model.addAttribute("loaiTienDs", loaiTienDs);

			// Lấy danh sách tài khoản tiền gửi ngân hàng, dùng cho bên nợ
			List<LoaiTaiKhoan> loaiTaiKhoanTgnhDs = taiKhoanDAO
					.danhSachTaiKhoanTheoCap1(LoaiTaiKhoan.TIEN_GUI_NGAN_HANG);
			model.addAttribute("loaiTaiKhoanTgnhDs", loaiTaiKhoanTgnhDs);

			// Lấy danh sách tài khoản, dùng cho bên có
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();
			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);

			model.addAttribute("tab", "tabCTBC");
			if (chungTu.getMaCt() > 0) {
				// Đây là trường hợp sửa CT
				return "suaBaoCo";
			} else {
				// Đây là trường hợp tạo mới CT
				return "taoMoiBaoCo";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/xoabaoco/{id}")
	public String xoaBaoCo(@PathVariable("id") int maCt, Model model) {
		try {
			// Xóa phiếu thu có MA_CT là maCt, LOAI_CT là ChungTu.CHUNG_TU_BAO_CO
			chungTuDAO.xoaChungTu(maCt, ChungTu.CHUNG_TU_BAO_CO);

			return "redirect:/danhsachbaoco";
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

	@RequestMapping("/xembaono/{id}")
	public String xemBaoNo(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_BAO_NO);
			if (chungTu == null) {
				return "redirect:/danhsachbaono";
			}
			model.addAttribute("chungTu", chungTu);

			model.addAttribute("tab", "tabCTBN");
			return "xemBaoNo";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/pdfbaono/{id}", method = RequestMethod.GET)
	public void pdfBaoNo(HttpServletRequest req, HttpServletResponse res, @PathVariable("id") int maCt, Model model) {
		try {
			JasperReport jasperReport = getCompiledFile("BaoNo", req);
			byte[] bytes = baoCaoDAO.taoBaoCaoChungTu(jasperReport, maCt);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/suabaono/{id}")
	public String suaBaoNo(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_BAO_NO);
			if (chungTu == null) {
				return "redirect:/danhsachbaono";
			}

			return chuanBiFormBaoNo(model, chungTu);
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

			ChungTu chungTu = new ChungTu();
			chungTu.setLoaiCt(ChungTu.CHUNG_TU_BAO_NO);

			// Lấy số phiếu thu của năm hiện tại
			int soBaoNo = chungTuDAO.demSoChungTuTheoLoaiCtVaNam(ChungTu.CHUNG_TU_BAO_NO, new Date());
			soBaoNo++;

			chungTu.setSoCt(soBaoNo);
			Date ngayLap = new Date();
			chungTu.setNgayLap(ngayLap);
			chungTu.setNgayHt(ngayLap);

			// Tài khoản khác, ghi nợ
			TaiKhoan taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			taiKhoan.setSoDu(LoaiTaiKhoan.NO);
			chungTu.themTaiKhoan(taiKhoan);

			// Tài khoản tiềm gửi ngân hàng, ghi có
			taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			taiKhoan.setSoDu(LoaiTaiKhoan.CO);
			chungTu.themTaiKhoan(taiKhoan);

			return chuanBiFormBaoNo(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/luutaomoibaono", method = RequestMethod.POST)
	public String luuTaoMoiBaoNo(@ModelAttribute("mainFinanceForm") @Validated ChungTu chungTu, BindingResult result,
			Model model) {
		try {
			if (result.hasErrors()) {
				return chuanBiFormBaoNo(model, chungTu);
			}

			logger.info("Lưu chứng từ: " + chungTu);

			chungTu.setLoaiCt(ChungTu.CHUNG_TU_BAO_NO);

			if (chungTu.getMaCt() > 0) {
				chungTuDAO.capNhatChungTu(chungTu);
				return "redirect:/xembaono/" + chungTu.getMaCt();
			} else {
				chungTuDAO.themChungTu(chungTu);
			}

			return "redirect:/danhsachbaono";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormBaoNo(Model model, ChungTu chungTu) {
		try {
			model.addAttribute("mainFinanceForm", chungTu);

			// Lấy danh sách các loại tiền
			List<LoaiTien> loaiTienDs = chungTuDAO.danhSachLoaiTien();
			model.addAttribute("loaiTienDs", loaiTienDs);

			// Lấy danh sách tài khoản tiền gửi ngân hàng, dùng cho bên có
			List<LoaiTaiKhoan> loaiTaiKhoanTgnhDs = taiKhoanDAO
					.danhSachTaiKhoanTheoCap1(LoaiTaiKhoan.TIEN_GUI_NGAN_HANG);
			model.addAttribute("loaiTaiKhoanTgnhDs", loaiTaiKhoanTgnhDs);

			// Lấy danh sách tài khoản, dùng cho bên nợ
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();
			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);

			model.addAttribute("tab", "tabCTBN");
			if (chungTu.getMaCt() > 0) {
				// Đây là trường hợp sửa CT
				return "suaBaoNo";
			} else {
				// Đây là trường hợp tạo mới CT
				return "taoMoiBaoNo";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/xoabaono/{id}")
	public String xoaBaoNo(@PathVariable("id") int maCt, Model model) {
		try {
			// Xóa phiếu thu có MA_CT là maCt, LOAI_CT là ChungTu.CHUNG_TU_BAO_NO
			chungTuDAO.xoaChungTu(maCt, ChungTu.CHUNG_TU_BAO_NO);

			return "redirect:/danhsachbaono";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/danhsachktth")
	public String danhSachKeToanTongHop(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			// Lấy danh sách phiếu kế toán tổng hợp
			List<ChungTu> keToanTongHopDs = chungTuDAO.danhSachChungTuTheoLoaiCt(ChungTu.CHUNG_TU_KT_TH);
			model.addAttribute("keToanTongHopDs", keToanTongHopDs);

			model.addAttribute("tab", "tabCTKTTH");
			return "danhSachKeToanTongHop";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/xemktth/{id}")
	public String xemKeToanTongHop(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_KT_TH);
			if (chungTu == null) {
				return "redirect:/danhsachktth";
			}
			model.addAttribute("chungTu", chungTu);

			model.addAttribute("tab", "tabCTKTTH");
			return "xemKeToanTongHop";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/pdfktth/{id}", method = RequestMethod.GET)
	public void pdfKeToanTongHop(HttpServletRequest req, HttpServletResponse res, @PathVariable("id") int maCt,
			Model model) {
		try {
			JasperReport jasperReport = getCompiledFile("KeToanTongHop", req);
			byte[] bytes = baoCaoDAO.taoBaoCaoChungTu(jasperReport, maCt);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/suaktth/{id}")
	public String suaKeToanTongHop(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_KT_TH);
			if (chungTu == null) {
				return "redirect:/danhsachktth";
			}

			return chuanBiFormKeToanTongHop(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/taomoiktth")
	public String taoMoiKeToanTongHop(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			ChungTu chungTu = new ChungTu();
			chungTu.setLoaiCt(ChungTu.CHUNG_TU_KT_TH);

			// Lấy số phiếu thu của năm hiện tại
			int soKeToanTongHop = chungTuDAO.demSoChungTuTheoLoaiCtVaNam(ChungTu.CHUNG_TU_KT_TH, new Date());
			soKeToanTongHop++;

			chungTu.setSoCt(soKeToanTongHop);
			Date ngayLap = new Date();
			chungTu.setNgayLap(ngayLap);
			chungTu.setNgayHt(ngayLap);

			// Tài khoản khác, ghi nợ
			TaiKhoan taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			taiKhoan.setSoDu(LoaiTaiKhoan.NO);
			chungTu.themTaiKhoan(taiKhoan);

			// Tài khoản tiềm gửi ngân hàng, ghi có
			taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			taiKhoan.setSoDu(LoaiTaiKhoan.CO);
			chungTu.themTaiKhoan(taiKhoan);

			return chuanBiFormKeToanTongHop(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/luutaomoiktth", method = RequestMethod.POST)
	public String luuTaoMoiKeToanTongHop(@ModelAttribute("mainFinanceForm") @Validated ChungTu chungTu,
			BindingResult result, Model model) {
		try {
			if (result.hasErrors()) {
				return chuanBiFormKeToanTongHop(model, chungTu);
			}

			logger.info("Lưu chứng từ: " + chungTu);

			chungTu.setLoaiCt(ChungTu.CHUNG_TU_KT_TH);

			if (chungTu.getMaCt() > 0) {
				chungTuDAO.capNhatChungTu(chungTu);
				return "redirect:/xemktth/" + chungTu.getMaCt();
			} else {
				chungTuDAO.themChungTu(chungTu);
			}

			return "redirect:/danhsachktth";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormKeToanTongHop(Model model, ChungTu chungTu) {
		try {
			model.addAttribute("mainFinanceForm", chungTu);

			// Lấy danh sách các loại tiền
			List<LoaiTien> loaiTienDs = chungTuDAO.danhSachLoaiTien();
			model.addAttribute("loaiTienDs", loaiTienDs);

			// Lấy danh sách tài khoản, dùng cho bên nợ & có
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();
			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);

			model.addAttribute("tab", "tabCTKTTH");
			if (chungTu.getMaCt() > 0) {
				// Đây là trường hợp sửa CT
				return "suaKeToanTongHop";
			} else {
				// Đây là trường hợp tạo mới CT
				return "taoMoiKeToanTongHop";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/xoaktth/{id}")
	public String xoaKeToanTongHop(@PathVariable("id") int maCt, Model model) {
		try {
			// Xóa phiếu thu có MA_CT là maCt, LOAI_CT là ChungTu.CHUNG_TU_BAO_NO
			chungTuDAO.xoaChungTu(maCt, ChungTu.CHUNG_TU_KT_TH);

			return "redirect:/danhsachktth";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/danhsachbtkc")
	public String danhSachButToanKetChuyen(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			// Lấy danh sách bút toán kết chuyển
			List<ChungTu> butToanKetChuyenDs = chungTuDAO.danhSachChungTuTheoLoaiCt(ChungTu.CHUNG_TU_BT_KC);
			model.addAttribute("butToanKetChuyenDs", butToanKetChuyenDs);

			model.addAttribute("tab", "tabBTKC");
			return "danhSachButToanKetChuyen";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/taomoibtkc")
	public String taoMoiButToanKetChuyen(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			ChungTu chungTu = new ChungTu();
			chungTu.setLoaiCt(ChungTu.CHUNG_TU_BT_KC);

			// Lấy số phiếu thu của năm hiện tại
			int soButToanKetChuyen = chungTuDAO.demSoChungTuTheoLoaiCtVaNam(ChungTu.CHUNG_TU_BT_KC, new Date());
			soButToanKetChuyen++;

			chungTu.setSoCt(soButToanKetChuyen);
			Date ngayLap = new Date();
			chungTu.setNgayLap(ngayLap);
			chungTu.setNgayHt(ngayLap);

			// Tài khoản khác, ghi nợ
			TaiKhoan taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			taiKhoan.setSoDu(LoaiTaiKhoan.NO);
			chungTu.themTaiKhoan(taiKhoan);

			// Tài khoản tiềm gửi ngân hàng, ghi có
			taiKhoan = new TaiKhoan();
			taiKhoan.setChungTu(chungTu);
			taiKhoan.setSoDu(LoaiTaiKhoan.CO);
			chungTu.themTaiKhoan(taiKhoan);

			DoiTuong doiTuong = new DoiTuong();
			doiTuong.setLoaiDt(DoiTuong.NHAN_VIEN);
			chungTu.setDoiTuong(doiTuong);

			return chuanBiFormButToanKetChuyen(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/luutaomoibtkc", method = RequestMethod.POST)
	public String luuTaoMoiButToanKetChuyen(@ModelAttribute("mainFinanceForm") @Validated ChungTu chungTu,
			BindingResult result, Model model) {
		try {
			if (result.hasErrors()) {
				return chuanBiFormButToanKetChuyen(model, chungTu);
			}

			logger.info("Lưu bút toán kết chuyển: " + chungTu);

			chungTu.setLoaiCt(ChungTu.CHUNG_TU_BT_KC);

			if (chungTu.getMaCt() > 0) {
				chungTuDAO.capNhatChungTu(chungTu);
				return "redirect:/xembtkc/" + chungTu.getMaCt();
			} else {
				chungTuDAO.themChungTu(chungTu);
			}

			return "redirect:/danhsachbtkc";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormButToanKetChuyen(Model model, ChungTu chungTu) {
		try {
			model.addAttribute("mainFinanceForm", chungTu);

			// Lấy danh sách các loại tiền
			List<LoaiTien> loaiTienDs = chungTuDAO.danhSachLoaiTien();
			model.addAttribute("loaiTienDs", loaiTienDs);

			// Lấy danh sách tài khoản, dùng cho bên nợ & có
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();
			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);

			model.addAttribute("tab", "tabBTKC");
			if (chungTu.getMaCt() > 0) {
				// Đây là trường hợp sửa bút toán
				return "suaButToanKetChuyen";
			} else {
				// Đây là trường hợp tạo mới bút toán
				return "taoMoiButToanKetChuyen";
			}
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

	private JasperReport getCompiledFile(String fileName, HttpServletRequest req) throws JRException {
		String jrxml = req.getSession().getServletContext().getRealPath("/baocao/chungtu/" + fileName + ".jrxml");
		String jasper = req.getSession().getServletContext().getRealPath("/baocao/chungtu/" + fileName + ".jasper");

		logger.info("Path " + jasper);
		File reportFile = new File(jasper);
		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(jrxml, jasper);
		}
		JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportFile.getPath());
		return jasperReport;
	}
}
