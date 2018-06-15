package com.idi.finance.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.multipart.MultipartFile;

import com.idi.finance.bean.DungChung;
import com.idi.finance.bean.bieudo.KpiGroup;
import com.idi.finance.bean.cdkt.BalanceAssetData;
import com.idi.finance.bean.cdkt.BalanceAssetItem;
import com.idi.finance.bean.cdkt.DuLieuKeToan;
import com.idi.finance.bean.cdkt.KyKeToanCon;
import com.idi.finance.bean.chungtu.ChungTu;
import com.idi.finance.bean.chungtu.TaiKhoan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.BalanceSheetDAO;
import com.idi.finance.dao.KyKeToanDAO;
import com.idi.finance.dao.SoKeToanDAO;
import com.idi.finance.dao.TaiKhoanDAO;
import com.idi.finance.form.BalanceAssetForm;
import com.idi.finance.form.TkSoKeToanForm;
import com.idi.finance.utils.ExcelProcessor;
import com.idi.finance.utils.Utils;

@Controller
public class BalanceSheetController {
	private static final Logger logger = Logger.getLogger(BalanceSheetController.class);

	@Autowired
	DungChung dungChung;

	@Autowired
	BalanceSheetDAO balanceSheetDAO;

	@Autowired
	TaiKhoanDAO taiKhoanDAO;

	@Autowired
	SoKeToanDAO soKeToanDAO;

