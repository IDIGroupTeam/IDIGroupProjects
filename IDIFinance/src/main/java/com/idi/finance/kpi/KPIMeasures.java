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

	/**
	 * @param currentAssets
	 * @param currentLiabilities
	 * @param incomes
	 * @param threshold
	 * @return a map of all current ratio. Key is period.
	 */
	public static HashMap<Date, NetProfitMargin> netProfitMargin(List<BalanceSheet> grossProfits,
			List<BalanceSheet> netIncomes, List<BalanceSheet> financeNetIncomes, double threshold) {
		if (grossProfits != null && netIncomes != null && financeNetIncomes != null) {
			HashMap<Date, NetProfitMargin> map = new HashMap<>();
			HashMap<Date, BalanceSheet> netIncomesMap = Utils.convertList2Map(netIncomes);
			HashMap<Date, BalanceSheet> financeNetIncomesMap = Utils.convertList2Map(financeNetIncomes);

			Iterator<BalanceSheet> iter = grossProfits.iterator();
			while (iter.hasNext()) {
				BalanceSheet grossProfit = iter.next();
				BalanceSheet netIncome = netIncomesMap.get(grossProfit.getAssetsPeriod());
				BalanceSheet financeNetIncome = financeNetIncomesMap.get(grossProfit.getAssetsPeriod());

				NetProfitMargin netProfitMargin = new NetProfitMargin();
				netProfitMargin.setPeriod(grossProfit.getAssetsPeriod());
				netProfitMargin.setValue(netProfitMargin(grossProfit.getEndValue(), netIncome.getEndValue(),
						financeNetIncome.getEndValue()));
				netProfitMargin.setThreshold(threshold);
				if (netProfitMargin.getValue() != 0) {
					// If has data for evaluating
					if (netProfitMargin.getValue() >= threshold) {
						// This is good situation
						netProfitMargin.setEvaluate(1);
					} else {
						// This is bad situation
						netProfitMargin.setEvaluate(-1);
					}
				}
				// netProfitMargin.setGrossProfit(grossProfit.getEndValue());
				// netProfitMargin.setNetIncome(netIncome.getEndValue());
				// netProfitMargin.setFinanceNetIncome(financeNetIncome.getEndValue());

				map.put(netProfitMargin.getPeriod(), netProfitMargin);
				logger.info(netProfitMargin);
			}

			return map;
		}

		return null;
	}

	/**
	 * For each period, netProfitMargin is grossProfit divide to total of netIncome
	 * and financeNetIncome.
	 * 
	 * @param grossProfit
	 * @param netIncome
	 * @param financeNetIncome
	 * @return netProfitMargin
	 */
	private static double netProfitMargin(double grossProfit, double netIncome, double financeNetIncome) {
		if ((netIncome + financeNetIncome) != 0) {
			return grossProfit / (netIncome + financeNetIncome);
		}
		return 0;
	}

	/**
	 * This method calculate list of current ratio from list of current assets and
	 * list of current liabilities. For each period, current ratio is current asset
	 * divide to current liability.
	 * 
	 * @param currentAssets
	 * @param currentLiabilities
	 * @param threshold
	 * @return a map of all current ratio. Key is period.
	 */
	public static HashMap<Date, CurrentRatio> currentRatio(List<BalanceSheet> currentAssets,
			List<BalanceSheet> currentLiabilities, double threshold) {
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
				currentRatio.setThreshold(threshold);
				if (currentLiability.getEndValue() != 0) {
					// If has data for evaluating
					if (currentRatio.getValue() >= threshold) {
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
				logger.info(currentRatio);
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
	 * @param threshold
	 * @return a map of all quick ratio. Key is period.
	 */
	public static HashMap<Date, QuickRatio> quickRatio(List<BalanceSheet> currentAssets, List<BalanceSheet> inventories,
			List<BalanceSheet> currentLiabilities, double threshold) {
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
				quickRatio.setThreshold(threshold);
				if (currentLiability.getEndValue() != 0) {
					// If has data for evaluating
					if (quickRatio.getValue() >= threshold) {
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
	 * @param threshold
	 * @return a map of all cash ratio. Key is period.
	 */
	public static HashMap<Date, CashRatio> cashRatio(List<BalanceSheet> cashEquivalents,
			List<BalanceSheet> currentLiabilities, double threshold) {
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
				cashRatio.setThreshold(threshold);
				if (currentLiability.getEndValue() != 0) {
					// If has data for evaluating
					if (cashRatio.getValue() >= threshold) {
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
	 * This method calculate list of receivable turnover from list of total
	 * operating revenues and list of receivables. For each period, receivable
	 * turnover is total operating revenue divide to average of end receivable and
	 * start receivable. (short receivable & long receivable)
	 * 
	 * @param totalOperatingRevenues
	 * @param shortReceivables
	 * @param longReceivables
	 * @param threshold
	 * @return a map of all receivable turnover. Key is period.
	 */
	public static HashMap<Date, ReceivableTurnover> receivableTurnover(List<BalanceSheet> totalOperatingRevenues,
			List<BalanceSheet> shortReceivables, List<BalanceSheet> longReceivables, double threshold) {
		if (totalOperatingRevenues != null && shortReceivables != null && longReceivables != null) {
			HashMap<Date, ReceivableTurnover> map = new HashMap<>();
			HashMap<Date, BalanceSheet> shortReceivablesMap = Utils.convertList2Map(shortReceivables);
			HashMap<Date, BalanceSheet> longReceivablesMap = Utils.convertList2Map(longReceivables);

			Iterator<BalanceSheet> iter = totalOperatingRevenues.iterator();
			while (iter.hasNext()) {
				BalanceSheet totalOperatingRevenue = iter.next();
				BalanceSheet shortReceivable = shortReceivablesMap.get(totalOperatingRevenue.getAssetsPeriod());
				BalanceSheet longReceivable = longReceivablesMap.get(totalOperatingRevenue.getAssetsPeriod());
				double startReceivable = shortReceivable.getStartValue() + longReceivable.getStartValue();
				double endReceivable = shortReceivable.getEndValue() + longReceivable.getEndValue();

				ReceivableTurnover receivablesTurnover = new ReceivableTurnover();
				receivablesTurnover.setPeriod(totalOperatingRevenue.getAssetsPeriod());
				receivablesTurnover.setValue(
						receivableTurnover(totalOperatingRevenue.getEndValue(), endReceivable, startReceivable));
				receivablesTurnover.setThreshold(threshold);
				if (endReceivable != 0) {
					// If has data for evaluating
					if (receivablesTurnover.getValue() >= threshold) {
						// This is good situation
						receivablesTurnover.setEvaluate(1);
					} else {
						// This is bad situation
						receivablesTurnover.setEvaluate(-1);
					}
				}
				// receivablesTurnover.setTotalOperatingRevenue(totalOperatingRevenue.getEndValue());
				// receivablesTurnover.setEndReceivable(endReceivable);
				// receivablesTurnover.setStartReceivable(startReceivable);

				map.put(receivablesTurnover.getPeriod(), receivablesTurnover);
				logger.info(receivablesTurnover);
			}

			return map;
		}

		return null;
	}

	/**
	 * This method calculate list of receivable turnover from list of total
	 * operating revenues and list of receivables. For each period, receivable
	 * turnover is total operating revenue divide to average of end receivable and
	 * start receivable.
	 * 
	 * @param totalOperatingRevenues
	 * @param receivables
	 * @param threshold
	 * @return a map of all receivable turnover. Key is period.
	 */
	public static HashMap<Date, ReceivableTurnover> receivableTurnover(List<BalanceSheet> totalOperatingRevenues,
			List<BalanceSheet> receivables, double threshold) {
		if (totalOperatingRevenues != null && receivables != null) {
			HashMap<Date, ReceivableTurnover> map = new HashMap<>();
			HashMap<Date, BalanceSheet> receivablesMap = Utils.convertList2Map(receivables);

			Iterator<BalanceSheet> iter = totalOperatingRevenues.iterator();
			while (iter.hasNext()) {
				BalanceSheet totalOperatingRevenue = iter.next();
				BalanceSheet receivable = receivablesMap.get(totalOperatingRevenue.getAssetsPeriod());

				ReceivableTurnover receivablesTurnover = new ReceivableTurnover();
				receivablesTurnover.setPeriod(totalOperatingRevenue.getAssetsPeriod());
				receivablesTurnover.setValue(receivableTurnover(totalOperatingRevenue.getEndValue(),
						receivable.getEndValue(), receivable.getStartValue()));

				receivablesTurnover.setThreshold(threshold);
				if (receivable.getEndValue() != 0) {
					// If has data for evaluating
					if (receivablesTurnover.getValue() >= threshold) {
						// This is good situation
						receivablesTurnover.setEvaluate(1);
					} else {
						// This is bad situation
						receivablesTurnover.setEvaluate(-1);
					}
				}
				// receivablesTurnover.setTotalOperatingRevenue(totalOperatingRevenue.getEndValue());
				// receivablesTurnover.setEndReceivable(receivable.getEndValue());
				// receivablesTurnover.setStartReceivable(receivable.getStartValue());

				map.put(receivablesTurnover.getPeriod(), receivablesTurnover);
				logger.info(receivablesTurnover);
			}

			return map;
		}

		return null;
	}

	/**
	 * For each period, receivable turnover is total operating revenue divide to
	 * average of end receivable and start receivable.
	 * 
	 * @param totalOperatingRevenue
	 * @param endReceivable
	 * @param startReceivable
	 * @return receivable turnover
	 */
	private static double receivableTurnover(double totalOperatingRevenue, double endReceivable,
			double startReceivable) {
		double avegareReceivable = (endReceivable + startReceivable) / 2;
		if (avegareReceivable != 0) {
			return totalOperatingRevenue / avegareReceivable;
		}
		return 0;
	}

	/**
	 * This method calculate list of receivable turnover from list of total
	 * operating revenues and list of receivables. For each period, receivable
	 * turnover is total operating revenue divide to average of end receivable and
	 * start receivable.
	 * 
	 * @param totalOperatingRevenues
	 * @param receivables
	 * @param threshold
	 * @param numberOfPeriods
	 * @return a map of all receivable turnover. Key is period.
	 */
	public static HashMap<Date, ReceivableTurnover> avgReceivablePeriod(List<BalanceSheet> totalOperatingRevenues,
			List<BalanceSheet> shortReceivables, List<BalanceSheet> longReceivables, double threshold,
			int numberOfPeriods) {
		if (totalOperatingRevenues != null && shortReceivables != null && longReceivables != null) {
			HashMap<Date, ReceivableTurnover> map = new HashMap<>();
			HashMap<Date, BalanceSheet> shortReceivablesMap = Utils.convertList2Map(shortReceivables);
			HashMap<Date, BalanceSheet> longReceivablesMap = Utils.convertList2Map(longReceivables);

			Iterator<BalanceSheet> iter = totalOperatingRevenues.iterator();
			while (iter.hasNext()) {
				BalanceSheet totalOperatingRevenue = iter.next();
				BalanceSheet shortReceivable = shortReceivablesMap.get(totalOperatingRevenue.getAssetsPeriod());
				BalanceSheet longReceivable = longReceivablesMap.get(totalOperatingRevenue.getAssetsPeriod());
				double startReceivable = shortReceivable.getStartValue() + longReceivable.getStartValue();
				double endReceivable = shortReceivable.getEndValue() + longReceivable.getEndValue();

				ReceivableTurnover receivablesTurnover = new ReceivableTurnover();
				receivablesTurnover.setPeriod(totalOperatingRevenue.getAssetsPeriod());
				receivablesTurnover.setValue(avgReceivablePeriod(totalOperatingRevenue.getEndValue(), endReceivable,
						startReceivable, numberOfPeriods));

				receivablesTurnover.setThreshold(threshold);
				if (endReceivable != 0) {
					// If has data for evaluating
					if (receivablesTurnover.getValue() >= threshold) {
						// This is good situation
						receivablesTurnover.setEvaluate(1);
					} else {
						// This is bad situation
						receivablesTurnover.setEvaluate(-1);
					}
				}
				// receivablesTurnover.setTotalOperatingRevenue(totalOperatingRevenue.getEndValue());
				// receivablesTurnover.setEndReceivable(endReceivable);
				// receivablesTurnover.setStartReceivable(startReceivable);

				map.put(receivablesTurnover.getPeriod(), receivablesTurnover);
				logger.info(receivablesTurnover);
			}

			return map;
		}

		return null;
	}

	/**
	 * This method calculate list of receivable turnover from list of total
	 * operating revenues and list of receivables. For each period, receivable
	 * turnover is total operating revenue divide to average of end receivable and
	 * start receivable.
	 * 
	 * @param totalOperatingRevenues
	 * @param receivables
	 * @param threshold
	 * @param numberOfPeriods
	 * @return a map of all receivable turnover. Key is period.
	 */
	public static HashMap<Date, ReceivableTurnover> avgReceivablePeriod(List<BalanceSheet> totalOperatingRevenues,
			List<BalanceSheet> receivables, double threshold, int numberOfPeriods) {
		if (totalOperatingRevenues != null && receivables != null) {
			HashMap<Date, ReceivableTurnover> map = new HashMap<>();
			HashMap<Date, BalanceSheet> receivablesMap = Utils.convertList2Map(receivables);

			Iterator<BalanceSheet> iter = totalOperatingRevenues.iterator();
			while (iter.hasNext()) {
				BalanceSheet totalOperatingRevenue = iter.next();
				BalanceSheet receivable = receivablesMap.get(totalOperatingRevenue.getAssetsPeriod());

				ReceivableTurnover receivablesTurnover = new ReceivableTurnover();
				receivablesTurnover.setPeriod(totalOperatingRevenue.getAssetsPeriod());
				receivablesTurnover.setValue(avgReceivablePeriod(totalOperatingRevenue.getEndValue(),
						receivable.getEndValue(), receivable.getStartValue(), numberOfPeriods));

				receivablesTurnover.setThreshold(threshold);
				if (receivable.getEndValue() != 0) {
					// If has data for evaluating
					if (receivablesTurnover.getValue() >= threshold) {
						// This is good situation
						receivablesTurnover.setEvaluate(1);
					} else {
						// This is bad situation
						receivablesTurnover.setEvaluate(-1);
					}
				}
				// receivablesTurnover.setTotalOperatingRevenue(totalOperatingRevenue.getEndValue());
				// receivablesTurnover.setEndReceivable(receivable.getEndValue());
				// receivablesTurnover.setStartReceivable(receivable.getStartValue());

				map.put(receivablesTurnover.getPeriod(), receivablesTurnover);
				logger.info(receivablesTurnover);
			}

			return map;
		}

		return null;
	}

	/**
	 * For each period, receivable turnover is total operating revenue divide to
	 * average of end receivable and start receivable.
	 * 
	 * @param totalOperatingRevenue
	 * @param endReceivable
	 * @param startReceivable
	 * @param numberOfPeriods
	 * @return receivable turnover
	 */
	private static double avgReceivablePeriod(double totalOperatingRevenue, double endReceivable,
			double startReceivable, int numberOfPeriods) {
		double avegareReceivable = (endReceivable + startReceivable) / 2;
		if (totalOperatingRevenue != 0) {
			return numberOfPeriods * avegareReceivable / totalOperatingRevenue;
		}
		return 0;
	}

	/**
	 * @param totalOperatingRevenues
	 * @param shortReceivables
	 * @param longReceivables
	 * @param costOfSolds
	 * @param inventories
	 * @param threshold
	 * @param numberOfPeriods
	 * @return
	 */
	public static HashMap<Date, OperatingCycle> operatingCycle(HashMap<Date, ReceivableTurnover> receivableTurnovers,
			HashMap<Date, ReceivableTurnover> dayInInventories, double threshold) {
		if (receivableTurnovers != null && dayInInventories != null) {
			HashMap<Date, OperatingCycle> map = new HashMap<>();

			Iterator<Date> iter = receivableTurnovers.keySet().iterator();
			while (iter.hasNext()) {
				Date period = iter.next();

				ReceivableTurnover receivableTurnover = receivableTurnovers.get(period);
				ReceivableTurnover dayInInventory = dayInInventories.get(period);

				if (receivableTurnover != null && dayInInventory != null) {
					OperatingCycle operatingCycle = new OperatingCycle();
					operatingCycle.setPeriod(period);
					operatingCycle.setValue(receivableTurnover.getValue() + dayInInventory.getValue());
					operatingCycle.setThreshold(threshold);

					if (operatingCycle.getValue() != 0) {
						// If has data for evaluating
						if (operatingCycle.getValue() >= threshold) {
							// This is good situation
							operatingCycle.setEvaluate(1);
						} else {
							// This is bad situation
							operatingCycle.setEvaluate(-1);
						}
					}
					operatingCycle.setTotalOperatingRevenue(receivableTurnover.getTotalOperatingRevenue());
					operatingCycle.setStartReceivable(receivableTurnover.getStartReceivable());
					operatingCycle.setEndReceivable(receivableTurnover.getEndReceivable());
					operatingCycle.setCostOfSold(dayInInventory.getTotalOperatingRevenue());
					operatingCycle.setStartInventory(dayInInventory.getStartReceivable());
					operatingCycle.setEndInventory(dayInInventory.getEndReceivable());

					map.put(period, operatingCycle);
					logger.info(operatingCycle);
				}
			}

			return map;
		}

		return null;
	}

	public static HashMap<Date, CashConversionCycle> cashConversionCycle(HashMap<Date, OperatingCycle> operatingCycles,
			HashMap<Date, ReceivableTurnover> avgPaymentPeriods, double threshold) {
		if (operatingCycles != null && avgPaymentPeriods != null) {
			HashMap<Date, CashConversionCycle> map = new HashMap<>();

			Iterator<Date> iter = operatingCycles.keySet().iterator();
			while (iter.hasNext()) {
				Date period = iter.next();

				OperatingCycle operatingCycle = operatingCycles.get(period);
				ReceivableTurnover avgPaymentPeriod = avgPaymentPeriods.get(period);

				if (operatingCycle != null && avgPaymentPeriod != null) {
					CashConversionCycle cashConversionCycle = new CashConversionCycle();
					cashConversionCycle.setPeriod(period);
					cashConversionCycle
							.setValue(cashConversionCycle(operatingCycle.getValue(), avgPaymentPeriod.getValue()));
					cashConversionCycle.setThreshold(threshold);

					if (cashConversionCycle.getValue() != 0) {
						// If has data for evaluating
						if (cashConversionCycle.getValue() >= threshold) {
							// This is good situation
							cashConversionCycle.setEvaluate(1);
						} else {
							// This is bad situation
							cashConversionCycle.setEvaluate(-1);
						}
					}
					cashConversionCycle.setOperatingCycle(operatingCycle.getValue());
					cashConversionCycle.setAvgPaymentPeriod(avgPaymentPeriod.getValue());

					map.put(period, cashConversionCycle);
					logger.info(operatingCycle);
				}
			}

			return map;
		}
		return null;
	}

	private static double cashConversionCycle(double operatingCycle, double avgPaymentPeriod) {
		if (avgPaymentPeriod != 0) {
			return operatingCycle / avgPaymentPeriod;
		}
		return 0;
	}

	/**
	 * This method calculate debt ratio from list of total debts and list of total
	 * assets. For each period, debt ratio is total debt divide to total asset.
	 * 
	 * @param totalDebts
	 * @param totalAssets
	 * @param threshold
	 * @return a map of all debt ratio. Key is period.
	 */
	public static HashMap<Date, DebtRatio> debtRatio(List<BalanceSheet> totalDebts, List<BalanceSheet> totalAssets,
			double threshold) {
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
				debtRatio.setThreshold(threshold);
				if (totalAsset.getEndValue() != 0) {
					// If has data for evaluating
					if (debtRatio.getValue() >= threshold) {
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
	 * @param threshold
	 * @return a map of all financial leverages. Key is period.
	 */
	public static HashMap<Date, FinancialLeverage> financialLeverage(List<BalanceSheet> totalAssets,
			List<BalanceSheet> totalEquities, double threshold) {
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
				financialLeverage.setThreshold(threshold);
				if (totalEquity.getEndValue() != 0) {
					// If has data for evaluating
					if (financialLeverage.getValue() >= threshold) {
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
