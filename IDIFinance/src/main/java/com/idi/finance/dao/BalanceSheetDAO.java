package com.idi.finance.dao;

import java.util.Date;
import java.util.List;

import com.idi.finance.bean.BalanceAssetData;
import com.idi.finance.bean.BalanceSheet;

public interface BalanceSheetDAO {
	public void insertOrUpdateBAs(List<BalanceAssetData> bas);

	public List<BalanceAssetData> listBAsByAssetsCodeAndYear(String assetCode, Date year);

	public List<BalanceAssetData> listBAsByAssetCodesAndDates(List<String> assetCodes, List<Date> assetPeriods);

	public void insertOrUpdateSRs(List<BalanceAssetData> srs);

	public List<BalanceAssetData> listSRssByAssetsCodeAndYear(String assetCode, Date year);

	public void insertOrUpdateBSs(List<BalanceSheet> bss);

	public List<BalanceSheet> listBSsByAssetsCodeAndYear(String assetsCode, Date year);

	public List<BalanceSheet> listBSsByDate(Date date);

	public List<String> listBSAssetsCodes();

	public List<Date> listBSAssetsPeriods();

	public void insertOrUpdateSR(List<BalanceSheet> srs);

	public List<BalanceSheet> listSRsByAssetsCodeAndYear(String assetsCode, Date year);

	public List<BalanceSheet> listSRsByDate(Date date);

	public List<String> listSRAssetsCodes();

	public List<Date> listSRAssetsPeriods();
}
