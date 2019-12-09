package com.idi.finance.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.idi.finance.bean.CauHinh;
import com.idi.finance.bean.DungChung;
import com.idi.finance.bean.Tien;
import com.idi.finance.bean.bctc.BalanceAssetData;
import com.idi.finance.bean.bctc.BalanceAssetItem;
import com.idi.finance.bean.bctc.DuLieuKeToan;
import com.idi.finance.bean.bctc.KyKeToanCon;
import com.idi.finance.bean.bieudo.KpiGroup;
import com.idi.finance.bean.kyketoan.KyKeToan;
import com.idi.finance.bean.kyketoan.SoDuKy;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.bean.taikhoan.TaiKhoan;
import com.idi.finance.dao.BalanceSheetDAO;
import com.idi.finance.dao.BaoCaoDAO;
import com.idi.finance.dao.KyKeToanDAO;
import com.idi.finance.dao.SoKeToanDAO;
import com.idi.finance.dao.TaiKhoanDAO;
import com.idi.finance.form.BalanceAssetForm;
import com.idi.finance.form.TkSoKeToanForm;
import com.idi.finance.hangso.PropCont;
import com.idi.finance.utils.ExcelProcessor;
import com.idi.finance.utils.ExpressionEval;
import com.idi.finance.utils.Utils;
import com.idi.finance.validator.BalanceSheetValidator;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
public class BalanceSheetController {
	private static final Logger logger = Logger.getLogger(BalanceSheetController.class);

	@Autowired
	DungChung dungChung;

	@Autowired
	PropCont props;

	@Autowired
	BalanceSheetDAO balanceSheetDAO;

	@Autowired
	TaiKhoanDAO taiKhoanDAO;

	@Autowired
	BaoCaoDAO baoCaoDAO;

	@Autowired
	SoKeToanDAO soKeToanDAO;

	@Autowired
	KyKeToanDAO kyKeToanDAO;

	@Autowired
	private BalanceSheetValidator balanceSheetValidator;

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

