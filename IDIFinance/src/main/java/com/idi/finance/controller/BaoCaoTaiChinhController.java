package com.idi.finance.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.idi.finance.bean.CauHinh;
import com.idi.finance.bean.DungChung;
import com.idi.finance.bean.bctc.BalanceAssetItem;
import com.idi.finance.bean.bctc.BaoCaoTaiChinh;
import com.idi.finance.bean.bctc.BaoCaoTaiChinhChiTiet;
import com.idi.finance.bean.bctc.BaoCaoTaiChinhCon;
import com.idi.finance.bean.bctc.DuLieuKeToan;
import com.idi.finance.bean.kyketoan.KyKeToan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.BalanceSheetDAO;
import com.idi.finance.dao.BaoCaoTaiChinhDAO;
import com.idi.finance.dao.KyKeToanDAO;
import com.idi.finance.dao.SoKeToanDAO;
import com.idi.finance.dao.TaiKhoanDAO;
import com.idi.finance.hangso.PropCont;
import com.idi.finance.service.BaoCaoService;
import com.idi.finance.service.BaoCaoTaiChinhService;
import com.idi.finance.utils.ExpressionEval;
import com.idi.finance.utils.ReportUtils;
import com.idi.finance.utils.Utils;
import com.idi.finance.validator.BaoCaoTaiChinhValidator;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;

@Controller
public class BaoCaoTaiChinhController {
	private static final Logger logger = Logger.getLogger(BaoCaoTaiChinhController.class);

	@Autowired
	MessageSource messageSource;

	@Autowired
	DungChung dungChung;

	@Autowired
	PropCont props;

	@Autowired
	BalanceSheetDAO balanceSheetDAO;

	@Autowired
	TaiKhoanDAO taiKhoanDAO;

	@Autowired
	SoKeToanDAO soKeToanDAO;

	@Autowired
	KyKeToanDAO kyKeToanDAO;

	@Autowired
	BaoCaoTaiChinhDAO bctcDAO;

	@Autowired
	BaoCaoService baoCaoService;

	@Autowired
	BaoCaoTaiChinhService bctcService;

	@Autowired
	private BaoCaoTaiChinhValidator bctcValidator;

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

