package com.idi.finance.dao;

import java.util.Date;
import java.util.List;

import com.idi.finance.bean.bctc.BalanceAssetData;
import com.idi.finance.bean.bctc.BalanceAssetItem;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public interface BalanceSheetDAO {
	// BS
	public BalanceAssetItem findBSBai(String assetCode, String maTk);

	public int updateBSBai(BalanceAssetItem oldBai, BalanceAssetItem newBai);

	public int insertBSBai(BalanceAssetItem bai);

	public void deleteBSBai(BalanceAssetItem bai);

	public void insertOrUpdateBA(BalanceAssetData bad);

	public void insertOrUpdateBAs(BalanceAssetData bad);

	public void insertOrUpdateBAs(List<BalanceAssetData> bas);

	public List<BalanceAssetItem> listBais();

	public List<BalanceAssetItem> listBais(String assetCode);

	public List<String> listBSAssetsCodes();

	public List<Date> listBSAssetsPeriods(int periodType);

	public List<BalanceAssetData> listBAsByAssetsCodeAndYear(String assetCode, Date year, int periodType);

	public List<BalanceAssetData> listBAsByAssetCodesAndDates(List<String> assetCodes, List<Date> assetPeriods,
			int periodType);

	public BalanceAssetData getPeriodStartValue(BalanceAssetData bad);

	public BalanceAssetData getPeriodEndValue(BalanceAssetData bad);

	public List<BalanceAssetData> calculateBs(Date start, Date end);

	public List<BalanceAssetData> calculateStartBs(Date start, Date end);

	public List<BalanceAssetData> calculateInBs(Date start, Date end);

	public List<BalanceAssetData> calculateEndBs(Date start, Date end, int maKkt);

	public boolean kiemTraBs(Date period, int periodType);

	// SR
	public void insertOrUpdateSR(BalanceAssetData sr);

	public void insertOrUpdateSRs(BalanceAssetData sr);

	public void insertOrUpdateSRs(List<BalanceAssetData> srs);

	public List<BalanceAssetItem> listSRBais();

	public List<String> listSRAssetsCodes();

	public List<Date> listSRAssetsPeriods(int periodType);

	public List<BalanceAssetData> listSRssByAssetsCodeAndYear(String assetCode, Date year, int periodType);

	public List<BalanceAssetData> listSRsByAssetCodesAndDates(List<String> assetCodes, List<Date> assetPeriods,
			int periodType);

	public BalanceAssetData getSRPeriodStartValue(BalanceAssetData bad);

	public BalanceAssetData getSRPeriodEndValue(BalanceAssetData bad);

	public List<BalanceAssetData> calculateSRBs(Date start, Date end);

	// CF
	public int insertCFBai(BalanceAssetItem bai);

	public void deleteCFBai(BalanceAssetItem bai);

	public void insertOrUpdateCF(BalanceAssetData sr);

	public void insertOrUpdateCFs(BalanceAssetData cf);

	public void insertOrUpdateCFs(List<BalanceAssetData> cfs);

	public List<BalanceAssetItem> listCFBais();

	public List<String> listCFAssetsCodes();

	public List<Date> listCFAssetsPeriods(int periodType);

	public List<BalanceAssetData> listCFssByAssetsCodeAndYear(String assetCode, Date year, int periodType);

	public List<BalanceAssetData> listCFsByAssetCodesAndDates(List<String> assetCodes, List<Date> assetPeriods,
			int periodType);

	public BalanceAssetData getCFPeriodStartValue(BalanceAssetData bad);

	public BalanceAssetData getCFPeriodEndValue(BalanceAssetData bad);

	public List<BalanceAssetData> calculateCFBs(Date start, Date end);

	// Khác
	public List<LoaiTaiKhoan> danhSachTkktThuocNhieuChiTieu();

	public List<BalanceAssetItem> danhSachCdktTheoTkkt(String maTk, int soDu);

}
