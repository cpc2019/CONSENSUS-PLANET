package com.pingan.baselibs.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static String timeStamp2Date(long time) {
		return timeStamp2Date(time, null);
	}

	public static String timeStamp2Day(long time) {
		String format = "yyyy/MM/dd";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(time)).trim();
	}

	public static String timeStamp2Date(long time, String format) {
		if (format == null || format.isEmpty()) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(time)).trim();
	}

	/**
	 * 秒转化为时分秒
	 */
	public static String second2Time(int time) {
		String timeStr = null;
		int hour = 0;
		int minute = 0;
		int second = 0;
		if (time <= 0) {
			return "00:00";
		} else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
				timeStr = unitFormat(minute) + ":" + unitFormat(second);
			} else {
				hour = minute / 60;
				if (hour > 99) return "99:59:59";
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
			}
		}
		return timeStr;
	}

	public static String unitFormat(int i) {
		String retStr = null;
		if (i >= 0 && i < 10) {
			retStr = "0" + Integer.toString(i);
		} else {
			retStr = "" + i;
		}
		return retStr;
	}

	public static long getStringToDate(String dateString, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		Date date = new Date();
		try {
			date = dateFormat.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date.getTime();
	}
}
