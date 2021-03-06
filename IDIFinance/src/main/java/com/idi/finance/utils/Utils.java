package com.idi.finance.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.idi.finance.bean.KyKeToan;

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

	public static List<Date> convertArray2List(String[] periods) {
		if (periods == null)
			return null;

		List<Date> results = new ArrayList<>();
		for (int i = 0; i < periods.length; i++) {
			try {
				SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");
				Date period = format.parse(periods[i]);
				results.add(Utils.getStartDateOfMonth(period));
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
			SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");
			results[i] = format.format(date);
			i++;
		}
		return results;
	}

	public static Date getStartDateOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	public static Date getEndDateOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MONTH, 1);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		cal.add(Calendar.MILLISECOND, -1);

		return cal.getTime();
	}

	public static Date getStartPeriod(Date date, int periodType) {
		Calendar cal = Calendar.getInstance(Locale.FRANCE);
		cal.setTime(date);

		if (periodType == KyKeToan.WEEK) {
			cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		} else if (periodType == KyKeToan.MONTH) {
			cal.set(Calendar.DAY_OF_MONTH, 1);
		} else if (periodType == KyKeToan.QUARTER) {
			int month = Math.round(cal.get(Calendar.MONTH) / 3) * 3;
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DAY_OF_MONTH, 1);
		} else if (periodType == KyKeToan.YEAR) {
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DAY_OF_MONTH, 1);
		} else {
			cal.set(Calendar.DAY_OF_MONTH, 1);
		}

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	public static Date getEndPeriod(Date date, int periodType) {
		Calendar cal = Calendar.getInstance(Locale.FRANCE);
		cal.setTime(date);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		if (periodType == KyKeToan.WEEK) {
			cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
			cal.add(Calendar.WEEK_OF_YEAR, 1);
			cal.add(Calendar.MILLISECOND, -1);
		} else if (periodType == KyKeToan.MONTH) {
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.MILLISECOND, -1);
		} else if (periodType == KyKeToan.QUARTER) {
			int month = Math.round(cal.get(Calendar.MONTH) / 3) * 3;
			cal.set(Calendar.MONTH, month + 3);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.add(Calendar.MILLISECOND, -1);
		} else if (periodType == KyKeToan.YEAR) {
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.add(Calendar.YEAR, 1);
			cal.add(Calendar.MILLISECOND, -1);
		} else {
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.MILLISECOND, -1);
		}

		return cal.getTime();
	}

	public static Date nextPeriod(Date date, int periodType) {
		date = getEndPeriod(date, periodType);
		Calendar cal = Calendar.getInstance(Locale.FRANCE);
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	public static KyKeToan nextPeriod(KyKeToan ky) {
		KyKeToan nextPeriod = new KyKeToan();
		nextPeriod.setLoai(ky.getLoai());
		nextPeriod.setDau(nextPeriod(ky.getDau(), ky.getLoai()));
		nextPeriod.setCuoi(getEndPeriod(nextPeriod.getDau(), nextPeriod.getLoai()));
		return nextPeriod;
	}

	public static Date prevPeriod(Date date, int periodType) {
		date = getStartPeriod(date, periodType);
		Calendar cal = Calendar.getInstance(Locale.FRANCE);
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		date = getStartPeriod(cal.getTime(), periodType);
		cal.setTime(date);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	public static KyKeToan prevPeriod(KyKeToan ky) {
		KyKeToan prevPeriod = new KyKeToan();
		prevPeriod.setLoai(ky.getLoai());
		prevPeriod.setDau(prevPeriod(ky.getDau(), ky.getLoai()));
		prevPeriod.setCuoi(getEndPeriod(prevPeriod.getDau(), prevPeriod.getLoai()));
		return prevPeriod;
	}
}
