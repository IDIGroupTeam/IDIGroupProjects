package com.idi.finance.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
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
import com.idi.finance.bean.bctc.DuLieuKeToan;
import com.idi.finance.bean.bctc.KyKeToanCon;
import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.KetChuyenButToan;
import com.idi.finance.bean.doituong.DoiTuong;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.hanghoa.KhoHang;
import com.idi.finance.bean.kyketoan.KyKeToan;
import com.idi.finance.bean.kyketoan.SoDuKy;
import com.idi.finance.bean.soketoan.NghiepVuKeToan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.bean.taikhoan.TaiKhoan;
import com.idi.finance.dao.ChungTuDAO;
import com.idi.finance.dao.HangHoaDAO;
import com.idi.finance.dao.KhachHangDAO;
import com.idi.finance.dao.KhoHangDAO;
import com.idi.finance.dao.KyKeToanDAO;
import com.idi.finance.dao.NhaCungCapDAO;
import com.idi.finance.dao.NhanVienDAO;
import com.idi.finance.dao.SoKeToanDAO;
import com.idi.finance.dao.TaiKhoanDAO;
import com.idi.finance.form.TkSoKeToanForm;
import com.idi.finance.utils.PropCont;
import com.idi.finance.utils.Utils;

@Controller
public class SoKeToanController {
	private static final Logger logger = Logger.getLogger(SoKeToanController.class);

	@Autowired
	DungChung dungChung;

	@Autowired
	PropCont props;

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

	@Autowired
	HangHoaDAO hangHoaDAO;

	@Autowired
	KhoHangDAO khoHangDAO;

	private WebDataBinder binder;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(List.class, "khoDs", new CustomCollectionEditor(List.class) {
			@Override
			protected Object convertElement(Object element) {
				KhoHang kho = new KhoHang();
				try {
					if (element instanceof String) {
						Integer maKho = Integer.parseInt((String) element);
						kho = hangHoaDAO.layKhoBai(maKho);
					} else if (element instanceof Integer) {
						Integer maKho = (Integer) element;
						kho = hangHoaDAO.layKhoBai(maKho);
					} else if (element instanceof KhoHang) {
						kho = (KhoHang) element;
					}

					return kho;
				} catch (Exception e) {
					e.printStackTrace();
				}

				return kho;
			}
		});

