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
import com.idi.finance.bean.bctc.BalanceAssetData;
import com.idi.finance.bean.bctc.BalanceAssetItem;
import com.idi.finance.bean.bctc.DuLieuKeToan;
import com.idi.finance.bean.bctc.KyKeToanCon;
import com.idi.finance.bean.bieudo.KpiGroup;
import com.idi.finance.bean.chungtu.TaiKhoan;
import com.idi.finance.bean.chungtu.Tien;
import com.idi.finance.bean.kyketoan.SoDuKy;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.BalanceSheetDAO;
import com.idi.finance.dao.KyKeToanDAO;
import com.idi.finance.dao.SoKeToanDAO;
import com.idi.finance.dao.TaiKhoanDAO;
import com.idi.finance.form.BalanceAssetForm;
import com.idi.finance.form.TkSoKeToanForm;
import com.idi.finance.utils.ExcelProcessor;
import com.idi.finance.utils.ExpressionEval;
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

	@RequestMapping("/bctc/cdkt/danhsach")
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

	@RequestMapping("/bctc/cdkt/capnhat")
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

			logger.info("Lấy danh sách các chỉ tiêu CDKT");
			List<BalanceAssetItem> bais = balanceSheetDAO.listBais();

			logger.info("Cập nhật các chi tiêu CĐKT cho tất cả các loại kỳ trong khoảng thời gian: " + form.getDau()
					+ " - " + form.getCuoi());
			HashMap<Integer, String> kyDs = new HashMap<>();

			logger.info("Lấy danh sách số dư đầu kỳ");
			List<SoDuKy> soDuKyDs = kyKeToanDAO.danhSachSoDuKy(form.getKyKeToan().getMaKyKt());

			// kyDs.put(KyKeToanCon.WEEK, "Tuần");
			kyDs.put(KyKeToanCon.MONTH, "Tháng");
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

						logger.info("Lấy toàn bộ giá trị của các chi tiêu cấp thấp nhất.");
						List<BalanceAssetData> allBads = balanceSheetDAO.calculateBs(dauKy, cuoi);
						// List<BalanceAssetData> allBads = balanceSheetDAO.calculateBs(buocNhay, cuoi);
						Iterator<BalanceAssetData> iter = allBads.iterator();
						while (iter.hasNext()) {
							BalanceAssetData balanceAssetData = iter.next();
							balanceAssetData.setPeriodType(ky);
							balanceAssetData.setPeriod(buocNhay);
						}

						logger.info("Tính giá trị cây BalanceAssetData theo từng kỳ, sau đó cập nhật vào CSDL");
						Iterator<BalanceAssetData> badIter = bads.iterator();
						while (badIter.hasNext()) {
							BalanceAssetData bad = badIter.next();
							bad = calCulcateBs(bad, allBads, soDuKyDs);
						}

						buocNhay = Utils.nextPeriod(buocNhay, ky);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return "redirect:/bctc/cdkt/danhsach";
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

	private BalanceAssetData calCulcateBs(BalanceAssetData bad, List<BalanceAssetData> allBads, List<SoDuKy> soDuKyDs) {
		if (bad == null)
			return null;

		try {
			// Lấy giá trị kỳ trước làm giá trị đầu kỳ
			// bad = balanceSheetDAO.getPeriodEndValue(bad);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Tính giá trị đầu kỳ / cuối kỳ
		if (bad.getChilds() != null && bad.getChilds().size() > 0) {
			Iterator<BalanceAssetData> iter = bad.getChilds().iterator();
			while (iter.hasNext()) {
				BalanceAssetData childBad = iter.next();
				childBad = calCulcateBs(childBad, allBads, soDuKyDs);
				bad.setEndValue(bad.getEndValue() + childBad.getEndValue());
				bad.setStartValue(bad.getStartValue() + childBad.getStartValue());
			}
		} else {
			if (bad.getAsset() != null && bad.getAsset().getTaiKhoanDs() != null) {
				bad.setStartValue(0);
				bad.setEndValue(0);
				// Lấy thông tin chi tiết của chỉ tiêu hiện từ danh sách tổng thể đã lấy trước
				Iterator<LoaiTaiKhoan> taiKhoanIter = bad.getAsset().getTaiKhoanDs().iterator();
				while (taiKhoanIter.hasNext()) {
					try {
						LoaiTaiKhoan loaiTaiKhoan = taiKhoanIter.next();

						// Tính giá trị đầu kỳ
						if (soDuKyDs != null) {
							Iterator<SoDuKy> soDuKyIter = soDuKyDs.iterator();
							while (soDuKyIter.hasNext()) {
								SoDuKy soDuKy = soDuKyIter.next();

								if (soDuKy.getLoaiTaiKhoan().equals(loaiTaiKhoan)) {
									if (loaiTaiKhoan.isLuongTinh()) {
										if (loaiTaiKhoan.getSoDuGiaTri() == LoaiTaiKhoan.NO) {
											bad.setStartValue(bad.getStartValue() + bad.getAsset().getSoDu()
													* loaiTaiKhoan.getSoDuGiaTri() * soDuKy.getNoDauKy());
										} else {
											bad.setStartValue(bad.getStartValue() + bad.getAsset().getSoDu()
													* loaiTaiKhoan.getSoDuGiaTri() * soDuKy.getCoDauKy());
										}
									} else {
										bad.setStartValue(bad.getStartValue() + bad.getAsset().getSoDu()
												* loaiTaiKhoan.getSoDuGiaTri() * loaiTaiKhoan.getSoDuGiaTri()
												* (soDuKy.getCoDauKy() - soDuKy.getNoDauKy()));
									}
									break;
								}
							}
						}

						// Tính giá trị phát sinh kỳ
						Iterator<BalanceAssetData> iter = allBads.iterator();
						while (iter.hasNext()) {
							BalanceAssetData balanceAssetData = iter.next();

							if (balanceAssetData.equals(bad)
									&& balanceAssetData.getAsset().getTaiKhoanDs().contains(loaiTaiKhoan)) {
								bad.setEndValue(bad.getEndValue() + bad.getAsset().getSoDu()
										* loaiTaiKhoan.getSoDuGiaTri() * balanceAssetData.getEndValue());
							}
						}
					} catch (Exception e) {
						// e.printStackTrace();
					}
				}

				// Tính giá trị cuối kỳ
				bad.setEndValue(bad.getEndValue() + bad.getStartValue());
			}
		}

		logger.info(bad);

		// Cập nhật vào cơ sở dữ liệu
		balanceSheetDAO.insertOrUpdateBA(bad);

		return bad;
	}

	@RequestMapping("/bctc/kqhdkd/danhsach")
	public String saleResults(@ModelAttribute("mainFinanceForm") BalanceAssetForm balanceSheetForm, Model model) {
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
				selectedAssetPeriods = new ArrayList<>();
				Date period = Utils.getStartPeriod(balanceSheetForm.getKyKeToan().getBatDau(),
						balanceSheetForm.getPeriodType());
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

			List<BalanceAssetData> bads = balanceSheetDAO.listSRsByAssetCodesAndDates(selectedAssetCodes,
					selectedAssetPeriods, balanceSheetForm.getPeriodType());
			List<String> assetCodes = balanceSheetDAO.listSRAssetsCodes();
			List<Date> assetPeriods = balanceSheetDAO.listSRAssetsPeriods(balanceSheetForm.getPeriodType());

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
			model.addAttribute("tab", "tabBKQHDKD");

			return "saleResult";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/bctc/kqhdkd/capnhat")
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
			// Lấy giá trị kỳ trước làm giá trị đầu kỳ
			bad = balanceSheetDAO.getSRPeriodEndValue(bad);
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
			logger.info("Tính giá trị chỉ tiêu " + bad.getAsset().getAssetCode() + " từ tài khoản cụ thể");
			if (bad.getAsset() != null && bad.getAsset().getTaiKhoanDs() != null) {
				// Lấy thông tin chi tiết của chỉ tiêu hiện từ danh sách tổng thể đã lấy trước
				Iterator<LoaiTaiKhoan> taiKhoanIter = bad.getAsset().getTaiKhoanDs().iterator();
				while (taiKhoanIter.hasNext()) {
					try {
						LoaiTaiKhoan loaiTaiKhoan = taiKhoanIter.next();

						Iterator<BalanceAssetData> iter = allBads.iterator();
						while (iter.hasNext()) {
							BalanceAssetData balanceAssetData = iter.next();
							balanceAssetData.setPeriodType(bad.getPeriodType());
							balanceAssetData.setPeriod(bad.getPeriod());

							if (balanceAssetData.equals(bad)
									&& balanceAssetData.getAsset().getTaiKhoanDs().contains(loaiTaiKhoan)) {
								logger.info("Tài khoản " + loaiTaiKhoan + ": " + balanceAssetData.getEndValue());
								bad.setEndValue(bad.getEndValue() + balanceAssetData.getEndValue());
								break;
							}
						}
					} catch (Exception e) {

					}
				}
			}
		}

		// Cập nhật vào cơ sở dữ liệu
		balanceSheetDAO.insertOrUpdateSR(bad);

		return bad;
	}

	@RequestMapping("/bctc/capnhatdulieu")
	public String update(Model model) {
		// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
		model.addAttribute("kpiGroups", dungChung.getKpiGroups());

		model.addAttribute("tab", "tabCNDL");
		return "update";
	}

	@RequestMapping(value = "/bctc/luutrudulieu", method = RequestMethod.POST)
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

	@RequestMapping("/bctc/cdkt/chitieu/danhsach")
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

	@RequestMapping("/bctc/cdkt/chitieu/taomoi")
	public String taoMoibalanceSheetCode(@ModelAttribute("mainFinanceForm") BalanceAssetForm form, Model model) {

		return "taoMoibalanceSheetCode";
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

			List<SoDuKy> soDuKyDs = kyKeToanDAO.danhSachSoDuKy(form.getKyKeToan().getMaKyKt());
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
				List<TaiKhoan> tongPsDs = soKeToanDAO.tongPhatSinh(kyKt.getDau(), kyKt.getCuoi());
				List<TaiKhoan> dauKyDs = soKeToanDAO.tongPhatSinh(form.getKyKeToan().getBatDau(), kyKtTruoc.getCuoi());

				dauKyDs = tronNoCoDauKy(dauKyDs, soDuKyDs);
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

	private List<TaiKhoan> tronNoCoDauKy(List<TaiKhoan> dauKyDs, List<SoDuKy> soDuKyDs) {
		if (dauKyDs == null || soDuKyDs == null) {
			return dauKyDs;
		}

		// Trộn nợ/có đầu kỳ
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
						soTien.setSoTien(soTien.getGiaTri() / soTien.getLoaiTien().getBanRa());
						taiKhoan.setSoTien(soTien);
					} else {
						soTien.setGiaTri(soTien.getGiaTri() + soDuKy.getCoDauKy());
						soTien.setSoTien(soTien.getGiaTri() / soTien.getLoaiTien().getBanRa());
						taiKhoan.setSoTien(soTien);
					}
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
					tien.setSoTien(tien.getGiaTri() / tien.getLoaiTien().getBanRa());

					taiKhoan.setLoaiTaiKhoan(soDuKy.getLoaiTaiKhoan());
					taiKhoan.setSoTien(tien);
					taiKhoan.setSoDu(LoaiTaiKhoan.NO);

					dauKyDs.add(taiKhoan);
				}

				if (soDuKy.getCoDauKy() > 0) {
					TaiKhoan taiKhoan = new TaiKhoan();

					Tien tien = new Tien();
					tien.setGiaTri(soDuKy.getCoDauKy());
					tien.setSoTien(tien.getGiaTri() / tien.getLoaiTien().getBanRa());

					taiKhoan.setLoaiTaiKhoan(soDuKy.getLoaiTaiKhoan());
					taiKhoan.setSoTien(tien);
					taiKhoan.setSoDu(LoaiTaiKhoan.CO);

					dauKyDs.add(taiKhoan);
				}
			}
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
}
