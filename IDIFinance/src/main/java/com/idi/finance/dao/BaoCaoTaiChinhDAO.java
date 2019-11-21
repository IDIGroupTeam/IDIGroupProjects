package com.idi.finance.dao;

import java.util.Date;
import java.util.List;

import com.idi.finance.bean.bctc.BaoCaoTaiChinh;
import com.idi.finance.bean.bctc.BaoCaoTaiChinhChiTiet;

public interface BaoCaoTaiChinhDAO {
	public List<BaoCaoTaiChinh> danhSachBctc(int maKkt);

	public BaoCaoTaiChinh layBctc(int maBctc);

	public BaoCaoTaiChinh themBctc(BaoCaoTaiChinh bctc);

	public void xoaBctc(int maBctc);

	public List<BaoCaoTaiChinhChiTiet> calculateEndBs(Date start, Date end, int maKkt);

	public List<BaoCaoTaiChinhChiTiet> calculateEndSR(Date start, Date end);

	public List<BaoCaoTaiChinhChiTiet> calculateEndCFBs(Date start, Date end);
}
