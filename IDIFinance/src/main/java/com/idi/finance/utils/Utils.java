package com.idi.finance.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.idi.finance.bean.BalanceSheet;

public class Utils {
	public static HashMap<Date, BalanceSheet> convertList2Map(List<BalanceSheet> bss) {
		if (bss != null) {
			HashMap<Date, BalanceSheet> map = new HashMap<>();

			Iterator<BalanceSheet> iter = bss.iterator();
			while (iter.hasNext()) {
				BalanceSheet bs = iter.next();

				if (bs.getAssetsPeriod() != null) {
					map.put(bs.getAssetsPeriod(), bs);
				}
			}
			return map;
		}
		return null;
	}
}