		this.binder = binder;
	}

	@RequestMapping(value = "/soketoan/nhatkychung", method = { RequestMethod.GET, RequestMethod.POST })
	public String sktNhatKyChung(@ModelAttribute("mainFinanceForm") @Validated TkSoKeToanForm form,
			BindingResult result, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

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
			List<String> loaiCtKtths = new ArrayList<>();
			List<String> loaiCtKhos = new ArrayList<>();
			List<String> loaiCtKcs = new ArrayList<>();

			if (form.getLoaiCts().contains(ChungTu.TAT_CA)) {
				loaiCts.add(ChungTu.CHUNG_TU_PHIEU_THU);
				loaiCts.add(ChungTu.CHUNG_TU_PHIEU_CHI);
				loaiCts.add(ChungTu.CHUNG_TU_BAO_NO);
				loaiCts.add(ChungTu.CHUNG_TU_BAO_CO);

				loaiCtKtths.add(ChungTu.CHUNG_TU_KT_TH);

				loaiCtKhos.add(ChungTu.CHUNG_TU_MUA_HANG);
				loaiCtKhos.add(ChungTu.CHUNG_TU_BAN_HANG);

				loaiCtKcs.add(ChungTu.CHUNG_TU_KET_CHUYEN);
			} else {
				Iterator<String> iter = form.getLoaiCts().iterator();
				while (iter.hasNext()) {
					String loaiCt = iter.next();

					if (loaiCt.equals(ChungTu.CHUNG_TU_KT_TH)) {
						loaiCtKtths.add(ChungTu.CHUNG_TU_KT_TH);
					} else if (loaiCt.equals(ChungTu.CHUNG_TU_KET_CHUYEN)) {
						loaiCtKcs.add(loaiCt);
					} else if (loaiCt.equals(ChungTu.CHUNG_TU_BAN_HANG) || loaiCt.equals(ChungTu.CHUNG_TU_MUA_HANG)) {
						loaiCtKhos.add(loaiCt);
					} else {
						loaiCts.add(loaiCt);
					}
				}
			}

			chungTuDs = chungTuDAO.danhSachChungTu(loaiCts, form.getDau(), form.getCuoi());
			if (chungTuDs == null)
				chungTuDs = new ArrayList<>();
			logger.info("chungTuDs: " + chungTuDs);

			List<ChungTu> chungTuKtthDs = chungTuDAO.danhSachChungTuKtth(loaiCtKtths, form.getDau(), form.getCuoi());
			if (chungTuKtthDs == null)
				chungTuKtthDs = new ArrayList<>();
			logger.info("chungTuKtthDs: " + chungTuKtthDs);

			List<ChungTu> chungTuKhoDs = chungTuDAO.danhSachChungTuKho(loaiCtKhos, form.getDau(), form.getCuoi());
			if (chungTuKhoDs == null)
				chungTuKhoDs = new ArrayList<>();
			logger.info("chungTuKhoDs: " + chungTuKhoDs);

			List<ChungTu> chungTuKcDs = null;
			if (loaiCtKcs.size() > 0) {
				chungTuKcDs = chungTuDAO.danhSachKetChuyen(form.getDau(), form.getCuoi());
			}
			if (chungTuKcDs == null)
				chungTuKcDs = new ArrayList<>();
			logger.info("chungTuKcDs: " + chungTuKcDs);

			chungTuDs.addAll(chungTuKtthDs);
			chungTuDs.addAll(chungTuKhoDs);
			chungTuDs.addAll(chungTuKcDs);

			List<TaiKhoan> taiKhoanDs = new ArrayList<>();
			for (Iterator<ChungTu> ctIter = chungTuDs.iterator(); ctIter.hasNext();) {
				ChungTu chungTu = ctIter.next();

				if (chungTu.getLoaiCt().equals(ChungTu.CHUNG_TU_KT_TH)) {
					taiKhoanDs.addAll(chungTu.getTaiKhoanKtthDs());
				} else if (chungTu.getLoaiCt().equals(ChungTu.CHUNG_TU_MUA_HANG)
						|| chungTu.getLoaiCt().equals(ChungTu.CHUNG_TU_BAN_HANG)) {
					if (chungTu.getTkThanhtoan() != null && chungTu.getTkThanhtoan().getLoaiTaiKhoan() != null
							&& chungTu.getTkThanhtoan().getLoaiTaiKhoan().getMaTk() != null
							&& !chungTu.getTkThanhtoan().getLoaiTaiKhoan().getMaTk().isEmpty()) {
						taiKhoanDs.add(chungTu.getTkThanhtoan());
					}
					if (chungTu.getTkKho() != null && chungTu.getTkKho().getLoaiTaiKhoan() != null
							&& chungTu.getTkKho().getLoaiTaiKhoan().getMaTk() != null
							&& !chungTu.getTkKho().getLoaiTaiKhoan().getMaTk().isEmpty()) {
						taiKhoanDs.add(chungTu.getTkKho());
					}
					if (chungTu.getTkDoanhThu() != null && chungTu.getTkDoanhThu().getLoaiTaiKhoan() != null
							&& chungTu.getTkDoanhThu().getLoaiTaiKhoan().getMaTk() != null
							&& !chungTu.getTkDoanhThu().getLoaiTaiKhoan().getMaTk().isEmpty()) {
						taiKhoanDs.add(chungTu.getTkDoanhThu());
					}
					if (chungTu.getTkChiPhi() != null && chungTu.getTkChiPhi().getLoaiTaiKhoan() != null
							&& chungTu.getTkChiPhi().getLoaiTaiKhoan().getMaTk() != null
							&& !chungTu.getTkChiPhi().getLoaiTaiKhoan().getMaTk().isEmpty()) {
						taiKhoanDs.add(chungTu.getTkChiPhi());
					}
					if (chungTu.getTkGiaVon() != null && chungTu.getTkGiaVon().getLoaiTaiKhoan() != null
							&& chungTu.getTkGiaVon().getLoaiTaiKhoan().getMaTk() != null
							&& !chungTu.getTkGiaVon().getLoaiTaiKhoan().getMaTk().isEmpty()) {
						taiKhoanDs.add(chungTu.getTkGiaVon());
					}
					if (chungTu.getTkChietKhau() != null && chungTu.getTkChietKhau().getLoaiTaiKhoan() != null
							&& chungTu.getTkChietKhau().getLoaiTaiKhoan().getMaTk() != null
							&& !chungTu.getTkChietKhau().getLoaiTaiKhoan().getMaTk().isEmpty()) {
						taiKhoanDs.add(chungTu.getTkChietKhau());
					}
					if (chungTu.getTkGiamGia() != null && chungTu.getTkGiamGia().getLoaiTaiKhoan() != null
							&& chungTu.getTkGiamGia().getLoaiTaiKhoan().getMaTk() != null
							&& !chungTu.getTkGiamGia().getLoaiTaiKhoan().getMaTk().isEmpty()) {
						taiKhoanDs.add(chungTu.getTkGiamGia());
					}
					if (chungTu.getTkTraLai() != null && chungTu.getTkTraLai().getLoaiTaiKhoan() != null
							&& chungTu.getTkTraLai().getLoaiTaiKhoan().getMaTk() != null
							&& !chungTu.getTkTraLai().getLoaiTaiKhoan().getMaTk().isEmpty()) {
						taiKhoanDs.add(chungTu.getTkTraLai());
					}
					if (chungTu.getTkThue() != null && chungTu.getTkThue().getLoaiTaiKhoan() != null
							&& chungTu.getTkThue().getLoaiTaiKhoan().getMaTk() != null
							&& !chungTu.getTkThue().getLoaiTaiKhoan().getMaTk().isEmpty()) {
						taiKhoanDs.add(chungTu.getTkThue());
					}
					if (chungTu.getTkThueGtgt() != null && chungTu.getTkThueGtgt().getLoaiTaiKhoan() != null
							&& chungTu.getTkThueGtgt().getLoaiTaiKhoan().getMaTk() != null
							&& !chungTu.getTkThueGtgt().getLoaiTaiKhoan().getMaTk().isEmpty()) {
						taiKhoanDs.add(chungTu.getTkThueGtgt());
					}
					if (chungTu.getTkThueTtdb() != null && chungTu.getTkThueTtdb().getLoaiTaiKhoan() != null
							&& chungTu.getTkThueTtdb().getLoaiTaiKhoan().getMaTk() != null
							&& !chungTu.getTkThueTtdb().getLoaiTaiKhoan().getMaTk().isEmpty()) {
						taiKhoanDs.add(chungTu.getTkThueTtdb());
					}
					if (chungTu.getTkThueXk() != null && chungTu.getTkThueXk().getLoaiTaiKhoan() != null
							&& chungTu.getTkThueXk().getLoaiTaiKhoan().getMaTk() != null
							&& !chungTu.getTkThueXk().getLoaiTaiKhoan().getMaTk().isEmpty()) {
						taiKhoanDs.add(chungTu.getTkThueXk());
					}
					if (chungTu.getTkThueNk() != null && chungTu.getTkThueNk().getLoaiTaiKhoan() != null
							&& chungTu.getTkThueNk().getLoaiTaiKhoan().getMaTk() != null
							&& !chungTu.getTkThueNk().getLoaiTaiKhoan().getMaTk().isEmpty()) {
						taiKhoanDs.add(chungTu.getTkThueNk());
					}
					if (chungTu.getTkThueXk() != null && chungTu.getTkThueXk().getLoaiTaiKhoan() != null
							&& chungTu.getTkThueXk().getLoaiTaiKhoan().getMaTk() != null
							&& !chungTu.getTkThueXk().getLoaiTaiKhoan().getMaTk().isEmpty()) {
						taiKhoanDs.add(chungTu.getTkThueXk());
					}
				} else if (chungTu.getLoaiCt().equals(ChungTu.CHUNG_TU_KET_CHUYEN)) {
					if (chungTu.getKcbtDs() != null) {
						for (Iterator<KetChuyenButToan> iter = chungTu.getKcbtDs().iterator(); iter.hasNext();) {
							KetChuyenButToan ketChuyen = iter.next();
							if (ketChuyen.getTaiKhoanNo() != null && ketChuyen.getTaiKhoanNo().getLoaiTaiKhoan() != null
									&& ketChuyen.getTaiKhoanNo().getLoaiTaiKhoan().getMaTk() != null
									&& !ketChuyen.getTaiKhoanNo().getLoaiTaiKhoan().getMaTk().isEmpty()) {
								taiKhoanDs.add(ketChuyen.getTaiKhoanNo());
							}
							if (ketChuyen.getTaiKhoanCo() != null && ketChuyen.getTaiKhoanCo().getLoaiTaiKhoan() != null
									&& ketChuyen.getTaiKhoanCo().getLoaiTaiKhoan().getMaTk() != null
									&& !ketChuyen.getTaiKhoanCo().getLoaiTaiKhoan().getMaTk().isEmpty()) {
								taiKhoanDs.add(ketChuyen.getTaiKhoanCo());
							}
						}
					}
				} else {
					// Phiếu thu, phiếu chi, báo nợ, báo có
					taiKhoanDs.addAll(chungTu.getTaiKhoanDs());
				}
			}

			Collections.sort(taiKhoanDs);

			model.addAttribute("kyKeToanDs", kyKeToanDAO.danhSachKyKeToan());
			model.addAttribute("taiKhoanDs", taiKhoanDs);
			model.addAttribute("mainFinanceForm", form);

			model.addAttribute("tab", "tabSKTNKC");
			return "sktNhatKyChung";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/soketoan/socai/{maTk}/{maKyKt}/{dau}/{cuoi}", method = { RequestMethod.GET })
	public String sktSoCaiMaTk(@PathVariable("maTk") String maTk, @PathVariable("maKyKt") int maKyKt,
			@PathVariable("dau") String dau, @PathVariable("cuoi") String cuoi, Model model) {
		KyKeToan kyKeToan = new KyKeToan();
		kyKeToan.setMaKyKt(maKyKt);

		Date batDau = null;
		Date ketThuc = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
			batDau = sdf.parse(dau);
			ketThuc = sdf.parse(cuoi);
		} catch (ParseException e) {
		}

		TkSoKeToanForm form = new TkSoKeToanForm();
		form.setTaiKhoan(maTk);
		form.setKyKeToan(kyKeToan);
		form.setDau(batDau);
		form.setCuoi(ketThuc);

		return sktSoCai(form, binder.getBindingResult(), model);

	}

	@RequestMapping(value = "/soketoan/socai", method = { RequestMethod.GET, RequestMethod.POST })
	public String sktSoCai(@ModelAttribute("mainFinanceForm") @Validated TkSoKeToanForm form, BindingResult result,
			Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

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
			LoaiTaiKhoan loaiTaiKhoan = taiKhoanDAO.layTaiKhoan(form.getTaiKhoan());

			double soDuDau = 0;
			double noDau = 0;
			double coDau = 0;

			double soDuCuoi = 0;
			double noCuoi = 0;
			double coCuoi = 0;

			double noPhatSinh = 0;
			double coPhatSinh = 0;

			KyKeToanCon kyKt = new KyKeToanCon(form.getDau(), form.getLoaiKy());
			if (form.getLoaiKy() == KyKeToanCon.NAN) {
				kyKt = new KyKeToanCon(form.getDau(), form.getCuoi());
			}

			// Lấy tổng nợ đầu kỳ
			double noDauKy = soKeToanDAO.tongPhatSinh(form.getTaiKhoan(), LoaiTaiKhoan.NO,
					form.getKyKeToan().getBatDau(), Utils.prevPeriod(kyKt).getCuoi());

			// Lấy tổng có đầu kỳ
			double coDauKy = soKeToanDAO.tongPhatSinh(form.getTaiKhoan(), LoaiTaiKhoan.CO,
					form.getKyKeToan().getBatDau(), Utils.prevPeriod(kyKt).getCuoi());

			// Tính ra số dư đầu kỳ
			try {
				SoDuKy soDuKy = kyKeToanDAO.laySoDuKy(form.getTaiKhoan(), form.getKyKeToan().getMaKyKt());
				noDauKy += soDuKy.getNoDauKy();
				coDauKy += soDuKy.getCoDauKy();
			} catch (Exception e) {

			}

			double soDuDauKy = noDauKy - coDauKy;
			soDuDau = soDuDauKy;
			noDau = noDauKy;
			coDau = coDauKy;

			while (kyKt != null && kyKt.getCuoi() != null && !kyKt.getCuoi().after(form.getCuoi())) {
				logger.info("Kỳ: " + kyKt);
				DuLieuKeToan duLieuKeToan = new DuLieuKeToan(kyKt, loaiTaiKhoan);

				// Lấy nghiệp vụ kế toán được ghi từ phiếu thu, phiếu chi, báo nợ, báo có
				duLieuKeToan.themNghiepVuKeToan(soKeToanDAO.danhSachNghiepVuKeToanTheoLoaiTaiKhoan(form.getTaiKhoan(),
						kyKt.getDau(), kyKt.getCuoi()));

				duLieuKeToan.themNghiepVuKeToan(soKeToanDAO
						.danhSachNghiepVuKeToanKtthTheoLoaiTaiKhoan(form.getTaiKhoan(), kyKt.getDau(), kyKt.getCuoi()));

				// Lấy nghiệp vụ kế toán được ghi từ chứng từ mua hàng, bán hàng
				duLieuKeToan.themNghiepVuKeToan(soKeToanDAO
						.danhSachNghiepVuKeToanKhoTheoLoaiTaiKhoan(form.getTaiKhoan(), kyKt.getDau(), kyKt.getCuoi()));

				// Lấy nghiệp vụ kế toán được ghi từ các kết chuyển
				duLieuKeToan.themNghiepVuKeToan(soKeToanDAO.danhSachNghiepVuKeToanKcTheoLoaiTaiKhoan(form.getTaiKhoan(),
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
				duLieuKeToan.setSoDuDauKy(soDuDauKy);
				duLieuKeToan.setNoDauKy(noDauKy);
				duLieuKeToan.setCoDauKy(coDauKy);
				duLieuKeToan.setTongNoPhatSinh(noPhatSinhKy);
				duLieuKeToan.setTongCoPhatSinh(coPhatSinhKy);

				logger.info("Số dư đầu kỳ: " + soDuDauKy + ". Nợ phát sinh kỳ: " + noPhatSinhKy + ". Có phát sinh kỳ: "
						+ coPhatSinhKy + ". Số dư cuối kỳ: " + duLieuKeToan.getSoDuCuoiKy());

				duLieuKeToan.tinhSoTienTonNghiepVuKeToanDs(loaiTaiKhoan);
				duLieuKeToanDs.add(duLieuKeToan);

				kyKt = Utils.nextPeriod(kyKt);
				soDuDauKy = duLieuKeToan.getSoDuCuoiKy();
				noDauKy = duLieuKeToan.getNoCuoiKy();
				coDauKy = duLieuKeToan.getCoCuoiKy();
			}

			noCuoi = noDau + noPhatSinh;
			coCuoi = coDau + coPhatSinh;
			soDuCuoi = noCuoi - coCuoi;

			model.addAttribute("soDuDau", soDuDau);
			model.addAttribute("noDau", noDau);
			model.addAttribute("coDau", coDau);
			model.addAttribute("noPhatSinh", noPhatSinh);
			model.addAttribute("coPhatSinh", coPhatSinh);
			model.addAttribute("soDuCuoi", soDuCuoi);
			model.addAttribute("noCuoi", noCuoi);
			model.addAttribute("coCuoi", coCuoi);

			model.addAttribute("loaiTaiKhoan", loaiTaiKhoan);
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

	@RequestMapping("/soketoan/socongno/tonghop")
	public String sktSoTongHopCongNo(@ModelAttribute("mainFinanceForm") @Validated TkSoKeToanForm form,
			BindingResult result, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			// Lấy kỳ kế toán mặc định
			if (form.getKyKeToan() == null) {
				form.setKyKeToan(dungChung.getKyKeToan());
			} else {
				form.setKyKeToan(kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt()));
			}

			Date homNay = new Date();
			if (!form.getKyKeToan().getBatDau().after(homNay) && !form.getKyKeToan().getKetThuc().before(homNay)) {
				// Nếu kỳ kế toán mặc định là năm hiện tại
				// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của
				// tháng hiện tại
				if (form.getDau() == null) {
					form.setDau(Utils.getStartDateOfMonth(homNay));
				}

				if (form.getCuoi() == null) {
					form.setCuoi(Utils.getEndDateOfMonth(homNay));
				}
			} else {
				// Nếu kỳ kế toán mặc định là năm trước
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

			// Lấy danh sách tài khoản công nợ 131; 331; 141; 334 ...
			List<LoaiTaiKhoan> loaiTaiKhoanDs = new ArrayList<>();
			try {
				String[] congNos = props.getCauHinh(PropCont.TAI_KHOAN_CONG_NO).getGiaTri().split(";");
				loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoan(Arrays.asList(congNos));
			} catch (Exception e) {
			}
			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);

			// Nếu không có đầu vào tài khoản thì đặt giá trị mặc định là 131
			if (form.getTaiKhoan() == null) {
				form.setTaiKhoan(loaiTaiKhoanDs.get(0).getMaTk());
			}

			logger.info("Sổ tổng hợp công nợ tài khoản: " + form.getTaiKhoan());
			// Lấy danh sách các nghiệp vụ kế toán theo tài khoản, đối tượng
			List<DuLieuKeToan> duLieuKeToanDs = new ArrayList<>();
			LoaiTaiKhoan loaiTaiKhoan = taiKhoanDAO.layTaiKhoan(form.getTaiKhoan());

			KyKeToanCon kyKt = new KyKeToanCon(form.getDau(), form.getLoaiKy());
			if (form.getLoaiKy() == KyKeToanCon.NAN) {
				kyKt = new KyKeToanCon(form.getDau(), form.getCuoi());
			}

			while (kyKt != null && kyKt.getCuoi() != null && !kyKt.getCuoi().after(form.getCuoi())) {
				logger.info("Kỳ: " + kyKt);
				DuLieuKeToan duLieuKeToan = new DuLieuKeToan(kyKt, loaiTaiKhoan);

				// Lấy số dư kỳ tất cả các đối tượng của tài khoản hiện tại
				List<SoDuKy> soDuKyDs = kyKeToanDAO.danhSachSoDuKyTheoDoiTuong(form.getTaiKhoan(),
						form.getKyKeToan().getMaKyKt());

				// Tính nợ/có đầu kỳ của tất cả các đối tượng
				List<DuLieuKeToan> noCoDauKyDs = soKeToanDAO.danhSachTongHopCongNo(form.getTaiKhoan(),
						form.getKyKeToan().getBatDau(), Utils.prevPeriod(kyKt).getCuoi());
				List<DuLieuKeToan> noCoDauKyKtthDs = soKeToanDAO.danhSachTongHopCongNoKtth(form.getTaiKhoan(),
						form.getKyKeToan().getBatDau(), Utils.prevPeriod(kyKt).getCuoi());
				if (noCoDauKyDs != null) {
					if (noCoDauKyKtthDs != null) {
						Iterator<DuLieuKeToan> noCoDauKyKtthIter = noCoDauKyKtthDs.iterator();
						while (noCoDauKyKtthIter.hasNext()) {
							DuLieuKeToan duLieuKeToanKtthTmpl = noCoDauKyKtthIter.next();

							int pos = noCoDauKyDs.indexOf(duLieuKeToanKtthTmpl);
							if (pos > -1) {
								DuLieuKeToan duLieuKeToanTmpl = noCoDauKyDs.get(pos);
								duLieuKeToanTmpl.tron(duLieuKeToanKtthTmpl);
							} else {
								noCoDauKyDs.add(duLieuKeToanKtthTmpl);
							}
						}
					}
				} else {
					noCoDauKyDs = noCoDauKyKtthDs;
				}

				// Tính số dự đầu kỳ thực
				if (noCoDauKyDs != null) {
					logger.info("Đặt lại giá trị từ tổng phát sinh sang số dư đầu");
					Iterator<DuLieuKeToan> noCoDauKyIter = noCoDauKyDs.iterator();
					while (noCoDauKyIter.hasNext()) {
						DuLieuKeToan duLieuKeToanTmpl = noCoDauKyIter.next();

						duLieuKeToanTmpl.setNoDauKy(duLieuKeToanTmpl.getTongNoPhatSinh());
						duLieuKeToanTmpl.setCoDauKy(duLieuKeToanTmpl.getTongCoPhatSinh());
						duLieuKeToanTmpl.setTongNoPhatSinh(0);
						duLieuKeToanTmpl.setTongCoPhatSinh(0);
					}

					logger.info("Trộn số dư đầu kỳ");
					noCoDauKyIter = noCoDauKyDs.iterator();
					while (noCoDauKyIter.hasNext()) {
						DuLieuKeToan duLieuKeToanTmpl = noCoDauKyIter.next();

						if (soDuKyDs != null) {
							Iterator<SoDuKy> soDuKyIter = soDuKyDs.iterator();
							while (soDuKyIter.hasNext()) {
								SoDuKy soDuKy = soDuKyIter.next();

								if (duLieuKeToanTmpl.getDoiTuong().equals(soDuKy.getDoiTuong())) {
									duLieuKeToanTmpl.setNoDauKy(duLieuKeToanTmpl.getNoDauKy() + soDuKy.getNoDauKy());
									duLieuKeToanTmpl.setCoDauKy(duLieuKeToanTmpl.getCoDauKy() + soDuKy.getCoDauKy());
									break;
								}
							}
						}
					}

					if (soDuKyDs != null) {
						Iterator<SoDuKy> soDuKyIter = soDuKyDs.iterator();
						while (soDuKyIter.hasNext()) {
							SoDuKy soDuKy = soDuKyIter.next();

							boolean coDoiTuong = false;
							noCoDauKyIter = noCoDauKyDs.iterator();
							while (noCoDauKyIter.hasNext()) {
								DuLieuKeToan duLieuKeToanTmpl = noCoDauKyIter.next();

								if (duLieuKeToanTmpl.getDoiTuong().equals(soDuKy.getDoiTuong())) {
									coDoiTuong = true;
									break;
								}
							}

							if (!coDoiTuong) {
								DuLieuKeToan duLieuKeToanTmpl = new DuLieuKeToan();
								duLieuKeToanTmpl.setLoaiTaiKhoan(soDuKy.getLoaiTaiKhoan());
								duLieuKeToanTmpl.setDoiTuong(soDuKy.getDoiTuong());
								duLieuKeToanTmpl.setNoDauKy(soDuKy.getNoDauKy());
								duLieuKeToanTmpl.setCoDauKy(soDuKy.getCoDauKy());
								noCoDauKyDs.add(duLieuKeToanTmpl);
							}
						}
					}
				}

				// Tính nợ/có phát sinh trong kỳ của tất cả các đối tượng
				List<DuLieuKeToan> tongNoCoPsDs = soKeToanDAO.danhSachTongHopCongNo(form.getTaiKhoan(), kyKt.getDau(),
						kyKt.getCuoi());
				List<DuLieuKeToan> tongNoCoPsKtthDs = soKeToanDAO.danhSachTongHopCongNoKtth(form.getTaiKhoan(),
						kyKt.getDau(), kyKt.getCuoi());
				if (tongNoCoPsDs != null) {
					if (tongNoCoPsKtthDs != null) {
						Iterator<DuLieuKeToan> tongNoCoPsKtthIter = tongNoCoPsKtthDs.iterator();
						while (tongNoCoPsKtthIter.hasNext()) {
							DuLieuKeToan duLieuKeToanKtthTmpl = tongNoCoPsKtthIter.next();

							int pos = tongNoCoPsDs.indexOf(duLieuKeToanKtthTmpl);
							if (pos > -1) {
								DuLieuKeToan duLieuKeToanTmpl = tongNoCoPsDs.get(pos);
								duLieuKeToanTmpl.tron(duLieuKeToanKtthTmpl);
							} else {
								tongNoCoPsDs.add(duLieuKeToanKtthTmpl);
							}
						}
					}
				} else {
					tongNoCoPsDs = tongNoCoPsKtthDs;
				}

				if (tongNoCoPsDs != null) {
					Iterator<DuLieuKeToan> tongNoCoPsIter = tongNoCoPsDs.iterator();
					while (tongNoCoPsIter.hasNext()) {
						DuLieuKeToan duLieuKeToanTmpl = tongNoCoPsIter.next();

						if (noCoDauKyDs != null) {
							Iterator<DuLieuKeToan> noCoDauKyIter = noCoDauKyDs.iterator();
							while (noCoDauKyIter.hasNext()) {
								DuLieuKeToan noCoDauKyTmpl = noCoDauKyIter.next();

								if (noCoDauKyTmpl.equals(duLieuKeToanTmpl)) {
									duLieuKeToanTmpl.tron(noCoDauKyTmpl);
									break;
								}
							}
						}
					}
				}

				if (noCoDauKyDs != null) {
					Iterator<DuLieuKeToan> noCoDauKyIter = noCoDauKyDs.iterator();
					while (noCoDauKyIter.hasNext()) {
						DuLieuKeToan noCoDauKyTmpl = noCoDauKyIter.next();

						if (tongNoCoPsDs != null) {
							boolean coDoiTuong = false;
							Iterator<DuLieuKeToan> tongNoCoPsIter = tongNoCoPsDs.iterator();
							while (tongNoCoPsIter.hasNext()) {
								DuLieuKeToan duLieuKeToanTmpl = tongNoCoPsIter.next();

								if (duLieuKeToanTmpl.equals(noCoDauKyTmpl)) {
									coDoiTuong = true;
									break;
								}
							}

							if (!coDoiTuong) {
								tongNoCoPsDs.add(noCoDauKyTmpl);
							}
						}
					}
				}

				duLieuKeToan.capNhatDuLieuKeToan(tongNoCoPsDs);
				duLieuKeToanDs.add(duLieuKeToan);

				kyKt = Utils.nextPeriod(kyKt);
			}

			model.addAttribute("loaiTaiKhoan", loaiTaiKhoan);
			model.addAttribute("kyKeToanDs", kyKeToanDAO.danhSachKyKeToan());
			model.addAttribute("duLieuKeToanDs", duLieuKeToanDs);
			model.addAttribute("mainFinanceForm", form);

			model.addAttribute("tab", "tabSKTSTHCN");
			return "sktSoTongHopCongNo";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/soketoan/socongno/chitiet/{maKyKt}/{maTk}/{loaiDt}/{maDt}/{dau}/{cuoi}", method = {
			RequestMethod.GET })
	public String sktSoChiTietCongNo(@PathVariable("maKyKt") int maKyKt, @PathVariable("maTk") String maTk,
			@PathVariable("loaiDt") int loaiDt, @PathVariable("maDt") int maDt, @PathVariable("dau") String dau,
			@PathVariable("cuoi") String cuoi, Model model) {
		KyKeToan kyKeToan = new KyKeToan();
		kyKeToan.setMaKyKt(maKyKt);
		String[] doiTuongDs = { loaiDt + "_" + maDt };

		Date batDau = null;
		Date ketThuc = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
			batDau = sdf.parse(dau);
			ketThuc = sdf.parse(cuoi);
		} catch (ParseException e) {
		}

		TkSoKeToanForm form = new TkSoKeToanForm();
		form.setTaiKhoan(maTk);
		form.setDoiTuongDs(doiTuongDs);
		form.setKyKeToan(kyKeToan);
		form.setDau(batDau);
		form.setCuoi(ketThuc);

		return sktSoChiTietCongNo(form, binder.getBindingResult(), model);
	}

	@RequestMapping("/soketoan/socongno/chitiet")
	public String sktSoChiTietCongNo(@ModelAttribute("mainFinanceForm") @Validated TkSoKeToanForm form,
			BindingResult result, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

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

			// Lấy danh sách tài khoản công nợ 131; 331; 141; 334
			List<LoaiTaiKhoan> loaiTaiKhoanDs = new ArrayList<>();
			try {
				String[] congNos = props.getCauHinh(PropCont.TAI_KHOAN_CONG_NO).getGiaTri().split(";");
				loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoan(Arrays.asList(congNos));
			} catch (Exception e) {
			}
			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);

			// Nếu không có đầu vào tài khoản thì đặt giá trị mặc định
			if (form.getTaiKhoan() == null) {
				form.setTaiKhoan(loaiTaiKhoanDs.get(0).getMaTk());
			}

			// Lấy danh sách đối tượng
			List<DoiTuong> khachHangDs = khachHangDAO.danhSachDoiTuong();
			List<DoiTuong> nhaCungCapDs = nhaCungCapDAO.danhSachDoiTuong();
			List<DoiTuong> nhanVienDs = nhanVienDAO.danhSachDoiTuong();
			List<DoiTuong> doiTuongTatCaDs = new ArrayList<>();
			doiTuongTatCaDs.addAll(khachHangDs);
			doiTuongTatCaDs.addAll(nhaCungCapDs);
			doiTuongTatCaDs.addAll(nhanVienDs);

			// Lấy danh sách đối tượng được chọn
			List<DoiTuong> doiTuongDuocChonDs = new ArrayList<>();
			logger.info("Khóa đối tượng được chọn:");
			if (form.getDoiTuongDs() != null) {
				// Không phải lần đầu
				for (String khoaDt : form.getDoiTuongDs()) {
					try {
						DoiTuong doiTuong = new DoiTuong();
						String[] khoaDts = khoaDt.split("_");
						logger.info("khoaDt: " + khoaDt);

						doiTuong.setLoaiDt(Integer.parseInt(khoaDts[0]));
						doiTuong.setMaDt(Integer.parseInt(khoaDts[1]));

						if (doiTuongTatCaDs != null) {
							Iterator<DoiTuong> iter = doiTuongTatCaDs.iterator();
							while (iter.hasNext()) {
								DoiTuong doiTuongTmpl = iter.next();

								if (doiTuongTmpl.equals(doiTuong)) {
									doiTuongDuocChonDs.add(doiTuongTmpl);
									break;
								}
							}
						}
					} catch (Exception e) {
					}
				}
			} else {
				// Nếu là lần đầu thì lấy tất cả đối tượng
				doiTuongDuocChonDs.addAll(doiTuongTatCaDs);
				String[] doiTuongDuocChonStrDs = new String[doiTuongTatCaDs.size()];
				int i = 0;
				for (Iterator<DoiTuong> iter = doiTuongDuocChonDs.iterator(); iter.hasNext();) {
					DoiTuong doiTuong = iter.next();
					doiTuongDuocChonStrDs[i] = doiTuong.getLoaiDt() + "_" + doiTuong.getMaDt();
					logger.info("khoaDt: " + doiTuongDuocChonStrDs[i]);
					i++;
				}
				form.setDoiTuongDs(doiTuongDuocChonStrDs);
			}

			logger.info("Sổ chi tiết công nợ tài khoản " + form.getTaiKhoan() + " của đối tượng " + doiTuongDuocChonDs);
			// Lấy danh sách các nghiệp vụ kế toán theo tài khoản, loại chứng từ, và từng kỳ
			List<DuLieuKeToan> duLieuKeToanDs = new ArrayList<>();
			LoaiTaiKhoan loaiTaiKhoan = taiKhoanDAO.layTaiKhoan(form.getTaiKhoan());

			KyKeToanCon kyKt = new KyKeToanCon(form.getDau(), form.getLoaiKy());
			if (form.getLoaiKy() == KyKeToanCon.NAN) {
				kyKt = new KyKeToanCon(form.getDau(), form.getCuoi());
			}

			logger.info("Lấy danh sách số dư kỳ theo đối tượng");
			List<SoDuKy> soDuKyDs = kyKeToanDAO.danhSachSoDuKyTheoDoiTuong(form.getTaiKhoan(),
					form.getKyKeToan().getMaKyKt());

			logger.info("Lấy tổng nợ/có đầu kỳ");
			List<DuLieuKeToan> noCoDauKyDs = soKeToanDAO.danhSachTongPhatSinhDoiTuong(form.getTaiKhoan(),
					form.getKyKeToan().getBatDau(), Utils.prevPeriod(kyKt).getCuoi());
			List<DuLieuKeToan> noCoDauKyKtthDs = soKeToanDAO.danhSachTongPhatSinhDoiTuongKtth(form.getTaiKhoan(),
					form.getKyKeToan().getBatDau(), Utils.prevPeriod(kyKt).getCuoi());
			if (noCoDauKyDs != null) {
				if (noCoDauKyKtthDs != null) {
					Iterator<DuLieuKeToan> noCoDauKyKtthIter = noCoDauKyKtthDs.iterator();
					while (noCoDauKyKtthIter.hasNext()) {
						DuLieuKeToan duLieuKeToanKtthTmpl = noCoDauKyKtthIter.next();

						int pos = noCoDauKyDs.indexOf(duLieuKeToanKtthTmpl);
						if (pos > -1) {
							DuLieuKeToan duLieuKeToanTmpl = noCoDauKyDs.get(pos);
							duLieuKeToanTmpl.tron(duLieuKeToanKtthTmpl);
						} else {
							noCoDauKyDs.add(duLieuKeToanKtthTmpl);
						}
					}
				}
			} else {
				noCoDauKyDs = noCoDauKyKtthDs;
			}

			Iterator<DuLieuKeToan> noCoDauKyIter = noCoDauKyDs.iterator();
			while (noCoDauKyIter.hasNext()) {
				DuLieuKeToan duLieuKeToanTmpl = noCoDauKyIter.next();

				duLieuKeToanTmpl.setNoDauKy(duLieuKeToanTmpl.getTongNoPhatSinh());
				duLieuKeToanTmpl.setCoDauKy(duLieuKeToanTmpl.getTongCoPhatSinh());
				duLieuKeToanTmpl.setTongNoPhatSinh(0);
				duLieuKeToanTmpl.setTongCoPhatSinh(0);
			}
			logger.info(noCoDauKyDs);

			logger.info("Lấy số dư nợ/có đầu kỳ cho từng đối tượng được chọn");
			List<DoiTuong> doiTuongDs = new ArrayList<>();
			if (doiTuongDuocChonDs != null) {
				Iterator<DoiTuong> doiTuongIter = doiTuongDuocChonDs.iterator();
				while (doiTuongIter.hasNext()) {
					DoiTuong doiTuong = doiTuongIter.next();

					DoiTuong doiTuongTmpl = new DoiTuong();
					doiTuongTmpl.setLoaiDt(doiTuong.getLoaiDt());
					doiTuongTmpl.setMaDt(doiTuong.getMaDt());
					doiTuongTmpl.setTenDt(doiTuong.getTenDt());

					DuLieuKeToan duLieuKeToan = new DuLieuKeToan();
					duLieuKeToan.setLoaiTaiKhoan(loaiTaiKhoan);
					duLieuKeToan.setKyKeToan(kyKt);
					duLieuKeToan.setDoiTuong(doiTuongTmpl);

					if (soDuKyDs != null) {
						Iterator<SoDuKy> soDuKyIter = soDuKyDs.iterator();
						while (soDuKyIter.hasNext()) {
							SoDuKy soDuKy = soDuKyIter.next();

							if (doiTuong.equals(soDuKy.getDoiTuong())) {
								duLieuKeToan.setNoDauKy(soDuKy.getNoDauKy());
								duLieuKeToan.setCoDauKy(soDuKy.getCoDauKy());
								break;
							}
						}
					}

					if (noCoDauKyDs != null) {
						noCoDauKyIter = noCoDauKyDs.iterator();
						while (noCoDauKyIter.hasNext()) {
							DuLieuKeToan duLieuKeToanTmpl = noCoDauKyIter.next();

							if (doiTuong.equals(duLieuKeToanTmpl.getDoiTuong())) {
								duLieuKeToan.tron(duLieuKeToanTmpl);
								break;
							}
						}
					}

					doiTuongTmpl.setDuLieuKeToan(duLieuKeToan);
					logger.info("Dữ liệu kế toán đầu kỳ: " + duLieuKeToan);
					logger.info(duLieuKeToan);

					doiTuongDs.add(doiTuongTmpl);
				}
			}

			while (kyKt != null && kyKt.getCuoi() != null && !kyKt.getCuoi().after(form.getCuoi())) {
				logger.info("========== Kỳ: " + kyKt);
				DuLieuKeToan duLieuKeToan = new DuLieuKeToan(kyKt, loaiTaiKhoan);

				// Lấy danh sách nghiệp vụ kế toán ghi vào chứng từ PT, PC, BN, BC
				List<NghiepVuKeToan> nvktDs = soKeToanDAO.danhSachNghiepVuKeToanTheoLoaiTaiKhoan(form.getTaiKhoan(),
						kyKt.getDau(), kyKt.getCuoi());
				logger.info("nvktDs: " + nvktDs);

				List<NghiepVuKeToan> nvktKtthDs = soKeToanDAO
						.danhSachNghiepVuKeToanKtthTheoLoaiTaiKhoan(form.getTaiKhoan(), kyKt.getDau(), kyKt.getCuoi());
				logger.info("nvktKtthDs: " + nvktKtthDs);

				// Lấy danh sách nghiệp vụ kế toán phát sinh ghi từ chứng từ MH, BH
				List<NghiepVuKeToan> nvktKhoDs = soKeToanDAO
						.danhSachNghiepVuKeToanKhoTheoLoaiTaiKhoan(form.getTaiKhoan(), kyKt.getDau(), kyKt.getCuoi());
				logger.info("nvktKhoDs: " + nvktKhoDs);

				// Lấy tổng phát sinh
				List<DuLieuKeToan> tongPhatSinhDs = soKeToanDAO.danhSachTongPhatSinhDoiTuong(form.getTaiKhoan(),
						kyKt.getDau(), kyKt.getCuoi());
				logger.info("tongPhatSinhDs: " + tongPhatSinhDs);
				List<DuLieuKeToan> tongPhatSinhKtthDs = soKeToanDAO.danhSachTongPhatSinhDoiTuongKtth(form.getTaiKhoan(),
						kyKt.getDau(), kyKt.getCuoi());
				logger.info("tongPhatSinhKtthDs: " + tongPhatSinhKtthDs);
				if (tongPhatSinhDs != null) {
					if (tongPhatSinhKtthDs != null) {
						Iterator<DuLieuKeToan> tongPhatSinhKtthIter = tongPhatSinhKtthDs.iterator();
						while (tongPhatSinhKtthIter.hasNext()) {
							DuLieuKeToan duLieuKeToanKtthTmpl = tongPhatSinhKtthIter.next();

							int pos = tongPhatSinhDs.indexOf(duLieuKeToanKtthTmpl);
							if (pos > -1) {
								DuLieuKeToan duLieuKeToanTmpl = tongPhatSinhDs.get(pos);
								duLieuKeToanTmpl.tron(duLieuKeToanKtthTmpl);
							} else {
								tongPhatSinhDs.add(duLieuKeToanKtthTmpl);
							}
						}
					}
				} else {
					tongPhatSinhDs = tongPhatSinhKtthDs;
				}

				// Xác định tổng phát sinh và các nvkt cho các đối tượng được chọn
				if (doiTuongDs != null) {
					Iterator<DoiTuong> doiTuongIter = doiTuongDs.iterator();
					while (doiTuongIter.hasNext()) {
						DoiTuong doiTuongTmpl = doiTuongIter.next();
						try {
							// Tính tổng phát sinh
							if (tongPhatSinhDs != null) {
								Iterator<DuLieuKeToan> tongPhatSinhIter = tongPhatSinhDs.iterator();
								while (tongPhatSinhIter.hasNext()) {
									DuLieuKeToan tongPhatSinh = tongPhatSinhIter.next();

									if (doiTuongTmpl.equals(tongPhatSinh.getDoiTuong())) {
										doiTuongTmpl.getDuLieuKeToan().tron(tongPhatSinh);
										break;
									}
								}
							}

							// Thêm nghiệp vụ kế toán vào danh sách
							logger.info("nvktDs: " + doiTuongTmpl.getDuLieuKeToan().getNghiepVuKeToanDs());
							if (nvktDs != null) {
								Iterator<NghiepVuKeToan> nvktIter = nvktDs.iterator();
								while (nvktIter.hasNext()) {
									NghiepVuKeToan nvkt = nvktIter.next();
									logger.info("nvktDs: " + nvkt);

									if (doiTuongTmpl.equals(nvkt.getChungTu().getDoiTuong())) {
										doiTuongTmpl.getDuLieuKeToan().themNghiepVuKeToan(nvkt);
									}
								}
							}

							// Thêm nghiệp vụ kế toán vào danh sách
							logger.info("nvktKtth: " + doiTuongTmpl.getDuLieuKeToan().getNghiepVuKeToanDs());
							if (nvktKtthDs != null) {
								Iterator<NghiepVuKeToan> nvktKtthIter = nvktKtthDs.iterator();
								while (nvktKtthIter.hasNext()) {
									NghiepVuKeToan nvktKtth = nvktKtthIter.next();
									logger.info("nvktKtth: " + nvktKtth);

									if (doiTuongTmpl.equals(nvktKtth.getTaiKhoanNo().getDoiTuong())
											|| doiTuongTmpl.equals(nvktKtth.getTaiKhoanCo().getDoiTuong())) {
										doiTuongTmpl.getDuLieuKeToan().themNghiepVuKeToan(nvktKtth);
									}
								}
							}

							// Thêm nghiệp vụ kế toán kho vào danh sách
							logger.info("nvktKhoDs: " + doiTuongTmpl.getDuLieuKeToan().getNghiepVuKeToanDs());
							if (nvktKhoDs != null) {
								Iterator<NghiepVuKeToan> nvktKhoIter = nvktKhoDs.iterator();
								while (nvktKhoIter.hasNext()) {
									NghiepVuKeToan nvktKho = nvktKhoIter.next();
									logger.info("nvktKho: " + nvktKho);

									if (doiTuongTmpl.equals(nvktKho.getChungTu().getDoiTuong())) {
										doiTuongTmpl.getDuLieuKeToan().themNghiepVuKeToan(nvktKho);
									}
								}
							}
							logger.info("nvktDs: " + doiTuongTmpl.getDuLieuKeToan().getNghiepVuKeToanDs());
						} catch (Exception e) {
							e.printStackTrace();
						}

						logger.info(doiTuongTmpl + " " + doiTuongTmpl.getDuLieuKeToan());
					}
				}

				duLieuKeToan.setDoiTuongDs(doiTuongDs);
				duLieuKeToanDs.add(duLieuKeToan);

				logger.info("Chuẩn bị dữ liệu cho kỳ sau: lấy số dư cuối kỳ trước gán làm số dư đầu kỳ sau");
				kyKt = Utils.nextPeriod(kyKt);
				List<DoiTuong> doiTuongTiepDs = new ArrayList<>();
				if (doiTuongDs != null) {
					Iterator<DoiTuong> doiTuongIter = doiTuongDs.iterator();
					while (doiTuongIter.hasNext()) {
						DoiTuong doiTuong = doiTuongIter.next();

						DoiTuong doiTuongTmpl = new DoiTuong();
						doiTuongTmpl.setLoaiDt(doiTuong.getLoaiDt());
						doiTuongTmpl.setMaDt(doiTuong.getMaDt());
						doiTuongTmpl.setTenDt(doiTuong.getTenDt());

						DuLieuKeToan duLieuKeToanTt = new DuLieuKeToan();

						duLieuKeToanTt.setNoDauKy(doiTuong.getDuLieuKeToan().getNoCuoiKy());
						duLieuKeToanTt.setCoDauKy(doiTuong.getDuLieuKeToan().getCoCuoiKy());

						duLieuKeToanTt.setLoaiTaiKhoan(loaiTaiKhoan);
						duLieuKeToanTt.setKyKeToan(kyKt);
						duLieuKeToanTt.setDoiTuong(doiTuongTmpl);

						doiTuongTmpl.setDuLieuKeToan(duLieuKeToanTt);
						logger.info("Dữ liệu kế toán kỳ sau: " + doiTuongTmpl.getDuLieuKeToan());

						doiTuongTiepDs.add(doiTuongTmpl);
					}
				}
				doiTuongDs = doiTuongTiepDs;
			}

			model.addAttribute("khachHangDs", khachHangDs);
			model.addAttribute("nhaCungCapDs", nhaCungCapDs);
			model.addAttribute("nhanVienDs", nhanVienDs);

			model.addAttribute("loaiTaiKhoan", loaiTaiKhoan);
			model.addAttribute("kyKeToanDs", kyKeToanDAO.danhSachKyKeToan());
			model.addAttribute("duLieuKeToanDs", duLieuKeToanDs);
			model.addAttribute("mainFinanceForm", form);

			model.addAttribute("tab", "tabSKTSCTCN");
			return "sktSoChiTietCongNo";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/soketoan/nhapxuatton/tonghop", method = { RequestMethod.GET, RequestMethod.POST })
	public String sktSoThNhapXuatTon(@ModelAttribute("mainFinanceForm") @Validated TkSoKeToanForm form,
			BindingResult result, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

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

			// Lấy danh sách tài khoản kho 152;153;154;155;156
			List<LoaiTaiKhoan> loaiTaiKhoanDs = new ArrayList<>();
			try {
				String[] khoVthhs = props.getCauHinh(PropCont.TAI_KHOAN_KHO_VTHH).getGiaTri().split(";");
				loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoan(Arrays.asList(khoVthhs));
			} catch (Exception e) {
			}

			// Nếu không có đầu vào tài khoản thì đặt giá trị mặc định
			if (form.getTaiKhoan() == null) {
				form.setTaiKhoan(loaiTaiKhoanDs.get(0).getMaTk());
			}
			LoaiTaiKhoan loaiTaiKhoan = taiKhoanDAO.layTaiKhoan(form.getTaiKhoan());

			// Lấy danh sách kho
			List<KhoHang> khoHangDs = hangHoaDAO.danhSachKhoBai();
			if (form != null && form.getKhoDs() == null) {
				form.setKhoDs(khoHangDs);
			}

			List<Integer> maKhoDs = null;
			if (form != null && form.getKhoDs() != null) {
				maKhoDs = new ArrayList<>();
				for (Iterator<KhoHang> iter = form.getKhoDs().iterator(); iter.hasNext();) {
					KhoHang khoHang = iter.next();
					maKhoDs.add(khoHang.getMaKho());
				}
			}

			logger.info("Sổ tổng hợp nhập xuất tồn ... ");
			logger.info("Tài khoản: " + form.getTaiKhoan());
			logger.info("Kho hàng: " + form.getKhoDs());

			// Lấy danh sách các nghiệp vụ kế toán theo tài khoản, loại chứng từ, và từng kỳ
			List<DuLieuKeToan> duLieuKeToanDs = new ArrayList<>();

			KyKeToanCon kyKt = new KyKeToanCon(form.getDau(), form.getLoaiKy());
			if (form.getLoaiKy() == KyKeToanCon.NAN) {
				kyKt = new KyKeToanCon(form.getDau(), form.getCuoi());
			}

			while (kyKt != null && kyKt.getCuoi() != null && !kyKt.getCuoi().after(form.getCuoi())) {
				logger.info("========== Kỳ: " + kyKt);
				DuLieuKeToan duLieuKeToan = new DuLieuKeToan(kyKt, loaiTaiKhoan);

				// Lấy danh sách số dư đầu kỳ nhập xuất tồn
				// theo mã tài khoản và danh sách kho
				List<SoDuKy> soDuKyDs = kyKeToanDAO.danhSachSoDuKyTheoHangHoa(loaiTaiKhoan.getMaTk(),
						kyKeToan.getMaKyKt(), maKhoDs);

				// Tính nợ/có phát sinh kỳ trước tất cả các hàng hóa
				// trong danh sách kho cho trước
				List<DuLieuKeToan> noCoDauKyDs = soKeToanDAO.danhSachTongHopNxt(form.getTaiKhoan(), maKhoDs,
						form.getDau(), Utils.prevPeriod(kyKt).getCuoi());

				// Trộn số dư đầu kỳ nhập xuất tồn các loại hàng hóa
				// trong danh sách kho cho trước
				if (noCoDauKyDs != null) {
					logger.info("Đặt lại giá trị từ tổng phát sinh sang số dư đầu");
					Iterator<DuLieuKeToan> noCoDauKyIter = noCoDauKyDs.iterator();
					while (noCoDauKyIter.hasNext()) {
						DuLieuKeToan duLieuKeToanTmpl = noCoDauKyIter.next();

						duLieuKeToanTmpl.setNoDauKy(duLieuKeToanTmpl.getTongNoPhatSinh());
						duLieuKeToanTmpl.setCoDauKy(duLieuKeToanTmpl.getTongCoPhatSinh());
						duLieuKeToanTmpl.setTongNoPhatSinh(0);
						duLieuKeToanTmpl.setTongCoPhatSinh(0);
					}

					logger.info("Trộn số dư đầu kỳ");
					noCoDauKyIter = noCoDauKyDs.iterator();
					while (noCoDauKyIter.hasNext()) {
						DuLieuKeToan duLieuKeToanTmpl = noCoDauKyIter.next();

						if (soDuKyDs != null) {
							Iterator<SoDuKy> soDuKyIter = soDuKyDs.iterator();
							while (soDuKyIter.hasNext()) {
								SoDuKy soDuKy = soDuKyIter.next();

								if (duLieuKeToanTmpl.getDoiTuong().equals(soDuKy.getDoiTuong())) {
									duLieuKeToanTmpl.setNoDauKy(duLieuKeToanTmpl.getNoDauKy() + soDuKy.getNoDauKy());
									duLieuKeToanTmpl.setCoDauKy(duLieuKeToanTmpl.getCoDauKy() + soDuKy.getCoDauKy());
									break;
								}
							}
						}
					}

					if (soDuKyDs != null) {
						Iterator<SoDuKy> soDuKyIter = soDuKyDs.iterator();
						while (soDuKyIter.hasNext()) {
							SoDuKy soDuKy = soDuKyIter.next();

							boolean coDoiTuong = false;
							noCoDauKyIter = noCoDauKyDs.iterator();
							while (noCoDauKyIter.hasNext()) {
								DuLieuKeToan duLieuKeToanTmpl = noCoDauKyIter.next();

								if (duLieuKeToanTmpl.getDoiTuong().equals(soDuKy.getDoiTuong())) {
									coDoiTuong = true;
									break;
								}
							}

							if (!coDoiTuong) {
								DuLieuKeToan duLieuKeToanTmpl = new DuLieuKeToan();
								duLieuKeToanTmpl.setLoaiTaiKhoan(soDuKy.getLoaiTaiKhoan());
								duLieuKeToanTmpl.setDoiTuong(soDuKy.getDoiTuong());
								duLieuKeToanTmpl.setNoDauKy(soDuKy.getNoDauKy());
								duLieuKeToanTmpl.setCoDauKy(soDuKy.getCoDauKy());
								noCoDauKyDs.add(duLieuKeToanTmpl);
							}
						}
					}
				}

				// Tính nợ/có phát sinh nhập xuất tồn trong kỳ của tất cả các hàng hóa
				List<DuLieuKeToan> tongNoCoPsDs = soKeToanDAO.danhSachTongHopNxt(form.getTaiKhoan(), maKhoDs,
						kyKt.getDau(), kyKt.getCuoi());

				duLieuKeToan.capNhatDuLieuKeToan(tongNoCoPsDs);
				duLieuKeToanDs.add(duLieuKeToan);
				kyKt = Utils.nextPeriod(kyKt);
			}

			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);
			model.addAttribute("loaiTaiKhoan", loaiTaiKhoan);
			model.addAttribute("khoHangDs", khoHangDs);
			model.addAttribute("kyKeToanDs", kyKeToanDAO.danhSachKyKeToan());
			model.addAttribute("duLieuKeToanDs", duLieuKeToanDs);
			model.addAttribute("mainFinanceForm", form);

			model.addAttribute("tab", "tabSKTSTHNXT");
			return "sktSoThNhapXuatTon";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/soketoan/nhapxuatton/chitiet", method = { RequestMethod.GET, RequestMethod.POST })
	public String sktSoCtNhapXuatTon(@ModelAttribute("mainFinanceForm") @Validated TkSoKeToanForm form,
			BindingResult result, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

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
				form.setTaiKhoan(LoaiTaiKhoan.HANG_HOA);
			}

			// Lấy danh sách tài khoản
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();
			LoaiTaiKhoan loaiTaiKhoan = taiKhoanDAO.layTaiKhoan(form.getTaiKhoan());

			// Lấy danh sách kho
			List<KhoHang> khoHangDs = hangHoaDAO.danhSachKhoBai();

			// Lấy danh sách hàng hóa
			List<HangHoa> hangHoaDs = hangHoaDAO.danhSachHangHoa();

			logger.info("Sổ chi tiết nhập xuất tồn ... ");
			logger.info("Tài khoản: " + form.getTaiKhoan());
			// Lấy danh sách các nghiệp vụ kế toán theo tài khoản, loại chứng từ, và từng kỳ
			List<DuLieuKeToan> duLieuKeToanDs = new ArrayList<>();

			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);
			model.addAttribute("loaiTaiKhoan", loaiTaiKhoan);
			model.addAttribute("khoHangDs", khoHangDs);
			model.addAttribute("hangHoaDs", hangHoaDs);
			model.addAttribute("kyKeToanDs", kyKeToanDAO.danhSachKyKeToan());
			model.addAttribute("duLieuKeToanDs", duLieuKeToanDs);
			model.addAttribute("mainFinanceForm", form);

			model.addAttribute("tab", "tabSKTSCTNXT");
			return "sktSoCtNhapXuatTon";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
}
