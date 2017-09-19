package com.idi.finance.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.idi.finance.bean.BalanceAssetData;
import com.idi.finance.bean.BalanceAssetItem;
import com.idi.finance.bean.KpiChart;
import com.idi.finance.bean.KpiGroup;
import com.idi.finance.bean.KpiMeasure;
import com.idi.finance.charts.KpiMeasureBarChart;
import com.idi.finance.charts.KpiMeasureChartProcessor;
import com.idi.finance.charts.KpiMeasureLineChart;
import com.idi.finance.dao.BalanceSheetDAO;
import com.idi.finance.dao.KpiChartDAO;
import com.idi.finance.form.SearchKpiChartForm;
import com.idi.finance.utils.Contants;
import com.idi.finance.utils.ExpressionEval;

@Controller
public class KpiChartController {
	private static final Logger logger = Logger.getLogger(KpiChartController.class);

	@Autowired
	BalanceSheetDAO balanceSheetDAO;

	@Autowired
	KpiChartDAO kpiChartDAO;

	@RequestMapping("/")
	public String kpiChart(Model model) {
		// Khởi tạo nhóm mặc định theo id nhỏ nhất
		int groupId = kpiChartDAO.getActiveKpiGroupId();
		return "forward:/bieudo/" + groupId;
	}

