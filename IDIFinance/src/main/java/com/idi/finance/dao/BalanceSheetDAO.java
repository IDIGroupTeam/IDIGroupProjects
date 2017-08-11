package com.idi.finance.dao;

import java.util.List;

import com.idi.finance.bean.BalanceSheet;

public interface BalanceSheetDAO {
	public void insertOrUpdateBss(List<BalanceSheet> bss);
}
