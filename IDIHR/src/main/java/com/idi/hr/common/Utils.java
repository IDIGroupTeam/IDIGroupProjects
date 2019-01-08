package com.idi.hr.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Utils {
	
	public static Calendar firstDayOfWeek(Calendar calendar){
	     Calendar cal = (Calendar) calendar.clone();
	     int day = cal.get(Calendar.DAY_OF_YEAR);
	     while(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
	          cal.set(Calendar.DAY_OF_YEAR, --day);
	     }
	     System.err.println("util " + cal.getTime());
	     return cal;
	}

	public static Calendar lastDayOfWeek(Calendar calendar){
	     Calendar cal = (Calendar) calendar.clone();
	     int day = cal.get(Calendar.DAY_OF_YEAR);
	     while(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY){
	          cal.set(Calendar.DAY_OF_YEAR, ++day);
	     }
	     return cal;
	}
	
	// This takes a 1-based month, e.g. January=1. If you want to use a 0-based
	// month, remove the "- 1" later on.
	public static int countWeekendDays(int year, int month) {
	    Calendar calendar = Calendar.getInstance();
	    // Note that month is 0-based in calendar, bizarrely.
	    calendar.set(year, month - 1, 1);
	    int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

	    int count = 0;
	    for (int day = 1; day <= daysInMonth; day++) {
	        calendar.set(year, month - 1, day);
	        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
	        if (dayOfWeek == Calendar.SATURDAY) {// ||dayOfWeek == Calendar.SUNDAY 
	            count++;
	            // Or do whatever you need to with the result.
	        }
	    }
	    return count;
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
	
	public static int monthsBetween(final Date s1, final Date s2) {
	    final Calendar d1 = Calendar.getInstance();
	    d1.setTime(s1);
	    final Calendar d2 = Calendar.getInstance();
	    d2.setTime(s2);
	    int diff = (d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR)) * 12 + d2.get(Calendar.MONTH) - d1.get(Calendar.MONTH);
	    return diff;
	}
	
/*	public static Map<String, String> workStatusMap() {
		Map<String, String> workStatusMap = new LinkedHashMap<String, String>();
		workStatusMap.put("ThuViec", "Thử việc");
		workStatusMap.put("ThoiVu", "Thời vụ");
		workStatusMap.put("CongTac", "Cộng tác");
		workStatusMap.put("ChinhThuc", "Chính thức");
		workStatusMap.put("NghiThaiSan", "Nghỉ thai sản");
		workStatusMap.put("NghiOm", "Nghỉ ốm");
		workStatusMap.put("NghiKhongLuong", "Nghỉ không lương");
		workStatusMap.put("DaThoiViec", "Đã thôi việc");
		return workStatusMap;
	}*/
}
