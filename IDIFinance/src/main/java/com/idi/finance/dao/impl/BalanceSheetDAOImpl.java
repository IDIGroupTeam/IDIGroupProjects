package com.idi.finance.dao.impl;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.idi.finance.bean.BalanceSheet;
import com.idi.finance.dao.BalanceSheetDAO;

public class BalanceSheetDAOImpl implements BalanceSheetDAO {
	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Override
	public void insertOrUpdateBss(List<BalanceSheet> bss) {
		if (bss == null)
			return;

		String updateQry = "UPDATE BALANCE_SHEET SET ASSETS_VALUE=?, CHANGED_RATIO WHERE ASSETS_CODE=? AND ASSETS_PERIOD=?";
		String insertQry = "INSERT INTO BALANCE_SHEET(ASSETS_NAME, ASSETS_CODE, NOTE, ASSETS_VALUE, CHANGED_RATIO, ASSETS_PERIOD,) VALUES(?, ?, ?, ?, ?, ?)";

		Iterator<BalanceSheet> iter = bss.iterator();
		while (iter.hasNext()) {
			BalanceSheet bs = iter.next();

			int count = 0;
			try {
				// update firstly, if now row is updated, we will be insert data
				Timestamp assetsPeriod = new Timestamp(bs.getAssetsPeriod().getTime());
				count = jdbcTmpl.update(updateQry, bs.getAssetsCode(), assetsPeriod);

				// This is new data, so insert it.
				if (count == 0) {
					count = jdbcTmpl.update(insertQry, bs.getAssetsName(), bs.getAssetsCode(), bs.getNote(),
							bs.getAssetsValue(), bs.getChangedRatio(), assetsPeriod);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
