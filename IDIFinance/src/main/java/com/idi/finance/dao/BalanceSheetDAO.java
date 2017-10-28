package com.idi.finance.dao;

import java.util.Date;
import java.util.List;

import com.idi.finance.bean.cdkt.BalanceAssetData;

public interface BalanceSheetDAO {
	public void insertOrUpdateBAs(List<BalanceAssetData> bas);

	public List<BalanceAssetData> listBAsByAssetsCodeAndYear(String assetCode, Date year);

	public List<BalanceAssetData> listBAsByAssetCodesAndDates(List<String> assetCodes, List<Date> assetPeriods);

	public void insertOrUpdateSRs(List<BalanceAssetData> srs);

	public List<BalanceAssetData> listSRssByAssetsCodeAndYear(String assetCode, Date year);

	public void insertOrUpdateCFs(List<BalanceAssetData> srs);

	public List<BalanceAssetData> listCFssByAssetsCodeAndYear(String assetCode, Date year);

	public List<String> listBSAssetsCodes();

	public List<Date> listBSAssetsPeriods();

	public List<String> listSRAssetsCodes();

	public List<Date> listSRAssetsPeriods();
}
