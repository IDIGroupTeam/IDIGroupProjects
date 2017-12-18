package com.idi.finance.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idi.finance.bean.KyKeToan;
import com.idi.finance.bean.bieudo.KpiGroup;
import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.soketoan.NghiepVuKeToan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.ChungTuDAO;
import com.idi.finance.dao.KhachHangDAO;
import com.idi.finance.dao.KpiChartDAO;
import com.idi.finance.dao.NhaCungCapDAO;
import com.idi.finance.dao.NhanVienDAO;
import com.idi.finance.dao.SoKeToanDAO;
import com.idi.finance.dao.TaiKhoanDAO;
import com.idi.finance.form.TkSoKeToanForm;
import com.idi.finance.utils.Utils;

@Controller
public class SoKeToanController {
	private static final Logger logger = Logger.getLogger(SoKeToanController.class);

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
	SoKeToanDAO soKeToanDAO;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("/soketoan/nhatkychung")
	public String sktNhatKyChung(@ModelAttribute("mainFinanceForm") TkSoKeToanForm form, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			Date homNay = new Date();
			// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của
			// tháng hiện tại
			if (form.getDau() == null) {
				form.setDau(Utils.getStartDateOfMonth(homNay));
			}

			if (form.getCuoi() == null) {
				form.setCuoi(Utils.getEndDateOfMonth(homNay));
			}

			// Nếu không có đầu vào loại chứng từ thì lấy tất cả các chứng từ
			if (form.getLoaiCts() == null || form.getLoaiCts().size() == 0) {
				form.themLoaiCt(ChungTu.TAT_CA);
			}

			// Lấy danh sách tất cả chứng từ
			List<ChungTu> chungTuDs = soKeToanDAO.danhSachChungTu(form.getDau(), form.getCuoi(), form.getLoaiCts());
			model.addAttribute("chungTuDs", chungTuDs);
			model.addAttribute("mainFinanceForm", form);

			model.addAttribute("tab", "tabSKTNKC");
			return "sktNhatKyChung";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/soketoan/socai")
	public String sktSoCai(@ModelAttribute("mainFinanceForm") TkSoKeToanForm form, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			Date homNay = new Date();
			// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của
			// tháng hiện tại
			if (form.getDau() == null) {
				form.setDau(Utils.getStartPeriod(homNay, form.getLoaiKy()));
			} else {
				form.setDau(Utils.getStartPeriod(form.getDau(), form.getLoaiKy()));
			}

			if (form.getCuoi() == null) {
				form.setCuoi(Utils.getEndPeriod(homNay, form.getLoaiKy()));
			} else {
				form.setCuoi(Utils.getEndPeriod(form.getCuoi(), form.getLoaiKy()));
			}

			// Nếu không có đầu vào loại chứng từ thì lấy tất cả các chứng từ
			if (form.getLoaiCts() == null || form.getLoaiCts().size() == 0) {
				form.themLoaiCt(ChungTu.TAT_CA);
			}

			// Nếu không có đầu vào tài khoản thì đặt giá trị mặc định là 111
			if (form.getTaiKhoan() == null) {
				form.setTaiKhoan(LoaiTaiKhoan.TIEN_MAT);
			}

			// Lấy danh sách tài khoản
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();
			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);

			logger.info("Tính sổ cái tài khoản: " + form.getTaiKhoan());
			// Lấy danh sách các nghiệp vụ kế toán theo tài khoản, loại chứng từ, và từng kỳ
			List<KyKeToan> kyKeToanDs = new ArrayList<>();
			logger.info("Loại kỳ: " + form.getLoaiKy());
			KyKeToan kyKt = new KyKeToan(form.getDau(), form.getLoaiKy());
			
			double soDuDau = 0;
			double soDuCuoi = 0;
			double noPhatSinh = 0;
			double coPhatSinh = 0;
			
			Date cuoiKyTruoc = Utils.prevPeriod(kyKt).getCuoi();
			
			// Lấy tổng nợ đầu kỳ
			double noDauKy = soKeToanDAO.tongPhatSinh(form.getTaiKhoan(), LoaiTaiKhoan.NO, null, cuoiKyTruoc);

			// Lấy tổng có đầu kỳ
			double coDauKy = soKeToanDAO.tongPhatSinh(form.getTaiKhoan(), LoaiTaiKhoan.CO, null, cuoiKyTruoc);

			// Tính ra số dư đầu kỳ
			double soDuDauKy = noDauKy - coDauKy;
			soDuDau = soDuDauKy;

			while (!kyKt.getCuoi().after(form.getCuoi())) {
				logger.info("Kỳ: " + kyKt);

				// Lấy nghiệp vụ kế toán được ghi từ phiếu thu, phiếu chi, báo nợ, báo có
				kyKt.setNghiepVuKeToanDs(soKeToanDAO.danhSachNghiepVuKeToanTheoLoaiTaiKhoan(form.getTaiKhoan(),
						kyKt.getDau(), kyKt.getCuoi(), form.getLoaiCts()));

				// Lấy nghiệp vụ kế toán được ghi từ phiếu kế toán tổng hợp
				kyKt.themNghiepVuKeToan(soKeToanDAO.danhSachNghiepVuKeToanTheoLoaiTaiKhoan(form.getTaiKhoan(),
						kyKt.getDau(), kyKt.getCuoi()));

				// Tính phát sinh nợ trong kỳ
				double noPhatSinhKy = soKeToanDAO.tongPhatSinh(form.getTaiKhoan(), LoaiTaiKhoan.NO, kyKt.getDau(),
						kyKt.getCuoi());
				noPhatSinh += noPhatSinhKy;

				// Tính phát sinh có trong kỳ
				double coPhatSinhKy = soKeToanDAO.tongPhatSinh(form.getTaiKhoan(), LoaiTaiKhoan.CO, kyKt.getDau(),
						kyKt.getCuoi());
				coPhatSinh += coPhatSinhKy;

				// Tính ra số dư cuối kỳ
				double soDuCuoiKy = noPhatSinhKy - coPhatSinhKy + soDuDauKy;

				kyKt.setSoDuDauKy(soDuDauKy);
				kyKt.setTongNoPhatSinh(noPhatSinhKy);
				kyKt.setTongCoPhatSinh(coPhatSinhKy);
				kyKt.setSoDuCuoiKy(soDuCuoiKy);
				logger.info("Số dư đầu kỳ: " + soDuDauKy + ". Nợ phát sinh kỳ: " + noPhatSinhKy + ". Có phát sinh kỳ: "
						+ coPhatSinhKy + ". Số dư cuối kỳ: " + soDuCuoiKy);

				kyKeToanDs.add(kyKt);
				kyKt = Utils.nextPeriod(kyKt);
				soDuDauKy = soDuCuoiKy;
			}

			soDuCuoi = soDuDau + noPhatSinh - coPhatSinh;

			model.addAttribute("soDuDau", soDuDau);
			model.addAttribute("noPhatSinh", noPhatSinh);
			model.addAttribute("coPhatSinh", coPhatSinh);
			model.addAttribute("soDuCuoi", soDuCuoi);

			model.addAttribute("kyKeToanDs", kyKeToanDs);
			model.addAttribute("mainFinanceForm", form);

			model.addAttribute("tab", "tabSKTSC");
			return "sktSoCai";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/soketoan/sotienmat")
	public String sktSoTienMat(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			String taiKhoan = LoaiTaiKhoan.TIEN_MAT;
			List<NghiepVuKeToan> nghiepVuKeToanDs = soKeToanDAO.danhSachNghiepVuKeToanTheoLoaiTaiKhoan(taiKhoan);
			model.addAttribute("taiKhoan", taiKhoan);
			model.addAttribute("nghiepVuKeToanDs", nghiepVuKeToanDs);

			model.addAttribute("tab", "tabSKTSTM");
			return "sktSoTienMat";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/soketoan/socongno")
	public String sktSoCongNo(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			// Lấy danh sách phiếu thu

			model.addAttribute("tab", "tabSKTSCN");
			return "sktSoCongNo";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/soketoan/sotienguinganhang")
	public String sktSoTienGuiNganHang(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			String taiKhoan = LoaiTaiKhoan.TIEN_GUI_NGAN_HANG;
			List<NghiepVuKeToan> nghiepVuKeToanDs = soKeToanDAO.danhSachNghiepVuKeToanTheoLoaiTaiKhoan(taiKhoan);
			model.addAttribute("taiKhoan", taiKhoan);
			model.addAttribute("nghiepVuKeToanDs", nghiepVuKeToanDs);

			model.addAttribute("tab", "tabSKTSTGNH");
			return "sktSoTienGuiNganHang";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
}
