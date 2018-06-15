package com.idi.finance.dao;

import java.util.Date;
import java.util.List;

import com.idi.finance.bean.cdkt.BalanceAssetData;
import com.idi.finance.bean.cdkt.BalanceAssetItem;
import com.idi.finance.bean.chungtu.TaiKhoan;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;

public interface BalanceSheetDAO {
	public void insertOrUpdateBA(BalanceAssetData bad);

	public void insertOrUpdateBAs(BalanceAssetData bad);

	public void insertOrUpdateBAs(List<BalanceAssetData> bas);

	public List<BalanceAssetData> listBAsByAssetsCodeAndYear(String assetCode, Date year, int periodType);

	public List<BalanceAssetData> listBAsByAssetCodesAndDates(List<String> assetCodes, List<Date> assetPeriods,
			int periodType);

	public void insertOrUpdateSRs(BalanceAssetData sr);

	public void insertOrUpdateSRs(List<BalanceAssetData> srs);

	public List<BalanceAssetData> listSRssByAssetsCodeAndYear(String assetCode, Date year, int periodType);

	public void insertOrUpdateCFs(BalanceAssetData cf);

	public void insertOrUpdateCFs(List<BalanceAssetData> cfs);

	public List<BalanceAssetData> listCFssByAssetsCodeAndYear(String assetCode, Date year, int periodType);

	public List<String> listBSAssetsCodes();

	public List<Date> listBSAssetsPeriods(int periodType);

	public List<String> listSRAssetsCodes();

	public List<Date> listSRAssetsPeriods();

	public List<LoaiTaiKhoan> danhSachTkktThuocNhieuChiTieu();

	public List<BalanceAssetItem> danhSachCdktTheoTkkt(String maTk, int soDu);

	public List<BalanceAssetItem> listBais();

	public BalanceAssetData getPeriodEndValue(BalanceAssetData bad);

	public BalanceAssetData calculateBs(BalanceAssetData bad);
}
