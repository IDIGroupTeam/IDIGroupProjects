package com.idi.finance.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.idi.finance.bean.BalanceSheet;
import com.idi.finance.charts.cashratio.CashRatioBarChart;
import com.idi.finance.charts.cashratio.CashRatioChartProcessor;
import com.idi.finance.charts.cashratio.CashRatioLineChart;
import com.idi.finance.charts.currentratio.CurrentRatioBarChart;
import com.idi.finance.charts.currentratio.CurrentRatioChartProcessor;
import com.idi.finance.charts.currentratio.CurrentRatioLineChart;
import com.idi.finance.charts.quickratio.QuickRatioBarChart;
import com.idi.finance.charts.quickratio.QuickRatioChartProcessor;
import com.idi.finance.charts.quickratio.QuickRatioLineChart;
import com.idi.finance.dao.BalanceSheetDAO;
import com.idi.finance.kpi.KPIMeasures;
import com.idi.finance.kpi.QuickRatio;
import com.idi.finance.kpi.CashRatio;
import com.idi.finance.kpi.CurrentRatio;
import com.idi.finance.utils.ExcelProcessor;

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
		List<BalanceSheet> currentAssets = balanceSheetDAO.listBssByAssetsCodeAndYear("100", currentYear);
		logger.info("currentAssets " + currentAssets.size());
		List<BalanceSheet> currentLiabilities = balanceSheetDAO.listBssByAssetsCodeAndYear("310", currentYear);
		logger.info("currentLiabilities " + currentLiabilities.size());

		// Calculate list of current ratios
		double threshold = 1.0;
		HashMap<Date, CurrentRatio> currentRatios = KPIMeasures.currentRatio(currentAssets, currentLiabilities,
				threshold);

		// Start drawing charts:
		CurrentRatioBarChart currentRatioBarChart = new CurrentRatioBarChart(currentRatios);
		CurrentRatioLineChart currentRatioLineChart = new CurrentRatioLineChart(currentRatios, threshold);
		CurrentRatioChartProcessor currentRatioChartProcessor = new CurrentRatioChartProcessor();

		// Sorting list of current ratios by period (month)
		SortedMap<Date, CurrentRatio> sortedCurrentRatios = new TreeMap<Date, CurrentRatio>(new Comparator<Date>() {
			@Override
			public int compare(Date date1, Date date2) {
				return date1.compareTo(date2);
			}
		});
		sortedCurrentRatios.putAll(currentRatios);

		model.addAttribute("action", "kntttucthoi");
		model.addAttribute("currentRatios", sortedCurrentRatios);
		model.addAttribute("currentRatioBarChart", currentRatioBarChart);
		model.addAttribute("currentRatioLineChart", currentRatioLineChart);
		model.addAttribute("currentRatioChartProcessor", currentRatioChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));

		return "kpiCurrentRatio";
	}

	@RequestMapping("/knttnhanh")
	public String kpiQuickRation(Model model) {
		// Vẽ biểu đồ Khả năng thanh toán nhanh theo tất cả các kỳ (tháng) trong năm
		// Với từng kỳ, lấy tài sản ngắn hạn (100) trừ hàng tồn kho (140),
		// tất cả chia cho nợ ngắn hạn (310)

		// Get list current assets, inventories and current liabilites for all period in
		// a year
		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);
		List<BalanceSheet> currentAssets = balanceSheetDAO.listBssByAssetsCodeAndYear("100", currentYear);
		logger.info("currentAssets " + currentAssets.size());
		List<BalanceSheet> inventories = balanceSheetDAO.listBssByAssetsCodeAndYear("140", currentYear);
		logger.info("inventories " + inventories.size());
		List<BalanceSheet> currentLiabilities = balanceSheetDAO.listBssByAssetsCodeAndYear("310", currentYear);
		logger.info("currentLiabilities " + currentLiabilities.size());

		// Calculate list of quick ratios
		double threshold = 0.0;
		HashMap<Date, QuickRatio> quickRatios = KPIMeasures.quickRatio(currentAssets, inventories, currentLiabilities,
				threshold);

		// Start drawing charts:
		QuickRatioBarChart quickRatioBarChart = new QuickRatioBarChart(quickRatios);
		QuickRatioLineChart quickRatioLineChart = new QuickRatioLineChart(quickRatios, threshold);
		QuickRatioChartProcessor quickRatioChartProcessor = new QuickRatioChartProcessor();

		// Sorting list of quick ratios by period (month)
		SortedMap<Date, QuickRatio> sortedQuickRatios = new TreeMap<Date, QuickRatio>(new Comparator<Date>() {
			@Override
			public int compare(Date date1, Date date2) {
				return date1.compareTo(date2);
			}
		});
		sortedQuickRatios.putAll(quickRatios);

		model.addAttribute("action", "knttnhanh");
		model.addAttribute("quickRatios", sortedQuickRatios);
		model.addAttribute("quickRatioBarChart", quickRatioBarChart);
		model.addAttribute("quickRatioLineChart", quickRatioLineChart);
		model.addAttribute("quickRatioChartProcessor", quickRatioChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));

		return "kpiQuickRatio";
	}

	@RequestMapping("/knttbangtien")
	public String kpiCashRation(Model model) {
		// Vẽ biểu đồ Khả năng thanh toán nhanh theo tất cả các kỳ (tháng) trong năm
		// Với từng kỳ, lấy tài sản ngắn hạn (100) trừ hàng tồn kho (140),
		// tất cả chia cho nợ ngắn hạn (310)

		// Get list current assets, inventories and current liabilites for all period in
		// a year
		Date currentYear = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentYear);
		List<BalanceSheet> cashEquivalents = balanceSheetDAO.listBssByAssetsCodeAndYear("110", currentYear);
		logger.info("cashEquivalents " + cashEquivalents.size());
		List<BalanceSheet> currentLiabilities = balanceSheetDAO.listBssByAssetsCodeAndYear("310", currentYear);
		logger.info("currentLiabilities " + currentLiabilities.size());

		// Calculate list of quick ratios
		double threshold = 0.1;
		HashMap<Date, CashRatio> cashRatios = KPIMeasures.cashRatio(cashEquivalents, currentLiabilities, threshold);

		// Start drawing charts:
		CashRatioBarChart cashRatioBarChart = new CashRatioBarChart(cashRatios);
		CashRatioLineChart cashRatioLineChart = new CashRatioLineChart(cashRatios, threshold);
		CashRatioChartProcessor cashRatioChartProcessor = new CashRatioChartProcessor();

		// Sorting list of quick ratios by period (month)
		SortedMap<Date, CashRatio> sortedCashRatios = new TreeMap<Date, CashRatio>(new Comparator<Date>() {
			@Override
			public int compare(Date date1, Date date2) {
				return date1.compareTo(date2);
			}
		});
		sortedCashRatios.putAll(cashRatios);

		model.addAttribute("action", "knttnhanh");
		model.addAttribute("cashRatios", sortedCashRatios);
		model.addAttribute("cashRatioBarChart", cashRatioBarChart);
		model.addAttribute("cashRatioLineChart", cashRatioLineChart);
		model.addAttribute("cashRatioChartProcessor", cashRatioChartProcessor);
		model.addAttribute("year", cal.get(Calendar.YEAR));

		model.addAttribute("action", "knttbangtien");
		return "kpiCashRatio";
	}

	@RequestMapping("/vqkhoanthu")
	public String kpiReceivableTurnOver(Model model) {
		model.addAttribute("action", "vqkhoanthu");
		return "kpiReceivableTurnOver";
	}

	@RequestMapping("/vqhangtonkho_sosach")
	public String kpiInventoriesTurnOverByDocument(Model model) {
		model.addAttribute("action", "vqhangtonkho_sosach");
		return "kpiInventoriesTurnOverByDocument";
	}

	@RequestMapping("/vqhangtonkho_thitruong")
	public String kpiInventoriesTurnOverByPrice(Model model) {
		model.addAttribute("action", "vqhangtonkho_thitruong");
		return "kpiInventoriesTurnOverByPrice";
	}

	@RequestMapping("/capnhat")
	public String update(Model model) {
		return "update";
	}

	@RequestMapping(value = "/luutru", method = RequestMethod.POST)
	public String save(Model model, @RequestParam("file") MultipartFile file) {
		if (file != null && file.getSize() > 0) {
			logger.info(file.getName() + " - " + file.getSize());
			try {
				List<BalanceSheet> bss = ExcelProcessor.readExcel(file.getInputStream());
				balanceSheetDAO.insertOrUpdateBss(bss);
				return "redirect:/";
			} catch (Exception e) {
				e.printStackTrace();
				String comment = "Không thể đọc excel file " + file.getName()
						+ ". Có thể file bị lỗi, không đúng định dạng, hoặc đường truyền chậm, xin mời thử lại.";
				model.addAttribute("comment", comment);
				return "update";
			}
		} else {
			String comment = "Hãy chọn file exel dữ liệu kế toán.";
			model.addAttribute("comment", comment);
			return "update";
		}
	}
}
