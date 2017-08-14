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
				currentRatio.setValue(currentRatio(currentAsset.getAssetsValue(), currentLiability.getAssetsValue()));
				currentRatio.setThresold(thresold);
				if (currentLiability.getAssetsValue() != 0) {
					// If has data for evaluating
					if (currentRatio.getValue() >= thresold) {
						// This is good situation
						currentRatio.setEvaluate(1);
					} else {
						// This is bad situation
						currentRatio.setEvaluate(-1);
					}
				}
				// currentRatio.setCurrentAsset(currentAsset.getAssetsValue());
				// currentRatio.setCurrentLiability(currentLiability.getAssetsValue());

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
	 * @return a map of all quick ratio. Key is period.
	 */
	public static HashMap<Date, QuickRatio> quickRatio(List<BalanceSheet> currentAssets, List<BalanceSheet> inventories,
			List<BalanceSheet> currentLiabilities, double thresold) {
		if (currentAssets != null && currentLiabilities != null) {
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
				quickRatio.setValue(quickRatio(currentAsset.getAssetsValue(), inventory.getAssetsValue(),
						currentLiability.getAssetsValue()));
				quickRatio.setThresold(thresold);
				if (currentLiability.getAssetsValue() != 0) {
					// If has data for evaluating
					if (quickRatio.getValue() >= thresold) {
						// This is good situation
						quickRatio.setEvaluate(1);
					} else {
						// This is bad situation
						quickRatio.setEvaluate(-1);
					}
				}
				// quickRatio.setCurrentAsset(currentAsset.getAssetsValue());
				// quickRatio.setInventory(inventory.getAssetsValue());
				// quickRatio.setCurrentLiability(currentLiability.getAssetsValue());

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
}
