package com.idi.finance.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.idi.finance.bean.BalanceAssetData;
import com.idi.finance.bean.BalanceAssetItem;
import com.idi.finance.bean.BalanceSheet;

public class Utils {
	public static String format(String str) {
		if (str != null) {
			str = str.replaceAll("\\s+", " ");
			str = str.trim();

			str = str.toLowerCase();
			str = str.substring(0, 1).toUpperCase() + str.substring(1);
		}
		return str;
	}

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

	public static List<Date> convertArray2List(String[] periods) {
		if (periods == null)
			return null;

		List<Date> results = new ArrayList<>();
		for (int i = 0; i < periods.length; i++) {
			try {
				SimpleDateFormat format = new SimpleDateFormat("M/yyyy");
				Date period = format.parse(periods[i]);
				results.add(Utils.standardDate(period));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return results;
	}

	public static String[] convertList2Array(List<Date> periods) {
		if (periods == null || periods.size() == 0)
			return null;

		String[] results = new String[periods.size()];
		int i = 0;
		Iterator<Date> iter = periods.iterator();
		while (iter.hasNext()) {
			Date date = iter.next();
			SimpleDateFormat format = new SimpleDateFormat("M/yyyy");
			results[i] = format.format(date);
			i++;
		}
		return results;
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
