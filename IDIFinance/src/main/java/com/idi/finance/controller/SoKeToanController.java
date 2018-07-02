package com.idi.finance.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.idi.finance.bean.DungChung;
import com.idi.finance.bean.cdkt.DuLieuKeToan;
import com.idi.finance.bean.cdkt.KyKeToanCon;
import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.doitac.KhachHang;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.kyketoan.SoDuKy;
import com.idi.finance.bean.soketoan.NghiepVuKeToan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.ChungTuDAO;
import com.idi.finance.dao.KhachHangDAO;
import com.idi.finance.dao.KyKeToanDAO;
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
	DungChung dungChung;

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

	@Autowired
	KyKeToanDAO kyKeToanDAO;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/soketoan/nhatkychung", method = { RequestMethod.GET, RequestMethod.POST })
	public String sktNhatKyChung(@ModelAttribute("mainFinanceForm") TkSoKeToanForm form, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			// Lấy kỳ kế toán mặc định
			if (form.getKyKeToan() == null) {
				form.setKyKeToan(dungChung.getKyKeToan());
			} else {
				form.setKyKeToan(kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt()));
			}

			Date homNay = new Date();
			if (!form.getKyKeToan().getBatDau().after(homNay) && !form.getKyKeToan().getKetThuc().before(homNay)) {
				// Nếu không có đầu vào ngày tháng, lấy tháng hiện tại
				if (form.getDau() == null) {
					form.setDau(Utils.getStartDateOfMonth(homNay));
				}

				if (form.getCuoi() == null) {
					form.setCuoi(Utils.getEndDateOfMonth(homNay));
				}
			} else {
				// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng kỳ
				if (form.getDau() == null) {
					form.setDau(form.getKyKeToan().getBatDau());
				}

				if (form.getCuoi() == null) {
					form.setCuoi(form.getKyKeToan().getKetThuc());
				}
			}

			// Nếu không có đầu vào loại chứng từ thì lấy tất cả các chứng từ
			if (form.getLoaiCts() == null || form.getLoaiCts().size() == 0) {
				form.themLoaiCt(ChungTu.TAT_CA);
			}

			// Lấy danh sách tất cả chứng từ
			List<ChungTu> chungTuDs = new ArrayList<>();
			List<String> loaiCts = new ArrayList<>();
			List<String> loaiCtKhos = new ArrayList<>();

			if (form.getLoaiCts().contains(ChungTu.TAT_CA)) {
				loaiCts.add(ChungTu.CHUNG_TU_PHIEU_THU);
				loaiCts.add(ChungTu.CHUNG_TU_PHIEU_CHI);
				loaiCts.add(ChungTu.CHUNG_TU_BAO_NO);
				loaiCts.add(ChungTu.CHUNG_TU_BAO_CO);
				loaiCts.add(ChungTu.CHUNG_TU_KT_TH);

				loaiCtKhos.add(ChungTu.CHUNG_TU_MUA_HANG);
				loaiCtKhos.add(ChungTu.CHUNG_TU_BAN_HANG);
			} else {
				Iterator<String> iter = form.getLoaiCts().iterator();
				while (iter.hasNext()) {
					String loaiCt = iter.next();

					if (loaiCt.equals(ChungTu.CHUNG_TU_BAN_HANG) || loaiCt.equals(ChungTu.CHUNG_TU_MUA_HANG)) {
						loaiCtKhos.add(loaiCt);
					} else {
						loaiCts.add(loaiCt);
					}
				}
			}

			chungTuDs = soKeToanDAO.danhSachChungTu(form.getDau(), form.getCuoi(), loaiCts);

			List<ChungTu> chungTuKhoDs = soKeToanDAO.danhSachChungTuKho(form.getDau(), form.getCuoi(), loaiCtKhos);

			if (chungTuDs == null)
				chungTuDs = new ArrayList<>();

			if (chungTuKhoDs == null)
				chungTuKhoDs = new ArrayList<>();

			chungTuDs.addAll(chungTuKhoDs);

			Collections.sort(chungTuDs, new Comparator<ChungTu>() {
				@Override
				public int compare(ChungTu ct1, ChungTu ct2) {
					try {
						if (ct1.getNgayHt().after(ct2.getNgayHt())) {
							return 1;
						} else if (ct1.getNgayHt().before(ct2.getNgayHt())) {
							return -1;
						} else {
							if (ct1.getMaCt() > ct2.getMaCt()) {
								return 1;
							} else if (ct1.getMaCt() < ct2.getMaCt()) {
								return -1;
							} else {
								return 0;
							}
						}
					} catch (Exception e) {

					}

					return 0;
				}
			});

			model.addAttribute("kyKeToanDs", kyKeToanDAO.danhSachKyKeToan());
			model.addAttribute("chungTuDs", chungTuDs);
			model.addAttribute("mainFinanceForm", form);

			model.addAttribute("tab", "tabSKTNKC");
			return "sktNhatKyChung";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/soketoan/socai", method = { RequestMethod.GET, RequestMethod.POST })
	public String sktSoCai(@ModelAttribute("mainFinanceForm") TkSoKeToanForm form, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			// Lấy kỳ kế toán mặc định
			if (form.getKyKeToan() == null) {
				form.setKyKeToan(dungChung.getKyKeToan());
			} else {
				form.setKyKeToan(kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt()));
			}

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
				// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của
				// tháng hiện tại
				if (form.getDau() == null) {
					form.setDau(form.getKyKeToan().getBatDau());
				}

				if (form.getCuoi() == null) {
					form.setCuoi(form.getKyKeToan().getKetThuc());
				}
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
			List<DuLieuKeToan> duLieuKeToanDs = new ArrayList<>();
			KyKeToanCon kyKt = new KyKeToanCon(form.getDau(), form.getLoaiKy());

			LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan(form.getTaiKhoan());

			double soDuDau = 0;
			double soDuCuoi = 0;
			double noPhatSinh = 0;
			double coPhatSinh = 0;

			if (form.getLoaiKy() == KyKeToanCon.NAN) {
				kyKt = new KyKeToanCon(form.getDau(), form.getCuoi());
				Date cuoiKyTruoc = Utils.prevPeriod(kyKt).getCuoi();

				// Lấy tổng nợ đầu kỳ
				double noDauKy = soKeToanDAO.tongPhatSinh(form.getTaiKhoan(), LoaiTaiKhoan.NO,
						form.getKyKeToan().getBatDau(), cuoiKyTruoc);

				// Lấy tổng có đầu kỳ
				double coDauKy = soKeToanDAO.tongPhatSinh(form.getTaiKhoan(), LoaiTaiKhoan.CO,
						form.getKyKeToan().getBatDau(), cuoiKyTruoc);

				// Tính ra số dư đầu kỳ
				double soDuDauKy = noDauKy - coDauKy;
				try {
					SoDuKy soDuKy = kyKeToanDAO.laySoDuKy(form.getTaiKhoan(), form.getKyKeToan().getMaKyKt());
					soDuDauKy = soDuDauKy + soDuKy.getNoDauKy() - soDuKy.getCoDauKy();
				} catch (Exception e) {

				}
				soDuDau = soDuDauKy;

				DuLieuKeToan duLieuKeToan = new DuLieuKeToan(kyKt, loaiTaiKhoan);

				// Lấy nghiệp vụ kế toán được ghi từ phiếu thu, phiếu chi, báo nợ, báo có, phiếu kế toán tổng hợp
				duLieuKeToan.themNghiepVuKeToan(soKeToanDAO.danhSachNghiepVuKeToanTheoLoaiTaiKhoan(form.getTaiKhoan(),
						kyKt.getDau(), kyKt.getCuoi(), form.getLoaiCts()));

				// Lấy nghiệp vụ kế toán được ghi từ phiếu kế toán tổng hợp
				// duLieuKeToan.themNghiepVuKeToan(soKeToanDAO.danhSachNghiepVuKeToanTheoLoaiTaiKhoan(form.getTaiKhoan(),
				// kyKt.getDau(), kyKt.getCuoi()));

				// Lấy nghiệp vụ kế toán được ghi từ chứng từ mua hàng, bán hàng

				// Lấy nghiệp vụ kế toán được ghi từ các kết chuyển

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

				duLieuKeToan.setSoDuDauKy(soDuDauKy);
				duLieuKeToan.setTongNoPhatSinh(noPhatSinhKy);
				duLieuKeToan.setTongCoPhatSinh(coPhatSinhKy);
				duLieuKeToan.setSoDuCuoiKy(soDuCuoiKy);
				logger.info("Số dư đầu kỳ: " + soDuDauKy + ". Nợ phát sinh kỳ: " + noPhatSinhKy + ". Có phát sinh kỳ: "
						+ coPhatSinhKy + ". Số dư cuối kỳ: " + soDuCuoiKy);

				duLieuKeToanDs.add(duLieuKeToan);
			} else {
				Date cuoiKyTruoc = Utils.prevPeriod(kyKt).getCuoi();

				// Lấy tổng nợ đầu kỳ
				double noDauKy = soKeToanDAO.tongPhatSinh(form.getTaiKhoan(), LoaiTaiKhoan.NO,
						form.getKyKeToan().getBatDau(), cuoiKyTruoc);

				// Lấy tổng có đầu kỳ
				double coDauKy = soKeToanDAO.tongPhatSinh(form.getTaiKhoan(), LoaiTaiKhoan.CO,
						form.getKyKeToan().getBatDau(), cuoiKyTruoc);

				// Tính ra số dư đầu kỳ
				double soDuDauKy = noDauKy - coDauKy;
				try {
					SoDuKy soDuKy = kyKeToanDAO.laySoDuKy(form.getTaiKhoan(), form.getKyKeToan().getMaKyKt());
					soDuDauKy = soDuDauKy + soDuKy.getNoDauKy() - soDuKy.getCoDauKy();
				} catch (Exception e) {

				}
				soDuDau = soDuDauKy;

				while (!kyKt.getCuoi().after(form.getCuoi())) {
					logger.info("Kỳ: " + kyKt);

					DuLieuKeToan duLieuKeToan = new DuLieuKeToan(kyKt, loaiTaiKhoan);

					// Lấy nghiệp vụ kế toán được ghi từ phiếu thu, phiếu chi, báo nợ, báo có
					duLieuKeToan.themNghiepVuKeToan(soKeToanDAO.danhSachNghiepVuKeToanTheoLoaiTaiKhoan(
							form.getTaiKhoan(), kyKt.getDau(), kyKt.getCuoi(), form.getLoaiCts()));

					// Lấy nghiệp vụ kế toán được ghi từ phiếu kế toán tổng hợp
					duLieuKeToan.themNghiepVuKeToan(soKeToanDAO
							.danhSachNghiepVuKeToanTheoLoaiTaiKhoan(form.getTaiKhoan(), kyKt.getDau(), kyKt.getCuoi()));

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

					duLieuKeToan.setSoDuDauKy(soDuDauKy);
					duLieuKeToan.setTongNoPhatSinh(noPhatSinhKy);
					duLieuKeToan.setTongCoPhatSinh(coPhatSinhKy);
					duLieuKeToan.setSoDuCuoiKy(soDuCuoiKy);
					logger.info("Số dư đầu kỳ: " + soDuDauKy + ". Nợ phát sinh kỳ: " + noPhatSinhKy
							+ ". Có phát sinh kỳ: " + coPhatSinhKy + ". Số dư cuối kỳ: " + soDuCuoiKy);

					duLieuKeToanDs.add(duLieuKeToan);

					kyKt = Utils.nextPeriod(kyKt);
					soDuDauKy = soDuCuoiKy;
				}
			}

			soDuCuoi = soDuDau + noPhatSinh - coPhatSinh;

			model.addAttribute("soDuDau", soDuDau);
			model.addAttribute("noPhatSinh", noPhatSinh);
			model.addAttribute("coPhatSinh", coPhatSinh);
			model.addAttribute("soDuCuoi", soDuCuoi);

			model.addAttribute("kyKeToanDs", kyKeToanDAO.danhSachKyKeToan());
			model.addAttribute("duLieuKeToanDs", duLieuKeToanDs);
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
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

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
	public String sktSoCongNo(@ModelAttribute("mainFinanceForm") TkSoKeToanForm form, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			// Lấy danh sách đối tượng (khách hàng)
			List<KhachHang> khachHangDs = khachHangDAO.danhSachKhachHang();

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
				form.setTaiKhoan(LoaiTaiKhoan.PHAI_THU_KHACH_HANG);
			}

			// Lấy danh sách tài khoản
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();
			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);

			logger.info("Tính sổ cái tài khoản: " + form.getTaiKhoan());
			// Lấy danh sách các nghiệp vụ kế toán theo tài khoản, loại chứng từ, và từng kỳ
			List<DuLieuKeToan> duLieuKeToanDs = new ArrayList<>();
			KyKeToanCon kyKt = new KyKeToanCon(form.getDau(), form.getLoaiKy());
			LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan(form.getTaiKhoan());

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

				DuLieuKeToan duLieuKeToan = new DuLieuKeToan(kyKt, loaiTaiKhoan);

				// Lấy nghiệp vụ kế toán được ghi từ phiếu thu, phiếu chi, báo nợ, báo có
				duLieuKeToan.themNghiepVuKeToan(soKeToanDAO.danhSachNghiepVuKeToanTheoLoaiTaiKhoan(form.getTaiKhoan(),
						kyKt.getDau(), kyKt.getCuoi(), form.getLoaiCts()));

				// Lấy nghiệp vụ kế toán được ghi từ phiếu kế toán tổng hợp
				duLieuKeToan.themNghiepVuKeToan(soKeToanDAO.danhSachNghiepVuKeToanTheoLoaiTaiKhoan(form.getTaiKhoan(),
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

				duLieuKeToan.setSoDuDauKy(soDuDauKy);
				duLieuKeToan.setTongNoPhatSinh(noPhatSinhKy);
				duLieuKeToan.setTongCoPhatSinh(coPhatSinhKy);
				duLieuKeToan.setSoDuCuoiKy(soDuCuoiKy);
				logger.info("Số dư đầu kỳ: " + soDuDauKy + ". Nợ phát sinh kỳ: " + noPhatSinhKy + ". Có phát sinh kỳ: "
						+ coPhatSinhKy + ". Số dư cuối kỳ: " + soDuCuoiKy);

				duLieuKeToanDs.add(duLieuKeToan);

				kyKt = Utils.nextPeriod(kyKt);
				soDuDauKy = soDuCuoiKy;
			}

			soDuCuoi = soDuDau + noPhatSinh - coPhatSinh;

			model.addAttribute("soDuDau", soDuDau);
			model.addAttribute("noPhatSinh", noPhatSinh);
			model.addAttribute("coPhatSinh", coPhatSinh);
			model.addAttribute("soDuCuoi", soDuCuoi);

			model.addAttribute("duLieuKeToanDs", duLieuKeToanDs);
			model.addAttribute("khachHangDs", khachHangDs);
			model.addAttribute("mainFinanceForm", form);

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
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

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