	@Autowired
	KyKeToanDAO kyKeToanDAO;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("/cdkt/candoiketoan")
	public String balanceAssets(@ModelAttribute("mainFinanceForm") BalanceAssetForm balanceSheetForm, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			// List balance assets:
			if (balanceSheetForm == null)
				balanceSheetForm = new BalanceAssetForm();

			// Lấy kỳ kế toán mặc định
			if (balanceSheetForm.getKyKeToan() == null) {
				balanceSheetForm.setKyKeToan(dungChung.getKyKeToan());
			} else {
				balanceSheetForm.setKyKeToan(kyKeToanDAO.layKyKeToan(balanceSheetForm.getKyKeToan().getMaKyKt()));
			}

			// Nếu không có đầu vào ngày tháng, lấy ngày đầu tiên và ngày cuối cùng của kỳ
			if (balanceSheetForm.getDau() == null) {
				balanceSheetForm.setDau(balanceSheetForm.getKyKeToan().getBatDau());
			}

			if (balanceSheetForm.getCuoi() == null) {
				balanceSheetForm.setCuoi(balanceSheetForm.getKyKeToan().getKetThuc());
			}

			List<Date> selectedAssetPeriods = null;
			if (balanceSheetForm.getAssetPeriods() != null) {
				selectedAssetPeriods = Utils.convertArray2List(balanceSheetForm.getAssetPeriods());
			} else if (!balanceSheetForm.isFirst()) {
				// Đây là lần đầu vào tab, không phải do ấn nút form tìm kiếm,
				// sẽ lấy dữ liệu tháng hiện tại

				// Time: Defaul: current year, current month
				// Date currentYear = Utils.getStartDateOfMonth(new Date());

				selectedAssetPeriods = new ArrayList<>();
				Date period = Utils.getStartPeriod(balanceSheetForm.getKyKeToan().getBatDau(),
						balanceSheetForm.getPeriodType());
				// selectedAssetPeriods.add(currentYear);
				selectedAssetPeriods.add(period);
				balanceSheetForm.setFirst(true);

				// Chuyển tháng từ List Date sang Array String
				balanceSheetForm.setAssetPeriods(Utils.convertList2Array(selectedAssetPeriods));
			}

			// AssetCode
			List<String> selectedAssetCodes = null;
			if (balanceSheetForm.getAssetCodes() != null) {
				selectedAssetCodes = Arrays.asList(balanceSheetForm.getAssetCodes());
			}

			List<BalanceAssetData> bads = balanceSheetDAO.listBAsByAssetCodesAndDates(selectedAssetCodes,
					selectedAssetPeriods, balanceSheetForm.getPeriodType());
			List<String> assetCodes = balanceSheetDAO.listBSAssetsCodes();
			List<Date> assetPeriods = balanceSheetDAO.listBSAssetsPeriods(balanceSheetForm.getPeriodType());

			// Paging:
			// Number records of a Page: Default: 25
			// Page Index: Default: 1
			// Total records
			// Total of page
			if (balanceSheetForm.getNumberRecordsOfPage() == 0) {
				balanceSheetForm.setNumberRecordsOfPage(25);
			}
			int numberRecordsOfPage = balanceSheetForm.getNumberRecordsOfPage();

			if (balanceSheetForm.getPageIndex() == 0) {
				balanceSheetForm.setPageIndex(1);
			}
			int pageIndex = balanceSheetForm.getPageIndex();
			balanceSheetForm.setPageIndex(pageIndex);

			int totalRecords = bads == null ? 0 : bads.size();
			balanceSheetForm.setTotalRecords(totalRecords);

			int totalPages = totalRecords % numberRecordsOfPage > 0 ? totalRecords / numberRecordsOfPage + 1
					: totalRecords / numberRecordsOfPage;
			balanceSheetForm.setTotalPages(totalPages);

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

	@RequestMapping("/cdkt/capnhatcandoiketoan")
	public String updateBS(@ModelAttribute("mainFinanceForm") BalanceAssetForm form, Model model) {
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

			logger.info("Cập nhật dữ liệu hạch toán trước đó");
			// balanceSheetDAO.capNhatCanDoiKeToan(form.getTaiKhoanDs());

			logger.info("Lấy danh sách các chỉ tiêu CDKT");
			List<BalanceAssetItem> bais = balanceSheetDAO.listBais();

			logger.info("Cập nhật các chi tiêu CĐKT cho tất cả các loại kỳ trong khoảng thời gian: " + form.getDau()
					+ " - " + form.getCuoi());
			HashMap<Integer, String> kyDs = new HashMap<>();

			// kyDs.put(KyKeToanCon.WEEK, "Tuần");
			// kyDs.put(KyKeToanCon.MONTH, "Tháng");
			// kyDs.put(KyKeToanCon.QUARTER, "Quý");
			kyDs.put(KyKeToanCon.YEAR, "Năm");

			Iterator<Integer> kyIter = kyDs.keySet().iterator();
			while (kyIter.hasNext()) {
				Integer ky = kyIter.next();
				logger.info("Cập nhật chi tiêu CĐKT cho loại kỳ: " + kyDs.get(ky));
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

						logger.info("Xây dựng cây BalanceAssetData từ cây BalanceAssetItem");
						List<BalanceAssetData> bads = new ArrayList<>();
						Iterator<BalanceAssetItem> baiIter = bais.iterator();
						while (baiIter.hasNext()) {
							BalanceAssetItem bai = baiIter.next();
							BalanceAssetData bad = createBad(bai, ky, buocNhay);
							bads.add(bad);
						}

						logger.info("Tính giá trị cây BalanceAssetData theo từng kỳ, sau đó cập nhật vào CSDL");
						Iterator<BalanceAssetData> badIter = bads.iterator();
						while (badIter.hasNext()) {
							BalanceAssetData bad = badIter.next();
							bad = calCulcateBs(bad);
						}

						buocNhay = Utils.nextPeriod(buocNhay, ky);
					}
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}

			return "redirect:/cdkt/candoiketoan";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
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

	private BalanceAssetData calCulcateBs(BalanceAssetData bad) {
		if (bad == null)
			return null;

		try {
			// Lấy giá trị kỳ trước làm giá trị đầu kỳ
			bad = balanceSheetDAO.getPeriodEndValue(bad);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Tính giá trị cuối kỳ
		if (bad.getChilds() != null && bad.getChilds().size() > 0) {
			Iterator<BalanceAssetData> iter = bad.getChilds().iterator();
			while (iter.hasNext()) {
				BalanceAssetData childBad = iter.next();
				childBad = calCulcateBs(childBad);
				bad.setEndValue(bad.getEndValue() + childBad.getEndValue());
			}
		} else {
			// Kết nối CSDL để tính giá trị cuối kỳ của chỉ tiêu cân đối kế toán theo các
			// tài khoản kế toán với công thức xác định trước cho từng loại chỉ tiêu
			try {
				bad = balanceSheetDAO.calculateBs(bad);
			} catch (Exception e) {
				// logger.error("LỖI: " + e.getMessage());
			}
		}

		// logger.info("Chỉ tiêu cân đối kế toán " + bad.getAsset().getAssetCode() + ":
		// " + bad.getEndValue());

		// Cập nhật vào cơ sở dữ liệu
		balanceSheetDAO.insertOrUpdateBA(bad);

		return bad;
	}

	@RequestMapping("/cdkt/capnhatdulieu")
	public String update(Model model) {
		// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
		model.addAttribute("kpiGroups", dungChung.getKpiGroups());

		model.addAttribute("tab", "tabCNDL");
		return "update";
	}

	@RequestMapping(value = "/cdkt/luutrudulieu", method = RequestMethod.POST)
	public String save(Model model, @ModelAttribute("mainFinanceForm") BalanceAssetForm balanceSheetForm) {
		// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
		model.addAttribute("kpiGroups", dungChung.getKpiGroups());

		if (balanceSheetForm != null && balanceSheetForm.getBalanceAssetFile() != null
				&& balanceSheetForm.getBalanceAssetFile().getSize() > 0) {
			MultipartFile file = balanceSheetForm.getBalanceAssetFile();
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

				return "redirect:/cdkt/capnhatdulieu";
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

	@RequestMapping("/cdkt/danhsachtaikhoan")
	public String balanceSheetCodes(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			List<BalanceAssetItem> bais = balanceSheetDAO.listBais();
			model.addAttribute("bais", bais);
			model.addAttribute("tab", "tabDSBCDKT");

			return "balanceSheetCodes";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/bctc/candoiphatsinh", method = { RequestMethod.GET, RequestMethod.POST })
	public String bangCanDoiPhatSinh(@ModelAttribute("mainFinanceForm") TkSoKeToanForm form, Model model) {
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

			// Lấy danh mục tài khoản các cấp
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.cayTaiKhoan();

			// Lấy dư nợ/có đầu kỳ, nợ phát sinh/có phát sinh và dư nợ/có cuối kỳ
			HashMap<KyKeToanCon, DuLieuKeToan> duLieuKeToanMap = new HashMap<>();

			// Lặp theo kỳ
			KyKeToanCon kyKt = new KyKeToanCon(form.getDau(), form.getLoaiKy());

			if (form.getLoaiKy() == KyKeToanCon.NAN) {
				logger.info("KỲ: " + kyKt);
				kyKt = new KyKeToanCon(form.getDau(), form.getCuoi());
				KyKeToanCon kyKtTruoc = kyKt.kyTruoc();

				DuLieuKeToan duLieuKeToan = new DuLieuKeToan(kyKt);

				// Tính tổng nợ/có đầu kỳ, phát sinh nợ/có trong kỳ và số dư nợ/có cuối kỳ
				duLieuKeToan = tongPhatSinh(duLieuKeToan, loaiTaiKhoanDs, duLieuKeToanMap.get(kyKtTruoc));

				if (duLieuKeToan.getDuLieuKeToanDs() != null) {
					Iterator<DuLieuKeToan> iter = duLieuKeToan.getDuLieuKeToanDs().iterator();
					while (iter.hasNext()) {
						DuLieuKeToan duLieuKeToanCon = iter.next();

						if (duLieuKeToanCon.getSoDuDauKy() >= 0) {
							duLieuKeToan.setNoDauKy(duLieuKeToan.getNoDauKy() + duLieuKeToanCon.getSoDuDauKy());
						} else {
							duLieuKeToan.setCoDauKy(duLieuKeToan.getCoDauKy() - duLieuKeToanCon.getSoDuDauKy());
						}

						duLieuKeToan.setTongNoPhatSinh(
								duLieuKeToan.getTongNoPhatSinh() + duLieuKeToanCon.getTongNoPhatSinh());
						duLieuKeToan.setTongCoPhatSinh(
								duLieuKeToan.getTongCoPhatSinh() + duLieuKeToanCon.getTongCoPhatSinh());

						if (duLieuKeToanCon.getSoDuCuoiKy() >= 0) {
							duLieuKeToan.setNoCuoiKy(duLieuKeToan.getNoCuoiKy() + duLieuKeToanCon.getSoDuCuoiKy());
						} else {
							duLieuKeToan.setCoCuoiKy(duLieuKeToan.getCoCuoiKy() - duLieuKeToanCon.getSoDuCuoiKy());
						}
					}
				}

				duLieuKeToanMap.put(kyKt, duLieuKeToan);
			} else {
				while (!kyKt.getCuoi().after(form.getCuoi())) {
					logger.info("KỲ: " + kyKt);

					DuLieuKeToan duLieuKeToan = new DuLieuKeToan(kyKt);
					KyKeToanCon kyKtTruoc = kyKt.kyTruoc();

					// Tính tổng nợ/có đầu kỳ, phát sinh nợ/có trong kỳ và số dư nợ/có cuối kỳ
					duLieuKeToan = tongPhatSinh(duLieuKeToan, loaiTaiKhoanDs, duLieuKeToanMap.get(kyKtTruoc));

					if (duLieuKeToan.getDuLieuKeToanDs() != null) {
						Iterator<DuLieuKeToan> iter = duLieuKeToan.getDuLieuKeToanDs().iterator();
						while (iter.hasNext()) {
							DuLieuKeToan duLieuKeToanCon = iter.next();

							if (duLieuKeToanCon.getSoDuDauKy() >= 0) {
								duLieuKeToan.setNoDauKy(duLieuKeToan.getNoDauKy() + duLieuKeToanCon.getSoDuDauKy());
							} else {
								duLieuKeToan.setCoDauKy(duLieuKeToan.getCoDauKy() - duLieuKeToanCon.getSoDuDauKy());
							}

							duLieuKeToan.setTongNoPhatSinh(
									duLieuKeToan.getTongNoPhatSinh() + duLieuKeToanCon.getTongNoPhatSinh());
							duLieuKeToan.setTongCoPhatSinh(
									duLieuKeToan.getTongCoPhatSinh() + duLieuKeToanCon.getTongCoPhatSinh());

							if (duLieuKeToanCon.getSoDuCuoiKy() >= 0) {
								duLieuKeToan.setNoCuoiKy(duLieuKeToan.getNoCuoiKy() + duLieuKeToanCon.getSoDuCuoiKy());
							} else {
								duLieuKeToan.setCoCuoiKy(duLieuKeToan.getCoCuoiKy() - duLieuKeToanCon.getSoDuCuoiKy());
							}
						}
					}

					duLieuKeToanMap.put(kyKt, duLieuKeToan);

					kyKt = Utils.nextPeriod(kyKt);
				}
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

	private DuLieuKeToan tongPhatSinh(DuLieuKeToan duLieuKeToan, List<LoaiTaiKhoan> loaiTaiKhoanDs,
			DuLieuKeToan duLieuKeToanKyTruoc) {
		if (duLieuKeToan == null)
			return null;

		HashMap<LoaiTaiKhoan, DuLieuKeToan> duLieuKeToanKyTruocMap = null;
		if (duLieuKeToanKyTruoc != null && duLieuKeToanKyTruoc.getDuLieuKeToanDs() != null
				&& duLieuKeToanKyTruoc.getDuLieuKeToanDs().size() > 0) {
			duLieuKeToanKyTruocMap = new HashMap<>();
			Iterator<DuLieuKeToan> iter = duLieuKeToanKyTruoc.getDuLieuKeToanDs().iterator();
			while (iter.hasNext()) {
				DuLieuKeToan duLieuKeToanConKyTruoc = iter.next();
				duLieuKeToanKyTruocMap.put(duLieuKeToanConKyTruoc.getLoaiTaiKhoan(), duLieuKeToanConKyTruoc);
			}
		}

		// Tính các giá trị cho các kỳ kế toán của tài khoản con
		if (loaiTaiKhoanDs != null && loaiTaiKhoanDs.size() > 0) {
			List<DuLieuKeToan> duLieuKeToanDs = new ArrayList<>();
			Iterator<LoaiTaiKhoan> iter = loaiTaiKhoanDs.iterator();
			while (iter.hasNext()) {
				LoaiTaiKhoan loaiTaiKhoan = iter.next();

				DuLieuKeToan duLieuKeToanCon = new DuLieuKeToan(duLieuKeToan.getKyKeToan(), loaiTaiKhoan);
				duLieuKeToanCon.setDuLieuKeToan(duLieuKeToan);

				DuLieuKeToan duLieuKeToanConKyTruoc = null;
				if (duLieuKeToanKyTruocMap != null) {
					duLieuKeToanConKyTruoc = duLieuKeToanKyTruocMap.get(loaiTaiKhoan);
				}

				duLieuKeToanCon = tongPhatSinh(duLieuKeToanCon, loaiTaiKhoan.getLoaiTaiKhoanDs(),
						duLieuKeToanConKyTruoc);
				duLieuKeToanDs.add(duLieuKeToanCon);
			}
			duLieuKeToan.themDuLieuKeToan(duLieuKeToanDs);
		}

		// Tính tổng phát sinh ở đây
		if (duLieuKeToan.getLoaiTaiKhoan() != null) {
			double soDuDauKy = 0;
			double soDuCuoiKy = 0;
			double tongNoPhatSinh = 0;
			double tongCoPhatSinh = 0;

			if (duLieuKeToanKyTruoc != null) {
				soDuDauKy = duLieuKeToanKyTruoc.getSoDuCuoiKy();
			} else {
				Date cuoiKyTruoc = Utils.prevPeriod(duLieuKeToan.getKyKeToan()).getCuoi();
				// Lấy tổng nợ đầu kỳ
				double noDauKy = soKeToanDAO.tongPhatSinh(duLieuKeToan.getLoaiTaiKhoan().getMaTk(), LoaiTaiKhoan.NO,
						null, cuoiKyTruoc);
				// Lấy tổng có đầu kỳ
				double coDauKy = soKeToanDAO.tongPhatSinh(duLieuKeToan.getLoaiTaiKhoan().getMaTk(), LoaiTaiKhoan.CO,
						null, cuoiKyTruoc);
				soDuDauKy = noDauKy - coDauKy;
			}

			tongNoPhatSinh = soKeToanDAO.tongPhatSinh(duLieuKeToan.getLoaiTaiKhoan().getMaTk(), LoaiTaiKhoan.NO,
					duLieuKeToan.getKyKeToan().getDau(), duLieuKeToan.getKyKeToan().getCuoi());

			tongCoPhatSinh = soKeToanDAO.tongPhatSinh(duLieuKeToan.getLoaiTaiKhoan().getMaTk(), LoaiTaiKhoan.CO,
					duLieuKeToan.getKyKeToan().getDau(), duLieuKeToan.getKyKeToan().getCuoi());

			soDuCuoiKy = tongNoPhatSinh - tongCoPhatSinh + soDuDauKy;

			duLieuKeToan.setSoDuDauKy(soDuDauKy);
			duLieuKeToan.setTongNoPhatSinh(tongNoPhatSinh);
			duLieuKeToan.setTongCoPhatSinh(tongCoPhatSinh);
			duLieuKeToan.setSoDuCuoiKy(soDuCuoiKy);
			logger.info(duLieuKeToan);
		}

		return duLieuKeToan;
	}
}
