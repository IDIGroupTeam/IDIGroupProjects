package com.idi.finance.utils;

import java.util.ArrayList;
import java.util.Calendar;
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

	public static List<Date> convertDateList(List<String> dates) {
		if (dates == null || dates.size() == 0)
			return null;

		List<Date> dateRs = new ArrayList<>();
		Iterator<String> datesIter = dates.iterator();
		while (datesIter.hasNext()) {
			String dateStr = (String) datesIter.next();
			Calendar cal = Calendar.getInstance();
			
		}
		return dateRs;
	}

	public static Date standardDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}
}
