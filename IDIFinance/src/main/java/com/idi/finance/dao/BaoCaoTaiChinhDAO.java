package com.idi.finance.dao;

import java.util.Date;
import java.util.List;

import com.idi.finance.bean.bctc.BaoCaoTaiChinh;
import com.idi.finance.bean.bctc.BaoCaoTaiChinhChiTiet;
import com.idi.finance.bean.bctc.BaoCaoTaiChinhCon;

public interface BaoCaoTaiChinhDAO {
	public List<BaoCaoTaiChinh> danhSachBctc(int maKkt);

	public BaoCaoTaiChinh layBctc(int maBctc);

	public BaoCaoTaiChinh themBctc(BaoCaoTaiChinh bctc);

	public void xoaBctc(int maBctc);

	public BaoCaoTaiChinhCon layBctcConCdkt(int maBctcCon);
	
	public BaoCaoTaiChinhCon layBctcConKqhdkd(int maBctcCon);
	
	public BaoCaoTaiChinhCon layBctcConLctt(int maBctcCon);

	public List<BaoCaoTaiChinhChiTiet> calculateEndBs(Date start, Date end, int maKkt);

	public List<BaoCaoTaiChinhChiTiet> calculateEndSR(Date start, Date end);

	public List<BaoCaoTaiChinhChiTiet> calculateEndCFBs(Date start, Date end);
}
