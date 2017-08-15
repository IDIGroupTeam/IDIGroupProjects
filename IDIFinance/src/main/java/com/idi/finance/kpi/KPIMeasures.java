package com.idi.finance.kpi;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.idi.finance.bean.BalanceSheet;
import com.idi.finance.utils.Utils;

public class KPIMeasures {
	private static final Logger logger = Logger.getLogger(KPIMeasures.class);
	public String KNTT_TUC_THOI = "Khả năng thanh toán tức thời";
	public String KNTT_NHANH = "Khả năng thanh toán nhanh";
	public String KNTT_BANG_TIEN = "Khả năng thanh toán bằng tiền";
	public String VQ_PHAI_THU = "Vòng quay phải thu";
	public String VQ_HANG_TON_KHO = "Vòng quay hàng tồn kho";
	public String KNTT_LAI_VAY = "Khả năng thanh toán lãi vay";

	/**
	 * This method calculate list of current ratio from list of current assets and
	 * list of current liabilities. For each period, current ratio is current asset
	 * divide to current liability.
	 * 
	 * @param currentAssets
	 * @param currentLiabilities
	 * @param thresold
	 * @return a map of all current ratio. Key is period.
	 */
	public static HashMap<Date, CurrentRatio> currentRatio(List<BalanceSheet> currentAssets,
			List<BalanceSheet> currentLiabilities, double thresold) {
		if (currentAssets != null && currentLiabilities != null) {
			HashMap<Date, CurrentRatio> map = new HashMap<>();
			HashMap<Date, BalanceSheet> currentLiabilitiesMap = Utils.convertList2Map(currentLiabilities);

			Iterator<BalanceSheet> iter = currentAssets.iterator();
			while (iter.hasNext()) {
				BalanceSheet currentAsset = iter.next();
				BalanceSheet currentLiability = currentLiabilitiesMap.get(currentAsset.getAssetsPeriod());

				CurrentRatio currentRatio = new CurrentRatio();
				currentRatio.setPeriod(currentAsset.getAssetsPeriod());
				currentRatio.setValue(currentRatio(currentAsset.getEndValue(), currentLiability.getEndValue()));
				currentRatio.setThresold(thresold);
				if (currentLiability.getEndValue() != 0) {
					// If has data for evaluating
					if (currentRatio.getValue() >= thresold) {
						// This is good situation
						currentRatio.setEvaluate(1);
					} else {
						// This is bad situation
						currentRatio.setEvaluate(-1);
					}
				}
				// currentRatio.setCurrentAsset(currentAsset.getEndValue());
				// currentRatio.setCurrentLiability(currentLiability.getEndValue());

				map.put(currentRatio.getPeriod(), currentRatio);
				logger.info("currentRatio " + currentRatio);
			}

			return map;
		}

		return null;
	}

	/**
	 * For each period, current ratio is current asset divide to current liability.
	 * 
	 * @param currentAsset
	 * @param currentLiability
	 * @return current ratio
	 */
	private static double currentRatio(double currentAsset, double currentLiability) {
		if (currentLiability != 0) {
			return currentAsset / currentLiability;
		}
		return 0;
	}