		if (target.getClass() == BalanceAssetItem.class) {
			binder.setValidator(balanceSheetValidator);
		}
	}

	@RequestMapping("/bctc/cdkt/danhsach")
	public String balanceAssets(@ModelAttribute("mainFinanceForm") BalanceAssetForm form, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			// List balance assets:
			if (form == null)
				form = new BalanceAssetForm();

			// Lấy kỳ kế toán mặc định
			if (form.getKyKeToan() == null) {
				form.setKyKeToan(dungChung.getKyKeToan());
			} else {
				form.setKyKeToan(kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt()));
			}

			// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của kỳ
			if (form.getDau() == null) {
				form.setDau(form.getKyKeToan().getBatDau());
			}

			if (form.getCuoi() == null) {
				form.setCuoi(form.getKyKeToan().getKetThuc());
			}

			form.setPeriodType(KyKeToanCon.MONTH);

			List<Date> selectedAssetPeriods = null;
			if (form.getAssetPeriods() != null) {
				selectedAssetPeriods = Utils.convertArray2List(form.getAssetPeriods());
			} else if (form.isFirst()) {
				// Đây là lần đầu vào tab, không phải do ấn nút form tìm kiếm,
				// sẽ lấy dữ liệu tháng hiện tại
				selectedAssetPeriods = new ArrayList<>();
				Date period = Utils.getStartPeriod(new Date(), form.getPeriodType());
				selectedAssetPeriods.add(period);
				form.setFirst(false);

				// Chuyển tháng từ List Date sang Array String
				form.setAssetPeriods(Utils.convertList2Array(selectedAssetPeriods));
			}

			// AssetCode
			List<String> selectedAssetCodes = null;
			if (form.getAssetCodes() != null) {
				selectedAssetCodes = Arrays.asList(form.getAssetCodes());
			}

			List<BalanceAssetData> bads = balanceSheetDAO.listBAsByAssetCodesAndDates(selectedAssetCodes,
					selectedAssetPeriods, form.getPeriodType());
			List<String> assetCodes = balanceSheetDAO.listBSAssetsCodes();
			List<Date> assetPeriods = Utils.listStartPeriods(form.getDau(), form.getCuoi(), form.getPeriodType());

			// Paging:
			// Number records of a Page: Default: 25
			// Page Index: Default: 1
			// Total records
			// Total of page
			if (form.getNumberRecordsOfPage() == 0) {
				form.setNumberRecordsOfPage(25);
			}
			int numberRecordsOfPage = form.getNumberRecordsOfPage();

			if (form.getPageIndex() == 0) {
				form.setPageIndex(1);
			}
			int pageIndex = form.getPageIndex();
			form.setPageIndex(pageIndex);

			int totalRecords = bads == null ? 0 : bads.size();
			form.setTotalRecords(totalRecords);

			int totalPages = totalRecords % numberRecordsOfPage > 0 ? totalRecords / numberRecordsOfPage + 1
					: totalRecords / numberRecordsOfPage;
			form.setTotalPages(totalPages);

			List<BalanceAssetData> pagingBads = new ArrayList<>();
			int max = pageIndex * numberRecordsOfPage < bads.size() ? pageIndex * numberRecordsOfPage : bads.size();
			for (int i = (pageIndex - 1) * numberRecordsOfPage; i < max; i++) {
				pagingBads.add(bads.get(i));
			}

			model.addAttribute("pageIndex", pageIndex);
			model.addAttribute("numberRecordsOfPage", numberRecordsOfPage);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("totalRecords", totalRecords);

			model.addAttribute("bads", pagingBads);
			model.addAttribute("assetCodes", assetCodes);
			model.addAttribute("assetPeriods", assetPeriods);
			model.addAttribute("kyKeToanDs", kyKeToanDAO.danhSachKyKeToan());
			model.addAttribute("tab", "tabBCDKT");

			return "balanceAsset";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/bctc/cdkt/capnhat")
	public String showUpdateBS(@ModelAttribute("mainFinanceForm") BalanceAssetForm form, Model model) {
		try {
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			if (form == null)
				form = new BalanceAssetForm();

			// Lấy kỳ kế toán mặc định
			if (form.getKyKeToan() == null) {
				form.setKyKeToan(dungChung.getKyKeToan());
			} else {
				form.setKyKeToan(kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt()));
			}

			// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của kỳ
			if (form.getDau() == null) {
				form.setDau(form.getKyKeToan().getBatDau());
			}

			if (form.getCuoi() == null) {
				form.setCuoi(form.getKyKeToan().getKetThuc());
			}

			form.setPeriodType(KyKeToanCon.MONTH);

			List<Date> assetPeriods = Utils.listStartPeriods(form.getDau(), form.getCuoi(), form.getPeriodType());

			model.addAttribute("assetPeriods", assetPeriods);
			model.addAttribute("tab", "tabBCDKT");

			return "showUpdateBS";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/bctc/cdkt/capnhat/luu")
	public String updateBS(@ModelAttribute("mainFinanceForm") BalanceAssetForm form, Model model) {
		try {
			if (form == null)
				form = new BalanceAssetForm();

			// Lấy kỳ kế toán mặc định
			if (form.getKyKeToan() == null) {
				form.setKyKeToan(dungChung.getKyKeToan());
			} else {
				form.setKyKeToan(kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt()));
			}

			// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của kỳ
			if (form.getDau() == null) {
				form.setDau(form.getKyKeToan().getBatDau());
			}

			if (form.getCuoi() == null) {
				form.setCuoi(form.getKyKeToan().getKetThuc());
			}

			form.setPeriodType(KyKeToanCon.MONTH);

			logger.info("Lấy danh sách các chỉ tiêu CDKT");
			List<BalanceAssetItem> bais = balanceSheetDAO.listBais();

			try {
				Date ngayDuocChon = new Date();
				SimpleDateFormat sdfTmpl = new SimpleDateFormat("dd/M/yyyy");
				if (form.getAssetPeriods() == null || form.getAssetPeriods().length == 0) {
					ngayDuocChon = Utils.getStartPeriod(ngayDuocChon, form.getPeriodType());

					String[] assetPeriods = { sdfTmpl.format(ngayDuocChon) };
					form.setAssetPeriods(assetPeriods);
				} else {
					ngayDuocChon = sdfTmpl.parse(form.getAssetPeriods()[0]);
				}

				KyKeToanCon kyKeToanCon = new KyKeToanCon(ngayDuocChon, form.getPeriodType());
				KyKeToanCon kyKeToanConTruoc = Utils.prevPeriod(kyKeToanCon);

				Date cuoiKy = kyKeToanCon.getCuoi();
				if (cuoiKy.after(form.getCuoi())) {
					cuoiKy = form.getCuoi();
				}
				Date cuoiKyTruoc = kyKeToanConTruoc.getCuoi();

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
				String ketThuc = sdf.format(cuoiKy);
				logger.info("Sinh bảng cân đối kế toán đến " + ketThuc);

				logger.info("Xây dựng cây BalanceAssetData từ cây BalanceAssetItem");
				List<BalanceAssetData> bads = new ArrayList<>();
				Iterator<BalanceAssetItem> baiIter = bais.iterator();
				while (baiIter.hasNext()) {
					BalanceAssetItem bai = baiIter.next();
					BalanceAssetData bad = createBad(bai, form.getPeriodType(), kyKeToanCon.getDau());
					bads.add(bad);
				}

				List<BalanceAssetData> allPrevBads = null;
				if (Utils.getStartPeriod(form.getDau(), form.getPeriodType()).equals(kyKeToanCon.getDau())) {
					// Nếu là kỳ (tháng) đầu tiên, thì tính lại bảng CĐKT kỳ (tháng trước) trước từ
					// số dư tài khoản đầu kỳ
					logger.info("Lấy toàn bộ giá trị kỳ trước của các chỉ tiêu cấp thấp nhất.");
					allPrevBads = balanceSheetDAO.calculateEndBs(form.getDau(), cuoiKyTruoc,
							form.getKyKeToan().getMaKyKt());
					Iterator<BalanceAssetData> prevIter = allPrevBads.iterator();
					while (prevIter.hasNext()) {
						BalanceAssetData balanceAssetData = prevIter.next();
						balanceAssetData.setPeriodType(form.getPeriodType());
						balanceAssetData.setPeriod(kyKeToanConTruoc.getDau());
					}
				} else {
					if (balanceSheetDAO.kiemTraBs(kyKeToanConTruoc.getDau(), kyKeToanConTruoc.getLoai())) {
						// Kỳ con trước đã được sinh bảng cân đối kế toán
						// Lấy dữ liệu từ kỳ trước làm đầu kỳ này
						List<Date> assetPeriods = new ArrayList<>();
						assetPeriods.add(kyKeToanConTruoc.getDau());
						allPrevBads = balanceSheetDAO.listBAsByAssetCodesAndDates(null, assetPeriods,
								form.getPeriodType());
					} else {
						// Kỳ trước chưa được sinh bảng cân đối kế toán
						// Bỏ qua, đặt bằng 0 hết, kế toán phải tự sinh lại cả 2 bảng cdkt
						allPrevBads = new ArrayList<>();
					}
				}

				logger.info("Lấy toàn bộ giá trị kỳ này của các chỉ tiêu cấp thấp nhất.");
				List<BalanceAssetData> allBads = balanceSheetDAO.calculateEndBs(form.getKyKeToan().getBatDau(), cuoiKy,
						form.getKyKeToan().getMaKyKt());
				Iterator<BalanceAssetData> iter = allBads.iterator();
				while (iter.hasNext()) {
					BalanceAssetData balanceAssetData = iter.next();
					balanceAssetData.setPeriodType(form.getPeriodType());
					balanceAssetData.setPeriod(kyKeToanCon.getDau());
				}

				logger.info("Tính giá trị cây BalanceAssetData, sau đó cập nhật vào CSDL");
				Iterator<BalanceAssetData> badIter = bads.iterator();
				while (badIter.hasNext()) {
					BalanceAssetData bad = badIter.next();
					bad = calCulcateInBs(bad, allBads, allPrevBads);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return "redirect:/bctc/cdkt/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/bctc/cdkt/xuat", method = RequestMethod.POST)
	public void exportBalanceAsset(HttpServletRequest req, HttpServletResponse res,
			@ModelAttribute("mainFinanceForm") BalanceAssetForm form, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			// List balance assets:
			if (form == null)
				form = new BalanceAssetForm();

			// Lấy kỳ kế toán mặc định
			if (form.getKyKeToan() == null) {
				form.setKyKeToan(dungChung.getKyKeToan());
			} else {
				form.setKyKeToan(kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt()));
			}

			// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của kỳ
			if (form.getDau() == null) {
				form.setDau(form.getKyKeToan().getBatDau());
			}

			if (form.getCuoi() == null) {
				form.setCuoi(form.getKyKeToan().getKetThuc());
			}

			form.setPeriodType(KyKeToanCon.MONTH);

			List<Date> selectedAssetPeriods = null;
			if (form.getAssetPeriods() != null) {
				selectedAssetPeriods = Utils.convertArray2List(form.getAssetPeriods());
			} else if (form.isFirst()) {
				// Đây là lần đầu vào tab, không phải do ấn nút form tìm kiếm,
				// sẽ lấy dữ liệu tháng hiện tại
				selectedAssetPeriods = new ArrayList<>();
				Date period = Utils.getStartPeriod(new Date(), form.getPeriodType());
				selectedAssetPeriods.add(period);
				form.setFirst(false);

				// Chuyển tháng từ List Date sang Array String
				form.setAssetPeriods(Utils.convertList2Array(selectedAssetPeriods));
			}

			// AssetCode
			List<String> selectedAssetCodes = null;
			if (form.getAssetCodes() != null) {
				selectedAssetCodes = Arrays.asList(form.getAssetCodes());
			}

			List<BalanceAssetData> bads = balanceSheetDAO.listBAsByAssetCodesAndDates(selectedAssetCodes,
					selectedAssetPeriods, form.getPeriodType());

			// Sinh bảng cân đối kế toán ra pdf
			HashMap<String, Object> params = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);
			JasperReport jasperReport = getCompiledFile("CDKT", req);
			byte[] bytes = baoCaoDAO.taoBangCdkt(jasperReport, params, bads);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			res.setHeader("Content-disposition", "inline; filename=BangCdkt.pdf");
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private BalanceAssetData createBad(BalanceAssetItem bai, int periodType, Date period) {
		if (bai == null)
			return null;

		BalanceAssetData bad = new BalanceAssetData();
		bad.setAsset(bai);
		bad.setPeriodType(periodType);
		bad.setPeriod(period);

		if (bai.getChilds() != null) {
			Iterator<BalanceAssetItem> iter = bai.getChilds().iterator();
			while (iter.hasNext()) {
				BalanceAssetItem childBai = iter.next();
				BalanceAssetData childBad = createBad(childBai, periodType, period);
				bad.addChild(childBad);
			}
		}

		return bad;
	}

	private BalanceAssetData calCulcateInBs(BalanceAssetData bad, List<BalanceAssetData> allBads,
			List<BalanceAssetData> allPrevBads) {
		if (bad == null)
			return null;

		if (bad.getChilds() != null && bad.getChilds().size() > 0) {
			Iterator<BalanceAssetData> iter = bad.getChilds().iterator();
			while (iter.hasNext()) {
				BalanceAssetData childBad = iter.next();
				childBad = calCulcateInBs(childBad, allBads, allPrevBads);
				bad.setEndValue(bad.getEndValue() + childBad.getEndValue());
			}
		} else {
			if (bad.getAsset() != null) {
				if (allBads != null) {
					Iterator<BalanceAssetData> iter = allBads.iterator();
					while (iter.hasNext()) {
						BalanceAssetData balanceAssetData = iter.next();

						if (balanceAssetData.equals(bad)) {
							bad.setEndValue(balanceAssetData.getEndValue());
						}
					}
				}
			}
		}

		if (allPrevBads != null) {
			Iterator<BalanceAssetData> prevIter = allPrevBads.iterator();
			while (prevIter.hasNext()) {
				BalanceAssetData balanceAssetData = prevIter.next();

				if (balanceAssetData.equals(bad)) {
					bad.setStartValue(balanceAssetData.getEndValue());
				}
			}
		}

		logger.info(bad);

		// Cập nhật vào cơ sở dữ liệu
		balanceSheetDAO.insertOrUpdateBA(bad);

		return bad;
	}

	@RequestMapping("/bctc/kqhdkd/danhsach")
	public String saleResults(@ModelAttribute("mainFinanceForm") BalanceAssetForm form, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			// List balance assets:
			if (form == null)
				form = new BalanceAssetForm();

			// Lấy kỳ kế toán mặc định
			if (form.getKyKeToan() == null) {
				form.setKyKeToan(dungChung.getKyKeToan());
			} else {
				form.setKyKeToan(kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt()));
			}

			// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của kỳ
			if (form.getDau() == null) {
				form.setDau(form.getKyKeToan().getBatDau());
			}

			if (form.getCuoi() == null) {
				form.setCuoi(form.getKyKeToan().getKetThuc());
			}

			form.setPeriodType(KyKeToanCon.MONTH);

			List<Date> selectedAssetPeriods = null;
			if (form.getAssetPeriods() != null) {
				selectedAssetPeriods = Utils.convertArray2List(form.getAssetPeriods());
			} else if (form.isFirst()) {
				// Đây là lần đầu vào tab, không phải do ấn nút form tìm kiếm,
				// sẽ lấy dữ liệu tháng hiện tại
				selectedAssetPeriods = new ArrayList<>();
				Date period = Utils.getStartPeriod(new Date(), form.getPeriodType());
				selectedAssetPeriods.add(period);
				form.setFirst(false);

				// Chuyển tháng từ List Date sang Array String
				form.setAssetPeriods(Utils.convertList2Array(selectedAssetPeriods));
			}

			// AssetCode
			List<String> selectedAssetCodes = null;
			if (form.getAssetCodes() != null) {
				selectedAssetCodes = Arrays.asList(form.getAssetCodes());
			}

			List<BalanceAssetData> bads = balanceSheetDAO.listSRsByAssetCodesAndDates(selectedAssetCodes,
					selectedAssetPeriods, form.getPeriodType());
			List<String> assetCodes = balanceSheetDAO.listSRAssetsCodes();
			List<Date> assetPeriods = Utils.listStartPeriods(form.getDau(), form.getCuoi(), form.getPeriodType());

			// Paging:
			// Number records of a Page: Default: 25
			// Page Index: Default: 1
			// Total records
			// Total of page
			if (form.getNumberRecordsOfPage() == 0) {
				form.setNumberRecordsOfPage(25);
			}
			int numberRecordsOfPage = form.getNumberRecordsOfPage();

			if (form.getPageIndex() == 0) {
				form.setPageIndex(1);
			}
			int pageIndex = form.getPageIndex();
			form.setPageIndex(pageIndex);

			int totalRecords = bads == null ? 0 : bads.size();
			form.setTotalRecords(totalRecords);

			int totalPages = totalRecords % numberRecordsOfPage > 0 ? totalRecords / numberRecordsOfPage + 1
					: totalRecords / numberRecordsOfPage;
			form.setTotalPages(totalPages);

			List<BalanceAssetData> pagingBads = new ArrayList<>();
			int max = pageIndex * numberRecordsOfPage < bads.size() ? pageIndex * numberRecordsOfPage : bads.size();
			for (int i = (pageIndex - 1) * numberRecordsOfPage; i < max; i++) {
				pagingBads.add(bads.get(i));
			}

			model.addAttribute("pageIndex", pageIndex);
			model.addAttribute("numberRecordsOfPage", numberRecordsOfPage);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("totalRecords", totalRecords);

			model.addAttribute("bads", pagingBads);
			model.addAttribute("assetCodes", assetCodes);
			model.addAttribute("assetPeriods", assetPeriods);
			model.addAttribute("kyKeToanDs", kyKeToanDAO.danhSachKyKeToan());
			model.addAttribute("tab", "tabBKQHDKD");

			return "saleResult";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/bctc/kqhdkd/xuat", method = RequestMethod.POST)
	public void exportsaleResults(HttpServletRequest req, HttpServletResponse res,
			@ModelAttribute("mainFinanceForm") BalanceAssetForm form, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			// List balance assets:
			if (form == null)
				form = new BalanceAssetForm();

			// Lấy kỳ kế toán mặc định
			if (form.getKyKeToan() == null) {
				form.setKyKeToan(dungChung.getKyKeToan());
			} else {
				form.setKyKeToan(kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt()));
			}

			// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của kỳ
			if (form.getDau() == null) {
				form.setDau(form.getKyKeToan().getBatDau());
			}

			if (form.getCuoi() == null) {
				form.setCuoi(form.getKyKeToan().getKetThuc());
			}

			form.setPeriodType(KyKeToanCon.MONTH);

			List<Date> selectedAssetPeriods = null;
			if (form.getAssetPeriods() != null) {
				selectedAssetPeriods = Utils.convertArray2List(form.getAssetPeriods());
			} else if (form.isFirst()) {
				// Đây là lần đầu vào tab, không phải do ấn nút form tìm kiếm,
				// sẽ lấy dữ liệu tháng hiện tại
				selectedAssetPeriods = new ArrayList<>();
				Date period = Utils.getStartPeriod(new Date(), form.getPeriodType());
				selectedAssetPeriods.add(period);
				form.setFirst(false);

				// Chuyển tháng từ List Date sang Array String
				form.setAssetPeriods(Utils.convertList2Array(selectedAssetPeriods));
			}

			// AssetCode
			List<String> selectedAssetCodes = null;
			if (form.getAssetCodes() != null) {
				selectedAssetCodes = Arrays.asList(form.getAssetCodes());
			}

			List<BalanceAssetData> bads = balanceSheetDAO.listSRsByAssetCodesAndDates(selectedAssetCodes,
					selectedAssetPeriods, form.getPeriodType());

			// Sinh bảng cân đối kế toán ra pdf
			HashMap<String, Object> params = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);
			JasperReport jasperReport = getCompiledFile("KQHDKD", req);
			byte[] bytes = baoCaoDAO.taoBangCdkt(jasperReport, params, bads);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			res.setHeader("Content-disposition", "inline; filename=BangKqhdkd.pdf");
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/bctc/kqhdkd/capnhat")
	public String showUpdateSR(@ModelAttribute("mainFinanceForm") BalanceAssetForm form, Model model) {
		try {
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			if (form == null)
				form = new BalanceAssetForm();

			// Lấy kỳ kế toán mặc định
			if (form.getKyKeToan() == null) {
				form.setKyKeToan(dungChung.getKyKeToan());
			} else {
				form.setKyKeToan(kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt()));
			}

			// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của kỳ
			if (form.getDau() == null) {
				form.setDau(form.getKyKeToan().getBatDau());
			}

			if (form.getCuoi() == null) {
				form.setCuoi(form.getKyKeToan().getKetThuc());
			}

			form.setPeriodType(KyKeToanCon.MONTH);

			List<Date> assetPeriods = Utils.listStartPeriods(form.getDau(), form.getCuoi(), form.getPeriodType());

			model.addAttribute("assetPeriods", assetPeriods);
			model.addAttribute("tab", "tabBKQHDKD");

			return "showUpdateSR";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/bctc/kqhdkd/capnhat/luu")
	public String updateSR(@ModelAttribute("mainFinanceForm") BalanceAssetForm form, Model model) {
		try {
			// Lấy kỳ kế toán mặc định
			if (form.getKyKeToan() == null) {
				form.setKyKeToan(dungChung.getKyKeToan());
			} else {
				form.setKyKeToan(kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt()));
			}

			// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của kỳ
			if (form.getDau() == null) {
				form.setDau(form.getKyKeToan().getBatDau());
			}

			if (form.getCuoi() == null) {
				form.setCuoi(form.getKyKeToan().getKetThuc());
			}

			form.setPeriodType(KyKeToanCon.MONTH);

			logger.info("Lấy danh sách các chỉ tiêu KQHDKD");
			List<BalanceAssetItem> bais = balanceSheetDAO.listSRBais();

			logger.info("Cập nhật các chi tiêu KQHDKD cho tất cả các loại kỳ trong khoảng thời gian: " + form.getDau()
					+ " - " + form.getCuoi());
			HashMap<Integer, String> kyDs = new HashMap<>();

			// kyDs.put(KyKeToanCon.WEEK, "Tuần");
			kyDs.put(KyKeToanCon.MONTH, "Tháng");
			// kyDs.put(KyKeToanCon.QUARTER, "Quý");
			kyDs.put(KyKeToanCon.YEAR, "Năm");

			Iterator<Integer> kyIter = kyDs.keySet().iterator();
			while (kyIter.hasNext()) {
				Integer ky = kyIter.next();
				logger.info("Cập nhật chi tiêu KQHDKD cho loại kỳ: " + kyDs.get(ky));
				try {
					Date dauKy = Utils.getStartPeriod(form.getDau(), ky);
					Date cuoiKy = Utils.getEndPeriod(form.getCuoi(), ky);
					Date buocNhay = dauKy;

					while (buocNhay.before(cuoiKy)) {
						Date cuoi = Utils.getEndPeriod(buocNhay, ky);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
						String batDau = sdf.format(buocNhay);
						String ketThuc = sdf.format(cuoi);

						logger.info("Kỳ " + batDau + " => " + ketThuc);

						logger.info("Xây dựng cây SaleResultData từ cây SaleResultItem");
						List<BalanceAssetData> bads = new ArrayList<>();
						Iterator<BalanceAssetItem> baiIter = bais.iterator();
						while (baiIter.hasNext()) {
							BalanceAssetItem bai = baiIter.next();
							BalanceAssetData bad = createBad(bai, ky, buocNhay);
							bads.add(bad);
						}

						logger.info("Lấy toàn bộ giá trị của các chi tiêu cấp thấp nhất.");
						List<BalanceAssetData> allBads = balanceSheetDAO.calculateSRBs(buocNhay, cuoi);

						logger.info("Tính giá trị cây SaleResultData theo từng kỳ, sau đó cập nhật vào CSDL");
						Iterator<BalanceAssetData> badIter = bads.iterator();
						while (badIter.hasNext()) {
							BalanceAssetData bad = badIter.next();
							bad = calCulcateSRBs(bad, allBads);
						}

						buocNhay = Utils.nextPeriod(buocNhay, ky);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return "redirect:/bctc/kqhdkd/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private BalanceAssetData calCulcateSRBs(BalanceAssetData bad, List<BalanceAssetData> allBads) {
		if (bad == null)
			return null;

		try {
			Date previousPeriod = Utils.prevPeriod(bad.getPeriod(), bad.getPeriodType());

			BalanceAssetData badTmpl = new BalanceAssetData();
			badTmpl.setAsset(bad.getAsset());
			badTmpl.setPeriod(previousPeriod);
			badTmpl.setPeriodType(bad.getPeriodType());

			badTmpl = balanceSheetDAO.getSRPeriodEndValue(badTmpl);

			// Lấy giá trị kỳ trước làm giá trị đầu kỳ
			bad.setStartValue(badTmpl.getEndValue());
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info(bad);

		// Tính giá trị cuối kỳ
		if (bad.getChilds() != null && bad.getChilds().size() > 0) {
			logger.info("Tính giá trị chỉ tiêu " + bad.getAsset().getAssetCode() + " từ chỉ tiêu con");
			Iterator<BalanceAssetData> iter = bad.getChilds().iterator();
			while (iter.hasNext()) {
				BalanceAssetData childBad = iter.next();
				childBad = calCulcateSRBs(childBad, allBads);
			}

			// Tính giá trị chỉ tiêu hiện tại từ các chỉ tiêu trước đó
			String rule = bad.getAsset().getRule();
			logger.info(rule);
			if (rule != null && !rule.trim().equals("")) {
				List<String> toanHangDs = ExpressionEval.getOperands(rule);
				if (toanHangDs != null) {
					// Với mỗi toán hạng, đó là một tài khoản kết toán
					// Cần tính giá trị của tài khoản kế toán
					Iterator<String> toanHangIter = toanHangDs.iterator();
					while (toanHangIter.hasNext()) {
						String toanHang = toanHangIter.next();

						iter = bad.getChilds().iterator();
						while (iter.hasNext()) {
							BalanceAssetData childBad = iter.next();

							if (childBad.getAsset().getAssetCode().equals(toanHang)) {
								String value = childBad.getEndValue() + "";
								value = value.replace(ExpressionEval.DAU_AM, ExpressionEval.DAU_AM_TAM_THOI);
								rule = rule.replaceAll(toanHang, value);
								logger.info(toanHang + ": " + childBad.getEndValue());
								break;
							}
						}
					}

					double ketQua = ExpressionEval.calExp(rule);
					logger.info(bad + ": " + ketQua);
					bad.setEndValue(ketQua);
				}
			}
		} else {
			if (bad.getAsset() != null && bad.getAsset().getTaiKhoanDs() != null) {
				Iterator<BalanceAssetData> iter = allBads.iterator();
				while (iter.hasNext()) {
					BalanceAssetData balanceAssetData = iter.next();
					balanceAssetData.setPeriodType(bad.getPeriodType());
					balanceAssetData.setPeriod(bad.getPeriod());

					if (balanceAssetData.equals(bad)) {
						bad.setEndValue(balanceAssetData.getEndValue());
					}
				}
			}
		}

		// Cập nhật vào cơ sở dữ liệu
		balanceSheetDAO.insertOrUpdateSR(bad);

		return bad;
	}

	@RequestMapping("/bctc/cdkt/chitieu/danhsach")
	public String balanceSheetCodes(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			List<BalanceAssetItem> bais = balanceSheetDAO.listBais();

			model.addAttribute("bais", bais);
			model.addAttribute("props", props);
			model.addAttribute("tab", "tabDSBCDKT");

			return "balanceSheetCodes";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/bctc/cdkt/chitieu/capcao/taomoi")
	public String taoMoiHighBalanceSheetCode(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/bctc/cdkt/chitieu/danhsach";
			}

			BalanceAssetItem bai = new BalanceAssetItem();

			return chuanBiFormHighBalanceSheetCode(model, bai);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormHighBalanceSheetCode(Model model, BalanceAssetItem bai) {
		try {
			model.addAttribute("props", props);
			model.addAttribute("mainFinanceForm", bai);
			bai.setType(BalanceAssetItem.BCTC_CDKT_CAO);

			// Lấy danh sách chỉ tiêu CDKT
			List<BalanceAssetItem> baiDs = balanceSheetDAO.listBais();
			List<BalanceAssetItem> baiDsRs = new ArrayList<>();

			if (baiDs != null) {
				// Lấy danh sách chỉ tiêu CDKT cấp cao
				Iterator<BalanceAssetItem> baiIter = baiDs.iterator();
				while (baiIter.hasNext()) {
					baiDsRs.addAll(lineUpHighestBai(baiIter.next()));
				}
			}

			model.addAttribute("baiDs", baiDsRs);
			model.addAttribute("tab", "tabDSBCDKT");

			// Đây là trường hợp tạo mới CT CĐKT & TK
			return "taoMoiHighBalanceSheetCode";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private List<BalanceAssetItem> lineUpHighestBai(BalanceAssetItem bai) {
		if (bai == null) {
			return null;
		}

		logger.info("bai: " + bai);
		List<BalanceAssetItem> baiDsRs = new ArrayList<>();

		if (bai.getTaiKhoanDs() == null || bai.getTaiKhoanDs().size() == 0) {
			baiDsRs.add(bai);
		}

		if (bai.getChilds() != null) {
			Iterator<BalanceAssetItem> iter = bai.getChilds().iterator();
			while (iter.hasNext()) {
				baiDsRs.addAll(lineUpHighestBai(iter.next()));
			}
		}

		return baiDsRs;
	}

	@RequestMapping(value = "/bctc/cdkt/chitieu/capcao/them", method = RequestMethod.POST)
	public String themHighBalanceSheetCode(@ModelAttribute("mainFinanceForm") @Validated BalanceAssetItem bai,
			BindingResult result, Model model) {
		try {
			if (result.hasErrors()) {
				return chuanBiFormHighBalanceSheetCode(model, bai);
			}

			logger.info("Thêm chỉ tiêu cân đối kế toán: " + bai);
			int count = balanceSheetDAO.insertBSHighBai(bai);
			if (count == -1) {
				result.rejectValue("assetCode", "NotEmpty.Bai.Duplicate");
				return chuanBiFormLowBalanceSheetCode(model, bai);
			}

			return "redirect:/bctc/cdkt/chitieu/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/bctc/cdkt/chitieu/capthap/taomoi")
	public String taoMoiLowBalanceSheetCode(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/bctc/cdkt/chitieu/danhsach";
			}

			BalanceAssetItem bai = new BalanceAssetItem();

			return chuanBiFormLowBalanceSheetCode(model, bai);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/bctc/cdkt/chitieu/capthap/them", method = RequestMethod.POST)
	public String themLowBalanceSheetCode(@ModelAttribute("mainFinanceForm") @Validated BalanceAssetItem bai,
			BindingResult result, Model model) {
		try {
			if (result.hasErrors()) {
				return chuanBiFormLowBalanceSheetCode(model, bai);
			}

			logger.info("Thêm chỉ tiêu cân đối kế toán: " + bai);
			int count = balanceSheetDAO.insertBSLowBai(bai);
			if (count == -1) {
				result.rejectValue("assetCode", "NotEmpty.Bai.Duplicate");
				return chuanBiFormLowBalanceSheetCode(model, bai);
			}

			return "redirect:/bctc/cdkt/chitieu/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormLowBalanceSheetCode(Model model, BalanceAssetItem bai) {
		try {
			model.addAttribute("props", props);
			model.addAttribute("mainFinanceForm", bai);
			bai.setType(BalanceAssetItem.BCTC_CDKT_THAP);

			// Lấy danh sách chỉ tiêu CDKT
			List<BalanceAssetItem> baiDs = balanceSheetDAO.listBais();
			List<BalanceAssetItem> baiDsRs = new ArrayList<>();

			if (baiDs != null) {
				// Lấy danh sách chỉ tiêu CDKT cấp thấp nhất
				Iterator<BalanceAssetItem> baiIter = baiDs.iterator();
				while (baiIter.hasNext()) {
					baiDsRs.addAll(lineUpLowestBai(baiIter.next()));
				}
			}

			// Lấy danh sách tài khoản kế toán
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();

			model.addAttribute("baiDs", baiDsRs);
			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);
			model.addAttribute("tab", "tabDSBCDKT");

			// Đây là trường hợp tạo mới CT CĐKT & TK
			return "taoMoiLowBalanceSheetCode";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private List<BalanceAssetItem> lineUpLowestBai(BalanceAssetItem bai) {
		if (bai == null) {
			return null;
		}

		logger.info("bai: " + bai);
		List<BalanceAssetItem> baiDsRs = new ArrayList<>();

		if (bai.getChilds() != null) {
			Iterator<BalanceAssetItem> iter = bai.getChilds().iterator();
			while (iter.hasNext()) {
				baiDsRs.addAll(lineUpLowestBai(iter.next()));
			}
		} else {
			baiDsRs.add(bai);
		}

		return baiDsRs;
	}

	@RequestMapping(value = "/bctc/cdkt/chitieu/xoa/cao", method = RequestMethod.POST)
	public @ResponseBody BalanceAssetItem xoaBalanceAssetItemHigh(@RequestBody BalanceAssetItem bai) {
		logger.info("Xoá BalanceAssetItem từ BALANCE_ASSET_ITEM: assetCode: " + bai.getAssetCode());

		balanceSheetDAO.deleteBSBaiHigh(bai);

		return bai;
	}

	@RequestMapping(value = "/bctc/cdkt/chitieu/xoa/thap", method = RequestMethod.POST)
	public @ResponseBody BalanceAssetItem xoaBalanceAssetItemLow(@RequestBody BalanceAssetItem bai) {
		logger.info("Xoá BalanceAssetItem từ CDKT_TAIKHOAN: assetCode: " + bai.getAssetCode() + ". maTk: "
				+ bai.getTaiKhoanDs().get(0).getMaTk());

		balanceSheetDAO.deleteBSBaiLow(bai);

		return bai;
	}

	@RequestMapping(value = "/bctc/cdkt/chitieu/capnhat/cao", method = RequestMethod.POST)
	public @ResponseBody BalanceAssetItem luuBalanceAssetItemHigh(@RequestBody BalanceAssetItem bai) {
		logger.info("assetCode: " + bai.getAssetCode() + ". assetName: " + bai.getAssetName());

		int count = balanceSheetDAO.updateBSBai(bai);
		logger.info("count " + count);

		return bai;
	}

	@RequestMapping(value = "/bctc/cdkt/chitieu/capnhat/thap", method = RequestMethod.POST)
	public @ResponseBody BalanceAssetItem luuBalanceAssetItemLow(@RequestBody BalanceAssetItem bai) {
		logger.info("Come here");
		String assetCode = bai.getAssetCode();
		String maTk = bai.getTaiKhoanDs().get(0).getMaTk();
		String maTkCu = bai.getTaiKhoanDs().get(0).getMaTkGoc();

		logger.info("assetCode: " + assetCode + ". maTkCu: " + maTkCu + ". maTk: " + maTk);
		BalanceAssetItem oldBai = new BalanceAssetItem();
		oldBai.setAssetCode(assetCode);
		LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
		loaiTaiKhoan.setMaTk(maTkCu);
		oldBai.themTaiKhoan(loaiTaiKhoan);

		BalanceAssetItem newBai = new BalanceAssetItem();
		newBai.setAssetCode(assetCode);
		LoaiTaiKhoan loaiTaiKhoanTmpl = new LoaiTaiKhoan();
		loaiTaiKhoanTmpl.setMaTk(maTk);
		newBai.themTaiKhoan(loaiTaiKhoanTmpl);

		int count = balanceSheetDAO.updateBSLowBai(oldBai, newBai);
		logger.info("count " + count);

		BalanceAssetItem rsBai = new BalanceAssetItem();
		if (count > 0) {
			rsBai = balanceSheetDAO.findBSBai(assetCode, maTk);
		}

		return rsBai;
	}

	@RequestMapping("/bctc/luuchuyentt/danhsach")
	public String cashFlow(@ModelAttribute("mainFinanceForm") BalanceAssetForm form, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			// List balance assets:
			if (form == null)
				form = new BalanceAssetForm();

			// Lấy kỳ kế toán mặc định
			if (form.getKyKeToan() == null) {
				form.setKyKeToan(dungChung.getKyKeToan());
			} else {
				form.setKyKeToan(kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt()));
			}

			// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của kỳ
			if (form.getDau() == null) {
				form.setDau(form.getKyKeToan().getBatDau());
			}

			if (form.getCuoi() == null) {
				form.setCuoi(form.getKyKeToan().getKetThuc());
			}

			form.setPeriodType(KyKeToanCon.MONTH);

			List<Date> selectedAssetPeriods = null;
			if (form.getAssetPeriods() != null) {
				selectedAssetPeriods = Utils.convertArray2List(form.getAssetPeriods());
			} else if (form.isFirst()) {
				// Đây là lần đầu vào tab, không phải do ấn nút form tìm kiếm,
				// sẽ lấy dữ liệu tháng hiện tại
				selectedAssetPeriods = new ArrayList<>();
				Date period = Utils.getStartPeriod(new Date(), form.getPeriodType());
				selectedAssetPeriods.add(period);
				form.setFirst(false);

				// Chuyển tháng từ List Date sang Array String
				form.setAssetPeriods(Utils.convertList2Array(selectedAssetPeriods));
			}

			// AssetCode
			List<String> selectedAssetCodes = null;
			if (form.getAssetCodes() != null) {
				selectedAssetCodes = Arrays.asList(form.getAssetCodes());
			}

			List<BalanceAssetData> bads = balanceSheetDAO.listCFsByAssetCodesAndDates(selectedAssetCodes,
					selectedAssetPeriods, form.getPeriodType());
			List<String> assetCodes = balanceSheetDAO.listCFAssetsCodes();
			List<Date> assetPeriods = Utils.listStartPeriods(form.getDau(), form.getCuoi(), form.getPeriodType());

			// Paging:
			// Number records of a Page: Default: 25
			// Page Index: Default: 1
			// Total records
			// Total of page
			if (form.getNumberRecordsOfPage() == 0) {
				form.setNumberRecordsOfPage(25);
			}
			int numberRecordsOfPage = form.getNumberRecordsOfPage();

			if (form.getPageIndex() == 0) {
				form.setPageIndex(1);
			}
			int pageIndex = form.getPageIndex();
			form.setPageIndex(pageIndex);

			int totalRecords = bads == null ? 0 : bads.size();
			form.setTotalRecords(totalRecords);

			int totalPages = totalRecords % numberRecordsOfPage > 0 ? totalRecords / numberRecordsOfPage + 1
					: totalRecords / numberRecordsOfPage;
			form.setTotalPages(totalPages);

			List<BalanceAssetData> pagingBads = new ArrayList<>();
			int max = pageIndex * numberRecordsOfPage < bads.size() ? pageIndex * numberRecordsOfPage : bads.size();
			for (int i = (pageIndex - 1) * numberRecordsOfPage; i < max; i++) {
				pagingBads.add(bads.get(i));
			}

			model.addAttribute("pageIndex", pageIndex);
			model.addAttribute("numberRecordsOfPage", numberRecordsOfPage);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("totalRecords", totalRecords);

			model.addAttribute("bads", pagingBads);
			model.addAttribute("assetCodes", assetCodes);
			model.addAttribute("assetPeriods", assetPeriods);
			model.addAttribute("kyKeToanDs", kyKeToanDAO.danhSachKyKeToan());
			model.addAttribute("tab", "tabBLCTT");

			return "cashFlow";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/bctc/luuchuyentt/xuat", method = RequestMethod.POST)
	public void exportcashFlow(HttpServletRequest req, HttpServletResponse res,
			@ModelAttribute("mainFinanceForm") BalanceAssetForm form, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			// List balance assets:
			if (form == null)
				form = new BalanceAssetForm();

			// Lấy kỳ kế toán mặc định
			if (form.getKyKeToan() == null) {
				form.setKyKeToan(dungChung.getKyKeToan());
			} else {
				form.setKyKeToan(kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt()));
			}

			// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của kỳ
			if (form.getDau() == null) {
				form.setDau(form.getKyKeToan().getBatDau());
			}

			if (form.getCuoi() == null) {
				form.setCuoi(form.getKyKeToan().getKetThuc());
			}

			form.setPeriodType(KyKeToanCon.MONTH);

			List<Date> selectedAssetPeriods = null;
			if (form.getAssetPeriods() != null) {
				selectedAssetPeriods = Utils.convertArray2List(form.getAssetPeriods());
			} else if (form.isFirst()) {
				// Đây là lần đầu vào tab, không phải do ấn nút form tìm kiếm,
				// sẽ lấy dữ liệu tháng hiện tại
				selectedAssetPeriods = new ArrayList<>();
				Date period = Utils.getStartPeriod(new Date(), form.getPeriodType());
				selectedAssetPeriods.add(period);
				form.setFirst(false);

				// Chuyển tháng từ List Date sang Array String
				form.setAssetPeriods(Utils.convertList2Array(selectedAssetPeriods));
			}

			// AssetCode
			List<String> selectedAssetCodes = null;
			if (form.getAssetCodes() != null) {
				selectedAssetCodes = Arrays.asList(form.getAssetCodes());
			}

			List<BalanceAssetData> bads = balanceSheetDAO.listCFsByAssetCodesAndDates(selectedAssetCodes,
					selectedAssetPeriods, form.getPeriodType());

			// Sinh bảng cân đối kế toán ra pdf
			HashMap<String, Object> params = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);
			JasperReport jasperReport = getCompiledFile("LCTT", req);
			byte[] bytes = baoCaoDAO.taoBangCdkt(jasperReport, params, bads);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			res.setHeader("Content-disposition", "inline; filename=BangLctt.pdf");
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/bctc/luuchuyentt/capnhat")
	public String showUpdateCF(@ModelAttribute("mainFinanceForm") BalanceAssetForm form, Model model) {
		try {
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			if (form == null)
				form = new BalanceAssetForm();

			// Lấy kỳ kế toán mặc định
			if (form.getKyKeToan() == null) {
				form.setKyKeToan(dungChung.getKyKeToan());
			} else {
				form.setKyKeToan(kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt()));
			}

			// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của kỳ
			if (form.getDau() == null) {
				form.setDau(form.getKyKeToan().getBatDau());
			}

			if (form.getCuoi() == null) {
				form.setCuoi(form.getKyKeToan().getKetThuc());
			}

			form.setPeriodType(KyKeToanCon.MONTH);

			List<Date> assetPeriods = Utils.listStartPeriods(form.getDau(), form.getCuoi(), form.getPeriodType());

			model.addAttribute("assetPeriods", assetPeriods);
			model.addAttribute("tab", "tabBLCTT");

			return "showUpdateCF";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/bctc/luuchuyentt/capnhat/luu")
	public String updateCF(@ModelAttribute("mainFinanceForm") BalanceAssetForm form, Model model) {
		try {
			// Lấy kỳ kế toán mặc định
			if (form.getKyKeToan() == null) {
				form.setKyKeToan(dungChung.getKyKeToan());
			} else {
				form.setKyKeToan(kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt()));
			}

			// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của kỳ
			if (form.getDau() == null) {
				form.setDau(form.getKyKeToan().getBatDau());
			}

			if (form.getCuoi() == null) {
				form.setCuoi(form.getKyKeToan().getKetThuc());
			}

			form.setPeriodType(KyKeToanCon.MONTH);

			logger.info("Lấy danh sách các chỉ tiêu LCTT");
			List<BalanceAssetItem> bais = balanceSheetDAO.listCFBais();

			logger.info("Cập nhật các chi tiêu LCTT cho tất cả các loại kỳ trong khoảng thời gian: " + form.getDau()
					+ " - " + form.getCuoi());
			HashMap<Integer, String> kyDs = new HashMap<>();

			// kyDs.put(KyKeToanCon.WEEK, "Tuần");
			kyDs.put(KyKeToanCon.MONTH, "Tháng");
			// kyDs.put(KyKeToanCon.QUARTER, "Quý");
			kyDs.put(KyKeToanCon.YEAR, "Năm");

			Iterator<Integer> kyIter = kyDs.keySet().iterator();
			while (kyIter.hasNext()) {
				Integer ky = kyIter.next();
				logger.info("Cập nhật chi tiêu LCTT cho loại kỳ: " + kyDs.get(ky));
				try {
					Date dauKy = Utils.getStartPeriod(form.getDau(), ky);
					Date cuoiKy = Utils.getEndPeriod(form.getCuoi(), ky);
					Date buocNhay = dauKy;

					while (buocNhay.before(cuoiKy)) {
						Date cuoi = Utils.getEndPeriod(buocNhay, ky);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
						String batDau = sdf.format(buocNhay);
						String ketThuc = sdf.format(cuoi);

						logger.info("Kỳ " + batDau + " => " + ketThuc);

						logger.info("Xây dựng cây CashFlowData từ cây CashFlowItem");
						List<BalanceAssetData> bads = new ArrayList<>();
						Iterator<BalanceAssetItem> baiIter = bais.iterator();
						while (baiIter.hasNext()) {
							BalanceAssetItem bai = baiIter.next();
							BalanceAssetData bad = createBad(bai, ky, buocNhay);
							bads.add(bad);
						}

						logger.info("Lấy toàn bộ giá trị của các chi tiêu cấp thấp nhất.");
						List<BalanceAssetData> allBads = balanceSheetDAO.calculateCFBs(buocNhay, cuoi);

						logger.info("Tính giá trị cây CashFlowData theo từng kỳ, sau đó cập nhật vào CSDL");
						Iterator<BalanceAssetData> badIter = bads.iterator();
						while (badIter.hasNext()) {
							BalanceAssetData bad = badIter.next();
							bad = calCulcateCFBs(bad, allBads);
						}

						buocNhay = Utils.nextPeriod(buocNhay, ky);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return "redirect:/bctc/luuchuyentt/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private BalanceAssetData calCulcateCFBs(BalanceAssetData bad, List<BalanceAssetData> allBads) {
		if (bad == null)
			return null;

		try {
			Date previousPeriod = Utils.prevPeriod(bad.getPeriod(), bad.getPeriodType());

			BalanceAssetData badTmpl = new BalanceAssetData();
			badTmpl.setAsset(bad.getAsset());
			badTmpl.setPeriod(previousPeriod);
			badTmpl.setPeriodType(bad.getPeriodType());

			badTmpl = balanceSheetDAO.getCFPeriodEndValue(badTmpl);

			// Lấy giá trị kỳ trước làm giá trị đầu kỳ
			bad.setStartValue(badTmpl.getEndValue());
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info(bad);

		// Tính giá trị cuối kỳ
		if (bad.getChilds() != null && bad.getChilds().size() > 0) {
			logger.info("Tính giá trị chỉ tiêu " + bad.getAsset().getAssetCode() + " từ chỉ tiêu con");
			Iterator<BalanceAssetData> iter = bad.getChilds().iterator();
			while (iter.hasNext()) {
				BalanceAssetData childBad = iter.next();
				childBad = calCulcateCFBs(childBad, allBads);
			}

			// Tính giá trị chỉ tiêu hiện tại từ các chỉ tiêu trước đó
			String rule = bad.getAsset().getRule();
			logger.info(rule);
			if (rule != null && !rule.trim().equals("")) {
				List<String> toanHangDs = ExpressionEval.getOperands(rule);
				if (toanHangDs != null) {
					// Với mỗi toán hạng, đó là một tài khoản kết toán
					// Cần tính giá trị của tài khoản kế toán
					Iterator<String> toanHangIter = toanHangDs.iterator();
					while (toanHangIter.hasNext()) {
						String toanHang = toanHangIter.next();

						iter = bad.getChilds().iterator();
						while (iter.hasNext()) {
							BalanceAssetData childBad = iter.next();

							if (childBad.getAsset().getAssetCode().equals(toanHang)) {
								String value = childBad.getEndValue() + "";
								value = value.replace(ExpressionEval.DAU_AM, ExpressionEval.DAU_AM_TAM_THOI);
								rule = rule.replaceAll(toanHang, value);
								logger.info(toanHang + ": " + childBad.getEndValue());
								break;
							}
						}
					}

					double ketQua = ExpressionEval.calExp(rule);
					logger.info(bad + ": " + ketQua);
					bad.setEndValue(ketQua);
				}
			}
		} else {
			if (bad.getAsset() != null && bad.getAsset().getAssetCode() != null) {
				if (bad.getAsset().getAssetCode().equals(BalanceAssetItem.LCTT_CT_60)) {
					// Chỉ tiêu này lấy từ chỉ tiêu 110 đầu kỳ của bảng CDKT
					BalanceAssetData badTmpl = new BalanceAssetData();

					BalanceAssetItem baiTmpl = new BalanceAssetItem();
					baiTmpl.setAssetCode(BalanceAssetItem.CDKT_CT_110);

					badTmpl.setAsset(baiTmpl);
					badTmpl.setPeriod(bad.getPeriod());
					badTmpl.setPeriodType(bad.getPeriodType());

					badTmpl = balanceSheetDAO.getPeriodStartValue(badTmpl);

					bad.setEndValue(badTmpl.getStartValue());
				} else if (bad.getAsset().getTaiKhoanDs() != null) {
					Iterator<BalanceAssetData> iter = allBads.iterator();
					while (iter.hasNext()) {
						BalanceAssetData balanceAssetData = iter.next();
						balanceAssetData.setPeriodType(bad.getPeriodType());
						balanceAssetData.setPeriod(bad.getPeriod());

						if (balanceAssetData.equals(bad)) {
							bad.setEndValue(balanceAssetData.getEndValue());
						}
					}
				}
			}
		}

		// Cập nhật vào cơ sở dữ liệu
		balanceSheetDAO.insertOrUpdateCF(bad);

		return bad;
	}

	@RequestMapping("/bctc/luuchuyentt/chitieu/danhsach")
	public String danhSachChiTieuLctt(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			List<BalanceAssetItem> bais = balanceSheetDAO.listCFBais();

			model.addAttribute("bais", bais);
			model.addAttribute("props", props);
			model.addAttribute("tab", "tabDSBLCTT");

			return "danhSachChiTieuLctt";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/bctc/luuchuyentt/chitieu/taomoi")
	public String taoMoiChiTieuLctt(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}
			if (kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/bctc/luuchuyentt/chitieu/danhsach";
			}

			BalanceAssetItem bai = new BalanceAssetItem();

			return chuanBiFormChiTieuLctt(model, bai);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/bctc/luuchuyentt/chitieu/them", method = RequestMethod.POST)
	public String themChiTieuLctt(@ModelAttribute("mainFinanceForm") @Validated BalanceAssetItem bai,
			BindingResult result, Model model) {
		try {
			if (result.hasErrors()) {
				return chuanBiFormChiTieuLctt(model, bai);
			}

			logger.info("Thêm chỉ tiêu cân đối kế toán: " + bai);

			int count = balanceSheetDAO.insertCFBai(bai);
			if (count == -1) {
				result.rejectValue("assetCode", "NotEmpty.Bai.Duplicate");
				return chuanBiFormChiTieuLctt(model, bai);
			}

			return "redirect:/bctc/luuchuyentt/chitieu/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormChiTieuLctt(Model model, BalanceAssetItem bai) {
		try {
			model.addAttribute("props", props);
			model.addAttribute("mainFinanceForm", bai);
			bai.setType(BalanceAssetItem.BCTC_LCTT);

			// Lấy danh sách chỉ tiêu LCTT
			List<BalanceAssetItem> baiDs = balanceSheetDAO.listCFBais();
			List<BalanceAssetItem> baiDsRs = new ArrayList<>();

			if (baiDs != null) {
				// Lấy danh sách chỉ tiêu LCTT cấp thấp nhất
				Iterator<BalanceAssetItem> baiIter = baiDs.iterator();
				while (baiIter.hasNext()) {
					baiDsRs.addAll(lineUpLowestBai(baiIter.next()));
				}
			}

			// Lấy danh sách tài khoản kế toán
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();

			model.addAttribute("baiDs", baiDsRs);
			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);
			model.addAttribute("tab", "tabDSBLCTT");

			// Đây là trường hợp tạo mới CT CĐKT & TK
			return "taoMoiChiTieuLctt";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/bctc/luuchuyentt/chitieu/xoa")
	public @ResponseBody BalanceAssetItem xoaChiTieuLctt(@RequestParam("assetCode") String assetCode,
			@RequestParam("maTk") String maTk, @RequestParam("soDu") int soDu,
			@RequestParam("doiUngMaTk") String doiUngMaTk) {
		logger.info("Xoá BalanceAssetItem từ CASH_FLOW_TAIKHOAN: assetCode: " + assetCode + ". maTk: " + maTk
				+ ". soDu: " + soDu + ". doiUngMaTk: " + doiUngMaTk);
		BalanceAssetItem bai = new BalanceAssetItem();
		bai.setAssetCode(assetCode);

		LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
		loaiTaiKhoan.setMaTk(maTk);
		loaiTaiKhoan.setSoDuGiaTri(soDu);

		LoaiTaiKhoan loaiTaiKhoanDu = new LoaiTaiKhoan();
		loaiTaiKhoanDu.setMaTk(doiUngMaTk);

		loaiTaiKhoan.setDoiUng(loaiTaiKhoanDu);

		bai.themTaiKhoan(loaiTaiKhoan);

		balanceSheetDAO.deleteCFBai(bai);

		return bai;
	}

	@RequestMapping("/bctc/capnhatdulieu")
	public String update(Model model) {
		// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
		model.addAttribute("kpiGroups", dungChung.getKpiGroups());

		model.addAttribute("tab", "tabCNDL");
		return "update";
	}

	@RequestMapping(value = "/bctc/luutrudulieu", method = RequestMethod.POST)
	public String save(Model model, @ModelAttribute("mainFinanceForm") BalanceAssetForm form) {
		// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
		model.addAttribute("kpiGroups", dungChung.getKpiGroups());

		if (form != null && form.getBalanceAssetFile() != null && form.getBalanceAssetFile().getSize() > 0) {
			MultipartFile file = form.getBalanceAssetFile();
			logger.info(file.getName() + " - " + file.getSize());
			try {
				// Read & insert kpi groups and kpi charts
				List<KpiGroup> kpiGroups = ExcelProcessor.readKpiMeasuresSheet(file.getInputStream());
				// kpiChartDAO.insertOrUpdateKpiGroups(kpiGroups);

				// Read & insert balance sheet data
				List<BalanceAssetData> bas = ExcelProcessor.readBalanceAssetSheetExcel(file.getInputStream());
				balanceSheetDAO.insertOrUpdateBAs(bas);

				// Read & insert sale result data
				List<BalanceAssetData> srs = ExcelProcessor.readSaleResultSheetExcel(file.getInputStream());
				balanceSheetDAO.insertOrUpdateSRs(srs);

				// Read & insert cash flows data
				List<BalanceAssetData> cashFlows = ExcelProcessor.readCashFlowsSheetExcel(file.getInputStream());
				balanceSheetDAO.insertOrUpdateCFs(cashFlows);

				return "redirect:/bctc/capnhatdulieu";
			} catch (Exception e) {
				e.printStackTrace();
				String comment = "Không thể đọc excel file " + file.getName()
						+ ". Có thể file bị lỗi, không đúng định dạng, hoặc đường truyền chậm, xin mời thử lại.";
				model.addAttribute("balanceAssetComment", comment);
				model.addAttribute("tab", "tabCNDL");
				return "update";
			}
		} else {
			String comment = "Hãy chọn file exel dữ liệu kế toán.";
			model.addAttribute("balanceAssetComment", comment);
			model.addAttribute("tab", "tabCNDL");
			return "update";
		}
	}

	@RequestMapping(value = "/bctc/candoiphatsinh", method = { RequestMethod.GET, RequestMethod.POST })
	public String bangCanDoiPhatSinh(@ModelAttribute("mainFinanceForm") @Validated TkSoKeToanForm form,
			BindingResult result, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			// Lấy kỳ kế toán mặc định
			KyKeToan kyKeToan = null;
			if (form.getKyKeToan() == null) {
				kyKeToan = dungChung.getKyKeToan();
			} else {
				kyKeToan = kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt());
			}

			form.setKyKeToan(kyKeToan);
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			if (kyKeToan.getSoDuKyDs() == null) {
				kyKeToan.setSoDuKyDs(kyKeToanDAO.danhSachSoDuKy(kyKeToan.getMaKyKt()));
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

			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.cayTaiKhoan();
			LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
			loaiTaiKhoan.themLoaiTaiKhoan(loaiTaiKhoanDs);

			// Lấy dư nợ/có đầu kỳ, nợ phát sinh/có phát sinh và dư nợ/có cuối kỳ
			HashMap<KyKeToanCon, DuLieuKeToan> duLieuKeToanMap = new HashMap<>();

			// Lặp theo kỳ
			KyKeToanCon kyKt = new KyKeToanCon(form.getDau(), form.getLoaiKy());
			if (form.getLoaiKy() == KyKeToanCon.NAN) {
				kyKt = new KyKeToanCon(form.getDau(), form.getCuoi());
			}

			while (kyKt != null && kyKt.getCuoi() != null && !kyKt.getCuoi().after(form.getCuoi())) {
				logger.info("KỲ: " + kyKt);
				KyKeToanCon kyKtTruoc = kyKt.kyTruoc();
				DuLieuKeToan duLieuKeToan = new DuLieuKeToan(kyKt, loaiTaiKhoan);

				List<TaiKhoan> dauKyDs = soKeToanDAO.tongPhatSinh(form.getKyKeToan().getBatDau(), kyKtTruoc.getCuoi());
				dauKyDs = tronNoCoDauKy(dauKyDs, kyKeToan.getSoDuKyDs());

				List<TaiKhoan> tongPsDs = soKeToanDAO.tongPhatSinh(kyKt.getDau(), kyKt.getCuoi());
				duLieuKeToan = tongPhatSinh(duLieuKeToan, tongPsDs, dauKyDs);

				duLieuKeToanMap.put(kyKt, duLieuKeToan);
				kyKt = Utils.nextPeriod(kyKt);
			}

			model.addAttribute("kyKeToanDs", kyKeToanDAO.danhSachKyKeToan());
			model.addAttribute("duLieuKeToanMap", duLieuKeToanMap);
			model.addAttribute("mainFinanceForm", form);

			model.addAttribute("tab", "tabBCDPS");
			return "bangCanDoiPhatSinh";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/bctc/candoiphatsinh/xuat", method = { RequestMethod.GET, RequestMethod.POST })
	public void exportBangCanDoiPhatSinh(HttpServletRequest req, HttpServletResponse res,
			@ModelAttribute("mainFinanceForm") @Validated TkSoKeToanForm form, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			// Lấy kỳ kế toán mặc định
			KyKeToan kyKeToan = null;
			if (form.getKyKeToan() == null) {
				kyKeToan = dungChung.getKyKeToan();
			} else {
				kyKeToan = kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt());
			}
			form.setKyKeToan(kyKeToan);

			if (kyKeToan.getSoDuKyDs() == null) {
				kyKeToan.setSoDuKyDs(kyKeToanDAO.danhSachSoDuKy(kyKeToan.getMaKyKt()));
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

			form.setLoaiKy(KyKeToanCon.NAN);

			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.cayTaiKhoan();
			LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
			loaiTaiKhoan.themLoaiTaiKhoan(loaiTaiKhoanDs);

			// Lặp theo kỳ
			KyKeToanCon kyKt = new KyKeToanCon(form.getDau(), form.getCuoi());
			KyKeToanCon kyKtTruoc = kyKt.kyTruoc();
			DuLieuKeToan duLieuKeToan = new DuLieuKeToan(kyKt, loaiTaiKhoan);

			List<TaiKhoan> dauKyDs = soKeToanDAO.tongPhatSinh(form.getKyKeToan().getBatDau(), kyKtTruoc.getCuoi());
			dauKyDs = tronNoCoDauKy(dauKyDs, kyKeToan.getSoDuKyDs());

			List<TaiKhoan> tongPsDs = soKeToanDAO.tongPhatSinh(kyKt.getDau(), kyKt.getCuoi());
			duLieuKeToan = tongPhatSinh(duLieuKeToan, tongPsDs, dauKyDs);

			// Sinh bảng cân đối phát sinh ra pdf
			JasperReport jasperReport = getCompiledFile("CDPS", req);

			HashMap<String, Object> params = props.getCauHinhTheoNhom(CauHinh.NHOM_CONG_TY);
			params.put("KY_KE_TOAN", kyKt);
			String path = req.getSession().getServletContext().getRealPath("/baocao/bctc/");
			params.put("SUBREPORT_DIR", path);

			byte[] bytes = baoCaoDAO.taoBangCdps(jasperReport, params, duLieuKeToan);

			res.reset();
			res.resetBuffer();
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			res.setHeader("Content-disposition", "inline; filename=BangCdps.pdf");
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (JRException | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<TaiKhoan> tronNoCoDauKy(List<TaiKhoan> dauKyDs, List<SoDuKy> soDuKyDs) {
		if (dauKyDs == null || soDuKyDs == null) {
			return dauKyDs;
		}

		logger.info("Trộn nợ/có đầu kỳ");
		Iterator<TaiKhoan> dkIter = dauKyDs.iterator();
		while (dkIter.hasNext()) {
			TaiKhoan taiKhoan = dkIter.next();

			Iterator<SoDuKy> sdkIter = soDuKyDs.iterator();
			while (sdkIter.hasNext()) {
				SoDuKy soDuKy = sdkIter.next();

				if (taiKhoan.getLoaiTaiKhoan().equals(soDuKy.getLoaiTaiKhoan())) {
					Tien soTien = taiKhoan.getSoTien();
					if (taiKhoan.getSoDu() == LoaiTaiKhoan.NO) {
						soTien.setGiaTri(soTien.getGiaTri() + soDuKy.getNoDauKy());
					} else {
						soTien.setGiaTri(soTien.getGiaTri() + soDuKy.getCoDauKy());
					}

					taiKhoan.setSoTien(soTien);
				}
			}
		}

		Iterator<SoDuKy> sdkIter = soDuKyDs.iterator();
		while (sdkIter.hasNext()) {
			SoDuKy soDuKy = sdkIter.next();

			boolean daXuLy = false;
			Iterator<TaiKhoan> iter = dauKyDs.iterator();
			while (iter.hasNext()) {
				TaiKhoan taiKhoan = iter.next();

				if (soDuKy.getLoaiTaiKhoan().equals(taiKhoan.getLoaiTaiKhoan())) {
					daXuLy = true;
					break;
				}
			}

			if (!daXuLy) {
				if (soDuKy.getNoDauKy() > 0) {
					TaiKhoan taiKhoan = new TaiKhoan();

					Tien tien = new Tien();
					tien.setGiaTri(soDuKy.getNoDauKy());

					taiKhoan.setLoaiTaiKhoan(soDuKy.getLoaiTaiKhoan());
					taiKhoan.setSoTien(tien);
					taiKhoan.setSoDu(LoaiTaiKhoan.NO);

					dauKyDs.add(taiKhoan);
				}

				if (soDuKy.getCoDauKy() > 0) {
					TaiKhoan taiKhoan = new TaiKhoan();

					Tien tien = new Tien();
					tien.setGiaTri(soDuKy.getCoDauKy());

					taiKhoan.setLoaiTaiKhoan(soDuKy.getLoaiTaiKhoan());
					taiKhoan.setSoTien(tien);
					taiKhoan.setSoDu(LoaiTaiKhoan.CO);

					dauKyDs.add(taiKhoan);
				}
			}
		}

		logger.info("Số dư đầu kỳ");
		for (Iterator<TaiKhoan> iter = dauKyDs.iterator(); iter.hasNext();) {
			TaiKhoan taiKhoan = iter.next();
			logger.info(taiKhoan);
		}

		return dauKyDs;
	}

	private DuLieuKeToan tongPhatSinh(DuLieuKeToan duLieuKeToan, List<TaiKhoan> tongPsDs, List<TaiKhoan> dauKyDs) {
		if (duLieuKeToan == null || duLieuKeToan.getKyKeToan() == null || duLieuKeToan.getLoaiTaiKhoan() == null)
			return null;

		if (tongPsDs == null) {
			tongPsDs = new ArrayList<>();
		}

		if (dauKyDs == null) {
			dauKyDs = new ArrayList<>();
		}

		List<LoaiTaiKhoan> loaiTaiKhoanDs = duLieuKeToan.getLoaiTaiKhoan().getLoaiTaiKhoanDs();
		if (loaiTaiKhoanDs != null && loaiTaiKhoanDs.size() > 0) {
			List<DuLieuKeToan> duLieuKeToanDs = new ArrayList<>();
			List<LoaiTaiKhoan> loaiTaiKhoanConDs = new ArrayList<>();

			Iterator<LoaiTaiKhoan> iter = loaiTaiKhoanDs.iterator();
			while (iter.hasNext()) {
				LoaiTaiKhoan loaiTaiKhoan = iter.next();

				DuLieuKeToan duLieuKeToanCon = new DuLieuKeToan(duLieuKeToan.getKyKeToan(), loaiTaiKhoan);
				duLieuKeToanCon.setDuLieuKeToan(duLieuKeToan);
				boolean coDuLieu = false;

				Iterator<TaiKhoan> tpsIter = tongPsDs.iterator();
				while (tpsIter.hasNext()) {
					TaiKhoan taiKhoan = tpsIter.next();

					if (taiKhoan.getLoaiTaiKhoan().equals(loaiTaiKhoan)) {
						if (taiKhoan.getSoDu() == LoaiTaiKhoan.NO) {
							duLieuKeToanCon.setTongNoPhatSinh(taiKhoan.getSoTien().getGiaTri());
						} else {
							duLieuKeToanCon.setTongCoPhatSinh(taiKhoan.getSoTien().getGiaTri());
						}
						coDuLieu = true;
					}
				}

				Iterator<TaiKhoan> dkIter = dauKyDs.iterator();
				while (dkIter.hasNext()) {
					TaiKhoan taiKhoan = dkIter.next();

					if (taiKhoan.getLoaiTaiKhoan().equals(loaiTaiKhoan)) {
						if (taiKhoan.getSoDu() == LoaiTaiKhoan.NO) {
							duLieuKeToanCon.setNoDauKy(taiKhoan.getSoTien().getGiaTri());
						} else {
							duLieuKeToanCon.setCoDauKy(taiKhoan.getSoTien().getGiaTri());
						}
						coDuLieu = true;
					}
				}

				if (coDuLieu) {
					loaiTaiKhoanConDs.add(loaiTaiKhoan);
					duLieuKeToanCon = tongPhatSinh(duLieuKeToanCon, tongPsDs, dauKyDs);
					duLieuKeToanDs.add(duLieuKeToanCon);
				}
			}

			duLieuKeToan.getLoaiTaiKhoan().setLoaiTaiKhoanDs(loaiTaiKhoanConDs);
			duLieuKeToan.setDuLieuKeToanDs(duLieuKeToanDs);
		}

		if (duLieuKeToan.getLoaiTaiKhoan().getMaTk() == null
				|| duLieuKeToan.getLoaiTaiKhoan().getMaTk().trim().equals("")) {
			// Đây là gốc cây
			if (duLieuKeToan.getDuLieuKeToanDs() != null && duLieuKeToan.getDuLieuKeToanDs().size() > 0) {
				Iterator<DuLieuKeToan> iter = duLieuKeToan.getDuLieuKeToanDs().iterator();
				while (iter.hasNext()) {
					DuLieuKeToan duLieuKeToanCon = iter.next();

					// Tính tổng nợ/có phát sinh cho dữ liệu kế toán gốc
					duLieuKeToan
							.setTongNoPhatSinh(duLieuKeToan.getTongNoPhatSinh() + duLieuKeToanCon.getTongNoPhatSinh());
					duLieuKeToan
							.setTongCoPhatSinh(duLieuKeToan.getTongCoPhatSinh() + duLieuKeToanCon.getTongCoPhatSinh());

					// Tính nơ/có đầu kỳ cho dữ liệu kế toán gốc
					duLieuKeToan.setNoDauKy(duLieuKeToan.getNoDauKy() + duLieuKeToanCon.getNoDauKy());
					duLieuKeToan.setCoDauKy(duLieuKeToan.getCoDauKy() + duLieuKeToanCon.getCoDauKy());
				}
			}
		}

		return duLieuKeToan;
	}

	private JasperReport getCompiledFile(String fileName, HttpServletRequest req) throws JRException {
		String jrxml = req.getSession().getServletContext().getRealPath("/baocao/bctc/" + fileName + ".jrxml");
		String jasper = req.getSession().getServletContext().getRealPath("/baocao/bctc/" + fileName + ".jasper");

		File reportFile = new File(jasper);
		// If compiled file is not found, then compile XML template
		// if (!reportFile.exists()) {
		logger.info("Compile Jasper report ... " + fileName);
		JasperCompileManager.compileReportToFile(jrxml, jasper);
		// }
		JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportFile.getPath());
		return jasperReport;
	}
}
