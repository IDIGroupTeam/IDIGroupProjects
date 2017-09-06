package com.idi.finance.dao;

import java.util.Date;
import java.util.List;

import com.idi.finance.bean.BalanceSheet;

public interface BalanceSheetDAO {
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