	@RequestMapping(value = "/bieudo/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String kpiChart(@ModelAttribute("SearchKpiChartForm") SearchKpiChartForm form,
			@PathVariable("id") int groupId, Model model) {
		try {
			// Khởi tạo nhóm mặc định theo giá trị biến client truyền đến
			if (groupId != 0) {
				model.addAttribute("tab", "tab" + groupId);
			} else {
				model.addAttribute("tab", "tabCNDL");
				return "updateDb";
			}

			Date currentYear = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentYear);

			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();

			KpiGroup kpiGroup = new KpiGroup(groupId);
			if (kpiGroups != null) {
				Iterator<KpiGroup> iter = kpiGroups.iterator();
				while (iter.hasNext()) {
					KpiGroup kpiGroupTmpl = iter.next();
					if (kpiGroup.getGroupId() == kpiGroupTmpl.getGroupId()) {
						kpiGroup = kpiGroupTmpl;
						break;
					}
				}
			}

			// Lấy danh sách tất cả biểu đồ thuộc nhóm mặc định
			List<KpiChart> kpiCharts = kpiChartDAO.listKpiChartByKpiGroup(kpiGroup);

			// Chọn ra danh sách biển đồ sẽ được hiển thị lên trang theo hai cách
			// Cách một: theo giá trị mặc định cấu hình trong db hoặc ở đâu đó
			// Cách hai: theo giá trị lấy từ biến do client truyền đến
			if (form != null && kpiCharts != null) {
				if (form.getKipCharts() == null) {
					// Lần đầu tiên, khi người dùng chưa chọn chart nào,
					// thì lấy thông tin chart mặc định cấu hình trong csdl
					List<Integer> selectedKpiChartIds = new ArrayList<>();
					Iterator<KpiChart> iter = kpiCharts.iterator();
					while (iter.hasNext()) {
						KpiChart kpiChart = iter.next();
						if (kpiChart.isHomeFlag()) {
							selectedKpiChartIds.add(kpiChart.getChartId());
						}
					}
					Integer[] ints = new Integer[selectedKpiChartIds.size()];
					ints = selectedKpiChartIds.toArray(ints);
					form.setKipCharts(ints);
				}
			}

			// Lấy thông tin cấu hình các biểu đồ sẽ được hiển thị
			kpiGroup.setKpiCharts(kpiChartDAO.listSelectedKpiChartInDetails(form.getKipCharts()));

			// Lấy dữ liệu các biểu đồ sẽ được vẽ theo thông tin cấu hình vừa thu được
			if (kpiGroup.getKpiCharts() != null) {
				Iterator<KpiChart> iter = kpiGroup.getKpiCharts().iterator();
				while (iter.hasNext()) {
					KpiChart kpiChart = iter.next();
					logger.info("TÍNH DỮ LIỆU THẬT CỦA BIỂU ĐỒ: " + kpiChart.getChartTitle());

					if (kpiChart.getKpiMeasures() != null) {
						Iterator<KpiMeasure> iter1 = kpiChart.getKpiMeasures().iterator();
						while (iter1.hasNext()) {
							KpiMeasure kpiMeasure = iter1.next();
							kpiMeasure = calculateKpiMeasure(kpiMeasure, currentYear);

							// Gộp kpis và operands của từng kpis vào biểu đồ chung, những cái có rồi sẽ
							// không được đưa vào danh sách chung của biểu đồ nữa
							kpiChart.addKpis(kpiMeasure.getKpis());
							kpiChart.addOperands(kpiMeasure.getOperands());
						}
					}
				}
			}

			// Đánh giá giá trị từng đường của biều đồ so với ngưỡng (threshold)
			if (kpiGroup.getKpiCharts() != null) {
				Iterator<KpiChart> iter = kpiGroup.getKpiCharts().iterator();
				while (iter.hasNext()) {
					KpiChart kpiChart = iter.next();

					if (kpiChart.getKpiMeasures() != null) {
						Iterator<KpiMeasure> iter1 = kpiChart.getKpiMeasures().iterator();
						while (iter1.hasNext()) {
							KpiMeasure kpiMeasure = iter1.next();
							HashMap<Date, Double> evaluates = new HashMap<>();

							Iterator<Date> iter2 = kpiMeasure.getValues().keySet().iterator();
							while (iter2.hasNext()) {
								Date date = iter2.next();
								double evaluate = 0;
								if (kpiMeasure.getValues().get(date) == null || kpiMeasure.getValues().get(date) == 0) {
									// Không có dữ liệu
									evaluate = 0;
								} else if (kpiMeasure.getValues().get(date) >= kpiMeasure.getChart().getThreshold()) {
									// Dữ liệu tốt
									evaluate = 1;
								} else {
									// Dữ liệu không tốt
									evaluate = -1;
								}
								evaluates.put(date, evaluate);
							}

							// Sorting list of current ratios by period (month)
							SortedMap<Date, Double> sortedMap = new TreeMap<Date, Double>(new Comparator<Date>() {
								@Override
								public int compare(Date date1, Date date2) {
									return date1.compareTo(date2);
								}
							});
							sortedMap.putAll(evaluates);
							kpiMeasure.setEvaluates(sortedMap);

							// Tạo danh sách thời kỳ cho biều đổ chung
							if (kpiChart.getDates() == null) {
								List<Date> dates = new ArrayList<>(sortedMap.keySet());
								Collections.sort(dates, new Comparator<Date>() {
									@Override
									public int compare(Date date1, Date date2) {
										return date1.compareTo(date2);
									}
								});
								kpiChart.setDates(dates);
							}
						}
					}
				}
			}

			// Tạo chart từ dữ liệu đó và chuyển cho jsp vẽ trang
			if (kpiGroup.getKpiCharts() != null) {
				Iterator<KpiChart> iter = kpiGroup.getKpiCharts().iterator();
				while (iter.hasNext()) {
					KpiChart kpiChart = iter.next();

					logger.info("VẼ BIỂU ĐỒ: " + kpiChart.getChartTitle());
					if (kpiChart.getKpiMeasures() != null) {
						Iterator<KpiMeasure> iter1 = kpiChart.getKpiMeasures().iterator();
						while (iter1.hasNext()) {
							KpiMeasure kpiMeasure = iter1.next();

							logger.info("VẼ ĐƯỜNG: " + kpiMeasure.getMeasureName());
							KpiMeasureBarChart barChart = new KpiMeasureBarChart(kpiMeasure);
							KpiMeasureChartProcessor barProcessor = new KpiMeasureChartProcessor();
							KpiMeasureLineChart thresholdLine = new KpiMeasureLineChart(kpiMeasure);

							model.addAttribute("barChart" + kpiMeasure.getMeasureId(), barChart);
							model.addAttribute("barProcessor" + kpiMeasure.getMeasureId(), barProcessor);
							model.addAttribute("KpiMeasureLineChart" + kpiChart.getChartId(), thresholdLine);
						}
					}
				}
			}

			// Dùng cho tạo giao diện
			model.addAttribute("kpiGroups", kpiGroups);
			model.addAttribute("kpiGroup", kpiGroup);
			model.addAttribute("kpiCharts", kpiCharts);
			model.addAttribute("year", cal.get(Calendar.YEAR));

			return "kpiChart";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * Dựa vào biểu thức mẫu của từng chỉ số kpi, lấy dữ liệu trong csdl và tính ra
	 * giá trị thực biểu đồ
	 * 
	 * @param kpiMeasure
	 * @param currentYear
	 * @return
	 */
	private KpiMeasure calculateKpiMeasure(KpiMeasure kpiMeasure, Date currentYear) {
		if (kpiMeasure == null || kpiMeasure.getExpression() == null || currentYear == null)
			return null;

		logger.info("Tính dữ liệu cho đường: " + kpiMeasure.getMeasureName());
		logger.info("Với biểu thức mẫu: " + kpiMeasure.getExpression());
		// Dựa trên biểu thức mẫu đường giá trị hiện tại của biểu đồ,
		// lấy dữ liệu cho các thành phần toán hạng trong biểu thức mẫu
		// Sau đó thay giá trị thực vào biểu thức mẫu để tính giá trị thực
		HashMap<Date, String> realExpression = new HashMap<>();
		String patternExp = kpiMeasure.getExpression();
		HashMap<BalanceAssetItem, SortedMap<Date, Double>> operandDatas = new HashMap<>();
		List<String> operands = ExpressionEval.getOperands(kpiMeasure.getExpression());
		Iterator<String> iter2 = operands.iterator();
		while (iter2.hasNext()) {
			String operand = iter2.next();
			logger.info("Toán hạng: " + operand);

			if (operand.indexOf(".") != -1) {
				// Toán hạng này là biến số
				List<BalanceAssetData> bads = new ArrayList<>();
				String[] partOperands = operand.split("\\.");
				if (partOperands[0].equals(Contants.BS)) {
					// Toán hạng này lấy từ bảng dữ liệu BALANCE_ASSET_DATA/BALANCE_ASSET_ITEM
					// Lấy dữ liệu từ BALANCE_ASSET_DATA/BALANCE_ASSET_ITEM
					bads = balanceSheetDAO.listBAsByAssetsCodeAndYear(partOperands[1], currentYear);

					if (partOperands[2].equals(Contants.DK)) {
						HashMap<Date, Double> tmpls = new HashMap<>();
						BalanceAssetItem asset = null;

						Iterator<BalanceAssetData> iter = bads.iterator();
						while (iter.hasNext()) {
							BalanceAssetData balanceAssetData = iter.next();
							String assetName = balanceAssetData.getAsset().getAssetName() + " " + Contants.DK_VALUE;
							balanceAssetData.getAsset().setAssetName(assetName);
							asset = balanceAssetData.getAsset();

							tmpls.put(balanceAssetData.getPeriod(), balanceAssetData.getStartValue());
							realExpression = replaceRealValueForExp(realExpression, patternExp,
									balanceAssetData.getPeriod(), operand, balanceAssetData.getStartValue());
						}

						// Sorting list of current ratios by period (month)
						SortedMap<Date, Double> sortedMap = new TreeMap<Date, Double>(new Comparator<Date>() {
							@Override
							public int compare(Date date1, Date date2) {
								return date1.compareTo(date2);
							}
						});
						sortedMap.putAll(tmpls);
						operandDatas.put(asset, sortedMap);
					} else {
						HashMap<Date, Double> tmpls = new HashMap<>();
						BalanceAssetItem asset = null;

						Iterator<BalanceAssetData> iter = bads.iterator();
						while (iter.hasNext()) {
							BalanceAssetData balanceAssetData = iter.next();
							String assetName = balanceAssetData.getAsset().getAssetName() + " " + Contants.CK_VALUE;
							balanceAssetData.getAsset().setAssetName(assetName);
							asset = balanceAssetData.getAsset();

							tmpls.put(balanceAssetData.getPeriod(), balanceAssetData.getEndValue());
							realExpression = replaceRealValueForExp(realExpression, patternExp,
									balanceAssetData.getPeriod(), operand, balanceAssetData.getEndValue());
						}

						// Sorting list of current ratios by period (month)
						SortedMap<Date, Double> sortedMap = new TreeMap<Date, Double>(new Comparator<Date>() {
							@Override
							public int compare(Date date1, Date date2) {
								return date1.compareTo(date2);
							}
						});
						sortedMap.putAll(tmpls);
						operandDatas.put(asset, sortedMap);
					}
				} else if (partOperands[0].equals(Contants.SR)) {
					// Toán hạng này lấy từ bảng dữ liệu SALE_RESULT_DATA/SALE_RESULT_ITEM
					// Lấy dữ liệu từ SALE_RESULT_DATA/SALE_RESULT_ITEM
					bads = balanceSheetDAO.listSRssByAssetsCodeAndYear(partOperands[1], currentYear);

					if (partOperands[2].equals(Contants.DK)) {
						HashMap<Date, Double> tmpls = new HashMap<>();
						BalanceAssetItem asset = null;

						Iterator<BalanceAssetData> iter = bads.iterator();
						while (iter.hasNext()) {
							BalanceAssetData balanceAssetData = iter.next();
							String assetName = balanceAssetData.getAsset().getAssetName() + " " + Contants.DK_VALUE;
							balanceAssetData.getAsset().setAssetName(assetName);
							asset = balanceAssetData.getAsset();

							tmpls.put(balanceAssetData.getPeriod(), balanceAssetData.getStartValue());
							realExpression = replaceRealValueForExp(realExpression, patternExp,
									balanceAssetData.getPeriod(), operand, balanceAssetData.getStartValue());
						}

						// Sorting list of current ratios by period (month)
						SortedMap<Date, Double> sortedMap = new TreeMap<Date, Double>(new Comparator<Date>() {
							@Override
							public int compare(Date date1, Date date2) {
								return date1.compareTo(date2);
							}
						});
						sortedMap.putAll(tmpls);
						operandDatas.put(asset, sortedMap);
					} else {
						HashMap<Date, Double> tmpls = new HashMap<>();
						BalanceAssetItem asset = null;

						Iterator<BalanceAssetData> iter = bads.iterator();
						while (iter.hasNext()) {
							BalanceAssetData balanceAssetData = iter.next();
							String assetName = balanceAssetData.getAsset().getAssetName() + " " + Contants.CK_VALUE;
							balanceAssetData.getAsset().setAssetName(assetName);
							asset = balanceAssetData.getAsset();

							tmpls.put(balanceAssetData.getPeriod(), balanceAssetData.getEndValue());
							realExpression = replaceRealValueForExp(realExpression, patternExp,
									balanceAssetData.getPeriod(), operand, balanceAssetData.getEndValue());
						}

						// Sorting list of current ratios by period (month)
						SortedMap<Date, Double> sortedMap = new TreeMap<Date, Double>(new Comparator<Date>() {
							@Override
							public int compare(Date date1, Date date2) {
								return date1.compareTo(date2);
							}
						});
						sortedMap.putAll(tmpls);
						operandDatas.put(asset, sortedMap);
					}
				} else if (partOperands[0].equals(Contants.KPI)) {
					// Toán hạng này lấy dữ liệu từ một chỉ số KPI khác, nên ta tính kpi đó trước
					KpiMeasure kpiMeasureTmpl = kpiChartDAO.listKpiMeasureById(partOperands[1]);
					kpiMeasureTmpl = calculateKpiMeasure(kpiMeasureTmpl, currentYear);
					kpiMeasure.addKpis(kpiMeasureTmpl, kpiMeasureTmpl.getValues());

					Iterator<Date> iter = kpiMeasureTmpl.getValues().keySet().iterator();
					while (iter.hasNext()) {
						Date date = iter.next();
						realExpression = replaceRealValueForExp(realExpression, patternExp, date, operand,
								kpiMeasureTmpl.getValues().get(date));
					}
				}
			}
		}
		kpiMeasure.setOperands(operandDatas);

		// Dựa trên giá trị lấy từ csdl, tính giá trị thực của biểu thức kpi
		HashMap<Date, Double> values = new HashMap<>();

		// Sorting by period (month)
		SortedMap<Date, String> sortedRealExpression = new TreeMap<Date, String>(new Comparator<Date>() {
			@Override
			public int compare(Date date1, Date date2) {
				return date1.compareTo(date2);
			}
		});
		sortedRealExpression.putAll(realExpression);

		logger.info("Biểu thức thật theo thời gian của đường: " + kpiMeasure.getMeasureName());
		Iterator<Date> iter = sortedRealExpression.keySet().iterator();
		while (iter.hasNext()) {
			Date date = iter.next();
			String realExp = sortedRealExpression.get(date);

			double value = ExpressionEval.calExp(realExp);
			values.put(date, value);
			logger.info(date + ": " + realExp + " = " + value);
		}

		// Sorting by period (month)
		SortedMap<Date, Double> sortedValues = new TreeMap<Date, Double>(new Comparator<Date>() {
			@Override
			public int compare(Date date1, Date date2) {
				return date1.compareTo(date2);
			}
		});
		sortedValues.putAll(values);
		kpiMeasure.setValues(sortedValues);

		return kpiMeasure;
	}

	/**
	 * 
	 * @param exps
	 * @param patternExp
	 * @param period
	 * @param operand
	 * @param value
	 * @return
	 */
	private HashMap<Date, String> replaceRealValueForExp(HashMap<Date, String> exps, String patternExp, Date period,
			String operand, double value) {
		if (exps == null) {
			exps = new HashMap<>();
		}
		if (period != null && operand != null) {
			String expression = exps.get(period);
			if (expression == null)
				expression = patternExp;

			operand = operand.replaceAll("\\s+", " ").trim();

			if (expression != null && !operand.equals("")) {
				expression = expression.replaceAll(operand, value + "");
				expression = ExpressionEval.formatExpression(expression);
			}

			exps.put(period, expression);
		}
		return exps;
	}

	@RequestMapping("/quanlybieudo")
	public String chartManagement(Model model) {
		// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
		List<KpiGroup> kpiGroups = kpiChartDAO.listKpiGroups();

		model.addAttribute("kpiGroups", kpiGroups);
		model.addAttribute("tab", "tabQLBD");

		return "chartManagement";
	}
}