	/**
	 * This method calculate list of quick ratio from list of current assets, list
	 * of inventories and list of current liabilities. For each period, quick ratio
	 * is current asset sub to inventory, and divide to current liability.
	 * 
	 * @param currentAssets
	 * @param inventories
	 * @param currentLiabilities
	 * @param thresold
	 * @return a map of all quick ratio. Key is period.
	 */
	public static HashMap<Date, QuickRatio> quickRatio(List<BalanceSheet> currentAssets, List<BalanceSheet> inventories,
			List<BalanceSheet> currentLiabilities, double thresold) {
		if (currentAssets != null && inventories != null && currentLiabilities != null) {
			HashMap<Date, QuickRatio> map = new HashMap<>();
			HashMap<Date, BalanceSheet> inventoriesMap = Utils.convertList2Map(inventories);
			HashMap<Date, BalanceSheet> currentLiabilitiesMap = Utils.convertList2Map(currentLiabilities);

			Iterator<BalanceSheet> iter = currentAssets.iterator();
			while (iter.hasNext()) {
				BalanceSheet currentAsset = iter.next();
				BalanceSheet inventory = inventoriesMap.get(currentAsset.getAssetsPeriod());
				BalanceSheet currentLiability = currentLiabilitiesMap.get(currentAsset.getAssetsPeriod());

				QuickRatio quickRatio = new QuickRatio();
				quickRatio.setPeriod(currentAsset.getAssetsPeriod());
				quickRatio.setValue(quickRatio(currentAsset.getEndValue(), inventory.getEndValue(),
						currentLiability.getEndValue()));
				quickRatio.setThresold(thresold);
				if (currentLiability.getEndValue() != 0) {
					// If has data for evaluating
					if (quickRatio.getValue() >= thresold) {
						// This is good situation
						quickRatio.setEvaluate(1);
					} else {
						// This is bad situation
						quickRatio.setEvaluate(-1);
					}
				}
				// quickRatio.setCurrentAsset(currentAsset.getEndValue());
				// quickRatio.setInventory(inventory.getEndValue());
				// quickRatio.setCurrentLiability(currentLiability.getEndValue());

				map.put(quickRatio.getPeriod(), quickRatio);
				logger.info("quickRatio " + quickRatio);
			}

			return map;
		}

		return null;
	}

	/**
	 * For each period, quick ratio is current asset sub to inventory, and divide to
	 * current liability.
	 * 
	 * @param currentAsset
	 * @param inventory
	 * @param currentLiability
	 * @return current ratio
	 */
	private static double quickRatio(double currentAsset, double inventory, double currentLiability) {
		if (currentLiability != 0) {
			return (currentAsset - inventory) / currentLiability;
		}
		return 0;
	}

	/**
	 * This method calculate list of cash ratio from list of cash & equivalents and
	 * list of current liabilities. For each period, cash ratio is cash & equivalent
	 * divide to current liability.
	 * 
	 * @param cashEquivalents
	 * @param currentLiabilities
	 * @param thresold
	 * @return a map of all cash ratio. Key is period.
	 */
	public static HashMap<Date, CashRatio> cashRatio(List<BalanceSheet> cashEquivalents,
			List<BalanceSheet> currentLiabilities, double thresold) {
		if (cashEquivalents != null && currentLiabilities != null) {
			HashMap<Date, CashRatio> map = new HashMap<>();
			HashMap<Date, BalanceSheet> currentLiabilitiesMap = Utils.convertList2Map(currentLiabilities);

			Iterator<BalanceSheet> iter = cashEquivalents.iterator();
			while (iter.hasNext()) {
				BalanceSheet cashEquivalent = iter.next();
				BalanceSheet currentLiability = currentLiabilitiesMap.get(cashEquivalent.getAssetsPeriod());

				CashRatio cashRatio = new CashRatio();
				cashRatio.setPeriod(cashEquivalent.getAssetsPeriod());
				cashRatio.setValue(cashRatio(cashEquivalent.getEndValue(), currentLiability.getEndValue()));
				cashRatio.setThresold(thresold);
				if (currentLiability.getEndValue() != 0) {
					// If has data for evaluating
					if (cashRatio.getValue() >= thresold) {
						// This is good situation
						cashRatio.setEvaluate(1);
					} else {
						// This is bad situation
						cashRatio.setEvaluate(-1);
					}
				}
				// cashRatio.setCashEquivalent(cashEquivalent.getEndValue());
				// cashRatio.setCurrentLiability(currentLiability.getEndValue());

				map.put(cashRatio.getPeriod(), cashRatio);
				logger.info("cashRatio " + cashRatio);
			}

			return map;
		}

		return null;
	}

	/**
	 * For each period, cash ratio is cash & equivalent divide to current liability.
	 * 
	 * @param cashEquivalent
	 * @param currentLiability
	 * @return cash ratio
	 */
	private static double cashRatio(double cashEquivalent, double currentLiability) {
		if (currentLiability != 0) {
			return cashEquivalent / currentLiability;
		}
		return 0;
	}

