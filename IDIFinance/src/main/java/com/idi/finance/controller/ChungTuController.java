package com.idi.finance.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

import com.idi.finance.bean.DungChung;
import com.idi.finance.bean.LoaiTien;
import com.idi.finance.bean.NhanVien;
import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.KetChuyenButToan;
import com.idi.finance.bean.chungtu.TaiKhoan;
import com.idi.finance.bean.chungtu.Tien;
import com.idi.finance.bean.doitac.KhachHang;
import com.idi.finance.bean.doitac.NhaCungCap;
import com.idi.finance.bean.hanghoa.DonGia;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.hanghoa.KhoHang;
import com.idi.finance.bean.kyketoan.KyKeToan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.BaoCaoDAO;
import com.idi.finance.dao.ChungTuDAO;
import com.idi.finance.dao.HangHoaDAO;
import com.idi.finance.dao.KhachHangDAO;
import com.idi.finance.dao.KhoHangDAO;
import com.idi.finance.dao.KyKeToanDAO;
import com.idi.finance.dao.NhaCungCapDAO;
import com.idi.finance.dao.NhanVienDAO;
import com.idi.finance.dao.SoKeToanDAO;
import com.idi.finance.dao.TaiKhoanDAO;
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
	DungChung dungChung;

	@Autowired
	TaiKhoanDAO taiKhoanDAO;

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

	@RequestMapping("/chungtu/phieuthu/danhsach")
	public String danhSachPhieuThu(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_PHIEU_THU);

			// Lấy danh sách phiếu thu
			List<ChungTu> phieuThuDs = chungTuDAO.danhSachChungTu(loaiCts, kyKeToan.getBatDau(), kyKeToan.getKetThuc());

			model.addAttribute("phieuThuDs", phieuThuDs);
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTPT");
			return "danhSachPhieuThu";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
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

	@RequestMapping("/chungtu/phieuthu/sua/{id}")
	public String suaPhieuThu(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_THU);
			if (chungTu == null) {
				return "redirect:/chungtu/phieuthu/danhsach";
			}

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/phieuthu/xem/" + maCt;
			}

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

			// Lấy số phiếu thu của năm hiện tại
			int soPhieuThu = chungTuDAO.demSoChungTuTheoLoaiCtVaKy(ChungTu.CHUNG_TU_PHIEU_THU, kyKeToan.getBatDau(),
					kyKeToan.getKetThuc());
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

	@RequestMapping(value = "/chungtu/phieuthu/luu", method = RequestMethod.POST)
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
				return "redirect:/chungtu/phieuthu/xem/" + chungTu.getMaCt();
			} else {
				chungTuDAO.themChungTu(chungTu);
			}

			return "redirect:/chungtu/phieuthu/danhsach";
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

	@RequestMapping("/chungtu/phieuthu/xoa/{id}")
	public String xoaPhieuThu(@PathVariable("id") int maCt, Model model) {
		KyKeToan kyKeToan = dungChung.getKyKeToan();
		if (kyKeToan == null) {
			return "koKyKeToanMacDinh";
		}
		if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/chungtu/phieuthu/danhsach";
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

	@RequestMapping("/chungtu/phieuchi/danhsach")
	public String danhSachPhieuChi(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_PHIEU_CHI);

			// Lấy danh sách phiếu chi
			List<ChungTu> phieuChiDs = chungTuDAO.danhSachChungTu(loaiCts, kyKeToan.getBatDau(), kyKeToan.getKetThuc());

			model.addAttribute("phieuChiDs", phieuChiDs);
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTPC");
			return "danhSachPhieuChi";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
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

	@RequestMapping("/chungtu/phieuchi/sua/{id}")
	public String suaPhieuChi(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_PHIEU_CHI);
			if (chungTu == null) {
				return "redirect:/chungtu/phieuchi/danhsach";
			}

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/phieuchi/xem/" + maCt;
			}

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

			// Lấy số phiếu thu của năm hiện tại
			int soPhieuChi = chungTuDAO.demSoChungTuTheoLoaiCtVaKy(ChungTu.CHUNG_TU_PHIEU_CHI, kyKeToan.getBatDau(),
					kyKeToan.getKetThuc());
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

	@RequestMapping(value = "/chungtu/phieuchi/luu", method = RequestMethod.POST)
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
				return "redirect:/chungtu/phieuchi/xem/" + chungTu.getMaCt();
			} else {
				chungTuDAO.themChungTu(chungTu);
			}

			return "redirect:/chungtu/phieuchi/danhsach";
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

	@RequestMapping("/chungtu/phieuchi/xoa/{id}")
	public String xoaPhieuChi(@PathVariable("id") int maCt, Model model) {
		KyKeToan kyKeToan = dungChung.getKyKeToan();
		if (kyKeToan == null) {
			return "koKyKeToanMacDinh";
		}
		if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/chungtu/phieuchi/danhsach";
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

	@RequestMapping("/chungtu/baoco/danhsach")
	public String danhSachBaoCo(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_BAO_CO);

			// Lấy danh sách phiếu chi
			List<ChungTu> baoCoDs = chungTuDAO.danhSachChungTu(loaiCts, kyKeToan.getBatDau(), kyKeToan.getKetThuc());

			model.addAttribute("baoCoDs", baoCoDs);
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTBC");
			return "danhSachBaoCo";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
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

	@RequestMapping("/chungtu/baoco/sua/{id}")
	public String suaBaoCo(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_BAO_CO);
			if (chungTu == null) {
				return "redirect:/chungtu/baoco/danhsach";
			}

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/baoco/xem/" + maCt;
			}

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

			// Lấy số báo có của năm hiện tại
			int soBaoCo = chungTuDAO.demSoChungTuTheoLoaiCtVaKy(ChungTu.CHUNG_TU_BAO_CO, kyKeToan.getBatDau(),
					kyKeToan.getKetThuc());
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

	@RequestMapping(value = "/chungtu/baoco/luu", method = RequestMethod.POST)
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
				return "redirect:/chungtu/baoco/xem/" + chungTu.getMaCt();
			} else {
				chungTuDAO.themChungTu(chungTu);
			}

			return "redirect:/chungtu/baoco/danhsach";
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

	@RequestMapping("/chungtu/baoco/xoa/{id}")
	public String xoaBaoCo(@PathVariable("id") int maCt, Model model) {
		KyKeToan kyKeToan = dungChung.getKyKeToan();
		if (kyKeToan == null) {
			return "koKyKeToanMacDinh";
		}
		if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/chungtu/baoco/danhsach";
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

	@RequestMapping("/chungtu/baono/danhsach")
	public String danhSachBaoNo(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_BAO_NO);

			// Lấy danh sách phiếu chi
			List<ChungTu> baoNoDs = chungTuDAO.danhSachChungTu(loaiCts, kyKeToan.getBatDau(), kyKeToan.getKetThuc());

			model.addAttribute("baoNoDs", baoNoDs);
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTBN");
			return "danhSachBaoNo";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
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

	@RequestMapping("/chungtu/baono/sua/{id}")
	public String suaBaoNo(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_BAO_NO);
			if (chungTu == null) {
				return "redirect:/chungtu/baono/danhsach";
			}

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/baono/xem/" + maCt;
			}

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

			// Lấy số phiếu thu của năm hiện tại
			int soBaoNo = chungTuDAO.demSoChungTuTheoLoaiCtVaKy(ChungTu.CHUNG_TU_BAO_NO, kyKeToan.getBatDau(),
					kyKeToan.getKetThuc());
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

	@RequestMapping(value = "/chungtu/baono/luu", method = RequestMethod.POST)
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
				return "redirect:/chungtu/baono/xem/" + chungTu.getMaCt();
			} else {
				chungTuDAO.themChungTu(chungTu);
			}

			return "redirect:/chungtu/baono/danhsach";
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

	@RequestMapping("/chungtu/baono/xoa/{id}")
	public String xoaBaoNo(@PathVariable("id") int maCt, Model model) {
		KyKeToan kyKeToan = dungChung.getKyKeToan();
		if (kyKeToan == null) {
			return "koKyKeToanMacDinh";
		}
		if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/chungtu/baono/danhsach";
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

	@RequestMapping("/chungtu/ktth/danhsach")
	public String danhSachKeToanTongHop(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_KT_TH);

			// Lấy danh sách phiếu kế toán tổng hợp
			List<ChungTu> keToanTongHopDs = chungTuDAO.danhSachChungTu(loaiCts, kyKeToan.getBatDau(),
					kyKeToan.getKetThuc());

			model.addAttribute("keToanTongHopDs", keToanTongHopDs);
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTKTTH");
			return "danhSachKeToanTongHop";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/ktth/xem/{id}")
	public String xemKeToanTongHop(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_KT_TH);
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

	@RequestMapping("/chungtu/ktth/sua/{id}")
	public String suaKeToanTongHop(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layChungTu(maCt, ChungTu.CHUNG_TU_KT_TH);
			if (chungTu == null) {
				return "redirect:/chungtu/ktth/danhsach";
			}

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/ktth/xem/" + maCt;
			}

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

			// Lấy số phiếu thu của năm hiện tại
			int soKeToanTongHop = chungTuDAO.demSoChungTuTheoLoaiCtVaKy(ChungTu.CHUNG_TU_KT_TH, kyKeToan.getBatDau(),
					kyKeToan.getKetThuc());
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

	@RequestMapping(value = "/chungtu/ktth/luu", method = RequestMethod.POST)
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
				return "redirect:/chungtu/ktth/xem/" + chungTu.getMaCt();
			} else {
				chungTuDAO.themChungTu(chungTu);
			}

			return "redirect:/chungtu/ktth/danhsach";
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

	@RequestMapping("/chungtu/ktth/xoa/{id}")
	public String xoaKeToanTongHop(@PathVariable("id") int maCt, Model model) {
		KyKeToan kyKeToan = dungChung.getKyKeToan();
		if (kyKeToan == null) {
			return "koKyKeToanMacDinh";
		}
		if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/chungtu/ktth/danhsach";
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

	@RequestMapping("/chungtu/muahang/danhsach")
	public String danhSachMuaHang(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_MUA_HANG);

			// Lấy danh sách chứng từ mua hàng
			List<ChungTu> muaHangDs = chungTuDAO.danhSachChungTuKho(loaiCts, kyKeToan.getBatDau(),
					kyKeToan.getKetThuc());

			model.addAttribute("muaHangDs", muaHangDs);
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTMH");
			return "danhSachMuaHang";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
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
			JasperReport jasperReport = getCompiledFile("MuaHang", req);
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

	@RequestMapping("/chungtu/muahang/sua/{id}")
	public String suaMuaHang(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layChungTuKho(maCt, ChungTu.CHUNG_TU_MUA_HANG);
			if (chungTu == null) {
				return "redirect:/chungtu/muahang/danhsach";
			}

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/muahang/xem/" + maCt;
			}

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

			// Lấy số phiếu thu của năm hiện tại
			int soKeToanTongHop = chungTuDAO.demSoChungTuTheoLoaiCtVaKy(ChungTu.CHUNG_TU_MUA_HANG, kyKeToan.getBatDau(),
					kyKeToan.getKetThuc());
			soKeToanTongHop++;

			chungTu.setTinhChatCt(tinhChatCt);
			chungTu.setChieu(ChungTu.MUA);

			chungTu.setSoCt(soKeToanTongHop);
			Date ngayLap = new Date();
			chungTu.setNgayLap(ngayLap);
			chungTu.setNgayHt(ngayLap);

			HangHoa hangHoa = new HangHoa();
			chungTu.themHangHoa(hangHoa);

			return chuanBiFormMuaHang(model, chungTu);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/chungtu/muahang/nhapkho/{id}")
	public String nhapKhoMuaHang(@PathVariable("id") int maCt, Model model) {
		logger.info("Nhập kho các hàng hóa của chứng từ có maCt " + maCt);
		try {
			ChungTu chungTu = chungTuDAO.layChungTuKho(maCt, ChungTu.CHUNG_TU_MUA_HANG);
			if (chungTu != null) {
				if (chungTu.getHangHoaDs() != null) {
					Iterator<HangHoa> iter = chungTu.getHangHoaDs().iterator();
					while (iter.hasNext()) {
						HangHoa hangHoa = iter.next();

						double soLuong = khoHangDAO.laySoLuong(hangHoa);
						if (soLuong == -1) {
							khoHangDAO.themNhapKho(hangHoa);
						} else {
							hangHoa.setSoLuong(soLuong + hangHoa.getSoLuong());
							khoHangDAO.suaNhapKho(hangHoa);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		return "redirect:/chungtu/muahang/xem/" + maCt;
	}

	@RequestMapping(value = "/chungtu/muahang/luu", method = RequestMethod.POST)
	public String luuTaoMoiMuaHang(@ModelAttribute("mainFinanceForm") @Validated ChungTu chungTu, BindingResult result,
			Model model) {
		try {
			if (result.hasErrors()) {
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
			int soHangHoa = chungTu.getHangHoaDs().size();
			Iterator<HangHoa> hhIter = chungTu.getHangHoaDs().iterator();
			while (hhIter.hasNext()) {
				HangHoa hangHoa = hhIter.next();
				hangHoa.getGiaKho().setLoaiTien(chungTu.getLoaiTien());

				// Tính tổng tiền mua hàng đề ghi vào tài khoản công nợ
				double tongDonGia = hangHoa.getSoLuong() * hangHoa.getDonGia().getSoTien();

				// Tổng thuế nhập khẩu, tiêu thụ đặc biệt, thuế GTGT (phương pháp trực tiếp)
				double tongThue = 0;

				// Chuyển các loại thuế về loại tiền chính của chứng từ
				if (hangHoa.getTkThueNk() != null && hangHoa.getTkThueNk().getLoaiTaiKhoan().getMaTk() != null
						&& hangHoa.getTkThueNk().getSoTien() != null) {
					double thue = hangHoa.getTkThueNk().getSoTien().getSoTien() / chungTu.getLoaiTien().getBanRa();
					hangHoa.getTkThueNk().getSoTien().setSoTien(thue);
					hangHoa.getTkThueNk().getSoTien().setLoaiTien(chungTu.getLoaiTien());
					tongThue += thue;

					if (soHangHoa > 1) {
						hangHoa.getTkThueNk().setLyDo(chungTu.getLyDo() + ": " + hangHoa.getTenHh());
					} else {
						hangHoa.getTkThueNk().setLyDo(chungTu.getLyDo());
					}
				}

				if (hangHoa.getTkThueTtdb() != null && hangHoa.getTkThueTtdb().getLoaiTaiKhoan().getMaTk() != null
						&& hangHoa.getTkThueTtdb().getSoTien() != null) {
					double thue = hangHoa.getTkThueTtdb().getSoTien().getSoTien() / chungTu.getLoaiTien().getBanRa();
					hangHoa.getTkThueTtdb().getSoTien().setSoTien(thue);
					hangHoa.getTkThueTtdb().getSoTien().setLoaiTien(chungTu.getLoaiTien());
					tongThue += thue;

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
					double thue = hangHoa.getTkThueGtgt().getSoTien().getSoTien() / chungTu.getLoaiTien().getBanRa();
					hangHoa.getTkThueGtgt().getSoTien().setSoTien(thue);
					hangHoa.getTkThueGtgt().getSoTien().setLoaiTien(chungTu.getLoaiTien());

					if (soHangHoa > 1) {
						hangHoa.getTkThueGtgt().setLyDo(chungTu.getLyDo() + ": " + hangHoa.getTenHh());
					} else {
						hangHoa.getTkThueGtgt().setLyDo(chungTu.getLyDo());
					}

					if (hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
						// Đây là trường hợp thuế Gtgt tính theo phương pháp trực tiếp
						// Ghi thuế gtgt vào hàng hóa
						tongThue += thue;
					}
				}

				// Tính chi phí và phân bổ chi phí
				// Phần này hiện chưa có
				double tongChiPhi = 0;

				// Từ đó tính ra giá nhập kho
				// Đã tính ở phần client gằng js nên không cần tính lại

				Tien tongTien = new Tien();
				tongTien.setLoaiTien(chungTu.getLoaiTien());
				tongTien.setSoTien(tongDonGia + tongThue);

				// Tính giá trị tài khoản kho
				if (hangHoa.getTkKho() != null && hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk() != null) {
					hangHoa.getTkKho().setSoTien(tongTien);

					if (soHangHoa > 1) {
						hangHoa.getTkKho().setLyDo(chungTu.getLyDo() + ": " + hangHoa.getTenHh());
					} else {
						hangHoa.getTkKho().setLyDo(chungTu.getLyDo());
					}
				}

				// Tính giá trị tài khoản thanh toán (công nợ)
				Tien thanhToan = new Tien();
				thanhToan.setLoaiTien(chungTu.getLoaiTien());
				thanhToan.setSoTien(tongDonGia);
				if (chungTu.getTinhChatCt() == ChungTu.HANG_HOA_TRONG_NUOC
						|| chungTu.getTinhChatCt() == ChungTu.DICH_VU_TRONG_NUOC) {
					if (hangHoa.getTkThueGtgt() != null && hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk() != null
							&& !hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk().trim().equals("")
							&& hangHoa.getTkThueGtgt().getSoTien() != null) {
						// Trường hợp có thuế Gtgt
						// Phương pháp trực tiếp hay khấu trừ đều như nhau
						thanhToan.setSoTien(tongDonGia + hangHoa.getTkThueGtgt().getSoTien().getSoTien());
					}
				}

				if (hangHoa.getTkThanhtoan() != null && hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk() != null) {
					hangHoa.getTkThanhtoan().setSoTien(thanhToan);

					if (soHangHoa > 1) {
						hangHoa.getTkThanhtoan().setLyDo(chungTu.getLyDo() + ": " + hangHoa.getTenHh());
					} else {
						hangHoa.getTkThanhtoan().setLyDo(chungTu.getLyDo());
					}
				}
			}

			if (chungTu.getMaCt() > 0) {
				chungTuDAO.capNhatChungTuKho(chungTu);
				return "redirect:/chungtu/muahang/xem/" + chungTu.getMaCt();
			} else {
				chungTuDAO.themChungTuKho(chungTu);
			}

			return "redirect:/chungtu/muahang/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormMuaHang(Model model, ChungTu chungTu) {
		try {
			model.addAttribute("mainFinanceForm", chungTu);

			// Lấy danh sách các loại tiền
			List<LoaiTien> loaiTienDs = chungTuDAO.danhSachLoaiTien();
			model.addAttribute("loaiTienDs", loaiTienDs);

			// Lấy danh sách tài khoản, dùng cho bên nợ & có
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();
			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);

			// Lấy danh sách kho
			List<KhoHang> khoBaiDs = hangHoaDAO.danhSachKhoBai();
			model.addAttribute("khoBaiDs", khoBaiDs);

			// Lấy danh sách kỹ hiệu hàng hóa
			List<HangHoa> khHangHoaDs = hangHoaDAO.danhSachKhHangHoa();
			model.addAttribute("khHangHoaDs", khHangHoaDs);

			model.addAttribute("tab", "tabCTMH");
			if (chungTu.getMaCt() > 0) {
				// Đây là trường hợp sửa CT
				return "suaMuaHang";
			} else {
				// Đây là trường hợp tạo mới CT
				return "taoMoiMuaHang";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/muahang/xoa/{id}")
	public String xoaMuaHang(@PathVariable("id") int maCt, Model model) {
		KyKeToan kyKeToan = dungChung.getKyKeToan();
		if (kyKeToan == null) {
			return "koKyKeToanMacDinh";
		}
		if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/chungtu/muahang/danhsach";
		}

		try {
			// Lấy chứng từ
			ChungTu chungTu = chungTuDAO.layChungTuKho(maCt, ChungTu.CHUNG_TU_MUA_HANG);

			if (chungTu != null) {
				// Xóa phiếu thu có MA_CT là maCt, LOAI_CT là ChungTu.CHUNG_TU_MUA_HANG
				chungTuDAO.xoaChungTuKho(chungTu);
			}

			return "redirect:/chungtu/muahang/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/banhang/danhsach")
	public String danhSachBanHang(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			List<String> loaiCts = new ArrayList<>();
			loaiCts.add(ChungTu.CHUNG_TU_BAN_HANG);

			// Lấy danh sách chứng từ mua hàng
			List<ChungTu> banHangDs = chungTuDAO.danhSachChungTuKho(loaiCts, kyKeToan.getBatDau(),
					kyKeToan.getKetThuc());

			model.addAttribute("banHangDs", banHangDs);
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabCTBH");
			return "danhSachBanHang";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
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
			JasperReport jasperReport = getCompiledFile("BanHang", req);
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

	@RequestMapping("/chungtu/banhang/sua/{id}")
	public String suaBanHang(@PathVariable("id") int maCt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			ChungTu chungTu = chungTuDAO.layChungTuKho(maCt, ChungTu.CHUNG_TU_BAN_HANG);
			if (chungTu == null) {
				return "redirect:/chungtu/banhang/danhsach";
			}

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(chungTu);
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/banhang/xem/" + maCt;
			}

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

			// Lấy số phiếu thu của năm hiện tại
			int soKeToanTongHop = chungTuDAO.demSoChungTuTheoLoaiCtVaKy(ChungTu.CHUNG_TU_BAN_HANG, kyKeToan.getBatDau(),
					kyKeToan.getKetThuc());
			soKeToanTongHop++;

			chungTu.setTinhChatCt(tinhChatCt);
			chungTu.setChieu(ChungTu.BAN);

			chungTu.setSoCt(soKeToanTongHop);
			Date ngayLap = new Date();
			chungTu.setNgayLap(ngayLap);
			chungTu.setNgayHt(ngayLap);

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
			Model model) {
		try {
			if (result.hasErrors()) {
				logger.info(result);
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
				int soHangHoa = chungTu.getHangHoaDs().size();
				Iterator<HangHoa> hhIter = chungTu.getHangHoaDs().iterator();
				while (hhIter.hasNext()) {
					HangHoa hangHoa = hhIter.next();

					// Tổng thuế: thực ra chỉ một trong 2: thuế XNK hoặc thuế GTGT
					double tongThue = 0;
					if (hangHoa.getTkThueGtgt() != null && hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk() != null
							&& !hangHoa.getTkThueGtgt().getLoaiTaiKhoan().getMaTk().trim().equals("")
							&& hangHoa.getTkThueGtgt().getSoTien() != null) {
						// Có thuế Gtgt, tính theo phương pháp khấu trừ
						double thue = hangHoa.getTkThueGtgt().getSoTien().getSoTien()
								/ chungTu.getLoaiTien().getBanRa();
						hangHoa.getTkThueGtgt().getSoTien().setSoTien(thue);
						hangHoa.getTkThueGtgt().getSoTien().setLoaiTien(chungTu.getLoaiTien());
						tongThue += thue;

						if (soHangHoa > 1) {
							hangHoa.getTkThueGtgt().setLyDo(chungTu.getLyDo() + ": " + hangHoa.getTenHh());
						} else {
							hangHoa.getTkThueGtgt().setLyDo(chungTu.getLyDo());
						}
					}

					if (hangHoa.getTkThueXk() != null && hangHoa.getTkThueXk().getLoaiTaiKhoan().getMaTk() != null
							&& !hangHoa.getTkThueXk().getLoaiTaiKhoan().getMaTk().trim().equals("")
							&& hangHoa.getTkThueXk().getSoTien() != null) {
						double thue = hangHoa.getTkThueXk().getSoTien().getSoTien() / chungTu.getLoaiTien().getBanRa();
						hangHoa.getTkThueXk().getSoTien().setSoTien(thue);
						hangHoa.getTkThueXk().getSoTien().setLoaiTien(chungTu.getLoaiTien());
						tongThue += thue;

						if (soHangHoa > 1) {
							hangHoa.getTkThueXk().setLyDo(chungTu.getLyDo() + ": " + hangHoa.getTenHh());
						} else {
							hangHoa.getTkThueXk().setLyDo(chungTu.getLyDo());
						}
					}

					// Tổng doanh thu
					double tongDoanhThu = hangHoa.getSoLuong() * hangHoa.getDonGia().getSoTien()
							* chungTu.getLoaiTien().getBanRa();
					Tien doanhThu = new Tien();
					doanhThu.setLoaiTien(chungTu.getLoaiTien());
					doanhThu.setSoTien(tongDoanhThu);

					if (hangHoa.getTkDoanhThu() != null && hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk() != null
							&& !hangHoa.getTkDoanhThu().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
						hangHoa.getTkDoanhThu().setSoTien(doanhThu);

						if (soHangHoa > 1) {
							hangHoa.getTkDoanhThu().setLyDo(chungTu.getLyDo() + ": " + hangHoa.getTenHh());
						} else {
							hangHoa.getTkDoanhThu().setLyDo(chungTu.getLyDo());
						}
					}

					// Tổng thanh toán
					double tongThanhToan = tongDoanhThu + tongThue;
					Tien thanhToan = new Tien();
					thanhToan.setLoaiTien(chungTu.getLoaiTien());
					thanhToan.setSoTien(tongThanhToan);

					if (hangHoa.getTkThanhtoan() != null && hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk() != null
							&& !hangHoa.getTkThanhtoan().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
						hangHoa.getTkThanhtoan().setSoTien(thanhToan);

						if (soHangHoa > 1) {
							hangHoa.getTkThanhtoan().setLyDo(chungTu.getLyDo() + ": " + hangHoa.getTenHh());
						} else {
							hangHoa.getTkThanhtoan().setLyDo(chungTu.getLyDo());
						}
					}

					// Tổng giá vốn
					double tongGiaVon = hangHoa.getSoLuong() * hangHoa.getGiaKho().getSoTien();
					Tien giaVon = new Tien();
					giaVon.setLoaiTien(chungTu.getLoaiTien());
					giaVon.setSoTien(tongGiaVon);

					if (hangHoa.getTkGiaVon() != null && hangHoa.getTkGiaVon().getLoaiTaiKhoan().getMaTk() != null
							&& !hangHoa.getTkGiaVon().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
						hangHoa.getTkGiaVon().setSoTien(giaVon);
						hangHoa.getTkGiaVon().setNhomDk(1);

						if (soHangHoa > 1) {
							hangHoa.getTkGiaVon().setLyDo(chungTu.getLyDo() + ": " + hangHoa.getTenHh());
						} else {
							hangHoa.getTkGiaVon().setLyDo(chungTu.getLyDo());
						}
					}

					if (hangHoa.getTkKho() != null && hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk() != null
							&& !hangHoa.getTkKho().getLoaiTaiKhoan().getMaTk().trim().equals("")) {
						hangHoa.getTkKho().setSoTien(giaVon);
						hangHoa.getTkKho().setNhomDk(1);

						if (soHangHoa > 1) {
							hangHoa.getTkKho().setLyDo(chungTu.getLyDo() + ": " + hangHoa.getTenHh());
						} else {
							hangHoa.getTkKho().setLyDo(chungTu.getLyDo());
						}
					}
				}

				if (chungTu.getMaCt() > 0) {
					chungTuDAO.capNhatChungTuKho(chungTu);
					return "redirect:/chungtu/banhang/xem/" + chungTu.getMaCt();
				} else {
					chungTuDAO.themChungTuKho(chungTu);
				}

				// Xuất kho: cần thực hiện việc xuất kho
				// Coi như dữ liệu đã được kiểm tra từ trước
				// Cần phải làm chức năng kiểm tra đó sau
				// Ta cũng có thể tách phần này thành một nút riêng
				// giống phần mua hàng có nút nhập kho riêng
				Iterator<HangHoa> iter = chungTu.getHangHoaDs().iterator();
				while (iter.hasNext()) {
					HangHoa hangHoa = iter.next();

					// Kiểm tra số lượng tổn kho
					double soLuong = khoHangDAO.laySoLuong(hangHoa);
					if (hangHoa.getSoLuong() < soLuong) {
						hangHoa.setSoLuong(soLuong - hangHoa.getSoLuong());
						khoHangDAO.suaNhapKho(hangHoa);
					} else if (hangHoa.getSoLuong() < soLuong) {
						// Vì đã xuất hết hàng, có thể xóa kho, xóa giá
						khoHangDAO.xoaKho(hangHoa);
					} else {
						// Không đủ hàng hóa để bán
						// Cần bắn ra lỗi và thông báo
					}
				}
			}

			return "redirect:/chungtu/banhang/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormBanHang(Model model, ChungTu chungTu) {
		try {
			model.addAttribute("mainFinanceForm", chungTu);

			// Lấy danh sách các loại tiền
			List<LoaiTien> loaiTienDs = chungTuDAO.danhSachLoaiTien();
			model.addAttribute("loaiTienDs", loaiTienDs);

			// Lấy danh sách tài khoản, dùng cho bên nợ & có
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();
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
			if (chungTu.getMaCt() > 0) {
				// Đây là trường hợp sửa CT
				return "suaBanHang";
			} else {
				// Đây là trường hợp tạo mới CT
				return "taoMoiBanHang";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/banhang/xoa/{id}")
	public String xoaBanHang(@PathVariable("id") int maCt, Model model) {
		KyKeToan kyKeToan = dungChung.getKyKeToan();
		if (kyKeToan == null) {
			return "koKyKeToanMacDinh";
		}
		if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/chungtu/banhang/danhsach";
		}

		try {
			// Lấy chứng từ
			ChungTu chungTu = chungTuDAO.layChungTuKho(maCt, ChungTu.CHUNG_TU_BAN_HANG);

			if (chungTu != null) {
				// Xóa phiếu thu có MA_CT là maCt, LOAI_CT là ChungTu.CHUNG_TU_BAN_HANG
				chungTuDAO.xoaChungTuKho(chungTu);
			}

			return "redirect:/chungtu/banhang/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/chungtu/ketchuyen/danhsach")
	public String danhSachKetChuyen(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			// Lấy danh sách kết chuyển
			List<ChungTu> ketChuyenDs = chungTuDAO.danhSachKetChuyen(kyKeToan.getBatDau(), kyKeToan.getKetThuc());

			model.addAttribute("ketChuyenDs", ketChuyenDs);
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

			// Lấy số phiếu thu của năm hiện tại
			int soKetChuyen = chungTuDAO.demSoChungTuTheoLoaiCtVaKy(ChungTu.CHUNG_TU_KET_CHUYEN, kyKeToan.getBatDau(),
					kyKeToan.getKetThuc());
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
		KyKeToan kyKeToan = dungChung.getKyKeToan();
		if (kyKeToan == null) {
			return "koKyKeToanMacDinh";
		}
		if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/chungtu/ketchuyen/danhsach";
		}

		try {
			ChungTu chungTu = chungTuDAO.layKetChuyen(maCt);

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
			JasperReport jasperReport = getCompiledFile("KetChuyenButToan", req);
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

	@RequestMapping("/chungtu/ketchuyen/buttoan/sua/{id}")
	public String suaKetChuyenButToan(@PathVariable("id") int maKc, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/chungtu/ketchuyen/buttoan/xem/" + maKc;
			}

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
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();
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
		KyKeToan kyKeToan = dungChung.getKyKeToan();
		if (kyKeToan == null) {
			return "koKyKeToanMacDinh";
		}
		if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/chungtu/ketchuyen/buttoan/xem/" + maKc;
		}

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
