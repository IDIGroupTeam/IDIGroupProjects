package com.idi.finance.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.idi.finance.bean.BalanceSheet;
import com.idi.finance.charts.KpiBarChart;
import com.idi.finance.charts.KpiChartProcessor;
import com.idi.finance.charts.KpiLineChart;
import com.idi.finance.dao.BalanceSheetDAO;
import com.idi.finance.form.BalanceSheetForm;
import com.idi.finance.kpi.KPIMeasures;
import com.idi.finance.kpi.NetProfitMargin;
import com.idi.finance.kpi.OperatingCycle;
import com.idi.finance.kpi.QuickRatio;
import com.idi.finance.kpi.ReceivableTurnover;
import com.idi.finance.kpi.CashConversionCycle;
import com.idi.finance.kpi.CashRatio;
import com.idi.finance.kpi.CurrentRatio;
import com.idi.finance.kpi.DebtRatio;
import com.idi.finance.kpi.FinancialLeverage;
import com.idi.finance.utils.ExcelProcessor;
import com.idi.finance.utils.Utils;

@Controller
public class BalanceSheetController {
	private static final Logger logger = Logger.getLogger(BalanceSheetController.class);

	@Autowired
	BalanceSheetDAO balanceSheetDAO;

	@RequestMapping("/")
	public String finance(Model model) {
		return "forward:/kntttucthoi";
	}

	@RequestMapping("/kntttucthoi")
	public String kpiCurrentRatio(Model model) {
		// Vẽ biểu đồ Khả năng thanh toán tức thời theo tất cả các kỳ (tháng) trong năm
		// Với từng kỳ, lấy tài sản ngắn hạn (100) chia cho nợ ngắn hạn (310)

		// Get list current assets and current liabilites for all period in a year
		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);
		List<BalanceSheet> currentAssets = balanceSheetDAO.listBSsByAssetsCodeAndYear("100", currentYear);
		logger.info("currentAssets " + currentAssets.size());
		List<BalanceSheet> currentLiabilities = balanceSheetDAO.listBSsByAssetsCodeAndYear("310", currentYear);
		logger.info("currentLiabilities " + currentLiabilities.size());

		// Calculate list of current ratios
		double threshold = 1.0;
		HashMap<Date, CurrentRatio> currentRatios = KPIMeasures.currentRatio(currentAssets, currentLiabilities,
				threshold);

		// Start drawing charts:
		KpiBarChart currentRatioBarChart = new KpiBarChart(currentRatios);
		KpiLineChart currentRatioLineChart = new KpiLineChart(currentRatios, threshold);
		KpiChartProcessor currentRatioChartProcessor = new KpiChartProcessor();

		// Sorting list of current ratios by period (month)
		SortedMap<Date, CurrentRatio> sortedCurrentRatios = new TreeMap<Date, CurrentRatio>(new Comparator<Date>() {
			@Override
			public int compare(Date date1, Date date2) {
				return date1.compareTo(date2);
			}
		});
		sortedCurrentRatios.putAll(currentRatios);