	/**
	 * This method calculate debt ratio from list of total debts and list of total
	 * assets. For each period, debt ratio is total debt divide to total asset.
	 * 
	 * @param totalDebts
	 * @param totalAssets
	 * @param thresold
	 * @return a map of all debt ratio. Key is period.
	 */
	public static HashMap<Date, DebtRatio> debtRatio(List<BalanceSheet> totalDebts, List<BalanceSheet> totalAssets,
			double thresold) {
		if (totalDebts != null && totalAssets != null) {
			HashMap<Date, DebtRatio> map = new HashMap<>();
			HashMap<Date, BalanceSheet> totalAssetsMap = Utils.convertList2Map(totalAssets);

			Iterator<BalanceSheet> iter = totalDebts.iterator();
			while (iter.hasNext()) {
				BalanceSheet totalDebt = iter.next();
				BalanceSheet totalAsset = totalAssetsMap.get(totalDebt.getAssetsPeriod());

				DebtRatio debtRatio = new DebtRatio();
				debtRatio.setPeriod(totalDebt.getAssetsPeriod());
				debtRatio.setValue(debtRatio(totalDebt.getEndValue(), totalAsset.getEndValue()));
				debtRatio.setThresold(thresold);
				if (totalAsset.getEndValue() != 0) {
					// If has data for evaluating
					if (debtRatio.getValue() >= thresold) {
						// This is good situation
						debtRatio.setEvaluate(1);
					} else {
						// This is bad situation
						debtRatio.setEvaluate(-1);
					}
				}
				// debtRatio.setTotalDebt(totalDebt.getEndValue());
				// debtRatio.setTotalAsset(totalAsset.getEndValue());

				map.put(debtRatio.getPeriod(), debtRatio);
				logger.info("debtRatio " + debtRatio);
			}

			return map;
		}

		return null;
	}

	/**
	 * For each period, debt ratio is total debt divide to total asset.
	 * 
	 * @param totalDebt
	 * @param totalAsset
	 * @return debt ratio
	 */
	private static double debtRatio(double totalDebt, double totalAsset) {
		if (totalAsset != 0) {
			return totalDebt / totalAsset;
		}
		return 0;
	}

	/**
	 * This method calculate list of financial leverage from list of total assets
	 * and list of total equities. For each period, financial leverage is total
	 * asset divide to total equity.
	 * 
	 * @param totalAssets
	 * @param totalEquities
	 * @param thresold
	 * @return a map of all financial leverages. Key is period.
	 */
	public static HashMap<Date, FinancialLeverage> financialLeverage(List<BalanceSheet> totalAssets,
			List<BalanceSheet> totalEquities, double thresold) {
		if (totalAssets != null && totalEquities != null) {
			HashMap<Date, FinancialLeverage> map = new HashMap<>();
			HashMap<Date, BalanceSheet> totalEquitiesMap = Utils.convertList2Map(totalEquities);

			Iterator<BalanceSheet> iter = totalAssets.iterator();
			while (iter.hasNext()) {
				BalanceSheet totalAsset = iter.next();
				BalanceSheet totalEquity = totalEquitiesMap.get(totalAsset.getAssetsPeriod());

				FinancialLeverage financialLeverage = new FinancialLeverage();
				financialLeverage.setPeriod(totalAsset.getAssetsPeriod());
				financialLeverage.setValue(financialLeverage(totalAsset.getEndValue(), totalEquity.getEndValue()));
				financialLeverage.setThresold(thresold);
				if (totalEquity.getEndValue() != 0) {
					// If has data for evaluating
					if (financialLeverage.getValue() >= thresold) {
						// This is good situation
						financialLeverage.setEvaluate(1);
					} else {
						// This is bad situation
						financialLeverage.setEvaluate(-1);
					}
				}
				// financialLeverage.setTotalAsset(totalAsset.getEndValue());
				// financialLeverage.setTotalEquity(totalEquity.getEndValue());

				map.put(financialLeverage.getPeriod(), financialLeverage);
				logger.info("financialLeverage " + financialLeverage);
			}

			return map;
		}

		return null;
	}

	/**
	 * For each period, financial leverage is total asset divide to total equity.
	 * 
	 * @param totalAsset
	 * @param totalEquity
	 * @return financial leverage
	 */
	private static double financialLeverage(double totalAsset, double totalEquity) {
		if (totalEquity != 0) {
			return totalAsset / totalEquity;
		}
		return 0;
	}
}