		if (target.getClass() == BaoCaoTaiChinh.class) {
			binder.setValidator(bctcValidator);
		}
	}

	@RequestMapping("/bctc/danhsach")
	public String danhSachBctc(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			List<BaoCaoTaiChinh> bctcDs = bctcDAO.danhSachBctc(kyKeToan.getMaKyKt());

			model.addAttribute("bctcDs", bctcDs);
			model.addAttribute("kyKeToan", kyKeToan);
		} catch (Exception e) {
			logger.info(e);
		}

		model.addAttribute("tab", "tabBCTCDS");
		return "danhSachBctc";
	}

	@RequestMapping("/bctc/danhsach/{maKyKt}")
	public @ResponseBody List<BaoCaoTaiChinh> danhSachBctcTheoKyKeToan(@PathVariable("maKyKt") int maKyKt) {
		try {
			List<BaoCaoTaiChinh> bctcDs = bctcDAO.danhSachBctc(maKyKt);

			return bctcDs;
		} catch (Exception e) {
			logger.info(e);
		}

		return null;
	}

	@RequestMapping("/bctc/taomoi")
	public String taoMoiBctc(@ModelAttribute("mainFinanceForm") BaoCaoTaiChinh form, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/bctc/danhsach";
			}

			BaoCaoTaiChinh bctc = new BaoCaoTaiChinh();
			bctc.setKyKeToan(kyKeToan);

			bctc.setBatDau(new Date("01/01/2019"));
			bctc.setKetThuc(new Date("12/31/2019"));
			bctc.setTieuDe("Nam 2019");
			bctc.setNguoiLap("Tran Dong Hai");
			bctc.setGiamDoc("Tran Dong Hai");

			return chuanBiFormBctc(model, bctc);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/bctc/danhsach";
		}
	}

	private String chuanBiFormBctc(Model model, BaoCaoTaiChinh bctc) {
		try {
			model.addAttribute("mainFinanceForm", bctc);

			Date homNay = new Date();
			model.addAttribute("homNay", homNay);

			bctc.setNgayLap(homNay);

			model.addAttribute("tab", "tabBCTCDS");
			return "taoMoiBctc";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/bctc/luu", method = RequestMethod.POST)
	public String luuTaoMoiBctc(@ModelAttribute("mainFinanceForm") @Validated BaoCaoTaiChinh bctc, BindingResult result,
			Model model, HttpServletRequest req) {
		FlashMap flash = RequestContextUtils.getOutputFlashMap(req);
		String message = messageSource.getMessage("Fail.TaoMoi.BaoCaoTaiChinh", null, LocaleContextHolder.getLocale());
		String danhSach = "/bctc/danhdach/";
		String xemChiTiet = "/bctc/xem/";

		try {
			if (result.getErrorCount() > 4) {
				return chuanBiFormBctc(model, bctc);
			}

			logger.info("Tạo báo cáo tài chính");
			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(bctc.getKyKeToan().getMaKyKt());
			bctc.setKyKeToan(kyKeToan);

			List<BaoCaoTaiChinhCon> bctcConDs = new ArrayList<>();
			for (BaoCaoTaiChinhCon bctcCon : bctc.getBctcDs()) {
				if (bctcCon.getLoaiBctc() != 0) {
					bctcCon.setBctc(bctc);
					bctcConDs.add(bctcCon);
				}
			}

			bctc.setBctcDs(bctcConDs);

			for (BaoCaoTaiChinhCon bctcCon : bctc.getBctcDs()) {
				logger.info("Báo cáo tài chính con: " + bctcCon);
				switch (bctcCon.getLoaiBctc()) {
				case BaoCaoTaiChinhCon.LOAI_CDKT:
					// Sinh bảng cân đối kế toán
					bctcCon = sinhBangCdkt(bctcCon);
					break;
				case BaoCaoTaiChinhCon.LOAI_KQHDKD:
					// Sinh bảng kết quả HDKD
					bctcCon = sinhBangKqhdkd(bctcCon);
					break;
				case BaoCaoTaiChinhCon.LOAI_LCTT:
					// Sinh bảng lưu chuyển tiền tệ
					bctcCon = sinhBangLctt(bctcCon);
					break;
				case BaoCaoTaiChinhCon.LOAI_CDPS:
					// Sinh bảng cân đối phát sinh
					bctcCon = sinhBangCdps(bctcCon);
					break;
				default:
					break;
				}
			}

			logger.info("Lưu báo cáo tài chính: " + bctc);
			bctc = bctcDAO.themBctc(bctc);
			return "redirect:" + xemChiTiet + bctc.getMaBctc();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(message);
			flash.put("message", message);
		}

		return "redirect:" + danhSach;
	}

	private BaoCaoTaiChinhCon sinhBangCdkt(BaoCaoTaiChinhCon bctcCon) {
		if (bctcCon == null || bctcCon.getBctc() == null || bctcCon.getBctc().getKyKeToan() == null
				|| bctcCon.getBctc().getBatDau() == null || bctcCon.getBctc().getKetThuc() == null) {
			return bctcCon;
		}

		logger.info("Sinh bảng cân đối kế toán ...");

		KyKeToan kyKeToan = bctcCon.getBctc().getKyKeToan();
		BaoCaoTaiChinh bctc = bctcCon.getBctc();

		logger.info("Lấy toàn bộ giá trị kỳ trước của các chỉ tiêu cấp thấp nhất.");

		Date ngayTruoc = Utils.moveDate(bctc.getBatDau(), Calendar.DATE, -1);
		List<BaoCaoTaiChinhChiTiet> bctcChiTietDauKyDs = bctcDAO.calculateEndBs(kyKeToan.getBatDau(), ngayTruoc,
				kyKeToan.getMaKyKt());
		for (BaoCaoTaiChinhChiTiet bctcChiTiet : bctcChiTietDauKyDs) {
			bctcChiTiet.setBctc(bctcCon);
		}

		logger.info("Lấy toàn bộ giá trị kỳ này của các chỉ tiêu cấp thấp nhất.");
		List<BaoCaoTaiChinhChiTiet> bctcChiTietDs = bctcDAO.calculateEndBs(kyKeToan.getBatDau(), bctc.getKetThuc(),
				kyKeToan.getMaKyKt());
		for (BaoCaoTaiChinhChiTiet bctcChiTiet : bctcChiTietDs) {
			bctcChiTiet.setBctc(bctcCon);
		}

		logger.info("Lấy danh sách các chỉ tiêu vào BalanceAssetItem");
		List<BalanceAssetItem> bais = balanceSheetDAO.listBais();

		List<BaoCaoTaiChinhChiTiet> chiTietDs = new ArrayList<>();
		logger.info("Xây dựng cây BaoCaoTaiChinhChiTiet từ cây BalanceAssetItem");
		for (BalanceAssetItem bai : bais) {
			BaoCaoTaiChinhChiTiet bctcChiTiet = taoBaoCaoTaiChinhChiTiet(bai, bctcCon);
			chiTietDs.add(bctcChiTiet);
		}

		logger.info("Tính giá trị cây BaoCaoTaiChinhChiTiet, sau đó cập nhật vào CSDL");
		for (BaoCaoTaiChinhChiTiet bctcChiTiet : chiTietDs) {
			bctcChiTiet = tinhBangCdkt(bctcChiTiet, bctcChiTietDs, bctcChiTietDauKyDs);
		}

		bctcCon.setChiTietDs(chiTietDs);

		return bctcCon;
	}

	private BaoCaoTaiChinhChiTiet taoBaoCaoTaiChinhChiTiet(BalanceAssetItem bai, BaoCaoTaiChinhCon bctcCon) {
		if (bai == null || bctcCon == null || bctcCon.getBctc() == null
				|| bctcCon.getBctc().getBatDau() == null && bctcCon.getBctc().getKetThuc() == null) {
			return null;
		}

		logger.info("Chỉ tiêu: " + bai);

		BaoCaoTaiChinhChiTiet bctcChiTiet = new BaoCaoTaiChinhChiTiet();
		bctcChiTiet.setBctc(bctcCon);
		bctcChiTiet.setAsset(bai);

		if (bai.getChilds() != null) {
			for (BalanceAssetItem childBai : bai.getChilds()) {
				BaoCaoTaiChinhChiTiet bctcChiTietCon = taoBaoCaoTaiChinhChiTiet(childBai, bctcCon);
				bctcChiTiet.themBctcChiTiet(bctcChiTietCon);
			}
		}

		return bctcChiTiet;
	}

	private BaoCaoTaiChinhChiTiet tinhBangCdkt(BaoCaoTaiChinhChiTiet bctcChiTiet,
			List<BaoCaoTaiChinhChiTiet> bctcChiTietDs, List<BaoCaoTaiChinhChiTiet> bctcChiTietDayKyDs) {
		if (bctcChiTiet == null || bctcChiTietDs == null) {
			return bctcChiTiet;
		}

		if (bctcChiTiet.getChiTietDs() != null && bctcChiTiet.getChiTietDs().size() > 0) {
			for (BaoCaoTaiChinhChiTiet bctcChiTietCon : bctcChiTiet.getChiTietDs()) {
				bctcChiTietCon = tinhBangCdkt(bctcChiTietCon, bctcChiTietDs, bctcChiTietDayKyDs);
				bctcChiTiet.getGiaTri()
						.setGiaTri(bctcChiTiet.getGiaTri().getGiaTri() + bctcChiTietCon.getGiaTri().getGiaTri());
			}
		} else {
			if (bctcChiTiet.getAsset() != null) {
				if (bctcChiTietDs != null) {
					for (BaoCaoTaiChinhChiTiet bctcChiTietTmpl : bctcChiTietDs) {
						if (bctcChiTiet.equals(bctcChiTietTmpl)) {
							bctcChiTiet.setGiaTri(bctcChiTietTmpl.getGiaTri());
							break;
						}
					}
				}
			}
		}

		if (bctcChiTietDayKyDs != null) {
			for (BaoCaoTaiChinhChiTiet bctcChiTietTmpl : bctcChiTietDayKyDs) {
				if (bctcChiTiet.equals(bctcChiTietTmpl)) {
					bctcChiTiet.setGiaTriKyTruoc(bctcChiTietTmpl.getGiaTri());
					break;
				}
			}
		}

		logger.info("Báo cáo chi tiết: " + bctcChiTiet);

		return bctcChiTiet;
	}

	private BaoCaoTaiChinhCon sinhBangKqhdkd(BaoCaoTaiChinhCon bctcCon) {
		if (bctcCon == null || bctcCon.getBctc() == null || bctcCon.getBctc().getKyKeToan() == null
				|| bctcCon.getBctc().getBatDau() == null || bctcCon.getBctc().getKetThuc() == null) {
			return bctcCon;
		}

		logger.info("Sinh bảng kết quả hoạt động kinh doanh ...");

		KyKeToan kyKeToan = bctcCon.getBctc().getKyKeToan();
		BaoCaoTaiChinh bctc = bctcCon.getBctc();

		logger.info("Lấy toàn bộ giá trị kỳ trước của các chỉ tiêu cấp thấp nhất.");
		Date ngayTruoc = Utils.moveDate(bctc.getBatDau(), Calendar.DATE, -1);
		List<BaoCaoTaiChinhChiTiet> bctcChiTietDauKyDs = bctcDAO.calculateEndSR(kyKeToan.getBatDau(), ngayTruoc);
		for (BaoCaoTaiChinhChiTiet bctcChiTiet : bctcChiTietDauKyDs) {
			bctcChiTiet.setBctc(bctcCon);
		}

		logger.info("Lấy toàn bộ giá trị của các chi tiêu cấp thấp nhất.");
		List<BaoCaoTaiChinhChiTiet> bctcChiTietDs = bctcDAO.calculateEndSR(kyKeToan.getBatDau(), bctc.getKetThuc());
		for (BaoCaoTaiChinhChiTiet bctcChiTiet : bctcChiTietDs) {
			bctcChiTiet.setBctc(bctcCon);
		}

		logger.info("Lấy danh sách các chỉ tiêu KQHDKD");
		List<BalanceAssetItem> bais = balanceSheetDAO.listSRBais();

		List<BaoCaoTaiChinhChiTiet> chiTietDs = new ArrayList<>();
		logger.info("Xây dựng cây BaoCaoTaiChinhChiTiet từ cây BalanceAssetItem");
		for (BalanceAssetItem bai : bais) {
			BaoCaoTaiChinhChiTiet bctcChiTiet = taoBaoCaoTaiChinhChiTiet(bai, bctcCon);
			chiTietDs.add(bctcChiTiet);
		}

		logger.info("Tính giá trị cây BaoCaoTaiChinhChiTiet, sau đó cập nhật vào CSDL");
		for (BaoCaoTaiChinhChiTiet bctcChiTiet : chiTietDs) {
			bctcChiTiet = tinhBangKqhdkd(bctcChiTiet, bctcChiTietDs, bctcChiTietDauKyDs);
		}

		bctcCon.setChiTietDs(chiTietDs);

		return bctcCon;
	}

	private BaoCaoTaiChinhChiTiet tinhBangKqhdkd(BaoCaoTaiChinhChiTiet bctcChiTiet,
			List<BaoCaoTaiChinhChiTiet> bctcChiTietDs, List<BaoCaoTaiChinhChiTiet> bctcChiTietDayKyDs) {
		if (bctcChiTiet == null || bctcChiTietDs == null) {
			return bctcChiTiet;
		}

		if (bctcChiTiet.getChiTietDs() != null && bctcChiTiet.getChiTietDs().size() > 0) {
			for (BaoCaoTaiChinhChiTiet bctcChiTietCon : bctcChiTiet.getChiTietDs()) {
				bctcChiTietCon = tinhBangKqhdkd(bctcChiTietCon, bctcChiTietDs, bctcChiTietDayKyDs);
			}

			// Tính giá trị chỉ tiêu hiện tại từ các chỉ tiêu trước đó
			String rule = bctcChiTiet.getAsset().getRule();
			logger.info(rule);
			if (rule != null && !rule.trim().equals("")) {
				List<String> toanHangDs = ExpressionEval.getOperands(rule);
				if (toanHangDs != null) {
					// Với mỗi toán hạng, đó là một tài khoản kế toán
					// Cần tính giá trị của tài khoản kế toán
					for (String toanHang : toanHangDs) {
						for (BaoCaoTaiChinhChiTiet bctcChiTietCon : bctcChiTiet.getChiTietDs()) {
							if (bctcChiTietCon.getAsset().getAssetCode().equals(toanHang)) {
								String value = bctcChiTietCon.getGiaTri().getGiaTri() + "";
								value = value.replace(ExpressionEval.DAU_AM, ExpressionEval.DAU_AM_TAM_THOI);
								rule = rule.replaceAll(toanHang, value);
								logger.info(toanHang + ": " + bctcChiTietCon.getGiaTri().getGiaTri());
								break;
							}
						}
					}

					double ketQua = ExpressionEval.calExp(rule);
					logger.info(bctcChiTiet + ": " + ketQua);
					bctcChiTiet.getGiaTri().setGiaTri(ketQua);
				}
			}
		} else {
			if (bctcChiTiet.getAsset() != null && bctcChiTiet.getAsset().getTaiKhoanDs() != null) {
				if (bctcChiTietDs != null) {
					for (BaoCaoTaiChinhChiTiet bctcChiTietTmpl : bctcChiTietDs) {
						if (bctcChiTiet.equals(bctcChiTietTmpl)) {
							bctcChiTiet.setGiaTri(bctcChiTietTmpl.getGiaTri());
							break;
						}
					}
				}
			}
		}

		if (bctcChiTietDayKyDs != null) {
			for (BaoCaoTaiChinhChiTiet bctcChiTietTmpl : bctcChiTietDayKyDs) {
				if (bctcChiTiet.equals(bctcChiTietTmpl)) {
					bctcChiTiet.setGiaTriKyTruoc(bctcChiTietTmpl.getGiaTri());
					break;
				}
			}
		}

		logger.info("Báo cáo chi tiết: " + bctcChiTiet);

		return bctcChiTiet;
	}

	private BaoCaoTaiChinhCon sinhBangLctt(BaoCaoTaiChinhCon bctcCon) {
		if (bctcCon == null || bctcCon.getBctc() == null || bctcCon.getBctc().getKyKeToan() == null
				|| bctcCon.getBctc().getBatDau() == null || bctcCon.getBctc().getKetThuc() == null) {
			return bctcCon;
		}

		logger.info("Sinh bảng lưu chuyển tiền tệ ...");

		KyKeToan kyKeToan = bctcCon.getBctc().getKyKeToan();
		BaoCaoTaiChinh bctc = bctcCon.getBctc();

		logger.info("Lấy toàn bộ giá trị kỳ trước của các chỉ tiêu cấp thấp nhất.");
		Date ngayTruoc = Utils.moveDate(bctc.getBatDau(), Calendar.DATE, -1);
		List<BaoCaoTaiChinhChiTiet> bctcChiTietDauKyDs = bctcDAO.calculateEndCFBs(kyKeToan.getBatDau(), ngayTruoc);
		for (BaoCaoTaiChinhChiTiet bctcChiTiet : bctcChiTietDauKyDs) {
			bctcChiTiet.setBctc(bctcCon);
		}

		logger.info("Lấy toàn bộ giá trị của các chi tiêu cấp thấp nhất.");
		List<BaoCaoTaiChinhChiTiet> bctcChiTietDs = bctcDAO.calculateEndCFBs(kyKeToan.getBatDau(), bctc.getKetThuc());
		for (BaoCaoTaiChinhChiTiet bctcChiTiet : bctcChiTietDs) {
			bctcChiTiet.setBctc(bctcCon);
		}

		logger.info("Lấy danh sách các chỉ tiêu LCTT");
		List<BalanceAssetItem> bais = balanceSheetDAO.listCFBais();

		List<BaoCaoTaiChinhChiTiet> chiTietDs = new ArrayList<>();
		logger.info("Xây dựng cây BaoCaoTaiChinhChiTiet từ cây BalanceAssetItem");
		for (BalanceAssetItem bai : bais) {
			BaoCaoTaiChinhChiTiet bctcChiTiet = taoBaoCaoTaiChinhChiTiet(bai, bctcCon);
			chiTietDs.add(bctcChiTiet);
		}

		logger.info("Tính giá trị cây BaoCaoTaiChinhChiTiet, sau đó cập nhật vào CSDL");
		for (BaoCaoTaiChinhChiTiet bctcChiTiet : chiTietDs) {
			bctcChiTiet = tinhBangLctt(bctcChiTiet, bctcChiTietDs, bctcChiTietDauKyDs);
		}

		bctcCon.setChiTietDs(chiTietDs);

		return bctcCon;
	}

	private BaoCaoTaiChinhChiTiet tinhBangLctt(BaoCaoTaiChinhChiTiet bctcChiTiet,
			List<BaoCaoTaiChinhChiTiet> bctcChiTietDs, List<BaoCaoTaiChinhChiTiet> bctcChiTietDayKyDs) {
		if (bctcChiTiet == null || bctcChiTietDs == null) {
			return bctcChiTiet;
		}

		if (bctcChiTiet.getChiTietDs() != null && bctcChiTiet.getChiTietDs().size() > 0) {
			for (BaoCaoTaiChinhChiTiet bctcChiTietCon : bctcChiTiet.getChiTietDs()) {
				bctcChiTietCon = tinhBangLctt(bctcChiTietCon, bctcChiTietDs, bctcChiTietDayKyDs);
			}

			// Tính giá trị chỉ tiêu hiện tại từ các chỉ tiêu trước đó
			String rule = bctcChiTiet.getAsset().getRule();
			logger.info(rule);
			if (rule != null && !rule.trim().equals("")) {
				List<String> toanHangDs = ExpressionEval.getOperands(rule);
				if (toanHangDs != null) {
					// Với mỗi toán hạng, đó là một tài khoản kế toán
					// Cần tính giá trị của tài khoản kế toán
					for (String toanHang : toanHangDs) {
						for (BaoCaoTaiChinhChiTiet bctcChiTietCon : bctcChiTiet.getChiTietDs()) {
							if (bctcChiTietCon.getAsset().getAssetCode().equals(toanHang)) {
								String value = bctcChiTietCon.getGiaTri().getGiaTri() + "";
								value = value.replace(ExpressionEval.DAU_AM, ExpressionEval.DAU_AM_TAM_THOI);
								rule = rule.replaceAll(toanHang, value);
								logger.info(toanHang + ": " + bctcChiTietCon.getGiaTri().getGiaTri());
								break;
							}
						}
					}

					double ketQua = ExpressionEval.calExp(rule);
					logger.info(bctcChiTiet + ": " + ketQua);
					bctcChiTiet.getGiaTri().setGiaTri(ketQua);
				}
			}
		} else {
			if (bctcChiTiet.getAsset() != null && bctcChiTiet.getAsset().getAssetCode() != null) {
				if (bctcChiTiet.getAsset().getAssetCode().equals(BalanceAssetItem.LCTT_CT_60)) {
					// Chỉ tiêu này lấy từ chỉ tiêu 110 đầu kỳ của bảng CDKT
					BaoCaoTaiChinhChiTiet bctcChiTietTmpl = new BaoCaoTaiChinhChiTiet();

					BalanceAssetItem baiTmpl = new BalanceAssetItem();
					baiTmpl.setAssetCode(BalanceAssetItem.CDKT_CT_110);

					bctcChiTietTmpl.setAsset(baiTmpl);
					bctcChiTietTmpl.setBctc(bctcChiTiet.getBctc());

					if (bctcChiTietDayKyDs != null) {
						// Lấy 110 kỳ trước tại đây ?, cần xem lại công thức
					}
					// badTmpl = balanceSheetDAO.getPeriodStartValue(badTmpl);
					// bad.setEndValue(badTmpl.getStartValue());
				} else if (bctcChiTiet.getAsset().getTaiKhoanDs() != null) {
					for (BaoCaoTaiChinhChiTiet bctcChiTietTmpl : bctcChiTietDs) {
						if (bctcChiTiet.equals(bctcChiTietTmpl)) {
							bctcChiTiet.setGiaTri(bctcChiTietTmpl.getGiaTri());
							break;
						}
					}
				}
			}
		}

		if (bctcChiTietDayKyDs != null) {
			for (BaoCaoTaiChinhChiTiet bctcChiTietTmpl : bctcChiTietDayKyDs) {
				if (bctcChiTiet.equals(bctcChiTietTmpl)) {
					bctcChiTiet.setGiaTriKyTruoc(bctcChiTietTmpl.getGiaTri());
					break;
				}
			}
		}

		logger.info("Báo cáo chi tiết: " + bctcChiTiet);

		return bctcChiTiet;
	}

	private BaoCaoTaiChinhCon sinhBangCdps(BaoCaoTaiChinhCon bctcCon) {
		if (bctcCon == null || bctcCon.getBctc() == null || bctcCon.getBctc().getKyKeToan() == null
				|| bctcCon.getBctc().getBatDau() == null || bctcCon.getBctc().getKetThuc() == null) {
			return bctcCon;
		}

		logger.info("Sinh bảng cân đối phát sinh");

		KyKeToan kyKeToan = bctcCon.getBctc().getKyKeToan();
		BaoCaoTaiChinh bctc = bctcCon.getBctc();
		List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.cayTaiKhoan();

		DuLieuKeToan duLieuKeToan = bctcService.taoBangCdps(bctc.getBatDau(), bctc.getKetThuc(), loaiTaiKhoanDs,
				kyKeToan);

		return null;
	}

	@RequestMapping("/bctc/xem/{id}")
	public String xemBctc(@PathVariable("id") int maBctc, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			BaoCaoTaiChinh bctc = bctcDAO.layBctc(maBctc);
			if (bctc == null) {
				return "redirect:/bctc/danhsach";
			}

			if (bctc.getBctcDs() != null) {
				for (BaoCaoTaiChinhCon bctcCon : bctc.getBctcDs()) {
					switch (bctcCon.getLoaiBctc()) {
					case BaoCaoTaiChinhCon.LOAI_CDKT:
						bctc.setBangCdkt(bctcCon);
						break;
					case BaoCaoTaiChinhCon.LOAI_KQHDKD:
						bctc.setBangKqhdkd(bctcCon);
						break;
					case BaoCaoTaiChinhCon.LOAI_LCTT:
						bctc.setBangLctt(bctcCon);
						break;
					case BaoCaoTaiChinhCon.LOAI_CDPS:
						bctc.setBangCdps(bctcCon);
						break;
					default:
						break;
					}
				}
			}

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(bctc.getKyKeToan().getMaKyKt());

			model.addAttribute("bctc", bctc);
			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabBCTCDS");
			return "xemBctc";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/bctc/danhsach";
		}
	}

	@RequestMapping(value = "/bctc/cdkt/pdf/{id}", method = RequestMethod.GET)
	public void pdfCdkt(HttpServletRequest req, HttpServletResponse res, @PathVariable("id") int maBctcCon,
			Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			BaoCaoTaiChinhCon bctcCon = bctcDAO.layBctcConCdkt(maBctcCon);
			if (bctcCon == null) {
				return;
			}

			// Sinh bảng cân đối kế toán ra pdf
			HashMap<String, Object> params = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);
			params.put("bctcCon", bctcCon);
			JasperReport jasperReport = ReportUtils.compileReport("BCDKT", "bctc", req);
			byte[] bytes = baoCaoService.taoBangCdkt(jasperReport, params, bctcCon.getChiTietDs());

			ReportUtils.writePdf2Response(bytes, "BangCdkt", res);
		} catch (JRException | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/bctc/kqhdkd/pdf/{id}", method = RequestMethod.GET)
	public void pdfKqhdkd(HttpServletRequest req, HttpServletResponse res, @PathVariable("id") int maBctcCon,
			Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			BaoCaoTaiChinhCon bctcCon = bctcDAO.layBctcConKqhdkd(maBctcCon);
			if (bctcCon == null) {
				return;
			}

			// Sinh bảng cân đối kế toán ra pdf
			HashMap<String, Object> params = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);
			params.put("bctcCon", bctcCon);
			JasperReport jasperReport = ReportUtils.compileReport("BKQHDKD", "bctc", req);
			byte[] bytes = baoCaoService.taoBangCdkt(jasperReport, params, bctcCon.getChiTietDs());

			ReportUtils.writePdf2Response(bytes, "BangKqhdkd", res);
		} catch (JRException | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/bctc/luuchuyentt/pdf/{id}", method = RequestMethod.GET)
	public void pdfLuuchuyentt(HttpServletRequest req, HttpServletResponse res, @PathVariable("id") int maBctcCon,
			Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			BaoCaoTaiChinhCon bctcCon = bctcDAO.layBctcConLctt(maBctcCon);
			if (bctcCon == null) {
				return;
			}

			// Sinh bảng cân đối kế toán ra pdf
			HashMap<String, Object> params = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);
			params.put("bctcCon", bctcCon);
			JasperReport jasperReport = ReportUtils.compileReport("BLCTT", "bctc", req);
			byte[] bytes = baoCaoService.taoBangCdkt(jasperReport, params, bctcCon.getChiTietDs());

			ReportUtils.writePdf2Response(bytes, "BangLctt", res);
		} catch (JRException | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/bctc/xoa/{id}")
	public String xoaBctc(@PathVariable("id") int maBctc, Model model) {
		logger.info("Xóa báo cáo tài chính có MA_BCTC: " + maBctc);
		try {
			bctcDAO.xoaBctc(maBctc);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/bctc/danhsach";
	}
}