		model.addAttribute("currentRatios", sortedCurrentRatios);
		model.addAttribute("currentRatioBarChart", currentRatioBarChart);
		model.addAttribute("currentRatioLineChart", currentRatioLineChart);
		model.addAttribute("currentRatioChartProcessor", currentRatioChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "kntttucthoi");
		model.addAttribute("tab", "tabKNTT");
		return "kpiCurrentRatio";
	}

	@RequestMapping("/knttnhanh")
	public String kpiQuickRatio(Model model) {
		// Vẽ biểu đồ Khả năng thanh toán nhanh theo tất cả các kỳ (tháng) trong năm
		// Với từng kỳ, lấy tài sản ngắn hạn (100) trừ hàng tồn kho (140),
		// tất cả chia cho nợ ngắn hạn (310)

		// Get list current assets, inventories and current liabilites for all period in
		// a year
		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);
		List<BalanceSheet> currentAssets = balanceSheetDAO.listBSsByAssetsCodeAndYear("100", currentYear);
		logger.info("currentAssets " + currentAssets.size());
		List<BalanceSheet> inventories = balanceSheetDAO.listBSsByAssetsCodeAndYear("140", currentYear);
		logger.info("inventories " + inventories.size());
		List<BalanceSheet> currentLiabilities = balanceSheetDAO.listBSsByAssetsCodeAndYear("310", currentYear);
		logger.info("currentLiabilities " + currentLiabilities.size());

		// Calculate list of quick ratios
		double threshold = 0.0;
		HashMap<Date, QuickRatio> quickRatios = KPIMeasures.quickRatio(currentAssets, inventories, currentLiabilities,
				threshold);

		// Start drawing charts:
		KpiBarChart quickRatioBarChart = new KpiBarChart(quickRatios);
		KpiLineChart quickRatioLineChart = new KpiLineChart(quickRatios, threshold);
		KpiChartProcessor quickRatioChartProcessor = new KpiChartProcessor();

		// Sorting list of quick ratios by period (month)
		SortedMap<Date, QuickRatio> sortedQuickRatios = new TreeMap<Date, QuickRatio>(new Comparator<Date>() {
			@Override
			public int compare(Date date1, Date date2) {
				return date1.compareTo(date2);
			}
		});
		sortedQuickRatios.putAll(quickRatios);

		model.addAttribute("quickRatios", sortedQuickRatios);
		model.addAttribute("quickRatioBarChart", quickRatioBarChart);
		model.addAttribute("quickRatioLineChart", quickRatioLineChart);
		model.addAttribute("quickRatioChartProcessor", quickRatioChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "knttnhanh");
		model.addAttribute("tab", "tabKNTT");
		return "kpiQuickRatio";
	}

	@RequestMapping("/knttbangtien")
	public String kpiCashRatio(Model model) {
		// Vẽ biểu đồ Khả năng thanh toán bằng tiền theo tất cả các kỳ (tháng) trong
		// năm.
		// Với từng kỳ, lấy tiền & tương đương tiền (110) chia cho nợ ngắn hạn (310)

		// Get list of cashes & equivalents and current liabilites for all period in a
		// year
		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);
		List<BalanceSheet> cashEquivalents = balanceSheetDAO.listBSsByAssetsCodeAndYear("110", currentYear);
		logger.info("cashEquivalents " + cashEquivalents.size());
		List<BalanceSheet> currentLiabilities = balanceSheetDAO.listBSsByAssetsCodeAndYear("310", currentYear);
		logger.info("currentLiabilities " + currentLiabilities.size());

		// Calculate list of cash ratios
		double threshold = 0.1;
		HashMap<Date, CashRatio> cashRatios = KPIMeasures.cashRatio(cashEquivalents, currentLiabilities, threshold);

		// Start drawing charts:
		KpiBarChart cashRatioBarChart = new KpiBarChart(cashRatios);
		KpiLineChart cashRatioLineChart = new KpiLineChart(cashRatios, threshold);
		KpiChartProcessor cashRatioChartProcessor = new KpiChartProcessor();

		// Sorting list of cash ratios by period (month)
		SortedMap<Date, CashRatio> sortedCashRatios = new TreeMap<Date, CashRatio>(new Comparator<Date>() {
			@Override
			public int compare(Date date1, Date date2) {
				return date1.compareTo(date2);
			}
		});
		sortedCashRatios.putAll(cashRatios);

		model.addAttribute("cashRatios", sortedCashRatios);
		model.addAttribute("cashRatioBarChart", cashRatioBarChart);
		model.addAttribute("cashRatioLineChart", cashRatioLineChart);
		model.addAttribute("cashRatioChartProcessor", cashRatioChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "knttbangtien");
		model.addAttribute("tab", "tabKNTT");
		return "kpiCashRatio";
	}

	@RequestMapping("/vqkhoanphaithu")
	public String kpiReceivableTurnOver(Model model) {
		// Vẽ biểu đồ vòng quay khoản phải thu theo tất cả các kỳ (tháng) trong năm. Với
		// từng kỳ, lấy doanh thu thuần (10) chia phải thu bình quân (phải thu đầu kỳ
		// bình quân với phải thu cuối kỳ - (130 + 210))

		// Get list of receivables & total operating revenues for all period in a year
		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);
		List<BalanceSheet> totalOperatingRevenues = balanceSheetDAO.listSRsByAssetsCodeAndYear("10", currentYear);
		logger.info("totalOperatingRevenues " + totalOperatingRevenues.size());
		List<BalanceSheet> shortReceivables = balanceSheetDAO.listBSsByAssetsCodeAndYear("130", currentYear);
		logger.info("shortReceivables " + shortReceivables.size());
		List<BalanceSheet> longReceivables = balanceSheetDAO.listBSsByAssetsCodeAndYear("210", currentYear);
		logger.info("longReceivables " + longReceivables.size());

		// Calculate list of receivable turnovers
		double threshold = 1.0;
		HashMap<Date, ReceivableTurnover> receivableTurnovers = KPIMeasures.receivableTurnover(totalOperatingRevenues,
				shortReceivables, longReceivables, threshold);

		// Start drawing charts:
		KpiBarChart receivableTurnoverBarChart = new KpiBarChart(receivableTurnovers);
		KpiLineChart receivableTurnoverLineChart = new KpiLineChart(receivableTurnovers, threshold);
		KpiChartProcessor receivableTurnoverChartProcessor = new KpiChartProcessor();

		// Sorting list of receivable turnovers by period (month)
		SortedMap<Date, ReceivableTurnover> sortedReceivableTurnovers = new TreeMap<Date, ReceivableTurnover>(
				new Comparator<Date>() {
					@Override
					public int compare(Date date1, Date date2) {
						return date1.compareTo(date2);
					}
				});
		sortedReceivableTurnovers.putAll(receivableTurnovers);

		model.addAttribute("receivableTurnovers", sortedReceivableTurnovers);
		model.addAttribute("receivableTurnoverBarChart", receivableTurnoverBarChart);
		model.addAttribute("receivableTurnoverLineChart", receivableTurnoverLineChart);
		model.addAttribute("receivableTurnoverChartProcessor", receivableTurnoverChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "vqkhoanphaithu");
		model.addAttribute("tab", "tabKNHD");
		return "kpiReceivableTurnOver";
	}

	// Chức năng này tương tự vqkhoanphaithu, nên dùng lại code của vqkhoanphaithu
	@RequestMapping("/kttienbinhquan")
	public String kpiAvgReceivablePeriod(Model model) {
		// Vẽ biểu đồ kỳ thu tiền bình quân theo tất cả các kỳ (tháng) trong năm. Với
		// từng kỳ, lấy doanh thu thuần (10) chia phải thu bình quân (phải thu đầu kỳ
		// bình quân với phải thu cuối kỳ - (130+210)), sau đó lấy 365 số ngày trong năm
		// (trong kỳ bình quân) chia cho giá trị nhận được.

		// Get list of receivables & total operating revenues for all period in a year
		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);
		List<BalanceSheet> totalOperatingRevenues = balanceSheetDAO.listSRsByAssetsCodeAndYear("10", currentYear);
		logger.info("totalOperatingRevenues " + totalOperatingRevenues.size());
		List<BalanceSheet> shortReceivables = balanceSheetDAO.listBSsByAssetsCodeAndYear("130", currentYear);
		logger.info("shortReceivables " + shortReceivables.size());
		List<BalanceSheet> longReceivables = balanceSheetDAO.listBSsByAssetsCodeAndYear("210", currentYear);
		logger.info("longReceivables " + longReceivables.size());

		// Calculate list of receivable turnovers
		double threshold = 1.0;
		int numberOfPeriods = 365;
		HashMap<Date, ReceivableTurnover> receivableTurnovers = KPIMeasures.avgReceivablePeriod(totalOperatingRevenues,
				shortReceivables, longReceivables, threshold, numberOfPeriods);

		// Start drawing charts:
		KpiBarChart receivableTurnoverBarChart = new KpiBarChart(receivableTurnovers);
		KpiLineChart receivableTurnoverLineChart = new KpiLineChart(receivableTurnovers, threshold);
		KpiChartProcessor receivableTurnoverChartProcessor = new KpiChartProcessor();

		// Sorting list of receivable turnovers by period (month)
		SortedMap<Date, ReceivableTurnover> sortedReceivableTurnovers = new TreeMap<Date, ReceivableTurnover>(
				new Comparator<Date>() {
					@Override
					public int compare(Date date1, Date date2) {
						return date1.compareTo(date2);
					}
				});
		sortedReceivableTurnovers.putAll(receivableTurnovers);

		model.addAttribute("receivableTurnovers", sortedReceivableTurnovers);
		model.addAttribute("receivableTurnoverBarChart", receivableTurnoverBarChart);
		model.addAttribute("receivableTurnoverLineChart", receivableTurnoverLineChart);
		model.addAttribute("receivableTurnoverChartProcessor", receivableTurnoverChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "kttienbinhquan");
		model.addAttribute("tab", "tabKNHD");
		return "kpiAvgReceivablePeriod";
	}

	// Chức năng này tương tự vqkhoanphaithu, nên dùng lại code của vqkhoanphaithu
	@RequestMapping("/vqhangtonkho_sosach")
	public String kpiInventoriesTurnOverByDocument(Model model) {
		// Vẽ biểu đồ vòng quay hàng tồn kho theo sổ sách theo tất cả các kỳ (tháng)
		// trong năm. Với từng kỳ, lấy giá vốn hàng bán (11) chia hàng tồn kho bình quân
		// (hàng tồn kho đầu kỳ bình quân với hàng tồn kho cuối kỳ - 140)

		// Get list of cost of goods solds & inventories for all period in a year
		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);
		List<BalanceSheet> costOfSolds = balanceSheetDAO.listSRsByAssetsCodeAndYear("11", currentYear);
		logger.info("Cost of good solds " + costOfSolds.size());
		List<BalanceSheet> inventories = balanceSheetDAO.listBSsByAssetsCodeAndYear("140", currentYear);
		logger.info("inventories " + inventories.size());

		// Calculate list of inventories turnovers
		double threshold = 1.0;
		HashMap<Date, ReceivableTurnover> receivableTurnovers = KPIMeasures.receivableTurnover(costOfSolds, inventories,
				threshold);

		// Start drawing charts:
		KpiBarChart receivableTurnoverBarChart = new KpiBarChart(receivableTurnovers);
		KpiLineChart receivableTurnoverLineChart = new KpiLineChart(receivableTurnovers, threshold);
		KpiChartProcessor receivableTurnoverChartProcessor = new KpiChartProcessor();

		// Sorting list of receivable turnovers by period (month)
		SortedMap<Date, ReceivableTurnover> sortedReceivableTurnovers = new TreeMap<Date, ReceivableTurnover>(
				new Comparator<Date>() {
					@Override
					public int compare(Date date1, Date date2) {
						return date1.compareTo(date2);
					}
				});
		sortedReceivableTurnovers.putAll(receivableTurnovers);

		model.addAttribute("receivableTurnovers", sortedReceivableTurnovers);
		model.addAttribute("receivableTurnoverBarChart", receivableTurnoverBarChart);
		model.addAttribute("receivableTurnoverLineChart", receivableTurnoverLineChart);
		model.addAttribute("receivableTurnoverChartProcessor", receivableTurnoverChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "vqhangtonkho_sosach");
		model.addAttribute("tab", "tabKNHD");
		return "kpiInventoriesTurnOverByDocument";
	}

	// Chức năng này tương tự vqkhoanphaithu, nên dùng lại code của vqkhoanphaithu
	@RequestMapping("/vqhangtonkho_thitruong")
	public String kpiInventoriesTurnOverByMarket(Model model) {
		// Vẽ biểu đồ vòng quay hàng tồn kho theo giá thị trường theo tất cả các kỳ
		// (tháng) trong năm. Với từng kỳ, lấy doanh thu thuần (10) chia hàng tồn kho
		// bình quân (hàng tồn kho đầu kỳ bình quân với hàng tồn kho cuối kỳ - 140)

		// Get list of cost of goods solds & inventories for all period in a year
		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);
		List<BalanceSheet> totalOperatingRevenues = balanceSheetDAO.listSRsByAssetsCodeAndYear("10", currentYear);
		logger.info("totalOperatingRevenues " + totalOperatingRevenues.size());
		List<BalanceSheet> inventories = balanceSheetDAO.listBSsByAssetsCodeAndYear("140", currentYear);
		logger.info("inventories " + inventories.size());

		// Calculate list of inventories turnovers
		double threshold = 1.0;
		HashMap<Date, ReceivableTurnover> receivableTurnovers = KPIMeasures.receivableTurnover(totalOperatingRevenues,
				inventories, threshold);

		// Start drawing charts:
		KpiBarChart receivableTurnoverBarChart = new KpiBarChart(receivableTurnovers);
		KpiLineChart receivableTurnoverLineChart = new KpiLineChart(receivableTurnovers, threshold);
		KpiChartProcessor receivableTurnoverChartProcessor = new KpiChartProcessor();

		// Sorting list of receivable turnovers by period (month)
		SortedMap<Date, ReceivableTurnover> sortedReceivableTurnovers = new TreeMap<Date, ReceivableTurnover>(
				new Comparator<Date>() {
					@Override
					public int compare(Date date1, Date date2) {
						return date1.compareTo(date2);
					}
				});
		sortedReceivableTurnovers.putAll(receivableTurnovers);

		model.addAttribute("receivableTurnovers", sortedReceivableTurnovers);
		model.addAttribute("receivableTurnoverBarChart", receivableTurnoverBarChart);
		model.addAttribute("receivableTurnoverLineChart", receivableTurnoverLineChart);
		model.addAttribute("receivableTurnoverChartProcessor", receivableTurnoverChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "vqhangtonkho_thitruong");
		model.addAttribute("tab", "tabKNHD");
		return "kpiInventoriesTurnOverByMarket";
	}

	// Chức năng này tương tự kttienbinhquan, nên dùng lại code của kttienbinhquan
	@RequestMapping("/sntonkhobinhquan_sosach")
	public String kpiDaysInInventoriesByDocument(Model model) {
		// Vẽ biểu đồ số ngày tồn kho bình quân theo sổ sách theo tất cả các kỳ (tháng)
		// trong năm. Với từng kỳ, lấy giá vốn hàng bán (11) chia hàng tồn kho bình quân
		// (hàng tồn kho đầu kỳ bình quân với hàng tồn kho cuối kỳ - 140), lấy 365 (số
		// ngày trong năm-kỳ) chia cho kết quả thu được.

		// // Get list of cost of goods solds & inventories for all period in a year
		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);
		List<BalanceSheet> costOfSolds = balanceSheetDAO.listSRsByAssetsCodeAndYear("11", currentYear);
		logger.info("Cost of good solds " + costOfSolds.size());
		List<BalanceSheet> inventories = balanceSheetDAO.listBSsByAssetsCodeAndYear("140", currentYear);
		logger.info("inventories " + inventories.size());

		// Calculate list of inventories turnovers
		double threshold = 1.0;
		int numberOfPeriods = 365;
		HashMap<Date, ReceivableTurnover> receivableTurnovers = KPIMeasures.avgReceivablePeriod(costOfSolds,
				inventories, threshold, numberOfPeriods);

		// Start drawing charts:
		KpiBarChart receivableTurnoverBarChart = new KpiBarChart(receivableTurnovers);
		KpiLineChart receivableTurnoverLineChart = new KpiLineChart(receivableTurnovers, threshold);
		KpiChartProcessor receivableTurnoverChartProcessor = new KpiChartProcessor();

		// Sorting list of receivable turnovers by period (month)
		SortedMap<Date, ReceivableTurnover> sortedReceivableTurnovers = new TreeMap<Date, ReceivableTurnover>(
				new Comparator<Date>() {
					@Override
					public int compare(Date date1, Date date2) {
						return date1.compareTo(date2);
					}
				});
		sortedReceivableTurnovers.putAll(receivableTurnovers);

		model.addAttribute("receivableTurnovers", sortedReceivableTurnovers);
		model.addAttribute("receivableTurnoverBarChart", receivableTurnoverBarChart);
		model.addAttribute("receivableTurnoverLineChart", receivableTurnoverLineChart);
		model.addAttribute("receivableTurnoverChartProcessor", receivableTurnoverChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "sntonkhobinhquan_sosach");
		model.addAttribute("tab", "tabKNHD");
		return "kpiDaysInInventoriesByDocument";
	}

	// Chức năng này tương tự kttienbinhquan, nên dùng lại code của kttienbinhquan
	@RequestMapping("/sntonkhobinhquan_thitruong")
	public String kpiDaysInInventoriesByMarket(Model model) {
		// Vẽ biểu đồ số ngày tồn kho bình quân theo giá thị trường theo tất cả các kỳ
		// (tháng) trong năm. Với từng kỳ, lấy doanh thu thuần (10) chia hàng tồn kho
		// bình quân (hàng tồn kho đầu kỳ bình quân với hàng tồn kho cuối kỳ - 140), lấy
		// 365 (số ngày trong năm-kỳ) chia cho kết quả thu được.

		// // Get list of cost of goods solds & inventories for all period in a year
		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);
		List<BalanceSheet> costOfSolds = balanceSheetDAO.listSRsByAssetsCodeAndYear("10", currentYear);
		logger.info("Cost of good solds " + costOfSolds.size());
		List<BalanceSheet> inventories = balanceSheetDAO.listBSsByAssetsCodeAndYear("140", currentYear);
		logger.info("inventories " + inventories.size());

		// Calculate list of inventories turnovers
		double threshold = 1.0;
		int numberOfPeriods = 365;
		HashMap<Date, ReceivableTurnover> receivableTurnovers = KPIMeasures.avgReceivablePeriod(costOfSolds,
				inventories, threshold, numberOfPeriods);

		// Start drawing charts:
		KpiBarChart receivableTurnoverBarChart = new KpiBarChart(receivableTurnovers);
		KpiLineChart receivableTurnoverLineChart = new KpiLineChart(receivableTurnovers, threshold);
		KpiChartProcessor receivableTurnoverChartProcessor = new KpiChartProcessor();

		// Sorting list of receivable turnovers by period (month)
		SortedMap<Date, ReceivableTurnover> sortedReceivableTurnovers = new TreeMap<Date, ReceivableTurnover>(
				new Comparator<Date>() {
					@Override
					public int compare(Date date1, Date date2) {
						return date1.compareTo(date2);
					}
				});
		sortedReceivableTurnovers.putAll(receivableTurnovers);

		model.addAttribute("receivableTurnovers", sortedReceivableTurnovers);
		model.addAttribute("receivableTurnoverBarChart", receivableTurnoverBarChart);
		model.addAttribute("receivableTurnoverLineChart", receivableTurnoverLineChart);
		model.addAttribute("receivableTurnoverChartProcessor", receivableTurnoverChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "sntonkhobinhquan_thitruong");
		model.addAttribute("tab", "tabKNHD");
		return "kpiDaysInInventoriesByMarket";
	}

	// Chức năng này tương tự kttienbinhquan, nên dùng lại code của kttienbinhquan
	@RequestMapping("/chukyhoatdong_sosach")
	public String kpiOperatingCycleByDocument(Model model) {
		// Vẽ biểu đồ chu kỳ hoạt động của doanh nghiệp theo giá trị sổ sách theo tất cả
		// các kỳ (tháng) trong năm. Với từng kỳ, lấy kỳ thu tiền bình quân cộng số ngày
		// tồn kho bình quân theo giá trị sổ sách: (2 chỉ số đã tính ở các đồ thị trên)
		// 365*(Start 130 + End 130)/(2*(10)) + 365* (Start 140 + End 140)/(2*(11))

		// Get list of receivables & total operating revenues for all period in a year
		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);
		double threshold = 1.0;
		int numberOfPeriods = 365;

		List<BalanceSheet> totalOperatingRevenues = balanceSheetDAO.listSRsByAssetsCodeAndYear("10", currentYear);
		logger.info("totalOperatingRevenues " + totalOperatingRevenues.size());
		List<BalanceSheet> shortReceivables = balanceSheetDAO.listBSsByAssetsCodeAndYear("130", currentYear);
		logger.info("shortReceivables " + shortReceivables.size());
		List<BalanceSheet> longReceivables = balanceSheetDAO.listBSsByAssetsCodeAndYear("210", currentYear);
		logger.info("longReceivables " + longReceivables.size());

		// Get list of cost of goods solds & inventories for all period in a year
		List<BalanceSheet> costOfSolds = balanceSheetDAO.listSRsByAssetsCodeAndYear("11", currentYear);
		logger.info("Cost of good solds " + costOfSolds.size());
		List<BalanceSheet> inventories = balanceSheetDAO.listBSsByAssetsCodeAndYear("140", currentYear);
		logger.info("inventories " + inventories.size());

		// Kỳ thu tiền bình quân
		HashMap<Date, ReceivableTurnover> receivableTurnovers = KPIMeasures.avgReceivablePeriod(totalOperatingRevenues,
				shortReceivables, longReceivables, threshold, numberOfPeriods);

		// Số ngày tồn kho bình quân theo giá trị sổ sách
		HashMap<Date, ReceivableTurnover> dayInInventories = KPIMeasures.avgReceivablePeriod(costOfSolds, inventories,
				threshold, numberOfPeriods);

		// Chu kỳ hoạt động của doanh nghiệp theo giá trị sổ sách
		HashMap<Date, OperatingCycle> operatingCycles = KPIMeasures.operatingCycle(receivableTurnovers,
				dayInInventories, threshold);

		// Start drawing charts:
		KpiBarChart operatingCycleBarChart = new KpiBarChart(operatingCycles);
		KpiLineChart operatingCycleLineChart = new KpiLineChart(operatingCycles, threshold);
		KpiChartProcessor operatingCycleChartProcessor = new KpiChartProcessor();

		// Sorting list of operating cycles by period (month)
		SortedMap<Date, OperatingCycle> sortedOperatingCycles = new TreeMap<Date, OperatingCycle>(
				new Comparator<Date>() {
					@Override
					public int compare(Date date1, Date date2) {
						return date1.compareTo(date2);
					}
				});
		sortedOperatingCycles.putAll(operatingCycles);

		model.addAttribute("operatingCycles", sortedOperatingCycles);
		model.addAttribute("operatingCycleBarChart", operatingCycleBarChart);
		model.addAttribute("operatingCycleLineChart", operatingCycleLineChart);
		model.addAttribute("operatingCycleChartProcessor", operatingCycleChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "chukyhoatdong_sosach");
		model.addAttribute("tab", "tabKNHD");
		return "kpiOperatingCycleByDocument";
	}

	// Chức năng này tương tự kttienbinhquan, nên dùng lại code của kttienbinhquan
	@RequestMapping("/chukyhoatdong_thitruong")
	public String kpiOperatingCycleByMarket(Model model) {
		// Vẽ biểu đồ chu kỳ hoạt động của doanh nghiệp theo giá trị thị trường theo
		// các kỳ (tháng) trong năm. Với từng kỳ, lấy kỳ thu tiền bình quân cộng số ngày
		// tồn kho bình quân theo giá thị trường: (2 chỉ số đã tính ở các đồ thị trên)
		// 365*(Start 130 + End 130)/(2*(10)) + 365* (Start 140 + End 140)/(2*(10))

		// Get list of receivables & total operating revenues for all period in a year
		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);
		double threshold = 1.0;
		int numberOfPeriods = 365;

		List<BalanceSheet> totalOperatingRevenues = balanceSheetDAO.listSRsByAssetsCodeAndYear("10", currentYear);
		logger.info("totalOperatingRevenues " + totalOperatingRevenues.size());
		List<BalanceSheet> shortReceivables = balanceSheetDAO.listBSsByAssetsCodeAndYear("130", currentYear);
		logger.info("shortReceivables " + shortReceivables.size());
		List<BalanceSheet> longReceivables = balanceSheetDAO.listBSsByAssetsCodeAndYear("210", currentYear);
		logger.info("longReceivables " + longReceivables.size());

		// Get list of inventories for all period in a year
		List<BalanceSheet> inventories = balanceSheetDAO.listBSsByAssetsCodeAndYear("140", currentYear);
		logger.info("inventories " + inventories.size());

		// Kỳ thu tiền bình quân
		HashMap<Date, ReceivableTurnover> receivableTurnovers = KPIMeasures.avgReceivablePeriod(totalOperatingRevenues,
				shortReceivables, longReceivables, threshold, numberOfPeriods);

		// Số ngày tồn kho bình quân theo giá trị thị trường
		HashMap<Date, ReceivableTurnover> dayInInventories = KPIMeasures.avgReceivablePeriod(totalOperatingRevenues,
				inventories, threshold, numberOfPeriods);

		// Chu kỳ hoạt động của doanh nghiệp theo giá trị sổ sách
		HashMap<Date, OperatingCycle> operatingCycles = KPIMeasures.operatingCycle(receivableTurnovers,
				dayInInventories, threshold);

		// Start drawing charts:
		KpiBarChart operatingCycleBarChart = new KpiBarChart(operatingCycles);
		KpiLineChart operatingCycleLineChart = new KpiLineChart(operatingCycles, threshold);
		KpiChartProcessor operatingCycleChartProcessor = new KpiChartProcessor();

		// Sorting list of operating cycles by period (month)
		SortedMap<Date, OperatingCycle> sortedOperatingCycles = new TreeMap<Date, OperatingCycle>(
				new Comparator<Date>() {
					@Override
					public int compare(Date date1, Date date2) {
						return date1.compareTo(date2);
					}
				});
		sortedOperatingCycles.putAll(operatingCycles);

		model.addAttribute("operatingCycles", sortedOperatingCycles);
		model.addAttribute("operatingCycleBarChart", operatingCycleBarChart);
		model.addAttribute("operatingCycleLineChart", operatingCycleLineChart);
		model.addAttribute("operatingCycleChartProcessor", operatingCycleChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "chukyhoatdong_sosach");
		model.addAttribute("action", "chukyhoatdong_thitruong");
		model.addAttribute("tab", "tabKNHD");
		return "kpiOperatingCycleByMarket";
	}

	// Chức năng này tương tự vqkhoanphaithu, nên dùng lại code của vqkhoanphaithu
	@RequestMapping("/vqkhoanphaitra")
	public String kpiPaymentTurnover(Model model) {
		// Vẽ biểu đồ Vòng quay khoản phải trả theo các kỳ (tháng) trong năm. Với từng
		// kỳ, lấy giá vốn hàng bán (11) chia phải trả bình quân của phải trả đầu kỳ và
		// cuối kỳ (310).

		// Get list of receivables & total operating revenues for all period in a year
		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);
		List<BalanceSheet> totalOperatingRevenues = balanceSheetDAO.listSRsByAssetsCodeAndYear("11", currentYear);
		logger.info("totalOperatingRevenues " + totalOperatingRevenues.size());
		List<BalanceSheet> shortReceivables = balanceSheetDAO.listBSsByAssetsCodeAndYear("310", currentYear);
		logger.info("shortReceivables " + shortReceivables.size());
		List<BalanceSheet> longReceivables = balanceSheetDAO.listBSsByAssetsCodeAndYear("330", currentYear);
		logger.info("longReceivables " + longReceivables.size());

		// Calculate list of receivable turnovers
		double threshold = 1.0;
		HashMap<Date, ReceivableTurnover> receivableTurnovers = KPIMeasures.receivableTurnover(totalOperatingRevenues,
				shortReceivables, longReceivables, threshold);

		// Start drawing charts:
		KpiBarChart receivableTurnoverBarChart = new KpiBarChart(receivableTurnovers);
		KpiLineChart receivableTurnoverLineChart = new KpiLineChart(receivableTurnovers, threshold);
		KpiChartProcessor receivableTurnoverChartProcessor = new KpiChartProcessor();

		// Sorting list of receivable turnovers by period (month)
		SortedMap<Date, ReceivableTurnover> sortedReceivableTurnovers = new TreeMap<Date, ReceivableTurnover>(
				new Comparator<Date>() {
					@Override
					public int compare(Date date1, Date date2) {
						return date1.compareTo(date2);
					}
				});
		sortedReceivableTurnovers.putAll(receivableTurnovers);

		model.addAttribute("receivableTurnovers", sortedReceivableTurnovers);
		model.addAttribute("receivableTurnoverBarChart", receivableTurnoverBarChart);
		model.addAttribute("receivableTurnoverLineChart", receivableTurnoverLineChart);
		model.addAttribute("receivableTurnoverChartProcessor", receivableTurnoverChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "vqkhoanphaitra");
		model.addAttribute("tab", "tabKNHD");
		return "kpiPaymentTurnover";
	}

	// Chức năng này tương tự kttienbinhquan, nên dùng lại code của kttienbinhquan
	@RequestMapping("/kyphaitrabinhquan")
	public String kpiAvgPaymentPeriod(Model model) {
		// Vẽ biểu đồ kỳ phải trả bình quân theo các kỳ (tháng) trong năm. Với từng
		// kỳ, lấy giá vốn hàng bán (11) chia phải trả bình quân của phải trả đầu kỳ và
		// cuối kỳ (310), sau đó lấy 365 (số ngày trong năm - kỳ) chia cho giá trị đó

		// Get list of receivables & total operating revenues for all period in a year
		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);
		List<BalanceSheet> totalOperatingRevenues = balanceSheetDAO.listSRsByAssetsCodeAndYear("11", currentYear);
		logger.info("totalOperatingRevenues " + totalOperatingRevenues.size());
		List<BalanceSheet> shortReceivables = balanceSheetDAO.listBSsByAssetsCodeAndYear("310", currentYear);
		logger.info("shortReceivables " + shortReceivables.size());
		List<BalanceSheet> longReceivables = balanceSheetDAO.listBSsByAssetsCodeAndYear("330", currentYear);
		logger.info("longReceivables " + longReceivables.size());

		// Calculate list of receivable turnovers
		double threshold = 1.0;
		int numberOfPeriods = 365;
		HashMap<Date, ReceivableTurnover> receivableTurnovers = KPIMeasures.avgReceivablePeriod(totalOperatingRevenues,
				shortReceivables, longReceivables, threshold, numberOfPeriods);

		// Start drawing charts:
		KpiBarChart receivableTurnoverBarChart = new KpiBarChart(receivableTurnovers);
		KpiLineChart receivableTurnoverLineChart = new KpiLineChart(receivableTurnovers, threshold);
		KpiChartProcessor receivableTurnoverChartProcessor = new KpiChartProcessor();

		// Sorting list of receivable turnovers by period (month)
		SortedMap<Date, ReceivableTurnover> sortedReceivableTurnovers = new TreeMap<Date, ReceivableTurnover>(
				new Comparator<Date>() {
					@Override
					public int compare(Date date1, Date date2) {
						return date1.compareTo(date2);
					}
				});
		sortedReceivableTurnovers.putAll(receivableTurnovers);

		model.addAttribute("receivableTurnovers", sortedReceivableTurnovers);
		model.addAttribute("receivableTurnoverBarChart", receivableTurnoverBarChart);
		model.addAttribute("receivableTurnoverLineChart", receivableTurnoverLineChart);
		model.addAttribute("receivableTurnoverChartProcessor", receivableTurnoverChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "kyphaitrabinhquan");
		model.addAttribute("tab", "tabKNHD");
		return "kpiAvgPaymentPeriod";
	}

	@RequestMapping("/ckluanchuyentien_sosach")
	public String kpiCashConversionCycleByDocument(Model model) {
		// Vẽ biểu đồ chu kỳ luân chuyển tiền theo giá trị sổ sách theo các kỳ (tháng)
		// trong năm. Với từng kỳ, lấy Chu kỳ hoạt động của doanh nghiệp theo giá trị sổ
		// sách chia cho Kỳ phải trả bình quân (Những giá trị này được tính trước đó)

		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);

		double threshold = 1.0;
		int numberOfPeriods = 365;

		logger.info("Kỳ thu tiền bình quân:");
		List<BalanceSheet> totalOperatingRevenues = balanceSheetDAO.listSRsByAssetsCodeAndYear("10", currentYear);
		logger.info("Doanh thu thuần (10) " + totalOperatingRevenues.size());
		List<BalanceSheet> shortReceivables = balanceSheetDAO.listBSsByAssetsCodeAndYear("130", currentYear);
		logger.info("Khoản phải thu ngắn hạn (130) " + shortReceivables.size());
		List<BalanceSheet> longReceivables = balanceSheetDAO.listBSsByAssetsCodeAndYear("210", currentYear);
		logger.info("Khoản phải thu dài hạn (210) " + longReceivables.size());
		HashMap<Date, ReceivableTurnover> receivableTurnovers = KPIMeasures.avgReceivablePeriod(totalOperatingRevenues,
				shortReceivables, longReceivables, threshold, numberOfPeriods);

		logger.info("Số ngày tồn kho bình quân theo giá trị sổ sách:");
		List<BalanceSheet> costOfSolds = balanceSheetDAO.listSRsByAssetsCodeAndYear("11", currentYear);
		logger.info("Giá vốn hàng bán (11) " + costOfSolds.size());
		List<BalanceSheet> inventories = balanceSheetDAO.listBSsByAssetsCodeAndYear("140", currentYear);
		logger.info("Hàng tồn kho (140) " + inventories.size());
		HashMap<Date, ReceivableTurnover> dayInInventories = KPIMeasures.avgReceivablePeriod(costOfSolds, inventories,
				threshold, numberOfPeriods);

		logger.info("Chu kỳ hoạt động của doanh nghiệp theo giá trị sổ sách:");
		HashMap<Date, OperatingCycle> operatingCycles = KPIMeasures.operatingCycle(receivableTurnovers,
				dayInInventories, threshold);

		logger.info("Tính kỳ phải trả bình quân:");
		totalOperatingRevenues = balanceSheetDAO.listSRsByAssetsCodeAndYear("11", currentYear);
		logger.info("Giá vốn hàng bán (11) " + totalOperatingRevenues.size());
		shortReceivables = balanceSheetDAO.listBSsByAssetsCodeAndYear("310", currentYear);
		logger.info("Nợ ngắn hạn (310) " + shortReceivables.size());
		longReceivables = balanceSheetDAO.listBSsByAssetsCodeAndYear("330", currentYear);
		logger.info("Nợ dài hạn (330) " + longReceivables.size());
		HashMap<Date, ReceivableTurnover> avgPaymentPeriods = KPIMeasures.avgReceivablePeriod(totalOperatingRevenues,
				shortReceivables, longReceivables, threshold, numberOfPeriods);

		logger.info("Tính chu kỳ luân chuyển tiền theo sổ sách:");
		HashMap<Date, CashConversionCycle> cashConversionCycles = KPIMeasures.cashConversionCycle(operatingCycles,
				avgPaymentPeriods, threshold);

		// Start drawing charts:
		KpiBarChart cashConversionCycleBarChart = new KpiBarChart(cashConversionCycles);
		KpiLineChart cashConversionCycleLineChart = new KpiLineChart(cashConversionCycles, threshold);
		KpiChartProcessor cashConversionCycleChartProcessor = new KpiChartProcessor();

		// Sorting list of receivable turnovers by period (month)
		SortedMap<Date, CashConversionCycle> sortedcashConversionCycles = new TreeMap<Date, CashConversionCycle>(
				new Comparator<Date>() {
					@Override
					public int compare(Date date1, Date date2) {
						return date1.compareTo(date2);
					}
				});
		sortedcashConversionCycles.putAll(cashConversionCycles);

		model.addAttribute("cashConversionCycles", sortedcashConversionCycles);
		model.addAttribute("cashConversionCycleBarChart", cashConversionCycleBarChart);
		model.addAttribute("cashConversionCycleLineChart", cashConversionCycleLineChart);
		model.addAttribute("cashConversionCycleChartProcessor", cashConversionCycleChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "ckluanchuyentien_sosach");
		model.addAttribute("tab", "tabKNHD");
		return "kpiCashConversionCycleByDocument";
	}

	@RequestMapping("/ckluanchuyentien_thitruong")
	public String kpiCashConversionCycleByMarket(Model model) {
		// Vẽ biểu đồ chu kỳ luân chuyển tiền theo giá trị thị trường theo các kỳ
		// (tháng) trong năm. Với từng kỳ, lấy Chu kỳ hoạt động của doanh nghiệp theo
		// giá trị thị trường chia cho Kỳ phải trả bình quân (Những giá trị này được
		// tính trước đó)

		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);

		double threshold = 1.0;
		int numberOfPeriods = 365;

		logger.info("Kỳ thu tiền bình quân:");
		List<BalanceSheet> totalOperatingRevenues = balanceSheetDAO.listSRsByAssetsCodeAndYear("10", currentYear);
		logger.info("Doanh thu thuần (10) " + totalOperatingRevenues.size());
		List<BalanceSheet> shortReceivables = balanceSheetDAO.listBSsByAssetsCodeAndYear("130", currentYear);
		logger.info("Khoản phải thu ngắn hạn (130) " + shortReceivables.size());
		List<BalanceSheet> longReceivables = balanceSheetDAO.listBSsByAssetsCodeAndYear("210", currentYear);
		logger.info("Khoản phải thu dài hạn (210) " + longReceivables.size());
		HashMap<Date, ReceivableTurnover> receivableTurnovers = KPIMeasures.avgReceivablePeriod(totalOperatingRevenues,
				shortReceivables, longReceivables, threshold, numberOfPeriods);

		logger.info("Số ngày tồn kho bình quân theo giá trị thị trường:");
		logger.info("Doanh thu thuần (10) " + totalOperatingRevenues.size());
		List<BalanceSheet> inventories = balanceSheetDAO.listBSsByAssetsCodeAndYear("140", currentYear);
		logger.info("Hàng tồn kho (140) " + inventories.size());
		HashMap<Date, ReceivableTurnover> dayInInventories = KPIMeasures.avgReceivablePeriod(totalOperatingRevenues,
				inventories, threshold, numberOfPeriods);

		logger.info("Chu kỳ hoạt động của doanh nghiệp theo giá trị thị trường:");
		HashMap<Date, OperatingCycle> operatingCycles = KPIMeasures.operatingCycle(receivableTurnovers,
				dayInInventories, threshold);

		logger.info("Tính kỳ phải trả bình quân:");
		totalOperatingRevenues = balanceSheetDAO.listSRsByAssetsCodeAndYear("11", currentYear);
		logger.info("Giá vốn hàng bán (11) " + totalOperatingRevenues.size());
		shortReceivables = balanceSheetDAO.listBSsByAssetsCodeAndYear("310", currentYear);
		logger.info("Nợ ngắn hạn (310) " + shortReceivables.size());
		longReceivables = balanceSheetDAO.listBSsByAssetsCodeAndYear("330", currentYear);
		logger.info("Nợ dài hạn (330) " + longReceivables.size());
		HashMap<Date, ReceivableTurnover> avgPaymentPeriods = KPIMeasures.avgReceivablePeriod(totalOperatingRevenues,
				shortReceivables, longReceivables, threshold, numberOfPeriods);

		logger.info("Tính chu kỳ luân chuyển tiền theo sổ sách:");
		HashMap<Date, CashConversionCycle> cashConversionCycles = KPIMeasures.cashConversionCycle(operatingCycles,
				avgPaymentPeriods, threshold);

		// Start drawing charts:
		KpiBarChart cashConversionCycleBarChart = new KpiBarChart(cashConversionCycles);
		KpiLineChart cashConversionCycleLineChart = new KpiLineChart(cashConversionCycles, threshold);
		KpiChartProcessor cashConversionCycleChartProcessor = new KpiChartProcessor();

		// Sorting list of receivable turnovers by period (month)
		SortedMap<Date, CashConversionCycle> sortedcashConversionCycles = new TreeMap<Date, CashConversionCycle>(
				new Comparator<Date>() {
					@Override
					public int compare(Date date1, Date date2) {
						return date1.compareTo(date2);
					}
				});
		sortedcashConversionCycles.putAll(cashConversionCycles);

		model.addAttribute("cashConversionCycles", sortedcashConversionCycles);
		model.addAttribute("cashConversionCycleBarChart", cashConversionCycleBarChart);
		model.addAttribute("cashConversionCycleLineChart", cashConversionCycleLineChart);
		model.addAttribute("cashConversionCycleChartProcessor", cashConversionCycleChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "ckluanchuyentien_thitruong");
		model.addAttribute("tab", "tabKNHD");
		return "kpiCashConversionCycleByMarket";
	}

	// Sử dụng hàm kpi của currentRatio vì tương tự nhau
	@RequestMapping("/knttlaivay")
	public String kpiInterestCoverage(Model model) {
		// Vẽ biểu đồ khả năng thanh toán lãi vay theo các kỳ (tháng) trong năm. Với
		// từng kỳ, lấy tổng lợi nhuận trước thuế (50) chia chi phí lãi vay (23).

		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);

		double threshold = 1.0;

		logger.info("Khả năng thanh toán lãi vay:");
		List<BalanceSheet> profits = balanceSheetDAO.listSRsByAssetsCodeAndYear("50", currentYear);
		logger.info("Tổng lợi nhuận trước thuế (50) " + profits.size());
		List<BalanceSheet> fees = balanceSheetDAO.listSRsByAssetsCodeAndYear("23", currentYear);
		logger.info("Chi phí lãi vay (23) " + fees.size());

		HashMap<Date, CurrentRatio> interestCoverages = KPIMeasures.currentRatio(profits, fees, threshold);

		// Start drawing charts:
		KpiBarChart interestCoverageBarChart = new KpiBarChart(interestCoverages);
		KpiLineChart interestCoverageLineChart = new KpiLineChart(interestCoverages, threshold);
		KpiChartProcessor interestCoverageChartProcessor = new KpiChartProcessor();

		// Sorting list of Interest Coverages by period (month)
		SortedMap<Date, CurrentRatio> sortedInterestCoverages = new TreeMap<Date, CurrentRatio>(new Comparator<Date>() {
			@Override
			public int compare(Date date1, Date date2) {
				return date1.compareTo(date2);
			}
		});
		sortedInterestCoverages.putAll(interestCoverages);

		model.addAttribute("interestCoverages", sortedInterestCoverages);
		model.addAttribute("interestCoverageBarChart", interestCoverageBarChart);
		model.addAttribute("interestCoverageLineChart", interestCoverageLineChart);
		model.addAttribute("interestCoverageChartProcessor", interestCoverageChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "knttlaivay");
		model.addAttribute("tab", "tabKNHD");
		return "kpiInterestCoverage";
	}

	@RequestMapping("/hssdtongtaisan")
	public String kpiTotalAssetsUtility(Model model) {
		// Vẽ biểu đồ Hiệu suất sử dụng tổng tài sản (Vòng quay tổng tài sản) theo các
		// kỳ (tháng) trong năm. Với từng kỳ, lấy doanh thu thuần (10), chia trung bình
		// tổng tài sản (tổng tài sản đầu kỳ và cuối kỳ - 270)

		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);

		double threshold = 1.0;

		logger.info("Hiệu suất sử dụng tổng tài sản (Vòng quay tổng tài sản):");
		List<BalanceSheet> totalOperatingRevenues = balanceSheetDAO.listSRsByAssetsCodeAndYear("10", currentYear);
		logger.info("Doanh thu thuần (10) " + totalOperatingRevenues.size());
		List<BalanceSheet> totalAssets = balanceSheetDAO.listBSsByAssetsCodeAndYear("270", currentYear);
		logger.info("Tổng tài sản (270) " + totalAssets.size());

		HashMap<Date, ReceivableTurnover> totalAssetsUtilities = KPIMeasures.receivableTurnover(totalOperatingRevenues,
				totalAssets, threshold);

		// Start drawing charts:
		KpiBarChart totalAssetsUtilityBarChart = new KpiBarChart(totalAssetsUtilities);
		KpiLineChart totalAssetsUtilityLineChart = new KpiLineChart(totalAssetsUtilities, threshold);
		KpiChartProcessor totalAssetsUtilityChartProcessor = new KpiChartProcessor();

		// Sorting list of receivable turnovers by period (month)
		SortedMap<Date, ReceivableTurnover> sortedtotalAssetsUtilities = new TreeMap<Date, ReceivableTurnover>(
				new Comparator<Date>() {
					@Override
					public int compare(Date date1, Date date2) {
						return date1.compareTo(date2);
					}
				});
		sortedtotalAssetsUtilities.putAll(totalAssetsUtilities);

		model.addAttribute("totalAssetsUtilities", sortedtotalAssetsUtilities);
		model.addAttribute("totalAssetsUtilityBarChart", totalAssetsUtilityBarChart);
		model.addAttribute("totalAssetsUtilityLineChart", totalAssetsUtilityLineChart);
		model.addAttribute("totalAssetsUtilityChartProcessor", totalAssetsUtilityChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "hssdtongtaisan");
		model.addAttribute("tab", "tabKNSL");
		return "kpiTotalAssetsUtility";
	}

	@RequestMapping("/hssdtaisancodinh")
	public String kpiFixedAssetsTurnover(Model model) {
		// Vẽ biểu đồ Hiệu suất sử dụng tài sản cố định theo kỳ (tháng) trong năm. Với
		// từng kỳ, lấy doanh thu thuần (10), chia trung bình tài sản cố định (tài sản
		// cố định đầu kỳ và cuối kỳ - 220)

		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);

		double threshold = 1.0;

		logger.info("Hiệu suất sử dụng tài sản cố định:");
		List<BalanceSheet> totalOperatingRevenues = balanceSheetDAO.listSRsByAssetsCodeAndYear("10", currentYear);
		logger.info("Doanh thu thuần (10) " + totalOperatingRevenues.size());
		List<BalanceSheet> fixedAssets = balanceSheetDAO.listBSsByAssetsCodeAndYear("220", currentYear);
		logger.info("Tài sản cố định (220) " + fixedAssets.size());

		HashMap<Date, ReceivableTurnover> fixedAssetsTurnovers = KPIMeasures.receivableTurnover(totalOperatingRevenues,
				fixedAssets, threshold);

		// Start drawing charts:
		KpiBarChart fixedAssetsTurnoverBarChart = new KpiBarChart(fixedAssetsTurnovers);
		KpiLineChart fixedAssetsTurnoverLineChart = new KpiLineChart(fixedAssetsTurnovers, threshold);
		KpiChartProcessor fixedAssetsTurnoverChartProcessor = new KpiChartProcessor();

		// Sorting list of receivable turnovers by period (month)
		SortedMap<Date, ReceivableTurnover> sortedFixedAssetsTurnovers = new TreeMap<Date, ReceivableTurnover>(
				new Comparator<Date>() {
					@Override
					public int compare(Date date1, Date date2) {
						return date1.compareTo(date2);
					}
				});
		sortedFixedAssetsTurnovers.putAll(fixedAssetsTurnovers);

		model.addAttribute("fixedAssetsTurnovers", sortedFixedAssetsTurnovers);
		model.addAttribute("fixedAssetsTurnoverBarChart", fixedAssetsTurnoverBarChart);
		model.addAttribute("fixedAssetsTurnoverLineChart", fixedAssetsTurnoverLineChart);
		model.addAttribute("fixedAssetsTurnoverChartProcessor", fixedAssetsTurnoverChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "hssdtaisancodinh");
		model.addAttribute("tab", "tabKNSL");
		return "kpiFixedAssetsTurnover";
	}

	@RequestMapping("/hssdvonluudong")
	public String kpiWorkingCapitalTurnover(Model model) {
		// Vẽ biểu đồ Hiệu suất sử dụng trên vòng quay vốn lưu động theo kỳ (tháng)
		// trong năm. Với từng kỳ, lấy doanh thu thuần (10), chia trung bình tài sản
		// ngắn hạn (tài sản ngắn hạn đầu kỳ và cuối kỳ - 100)

		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);

		double threshold = 1.0;

		logger.info("Hiệu suất sử dụng trên vòng quay vốn lưu động:");
		List<BalanceSheet> totalOperatingRevenues = balanceSheetDAO.listSRsByAssetsCodeAndYear("10", currentYear);
		logger.info("Doanh thu thuần (10) " + totalOperatingRevenues.size());
		List<BalanceSheet> shorttermAssets = balanceSheetDAO.listBSsByAssetsCodeAndYear("100", currentYear);
		logger.info("Tài sản ngắn hạn (100) " + shorttermAssets.size());

		HashMap<Date, ReceivableTurnover> workingCapitalTurnovers = KPIMeasures
				.receivableTurnover(totalOperatingRevenues, shorttermAssets, threshold);

		// Start drawing charts:
		KpiBarChart workingCapitalTurnoverBarChart = new KpiBarChart(workingCapitalTurnovers);
		KpiLineChart workingCapitalTurnoverLineChart = new KpiLineChart(workingCapitalTurnovers, threshold);
		KpiChartProcessor workingCapitalTurnoverChartProcessor = new KpiChartProcessor();

		// Sorting list of receivable turnovers by period (month)
		SortedMap<Date, ReceivableTurnover> sortedWorkingCapitalTurnovers = new TreeMap<Date, ReceivableTurnover>(
				new Comparator<Date>() {
					@Override
					public int compare(Date date1, Date date2) {
						return date1.compareTo(date2);
					}
				});
		sortedWorkingCapitalTurnovers.putAll(workingCapitalTurnovers);

		model.addAttribute("workingCapitalTurnovers", sortedWorkingCapitalTurnovers);
		model.addAttribute("workingCapitalTurnoverBarChart", workingCapitalTurnoverBarChart);
		model.addAttribute("workingCapitalTurnoverLineChart", workingCapitalTurnoverLineChart);
		model.addAttribute("workingCapitalTurnoverChartProcessor", workingCapitalTurnoverChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "hssdvonluudong");
		model.addAttribute("tab", "tabKNSL");
		return "kpiWorkingCapitalTurnover";
	}

	// Sử dụng hàm kpi của currentRatio vì tương tự nhau
	@RequestMapping("/tysuatloinhuangop")
	public String kpiGrossProfitMargin(Model model) {
		// Vẽ biểu đồ Tỷ suất lợi nhuận gộp theo các kỳ (tháng) trong năm. Với
		// từng kỳ, lấy lợi nhuận gộp (20) chia doanh thu thuần (10).

		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);

		double threshold = 0.5;

		logger.info("Tỷ suất lợi nhuận gộp (Lợi nhuận gộp biên):");
		List<BalanceSheet> grossProfits = balanceSheetDAO.listSRsByAssetsCodeAndYear("20", currentYear);
		logger.info("Lợi nhuận gộp (20) " + grossProfits.size());
		List<BalanceSheet> netIncomes = balanceSheetDAO.listSRsByAssetsCodeAndYear("10", currentYear);
		logger.info("Doanh thu thuần (10) " + netIncomes.size());

		HashMap<Date, CurrentRatio> grossProfitMargins = KPIMeasures.currentRatio(grossProfits, netIncomes, threshold);

		// Start drawing charts:
		KpiBarChart grossProfitMarginBarChart = new KpiBarChart(grossProfitMargins);
		KpiLineChart grossProfitMarginLineChart = new KpiLineChart(grossProfitMargins, threshold);
		KpiChartProcessor grossProfitMarginChartProcessor = new KpiChartProcessor();

		// Sorting list of Interest Coverages by period (month)
		SortedMap<Date, CurrentRatio> sortedGrossProfitMargins = new TreeMap<Date, CurrentRatio>(
				new Comparator<Date>() {
					@Override
					public int compare(Date date1, Date date2) {
						return date1.compareTo(date2);
					}
				});
		sortedGrossProfitMargins.putAll(grossProfitMargins);

		model.addAttribute("grossProfitMargins", sortedGrossProfitMargins);
		model.addAttribute("grossProfitMarginBarChart", grossProfitMarginBarChart);
		model.addAttribute("grossProfitMarginLineChart", grossProfitMarginLineChart);
		model.addAttribute("grossProfitMarginChartProcessor", grossProfitMarginChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "tysuatloinhuangop");
		model.addAttribute("tab", "tabKNSL");
		return "kpiGrossProfitMargin";
	}

	// Sử dụng hàm kpi của currentRatio vì tương tự nhau
	@RequestMapping("/tysuatloinhuanrong")
	public String kpiNetProfitMargin(Model model) {
		// Vẽ biểu đồ Tỷ suất lợi nhuận ròng theo các kỳ (tháng) trong năm. Với
		// từng kỳ, lấy lợi nhuận sau thuế (60) chia tổng doanh thu bán hàng (01) và
		// doan thu hoạt động tài chính (21).

		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);

		double threshold = 1.0;

		logger.info("Tỷ suất lợi nhuận ròng (Lợi nhuận ròng ròng):");
		List<BalanceSheet> grossProfits = balanceSheetDAO.listSRsByAssetsCodeAndYear("60", currentYear);
		logger.info("Lợi nhuận sau thuế (60) " + grossProfits.size());
		List<BalanceSheet> netIncomes = balanceSheetDAO.listSRsByAssetsCodeAndYear("01", currentYear);
		logger.info("Doanh thu bán hàng và cung cấp dịch vụ (01) " + netIncomes.size());
		List<BalanceSheet> financeNetIncomes = balanceSheetDAO.listSRsByAssetsCodeAndYear("21", currentYear);
		logger.info("Doanh thu hoạt động tài chính (21) " + financeNetIncomes.size());

		HashMap<Date, NetProfitMargin> netProfitMargins = KPIMeasures.netProfitMargin(grossProfits, netIncomes,
				financeNetIncomes, threshold);

		// Start drawing charts:
		KpiBarChart netProfitMarginBarChart = new KpiBarChart(netProfitMargins);
		KpiLineChart netProfitMarginLineChart = new KpiLineChart(netProfitMargins, threshold);
		KpiChartProcessor netProfitMarginChartProcessor = new KpiChartProcessor();

		// Sorting list of Interest Coverages by period (month)
		SortedMap<Date, NetProfitMargin> sortedNetProfitMargins = new TreeMap<Date, NetProfitMargin>(
				new Comparator<Date>() {
					@Override
					public int compare(Date date1, Date date2) {
						return date1.compareTo(date2);
					}
				});
		sortedNetProfitMargins.putAll(netProfitMargins);

		model.addAttribute("netProfitMargins", sortedNetProfitMargins);
		model.addAttribute("netProfitMarginBarChart", netProfitMarginBarChart);
		model.addAttribute("netProfitMarginLineChart", netProfitMarginLineChart);
		model.addAttribute("netProfitMarginChartProcessor", netProfitMarginChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "tysuatloinhuanrong");
		model.addAttribute("tab", "tabKNSL");
		return "kpiNetProfitMargin";
	}

	@RequestMapping("/hesono")
	public String kpiDebtRatio(Model model) {
		// Vẽ biểu đồ Hệ số nợ theo tất cả các kỳ (tháng) trong năm
		// Với từng kỳ, lấy tổng số nợ (300) chia cho tổng tài sản (270)

		// Get list of total debts and total assets for all period in a year
		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);
		List<BalanceSheet> totalDebts = balanceSheetDAO.listBSsByAssetsCodeAndYear("300", currentYear);
		logger.info("totalDebt " + totalDebts.size());
		List<BalanceSheet> totalAssets = balanceSheetDAO.listBSsByAssetsCodeAndYear("270", currentYear);
		logger.info("totalAssets " + totalAssets.size());

		// Calculate list of debt ratios
		double threshold = 0.0;
		HashMap<Date, DebtRatio> debtRatios = KPIMeasures.debtRatio(totalDebts, totalAssets, threshold);

		// Start drawing charts:
		KpiBarChart debtRatioBarChart = new KpiBarChart(debtRatios);
		KpiLineChart debtRatioLineChart = new KpiLineChart(debtRatios, threshold);
		KpiChartProcessor debtRatioChartProcessor = new KpiChartProcessor();

		// Sorting list of debt ratios by period (month)
		SortedMap<Date, DebtRatio> sortedDebtRatios = new TreeMap<Date, DebtRatio>(new Comparator<Date>() {
			@Override
			public int compare(Date date1, Date date2) {
				return date1.compareTo(date2);
			}
		});
		sortedDebtRatios.putAll(debtRatios);

		model.addAttribute("debtRatios", sortedDebtRatios);
		model.addAttribute("debtRatioBarChart", debtRatioBarChart);
		model.addAttribute("debtRatioLineChart", debtRatioLineChart);
		model.addAttribute("debtRatioChartProcessor", debtRatioChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "hesono");
		model.addAttribute("tab", "tabKNCDN");
		return "kpiDebtRatio";
	}

	@RequestMapping("/donbaytaichinh")
	public String kpiFinancialLeverage(Model model) {
		// Vẽ biểu đồ đòn bẩy tài chính theo tất cả các kỳ (tháng) trong năm
		// Với từng kỳ, lấy tổng tài sản (270) chia cho vốn chủ sở hữu (400)

		// Get list of total assets and total equities for all period in a year
		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);
		List<BalanceSheet> totalAssets = balanceSheetDAO.listBSsByAssetsCodeAndYear("270", currentYear);
		logger.info("totalAssets " + totalAssets.size());
		List<BalanceSheet> totalEquities = balanceSheetDAO.listBSsByAssetsCodeAndYear("400", currentYear);
		logger.info("totalEquities " + totalEquities.size());

		// Calculate list of financial leverages
		double threshold = 0.0;
		HashMap<Date, FinancialLeverage> financialLeverages = KPIMeasures.financialLeverage(totalAssets, totalEquities,
				threshold);

		// Start drawing charts:
		KpiBarChart financialLeverageBarChart = new KpiBarChart(financialLeverages);
		KpiLineChart financialLeverageLineChart = new KpiLineChart(financialLeverages, threshold);
		KpiChartProcessor financialLeverageChartProcessor = new KpiChartProcessor();

		// Sorting list of financial leverages by period (month)
		SortedMap<Date, FinancialLeverage> sortedFinancialLeverages = new TreeMap<Date, FinancialLeverage>(
				new Comparator<Date>() {
					@Override
					public int compare(Date date1, Date date2) {
						return date1.compareTo(date2);
					}
				});
		sortedFinancialLeverages.putAll(financialLeverages);

		model.addAttribute("financialLeverages", sortedFinancialLeverages);
		model.addAttribute("financialLeverageBarChart", financialLeverageBarChart);
		model.addAttribute("financialLeverageLineChart", financialLeverageLineChart);
		model.addAttribute("financialLeverageChartProcessor", financialLeverageChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("action", "donbaytaichinh");
		model.addAttribute("tab", "tabKNCDN");
		return "kpiFinancialLeverage";
	}

	@RequestMapping("/bangcandoiketoan")
	public String balanceAssets(@ModelAttribute("balanceSheetForm") BalanceSheetForm balanceSheetForm, Model model) {
		// List balance assets:
		logger.info(balanceSheetForm);
		if (balanceSheetForm == null)
			balanceSheetForm = new BalanceSheetForm();

		// Time: Defaul: current year, current month
		logger.info(balanceSheetForm.getAssetsPeriods());
		List<Date> assestPeriods = null;
		if (balanceSheetForm.getAssetsPeriods() == null || balanceSheetForm.getAssetsPeriods().length <= 0) {
			assestPeriods = new ArrayList<>();
			assestPeriods.add(Utils.standardDate(new Date()));
		} else {
			
		}

		// Code: Default: All code
		logger.info(balanceSheetForm.getAssetsCodes());

		// Paging:
		// Number records of a Page: Default: 25
		// Page Index: Default: 1
		// Total records
		// Total of page
		
		Date currentYear = Utils.standardDate(new Date());

		List<BalanceSheet> bss = balanceSheetDAO.listBSsByDate(currentYear);
		List<String> assetsCodes = balanceSheetDAO.listBSAssetsCodes();
		List<Date> assetsPeriods = balanceSheetDAO.listBSAssetsPeriods();

		model.addAttribute("bss", bss);
		model.addAttribute("assetsCodes", assetsCodes);
		model.addAttribute("assetsPeriods", assetsPeriods);
		model.addAttribute("tab", "tabBCDKT");
		return "balanceAssets";
	}

	@RequestMapping("/capnhat")
	public String update(Model model) {
		model.addAttribute("tab", "tabCNDL");
		return "update";
	}

	@RequestMapping(value = "/luutru", method = RequestMethod.POST)
	public String save(Model model, @RequestParam("file") MultipartFile file) {
		if (file != null && file.getSize() > 0) {
			logger.info(file.getName() + " - " + file.getSize());
			try {
				// Read & insert balance sheet data
				List<BalanceSheet> bss = ExcelProcessor.readBalanceSheetExcel(file.getInputStream());
				balanceSheetDAO.insertOrUpdateBSs(bss);

				// Read & insert sale result data
				List<BalanceSheet> saleResults = ExcelProcessor.readSaleResultExcel(file.getInputStream());
				balanceSheetDAO.insertOrUpdateSR(saleResults);

				return "redirect:/";
			} catch (Exception e) {
				e.printStackTrace();
				String comment = "Không thể đọc excel file " + file.getName()
						+ ". Có thể file bị lỗi, không đúng định dạng, hoặc đường truyền chậm, xin mời thử lại.";
				model.addAttribute("comment", comment);
				model.addAttribute("tab", "tabCNDL");
				return "update";
			}
		} else {
			String comment = "Hãy chọn file exel dữ liệu kế toán.";
			model.addAttribute("comment", comment);
			model.addAttribute("tab", "tabCNDL");
			return "update";
		}
	}
}
