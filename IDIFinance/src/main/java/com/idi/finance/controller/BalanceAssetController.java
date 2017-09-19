package com.idi.finance.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.idi.finance.bean.BalanceAssetData;
import com.idi.finance.bean.KpiGroup;
import com.idi.finance.dao.BalanceSheetDAO;
import com.idi.finance.dao.KpiChartDAO;
import com.idi.finance.form.BalanceAssetForm;
import com.idi.finance.utils.ExcelProcessor;
import com.idi.finance.utils.Utils;

@Controller
public class BalanceAssetController {
	private static final Logger logger = Logger.getLogger(BalanceAssetController.class);
	
	@Autowired
	BalanceSheetDAO balanceSheetDAO;

	@Autowired
	KpiChartDAO kpiChartDAO;
	
	@RequestMapping("/candoiketoan")
	public String balanceAssets(@ModelAttribute("mainFinanceForm") BalanceAssetForm balanceSheetForm, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
			model.addAttribute("kpiGroups", kpiGroups);

			// List balance assets:
			if (balanceSheetForm == null)
				balanceSheetForm = new BalanceAssetForm();

			// Time: Defaul: current year, current month
			Date currentYear = Utils.standardDate(new Date());
			List<Date> selectedAssetPeriods = null;
			if (balanceSheetForm.getAssetPeriods() != null) {
				selectedAssetPeriods = Utils.convertArray2List(balanceSheetForm.getAssetPeriods());
			} else if (!balanceSheetForm.isFirst()) {
				// Đây là lần đầu vào tab, không phải do ấn nút form tìm kiếm,
				// sẽ lấy dữ liệu tháng hiện tại
				selectedAssetPeriods = new ArrayList<>();
				selectedAssetPeriods.add(currentYear);
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
					selectedAssetPeriods);
			List<String> assetCodes = balanceSheetDAO.listBSAssetsCodes();
			List<Date> assetPeriods = balanceSheetDAO.listBSAssetsPeriods();

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
			model.addAttribute("tab", "tabBCDKT");

			return "balanceAsset";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/capnhatdulieu")
	public String update(Model model) {
		// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
		List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();
		model.addAttribute("kpiGroups", kpiGroups);

		model.addAttribute("tab", "tabCNDL");
		return "updateDb";
	}

	@RequestMapping(value = "/luutrudulieu", method = RequestMethod.POST)
	public String save(Model model, @RequestParam("file") MultipartFile file) {
		// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
		List<KpiGroup> kpiGroupsDb = kpiChartDAO.listKpiGroups();
		model.addAttribute("kpiGroups", kpiGroupsDb);

		if (file != null && file.getSize() > 0) {
			logger.info(file.getName() + " - " + file.getSize());
			try {
				// Read & insert kpi groups and kpi charts
				List<KpiGroup> kpiGroups = ExcelProcessor.readKpiMeasuresSheet(file.getInputStream());
				kpiChartDAO.insertOrUpdateKpiGroups(kpiGroups);

				// Read & insert balance sheet data
				List<BalanceAssetData> bas = ExcelProcessor.readBalanceAssetSheetExcel(file.getInputStream());
				balanceSheetDAO.insertOrUpdateBAs(bas);

				// Read & insert sale result data
				List<BalanceAssetData> srs = ExcelProcessor.readSaleResultSheetExcel(file.getInputStream());
				balanceSheetDAO.insertOrUpdateSRs(srs);

				return "redirect:/";
			} catch (Exception e) {
				e.printStackTrace();
				String comment = "Không thể đọc excel file " + file.getName()
						+ ". Có thể file bị lỗi, không đúng định dạng, hoặc đường truyền chậm, xin mời thử lại.";
				model.addAttribute("comment", comment);
				model.addAttribute("tab", "tabCNDL");
				return "updateDb";
			}
		} else {
			String comment = "Hãy chọn file exel dữ liệu kế toán.";
			model.addAttribute("comment", comment);
			model.addAttribute("tab", "tabCNDL");
			return "updateDb";
		}
	}
}
