package com.idi.finance.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.idi.finance.bean.bctc.KyKeToanCon;

public class Utils {
	private static int precision = 2;
	private static MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);

	public static List<String> parseString(String str) {
		if (str == null) {
			return null;
		}

		List<String> rs = new ArrayList<>();
		String[] tmp = str.split(";");
		for (int i = 0; i < tmp.length; i++) {
			if (!tmp[i].trim().isEmpty()) {
				rs.add(tmp[i].trim());
			}
		}

		return rs;

	}

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

	public static Date moveDate(Date date, int dateType, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(dateType, num);
		return cal.getTime();
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

		if (periodType == KyKeToanCon.WEEK) {
			cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		} else if (periodType == KyKeToanCon.MONTH) {
			cal.set(Calendar.DAY_OF_MONTH, 1);
		} else if (periodType == KyKeToanCon.QUARTER) {
			int month = Math.round(cal.get(Calendar.MONTH) / 3) * 3;
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DAY_OF_MONTH, 1);
		} else if (periodType == KyKeToanCon.YEAR) {
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

		if (periodType == KyKeToanCon.WEEK) {
			cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
			cal.add(Calendar.WEEK_OF_YEAR, 1);
			cal.add(Calendar.MILLISECOND, -1);
		} else if (periodType == KyKeToanCon.MONTH) {
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.MILLISECOND, -1);
		} else if (periodType == KyKeToanCon.QUARTER) {
			int month = Math.round(cal.get(Calendar.MONTH) / 3) * 3;
			cal.set(Calendar.MONTH, month + 3);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.add(Calendar.MILLISECOND, -1);
		} else if (periodType == KyKeToanCon.YEAR) {
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

	public static KyKeToanCon nextPeriod(KyKeToanCon ky) {
		if (ky == null || ky.getCuoi() == null)
			return null;

		KyKeToanCon nextPeriod = new KyKeToanCon();
		nextPeriod.setLoai(ky.getLoai());

		if (ky.getLoai() == KyKeToanCon.NAN) {
			Date dau = new Date();
			dau.setTime(ky.getCuoi().getTime() + 1);
			nextPeriod.setDau(dau);
			nextPeriod.setCuoi(null);
		} else {
			nextPeriod.setDau(nextPeriod(ky.getDau(), ky.getLoai()));
			nextPeriod.setCuoi(getEndPeriod(nextPeriod.getDau(), nextPeriod.getLoai()));
		}

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

	public static KyKeToanCon prevPeriod(KyKeToanCon ky) {
		if (ky == null || ky.getDau() == null)
			return null;

		KyKeToanCon prevPeriod = new KyKeToanCon();
		prevPeriod.setLoai(ky.getLoai());

		if (ky.getLoai() == KyKeToanCon.NAN) {
			Date cuoi = new Date();
			cuoi.setTime(ky.getDau().getTime() - 1);
			prevPeriod.setDau(null);
			prevPeriod.setCuoi(cuoi);
		} else {
			prevPeriod.setDau(prevPeriod(ky.getDau(), ky.getLoai()));
			prevPeriod.setCuoi(getEndPeriod(prevPeriod.getDau(), prevPeriod.getLoai()));
		}

		return prevPeriod;
	}

	public static List<Date> listStartPeriods(Date start, Date end, int periodType) {
		if (start == null || end == null)
			return null;

		List<Date> rs = new ArrayList<>();

		Date startPeriod = Utils.getStartPeriod(start, periodType);
		Date count = startPeriod;
		while (count.before(end)) {
			rs.add(count);
			count = Utils.nextPeriod(count, periodType);
		}

		return rs;
	}

	public static double multiply(double a, double b) {
		if (mc == null) {
			mc = new MathContext(precision, RoundingMode.HALF_UP);
		}

		BigDecimal aObj = new BigDecimal(a + "");
		BigDecimal bObj = new BigDecimal(b + "");
		return aObj.multiply(bObj, mc).doubleValue();
	}

	public static double divide(double a, double b) {
		if (mc == null) {
			mc = new MathContext(precision, RoundingMode.HALF_UP);
		}

		BigDecimal aObj = new BigDecimal(a + "");
		BigDecimal bObj = new BigDecimal(b + "");
		return aObj.divide(bObj, mc).doubleValue();
	}

	public static double add(double a, double b) {
		if (mc == null) {
			mc = new MathContext(precision, RoundingMode.HALF_UP);
		}

		BigDecimal aObj = new BigDecimal(a + "");
		BigDecimal bObj = new BigDecimal(b + "");
		return aObj.add(bObj, mc).doubleValue();
	}

	public static double subtract(double a, double b) {
		if (mc == null) {
			mc = new MathContext(precision, RoundingMode.HALF_UP);
		}

		BigDecimal aObj = new BigDecimal(a + "");
		BigDecimal bObj = new BigDecimal(b + "");
		return aObj.subtract(bObj, mc).doubleValue();
	}

	public static void main(String[] args) {
		Double a = Double.valueOf("4.6");
		Double b = Double.valueOf("5.8");
		Double mul = a * b;
		Double div = a / b;
		Double add = a + b;
		Double sub = a - b;

		// System.out.println(Float.valueOf("4.6") + Float.valueOf("5.8"));
		// System.out.println(Float.valueOf(4.6f) + Float.valueOf(5.8f));
		System.out.println(Double.valueOf("4.6") + Double.valueOf("5.8"));
		System.out.println((Double.valueOf(4.6) + Double.valueOf(5.8)));

		double x = 12.7;
		double y = 3.1;
		double z = x / y;
		double t = x + y;
		System.out.println("x: " + x);
		System.out.println("y: " + y);
		System.out.println("z: " + z);
		System.out.println("y*z: " + (y * z));
		System.out.println("t: " + t);
		System.out.println("t-y: " + (t - y));
	}

	public static double round(double value) {
		long factor = (long) Math.pow(10, precision);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public static double round(double value, int precision) {
		if (precision < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, precision);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
}
